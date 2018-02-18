package com.ahmedosman.tripplanner.home;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmedosman.tripplanner.R;
import com.ahmedosman.tripplanner.sqllite.TripsTable;
import com.ahmedosman.tripplanner.models.Trip;

public class UpcomingTripsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Trip[] myDataset;
    private Activity parentActivity;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TripsTable tripsTable = new TripsTable();
        myDataset = tripsTable.select(Home.UPCOMING,getActivity().getApplicationContext());
        parentActivity = getActivity();
        mRecyclerView = (RecyclerView) parentActivity.findViewById(R.id.list_trips);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(parentActivity, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CardsAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);
    }

    public UpcomingTripsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trips, container, false);
    }

}
