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

import com.example.restaurantroulette.R;
import com.example.restaurantroulette.SearchPageActivity;

public class HomeFragment extends Fragment implements View.OnClickListener{
    public Button btStart;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        btStart = view.findViewById(R.id.btStartSearch);
        //when the user clicks the start button
        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "redirect successful", Toast.LENGTH_SHORT).show();
                goSearchPage();
            }
        });
        return view;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstances) {
        super.onViewCreated(view, savedInstances);
    }
    //redirects user to page where they will search for a restaurant
    private void goSearchPage() {
        Intent i = new Intent(getActivity(), SearchPageActivity.class);
        startActivity(i);
    }
    //empty method for implementation of view.onclicklistener
    @Override
    public void onClick(View v) {}
}