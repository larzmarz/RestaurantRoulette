package com.example.restaurantroulette;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URL;

public class RestaurantActivity extends AppCompatActivity {
    private ImageButton ibYes;
    private ImageButton ibNo;
    private ImageView ivRestaurantPicture;
    private TextView tvRestaurantDetails;
    private TextView tvPhonenumber;
    private TextView tvUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        ibYes = findViewById(R.id.ibYes);
        ibNo = findViewById(R.id.ibNo);
        ivRestaurantPicture = findViewById(R.id.ivRestaurantPicture);
        tvRestaurantDetails = findViewById(R.id.tvRestaurantDetails);
        tvPhonenumber = findViewById(R.id.tvPhoneNumber);
        tvUrl = findViewById(R.id.tvUrl);
        Intent intent = getIntent();
        String restaurantName = intent.getStringExtra("restaurant_name");
        String rNumber = intent.getStringExtra("phone");
        String imageUrl = intent.getStringExtra("image_url");
        String url = intent.getStringExtra("url");
        Glide.with((Context) this)
                .load(imageUrl)
                .placeholder(R.drawable.orange_splash)
                .into(ivRestaurantPicture);
        tvRestaurantDetails.setText(restaurantName);
        tvPhonenumber.setText(rNumber);
        tvUrl.setText(url);
    }
}