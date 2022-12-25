package com.zzhoujay.markdown.style;

import android.annotation.SuppressLint;
import android.text.ParcelableSpan;
import android.text.TextPaint;
import android.text.style.StyleSpan;

@SuppressLint({"ParcelCreator"})
/* loaded from: classes4.dex */
public class FontSpan extends StyleSpan implements ParcelableSpan {
    private final int color;
    private final float size;

    public FontSpan(float f, int i, int i2) {
        super(i);
        this.size = f;
        this.color = i2;
    }

    @Override // android.text.style.StyleSpan, android.text.style.MetricAffectingSpan
    public void updateMeasureState(TextPaint textPaint) {
        super.updateMeasureState(textPaint);
        textPaint.setTextSize(textPaint.getTextSize() * this.size);
    }

    @Override // android.text.style.StyleSpan, android.text.style.CharacterStyle
    public void updateDrawState(TextPaint textPaint) {
        super.updateDrawState(textPaint);
        updateMeasureState(textPaint);
        textPaint.setColor(this.color);
    }
}
