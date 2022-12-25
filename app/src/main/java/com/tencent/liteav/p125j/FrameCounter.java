package com.tencent.liteav.p125j;

import android.util.Log;

/* renamed from: com.tencent.liteav.j.b */
/* loaded from: classes3.dex */
public class FrameCounter {

    /* renamed from: a */
    private static boolean f4417a;

    /* renamed from: b */
    private static int f4418b;

    /* renamed from: c */
    private static int f4419c;

    /* renamed from: d */
    private static int f4420d;

    /* renamed from: e */
    private static int f4421e;

    /* renamed from: f */
    private static int f4422f;

    /* renamed from: g */
    private static int f4423g;

    /* renamed from: h */
    private static int f4424h;

    /* renamed from: i */
    private static boolean f4425i;

    /* renamed from: a */
    public static void m1439a() {
        f4418b++;
        if (f4417a) {
            Log.w("FrameCounter", "decodeVideoCount:" + f4418b);
        }
    }

    /* renamed from: b */
    public static void m1438b() {
        f4419c++;
        if (f4417a) {
            Log.w("FrameCounter", "decodeAudioCount:" + f4419c);
        }
    }

    /* renamed from: c */
    public static void m1437c() {
        f4420d++;
        if (f4417a) {
            Log.w("FrameCounter", "processVideoCount:" + f4420d);
        }
    }

    /* renamed from: d */
    public static void m1436d() {
        f4421e++;
        if (f4417a) {
            Log.w("FrameCounter", "processAudioCount:" + f4421e);
        }
    }

    /* renamed from: e */
    public static void m1435e() {
        f4422f++;
        if (f4417a) {
            Log.w("FrameCounter", "renderVideoCount:" + f4422f);
        }
    }

    /* renamed from: f */
    public static void m1434f() {
        f4423g++;
        if (f4417a) {
            Log.w("FrameCounter", "encodeVideoCount:" + f4423g);
        }
    }

    /* renamed from: g */
    public static void m1433g() {
        f4424h++;
        if (f4417a) {
            Log.w("FrameCounter", "encodeAudioCount:" + f4424h);
        }
    }

    /* renamed from: h */
    public static void m1432h() {
        f4425i = true;
        f4418b = 0;
        f4419c = 0;
        f4420d = 0;
        f4421e = 0;
        f4422f = 0;
        f4423g = 0;
        f4424h = 0;
    }
}
