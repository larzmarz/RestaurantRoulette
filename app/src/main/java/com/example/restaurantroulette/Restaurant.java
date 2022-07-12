package com.example.restaurantroulette;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.parceler.ParcelClass;

import java.util.ArrayList;
import java.util.List;

@ParseClassName("Restaurant")
public class Restaurant extends ParseObject{
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_NAME = "name";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_KEY = "createdAt";


    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }
    public void setUser(ParseUser user){
        put(KEY_USER, user);
    }
    public String getName(){return getString(KEY_NAME);}
    public void setName(String name){put(KEY_NAME, name);}
    public String getDescription(){
        return getString(KEY_DESCRIPTION);
    }
    public void setDescription(String description){
        put(KEY_DESCRIPTION, description);
    }
    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }
    public void setImage(String imageUrl) {
        put(KEY_IMAGE, imageUrl);
    }
    public String getCreated(){
        return getString(KEY_CREATED_KEY);
    }
    public void setCreated(ParseFile date){
        put(KEY_CREATED_KEY, date);
    }



}
