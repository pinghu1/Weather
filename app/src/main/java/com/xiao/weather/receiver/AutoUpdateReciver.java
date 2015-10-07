package com.xiao.weather.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.xiao.weather.service.AutoUpdateService;

public class AutoUpdateReciver extends BroadcastReceiver {
    public AutoUpdateReciver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, AutoUpdateService.class);
        context.startService(i);
    }
}
