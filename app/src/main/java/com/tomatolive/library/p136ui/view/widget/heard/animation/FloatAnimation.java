package com.tomatolive.library.p136ui.view.widget.heard.animation;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/* renamed from: com.tomatolive.library.ui.view.widget.heard.animation.FloatAnimation */
/* loaded from: classes4.dex */
public class FloatAnimation extends Animation {
    private float mDistance;
    private PathMeasure mPm;
    private float mRotation;
    private View mView;

    private static float scale(double d, double d2, double d3, double d4, double d5) {
        return (float) ((((d - d2) / (d3 - d2)) * (d5 - d4)) + d4);
    }

    public FloatAnimation(boolean z, Path path, float f, View view, View view2) {
        this.mPm = new PathMeasure(path, false);
        this.mDistance = this.mPm.getLength();
        this.mView = view2;
        this.mRotation = f;
        if (z) {
            view.setLayerType(1, null);
        } else {
            view.setLayerType(2, null);
        }
    }

    @Override // android.view.animation.Animation
    protected void applyTransformation(float f, Transformation transformation) {
        float scale;
        this.mPm.getMatrix(this.mDistance * f, transformation.getMatrix(), 1);
        this.mView.setRotation(this.mRotation * f);
        float f2 = 3000.0f * f;
        if (f2 < 200.0f) {
            scale = scale(f, 0.0d, 0.06666667014360428d, 0.20000000298023224d, 1.100000023841858d);
        } else {
            scale = f2 < 300.0f ? scale(f, 0.06666667014360428d, 0.10000000149011612d, 1.100000023841858d, 1.0d) : 1.0f;
        }
        this.mView.setScaleX(scale);
        this.mView.setScaleY(scale);
        transformation.setAlpha(1.0f - f);
    }
}
