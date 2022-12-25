package com.gen.p059mh.webapp_extensions.views.camera;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.p002v4.view.ViewCompat;
import android.view.View;

/* renamed from: com.gen.mh.webapp_extensions.views.camera.CoverView */
/* loaded from: classes2.dex */
public class CoverView extends View {
    Paint clearPaint = new Paint();
    Paint stokePaint = new Paint();

    public CoverView(Context context) {
        super(context);
        this.clearPaint.setColor(0);
        this.clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        this.clearPaint.setStyle(Paint.Style.FILL);
        this.stokePaint.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.stokePaint.setStrokeWidth(getContext().getResources().getDisplayMetrics().density * 1.0f);
        this.stokePaint.setStyle(Paint.Style.STROKE);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        if (getContext() == null) {
            return;
        }
        canvas.drawColor(Color.argb(85, 0, 0, 0));
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        Rect rect = new Rect();
        float f = getContext().getResources().getDisplayMetrics().density * 240.0f;
        float f2 = width;
        rect.left = (int) ((f2 - f) / 2.0f);
        float f3 = height;
        rect.top = (int) ((f3 - f) / 2.0f);
        rect.right = (int) ((f2 + f) / 2.0f);
        rect.bottom = (int) ((f3 + f) / 2.0f);
        canvas.drawRect(rect, this.clearPaint);
        canvas.drawRect(rect, this.stokePaint);
    }
}
