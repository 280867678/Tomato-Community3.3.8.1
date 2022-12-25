package com.faceunity.beautycontrolview.seekbar.internal.drawable;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.os.SystemClock;
import android.support.annotation.NonNull;

/* loaded from: classes2.dex */
public class ThumbDrawable extends StateDrawable implements Animatable {
    private boolean mOpen;
    private boolean mRunning;
    private final int mSize;
    private Runnable opener = new Runnable() { // from class: com.faceunity.beautycontrolview.seekbar.internal.drawable.ThumbDrawable.1
        @Override // java.lang.Runnable
        public void run() {
            ThumbDrawable.this.mOpen = true;
            ThumbDrawable.this.invalidateSelf();
            ThumbDrawable.this.mRunning = false;
        }
    };

    @Override // android.graphics.drawable.Animatable
    public void start() {
    }

    public ThumbDrawable(@NonNull ColorStateList colorStateList, int i) {
        super(colorStateList);
        this.mSize = i;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mSize;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mSize;
    }

    @Override // com.faceunity.beautycontrolview.seekbar.internal.drawable.StateDrawable
    public void doDraw(Canvas canvas, Paint paint) {
        if (!this.mOpen) {
            Rect bounds = getBounds();
            canvas.drawCircle(bounds.centerX(), bounds.centerY(), this.mSize / 2, paint);
        }
    }

    public void animateToPressed() {
        scheduleSelf(this.opener, SystemClock.uptimeMillis() + 100);
        this.mRunning = true;
    }

    public void animateToNormal() {
        this.mOpen = false;
        this.mRunning = false;
        unscheduleSelf(this.opener);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Animatable
    public void stop() {
        animateToNormal();
    }

    @Override // android.graphics.drawable.Animatable
    public boolean isRunning() {
        return this.mRunning;
    }
}
