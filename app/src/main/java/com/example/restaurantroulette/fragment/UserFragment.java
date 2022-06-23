package com.example.restaurantroulette.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.restaurantroulette.LoginActivity;
import com.example.restaurantroulette.R;
import com.parse.ParseUser;

public class UserFragment extends Fragment implements View.OnClickListener{
    private Button btLogout;
    View view;
    // Required empty public constructor
    public UserFragment() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user, container, false);
        btLogout = view.findViewById(R.id.btLogout);
        //when the user clicks the logout button
        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Logged out!", Toast.LENGTH_SHORT).show();
                onLogout();
            }
        });
        return view;
    }
    private void onLogout() {
        //logout of account
        ParseUser.logOut();
        ParseUser currentUser =ParseUser.getCurrentUser();
        //redirect to login page
        Intent i = new Intent(getContext(), LoginActivity.class);
        startActivity(i);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstances) {
        super.onViewCreated(view, savedInstances);
    }
    //empty Onclick for onclicklistener implementation
    @Override
    public void onClick(View v) {
    }
}