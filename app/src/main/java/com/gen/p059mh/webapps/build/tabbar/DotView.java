package com.gen.p059mh.webapps.build.tabbar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.p002v4.internal.view.SupportMenu;
import android.view.View;

/* renamed from: com.gen.mh.webapps.build.tabbar.DotView */
/* loaded from: classes2.dex */
public class DotView extends View {
    String badgeText;
    Bitmap bitmap;
    String textStr;
    int textSize = 48;
    int textNumSize = 36;
    int radius = 25;
    Paint mainPaint = new Paint();
    Paint contentPaint = new Paint();
    Paint dotPaint = new Paint();
    Paint badgeNumPaint = new Paint();

    public DotView(Context context) {
        super(context);
        this.mainPaint.setAntiAlias(true);
        this.mainPaint.setColor(-16776961);
        this.mainPaint.setTextSize(this.textSize);
        this.mainPaint.setTextAlign(Paint.Align.CENTER);
        this.contentPaint.setAntiAlias(true);
        this.mainPaint.setAntiAlias(true);
        this.dotPaint.setColor(SupportMenu.CATEGORY_MASK);
        this.dotPaint.setTextAlign(Paint.Align.CENTER);
        this.badgeNumPaint.setAntiAlias(true);
        this.badgeNumPaint.setColor(-1);
        this.badgeNumPaint.setTypeface(Typeface.DEFAULT_BOLD);
        this.badgeNumPaint.setTextSize(this.textNumSize);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setImageDrawable(Drawable drawable) {
        this.bitmap = ((BitmapDrawable) drawable).getBitmap();
        invalidate();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = ((getHeight() - this.textSize) - 20) / 2;
        if (this.bitmap != null) {
            height = (((getHeight() - this.bitmap.getHeight()) - this.textSize) - 20) / 2;
            Bitmap bitmap = this.bitmap;
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, (width / 2) - (bitmap.getWidth() / 2), height, this.contentPaint);
            }
            String str = this.textStr;
            if (str != null) {
                canvas.drawText(str, width / 2, this.bitmap.getHeight() + height + this.textSize, this.mainPaint);
            }
        }
        int i = (width / 2) + (this.radius * 2);
        if ("".equals(this.badgeText)) {
            this.dotPaint.setColor(-1);
            float f = i;
            float f2 = height;
            canvas.drawCircle(f, f2, this.radius + 5, this.dotPaint);
            this.dotPaint.setColor(SupportMenu.CATEGORY_MASK);
            canvas.drawCircle(f, f2, this.radius - 5, this.dotPaint);
            return;
        }
        String str2 = this.badgeText;
        if (str2 == null) {
            return;
        }
        int i2 = this.radius + 5;
        float measureText = this.badgeNumPaint.measureText(str2) - 18.0f;
        this.dotPaint.setColor(-1);
        Path path = new Path();
        float f3 = i - i2;
        float f4 = height - i2;
        float f5 = i + i2;
        float f6 = height + i2;
        path.addArc(new RectF(f3, f4, f5, f6), 90.0f, 180.0f);
        path.addArc(new RectF(f3 + measureText, f4, f5 + measureText, f6), 270.0f, 180.0f);
        canvas.drawPath(path, this.dotPaint);
        float f7 = i;
        float f8 = measureText + f7;
        canvas.drawRect(new RectF(f7, f4, f8, f6), this.dotPaint);
        this.dotPaint.setColor(SupportMenu.CATEGORY_MASK);
        Path path2 = new Path();
        int i3 = this.radius;
        path2.addArc(new RectF(i - i3, height - i3, i + i3, i3 + height), 90.0f, 180.0f);
        int i4 = this.radius;
        path2.addArc(new RectF((i - i4) + measureText, height - i4, i + i4 + measureText, i4 + height), 270.0f, 180.0f);
        canvas.drawPath(path2, this.dotPaint);
        int i5 = this.radius;
        canvas.drawRect(new RectF(f7, height - i5, f8, i5 + height), this.dotPaint);
        canvas.drawText(this.badgeText, i - 9, height + (((i2 * 2) - this.textNumSize) / 2), this.badgeNumPaint);
    }

    public void setTextColor(int i) {
        Paint paint = this.mainPaint;
        if (paint != null) {
            paint.setColor(i);
        }
        invalidate();
    }

    public void setText(String str) {
        this.textStr = str;
        invalidate();
    }

    public void setBadgeText(String str) {
        this.badgeText = str;
        invalidate();
    }
}
