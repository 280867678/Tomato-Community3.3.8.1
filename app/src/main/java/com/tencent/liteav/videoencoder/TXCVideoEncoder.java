package com.tencent.liteav.videoencoder;

import android.opengl.GLES20;
import android.os.Bundle;
import com.tencent.avroom.TXCAVRoomConstants;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.module.TXCModule;
import com.tencent.liteav.basic.p108d.TXINotifyListener;
import com.tencent.liteav.basic.p109e.EGL10Helper;
import com.tencent.liteav.basic.p110f.TXCConfigCenter;
import com.tencent.liteav.basic.util.TXCSystemUtil;
import com.tencent.liteav.basic.util.TXCThread;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.liteav.beauty.p115b.TXCGPUI4202RGBAFilter;
import com.tencent.rtmp.TXLiveConstants;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import javax.microedition.khronos.egl.EGLContext;

/* renamed from: com.tencent.liteav.videoencoder.b */
/* loaded from: classes3.dex */
public class TXCVideoEncoder extends TXCModule {

    /* renamed from: r */
    private static Integer f5606r = 1;

    /* renamed from: u */
    private static final String f5607u = TXCVideoEncoder.class.getSimpleName();

    /* renamed from: v */
    private static int f5608v = 0;

    /* renamed from: e */
    private int f5613e;

    /* renamed from: j */
    private TXSVideoEncoderParam f5618j;

    /* renamed from: p */
    private EGL10Helper f5624p;

    /* renamed from: q */
    private TXCThread f5625q;

    /* renamed from: s */
    private boolean f5626s;

    /* renamed from: t */
    private TXCGPUI4202RGBAFilter f5627t;

    /* renamed from: a */
    private TXIVideoEncoder f5609a = null;

    /* renamed from: b */
    private TXIVideoEncoderListener f5610b = null;

    /* renamed from: c */
    private WeakReference<TXINotifyListener> f5611c = null;

    /* renamed from: d */
    private int f5612d = 0;

    /* renamed from: f */
    private int f5614f = 1;

    /* renamed from: g */
    private Timer f5615g = null;

    /* renamed from: h */
    private TimerTask f5616h = null;

    /* renamed from: i */
    private LinkedList<Runnable> f5617i = new LinkedList<>();

    /* renamed from: k */
    private float f5619k = 0.0f;

    /* renamed from: l */
    private float f5620l = 0.0f;

    /* renamed from: m */
    private float f5621m = 0.0f;

    /* renamed from: n */
    private int f5622n = 0;

    /* renamed from: o */
    private int f5623o = 0;

    /* renamed from: k */
    static /* synthetic */ int m398k(TXCVideoEncoder tXCVideoEncoder) {
        int i = tXCVideoEncoder.f5622n + 1;
        tXCVideoEncoder.f5622n = i;
        return i;
    }

    public TXCVideoEncoder(int i) {
        this.f5613e = 2;
        this.f5613e = i;
    }

    /* renamed from: a */
    public int m427a(TXSVideoEncoderParam tXSVideoEncoderParam) {
        int i;
        this.f5618j = tXSVideoEncoderParam;
        int m2968c = tXSVideoEncoderParam.enableBlackList ? TXCConfigCenter.m2988a().m2968c() : 2;
        if (this.f5613e == 1 && m2968c != 0) {
            this.f5609a = new TXCHWVideoEncoder();
            this.f5614f = 1;
            m429a(1008, "启动硬编", 1);
        } else if (this.f5613e == 3 && tXSVideoEncoderParam.width == 720 && tXSVideoEncoderParam.height == 1280 && m2968c != 0) {
            this.f5609a = new TXCHWVideoEncoder();
            this.f5614f = 1;
            m429a(1008, "启动硬编", 1);
        } else {
            this.f5609a = new TXCSWVideoEncoder();
            this.f5614f = 2;
            m429a(1008, "启动软编", 2);
        }
        setStatusValue(4004, Long.valueOf(this.f5614f));
        TXIVideoEncoder tXIVideoEncoder = this.f5609a;
        if (tXIVideoEncoder != null) {
            TXIVideoEncoderListener tXIVideoEncoderListener = this.f5610b;
            if (tXIVideoEncoderListener != null) {
                tXIVideoEncoder.setListener(tXIVideoEncoderListener);
            }
            int i2 = this.f5612d;
            if (i2 != 0) {
                this.f5609a.setBitrate(i2);
            }
            this.f5609a.setID(getID());
            i = this.f5609a.start(tXSVideoEncoderParam);
            if (i != 0) {
                return i;
            }
        } else {
            i = 10000002;
        }
        if (this.f5613e == 3) {
            this.f5619k = 0.0f;
            this.f5620l = 0.0f;
            this.f5621m = 0.0f;
            this.f5622n = 0;
            this.f5623o = TXCConfigCenter.m2988a().m2962f();
            m408d();
        }
        return i;
    }

