package com.zzhoujay.html.style;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.Layout;
import android.text.Spanned;
import android.text.style.BulletSpan;

/* loaded from: classes4.dex */
public class ZBulletSpan extends BulletSpan {
    private static Path sBulletPath;
    private final int mGapWidth = 15;
    private final boolean mWantColor = false;
    private final int mColor = 0;

    @Override // android.text.style.BulletSpan, android.text.style.LeadingMarginSpan
    public int getLeadingMargin(boolean z) {
        return this.mGapWidth + 10;
    }

    @Override // android.text.style.BulletSpan, android.text.style.LeadingMarginSpan
    public void drawLeadingMargin(Canvas canvas, Paint paint, int i, int i2, int i3, int i4, int i5, CharSequence charSequence, int i6, int i7, boolean z, Layout layout) {
        if (((Spanned) charSequence).getSpanStart(this) == i6) {
            Paint.Style style = paint.getStyle();
            int i8 = 0;
            if (this.mWantColor) {
                i8 = paint.getColor();
                paint.setColor(this.mColor);
            }
            paint.setStyle(Paint.Style.FILL);
            if (canvas.isHardwareAccelerated()) {
                if (sBulletPath == null) {
                    sBulletPath = new Path();
                    sBulletPath.addCircle(0.0f, 0.0f, 6.0f, Path.Direction.CW);
                }
                canvas.save();
                canvas.translate(i + (i2 * 5), (i3 + i5) / 2.0f);
                canvas.drawPath(sBulletPath, paint);
                canvas.restore();
            } else {
                canvas.drawCircle(i + (i2 * 5), (i3 + i5) / 2.0f, 5.0f, paint);
            }
            if (this.mWantColor) {
                paint.setColor(i8);
            }
            paint.setStyle(style);
        }
    }
}
