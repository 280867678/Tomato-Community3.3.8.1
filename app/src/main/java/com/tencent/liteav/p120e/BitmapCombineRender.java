package com.tencent.liteav.p120e;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p109e.CropRect;
import com.tencent.liteav.basic.p109e.TXCOpenGlUtils;
import com.tencent.liteav.basic.p112h.TXCCombineFrame;
import com.tencent.liteav.p119d.Frame;
import com.tencent.liteav.p125j.BitmapUtil;
import com.tencent.liteav.p127l.TXCCombineProcessor;
import java.util.List;

/* renamed from: com.tencent.liteav.e.f */
/* loaded from: classes3.dex */
public class BitmapCombineRender {

    /* renamed from: b */
    private TXCCombineProcessor f3685b;

    /* renamed from: c */
    private int f3686c;

    /* renamed from: d */
    private int f3687d;

    /* renamed from: e */
    private int f3688e;

    /* renamed from: f */
    private C3451a f3689f;

    /* renamed from: g */
    private C3451a f3690g;

    /* renamed from: a */
    private final String f3684a = "BitmapCombineRender";

    /* renamed from: h */
    private int f3691h = -1;

    /* compiled from: BitmapCombineRender.java */
    /* renamed from: com.tencent.liteav.e.f$a */
    /* loaded from: classes3.dex */
    protected class C3451a {

        /* renamed from: a */
        public int f3692a;

        /* renamed from: b */
        public int f3693b;

        /* renamed from: c */
        public int f3694c;

        protected C3451a() {
        }
    }

    public BitmapCombineRender(Context context, int i, int i2) {
        this.f3685b = new TXCCombineProcessor(context);
        this.f3686c = i;
        this.f3687d = i2;
    }

    /* renamed from: a */
    public int m2101a(Frame frame, int i, boolean z) {
        CropRect cropRect;
        int i2;
        if (z) {
            return this.f3691h;
        }
        List m2302w = frame.m2302w();
        if (m2302w == null || m2302w.size() == 0) {
            TXCLog.m2914e("BitmapCombineRender", "bitmapList is empty");
            return -1;
        }
        Bitmap bitmap = (Bitmap) m2302w.get(0);
        if (this.f3689f == null) {
            this.f3689f = new C3451a();
            this.f3689f.f3692a = TXCOpenGlUtils.m3003a(bitmap, -1, false);
            this.f3689f.f3693b = bitmap.getWidth();
            this.f3689f.f3694c = bitmap.getHeight();
        } else {
            if (bitmap.getWidth() == this.f3689f.f3693b) {
                int height = bitmap.getHeight();
                C3451a c3451a = this.f3689f;
                if (height == c3451a.f3694c) {
                    TXCOpenGlUtils.m3003a(bitmap, c3451a.f3692a, false);
                }
            }
            GLES20.glDeleteTextures(1, new int[]{this.f3689f.f3692a}, 0);
            this.f3689f.f3692a = TXCOpenGlUtils.m3003a(bitmap, -1, false);
            this.f3689f.f3693b = bitmap.getWidth();
            this.f3689f.f3694c = bitmap.getHeight();
        }
        CropRect m2104a = m2104a(bitmap.getWidth(), bitmap.getHeight());
        CropRect cropRect2 = new CropRect(0, 0, 0, 0);
        if (m2302w.size() > 1) {
            Bitmap bitmap2 = (Bitmap) m2302w.get(1);
            if (this.f3690g == null) {
                this.f3690g = new C3451a();
                this.f3690g.f3692a = TXCOpenGlUtils.m3003a(bitmap2, -1, false);
                this.f3690g.f3693b = bitmap2.getWidth();
                this.f3690g.f3694c = bitmap2.getHeight();
            } else {
                if (bitmap2.getWidth() == this.f3690g.f3693b) {
                    int height2 = bitmap2.getHeight();
                    C3451a c3451a2 = this.f3690g;
                    if (height2 == c3451a2.f3694c) {
                        TXCOpenGlUtils.m3003a(bitmap2, c3451a2.f3692a, false);
                    }
                }
                GLES20.glDeleteTextures(1, new int[]{this.f3690g.f3692a}, 0);
                this.f3690g.f3692a = TXCOpenGlUtils.m3003a(bitmap2, -1, false);
                this.f3690g.f3693b = bitmap2.getWidth();
                this.f3690g.f3694c = bitmap2.getHeight();
            }
            CropRect m2103a = m2103a(bitmap2.getWidth(), bitmap2.getHeight(), i);
            i2 = this.f3690g.f3692a;
            cropRect = m2103a;
        } else {
            cropRect = cropRect2;
            i2 = -1;
        }
        switch (i) {
            case 1:
                this.f3691h = m2102a(this.f3689f.f3692a, i2, frame.m2329e(), i, m2104a, cropRect);
                return this.f3691h;
            case 2:
                this.f3691h = m2100b(this.f3689f.f3692a, i2, frame.m2329e(), i, m2104a, cropRect);
                return this.f3691h;
            case 3:
                this.f3691h = m2097e(this.f3689f.f3692a, i2, frame.m2329e(), i, m2104a, cropRect);
                return this.f3691h;
            case 4:
            case 5:
                this.f3691h = m2099c(this.f3689f.f3692a, i2, frame.m2329e(), i, m2104a, cropRect);
                return this.f3691h;
            case 6:
                this.f3691h = m2098d(this.f3689f.f3692a, i2, frame.m2329e(), i, m2104a, cropRect);
                return this.f3691h;
            default:
                return -1;
        }
    }

