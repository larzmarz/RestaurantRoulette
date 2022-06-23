package com.example.restaurantroulette;

import static com.facebook.stetho.inspector.network.PrettyPrinterDisplayType.JSON;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestHeaders;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import org.json.JSONException;
import org.json.JSONObject;
import okhttp3.Headers;
public class SearchPageActivity extends AppCompatActivity {
    //redirect to the specific api request I need
    public static final String BUSINESS_INFO = "https://api.yelp.com/v3/businesses/search";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestHeaders headers = new RequestHeaders();
        //TODO: Hide API Key
        headers.put("Authorization", "Bearer 9r3kos1OAvsDBYAPSPskzt-Yu6qIbcaVsBIAS_BznnDOEoTesIHTU_hoojwl4yih23D0K0RNfdnALn24KdyquMsFiuv12mWiI2ag7zVVRhBMRdmQBWWeNzoKrjayYnYx");
        //whatever follows the "?location=" is the user's location
        //debugger shows what happens in action and the data collected by the API
        client.get(BUSINESS_INFO + "?location=10018", headers, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json){
                try {
                    //for testing reasons, displays first restaurant
                    JSONObject business = json.jsonObject.getJSONArray("businesses").getJSONObject(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {}
        });
    }
}