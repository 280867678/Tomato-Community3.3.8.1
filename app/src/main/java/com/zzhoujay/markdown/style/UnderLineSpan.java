package com.zzhoujay.markdown.style;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.style.LineHeightSpan;
import android.text.style.ReplacementSpan;

/* loaded from: classes4.dex */
public class UnderLineSpan extends ReplacementSpan implements LineHeightSpan {
    private Drawable drawable;
    private int height;
    private int width;

    public UnderLineSpan(Drawable drawable, int i, int i2) {
        this.height = i2;
        this.width = i;
        this.drawable = drawable;
    }

    @Override // android.text.style.ReplacementSpan
    public int getSize(Paint paint, CharSequence charSequence, int i, int i2, Paint.FontMetricsInt fontMetricsInt) {
        return (int) paint.measureText(charSequence, i, i2);
    }

    @Override // android.text.style.ReplacementSpan
    public void draw(Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, Paint paint) {
        int i6 = (int) f;
        this.drawable.setBounds(i6, i5 - this.height, this.width + i6, i5);
        this.drawable.draw(canvas);
    }

    @Override // android.text.style.LineHeightSpan
    public void chooseHeight(CharSequence charSequence, int i, int i2, int i3, int i4, Paint.FontMetricsInt fontMetricsInt) {
        fontMetricsInt.top /= 3;
        fontMetricsInt.ascent /= 3;
        fontMetricsInt.bottom /= 3;
        fontMetricsInt.descent /= 3;
    }
}
