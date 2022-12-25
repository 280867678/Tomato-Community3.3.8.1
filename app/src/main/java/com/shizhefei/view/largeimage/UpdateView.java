package com.shizhefei.view.largeimage;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

/* loaded from: classes3.dex */
public abstract class UpdateView extends View {
    int[] location;
    private boolean lock;
    private final ViewTreeObserver.OnPreDrawListener mDrawListener;
    private boolean mGlobalListenersAdded;
    final WindowManager.LayoutParams mLayout;
    int[] mLocation;
    boolean mRequestedVisible;
    final ViewTreeObserver.OnScrollChangedListener mScrollChangedListener;
    boolean mViewVisibility;
    private Rect mVisibilityRect;
    boolean mVisible;
    boolean mWindowVisibility;
    private Rect mWindowVisibleDisplayFrame;
    int[] tempLocationInWindow;
    Rect tempVisibilityRect;
    long time;

    protected abstract void onUpdateWindow(Rect rect);

    public void setIndex(int i) {
    }

    @TargetApi(11)
    public UpdateView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mRequestedVisible = false;
        this.mWindowVisibility = false;
        this.mViewVisibility = false;
        this.mLayout = new WindowManager.LayoutParams();
        this.mScrollChangedListener = new ViewTreeObserver.OnScrollChangedListener() { // from class: com.shizhefei.view.largeimage.UpdateView.1
            @Override // android.view.ViewTreeObserver.OnScrollChangedListener
            public void onScrollChanged() {
                UpdateView.this.updateWindow(false, false);
            }
        };
        this.mDrawListener = new ViewTreeObserver.OnPreDrawListener(this) { // from class: com.shizhefei.view.largeimage.UpdateView.2
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                return true;
            }
        };
        this.mLocation = new int[2];
        this.mVisible = false;
        this.tempLocationInWindow = new int[2];
        this.tempVisibilityRect = new Rect();
        this.mVisibilityRect = new Rect();
        this.location = new int[2];
        this.mWindowVisibleDisplayFrame = new Rect();
    }

    public UpdateView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRequestedVisible = false;
        this.mWindowVisibility = false;
        this.mViewVisibility = false;
        this.mLayout = new WindowManager.LayoutParams();
        this.mScrollChangedListener = new ViewTreeObserver.OnScrollChangedListener() { // from class: com.shizhefei.view.largeimage.UpdateView.1
            @Override // android.view.ViewTreeObserver.OnScrollChangedListener
            public void onScrollChanged() {
                UpdateView.this.updateWindow(false, false);
            }
        };
        this.mDrawListener = new ViewTreeObserver.OnPreDrawListener(this) { // from class: com.shizhefei.view.largeimage.UpdateView.2
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                return true;
            }
        };
        this.mLocation = new int[2];
        this.mVisible = false;
        this.tempLocationInWindow = new int[2];
        this.tempVisibilityRect = new Rect();
        this.mVisibilityRect = new Rect();
        this.location = new int[2];
        this.mWindowVisibleDisplayFrame = new Rect();
    }

    public UpdateView(Context context) {
        super(context);
        this.mRequestedVisible = false;
        this.mWindowVisibility = false;
        this.mViewVisibility = false;
        this.mLayout = new WindowManager.LayoutParams();
        this.mScrollChangedListener = new ViewTreeObserver.OnScrollChangedListener() { // from class: com.shizhefei.view.largeimage.UpdateView.1
            @Override // android.view.ViewTreeObserver.OnScrollChangedListener
            public void onScrollChanged() {
                UpdateView.this.updateWindow(false, false);
            }
        };
        this.mDrawListener = new ViewTreeObserver.OnPreDrawListener(this) { // from class: com.shizhefei.view.largeimage.UpdateView.2
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                return true;
            }
        };
        this.mLocation = new int[2];
        this.mVisible = false;
        this.tempLocationInWindow = new int[2];
        this.tempVisibilityRect = new Rect();
        this.mVisibilityRect = new Rect();
        this.location = new int[2];
        this.mWindowVisibleDisplayFrame = new Rect();
    }

    @Override // android.view.View
    protected void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        boolean z = true;
        this.mWindowVisibility = i == 0;
        if (!this.mWindowVisibility || !this.mViewVisibility) {
            z = false;
        }
        this.mRequestedVisible = z;
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        super.setVisibility(i);
        boolean z = true;
        this.mViewVisibility = i == 0;
        if (!this.mWindowVisibility || !this.mViewVisibility) {
            z = false;
        }
        if (z != this.mRequestedVisible) {
            requestLayout();
        }
        this.mRequestedVisible = z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mLayout.token = getWindowToken();
        this.mLayout.setTitle("SurfaceView");
        this.mViewVisibility = getVisibility() == 0;
        if (!this.mGlobalListenersAdded) {
            ViewTreeObserver viewTreeObserver = getViewTreeObserver();
            viewTreeObserver.addOnScrollChangedListener(this.mScrollChangedListener);
            viewTreeObserver.addOnPreDrawListener(this.mDrawListener);
            this.mGlobalListenersAdded = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.View
    public void onDetachedFromWindow() {
        if (this.mGlobalListenersAdded) {
            ViewTreeObserver viewTreeObserver = getViewTreeObserver();
            viewTreeObserver.removeOnScrollChangedListener(this.mScrollChangedListener);
            viewTreeObserver.removeOnPreDrawListener(this.mDrawListener);
            this.mGlobalListenersAdded = false;
        }
        this.mRequestedVisible = false;
        updateWindow(false, false);
        this.mLayout.token = null;
        super.onDetachedFromWindow();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateWindow(boolean z, boolean z2) {
        if (this.lock) {
            return;
        }
        long uptimeMillis = SystemClock.uptimeMillis();
        if (uptimeMillis - this.time < 16) {
            return;
        }
        this.time = uptimeMillis;
        getLocationInWindow(this.tempLocationInWindow);
        boolean z3 = this.mVisible != this.mRequestedVisible;
        if (!z && !z3) {
            int[] iArr = this.tempLocationInWindow;
            int i = iArr[0];
            int[] iArr2 = this.mLocation;
            if (i == iArr2[0] && iArr[1] == iArr2[1] && !z2) {
                return;
            }
        }
        int[] iArr3 = this.mLocation;
        int[] iArr4 = this.tempLocationInWindow;
        iArr3[0] = iArr4[0];
        iArr3[1] = iArr4[1];
        getVisibilityRect(this.tempVisibilityRect);
        if (this.mVisibilityRect.equals(this.tempVisibilityRect)) {
            return;
        }
        if (this.mVisibilityRect.isEmpty() && this.tempVisibilityRect.isEmpty()) {
            return;
        }
        this.mVisibilityRect.set(this.tempVisibilityRect);
        onUpdateWindow(this.mVisibilityRect);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void getVisibilityRect(Rect rect) {
        getGlobalVisibleRect(rect);
        getWindowVisibleDisplayFrame(this.mWindowVisibleDisplayFrame);
        int i = rect.left;
        int i2 = this.mWindowVisibleDisplayFrame.left;
        if (i < i2) {
            rect.left = i2;
        }
        int i3 = rect.right;
        int i4 = this.mWindowVisibleDisplayFrame.right;
        if (i3 > i4) {
            rect.right = i4;
        }
        int i5 = rect.top;
        int i6 = this.mWindowVisibleDisplayFrame.top;
        if (i5 < i6) {
            rect.top = i6;
        }
        int i7 = rect.bottom;
        int i8 = this.mWindowVisibleDisplayFrame.bottom;
        if (i7 > i8) {
            rect.bottom = i8;
        }
        getLocationInWindow(this.location);
        int i9 = rect.left;
        int[] iArr = this.location;
        rect.left = i9 - iArr[0];
        rect.right -= iArr[0];
        rect.top -= iArr[1];
        rect.bottom -= iArr[1];
    }
}
