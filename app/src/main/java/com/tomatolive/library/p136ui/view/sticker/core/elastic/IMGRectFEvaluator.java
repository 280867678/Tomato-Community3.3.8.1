package com.tomatolive.library.p136ui.view.sticker.core.elastic;

import android.animation.TypeEvaluator;
import android.graphics.RectF;

/* renamed from: com.tomatolive.library.ui.view.sticker.core.elastic.IMGRectFEvaluator */
/* loaded from: classes3.dex */
public class IMGRectFEvaluator implements TypeEvaluator<RectF> {
    private RectF mRect;

    public IMGRectFEvaluator() {
    }

    public IMGRectFEvaluator(RectF rectF) {
        this.mRect = rectF;
    }

    @Override // android.animation.TypeEvaluator
    public RectF evaluate(float f, RectF rectF, RectF rectF2) {
        float f2 = rectF.left;
        float f3 = f2 + ((rectF2.left - f2) * f);
        float f4 = rectF.top;
        float f5 = f4 + ((rectF2.top - f4) * f);
        float f6 = rectF.right;
        float f7 = f6 + ((rectF2.right - f6) * f);
        float f8 = rectF.bottom;
        float f9 = f8 + ((rectF2.bottom - f8) * f);
        RectF rectF3 = this.mRect;
        if (rectF3 == null) {
            return new RectF(f3, f5, f7, f9);
        }
        rectF3.set(f3, f5, f7, f9);
        return this.mRect;
    }
}
