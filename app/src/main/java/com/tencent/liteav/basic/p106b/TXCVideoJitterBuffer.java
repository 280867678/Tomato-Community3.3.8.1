package com.tencent.liteav.basic.p106b;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p111g.TXSNALPacket;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import java.util.LinkedList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* renamed from: com.tencent.liteav.basic.b.a */
/* loaded from: classes3.dex */
public class TXCVideoJitterBuffer {

    /* renamed from: q */
    private HandlerThread f2354q;

    /* renamed from: r */
    private Handler f2355r;

    /* renamed from: a */
    private TXIVideoJitterBufferListener f2338a = null;

    /* renamed from: b */
    private LinkedList<TXSNALPacket> f2339b = new LinkedList<>();

    /* renamed from: c */
    private LinkedList<TXSNALPacket> f2340c = new LinkedList<>();

    /* renamed from: d */
    private long f2341d = 0;

    /* renamed from: e */
    private long f2342e = 15;

    /* renamed from: f */
    private volatile boolean f2343f = false;

    /* renamed from: g */
    private volatile float f2344g = 1.0f;

    /* renamed from: h */
    private long f2345h = 0;

    /* renamed from: i */
    private long f2346i = 0;

    /* renamed from: j */
    private long f2347j = 0;

    /* renamed from: k */
    private long f2348k = 0;

    /* renamed from: l */
    private long f2349l = 0;

    /* renamed from: m */
    private long f2350m = 0;

    /* renamed from: n */
    private long f2351n = 0;

    /* renamed from: o */
    private long f2352o = 0;

    /* renamed from: p */
    private boolean f2353p = false;

    /* renamed from: s */
    private boolean f2356s = false;

    /* renamed from: t */
    private long f2357t = 20;

    /* renamed from: u */
    private long f2358u = 0;

    /* renamed from: v */
    private volatile long f2359v = 0;

    /* renamed from: w */
    private volatile long f2360w = 0;

    /* renamed from: x */
    private int f2361x = 0;

    /* renamed from: y */
    private int f2362y = 0;

    /* renamed from: z */
    private long f2363z = 0;

    /* renamed from: A */
    private long f2331A = 0;

    /* renamed from: B */
    private long f2332B = 0;

    /* renamed from: C */
    private long f2333C = 0;

    /* renamed from: D */
    private long f2334D = 0;

    /* renamed from: E */
    private long f2335E = 0;

    /* renamed from: F */
    private long f2336F = 0;

    /* renamed from: G */
    private ReadWriteLock f2337G = new ReentrantReadWriteLock();

    /* renamed from: j */
    static /* synthetic */ long m3134j(TXCVideoJitterBuffer tXCVideoJitterBuffer) {
        long j = tXCVideoJitterBuffer.f2331A + 1;
        tXCVideoJitterBuffer.f2331A = j;
        return j;
    }

    /* renamed from: l */
    static /* synthetic */ long m3130l(TXCVideoJitterBuffer tXCVideoJitterBuffer) {
        long j = tXCVideoJitterBuffer.f2335E + 1;
        tXCVideoJitterBuffer.f2335E = j;
        return j;
    }

    public TXCVideoJitterBuffer() {
        this.f2354q = null;
        this.f2355r = null;
        this.f2354q = new HandlerThread("VideoJitterBufferHandler");
        this.f2354q.start();
        this.f2337G.writeLock().lock();
        this.f2355r = new Handler(this.f2354q.getLooper());
        this.f2337G.writeLock().unlock();
    }