    /* renamed from: a */
    private CropRect m2104a(int i, int i2) {
        CropRect cropRect = new CropRect(0, 0, this.f3686c, this.f3687d);
        float f = i;
        float f2 = i2;
        int i3 = this.f3686c;
        int i4 = this.f3687d;
        if (f / f2 >= i3 / i4) {
            float f3 = (i3 * i2) / f;
            cropRect.f2533a = 0;
            cropRect.f2534b = ((int) (i4 - f3)) / 2;
            cropRect.f2535c = i3;
            cropRect.f2536d = (int) f3;
        } else {
            float f4 = (i * i4) / f2;
            cropRect.f2533a = ((int) (i3 - f4)) / 2;
            cropRect.f2534b = 0;
            cropRect.f2535c = (int) f4;
            cropRect.f2536d = i4;
        }
        return cropRect;
    }

    /* renamed from: a */
    private CropRect m2103a(int i, int i2, int i3) {
        CropRect cropRect = new CropRect(0, 0, this.f3686c, this.f3687d);
        float f = i;
        float f2 = i2;
        int i4 = this.f3686c;
        int i5 = this.f3687d;
        if (f / f2 >= i4 / i5) {
            float f3 = i4;
            float f4 = (i2 * i4) / f;
            if (i3 == 1) {
                cropRect.f2533a = i4;
            } else {
                cropRect.f2533a = 0;
            }
            if (i3 == 2) {
                int i6 = this.f3687d;
                cropRect.f2534b = i6 + (((int) (i6 - f4)) / 2);
            } else {
                cropRect.f2534b = ((int) (this.f3687d - f4)) / 2;
            }
            cropRect.f2535c = (int) f3;
            cropRect.f2536d = (int) f4;
        } else {
            float f5 = (i * i5) / f2;
            float f6 = i5;
            if (i3 == 1) {
                cropRect.f2533a = i4 + (((int) (i4 - f5)) / 2);
            } else {
                cropRect.f2533a = ((int) (i4 - f5)) / 2;
            }
            if (i3 == 2) {
                cropRect.f2534b = this.f3687d;
            } else {
                cropRect.f2534b = 0;
            }
            cropRect.f2535c = (int) f5;
            cropRect.f2536d = (int) f6;
        }
        return cropRect;
    }

    /* renamed from: a */
    private int m2102a(int i, int i2, long j, int i3, CropRect cropRect, CropRect cropRect2) {
        float m1445a = BitmapUtil.m1445a(i3, j / 1000);
        TXCCombineFrame tXCCombineFrame = new TXCCombineFrame();
        tXCCombineFrame.f2725a = i;
        tXCCombineFrame.f2726b = 0;
        tXCCombineFrame.f2727c = cropRect.f2535c;
        tXCCombineFrame.f2728d = cropRect.f2536d;
        tXCCombineFrame.f2731g = cropRect;
        TXCCombineFrame tXCCombineFrame2 = new TXCCombineFrame();
        tXCCombineFrame2.f2725a = i2;
        tXCCombineFrame2.f2726b = 0;
        tXCCombineFrame2.f2727c = cropRect2.f2535c;
        tXCCombineFrame2.f2728d = cropRect2.f2536d;
        tXCCombineFrame2.f2731g = cropRect2;
        TXCCombineFrame[] tXCCombineFrameArr = {tXCCombineFrame, tXCCombineFrame2};
        int i4 = (int) (this.f3686c * m1445a);
        TXCLog.m2913i("BitmapCombineRender", "processTwoPicLeftRightCombine, cropOffsetRatio = " + m1445a + ", cropOffset = " + i4);
        CropRect cropRect3 = new CropRect(i4, 0, this.f3686c, this.f3687d);
        this.f3685b.m1285a((this.f3686c * 2) + this.f3688e, this.f3687d);
        this.f3685b.m1284a(cropRect3);
        return this.f3685b.m1279a(tXCCombineFrameArr, 0);
    }

