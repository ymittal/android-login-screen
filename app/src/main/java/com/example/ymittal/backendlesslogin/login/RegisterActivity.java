package com.example.ymittal.backendlesslogin.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.ymittal.backendlesslogin.R;

public class RegisterActivity extends AppCompatActivity {
    private EditText etEmail;
    private EditText etName;
    private EditText etPassword;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initUI();
    }

    private void initUI() {
        etEmail = (EditText) findViewById(R.id.etEmail);
        etName = (EditText) findViewById(R.id.etName);
        etPassword = (EditText) findViewById(R.id.etPassword);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRegisterButtonClicked();
            }
        });
    }

    private void onRegisterButtonClicked() {
        if (etEmail.getText().toString().equals("")) {
            etEmail.setError(getString(R.string.error_email));
            return;
        }
        if (etPassword.getText().toString().equals("")) {
            etEmail.setError(getString(R.string.error_pass));
            return;
        }

        BackendlessUser newUser = new BackendlessUser();
        newUser.setEmail(etEmail.getText().toString());
        newUser.setProperty("name", etName.getText().toString());
        newUser.setPassword(etPassword.getText().toString());

        Backendless.UserService.register(newUser, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {
                Toast.makeText(RegisterActivity.this, R.string.register_success,
                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(RegisterActivity.this, R.string.register_fail,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}