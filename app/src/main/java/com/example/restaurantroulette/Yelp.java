package com.example.restaurantroulette;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

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

    //empty for Parcel
    public Yelp(){
    }
    public Yelp(JSONObject jsonObject) throws JSONException{
        locationZip = jsonObject.getString("location.zip_code");
        locationAddress1 = jsonObject.getString("location.address1");
        locationAddress2 = jsonObject.getString("location.address2");
        locationAddress3 = jsonObject.getString("location.address3");

        name = jsonObject.getString("name");
        price = jsonObject.getString("price");
        rating = jsonObject.getDouble("rating");
        alias = jsonObject.getString("alias");
    }
    public String getLocationZip() {return locationZip;}
    public String getLocationAddress1() {return locationAddress1;}
    public String getLocationAddress2() {return locationAddress2;}
    public String getLocationAddress3() {return locationAddress3;}
    public String getName() {return name;}
    public String getPrice() {return price;}
    public Double getRating() {return rating;}
    public String getAlias() {return alias;}
}
