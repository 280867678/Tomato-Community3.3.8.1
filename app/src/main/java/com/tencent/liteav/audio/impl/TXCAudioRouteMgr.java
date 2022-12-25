package com.tencent.liteav.audio.impl;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.tencent.liteav.audio.TXEAudioDef;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCSystemUtil;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tencent.liteav.audio.impl.a */
/* loaded from: classes3.dex */
public class TXCAudioRouteMgr implements TXITelephonyMrgListener {

    /* renamed from: a */
    private final String f2136a;

    /* renamed from: b */
    private Context f2137b;

    /* renamed from: c */
    private BroadcastReceiver f2138c;

    /* renamed from: d */
    private BluetoothProfile.ServiceListener f2139d;

    /* renamed from: e */
    private BluetoothHeadset f2140e;

    /* renamed from: f */
    private List<TXIHeadsetMgrListener> f2141f;

    /* renamed from: g */
    private Handler f2142g;

    /* renamed from: h */
    private boolean f2143h;

    /* renamed from: i */
    private int f2144i;

    /* renamed from: j */
    private boolean f2145j;

    /* renamed from: k */
    private AudioManager f2146k;

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: TXCAudioRouteMgr.java */
    /* renamed from: com.tencent.liteav.audio.impl.a$a */
    /* loaded from: classes3.dex */
    public static class C3305a {

        /* renamed from: a */
        private static final TXCAudioRouteMgr f2151a = new TXCAudioRouteMgr();
    }

    /* renamed from: a */
    public static void m3361a(String str) {
        TXCTraeJNI.nativeSetTraeConfig(str);
    }

    static {
        TXCSystemUtil.m2873e();
    }

    /* renamed from: a */
    public static TXCAudioRouteMgr m3371a() {
        return C3305a.f2151a;
    }

    private TXCAudioRouteMgr() {
        this.f2136a = "AudioCenter:" + TXCAudioRouteMgr.class.getSimpleName();
        this.f2144i = TXEAudioDef.TXE_AUDIO_MODE_SPEAKER;
        this.f2145j = false;
        this.f2141f = new ArrayList();
    }

