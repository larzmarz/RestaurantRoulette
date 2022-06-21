package com.example.restaurantroulette;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseUser;

public class SignUpActivity extends AppCompatActivity {
    ParseUser user = new ParseUser();
    public static final String TAG = "LoginActivity";
    public EditText etUsernameSU;
    public EditText etPasswordSU;
    public EditText etEmailSU;
    public EditText etFullName;

    public Button btLoginSU;
    public Button btSignupSU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etUsernameSU = findViewById(R.id.etUsername);
        etPasswordSU = findViewById(R.id.etPassword);
        etEmailSU = findViewById(R.id.etEmailSU);
        etFullName = findViewById(R.id.etFullName);
        btLoginSU = findViewById(R.id.btLogin);
        btSignupSU = findViewById(R.id.btSignup);
        user.setUsername(etUsernameSU.getText().toString());
        user.setPassword(etPasswordSU.getText().toString());
        user.setEmail(etEmailSU.getText().toString());

    }
}