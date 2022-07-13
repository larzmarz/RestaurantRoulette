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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.restaurantroulette.R;
import com.example.restaurantroulette.Restaurant;
import com.example.restaurantroulette.Yelp;

import org.parceler.Parcels;

import java.util.List;

public class YelpAdapter extends RecyclerView.Adapter<YelpAdapter.ViewHolder> {
    Context context;
    List<Restaurant> restaurants;
    private SwipeRefreshLayout swipeContainer;

    public YelpAdapter(Context context, List<Restaurant> restaurants) {
        this.context = context;
        this.restaurants = restaurants;
    }
    public void clear(){
        restaurants.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("YelpAdapter", "onCreateViewHolder");
        View restaurantView = LayoutInflater.from(context).inflate(R.layout.item_restaurant, parent, false);
        return new ViewHolder(restaurantView);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("YelpAdapter", "onBindViewHolder" + position);
        //Get the restaurant at the position
        Restaurant res = restaurants.get(position);
        //Bind restaurant data into the VH
        holder.bind(res);
    }
    @Override
    public int getItemCount() {
        return restaurants.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvName;
        TextView tvYelpUrl;
        TextView tvDate;
        ImageView ivRestPicsURL;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvRestName);
            tvYelpUrl = itemView.findViewById(R.id.tvYelpUrl);
            tvDate = itemView.findViewById(R.id.tvDate);
            ivRestPicsURL = itemView.findViewById(R.id.ivRestPic);
            itemView.setOnClickListener(this);
        }
        public void bind(Restaurant restaurant) {
            tvName.setText(restaurant.getName());
            tvYelpUrl.setText(restaurant.getDescription());
            tvDate.setText(restaurant.getCreated());
            Glide.with(context)
                .load(restaurant.getImage())
                .placeholder(R.drawable.orange_splash)
                .apply(new RequestOptions().override(500, 500))
                .into(ivRestPicsURL);
            //this will be for the detail view of the restaurant
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "In progress", Toast.LENGTH_SHORT).show();
                }
            });
        }
        //required empty Onclick for implementation of View.OnClickListener
        @Override
        public void onClick(View v) {
        }
    }
}
