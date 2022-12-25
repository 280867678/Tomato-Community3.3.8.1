package com.tencent.liteav.p104b;

import android.annotation.TargetApi;
import android.media.MediaFormat;
import android.util.Log;
import com.tencent.liteav.p119d.Frame;
import com.tencent.liteav.videoediter.audio.BufferUtils;
import com.tencent.liteav.videoediter.audio.TXChannelResample;
import com.tencent.liteav.videoediter.audio.TXSkpResample;

@TargetApi(16)
/* renamed from: com.tencent.liteav.b.b */
/* loaded from: classes3.dex */
public class TXCombineAudioMixer {

    /* renamed from: c */
    private int f2177c;

    /* renamed from: d */
    private MediaFormat f2178d;

    /* renamed from: e */
    private MediaFormat f2179e;

    /* renamed from: f */
    private TXChannelResample f2180f;

    /* renamed from: g */
    private TXSkpResample f2181g;

    /* renamed from: h */
    private TXChannelResample f2182h;

    /* renamed from: i */
    private TXSkpResample f2183i;

    /* renamed from: a */
    private volatile float f2175a = 1.0f;

    /* renamed from: b */
    private volatile float f2176b = 1.0f;

    /* renamed from: j */
    private short[] f2184j = null;

    /* renamed from: a */
    public void m3324a(int i) {
        this.f2177c = i;
    }

    /* renamed from: a */
    public void m3323a(MediaFormat mediaFormat) {
        this.f2178d = mediaFormat;
    }

    /* renamed from: b */
    public void m3318b(MediaFormat mediaFormat) {
        this.f2179e = mediaFormat;
    }

    /* renamed from: a */
    public void m3325a() {
        int integer;
        int integer2;
        int integer3;
        int integer4;
        MediaFormat mediaFormat = this.f2178d;
        if (mediaFormat == null || this.f2179e == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("AudioFormat1 :");
            MediaFormat mediaFormat2 = this.f2178d;
            if (mediaFormat2 == null) {
                mediaFormat2 = "not set!!!";
            }
            sb.append(mediaFormat2);
            sb.append(",AudioFormat2:");
            MediaFormat mediaFormat3 = this.f2179e;
            if (mediaFormat3 == null) {
                mediaFormat3 = "not set!!!";
            }
            sb.append(mediaFormat3);
            Log.e("TXCombineAudioMixer", sb.toString());
        } else if (this.f2177c == 0) {
            Log.e("TXCombineAudioMixer", "Target Audio SampleRate is not set!!!");
        } else {
            if (mediaFormat.containsKey("channel-count") && (integer4 = this.f2178d.getInteger("channel-count")) != 1) {
                this.f2180f = new TXChannelResample();
                this.f2180f.m507a(integer4, 1);
            }
            if (this.f2178d.containsKey("sample-rate") && (integer3 = this.f2178d.getInteger("sample-rate")) != this.f2177c) {
                this.f2181g = new TXSkpResample();
                this.f2181g.init(integer3, this.f2177c);
            }
            if (this.f2179e.containsKey("channel-count") && (integer2 = this.f2179e.getInteger("channel-count")) != 1) {
                this.f2182h = new TXChannelResample();
                this.f2182h.m507a(integer2, 1);
            }
            if (!this.f2179e.containsKey("sample-rate") || (integer = this.f2179e.getInteger("sample-rate")) == this.f2177c) {
                return;
            }
            this.f2183i = new TXSkpResample();
            this.f2183i.init(integer, this.f2177c);
        }
    }

    /* renamed from: b */
    public void m3319b() {
        this.f2184j = null;
    }

    /* renamed from: a */
    public Frame m3322a(Frame frame, Frame frame2) {
        short[] m551a = BufferUtils.m551a(frame.m2338b(), frame.m2325g());
        TXChannelResample tXChannelResample = this.f2180f;
        if (tXChannelResample != null) {
            m551a = tXChannelResample.m506a(m551a);
        }
        TXSkpResample tXSkpResample = this.f2181g;
        if (tXSkpResample != null) {
            m551a = tXSkpResample.doResample(m551a);
        }
        short[] m551a2 = BufferUtils.m551a(frame2.m2338b(), frame2.m2325g());
        TXChannelResample tXChannelResample2 = this.f2182h;
        if (tXChannelResample2 != null) {
            m551a2 = tXChannelResample2.m506a(m551a2);
        }
        TXSkpResample tXSkpResample2 = this.f2183i;
        if (tXSkpResample2 != null) {
            m551a2 = tXSkpResample2.doResample(m551a2);
        }
        if (m551a.length == m551a2.length) {
            short[] m3317b = m3317b(m551a, m551a2);
            frame.m2341a(BufferUtils.m550a(frame.m2338b(), m3317b));
            frame.m2331d(m3317b.length * 2);
            return frame;
        } else if (m551a.length > m551a2.length) {
            short[] m3320a = m3320a(this.f2184j, m551a);
            short[] m3317b2 = m3317b(m3320a, m551a2);
            this.f2184j = m3321a(m3320a, m551a2.length, m3320a.length - m551a2.length);
            frame2.m2341a(BufferUtils.m550a(frame2.m2338b(), m3317b2));
            frame2.m2331d(m3317b2.length * 2);
            return frame2;
        } else {
            short[] m3320a2 = m3320a(this.f2184j, m551a2);
            short[] m3317b3 = m3317b(m3320a2, m551a);
            this.f2184j = m3321a(m3320a2, m551a.length, m3320a2.length - m551a.length);
            frame.m2341a(BufferUtils.m550a(frame.m2338b(), m3317b3));
            frame.m2331d(m3317b3.length * 2);
            return frame;
        }
    }

    /* renamed from: a */
    private short[] m3320a(short[] sArr, short[] sArr2) {
        if (sArr == null || sArr.length == 0) {
            return sArr2;
        }
        short[] sArr3 = new short[sArr.length + sArr2.length];
        System.arraycopy(sArr, 0, sArr3, 0, sArr.length);
        System.arraycopy(sArr2, 0, sArr3, sArr.length, sArr2.length);
        return sArr3;
    }

    /* renamed from: a */
    private short[] m3321a(short[] sArr, int i, int i2) {
        short[] sArr2 = new short[i2];
        System.arraycopy(sArr, i, sArr2, 0, i2);
        return sArr2;
    }

    /* renamed from: b */
    private short[] m3317b(short[] sArr, short[] sArr2) {
        int length;
        short[] sArr3;
        if (sArr.length > sArr2.length) {
            length = sArr2.length;
            sArr3 = sArr2;
        } else {
            length = sArr.length;
            sArr3 = sArr;
        }
        for (int i = 0; i < length; i++) {
            int i2 = (int) ((sArr[i] * this.f2176b) + (sArr2[i] * this.f2175a));
            short s = Short.MIN_VALUE;
            if (i2 > 32767) {
                s = Short.MAX_VALUE;
            } else if (i2 >= -32768) {
                s = (short) i2;
            }
            sArr3[i] = s;
        }
        return sArr3;
    }
}
