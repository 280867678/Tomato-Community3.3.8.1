package com.tencent.liteav.p120e;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.p119d.Frame;
import com.tencent.liteav.p125j.BitmapUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: com.tencent.liteav.e.p */
/* loaded from: classes3.dex */
public class PicDec {

    /* renamed from: A */
    private int f3698A;

    /* renamed from: f */
    private IPictureDecderListener f3705f;

    /* renamed from: h */
    private long f3707h;

    /* renamed from: l */
    private long f3711l;

    /* renamed from: m */
    private long f3712m;

    /* renamed from: n */
    private boolean f3713n;

    /* renamed from: o */
    private long f3714o;

    /* renamed from: p */
    private long f3715p;

    /* renamed from: u */
    private Frame f3720u;

    /* renamed from: x */
    private long f3723x;

    /* renamed from: z */
    private int f3725z;

    /* renamed from: a */
    private final String f3700a = "PicDec";

    /* renamed from: b */
    private int f3701b = 1;

    /* renamed from: g */
    private int f3706g = 20;

    /* renamed from: j */
    private long f3709j = 1000;

    /* renamed from: k */
    private long f3710k = 500;

    /* renamed from: q */
    private long f3716q = -1;

    /* renamed from: r */
    private long f3717r = -1;

    /* renamed from: s */
    private long f3718s = -1;

    /* renamed from: t */
    private long f3719t = -1;

    /* renamed from: y */
    private int f3724y = 0;

    /* renamed from: B */
    private int f3699B = -1;

    /* renamed from: d */
    private HandlerThread f3703d = new HandlerThread("picDec_handler_thread");

    /* renamed from: c */
    private Handler f3702c = new HandlerC3452a(this.f3703d.getLooper());

    /* renamed from: e */
    private List<Bitmap> f3704e = new ArrayList();

    /* renamed from: i */
    private List<Long> f3708i = new ArrayList();

    /* renamed from: v */
    private AtomicBoolean f3721v = new AtomicBoolean(false);

    /* renamed from: w */
    private AtomicBoolean f3722w = new AtomicBoolean(false);

    public PicDec() {
        this.f3703d.start();
    }

    /* renamed from: a */
    public void m2083a(boolean z) {
        this.f3713n = z;
    }

    /* renamed from: a */
    public void m2084a(List<Bitmap> list, int i) {
        if (list == null || list.size() == 0) {
            TXCLog.m2914e("PicDec", "setBitmapList, bitmapList is empty");
            return;
        }
        if (i <= 0) {
            this.f3706g = 20;
        } else {
            this.f3706g = i;
        }
        m2085a(list);
        this.f3707h = 1000 / this.f3706g;
    }

    /* renamed from: a */
    public long m2093a(int i) {
        synchronized (this) {
            if (this.f3699B != i) {
                this.f3708i.clear();
            }
        }
        this.f3699B = i;
        this.f3709j = BitmapUtil.m1446a(i);
        this.f3710k = BitmapUtil.m1443b(i);
        if (i == 5 || i == 4) {
            this.f3711l = this.f3704e.size() * (this.f3709j + this.f3710k);
        } else {
            long j = this.f3709j;
            long j2 = this.f3710k;
            this.f3711l = (this.f3704e.size() * (j + j2)) - j2;
        }
        this.f3712m = this.f3707h * (((int) ((this.f3711l / 1000) * this.f3706g)) - 1);
        return this.f3712m;
    }

    /* renamed from: a */
    public void m2088a(IPictureDecderListener iPictureDecderListener) {
        this.f3705f = iPictureDecderListener;
    }

    /* renamed from: j */
    private void m2064j() {
        this.f3716q = -1L;
        this.f3717r = -1L;
        this.f3718s = -1L;
        this.f3719t = -1L;
        this.f3721v.compareAndSet(true, false);
    }

    /* renamed from: a */
    public void m2091a(long j, long j2) {
        this.f3714o = j;
        this.f3715p = j2;
    }

    /* renamed from: a */
    public int m2094a() {
        if (this.f3704e.size() == 0) {
            return 0;
        }
        this.f3725z = 720;
        return this.f3725z;
    }

