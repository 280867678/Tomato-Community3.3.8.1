package com.tencent.liteav.videoediter.audio;

import android.annotation.TargetApi;
import android.media.MediaFormat;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.Surface;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.p119d.Frame;
import com.tencent.liteav.p122g.MediaExtractorWrapper;
import com.tencent.liteav.p122g.TXAudioDecoderWrapper;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@TargetApi(18)
/* renamed from: com.tencent.liteav.videoediter.audio.c */
/* loaded from: classes3.dex */
public class TXAudioMixer {

    /* renamed from: a */
    public static String[] f5489a = {"audio/mp4a-latm", "audio/mpeg"};

    /* renamed from: b */
    private static final String f5490b = "c";

    /* renamed from: d */
    private MediaFormat f5493d;

    /* renamed from: e */
    private MediaExtractorWrapper f5494e;

    /* renamed from: f */
    private MediaFormat f5495f;

    /* renamed from: g */
    private String f5496g;

    /* renamed from: j */
    private int f5499j;

    /* renamed from: k */
    private int f5500k;

    /* renamed from: l */
    private int f5501l;

    /* renamed from: m */
    private int f5502m;

    /* renamed from: n */
    private List<Frame> f5503n;

    /* renamed from: o */
    private C3664a f5504o;

    /* renamed from: p */
    private TXAudioDecoderWrapper f5505p;

    /* renamed from: v */
    private TXChannelResample f5511v;

    /* renamed from: w */
    private TXSkpResample f5512w;

    /* renamed from: y */
    private Frame f5514y;

    /* renamed from: h */
    private volatile long f5497h = -1;

    /* renamed from: i */
    private volatile long f5498i = -1;

    /* renamed from: z */
    private Handler f5515z = new Handler(Looper.getMainLooper());

    /* renamed from: A */
    private boolean f5491A = true;

    /* renamed from: q */
    private AtomicBoolean f5506q = new AtomicBoolean(false);

    /* renamed from: r */
    private AtomicBoolean f5507r = new AtomicBoolean(false);

    /* renamed from: c */
    private AtomicInteger f5492c = new AtomicInteger(-1);

    /* renamed from: s */
    private ReentrantLock f5508s = new ReentrantLock();

    /* renamed from: t */
    private Condition f5509t = this.f5508s.newCondition();

    /* renamed from: u */
    private Condition f5510u = this.f5508s.newCondition();

    /* renamed from: x */
    private TXMixerHelper f5513x = new TXMixerHelper();

    public TXAudioMixer() {
        this.f5513x.m503a(1.0f);
    }

    /* renamed from: a */
    public void m547a(float f) {
        this.f5513x.m503a(f);
    }

    /* renamed from: a */
    public int m541a(String str) throws IOException {
        String str2 = this.f5496g;
        if (str2 != null && !str2.equals(str)) {
            this.f5497h = -1L;
            this.f5498i = -1L;
        }
        if (TextUtils.isEmpty(str)) {
            m530d();
            this.f5495f = null;
            return 0;
        }
        if (this.f5492c.get() == 0 || this.f5492c.get() == 1) {
            m530d();
        }
        this.f5496g = str;
        return m525g();
    }

    /* renamed from: a */
    public void m545a(long j, long j2) {
        this.f5497h = j * 1000;
        this.f5498i = j2 * 1000;
        MediaExtractorWrapper mediaExtractorWrapper = this.f5494e;
        if (mediaExtractorWrapper != null) {
            mediaExtractorWrapper.m1739c(this.f5497h);
        }
        String str = f5490b;
        Log.d(str, "bgm startTime :" + this.f5497h + ",bgm endTime:" + this.f5498i);
    }

    /* renamed from: b */
    public void m536b(float f) {
        this.f5513x.m500b(f);
    }

    /* renamed from: a */
    public void m544a(MediaFormat mediaFormat) {
        if (mediaFormat == null) {
            TXCLog.m2914e(f5490b, "target media format can't be null");
            return;
        }
        this.f5493d = mediaFormat;
        this.f5500k = this.f5493d.getInteger("channel-count");
        this.f5499j = this.f5493d.getInteger("sample-rate");
        m517o();
    }

