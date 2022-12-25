package com.tencent.liteav.audio.impl.Record;

import android.content.Context;
import android.media.AudioManager;
import android.media.AudioRecord;
import com.tencent.liteav.audio.TXEAudioDef;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.ugc.TXRecordCommon;
import java.lang.ref.WeakReference;

/* renamed from: com.tencent.liteav.audio.impl.Record.f */
/* loaded from: classes3.dex */
public class TXCAudioSysRecord implements Runnable {

    /* renamed from: a */
    private static final String f2124a = "AudioCenter:" + TXCAudioSysRecord.class.getSimpleName();

    /* renamed from: b */
    private static TXCAudioSysRecord f2125b = null;

    /* renamed from: c */
    private Context f2126c;

    /* renamed from: h */
    private AudioRecord f2131h;

    /* renamed from: j */
    private WeakReference<TXIAudioPcmRecordListener> f2133j;

    /* renamed from: d */
    private int f2127d = TXRecordCommon.AUDIO_SAMPLERATE_48000;

    /* renamed from: e */
    private int f2128e = 1;

    /* renamed from: f */
    private int f2129f = 16;

    /* renamed from: g */
    private int f2130g = TXEAudioDef.TXE_AEC_NONE;

    /* renamed from: i */
    private byte[] f2132i = null;

    /* renamed from: k */
    private Thread f2134k = null;

    /* renamed from: l */
    private boolean f2135l = false;

    /* renamed from: a */
    public static TXCAudioSysRecord m3382a() {
        if (f2125b == null) {
            synchronized (TXCAudioSysRecord.class) {
                if (f2125b == null) {
                    f2125b = new TXCAudioSysRecord();
                }
            }
        }
        return f2125b;
    }

    private TXCAudioSysRecord() {
    }

    /* renamed from: a */
    public synchronized void m3379a(TXIAudioPcmRecordListener tXIAudioPcmRecordListener) {
        if (tXIAudioPcmRecordListener == null) {
            this.f2133j = null;
        } else {
            this.f2133j = new WeakReference<>(tXIAudioPcmRecordListener);
        }
    }

    /* renamed from: a */
    public void m3380a(Context context, int i, int i2, int i3, int i4) {
        m3377b();
        this.f2126c = context;
        this.f2127d = i;
        this.f2128e = i2;
        this.f2129f = i3;
        this.f2130g = i4;
        this.f2135l = true;
        this.f2134k = new Thread(this, "AudioSysRecord Thread");
        this.f2134k.start();
    }

    /* renamed from: b */
    public void m3377b() {
        this.f2135l = false;
        long currentTimeMillis = System.currentTimeMillis();
        Thread thread = this.f2134k;
        if (thread != null && thread.isAlive() && Thread.currentThread().getId() != this.f2134k.getId()) {
            try {
                this.f2134k.join();
            } catch (Exception e) {
                e.printStackTrace();
                String str = f2124a;
                TXCLog.m2914e(str, "record stop Exception: " + e.getMessage());
            }
        }
        String str2 = f2124a;
        TXCLog.m2913i(str2, "stop record cost time(MS): " + (System.currentTimeMillis() - currentTimeMillis));
        this.f2134k = null;
    }

    /* renamed from: c */
    public synchronized boolean m3376c() {
        return this.f2135l;
    }

