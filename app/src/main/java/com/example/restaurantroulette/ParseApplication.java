package com.example.restaurantroulette;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(User.class);
        ParseObject.registerSubclass(Restaurant.class);
        // connecting backend data collection app
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("JuEszvWCLncywS7ocP59OXIwZH0qq5OHBwaKLVJf")
                .clientKey("e9eUIyxdCZkKHsPbb2XMeH2Pth4rL6OomfzYDyfM")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
