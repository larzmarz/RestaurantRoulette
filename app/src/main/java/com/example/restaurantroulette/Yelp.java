package com.example.restaurantroulette;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

//TODO: scan entire project to make sure deleting this class does not break anything
@Parcel
public class Yelp {
    public String locationZip;
    public String locationAddress1;
    public String locationAddress2;
    public String locationAddress3;
    public String name;
    public String price;
    public Double rating;
    public String alias;
    String posterPath;

    public static Yelp fromJson(JSONObject jsonObject) throws JSONException{
        Yelp yelp = new Yelp();
        yelp.locationZip = jsonObject.getString("location.zip_code");
        yelp.locationAddress1 = jsonObject.getString("location.address1");
        yelp.locationAddress2 = jsonObject.getString("location.address2");
        yelp.locationAddress3 = jsonObject.getString("location.address3");

        yelp.name = jsonObject.getString("name");
        yelp.price = jsonObject.getString("price");
        yelp.rating = jsonObject.getDouble("rating");
        yelp.alias = jsonObject.getString("alias");

        return yelp;
    }

}
