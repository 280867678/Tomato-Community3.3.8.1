package com.tencent.liteav;

import android.content.Context;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p108d.TXINotifyListener;
import com.tencent.liteav.basic.p111g.TXSVFrame;
import com.tencent.liteav.basic.util.TXCSystemUtil;
import com.tencent.liteav.screencapture.TXCScreenCapture;
import com.tencent.liteav.screencapture.TXIScreenCaptureListener;
import java.util.LinkedList;
import java.util.Queue;
import javax.microedition.khronos.egl.EGLContext;

/* renamed from: com.tencent.liteav.l */
/* loaded from: classes3.dex */
public class TXCScreenCaptureSource implements TXICaptureSource, TXIScreenCaptureListener {

    /* renamed from: a */
    private static final String f4626a = "l";

    /* renamed from: b */
    private TXICaptureSourceListener f4627b;

    /* renamed from: c */
    private TXCScreenCapture f4628c;

    /* renamed from: e */
    private int f4630e;

    /* renamed from: f */
    private int f4631f;

    /* renamed from: g */
    private int f4632g;

    /* renamed from: h */
    private int f4633h;

    /* renamed from: i */
    private int f4634i;

    /* renamed from: d */
    private EGLContext f4629d = null;

    /* renamed from: j */
    private final Queue<Runnable> f4635j = new LinkedList();

    @Override // com.tencent.liteav.TXICaptureSource
    /* renamed from: a */
    public void mo1050a(float f) {
    }

    @Override // com.tencent.liteav.TXICaptureSource
    /* renamed from: a */
    public void mo1049a(float f, float f2) {
    }

    @Override // com.tencent.liteav.TXICaptureSource
    /* renamed from: a */
    public void mo1045a(TXSVFrame tXSVFrame) {
    }

    @Override // com.tencent.liteav.TXICaptureSource
    /* renamed from: a */
    public boolean mo1048a(int i) {
        return false;
    }

    @Override // com.tencent.liteav.TXICaptureSource
    /* renamed from: b */
    public void mo1040b(int i) {
    }

    @Override // com.tencent.liteav.TXICaptureSource
    /* renamed from: b */
    public void mo1039b(boolean z) {
    }

    @Override // com.tencent.liteav.TXICaptureSource
    /* renamed from: c */
    public void mo1037c(int i) {
    }

    @Override // com.tencent.liteav.TXICaptureSource
    /* renamed from: c */
    public void mo1036c(boolean z) {
    }

    @Override // com.tencent.liteav.TXICaptureSource
    /* renamed from: d */
    public void mo1034d(int i) {
    }

    @Override // com.tencent.liteav.TXICaptureSource
    /* renamed from: d */
    public boolean mo1035d() {
        return true;
    }

    @Override // com.tencent.liteav.TXICaptureSource
    /* renamed from: d */
    public boolean mo1033d(boolean z) {
        return false;
    }

    @Override // com.tencent.liteav.TXICaptureSource
    /* renamed from: e */
    public int mo1032e() {
        return 0;
    }

    @Override // com.tencent.liteav.TXICaptureSource
    /* renamed from: e */
    public void mo1031e(boolean z) {
    }

    public TXCScreenCaptureSource(Context context, TXCLivePushConfig tXCLivePushConfig) {
        this.f4628c = null;
        this.f4628c = new TXCScreenCapture(context, tXCLivePushConfig.f4292N);
        this.f4628c.m805a((TXIScreenCaptureListener) this);
        boolean m1465a = tXCLivePushConfig.m1465a();
        this.f4630e = tXCLivePushConfig.f4300h;
        if (tXCLivePushConfig.f4293a > 1280 || tXCLivePushConfig.f4294b > 1280) {
            int i = tXCLivePushConfig.f4293a;
            int i2 = tXCLivePushConfig.f4294b;
            this.f4631f = m1465a ? Math.max(i, i2) : Math.min(i, i2);
            this.f4632g = m1465a ? Math.min(tXCLivePushConfig.f4293a, tXCLivePushConfig.f4294b) : Math.max(tXCLivePushConfig.f4293a, tXCLivePushConfig.f4294b);
        } else {
            int i3 = 720;
            this.f4631f = m1465a ? 1280 : 720;
            this.f4632g = !m1465a ? 1280 : i3;
        }
        this.f4633h = tXCLivePushConfig.f4293a;
        this.f4634i = tXCLivePushConfig.f4294b;
    }

