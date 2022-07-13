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
    public EditText etFullNameSU;
    public EditText etPwdRetypeSU;
    public Button btSignupSU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etUsernameSU = findViewById(R.id.etUsernameSU);
        etPasswordSU = findViewById(R.id.etPasswordSU);
        etFullNameSU = findViewById(R.id.etFullNameSU);
        etPwdRetypeSU = findViewById(R.id.etPwdRetypeSU);
        btSignupSU = findViewById(R.id.btSignupSU);
        //signs user up
        btSignupSU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if all the fields are filled out
                if (!etUsernameSU.getText().toString().isEmpty() && !etPasswordSU.getText().toString().isEmpty() && !etFullNameSU.getText().toString().isEmpty() && !etPwdRetypeSU.getText().toString().isEmpty()){
                    //if the passwords match
                    if (etPasswordSU.getText().toString().equals(etPwdRetypeSU.getText().toString())){
                        ParseUser user = new ParseUser();
                        user.setUsername(etUsernameSU.getText().toString());
                        user.setPassword(etPasswordSU.getText().toString());
                        //TODO: save the full names to the backend
                        user.signUpInBackground(new SignUpCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    Toast.makeText(SignUpActivity.this, "SignupSuccessful!", Toast.LENGTH_SHORT).show();
                                    goMainActivity();
                                }else {
                                    Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    //if the passwords don't match
                    }else {
                        Toast.makeText(SignUpActivity.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                    }
                //if any field is empty
                }else {
                    Toast.makeText(SignUpActivity.this, "Fill out all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //redirect user to main page once authenticated and signed up
    private void goMainActivity() {
        Intent i = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}