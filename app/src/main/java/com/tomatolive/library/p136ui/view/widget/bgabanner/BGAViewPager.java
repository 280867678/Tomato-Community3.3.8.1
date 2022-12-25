package com.tomatolive.library.p136ui.view.widget.bgabanner;

import android.content.Context;
import android.support.p002v4.view.VelocityTrackerCompat;
import android.support.p002v4.view.ViewCompat;
import android.support.p002v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/* renamed from: com.tomatolive.library.ui.view.widget.bgabanner.BGAViewPager */
/* loaded from: classes4.dex */
public class BGAViewPager extends ViewPager {
    private boolean mAllowUserScrollable = true;
    private AutoPlayDelegate mAutoPlayDelegate;

    /* renamed from: com.tomatolive.library.ui.view.widget.bgabanner.BGAViewPager$AutoPlayDelegate */
    /* loaded from: classes4.dex */
    public interface AutoPlayDelegate {
        void handleAutoPlayActionUpOrCancel(float f);

        void onTouchListener(MotionEvent motionEvent);
    }

    public BGAViewPager(Context context) {
        super(context);
    }

    public BGAViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.support.p002v4.view.ViewPager
    public void setPageTransformer(boolean z, ViewPager.PageTransformer pageTransformer) {
        boolean z2 = pageTransformer != null;
        try {
            Field declaredField = ViewPager.class.getDeclaredField("mPageTransformer");
            declaredField.setAccessible(true);
            boolean z3 = z2 != (((ViewPager.PageTransformer) declaredField.get(this)) != null);
            declaredField.set(this, pageTransformer);
            Method declaredMethod = ViewPager.class.getDeclaredMethod("setChildrenDrawingOrderEnabledCompat", Boolean.TYPE);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(this, Boolean.valueOf(z2));
            Field declaredField2 = ViewPager.class.getDeclaredField("mDrawingOrder");
            declaredField2.setAccessible(true);
            if (z2) {
                declaredField2.setInt(this, z ? 2 : 1);
            } else {
                declaredField2.setInt(this, 0);
            }
            if (!z3) {
                return;
            }
            Method declaredMethod2 = ViewPager.class.getDeclaredMethod("populate", new Class[0]);
            declaredMethod2.setAccessible(true);
            declaredMethod2.invoke(this, new Object[0]);
        } catch (Exception unused) {
        }
    }

    public void setPageChangeDuration(int i) {
        try {
            Field declaredField = ViewPager.class.getDeclaredField("mScroller");
            declaredField.setAccessible(true);
            declaredField.set(this, new BGABannerScroller(getContext(), i));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setBannerCurrentItemInternal(int i, boolean z) {
        try {
            Method declaredMethod = ViewPager.class.getDeclaredMethod("setCurrentItemInternal", Integer.TYPE, Boolean.TYPE, Boolean.TYPE);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(this, Integer.valueOf(i), Boolean.valueOf(z), true);
            ViewCompat.postInvalidateOnAnimation(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAllowUserScrollable(boolean z) {
        this.mAllowUserScrollable = z;
    }

    @Override // android.support.p002v4.view.ViewPager, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (!this.mAllowUserScrollable || getAdapter() == null || getAdapter().getCount() <= 0) {
            return false;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.support.p002v4.view.ViewPager, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        AutoPlayDelegate autoPlayDelegate = this.mAutoPlayDelegate;
        if (autoPlayDelegate != null) {
            autoPlayDelegate.onTouchListener(motionEvent);
        }
        if (!this.mAllowUserScrollable || getAdapter() == null || getAdapter().getCount() <= 0) {
            return false;
        }
        if (this.mAutoPlayDelegate != null && (motionEvent.getAction() == 3 || motionEvent.getAction() == 1)) {
            this.mAutoPlayDelegate.handleAutoPlayActionUpOrCancel(getXVelocity());
            return false;
        }
        return super.onTouchEvent(motionEvent);
    }

    private float getXVelocity() {
        try {
            Field declaredField = ViewPager.class.getDeclaredField("mVelocityTracker");
            declaredField.setAccessible(true);
            VelocityTracker velocityTracker = (VelocityTracker) declaredField.get(this);
            Field declaredField2 = ViewPager.class.getDeclaredField("mActivePointerId");
            declaredField2.setAccessible(true);
            Field declaredField3 = ViewPager.class.getDeclaredField("mMaximumVelocity");
            declaredField3.setAccessible(true);
            velocityTracker.computeCurrentVelocity(1000, declaredField3.getInt(this));
            return VelocityTrackerCompat.getXVelocity(velocityTracker, declaredField2.getInt(this));
        } catch (Exception unused) {
            return 0.0f;
        }
    }

    public void setAutoPlayDelegate(AutoPlayDelegate autoPlayDelegate) {
        this.mAutoPlayDelegate = autoPlayDelegate;
    }
}
