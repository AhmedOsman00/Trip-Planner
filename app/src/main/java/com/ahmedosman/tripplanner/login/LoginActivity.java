package com.ahmedosman.tripplanner.login;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ahmedosman.tripplanner.R;


public class LoginActivity extends AppCompatActivity {
    private FragmentManager mgr;
    private FragmentTransaction trns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(savedInstanceState == null) {
            mgr = getSupportFragmentManager();
            trns = mgr.beginTransaction();
            SignInFragment signInFragment = new SignInFragment();
            trns.add(R.id.login_fragment, signInFragment, "signin");
            trns.commit();
        }
    }

}
