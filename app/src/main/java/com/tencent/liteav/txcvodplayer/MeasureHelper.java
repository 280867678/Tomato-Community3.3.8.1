package com.tencent.liteav.txcvodplayer;

import android.view.View;
import java.lang.ref.WeakReference;

/* renamed from: com.tencent.liteav.txcvodplayer.b */
/* loaded from: classes3.dex */
public final class MeasureHelper {

    /* renamed from: a */
    private WeakReference<View> f5386a;

    /* renamed from: b */
    private int f5387b;

    /* renamed from: c */
    private int f5388c;

    /* renamed from: d */
    private int f5389d;

    /* renamed from: e */
    private int f5390e;

    /* renamed from: f */
    private int f5391f;

    /* renamed from: g */
    private int f5392g;

    /* renamed from: h */
    private int f5393h;

    /* renamed from: i */
    private int f5394i = 0;

    public MeasureHelper(View view) {
        this.f5386a = new WeakReference<>(view);
    }

    /* renamed from: a */
    public void m644a(int i, int i2) {
        this.f5387b = i;
        this.f5388c = i2;
    }

    /* renamed from: b */
    public void m641b(int i, int i2) {
        this.f5389d = i;
        this.f5390e = i2;
    }

    /* renamed from: a */
    public void m645a(int i) {
        this.f5391f = i;
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x00ae, code lost:
        if (r4 != false) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00b3, code lost:
        r12 = (int) (r0 / r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00b7, code lost:
        r11 = (int) (r3 * r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00b1, code lost:
        if (r4 != false) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00f7, code lost:
        if (r1 > r11) goto L75;
     */
    /* renamed from: c */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void m640c(int i, int i2) {
        int i3;
        float f;
        int i4;
        int i5 = this.f5391f;
        if (i5 == 90 || i5 == 270) {
            i2 = i;
            i = i2;
        }
        int defaultSize = View.getDefaultSize(this.f5387b, i);
        int defaultSize2 = View.getDefaultSize(this.f5388c, i2);
        if (this.f5394i != 3) {
            if (this.f5387b <= 0 || this.f5388c <= 0) {
                i = defaultSize;
                i2 = defaultSize2;
            } else {
                int mode = View.MeasureSpec.getMode(i);
                i = View.MeasureSpec.getSize(i);
                int mode2 = View.MeasureSpec.getMode(i2);
                i2 = View.MeasureSpec.getSize(i2);
                if (mode == Integer.MIN_VALUE && mode2 == Integer.MIN_VALUE) {
                    float f2 = i;
                    float f3 = i2;
                    float f4 = f2 / f3;
                    int i6 = this.f5394i;
                    if (i6 == 4) {
                        int i7 = this.f5391f;
                        f = (i7 == 90 || i7 == 270) ? 0.5625f : 1.7777778f;
                    } else if (i6 == 5) {
                        int i8 = this.f5391f;
                        f = (i8 == 90 || i8 == 270) ? 0.75f : 1.3333334f;
                    } else {
                        f = this.f5387b / this.f5388c;
                        int i9 = this.f5389d;
                        if (i9 > 0 && (i4 = this.f5390e) > 0) {
                            f = (f * i9) / i4;
                        }
                    }
                    boolean z = f > f4;
                    int i10 = this.f5394i;
                    if (i10 != 0) {
                        if (i10 != 1) {
                            if (i10 != 4 && i10 != 5) {
                                if (z) {
                                    i = Math.min(this.f5387b, i);
                                    i2 = (int) (i / f);
                                } else {
                                    int min = Math.min(this.f5388c, i2);
                                    i2 = min;
                                    i = (int) (min * f);
                                }
                            }
                        }
                    }
                } else if (mode == 1073741824 && mode2 == 1073741824) {
                    int i11 = this.f5387b;
                    int i12 = i11 * i2;
                    int i13 = this.f5388c;
                    if (i12 < i * i13) {
                        i = (i11 * i2) / i13;
                    } else if (i11 * i2 > i * i13) {
                        i2 = (i13 * i) / i11;
                    }
                } else if (mode == 1073741824) {
                    int i14 = (this.f5388c * i) / this.f5387b;
                    if (mode2 != Integer.MIN_VALUE || i14 <= i2) {
                        i2 = i14;
                    }
                } else if (mode2 == 1073741824) {
                    i3 = (this.f5387b * i2) / this.f5388c;
                    if (mode == Integer.MIN_VALUE) {
                    }
                    i = i3;
                } else {
                    i3 = this.f5387b;
                    int i15 = this.f5388c;
                    if (mode2 != Integer.MIN_VALUE || i15 <= i2) {
                        i2 = i15;
                    } else {
                        i3 = (i3 * i2) / i15;
                    }
                    if (mode == Integer.MIN_VALUE && i3 > i) {
                        i2 = (this.f5388c * i) / this.f5387b;
                    }
                    i = i3;
                }
            }
        }
        this.f5392g = i;
        this.f5393h = i2;
    }

    /* renamed from: a */
    public int m646a() {
        return this.f5392g;
    }

    /* renamed from: b */
    public int m643b() {
        return this.f5393h;
    }

    /* renamed from: b */
    public void m642b(int i) {
        this.f5394i = i;
    }
}
