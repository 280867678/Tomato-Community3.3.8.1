package com.tomatolive.library.p136ui.view.sticker.core.elastic;

import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.view.animation.AccelerateDecelerateInterpolator;

/* renamed from: com.tomatolive.library.ui.view.sticker.core.elastic.IMGElasticAnimator */
/* loaded from: classes3.dex */
public class IMGElasticAnimator extends ValueAnimator {
    private IMGElastic mElastic;

    public IMGElasticAnimator() {
        setEvaluator(new IMGPointFEvaluator());
        setInterpolator(new AccelerateDecelerateInterpolator());
    }

    public IMGElasticAnimator(IMGElastic iMGElastic) {
        this();
        setElastic(iMGElastic);
    }

    public void setElastic(IMGElastic iMGElastic) {
        this.mElastic = iMGElastic;
        if (this.mElastic != null) {
            return;
        }
        throw new IllegalArgumentException("IMGElastic cannot be null.");
    }

    public void start(float f, float f2) {
        setObjectValues(new PointF(f, f2), this.mElastic.getPivot());
        start();
    }
}
