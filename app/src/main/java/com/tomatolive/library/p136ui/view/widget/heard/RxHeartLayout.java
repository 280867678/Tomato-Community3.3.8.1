package com.tomatolive.library.p136ui.view.widget.heard;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.tomatolive.library.R$styleable;
import com.tomatolive.library.p136ui.view.widget.heard.animation.RxAbstractPathAnimator;
import com.tomatolive.library.p136ui.view.widget.heard.animation.RxPathAnimator;

/* renamed from: com.tomatolive.library.ui.view.widget.heard.RxHeartLayout */
/* loaded from: classes4.dex */
public class RxHeartLayout extends RelativeLayout {
    private RxAbstractPathAnimator mAnimator;

    public RxHeartLayout(Context context) {
        super(context);
        init(null, 0);
    }

    public RxHeartLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(attributeSet, 0);
    }

    public RxHeartLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(attributeSet, i);
    }

    private void init(AttributeSet attributeSet, int i) {
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.RxHeartLayout, i, 0);
        this.mAnimator = new RxPathAnimator(RxAbstractPathAnimator.Config.fromTypeArray(obtainStyledAttributes));
        obtainStyledAttributes.recycle();
    }

    public RxAbstractPathAnimator getAnimator() {
        return this.mAnimator;
    }

    public void setAnimator(RxAbstractPathAnimator rxAbstractPathAnimator) {
        clearAnimation();
        this.mAnimator = rxAbstractPathAnimator;
    }

    @Override // android.view.View
    public void clearAnimation() {
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).clearAnimation();
        }
        removeAllViews();
    }

    public void addHeart(int i) {
        RxHeartView rxHeartView = new RxHeartView(getContext());
        rxHeartView.setColor(i);
        this.mAnimator.start(false, rxHeartView, this);
    }

    public void addHeart(int i, int i2, int i3) {
        RxHeartView rxHeartView = new RxHeartView(getContext());
        rxHeartView.setColorAndDrawables(i, i2, i3);
        this.mAnimator.start(false, rxHeartView, this);
    }
}
