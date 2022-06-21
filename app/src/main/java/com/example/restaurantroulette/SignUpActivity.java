package com.example.restaurantroulette;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {
    ParseUser user = new ParseUser();
    public static final String TAG = "SignupActivity";
    public EditText etUsernameSU;
    public EditText etPasswordSU;
    public EditText etEmailSU;
    public EditText etFullName;
    public Button btSignupSU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etUsernameSU = findViewById(R.id.etUsername);
        etPasswordSU = findViewById(R.id.etPassword);
        //etEmailSU = findViewById(R.id.etEmailSU);
        //etFullName = findViewById(R.id.etFullName);
        //btLoginSU = findViewById(R.id.btLogin);
        btSignupSU = findViewById(R.id.btSignup);
        btSignupSU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Log.i(TAG, "onClick signup button");
                    String username = etUsernameSU.getText().toString();
                    String password = etPasswordSU.getText().toString();
                    signupUser(username, password);
            }
        });
    }
    private void signupUser(String username, String password) {
        Log.i(TAG, "Attempting to signup user" + username);
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with signup", e);
                    Toast.makeText(SignUpActivity.this, "Issue with Signup: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                goMainActivity();
                Toast.makeText(SignUpActivity.this, "Signup Success", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void goMainActivity() {
        Intent i = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}