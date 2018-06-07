package com.ahmedosman.tripplanner.home;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmedosman.tripplanner.R;
import com.ahmedosman.tripplanner.addtrip.AddTrip;
import com.ahmedosman.tripplanner.login.LoginActivity;
import com.ahmedosman.tripplanner.sqllite.TripsTable;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String PAST = "past";
    public static final String UPCOMING = "upcoming";
    private TextView userName;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.popup_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        UpcomingTripsFragment upcomingFragment = new UpcomingTripsFragment();
        replaceFragment(this,upcomingFragment,R.id.fragment_layout,false,"upcoming");

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
        View headerView = navigationView.getHeaderView(0);
        userName = (TextView) headerView.findViewById(R.id.username);
        userName.setText(TripsTable.getUserName());
    }



    private void replaceFragment(AppCompatActivity activity, Fragment fragment, @IdRes int container,
                                       boolean isNeedToAddToStack,
                                       String fragmentTag) {

        FragmentManager manager = activity.getSupportFragmentManager();
        boolean isInStack = manager.popBackStackImmediate(fragmentTag, 0);
        FragmentTransaction ft = manager.beginTransaction();

        if (isInStack) {
            fragment = manager.findFragmentByTag(fragmentTag);
        }

        ft.replace(container, fragment, fragmentTag);
        if (!isInStack && isNeedToAddToStack) {
            ft.addToBackStack(fragmentTag);
        }

        ft.commit();
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
                replaceFragment(this,upcomingFragment,R.id.fragment_layout,false,"upcoming");

                break;
            case R.id.nav_past_trips:
                Toast.makeText(getApplicationContext(), "Past", Toast.LENGTH_SHORT).show();
                PastTripsFragment pastTripsFragment = new PastTripsFragment();
                replaceFragment(this,pastTripsFragment,R.id.fragment_layout,false,PAST);

                break;
            case R.id.nav_sign_out:
                Toast.makeText(getApplicationContext(), "Signed out Successfully", Toast.LENGTH_LONG).show();
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