    /* renamed from: d */
    private void m3375d() {
        AudioRecord audioRecord;
        int i = this.f2127d;
        int i2 = this.f2128e;
        int i3 = this.f2129f;
        int i4 = this.f2130g;
        int i5 = 3;
        TXCLog.m2913i(f2124a, String.format("audio record sampleRate = %d, channels = %d, bits = %d, aectype = %d", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4)));
        int i6 = i2 == 1 ? 16 : 12;
        int i7 = i3 == 8 ? 3 : 2;
        int minBufferSize = AudioRecord.getMinBufferSize(i, i6, i7);
        try {
            try {
                if (i4 == TXEAudioDef.TXE_AEC_SYSTEM) {
                    TXCLog.m2913i(f2124a, "audio record type: system aec");
                    if (this.f2126c != null) {
                        ((AudioManager) this.f2126c.getSystemService("audio")).setMode(3);
                    }
                    i5 = minBufferSize;
                    this.f2131h = new AudioRecord(7, i, i6, i7, minBufferSize * 2);
                    if (this.f2126c != null) {
                        ((AudioManager) this.f2126c.getSystemService("audio")).setMode(0);
                    }
                } else {
                    i5 = minBufferSize;
                    TXCLog.m2913i(f2124a, "audio record type: system normal");
                    this.f2131h = new AudioRecord(1, i, i6, i7, i5 * 2);
                }
            } catch (IllegalArgumentException e) {
                e = e;
                e.printStackTrace();
                audioRecord = this.f2131h;
                if (audioRecord != null) {
                }
                TXCLog.m2914e(f2124a, "audio record: initialize the mic failed.");
                m3374e();
                m3381a(TXEAudioDef.TXE_AUDIO_RECORD_ERR_NO_MIC_PERMIT, "open mic failed!");
                return;
            }
        } catch (IllegalArgumentException e2) {
            e = e2;
            i5 = minBufferSize;
        }
        audioRecord = this.f2131h;
        if (audioRecord != null || audioRecord.getState() != 1) {
            TXCLog.m2914e(f2124a, "audio record: initialize the mic failed.");
            m3374e();
            m3381a(TXEAudioDef.TXE_AUDIO_RECORD_ERR_NO_MIC_PERMIT, "open mic failed!");
            return;
        }
        int i8 = ((i2 * 1024) * i3) / 8;
        if (i8 > i5) {
            this.f2132i = new byte[i5];
        } else {
            this.f2132i = new byte[i8];
        }
        TXCLog.m2913i(f2124a, String.format("audio record: mic open rate=%dHZ, channels=%d, bits=%d, buffer=%d/%d, state=%d", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i5), Integer.valueOf(this.f2132i.length), Integer.valueOf(this.f2131h.getState())));
        AudioRecord audioRecord2 = this.f2131h;
        if (audioRecord2 == null) {
            return;
        }
        try {
            audioRecord2.startRecording();
        } catch (Exception e3) {
            e3.printStackTrace();
            TXCLog.m2914e(f2124a, "mic startRecording failed.");
            m3381a(TXEAudioDef.TXE_AUDIO_RECORD_ERR_NO_MIC_PERMIT, "start recording failed!");
        }
    }

    /* renamed from: e */
    private void m3374e() {
        if (this.f2131h != null) {
            TXCLog.m2913i(f2124a, "stop mic");
            try {
                this.f2131h.setRecordPositionUpdateListener(null);
                this.f2131h.stop();
                this.f2131h.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.f2131h = null;
        this.f2132i = null;
    }

    /* renamed from: a */
    private void m3378a(byte[] bArr, int i, long j) {
        TXIAudioPcmRecordListener tXIAudioPcmRecordListener;
        synchronized (this) {
            tXIAudioPcmRecordListener = this.f2133j != null ? this.f2133j.get() : null;
        }
        if (tXIAudioPcmRecordListener != null) {
            tXIAudioPcmRecordListener.onAudioRecordPCM(bArr, i, j);
        } else {
            TXCLog.m2914e(f2124a, "onRecordPcmData:no callback");
        }
    }

    /* renamed from: a */
    private void m3381a(int i, String str) {
        TXIAudioPcmRecordListener tXIAudioPcmRecordListener;
        synchronized (this) {
            tXIAudioPcmRecordListener = this.f2133j != null ? this.f2133j.get() : null;
        }
        if (tXIAudioPcmRecordListener != null) {
            tXIAudioPcmRecordListener.onAudioRecordError(i, str);
        } else {
            TXCLog.m2914e(f2124a, "onRecordError:no callback");
        }
    }

    /* renamed from: f */
    private void m3373f() {
        TXIAudioPcmRecordListener tXIAudioPcmRecordListener;
        synchronized (this) {
            tXIAudioPcmRecordListener = this.f2133j != null ? this.f2133j.get() : null;
        }
        if (tXIAudioPcmRecordListener != null) {
            tXIAudioPcmRecordListener.onAudioRecordStart();
        } else {
            TXCLog.m2914e(f2124a, "onRecordStart:no callback");
        }
    }

    /* renamed from: g */
    private void m3372g() {
        TXIAudioPcmRecordListener tXIAudioPcmRecordListener;
        synchronized (this) {
            tXIAudioPcmRecordListener = this.f2133j != null ? this.f2133j.get() : null;
        }
        if (tXIAudioPcmRecordListener != null) {
            tXIAudioPcmRecordListener.onAudioRecordStop();
        } else {
            TXCLog.m2914e(f2124a, "onRecordStop:no callback");
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        int i;
        byte[] bArr;
        if (!this.f2135l) {
            TXCLog.m2911w(f2124a, "audio record: abandom start audio sys record thread!");
            return;
        }
        m3373f();
        m3375d();
        loop0: while (true) {
            i = 0;
            int i2 = 0;
            while (this.f2135l && !Thread.interrupted() && this.f2131h != null && i <= 5) {
                System.currentTimeMillis();
                AudioRecord audioRecord = this.f2131h;
                byte[] bArr2 = this.f2132i;
                int read = audioRecord.read(bArr2, i2, bArr2.length - i2);
                bArr = this.f2132i;
                if (read != bArr.length - i2) {
                    if (read <= 0) {
                        String str = f2124a;
                        TXCLog.m2914e(str, "read pcm eror, len =" + read);
                        i++;
                    } else {
                        i2 += read;
                    }
                }
            }
            m3378a(bArr, bArr.length, TXCTimeUtil.getTimeTick());
        }
        m3374e();
        if (i > 5) {
            m3381a(TXEAudioDef.TXE_AUDIO_RECORD_ERR_NO_MIC_PERMIT, "read data failed!");
        } else {
            m3372g();
        }
    }
}
