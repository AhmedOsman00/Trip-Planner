package com.ahmedosman.tripplanner.broadcast;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.ahmedosman.tripplanner.broadcast.AlarmBroadCastReciever;
import com.ahmedosman.tripplanner.models.Trip;

import java.util.Calendar;



public class MyAlarmManager {


    //Trigger alarm manager with entered time interval
    public static void triggerAlarmManager(Calendar cal, Trip trip , Context mContext) {
        Intent alarmIntent = new Intent(mContext, AlarmBroadCastReciever.class);
        alarmIntent.putExtra("TRIP",trip);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, trip.getTripId(), alarmIntent, 0);
        AlarmManager manager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);//get instance of alarm manager
        manager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);//set alarm manager with entered timer by converting into milliseconds
        //manager.set(AlarmManager.RTC_WAKEUP, 3000, pendingIntent);//set alarm manager with entered timer by converting into milliseconds

        Log.d("TAG___",cal.getTimeInMillis()+"");
    }


    //Stop/Cancel alarm manager
    public static void stopAlarmManager(int id ,Context mContext) {
        AlarmManager manager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(mContext, AlarmBroadCastReciever.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        pendingIntent.cancel();
        //remove the notification from notification tray
//        NotificationManager notificationManager = (NotificationManager) this
//                .getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.cancel(AlarmNotificationService.NOTIFICATION_ID);

    }
}
