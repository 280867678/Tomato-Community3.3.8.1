package com.tomatolive.library.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.text.TextUtils;
import android.util.Log;

/* loaded from: classes4.dex */
public class BluetoothHeadsetUtils {
    private static final String TAG = "BluetoothHeadsetUtils";
    private AudioManager mAudioManager;
    private BluetoothHeadset mBluetoothHeadset;
    private Context mContext;
    private boolean mIsOnHeadsetSco;
    private boolean mIsStarted;
    private BluetoothProfile.ServiceListener mHeadsetProfileListener = new BluetoothProfile.ServiceListener() { // from class: com.tomatolive.library.utils.BluetoothHeadsetUtils.1
        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceDisconnected(int i) {
            Log.d(BluetoothHeadsetUtils.TAG, "Profile listener onServiceDisconnected");
            if (BluetoothHeadsetUtils.this.mBluetoothHeadset != null) {
                BluetoothHeadsetUtils.this.stopBluetoothSco();
            }
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            Log.d(BluetoothHeadsetUtils.TAG, "Profile listener onServiceConnected");
            BluetoothHeadsetUtils.this.mBluetoothHeadset = (BluetoothHeadset) bluetoothProfile;
            if (BluetoothHeadsetUtils.this.mBluetoothHeadset.getConnectedDevices().size() > 0) {
                BluetoothHeadsetUtils.this.startBluetoothSco();
            }
        }
    };
    private BroadcastReceiver mHeadsetBroadcastReceiver = new BroadcastReceiver() { // from class: com.tomatolive.library.utils.BluetoothHeadsetUtils.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent == null) {
                return;
            }
            String action = intent.getAction();
            if (TextUtils.equals(action, "android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED")) {
                int intExtra = intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0);
                Log.d(BluetoothHeadsetUtils.TAG, "Action = " + action + " State = " + intExtra);
                if (intExtra == 2) {
                    Log.d(BluetoothHeadsetUtils.TAG, "Headset connected");
                    if (BluetoothHeadsetUtils.this.mBluetoothHeadset != null) {
                        BluetoothHeadsetUtils.this.startBluetoothSco();
                    } else {
                        BluetoothHeadsetUtils.this.mBluetoothAdapter.getProfileProxy(BluetoothHeadsetUtils.this.mContext, BluetoothHeadsetUtils.this.mHeadsetProfileListener, 1);
                    }
                } else if (intExtra != 0) {
                } else {
                    Log.d(BluetoothHeadsetUtils.TAG, "Headset disconnected");
                    if (BluetoothHeadsetUtils.this.mBluetoothHeadset == null) {
                        return;
                    }
                    BluetoothHeadsetUtils.this.stopBluetoothSco();
                }
            } else if (!TextUtils.equals(action, "android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED")) {
            } else {
                int intExtra2 = intent.getIntExtra("android.bluetooth.profile.extra.STATE", 10);
                if (intExtra2 == 12) {
                    Log.d(BluetoothHeadsetUtils.TAG, "Headset audio connected");
                    BluetoothHeadsetUtils.this.mIsOnHeadsetSco = true;
                } else if (intExtra2 != 10) {
                } else {
                    Log.d(BluetoothHeadsetUtils.TAG, "Headset audio disconnected");
                    BluetoothHeadsetUtils.this.mIsOnHeadsetSco = false;
                }
            }
        }
    };
    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    public BluetoothHeadsetUtils(Context context) {
        this.mContext = context;
        this.mAudioManager = (AudioManager) this.mContext.getSystemService("audio");
    }

    public void start() {
        if (!this.mIsStarted && this.mBluetoothAdapter != null && this.mAudioManager.isBluetoothScoAvailableOffCall()) {
            this.mContext.registerReceiver(this.mHeadsetBroadcastReceiver, new IntentFilter("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED"));
            this.mContext.registerReceiver(this.mHeadsetBroadcastReceiver, new IntentFilter("android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED"));
            if (this.mBluetoothAdapter.isEnabled()) {
                this.mBluetoothAdapter.getProfileProxy(this.mContext, this.mHeadsetProfileListener, 1);
            }
            this.mIsStarted = true;
        }
    }

    public void stop() {
        if (!this.mIsStarted) {
            return;
        }
        if (this.mBluetoothHeadset != null) {
            stopBluetoothSco();
            this.mBluetoothAdapter.closeProfileProxy(1, this.mBluetoothHeadset);
            this.mBluetoothHeadset = null;
        }
        this.mContext.unregisterReceiver(this.mHeadsetBroadcastReceiver);
        this.mIsStarted = false;
    }

    public boolean isOnHeadsetSco() {
        return this.mIsOnHeadsetSco;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startBluetoothSco() {
        this.mAudioManager.setMode(3);
        this.mAudioManager.startBluetoothSco();
        this.mAudioManager.setBluetoothScoOn(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopBluetoothSco() {
        this.mAudioManager.setMode(0);
        this.mAudioManager.stopBluetoothSco();
        this.mAudioManager.setBluetoothScoOn(false);
    }
}
