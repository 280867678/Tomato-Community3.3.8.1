package com.tencent.liteav.renderer;

import android.graphics.Matrix;
import android.os.Handler;
import android.view.TextureView;
import com.tencent.liteav.basic.log.TXCLog;

/* renamed from: com.tencent.liteav.renderer.e */
/* loaded from: classes3.dex */
public class TXCTextureViewWrapper {

    /* renamed from: a */
    private TextureView f5120a;

    /* renamed from: b */
    private Handler f5121b;

    /* renamed from: c */
    private int f5122c;

    /* renamed from: d */
    private int f5123d;

    /* renamed from: e */
    private int f5124e = 640;

    /* renamed from: f */
    private int f5125f = 480;

    /* renamed from: g */
    private int f5126g = 0;

    /* renamed from: h */
    private int f5127h = 0;

    /* renamed from: i */
    private int f5128i = 1;

    /* renamed from: j */
    private int f5129j = 0;

    /* renamed from: k */
    private float f5130k = 1.0f;

    /* renamed from: l */
    private int f5131l = 0;

    public TXCTextureViewWrapper(TextureView textureView) {
        this.f5122c = 0;
        this.f5123d = 0;
        this.f5120a = textureView;
        this.f5122c = textureView.getWidth();
        this.f5123d = textureView.getHeight();
        this.f5121b = new Handler(textureView.getContext().getMainLooper());
    }

    /* renamed from: a */
    public void m910a(final int i) {
        try {
            this.f5121b.post(new Runnable() { // from class: com.tencent.liteav.renderer.e.1
                @Override // java.lang.Runnable
                public void run() {
                    TXCTextureViewWrapper.this.m906b(i);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x005f, code lost:
        if (r6 < r0) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0061, code lost:
        r4 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0073, code lost:
        if (r6 < r0) goto L38;
     */
    /* JADX WARN: Removed duplicated region for block: B:23:0x007e  */
    /* renamed from: b */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void m906b(int i) {
        float f;
        float f2;
        int i2;
        this.f5128i = i;
        if (this.f5120a != null) {
            float f3 = 1.0f;
            if (i == 1) {
                int i3 = this.f5129j;
                if (i3 != 0 && i3 != 180 && (i3 == 270 || i3 == 90)) {
                    int i4 = this.f5126g;
                    if (i4 == 0 || (i2 = this.f5127h) == 0) {
                        return;
                    }
                    float f4 = this.f5123d / i4;
                    f = this.f5122c / i2;
                    if (f4 <= f) {
                        f3 = f4;
                    }
                    f3 = f;
                }
                if (this.f5130k < 0.0f) {
                    f3 = -f3;
                }
                this.f5120a.setScaleX(f3);
                this.f5120a.setScaleY(Math.abs(f3));
                this.f5130k = f3;
            }
            if (i == 0) {
                if (this.f5126g == 0 || this.f5127h == 0) {
                    return;
                }
                int i5 = this.f5129j;
                if (i5 == 0 || i5 == 180) {
                    f = this.f5123d / this.f5127h;
                    f2 = this.f5122c / this.f5126g;
                } else if (i5 == 270 || i5 == 90) {
                    f = this.f5123d / this.f5126g;
                    f2 = this.f5122c / this.f5127h;
                }
            }
            if (this.f5130k < 0.0f) {
            }
            this.f5120a.setScaleX(f3);
            this.f5120a.setScaleY(Math.abs(f3));
            this.f5130k = f3;
        }
    }

    /* renamed from: c */
    public void m903c(final int i) {
        try {
            this.f5121b.post(new Runnable() { // from class: com.tencent.liteav.renderer.e.2
                @Override // java.lang.Runnable
                public void run() {
                    TXCTextureViewWrapper.this.m900d(i);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x003f, code lost:
        if (r5 > r2) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0041, code lost:
        r1 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0043, code lost:
        r1 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0049, code lost:
        if (r5 < r2) goto L25;
     */
    /* renamed from: d */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void m900d(int i) {
        int i2;
        int i3 = i % 360;
        this.f5129j = i3;
        if (this.f5120a != null) {
            float f = 1.0f;
            if (i3 == 0 || i3 == 180) {
                this.f5120a.setRotation(360 - i3);
                int i4 = this.f5128i;
                if (i4 != 1 && i4 == 0) {
                    int i5 = this.f5126g;
                    if (i5 == 0 || (i2 = this.f5127h) == 0) {
                        return;
                    }
                    f = this.f5123d / i2;
                    float f2 = this.f5122c / i5;
                    if (f < f2) {
                        f = f2;
                    }
                }
            } else if (i3 == 270 || i3 == 90) {
                if (this.f5126g == 0 || this.f5127h == 0) {
                    return;
                }
                this.f5120a.setRotation(360 - i3);
                float f3 = this.f5123d / this.f5126g;
                float f4 = this.f5122c / this.f5127h;
                int i6 = this.f5128i;
                if (i6 != 1) {
                    if (i6 == 0) {
                    }
                }
            }
            if (this.f5130k < 0.0f) {
                f = -f;
            }
            this.f5120a.setScaleX(f);
            this.f5120a.setScaleY(Math.abs(f));
            this.f5130k = f;
        }
    }

    /* renamed from: a */
    private void m911a() {
        try {
            this.f5121b.post(new Runnable() { // from class: com.tencent.liteav.renderer.e.3
                @Override // java.lang.Runnable
                public void run() {
                    TXCTextureViewWrapper tXCTextureViewWrapper = TXCTextureViewWrapper.this;
                    tXCTextureViewWrapper.m902c(tXCTextureViewWrapper.f5124e, TXCTextureViewWrapper.this.f5125f);
                    TXCTextureViewWrapper tXCTextureViewWrapper2 = TXCTextureViewWrapper.this;
                    tXCTextureViewWrapper2.m906b(tXCTextureViewWrapper2.f5128i);
                    TXCTextureViewWrapper tXCTextureViewWrapper3 = TXCTextureViewWrapper.this;
                    tXCTextureViewWrapper3.m900d(tXCTextureViewWrapper3.f5129j);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: c */
    public void m902c(int i, int i2) {
        int i3;
        int i4;
        if (this.f5120a == null || i == 0 || i2 == 0 || (i3 = this.f5122c) == 0 || (i4 = this.f5123d) == 0) {
            return;
        }
        double d = i2 / i;
        if (i4 > ((int) (i3 * d))) {
            this.f5126g = i3;
            this.f5127h = (int) (i3 * d);
        } else {
            this.f5126g = (int) (i4 / d);
            this.f5127h = i4;
        }
        int i5 = this.f5122c;
        int i6 = this.f5126g;
        int i7 = this.f5123d;
        int i8 = this.f5127h;
        Matrix matrix = new Matrix();
        this.f5120a.getTransform(matrix);
        matrix.setScale(i6 / i5, i8 / i7);
        matrix.postTranslate((i5 - i6) / 2.0f, (i7 - i8) / 2.0f);
        this.f5120a.setTransform(matrix);
        this.f5120a.requestLayout();
    }

    /* renamed from: a */
    public void m909a(int i, int i2) {
        TXCLog.m2911w("TXCTextureViewWrapper", "vrender: set view size:" + i + "," + i2);
        this.f5122c = i;
        this.f5123d = i2;
        m911a();
    }

    /* renamed from: b */
    public void m905b(int i, int i2) {
        TXCLog.m2911w("TXCTextureViewWrapper", "vrender: set video size:" + i + "," + i2);
        this.f5124e = i;
        this.f5125f = i2;
        m911a();
    }
}
