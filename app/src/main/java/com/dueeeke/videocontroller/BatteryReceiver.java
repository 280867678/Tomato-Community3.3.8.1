package com.dueeeke.videocontroller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

/* loaded from: classes2.dex */
public class BatteryReceiver extends BroadcastReceiver {
    public BatteryReceiver(ImageView imageView) {
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras == null) {
            return;
        }
        int i = (extras.getInt("level") * 100) / extras.getInt("scale");
    }
}
