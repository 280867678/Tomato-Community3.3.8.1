package razerdp.basepopup;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import razerdp.basepopup.BasePopupWindow;
import razerdp.util.PopupUiUtils;
import razerdp.util.PopupUtils;
import razerdp.util.log.PopupLog;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public final class PopupDecorViewProxy extends ViewGroup implements PopupKeyboardStateChangeListener {
    private int childBottomMargin;
    private int childLeftMargin;
    private int childRightMargin;
    private int childTopMargin;
    private boolean lastVisibleState;
    private CheckAndCallAutoAnchorLocate mCheckAndCallAutoAnchorLocate;
    private Flag mFlag;
    private BasePopupHelper mHelper;
    private PopupMaskLayout mMaskLayout;
    private View mTarget;
    private Rect mTouchRect;
    private WindowManagerProxy mWindowManagerProxy;
    private int offset;
    private int originY;
    private ValueAnimator valueAnimator;
    private Rect viewRect;

    @Override // android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    private PopupDecorViewProxy(Context context) {
        this(context, null);
    }

    private PopupDecorViewProxy(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    private PopupDecorViewProxy(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mTouchRect = new Rect();
        this.mFlag = new Flag();
        this.viewRect = new Rect();
    }

    public static PopupDecorViewProxy create(Context context, WindowManagerProxy windowManagerProxy, BasePopupHelper basePopupHelper) {
        PopupDecorViewProxy popupDecorViewProxy = new PopupDecorViewProxy(context);
        popupDecorViewProxy.init(basePopupHelper, windowManagerProxy);
        return popupDecorViewProxy;
    }

    @SuppressLint({"ClickableViewAccessibility"})
    private void init(BasePopupHelper basePopupHelper, WindowManagerProxy windowManagerProxy) {
        this.mWindowManagerProxy = windowManagerProxy;
        this.mHelper = basePopupHelper;
        this.mHelper.registerKeyboardStateChangeListener(this);
        setClipChildren(this.mHelper.isClipChildren());
        this.mMaskLayout = PopupMaskLayout.create(getContext(), this.mHelper);
        this.mFlag.flag = 0;
        if (!this.mHelper.isOutSideTouchable()) {
            setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            addViewInLayout(this.mMaskLayout, -1, new ViewGroup.LayoutParams(-1, -1));
            this.mMaskLayout.setOnTouchListener(new View.OnTouchListener() { // from class: razerdp.basepopup.PopupDecorViewProxy.1
                @Override // android.view.View.OnTouchListener
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int action = motionEvent.getAction();
                    if (action != 0) {
                        if (action != 1 || !PopupDecorViewProxy.this.mHelper.isOutSideDismiss()) {
                            return false;
                        }
                        int x = (int) motionEvent.getX();
                        int y = (int) motionEvent.getY();
                        if (PopupDecorViewProxy.this.mTarget != null) {
                            View findViewById = PopupDecorViewProxy.this.mTarget.findViewById(PopupDecorViewProxy.this.mHelper.getContentRootId());
                            if (findViewById == null) {
                                PopupDecorViewProxy.this.mTarget.getGlobalVisibleRect(PopupDecorViewProxy.this.mTouchRect);
                            } else {
                                findViewById.getGlobalVisibleRect(PopupDecorViewProxy.this.mTouchRect);
                            }
                        }
                        if (PopupDecorViewProxy.this.mTouchRect.contains(x, y)) {
                            return false;
                        }
                        PopupDecorViewProxy.this.mHelper.onOutSideTouch();
                        return false;
                    }
                    return PopupDecorViewProxy.this.mHelper.isOutSideDismiss();
                }
            });
            return;
        }
        setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        Activity scanForActivity = PopupUtils.scanForActivity(getContext(), 15);
        if (scanForActivity == null) {
            return;
        }
        checkAndClearDecorMaskLayout(scanForActivity);
        addMaskToDecor(scanForActivity.getWindow());
    }

    private void checkAndClearDecorMaskLayout(Activity activity) {
        if (activity == null || activity.getWindow() == null) {
            return;
        }
        View decorView = activity.getWindow().getDecorView();
        if (!(decorView instanceof ViewGroup)) {
            return;
        }
        ViewGroup viewGroup = (ViewGroup) decorView;
        for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = viewGroup.getChildAt(childCount);
            if (childAt instanceof PopupMaskLayout) {
                viewGroup.removeViewInLayout(childAt);
            }
        }
    }

    private void addMaskToDecor(Window window) {
        View decorView = window == null ? null : window.getDecorView();
        if (!(decorView instanceof ViewGroup)) {
            PopupMaskLayout popupMaskLayout = this.mMaskLayout;
            if (popupMaskLayout == null) {
                return;
            }
            popupMaskLayout.onDetachedFromWindow();
            this.mMaskLayout = null;
            return;
        }
        ((ViewGroup) decorView).addView(this.mMaskLayout, -1, -1);
    }

    public void addPopupDecorView(View view, WindowManager.LayoutParams layoutParams) {
        View childAt;
        if (view == null) {
            throw new NullPointerException("contentView不能为空");
        }
        if (getChildCount() == 2) {
            removeViewsInLayout(1, 1);
        }
        this.mTarget = view;
        WindowManager.LayoutParams layoutParams2 = new WindowManager.LayoutParams();
        layoutParams2.copyFrom(layoutParams);
        layoutParams2.x = 0;
        layoutParams2.y = 0;
        if (this.mHelper.getParaseFromXmlParams() == null) {
            View findContentView = findContentView(view);
            if (findContentView != null) {
                if (!this.mHelper.isCustomMeasure()) {
                    if (layoutParams2.width <= 0) {
                        layoutParams2.width = findContentView.getMeasuredWidth() <= 0 ? this.mHelper.getPopupViewWidth() : findContentView.getMeasuredWidth();
                    }
                    if (layoutParams2.height <= 0) {
                        layoutParams2.height = findContentView.getMeasuredHeight() <= 0 ? this.mHelper.getPopupViewHeight() : findContentView.getMeasuredHeight();
                    }
                } else {
                    layoutParams2.width = this.mHelper.getPopupViewWidth();
                    layoutParams2.height = this.mHelper.getPopupViewHeight();
                }
            }
        } else {
            if ((view instanceof ViewGroup) && (childAt = ((ViewGroup) view).getChildAt(0)) != null) {
                childAt.setLayoutParams(new FrameLayout.LayoutParams(this.mHelper.getParaseFromXmlParams()));
            }
            layoutParams2.width = this.mHelper.getPopupViewWidth();
            layoutParams2.height = this.mHelper.getPopupViewHeight();
            this.childLeftMargin = this.mHelper.getParaseFromXmlParams().leftMargin;
            this.childTopMargin = this.mHelper.getParaseFromXmlParams().topMargin;
            this.childRightMargin = this.mHelper.getParaseFromXmlParams().rightMargin;
            this.childBottomMargin = this.mHelper.getParaseFromXmlParams().bottomMargin;
        }
        addView(view, layoutParams2);
    }

    private View findContentView(View view) {
        View view2 = null;
        if (view == null) {
            return null;
        }
        if (!(view instanceof ViewGroup)) {
            return view;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        if (viewGroup.getChildCount() <= 0) {
            return view;
        }
        String simpleName = viewGroup.getClass().getSimpleName();
        while (!isContentView(simpleName)) {
            view2 = viewGroup.getChildAt(0);
            simpleName = view2.getClass().getSimpleName();
            if (!(view2 instanceof ViewGroup)) {
                break;
            }
            viewGroup = (ViewGroup) view2;
        }
        return view2;
    }

    private boolean isContentView(String str) {
        return !TextUtils.equals(str, "PopupDecorView") && !TextUtils.equals(str, "PopupViewContainer") && !TextUtils.equals(str, "PopupBackgroundView");
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        Flag flag = this.mFlag;
        flag.flag &= -2;
        flag.flag &= -17;
        PopupLog.m25i("onMeasure", Integer.valueOf(View.MeasureSpec.getSize(i)), Integer.valueOf(View.MeasureSpec.getSize(i2)));
        if (!this.mHelper.isOutSideTouchable()) {
            measureWithIntercept(i, i2);
        } else {
            measureWithOutIntercept(i, i2);
        }
    }

    private void measureWithIntercept(int i, int i2) {
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        if (getResources().getConfiguration().orientation == 2) {
            if (size < size2) {
                i = View.MeasureSpec.makeMeasureSpec(size2, mode2);
                i2 = View.MeasureSpec.makeMeasureSpec(size, mode);
            }
        } else if (size > size2) {
            i = View.MeasureSpec.makeMeasureSpec(size2, mode2);
            i2 = View.MeasureSpec.makeMeasureSpec(size, mode);
        }
        PopupLog.m25i("measureWithIntercept", Integer.valueOf(View.MeasureSpec.getSize(i)), Integer.valueOf(View.MeasureSpec.getSize(i2)));
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            if (childAt == this.mMaskLayout) {
                measureChild(childAt, View.MeasureSpec.makeMeasureSpec(getScreenWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(getScreenHeight(), 1073741824));
            } else {
                measureWrappedDecorView(childAt, i, i2);
            }
        }
        setMeasuredDimension(getScreenWidth(), getScreenHeight());
    }

    private void measureWithOutIntercept(int i, int i2) {
        int childCount = getChildCount();
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = getChildAt(i6);
            View view = this.mTarget;
            if (childAt == view) {
                measureWrappedDecorView(view, i, i2);
            } else {
                measureChild(childAt, i, i2);
            }
            i3 = Math.max(i3, childAt.getMeasuredWidth());
            i5 = Math.max(i5, childAt.getMeasuredHeight());
            i4 = ViewGroup.combineMeasuredStates(i4, childAt.getMeasuredState());
        }
        setMeasuredDimension(ViewGroup.resolveSizeAndState(i3, i, i4), ViewGroup.resolveSizeAndState(i5, i2, i4 << 16));
    }

    private void measureWrappedDecorView(View view, int i, int i2) {
        int anchorY;
        int screenHeight;
        int anchorY2;
        int range;
        int range2;
        if (view == null || view.getVisibility() == 8) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        boolean z = false;
        int childMeasureSpec = ViewGroup.getChildMeasureSpec(i, 0, layoutParams.width);
        int childMeasureSpec2 = ViewGroup.getChildMeasureSpec(i2, 0, layoutParams.height);
        int size = View.MeasureSpec.getSize(childMeasureSpec);
        int size2 = View.MeasureSpec.getSize(childMeasureSpec2);
        int mode = View.MeasureSpec.getMode(childMeasureSpec);
        int mode2 = View.MeasureSpec.getMode(childMeasureSpec2);
        int popupGravity = this.mHelper.getPopupGravity();
        boolean z2 = this.mHelper.getGravityMode() == BasePopupWindow.GravityMode.ALIGN_TO_ANCHOR_SIDE;
        if (this.mHelper.isClipToScreen() && this.mHelper.isShowAsDropDown()) {
            z = true;
        }
        if (z && size > (range2 = PopupUtils.range(size, this.mHelper.getMinWidth(), size)) && !this.mHelper.isKeepSize()) {
            Flag flag = this.mFlag;
            flag.flag = 1 | flag.flag;
            size = range2;
        }
        if (this.mHelper.getMinWidth() > 0 && size2 < this.mHelper.getMinWidth()) {
            size = this.mHelper.getMinWidth();
        }
        if (this.mHelper.getMaxWidth() > 0 && size > this.mHelper.getMaxWidth()) {
            size = this.mHelper.getMaxWidth();
        }
        if (z) {
            if ((popupGravity & 112) == 48) {
                if (z2) {
                    anchorY = getScreenHeight() - this.mHelper.getAnchorY();
                } else {
                    anchorY = this.mHelper.getAnchorY();
                }
                if (this.mHelper.isAutoLocatePopup() && ((this.mHelper.getMinHeight() > 0 && anchorY < this.mHelper.getMinHeight()) || anchorY <= (size2 >> 2))) {
                    if (z2) {
                        screenHeight = getScreenHeight();
                        anchorY2 = this.mHelper.getAnchorY();
                    } else {
                        screenHeight = getScreenHeight();
                        anchorY2 = this.mHelper.getAnchorY() + this.mHelper.getAnchorHeight();
                    }
                    anchorY = screenHeight - anchorY2;
                }
            } else {
                if (z2) {
                    anchorY = this.mHelper.getAnchorY() + this.mHelper.getAnchorHeight();
                } else {
                    anchorY = getScreenHeight() - (this.mHelper.getAnchorY() + this.mHelper.getAnchorHeight());
                }
                if (this.mHelper.isAutoLocatePopup() && ((this.mHelper.getMinHeight() > 0 && anchorY < this.mHelper.getMinHeight()) || anchorY <= (size2 >> 2))) {
                    if (z2) {
                        anchorY = this.mHelper.getAnchorY() + this.mHelper.getAnchorHeight();
                    } else {
                        anchorY = this.mHelper.getAnchorY();
                    }
                }
            }
            int i3 = (anchorY - this.childTopMargin) - this.childBottomMargin;
            if (i3 <= 0) {
                Log.e("PopupDecorViewProxy", "BasePopup 可用展示空间小于或等于0，高度将按原测量值设定，不进行调整适配");
                this.mFlag.flag |= 16;
                range = size2;
            } else {
                range = PopupUtils.range(i3, this.mHelper.getMinHeight(), i3);
            }
            if (size2 > range && !this.mHelper.isKeepSize()) {
                this.mFlag.flag |= 16;
                size2 = range;
            }
        }
        if (this.mHelper.getMinHeight() > 0 && size2 < this.mHelper.getMinHeight()) {
            size2 = this.mHelper.getMinHeight();
        }
        if (this.mHelper.getMaxHeight() > 0 && size2 > this.mHelper.getMaxHeight()) {
            size2 = this.mHelper.getMaxHeight();
        }
        view.measure(View.MeasureSpec.makeMeasureSpec(size, mode), View.MeasureSpec.makeMeasureSpec(size2, mode2));
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        PopupLog.m25i("onLayout", Boolean.valueOf(z), Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4));
        if (!this.mHelper.isOutSideTouchable()) {
            layoutWithIntercept(i, i2, i3, i4);
        } else {
            layoutWithOutIntercept(i, i2, i3, i4);
        }
    }

    private void layoutWithOutIntercept(int i, int i2, int i3, int i4) {
        if ((this.mFlag.flag & 256) != 0 && (getLayoutParams() instanceof WindowManager.LayoutParams)) {
            this.mWindowManagerProxy.updateViewLayout(this, getLayoutParams());
        }
        int childCount = getChildCount();
        if (childCount > 0) {
            for (int i5 = 0; i5 < childCount; i5++) {
                View childAt = getChildAt(i5);
                childAt.layout(i, i2, i3, i4);
                if (childAt == this.mTarget && this.mMaskLayout != null && this.mHelper.isAlignBackground() && this.mHelper.getAlignBackgroundGravity() != 0) {
                    if (getLayoutParams() instanceof WindowManager.LayoutParams) {
                        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) getLayoutParams();
                        int i6 = layoutParams.x;
                        i += i6;
                        int i7 = layoutParams.y;
                        i2 += i7;
                        i3 += i6;
                        i4 += i7;
                    }
                    this.mMaskLayout.handleAlignBackground(this.mHelper.getAlignBackgroundGravity(), i, i2, i3, i4);
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:138:0x00b6, code lost:
        if (r4 != false) goto L139;
     */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x00c7, code lost:
        if (r4 != false) goto L27;
     */
    /* JADX WARN: Removed duplicated region for block: B:37:0x01a3  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x025f  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0271 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0289 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x02be  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x02d3 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0261  */
    @SuppressLint({"RtlHardcoded"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void layoutWithIntercept(int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        int i8;
        int measuredHeight;
        int i9;
        boolean z;
        int i10;
        int i11;
        int i12;
        int anchorY;
        int measuredHeight2;
        int i13 = i;
        int i14 = i2;
        int childCount = getChildCount();
        int i15 = 0;
        while (i15 < childCount) {
            View childAt = getChildAt(i15);
            if (childAt.getVisibility() != 8) {
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight3 = childAt.getMeasuredHeight();
                int popupGravity = this.mHelper.getPopupGravity();
                int offsetX = this.mHelper.getOffsetX();
                int offsetY = this.mHelper.getOffsetY();
                boolean z2 = this.mHelper.isAlignBackground() && this.mHelper.getAlignBackgroundGravity() != 0;
                getMeasuredWidth();
                int measuredHeight4 = getMeasuredHeight();
                if (childAt == this.mMaskLayout) {
                    childAt.layout(i13, i14, measuredWidth + i13, measuredHeight3 + i14);
                } else {
                    boolean isShowAsDropDown = this.mHelper.isShowAsDropDown();
                    boolean z3 = this.mHelper.getGravityMode() == BasePopupWindow.GravityMode.ALIGN_TO_ANCHOR_SIDE;
                    i5 = childCount;
                    int anchorX = this.mHelper.getAnchorX() + (this.mHelper.getAnchorWidth() >> 1);
                    i6 = i15;
                    int anchorY2 = this.mHelper.getAnchorY() + (this.mHelper.getAnchorHeight() >> 1);
                    int i16 = popupGravity & 7;
                    if (i16 != 1) {
                        if (i16 == 3) {
                            if (isShowAsDropDown) {
                                i7 = this.mHelper.getAnchorX();
                            }
                            i7 = i13;
                        } else if (i16 != 5) {
                            if (isShowAsDropDown) {
                                i7 = this.mHelper.getAnchorX();
                            }
                            i7 = i13;
                        } else {
                            if (isShowAsDropDown) {
                                i7 = this.mHelper.getAnchorX() + this.mHelper.getAnchorWidth();
                            } else {
                                i7 = getMeasuredWidth();
                            }
                            i7 -= measuredWidth;
                        }
                        i15 = i6 + 1;
                        i13 = i;
                        i14 = i2;
                        childCount = i5;
                    } else if (isShowAsDropDown) {
                        i7 = this.mHelper.getAnchorX();
                        offsetX += anchorX - ((measuredWidth >> 1) + i7);
                    } else {
                        i7 = ((i3 - i13) - measuredWidth) >> 1;
                    }
                    int measuredWidth2 = getMeasuredWidth();
                    int i17 = (i7 + this.childLeftMargin) - this.childRightMargin;
                    int i18 = popupGravity & 112;
                    if (i18 != 16) {
                        if (i18 == 48) {
                            if (isShowAsDropDown) {
                                i8 = this.mHelper.getAnchorY();
                                if (!z3) {
                                    i8 -= measuredHeight3;
                                }
                                i9 = z3 ? this.mHelper.getAnchorY() : 0;
                                measuredHeight = z3 ? getMeasuredHeight() : this.mHelper.getAnchorY();
                                int i19 = (i8 + this.childTopMargin) - this.childBottomMargin;
                                if (!this.mHelper.isAutoLocatePopup()) {
                                }
                                z = true;
                                measuredWidth2 = getMeasuredWidth();
                                measuredHeight = getMeasuredHeight();
                                i10 = 0;
                                i11 = i17 + offsetX;
                                int i20 = i19 + offsetY + (this.mHelper.isFullScreen() ? 0 : -PopupUiUtils.getStatusBarHeight(getContext()));
                                int i21 = i11 + measuredWidth;
                                int i22 = i20 + measuredHeight3;
                                if (i11 >= 0) {
                                }
                                if (!this.mHelper.isClipToScreen()) {
                                }
                                childAt.layout(i11, i20, i21, i22);
                                if (z2) {
                                }
                            }
                            i8 = i14;
                            measuredHeight = measuredHeight4;
                        } else if (i18 != 80) {
                            if (isShowAsDropDown) {
                                measuredHeight = getMeasuredHeight();
                                i8 = this.mHelper.getAnchorY() + this.mHelper.getAnchorHeight();
                            }
                            i8 = i14;
                            measuredHeight = measuredHeight4;
                        } else if (isShowAsDropDown) {
                            int anchorY3 = this.mHelper.getAnchorY() + this.mHelper.getAnchorHeight();
                            if (z3) {
                                anchorY3 -= measuredHeight3;
                            }
                            i8 = anchorY3;
                            i9 = z3 ? 0 : this.mHelper.getAnchorY() + this.mHelper.getAnchorHeight();
                            measuredHeight = z3 ? this.mHelper.getAnchorY() + this.mHelper.getAnchorHeight() : getMeasuredHeight();
                            int i192 = (i8 + this.childTopMargin) - this.childBottomMargin;
                            if (!this.mHelper.isAutoLocatePopup() && this.mHelper.isClipToScreen()) {
                                int i23 = i192 + offsetY + (this.mHelper.isFullScreen() ? 0 : -PopupUiUtils.getStatusBarHeight(getContext()));
                                if (i18 != 48) {
                                    if (z3) {
                                        measuredHeight2 = this.mHelper.getAnchorY() + this.mHelper.getAnchorHeight();
                                        i12 = i9;
                                    } else {
                                        i12 = i9;
                                        measuredHeight2 = getMeasuredHeight() - (this.mHelper.getAnchorY() + this.mHelper.getAnchorHeight());
                                    }
                                    if (measuredHeight3 > measuredHeight2) {
                                        offsetY -= z3 ? 0 : i23 - this.mHelper.getAnchorY();
                                        measuredHeight = this.mHelper.getAnchorY();
                                        z = true;
                                        postAnchorLocation(true);
                                    } else {
                                        z = true;
                                    }
                                } else {
                                    i12 = i9;
                                    z = true;
                                    if (z3) {
                                        anchorY = getMeasuredHeight() - this.mHelper.getAnchorY();
                                    } else {
                                        anchorY = this.mHelper.getAnchorY();
                                    }
                                    if (measuredHeight3 > anchorY) {
                                        offsetY += z3 ? 0 : (this.mHelper.getAnchorY() + this.mHelper.getAnchorHeight()) - i192;
                                        postAnchorLocation(false);
                                        i10 = this.mHelper.getAnchorY() + this.mHelper.getAnchorHeight();
                                        i11 = i17 + offsetX;
                                        int i202 = i192 + offsetY + (this.mHelper.isFullScreen() ? 0 : -PopupUiUtils.getStatusBarHeight(getContext()));
                                        int i212 = i11 + measuredWidth;
                                        int i222 = i202 + measuredHeight3;
                                        if (i11 >= 0 && i202 >= 0 && i212 <= getMeasuredWidth() && i222 <= getMeasuredHeight()) {
                                            z = false;
                                        }
                                        if (!this.mHelper.isClipToScreen() && z) {
                                            if (i11 < 0) {
                                                int i24 = 0 - i11;
                                                if (i24 <= measuredWidth2 - i212) {
                                                    i11 += i24;
                                                    i212 = i11 + measuredWidth;
                                                } else {
                                                    i11 = 0;
                                                }
                                            }
                                            if (i212 > measuredWidth2) {
                                                int i25 = i212 - measuredWidth2;
                                                if (i25 <= i11) {
                                                    i11 -= i25;
                                                    measuredWidth2 = i11 + measuredWidth;
                                                }
                                                i212 = measuredWidth2;
                                            }
                                            if (i202 < i10) {
                                                int i26 = i10 - i202;
                                                if (i26 <= measuredHeight - i222) {
                                                    i202 += i26;
                                                    i222 = i202 + measuredHeight3;
                                                } else {
                                                    i202 = i10;
                                                }
                                            }
                                            if (i222 > measuredHeight) {
                                                int i27 = i222 - measuredHeight;
                                                if (i10 == 0) {
                                                    i202 -= i27;
                                                }
                                                i222 = i202 + measuredHeight3;
                                            }
                                        }
                                        childAt.layout(i11, i202, i212, i222);
                                        if (z2) {
                                            this.mMaskLayout.handleAlignBackground(this.mHelper.getAlignBackgroundGravity(), i11, i202, i212, i222);
                                        }
                                    }
                                }
                                i10 = i12;
                                i11 = i17 + offsetX;
                                int i2022 = i192 + offsetY + (this.mHelper.isFullScreen() ? 0 : -PopupUiUtils.getStatusBarHeight(getContext()));
                                int i2122 = i11 + measuredWidth;
                                int i2222 = i2022 + measuredHeight3;
                                if (i11 >= 0) {
                                    z = false;
                                }
                                if (!this.mHelper.isClipToScreen()) {
                                }
                                childAt.layout(i11, i2022, i2122, i2222);
                                if (z2) {
                                }
                            } else {
                                z = true;
                                measuredWidth2 = getMeasuredWidth();
                                measuredHeight = getMeasuredHeight();
                            }
                            i10 = 0;
                            i11 = i17 + offsetX;
                            int i20222 = i192 + offsetY + (this.mHelper.isFullScreen() ? 0 : -PopupUiUtils.getStatusBarHeight(getContext()));
                            int i21222 = i11 + measuredWidth;
                            int i22222 = i20222 + measuredHeight3;
                            if (i11 >= 0) {
                            }
                            if (!this.mHelper.isClipToScreen()) {
                            }
                            childAt.layout(i11, i20222, i21222, i22222);
                            if (z2) {
                            }
                        } else {
                            i8 = (i4 - i14) - measuredHeight3;
                            measuredHeight = measuredHeight4;
                        }
                        i9 = 0;
                        int i1922 = (i8 + this.childTopMargin) - this.childBottomMargin;
                        if (!this.mHelper.isAutoLocatePopup()) {
                        }
                        z = true;
                        measuredWidth2 = getMeasuredWidth();
                        measuredHeight = getMeasuredHeight();
                        i10 = 0;
                        i11 = i17 + offsetX;
                        int i202222 = i1922 + offsetY + (this.mHelper.isFullScreen() ? 0 : -PopupUiUtils.getStatusBarHeight(getContext()));
                        int i212222 = i11 + measuredWidth;
                        int i222222 = i202222 + measuredHeight3;
                        if (i11 >= 0) {
                        }
                        if (!this.mHelper.isClipToScreen()) {
                        }
                        childAt.layout(i11, i202222, i212222, i222222);
                        if (z2) {
                        }
                    } else if (isShowAsDropDown) {
                        i8 = this.mHelper.getAnchorY() + this.mHelper.getAnchorHeight();
                        offsetY += anchorY2 - ((measuredHeight3 >> 1) + i8);
                        measuredHeight = getMeasuredHeight();
                        i9 = 0;
                        int i19222 = (i8 + this.childTopMargin) - this.childBottomMargin;
                        if (!this.mHelper.isAutoLocatePopup()) {
                        }
                        z = true;
                        measuredWidth2 = getMeasuredWidth();
                        measuredHeight = getMeasuredHeight();
                        i10 = 0;
                        i11 = i17 + offsetX;
                        int i2022222 = i19222 + offsetY + (this.mHelper.isFullScreen() ? 0 : -PopupUiUtils.getStatusBarHeight(getContext()));
                        int i2122222 = i11 + measuredWidth;
                        int i2222222 = i2022222 + measuredHeight3;
                        if (i11 >= 0) {
                        }
                        if (!this.mHelper.isClipToScreen()) {
                        }
                        childAt.layout(i11, i2022222, i2122222, i2222222);
                        if (z2) {
                        }
                    } else {
                        i8 = ((i4 - i14) - measuredHeight3) >> 1;
                        measuredHeight = measuredHeight4;
                        i9 = 0;
                        int i192222 = (i8 + this.childTopMargin) - this.childBottomMargin;
                        if (!this.mHelper.isAutoLocatePopup()) {
                        }
                        z = true;
                        measuredWidth2 = getMeasuredWidth();
                        measuredHeight = getMeasuredHeight();
                        i10 = 0;
                        i11 = i17 + offsetX;
                        int i20222222 = i192222 + offsetY + (this.mHelper.isFullScreen() ? 0 : -PopupUiUtils.getStatusBarHeight(getContext()));
                        int i21222222 = i11 + measuredWidth;
                        int i22222222 = i20222222 + measuredHeight3;
                        if (i11 >= 0) {
                        }
                        if (!this.mHelper.isClipToScreen()) {
                        }
                        childAt.layout(i11, i20222222, i21222222, i22222222);
                        if (z2) {
                        }
                    }
                    i15 = i6 + 1;
                    i13 = i;
                    i14 = i2;
                    childCount = i5;
                }
            }
            i5 = childCount;
            i6 = i15;
            i15 = i6 + 1;
            i13 = i;
            i14 = i2;
            childCount = i5;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0198  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x01a8  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x01b8  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x01f0  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x01aa  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00f6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void fitWindowParams(WindowManager.LayoutParams layoutParams) {
        int anchorX;
        int i;
        int popupGravity;
        int anchorY;
        int i2;
        int i3;
        int anchorY2;
        int screenHeight;
        int anchorY3;
        int measuredHeight;
        int measuredWidth;
        if (getMeasuredWidth() == 0 || getMeasuredHeight() == 0) {
            this.mFlag.flag |= 256;
            return;
        }
        int popupGravity2 = this.mHelper.getPopupGravity();
        int i4 = 0;
        boolean z = this.mHelper.getGravityMode() == BasePopupWindow.GravityMode.ALIGN_TO_ANCHOR_SIDE;
        int popupGravity3 = this.mHelper.getPopupGravity() & 7;
        if (popupGravity3 == 1) {
            anchorX = this.mHelper.isShowAsDropDown() ? this.mHelper.getAnchorX() + ((this.mHelper.getAnchorWidth() - getMeasuredWidth()) >> 1) : (getScreenWidth() - getMeasuredWidth()) >> 1;
        } else {
            if (popupGravity3 != 3) {
                if (popupGravity3 == 5) {
                    if (this.mHelper.isShowAsDropDown()) {
                        anchorX = this.mHelper.getAnchorX() + this.mHelper.getAnchorWidth();
                        if (z) {
                            measuredWidth = getMeasuredWidth();
                        }
                    } else {
                        anchorX = getScreenWidth();
                        measuredWidth = getMeasuredWidth();
                    }
                    anchorX -= measuredWidth;
                }
                i = 0;
            } else {
                if (this.mHelper.isShowAsDropDown()) {
                    anchorX = this.mHelper.getAnchorX();
                    if (!z) {
                        measuredWidth = getMeasuredWidth();
                        anchorX -= measuredWidth;
                    }
                }
                i = 0;
            }
            int i5 = (i + this.childLeftMargin) - this.childRightMargin;
            popupGravity = this.mHelper.getPopupGravity() & 112;
            if (popupGravity != 16) {
                anchorY = this.mHelper.isShowAsDropDown() ? this.mHelper.getAnchorY() + ((this.mHelper.getAnchorHeight() - getMeasuredHeight()) >> 1) : (getScreenHeight() - getMeasuredHeight()) >> 1;
            } else {
                if (popupGravity != 48) {
                    if (popupGravity == 80) {
                        if (this.mHelper.isShowAsDropDown()) {
                            anchorY3 = this.mHelper.getAnchorY() + this.mHelper.getAnchorHeight();
                            if (z) {
                                measuredHeight = getMeasuredHeight();
                                anchorY3 -= measuredHeight;
                            }
                            i2 = anchorY3 + 0;
                        } else {
                            anchorY = getScreenHeight() - getMeasuredHeight();
                        }
                    }
                    i2 = 0;
                } else {
                    if (this.mHelper.isShowAsDropDown()) {
                        anchorY3 = this.mHelper.getAnchorY();
                        if (!z) {
                            measuredHeight = getMeasuredHeight();
                            anchorY3 -= measuredHeight;
                        }
                        i2 = anchorY3 + 0;
                    }
                    i2 = 0;
                }
                i3 = (i2 + this.childTopMargin) - this.childBottomMargin;
                PopupLog.m26i("fitWindowParams  ::  {\n\t\tscreenWidth = " + getScreenWidth() + "\n\t\tscreenHeight = " + getScreenHeight() + "\n\t\tanchorX = " + this.mHelper.getAnchorX() + "\n\t\tanchorY = " + this.mHelper.getAnchorY() + "\n\t\tviewWidth = " + getMeasuredWidth() + "\n\t\tviewHeight = " + getMeasuredHeight() + "\n\t\toffsetX = " + i5 + "\n\t\toffsetY = " + i3 + "\n}");
                if (this.mHelper.isAutoLocatePopup() && this.mHelper.isClipToScreen()) {
                    int i6 = (!this.mHelper.isFullScreen() ? 0 : -PopupUiUtils.getStatusBarHeight(getContext())) + i3;
                    if ((popupGravity2 & 112) != 48) {
                        if (z) {
                            anchorY2 = getScreenHeight() - this.mHelper.getAnchorY();
                        } else {
                            anchorY2 = this.mHelper.getAnchorY();
                        }
                        if (getMeasuredHeight() > anchorY2) {
                            i3 += z ? 0 : (this.mHelper.getAnchorY() + this.mHelper.getMinHeight()) - i3;
                            postAnchorLocation(false);
                        }
                    } else {
                        if (z) {
                            screenHeight = this.mHelper.getAnchorY() + this.mHelper.getAnchorHeight();
                        } else {
                            screenHeight = getScreenHeight() - (this.mHelper.getAnchorY() + this.mHelper.getAnchorHeight());
                        }
                        if (getMeasuredHeight() > screenHeight) {
                            if (!z) {
                                i4 = i6 - this.mHelper.getAnchorY();
                            }
                            i3 -= i4;
                            postAnchorLocation(true);
                        }
                    }
                }
                layoutParams.x = i5 + this.mHelper.getOffsetX();
                layoutParams.y = i3 + this.mHelper.getOffsetY();
                this.originY = layoutParams.y;
                this.mFlag.flag &= -257;
            }
            i2 = anchorY + 0;
            i3 = (i2 + this.childTopMargin) - this.childBottomMargin;
            PopupLog.m26i("fitWindowParams  ::  {\n\t\tscreenWidth = " + getScreenWidth() + "\n\t\tscreenHeight = " + getScreenHeight() + "\n\t\tanchorX = " + this.mHelper.getAnchorX() + "\n\t\tanchorY = " + this.mHelper.getAnchorY() + "\n\t\tviewWidth = " + getMeasuredWidth() + "\n\t\tviewHeight = " + getMeasuredHeight() + "\n\t\toffsetX = " + i5 + "\n\t\toffsetY = " + i3 + "\n}");
            if (this.mHelper.isAutoLocatePopup()) {
                int i62 = (!this.mHelper.isFullScreen() ? 0 : -PopupUiUtils.getStatusBarHeight(getContext())) + i3;
                if ((popupGravity2 & 112) != 48) {
                }
            }
            layoutParams.x = i5 + this.mHelper.getOffsetX();
            layoutParams.y = i3 + this.mHelper.getOffsetY();
            this.originY = layoutParams.y;
            this.mFlag.flag &= -257;
        }
        i = anchorX + 0;
        int i52 = (i + this.childLeftMargin) - this.childRightMargin;
        popupGravity = this.mHelper.getPopupGravity() & 112;
        if (popupGravity != 16) {
        }
        i2 = anchorY + 0;
        i3 = (i2 + this.childTopMargin) - this.childBottomMargin;
        PopupLog.m26i("fitWindowParams  ::  {\n\t\tscreenWidth = " + getScreenWidth() + "\n\t\tscreenHeight = " + getScreenHeight() + "\n\t\tanchorX = " + this.mHelper.getAnchorX() + "\n\t\tanchorY = " + this.mHelper.getAnchorY() + "\n\t\tviewWidth = " + getMeasuredWidth() + "\n\t\tviewHeight = " + getMeasuredHeight() + "\n\t\toffsetX = " + i52 + "\n\t\toffsetY = " + i3 + "\n}");
        if (this.mHelper.isAutoLocatePopup()) {
        }
        layoutParams.x = i52 + this.mHelper.getOffsetX();
        layoutParams.y = i3 + this.mHelper.getOffsetY();
        this.originY = layoutParams.y;
        this.mFlag.flag &= -257;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        PopupMaskLayout popupMaskLayout = this.mMaskLayout;
        if (popupMaskLayout != null) {
            popupMaskLayout.handleStart(-2L);
        }
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        BasePopupHelper basePopupHelper = this.mHelper;
        if (basePopupHelper != null) {
            return basePopupHelper.onInterceptTouchEvent(motionEvent);
        }
        return false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        KeyEvent.DispatcherState keyDispatcherState;
        BasePopupHelper basePopupHelper = this.mHelper;
        if (basePopupHelper != null && basePopupHelper.onDispatchKeyEvent(keyEvent)) {
            return true;
        }
        if (keyEvent.getKeyCode() == 4) {
            if (getKeyDispatcherState() == null) {
                return super.dispatchKeyEvent(keyEvent);
            }
            if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                KeyEvent.DispatcherState keyDispatcherState2 = getKeyDispatcherState();
                if (keyDispatcherState2 != null) {
                    keyDispatcherState2.startTracking(keyEvent, this);
                }
                return true;
            } else if (keyEvent.getAction() == 1 && (keyDispatcherState = getKeyDispatcherState()) != null && keyDispatcherState.isTracking(keyEvent) && !keyEvent.isCanceled() && this.mHelper != null) {
                PopupLog.m25i("PopupDecorViewProxy", "dispatchKeyEvent: >>> onBackPressed");
                return this.mHelper.onBackPressed();
            } else {
                return super.dispatchKeyEvent(keyEvent);
            }
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        BasePopupHelper basePopupHelper = this.mHelper;
        if (basePopupHelper == null || !basePopupHelper.onTouchEvent(motionEvent)) {
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            if (motionEvent.getAction() == 0 && (x < 0 || x >= getWidth() || y < 0 || y >= getHeight())) {
                if (this.mHelper != null) {
                    PopupLog.m25i("PopupDecorViewProxy", "onTouchEvent:[ACTION_DOWN] >>> onOutSideTouch");
                    return this.mHelper.onOutSideTouch();
                }
            } else if (motionEvent.getAction() == 4 && this.mHelper != null) {
                PopupLog.m25i("PopupDecorViewProxy", "onTouchEvent:[ACTION_OUTSIDE] >>> onOutSideTouch");
                return this.mHelper.onOutSideTouch();
            }
            return super.onTouchEvent(motionEvent);
        }
        return true;
    }

    int getScreenWidth() {
        int screenWidthCompat = PopupUiUtils.getScreenWidthCompat(getContext());
        PopupLog.m26i("autoSize  width = " + screenWidthCompat);
        return screenWidthCompat;
    }

    int getScreenHeight() {
        int screenHeightCompat = PopupUiUtils.getScreenHeightCompat(getContext());
        PopupLog.m26i("autoSize  height = " + screenHeightCompat);
        return screenHeightCompat;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        PopupMaskLayout popupMaskLayout;
        super.onDetachedFromWindow();
        if (this.mHelper.isOutSideTouchable() && (popupMaskLayout = this.mMaskLayout) != null && popupMaskLayout.getParent() != null) {
            ((ViewGroup) this.mMaskLayout.getParent()).removeViewInLayout(this.mMaskLayout);
        }
        this.mHelper.registerKeyboardStateChangeListener(null);
        CheckAndCallAutoAnchorLocate checkAndCallAutoAnchorLocate = this.mCheckAndCallAutoAnchorLocate;
        if (checkAndCallAutoAnchorLocate != null) {
            removeCallbacks(checkAndCallAutoAnchorLocate);
            this.mCheckAndCallAutoAnchorLocate = null;
        }
    }

    public void updateLayout() {
        BasePopupHelper basePopupHelper = this.mHelper;
        if (basePopupHelper != null) {
            basePopupHelper.onUpdate();
        }
        PopupMaskLayout popupMaskLayout = this.mMaskLayout;
        if (popupMaskLayout != null) {
            popupMaskLayout.update();
        }
        if (isLayoutRequested()) {
            requestLayout();
        }
    }

    private void removeAnchorLocationChecker() {
        CheckAndCallAutoAnchorLocate checkAndCallAutoAnchorLocate = this.mCheckAndCallAutoAnchorLocate;
        if (checkAndCallAutoAnchorLocate != null) {
            removeCallbacks(checkAndCallAutoAnchorLocate);
        }
    }

    private void postAnchorLocation(boolean z) {
        if (this.mCheckAndCallAutoAnchorLocate == null) {
            this.mCheckAndCallAutoAnchorLocate = new CheckAndCallAutoAnchorLocate(z);
        } else {
            removeAnchorLocationChecker();
        }
        this.mCheckAndCallAutoAnchorLocate.onTop = z;
        postDelayed(this.mCheckAndCallAutoAnchorLocate, 32L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public final class CheckAndCallAutoAnchorLocate implements Runnable {
        private boolean hasCalled;
        private boolean onTop;

        CheckAndCallAutoAnchorLocate(boolean z) {
            this.onTop = z;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (PopupDecorViewProxy.this.mHelper == null || this.hasCalled) {
                return;
            }
            if (this.onTop) {
                PopupDecorViewProxy.this.mHelper.onAnchorTop();
            } else {
                PopupDecorViewProxy.this.mHelper.onAnchorBottom();
            }
            this.hasCalled = true;
        }
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        (getContext() instanceof Activity ? ((Activity) getContext()).getWindow().getDecorView() : this).post(new Runnable() { // from class: razerdp.basepopup.PopupDecorViewProxy.2
            @Override // java.lang.Runnable
            public void run() {
                PopupDecorViewProxy.this.updateLayout();
            }
        });
    }

    @Override // razerdp.basepopup.PopupKeyboardStateChangeListener
    public void onKeyboardChange(int i, int i2, boolean z, boolean z2) {
        View findFocus;
        int i3;
        int onKeyboardChangeResult;
        if (PopupUiUtils.getScreenOrientation(getContext()) == 2) {
            return;
        }
        if ((this.mHelper.getSoftInputMode() != 32 && this.mHelper.getSoftInputMode() != 16) || (findFocus = findFocus()) == null || this.lastVisibleState == z) {
            return;
        }
        findFocus.getGlobalVisibleRect(this.viewRect);
        final ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (this.mHelper.isOutSideTouchable()) {
            i3 = layoutParams instanceof WindowManager.LayoutParams ? ((WindowManager.LayoutParams) layoutParams).y : this.mTarget.getTop();
        } else {
            i3 = 0;
        }
        if (!z2) {
            i3 -= PopupUiUtils.getStatusBarHeight(getContext());
        }
        if (z && i > 0) {
            int bottom = (this.mTarget.getBottom() + i3) - i;
            if (bottom > 0 && this.viewRect.top + i3 >= bottom) {
                this.offset = bottom;
            } else {
                int i4 = this.viewRect.bottom;
                if (i4 > i) {
                    this.offset = i4 - i;
                }
            }
        } else {
            this.offset = 0;
        }
        if (this.mHelper.getEventInterceptor() != null && (onKeyboardChangeResult = this.mHelper.getEventInterceptor().onKeyboardChangeResult(i2, z, this.offset)) != 0) {
            this.offset = onKeyboardChangeResult;
        }
        if (this.mHelper.isOutSideTouchable()) {
            ValueAnimator valueAnimator = this.valueAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            int[] iArr = new int[2];
            iArr[0] = i3;
            iArr[1] = z ? i3 - this.offset : this.originY;
            this.valueAnimator = ValueAnimator.ofInt(iArr);
            this.valueAnimator.setDuration(300L);
            this.valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: razerdp.basepopup.PopupDecorViewProxy.3
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    ViewGroup.LayoutParams layoutParams2 = layoutParams;
                    if (layoutParams2 instanceof WindowManager.LayoutParams) {
                        ((WindowManager.LayoutParams) layoutParams2).y = ((Integer) valueAnimator2.getAnimatedValue()).intValue();
                        PopupDecorViewProxy.this.mWindowManagerProxy.updateViewLayoutOriginal(PopupDecorViewProxy.this, layoutParams);
                    }
                }
            });
            this.valueAnimator.start();
        } else {
            this.mTarget.animate().cancel();
            this.mTarget.animate().translationY(-this.offset).setDuration(300L).start();
            PopupLog.m25i("onKeyboardChange", Boolean.valueOf(z), Integer.valueOf(i2), Integer.valueOf(this.offset));
        }
        this.lastVisibleState = z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static class Flag {
        int flag;

        Flag() {
        }
    }
}
