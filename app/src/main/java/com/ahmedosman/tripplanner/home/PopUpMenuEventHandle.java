package com.ahmedosman.tripplanner.home;


import android.content.Context;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;

import com.ahmedosman.tripplanner.R;
import com.ahmedosman.tripplanner.addtrip.AddTrip;
import com.ahmedosman.tripplanner.broadcast.Dialogue;
import com.ahmedosman.tripplanner.broadcast.MyAlarmManager;
import com.ahmedosman.tripplanner.models.Trip;
import com.ahmedosman.tripplanner.sqllite.TripsTable;
import com.ahmedosman.tripplanner.viewtrip.ViewTrip;

public class PopUpMenuEventHandle implements PopupMenu.OnMenuItemClickListener{
    Context context;
    Trip trip;
    public PopUpMenuEventHandle(Context context,Trip trip){
        this.context =context;
        this.trip = trip;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId()== R.id.onhold_strat_trip) {
            Intent mapintent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?saddr="+trip.getStartPoint()+"&daddr="+trip.getEndPoint()));
            context.startActivity(mapintent);
            return true;
        }
        if (item.getItemId()== R.id.onhold_edit_trip){
            Intent editIntent = new Intent(context, AddTrip.class);
            editIntent.putExtra(ViewTrip.EDIT_TRIP, trip);
            context.startActivity(editIntent);
            return true;
        }
        if (item.getItemId()== R.id.onhold_delete){
            TripsTable.delete(trip.getTripId(),context);
            MyAlarmManager.stopAlarmManager(trip.getTripId(),context);
            return true;
        }
        return false;
    }
}
