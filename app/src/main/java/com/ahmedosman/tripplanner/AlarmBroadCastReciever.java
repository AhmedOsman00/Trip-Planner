package com.ahmedosman.tripplanner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Ahmed on 13-Feb-18.
 */

public class AlarmBroadCastReciever extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        intent.setClass(context,Dialogue.class);
        context.startActivity(intent);
    }
}
