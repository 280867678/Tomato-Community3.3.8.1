package com.gen.p059mh.webapp_extensions.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.p002v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import com.gen.p059mh.webapp_extensions.R$styleable;

/* renamed from: com.gen.mh.webapp_extensions.views.SeekOrBackButton */
/* loaded from: classes2.dex */
public class SeekOrBackButton extends View {
    Bitmap bitmap;
    boolean isMirror;
    Paint paint;
    int times;

    public SeekOrBackButton(Context context) {
        this(context, null);
    }

    public SeekOrBackButton(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SeekOrBackButton(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.times = 2;
        this.isMirror = true;
        init(attributeSet);
    }

    private void init(AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.SeekOrBackButton);
            int resourceId = obtainStyledAttributes.getResourceId(R$styleable.SeekOrBackButton_resource, 0);
            this.times = obtainStyledAttributes.getInteger(R$styleable.SeekOrBackButton_times, 1);
            this.isMirror = obtainStyledAttributes.getBoolean(R$styleable.SeekOrBackButton_is_mirror, false);
            this.bitmap = drawable2Bitmap(ContextCompat.getDrawable(getContext(), resourceId));
            obtainStyledAttributes.recycle();
        }
        this.paint = new Paint();
        startDraw();
    }

    public void setTimes(int i) {
        this.times = i;
    }

    public void startDraw() {
        invalidate();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.bitmap != null) {
            for (int i = 0; i < this.times; i++) {
                if (this.isMirror) {
                    Matrix matrix = new Matrix();
                    matrix.postScale(-1.0f, 1.0f, this.bitmap.getWidth() / 2, this.bitmap.getHeight() / 2);
                    matrix.postTranslate(this.bitmap.getWidth() * i, 0.0f);
                    canvas.drawBitmap(this.bitmap, matrix, this.paint);
                } else {
                    Bitmap bitmap = this.bitmap;
                    canvas.drawBitmap(bitmap, bitmap.getWidth() * i, 0.0f, this.paint);
                }
            }
        }
    }

    public Bitmap drawable2Bitmap(Drawable drawable) {
        Bitmap createBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != -1 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return createBitmap;
    }
}
