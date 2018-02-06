package com.cattracker.joe.cattracker;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    EditText usernameTextField;
    EditText passwordTextField;
    Button loginButton;
    Button registerButton;
    LoginDialogManager feedbackDialog = new LoginDialogManager();
    SessionManager session;

    IUserLoginRepository loginRepo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginRepo = MockUserLoginRepository.getInstance(this);
        session = new SessionManager(this);

        usernameTextField = (EditText) findViewById(R.id.login_username_field);
        passwordTextField = (EditText) findViewById(R.id.login_password_field);
        loginButton = (Button) findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                String username = usernameTextField.getText().toString();
                String password = passwordTextField.getText().toString();

                // Verify that user name and password are filled in
                if(username.trim().length() > 0 && password.trim().length() > 0){

                    Uri.Builder builder = new Uri.Builder();
                    builder.path(username.trim());
                    Uri user = builder.build();

                    if(loginRepo.checkUserCredentials(user, password.trim()))
                    {
                        session.createLoginSession(username);

                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        finish();

                    }
                    else
                    {
                        feedbackDialog.showAlertDialog(LoginActivity.this, "Login failed", "Username or password is incorrect", false);
                    }
                }
                else
                {
                    feedbackDialog.showAlertDialog(LoginActivity.this, "Login failed", "Please enter username and password", false);
                }

            }
        });

        registerButton = (Button) findViewById(R.id.login_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameTextField.getText().toString();
                String password = passwordTextField.getText().toString();

                // Verify that user name and password are filled in
                if(username.trim().length() > 0 && password.trim().length() > 0){

                    Uri.Builder builder = new Uri.Builder();
                    builder.path(username.trim());
                    Uri user = builder.build();

                    if(loginRepo.addUser(user, password)){
                        session.createLoginSession(username);

                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        finish();

                    }
                    else
                    {
                        feedbackDialog.showAlertDialog(LoginActivity.this, "Registration failed..", "Username is already taken.", false);
                    }
                }
                else
                {
                    feedbackDialog.showAlertDialog(LoginActivity.this, "Login failed..", "Please enter a username and password", false);
                }
            }
        });
    }
}
