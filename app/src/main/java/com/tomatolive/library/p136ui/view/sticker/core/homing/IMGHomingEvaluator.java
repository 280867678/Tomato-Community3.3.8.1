package com.tomatolive.library.p136ui.view.sticker.core.homing;

import android.animation.TypeEvaluator;

/* renamed from: com.tomatolive.library.ui.view.sticker.core.homing.IMGHomingEvaluator */
/* loaded from: classes3.dex */
public class IMGHomingEvaluator implements TypeEvaluator<IMGHoming> {
    private IMGHoming homing;

    public IMGHomingEvaluator() {
    }

    public IMGHomingEvaluator(IMGHoming iMGHoming) {
        this.homing = iMGHoming;
    }

    @Override // android.animation.TypeEvaluator
    public IMGHoming evaluate(float f, IMGHoming iMGHoming, IMGHoming iMGHoming2) {
        float f2 = iMGHoming.f5856x;
        float f3 = f2 + ((iMGHoming2.f5856x - f2) * f);
        float f4 = iMGHoming.f5857y;
        float f5 = f4 + ((iMGHoming2.f5857y - f4) * f);
        float f6 = iMGHoming.scale;
        float f7 = f6 + ((iMGHoming2.scale - f6) * f);
        float f8 = iMGHoming.rotate;
        float f9 = f8 + (f * (iMGHoming2.rotate - f8));
        IMGHoming iMGHoming3 = this.homing;
        if (iMGHoming3 == null) {
            this.homing = new IMGHoming(f3, f5, f7, f9);
        } else {
            iMGHoming3.set(f3, f5, f7, f9);
        }
        return this.homing;
    }
}