    /* renamed from: g */
    private int m525g() throws IOException {
        boolean z;
        this.f5492c.getAndSet(0);
        m516p();
        String string = this.f5495f.getString("mime");
        String[] strArr = f5489a;
        int length = strArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                z = false;
                break;
            }
            String str = strArr[i];
            if (string != null && string.equals(str)) {
                z = true;
                break;
            }
            i++;
        }
        if (!z) {
            this.f5492c.getAndSet(2);
            return -1;
        }
        m524h();
        m515q();
        m517o();
        return 0;
    }

    /* renamed from: h */
    private void m524h() throws IOException {
        this.f5505p = new TXAudioDecoderWrapper();
        this.f5505p.mo468a(this.f5494e.m1727m());
        this.f5505p.mo467a(this.f5494e.m1727m(), (Surface) null);
        this.f5505p.mo469a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: i */
    public void m523i() {
        try {
            m541a(this.f5496g);
            m548a();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* renamed from: a */
    public void m548a() {
        if (this.f5492c.get() == -1 || this.f5492c.get() == 2) {
            TXCLog.m2914e(f5490b, "you should set bgm info first");
        } else if (this.f5492c.get() == 1) {
            TXCLog.m2914e(f5490b, "decode have been started");
        } else {
            this.f5492c.getAndSet(1);
            m522j();
        }
    }

    /* renamed from: a */
    public short[] m539a(short[] sArr) {
        if (this.f5492c.get() != 1) {
            TXCLog.m2914e(f5490b, "you should start first");
            return sArr;
        }
        return this.f5513x.m501a(sArr, m546a(sArr.length));
    }

    /* renamed from: a */
    private short[] m546a(int i) {
        short[] m543a;
        Frame m532c = m532c();
        if (m532c == null || (m543a = m543a(m532c)) == null) {
            return null;
        }
        short[] copyOf = Arrays.copyOf(m543a, i);
        int length = m543a.length;
        if (length >= i) {
            if (length > i) {
                this.f5514y = m533b(Arrays.copyOfRange(m543a, i, m543a.length));
                return copyOf;
            } else if (length != i) {
                return copyOf;
            } else {
                short[] m543a2 = m543a(m532c);
                this.f5514y = null;
                return m543a2;
            }
        }
        while (length < i) {
            Frame m532c2 = m532c();
            if (m532c2 == null) {
                return null;
            }
            short[] m543a3 = m543a(m532c2);
            if (m543a3.length + length > i) {
                short[] m538a = m538a(copyOf, length, m543a3);
                if (m538a != null) {
                    length += m543a3.length - m538a.length;
                    this.f5514y = m533b(m538a);
                }
            } else {
                m538a(copyOf, length, m543a3);
                length += m543a3.length;
                this.f5514y = null;
            }
        }
        return copyOf;
    }

    /* renamed from: a */
    private short[] m543a(Frame frame) {
        if (frame instanceof BGMFrame) {
            return ((BGMFrame) frame).m552z();
        }
        return BufferUtils.m551a(frame.m2338b(), frame.m2325g());
    }

    /* renamed from: a */
    private short[] m538a(short[] sArr, int i, short[] sArr2) {
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

    /* renamed from: b */
    public boolean m537b() {
        return !this.f5491A && this.f5507r.get();
    }

    /* renamed from: c */
    public Frame m532c() {
        Frame frame = this.f5514y;
        Frame frame2 = null;
        if (frame != null) {
            this.f5514y = null;
            return frame;
        } else if (!this.f5491A && this.f5507r.get()) {
            return null;
        } else {
            while (true) {
                List<Frame> list = this.f5503n;
                if (list == null || list.size() != 0) {
                    break;
                }
                this.f5508s.lock();
                try {
                    try {
                        this.f5510u.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } finally {
                    this.f5508s.unlock();
                }
            }
            List<Frame> list2 = this.f5503n;
            if (list2 != null && list2.size() <= 10) {
                this.f5508s.lock();
                this.f5509t.signal();
            }
            while (true) {
                if (frame2 != null && frame2.m2325g() != 0) {
                    break;
                }
                List<Frame> list3 = this.f5503n;
                if (list3 == null || list3.size() == 0) {
                    break;
                }
                frame2 = this.f5503n.remove(0);
            }
            return frame2;
        }
    }

    /* renamed from: d */
    public void m530d() {
        if (this.f5492c.get() == -1) {
            return;
        }
        this.f5492c.getAndSet(2);
        TXCLog.m2913i(f5490b, "============================start cancel mix task=============================");
        m511u();
        m513s();
        m514r();
        m512t();
        this.f5515z.removeCallbacksAndMessages(null);
        TXCLog.m2913i(f5490b, "============================cancel finish =============================");
    }

    /* renamed from: j */
    private void m522j() {
        m521k();
        this.f5504o = new C3664a();
        this.f5504o.start();
    }

    /* renamed from: k */
    private void m521k() {
        C3664a c3664a = this.f5504o;
        if (c3664a != null && c3664a.isAlive() && !this.f5504o.isInterrupted()) {
            this.f5504o.interrupt();
            this.f5504o = null;
        }
        m514r();
        m515q();
        this.f5506q.getAndSet(false);
        this.f5507r.getAndSet(false);
    }

    /* renamed from: e */
    public MediaFormat m528e() {
        return this.f5495f;
    }

    /* renamed from: a */
    public void m540a(boolean z) {
        this.f5491A = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: TXAudioMixer.java */
    /* renamed from: com.tencent.liteav.videoediter.audio.c$a */
    /* loaded from: classes3.dex */
    public class C3664a extends Thread {
        public C3664a() {
            super("Mixer-BGM-Decoder-Thread");
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            super.run();
            TXCLog.m2915d(TXAudioMixer.f5490b, "================= start thread===================");
            try {
                TXAudioMixer.this.m520l();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (TXAudioMixer.this.f5491A && TXAudioMixer.this.f5492c.get() == 1) {
                TXAudioMixer.this.f5515z.post(new Runnable() { // from class: com.tencent.liteav.videoediter.audio.c.a.1
                    @Override // java.lang.Runnable
                    public void run() {
                        TXAudioMixer.this.m523i();
                    }
                });
            }
            TXCLog.m2915d(TXAudioMixer.f5490b, "================= finish thread===================");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: l */
    public void m520l() throws Exception {
        Frame frame;
        Frame m535b;
        TXCLog.m2915d(f5490b, "================= start decode===================");
        while (true) {
            if (this.f5492c.get() != 1 || Thread.currentThread().isInterrupted()) {
                break;
            } else if (this.f5507r.get()) {
                TXCLog.m2915d(f5490b, "=================解码完毕===================");
                break;
            } else {
                try {
                    m519m();
                    frame = m518n();
                } catch (Exception unused) {
                    frame = null;
                }
                if (frame != null && (m535b = m535b(frame)) != null) {
                    List<Frame> list = this.f5503n;
                    if (list != null && list.size() == 20) {
                        this.f5508s.lock();
                        try {
                            try {
                                this.f5509t.await();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            this.f5508s.unlock();
                        } finally {
                            this.f5508s.unlock();
                        }
                    }
                    List<Frame> list2 = this.f5503n;
                    if (list2 != null && list2.size() == 0) {
                        if (m535b != null) {
                            this.f5503n.add(m535b);
                        }
                        this.f5508s.lock();
                        this.f5510u.signal();
                    } else {
                        List<Frame> list3 = this.f5503n;
                        if (list3 != null && m535b != null) {
                            list3.add(m535b);
                        }
                    }
                }
            }
        }
        TXCLog.m2915d(f5490b, "=================decode finish===================");
    }

    /* renamed from: m */
    private void m519m() throws InterruptedException {
        Frame mo461c;
        if (!this.f5506q.get() && (mo461c = this.f5505p.mo461c()) != null) {
            Frame m1741b = this.f5494e.m1741b(mo461c);
            if (this.f5494e.m1736d(m1741b)) {
                this.f5506q.getAndSet(true);
                String str = f5490b;
                TXCLog.m2915d(str, "audio endOfFile:" + this.f5506q.get());
                TXCLog.m2915d(f5490b, "read audio end");
            }
            this.f5505p.mo466a(m1741b);
        }
    }

    /* renamed from: n */
    private Frame m518n() {
        Frame mo460d;
        if (this.f5492c.get() != 1 || (mo460d = this.f5505p.mo460d()) == null || mo460d.m2310o() == null) {
            return null;
        }
        if (mo460d.m2329e() < this.f5497h && (mo460d.m2310o().flags & 4) == 0) {
            return null;
        }
        if (mo460d.m2329e() > this.f5498i) {
            this.f5507r.getAndSet(true);
            return null;
        }
        if ((mo460d.m2310o().flags & 4) != 0) {
            TXCLog.m2915d(f5490b, "==================generate decode Audio END==========================");
            this.f5507r.getAndSet(true);
        }
        return mo460d;
    }

    /* renamed from: b */
    private Frame m535b(Frame frame) throws InterruptedException {
        short[] m551a;
        TXChannelResample tXChannelResample;
        if (frame.m2310o().flags == 2) {
            return frame;
        }
        if ((this.f5500k == this.f5502m && this.f5499j == this.f5501l) || (m551a = BufferUtils.m551a(frame.m2338b(), frame.m2325g())) == null || m551a.length == 0 || (tXChannelResample = this.f5511v) == null || this.f5512w == null) {
            return frame;
        }
        if (this.f5500k != this.f5502m) {
            m551a = tXChannelResample.m506a(m551a);
        }
        if (this.f5499j != this.f5501l && ((m551a = this.f5512w.doResample(m551a)) == null || m551a.length == 0)) {
            return null;
        }
        return m533b(m551a);
    }

    /* renamed from: b */
    private Frame m533b(short[] sArr) {
        if (sArr == null || sArr.length == 0) {
            return null;
        }
        BGMFrame bGMFrame = new BGMFrame();
        bGMFrame.m553a(sArr);
        bGMFrame.m2331d(sArr.length * 2);
        bGMFrame.m2322h(this.f5500k);
        bGMFrame.m2324g(this.f5499j);
        return bGMFrame;
    }

    @TargetApi(16)
    /* renamed from: o */
    private void m517o() {
        if (this.f5495f == null || this.f5493d == null) {
            return;
        }
        if (this.f5511v == null) {
            this.f5511v = new TXChannelResample();
        }
        this.f5511v.m507a(this.f5502m, this.f5500k);
        if (this.f5512w == null) {
            this.f5512w = new TXSkpResample();
        }
        this.f5512w.init(this.f5501l, this.f5499j);
        TXCLog.m2913i(f5490b, "TXChannelResample and TXSkpResample have been created!!!");
    }

    /* renamed from: p */
    private void m516p() throws IOException {
        this.f5494e = new MediaExtractorWrapper(true);
        this.f5494e.m1744a(this.f5496g);
        this.f5495f = this.f5494e.m1727m();
        this.f5502m = this.f5495f.getInteger("channel-count");
        this.f5501l = this.f5495f.getInteger("sample-rate");
        if (this.f5497h == -1 && this.f5498i == -1) {
            this.f5497h = 0L;
            this.f5498i = this.f5495f.getLong("durationUs") * 1000;
        }
        this.f5494e.m1739c(this.f5497h);
    }

    /* renamed from: q */
    private void m515q() {
        this.f5503n = new LinkedList();
        this.f5503n = Collections.synchronizedList(this.f5503n);
    }

    /* renamed from: r */
    private void m514r() {
        if (this.f5503n != null) {
            TXCLog.m2913i(f5490b, "clean audio frame queue");
            this.f5503n.clear();
            this.f5503n = null;
        }
    }

    /* renamed from: s */
    private void m513s() {
        if (this.f5494e != null) {
            TXCLog.m2913i(f5490b, "release media extractor");
            this.f5494e.m1725o();
            this.f5494e = null;
        }
    }

    /* renamed from: t */
    private void m512t() {
        if (this.f5511v != null) {
            this.f5511v = null;
            TXCLog.m2913i(f5490b, "release chanel resample ");
        }
        if (this.f5512w != null) {
            TXCLog.m2913i(f5490b, "release skp resample ");
            this.f5512w.destroy();
            this.f5512w = null;
        }
    }

    /* renamed from: u */
    private void m511u() {
        C3664a c3664a = this.f5504o;
        if (c3664a != null && c3664a.isAlive() && !this.f5504o.isInterrupted()) {
            TXCLog.m2913i(f5490b, "interrupt the decode thread");
            this.f5504o.interrupt();
            this.f5504o = null;
        }
        if (this.f5505p != null) {
            TXCLog.m2913i(f5490b, "stop audio decode");
            this.f5505p.mo463b();
            this.f5505p = null;
        }
    }
}
