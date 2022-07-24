package com.example.restaurantroulette;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import org.parceler.Parcels;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {
    private Context context;
    private List<Restaurant> rests;
    public ProfileAdapter (Context context, List<Restaurant> rests){
        this.context = context;
        this.rests = rests;
    }

    @NonNull
    @Override
    public ProfileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user_restaurant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileAdapter.ViewHolder holder, int position) {
        Restaurant restaurant = rests.get(position);
        holder.bind(restaurant);
    }
    @Override
    public int getItemCount() {
        return rests.size();
    }
    public void clear() {
        rests.clear();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView ivUserPost;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivUserPost = itemView.findViewById(R.id.ivUserPost);
        }
        public void bind(Restaurant rest){
            String image = rest.getImage();
            if (image != null){
                Glide.with(context)
                    .load(image).
                    into(ivUserPost);
            }
        }
        @Override
        public void onClick(View v){
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION){
                Log.i("ProfileAdapter", "clicked on restaurant");
                Restaurant restaurant = rests.get(position);
                Intent i = new Intent(context, RestaurantDetailsActivity.class);
                i.putExtra(Restaurant.class.getSimpleName(), Parcels.wrap(restaurant));
                context.startActivity(i);
            }
        }
    }
}
