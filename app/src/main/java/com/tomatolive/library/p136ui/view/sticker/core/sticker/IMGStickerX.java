package com.tomatolive.library.p136ui.view.sticker.core.sticker;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.p002v4.internal.view.SupportMenu;
import android.view.MotionEvent;

/* renamed from: com.tomatolive.library.ui.view.sticker.core.sticker.IMGStickerX */
/* loaded from: classes3.dex */
public class IMGStickerX {
    private static final float SIZE_ANCHOR = 60.0f;
    private static final float STROKE_FRAME = 6.0f;
    private StickerEvent mTouchEvent;
    private float mBaseScale = 1.0f;
    private float mScale = 1.0f;
    private float mBaseRotate = 0.0f;
    private float mRotate = 0.0f;

    /* renamed from: mX */
    private float f5862mX = 0.0f;

    /* renamed from: mY */
    private float f5863mY = 0.0f;
    protected float[] mPivotXY = {0.0f, 0.0f};
    private boolean isActivated = true;
    protected RectF mFrame = new RectF();
    private RectF mRemoveFrame = new RectF();
    private RectF mAdjustFrame = new RectF();
    private Paint mPaint = new Paint(1);

    /* renamed from: com.tomatolive.library.ui.view.sticker.core.sticker.IMGStickerX$StickerEvent */
    /* loaded from: classes3.dex */
    public enum StickerEvent {
        REMOVE,
        BODY,
        ADJUST
    }

    public IMGStickerX() {
        this.mPaint.setColor(SupportMenu.CATEGORY_MASK);
        this.mPaint.setStrokeWidth(STROKE_FRAME);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mFrame.set(0.0f, 0.0f, 120.0f, 120.0f);
        this.mRemoveFrame.set(0.0f, 0.0f, 60.0f, 60.0f);
        this.mAdjustFrame.set(0.0f, 0.0f, 60.0f, 60.0f);
    }

    public boolean isActivated() {
        return this.isActivated;
    }

    public void setActivated(boolean z) {
        this.isActivated = z;
    }

    public void onMeasure(float f, float f2) {
        this.mFrame.set(0.0f, 0.0f, f, f2);
        RectF rectF = this.mFrame;
        rectF.offset(this.mPivotXY[0] - rectF.centerX(), this.mPivotXY[1] - this.mFrame.centerY());
    }

    public void onDraw(Canvas canvas) {
        if (this.isActivated) {
            canvas.save();
            float f = this.mRotate;
            float[] fArr = this.mPivotXY;
            canvas.rotate(f, fArr[0], fArr[1]);
            canvas.drawRect(this.mFrame, this.mPaint);
            RectF rectF = this.mFrame;
            canvas.translate(rectF.left, rectF.top);
            canvas.drawRect(this.mRemoveFrame, this.mPaint);
            canvas.translate(this.mFrame.width() - this.mAdjustFrame.width(), this.mFrame.height() - this.mAdjustFrame.height());
            canvas.drawRect(this.mAdjustFrame, this.mPaint);
            canvas.restore();
        }
        float f2 = this.mRotate;
        float[] fArr2 = this.mPivotXY;
        canvas.rotate(f2, fArr2[0], fArr2[1]);
    }

    public void setScale(float f) {
        this.mScale = f;
    }

    public void setRotate(float f) {
        this.mRotate = f;
    }

    public void setBaseScale(float f) {
        this.mBaseScale = f;
    }

    public void setBaseRotate(float f) {
        this.mBaseRotate = f;
    }

    public void offset(float f, float f2) {
        float[] fArr = this.mPivotXY;
        fArr[0] = fArr[0] + f;
        fArr[1] = fArr[1] + f2;
        RectF rectF = this.mFrame;
        rectF.offset(fArr[0] - rectF.centerX(), this.mPivotXY[1] - this.mFrame.centerY());
    }

    public StickerEvent onTouch(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (this.mTouchEvent != null || actionMasked == 0) {
            if (actionMasked == 0) {
                this.f5862mX = motionEvent.getX();
                this.f5863mY = motionEvent.getY();
                this.mTouchEvent = getTouchEvent(this.f5862mX, this.f5863mY);
                return this.mTouchEvent;
            } else if (actionMasked == 2) {
                if (this.mTouchEvent == StickerEvent.BODY) {
                    offset(motionEvent.getX() - this.f5862mX, motionEvent.getY() - this.f5863mY);
                    this.f5862mX = motionEvent.getX();
                    this.f5863mY = motionEvent.getY();
                }
                return this.mTouchEvent;
            } else {
                return this.mTouchEvent;
            }
        }
        return null;
    }

    private StickerEvent getTouchEvent(float f, float f2) {
        float[] fArr = {f, f2};
        Matrix matrix = new Matrix();
        matrix.setRotate(this.mRotate, this.mFrame.centerX(), this.mFrame.centerY());
        matrix.mapPoints(fArr);
        if (this.mFrame.contains(fArr[0], fArr[1])) {
            if (isInsideRemove(fArr[0], fArr[1])) {
                StickerEvent stickerEvent = StickerEvent.REMOVE;
                this.mTouchEvent = stickerEvent;
                return stickerEvent;
            } else if (isInsideAdjust(fArr[0], fArr[1])) {
                StickerEvent stickerEvent2 = StickerEvent.ADJUST;
                this.mTouchEvent = stickerEvent2;
                return stickerEvent2;
            } else {
                return StickerEvent.BODY;
            }
        }
        return null;
    }

    public void setTouchEvent(StickerEvent stickerEvent) {
        this.mTouchEvent = stickerEvent;
    }

    public boolean isInsideRemove(float f, float f2) {
        RectF rectF = this.mRemoveFrame;
        RectF rectF2 = this.mFrame;
        return rectF.contains(f - rectF2.left, f2 - rectF2.top);
    }

    public boolean isInsideAdjust(float f, float f2) {
        RectF rectF = this.mAdjustFrame;
        return rectF.contains((f - this.mFrame.right) + rectF.width(), (f2 - this.mFrame.bottom) + this.mAdjustFrame.height());
    }

    public boolean isInside(float f, float f2) {
        float[] fArr = {f, f2};
        Matrix matrix = new Matrix();
        matrix.setRotate(this.mRotate, this.mFrame.centerX(), this.mFrame.centerY());
        matrix.mapPoints(fArr);
        return this.mFrame.contains(fArr[0], fArr[1]);
    }

    public void transform(Matrix matrix) {
        matrix.mapPoints(this.mPivotXY);
        RectF rectF = this.mFrame;
        rectF.offset(this.mPivotXY[0] - rectF.centerX(), this.mPivotXY[1] - this.mFrame.centerY());
    }
}
