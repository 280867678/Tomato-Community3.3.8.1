package com.tomatolive.library.p136ui.view.sticker.core.homing;

/* renamed from: com.tomatolive.library.ui.view.sticker.core.homing.IMGHoming */
/* loaded from: classes3.dex */
public class IMGHoming {
    public float rotate;
    public float scale;

    /* renamed from: x */
    public float f5856x;

    /* renamed from: y */
    public float f5857y;

    public IMGHoming(float f, float f2, float f3, float f4) {
        this.f5856x = f;
        this.f5857y = f2;
        this.scale = f3;
        this.rotate = f4;
    }

    public void set(float f, float f2, float f3, float f4) {
        this.f5856x = f;
        this.f5857y = f2;
        this.scale = f3;
        this.rotate = f4;
    }

    public void concat(IMGHoming iMGHoming) {
        this.scale *= iMGHoming.scale;
        this.f5856x += iMGHoming.f5856x;
        this.f5857y += iMGHoming.f5857y;
    }

    public void rConcat(IMGHoming iMGHoming) {
        this.scale *= iMGHoming.scale;
        this.f5856x -= iMGHoming.f5856x;
        this.f5857y -= iMGHoming.f5857y;
    }

    public static boolean isRotate(IMGHoming iMGHoming, IMGHoming iMGHoming2) {
        return Float.compare(iMGHoming.rotate, iMGHoming2.rotate) != 0;
    }

    public String toString() {
        return "IMGHoming{x=" + this.f5856x + ", y=" + this.f5857y + ", scale=" + this.scale + ", rotate=" + this.rotate + '}';
    }
}
