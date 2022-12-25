package com.tencent.liteav;

import android.graphics.Bitmap;
import com.tencent.liteav.audio.TXCAudioRecorder;
import com.tomatolive.library.p136ui.view.dialog.LotteryDialog;

/* renamed from: com.tencent.liteav.h */
/* loaded from: classes3.dex */
public class TXCLivePushConfig implements Cloneable {

    /* renamed from: a */
    public int f4293a = 0;

    /* renamed from: b */
    public int f4294b = 0;

    /* renamed from: c */
    public int f4295c = LotteryDialog.MAX_VALUE;

    /* renamed from: d */
    public int f4296d = 600;

    /* renamed from: e */
    public int f4297e = 300;

    /* renamed from: f */
    public int f4298f = 5;

    /* renamed from: g */
    public boolean f4299g = true;

    /* renamed from: h */
    public int f4300h = 20;

    /* renamed from: i */
    public int f4301i = 1;

    /* renamed from: j */
    public int f4302j = 2;

    /* renamed from: k */
    public int f4303k = 1;

    /* renamed from: l */
    public int f4304l = 1;

    /* renamed from: m */
    public boolean f4305m = true;

    /* renamed from: n */
    public boolean f4306n = true;

    /* renamed from: o */
    public int f4307o = 3;

    /* renamed from: p */
    public int f4308p = 3;

    /* renamed from: q */
    public int f4309q = TXCAudioRecorder.f2042a;

    /* renamed from: r */
    public int f4310r = TXCAudioRecorder.f2043b;

    /* renamed from: s */
    public boolean f4311s = true;

    /* renamed from: t */
    public Bitmap f4312t = null;

    /* renamed from: u */
    public int f4313u = 300;

    /* renamed from: v */
    public int f4314v = 10;

    /* renamed from: w */
    public int f4315w = 1;

    /* renamed from: x */
    public Bitmap f4316x = null;

    /* renamed from: y */
    public int f4317y = 0;

    /* renamed from: z */
    public int f4318z = 0;

    /* renamed from: A */
    public float f4279A = 0.0f;

    /* renamed from: B */
    public float f4280B = 0.0f;

    /* renamed from: C */
    public float f4281C = -1.0f;

    /* renamed from: D */
    public boolean f4282D = true;

    /* renamed from: E */
    public boolean f4283E = false;

    /* renamed from: F */
    public boolean f4284F = false;

    /* renamed from: G */
    public boolean f4285G = true;

    /* renamed from: H */
    public int f4286H = 1;

    /* renamed from: I */
    public boolean f4287I = false;

    /* renamed from: J */
    public boolean f4288J = false;

    /* renamed from: K */
    public int f4289K = 0;

    /* renamed from: L */
    public boolean f4290L = false;

    /* renamed from: M */
    public boolean f4291M = true;

    /* renamed from: N */
    public boolean f4292N = false;

    /* renamed from: a */
    public boolean m1465a() {
        switch (this.f4303k) {
            case 0:
                this.f4293a = 368;
                this.f4294b = 640;
                break;
            case 1:
                this.f4293a = 544;
                this.f4294b = 960;
                break;
            case 2:
                this.f4293a = 720;
                this.f4294b = 1280;
                break;
            case 3:
                this.f4293a = 640;
                this.f4294b = 368;
                return true;
            case 4:
                this.f4293a = 960;
                this.f4294b = 544;
                return true;
            case 5:
                this.f4293a = 1280;
                this.f4294b = 720;
                return true;
            case 6:
                this.f4293a = 320;
                this.f4294b = 480;
                break;
            case 7:
                this.f4293a = 192;
                this.f4294b = 320;
                break;
            case 8:
                this.f4293a = 272;
                this.f4294b = 480;
                break;
            case 9:
                this.f4293a = 320;
                this.f4294b = 192;
                return true;
            case 10:
                this.f4293a = 480;
                this.f4294b = 272;
                return true;
            case 11:
                this.f4293a = 240;
                this.f4294b = 320;
                break;
            case 12:
                this.f4293a = 368;
                this.f4294b = 480;
                break;
            case 13:
                this.f4293a = 480;
                this.f4294b = 640;
                break;
            case 14:
                this.f4293a = 320;
                this.f4294b = 240;
                return true;
            case 15:
                this.f4293a = 480;
                this.f4294b = 368;
                return true;
            case 16:
                this.f4293a = 640;
                this.f4294b = 480;
                return true;
            case 17:
                this.f4293a = 480;
                this.f4294b = 480;
                break;
            case 18:
                this.f4293a = 272;
                this.f4294b = 272;
                break;
            case 19:
                this.f4293a = 160;
                this.f4294b = 160;
                break;
            default:
                this.f4293a = 368;
                this.f4294b = 640;
                break;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Object clone() throws CloneNotSupportedException {
        return (TXCLivePushConfig) super.clone();
    }
}