    @Override // com.tencent.liteav.basic.module.TXCModule
    public void setID(String str) {
        super.setID(str);
        TXIVideoEncoder tXIVideoEncoder = this.f5609a;
        if (tXIVideoEncoder != null) {
            tXIVideoEncoder.setID(str);
        }
        setStatusValue(4004, Long.valueOf(this.f5614f));
    }

    /* renamed from: a */
    public EGLContext m432a(final int i, final int i2) {
        if (!this.f5626s) {
            this.f5626s = true;
            synchronized (f5606r) {
                StringBuilder sb = new StringBuilder();
                sb.append("CVGLThread");
                Integer num = f5606r;
                f5606r = Integer.valueOf(f5606r.intValue() + 1);
                sb.append(num);
                this.f5625q = new TXCThread(sb.toString());
            }
            final boolean[] zArr = new boolean[1];
            this.f5625q.m2866a(new Runnable() { // from class: com.tencent.liteav.videoencoder.b.1
                @Override // java.lang.Runnable
                public void run() {
                    TXCVideoEncoder.this.f5624p = EGL10Helper.m3082a(null, null, null, i, i2);
                    zArr[0] = TXCVideoEncoder.this.f5624p != null;
                }
            });
            if (!zArr[0]) {
                return null;
            }
            return this.f5624p.m3080c();
        }
        EGL10Helper eGL10Helper = this.f5624p;
        if (eGL10Helper == null) {
            return null;
        }
        return eGL10Helper.m3080c();
    }

    /* renamed from: a */
    protected void m418a(Runnable runnable) {
        synchronized (this.f5617i) {
            this.f5617i.add(runnable);
        }
    }

    /* renamed from: a */
    private boolean m417a(Queue<Runnable> queue) {
        synchronized (queue) {
            if (queue.isEmpty()) {
                return false;
            }
            Runnable poll = queue.poll();
            if (poll == null) {
                return false;
            }
            poll.run();
            return true;
        }
    }

    /* renamed from: a */
    public long m416a(final byte[] bArr, final int i, final int i2, final int i3, final long j) {
        if (this.f5624p == null) {
            return -1L;
        }
        this.f5625q.m2864b(new Runnable() { // from class: com.tencent.liteav.videoencoder.b.2
            @Override // java.lang.Runnable
            public void run() {
                if (TXCVideoEncoder.this.f5627t == null || TXCVideoEncoder.this.f5627t.m3013o() != i2 || TXCVideoEncoder.this.f5627t.m3012p() != i3) {
                    if (TXCVideoEncoder.this.f5627t != null) {
                        TXCVideoEncoder.this.f5627t.mo1351e();
                        TXCVideoEncoder.this.f5627t = null;
                    }
                    TXCVideoEncoder.this.f5627t = new TXCGPUI4202RGBAFilter(i);
                    if (!TXCVideoEncoder.this.f5627t.mo2653c()) {
                        TXCVideoEncoder.this.f5624p.m3081b();
                        TXCVideoEncoder.this.f5624p = null;
                        TXCVideoEncoder.this.f5627t = null;
                        return;
                    }
                    TXCVideoEncoder.this.f5627t.mo1353a(true);
                    TXCVideoEncoder.this.f5627t.mo1333a(i2, i3);
                }
                TXCVideoEncoder.this.f5627t.m2672a(bArr);
                GLES20.glViewport(0, 0, i2, i3);
                int m2671r = TXCVideoEncoder.this.f5627t.m2671r();
                GLES20.glFlush();
                TXCVideoEncoder tXCVideoEncoder = TXCVideoEncoder.this;
                tXCVideoEncoder.m431a(m2671r, tXCVideoEncoder.f5618j.width, TXCVideoEncoder.this.f5618j.height, j);
            }
        });
        return 0L;
    }

