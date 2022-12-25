package razerdp.basepopup;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import razerdp.basepopup.BasePopupWindow;
import razerdp.blur.PopupBlurOption;
import razerdp.interceptor.PopupWindowEventInterceptor;
import razerdp.library.R$id;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public final class BasePopupHelper implements PopupTouchController, PopupWindowActionListener, PopupWindowLocationListener, PopupKeyboardStateChangeListener, BasePopupFlag {
    private static final int CONTENT_VIEW_ID = R$id.base_popup_content_root;
    private static int showCount;
    private PopupWindowActionListener mActionListener;
    private int mAnchorViewHeight;
    private int mAnchorViewWidth;
    private View mBackgroundView;
    private PopupBlurOption mBlurOption;
    private Animation mDismissAnimation;
    private Animator mDismissAnimator;
    private PopupWindowEventInterceptor mEventInterceptor;
    private PopupKeyboardStateChangeListener mKeyboardStateChangeListener;
    private PopupWindowLocationListener mLocationListener;
    private BasePopupWindow.OnBeforeShowCallback mOnBeforeShowCallback;
    private BasePopupWindow.OnDismissListener mOnDismissListener;
    private ViewGroup.MarginLayoutParams mParseFromXmlParams;
    private Animation mShowAnimation;
    private Animator mShowAnimator;
    private InnerShowInfo mShowInfo;
    private PopupTouchController mTouchControllerDelegate;
    private int maxHeight;
    private int maxWidth;
    private int minHeight;
    private int minWidth;
    private int offsetX;
    private int offsetY;
    private int popupViewHeight;
    private int popupViewWidth;
    private int preMeasureHeight;
    private int preMeasureWidth;
    private int contentRootId = CONTENT_VIEW_ID;
    private int flag = 125;
    private BasePopupWindow.GravityMode gravityMode = BasePopupWindow.GravityMode.RELATIVE_TO_ANCHOR;
    private int popupGravity = 0;
    private Drawable mBackgroundDrawable = new ColorDrawable(BasePopupWindow.DEFAULT_BACKGROUND_COLOR);
    private int alignBackgroundGravity = 48;
    private int mSoftInputMode = 16;
    private int[] mAnchorViewLocation = new int[2];

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public enum ShowMode {
        RELATIVE_TO_ANCHOR,
        SCREEN,
        POSITION
    }

    BasePopupHelper setShowMode(ShowMode showMode) {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static class InnerShowInfo {
        WeakReference<View> mAnchorView;
        boolean positionMode;

        InnerShowInfo(View view, boolean z) {
            this.mAnchorView = new WeakReference<>(view);
            this.positionMode = z;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BasePopupHelper(PopupTouchController popupTouchController) {
        ShowMode showMode = ShowMode.SCREEN;
        new Point();
        this.mTouchControllerDelegate = popupTouchController;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BasePopupHelper registerActionListener(PopupWindowActionListener popupWindowActionListener) {
        this.mActionListener = popupWindowActionListener;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BasePopupHelper registerLocationLisener(PopupWindowLocationListener popupWindowLocationListener) {
        this.mLocationListener = popupWindowLocationListener;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BasePopupHelper registerKeyboardStateChangeListener(PopupKeyboardStateChangeListener popupKeyboardStateChangeListener) {
        this.mKeyboardStateChangeListener = popupKeyboardStateChangeListener;
        return this;
    }

    public View inflate(Context context, int i) {
        try {
            View inflate = LayoutInflater.from(context).inflate(i, (ViewGroup) new FrameLayout(context), false);
            ViewGroup.LayoutParams layoutParams = inflate.getLayoutParams();
            if (layoutParams == null) {
                return null;
            }
            checkAndSetGravity(layoutParams);
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                this.mParseFromXmlParams = new ViewGroup.MarginLayoutParams((ViewGroup.MarginLayoutParams) layoutParams);
                if ((this.flag & 16777216) != 0) {
                    this.mParseFromXmlParams.width = this.popupViewWidth;
                }
                if ((this.flag & 33554432) != 0) {
                    this.mParseFromXmlParams.height = this.popupViewHeight;
                }
                return inflate;
            }
            this.mParseFromXmlParams = new ViewGroup.MarginLayoutParams(layoutParams);
            if ((this.flag & 16777216) != 0) {
                this.mParseFromXmlParams.width = this.popupViewWidth;
            }
            if ((this.flag & 33554432) != 0) {
                this.mParseFromXmlParams.height = this.popupViewHeight;
            }
            return inflate;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void checkAndSetGravity(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams == null) {
            return;
        }
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            setPopupGravity(this.gravityMode, ((LinearLayout.LayoutParams) layoutParams).gravity);
        } else if (!(layoutParams instanceof FrameLayout.LayoutParams)) {
        } else {
            setPopupGravity(this.gravityMode, ((FrameLayout.LayoutParams) layoutParams).gravity);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Animation getShowAnimation() {
        return this.mShowAnimation;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BasePopupHelper setShowAnimation(Animation animation) {
        Animation animation2 = this.mShowAnimation;
        if (animation2 == animation) {
            return this;
        }
        if (animation2 != null) {
            animation2.cancel();
        }
        this.mShowAnimation = animation;
        applyBlur(this.mBlurOption);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Animator getShowAnimator() {
        return this.mShowAnimator;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BasePopupHelper setShowAnimator(Animator animator) {
        Animator animator2 = this.mShowAnimator;
        if (animator2 == animator) {
            return this;
        }
        if (animator2 != null) {
            animator2.cancel();
        }
        this.mShowAnimator = animator;
        applyBlur(this.mBlurOption);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Animation getDismissAnimation() {
        return this.mDismissAnimation;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BasePopupHelper setDismissAnimation(Animation animation) {
        Animation animation2 = this.mDismissAnimation;
        if (animation2 == animation) {
            return this;
        }
        if (animation2 != null) {
            animation2.cancel();
        }
        this.mDismissAnimation = animation;
        applyBlur(this.mBlurOption);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Animator getDismissAnimator() {
        return this.mDismissAnimator;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BasePopupHelper setDismissAnimator(Animator animator) {
        Animator animator2 = this.mDismissAnimator;
        if (animator2 == animator) {
            return this;
        }
        if (animator2 != null) {
            animator2.cancel();
        }
        this.mDismissAnimator = animator;
        applyBlur(this.mBlurOption);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isCustomMeasure() {
        return (this.flag & 50331648) != 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getPopupViewWidth() {
        if ((this.flag & 16777216) != 0) {
            return this.popupViewWidth;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = this.mParseFromXmlParams;
        if (marginLayoutParams != null) {
            return marginLayoutParams.width;
        }
        return this.popupViewWidth;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BasePopupHelper setPopupViewWidth(int i) {
        this.popupViewWidth = i;
        if (i != -2) {
            setFlag(16777216, true);
            ViewGroup.MarginLayoutParams marginLayoutParams = this.mParseFromXmlParams;
            if (marginLayoutParams != null) {
                marginLayoutParams.width = i;
            }
        } else {
            setFlag(16777216, false);
        }
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getPopupViewHeight() {
        if ((this.flag & 33554432) != 0) {
            return this.popupViewHeight;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = this.mParseFromXmlParams;
        if (marginLayoutParams != null) {
            return marginLayoutParams.height;
        }
        return this.popupViewHeight;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BasePopupHelper setPopupViewHeight(int i) {
        this.popupViewHeight = i;
        if (i != -2) {
            setFlag(33554432, true);
            ViewGroup.MarginLayoutParams marginLayoutParams = this.mParseFromXmlParams;
            if (marginLayoutParams != null) {
                marginLayoutParams.height = i;
            }
        } else {
            setFlag(33554432, false);
        }
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getPreMeasureWidth() {
        return this.preMeasureWidth;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BasePopupHelper setPreMeasureWidth(int i) {
        this.preMeasureWidth = i;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getPreMeasureHeight() {
        return this.preMeasureHeight;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BasePopupHelper setPreMeasureHeight(int i) {
        this.preMeasureHeight = i;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isPopupFadeEnable() {
        return (this.flag & 64) != 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BasePopupHelper setPopupFadeEnable(PopupWindow popupWindow, boolean z) {
        if (popupWindow == null) {
            return this;
        }
        setFlag(64, z);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isShowAsDropDown() {
        return (this.flag & 256) != 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BasePopupHelper setShowAsDropDown(boolean z) {
        setFlag(256, z);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BasePopupHelper setShowLocation(int i, int i2) {
        int[] iArr = this.mAnchorViewLocation;
        iArr[0] = i;
        iArr[1] = i2;
        this.mAnchorViewWidth = 1;
        this.mAnchorViewHeight = 1;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BasePopupWindow.GravityMode getGravityMode() {
        return this.gravityMode;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getPopupGravity() {
        return this.popupGravity;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BasePopupHelper setPopupGravity(BasePopupWindow.GravityMode gravityMode, int i) {
        if (i == this.popupGravity && this.gravityMode == gravityMode) {
            return this;
        }
        this.gravityMode = gravityMode;
        this.popupGravity = i;
        return this;
    }

    public BasePopupHelper setClipChildren(boolean z) {
        setFlag(16, z);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getOffsetX() {
        return this.offsetX;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BasePopupHelper setOffsetX(int i) {
        this.offsetX = i;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getOffsetY() {
        return this.offsetY;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BasePopupHelper setOffsetY(int i) {
        this.offsetY = i;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isAutoShowInputMethod() {
        return (this.flag & 512) != 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BasePopupHelper autoShowInputMethod(PopupWindow popupWindow, boolean z) {
        if (popupWindow == null) {
            return this;
        }
        setFlag(512, z);
        popupWindow.setSoftInputMode(z ? 16 : 1);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BasePopupHelper setSoftInputMode(int i) {
        this.mSoftInputMode = i;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isAutoLocatePopup() {
        return (this.flag & 128) != 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BasePopupHelper autoLocatePopup(boolean z) {
        setFlag(128, z);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BasePopupWindow.OnDismissListener getOnDismissListener() {
        return this.mOnDismissListener;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BasePopupHelper setOnDismissListener(BasePopupWindow.OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BasePopupWindow.OnBeforeShowCallback getOnBeforeShowCallback() {
        return this.mOnBeforeShowCallback;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BasePopupHelper setOnBeforeShowCallback(BasePopupWindow.OnBeforeShowCallback onBeforeShowCallback) {
        this.mOnBeforeShowCallback = onBeforeShowCallback;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isOutSideDismiss() {
        return (this.flag & 1) != 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BasePopupHelper dismissOutSideTouch(PopupWindow popupWindow, boolean z) {
        if (popupWindow == null) {
            return this;
        }
        setFlag(1, z);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isOutSideTouchable() {
        return (this.flag & 2) != 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BasePopupHelper outSideTouchable(PopupWindow popupWindow, boolean z) {
        if (popupWindow == null) {
            return this;
        }
        setFlag(2, z);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BasePopupHelper setClipToScreen(boolean z) {
        setFlag(32, z);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BasePopupHelper setEventInterceptor(PopupWindowEventInterceptor popupWindowEventInterceptor) {
        this.mEventInterceptor = popupWindowEventInterceptor;
        return this;
    }

    BasePopupHelper getAnchorLocation(View view) {
        if (view == null) {
            return this;
        }
        view.getLocationOnScreen(this.mAnchorViewLocation);
        this.mAnchorViewWidth = view.getWidth();
        this.mAnchorViewHeight = view.getHeight();
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getAnchorWidth() {
        return this.mAnchorViewWidth;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getAnchorHeight() {
        return this.mAnchorViewHeight;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getAnchorX() {
        return this.mAnchorViewLocation[0];
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getAnchorY() {
        return this.mAnchorViewLocation[1];
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isBackPressEnable() {
        return (this.flag & 4) != 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isClipToScreen() {
        return (this.flag & 32) != 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BasePopupHelper backPressEnable(PopupWindow popupWindow, boolean z) {
        if (popupWindow == null) {
            return this;
        }
        setFlag(4, z);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isFullScreen() {
        return (this.flag & 8) != 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BasePopupHelper fullScreen(boolean z) {
        setFlag(8, z);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public PopupBlurOption getBlurOption() {
        return this.mBlurOption;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BasePopupHelper applyBlur(PopupBlurOption popupBlurOption) {
        this.mBlurOption = popupBlurOption;
        if (popupBlurOption != null) {
            if (popupBlurOption.getBlurInDuration() <= 0) {
                long showAnimationDuration = getShowAnimationDuration();
                if (showAnimationDuration > 0) {
                    popupBlurOption.setBlurInDuration(showAnimationDuration);
                }
            }
            if (popupBlurOption.getBlurOutDuration() <= 0) {
                long dismissAnimationDuration = getDismissAnimationDuration();
                if (dismissAnimationDuration > 0) {
                    popupBlurOption.setBlurOutDuration(dismissAnimationDuration);
                }
            }
        }
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public long getShowAnimationDuration() {
        long durationFromAnimator;
        Animation animation = this.mShowAnimation;
        if (animation != null) {
            durationFromAnimator = animation.getDuration();
        } else {
            Animator animator = this.mShowAnimator;
            durationFromAnimator = animator != null ? getDurationFromAnimator(animator) : 0L;
        }
        if (durationFromAnimator < 0) {
            return 500L;
        }
        return durationFromAnimator;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public long getDismissAnimationDuration() {
        long durationFromAnimator;
        Animation animation = this.mDismissAnimation;
        if (animation != null) {
            durationFromAnimator = animation.getDuration();
        } else {
            Animator animator = this.mDismissAnimator;
            durationFromAnimator = animator != null ? getDurationFromAnimator(animator) : 0L;
        }
        if (durationFromAnimator < 0) {
            return 500L;
        }
        return durationFromAnimator;
    }

    private long getDurationFromAnimator(Animator animator) {
        if (animator == null) {
            return -1L;
        }
        if (animator instanceof AnimatorSet) {
            AnimatorSet animatorSet = (AnimatorSet) animator;
            long duration = animatorSet.getDuration();
            if (duration >= 0) {
                return duration;
            }
            Iterator<Animator> it2 = animatorSet.getChildAnimations().iterator();
            while (it2.hasNext()) {
                duration = Math.max(duration, it2.next().getDuration());
            }
            return duration;
        }
        return animator.getDuration();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Drawable getPopupBackground() {
        return this.mBackgroundDrawable;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BasePopupHelper setPopupBackground(Drawable drawable) {
        this.mBackgroundDrawable = drawable;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isAlignBackground() {
        return (this.flag & 1024) != 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BasePopupHelper setAlignBackgound(boolean z) {
        setFlag(1024, z);
        if (!z) {
            setAlignBackgroundGravity(0);
        }
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getAlignBackgroundGravity() {
        if (isAlignBackground() && this.alignBackgroundGravity == 0) {
            this.alignBackgroundGravity = 48;
        }
        return this.alignBackgroundGravity;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BasePopupHelper setAlignBackgroundGravity(int i) {
        this.alignBackgroundGravity = i;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isAllowToBlur() {
        PopupBlurOption popupBlurOption = this.mBlurOption;
        return popupBlurOption != null && popupBlurOption.isAllowToBlur();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isClipChildren() {
        return (this.flag & 16) != 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ViewGroup.MarginLayoutParams getParaseFromXmlParams() {
        return this.mParseFromXmlParams;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getShowCount() {
        return showCount;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public PopupWindowEventInterceptor getEventInterceptor() {
        return this.mEventInterceptor;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BasePopupHelper setContentRootId(View view) {
        if (view == null) {
            return this;
        }
        if (view.getId() == -1) {
            view.setId(CONTENT_VIEW_ID);
        }
        this.contentRootId = view.getId();
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getContentRootId() {
        return this.contentRootId;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getSoftInputMode() {
        return this.mSoftInputMode;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public View getBackgroundView() {
        return this.mBackgroundView;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BasePopupHelper setBackgroundView(View view) {
        this.mBackgroundView = view;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getMaxWidth() {
        return this.maxWidth;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BasePopupHelper setMaxWidth(int i) {
        this.maxWidth = i;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getMaxHeight() {
        return this.maxHeight;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BasePopupHelper setMaxHeight(int i) {
        this.maxHeight = i;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getMinWidth() {
        return this.minWidth;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BasePopupHelper setMinWidth(int i) {
        this.minWidth = i;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getMinHeight() {
        return this.minHeight;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BasePopupHelper setMinHeight(int i) {
        this.minHeight = i;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BasePopupHelper keepSize(boolean z) {
        setFlag(2048, z);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isKeepSize() {
        return (this.flag & 2048) != 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void prepare(View view, boolean z) {
        this.mShowInfo = new InnerShowInfo(view, z);
        if (z) {
            setShowMode(ShowMode.POSITION);
        } else {
            setShowMode(view == null ? ShowMode.SCREEN : ShowMode.RELATIVE_TO_ANCHOR);
        }
        getAnchorLocation(view);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void handleShow() {
        int i = Build.VERSION.SDK_INT;
        if (i == 21 || i == 22) {
            showCount++;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void handleDismiss() {
        int i = Build.VERSION.SDK_INT;
        if (i == 21 || i == 22) {
            showCount--;
            showCount = Math.max(0, showCount);
        }
    }

    private void setFlag(int i, boolean z) {
        if (!z) {
            this.flag = (~i) & this.flag;
            return;
        }
        this.flag |= i;
        if (i != 128) {
            return;
        }
        this.flag |= 256;
    }

    @Override // razerdp.basepopup.PopupTouchController
    public boolean onBeforeDismiss() {
        return this.mTouchControllerDelegate.onBeforeDismiss();
    }

    @Override // razerdp.basepopup.PopupTouchController
    public boolean callDismissAtOnce() {
        return this.mTouchControllerDelegate.callDismissAtOnce();
    }

    @Override // razerdp.basepopup.PopupTouchController
    public boolean onDispatchKeyEvent(KeyEvent keyEvent) {
        return this.mTouchControllerDelegate.onDispatchKeyEvent(keyEvent);
    }

    @Override // razerdp.basepopup.PopupTouchController
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return this.mTouchControllerDelegate.onInterceptTouchEvent(motionEvent);
    }

    @Override // razerdp.basepopup.PopupTouchController
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.mTouchControllerDelegate.onTouchEvent(motionEvent);
    }

    @Override // razerdp.basepopup.PopupTouchController
    public boolean onBackPressed() {
        return this.mTouchControllerDelegate.onBackPressed();
    }

    @Override // razerdp.basepopup.PopupTouchController
    public boolean onOutSideTouch() {
        return this.mTouchControllerDelegate.onOutSideTouch();
    }

    @Override // razerdp.basepopup.PopupWindowActionListener
    public void onShow(boolean z) {
        PopupWindowActionListener popupWindowActionListener = this.mActionListener;
        if (popupWindowActionListener != null) {
            popupWindowActionListener.onShow(z);
        }
    }

    @Override // razerdp.basepopup.PopupWindowActionListener
    public void onDismiss(boolean z) {
        PopupWindowActionListener popupWindowActionListener = this.mActionListener;
        if (popupWindowActionListener != null) {
            popupWindowActionListener.onDismiss(z);
        }
    }

    @Override // razerdp.basepopup.PopupWindowLocationListener
    public void onAnchorTop() {
        PopupWindowLocationListener popupWindowLocationListener = this.mLocationListener;
        if (popupWindowLocationListener != null) {
            popupWindowLocationListener.onAnchorTop();
        }
    }

    @Override // razerdp.basepopup.PopupWindowLocationListener
    public void onAnchorBottom() {
        PopupWindowLocationListener popupWindowLocationListener = this.mLocationListener;
        if (popupWindowLocationListener != null) {
            popupWindowLocationListener.onAnchorBottom();
        }
    }

    @Override // razerdp.basepopup.PopupKeyboardStateChangeListener
    public void onKeyboardChange(int i, int i2, boolean z, boolean z2) {
        PopupKeyboardStateChangeListener popupKeyboardStateChangeListener = this.mKeyboardStateChangeListener;
        if (popupKeyboardStateChangeListener != null) {
            popupKeyboardStateChangeListener.onKeyboardChange(i, i2, z, z2);
        }
    }

    public boolean onUpdate() {
        InnerShowInfo innerShowInfo = this.mShowInfo;
        if (innerShowInfo != null) {
            WeakReference<View> weakReference = innerShowInfo.mAnchorView;
            prepare(weakReference == null ? null : weakReference.get(), this.mShowInfo.positionMode);
            return false;
        }
        return false;
    }
}
