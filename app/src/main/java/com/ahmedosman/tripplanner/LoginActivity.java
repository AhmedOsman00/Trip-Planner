package com.ahmedosman.tripplanner;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class LoginActivity extends AppCompatActivity {
    private FragmentManager mgr;
    private FragmentTransaction trns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mgr = getSupportFragmentManager();
        trns = mgr.beginTransaction();
        SignInFragment signInFragment = new SignInFragment();
        trns.add(R.id.login_fragment, signInFragment, "signin");
        trns.commit();
    }
}
