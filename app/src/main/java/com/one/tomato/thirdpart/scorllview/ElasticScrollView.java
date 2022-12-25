package com.one.tomato.thirdpart.scorllview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ScrollView;

/* loaded from: classes3.dex */
public class ElasticScrollView extends ScrollView {
    private int bottomPosition;
    private View contentView;
    private boolean isCanPullDown;
    private boolean isCanPullUp;
    private int leftPosition;
    private int rightPosition;
    private float startY;
    private int topPosition;

    public ElasticScrollView(Context context) {
        super(context);
    }

    public ElasticScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {
            this.contentView = getChildAt(0);
        }
    }

    @Override // android.widget.ScrollView, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        View view = this.contentView;
        if (view != null) {
            this.leftPosition = view.getLeft();
            this.topPosition = this.contentView.getTop();
            this.rightPosition = this.contentView.getRight();
            this.bottomPosition = this.contentView.getBottom();
        }
    }

    private boolean isScrollViewTop() {
        return getScrollY() == 0;
    }

    private boolean isScrollViewBottom() {
        return this.contentView.getMeasuredHeight() <= getScrollY() + getHeight();
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (this.contentView == null) {
            return super.dispatchTouchEvent(motionEvent);
        }
        int action = motionEvent.getAction();
        if (action == 0) {
            this.startY = motionEvent.getY();
            this.isCanPullDown = isScrollViewTop();
            this.isCanPullUp = isScrollViewBottom();
        } else if (action == 1) {
            float y = motionEvent.getY();
            if ((y > this.startY && this.isCanPullDown) || (y < this.startY && this.isCanPullUp)) {
                View view = this.contentView;
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "translationY", view.getTop(), this.topPosition);
                ofFloat.setDuration(500L);
                ofFloat.setInterpolator(new AccelerateInterpolator());
                ofFloat.start();
                this.contentView.layout(this.leftPosition, this.topPosition, this.rightPosition, this.bottomPosition);
                return true;
            }
        } else if (action == 2) {
            if (!this.isCanPullUp && !this.isCanPullDown) {
                this.startY = motionEvent.getY();
                this.isCanPullDown = isScrollViewTop();
                this.isCanPullUp = isScrollViewBottom();
            }
            if ((this.isCanPullDown && motionEvent.getY() > this.startY) || ((this.isCanPullUp && motionEvent.getY() < this.startY) || (this.isCanPullDown && this.isCanPullUp))) {
                int y2 = (int) (motionEvent.getY() - this.startY);
                this.contentView.layout(this.leftPosition, this.topPosition + y2, this.rightPosition, this.bottomPosition + y2);
                return true;
            }
        }
        return super.dispatchTouchEvent(motionEvent);
    }
}
