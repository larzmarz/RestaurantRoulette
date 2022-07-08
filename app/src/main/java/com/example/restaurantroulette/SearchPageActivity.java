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
    String[] priceString;
    String[] ratingString;
    JSONObject businessName;

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
        btSurpriseMe = findViewById(R.id.btSurpriseMe);
        btSurpriseMe.setOnClickListener(new View.OnClickListener() {
            //here is where the restaurant will be completely randomized
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
                    JSONArray businesses = json.jsonObject.getJSONArray("businesses");
                    for(int i = 0; i < businesses.length(); i++){
                        //adding all the values of the restaurants given to the app through json into their respective arrays
                        JSONObject objectBusiness = businesses.getJSONObject(i);
                        AddToArrayOfName(objectBusiness.getString("name"));
                        AddToArrayOfRating(objectBusiness.getDouble("rating"));
                        AddToArrayOfPrice(objectBusiness.getString("price"));
                        AddToArrayOfImageUrl(objectBusiness.getString("image_url"));
                        AddToArrayOfUrl(objectBusiness.getString("url"));
//                      AddToArrayOfPhone(objectBusiness.getString("phone"));
                        //go into the categories to get the correct aliases
                        //TODO: see why it interferes with the program above and stops the for loop
                        //AddToCategoryAliasArray(objectBusiness.getJSONObject("categories").getString("alias"));
                    }
                    //price String array set up
                    if(price.contains("$") && price.contains("$$") && price.contains("$$$") && price.contains("$$$$")){
                        priceString = new String[]{"$", "$$", "$$$", "$$$$"};
                    }else if(!price.contains("$") && price.contains("$$") && price.contains("$$$") && price.contains("$$$$")){
                        priceString = new String[]{"$$", "$$$", "$$$$"};
                    }else if(!price.contains("$") && !price.contains("$$") && price.contains("$$$") && price.contains("$$$$")){
                        priceString = new String[]{"$$$", "$$$$"};
                    }else if(!price.contains("$") && !price.contains("$$") && !price.contains("$$$") && price.contains("$$$$")){
                        priceString = new String[]{"$$$$"};
                    }else if(price.contains("$") && !price.contains("$$") && price.contains("$$$") && !price.contains("$$$$")){
                        priceString = new String[]{"$", "$$$"};
                    }else if(price.contains("$") && price.contains("$$") && !price.contains("$$$") && !price.contains("$$$$")){
                        priceString = new String[]{"$", "$$"};
                    }else if(price.contains("$") && price.contains("$$") && price.contains("$$$") && !price.contains("$$$$")){
                        priceString = new String[]{"$", "$$", "$$$"};
                    }else if(!price.contains("$") && price.contains("$$") && !price.contains("$$$") && !price.contains("$$$$")){
                        priceString = new String[]{"$$"};
                    }else if(!price.contains("$") && price.contains("$$") && price.contains("$$$") && !price.contains("$$$$")){
                        priceString = new String[]{"$$", "$$$"};
                    }else if(!price.contains("$") && !price.contains("$$") && price.contains("$$$") && !price.contains("$$$$")){
                        priceString = new String[]{"$$$"};
                    }else if(price.contains("$") && !price.contains("$$") && price.contains("$$$") && price.contains("$$$$")){
                        priceString = new String[]{"$", "$$$", "$$$$"};
                    }else if(price.contains("$") && !price.contains("$$") && !price.contains("$$$") && price.contains("$$$$")){
                        priceString = new String[]{"$", "$$$$"};
                    }else if(price.contains("$") && price.contains("$$") && !price.contains("$$$") && price.contains("$$$$")){
                        priceString = new String[]{"$", "$$", "$$$$"};
                    }else if(!price.contains("$") && price.contains("$$") && !price.contains("$$$") && price.contains("$$$$")){
                        priceString = new String[]{"$$", "$$$$"};
                    }else if(price.contains("$") && !price.contains("$$") && !price.contains("$$$") && !price.contains("$$$$")){
                        priceString = new String[]{"$"};
                    }

                    //rating string array set up
                    if(rating.contains("1")){
                        ratingString = new String[]{"1"};
                    }else if(rating.contains("2")){
                        ratingString = new String[]{"2"};
                    }else if(rating.contains("3")){
                        ratingString = new String[]{"3"};
                    }else if(rating.contains("4")){
                        ratingString = new String[]{"4"};
                    }else if(rating.contains("5")){
                        ratingString = new String[]{"5"};
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
        //setting the spinners, testing variables, not real options
        String[] mileRadius = getResources().getStringArray(R.array.mile_radius);
        String[] rating = getResources().getStringArray(R.array.rating);
        String[] priceRange = getResources().getStringArray(R.array.price_range);

        //TODO: make the string array priceString & ratingString successfully link below
        //priceString crashes the app
        ArrayAdapter adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, priceRange);
        //ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, mileRadius);
        ArrayAdapter adapter3 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, rating);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPriceRange.setAdapter(adapter1);
        spRating.setAdapter(adapter3);
    }


    //storing the different strings to then compare their values
    private void AddToCategoryAliasArray(String al){
        if(!categoriesAlias.contains(al)) {
            categoriesAlias.add(al);
        }
    }
    private void AddToArrayOfImageUrl(String url){imageUrl.add(url);}
    private void AddToArrayOfPrice(String p){price.add(p);}
    private void AddToArrayOfRating(Double r){rating.add(r);}
    //adds alias if it's not a repeat
    private void AddToArrayOfName(String a){
        if(!name.contains(a)) {
            name.add(a);
        }
    }
    private void AddToArrayOfUrl(String u){
        url.add(u);
    }
//    private void AddToArrayOfPhone(String p){
//        phone.add(p);
//    }
    //TODO: sort all the values to then throw the options back at the spinners
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