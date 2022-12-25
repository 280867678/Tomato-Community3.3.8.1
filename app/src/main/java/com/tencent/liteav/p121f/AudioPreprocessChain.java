package com.tencent.liteav.p121f;

import android.media.MediaFormat;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.one.tomato.entity.C2516Ad;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.p118c.BgmConfig;
import com.tencent.liteav.p119d.AudioFormat;
import com.tencent.liteav.p119d.Frame;
import com.tencent.liteav.p120e.IAudioPreprocessListener;
import com.tencent.liteav.videoediter.audio.BufferUtils;
import com.tencent.liteav.videoediter.audio.TXAudioMixer;
import com.tencent.liteav.videoediter.audio.TXAudioVolumer;
import com.tencent.liteav.videoediter.audio.TXChannelResample;
import com.tencent.liteav.videoediter.audio.TXJNIAudioResampler;
import com.tomatolive.library.utils.ConstantUtils;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: com.tencent.liteav.f.b */
/* loaded from: classes3.dex */
public class AudioPreprocessChain {

    /* renamed from: a */
    public Frame f3815a;

    /* renamed from: c */
    private TXAudioVolumer f3817c;

    /* renamed from: d */
    private TXChannelResample f3818d;

    /* renamed from: e */
    private TXJNIAudioResampler f3819e;

    /* renamed from: f */
    private TXAudioMixer f3820f;

    /* renamed from: g */
    private IAudioPreprocessListener f3821g;

    /* renamed from: h */
    private int f3822h;

    /* renamed from: i */
    private int f3823i;

    /* renamed from: o */
    private AudioFormat f3829o;

    /* renamed from: p */
    private float f3830p;

    /* renamed from: s */
    private HandlerThread f3833s;

    /* renamed from: t */
    private HandlerC3468a f3834t;

    /* renamed from: x */
    private long f3838x;

    /* renamed from: y */
    private double f3839y;

    /* renamed from: z */
    private double f3840z;

    /* renamed from: b */
    private final String f3816b = "AudioPreprocessChain";

    /* renamed from: k */
    private long f3825k = -1;

    /* renamed from: l */
    private long f3826l = -1;

    /* renamed from: m */
    private int f3827m = 0;

    /* renamed from: w */
    private Object f3837w = new Object();

    /* renamed from: j */
    private LinkedList<Long> f3824j = new LinkedList<>();

    /* renamed from: n */
    private BgmConfig f3828n = BgmConfig.m2505a();

    /* renamed from: q */
    private SpeedFilterChain f3831q = SpeedFilterChain.m1849a();

    /* renamed from: u */
    private AtomicBoolean f3835u = new AtomicBoolean(false);

    /* renamed from: v */
    private final AtomicBoolean f3836v = new AtomicBoolean(false);

    /* renamed from: r */
    private boolean f3832r = true;

    /* renamed from: a */
    public void m1927a() {
        TXCLog.m2915d("AudioPreprocessChain", "initFilter");
        this.f3819e = new TXJNIAudioResampler();
        this.f3818d = new TXChannelResample();
        synchronized (this.f3837w) {
            this.f3820f = new TXAudioMixer();
        }
        this.f3817c = new TXAudioVolumer();
        this.f3830p = 1.0f;
        this.f3819e.setSpeed(this.f3830p);
    }

    /* renamed from: b */
    public void m1911b() {
        TXCLog.m2915d("AudioPreprocessChain", "destroyFilter");
        this.f3825k = -1L;
        this.f3826l = -1L;
        this.f3827m = 0;
        TXJNIAudioResampler tXJNIAudioResampler = this.f3819e;
        if (tXJNIAudioResampler != null) {
            tXJNIAudioResampler.destroy();
            this.f3819e = null;
        }
        synchronized (this.f3837w) {
            if (this.f3820f != null) {
                this.f3820f.m530d();
                this.f3820f = null;
            }
        }
        if (this.f3818d != null) {
            this.f3818d = null;
        }
        LinkedList<Long> linkedList = this.f3824j;
        if (linkedList != null) {
            linkedList.clear();
        }
    }

