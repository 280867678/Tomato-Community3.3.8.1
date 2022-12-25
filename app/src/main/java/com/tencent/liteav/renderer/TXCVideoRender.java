package com.tencent.liteav.renderer;

import android.graphics.SurfaceTexture;
import android.os.Build;
import android.view.Surface;
import android.view.TextureView;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.module.TXCModule;
import com.tencent.liteav.basic.p108d.TXINotifyListener;
import com.tencent.liteav.basic.p109e.TXCGLSurfaceRenderThread;
import com.tencent.liteav.basic.util.TXCSystemUtil;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.rtmp.TXLiveConstants;
import java.lang.ref.WeakReference;
import javax.microedition.khronos.egl.EGLContext;

/* renamed from: com.tencent.liteav.renderer.f */
/* loaded from: classes3.dex */
public class TXCVideoRender extends TXCModule implements TextureView.SurfaceTextureListener {

    /* renamed from: a */
    private static final float[] f5137a = {1.0f, 0.0f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f};

    /* renamed from: b */
    private SurfaceTexture f5138b;

    /* renamed from: c */
    protected TextureView f5139c;

    /* renamed from: d */
    protected TXCTextureViewWrapper f5140d;

    /* renamed from: l */
    protected TXIVideoRenderListener f5148l;

    /* renamed from: m */
    WeakReference<TXINotifyListener> f5149m;

    /* renamed from: n */
    private long f5150n;

    /* renamed from: p */
    private TXCGLSurfaceRenderThread f5152p;

    /* renamed from: q */
    private TXTweenFilter f5153q;

    /* renamed from: r */
    private Surface f5154r;

    /* renamed from: t */
    private int f5156t;

    /* renamed from: u */
    private int[] f5157u;

    /* renamed from: e */
    protected int f5141e = 0;

    /* renamed from: f */
    protected int f5142f = 0;

    /* renamed from: g */
    protected int f5143g = 0;

    /* renamed from: h */
    protected int f5144h = 0;

    /* renamed from: o */
    private int f5151o = 800;

    /* renamed from: s */
    private int f5155s = 0;

    /* renamed from: i */
    protected volatile int f5145i = -1;

    /* renamed from: j */
    protected int f5146j = 0;

    /* renamed from: k */
    protected int f5147k = 0;

    /* renamed from: w */
    private boolean f5159w = false;

    /* renamed from: x */
    private C3617a f5160x = new C3617a();

    /* renamed from: v */
    private boolean f5158v = false;

    /* compiled from: TXCVideoRender.java */
    /* renamed from: com.tencent.liteav.renderer.f$a */
    /* loaded from: classes3.dex */
    public static class C3617a {

        /* renamed from: a */
        public long f5164a;

        /* renamed from: b */
        public long f5165b;

        /* renamed from: c */
        public long f5166c;

        /* renamed from: d */
        public long f5167d;

        /* renamed from: e */
        public long f5168e;

        /* renamed from: f */
        public long f5169f;

        /* renamed from: g */
        public long f5170g;

        /* renamed from: h */
        public long f5171h;

        /* renamed from: i */
        public long f5172i;

        /* renamed from: j */
        public long f5173j;

        /* renamed from: k */
        public int f5174k;

        /* renamed from: l */
        public int f5175l;
    }

    /* renamed from: a */
    public SurfaceTexture mo898a() {
        return null;
    }

    /* renamed from: a */
    protected void mo891a(SurfaceTexture surfaceTexture) {
    }

    /* renamed from: b */
    protected void mo879b(SurfaceTexture surfaceTexture) {
    }

    /* renamed from: g */
    protected void mo873g() {
    }

