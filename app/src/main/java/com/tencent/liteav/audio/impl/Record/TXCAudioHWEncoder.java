package com.tencent.liteav.audio.impl.Record;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.view.Surface;
import com.tencent.liteav.audio.TXCAudioRecorder;
import com.tencent.liteav.audio.TXIAudioRecordListener;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCSystemUtil;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.ugc.TXRecordCommon;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.Vector;

/* renamed from: com.tencent.liteav.audio.impl.Record.e */
/* loaded from: classes3.dex */
public class TXCAudioHWEncoder extends Thread {

    /* renamed from: a */
    private MediaCodec.BufferInfo f2110a;

    /* renamed from: b */
    private MediaCodecInfo f2111b;

    /* renamed from: c */
    private MediaFormat f2112c;

    /* renamed from: d */
    private MediaCodec f2113d;

    /* renamed from: e */
    private Vector<byte[]> f2114e;

    /* renamed from: f */
    private WeakReference<TXIAudioRecordListener> f2115f;

    /* renamed from: g */
    private volatile boolean f2116g = false;

    /* renamed from: h */
    private volatile boolean f2117h = false;

    /* renamed from: i */
    private final Object f2118i = new Object();

    /* renamed from: j */
    private long f2119j = 0;

    /* renamed from: k */
    private int f2120k = TXCAudioRecorder.f2042a;

    /* renamed from: l */
    private int f2121l = TXCAudioRecorder.f2043b;

    /* renamed from: m */
    private int f2122m = TXCAudioRecorder.f2044c;

    /* renamed from: n */
    private byte[] f2123n;

    static {
        TXCSystemUtil.m2873e();
    }

    @TargetApi(16)
    public TXCAudioHWEncoder() {
        super("TXAudioRecordThread");
    }

    /* renamed from: a */
    public void m3392a(int i, int i2, int i3, int i4, WeakReference<TXIAudioRecordListener> weakReference) {
        this.f2115f = weakReference;
        this.f2110a = new MediaCodec.BufferInfo();
        this.f2114e = new Vector<>();
        this.f2120k = i2;
        this.f2121l = i3;
        this.f2122m = i4;
        m3388b();
    }

    /* renamed from: a */
    public void m3389a(byte[] bArr, long j) {
        Vector<byte[]> vector = this.f2114e;
        if (vector != null && bArr != null) {
            synchronized (vector) {
                if (this.f2114e == null) {
                    return;
                }
                this.f2114e.add(bArr);
            }
        }
        synchronized (this.f2118i) {
            this.f2118i.notify();
        }
    }

    /* renamed from: a */
    public void m3393a() {
        m3386c();
    }

    /* renamed from: b */
    private void m3388b() {
        this.f2111b = m3391a("audio/mp4a-latm");
        if (this.f2111b == null) {
            TXCLog.m2914e("AudioCenter:TXCAudioHWEncoder", "Unable to find an appropriate codec for audio/mp4a-latm");
            return;
        }
        TXCLog.m2913i("AudioCenter:TXCAudioHWEncoder", "selected codec: " + this.f2111b.getName());
        int i = this.f2120k;
        int i2 = TXRecordCommon.AUDIO_SAMPLERATE_32000;
        if (i >= 32000) {
            i2 = 64000;
        }
        this.f2112c = MediaFormat.createAudioFormat("audio/mp4a-latm", this.f2120k, this.f2121l);
        this.f2112c.setInteger("bitrate", i2);
        this.f2112c.setInteger("channel-count", this.f2121l);
        this.f2112c.setInteger("sample-rate", this.f2120k);
        this.f2112c.setInteger("aac-profile", 2);
        TXCLog.m2913i("AudioCenter:TXCAudioHWEncoder", "format: " + this.f2112c);
        try {
            m3385d();
        } catch (Exception e) {
            e.printStackTrace();
        }
        start();
    }

    /* renamed from: c */
    private void m3386c() {
        this.f2117h = true;
    }

    @TargetApi(16)
    /* renamed from: d */
    private void m3385d() throws IOException {
        if (this.f2113d != null) {
            return;
        }
        this.f2113d = MediaCodec.createEncoderByType("audio/mp4a-latm");
        this.f2113d.configure(this.f2112c, (Surface) null, (MediaCrypto) null, 1);
        this.f2113d.start();
        TXCLog.m2913i("AudioCenter:TXCAudioHWEncoder", "prepare finishing");
        this.f2116g = true;
    }