    /* renamed from: b */
    public int m2082b() {
        if (this.f3704e.size() == 0) {
            return 0;
        }
        this.f3698A = 1280;
        return this.f3698A;
    }

    /* renamed from: c */
    public boolean m2077c() {
        return this.f3722w.get();
    }

    /* renamed from: d */
    public synchronized void m2074d() {
        if (this.f3701b == 2) {
            TXCLog.m2914e("PicDec", "start(), mState is play, ignore");
            return;
        }
        this.f3701b = 2;
        this.f3722w.compareAndSet(true, false);
        m2064j();
        this.f3702c.sendEmptyMessage(1);
    }

    /* renamed from: e */
    public synchronized void m2072e() {
        if (this.f3701b == 1) {
            TXCLog.m2914e("PicDec", "stop(), mState is init, ignore");
            return;
        }
        this.f3701b = 1;
        this.f3702c.sendEmptyMessage(4);
    }

    /* renamed from: f */
    public void m2070f() {
        int i = this.f3701b;
        if (i == 1 || i == 3) {
            TXCLog.m2914e("PicDec", "pause(), mState = " + this.f3701b + ", ignore");
            return;
        }
        this.f3701b = 3;
        this.f3702c.sendEmptyMessage(3);
    }

    /* renamed from: g */
    public void m2068g() {
        int i = this.f3701b;
        if (i == 1 || i == 2) {
            TXCLog.m2914e("PicDec", "resume(), mState = " + this.f3701b + ", ignore");
            return;
        }
        this.f3701b = 2;
        this.f3702c.sendEmptyMessage(2);
    }

    /* renamed from: a */
    public void m2092a(long j) {
        this.f3701b = 4;
        this.f3723x = j;
        this.f3702c.sendEmptyMessage(5);
    }

    /* renamed from: h */
    public synchronized void m2066h() {
        if (this.f3701b == 1) {
            TXCLog.m2914e("PicDec", "getNextBitmapFrame, current state is init, ignore");
        } else {
            this.f3702c.sendEmptyMessage(2);
        }
    }

