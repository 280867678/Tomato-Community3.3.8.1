package com.tomatolive.library.p136ui.view.widget.bgabanner.transformer;

import android.support.p002v4.view.ViewCompat;
import android.view.View;

/* renamed from: com.tomatolive.library.ui.view.widget.bgabanner.transformer.FlipPageTransformer */
/* loaded from: classes4.dex */
public class FlipPageTransformer extends BGAPageTransformer {
    private static final float ROTATION = 180.0f;

    @Override // com.tomatolive.library.p136ui.view.widget.bgabanner.transformer.BGAPageTransformer
    public void handleInvisiblePage(View view, float f) {
    }

    @Override // com.tomatolive.library.p136ui.view.widget.bgabanner.transformer.BGAPageTransformer
    public void handleLeftPage(View view, float f) {
        ViewCompat.setTranslationX(view, (-view.getWidth()) * f);
        ViewCompat.setRotationY(view, ROTATION * f);
        if (f > -0.5d) {
            view.setVisibility(0);
        } else {
            view.setVisibility(4);
        }
    }

    @Override // com.tomatolive.library.p136ui.view.widget.bgabanner.transformer.BGAPageTransformer
    public void handleRightPage(View view, float f) {
        ViewCompat.setTranslationX(view, (-view.getWidth()) * f);
        ViewCompat.setRotationY(view, ROTATION * f);
        if (f < 0.5d) {
            view.setVisibility(0);
        } else {
            view.setVisibility(4);
        }
    }
}
