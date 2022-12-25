package com.tencent.liteav.audio.impl;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import com.tencent.liteav.basic.log.TXCLog;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* renamed from: com.tencent.liteav.audio.impl.c */
/* loaded from: classes3.dex */
public class TXCTelephonyMgr {

    /* renamed from: a */
    private static final TXCTelephonyMgr f2153a = new TXCTelephonyMgr();

    /* renamed from: b */
    private ConcurrentHashMap<Integer, WeakReference<TXITelephonyMrgListener>> f2154b = new ConcurrentHashMap<>();

    /* renamed from: c */
    private PhoneStateListener f2155c = null;

    /* renamed from: d */
    private Context f2156d;

    /* renamed from: a */
    public static TXCTelephonyMgr m3346a() {
        return f2153a;
    }

    private TXCTelephonyMgr() {
    }

    /* renamed from: a */
    public synchronized void m3340a(TXITelephonyMrgListener tXITelephonyMrgListener) {
        if (tXITelephonyMrgListener == null) {
            return;
        }
        this.f2154b.put(Integer.valueOf(tXITelephonyMrgListener.hashCode()), new WeakReference<>(tXITelephonyMrgListener));
    }

    /* renamed from: b */
    public synchronized void m3338b(TXITelephonyMrgListener tXITelephonyMrgListener) {
        if (tXITelephonyMrgListener == null) {
            return;
        }
        if (this.f2154b.containsKey(Integer.valueOf(tXITelephonyMrgListener.hashCode()))) {
            this.f2154b.remove(Integer.valueOf(tXITelephonyMrgListener.hashCode()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public synchronized void m3345a(int i) {
        Iterator<Map.Entry<Integer, WeakReference<TXITelephonyMrgListener>>> it2 = this.f2154b.entrySet().iterator();
        while (it2.hasNext()) {
            TXITelephonyMrgListener tXITelephonyMrgListener = it2.next().getValue().get();
            if (tXITelephonyMrgListener != null) {
                tXITelephonyMrgListener.mo3337b(i);
            } else {
                it2.remove();
            }
        }
    }

    /* renamed from: a */
    public void m3344a(Context context) {
        if (this.f2155c != null) {
            return;
        }
        this.f2156d = context.getApplicationContext();
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.tencent.liteav.audio.impl.c.1
            @Override // java.lang.Runnable
            public void run() {
                if (TXCTelephonyMgr.this.f2155c != null) {
                    return;
                }
                TXCTelephonyMgr.this.f2155c = new PhoneStateListener() { // from class: com.tencent.liteav.audio.impl.c.1.1
                    @Override // android.telephony.PhoneStateListener
                    public void onCallStateChanged(int i, String str) {
                        super.onCallStateChanged(i, str);
                        TXCLog.m2913i("AudioCenter:TXCTelephonyMgr", "onCallStateChanged:" + i);
                        TXCTelephonyMgr.this.m3345a(i);
                    }
                };
                ((TelephonyManager) TXCTelephonyMgr.this.f2156d.getSystemService("phone")).listen(TXCTelephonyMgr.this.f2155c, 32);
            }
        });
    }

    protected void finalize() throws Throwable {
        super.finalize();
        if (this.f2155c == null || this.f2156d == null) {
            return;
        }
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.tencent.liteav.audio.impl.c.2
            @Override // java.lang.Runnable
            public void run() {
                if (TXCTelephonyMgr.this.f2155c != null && TXCTelephonyMgr.this.f2156d != null) {
                    ((TelephonyManager) TXCTelephonyMgr.this.f2156d.getApplicationContext().getSystemService("phone")).listen(TXCTelephonyMgr.this.f2155c, 0);
                }
                TXCTelephonyMgr.this.f2155c = null;
            }
        });
    }
}
