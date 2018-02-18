package com.ahmedosman.tripplanner.viewtrip;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ahmedosman.tripplanner.R;
import com.ahmedosman.tripplanner.addtrip.AddTrip;
import com.ahmedosman.tripplanner.broadcast.MyAlarmManager;
import com.ahmedosman.tripplanner.home.CardsAdapter;
import com.ahmedosman.tripplanner.home.Home;
import com.ahmedosman.tripplanner.models.Trip;
import com.ahmedosman.tripplanner.sqllite.TripsTable;

public class ViewTrip extends AppCompatActivity {
    private Trip trip;
    private CollapsingToolbarLayout appbar;
    private AppBarLayout headerappbar;
    public static final String EDIT_TRIP = "edit_trip";
    private TextView start_view;
    private TextView end_view;
    private TextView date_view;
    private TextView round_view;
    private TextView reminder_view;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_view_trip, menu);
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trip);
        headerappbar = (AppBarLayout) findViewById(R.id.app_bar);

        start_view = (TextView) findViewById(R.id.start_view);
        end_view = (TextView) findViewById(R.id.end_view);
        date_view = (TextView) findViewById(R.id.date_view);
        round_view = (TextView) findViewById(R.id.round_view);
        reminder_view = (TextView) findViewById(R.id.reminder_view);
        appbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        trip = (Trip) getIntent().getSerializableExtra(CardsAdapter.CURRENT_TRIP);
        start_view.setText(trip.getStartPoint());
        end_view.setText(trip.getEndPoint());
        date_view.setText(trip.getDay()+"/"+trip.getMonth()+"/"+trip.getYear()+"-"+trip.getHours()+"-"+trip.getMinutes());
        appbar.setTitle(trip.getTripName());
        if(trip.getRoundTrip()){
            round_view.setText("Round");
        }else{
            round_view.setText("One way");
        }
        ImageButton edit = (ImageButton) findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editIntent = new Intent(ViewTrip.this, AddTrip.class);
                editIntent.putExtra(EDIT_TRIP, trip);
                startActivity(editIntent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuItem doneMenuItem = (MenuItem) findViewById(R.id.done_trip_view);
        MenuItem reversMenuItem = (MenuItem) findViewById(R.id.reverse_trip_view);
        switch (item.getItemId()) {
            case R.id.start_trip_view:
                Intent mapintent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr="+trip.getStartPoint()+"&daddr="+trip.getEndPoint()));
                startActivity(mapintent);
                return true;
            case R.id.delete_trip_view:
                TripsTable.delete(trip.getTripId(),this);
                MyAlarmManager.stopAlarmManager(trip.getTripId(),ViewTrip.this);
                return true;
            case R.id.done_trip_view:
                trip.setStatus(Home.PAST);
                TripsTable.update(trip,this);
                MyAlarmManager.stopAlarmManager(trip.getTripId(),ViewTrip.this);
                doneMenuItem.setVisible(false);
                reversMenuItem.setVisible(true);
                return true;
            case R.id.reverse_trip_view:
                String startPoint = trip.getStartPoint();
                trip.setStartPoint(trip.getEndPoint());
                trip.setEndPoint(startPoint);
                trip.setStatus(Home.UPCOMING);
                TripsTable.insert(trip,this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
