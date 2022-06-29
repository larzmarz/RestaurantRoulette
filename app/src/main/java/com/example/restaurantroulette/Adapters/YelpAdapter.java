package com.example.restaurantroulette.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.restaurantroulette.R;
import com.example.restaurantroulette.Yelp;

import org.parceler.Parcels;

import java.util.List;

//public class YelpAdapter extends RecyclerView.Adapter<YelpAdapter.ViewHolder> {
//    Context context;
//    List<Yelp> restaurants;
//
//    public YelpAdapter(Context context, List<Yelp> resaturants) {}
//
//        @NonNull
//        @Override
//        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            Log.d("YelpAdapter", "onCreateViewHolder");
//            View restaurantView = LayoutInflater.from(context).inflate(R.layout.activity_restaurant, parent, false);
//            return new ViewHolder(restaurantView);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//            Log.d("YelpAdapter", "onBindViewHolder" + position);
//            //Get the movie at the position
//            Yelp yelp = restaurants.get(position);
//            //Bind movie data into the VH
//            holder.bind(restaurants);
//        }
//
//        @Override
//        public int getItemCount() {
//            return 1;
//        }
//
//        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
//            TextView tvName;
//            TextView tvInfo;
//            ImageView ivRestPics;
//
//            public ViewHolder(@NonNull View itemView) {
//                super(itemView);
//                tvName = itemView.findViewById(R.id.ivRestaurantPicture);
//                tvInfo = itemView.findViewById(R.id.tvRestaurantDetails);
//                ivRestPics = itemView.findViewById(R.id.ivRestaurantPicture);
//
//                itemView.setOnClickListener(this);
//            }
//
//            public void bind(List<Yelp> restaurant) {
//                //TODO: make i a random int that runs through the restaurant arrays
//                int i = 0;
//                tvName.setText(restaurant.get(i).getName());
//                tvInfo.setText(restaurant.get(i).locationAddress1);
//                //TODO: find out the type that the restaurant picture is
//                //ivRestPics.setImageResource(restaurant.get().);
//            }
//            @Override
//            public void onClick(View v) {
//
//            }
//        }
//    }
