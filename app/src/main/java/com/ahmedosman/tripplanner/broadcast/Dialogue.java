package com.ahmedosman.tripplanner.broadcast;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ahmedosman.tripplanner.R;
import com.ahmedosman.tripplanner.models.Trip;
import com.ahmedosman.tripplanner.sqllite.TripsTable;
import com.ahmedosman.tripplanner.viewtrip.ViewTrip;

public class Dialogue extends AppCompatActivity {
    private TextView trip_name;
    private Button start;
    private Button view;
    private Button cancel;
    private Button later;
    private Trip trip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogue);
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialogue_layout);
        dialog.setCancelable(true);
        dialog.show();
        TextView trip_name = (TextView)findViewById(R.id.name_of_trip);
         final Button start= (Button)findViewById(R.id.Strat);
         Button view= (Button)findViewById(R.id.View);
         Button cancel= (Button)findViewById(R.id.Cancel);
         Button later= (Button)findViewById(R.id.Later);
        trip = (Trip) getIntent().getSerializableExtra("TRIP");
        trip_name.setText(trip.getTripName());
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mapintent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr="+trip.getStartPoint()+"&daddr="+trip.getEndPoint()));
                startActivity(mapintent);
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dialogue.this, ViewTrip.class);
                startActivity(intent);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TripsTable.delete(trip.getTripId(),Dialogue.this);
                MyAlarmManager.stopAlarmManager(trip.getTripId(),Dialogue.this);
            }
        });
        later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                NotificationCompat.Builder builder =(NotificationCompat.Builder) new NotificationCompat.Builder(Dialogue.this)
//                        .setSmallIcon(R.drawable.notification_icon)
//                        .setContentTitle("Remember :"+t.getTripName())
//                        .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.notification_icon))
//                        .setContentText("Your notes :"+t.getNotes())
//                        .setAutoCancel(true);
//                Intent resultIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://maps.google.com/maps?saddr=" + String.valueOf(t.getStartLatitude()) +
//                        ", " + String.valueOf(trip.getStartPoint()) + "&daddr=" + String.valueOf(trip.getEndLatitude()) +
//                        ", " + String.valueOf(trip.getEndLongitude())));
//
//                PendingIntent resultPendingIntent = PendingIntent.getActivity(Dialogue.this, trip.getTripId(), resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//                builder.setContentIntent(resultPendingIntent);
            }
        });
    }
}