    /* renamed from: b */
    private int m2100b(int i, int i2, long j, int i3, CropRect cropRect, CropRect cropRect2) {
        float m1445a = BitmapUtil.m1445a(i3, j / 1000);
        TXCCombineFrame tXCCombineFrame = new TXCCombineFrame();
        tXCCombineFrame.f2725a = i;
        tXCCombineFrame.f2726b = 0;
        tXCCombineFrame.f2727c = cropRect.f2535c;
        tXCCombineFrame.f2728d = cropRect.f2536d;
        tXCCombineFrame.f2731g = cropRect;
        TXCCombineFrame tXCCombineFrame2 = new TXCCombineFrame();
        tXCCombineFrame2.f2725a = i2;
        tXCCombineFrame2.f2726b = 0;
        tXCCombineFrame2.f2727c = cropRect2.f2535c;
        tXCCombineFrame2.f2728d = cropRect2.f2536d;
        tXCCombineFrame2.f2731g = cropRect2;
        TXCCombineFrame[] tXCCombineFrameArr = {tXCCombineFrame, tXCCombineFrame2};
        int i4 = (int) (this.f3687d * m1445a);
        TXCLog.m2913i("BitmapCombineRender", "processTwoPicUpDownCombine, cropOffsetRatio = " + m1445a + ", cropOffset = " + i4);
        CropRect cropRect3 = new CropRect(0, i4, this.f3686c, this.f3687d);
        this.f3685b.m1285a(this.f3686c, (this.f3687d * 2) + this.f3688e);
        this.f3685b.m1284a(cropRect3);
        return this.f3685b.m1279a(tXCCombineFrameArr, 0);
    }

    /* renamed from: c */
    private int m2099c(int i, int i2, long j, int i3, CropRect cropRect, CropRect cropRect2) {
        long j2 = j / 1000;
        float m1442b = BitmapUtil.m1442b(i3, j2);
        float m1441c = BitmapUtil.m1441c(i3, j2);
        TXCLog.m2913i("BitmapCombineRender", "processTwoPicZoom, scaleRatio = " + m1442b + ", alpha = " + m1441c);
        TXCCombineFrame tXCCombineFrame = new TXCCombineFrame();
        tXCCombineFrame.f2725a = i;
        tXCCombineFrame.f2726b = 0;
        tXCCombineFrame.f2727c = cropRect.f2535c;
        tXCCombineFrame.f2728d = cropRect.f2536d;
        tXCCombineFrame.f2731g = cropRect;
        TXCCombineFrame tXCCombineFrame2 = new TXCCombineFrame();
        tXCCombineFrame2.f2725a = i2;
        tXCCombineFrame2.f2726b = 0;
        tXCCombineFrame2.f2727c = cropRect2.f2535c;
        tXCCombineFrame2.f2728d = cropRect2.f2536d;
        tXCCombineFrame2.f2731g = cropRect2;
        TXCCombineFrame[] tXCCombineFrameArr = {tXCCombineFrame, tXCCombineFrame2};
        if (tXCCombineFrame.f2729e == null) {
            tXCCombineFrame.f2729e = new TXCCombineFrame.C3362a();
        }
        TXCCombineFrame.C3362a c3362a = tXCCombineFrame.f2729e;
        c3362a.f2732a = m1442b;
        c3362a.f2734c = m1441c;
        if (i2 >= 0) {
            tXCCombineFrame2.f2729e = new TXCCombineFrame.C3362a();
            if (i3 == 5) {
                tXCCombineFrame2.f2729e.f2732a = 1.1f;
            }
            tXCCombineFrame2.f2729e.f2734c = 1.0f - m1441c;
        }
        this.f3685b.m1285a(this.f3686c, this.f3687d);
        this.f3685b.m1284a((CropRect) null);
        return this.f3685b.m1279a(tXCCombineFrameArr, 0);
    }

