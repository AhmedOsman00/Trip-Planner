package com.ahmedosman.tripplanner;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PastTripsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Trip[] myDataset = {new Trip()};
    private Activity parentActivity;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TripsTable tripsTable = new TripsTable(getActivity().getApplicationContext());
        //myDataset = tripsTable.select(Home.PAST);
        parentActivity = getActivity();
        mRecyclerView = (RecyclerView) parentActivity.findViewById(R.id.list_upcoming_trips);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(parentActivity, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CardsAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);
    }

    public PastTripsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trips, container, false);
    }

}
