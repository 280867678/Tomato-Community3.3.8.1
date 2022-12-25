package com.one.tomato.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.one.tomato.entity.event.NetWorkEvent;
import com.one.tomato.mvp.base.bus.RxBus;

/* loaded from: classes3.dex */
public class ConnectionChangeReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        NetWorkEvent netWorkEvent = new NetWorkEvent();
        if (NetWorkUtil.isNetworkConnected()) {
            netWorkEvent.isNetCollected = true;
        } else {
            netWorkEvent.isNetCollected = false;
        }
        RxBus.getDefault().post(netWorkEvent);
    }
}