    /* renamed from: h */
    protected void mo872h() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: o */
    public void m865o() {
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    /* renamed from: a */
    public void m893a(long j) {
        this.f5150n = j;
    }

    /* renamed from: a */
    public void m897a(int i) {
        if (i > 0) {
            this.f5151o = i;
        }
    }

    /* renamed from: a */
    public void m885a(TXIVideoRenderListener tXIVideoRenderListener) {
        this.f5148l = tXIVideoRenderListener;
    }

    /* renamed from: a */
    public void m887a(TXINotifyListener tXINotifyListener) {
        this.f5149m = new WeakReference<>(tXINotifyListener);
    }

    /* renamed from: a */
    public void m888a(TextureView textureView) {
        m877b(textureView);
    }

    /* renamed from: a */
    public void m889a(Surface surface) {
        m878b(surface);
    }

    /* renamed from: a */
    public void mo892a(long j, int i, int i2) {
        mo896a(i, i2);
        m883b();
    }

    /* renamed from: a */
    public void m890a(SurfaceTexture surfaceTexture, int i, int i2) {
        mo896a(i, i2);
        m883b();
    }

    /* renamed from: a */
    public void mo894a(int i, int i2, int i3, boolean z, int i4) {
        mo896a(i2, i3);
    }

    /* renamed from: i */
    public void m871i() {
        if (Build.VERSION.SDK_INT >= 21) {
            this.f5159w = true;
        } else {
            this.f5159w = false;
        }
        this.f5158v = false;
        m862r();
    }

    /* renamed from: j */
    public void m870j() {
        this.f5158v = false;
        this.f5159w = false;
        this.f5157u = null;
        if (this.f5145i == 1) {
            this.f5145i = -1;
            mo872h();
            synchronized (this) {
                if (this.f5152p != null) {
                    this.f5152p.m3071a();
                    this.f5152p = null;
                }
            }
        }
    }

    /* renamed from: b */
    public void m881b(int i, int i2) {
        mo896a(i, i2);
    }

    /* renamed from: b */
    public void m882b(int i) {
        this.f5156t = i;
        TXCTextureViewWrapper tXCTextureViewWrapper = this.f5140d;
        if (tXCTextureViewWrapper != null) {
            tXCTextureViewWrapper.m910a(i);
        }
    }

    /* renamed from: c */
    public void m875c(int i) {
        this.f5155s = i;
        TXCTextureViewWrapper tXCTextureViewWrapper = this.f5140d;
        if (tXCTextureViewWrapper != null) {
            tXCTextureViewWrapper.m903c(i);
        }
    }

    /* renamed from: k */
    public int m869k() {
        TextureView textureView = this.f5139c;
        if (textureView != null) {
            return textureView.getWidth();
        }
        if (this.f5154r == null) {
            return 0;
        }
        return this.f5146j;
    }

    /* renamed from: l */
    public int m868l() {
        TextureView textureView = this.f5139c;
        if (textureView != null) {
            return textureView.getHeight();
        }
        if (this.f5154r == null) {
            return 0;
        }
        return this.f5147k;
    }

    /* renamed from: m */
    public int m867m() {
        return this.f5143g;
    }

    /* renamed from: n */
    public int m866n() {
        return this.f5144h;
    }

    /* renamed from: b */
    private void m877b(TextureView textureView) {
        TextureView textureView2;
        boolean z = false;
        this.f5145i = 0;
        if ((this.f5139c == null && textureView != null) || ((textureView2 = this.f5139c) != null && !textureView2.equals(textureView))) {
            z = true;
        }
        TXCLog.m2911w("TXCVideoRender", "play:vrender: set video view @old=" + this.f5139c + ",new=" + textureView);
        if (z) {
            TextureView textureView3 = this.f5139c;
            if (textureView3 != null && this.f5138b == null) {
                mo879b(textureView3.getSurfaceTexture());
            }
            this.f5139c = textureView;
            TextureView textureView4 = this.f5139c;
            if (textureView4 == null) {
                return;
            }
            this.f5141e = textureView4.getWidth();
            this.f5142f = this.f5139c.getHeight();
            this.f5140d = new TXCTextureViewWrapper(this.f5139c);
            this.f5140d.m905b(this.f5143g, this.f5144h);
            this.f5140d.m909a(this.f5141e, this.f5142f);
            this.f5139c.setSurfaceTextureListener(this);
            if (this.f5138b != null) {
                if (Build.VERSION.SDK_INT < 16) {
                    return;
                }
                SurfaceTexture surfaceTexture = this.f5139c.getSurfaceTexture();
                SurfaceTexture surfaceTexture2 = this.f5138b;
                if (surfaceTexture == surfaceTexture2) {
                    return;
                }
                this.f5139c.setSurfaceTexture(surfaceTexture2);
            } else if (!this.f5139c.isAvailable()) {
            } else {
                mo891a(this.f5139c.getSurfaceTexture());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: a */
    public void mo896a(int i, int i2) {
        if (this.f5143g == i && this.f5144h == i2) {
            return;
        }
        if (this.f5143g == i && this.f5144h == i2) {
            return;
        }
        this.f5143g = i;
        this.f5144h = i2;
        TXCTextureViewWrapper tXCTextureViewWrapper = this.f5140d;
        if (tXCTextureViewWrapper == null) {
            return;
        }
        tXCTextureViewWrapper.m905b(this.f5143g, this.f5144h);
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        TXCLog.m2911w("TXCVideoRender", "play:vrender: texture available @" + surfaceTexture);
        this.f5141e = i;
        this.f5142f = i2;
        TXCTextureViewWrapper tXCTextureViewWrapper = this.f5140d;
        if (tXCTextureViewWrapper != null) {
            tXCTextureViewWrapper.m909a(this.f5141e, this.f5142f);
        }
        if (this.f5138b != null) {
            if (Build.VERSION.SDK_INT >= 16) {
                SurfaceTexture surfaceTexture2 = this.f5139c.getSurfaceTexture();
                SurfaceTexture surfaceTexture3 = this.f5138b;
                if (surfaceTexture2 != surfaceTexture3) {
                    this.f5139c.setSurfaceTexture(surfaceTexture3);
                }
            }
            this.f5138b = null;
            return;
        }
        mo891a(surfaceTexture);
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
        TXCLog.m2911w("TXCVideoRender", "play:vrender: texture size change new:" + i + "," + i2 + " old:" + this.f5141e + "," + this.f5142f);
        this.f5141e = i;
        this.f5142f = i2;
        TXCTextureViewWrapper tXCTextureViewWrapper = this.f5140d;
        if (tXCTextureViewWrapper != null) {
            tXCTextureViewWrapper.m909a(this.f5141e, this.f5142f);
        }
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        try {
            TXCLog.m2911w("TXCVideoRender", "play:vrender:  onSurfaceTextureDestroyed when need save texture : " + this.f5159w);
            if (this.f5159w) {
                this.f5138b = surfaceTexture;
            } else {
                this.f5160x.f5164a = 0L;
                mo879b(surfaceTexture);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.f5138b == null;
    }

    /* renamed from: b */
    private void m878b(Surface surface) {
        TXCLog.m2913i("TXCVideoRender", "surface-render: set surface " + surface);
        this.f5154r = surface;
        this.f5145i = 1;
        if (surface != null) {
            mo873g();
            return;
        }
        synchronized (this) {
            if (this.f5152p != null) {
                this.f5152p.m3071a();
                this.f5152p = null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: p */
    public void m864p() {
        synchronized (this) {
            if (this.f5152p != null) {
                this.f5152p.m3071a();
                this.f5152p = null;
            }
        }
        TXTweenFilter tXTweenFilter = this.f5153q;
        if (tXTweenFilter != null) {
            tXTweenFilter.m847c();
            this.f5153q = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: a */
    public void m884a(EGLContext eGLContext, int i, float[] fArr, boolean z) {
        if (this.f5145i == 1) {
            int[] m895a = m895a(i, this.f5143g, this.f5144h, z);
            this.f5157u = m895a;
            int i2 = m895a[0];
            int i3 = m895a[1];
            int i4 = m895a[2];
            synchronized (this) {
                if (this.f5154r != null) {
                    if (this.f5152p != null && this.f5152p.m3064b() != this.f5154r) {
                        this.f5152p.m3071a();
                        this.f5152p = null;
                    }
                    if (this.f5152p == null && this.f5145i == 1) {
                        this.f5152p = new TXCGLSurfaceRenderThread();
                        this.f5152p.m3065a(eGLContext, this.f5154r);
                    }
                    if (this.f5152p != null && this.f5145i == 1) {
                        this.f5152p.m3069a(i2, false, 0, this.f5146j, this.f5147k, i3, i4, false);
                    }
                } else if (this.f5152p != null) {
                    this.f5152p.m3071a();
                    this.f5152p = null;
                }
            }
        }
    }

    /* renamed from: a */
    private int[] m895a(int i, int i2, int i3, boolean z) {
        TXTweenFilter tXTweenFilter = this.f5153q;
        if (tXTweenFilter != null && tXTweenFilter.m858a() != z) {
            this.f5153q.m847c();
            this.f5153q = null;
        }
        if (this.f5153q == null) {
            this.f5153q = new TXTweenFilter(Boolean.valueOf(z));
            this.f5153q.m851b();
        }
        if (!z) {
            this.f5153q.m852a(f5137a);
        }
        int i4 = this.f5146j;
        int i5 = this.f5147k;
        if (this.f5156t == 0) {
            this.f5153q.m857a(TXTweenFilter.f5176a);
        } else {
            this.f5153q.m857a(TXTweenFilter.f5177b);
        }
        this.f5153q.m850b(this.f5155s);
        this.f5153q.m849b(i2, i3);
        this.f5153q.m856a(i4, i5);
        return new int[]{this.f5153q.m844d(i), i4, i5};
    }

    /* renamed from: c */
    public void m874c(final int i, final int i2) {
        if (i == this.f5146j && i2 == this.f5147k) {
            return;
        }
        if (this.f5152p != null && this.f5145i == 1 && this.f5157u != null) {
            this.f5152p.m3066a(new Runnable() { // from class: com.tencent.liteav.renderer.f.1
                @Override // java.lang.Runnable
                public void run() {
                    TXCVideoRender tXCVideoRender = TXCVideoRender.this;
                    tXCVideoRender.f5146j = i;
                    tXCVideoRender.f5147k = i2;
                    if (tXCVideoRender.f5152p != null) {
                        TXCGLSurfaceRenderThread tXCGLSurfaceRenderThread = TXCVideoRender.this.f5152p;
                        int i3 = TXCVideoRender.this.f5157u[0];
                        TXCVideoRender tXCVideoRender2 = TXCVideoRender.this;
                        tXCGLSurfaceRenderThread.m3069a(i3, false, 0, tXCVideoRender2.f5146j, tXCVideoRender2.f5147k, tXCVideoRender2.f5157u[1], TXCVideoRender.this.f5157u[2], true);
                    }
                }
            });
            return;
        }
        this.f5146j = i;
        this.f5147k = i2;
    }

    /* renamed from: q */
    public C3617a m863q() {
        return this.f5160x;
    }

    /* renamed from: r */
    public void m862r() {
        C3617a c3617a = this.f5160x;
        c3617a.f5164a = 0L;
        c3617a.f5165b = 0L;
        c3617a.f5166c = 0L;
        c3617a.f5167d = 0L;
        c3617a.f5168e = 0L;
        c3617a.f5169f = 0L;
        c3617a.f5170g = 0L;
        c3617a.f5171h = 0L;
        c3617a.f5172i = 0L;
        c3617a.f5174k = 0;
        c3617a.f5175l = 0;
        setStatusValue(6001, 0L);
        setStatusValue(6002, Double.valueOf(0.0d));
        setStatusValue(6003, 0L);
        setStatusValue(6005, 0L);
        setStatusValue(6006, 0L);
        setStatusValue(6004, 0L);
    }

    /* renamed from: b */
    private long m880b(long j) {
        long timeTick = TXCTimeUtil.getTimeTick();
        if (j > timeTick) {
            return 0L;
        }
        return timeTick - j;
    }

    /* renamed from: b */
    private void m883b() {
        C3617a c3617a;
        if (!this.f5158v) {
            TXCSystemUtil.m2883a(this.f5149m, this.f5150n, 2003, "渲染首个视频数据包(IDR)");
            setStatusValue(6001, Long.valueOf(TXCTimeUtil.getTimeTick()));
            this.f5158v = true;
        }
        C3617a c3617a2 = this.f5160x;
        c3617a2.f5166c++;
        if (c3617a2.f5164a == 0) {
            c3617a2.f5164a = TXCTimeUtil.getTimeTick();
        } else {
            long timeTick = TXCTimeUtil.getTimeTick() - this.f5160x.f5164a;
            if (timeTick >= 1000) {
                setStatusValue(6002, Double.valueOf(((c3617a.f5166c - c3617a.f5165b) * 1000.0d) / timeTick));
                C3617a c3617a3 = this.f5160x;
                c3617a3.f5165b = c3617a3.f5166c;
                c3617a3.f5164a += timeTick;
            }
        }
        C3617a c3617a4 = this.f5160x;
        long j = c3617a4.f5167d;
        if (j != 0) {
            c3617a4.f5172i = m880b(j);
            C3617a c3617a5 = this.f5160x;
            if (c3617a5.f5172i > 500) {
                c3617a5.f5168e++;
                setStatusValue(6003, Long.valueOf(c3617a5.f5168e));
                C3617a c3617a6 = this.f5160x;
                long j2 = c3617a6.f5172i;
                if (j2 > c3617a6.f5171h) {
                    c3617a6.f5171h = j2;
                    setStatusValue(6005, Long.valueOf(c3617a6.f5171h));
                }
                C3617a c3617a7 = this.f5160x;
                c3617a7.f5170g += c3617a7.f5172i;
                setStatusValue(6006, Long.valueOf(c3617a7.f5170g));
                TXCLog.m2911w("TXCVideoRender", "render frame count:" + this.f5160x.f5166c + " block time:" + this.f5160x.f5172i + "> 500");
            }
            if (this.f5160x.f5172i > this.f5151o) {
                TXCLog.m2911w("TXCVideoRender", "render frame count:" + this.f5160x.f5166c + " block time:" + this.f5160x.f5172i + "> " + this.f5151o);
            }
            C3617a c3617a8 = this.f5160x;
            if (c3617a8.f5172i > 1000) {
                c3617a8.f5169f++;
                setStatusValue(6004, Long.valueOf(c3617a8.f5169f));
                TXCLog.m2911w("TXCVideoRender", "render frame count:" + this.f5160x.f5166c + " block time:" + this.f5160x.f5172i + "> 1000");
                TXCSystemUtil.m2883a(this.f5149m, this.f5150n, (int) TXLiveConstants.PLAY_WARNING_VIDEO_PLAY_LAG, "当前视频播放出现卡顿" + this.f5160x.f5172i + "ms");
            }
        }
        this.f5160x.f5167d = TXCTimeUtil.getTimeTick();
        C3617a c3617a9 = this.f5160x;
        c3617a9.f5175l = this.f5144h;
        c3617a9.f5174k = this.f5143g;
    }
}
