package com.example.restaurantroulette;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class RestaurantDetailsActivity extends AppCompatActivity {
    public static final String RESTAURANT_KEY = "Rest";
    private TextView tvName;
    private TextView tvUrl;
    private EditText etThoughts;
    private ImageView ivPhoto;

    private Restaurant restaurant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);
        tvName = findViewById(R.id.tvNameDV);
        tvUrl = findViewById(R.id.tvUrlDV);
        ivPhoto = findViewById(R.id.ivPhoto);
        etThoughts = findViewById(R.id.etThoughts);

        restaurant = getIntent().getParcelableExtra(RESTAURANT_KEY);
        tvName.setText(restaurant.getName());
        tvUrl.setText(restaurant.getDescription());
        Glide.with((Context) this)
                .load(restaurant.getImage())
                .placeholder(R.drawable.tempbackgroundsearch)
                .into(ivPhoto);
    }
}