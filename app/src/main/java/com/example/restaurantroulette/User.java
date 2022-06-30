package com.example.restaurantroulette;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("_User")
public class User extends ParseUser {
    public static final String KEY_PROFILE_PHOTO = "profilePhoto";
    public static final String KEY_DESCRIPTION = "Description";

    public ParseFile getProfilePhoto(){
        return getParseFile(KEY_PROFILE_PHOTO);
    }
    public void setProfilePhoto(ParseFile parseFile){
        put(KEY_PROFILE_PHOTO, parseFile);
    }

    public ParseObject getDescription(){return getParseObject(KEY_DESCRIPTION);}
    public void setKeyDescription(ParseObject parseObject){put(KEY_DESCRIPTION, parseObject);}
}