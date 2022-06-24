package com.example.restaurantroulette;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class RestaurantActivity extends AppCompatActivity {
    private ImageButton ibYes;
    private ImageButton ibNo;
    private ImageView ivRestaurantPicture;
    private TextView tvRestaurantDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        ibYes = findViewById(R.id.ibYes);
        ibNo = findViewById(R.id.ibNo);
        ivRestaurantPicture = findViewById(R.id.ivRestaurantPicture);
        tvRestaurantDetails = findViewById(R.id.tvRestaurantDetails);
    }
}