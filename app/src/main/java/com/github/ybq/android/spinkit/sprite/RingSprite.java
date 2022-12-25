package com.github.ybq.android.spinkit.sprite;

import android.graphics.Canvas;
import android.graphics.Paint;

/* loaded from: classes2.dex */
public class RingSprite extends ShapeSprite {
    @Override // com.github.ybq.android.spinkit.sprite.ShapeSprite
    public void drawShape(Canvas canvas, Paint paint) {
        if (getDrawBounds() != null) {
            paint.setStyle(Paint.Style.STROKE);
            int min = Math.min(getDrawBounds().width(), getDrawBounds().height()) / 2;
            paint.setStrokeWidth(min / 12);
            canvas.drawCircle(getDrawBounds().centerX(), getDrawBounds().centerY(), min, paint);
        }
    }
}