    /* renamed from: a */
    public void m1922a(MediaFormat mediaFormat) {
        if (mediaFormat == null) {
            TXCLog.m2914e("AudioPreprocessChain", "setAudioFormat audioFormat is null");
            return;
        }
        this.f3829o = new AudioFormat();
        if (Build.VERSION.SDK_INT >= 16) {
            this.f3829o.f3455b = mediaFormat.getInteger("sample-rate");
            this.f3829o.f3454a = mediaFormat.getInteger("channel-count");
            TXCLog.m2913i("AudioPreprocessChain", "setAudioFormat sampleRate:" + this.f3829o.f3455b);
            TXCLog.m2913i("AudioPreprocessChain", "setAudioFormat channelCount:" + this.f3829o.f3454a);
        }
        if (this.f3822h != 0 && this.f3823i != 0) {
            this.f3819e.setChannelCount(this.f3829o.f3454a);
            this.f3818d.m507a(this.f3822h, this.f3829o.f3454a);
            this.f3819e.setSampleRate(this.f3823i, this.f3829o.f3455b);
        }
        TXAudioMixer tXAudioMixer = this.f3820f;
        if (tXAudioMixer == null) {
            return;
        }
        tXAudioMixer.m544a(mediaFormat);
    }

    /* renamed from: c */
    public void m1903c() {
        TXCLog.m2913i("AudioPreprocessChain", C2516Ad.TYPE_START);
        if (TextUtils.isEmpty(this.f3828n.f3327a)) {
            this.f3828n.f3334h = false;
            return;
        }
        this.f3828n.f3334h = true;
        this.f3835u.set(true);
        m1915a(this.f3828n.f3327a);
        BgmConfig bgmConfig = this.f3828n;
        long j = bgmConfig.f3328b;
        if (j != -1) {
            long j2 = bgmConfig.f3329c;
            if (j2 != -1) {
                m1923a(j, j2);
            }
        }
        m1912a(this.f3828n.f3331e);
        m1926a(this.f3828n.f3332f);
        m1910b(this.f3828n.f3333g);
        m1924a(this.f3828n.f3330d);
        this.f3839y = 0.0d;
        if (!this.f3828n.f3336j) {
            return;
        }
        m1888k();
    }

    /* renamed from: k */
    private void m1888k() {
        BgmConfig bgmConfig = this.f3828n;
        if (bgmConfig.f3338l <= 0) {
            return;
        }
        if (bgmConfig.f3331e) {
            this.f3840z = this.f3838x / 1000000.0d;
            TXCLog.m2913i("AudioPreprocessChain", "getBgmEndTimePts, is loop, mBgmEndTimeSec = " + this.f3840z);
            return;
        }
        long j = (bgmConfig.f3329c * 1000) - (bgmConfig.f3328b * 1000);
        long j2 = this.f3838x;
        if (j2 > j) {
            j2 = j;
        }
        this.f3840z = j2 / 1000000.0d;
        TXCLog.m2913i("AudioPreprocessChain", "getBgmEndTimePts, not loop, mVideoDurationUs = " + this.f3838x + ", bgmDurationUs = " + j + ", so mBgmEndTimeSec = " + this.f3840z);
    }

    /* renamed from: d */
    public void m1899d() {
        TXCLog.m2913i("AudioPreprocessChain", "stop");
        if (!this.f3832r) {
            HandlerC3468a handlerC3468a = this.f3834t;
            if (handlerC3468a != null) {
                handlerC3468a.removeCallbacksAndMessages(null);
                this.f3833s.quit();
            }
            this.f3836v.set(true);
            this.f3833s = null;
            this.f3834t = null;
        }
    }

    /* renamed from: e */
    public int m1897e() {
        if (!this.f3832r) {
            if (this.f3833s == null) {
                this.f3833s = new HandlerThread("bgm_handler_thread");
                this.f3833s.start();
                this.f3834t = new HandlerC3468a(this.f3833s.getLooper());
            }
            this.f3836v.set(false);
            this.f3834t.sendEmptyMessage(ConstantUtils.MAX_ITEM_NUM);
            return 0;
        }
        TXCLog.m2911w("AudioPreprocessChain", "tryStartAddBgmForNoAudioTrack, this has audio track, ignore!");
        return -1;
    }

    /* renamed from: f */
    public void m1895f() {
        TXCLog.m2913i("AudioPreprocessChain", "pause");
        this.f3835u.set(false);
    }

    /* renamed from: g */
    public void m1893g() {
        TXCLog.m2913i("AudioPreprocessChain", "resume");
        this.f3835u.set(true);
    }

    /* renamed from: a */
    public int m1915a(String str) {
        int i;
        try {
            i = this.f3820f.m541a(str);
        } catch (IOException e) {
            e.printStackTrace();
            i = -1;
        }
        this.f3820f.m548a();
        if (!TextUtils.isEmpty(str)) {
            this.f3828n.f3334h = true;
        } else {
            this.f3828n.f3334h = false;
        }
        return i;
    }

