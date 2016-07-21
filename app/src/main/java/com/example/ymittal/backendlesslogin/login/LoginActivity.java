package com.example.ymittal.backendlesslogin.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.async.callback.BackendlessCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.ymittal.backendlesslogin.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private TextView tvRegister;
    private TextView tvRestore;
    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private CheckBox checkboxRemember;
    private Button btnFacebook;
    private Button btnGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkAutomaticLogin();

        setContentView(R.layout.activity_login);
        initViews();

        if (Backendless.UserService.CurrentUser() != null)
            Toast.makeText(LoginActivity.this, Backendless.UserService.CurrentUser().getUserId(),
                    Toast.LENGTH_SHORT).show();
    }

    private void checkAutomaticLogin() {
        Backendless.UserService.isValidLogin(new BackendlessCallback<Boolean>() {
            @Override
            public void handleResponse(Boolean isValidLogin) {
                if (isValidLogin && Backendless.UserService.CurrentUser() == null) {
                    String currentUserId = Backendless.UserService.loggedInUser();
                    if (!currentUserId.equals("")) {
                        Backendless.UserService.findById(currentUserId,
                                new BackendlessCallback<BackendlessUser>() {
                                    @Override
                                    public void handleResponse(BackendlessUser currentUser) {
                                        Backendless.UserService.setCurrentUser(currentUser);
                                        loginSuccess();
                                    }
                                });
                    }
                }
            }
        });
    }

    private void initViews() {
        tvRegister = (TextView) findViewById(R.id.tvRegister);
        tvRestore = (TextView) findViewById(R.id.tvRestore);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        checkboxRemember = (CheckBox) findViewById(R.id.checkboxRemember);
        btnFacebook = (Button) findViewById(R.id.btnFacebook);
        btnGoogle = (Button) findViewById(R.id.btnGoogle);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLoginButtonClicked();
            }
        });
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        tvRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RestorePasswordActivity.class));
            }
        });
        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLoginWithFacebookButtonClicked();
            }
        });

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLoginWithGoogleButtonClicked();
            }
        });
    }

    public void onLoginWithFacebookButtonClicked() {
        Map<String, String> fieldMap = new HashMap<>();
        fieldMap.put("name", "name");
        fieldMap.put("gender", "gender");
        fieldMap.put("email", "email");

        List<String> facebookPermissions = new ArrayList<>();
        facebookPermissions.add("email");

        Backendless.UserService.loginWithFacebook(LoginActivity.this, null, fieldMap, facebookPermissions,
                new AsyncCallback<BackendlessUser>() {
                    @Override
                    public void handleResponse(BackendlessUser response) {
                        Toast.makeText(LoginActivity.this, R.string.fb_login_success,
                                Toast.LENGTH_SHORT).show();
                        loginSuccess();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(LoginActivity.this, getString(R.string.login_fail),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loginSuccess() {
        // TODO: go to home screen
    }

    private void onLoginWithGoogleButtonClicked() {
        Map<String, String> fieldMap = new HashMap<>();
        fieldMap.put("name", "name");
        fieldMap.put("gender", "gender");
        fieldMap.put("email", "email");

        List<String> googlePermissions = new ArrayList<>();

        Backendless.UserService.loginWithGooglePlus(LoginActivity.this, null, fieldMap, googlePermissions,
                new AsyncCallback<BackendlessUser>() {
                    @Override
                    public void handleResponse(BackendlessUser response) {
                        Toast.makeText(LoginActivity.this, R.string.google_login_success,
                                Toast.LENGTH_SHORT).show();
                        loginSuccess();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(LoginActivity.this, getString(R.string.login_fail),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void onLoginButtonClicked() {
        Backendless.UserService.login(etEmail.getText().toString(),
                etPassword.getText().toString(),
                new AsyncCallback<BackendlessUser>() {
                    @Override
                    public void handleResponse(BackendlessUser response) {
                        Toast.makeText(LoginActivity.this, R.string.email_login_success,
                                Toast.LENGTH_SHORT).show();
                        loginSuccess();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(LoginActivity.this, getString(R.string.login_fail) + fault.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                }, checkboxRemember.isChecked());
    }
}