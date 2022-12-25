package com.tencent.liteav.audio.impl.Record;

import android.content.Context;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import java.lang.ref.WeakReference;
import java.util.Arrays;

/* renamed from: com.tencent.liteav.audio.impl.Record.a */
/* loaded from: classes3.dex */
public class TXCAudioBGMRecord implements Runnable {

    /* renamed from: a */
    private WeakReference<TXIAudioPcmRecordListener> f2093a;

    /* renamed from: b */
    private Context f2094b;

    /* renamed from: c */
    private int f2095c;

    /* renamed from: d */
    private int f2096d;

    /* renamed from: e */
    private int f2097e;

    /* renamed from: f */
    private boolean f2098f;

    /* renamed from: g */
    private Thread f2099g;

    /* renamed from: h */
    private byte[] f2100h;

    /* renamed from: a */
    public synchronized void m3408a(TXIAudioPcmRecordListener tXIAudioPcmRecordListener) {
        if (tXIAudioPcmRecordListener == null) {
            this.f2093a = null;
        } else {
            this.f2093a = new WeakReference<>(tXIAudioPcmRecordListener);
        }
    }

    /* renamed from: a */
    public void m3409a(Context context, int i, int i2, int i3) {
        m3410a();
        this.f2094b = context;
        this.f2095c = i;
        this.f2096d = i2;
        this.f2097e = i3;
        this.f2098f = true;
        this.f2099g = new Thread(this, "AudioSysRecord Thread");
        this.f2099g.start();
    }

    /* renamed from: a */
    public void m3410a() {
        this.f2098f = false;
        long currentTimeMillis = System.currentTimeMillis();
        Thread thread = this.f2099g;
        if (thread != null && thread.isAlive() && Thread.currentThread().getId() != this.f2099g.getId()) {
            try {
                this.f2099g.join();
            } catch (Exception e) {
                e.printStackTrace();
                TXCLog.m2914e("AudioCenter:TXCAudioBGMRecord", "record stop Exception: " + e.getMessage());
            }
        }
        TXCLog.m2913i("AudioCenter:TXCAudioBGMRecord", "stop record cost time(MS): " + (System.currentTimeMillis() - currentTimeMillis));
        this.f2099g = null;
    }

    /* renamed from: b */
    public boolean m3406b() {
        return this.f2098f;
    }

    /* renamed from: a */
    private void m3407a(byte[] bArr, int i, long j) {
        TXIAudioPcmRecordListener tXIAudioPcmRecordListener;
        synchronized (this) {
            tXIAudioPcmRecordListener = this.f2093a != null ? this.f2093a.get() : null;
        }
        if (tXIAudioPcmRecordListener != null) {
            tXIAudioPcmRecordListener.onAudioRecordPCM(bArr, i, j);
        } else {
            TXCLog.m2914e("AudioCenter:TXCAudioBGMRecord", "onRecordPcmData:no callback");
        }
    }

    /* renamed from: c */
    private void m3405c() {
        TXIAudioPcmRecordListener tXIAudioPcmRecordListener;
        synchronized (this) {
            tXIAudioPcmRecordListener = this.f2093a != null ? this.f2093a.get() : null;
        }
        if (tXIAudioPcmRecordListener != null) {
            tXIAudioPcmRecordListener.onAudioRecordStart();
        } else {
            TXCLog.m2914e("AudioCenter:TXCAudioBGMRecord", "onRecordStart:no callback");
        }
    }

    /* renamed from: d */
    private void m3404d() {
        TXIAudioPcmRecordListener tXIAudioPcmRecordListener;
        synchronized (this) {
            tXIAudioPcmRecordListener = this.f2093a != null ? this.f2093a.get() : null;
        }
        if (tXIAudioPcmRecordListener != null) {
            tXIAudioPcmRecordListener.onAudioRecordStop();
        } else {
            TXCLog.m2914e("AudioCenter:TXCAudioBGMRecord", "onRecordStop:no callback");
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        if (!this.f2098f) {
            TXCLog.m2911w("AudioCenter:TXCAudioBGMRecord", "audio record: abandom start audio sys record thread!");
            return;
        }
        m3405c();
        int i = this.f2095c;
        int i2 = this.f2096d;
        int i3 = this.f2097e;
        int i4 = ((i2 * 1024) * i3) / 8;
        this.f2100h = new byte[i4];
        Arrays.fill(this.f2100h, (byte) 0);
        long j = 0;
        long currentTimeMillis = System.currentTimeMillis();
        while (this.f2098f && !Thread.interrupted()) {
            if (((((((System.currentTimeMillis() - currentTimeMillis) * i) * i2) * i3) / 8) / 1000) - j < i4) {
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                byte[] bArr = this.f2100h;
                j += bArr.length;
                m3407a(bArr, bArr.length, TXCTimeUtil.getTimeTick());
            }
        }
        m3404d();
    }
}
