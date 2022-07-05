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

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestHeaders;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.restaurantroulette.fragment.HomeFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Headers;
public class SearchPageActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spTypeFood;
    private Spinner spPriceRange;
    private Spinner spLocation;
    private Spinner spRating;
    private Button btRandomize;
    ArrayList<JSONObject> alias;
    ArrayList<JSONObject> rating;
    ArrayList<JSONObject> price;
    String businessName;

    //redirect to the specific api request I need
    public static final String BUSINESS_INFO = "https://api.yelp.com/v3/businesses/search";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        Intent intent = getIntent();
        String zipcode = intent.getStringExtra("zip");
        spPriceRange = findViewById(R.id.spPriceRange);
        spLocation = findViewById(R.id.spMileRadius);
        spRating = findViewById(R.id.spRating);
        btRandomize = findViewById(R.id.btRandomize);
        btRandomize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goRestaurantPage();
            }
        });
        //setting the spinners, testing variables, not real options
        //String[] typeFood = getResources().getStringArray(R.array.food_items);
        String[] priceRange = getResources().getStringArray(R.array.price_range);
        String[] mileRadius = getResources().getStringArray(R.array.mile_radius);
        String[] rating = getResources().getStringArray(R.array.rating);

        //ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, typeFood);
        ArrayAdapter adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, priceRange);
        //ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, mileRadius);
        ArrayAdapter adapter3 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, rating);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spTypeFood.setAdapter(adapter);
        spPriceRange.setAdapter(adapter1);
        //spLocation.setAdapter(adapter2);
        spRating.setAdapter(adapter3);

        //calling Yelp API commands
        AsyncHttpClient client = new AsyncHttpClient();
        RequestHeaders headers = new RequestHeaders();
        //TODO: Hide API Key
        headers.put("Authorization", "Bearer 9r3kos1OAvsDBYAPSPskzt-Yu6qIbcaVsBIAS_BznnDOEoTesIHTU_hoojwl4yih23D0K0RNfdnALn24KdyquMsFiuv12mWiI2ag7zVVRhBMRdmQBWWeNzoKrjayYnYx");
        //whatever follows the "?location=" is the user's location
        //debugger shows what happens in action and the data collected by the API
        //TODO: get zipcode input from the search page
        String location = "?location=" + zipcode;
        Toast.makeText(this, "zip is" + zipcode, Toast.LENGTH_SHORT).show();
        client.get(BUSINESS_INFO + "?location=33176", headers, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json){
                Log.i("test", "");
                try {
                    //for testing reasons, gets first restaurant
                    JSONObject business = json.jsonObject.getJSONArray("businesses").getJSONObject(0);
                    JSONObject image = json.jsonObject.getJSONArray("image_url").getJSONObject(0);
                    businessName = business.getString("name");
                    Intent intent = new Intent();
                    //these two intents will serve to transport the data of he restaurant to the restaurant page
                    intent.putExtra("restaurant", (Parcelable) business);
                    intent.putExtra("image", (Parcelable) image);
                    for(int i = 0; i < json.jsonObject.getJSONArray("name").length(); i++){
                        //adding all the values of the restaurants given to the app through json into their respective arrays
                        AddToArrayOfAlias(json.jsonObject.getJSONArray("alias").getJSONObject(i));
                        AddToArrayOfRating(json.jsonObject.getJSONArray("rating").getJSONObject(i));
                        AddToArrayOfPrice(json.jsonObject.getJSONArray("price").getJSONObject(i));
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.i("fail", "");
            }
        });
    }
    //storing the different strings to then compare their values
    //TODO: deal with repeats
    private void AddToArrayOfPrice(JSONObject json){price.add(json);}
    private void AddToArrayOfRating(JSONObject json){rating.add(json);}
    private void AddToArrayOfAlias(JSONObject json){alias.add(json);}
    //TODO: sort all the values to then throw the options back at the spinners

    //all the booleans for the prices, if all are true, then the spinner will include all the money signs
    public boolean MoneySign1(JSONObject jsonObject) throws JSONException {
        if(jsonObject.getJSONArray("price").equals("$")){return true;}return false;}
    public boolean MoneySign2(JSONObject jsonObject) throws JSONException{
        if(jsonObject.getJSONArray("price").equals("$$")){return true;}return false;}
    public boolean MoneySign3(JSONObject jsonObject) throws JSONException{
        if(jsonObject.getJSONArray("price").equals("$$$")){return true;}return false;}
    public boolean MoneySign4(JSONObject jsonObject) throws JSONException{
        if(jsonObject.getJSONArray("price").equals("$$$$")){return true;}return false;}

    //booleans for rating
    public boolean RatingSign1(JSONObject jsonObject) throws JSONException {
        if(jsonObject.getJSONArray("rating").equals("1")){return true;}return false;}
    public boolean RatingSign2(JSONObject jsonObject) throws JSONException {
        if(jsonObject.getJSONArray("rating").equals("2")){return true;}return false;}
    public boolean RatingSign3(JSONObject jsonObject) throws JSONException {
        if(jsonObject.getJSONArray("rating").equals("3")){return true;}return false;}
    public boolean RatingSign4(JSONObject jsonObject) throws JSONException {
        if(jsonObject.getJSONArray("rating").equals("4")){return true;}return false;}
    public boolean RatingSign5(JSONObject jsonObject) throws JSONException {
        if(jsonObject.getJSONArray("rating").equals("5")){return true;}return false;}

    //checking through aliases



    private void goRestaurantPage(){
        Intent i = new Intent(SearchPageActivity.this, RestaurantActivity.class);
        startActivity(i);
        finish();
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}
    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}