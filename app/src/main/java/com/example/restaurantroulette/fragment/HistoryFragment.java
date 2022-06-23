package com.example.restaurantroulette.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.restaurantroulette.R;

public class HistoryFragment extends Fragment {
    private SwipeRefreshLayout swipeContainer;
    //required empty constructor
    public HistoryFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstances) {
        super.onViewCreated(view, savedInstances);
        //TODO: implement swipe refresh for the page with the restaurants the user has been to
        swipeContainer = view.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            //refresh implementation
            public void onRefresh() {}
        });
    }
}