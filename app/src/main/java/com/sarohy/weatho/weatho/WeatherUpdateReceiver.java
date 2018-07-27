package com.sarohy.weatho.weatho;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class WeatherUpdateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Service Check","Yes");
        context.startService(new Intent(context,WeatherFetchService.class));
    }
}
