package com.tencent.liteav.muxer.jni;

import com.tencent.liteav.basic.log.TXCLog;
import java.nio.ByteBuffer;

/* loaded from: classes3.dex */
public class TXSWMuxerJNI {

    /* renamed from: a */
    private long f4727a;

    /* renamed from: b */
    private volatile boolean f4728b = true;

    /* renamed from: c */
    private volatile boolean f4729c;

    /* loaded from: classes3.dex */
    public static class AVOptions {
        public int videoWidth = 960;
        public int videoHeight = 540;
        public int videoGOP = 12;
        public int audioSampleRate = 0;
        public int audioChannels = 0;
    }

    private native long init();

    private native void release(long j);

    private native void setAVParams(long j, AVOptions aVOptions);

    private native void setAudioCSD(long j, byte[] bArr);

    private native void setDstPath(long j, String str);

    private native void setVideoCSD(long j, byte[] bArr, byte[] bArr2);

    private native int start(long j);

    private native int stop(long j);

    private native int writeFrame(long j, byte[] bArr, int i, int i2, int i3, int i4, long j2);

    public TXSWMuxerJNI() {
        this.f4727a = -1L;
        this.f4727a = init();
    }

    /* renamed from: a */
    public void m1210a(AVOptions aVOptions) {
        if (this.f4728b) {
            setAVParams(this.f4727a, aVOptions);
        } else {
            TXCLog.m2914e("TXSWMuxerJNI", "Muxer isn't init yet!");
        }
    }

    /* renamed from: a */
    public void m1209a(String str) {
        if (this.f4728b) {
            setDstPath(this.f4727a, str);
        } else {
            TXCLog.m2914e("TXSWMuxerJNI", "Muxer isn't init yet!");
        }
    }

    /* renamed from: a */
    public void m1206a(ByteBuffer byteBuffer, int i, ByteBuffer byteBuffer2, int i2) {
        if (this.f4728b) {
            setVideoCSD(this.f4727a, m1204b(byteBuffer, i), m1204b(byteBuffer2, i2));
        } else {
            TXCLog.m2914e("TXSWMuxerJNI", "Muxer isn't init yet!");
        }
    }

    /* renamed from: a */
    public void m1208a(ByteBuffer byteBuffer, int i) {
        if (this.f4728b) {
            setAudioCSD(this.f4727a, m1204b(byteBuffer, i));
        } else {
            TXCLog.m2914e("TXSWMuxerJNI", "Muxer isn't init yet!");
        }
    }

    /* renamed from: a */
    public int m1207a(ByteBuffer byteBuffer, int i, int i2, int i3, int i4, long j) {
        if (this.f4728b) {
            if (this.f4729c) {
                int writeFrame = writeFrame(this.f4727a, m1204b(byteBuffer, i3), i, i2, i3, i4, j);
                if (writeFrame != 0) {
                    TXCLog.m2914e("TXSWMuxerJNI", "Muxer write frame error!");
                }
                return writeFrame;
            }
            TXCLog.m2914e("TXSWMuxerJNI", "Muxer isn't start yet!");
            return -1;
        }
        TXCLog.m2914e("TXSWMuxerJNI", "Muxer isn't init yet!");
        return -1;
    }

    /* renamed from: a */
    public int m1211a() {
        if (this.f4728b) {
            int start = start(this.f4727a);
            if (start == 0) {
                this.f4729c = true;
            } else {
                TXCLog.m2914e("TXSWMuxerJNI", "Start Muxer Error!!!");
            }
            return start;
        }
        TXCLog.m2914e("TXSWMuxerJNI", "Muxer isn't init yet!");
        return -1;
    }

    /* renamed from: b */
    public int m1205b() {
        if (this.f4728b) {
            if (this.f4729c) {
                this.f4729c = false;
                int stop = stop(this.f4727a);
                if (stop != 0) {
                    TXCLog.m2914e("TXSWMuxerJNI", "Stop Muxer Error!!!");
                }
                return stop;
            }
            TXCLog.m2914e("TXSWMuxerJNI", "Muxer isn't start yet!");
            return -1;
        }
        TXCLog.m2914e("TXSWMuxerJNI", "Muxer isn't init yet!");
        return -1;
    }

    /* renamed from: c */
    public void m1203c() {
        if (this.f4728b) {
            release(this.f4727a);
            this.f4728b = false;
            this.f4729c = false;
            return;
        }
        TXCLog.m2914e("TXSWMuxerJNI", "Muxer isn't init yet!");
    }

    /* renamed from: b */
    private byte[] m1204b(ByteBuffer byteBuffer, int i) {
        if (byteBuffer == null) {
            return null;
        }
        byte[] bArr = new byte[i];
        byteBuffer.get(bArr);
        return bArr;
    }
}
