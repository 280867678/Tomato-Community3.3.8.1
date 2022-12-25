package com.faceunity.beautycontrolview.seekbar.internal.drawable;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;

/* loaded from: classes2.dex */
public class TrackRectDrawable extends StateDrawable {
    public TrackRectDrawable(@NonNull ColorStateList colorStateList) {
        super(colorStateList);
    }

    @Override // com.faceunity.beautycontrolview.seekbar.internal.drawable.StateDrawable
    void doDraw(Canvas canvas, Paint paint) {
        canvas.drawRect(getBounds(), paint);
    }
}
