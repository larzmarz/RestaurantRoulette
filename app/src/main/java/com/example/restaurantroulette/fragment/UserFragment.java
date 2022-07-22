package com.example.restaurantroulette.fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.restaurantroulette.LoginActivity;
import com.example.restaurantroulette.ProfileAdapter;
import com.example.restaurantroulette.R;
import com.example.restaurantroulette.Restaurant;
import com.example.restaurantroulette.User;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class UserFragment extends BaseFragment implements View.OnClickListener{
    private Button btLogout;
    TextView tvUsername;
    ImageView ivProfilePhoto;
    TextView tvDescription;
    protected SwipeRefreshLayout swipeContainer;
    public User userToFilterBy;
    protected ProfileAdapter adapter;
    protected List<Restaurant> allRestaurants;
    protected RecyclerView rvProfile;
    public User user = (User) ParseUser.getCurrentUser();
    public UserFragment(ParseUser userToFilterBy) {
        this.userToFilterBy = (User) userToFilterBy;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_user, parent, false);
    }
    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        rvProfile = (RecyclerView) view.findViewById(R.id.rvProfile);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvProfile.setLayoutManager(layoutManager);

        rvProfile.setAdapter(adapter);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainerFeed);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                queryRestaurants();
                adapter.notifyDataSetChanged();
                swipeContainer.setRefreshing(false);
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_light);
        allRestaurants = new ArrayList<>();
        adapter = new ProfileAdapter(getContext(), allRestaurants);
        rvProfile.setAdapter(adapter);
        rvProfile.setLayoutManager(new GridLayoutManager(getContext(), 3));
        userToFilterBy.fetchInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                userToFilterBy = (User) object;
                displayUserInfo();
            }
        });
        queryRestaurants();

        // Setup any handles to view objects here
        tvUsername = view.findViewById(R.id.tvUsername);
        tvDescription = view.findViewById(R.id.tvDescription);
        ivProfilePhoto = view.findViewById(R.id.ivProfilePhoto);
        ivProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //take photo
                launchCamera();
            }
        });
        user.fetchInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                user = (User) object;
                displayUserInfo();
            }
        });
        //TODO: fix this, previous code crashed app
//        btLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ParseUser currentUser =ParseUser.getCurrentUser();
//                currentUser.logOut();
//                Intent i = new Intent(getContext(), LoginActivity.class);
//                startActivity(i);
//            }
//        });
    }
    protected void queryRestaurants() {
        ParseQuery<Restaurant> restaurantParseQuery = ParseQuery.getQuery(Restaurant.class);
        restaurantParseQuery.include(Restaurant.KEY_USER);
        restaurantParseQuery.whereEqualTo(Restaurant.KEY_USER, userToFilterBy);
        restaurantParseQuery.setLimit(20);
        restaurantParseQuery.addDescendingOrder(Restaurant.KEY_CREATED_KEY);
        restaurantParseQuery.findInBackground(new FindCallback<Restaurant>() {
            @Override
            public void done(List<Restaurant> objects, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting restaurants", e);
                    return;
                }
                for (Restaurant restaurant : objects) {
                    Log.i(TAG, "Post: " + restaurant.getDescription() + ", username: " +
                            restaurant.getUser().getUsername());
                }
                adapter.clear();
                allRestaurants.addAll(objects);
                adapter.notifyDataSetChanged();
            }
        });
    }
    public void displayUserInfo(){
        tvUsername.setText(user.getUsername());
        // TODO: make sure this is the right setter
        tvDescription.setText((CharSequence) user.getDescription());
        ParseFile profilePhoto = user.getProfilePhoto();
        if (profilePhoto != null){
            Glide.with(getContext())
                .load(user.getProfilePhoto().getUrl())
                .circleCrop().
                into(ivProfilePhoto);
        }else {
            Toast.makeText(getContext(), "Profile Photo does not exist for " + user.getUsername(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                Glide.with(getContext())
                    .load(takenImage)
                    .circleCrop()
                    .into(ivProfilePhoto);
                user.setProfilePhoto(new ParseFile(photoFile));
                user.saveInBackground();
            } else { // Result was a failure
                Toast.makeText(getContext(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onClick(View v) {}
}