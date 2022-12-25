package com.tomatolive.library.p136ui.view.widget.swipeview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;
import com.tomatolive.library.R$styleable;
import java.util.ArrayList;

/* renamed from: com.tomatolive.library.ui.view.widget.swipeview.EasySwipeMenuLayout */
/* loaded from: classes4.dex */
public class EasySwipeMenuLayout extends ViewGroup {
    private static final String TAG = "EasySwipeMenuLayout";
    private static State mStateCache;
    private static EasySwipeMenuLayout mViewCache;
    private float distanceX;
    private float finalyDistanceX;
    private boolean isSwipeing;
    private boolean mCanLeftSwipe;
    private boolean mCanRightSwipe;
    private View mContentView;
    private ViewGroup.MarginLayoutParams mContentViewLp;
    private int mContentViewResID;
    private PointF mFirstP;
    private float mFraction;
    private PointF mLastP;
    private View mLeftView;
    private int mLeftViewResID;
    private final ArrayList<View> mMatchParentChildren;
    private View mRightView;
    private int mRightViewResID;
    private int mScaledTouchSlop;
    private Scroller mScroller;
    State result;

    public EasySwipeMenuLayout(Context context) {
        this(context, null);
    }

    public EasySwipeMenuLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public EasySwipeMenuLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mMatchParentChildren = new ArrayList<>(1);
        this.mFraction = 0.3f;
        this.mCanLeftSwipe = true;
        this.mCanRightSwipe = true;
        init(context, attributeSet, i);
    }

    private void init(Context context, AttributeSet attributeSet, int i) {
        this.mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mScroller = new Scroller(context);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R$styleable.EasySwipeMenuLayout, i, 0);
        try {
            try {
                int indexCount = obtainStyledAttributes.getIndexCount();
                for (int i2 = 0; i2 < indexCount; i2++) {
                    int index = obtainStyledAttributes.getIndex(i2);
                    if (index == R$styleable.EasySwipeMenuLayout_leftMenuView) {
                        this.mLeftViewResID = obtainStyledAttributes.getResourceId(R$styleable.EasySwipeMenuLayout_leftMenuView, -1);
                    } else if (index == R$styleable.EasySwipeMenuLayout_rightMenuView) {
                        this.mRightViewResID = obtainStyledAttributes.getResourceId(R$styleable.EasySwipeMenuLayout_rightMenuView, -1);
                    } else if (index == R$styleable.EasySwipeMenuLayout_contentView) {
                        this.mContentViewResID = obtainStyledAttributes.getResourceId(R$styleable.EasySwipeMenuLayout_contentView, -1);
                    } else if (index == R$styleable.EasySwipeMenuLayout_canLeftSwipe) {
                        this.mCanLeftSwipe = obtainStyledAttributes.getBoolean(R$styleable.EasySwipeMenuLayout_canLeftSwipe, true);
                    } else if (index == R$styleable.EasySwipeMenuLayout_canRightSwipe) {
                        this.mCanRightSwipe = obtainStyledAttributes.getBoolean(R$styleable.EasySwipeMenuLayout_canRightSwipe, true);
                    } else if (index == R$styleable.EasySwipeMenuLayout_fraction) {
                        this.mFraction = obtainStyledAttributes.getFloat(R$styleable.EasySwipeMenuLayout_fraction, 0.5f);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int childMeasureSpec;
        int childMeasureSpec2;
        super.onMeasure(i, i2);
        setClickable(true);
        int childCount = getChildCount();
        boolean z = (View.MeasureSpec.getMode(i) == 1073741824 && View.MeasureSpec.getMode(i2) == 1073741824) ? false : true;
        this.mMatchParentChildren.clear();
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = getChildAt(i6);
            if (childAt.getVisibility() != 8) {
                measureChildWithMargins(childAt, i, 0, i2, 0);
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) childAt.getLayoutParams();
                int max = Math.max(i4, childAt.getMeasuredWidth() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin);
                int max2 = Math.max(i5, childAt.getMeasuredHeight() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin);
                int combineMeasuredStates = ViewGroup.combineMeasuredStates(i3, childAt.getMeasuredState());
                if (z && (marginLayoutParams.width == -1 || marginLayoutParams.height == -1)) {
                    this.mMatchParentChildren.add(childAt);
                }
                i4 = max;
                i5 = max2;
                i3 = combineMeasuredStates;
            }
        }
        int i7 = i3;
        setMeasuredDimension(ViewGroup.resolveSizeAndState(Math.max(i4, getSuggestedMinimumWidth()), i, i7), ViewGroup.resolveSizeAndState(Math.max(i5, getSuggestedMinimumHeight()), i2, i7 << 16));
        int size = this.mMatchParentChildren.size();
        if (size > 1) {
            for (int i8 = 0; i8 < size; i8++) {
                View view = this.mMatchParentChildren.get(i8);
                ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                if (marginLayoutParams2.width == -1) {
                    childMeasureSpec = View.MeasureSpec.makeMeasureSpec(Math.max(0, (getMeasuredWidth() - marginLayoutParams2.leftMargin) - marginLayoutParams2.rightMargin), 1073741824);
                } else {
                    childMeasureSpec = ViewGroup.getChildMeasureSpec(i, marginLayoutParams2.leftMargin + marginLayoutParams2.rightMargin, marginLayoutParams2.width);
                }
                if (marginLayoutParams2.height == -1) {
                    childMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(Math.max(0, (getMeasuredHeight() - marginLayoutParams2.topMargin) - marginLayoutParams2.bottomMargin), 1073741824);
                } else {
                    childMeasureSpec2 = ViewGroup.getChildMeasureSpec(i2, marginLayoutParams2.topMargin + marginLayoutParams2.bottomMargin, marginLayoutParams2.height);
                }
                view.measure(childMeasureSpec, childMeasureSpec2);
            }
        }
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new ViewGroup.MarginLayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int childCount = getChildCount();
        int paddingLeft = getPaddingLeft() + 0;
        getPaddingLeft();
        int paddingTop = getPaddingTop() + 0;
        getPaddingTop();
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            if (this.mLeftView == null && childAt.getId() == this.mLeftViewResID) {
                this.mLeftView = childAt;
                this.mLeftView.setClickable(true);
            } else if (this.mRightView == null && childAt.getId() == this.mRightViewResID) {
                this.mRightView = childAt;
                this.mRightView.setClickable(true);
            } else if (this.mContentView == null && childAt.getId() == this.mContentViewResID) {
                this.mContentView = childAt;
                this.mContentView.setClickable(true);
            }
        }
        View view = this.mContentView;
        if (view != null) {
            this.mContentViewLp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            ViewGroup.MarginLayoutParams marginLayoutParams = this.mContentViewLp;
            int i6 = marginLayoutParams.topMargin + paddingTop;
            int i7 = marginLayoutParams.leftMargin;
            this.mContentView.layout(paddingLeft + i7, i6, paddingLeft + i7 + this.mContentView.getMeasuredWidth(), this.mContentView.getMeasuredHeight() + i6);
        }
        View view2 = this.mLeftView;
        if (view2 != null) {
            ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) view2.getLayoutParams();
            int i8 = marginLayoutParams2.topMargin + paddingTop;
            int i9 = marginLayoutParams2.rightMargin;
            this.mLeftView.layout((0 - this.mLeftView.getMeasuredWidth()) + marginLayoutParams2.leftMargin + i9, i8, 0 - i9, this.mLeftView.getMeasuredHeight() + i8);
        }
        View view3 = this.mRightView;
        if (view3 != null) {
            ViewGroup.MarginLayoutParams marginLayoutParams3 = (ViewGroup.MarginLayoutParams) view3.getLayoutParams();
            int i10 = paddingTop + marginLayoutParams3.topMargin;
            int right = this.mContentView.getRight() + this.mContentViewLp.rightMargin + marginLayoutParams3.leftMargin;
            this.mRightView.layout(right, i10, this.mRightView.getMeasuredWidth() + right, this.mRightView.getMeasuredHeight() + i10);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x000e, code lost:
        if (r0 != 3) goto L8;
     */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.isSwipeing = false;
            if (this.mLastP == null) {
                this.mLastP = new PointF();
            }
            this.mLastP.set(motionEvent.getRawX(), motionEvent.getRawY());
            if (this.mFirstP == null) {
                this.mFirstP = new PointF();
            }
            this.mFirstP.set(motionEvent.getRawX(), motionEvent.getRawY());
            EasySwipeMenuLayout easySwipeMenuLayout = mViewCache;
            if (easySwipeMenuLayout != null) {
                if (easySwipeMenuLayout != this) {
                    easySwipeMenuLayout.handlerSwipeMenu(State.CLOSE);
                }
                getParent().requestDisallowInterceptTouchEvent(true);
            }
        } else {
            if (action != 1) {
                if (action == 2) {
                    float rawX = this.mLastP.x - motionEvent.getRawX();
                    float rawY = this.mLastP.y - motionEvent.getRawY();
                    if (Math.abs(rawY) <= this.mScaledTouchSlop || Math.abs(rawY) <= Math.abs(rawX)) {
                        scrollBy((int) rawX, 0);
                        if (getScrollX() < 0) {
                            if (!this.mCanRightSwipe || this.mLeftView == null) {
                                scrollTo(0, 0);
                            } else if (getScrollX() < this.mLeftView.getLeft()) {
                                scrollTo(this.mLeftView.getLeft(), 0);
                            }
                        } else if (getScrollX() > 0) {
                            if (!this.mCanLeftSwipe || this.mRightView == null) {
                                scrollTo(0, 0);
                            } else if (getScrollX() > (this.mRightView.getRight() - this.mContentView.getRight()) - this.mContentViewLp.rightMargin) {
                                scrollTo((this.mRightView.getRight() - this.mContentView.getRight()) - this.mContentViewLp.rightMargin, 0);
                            }
                        }
                        if (Math.abs(rawX) > this.mScaledTouchSlop) {
                            getParent().requestDisallowInterceptTouchEvent(true);
                        }
                        this.mLastP.set(motionEvent.getRawX(), motionEvent.getRawY());
                    }
                }
            }
            this.finalyDistanceX = this.mFirstP.x - motionEvent.getRawX();
            if (Math.abs(this.finalyDistanceX) > this.mScaledTouchSlop) {
                this.isSwipeing = true;
            }
            this.result = isShouldOpen(getScrollX());
            handlerSwipeMenu(this.result);
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x000d, code lost:
        if (r0 != 3) goto L17;
     */
    @Override // android.view.ViewGroup
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action != 0) {
            if (action != 1) {
                if (action == 2) {
                    if (Math.abs(this.finalyDistanceX) > this.mScaledTouchSlop) {
                        return true;
                    }
                }
            }
            if (this.isSwipeing) {
                this.isSwipeing = false;
                this.finalyDistanceX = 0.0f;
                return true;
            }
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    private void handlerSwipeMenu(State state) {
        if (state == State.LEFTOPEN) {
            this.mScroller.startScroll(getScrollX(), 0, this.mLeftView.getLeft() - getScrollX(), 0);
            mViewCache = this;
            mStateCache = state;
        } else if (state == State.RIGHTOPEN) {
            mViewCache = this;
            this.mScroller.startScroll(getScrollX(), 0, ((this.mRightView.getRight() - this.mContentView.getRight()) - this.mContentViewLp.rightMargin) - getScrollX(), 0);
            mStateCache = state;
        } else {
            this.mScroller.startScroll(getScrollX(), 0, -getScrollX(), 0);
            mViewCache = null;
            mStateCache = null;
        }
        invalidate();
    }

    @Override // android.view.View
    public void computeScroll() {
        if (this.mScroller.computeScrollOffset()) {
            scrollTo(this.mScroller.getCurrX(), this.mScroller.getCurrY());
            invalidate();
        }
    }

    private State isShouldOpen(int i) {
        View view;
        View view2;
        if (this.mScaledTouchSlop >= Math.abs(this.finalyDistanceX)) {
            return mStateCache;
        }
        Log.i(TAG, ">>>finalyDistanceX:" + this.finalyDistanceX);
        float f = this.finalyDistanceX;
        if (f < 0.0f) {
            if (getScrollX() < 0 && (view2 = this.mLeftView) != null && Math.abs(view2.getWidth() * this.mFraction) < Math.abs(getScrollX())) {
                return State.LEFTOPEN;
            }
            if (getScrollX() > 0 && this.mRightView != null) {
                return State.CLOSE;
            }
        } else if (f > 0.0f) {
            if (getScrollX() > 0 && (view = this.mRightView) != null && Math.abs(view.getWidth() * this.mFraction) < Math.abs(getScrollX())) {
                return State.RIGHTOPEN;
            }
            if (getScrollX() < 0 && this.mLeftView != null) {
                return State.CLOSE;
            }
        }
        return State.CLOSE;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        EasySwipeMenuLayout easySwipeMenuLayout = mViewCache;
        if (this == easySwipeMenuLayout) {
            easySwipeMenuLayout.handlerSwipeMenu(State.CLOSE);
        }
        super.onDetachedFromWindow();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        EasySwipeMenuLayout easySwipeMenuLayout = mViewCache;
        if (this == easySwipeMenuLayout) {
            easySwipeMenuLayout.handlerSwipeMenu(mStateCache);
        }
    }

    public void resetStatus() {
        State state;
        Scroller scroller;
        if (mViewCache == null || (state = mStateCache) == null || state == State.CLOSE || (scroller = this.mScroller) == null) {
            return;
        }
        scroller.startScroll(mViewCache.getScrollX(), 0, -mViewCache.getScrollX(), 0);
        mViewCache.invalidate();
        mViewCache = null;
        mStateCache = null;
    }

    public float getFraction() {
        return this.mFraction;
    }

    public void setFraction(float f) {
        this.mFraction = f;
    }

    public boolean isCanLeftSwipe() {
        return this.mCanLeftSwipe;
    }

    public void setCanLeftSwipe(boolean z) {
        this.mCanLeftSwipe = z;
    }

    public boolean isCanRightSwipe() {
        return this.mCanRightSwipe;
    }

    public void setCanRightSwipe(boolean z) {
        this.mCanRightSwipe = z;
    }

    public static EasySwipeMenuLayout getViewCache() {
        return mViewCache;
    }

    public static State getStateCache() {
        return mStateCache;
    }

    private boolean isLeftToRight() {
        return this.distanceX < 0.0f;
    }
}