    /* renamed from: a */
    public void m3368a(Context context) {
        if (this.f2138c != null) {
            return;
        }
        TXCLog.m2913i(this.f2136a, "init");
        this.f2137b = context.getApplicationContext();
        this.f2146k = (AudioManager) context.getSystemService("audio");
        this.f2142g = new Handler(Looper.getMainLooper());
        TXCTelephonyMgr.m3346a().m3344a(this.f2137b);
        TXCTelephonyMgr.m3346a().m3340a(this);
        this.f2138c = new BroadcastReceiver() { // from class: com.tencent.liteav.audio.impl.a.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                String str = TXCAudioRouteMgr.this.f2136a;
                TXCLog.m2913i(str, "onReceive, action = " + action);
                if (action.equals("android.intent.action.HEADSET_PLUG")) {
                    TXCAudioRouteMgr.this.m3358b(intent);
                } else if (action.equals("android.media.ACTION_SCO_AUDIO_STATE_UPDATED")) {
                    TXCAudioRouteMgr.this.m3367a(intent);
                } else {
                    BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                    if (bluetoothDevice == null || TXCAudioRouteMgr.this.f2140e == null) {
                        return;
                    }
                    try {
                        Thread.sleep(500L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    TXCAudioRouteMgr.this.m3369a(bluetoothDevice);
                }
            }
        };
        this.f2139d = new BluetoothProfile.ServiceListener() { // from class: com.tencent.liteav.audio.impl.a.2
            @Override // android.bluetooth.BluetoothProfile.ServiceListener
            public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
                TXCLog.m2913i(TXCAudioRouteMgr.this.f2136a, "onServiceConnected");
                if (i == 1) {
                    TXCAudioRouteMgr.this.f2140e = (BluetoothHeadset) bluetoothProfile;
                    TXCAudioRouteMgr.this.m3359b();
                }
            }

            @Override // android.bluetooth.BluetoothProfile.ServiceListener
            public void onServiceDisconnected(int i) {
                TXCLog.m2913i(TXCAudioRouteMgr.this.f2136a, "onServiceDisconnected");
                if (i == 1) {
                    TXCAudioRouteMgr.this.f2140e = null;
                    TXCAudioRouteMgr tXCAudioRouteMgr = TXCAudioRouteMgr.this;
                    tXCAudioRouteMgr.m3370a(tXCAudioRouteMgr.f2144i);
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.HEADSET_PLUG");
        intentFilter.addAction("android.bluetooth.device.action.ACL_CONNECTED");
        intentFilter.addAction("android.bluetooth.device.action.ACL_DISCONNECTED");
        intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        intentFilter.addAction("android.media.ACTION_SCO_AUDIO_STATE_UPDATED");
        this.f2137b.registerReceiver(this.f2138c, intentFilter);
        try {
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            if (defaultAdapter == null) {
                return;
            }
            defaultAdapter.getProfileProxy(this.f2137b, this.f2139d, 1);
        } catch (Exception e) {
            String str = this.f2136a;
            TXCLog.m2914e(str, "BluetoothAdapter getProfileProxy: " + e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: b */
    public void m3359b() {
        List<BluetoothDevice> list = null;
        try {
            list = this.f2140e.getConnectedDevices();
        } catch (Exception e) {
            String str = this.f2136a;
            TXCLog.m2914e(str, "getConnectedDevices exception = " + e);
            this.f2140e = null;
        }
        if (list == null || list.size() <= 0) {
            return;
        }
        m3369a(list.get(0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public void m3367a(Intent intent) {
        int intExtra = intent.getIntExtra("android.media.extra.SCO_AUDIO_STATE", -1);
        String str = this.f2136a;
        Log.d(str, "Audio SCO state: " + intExtra + ", thread id = " + Thread.currentThread().getId());
        if (intExtra == 1) {
            TXCLog.m2913i(this.f2136a, "SCO connected, yeah!");
            this.f2145j = true;
            this.f2146k.setBluetoothScoOn(true);
            m3370a(this.f2144i);
        } else if (intExtra == 2) {
            TXCLog.m2913i(this.f2136a, "SCO connecting");
        } else if (intExtra != 0) {
        } else {
            TXCLog.m2913i(this.f2136a, "SCO disconnect");
            if (this.f2140e == null || !this.f2145j) {
                return;
            }
            TXCLog.m2913i(this.f2136a, "mLastBTScoConnected true, retry once");
            this.f2145j = false;
            this.f2142g.postDelayed(new Runnable() { // from class: com.tencent.liteav.audio.impl.a.3
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        TXCAudioRouteMgr.this.f2146k.startBluetoothSco();
                    } catch (NullPointerException e) {
                        String str2 = TXCAudioRouteMgr.this.f2136a;
                        TXCLog.m2913i(str2, "startBluetoothSco exception = " + e);
                        e.printStackTrace();
                    }
                }
            }, 1000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: b */
    public void m3358b(Intent intent) {
        if (intent.hasExtra("state")) {
            int intExtra = intent.getIntExtra("state", 0);
            if (intExtra == 0) {
                this.f2143h = false;
                m3360a(false);
                m3370a(this.f2144i);
                TXCLog.m2915d(this.f2136a, "pull out wired headset");
            } else if (1 != intExtra) {
            } else {
                this.f2143h = true;
                m3360a(true);
                m3370a(this.f2144i);
                TXCLog.m2915d(this.f2136a, "insert wired headset");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public void m3369a(BluetoothDevice bluetoothDevice) {
        BluetoothHeadset bluetoothHeadset;
        int i;
        if (bluetoothDevice == null || (bluetoothHeadset = this.f2140e) == null) {
            return;
        }
        try {
            i = bluetoothHeadset.getConnectionState(bluetoothDevice);
        } catch (Exception e) {
            String str = this.f2136a;
            TXCLog.m2914e(str, "getConnectionState exception: " + e);
            i = 0;
        }
        String str2 = this.f2136a;
        TXCLog.m2915d(str2, "BluetoothHeadset state：" + i);
        if (i == 0) {
            m3360a(false);
            TXCLog.m2915d(this.f2136a, "BluetoothHeadset disconnect");
            this.f2146k.setBluetoothScoOn(false);
            this.f2146k.stopBluetoothSco();
            m3370a(this.f2144i);
        } else if (i != 2) {
        } else {
            m3360a(true);
            TXCLog.m2915d(this.f2136a, "BluetoothHeadset connect");
            if (!this.f2146k.isBluetoothScoAvailableOffCall()) {
                TXCLog.m2914e(this.f2136a, "not support BTHeadset sco");
                return;
            }
            try {
                this.f2146k.startBluetoothSco();
            } catch (NullPointerException e2) {
                String str3 = this.f2136a;
                TXCLog.m2913i(str3, "startBluetoothSco exception = " + e2);
                e2.printStackTrace();
            }
        }
    }

    /* renamed from: a */
    public synchronized void m3370a(int i) {
        this.f2144i = i;
        if (this.f2143h) {
            this.f2146k.setMode(0);
            this.f2146k.setSpeakerphoneOn(false);
            TXCLog.m2915d(this.f2136a, "setAudioMode, is wiredHeadsetOn, set MODE_NORMAL and speakerphoneOn false");
        } else if (this.f2140e != null && this.f2146k.isBluetoothScoOn()) {
            this.f2146k.setMode(3);
            this.f2146k.setSpeakerphoneOn(false);
            TXCLog.m2915d(this.f2136a, "setAudioMode, is bluetoothHeadset connect and isBluetoothScoOn true, set mode MODE_IN_COMMUNICATION and speakerphoneOn false");
        } else if (this.f2146k == null) {
        } else {
            if (i == TXEAudioDef.TXE_AUDIO_MODE_RECEIVER) {
                this.f2146k.setMode(3);
                this.f2146k.setSpeakerphoneOn(false);
                TXCLog.m2913i(this.f2136a, "AudioCenter setAudioMode to receiver, MODE_IN_COMMUNICATION, speakerphoneOn false");
            } else {
                this.f2146k.setMode(0);
                this.f2146k.setSpeakerphoneOn(true);
                TXCLog.m2913i(this.f2136a, "AudioCenter setAudioMode to speaker, MODE_NORMAL, speakerphoneOn true");
            }
        }
    }

    /* renamed from: a */
    public synchronized void m3362a(TXIHeadsetMgrListener tXIHeadsetMgrListener) {
        if (tXIHeadsetMgrListener == null) {
            return;
        }
        this.f2141f.add(tXIHeadsetMgrListener);
        if (this.f2143h) {
            tXIHeadsetMgrListener.OnHeadsetState(true);
        } else if (this.f2140e != null && this.f2146k != null && this.f2146k.isBluetoothScoOn()) {
            tXIHeadsetMgrListener.OnHeadsetState(true);
        } else {
            tXIHeadsetMgrListener.OnHeadsetState(false);
        }
    }

    /* renamed from: b */
    public synchronized void m3355b(TXIHeadsetMgrListener tXIHeadsetMgrListener) {
        if (tXIHeadsetMgrListener == null) {
            return;
        }
        this.f2141f.remove(tXIHeadsetMgrListener);
    }

    /* renamed from: a */
    private synchronized void m3360a(boolean z) {
        for (TXIHeadsetMgrListener tXIHeadsetMgrListener : this.f2141f) {
            tXIHeadsetMgrListener.OnHeadsetState(z);
        }
    }

    @Override // com.tencent.liteav.audio.impl.TXITelephonyMrgListener
    /* renamed from: b */
    public void mo3337b(int i) {
        String str = this.f2136a;
        TXCLog.m2913i(str, "onCallStateChanged, state = " + i);
        if (!this.f2146k.isBluetoothScoAvailableOffCall()) {
            TXCLog.m2914e(this.f2136a, "not support BTHeadset sco");
        } else if (i != 0 || this.f2140e == null) {
        } else {
            TXCLog.m2913i(this.f2136a, "to restartBluetoothSco");
            this.f2142g.postDelayed(new Runnable() { // from class: com.tencent.liteav.audio.impl.a.4
                @Override // java.lang.Runnable
                public void run() {
                    TXCAudioRouteMgr.this.m3359b();
                }
            }, 1000L);
        }
    }
}
