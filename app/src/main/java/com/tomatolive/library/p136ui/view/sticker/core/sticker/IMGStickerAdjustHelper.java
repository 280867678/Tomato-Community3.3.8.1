package com.tomatolive.library.p136ui.view.sticker.core.sticker;

import android.graphics.Matrix;
import android.view.MotionEvent;
import android.view.View;
import com.tomatolive.library.p136ui.view.sticker.view.IMGStickerView;

/* renamed from: com.tomatolive.library.ui.view.sticker.core.sticker.IMGStickerAdjustHelper */
/* loaded from: classes3.dex */
public class IMGStickerAdjustHelper implements View.OnTouchListener {
    private static final String TAG = "IMGStickerAdjustHelper";

    /* renamed from: M */
    private Matrix f5858M = new Matrix();
    private float lastScale;
    private float mCenterX;
    private float mCenterY;
    private IMGStickerView mContainer;
    private double mDegrees;
    private double mRadius;
    private View mView;

    public IMGStickerAdjustHelper(IMGStickerView iMGStickerView, View view) {
        this.mView = view;
        this.mContainer = iMGStickerView;
        this.mView.setOnTouchListener(this);
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action != 0) {
            if (action != 2) {
                return false;
            }
            float[] fArr = {motionEvent.getX(), motionEvent.getY()};
            double length = toLength(0.0f, 0.0f, (this.mView.getX() + fArr[0]) - this.mContainer.getPivotX(), (this.mView.getY() + fArr[1]) - this.mContainer.getPivotY());
            float f = (float) (length / this.mRadius);
            this.mContainer.addScale(f);
            this.lastScale = f;
            this.mRadius = length;
            return true;
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        this.mCenterY = 0.0f;
        this.mCenterX = 0.0f;
        float x2 = (this.mView.getX() + x) - this.mContainer.getPivotX();
        float y2 = (this.mView.getY() + y) - this.mContainer.getPivotY();
        this.mRadius = toLength(0.0f, 0.0f, x2, y2);
        this.mDegrees = toDegrees(y2, x2);
        this.f5858M.setTranslate(x2 - x, y2 - y);
        return true;
    }

    private static double toDegrees(float f, float f2) {
        return Math.toDegrees(Math.atan2(f, f2));
    }

    private static double toLength(float f, float f2, float f3, float f4) {
        float f5 = f - f3;
        float f6 = f2 - f4;
        return Math.sqrt((f5 * f5) + (f6 * f6));
    }
}
