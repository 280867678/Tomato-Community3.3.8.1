package com.tencent.liteav.audio.impl.Record;

import android.content.Context;
import com.tencent.liteav.basic.log.TXCLog;
import java.lang.ref.WeakReference;

/* renamed from: com.tencent.liteav.audio.impl.Record.b */
/* loaded from: classes3.dex */
public abstract class TXCAudioBaseRecord {

    /* renamed from: a */
    protected int f2101a = 0;

    /* renamed from: b */
    protected int f2102b = 0;

    /* renamed from: c */
    protected int f2103c = 0;

    /* renamed from: d */
    private WeakReference<TXIAudioPcmRecordListener> f2104d = null;

    /* renamed from: a */
    public void mo3399a(Context context, int i, int i2, int i3) {
        this.f2101a = i;
        this.f2102b = i2;
        this.f2103c = i3;
    }

    /* renamed from: a */
    public synchronized void m3402a(TXIAudioPcmRecordListener tXIAudioPcmRecordListener) {
        if (tXIAudioPcmRecordListener == null) {
            this.f2104d = null;
        } else {
            this.f2104d = new WeakReference<>(tXIAudioPcmRecordListener);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: a */
    public void m3401a(byte[] bArr, int i, long j) {
        TXIAudioPcmRecordListener tXIAudioPcmRecordListener;
        synchronized (this) {
            tXIAudioPcmRecordListener = this.f2104d != null ? this.f2104d.get() : null;
        }
        if (tXIAudioPcmRecordListener != null) {
            tXIAudioPcmRecordListener.onAudioRecordPCM(bArr, i, j);
        } else {
            TXCLog.m2914e("AudioCenter:TXCAudioBaseRecord", "onRecordPcmData:no callback");
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: a */
    public void m3403a() {
        TXIAudioPcmRecordListener tXIAudioPcmRecordListener;
        synchronized (this) {
            tXIAudioPcmRecordListener = this.f2104d != null ? this.f2104d.get() : null;
        }
        if (tXIAudioPcmRecordListener != null) {
            tXIAudioPcmRecordListener.onAudioRecordStart();
        } else {
            TXCLog.m2914e("AudioCenter:TXCAudioBaseRecord", "onRecordStart:no callback");
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: b */
    public void m3400b() {
        TXIAudioPcmRecordListener tXIAudioPcmRecordListener;
        synchronized (this) {
            tXIAudioPcmRecordListener = this.f2104d != null ? this.f2104d.get() : null;
        }
        if (tXIAudioPcmRecordListener != null) {
            tXIAudioPcmRecordListener.onAudioRecordStop();
        } else {
            TXCLog.m2914e("AudioCenter:TXCAudioBaseRecord", "onRecordStop:no callback");
        }
    }
}
