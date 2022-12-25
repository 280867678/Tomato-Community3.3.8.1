package com.luck.picture.lib.dialog;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.luck.picture.lib.R$drawable;

/* loaded from: classes3.dex */
public class PictureSpinView extends ImageView implements PictureIndeterminate {
    private int mFrameTime;
    private boolean mNeedToUpdateView;
    private float mRotateDegrees;
    private Runnable mUpdateViewRunnable;

    public PictureSpinView(Context context) {
        super(context);
        init();
    }

    public PictureSpinView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        setImageResource(R$drawable.kprogresshud_spinner);
        this.mFrameTime = 83;
        this.mUpdateViewRunnable = new Runnable() { // from class: com.luck.picture.lib.dialog.PictureSpinView.1
            @Override // java.lang.Runnable
            public void run() {
                PictureSpinView pictureSpinView;
                PictureSpinView.this.mRotateDegrees += 30.0f;
                PictureSpinView pictureSpinView2 = PictureSpinView.this;
                pictureSpinView2.mRotateDegrees = pictureSpinView2.mRotateDegrees < 360.0f ? PictureSpinView.this.mRotateDegrees : PictureSpinView.this.mRotateDegrees - 360.0f;
                PictureSpinView.this.invalidate();
                if (PictureSpinView.this.mNeedToUpdateView) {
                    PictureSpinView.this.postDelayed(this, pictureSpinView.mFrameTime);
                }
            }
        };
    }

    public void setAnimationSpeed(float f) {
        this.mFrameTime = (int) (83.0f / f);
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(Canvas canvas) {
        canvas.rotate(this.mRotateDegrees, getWidth() / 2, getHeight() / 2);
        super.onDraw(canvas);
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mNeedToUpdateView = true;
        post(this.mUpdateViewRunnable);
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDetachedFromWindow() {
        this.mNeedToUpdateView = false;
        super.onDetachedFromWindow();
    }
}
