package com.example.restaurantroulette;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    //variables used in this activity
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //attaching variables to their respective views
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        //setting click listeners
        bottomNavigationView.setOnNavigationItemSelectedListener( new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.action_home:
                        Toast.makeText(MainActivity.this, "Home screen button works!", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_user:
                        Toast.makeText(MainActivity.this, "User button works!", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_history:
                        Toast.makeText(MainActivity.this, "History screen button works!", Toast.LENGTH_SHORT).show();
                    default:
                         break;
                }
                return true;
            }
        });
    }
}