    /* renamed from: a */
    public void m434a() {
        TXCThread tXCThread = this.f5625q;
        if (tXCThread != null) {
            final EGL10Helper eGL10Helper = this.f5624p;
            tXCThread.m2864b(new Runnable() { // from class: com.tencent.liteav.videoencoder.b.3
                @Override // java.lang.Runnable
                public void run() {
                    TXCVideoEncoder.this.f5617i.clear();
                    if (TXCVideoEncoder.this.f5609a != null) {
                        TXCVideoEncoder.this.f5609a.stop();
                    }
                    if (TXCVideoEncoder.this.f5627t != null) {
                        TXCVideoEncoder.this.f5627t.mo1351e();
                        TXCVideoEncoder.this.f5627t = null;
                    }
                    EGL10Helper eGL10Helper2 = eGL10Helper;
                    if (eGL10Helper2 != null) {
                        eGL10Helper2.m3081b();
                    }
                }
            });
            this.f5625q = null;
            this.f5624p = null;
        } else {
            this.f5617i.clear();
            TXIVideoEncoder tXIVideoEncoder = this.f5609a;
            if (tXIVideoEncoder != null) {
                tXIVideoEncoder.stop();
            }
        }
        if (this.f5613e == 3) {
            this.f5619k = 0.0f;
            this.f5620l = 0.0f;
            this.f5621m = 0.0f;
            this.f5622n = 0;
            m406e();
        }
        this.f5610b = null;
        this.f5612d = 0;
    }

    /* renamed from: a */
    public long m431a(int i, int i2, int i3, long j) {
        do {
        } while (m417a(this.f5617i));
        TXIVideoEncoder tXIVideoEncoder = this.f5609a;
        if (tXIVideoEncoder != null) {
            return tXIVideoEncoder.pushVideoFrame(i, i2, i3, j);
        }
        return 10000002L;
    }

    /* renamed from: b */
    public long m414b(int i, int i2, int i3, long j) {
        do {
        } while (m417a(this.f5617i));
        TXIVideoEncoder tXIVideoEncoder = this.f5609a;
        if (tXIVideoEncoder != null) {
            return tXIVideoEncoder.pushVideoFrameSync(i, i2, i3, j);
        }
        return 10000002L;
    }

    /* renamed from: b */
    public void m415b() {
        TXIVideoEncoder tXIVideoEncoder = this.f5609a;
        if (tXIVideoEncoder != null) {
            tXIVideoEncoder.signalEOSAndFlush();
        }
    }

    /* renamed from: a */
    public void m428a(TXINotifyListener tXINotifyListener) {
        this.f5611c = new WeakReference<>(tXINotifyListener);
    }

    /* renamed from: a */
    public void m419a(TXIVideoEncoderListener tXIVideoEncoderListener) {
        this.f5610b = tXIVideoEncoderListener;
        m418a(new Runnable() { // from class: com.tencent.liteav.videoencoder.b.4
            @Override // java.lang.Runnable
            public void run() {
                if (TXCVideoEncoder.this.f5609a != null) {
                    TXCVideoEncoder.this.f5609a.setListener(TXCVideoEncoder.this.f5610b);
                }
            }
        });
    }

    /* renamed from: a */
    public void m433a(int i) {
        this.f5612d = i;
        m418a(new Runnable() { // from class: com.tencent.liteav.videoencoder.b.5
            @Override // java.lang.Runnable
            public void run() {
                if (TXCVideoEncoder.this.f5609a != null) {
                    TXCVideoEncoder.this.f5609a.setBitrate(TXCVideoEncoder.this.f5612d);
                }
            }
        });
    }

    /* renamed from: c */
    public long m411c() {
        TXIVideoEncoder tXIVideoEncoder = this.f5609a;
        if (tXIVideoEncoder != null) {
            return tXIVideoEncoder.getRealFPS();
        }
        return 0L;
    }

