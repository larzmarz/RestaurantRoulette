package com.example.restaurantroulette.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.restaurantroulette.Adapters.YelpAdapter;
import com.example.restaurantroulette.R;
import com.example.restaurantroulette.Restaurant;
import com.example.restaurantroulette.User;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {
    private RecyclerView rvPlaces;
    private SwipeRefreshLayout swipeContainer;
    protected YelpAdapter adapter;
    protected List<Restaurant> allRestaurants;
    public static final String TAG = "History Fragment";
    public User user = (User) ParseUser.getCurrentUser();
    // required empty constructor
    public HistoryFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstances) {
        super.onViewCreated(view, savedInstances);
        rvPlaces = view.findViewById(R.id.rvPlaces);
        swipeContainer = view.findViewById(R.id.swipeContainerFeed);
        allRestaurants = new ArrayList<>();
        adapter = new YelpAdapter(getContext(), allRestaurants);
        rvPlaces.setAdapter(adapter);
        rvPlaces.setLayoutManager(new LinearLayoutManager(getContext()));
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            // refresh implementation
            public void onRefresh() {
                adapter.clear();
                queryRestaurants();
            }
        });
        queryRestaurants();
    }
    private void queryRestaurants() {
        ParseQuery<Restaurant> query = ParseQuery.getQuery(Restaurant.class);
            query.include(Restaurant.KEY_USER);
            query.addDescendingOrder(Restaurant.KEY_CREATED_KEY);
            query.findInBackground(new FindCallback<Restaurant>() {
                @Override
                public void done(List<Restaurant> rests, ParseException e) {
                    if (e != null){
                        Log.i(TAG, "Issues with getting restaurants", e);
                        return;
                    }
                    for (Restaurant rest : rests){
                        Log.i(TAG, "Restaurant: " + rest.getName() + ",username: " + rest.getUser().getUsername());
                    }
                    allRestaurants.addAll(rests);
                    adapter.notifyDataSetChanged();
                    swipeContainer.setRefreshing(false);
                }
            });
        }
    }