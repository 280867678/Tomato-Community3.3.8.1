package com.tencent.liteav.renderer;

import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.view.TextureView;
import com.tencent.liteav.basic.log.TXCLog;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import javax.microedition.khronos.egl.EGLContext;

/* renamed from: com.tencent.liteav.renderer.a */
/* loaded from: classes3.dex */
public class TXCGLRender extends TXCVideoRender implements SurfaceTexture.OnFrameAvailableListener {

    /* renamed from: a */
    TXIVideoRenderTextureListener f5076a;

    /* renamed from: b */
    AbstractC3612a f5077b;

    /* renamed from: s */
    private TXCGLRenderThread f5083s;

    /* renamed from: t */
    private SurfaceTexture f5084t;

    /* renamed from: u */
    private TXCOesTextureRender f5085u;

    /* renamed from: v */
    private boolean f5086v;

    /* renamed from: x */
    private TXCOesTextureRender f5088x;

    /* renamed from: z */
    private TXCYuvTextureRender f5090z;

    /* renamed from: n */
    private final int f5078n = 0;

    /* renamed from: o */
    private final int f5079o = 0;

    /* renamed from: p */
    private final int f5080p = 0;

    /* renamed from: q */
    private final int f5081q = 0;

    /* renamed from: r */
    private Object f5082r = new Object();

    /* renamed from: y */
    private ArrayList<Long> f5089y = new ArrayList<>();

    /* renamed from: A */
    private final Queue<Runnable> f5075A = new LinkedList();

    /* renamed from: w */
    private float[] f5087w = new float[16];

    /* compiled from: TXCGLRender.java */
    /* renamed from: com.tencent.liteav.renderer.a$a */
    /* loaded from: classes3.dex */
    public interface AbstractC3612a {
        /* renamed from: d */
        void mo935d(int i);
    }

    @Override // com.tencent.liteav.renderer.TXCVideoRender, android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    /* renamed from: a */
    public void m944a(TXIVideoRenderTextureListener tXIVideoRenderTextureListener) {
        this.f5076a = tXIVideoRenderTextureListener;
    }

    /* renamed from: a */
    public void m945a(AbstractC3612a abstractC3612a) {
        TXCYuvTextureRender tXCYuvTextureRender;
        this.f5077b = abstractC3612a;
        if (abstractC3612a == null || (tXCYuvTextureRender = this.f5090z) == null) {
            return;
        }
        tXCYuvTextureRender.setHasFrameBuffer(this.f5143g, this.f5144h);
    }

    @Override // com.tencent.liteav.renderer.TXCVideoRender
    /* renamed from: a */
    public void mo892a(long j, int i, int i2) {
        synchronized (this) {
            this.f5089y.add(Long.valueOf(j));
        }
        super.mo892a(j, i, i2);
    }

    @Override // com.tencent.liteav.renderer.TXCVideoRender
    /* renamed from: a */
    public void mo894a(int i, int i2, int i3, boolean z, int i4) {
        GLES20.glViewport(0, 0, m869k(), m868l());
        TXCOesTextureRender tXCOesTextureRender = this.f5088x;
        if (tXCOesTextureRender != null) {
            tXCOesTextureRender.m919a(i, z, i4);
        }
        super.mo894a(i, i2, i3, z, i4);
    }

    @Override // com.tencent.liteav.renderer.TXCVideoRender
    /* renamed from: a */
    public SurfaceTexture mo898a() {
        return this.f5084t;
    }

    /* renamed from: b */
    public EGLContext m942b() {
        EGLContext m934a;
        synchronized (this.f5082r) {
            m934a = this.f5083s != null ? this.f5083s.m934a() : null;
        }
        return m934a;
    }

    @Override // com.tencent.liteav.renderer.TXCVideoRender
    /* renamed from: a */
    protected void mo891a(SurfaceTexture surfaceTexture) {
        mo873g();
    }

