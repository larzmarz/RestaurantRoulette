package com.example.restaurantroulette;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.restaurantroulette.fragment.HistoryFragment;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;
import java.net.URL;
import androidx.fragment.app.Fragment;


public class RestaurantActivity extends AppCompatActivity{
    private ImageButton ibYes;
    private ImageButton ibNo;
    private ImageView ivRestaurantPicture;
    private TextView tvRestaurantDetails;
    private TextView tvPhonenumber;
    private TextView tvUrl;
    public File ptFile;

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
            .placeholder(R.drawable.tempbackgroundsearch)
            .into(ivRestaurantPicture);
        tvRestaurantDetails.setText(restaurantName);
        tvUrl.setText(url);
        ibYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                saveRestaurant(url, ParseUser.getCurrentUser(), imageUrl, restaurantName);
                goMainFragment();
            }
        });
        ibNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goMainFragment();
            }
        });
    }
    private void goMainFragment(){
        Intent intent = new Intent(RestaurantActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void saveRestaurant(String description, ParseUser currentUser, String image, String name) {
        Restaurant restaurant = new Restaurant();
        restaurant.setDescription(description);
        restaurant.setImage(image);
        restaurant.setUser(currentUser);
        restaurant.setName(name);
        restaurant.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null){
                    Log.e("TAG", "Error while saving", e);
                }else {
                    Log.i("TAG", "Post save was successful!");
                }
            }
        });
    }

}
