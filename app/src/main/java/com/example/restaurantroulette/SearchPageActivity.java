package com.example.restaurantroulette;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestHeaders;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.restaurantroulette.fragment.HomeFragment;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import okhttp3.Headers;

public class SearchPageActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Button btRandomize;
    private Button btSurpriseMe;
    private TextView tvAliases;
    ProgressBar progressBar;
    TextInputLayout til_price;
    TextInputLayout til_rating;
    AutoCompleteTextView act_price;
    AutoCompleteTextView act_rating;
    ArrayList<String> name;
    ArrayList<Double> rating;
    ArrayList<String> price;
    ArrayList<String> categoriesAlias;
    ArrayList<String> imageUrl;
    ArrayList<String> phone;
    ArrayList<String> url;
    ArrayList<String> filteredRating;
    ArrayList<String> filteredPrice;
    ArrayList<Integer> restaurantIndices;
    ArrayList<Integer> aliasList;
    String[] aliasesArray;
    String[] arrayOfSelectedAliases;
    String priceSelected;
    String ratingSelected;
    Double ratingSelectedFiltered;
    StringBuilder stringBuilder = new StringBuilder("");
    int officialHolder = 0;
    int finalIndexOfRestaurant;
    boolean[] selectedAlias;
    public static final String TAG = "Search Page Activity";

    public static final String BUSINESS_INFO = "https://api.yelp.com/v3/businesses/search";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        Intent intent = getIntent();
        String zipcode = intent.getStringExtra("zip");
        String mileRadius = intent.getStringExtra("radius");
        tvAliases = findViewById(R.id.tvFoodAliases);
        progressBar = findViewById(R.id.pbLoading);
        til_price = (TextInputLayout)findViewById(R.id.til_price);
        act_price = (AutoCompleteTextView) findViewById(R.id.act_price);
        til_rating = (TextInputLayout)findViewById(R.id.til_rating);
        act_rating = (AutoCompleteTextView) findViewById(R.id.act_rating);
        btSurpriseMe = findViewById(R.id.btSurpriseMe);
        btSurpriseMe.setOnClickListener(new View.OnClickListener() {
            // here is where the restaurant will be completely randomized
            @Override
            public void onClick(View v) {
                goSurpriseRestaurantPage();
            }
        });
        btRandomize = findViewById(R.id.btRandomize);
        name = new ArrayList<String>();
        price = new ArrayList<String>();
        rating = new ArrayList<Double>();
        categoriesAlias = new ArrayList<String>();
        imageUrl = new ArrayList<String>();
        url = new ArrayList<String>();
        filteredRating = new ArrayList<String>();
        filteredPrice = new ArrayList<String>();
        restaurantIndices = new ArrayList<Integer>();
        aliasList = new ArrayList<>();

        // calling Yelp API commands
        AsyncHttpClient client = new AsyncHttpClient();
        RequestHeaders headers = new RequestHeaders();
        headers.put("Authorization", getString(R.string.yelp_key));
        int zip_int = Integer.parseInt(zipcode);
        int radius_int = Integer.parseInt(mileRadius);
        if (radius_int == 0) {
            radius_int = 1609 * 20;
            //the max radius is 25 miles
//        }else if (radius_int > 25){
//            radius_int = 1609 * 25;
//        }
        }else if (radius_int > 0 && radius_int <= 25){
            radius_int *= 1609;
        }
        String location = "?location=" + zip_int;
        String radius = "&radius=" + radius_int;
        client.get(BUSINESS_INFO + location + radius, headers, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json){
                try {
                    // for testing reasons, gets first restaurant
                    JSONArray businesses = json.jsonObject.getJSONArray("businesses");
                    for(int i = 0; i < businesses.length(); i++){
                        //adding all the values of the restaurants given to the app through json into their respective arrays
                        JSONObject objectBusiness = businesses.getJSONObject(i);
                        try {
                            AddToArrayOfPrice(objectBusiness.getString("price"));
                        }
                        catch (Exception e){
                            AddToArrayOfPrice("unknown");
                        }
                        AddToArrayOfName(objectBusiness.getString("name"));
                        AddToArrayOfRating(objectBusiness.getDouble("rating"));
                        AddToArrayOfImageUrl(objectBusiness.getString("image_url"));
                        AddToArrayOfUrl(objectBusiness.getString("url"));
                        AddToCategoryAliasArray(objectBusiness.getJSONArray("categories").getJSONObject(0).getString("alias"));
                        if (i == businesses.length() - 1){
                            aliasesArray = new String[categoriesAlias.size()];
                            for(int j = 0; j < categoriesAlias.size() - 1 ; j++){
                                if (aliasesArray[j] == null){
                                    aliasesArray[j] = categoriesAlias.get(j);
                                    selectedAlias = new boolean[aliasesArray.length];
                                }
                            }
                        }
                    }
                    // price String array set up
                }catch (JSONException e) {
                    e.printStackTrace();
                }
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.i("SearchPage", "error while parsing json data");
            }
        });
        // setting the spinners, testing variables, not real options
        ArrayAdapter<String> arrayAdapter_price = new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,filteredPrice);
        ArrayAdapter<String> arrayAdapter_rating = new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,filteredRating);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, categoriesAlias);
        ArrayAdapter<String> arrayadapter_alias = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, categoriesAlias);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        act_price.setAdapter(arrayAdapter_price);
        act_rating.setAdapter(arrayAdapter_rating);

        act_price.setThreshold(1);
        act_price.setThreshold(1);

        act_price.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                priceSelected = parent.getItemAtPosition(position).toString();
            }
        });
        act_rating.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ratingSelected = parent.getItemAtPosition(position).toString();
                switch (ratingSelected) {
                    case "1+":
                        ratingSelectedFiltered = 1.0;
                        break;
                    case "2+":
                        ratingSelectedFiltered = 2.0;
                        break;
                    case "3+":
                        ratingSelectedFiltered = 3.0;
                        break;
                    case "4+":
                        ratingSelectedFiltered = 4.0;
                        break;
                    case "5":
                        ratingSelectedFiltered = 5.0;
                        break;
                }
            }
        });
        tvAliases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SearchPageActivity.this);
                builder.setTitle("Select FoodTypes");
                builder.setCancelable(false);
                builder.setMultiChoiceItems(categoriesAlias.toArray(aliasesArray), selectedAlias, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if(isChecked){
                            aliasList.add(which);
                            Collections.sort(aliasList);
                        }else{
                            aliasList.remove(Integer.valueOf(which));
                        }
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            for (int j = 0; j < aliasList.size(); j++) {
                                stringBuilder.append(aliasesArray[aliasList.get(j)]);
                                if (j != aliasList.size()) {
                                    stringBuilder.append(",");
                                }
                            }
                        tvAliases.setText(stringBuilder.toString());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedAlias.length; j++) {
                            // remove all selection
                            selectedAlias[j] = false;
                            // clear language list
                            aliasList.clear();
                            // clear text view value
                            tvAliases.setText("");
                        }
                    }
                });
                builder.show();
            }
        });
        btRandomize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {sortingThruSelected();}
        });
    }
    private void sortingThruSelected(){
        aliasSelectedLineScanner();
        int rHolder;
        int pHolder;
        int cnt = 0;
        //goes through price arraylist
        for (int p = 0; p < price.size(); p++){
            //checking if the length of the price at p is the same as the user indicated or less
            if (price.get(p).equals(priceSelected) || ((price.get(p)).length() <= priceSelected.length())) {
                //if it is, assign that index a value of p
                pHolder = p;
                //go into the rating arraylist
                for (int r = 0; r < rating.size(); r++) {
                    //if the rating in the list is higher or equal to what the user asked for
                    if (rating.get(r) >= ratingSelectedFiltered){
                        if (r == pHolder) {
                            officialHolder = r;
                            restaurantIndices.add(officialHolder);
                            cnt++;
                        }
                    }
                }
            }
        }


        if (cnt > 0){
            Random rand = new Random();
            finalIndexOfRestaurant = rand.nextInt(restaurantIndices.size());
            goRestaurantPage();
        }else {
            Toast.makeText(this, "No matches available, try again!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SearchPageActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }


    // storing the different strings to then compare their values
    private void AddToCategoryAliasArray(String al){
        if (!categoriesAlias.contains(al)) {
            categoriesAlias.add(al);
        }
    }
    private void AddToArrayOfImageUrl(String url){imageUrl.add(url);}
    private void AddToArrayOfPrice(String p){
        price.add(p);
        if(!p.equals("unknown")){
            filteredPriceMethod(p);
        }
    }
    private void AddToArrayOfRating(Double r){rating.add(r); filteredRatingMethod(r);}
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
    // adds alias if it's not a repeat
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
        Intent intent = new Intent(SearchPageActivity.this, RestaurantActivity.class);
        intent.putExtra("image_url", imageUrl.get(finalIndexOfRestaurant));
        intent.putExtra("restaurant_name", name.get(finalIndexOfRestaurant));
        intent.putExtra("price", price.get(finalIndexOfRestaurant));
        intent.putExtra("url", url.get(finalIndexOfRestaurant));
        startActivity(intent);
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
        startActivity(intent);
        finish();
    }
    private void aliasSelectedLineScanner(){
        Reader reader = new StringReader(stringBuilder.toString());
        Scanner scanner = new Scanner(reader).useDelimiter(",");
        List<String> tmpList = new LinkedList<>();
        scanner.forEachRemaining(tmpList::add);
        arrayOfSelectedAliases = tmpList.toArray(new String[tmpList.size()]);

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}
    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}