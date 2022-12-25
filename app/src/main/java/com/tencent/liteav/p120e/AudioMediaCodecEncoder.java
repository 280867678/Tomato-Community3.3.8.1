package com.tencent.liteav.p120e;

import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Build;
import android.view.Surface;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCThread;
import com.tencent.liteav.p118c.CutTimeConfig;
import com.tencent.liteav.p118c.ReverseConfig;
import com.tencent.liteav.p118c.VideoOutputConfig;
import com.tencent.liteav.p119d.Frame;
import com.tencent.liteav.videoediter.audio.BufferUtils;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;
import java.util.concurrent.LinkedBlockingDeque;

/* renamed from: com.tencent.liteav.e.a */
/* loaded from: classes3.dex */
public class AudioMediaCodecEncoder {

    /* renamed from: b */
    private TXIAudioEncoderListener f3479b;

    /* renamed from: c */
    private IAudioDecodeCallback f3480c;

    /* renamed from: d */
    private boolean f3481d;

    /* renamed from: e */
    private int f3482e;

    /* renamed from: f */
    private int f3483f;

    /* renamed from: h */
    private TreeSet<Long> f3485h;

    /* renamed from: i */
    private MediaCodec f3486i;

    /* renamed from: j */
    private Long f3487j;

    /* renamed from: l */
    private int f3489l;

    /* renamed from: m */
    private TXHAudioEncoderParam f3490m;

    /* renamed from: n */
    private ArrayList<Frame> f3491n;

    /* renamed from: p */
    private MediaFormat f3493p;

    /* renamed from: k */
    private final Object f3488k = new Object();

    /* renamed from: o */
    private long f3492o = -1;

    /* renamed from: q */
    private Runnable f3494q = new Runnable() { // from class: com.tencent.liteav.e.a.3
        @Override // java.lang.Runnable
        public void run() {
            if (!AudioMediaCodecEncoder.this.f3481d) {
                return;
            }
            AudioMediaCodecEncoder.this.m2267h();
        }
    };

    /* renamed from: a */
    private TXCThread f3478a = new TXCThread("HWAudioEncoder");

    /* renamed from: g */
    private LinkedBlockingDeque<Frame> f3484g = new LinkedBlockingDeque<>();

    /* renamed from: a */
    public void m2284a(TXIAudioEncoderListener tXIAudioEncoderListener) {
        this.f3479b = tXIAudioEncoderListener;
    }

    /* renamed from: a */
    public void m2285a(final TXHAudioEncoderParam tXHAudioEncoderParam) {
        this.f3492o = -1L;
        ArrayList<Frame> arrayList = this.f3491n;
        if (arrayList == null) {
            this.f3491n = new ArrayList<>();
        } else {
            arrayList.clear();
        }
        this.f3493p = m2273c(tXHAudioEncoderParam);
        synchronized (this) {
            this.f3478a.m2866a(new Runnable() { // from class: com.tencent.liteav.e.a.1
                @Override // java.lang.Runnable
                public void run() {
                    if (AudioMediaCodecEncoder.this.f3481d) {
                        return;
                    }
                    AudioMediaCodecEncoder.this.m2277b(tXHAudioEncoderParam);
                }
            });
        }
    }

