package com.example.restaurantroulette;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    TextView tvYourthoughts;
    TextView tvYourPreviousThoughts;
    Button btSubmit;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);
        tvName = findViewById(R.id.tvNameDV);
        tvUrl = findViewById(R.id.tvUrlDV);
        ivPhoto = findViewById(R.id.ivPhoto);
        etThoughts = findViewById(R.id.etThoughts);
        btSubmit = findViewById(R.id.btSubmit);
        tvYourthoughts = findViewById(R.id.tvYourThoughts);
        tvYourPreviousThoughts = findViewById(R.id.tvYourPostedThoughts);
        restaurant = getIntent().getParcelableExtra(RESTAURANT_KEY);
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restaurant.setThoughts(etThoughts.getText().toString());
                restaurant.saveInBackground();
                Intent intent = new Intent(RestaurantDetailsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        if(restaurant.getKeyThoughts() != null){
            tvYourPreviousThoughts.setText(restaurant.getKeyThoughts());
        }else{
            tvYourPreviousThoughts.setText("You have no postings! Enter your thoughts above and submit");
        }
        tvName.setText(restaurant.getName());
        tvUrl.setText(restaurant.getDescription());
        Glide.with((Context) this)
                .load(restaurant.getImage())
                .placeholder(R.drawable.tempbackgroundsearch)
                .into(ivPhoto);
    }
}