    /* renamed from: d */
    private int m2098d(int i, int i2, long j, int i3, CropRect cropRect, CropRect cropRect2) {
        float m1441c = BitmapUtil.m1441c(i3, j / 1000);
        TXCLog.m2913i("BitmapCombineRender", "processTwoPicFaceInOut, alpha = " + m1441c);
        TXCCombineFrame tXCCombineFrame = new TXCCombineFrame();
        tXCCombineFrame.f2725a = i;
        tXCCombineFrame.f2726b = 0;
        tXCCombineFrame.f2727c = cropRect.f2535c;
        tXCCombineFrame.f2728d = cropRect.f2536d;
        tXCCombineFrame.f2731g = cropRect;
        TXCCombineFrame tXCCombineFrame2 = new TXCCombineFrame();
        tXCCombineFrame2.f2725a = i2;
        tXCCombineFrame2.f2726b = 0;
        tXCCombineFrame2.f2727c = cropRect2.f2535c;
        tXCCombineFrame2.f2728d = cropRect2.f2536d;
        tXCCombineFrame2.f2731g = cropRect2;
        TXCCombineFrame[] tXCCombineFrameArr = {tXCCombineFrame, tXCCombineFrame2};
        tXCCombineFrame.f2729e = new TXCCombineFrame.C3362a();
        tXCCombineFrame.f2729e.f2734c = m1441c;
        if (i2 >= 0) {
            tXCCombineFrame2.f2729e = new TXCCombineFrame.C3362a();
            tXCCombineFrame2.f2729e.f2734c = 1.0f - m1441c;
        }
        this.f3685b.m1285a(this.f3686c, this.f3687d);
        this.f3685b.m1284a((CropRect) null);
        return this.f3685b.m1279a(tXCCombineFrameArr, 0);
    }

    /* renamed from: e */
    private int m2097e(int i, int i2, long j, int i3, CropRect cropRect, CropRect cropRect2) {
        long j2 = j / 1000;
        int m1440d = BitmapUtil.m1440d(i3, j2);
        float m1442b = BitmapUtil.m1442b(i3, j2);
        TXCLog.m2913i("BitmapCombineRender", "processTwoPicRotation, rotation = " + m1440d + ", scale = " + m1442b);
        TXCCombineFrame tXCCombineFrame = new TXCCombineFrame();
        tXCCombineFrame.f2725a = i;
        tXCCombineFrame.f2726b = 0;
        tXCCombineFrame.f2727c = cropRect.f2535c;
        tXCCombineFrame.f2728d = cropRect.f2536d;
        tXCCombineFrame.f2731g = cropRect;
        TXCCombineFrame tXCCombineFrame2 = new TXCCombineFrame();
        tXCCombineFrame2.f2725a = i2;
        tXCCombineFrame2.f2726b = 0;
        tXCCombineFrame2.f2727c = cropRect2.f2535c;
        tXCCombineFrame2.f2728d = cropRect2.f2536d;
        tXCCombineFrame2.f2731g = cropRect2;
        TXCCombineFrame[] tXCCombineFrameArr = {tXCCombineFrame, tXCCombineFrame2};
        tXCCombineFrame.f2729e = new TXCCombineFrame.C3362a();
        TXCCombineFrame.C3362a c3362a = tXCCombineFrame.f2729e;
        c3362a.f2733b = m1440d;
        c3362a.f2732a = m1442b;
        c3362a.f2735d = true;
        if (i2 >= 0) {
            tXCCombineFrame2.f2729e = new TXCCombineFrame.C3362a();
        }
        if (m1440d != 0) {
            tXCCombineFrame.f2729e.f2735d = true;
            TXCCombineFrame.C3362a c3362a2 = tXCCombineFrame2.f2729e;
            if (c3362a2 != null) {
                c3362a2.f2735d = true;
            }
        } else {
            tXCCombineFrame.f2729e.f2734c = 1.0f;
            TXCCombineFrame.C3362a c3362a3 = tXCCombineFrame2.f2729e;
            if (c3362a3 != null) {
                c3362a3.f2734c = 0.0f;
            }
        }
        this.f3685b.m1285a(this.f3686c, this.f3687d);
        this.f3685b.m1284a((CropRect) null);
        return this.f3685b.m1279a(tXCCombineFrameArr, 0);
    }

    /* renamed from: a */
    public void m2105a() {
        int i;
        int[] iArr = new int[2];
        C3451a c3451a = this.f3689f;
        if (c3451a != null) {
            iArr[0] = c3451a.f3692a;
            i = 1;
        } else {
            i = 0;
        }
        C3451a c3451a2 = this.f3690g;
        if (c3451a2 != null) {
            iArr[1] = c3451a2.f3692a;
            i++;
        }
        GLES20.glDeleteTextures(i, iArr, 0);
        this.f3689f = null;
        this.f3690g = null;
        this.f3685b.m1286a();
    }
}