    /* renamed from: e */
    private void m3384e() {
        MediaCodec mediaCodec = this.f2113d;
        if (mediaCodec != null) {
            mediaCodec.stop();
            this.f2113d.release();
            this.f2113d = null;
        }
        this.f2116g = false;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        boolean isEmpty;
        byte[] remove;
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(1024);
        while (!this.f2117h) {
            if (this.f2116g) {
                synchronized (this.f2114e) {
                    isEmpty = this.f2114e.isEmpty();
                }
                if (isEmpty) {
                    try {
                        Thread.sleep(10L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    synchronized (this.f2114e) {
                        remove = this.f2114e.remove(0);
                    }
                    if (remove != null) {
                        try {
                            allocateDirect.clear();
                            if (remove.length > allocateDirect.capacity()) {
                                allocateDirect = ByteBuffer.allocateDirect(remove.length);
                            }
                            allocateDirect.clear();
                            allocateDirect.put(remove);
                            allocateDirect.flip();
                            m3390a(allocateDirect, remove.length, m3383f());
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                }
            } else {
                synchronized (this.f2118i) {
                    try {
                        this.f2118i.wait();
                    } catch (InterruptedException e3) {
                        e3.printStackTrace();
                    }
                }
            }
        }
        m3384e();
    }

    /* renamed from: a */
    private void m3390a(ByteBuffer byteBuffer, int i, long j) {
        int dequeueOutputBuffer;
        if (this.f2117h) {
            return;
        }
        ByteBuffer[] inputBuffers = this.f2113d.getInputBuffers();
        int dequeueInputBuffer = this.f2113d.dequeueInputBuffer(10000L);
        if (dequeueInputBuffer >= 0) {
            ByteBuffer byteBuffer2 = inputBuffers[dequeueInputBuffer];
            byteBuffer2.clear();
            if (byteBuffer != null) {
                byteBuffer2.put(byteBuffer);
            }
            if (i <= 0) {
                TXCLog.m2913i("AudioCenter:TXCAudioHWEncoder", "send BUFFER_FLAG_END_OF_STREAM");
                this.f2113d.queueInputBuffer(dequeueInputBuffer, 0, 0, j, 4);
            } else {
                this.f2113d.queueInputBuffer(dequeueInputBuffer, 0, i, j, 0);
            }
        }
        ByteBuffer[] outputBuffers = this.f2113d.getOutputBuffers();
        do {
            dequeueOutputBuffer = this.f2113d.dequeueOutputBuffer(this.f2110a, 10000L);
            if (dequeueOutputBuffer != -1) {
                if (dequeueOutputBuffer == -3) {
                    outputBuffers = this.f2113d.getOutputBuffers();
                    continue;
                } else if (dequeueOutputBuffer == -2) {
                    this.f2113d.getOutputFormat();
                    continue;
                } else if (dequeueOutputBuffer < 0) {
                    continue;
                } else {
                    ByteBuffer byteBuffer3 = outputBuffers[dequeueOutputBuffer];
                    if ((this.f2110a.flags & 2) != 0) {
                        TXCLog.m2915d("AudioCenter:TXCAudioHWEncoder", "drain:BUFFER_FLAG_CODEC_CONFIG");
                        this.f2110a.size = 0;
                    }
                    MediaCodec.BufferInfo bufferInfo = this.f2110a;
                    if (bufferInfo.size != 0) {
                        bufferInfo.presentationTimeUs = m3383f();
                        this.f2123n = new byte[byteBuffer3.limit()];
                        byteBuffer3.get(this.f2123n);
                        m3387b(this.f2123n, this.f2110a.presentationTimeUs);
                        this.f2119j = this.f2110a.presentationTimeUs;
                    }
                    this.f2113d.releaseOutputBuffer(dequeueOutputBuffer, false);
                    continue;
                }
            }
        } while (dequeueOutputBuffer >= 0);
    }

    /* renamed from: f */
    private long m3383f() {
        long timeTick = TXCTimeUtil.getTimeTick();
        long j = this.f2119j;
        return timeTick < j ? timeTick + (j - timeTick) : timeTick;
    }

    /* renamed from: a */
    private static final MediaCodecInfo m3391a(String str) {
        TXCLog.m2912v("AudioCenter:TXCAudioHWEncoder", "selectAudioCodec:");
        int codecCount = MediaCodecList.getCodecCount();
        for (int i = 0; i < codecCount; i++) {
            MediaCodecInfo codecInfoAt = MediaCodecList.getCodecInfoAt(i);
            if (codecInfoAt.isEncoder()) {
                String[] supportedTypes = codecInfoAt.getSupportedTypes();
                for (int i2 = 0; i2 < supportedTypes.length; i2++) {
                    TXCLog.m2913i("AudioCenter:TXCAudioHWEncoder", "supportedType:" + codecInfoAt.getName() + ",MIME=" + supportedTypes[i2]);
                    if (supportedTypes[i2].equalsIgnoreCase(str)) {
                        return codecInfoAt;
                    }
                }
                continue;
            }
        }
        return null;
    }

    /* renamed from: b */
    private void m3387b(byte[] bArr, long j) {
        TXIAudioRecordListener tXIAudioRecordListener;
        WeakReference<TXIAudioRecordListener> weakReference = this.f2115f;
        if (weakReference == null || (tXIAudioRecordListener = weakReference.get()) == null) {
            return;
        }
        tXIAudioRecordListener.onRecordEncData(bArr, j, this.f2120k, this.f2121l, this.f2122m);
    }
}
