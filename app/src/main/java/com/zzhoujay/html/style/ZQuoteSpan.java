package com.zzhoujay.html.style;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.style.QuoteSpan;

/* loaded from: classes4.dex */
public class ZQuoteSpan extends QuoteSpan {
    @Override // android.text.style.QuoteSpan, android.text.style.LeadingMarginSpan
    public int getLeadingMargin(boolean z) {
        return 55;
    }

    public ZQuoteSpan() {
        this(-3355444);
    }

    public ZQuoteSpan(int i) {
        super(i);
    }

    @Override // android.text.style.QuoteSpan, android.text.style.LeadingMarginSpan
    public void drawLeadingMargin(Canvas canvas, Paint paint, int i, int i2, int i3, int i4, int i5, CharSequence charSequence, int i6, int i7, boolean z, Layout layout) {
        Paint.Style style = paint.getStyle();
        int color = paint.getColor();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getColor());
        canvas.drawRect(i, i3, i + (i2 * 15), i5, paint);
        paint.setStyle(style);
        paint.setColor(color);
    }
}