    /* renamed from: d */
    private void m408d() {
        if (this.f5616h == null) {
            this.f5616h = new C3687a(this);
        }
        this.f5615g = new Timer();
        this.f5615g.schedule(this.f5616h, 1000L, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: e */
    public void m406e() {
        Timer timer = this.f5615g;
        if (timer != null) {
            timer.cancel();
            this.f5615g = null;
        }
        if (this.f5616h != null) {
            this.f5616h = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public void m430a(int i, String str) {
        TXINotifyListener tXINotifyListener;
        WeakReference<TXINotifyListener> weakReference = this.f5611c;
        if (weakReference == null || (tXINotifyListener = weakReference.get()) == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt(TXCAVRoomConstants.EVT_ID, i);
        bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
        bundle.putCharSequence("EVT_MSG", str);
        tXINotifyListener.onNotifyEvent(i, bundle);
    }

    /* renamed from: a */
    private void m429a(int i, String str, int i2) {
        TXINotifyListener tXINotifyListener;
        WeakReference<TXINotifyListener> weakReference = this.f5611c;
        if (weakReference == null || (tXINotifyListener = weakReference.get()) == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt(TXCAVRoomConstants.EVT_ID, i);
        bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
        bundle.putCharSequence("EVT_MSG", str);
        bundle.putInt("EVT_PARAM1", i2);
        tXINotifyListener.onNotifyEvent(i, bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: f */
    public void m404f() {
        m418a(new Runnable() { // from class: com.tencent.liteav.videoencoder.b.6
            @Override // java.lang.Runnable
            public void run() {
                TXCVideoEncoder.this.m430a((int) TXLiveConstants.PUSH_WARNING_VIDEO_ENCODE_SW_SWITCH_HW, "软编切硬编");
                if (TXCVideoEncoder.this.f5609a != null) {
                    TXCVideoEncoder.this.f5609a.setListener(null);
                    TXCVideoEncoder.this.f5609a.stop();
                }
                TXCVideoEncoder.this.f5609a = new TXCHWVideoEncoder();
                TXCVideoEncoder.this.f5614f = 1;
                TXCVideoEncoder tXCVideoEncoder = TXCVideoEncoder.this;
                tXCVideoEncoder.setStatusValue(4004, Long.valueOf(tXCVideoEncoder.f5614f));
                TXCVideoEncoder.this.f5609a.start(TXCVideoEncoder.this.f5618j);
                if (TXCVideoEncoder.this.f5610b != null) {
                    TXCVideoEncoder.this.f5609a.setListener(TXCVideoEncoder.this.f5610b);
                }
                if (TXCVideoEncoder.this.f5612d != 0) {
                    TXCVideoEncoder.this.f5609a.setBitrate(TXCVideoEncoder.this.f5612d);
                }
                TXCVideoEncoder.this.f5609a.setID(TXCVideoEncoder.this.getID());
            }
        });
        TXCLog.m2911w("TXCVideoEncoder", "switchSWToHW");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: TXCVideoEncoder.java */
    /* renamed from: com.tencent.liteav.videoencoder.b$a */
    /* loaded from: classes3.dex */
    public static class C3687a extends TimerTask {

        /* renamed from: a */
        private WeakReference<TXCVideoEncoder> f5643a;

        public C3687a(TXCVideoEncoder tXCVideoEncoder) {
            this.f5643a = new WeakReference<>(tXCVideoEncoder);
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            TXCVideoEncoder tXCVideoEncoder;
            WeakReference<TXCVideoEncoder> weakReference = this.f5643a;
            if (weakReference == null || (tXCVideoEncoder = weakReference.get()) == null) {
                return;
            }
            if (tXCVideoEncoder.f5622n >= tXCVideoEncoder.f5623o) {
                if (TXCConfigCenter.m2988a().m2987a(tXCVideoEncoder.f5619k / tXCVideoEncoder.f5623o, tXCVideoEncoder.f5620l / tXCVideoEncoder.f5623o, tXCVideoEncoder.f5621m / tXCVideoEncoder.f5623o) && TXCConfigCenter.m2988a().m2968c() != 0) {
                    tXCVideoEncoder.m404f();
                }
                tXCVideoEncoder.m406e();
                return;
            }
            int[] m2894a = TXCSystemUtil.m2894a();
            TXCVideoEncoder.m398k(tXCVideoEncoder);
            tXCVideoEncoder.f5619k += m2894a[0] / 10;
            tXCVideoEncoder.f5620l += m2894a[1] / 10;
            tXCVideoEncoder.f5621m += (float) ((tXCVideoEncoder.m411c() * 100) / tXCVideoEncoder.f5618j.fps);
        }
    }
}
