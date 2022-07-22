package com.example.restaurantroulette.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantroulette.R;
import com.example.restaurantroulette.SearchPageActivity;

public class HomeFragment extends Fragment implements View.OnClickListener{
    public Button btStart;
    public EditText etZipCode;
    public TextView tvMileInstructions;
    public EditText etMileRadius;
    public String zipCode;
    public String mileRadius;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        etZipCode = view.findViewById(R.id.etZipCode);
        btStart = view.findViewById(R.id.btStartSearch);
        etMileRadius = view.findViewById(R.id.etMileRadius);
        tvMileInstructions = view.findViewById(R.id.tvMileInstructions);

        // when the user clicks the start button
        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Set limits on zip codes so they don't exceed 5 digits, note: some zipcodes start with two zeros
                if (etZipCode.getText().toString().isEmpty() || etMileRadius.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Enter your Zip Code and Mile Radius", Toast.LENGTH_SHORT).show();
                }else{
                    // this zip code is what will go into the yelp location section
                    zipCode = etZipCode.getText().toString();
                    mileRadius = etMileRadius.getText().toString();
                    goSearchPage();
                }
            }
        });
        return view;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstances) {
        super.onViewCreated(view, savedInstances);
    }
    // redirects user to page where they will search for a restaurant
    private void goSearchPage() {
        Intent i = new Intent(getActivity(), SearchPageActivity.class);
        i.putExtra("zip", zipCode);
        i.putExtra("radius", mileRadius);
        startActivity(i);
    }
    // empty method for implementation of view.onclicklistener
    @Override
    public void onClick(View v) {}
    public String getZip(){
        return zipCode;
    }
}