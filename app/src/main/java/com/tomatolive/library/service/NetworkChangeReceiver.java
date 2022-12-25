package com.tomatolive.library.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.tomatolive.library.utils.NetUtils;

/* loaded from: classes3.dex */
public class NetworkChangeReceiver extends BroadcastReceiver {
    private NetChangeListener listener = null;
    private int tempState = 100;

    /* loaded from: classes3.dex */
    public interface NetChangeListener {
        void onNetChangeListener(int i);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        int netWorkState;
        if (!TextUtils.equals(intent.getAction(), "android.net.conn.CONNECTIVITY_CHANGE") || this.tempState == (netWorkState = NetUtils.getNetWorkState())) {
            return;
        }
        this.tempState = netWorkState;
        NetChangeListener netChangeListener = this.listener;
        if (netChangeListener == null) {
            return;
        }
        netChangeListener.onNetChangeListener(netWorkState);
    }

    public void setOnNetChangeListener(NetChangeListener netChangeListener) {
        if (netChangeListener != null) {
            this.listener = netChangeListener;
        }
    }
}
