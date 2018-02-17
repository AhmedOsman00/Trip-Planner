package com.ahmedosman.tripplanner;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FragmentManager mgr;
    private FragmentTransaction trns;
    public static final String PAST = "past";
    public static final String UPCOMING = "upcoming";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mgr = getFragmentManager();
        trns = mgr.beginTransaction();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        UpcomingTripsFragment upcomingFragment = new UpcomingTripsFragment();
        FragmentManager mgr = getFragmentManager();
        FragmentTransaction trns = mgr.beginTransaction();
        trns.add(R.id.fragment_layout, upcomingFragment, "upcoming");
        trns.commit();
        FloatingActionButton addNewTrip = (FloatingActionButton)findViewById(R.id.add_new_trip);
        addNewTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this,AddTrip.class);
                startActivity(intent);
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.nav_upcoming_trips:
                Toast.makeText(getApplicationContext(), "Upcoming", Toast.LENGTH_SHORT).show();
                UpcomingTripsFragment upcomingFragment = new UpcomingTripsFragment();
                trns.replace(R.id.fragment_layout, upcomingFragment, UPCOMING);
                trns.commit();
                break;
            case R.id.nav_past_trips:
                Toast.makeText(getApplicationContext(), "Past", Toast.LENGTH_SHORT).show();
                PastTripsFragment pastTripsFragment = new PastTripsFragment();
                trns.replace(R.id.fragment_layout, pastTripsFragment, PAST);
                trns.commit();
                break;
            case R.id.nav_sign_out:
                Toast.makeText(getApplicationContext(), "Signed out", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_about:
                Toast.makeText(getApplicationContext(), "Trip Planner Version 1.0", Toast.LENGTH_SHORT).show();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
