package com.tomatolive.library.p136ui.view.sticker.core.elastic;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/* renamed from: com.tomatolive.library.ui.view.sticker.core.elastic.IMGPointFEvaluator */
/* loaded from: classes3.dex */
public class IMGPointFEvaluator implements TypeEvaluator<PointF> {
    private PointF mPoint;

    public IMGPointFEvaluator() {
    }

    public IMGPointFEvaluator(PointF pointF) {
        this.mPoint = pointF;
    }

    @Override // android.animation.TypeEvaluator
    public PointF evaluate(float f, PointF pointF, PointF pointF2) {
        float f2 = pointF.x;
        float f3 = f2 + ((pointF2.x - f2) * f);
        float f4 = pointF.y;
        float f5 = f4 + (f * (pointF2.y - f4));
        PointF pointF3 = this.mPoint;
        if (pointF3 != null) {
            pointF3.set(f3, f5);
            return this.mPoint;
        }
        return new PointF(f3, f5);
    }
}
