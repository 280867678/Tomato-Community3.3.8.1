package com.tencent.liteav.basic.p109e;

/* renamed from: com.tencent.liteav.basic.e.k */
/* loaded from: classes3.dex */
public class TXCTextureRotationUtil {

    /* renamed from: a */
    public static final float[] f2680a = {0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f};

    /* renamed from: b */
    public static final float[] f2681b = {1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f};

    /* renamed from: c */
    public static final float[] f2682c = {1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f};

    /* renamed from: d */
    public static final float[] f2683d = {0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f};

    /* renamed from: e */
    public static final float[] f2684e = {-1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f};

    /* renamed from: a */
    private static float m2992a(float f) {
        return f == 0.0f ? 1.0f : 0.0f;
    }

    /* compiled from: TXCTextureRotationUtil.java */
    /* renamed from: com.tencent.liteav.basic.e.k$1 */
    /* loaded from: classes3.dex */
    static /* synthetic */ class C33581 {

        /* renamed from: a */
        static final /* synthetic */ int[] f2685a = new int[TXCRotation.values().length];

        static {
            try {
                f2685a[TXCRotation.ROTATION_90.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f2685a[TXCRotation.ROTATION_180.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f2685a[TXCRotation.ROTATION_270.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f2685a[TXCRotation.NORMAL.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    /* renamed from: a */
    public static float[] m2991a(TXCRotation tXCRotation, boolean z, boolean z2) {
        float[] fArr;
        int i = C33581.f2685a[tXCRotation.ordinal()];
        if (i == 1) {
            fArr = (float[]) f2681b.clone();
        } else if (i == 2) {
            fArr = (float[]) f2682c.clone();
        } else if (i == 3) {
            fArr = (float[]) f2683d.clone();
        } else {
            fArr = (float[]) f2680a.clone();
        }
        if (z) {
            fArr = new float[]{m2992a(fArr[0]), fArr[1], m2992a(fArr[2]), fArr[3], m2992a(fArr[4]), fArr[5], m2992a(fArr[6]), fArr[7]};
        }
        return z2 ? new float[]{fArr[0], m2992a(fArr[1]), fArr[2], m2992a(fArr[3]), fArr[4], m2992a(fArr[5]), fArr[6], m2992a(fArr[7])} : fArr;
    }
}