    @Override // com.tencent.liteav.TXICaptureSource
    /* renamed from: a */
    public void mo1051a() {
        this.f4628c.m812a(this.f4631f, this.f4632g, this.f4630e);
        this.f4628c.m802a(true);
    }

    @Override // com.tencent.liteav.TXICaptureSource
    /* renamed from: a */
    public void mo1042a(boolean z) {
        this.f4628c.m804a((Object) null);
        this.f4628c.m802a(false);
    }

    @Override // com.tencent.liteav.TXICaptureSource
    /* renamed from: c */
    public void mo1038c() {
        this.f4628c.m802a(false);
    }

    @Override // com.tencent.liteav.TXICaptureSource
    /* renamed from: b */
    public void mo1041b() {
        this.f4628c.m802a(true);
    }

    @Override // com.tencent.liteav.TXICaptureSource
    /* renamed from: f */
    public EGLContext mo1030f() {
        return this.f4629d;
    }

    @Override // com.tencent.liteav.TXICaptureSource
    /* renamed from: a */
    public void mo1044a(TXICaptureSourceListener tXICaptureSourceListener) {
        this.f4627b = tXICaptureSourceListener;
    }

    @Override // com.tencent.liteav.TXICaptureSource
    /* renamed from: a */
    public void mo1043a(Runnable runnable) {
        TXCScreenCapture tXCScreenCapture = this.f4628c;
        if (tXCScreenCapture != null) {
            tXCScreenCapture.m803a(runnable);
        }
    }

    @Override // com.tencent.liteav.TXICaptureSource
    /* renamed from: a */
    public void mo1046a(TXINotifyListener tXINotifyListener) {
        TXCScreenCapture tXCScreenCapture = this.f4628c;
        if (tXCScreenCapture != null) {
            tXCScreenCapture.m807a(tXINotifyListener);
        }
    }

    @Override // com.tencent.liteav.TXICaptureSource
    /* renamed from: a */
    public void mo1047a(int i, int i2) {
        this.f4633h = i;
        this.f4634i = i2;
    }

    /* renamed from: a */
    private boolean m1288a(Queue<Runnable> queue) {
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

    @Override // com.tencent.liteav.screencapture.TXIScreenCaptureListener
    /* renamed from: a */
    public void mo757a(int i, EGLContext eGLContext) {
        if (i == 0) {
            this.f4629d = eGLContext;
            return;
        }
        this.f4629d = null;
        TXCLog.m2914e(f4626a, "Start screen capture failed");
    }

    @Override // com.tencent.liteav.screencapture.TXIScreenCaptureListener
    /* renamed from: a */
    public void mo758a(int i, int i2, int i3, int i4, long j) {
        do {
        } while (m1288a(this.f4635j));
        if (i == 0) {
            if (this.f4627b == null) {
                return;
            }
            m1287f(i3 < i4);
            TXSVFrame tXSVFrame = new TXSVFrame();
            tXSVFrame.f2717d = i3;
            tXSVFrame.f2718e = i4;
            int i5 = this.f4633h;
            tXSVFrame.f2719f = i5;
            int i6 = this.f4634i;
            tXSVFrame.f2720g = i6;
            tXSVFrame.f2714a = i2;
            tXSVFrame.f2715b = 0;
            tXSVFrame.f2722i = 0;
            tXSVFrame.f2724k = TXCSystemUtil.m2891a(tXSVFrame.f2717d, tXSVFrame.f2718e, i5, i6);
            this.f4627b.mo1028b(tXSVFrame);
            return;
        }
        TXCLog.m2914e(f4626a, "onScreenCaptureFrame failed");
    }

    @Override // com.tencent.liteav.screencapture.TXIScreenCaptureListener
    /* renamed from: a */
    public void mo756a(Object obj) {
        do {
        } while (m1288a(this.f4635j));
        TXICaptureSourceListener tXICaptureSourceListener = this.f4627b;
        if (tXICaptureSourceListener != null) {
            tXICaptureSourceListener.mo1027r();
        }
    }

    /* renamed from: f */
    private void m1287f(boolean z) {
        if (z) {
            int i = this.f4633h;
            int i2 = this.f4634i;
            if (i <= i2) {
                return;
            }
            mo1047a(i2, i);
            return;
        }
        int i3 = this.f4633h;
        int i4 = this.f4634i;
        if (i3 >= i4) {
            return;
        }
        mo1047a(i4, i3);
    }
}