    /* renamed from: a */
    public void m2292a() {
        synchronized (this) {
            this.f3478a.m2866a(new Runnable() { // from class: com.tencent.liteav.e.a.2
                @Override // java.lang.Runnable
                public void run() {
                    if (AudioMediaCodecEncoder.this.f3481d) {
                        AudioMediaCodecEncoder.this.m2270e();
                        AudioMediaCodecEncoder.this.f3478a.m2868a().removeCallbacksAndMessages(null);
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: b */
    public void m2277b(TXHAudioEncoderParam tXHAudioEncoderParam) {
        MediaCodecInfo m2283a;
        TXCLog.m2913i("AudioMediaCodecEncoder", "startAudioInner");
        if (Build.VERSION.SDK_INT < 16 || (m2283a = m2283a("audio/mp4a-latm")) == null || this.f3493p == null) {
            return;
        }
        try {
            this.f3486i = MediaCodec.createByCodecName(m2283a.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.f3486i.configure(this.f3493p, (Surface) null, (MediaCrypto) null, 1);
        this.f3486i.start();
        this.f3484g.clear();
        this.f3481d = true;
        this.f3478a.m2865a(this.f3494q, 1L);
        this.f3485h = new TreeSet<>();
        this.f3487j = 0L;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: e */
    public void m2270e() {
        TXCLog.m2913i("AudioMediaCodecEncoder", "stopInner");
        if (Build.VERSION.SDK_INT < 16) {
            return;
        }
        MediaCodec mediaCodec = this.f3486i;
        if (mediaCodec != null) {
            mediaCodec.stop();
            this.f3486i.release();
        }
        this.f3481d = false;
    }

    /* renamed from: b */
    public void m2280b() {
        this.f3478a = null;
    }

    /* renamed from: a */
    public void m2290a(Frame frame) {
        int i = this.f3490m.channelCount * 2048;
        if (frame.m2325g() <= i) {
            m2279b(frame);
            return;
        }
        synchronized (this.f3491n) {
            this.f3491n.add(frame);
        }
        while (true) {
            short[] m2291a = m2291a(i / 2);
            if (m2291a == null) {
                return;
            }
            Frame m2282a = m2282a(m2291a);
            if (m2282a != null) {
                m2279b(m2282a);
            }
        }
    }

    /* renamed from: c */
    public Frame m2276c() {
        Frame remove;
        synchronized (this.f3491n) {
            remove = this.f3491n.size() > 0 ? this.f3491n.remove(0) : null;
        }
        return remove;
    }

    /* renamed from: a */
    private short[] m2291a(int i) {
        short[] m2275c;
        Frame m2276c = m2276c();
        if (m2276c == null || (m2275c = m2275c(m2276c)) == null) {
            return null;
        }
        short[] copyOf = Arrays.copyOf(m2275c, i);
        int length = m2275c.length;
        if (length >= i) {
            if (length <= i) {
                return length == i ? m2275c(m2276c) : copyOf;
            }
            Frame m2282a = m2282a(Arrays.copyOfRange(m2275c, i, m2275c.length));
            synchronized (this.f3491n) {
                this.f3491n.add(m2282a);
            }
            return copyOf;
        }
        while (length < i) {
            Frame m2276c2 = m2276c();
            if (m2276c2 == null) {
                synchronized (this.f3491n) {
                    this.f3491n.add(m2276c);
                }
                return null;
            }
            short[] m2275c2 = m2275c(m2276c2);
            if (m2275c2.length + length > i) {
                short[] m2281a = m2281a(copyOf, length, m2275c2);
                if (m2281a != null) {
                    length += m2275c2.length - m2281a.length;
                    Frame m2282a2 = m2282a(m2281a);
                    synchronized (this.f3491n) {
                        this.f3491n.add(m2282a2);
                    }
                } else {
                    continue;
                }
            } else {
                m2281a(copyOf, length, m2275c2);
                length += m2275c2.length;
            }
        }
        return copyOf;
    }

    /* renamed from: c */
    private short[] m2275c(Frame frame) {
        return BufferUtils.m551a(frame.m2338b(), frame.m2325g());
    }

    /* renamed from: a */
    private Frame m2282a(short[] sArr) {
        if (sArr == null || sArr.length == 0) {
            return null;
        }
        Frame frame = new Frame();
        frame.m2341a(BufferUtils.m549a(sArr));
        frame.m2331d(sArr.length * 2);
        return frame;
    }

    /* renamed from: a */
    private short[] m2281a(short[] sArr, int i, short[] sArr2) {
        int i2 = 0;
        while (i2 < sArr2.length && i < sArr.length) {
            sArr[i] = sArr2[i2];
            i++;
            i2++;
        }
        if ((sArr2.length - i2) + 1 > 0) {
            return Arrays.copyOfRange(sArr2, i2, sArr2.length);
        }
        return null;
    }

    /* renamed from: f */
    private Long m2269f() {
        long j;
        int i = this.f3489l;
        if (i == 0) {
            j = this.f3492o;
        } else {
            j = this.f3492o + ((i * 1024000000) / this.f3490m.sampleRate);
        }
        this.f3489l++;
        return Long.valueOf(j);
    }

    /* renamed from: b */
    public void m2279b(Frame frame) {
        this.f3482e++;
        try {
            this.f3484g.put(frame);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (this.f3488k) {
            if (this.f3485h != null) {
                if (this.f3492o == -1) {
                    this.f3492o = frame.m2329e();
                }
                long longValue = m2269f().longValue();
                boolean m2432m = VideoOutputConfig.m2457a().m2432m();
                boolean m2476b = ReverseConfig.m2478a().m2476b();
                if (m2432m && !m2476b) {
                    longValue += CutTimeConfig.m2501a().m2494f();
                }
                this.f3485h.add(Long.valueOf(longValue));
            }
        }
        while (this.f3484g.size() > 0 && this.f3481d) {
            Frame m2268g = m2268g();
            if (m2268g == null) {
                IAudioDecodeCallback iAudioDecodeCallback = this.f3480c;
                if (iAudioDecodeCallback != null) {
                    iAudioDecodeCallback.mo1531a(this.f3484g.size());
                }
            } else {
                Frame frame2 = null;
                try {
                    frame2 = this.f3484g.take();
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
                if (frame2 != null) {
                    m2289a(m2268g, frame2);
                }
            }
        }
    }

    /* renamed from: g */
    private Frame m2268g() {
        int dequeueInputBuffer;
        ByteBuffer byteBuffer;
        if (Build.VERSION.SDK_INT >= 16 && (dequeueInputBuffer = this.f3486i.dequeueInputBuffer(10000L)) >= 0) {
            if (Build.VERSION.SDK_INT >= 21) {
                byteBuffer = this.f3486i.getInputBuffer(dequeueInputBuffer);
            } else {
                byteBuffer = this.f3486i.getInputBuffers()[dequeueInputBuffer];
            }
            ByteBuffer byteBuffer2 = byteBuffer;
            byteBuffer2.clear();
            return new Frame(byteBuffer2, 0, 0L, dequeueInputBuffer, 0, 0);
        }
        return null;
    }

    /* renamed from: a */
    private void m2289a(Frame frame, Frame frame2) {
        if (Build.VERSION.SDK_INT < 16) {
            return;
        }
        int m2332d = frame.m2332d();
        ByteBuffer m2338b = frame.m2338b();
        if (frame2.m2309p()) {
            this.f3486i.queueInputBuffer(m2332d, 0, 0, frame2.m2329e(), 4);
            return;
        }
        ByteBuffer duplicate = frame2.m2338b().duplicate();
        duplicate.rewind();
        duplicate.limit(frame2.m2325g());
        m2338b.rewind();
        if (frame2.m2325g() <= m2338b.remaining()) {
            m2338b.put(duplicate);
            this.f3486i.queueInputBuffer(m2332d, 0, frame2.m2325g(), frame2.m2329e(), 0);
            return;
        }
        TXCLog.m2914e("AudioMediaCodecEncoder", "input size is larger than buffer capacity. should increate buffer capacity by setting MediaFormat.KEY_MAX_INPUT_SIZE while configure. mime = ");
        throw new IllegalArgumentException("input size is larger than buffer capacity. should increate buffer capacity by setting MediaFormat.KEY_MAX_INPUT_SIZE while configure. mime = ");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: h */
    public void m2267h() {
        ByteBuffer byteBuffer;
        if (Build.VERSION.SDK_INT < 16) {
            return;
        }
        if (this.f3486i == null) {
            TXCLog.m2914e("AudioMediaCodecEncoder", "onDecodeOutput, mMediaCodec is null");
            TXCThread tXCThread = this.f3478a;
            if (tXCThread == null) {
                return;
            }
            tXCThread.m2865a(this.f3494q, 10L);
            return;
        }
        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        ByteBuffer[] outputBuffers = this.f3486i.getOutputBuffers();
        int dequeueOutputBuffer = this.f3486i.dequeueOutputBuffer(bufferInfo, 10000L);
        if (dequeueOutputBuffer == -1) {
            TXCThread tXCThread2 = this.f3478a;
            if (tXCThread2 == null) {
                return;
            }
            tXCThread2.m2864b(this.f3494q);
            return;
        }
        if (dequeueOutputBuffer == -3) {
            this.f3486i.getOutputBuffers();
        } else if (dequeueOutputBuffer == -2) {
            MediaFormat outputFormat = this.f3486i.getOutputFormat();
            TXIAudioEncoderListener tXIAudioEncoderListener = this.f3479b;
            if (tXIAudioEncoderListener != null) {
                tXIAudioEncoderListener.mo1529a(outputFormat);
            }
        } else if (dequeueOutputBuffer >= 0) {
            if (Build.VERSION.SDK_INT >= 21) {
                byteBuffer = this.f3486i.getOutputBuffer(dequeueOutputBuffer);
            } else {
                byteBuffer = outputBuffers[dequeueOutputBuffer];
            }
            if (byteBuffer == null) {
                throw new RuntimeException("encoderOutputBuffer " + dequeueOutputBuffer + " was null.mime:");
            }
            byte[] bArr = new byte[bufferInfo.size];
            byteBuffer.position(bufferInfo.offset);
            byteBuffer.limit(bufferInfo.offset + bufferInfo.size);
            byteBuffer.get(bArr, 0, bufferInfo.size);
            if ((bufferInfo.flags & 2) == 2) {
                bufferInfo.size = 0;
            }
            if (this.f3479b != null && bufferInfo.size != 0) {
                this.f3483f++;
                bufferInfo.presentationTimeUs = m2272d();
                MediaCodec.BufferInfo bufferInfo2 = new MediaCodec.BufferInfo();
                ByteBuffer wrap = ByteBuffer.wrap(bArr);
                bufferInfo2.set(bufferInfo.offset, bArr.length, bufferInfo.presentationTimeUs, bufferInfo.flags);
                this.f3479b.mo1528a(wrap, bufferInfo);
            }
            this.f3486i.releaseOutputBuffer(dequeueOutputBuffer, false);
            if ((bufferInfo.flags & 4) != 0) {
                TXIAudioEncoderListener tXIAudioEncoderListener2 = this.f3479b;
                if (tXIAudioEncoderListener2 == null) {
                    return;
                }
                tXIAudioEncoderListener2.mo1530a();
                return;
            }
        }
        TXCThread tXCThread3 = this.f3478a;
        if (tXCThread3 == null) {
            return;
        }
        tXCThread3.m2864b(this.f3494q);
    }

    /* renamed from: d */
    protected long m2272d() {
        synchronized (this.f3488k) {
            if (!this.f3485h.isEmpty()) {
                this.f3487j = this.f3485h.pollFirst();
                return this.f3487j.longValue();
            }
            this.f3487j = Long.valueOf(this.f3487j.longValue() + 100);
            return this.f3487j.longValue();
        }
    }

    /* renamed from: c */
    private MediaFormat m2273c(TXHAudioEncoderParam tXHAudioEncoderParam) {
        this.f3490m = tXHAudioEncoderParam;
        if (Build.VERSION.SDK_INT >= 16) {
            MediaFormat createAudioFormat = MediaFormat.createAudioFormat("audio/mp4a-latm", tXHAudioEncoderParam.sampleRate, tXHAudioEncoderParam.channelCount);
            createAudioFormat.setInteger("bitrate", tXHAudioEncoderParam.audioBitrate);
            createAudioFormat.setInteger("aac-profile", 2);
            int i = tXHAudioEncoderParam.channelCount * 1024 * 2;
            if (i <= 102400) {
                i = 102400;
            }
            createAudioFormat.setInteger("max-input-size", i);
            return createAudioFormat;
        }
        return null;
    }

    /* renamed from: a */
    private static MediaCodecInfo m2283a(String str) {
        if (Build.VERSION.SDK_INT >= 16) {
            int codecCount = MediaCodecList.getCodecCount();
            for (int i = 0; i < codecCount; i++) {
                MediaCodecInfo codecInfoAt = MediaCodecList.getCodecInfoAt(i);
                if (codecInfoAt.isEncoder()) {
                    for (String str2 : codecInfoAt.getSupportedTypes()) {
                        if (str2.equalsIgnoreCase(str)) {
                            return codecInfoAt;
                        }
                    }
                    continue;
                }
            }
            return null;
        }
        return null;
    }

    /* renamed from: a */
    public void m2286a(IAudioDecodeCallback iAudioDecodeCallback) {
        TXCLog.m2913i("AudioMediaCodecEncoder", "setPCMQueueCallback listener:" + iAudioDecodeCallback);
        this.f3480c = iAudioDecodeCallback;
    }
}
