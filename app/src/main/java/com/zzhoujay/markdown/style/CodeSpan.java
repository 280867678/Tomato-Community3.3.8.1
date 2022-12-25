package com.zzhoujay.markdown.style;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.style.ReplacementSpan;

/* loaded from: classes4.dex */
public class CodeSpan extends ReplacementSpan {
    private Drawable drawable;
    private float padding;
    private int width;

    public CodeSpan(int i) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(i);
        gradientDrawable.setCornerRadius(10.0f);
        this.drawable = gradientDrawable;
    }

    @Override // android.text.style.ReplacementSpan
    public int getSize(Paint paint, CharSequence charSequence, int i, int i2, Paint.FontMetricsInt fontMetricsInt) {
        this.padding = paint.measureText("t");
        this.width = (int) (paint.measureText(charSequence, i, i2) + (this.padding * 2.0f));
        return this.width;
    }

    @Override // android.text.style.ReplacementSpan
    public void draw(Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, Paint paint) {
        int i6 = (int) f;
        this.drawable.setBounds(i6, i3, this.width + i6, i5);
        this.drawable.draw(canvas);
        canvas.drawText(charSequence, i, i2, f + this.padding, i4, paint);
    }
}
