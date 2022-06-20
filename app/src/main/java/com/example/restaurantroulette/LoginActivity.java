package com.example.restaurantroulette;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "LoginActivity";
    public EditText etUsername;
    public EditText etPassword;
    public Button btLogin;
    public Button btSignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btLogin = findViewById(R.id.btLogin);
        btSignup = findViewById(R.id.btSignup);
        //activate login button
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                loginUser(username, password);
            }
        });
        //activate signup button
        btSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goSignupActivity();
            }
        });
    }
    //processes user's credentials
    private void loginUser(String username, String password) {
        Log.i(TAG, "Attempting to log in user: " + username + " " + password);
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e != null){
                    Toast.makeText(LoginActivity.this, "Issues with login", Toast.LENGTH_SHORT).show();
                    return;
                }
                //directing user to homescreen if no errors are present
                goMainActivity();
                Toast.makeText(LoginActivity.this, "Successfully logged in!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //redirect user to home screen
    private void goMainActivity() {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
    //redirect user to sign up screen
    private void goSignupActivity() {
        Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(i);
        finish();
    }
}