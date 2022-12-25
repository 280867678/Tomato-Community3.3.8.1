package com.tomatolive.library.p136ui.view.widget.heard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.support.p005v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import com.tomatolive.library.R$drawable;

/* renamed from: com.tomatolive.library.ui.view.widget.heard.RxHeartView */
/* loaded from: classes4.dex */
public class RxHeartView extends AppCompatImageView {
    private static Bitmap sHeart;
    private static Bitmap sHeartBorder;
    private static final Paint sPaint = new Paint(3);
    private static final Canvas sCanvas = new Canvas();
    private int mHeartResId = R$drawable.anim_heart;
    private int mHeartBorderResId = R$drawable.anim_heart_border;

    public RxHeartView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public RxHeartView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public RxHeartView(Context context) {
        super(context);
    }

    private static Bitmap createBitmapSafely(int i, int i2) {
        try {
            return Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setColor(int i) {
        setImageDrawable(new BitmapDrawable(getResources(), createHeart(i)));
    }

    public void setColorAndDrawables(int i, int i2, int i3) {
        if (i2 != this.mHeartResId) {
            sHeart = null;
        }
        if (i3 != this.mHeartBorderResId) {
            sHeartBorder = null;
        }
        this.mHeartResId = i2;
        this.mHeartBorderResId = i3;
        setColor(i);
    }

    private Bitmap createHeart(int i) {
        if (sHeart == null) {
            sHeart = BitmapFactory.decodeResource(getResources(), this.mHeartResId);
        }
        if (sHeartBorder == null) {
            sHeartBorder = BitmapFactory.decodeResource(getResources(), this.mHeartBorderResId);
        }
        Bitmap bitmap = sHeart;
        Bitmap bitmap2 = sHeartBorder;
        Bitmap createBitmapSafely = createBitmapSafely(bitmap2.getWidth(), bitmap2.getHeight());
        if (createBitmapSafely == null) {
            return null;
        }
        Canvas canvas = sCanvas;
        canvas.setBitmap(createBitmapSafely);
        Paint paint = sPaint;
        canvas.drawBitmap(bitmap2, 0.0f, 0.0f, paint);
        paint.setColorFilter(new PorterDuffColorFilter(i, PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(bitmap, (bitmap2.getWidth() - bitmap.getWidth()) / 2.0f, (bitmap2.getHeight() - bitmap.getHeight()) / 2.0f, paint);
        paint.setColorFilter(null);
        canvas.setBitmap(null);
        return createBitmapSafely;
    }
}
