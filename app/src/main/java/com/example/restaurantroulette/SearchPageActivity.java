package com.example.restaurantroulette;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import com.bumptech.glide.Glide;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestHeaders;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.restaurantroulette.fragment.HomeFragment;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import okhttp3.Headers;
public class SearchPageActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spTypeFood;
    private Spinner spPriceRange;
    private Spinner spLocation;
    private Spinner spRating;
    private Button btRandomize;
    private Button btSurpriseMe;
    ArrayList<String> name;
    ArrayList<Double> rating;
    ArrayList<String> price;
    ArrayList<String> categoriesAlias;
    ArrayList<String> imageUrl;
    ArrayList<String> phone;
    ArrayList<String> url;
    ArrayList<String> filteredRating;
    ArrayList<String> filteredPrice;

    // redirect to the specific api request I need
    public static final String BUSINESS_INFO = "https://api.yelp.com/v3/businesses/search";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        Intent intent = getIntent();
        String zipcode = intent.getStringExtra("zip");
        String mileRadius = intent.getStringExtra("radius");
        spPriceRange = findViewById(R.id.spPriceRange);
        spRating = findViewById(R.id.spRating);
        btSurpriseMe = findViewById(R.id.btSurpriseMe);
        btSurpriseMe.setOnClickListener(new View.OnClickListener() {
            // here is where the restaurant will be completely randomized
            @Override
            public void onClick(View v) {
                goSurpriseRestaurantPage();
            }
        });
        btRandomize = findViewById(R.id.btRandomize);
        btRandomize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goRestaurantPage();
            }
        });

        name = new ArrayList<String>();
        price = new ArrayList<String>();
        rating = new ArrayList<Double>();
        categoriesAlias = new ArrayList<String>();
        imageUrl = new ArrayList<String>();
        url = new ArrayList<String>();
        filteredRating = new ArrayList<String>();
        filteredPrice = new ArrayList<String>();

        // calling Yelp API commands
        AsyncHttpClient client = new AsyncHttpClient();
        RequestHeaders headers = new RequestHeaders();
        headers.put("Authorization", getString(R.string.yelp_key));
        // whatever follows the "?location=" is the user's location
        // debugger shows what happens in action and the data collected by the API
        // TODO: get zipcode input from the search page
        int zip_int = Integer.parseInt(zipcode);
        int radius_int = Integer.parseInt(mileRadius);
        if(radius_int == 0){
            radius_int = 1609 * 20;
        }else if(radius_int > 0){
            radius_int *= 1609;
        }
        String location = "?location=" + zip_int;
        String radius = "&radius=" + radius_int;
        client.get(BUSINESS_INFO + location + radius, headers, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json){
                Log.i("test", "");
                try {
                    // for testing reasons, gets first restaurant
                    JSONArray businesses = json.jsonObject.getJSONArray("businesses");
                    for(int i = 0; i < businesses.length(); i++){
                        //adding all the values of the restaurants given to the app through json into their respective arrays
                        JSONObject objectBusiness = businesses.getJSONObject(i);
                        AddToArrayOfName(objectBusiness.getString("name"));
                        AddToArrayOfRating(objectBusiness.getDouble("rating"));
                        AddToArrayOfPrice(objectBusiness.getString("price"));
                        AddToArrayOfImageUrl(objectBusiness.getString("image_url"));
                        AddToArrayOfUrl(objectBusiness.getString("url"));
                        //go into the categories to get the correct aliases
                        //TODO: see why it interferes with the program above and stops the for loop
                        //AddToCategoryAliasArray(objectBusiness.getJSONObject("categories").getString("alias"));

                    }
                    // price String array set up
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.i("fail", "");
            }
        });
        // setting the spinners, testing variables, not real options

        // TODO: make the string array priceString & ratingString successfully link below
        // priceString crashes the app
        ArrayAdapter<String> adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, filteredPrice);
        ArrayAdapter<String> adapter3 = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, filteredRating);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPriceRange.setAdapter(adapter1);
        spRating.setAdapter(adapter3);

        // these are the user's responses
        // TODO: see why they are causing the app to crash
//        String priceResponse = spPriceRange.getSelectedItem().toString();
//        String priceRating = spRating.getSelectedItem().toString();

    }

    //storing the different strings to then compare their values
    private void AddToCategoryAliasArray(String al){
        if (!categoriesAlias.contains(al)) {
            categoriesAlias.add(al);
        }
    }
    private void AddToArrayOfImageUrl(String url){imageUrl.add(url);}
    private void AddToArrayOfPrice(String p){price.add(p); filteredPriceMethod(p);}
    private void AddToArrayOfRating(Double r){rating.add(r); filteredRatingMethod(r);}

    //TODO: sort all the values to then throw the options back at the spinners
    private void filteredRatingMethod(Double r) {
        if (r >= 1 && r < 2){
            if (!filteredRating.contains("1+")){
                filteredRating.add("1+");
            }
        }
        if (r >= 2 && r < 3){
            if (!filteredRating.contains("2+")){
                filteredRating.add("2+");
            }
        }
        if (r >= 3 && r < 4){
            if (!filteredRating.contains("3+")){
                filteredRating.add("3+");
            }
        }
        if (r >= 4 && r < 5){
            if (!filteredRating.contains("4+")){
                filteredRating.add("4+");
            }
        }
        if (r == 5){
            if (!filteredRating.contains("5")){
                filteredRating.add("5");
            }
        }
    }
    private void filteredPriceMethod(String p) {
        if (p.equals("$")){
            if (!filteredPrice.contains("$")){
                filteredPrice.add("$");
            }
        }
        if (p.equals("$$")){
            if (!filteredPrice.contains("$$")){
                filteredPrice.add("$$");
            }
        }
        if (p.equals("$$$")){
            if (!filteredPrice.contains("$$$")){
                filteredPrice.add("$$$");
            }
        }
        if (p.equals("$$$$")){
            if (!filteredPrice.contains("$$$$")){
                filteredPrice.add("$$$$");
            }
        }
}


    //adds alias if it's not a repeat
    private void AddToArrayOfName(String a){
        if (!name.contains(a)) {
            name.add(a);
        }
    }
    private void AddToArrayOfUrl(String u){
        url.add(u);
    }
//    private void AddToArrayOfPhone(String p){
//        phone.add(p);
//    }

    private void goRestaurantPage(){
        Intent i = new Intent(SearchPageActivity.this, RestaurantActivity.class);
        startActivity(i);
        finish();
    }
    private void goSurpriseRestaurantPage() {
        Random rand = new Random();
        int randomImageUrlInt = rand.nextInt(imageUrl.size());
        int holder = randomImageUrlInt;
        Intent intent = new Intent(SearchPageActivity.this, RestaurantActivity.class);
        intent.putExtra("image_url", imageUrl.get(holder));
        intent.putExtra("restaurant_name", name.get(holder));
        intent.putExtra("price", price.get(holder));
        //intent.putExtra("phone", phone.get(holder));
        intent.putExtra("url", url.get(holder));
        Log.i("wawawa", imageUrl.get(holder));
        startActivity(intent);
        finish();
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}
    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}