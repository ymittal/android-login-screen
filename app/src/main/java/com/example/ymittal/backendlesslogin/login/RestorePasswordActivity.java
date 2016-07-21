package com.example.ymittal.backendlesslogin.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.ymittal.backendlesslogin.R;

public class RestorePasswordActivity extends AppCompatActivity {
    private EditText etEmail;
    private Button btnRestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore_password);

        initUI();
    }

    private void initUI() {
        etEmail = (EditText) findViewById(R.id.etEmail);

        btnRestore = (Button) findViewById(R.id.btnRestore);
        btnRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRestorePasswordButtonClicked();
            }
        });
    }

    private void onRestorePasswordButtonClicked() {
        if (etEmail.getText().toString().equals("")) {
            etEmail.setError(getString(R.string.error_email));
            return;
        }
        Backendless.UserService.restorePassword(etEmail.getText().toString(),
                new AsyncCallback<Void>() {
                    @Override
                    public void handleResponse(Void response) {
                        Toast.makeText(RestorePasswordActivity.this,
                                R.string.restore_email_sent,
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(RestorePasswordActivity.this,
                                getString(R.string.restore_email_fail),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}