    /* compiled from: PicDec.java */
    /* renamed from: com.tencent.liteav.e.p$a */
    /* loaded from: classes3.dex */
    class HandlerC3452a extends Handler {
        public HandlerC3452a(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                PicDec.this.m2059o();
                PicDec picDec = PicDec.this;
                picDec.m2076c(picDec.f3714o);
                PicDec.this.f3702c.sendEmptyMessage(2);
            } else if (i == 2) {
                PicDec.this.m2061m();
            } else if (i == 3) {
                PicDec.this.m2063k();
            } else if (i == 4) {
                TXCLog.m2913i("PicDec", "stopDecode");
                PicDec.this.m2062l();
            } else if (i != 5) {
            } else {
                PicDec picDec2 = PicDec.this;
                picDec2.m2081b(picDec2.f3723x);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: k */
    public void m2063k() {
        this.f3702c.removeMessages(2);
        m2064j();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: l */
    public void m2062l() {
        this.f3702c.removeMessages(2);
        m2064j();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: m */
    public void m2061m() {
        if (this.f3713n && this.f3718s >= 0) {
            if (this.f3721v.get()) {
                m2080b(this.f3720u);
            } else if (!m2060n()) {
                this.f3702c.sendEmptyMessageDelayed(2, 5L);
                return;
            } else {
                m2080b(this.f3720u);
            }
        }
        this.f3720u = new Frame();
        synchronized (this) {
            if (this.f3708i.size() <= 0) {
                return;
            }
            long longValue = this.f3708i.get(this.f3724y).longValue();
            if (longValue > this.f3715p * 1000) {
                longValue = -1;
            }
            if (longValue == -1) {
                this.f3720u.m2334c(4);
                this.f3720u.m2343a(0L);
                this.f3720u.m2318j(m2094a());
                this.f3720u.m2316k(m2082b());
                this.f3720u.m2312m(0);
                m2080b(this.f3720u);
                this.f3702c.sendEmptyMessage(4);
                this.f3722w.set(true);
                return;
            }
            synchronized (this) {
                this.f3724y++;
            }
            this.f3718s = longValue / 1000;
            this.f3720u.m2343a(longValue);
            this.f3720u.m2336b(longValue);
            this.f3720u.m2326f(this.f3706g);
            this.f3720u.m2312m(0);
            m2089a(this.f3720u);
            if (!this.f3713n) {
                m2080b(this.f3720u);
            } else if (this.f3716q < 0) {
                this.f3716q = this.f3718s;
                this.f3721v.set(true);
                this.f3718s = longValue;
                this.f3717r = System.currentTimeMillis();
                this.f3702c.sendEmptyMessage(2);
            } else {
                this.f3721v.compareAndSet(true, false);
                this.f3702c.sendEmptyMessageDelayed(2, 5L);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: b */
    public void m2081b(long j) {
        m2076c(j);
        this.f3720u = new Frame();
        synchronized (this) {
            if (this.f3708i.size() <= 0) {
                return;
            }
            this.f3720u.m2343a(this.f3708i.get(this.f3724y).longValue());
            this.f3720u.m2312m(0);
            m2089a(this.f3720u);
            m2080b(this.f3720u);
        }
    }

    /* renamed from: a */
    private void m2089a(Frame frame) {
        Bitmap bitmap;
        long m2329e = frame.m2329e() / 1000;
        int i = (int) (m2329e / (this.f3709j + this.f3710k));
        TXCLog.m2913i("PicDec", "setBitmapsAndDisplayRatio, frameTimeMs = " + m2329e + ", picIndex = " + i + ", loopTime = " + (this.f3709j + this.f3710k));
        if (i >= this.f3704e.size()) {
            List<Bitmap> list = this.f3704e;
            bitmap = list.get(list.size() - 1);
        } else {
            bitmap = this.f3704e.get(i);
        }
        Bitmap bitmap2 = null;
        if (i < this.f3704e.size() - 1) {
            bitmap2 = this.f3704e.get(i + 1);
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(bitmap);
        if (bitmap2 != null) {
            arrayList.add(bitmap2);
        }
        frame.m2340a(arrayList);
        frame.m2318j(m2094a());
        frame.m2316k(m2082b());
    }

    /* renamed from: n */
    private boolean m2060n() {
        this.f3719t = System.currentTimeMillis();
        this.f3718s = this.f3720u.m2329e() / 1000;
        return Math.abs(this.f3718s - this.f3716q) < this.f3719t - this.f3717r;
    }

    /* renamed from: b */
    private void m2080b(Frame frame) {
        IPictureDecderListener iPictureDecderListener = this.f3705f;
        if (iPictureDecderListener != null) {
            iPictureDecderListener.mo1937a(frame);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: c */
    public synchronized void m2076c(long j) {
        if (this.f3708i.size() <= 0) {
            return;
        }
        for (int i = 0; i < this.f3708i.size(); i++) {
            if (this.f3708i.get(i).longValue() / 1000 >= j) {
                this.f3724y = i;
                return;
            }
        }
    }

    /* renamed from: a */
    private void m2085a(List<Bitmap> list) {
        for (int i = 0; i < list.size(); i++) {
            this.f3704e.add(m2090a(list.get(i), 720, 1280));
        }
    }

    /* renamed from: a */
    public static Bitmap m2090a(Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float f = width;
        float f2 = height;
        float f3 = i;
        float f4 = i2;
        float f5 = f / f2 >= f3 / f4 ? f3 / f : f4 / f2;
        Matrix matrix = new Matrix();
        matrix.postScale(f5, f5);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: o */
    public synchronized void m2059o() {
        if (this.f3708i.size() > 0) {
            return;
        }
        int i = (int) ((this.f3711l / 1000) * this.f3706g);
        for (int i2 = 0; i2 < i; i2++) {
            if (i2 == i - 1) {
                this.f3708i.add(Long.valueOf(i2 * this.f3707h * 1000));
                this.f3708i.add(-1L);
            } else {
                this.f3708i.add(Long.valueOf(i2 * this.f3707h * 1000));
            }
        }
    }

    /* renamed from: i */
    public void m2065i() {
        for (int i = 0; i < this.f3704e.size(); i++) {
            this.f3704e.get(i).recycle();
        }
        this.f3704e.clear();
        HandlerThread handlerThread = this.f3703d;
        if (handlerThread != null) {
            handlerThread.quit();
        }
    }
}