    /* renamed from: a */
    public void m1923a(long j, long j2) {
        TXAudioMixer tXAudioMixer = this.f3820f;
        if (tXAudioMixer != null) {
            tXAudioMixer.m545a(j, j2);
        }
    }

    /* renamed from: a */
    public void m1912a(boolean z) {
        TXAudioMixer tXAudioMixer = this.f3820f;
        if (tXAudioMixer != null) {
            tXAudioMixer.m540a(z);
        }
    }

    /* renamed from: a */
    public void m1924a(long j) {
        this.f3828n.f3330d = j;
    }

    /* renamed from: a */
    public void m1926a(float f) {
        TXAudioMixer tXAudioMixer = this.f3820f;
        if (tXAudioMixer != null) {
            tXAudioMixer.m536b(f);
        }
    }

    /* renamed from: b */
    public void m1910b(float f) {
        TXAudioMixer tXAudioMixer = this.f3820f;
        if (tXAudioMixer != null) {
            tXAudioMixer.m547a(f);
        }
    }

    /* renamed from: b */
    public void m1908b(long j) {
        this.f3838x = j;
    }

    /* renamed from: h */
    public MediaFormat m1891h() {
        return this.f3820f.m528e();
    }

    /* renamed from: a */
    public void m1920a(IAudioPreprocessListener iAudioPreprocessListener) {
        this.f3821g = iAudioPreprocessListener;
    }

    /* renamed from: a */
    public void m1921a(Frame frame) {
        if (frame == null) {
            TXCLog.m2914e("AudioPreprocessChain", "processFrame, frame is null");
        } else if (frame.m2308q() || frame.m2307r()) {
            TXCLog.m2913i("AudioPreprocessChain", "processFrame, frame is isUnNormallFrame");
            IAudioPreprocessListener iAudioPreprocessListener = this.f3821g;
            if (iAudioPreprocessListener == null) {
                return;
            }
            iAudioPreprocessListener.mo1486a(frame);
        } else if (frame.m2309p()) {
            TXCLog.m2914e("AudioPreprocessChain", "processFrame, frame is end");
            IAudioPreprocessListener iAudioPreprocessListener2 = this.f3821g;
            if (iAudioPreprocessListener2 == null) {
                return;
            }
            iAudioPreprocessListener2.mo1486a(frame);
        } else {
            if (!this.f3831q.m1843c() && this.f3823i == this.f3829o.f3455b) {
                this.f3824j.add(Long.valueOf(frame.m2329e()));
            } else {
                this.f3830p = this.f3831q.m1847a(frame.m2329e());
                this.f3819e.setSpeed(this.f3830p);
                if (this.f3825k == -1) {
                    this.f3825k = frame.m2329e();
                }
                this.f3824j.add(m1887l());
            }
            Frame m1914a = m1914a(frame.m2338b(), m1907b(frame));
            IAudioPreprocessListener iAudioPreprocessListener3 = this.f3821g;
            if (iAudioPreprocessListener3 == null) {
                return;
            }
            iAudioPreprocessListener3.mo1486a(m1914a);
        }
    }

    /* renamed from: i */
    public void m1890i() {
        IAudioPreprocessListener iAudioPreprocessListener;
        TXJNIAudioResampler tXJNIAudioResampler = this.f3819e;
        if (tXJNIAudioResampler != null) {
            short[] flushBuffer = tXJNIAudioResampler.flushBuffer();
            int i = this.f3823i;
            AudioFormat audioFormat = this.f3829o;
            if (i != audioFormat.f3455b && audioFormat.f3454a == 2 && flushBuffer != null) {
                this.f3818d.m507a(1, 2);
                flushBuffer = this.f3818d.m506a(flushBuffer);
            }
            Frame frame = null;
            if (flushBuffer != null && flushBuffer.length > 0) {
                this.f3824j.add(Long.valueOf(m1887l().longValue()));
                frame = m1914a((ByteBuffer) null, flushBuffer);
            }
            if (frame == null || (iAudioPreprocessListener = this.f3821g) == null) {
                return;
            }
            iAudioPreprocessListener.mo1486a(frame);
        }
    }

