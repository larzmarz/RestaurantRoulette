package com.example.restaurantroulette;

import static com.facebook.stetho.inspector.network.PrettyPrinterDisplayType.JSON;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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
import org.json.JSONException;
import org.json.JSONObject;
import okhttp3.Headers;
public class SearchPageActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spTypeFood;
    private Spinner spPriceRange;
    private Spinner spLocation;
    private Button btRandomize;

    //redirect to the specific api request I need
    public static final String BUSINESS_INFO = "https://api.yelp.com/v3/businesses/search";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        spTypeFood = findViewById(R.id.spTypeFood);
        spPriceRange = findViewById(R.id.spPriceRange);
        spLocation = findViewById(R.id.spMileRadius);
        btRandomize = findViewById(R.id.btRandomize);
        btRandomize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SearchPageActivity.this, "Random Button Works", Toast.LENGTH_SHORT).show();
            }
        });
        //setting the spinners
        String[] typeFood = getResources().getStringArray(R.array.food_items);
        String[] priceRange = getResources().getStringArray(R.array.price_range);
        String[] mileRadius = getResources().getStringArray(R.array.mile_radius);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, typeFood);
        ArrayAdapter adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, priceRange);
        ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, mileRadius);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTypeFood.setAdapter(adapter);
        spPriceRange.setAdapter(adapter1);
        spLocation.setAdapter(adapter2);

        //calling Yelp API commands
        AsyncHttpClient client = new AsyncHttpClient();
        RequestHeaders headers = new RequestHeaders();
        //TODO: Hide API Key
        headers.put("Authorization", "Bearer 9r3kos1OAvsDBYAPSPskzt-Yu6qIbcaVsBIAS_BznnDOEoTesIHTU_hoojwl4yih23D0K0RNfdnALn24KdyquMsFiuv12mWiI2ag7zVVRhBMRdmQBWWeNzoKrjayYnYx");
        //whatever follows the "?location=" is the user's location
        //debugger shows what happens in action and the data collected by the API
        client.get(BUSINESS_INFO + "?location=33176", headers, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json){
                try {
                    //for testing reasons, gets first restaurant
                    JSONObject business = json.jsonObject.getJSONArray("businesses").getJSONObject(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {}
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}