    @Override // com.tencent.liteav.renderer.TXCVideoRender
    /* renamed from: b */
    protected void mo879b(SurfaceTexture surfaceTexture) {
        mo872h();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tencent.liteav.renderer.TXCVideoRender
    /* renamed from: a */
    public void mo896a(int i, int i2) {
        super.mo896a(i, i2);
        TXCYuvTextureRender tXCYuvTextureRender = this.f5090z;
        if (tXCYuvTextureRender != null) {
            tXCYuvTextureRender.setVideoSize(i, i2);
        }
        TXCOesTextureRender tXCOesTextureRender = this.f5085u;
        if (tXCOesTextureRender != null) {
            tXCOesTextureRender.m921a(i, i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: c */
    public void m941c() {
        TXCYuvTextureRender tXCYuvTextureRender;
        m937s();
        TXCTextureViewWrapper tXCTextureViewWrapper = this.f5140d;
        if (tXCTextureViewWrapper != null) {
            tXCTextureViewWrapper.m909a(this.f5141e, this.f5142f);
            this.f5140d.m905b(this.f5143g, this.f5144h);
        }
        TXCOesTextureRender tXCOesTextureRender = this.f5085u;
        if (tXCOesTextureRender != null) {
            tXCOesTextureRender.m915b();
            this.f5084t = new SurfaceTexture(this.f5085u.m922a());
            this.f5084t.setOnFrameAvailableListener(this);
        }
        TXCYuvTextureRender tXCYuvTextureRender2 = this.f5090z;
        if (tXCYuvTextureRender2 != null) {
            tXCYuvTextureRender2.createTexture();
        }
        if (this.f5077b != null && (tXCYuvTextureRender = this.f5090z) != null) {
            tXCYuvTextureRender.setHasFrameBuffer(this.f5143g, this.f5144h);
        }
        TXCOesTextureRender tXCOesTextureRender2 = this.f5088x;
        if (tXCOesTextureRender2 != null) {
            tXCOesTextureRender2.m915b();
        }
        TXIVideoRenderListener tXIVideoRenderListener = this.f5148l;
        if (tXIVideoRenderListener != null) {
            tXIVideoRenderListener.mo861a(this.f5084t);
        }
    }

    /* renamed from: s */
    private void m937s() {
        this.f5085u = new TXCOesTextureRender(true);
        this.f5090z = new TXCYuvTextureRender();
        this.f5088x = new TXCOesTextureRender(false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: d */
    public void m940d() {
        try {
            if (this.f5148l != null) {
                this.f5148l.mo860b(this.f5084t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        TXCOesTextureRender tXCOesTextureRender = this.f5085u;
        if (tXCOesTextureRender != null) {
            tXCOesTextureRender.m913c();
            this.f5085u = null;
        }
        TXCYuvTextureRender tXCYuvTextureRender = this.f5090z;
        if (tXCYuvTextureRender != null) {
            tXCYuvTextureRender.onSurfaceDestroy();
            this.f5090z = null;
        }
        TXCOesTextureRender tXCOesTextureRender2 = this.f5088x;
        if (tXCOesTextureRender2 != null) {
            tXCOesTextureRender2.m913c();
            this.f5088x = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: e */
    public boolean m939e() {
        do {
        } while (m943a(this.f5075A));
        return m936t();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: f */
    public SurfaceTexture m938f() {
        TextureView textureView = this.f5139c;
        if (textureView != null) {
            return textureView.getSurfaceTexture();
        }
        return null;
    }

    @Override // com.tencent.liteav.renderer.TXCVideoRender
    /* renamed from: g */
    protected void mo873g() {
        synchronized (this.f5082r) {
            if (this.f5083s == null) {
                this.f5083s = new TXCGLRenderThread(new WeakReference(this));
                this.f5083s.start();
                TXCLog.m2911w("TXCVideoRender", "play:vrender: start render thread");
            } else {
                TXCLog.m2911w("TXCVideoRender", "play:vrender: render thread is running");
            }
        }
    }

    @Override // com.tencent.liteav.renderer.TXCVideoRender
    /* renamed from: h */
    protected void mo872h() {
        synchronized (this.f5082r) {
            if (this.f5083s != null) {
                this.f5083s.m932b();
                this.f5083s = null;
                TXCLog.m2911w("TXCVideoRender", "play:vrender: quit render thread");
            }
        }
        this.f5084t = null;
    }

    /* renamed from: t */
    private boolean m936t() {
        long longValue;
        boolean z;
        TXCYuvTextureRender tXCYuvTextureRender;
        int i;
        synchronized (this) {
            if (this.f5086v) {
                z = this.f5086v;
                this.f5086v = false;
                longValue = 0;
            } else if (this.f5089y.isEmpty()) {
                return false;
            } else {
                longValue = this.f5089y.get(0).longValue();
                this.f5089y.remove(0);
                z = false;
            }
            GLES20.glViewport(0, 0, m869k(), m868l());
            EGLContext m942b = this.f5145i == 1 ? m942b() : null;
            if (z) {
                SurfaceTexture surfaceTexture = this.f5084t;
                if (surfaceTexture != null) {
                    surfaceTexture.updateTexImage();
                    this.f5084t.getTransformMatrix(this.f5087w);
                }
                TXIVideoRenderTextureListener tXIVideoRenderTextureListener = this.f5076a;
                if (tXIVideoRenderTextureListener != null) {
                    tXIVideoRenderTextureListener.mo859a(this.f5085u.m922a(), this.f5087w);
                } else {
                    TXCOesTextureRender tXCOesTextureRender = this.f5085u;
                    if (tXCOesTextureRender != null) {
                        tXCOesTextureRender.m918a(this.f5084t);
                    }
                }
                if (this.f5145i == 1) {
                    m884a(m942b, this.f5085u.m922a(), this.f5087w, true);
                }
            } else if (longValue != 0 && (tXCYuvTextureRender = this.f5090z) != null) {
                if (this.f5077b != null) {
                    tXCYuvTextureRender.setHasFrameBuffer(this.f5143g, this.f5144h);
                    i = this.f5090z.drawToTexture(longValue);
                    this.f5077b.mo935d(i);
                } else {
                    if (this.f5145i == 0) {
                        this.f5090z.drawFrame(longValue);
                    }
                    i = -1;
                }
                if (this.f5145i == 1) {
                    if (i == -1) {
                        this.f5090z.setHasFrameBuffer(this.f5143g, this.f5144h);
                        i = this.f5090z.drawToTexture(longValue);
                    }
                    m884a(m942b, i, (float[]) null, false);
                }
            }
            return true;
        }
    }

    /* renamed from: a */
    private boolean m943a(Queue<Runnable> queue) {
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

    @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        synchronized (this) {
            this.f5086v = true;
        }
    }

    @Override // com.tencent.liteav.basic.module.TXCModule
    public void finalize() throws Throwable {
        super.finalize();
        TXCLog.m2911w("TXCVideoRender", "play:vrender: quit render thread when finalize");
        try {
            mo872h();
        } catch (Exception unused) {
        }
    }
}
