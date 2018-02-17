package com.ahmedosman.tripplanner;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class UpcomingTripsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    //private Trip[] myDataset = {new Trip()};
    private Activity parentActivity;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TripsTable tripsTable = new TripsTable(getActivity().getApplicationContext());
        // myDataset = tripsTable.select(Home.UPCOMING);
        Trip trip = new Trip();
        trip.setTripName("ahmed");
        Trip trip1 = new Trip();
        trip.setTripName("osman");
        Trip trip2 = new Trip();
        trip.setTripName("fadwa");
        Trip trip3 = new Trip();
        trip.setTripName("shimaa");
        Trip trip4 = new Trip();
        trip.setTripName("mohamed");
        Trip trip5 = new Trip();
        trip.setTripName("mahmoud");
        Trip trip6 = new Trip();
        trip.setTripName("hossam");
        Trip[] myDataset = {trip,trip1,trip2,trip3,trip4,trip5,trip6};
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
