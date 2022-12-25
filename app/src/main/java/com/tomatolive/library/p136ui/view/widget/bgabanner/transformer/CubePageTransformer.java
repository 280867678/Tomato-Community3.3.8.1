package com.tomatolive.library.p136ui.view.widget.bgabanner.transformer;

import android.support.p002v4.view.ViewCompat;
import android.view.View;

/* renamed from: com.tomatolive.library.ui.view.widget.bgabanner.transformer.CubePageTransformer */
/* loaded from: classes4.dex */
public class CubePageTransformer extends BGAPageTransformer {
    private float mMaxRotation = 90.0f;

    public CubePageTransformer() {
    }

    public CubePageTransformer(float f) {
        setMaxRotation(f);
    }

    @Override // com.tomatolive.library.p136ui.view.widget.bgabanner.transformer.BGAPageTransformer
    public void handleInvisiblePage(View view, float f) {
        ViewCompat.setPivotX(view, view.getMeasuredWidth());
        ViewCompat.setPivotY(view, view.getMeasuredHeight() * 0.5f);
        ViewCompat.setRotationY(view, 0.0f);
    }

    @Override // com.tomatolive.library.p136ui.view.widget.bgabanner.transformer.BGAPageTransformer
    public void handleLeftPage(View view, float f) {
        ViewCompat.setPivotX(view, view.getMeasuredWidth());
        ViewCompat.setPivotY(view, view.getMeasuredHeight() * 0.5f);
        ViewCompat.setRotationY(view, this.mMaxRotation * f);
    }

    @Override // com.tomatolive.library.p136ui.view.widget.bgabanner.transformer.BGAPageTransformer
    public void handleRightPage(View view, float f) {
        ViewCompat.setPivotX(view, 0.0f);
        ViewCompat.setPivotY(view, view.getMeasuredHeight() * 0.5f);
        ViewCompat.setRotationY(view, this.mMaxRotation * f);
    }

    public void setMaxRotation(float f) {
        if (f < 0.0f || f > 90.0f) {
            return;
        }
        this.mMaxRotation = f;
    }
}
