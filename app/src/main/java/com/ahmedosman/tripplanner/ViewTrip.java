package com.ahmedosman.tripplanner;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

public class ViewTrip extends AppCompatActivity {
    private Trip trip;
    private AppBarLayout appbar;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_view_trip, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trip);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        appbar = (AppBarLayout) findViewById(R.id.app_bar);
        //setSupportActionBar(toolbar);
        Intent intent = getIntent();
        trip = (Trip)intent.getSerializableExtra("");

        ImageButton edit = (ImageButton) findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent editIntent = new Intent();
                editIntent.putExtra("",trip);
                startActivity(editIntent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuItem doneMenuItem = (MenuItem)findViewById(R.id.done_trip_view);
        MenuItem reversMenuItem = (MenuItem)findViewById(R.id.reverse_trip_view);
        switch (item.getItemId()) {
            case R.id.start_trip_view:
                Intent mapintent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345"));
                startActivity(mapintent);
                return true;
            case R.id.delete_trip_view:
                //delete
                //delete alarm
                return true;
            case R.id.done_trip_view:
                trip.setStatus("done");
                //update database
                //delete alarm
                doneMenuItem.setVisible(false);
                reversMenuItem.setVisible(true);
                return true;
            case R.id.reverse_trip_view:
                String startPoint = trip.getStartPoint();
                trip.setStartPoint(trip.getEndPoint());
                trip.setEndPoint(startPoint);
                trip.setStatus("upcoming");
                //create new trip
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
