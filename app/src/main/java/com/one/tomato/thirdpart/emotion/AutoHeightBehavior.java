package com.one.tomato.thirdpart.emotion;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import sj.keyboard.widget.AutoHeightLayout;

/* loaded from: classes3.dex */
public class AutoHeightBehavior extends AppBarLayout.ScrollingViewBehavior {
    private OnDependentViewChangedListener onDependentViewChangedListener;

    /* loaded from: classes3.dex */
    public interface OnDependentViewChangedListener {
        void onDependentViewChangedListener(CoordinatorLayout coordinatorLayout, View view, View view2);
    }

    public AutoHeightBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.support.design.widget.AppBarLayout.ScrollingViewBehavior, android.support.design.widget.CoordinatorLayout.Behavior
    public boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, View view, View view2) {
        boolean onDependentViewChanged = super.onDependentViewChanged(coordinatorLayout, view, view2);
        if (view instanceof AutoHeightLayout) {
            ((AutoHeightLayout) view).updateMaxParentHeight(view.getHeight() - view2.getHeight());
            OnDependentViewChangedListener onDependentViewChangedListener = this.onDependentViewChangedListener;
            if (onDependentViewChangedListener != null) {
                onDependentViewChangedListener.onDependentViewChangedListener(coordinatorLayout, view, view2);
            }
        }
        return onDependentViewChanged;
    }

    public void setOnDependentViewChangedListener(OnDependentViewChangedListener onDependentViewChangedListener) {
        this.onDependentViewChangedListener = onDependentViewChangedListener;
    }
}
