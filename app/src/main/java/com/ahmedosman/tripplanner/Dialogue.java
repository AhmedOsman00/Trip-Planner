package com.ahmedosman.tripplanner;

import android.app.Dialog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class Dialogue extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogue);
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialogue_layout);
        //ListView lv = (ListView) dialog.findViewById(R.id.lv);
        dialog.setCancelable(true);
        dialog.setTitle("ListView");
        dialog.show();

    }
}