    /* renamed from: l */
    private Long m1887l() {
        long j;
        int i = this.f3827m;
        if (i == 0) {
            j = this.f3825k;
        } else {
            j = this.f3825k + ((i * 1024000000) / this.f3829o.f3455b);
        }
        this.f3827m++;
        return Long.valueOf(j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public long m1925a(int i) {
        long j = this.f3826l;
        if (j == -1) {
            j = this.f3825k;
        }
        AudioFormat audioFormat = this.f3829o;
        this.f3826l = ((i * 1000000) / ((audioFormat.f3455b * audioFormat.f3454a) * 2)) + j;
        return j;
    }

    /* renamed from: a */
    private Frame m1914a(ByteBuffer byteBuffer, short[] sArr) {
        short[] m509a;
        if (sArr != null && sArr.length != 0) {
            LinkedList<Long> linkedList = this.f3824j;
            if (linkedList == null || linkedList.size() == 0) {
                TXCLog.m2914e("AudioPreprocessChain", "doMixer mTimeQueue:" + this.f3824j);
            } else {
                long longValue = this.f3824j.pollFirst().longValue();
                BgmConfig bgmConfig = this.f3828n;
                if (bgmConfig.f3334h) {
                    if (longValue >= bgmConfig.f3330d) {
                        this.f3820f.m547a(m1909b(sArr.length * 2));
                        m509a = this.f3820f.m539a(sArr);
                    } else {
                        this.f3817c.m510a(bgmConfig.f3332f);
                        m509a = this.f3817c.m509a(sArr);
                    }
                    return m1913a(byteBuffer, m509a, longValue);
                }
                this.f3817c.m510a(bgmConfig.f3332f);
                return m1913a(byteBuffer, this.f3817c.m509a(sArr), longValue);
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0088  */
    /* renamed from: b */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public float m1909b(int i) {
        double d;
        double log10;
        double log102;
        BgmConfig bgmConfig = this.f3828n;
        if (!bgmConfig.f3336j) {
            return bgmConfig.f3333g;
        }
        double d2 = this.f3839y;
        double d3 = i;
        AudioFormat audioFormat = this.f3829o;
        this.f3839y = d2 + (d3 / ((audioFormat.f3455b * audioFormat.f3454a) * 2.0d));
        long j = bgmConfig.f3337k;
        float f = ((float) j) / 1000.0f;
        float f2 = ((float) bgmConfig.f3338l) / 1000.0f;
        if (j > 0 && this.f3839y <= f) {
            double pow = Math.pow(10.0d, Math.log10(f + 1.0f) / 1.0d);
            log10 = Math.log10(this.f3839y + 1.0d);
            log102 = Math.log10(pow);
        } else if (this.f3828n.f3338l > 0 && this.f3839y >= this.f3840z - f2) {
            double pow2 = Math.pow(10.0d, Math.log10(f2 + 1.0f) / 1.0d);
            log10 = Math.log10((this.f3840z + 1.0d) - this.f3839y);
            log102 = Math.log10(pow2);
        } else {
            d = 1.0d;
            if (d >= 0.0d) {
                d = 0.0d;
            } else if (d > 1.0d) {
                d = 1.0d;
            }
            return (float) (this.f3828n.f3333g * d);
        }
        d = log10 / log102;
        if (d >= 0.0d) {
        }
        return (float) (this.f3828n.f3333g * d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public Frame m1913a(ByteBuffer byteBuffer, short[] sArr, long j) {
        ByteBuffer m550a = BufferUtils.m550a(byteBuffer, sArr);
        Frame frame = new Frame();
        frame.m2331d(sArr.length * 2);
        frame.m2341a(m550a);
        frame.m2322h(this.f3829o.f3454a);
        frame.m2324g(this.f3829o.f3455b);
        frame.m2336b(j);
        frame.m2343a(j);
        return frame;
    }

    /* renamed from: b */
    private short[] m1907b(Frame frame) {
        m1902c(frame);
        short[] m551a = BufferUtils.m551a(frame.m2338b(), frame.m2325g());
        int i = this.f3823i;
        AudioFormat audioFormat = this.f3829o;
        if (i != audioFormat.f3455b && audioFormat.f3454a == 2) {
            if (this.f3822h == 2) {
                this.f3818d.m507a(2, 1);
                if (m551a != null) {
                    m551a = this.f3818d.m506a(m551a);
                }
            }
            if (this.f3830p != 1.0f || this.f3823i != this.f3829o.f3455b) {
                m551a = this.f3819e.resample(m551a);
            }
            if (m551a == null) {
                return m551a;
            }
            this.f3818d.m507a(1, 2);
            return this.f3818d.m506a(m551a);
        }
        if (this.f3822h != this.f3829o.f3454a) {
            m551a = this.f3818d.m506a(m551a);
        }
        return (this.f3830p == 1.0f && this.f3823i == this.f3829o.f3455b) ? m551a : this.f3819e.resample(m551a);
    }

    /* renamed from: c */
    private void m1902c(Frame frame) {
        if (this.f3822h != frame.m2317k()) {
            this.f3822h = frame.m2317k();
            TXCLog.m2913i("AudioPreprocessChain", "setAudioFormat initResampler setChannelCount");
            this.f3819e.setChannelCount(this.f3829o.f3454a);
            this.f3818d.m507a(this.f3822h, this.f3829o.f3454a);
        }
        if (this.f3823i != frame.m2319j()) {
            this.f3823i = frame.m2319j();
            TXCLog.m2913i("AudioPreprocessChain", "setAudioFormat initResampler setSampleRate");
            this.f3819e.setSampleRate(this.f3823i, this.f3829o.f3455b);
        }
    }

    /* renamed from: b */
    public void m1904b(boolean z) {
        this.f3832r = z;
    }

    /* renamed from: c */
    public void m1900c(boolean z) {
        this.f3835u.set(z);
    }

    /* renamed from: j */
    public boolean m1889j() {
        return this.f3836v.get();
    }

    /* compiled from: AudioPreprocessChain.java */
    /* renamed from: com.tencent.liteav.f.b$a */
    /* loaded from: classes3.dex */
    class HandlerC3468a extends Handler {
        public HandlerC3468a(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 10000) {
                return;
            }
            m1886a();
        }

        /* renamed from: a */
        private void m1886a() {
            Frame m532c;
            boolean m537b;
            AudioPreprocessChain audioPreprocessChain;
            Frame frame;
            if (AudioPreprocessChain.this.f3836v.get()) {
                return;
            }
            if (AudioPreprocessChain.this.f3835u.get()) {
                synchronized (AudioPreprocessChain.this.f3837w) {
                    m532c = AudioPreprocessChain.this.f3820f.m532c();
                    m537b = AudioPreprocessChain.this.f3820f.m537b();
                }
                if (m532c == null && m537b) {
                    AudioPreprocessChain.this.f3836v.set(true);
                    Frame m1885b = m1885b();
                    if (AudioPreprocessChain.this.f3821g != null) {
                        AudioPreprocessChain.this.f3821g.mo1486a(m1885b);
                        return;
                    }
                }
                if (m532c != null && m532c.m2338b() != null) {
                    float m1909b = AudioPreprocessChain.this.m1909b(m532c.m2325g());
                    TXCLog.m2913i("AudioPreprocessChain", "BgmHandler, bgmVolume = " + m1909b);
                    if (m1909b != 1.0f) {
                        AudioPreprocessChain.this.f3817c.m510a(m1909b);
                        m532c = AudioPreprocessChain.this.m1913a(m532c.m2338b(), AudioPreprocessChain.this.f3817c.m509a(BufferUtils.m551a(m532c.m2338b(), m532c.m2325g())), m532c.m2329e());
                    }
                    long m1925a = AudioPreprocessChain.this.m1925a(m532c.m2325g());
                    if (m1925a == -1) {
                        m1925a = 0;
                    }
                    m532c.m2343a(m1925a);
                    TXCLog.m2913i("AudioPreprocessChain", "BgmHandler pts:" + m1925a + ", duration:" + AudioPreprocessChain.this.f3838x);
                    if (AudioPreprocessChain.this.f3838x == 0 && (frame = (audioPreprocessChain = AudioPreprocessChain.this).f3815a) != null) {
                        audioPreprocessChain.f3838x = frame.m2329e();
                    }
                    if (m1925a >= AudioPreprocessChain.this.f3838x) {
                        AudioPreprocessChain.this.f3836v.set(true);
                        Frame m1885b2 = m1885b();
                        if (AudioPreprocessChain.this.f3821g != null) {
                            AudioPreprocessChain.this.f3821g.mo1486a(m1885b2);
                            return;
                        }
                    }
                    m532c.m2343a(m1925a);
                    if (AudioPreprocessChain.this.f3821g != null) {
                        AudioPreprocessChain.this.f3821g.mo1486a(m532c);
                    }
                }
                sendEmptyMessageDelayed(ConstantUtils.MAX_ITEM_NUM, 10L);
                return;
            }
            sendEmptyMessageDelayed(ConstantUtils.MAX_ITEM_NUM, 10L);
        }

        /* renamed from: b */
        private Frame m1885b() {
            Frame frame = new Frame();
            frame.m2331d(0);
            frame.m2343a(0L);
            frame.m2334c(4);
            return frame;
        }
    }
}
