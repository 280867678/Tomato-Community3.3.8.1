package com.zzhoujay.markdown.style;

import android.graphics.Paint;
import android.text.style.LineHeightSpan;

/* loaded from: classes4.dex */
public class ScaleHeightSpan implements LineHeightSpan {
    private float scale;

    public ScaleHeightSpan(float f) {
        this.scale = f;
    }

    @Override // android.text.style.LineHeightSpan
    public void chooseHeight(CharSequence charSequence, int i, int i2, int i3, int i4, Paint.FontMetricsInt fontMetricsInt) {
        float f = this.scale;
        fontMetricsInt.ascent = (int) (fontMetricsInt.ascent * f);
        fontMetricsInt.top = (int) (fontMetricsInt.top * f);
        fontMetricsInt.descent = (int) (fontMetricsInt.descent * f);
        fontMetricsInt.bottom = (int) (fontMetricsInt.bottom * f);
    }
}
