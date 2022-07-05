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
import android.widget.Toast;

import com.example.restaurantroulette.R;
import com.example.restaurantroulette.SearchPageActivity;

public class HomeFragment extends Fragment implements View.OnClickListener{
    public Button btStart;
    public EditText etZipCode;
    public Spinner spLocation;
    //TODO: change to int when time to set limits on zip codes
    public String zipCode;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        etZipCode = view.findViewById(R.id.etZipCode);
        //zipCode = Integer.parseInt(etZipCode.getText().toString());
        btStart = view.findViewById(R.id.btStartSearch);
        //when the user clicks the start button
        //TODO: See why there is an error in line 41
        /*spLocation = view.findViewById(R.id.spMileRadius);
        String[] mileRadius = getResources().getStringArray(R.array.mile_radius);
        ArrayAdapter adapter2 = new ArrayAdapter(HomeFragment.this, android.R.layout.simple_spinner_item, mileRadius);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLocation.setAdapter(adapter2);*/
        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Set limits on zip codes so they don't exceed 5 digits, note: some zipcodes start with two zeros
                if(etZipCode.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Enter your Zip Code", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Let the adventure begin", Toast.LENGTH_SHORT).show();
                    //this zip code is what will go into the yelp location section
                    zipCode = etZipCode.getText().toString();
                    Log.i("hello", "onClick: " + zipCode);
                    goSearchPage();
                }
            }
        });
        return view;
    }
    private String holdingZipCode(String zipCode) {
        return zipCode;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstances) {
        super.onViewCreated(view, savedInstances);
    }
    //redirects user to page where they will search for a restaurant
    private void goSearchPage() {
        Intent i = new Intent(getActivity(), SearchPageActivity.class);
        i.putExtra("zip", zipCode);
        startActivity(i);
    }
    //empty method for implementation of view.onclicklistener
    @Override
    public void onClick(View v) {}
    public String getZip(){
        return zipCode;
    }
}