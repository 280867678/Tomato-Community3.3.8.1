package com.one.tomato.thirdpart.appbar;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.p002v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;
import java.lang.reflect.Field;

/* loaded from: classes3.dex */
public class FlingBehavior extends AppBarLayout.Behavior {
    public FlingBehavior() {
    }

    public FlingBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.support.design.widget.AppBarLayout.BaseBehavior, android.support.design.widget.CoordinatorLayout.Behavior
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int i, int i2, int[] iArr, int i3) {
        if (i3 == 1 && getTopAndBottomOffset() == 0) {
            ViewCompat.stopNestedScroll(view, i3);
        }
        super.onNestedPreScroll(coordinatorLayout, appBarLayout, view, i, i2, iArr, i3);
    }

    @Override // android.support.design.widget.HeaderBehavior, android.support.design.widget.CoordinatorLayout.Behavior
    public boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, MotionEvent motionEvent) {
        Object superSuperField;
        if (motionEvent.getAction() == 0 && (superSuperField = getSuperSuperField(this, "mScroller")) != null && (superSuperField instanceof OverScroller)) {
            ((OverScroller) superSuperField).abortAnimation();
        }
        return super.onInterceptTouchEvent(coordinatorLayout, (CoordinatorLayout) appBarLayout, motionEvent);
    }

    private Object getSuperSuperField(Object obj, String str) {
        try {
            Field declaredField = obj.getClass().getSuperclass().getSuperclass().getDeclaredField(str);
            declaredField.setAccessible(true);
            return declaredField.get(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