    /* renamed from: a */
    public void m3166a(final TXIVideoJitterBufferListener tXIVideoJitterBufferListener) {
        this.f2337G.readLock().lock();
        Handler handler = this.f2355r;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.tencent.liteav.basic.b.a.1
                @Override // java.lang.Runnable
                public void run() {
                    TXCVideoJitterBuffer.this.f2338a = tXIVideoJitterBufferListener;
                }
            });
        }
        this.f2337G.readLock().unlock();
    }

    /* renamed from: a */
    public void m3174a() {
        this.f2337G.readLock().lock();
        Handler handler = this.f2355r;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.tencent.liteav.basic.b.a.2
                @Override // java.lang.Runnable
                public void run() {
                    TXCVideoJitterBuffer.this.f2356s = true;
                    TXCVideoJitterBuffer.this.f2332B = TXCTimeUtil.getTimeTick();
                }
            });
        }
        this.f2337G.readLock().unlock();
        m3129m();
    }

    /* renamed from: b */
    public void m3163b() {
        this.f2337G.writeLock().lock();
        Handler handler = this.f2355r;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.tencent.liteav.basic.b.a.3
                @Override // java.lang.Runnable
                public void run() {
                    TXCVideoJitterBuffer.this.f2356s = false;
                    TXCVideoJitterBuffer.this.m3131l();
                    try {
                        Looper.myLooper().quit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        this.f2355r = null;
        this.f2337G.writeLock().unlock();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: l */
    public void m3131l() {
        this.f2339b.clear();
        this.f2341d = 0L;
        this.f2340c.clear();
        this.f2342e = 15L;
        this.f2345h = 0L;
        this.f2346i = 0L;
        this.f2347j = 0L;
        this.f2359v = 0L;
        this.f2360w = 0L;
        this.f2362y = 0;
        this.f2361x = 0;
        this.f2343f = false;
        this.f2344g = 1.0f;
        this.f2348k = 0L;
        this.f2352o = 0L;
        this.f2349l = 0L;
        this.f2350m = 0L;
        this.f2333C = 0L;
        this.f2334D = 0L;
        this.f2335E = 0L;
        this.f2336F = 0L;
        this.f2353p = false;
    }

    /* renamed from: a */
    public void m3164a(boolean z) {
        this.f2353p = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: m */
    public void m3129m() {
        this.f2337G.readLock().lock();
        Handler handler = this.f2355r;
        if (handler != null) {
            handler.postDelayed(new Runnable() { // from class: com.tencent.liteav.basic.b.a.4
                @Override // java.lang.Runnable
                public void run() {
                    while (TXCVideoJitterBuffer.this.f2339b != null && !TXCVideoJitterBuffer.this.f2339b.isEmpty() && !TXCVideoJitterBuffer.this.f2353p) {
                        TXCVideoJitterBuffer.this.m3158c();
                        TXSNALPacket m3127n = TXCVideoJitterBuffer.this.m3127n();
                        if (m3127n == null) {
                            break;
                        } else if (m3127n != null && TXCVideoJitterBuffer.this.f2338a != null) {
                            TXCVideoJitterBuffer.this.f2338a.mo1403b(m3127n);
                        }
                    }
                    while (!TXCVideoJitterBuffer.this.f2340c.isEmpty() && TXCVideoJitterBuffer.this.f2338a != null) {
                        TXSNALPacket tXSNALPacket = (TXSNALPacket) TXCVideoJitterBuffer.this.f2340c.getFirst();
                        long mo1379t = TXCVideoJitterBuffer.this.f2338a.mo1379t();
                        if (0 == mo1379t) {
                            mo1379t = TXCVideoJitterBuffer.this.f2359v;
                        }
                        if (tXSNALPacket.pts > mo1379t) {
                            break;
                        }
                        TXCVideoJitterBuffer.this.f2338a.mo1398c(tXSNALPacket);
                        TXCVideoJitterBuffer.this.f2340c.removeFirst();
                    }
                    long timeTick = TXCTimeUtil.getTimeTick();
                    if (timeTick > TXCVideoJitterBuffer.this.f2332B + 200) {
                        TXCVideoJitterBuffer.this.f2363z += TXCVideoJitterBuffer.this.m3154d();
                        TXCVideoJitterBuffer.m3134j(TXCVideoJitterBuffer.this);
                        long m3150e = TXCVideoJitterBuffer.this.m3150e();
                        TXCVideoJitterBuffer.this.f2334D += m3150e;
                        TXCVideoJitterBuffer.m3130l(TXCVideoJitterBuffer.this);
                        if (TXCVideoJitterBuffer.this.f2335E > 0) {
                            TXCVideoJitterBuffer tXCVideoJitterBuffer = TXCVideoJitterBuffer.this;
                            tXCVideoJitterBuffer.f2336F = tXCVideoJitterBuffer.f2334D / TXCVideoJitterBuffer.this.f2335E;
                        }
                        if (m3150e > TXCVideoJitterBuffer.this.f2333C) {
                            TXCVideoJitterBuffer.this.f2333C = m3150e;
                        }
                        TXCVideoJitterBuffer.this.f2332B = timeTick;
                    }
                    if (TXCVideoJitterBuffer.this.f2356s) {
                        TXCVideoJitterBuffer.this.m3129m();
                    }
                }
            }, this.f2357t);
        }
        this.f2337G.readLock().unlock();
    }

    /* renamed from: a */
    public void m3165a(final TXSNALPacket tXSNALPacket) {
        if (tXSNALPacket == null) {
            return;
        }
        if (tXSNALPacket.pts > this.f2360w || tXSNALPacket.pts + 500 < this.f2360w) {
            this.f2360w = tXSNALPacket.pts;
        }
        if (this.f2359v > this.f2360w) {
            this.f2359v = this.f2360w;
        }
        int i = tXSNALPacket.nalType;
        if (i == 0) {
            this.f2361x = this.f2362y;
            this.f2362y = 1;
        } else if (i == 2 || i == 1) {
            this.f2362y++;
        }
        this.f2337G.readLock().lock();
        Handler handler = this.f2355r;
        if (handler != null) {
            handler.post(new Runnable() { // from class: com.tencent.liteav.basic.b.a.5
                @Override // java.lang.Runnable
                public void run() {
                    TXCVideoJitterBuffer tXCVideoJitterBuffer;
                    if (tXSNALPacket.nalType == 6) {
                        TXCVideoJitterBuffer.this.f2340c.add(tXSNALPacket);
                        return;
                    }
                    TXCVideoJitterBuffer.this.f2339b.add(tXSNALPacket);
                    TXCVideoJitterBuffer.this.f2341d = tXCVideoJitterBuffer.f2339b.size();
                    if (TXCVideoJitterBuffer.this.f2338a != null) {
                        TXCVideoJitterBuffer.this.f2341d += TXCVideoJitterBuffer.this.f2338a.mo1378u();
                    }
                    TXCVideoJitterBuffer.this.m3149e(tXSNALPacket.dts);
                }
            });
        }
        this.f2337G.readLock().unlock();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: n */
    public TXSNALPacket m3127n() {
        long j;
        LinkedList<TXSNALPacket> linkedList = this.f2339b;
        if (linkedList != null && !linkedList.isEmpty()) {
            if (this.f2359v > this.f2360w) {
                this.f2359v = this.f2360w;
            }
            long timeTick = TXCTimeUtil.getTimeTick();
            boolean z = true;
            if (this.f2348k != 0) {
                TXSNALPacket first = this.f2339b.getFirst();
                long j2 = first.dts;
                long j3 = this.f2348k;
                if (j2 > j3) {
                    j = m3162b(j2 - j3);
                } else {
                    long m3162b = m3162b(0L);
                    TXCLog.m2911w("TXCVideoJitterBuffer", "videojitter pull nal with invalid ts, current dts [" + first.dts + "] < last dts[ " + this.f2348k + "]!!! decInterval is " + m3162b);
                    j = m3162b;
                }
                long j4 = this.f2352o;
                long j5 = this.f2358u;
                if (j + j4 <= timeTick + j5) {
                    this.f2358u = (j5 + timeTick) - (j4 + j);
                    if (this.f2358u > j) {
                        this.f2358u = j;
                    }
                } else {
                    z = false;
                }
            }
            if (z) {
                TXSNALPacket m3125o = m3125o();
                m3145f(m3125o.dts);
                this.f2352o = timeTick;
                return m3125o;
            }
        }
        return null;
    }

    /* renamed from: b */
    private long m3162b(long j) {
        float m3157c;
        long j2 = 500;
        if (j > 500) {
            j = 500;
        }
        if (j <= 0) {
            long j3 = this.f2342e;
            if (j3 <= 0) {
                return 0L;
            }
            return 1000 / j3;
        }
        TXIVideoJitterBufferListener tXIVideoJitterBufferListener = this.f2338a;
        if ((tXIVideoJitterBufferListener != null ? tXIVideoJitterBufferListener.mo1379t() : 0L) > 0) {
            j2 = 50;
        } else if (this.f2343f) {
            j2 = 200;
        }
        long m3171a = m3171a(j, j2);
        if (this.f2343f) {
            m3157c = m3153d(m3171a);
        } else {
            m3157c = m3157c(m3171a);
        }
        return ((float) j) / m3157c;
    }

    /* renamed from: a */
    private long m3171a(long j, long j2) {
        long j3 = this.f2342e;
        if (j3 > 0) {
            long j4 = 1000 / j3;
            if (j < j4) {
                j = j4;
            }
        }
        return j > j2 ? j : j2;
    }

    /* renamed from: c */
    private float m3157c(long j) {
        TXIVideoJitterBufferListener tXIVideoJitterBufferListener;
        TXIVideoJitterBufferListener tXIVideoJitterBufferListener2 = this.f2338a;
        if ((tXIVideoJitterBufferListener2 != null ? tXIVideoJitterBufferListener2.mo1378u() : 0) > 24) {
            TXCLog.m2914e("TXCVideoJitterBuffer", "videojitter pull nal with speed : 0.1");
            return 0.1f;
        }
        TXIVideoJitterBufferListener tXIVideoJitterBufferListener3 = this.f2338a;
        long j2 = 0;
        long mo1379t = tXIVideoJitterBufferListener3 != null ? tXIVideoJitterBufferListener3.mo1379t() : 0L;
        if (mo1379t > 0) {
            if (mo1379t >= this.f2359v + j) {
                return mo1379t >= (this.f2359v + j) + 200 ? 2.2f : 1.2f;
            }
            long j3 = mo1379t + j;
            if (this.f2359v < j3) {
                return 1.0f;
            }
            return this.f2359v >= j3 + 200 ? 0.5f : 0.9f;
        }
        long j4 = this.f2360w > this.f2359v ? this.f2360w - this.f2359v : 0L;
        long j5 = this.f2344g * 1000.0f;
        if (this.f2338a != null) {
            j2 = this.f2351n * tXIVideoJitterBufferListener.mo1378u();
        }
        if (j2 <= j5) {
            j2 = j5;
        }
        float f = j4 > j + j2 ? 1.1f : 1.0f;
        if (j4 > j2) {
            return f;
        }
        return 1.0f;
    }

    /* renamed from: d */
    private float m3153d(long j) {
        TXIVideoJitterBufferListener tXIVideoJitterBufferListener;
        TXIVideoJitterBufferListener tXIVideoJitterBufferListener2;
        TXIVideoJitterBufferListener tXIVideoJitterBufferListener3 = this.f2338a;
        if ((tXIVideoJitterBufferListener3 != null ? tXIVideoJitterBufferListener3.mo1378u() : 0) > 24) {
            TXCLog.m2914e("TXCVideoJitterBuffer", "videojitter pull nal with speed : 0.1");
            return 0.1f;
        }
        TXIVideoJitterBufferListener tXIVideoJitterBufferListener4 = this.f2338a;
        long j2 = 0;
        long mo1379t = tXIVideoJitterBufferListener4 != null ? tXIVideoJitterBufferListener4.mo1379t() : 0L;
        if (mo1379t > 0 && (tXIVideoJitterBufferListener2 = this.f2338a) != null && 0 != tXIVideoJitterBufferListener2.mo1380s()) {
            if (mo1379t >= this.f2359v + j) {
                return mo1379t >= (this.f2359v + j) + 200 ? 2.2f : 1.5f;
            }
            long j3 = mo1379t + j;
            if (this.f2359v < j3 || this.f2359v >= 400 + j3) {
                return 1.0f;
            }
            return this.f2359v >= j3 + 200 ? 0.5f : 0.7f;
        }
        long j4 = this.f2360w > this.f2359v ? this.f2360w - this.f2359v : 0L;
        long j5 = this.f2344g * 1000.0f;
        if (this.f2338a != null) {
            j2 = this.f2351n * tXIVideoJitterBufferListener.mo1378u();
        }
        if (j2 <= j5) {
            j2 = j5;
        }
        float f = j4 > j + j2 ? 1.2f : 1.0f;
        if (j4 > j2) {
            return f;
        }
        return 1.0f;
    }

    /* renamed from: c */
    void m3158c() {
        if (this.f2339b.size() == 0) {
            return;
        }
        this.f2339b.getFirst();
        this.f2339b.getLast();
        TXIVideoJitterBufferListener tXIVideoJitterBufferListener = this.f2338a;
        int i = 0;
        int mo1378u = tXIVideoJitterBufferListener != null ? tXIVideoJitterBufferListener.mo1378u() : 0;
        if (this.f2339b.isEmpty() || mo1378u < 24) {
            return;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < this.f2339b.size(); i3++) {
            if (this.f2339b.get(i3).nalType == 0) {
                i2 = i3;
            }
        }
        while (!this.f2339b.isEmpty() && i < i2) {
            this.f2348k = this.f2339b.getFirst().dts;
            while (!this.f2340c.isEmpty()) {
                TXSNALPacket first = this.f2340c.getFirst();
                if (first.pts > this.f2339b.getFirst().dts) {
                    break;
                }
                this.f2338a.mo1398c(first);
                this.f2340c.removeFirst();
            }
            this.f2339b.removeFirst();
            i++;
        }
        if (i <= 0) {
            return;
        }
        TXCLog.m2911w("TXCVideoJitterBuffer", "videojitter cache too maney ， so drop " + i + " frames");
    }

    /* renamed from: o */
    private TXSNALPacket m3125o() {
        TXIVideoJitterBufferListener tXIVideoJitterBufferListener;
        if (!this.f2339b.isEmpty()) {
            TXSNALPacket first = this.f2339b.getFirst();
            this.f2339b.removeFirst();
            this.f2341d = this.f2339b.size();
            if (this.f2338a == null) {
                return first;
            }
            this.f2341d += tXIVideoJitterBufferListener.mo1378u();
            return first;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: e */
    public void m3149e(long j) {
        long j2 = this.f2347j;
        if (j2 != 0) {
            long j3 = this.f2346i;
            if (j3 >= 5) {
                this.f2342e = this.f2345h / j3;
                long j4 = this.f2342e;
                if (j4 > 200) {
                    this.f2342e = 200L;
                } else if (j4 < 1) {
                    this.f2342e = 1L;
                }
                if (this.f2342e >= 30 && this.f2357t != 5) {
                    this.f2357t = 5L;
                }
                this.f2345h = 0L;
                this.f2346i = 0L;
            } else {
                long j5 = j - j2;
                if (j5 > 0) {
                    this.f2345h += 1000 / j5;
                    this.f2346i = j3 + 1;
                }
            }
        }
        this.f2347j = j;
    }

    /* renamed from: f */
    private void m3145f(long j) {
        long j2;
        long j3 = this.f2348k;
        if (j3 != 0) {
            if (j > j3) {
                j2 = j - j3;
                if (j2 > 500) {
                    j2 = 500;
                }
            } else {
                long j4 = this.f2342e;
                j2 = j4 > 0 ? 1000 / j4 : 0L;
            }
            this.f2349l += j2;
            this.f2350m++;
            long j5 = this.f2350m;
            if (j5 >= 5) {
                this.f2351n = this.f2349l / j5;
                long j6 = this.f2351n;
                if (j6 > 500) {
                    this.f2351n = 500L;
                } else if (j6 < 5) {
                    this.f2351n = 5L;
                }
                this.f2349l = 0L;
                this.f2350m = 0L;
            }
        }
        this.f2348k = j;
    }

    /* renamed from: b */
    public void m3159b(boolean z) {
        this.f2343f = z;
    }

    /* renamed from: a */
    public void m3173a(float f) {
        this.f2344g = f;
    }

    /* renamed from: a */
    public void m3172a(long j) {
        this.f2359v = j;
        if (this.f2359v > this.f2360w) {
            this.f2359v = this.f2360w;
        }
    }

    /* renamed from: d */
    public long m3154d() {
        return this.f2360w - this.f2359v;
    }

    /* renamed from: e */
    public long m3150e() {
        return this.f2341d;
    }

    /* renamed from: f */
    public long m3146f() {
        return this.f2359v;
    }

    /* renamed from: g */
    public long m3142g() {
        return this.f2360w;
    }

    /* renamed from: h */
    public int m3139h() {
        return this.f2361x;
    }

    /* renamed from: i */
    public long m3137i() {
        return this.f2333C;
    }

    /* renamed from: j */
    public long m3135j() {
        return this.f2336F;
    }

    /* renamed from: k */
    public long m3133k() {
        long j = this.f2331A;
        long j2 = j != 0 ? this.f2363z / j : 0L;
        this.f2331A = 0L;
        this.f2363z = 0L;
        return j2;
    }

    protected void finalize() throws Throwable {
        super.finalize();
        try {
            m3163b();
        } catch (Exception unused) {
        }
    }
}
