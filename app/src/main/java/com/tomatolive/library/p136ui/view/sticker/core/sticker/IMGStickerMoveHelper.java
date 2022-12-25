package com.tomatolive.library.p136ui.view.sticker.core.sticker;

import android.graphics.Matrix;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.blankj.utilcode.util.ScreenUtils;

/* renamed from: com.tomatolive.library.ui.view.sticker.core.sticker.IMGStickerMoveHelper */
/* loaded from: classes3.dex */
public class IMGStickerMoveHelper {

    /* renamed from: M */
    private static final Matrix f5859M = new Matrix();
    private static final String TAG = "IMGStickerMoveHelper";
    private View mView;

    /* renamed from: mX */
    private float f5860mX;

    /* renamed from: mY */
    private float f5861mY;

    public IMGStickerMoveHelper(View view) {
        this.mView = view;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.f5860mX = motionEvent.getX();
            this.f5861mY = motionEvent.getY();
            f5859M.reset();
            f5859M.setRotate(view.getRotation());
            return true;
        } else if (actionMasked != 2) {
            return false;
        } else {
            ViewGroup viewGroup = (ViewGroup) view.getParent();
            float[] fArr = {motionEvent.getX() - this.f5860mX, motionEvent.getY() - this.f5861mY};
            f5859M.mapPoints(fArr);
            if (view.getLeft() + this.mView.getTranslationX() + fArr[0] >= 0.0f && view.getLeft() + this.mView.getTranslationX() + fArr[0] + (view.getRight() - view.getLeft()) <= viewGroup.getWidth()) {
                view.setTranslationX(this.mView.getTranslationX() + fArr[0]);
            } else if (view.getLeft() + this.mView.getTranslationX() + fArr[0] < 0.0f) {
                if (fArr[0] >= 0.0f) {
                    view.setTranslationX(this.mView.getTranslationX() + fArr[0]);
                }
            } else if (view.getLeft() + this.mView.getTranslationX() + fArr[0] + (view.getRight() - view.getLeft()) > viewGroup.getWidth() && fArr[0] <= 0.0f) {
                view.setTranslationX(this.mView.getTranslationX() + fArr[0]);
            }
            if (view.getTop() + this.mView.getTranslationY() + fArr[1] >= (ScreenUtils.getScreenHeight() / 10) + 0 && ((view.getTop() + (this.mView.getTranslationY() + fArr[1])) + view.getBottom()) - view.getTop() <= viewGroup.getHeight() - (ScreenUtils.getScreenHeight() / 10)) {
                view.setTranslationY(this.mView.getTranslationY() + fArr[1]);
            } else if (view.getTop() + this.mView.getTranslationY() + fArr[1] < 0.0f) {
                if (fArr[1] >= (ScreenUtils.getScreenHeight() / 10) + 0) {
                    view.setTranslationY(this.mView.getTranslationY() + fArr[1]);
                }
            } else if (((view.getTop() + (this.mView.getTranslationY() + fArr[1])) + view.getBottom()) - view.getTop() > viewGroup.getHeight() - (ScreenUtils.getScreenHeight() / 10) && fArr[1] <= 0.0f) {
                view.setTranslationY(this.mView.getTranslationY() + fArr[1]);
            }
            return true;
        }
    }
}
