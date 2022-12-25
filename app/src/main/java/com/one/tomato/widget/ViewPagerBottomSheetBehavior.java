package com.one.tomato.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.CoordinatorLayout;
import android.support.p002v4.math.MathUtils;
import android.support.p002v4.view.AbsSavedState;
import android.support.p002v4.view.ViewCompat;
import android.support.p002v4.view.ViewPager;
import android.support.p002v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.broccoli.p150bh.R;
import com.one.tomato.R$styleable;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class ViewPagerBottomSheetBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
    int activePointerId;
    private BottomSheetCallback callback;
    int collapsedOffset;
    int fitToContentsOffset;
    int halfExpandedOffset;
    boolean hideable;
    private boolean ignoreEvents;
    private Map<View, Integer> importantForAccessibilityMap;
    private int initialY;
    private int lastNestedScrollDy;
    private int lastPeekHeight;
    private float maximumVelocity;
    private boolean nestedScrolled;
    WeakReference<View> nestedScrollingChildRef;
    int parentHeight;
    private int peekHeight;
    private boolean peekHeightAuto;
    private int peekHeightMin;
    private boolean skipCollapsed;
    boolean touchingScrollingChild;
    private VelocityTracker velocityTracker;
    ViewDragHelper viewDragHelper;
    WeakReference<V> viewRef;
    private boolean fitToContents = true;
    int state = 4;
    private final ViewDragHelper.Callback dragCallback = new NamelessClass_1();

    /* loaded from: classes3.dex */
    public static abstract class BottomSheetCallback {
        public abstract void onSlide(@NonNull View view, float f);

        public abstract void onStateChanged(@NonNull View view, int i);
    }

    public ViewPagerBottomSheetBehavior() {
    }

    public ViewPagerBottomSheetBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        int i;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.BottomSheetBehavior_Layout);
        TypedValue peekValue = obtainStyledAttributes.peekValue(2);
        if (peekValue != null && (i = peekValue.data) == -1) {
            setPeekHeight(i);
        } else {
            setPeekHeight(obtainStyledAttributes.getDimensionPixelSize(2, -1));
        }
        setHideable(obtainStyledAttributes.getBoolean(1, false));
        setFitToContents(obtainStyledAttributes.getBoolean(0, true));
        setSkipCollapsed(obtainStyledAttributes.getBoolean(3, false));
        obtainStyledAttributes.recycle();
        this.maximumVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
    }

    @Override // android.support.design.widget.CoordinatorLayout.Behavior
    public Parcelable onSaveInstanceState(CoordinatorLayout coordinatorLayout, V v) {
        return new SavedState(super.onSaveInstanceState(coordinatorLayout, v), this.state);
    }

    @Override // android.support.design.widget.CoordinatorLayout.Behavior
    public void onRestoreInstanceState(CoordinatorLayout coordinatorLayout, V v, Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(coordinatorLayout, v, savedState.getSuperState());
        int i = savedState.state;
        if (i != 1 && i != 2) {
            this.state = i;
        } else {
            this.state = 4;
        }
    }

    @Override // android.support.design.widget.CoordinatorLayout.Behavior
    public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, V v, int i) {
        if (ViewCompat.getFitsSystemWindows(coordinatorLayout) && !ViewCompat.getFitsSystemWindows(v)) {
            v.setFitsSystemWindows(true);
        }
        int top2 = v.getTop();
        coordinatorLayout.onLayoutChild(v, i);
        this.parentHeight = coordinatorLayout.getHeight();
        if (this.peekHeightAuto) {
            if (this.peekHeightMin == 0) {
                this.peekHeightMin = coordinatorLayout.getResources().getDimensionPixelSize(R.dimen.design_bottom_sheet_peek_height_min);
            }
            this.lastPeekHeight = Math.max(this.peekHeightMin, this.parentHeight - ((coordinatorLayout.getWidth() * 9) / 16));
        } else {
            this.lastPeekHeight = this.peekHeight;
        }
        this.fitToContentsOffset = Math.max(0, this.parentHeight - v.getHeight());
        this.halfExpandedOffset = this.parentHeight / 2;
        calculateCollapsedOffset();
        int i2 = this.state;
        if (i2 == 3) {
            ViewCompat.offsetTopAndBottom(v, getExpandedOffset());
        } else if (i2 == 6) {
            ViewCompat.offsetTopAndBottom(v, this.halfExpandedOffset);
        } else if (this.hideable && i2 == 5) {
            ViewCompat.offsetTopAndBottom(v, this.parentHeight);
        } else {
            int i3 = this.state;
            if (i3 == 4) {
                ViewCompat.offsetTopAndBottom(v, this.collapsedOffset);
            } else if (i3 == 1 || i3 == 2) {
                ViewCompat.offsetTopAndBottom(v, top2 - v.getTop());
            }
        }
        if (this.viewDragHelper == null) {
            this.viewDragHelper = ViewDragHelper.create(coordinatorLayout, this.dragCallback);
        }
        this.viewRef = new WeakReference<>(v);
        this.nestedScrollingChildRef = new WeakReference<>(findScrollingChild(v));
        return true;
    }

    @Override // android.support.design.widget.CoordinatorLayout.Behavior
    public boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
        ViewDragHelper viewDragHelper;
        if (!v.isShown()) {
            this.ignoreEvents = true;
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            reset();
        }
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
        this.velocityTracker.addMovement(motionEvent);
        View view = null;
        if (actionMasked == 0) {
            int x = (int) motionEvent.getX();
            this.initialY = (int) motionEvent.getY();
            WeakReference<View> weakReference = this.nestedScrollingChildRef;
            View view2 = weakReference != null ? weakReference.get() : null;
            if (view2 != null && coordinatorLayout.isPointInChildBounds(view2, x, this.initialY)) {
                this.activePointerId = motionEvent.getPointerId(motionEvent.getActionIndex());
                this.touchingScrollingChild = true;
            }
            this.ignoreEvents = this.activePointerId == -1 && !coordinatorLayout.isPointInChildBounds(v, x, this.initialY);
        } else if (actionMasked == 1 || actionMasked == 3) {
            this.touchingScrollingChild = false;
            this.activePointerId = -1;
            if (this.ignoreEvents) {
                this.ignoreEvents = false;
                return false;
            }
        }
        if (!this.ignoreEvents && (viewDragHelper = this.viewDragHelper) != null && viewDragHelper.shouldInterceptTouchEvent(motionEvent)) {
            return true;
        }
        WeakReference<View> weakReference2 = this.nestedScrollingChildRef;
        if (weakReference2 != null) {
            view = weakReference2.get();
        }
        return actionMasked == 2 && view != null && !this.ignoreEvents && this.state != 1 && !coordinatorLayout.isPointInChildBounds(view, (int) motionEvent.getX(), (int) motionEvent.getY()) && this.viewDragHelper != null && Math.abs(((float) this.initialY) - motionEvent.getY()) > ((float) this.viewDragHelper.getTouchSlop());
    }

    @Override // android.support.design.widget.CoordinatorLayout.Behavior
    public boolean onTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
        if (!v.isShown()) {
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (this.state == 1 && actionMasked == 0) {
            return true;
        }
        ViewDragHelper viewDragHelper = this.viewDragHelper;
        if (viewDragHelper != null) {
            viewDragHelper.processTouchEvent(motionEvent);
        }
        if (actionMasked == 0) {
            reset();
        }
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
        this.velocityTracker.addMovement(motionEvent);
        if (actionMasked == 2 && !this.ignoreEvents && Math.abs(this.initialY - motionEvent.getY()) > this.viewDragHelper.getTouchSlop()) {
            this.viewDragHelper.captureChildView(v, motionEvent.getPointerId(motionEvent.getActionIndex()));
        }
        return !this.ignoreEvents;
    }

    @Override // android.support.design.widget.CoordinatorLayout.Behavior
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, @NonNull View view2, int i, int i2) {
        this.lastNestedScrollDy = 0;
        this.nestedScrolled = false;
        return (i & 2) != 0;
    }

    @Override // android.support.design.widget.CoordinatorLayout.Behavior
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, int i, int i2, @NonNull int[] iArr, int i3) {
        if (i3 == 1 || view != this.nestedScrollingChildRef.get()) {
            return;
        }
        int top2 = v.getTop();
        int i4 = top2 - i2;
        if (i2 > 0) {
            if (i4 < getExpandedOffset()) {
                iArr[1] = top2 - getExpandedOffset();
                ViewCompat.offsetTopAndBottom(v, -iArr[1]);
                setStateInternal(3);
            } else {
                iArr[1] = i2;
                ViewCompat.offsetTopAndBottom(v, -i2);
                setStateInternal(1);
            }
        } else if (i2 < 0 && !view.canScrollVertically(-1)) {
            int i5 = this.collapsedOffset;
            if (i4 > i5 && !this.hideable) {
                iArr[1] = top2 - i5;
                ViewCompat.offsetTopAndBottom(v, -iArr[1]);
                setStateInternal(4);
            } else {
                iArr[1] = i2;
                ViewCompat.offsetTopAndBottom(v, -i2);
                setStateInternal(1);
            }
        }
        dispatchOnSlide(v.getTop());
        this.lastNestedScrollDy = i2;
        this.nestedScrolled = true;
    }

    @Override // android.support.design.widget.CoordinatorLayout.Behavior
    public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, int i) {
        int i2;
        int i3 = 3;
        if (v.getTop() == getExpandedOffset()) {
            setStateInternal(3);
        } else if (view != this.nestedScrollingChildRef.get() || !this.nestedScrolled) {
        } else {
            if (this.lastNestedScrollDy > 0) {
                i2 = getExpandedOffset();
            } else if (this.hideable && shouldHide(v, getYVelocity())) {
                i2 = this.parentHeight;
                i3 = 5;
            } else {
                if (this.lastNestedScrollDy == 0) {
                    int top2 = v.getTop();
                    if (this.fitToContents) {
                        if (Math.abs(top2 - this.fitToContentsOffset) < Math.abs(top2 - this.collapsedOffset)) {
                            i2 = this.fitToContentsOffset;
                        } else {
                            i2 = this.collapsedOffset;
                        }
                    } else {
                        int i4 = this.halfExpandedOffset;
                        if (top2 < i4) {
                            if (top2 < Math.abs(top2 - this.collapsedOffset)) {
                                i2 = 0;
                            } else {
                                i2 = this.halfExpandedOffset;
                            }
                        } else if (Math.abs(top2 - i4) < Math.abs(top2 - this.collapsedOffset)) {
                            i2 = this.halfExpandedOffset;
                        } else {
                            i2 = this.collapsedOffset;
                        }
                        i3 = 6;
                    }
                } else {
                    i2 = this.collapsedOffset;
                }
                i3 = 4;
            }
            if (this.viewDragHelper.smoothSlideViewTo(v, v.getLeft(), i2)) {
                setStateInternal(2);
                ViewCompat.postOnAnimation(v, new SettleRunnable(v, i3));
            } else {
                setStateInternal(i3);
            }
            this.nestedScrolled = false;
        }
    }

    @Override // android.support.design.widget.CoordinatorLayout.Behavior
    public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, @NonNull View view, float f, float f2) {
        return view == this.nestedScrollingChildRef.get() && (this.state != 3 || super.onNestedPreFling(coordinatorLayout, v, view, f, f2));
    }

    public void setFitToContents(boolean z) {
        if (this.fitToContents != z) {
            this.fitToContents = z;
            if (this.viewRef != null) {
                calculateCollapsedOffset();
            }
            setStateInternal((!this.fitToContents || this.state != 6) ? this.state : 3);
        }
    }

    public final void setPeekHeight(int i) {
        WeakReference<V> weakReference;
        V v;
        boolean z = true;
        if (i == -1) {
            if (!this.peekHeightAuto) {
                this.peekHeightAuto = true;
            }
            z = false;
        } else {
            if (this.peekHeightAuto || this.peekHeight != i) {
                this.peekHeightAuto = false;
                this.peekHeight = Math.max(0, i);
                this.collapsedOffset = this.parentHeight - i;
            }
            z = false;
        }
        if (!z || this.state != 4 || (weakReference = this.viewRef) == null || (v = weakReference.get()) == null) {
            return;
        }
        v.requestLayout();
    }

    public void setHideable(boolean z) {
        this.hideable = z;
    }

    public void setSkipCollapsed(boolean z) {
        this.skipCollapsed = z;
    }

    public void setBottomSheetCallback(BottomSheetCallback bottomSheetCallback) {
        this.callback = bottomSheetCallback;
    }

    public final void setState(final int i) {
        if (i != this.state) {
            WeakReference<V> weakReference = this.viewRef;
            if (weakReference == null) {
                if (i != 4 && i != 3 && i != 6 && (!this.hideable || i != 5)) {
                    return;
                }
                this.state = i;
                return;
            }
            final V v = weakReference.get();
            if (v == null) {
                return;
            }
            ViewParent parent = v.getParent();
            if (parent != null && parent.isLayoutRequested() && ViewCompat.isAttachedToWindow(v)) {
                v.post(new Runnable() { // from class: com.one.tomato.widget.ViewPagerBottomSheetBehavior.1
                    @Override // java.lang.Runnable
                    public void run() {
                        ViewPagerBottomSheetBehavior.this.startSettlingAnimation(v, i);
                    }
                });
            } else {
                startSettlingAnimation(v, i);
            }
        }
    }

    void setStateInternal(int i) {
        BottomSheetCallback bottomSheetCallback;
        if (this.state != i) {
            this.state = i;
            if (i == 6 || i == 3) {
                updateImportantForAccessibility(true);
            } else if (i == 5 || i == 4) {
                updateImportantForAccessibility(false);
            }
            V v = this.viewRef.get();
            if (v == null || (bottomSheetCallback = this.callback) == null) {
                return;
            }
            bottomSheetCallback.onStateChanged(v, i);
        }
    }

    private void calculateCollapsedOffset() {
        if (this.fitToContents) {
            this.collapsedOffset = Math.max(this.parentHeight - this.lastPeekHeight, this.fitToContentsOffset);
        } else {
            this.collapsedOffset = this.parentHeight - this.lastPeekHeight;
        }
    }

    private void reset() {
        this.activePointerId = -1;
        VelocityTracker velocityTracker = this.velocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.velocityTracker = null;
        }
    }

    boolean shouldHide(View view, float f) {
        if (this.skipCollapsed) {
            return true;
        }
        return view.getTop() >= this.collapsedOffset && Math.abs((((float) view.getTop()) + (f * 0.1f)) - ((float) this.collapsedOffset)) / ((float) this.peekHeight) > 0.5f;
    }

    @VisibleForTesting
    View findScrollingChild(View view) {
        View findScrollingChild;
        if (ViewCompat.isNestedScrollingEnabled(view)) {
            return view;
        }
        if (view instanceof ViewPager) {
            ViewPager viewPager = (ViewPager) view;
            View childAt = viewPager.getChildAt(viewPager.getCurrentItem());
            if (childAt != null && (findScrollingChild = findScrollingChild(childAt)) != null) {
                return findScrollingChild;
            }
        } else if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View findScrollingChild2 = findScrollingChild(viewGroup.getChildAt(i));
                if (findScrollingChild2 != null) {
                    return findScrollingChild2;
                }
            }
        }
        return null;
    }

    public void updateScrollingChild() {
        this.nestedScrollingChildRef = new WeakReference<>(findScrollingChild(this.viewRef.get()));
    }

    private float getYVelocity() {
        VelocityTracker velocityTracker = this.velocityTracker;
        if (velocityTracker == null) {
            return 0.0f;
        }
        velocityTracker.computeCurrentVelocity(1000, this.maximumVelocity);
        return this.velocityTracker.getYVelocity(this.activePointerId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getExpandedOffset() {
        if (this.fitToContents) {
            return this.fitToContentsOffset;
        }
        return 0;
    }

    void startSettlingAnimation(View view, int i) {
        int i2;
        int i3;
        if (i == 4) {
            i2 = this.collapsedOffset;
        } else if (i == 6) {
            int i4 = this.halfExpandedOffset;
            if (!this.fitToContents || i4 > (i3 = this.fitToContentsOffset)) {
                i2 = i4;
            } else {
                i2 = i3;
                i = 3;
            }
        } else if (i == 3) {
            i2 = getExpandedOffset();
        } else if (!this.hideable || i != 5) {
            throw new IllegalArgumentException("Illegal state argument: " + i);
        } else {
            i2 = this.parentHeight;
        }
        if (this.viewDragHelper.smoothSlideViewTo(view, view.getLeft(), i2)) {
            setStateInternal(2);
            ViewCompat.postOnAnimation(view, new SettleRunnable(view, i));
            return;
        }
        setStateInternal(i);
    }

    void dispatchOnSlide(int i) {
        BottomSheetCallback bottomSheetCallback;
        V v = this.viewRef.get();
        if (v == null || (bottomSheetCallback = this.callback) == null) {
            return;
        }
        int i2 = this.collapsedOffset;
        if (i > i2) {
            bottomSheetCallback.onSlide(v, (i2 - i) / (this.parentHeight - i2));
        } else {
            bottomSheetCallback.onSlide(v, (i2 - i) / (i2 - getExpandedOffset()));
        }
    }

    public static <V extends View> ViewPagerBottomSheetBehavior<View> from(V v) {
        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        if (!(layoutParams instanceof CoordinatorLayout.LayoutParams)) {
            throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
        }
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) layoutParams).getBehavior();
        if (!(behavior instanceof ViewPagerBottomSheetBehavior)) {
            throw new IllegalArgumentException("The view is not associated with BottomSheetBehavior");
        }
        return (ViewPagerBottomSheetBehavior) behavior;
    }

    private void updateImportantForAccessibility(boolean z) {
        WeakReference<V> weakReference = this.viewRef;
        if (weakReference != null) {
            ViewParent parent = weakReference.get().getParent();
            if (!(parent instanceof CoordinatorLayout)) {
                return;
            }
            CoordinatorLayout coordinatorLayout = (CoordinatorLayout) parent;
            int childCount = coordinatorLayout.getChildCount();
            if (Build.VERSION.SDK_INT >= 16 && z) {
                if (this.importantForAccessibilityMap != null) {
                    return;
                }
                this.importantForAccessibilityMap = new HashMap(childCount);
            }
            for (int i = 0; i < childCount; i++) {
                View childAt = coordinatorLayout.getChildAt(i);
                if (childAt != this.viewRef.get()) {
                    if (!z) {
                        Map<View, Integer> map = this.importantForAccessibilityMap;
                        if (map != null && map.containsKey(childAt)) {
                            ViewCompat.setImportantForAccessibility(childAt, this.importantForAccessibilityMap.get(childAt).intValue());
                        }
                    } else {
                        if (Build.VERSION.SDK_INT >= 16) {
                            this.importantForAccessibilityMap.put(childAt, Integer.valueOf(childAt.getImportantForAccessibility()));
                        }
                        ViewCompat.setImportantForAccessibility(childAt, 4);
                    }
                }
            }
            if (z) {
                return;
            }
            this.importantForAccessibilityMap = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes3.dex */
    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() { // from class: com.one.tomato.widget.ViewPagerBottomSheetBehavior.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.ClassLoaderCreator
            /* renamed from: createFromParcel */
            public SavedState mo6450createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            @Override // android.os.Parcelable.Creator
            /* renamed from: createFromParcel */
            public SavedState mo6449createFromParcel(Parcel parcel) {
                return new SavedState(parcel, (ClassLoader) null);
            }

            @Override // android.os.Parcelable.Creator
            /* renamed from: newArray */
            public SavedState[] mo6451newArray(int i) {
                return new SavedState[i];
            }
        };
        final int state;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.state = parcel.readInt();
        }

        public SavedState(Parcelable parcelable, int i) {
            super(parcelable);
            this.state = i;
        }

        @Override // android.support.p002v4.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.state);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class SettleRunnable implements Runnable {
        private final int targetState;
        private final View view;

        SettleRunnable(View view, int i) {
            this.view = view;
            this.targetState = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            ViewDragHelper viewDragHelper = ViewPagerBottomSheetBehavior.this.viewDragHelper;
            if (viewDragHelper != null && viewDragHelper.continueSettling(true)) {
                ViewCompat.postOnAnimation(this.view, this);
            } else {
                ViewPagerBottomSheetBehavior.this.setStateInternal(this.targetState);
            }
        }
    }

    /* loaded from: classes3.dex */
    class NamelessClass_1 extends ViewDragHelper.Callback {
        NamelessClass_1() {
        }

        @Override // android.support.p002v4.widget.ViewDragHelper.Callback
        public boolean tryCaptureView(@NonNull View view, int i) {
            WeakReference<V> weakReference;
            View view2;
            ViewPagerBottomSheetBehavior viewPagerBottomSheetBehavior = ViewPagerBottomSheetBehavior.this;
            int i2 = viewPagerBottomSheetBehavior.state;
            if (i2 != 1 && !viewPagerBottomSheetBehavior.touchingScrollingChild) {
                return (i2 != 3 || viewPagerBottomSheetBehavior.activePointerId != i || (view2 = viewPagerBottomSheetBehavior.nestedScrollingChildRef.get()) == null || !view2.canScrollVertically(-1)) && (weakReference = ViewPagerBottomSheetBehavior.this.viewRef) != null && weakReference.get() == view;
            }
            return false;
        }

        @Override // android.support.p002v4.widget.ViewDragHelper.Callback
        public void onViewPositionChanged(@NonNull View view, int i, int i2, int i3, int i4) {
            ViewPagerBottomSheetBehavior.this.dispatchOnSlide(i2);
        }

        @Override // android.support.p002v4.widget.ViewDragHelper.Callback
        public void onViewDragStateChanged(int i) {
            if (i == 1) {
                ViewPagerBottomSheetBehavior.this.setStateInternal(1);
            }
        }

        @Override // android.support.p002v4.widget.ViewDragHelper.Callback
        public void onViewReleased(@NonNull View view, float f, float f2) {
            int i = 0;
            int i2 = 4;
            if (f2 < 0.0f) {
                if (ViewPagerBottomSheetBehavior.this.fitToContents) {
                    i = ViewPagerBottomSheetBehavior.this.fitToContentsOffset;
                } else {
                    int top2 = view.getTop();
                    int i3 = ViewPagerBottomSheetBehavior.this.halfExpandedOffset;
                    if (top2 > i3) {
                        i = i3;
                        i2 = 6;
                    }
                }
                i2 = 3;
            } else {
                ViewPagerBottomSheetBehavior viewPagerBottomSheetBehavior = ViewPagerBottomSheetBehavior.this;
                if (!viewPagerBottomSheetBehavior.hideable || !viewPagerBottomSheetBehavior.shouldHide(view, f2) || (view.getTop() <= ViewPagerBottomSheetBehavior.this.collapsedOffset && Math.abs(f) >= Math.abs(f2))) {
                    if (f2 != 0.0f && Math.abs(f) <= Math.abs(f2)) {
                        i = ViewPagerBottomSheetBehavior.this.collapsedOffset;
                    } else {
                        int top3 = view.getTop();
                        if (ViewPagerBottomSheetBehavior.this.fitToContents) {
                            if (Math.abs(top3 - ViewPagerBottomSheetBehavior.this.fitToContentsOffset) < Math.abs(top3 - ViewPagerBottomSheetBehavior.this.collapsedOffset)) {
                                i = ViewPagerBottomSheetBehavior.this.fitToContentsOffset;
                                i2 = 3;
                            } else {
                                i = ViewPagerBottomSheetBehavior.this.collapsedOffset;
                            }
                        } else {
                            ViewPagerBottomSheetBehavior viewPagerBottomSheetBehavior2 = ViewPagerBottomSheetBehavior.this;
                            int i4 = viewPagerBottomSheetBehavior2.halfExpandedOffset;
                            if (top3 < i4) {
                                if (top3 >= Math.abs(top3 - viewPagerBottomSheetBehavior2.collapsedOffset)) {
                                    i = ViewPagerBottomSheetBehavior.this.halfExpandedOffset;
                                }
                                i2 = 3;
                            } else if (Math.abs(top3 - i4) < Math.abs(top3 - ViewPagerBottomSheetBehavior.this.collapsedOffset)) {
                                i = ViewPagerBottomSheetBehavior.this.halfExpandedOffset;
                            } else {
                                i = ViewPagerBottomSheetBehavior.this.collapsedOffset;
                            }
                            i2 = 6;
                        }
                    }
                } else {
                    i = ViewPagerBottomSheetBehavior.this.parentHeight;
                    i2 = 5;
                }
            }
            if (ViewPagerBottomSheetBehavior.this.viewDragHelper.settleCapturedViewAt(view.getLeft(), i)) {
                ViewPagerBottomSheetBehavior.this.setStateInternal(2);
                ViewPagerBottomSheetBehavior viewPagerBottomSheetBehavior3 = ViewPagerBottomSheetBehavior.this;
                viewPagerBottomSheetBehavior3.getClass();
                ViewCompat.postOnAnimation(view, new SettleRunnable(view, i2));
                return;
            }
            ViewPagerBottomSheetBehavior.this.setStateInternal(i2);
        }

        @Override // android.support.p002v4.widget.ViewDragHelper.Callback
        public int clampViewPositionVertical(@NonNull View view, int i, int i2) {
            int expandedOffset = ViewPagerBottomSheetBehavior.this.getExpandedOffset();
            ViewPagerBottomSheetBehavior viewPagerBottomSheetBehavior = ViewPagerBottomSheetBehavior.this;
            return MathUtils.clamp(i, expandedOffset, viewPagerBottomSheetBehavior.hideable ? viewPagerBottomSheetBehavior.parentHeight : viewPagerBottomSheetBehavior.collapsedOffset);
        }

        @Override // android.support.p002v4.widget.ViewDragHelper.Callback
        public int clampViewPositionHorizontal(@NonNull View view, int i, int i2) {
            return view.getLeft();
        }

        @Override // android.support.p002v4.widget.ViewDragHelper.Callback
        public int getViewVerticalDragRange(@NonNull View view) {
            ViewPagerBottomSheetBehavior viewPagerBottomSheetBehavior = ViewPagerBottomSheetBehavior.this;
            return viewPagerBottomSheetBehavior.hideable ? viewPagerBottomSheetBehavior.parentHeight : viewPagerBottomSheetBehavior.collapsedOffset;
        }
    }
}
