package com.ahmedosman.tripplanner.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


public class AlarmBroadCastReciever extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        intent.setClass(context,Dialogue.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
