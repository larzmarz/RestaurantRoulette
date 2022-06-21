package com.example.restaurantroulette;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.restaurantroulette.fragment.HistoryFragment;
import com.example.restaurantroulette.fragment.HomeFragment;
import com.example.restaurantroulette.fragment.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    //variables used in this activity
    final FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView bottomNavigationView;
    HistoryFragment historyfragment = new HistoryFragment();
    HomeFragment homeFragment = new HomeFragment();
    UserFragment userFragment = new UserFragment();
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
                        fragment = homeFragment;
                        break;
                    case R.id.action_user:
                        Toast.makeText(MainActivity.this, "User button works!", Toast.LENGTH_SHORT).show();
                        fragment = userFragment;
                        break;
                    case R.id.action_history:
                        Toast.makeText(MainActivity.this, "History screen button works!", Toast.LENGTH_SHORT).show();
                    default:
                        fragment =historyfragment;
                         break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.action_home);
    }
}