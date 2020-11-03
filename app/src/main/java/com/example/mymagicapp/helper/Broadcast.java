package com.example.mymagicapp.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.mymagicapp.activities.LoginActivity;
import com.example.mymagicapp.activities.SettingActivity;

public class Broadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context, LoginActivity.class);
        context.startActivity(intent1);
    }
}
