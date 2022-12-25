package com.zyyoona7.popup;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.p002v4.view.ViewCompat;
import android.support.p002v4.widget.PopupWindowCompat;
import android.transition.Transition;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.PopupWindow;
import com.zyyoona7.popup.BasePopup;

/* loaded from: classes4.dex */
public abstract class BasePopup<T extends BasePopup> implements PopupWindow.OnDismissListener {
    private boolean isBackgroundDim;
    private boolean isRealWHAlready;
    private View mAnchorView;
    private int mAnimationStyle;
    private View mContentView;
    private Context mContext;
    @NonNull
    private ViewGroup mDimView;
    private Transition mEnterTransition;
    private Transition mExitTransition;
    private int mLayoutId;
    private int mOffsetX;
    private int mOffsetY;
    private PopupWindow.OnDismissListener mOnDismissListener;
    private OnRealWHAlreadyListener mOnRealWHAlreadyListener;
    private PopupWindow mPopupWindow;
    private boolean mFocusable = true;
    private boolean mOutsideTouchable = true;
    private int mWidth = -2;
    private int mHeight = -2;
    private float mDimValue = 0.7f;
    @ColorInt
    private int mDimColor = ViewCompat.MEASURED_STATE_MASK;
    private boolean mFocusAndOutsideEnable = true;
    private int mYGravity = 2;
    private int mXGravity = 1;
    private int mInputMethodMode = 0;
    private int mSoftInputMode = 1;
    private boolean isNeedReMeasureWH = false;
    private boolean isAtAnchorViewMethod = false;

    /* loaded from: classes4.dex */
    public interface OnRealWHAlreadyListener {
        void onRealWHAlready(BasePopup basePopup, int i, int i2, int i3, int i4);
    }

    protected abstract void initAttributes();

    protected abstract void initViews(View view, T t);

    protected void onPopupWindowDismiss() {
    }

    protected T self() {
        return this;
    }

    public T apply() {
        if (this.mPopupWindow == null) {
            this.mPopupWindow = new PopupWindow();
        }
        onPopupWindowCreated();
        initContentViewAndWH();
        onPopupWindowViewCreated(this.mContentView);
        int i = this.mAnimationStyle;
        if (i != 0) {
            this.mPopupWindow.setAnimationStyle(i);
        }
        initFocusAndBack();
        this.mPopupWindow.setOnDismissListener(this);
        if (Build.VERSION.SDK_INT >= 23) {
            Transition transition = this.mEnterTransition;
            if (transition != null) {
                this.mPopupWindow.setEnterTransition(transition);
            }
            Transition transition2 = this.mExitTransition;
            if (transition2 != null) {
                this.mPopupWindow.setExitTransition(transition2);
            }
        }
        self();
        return this;
    }

    private void initContentViewAndWH() {
        Context context;
        if (this.mContentView == null) {
            if (this.mLayoutId != 0 && (context = this.mContext) != null) {
                this.mContentView = LayoutInflater.from(context).inflate(this.mLayoutId, (ViewGroup) null);
            } else {
                throw new IllegalArgumentException("The content view is null,the layoutId=" + this.mLayoutId + ",context=" + this.mContext);
            }
        }
        this.mPopupWindow.setContentView(this.mContentView);
        int i = this.mWidth;
        if (i > 0 || i == -2 || i == -1) {
            this.mPopupWindow.setWidth(this.mWidth);
        } else {
            this.mPopupWindow.setWidth(-2);
        }
        int i2 = this.mHeight;
        if (i2 > 0 || i2 == -2 || i2 == -1) {
            this.mPopupWindow.setHeight(this.mHeight);
        } else {
            this.mPopupWindow.setHeight(-2);
        }
        measureContentView();
        registerOnGlobalLayoutListener();
        this.mPopupWindow.setInputMethodMode(this.mInputMethodMode);
        this.mPopupWindow.setSoftInputMode(this.mSoftInputMode);
    }

    private void initFocusAndBack() {
        if (!this.mFocusAndOutsideEnable) {
            this.mPopupWindow.setFocusable(true);
            this.mPopupWindow.setOutsideTouchable(false);
            this.mPopupWindow.setBackgroundDrawable(null);
            this.mPopupWindow.getContentView().setFocusable(true);
            this.mPopupWindow.getContentView().setFocusableInTouchMode(true);
            this.mPopupWindow.getContentView().setOnKeyListener(new View.OnKeyListener() { // from class: com.zyyoona7.popup.BasePopup.1
                @Override // android.view.View.OnKeyListener
                public boolean onKey(View view, int i, KeyEvent keyEvent) {
                    if (i == 4) {
                        BasePopup.this.mPopupWindow.dismiss();
                        return true;
                    }
                    return false;
                }
            });
            this.mPopupWindow.setTouchInterceptor(new View.OnTouchListener() { // from class: com.zyyoona7.popup.BasePopup.2
                @Override // android.view.View.OnTouchListener
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int x = (int) motionEvent.getX();
                    int y = (int) motionEvent.getY();
                    if (motionEvent.getAction() == 0 && (x < 0 || x >= BasePopup.this.mWidth || y < 0 || y >= BasePopup.this.mHeight)) {
                        Log.d("EasyPopup", "onTouch outside:mWidth=" + BasePopup.this.mWidth + ",mHeight=" + BasePopup.this.mHeight);
                        return true;
                    } else if (motionEvent.getAction() != 4) {
                        return false;
                    } else {
                        Log.d("EasyPopup", "onTouch outside event:mWidth=" + BasePopup.this.mWidth + ",mHeight=" + BasePopup.this.mHeight);
                        return true;
                    }
                }
            });
            return;
        }
        this.mPopupWindow.setFocusable(this.mFocusable);
        this.mPopupWindow.setOutsideTouchable(this.mOutsideTouchable);
        this.mPopupWindow.setBackgroundDrawable(new ColorDrawable(0));
    }

    protected void onPopupWindowCreated() {
        initAttributes();
    }

    protected void onPopupWindowViewCreated(View view) {
        self();
        initViews(view, this);
    }

    private void measureContentView() {
        View contentView = getContentView();
        if (this.mWidth <= 0 || this.mHeight <= 0) {
            contentView.measure(0, 0);
            if (this.mWidth <= 0) {
                this.mWidth = contentView.getMeasuredWidth();
            }
            if (this.mHeight > 0) {
                return;
            }
            this.mHeight = contentView.getMeasuredHeight();
        }
    }

    private void registerOnGlobalLayoutListener() {
        getContentView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.zyyoona7.popup.BasePopup.3
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                BasePopup.this.getContentView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
                BasePopup basePopup = BasePopup.this;
                basePopup.mWidth = basePopup.getContentView().getWidth();
                BasePopup basePopup2 = BasePopup.this;
                basePopup2.mHeight = basePopup2.getContentView().getHeight();
                BasePopup.this.isRealWHAlready = true;
                BasePopup.this.isNeedReMeasureWH = false;
                if (BasePopup.this.mOnRealWHAlreadyListener != null) {
                    OnRealWHAlreadyListener onRealWHAlreadyListener = BasePopup.this.mOnRealWHAlreadyListener;
                    BasePopup basePopup3 = BasePopup.this;
                    onRealWHAlreadyListener.onRealWHAlready(basePopup3, basePopup3.mWidth, BasePopup.this.mHeight, BasePopup.this.mAnchorView == null ? 0 : BasePopup.this.mAnchorView.getWidth(), BasePopup.this.mAnchorView == null ? 0 : BasePopup.this.mAnchorView.getHeight());
                }
                if (!BasePopup.this.isShowing() || !BasePopup.this.isAtAnchorViewMethod) {
                    return;
                }
                BasePopup basePopup4 = BasePopup.this;
                basePopup4.updateLocation(basePopup4.mWidth, BasePopup.this.mHeight, BasePopup.this.mAnchorView, BasePopup.this.mYGravity, BasePopup.this.mXGravity, BasePopup.this.mOffsetX, BasePopup.this.mOffsetY);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLocation(int i, int i2, @NonNull View view, int i3, int i4, int i5, int i6) {
        if (this.mPopupWindow == null) {
            return;
        }
        this.mPopupWindow.update(view, calculateX(view, i4, i, i5), calculateY(view, i3, i2, i6), i, i2);
    }

    public T setContentView(Context context, @LayoutRes int i) {
        this.mContext = context;
        this.mContentView = null;
        this.mLayoutId = i;
        self();
        return this;
    }

    public T setFocusAndOutsideEnable(boolean z) {
        this.mFocusAndOutsideEnable = z;
        self();
        return this;
    }

    public T setBackgroundDimEnable(boolean z) {
        this.isBackgroundDim = z;
        self();
        return this;
    }

    public T setDimValue(@FloatRange(from = 0.0d, m5592to = 1.0d) float f) {
        this.mDimValue = f;
        self();
        return this;
    }

    public T setDimColor(@ColorInt int i) {
        this.mDimColor = i;
        self();
        return this;
    }

    private void checkIsApply(boolean z) {
        if (this.isAtAnchorViewMethod != z) {
            this.isAtAnchorViewMethod = z;
        }
        if (this.mPopupWindow == null) {
            apply();
        }
    }

    public void showAtAnchorView(@NonNull View view, int i, int i2) {
        showAtAnchorView(view, i, i2, 0, 0);
    }

    public void showAtAnchorView(@NonNull View view, int i, int i2, int i3, int i4) {
        checkIsApply(true);
        this.mAnchorView = view;
        this.mOffsetX = i3;
        this.mOffsetY = i4;
        this.mYGravity = i;
        this.mXGravity = i2;
        handleBackgroundDim();
        int calculateX = calculateX(view, i2, this.mWidth, this.mOffsetX);
        int calculateY = calculateY(view, i, this.mHeight, this.mOffsetY);
        if (this.isNeedReMeasureWH) {
            registerOnGlobalLayoutListener();
        }
        PopupWindowCompat.showAsDropDown(this.mPopupWindow, view, calculateX, calculateY, 0);
    }

    private int calculateY(View view, int i, int i2, int i3) {
        int height;
        if (i != 0) {
            if (i == 1) {
                i2 += view.getHeight();
            } else if (i == 3) {
                height = view.getHeight();
            } else if (i != 4) {
                return i3;
            }
            return i3 - i2;
        }
        height = (view.getHeight() / 2) + (i2 / 2);
        return i3 - height;
    }

    private int calculateX(View view, int i, int i2, int i3) {
        int width;
        if (i != 0) {
            if (i != 1) {
                if (i == 2) {
                    width = view.getWidth();
                } else if (i == 3 || i != 4) {
                    return i3;
                } else {
                    i2 -= view.getWidth();
                }
            }
            return i3 - i2;
        }
        width = (view.getWidth() / 2) - (i2 / 2);
        return i3 + width;
    }

    private void handleBackgroundDim() {
        if (Build.VERSION.SDK_INT < 18 || !this.isBackgroundDim) {
            return;
        }
        ViewGroup viewGroup = this.mDimView;
        if (viewGroup != null) {
            applyDim(viewGroup);
        } else if (getContentView() == null || getContentView().getContext() == null || !(getContentView().getContext() instanceof Activity)) {
        } else {
            applyDim((Activity) getContentView().getContext());
        }
    }

    @RequiresApi(api = 18)
    private void applyDim(Activity activity) {
        ViewGroup viewGroup = (ViewGroup) activity.getWindow().getDecorView().getRootView();
        ColorDrawable colorDrawable = new ColorDrawable(this.mDimColor);
        colorDrawable.setBounds(0, 0, viewGroup.getWidth(), viewGroup.getHeight());
        colorDrawable.setAlpha((int) (this.mDimValue * 255.0f));
        viewGroup.getOverlay().add(colorDrawable);
    }

    @RequiresApi(api = 18)
    private void applyDim(ViewGroup viewGroup) {
        ColorDrawable colorDrawable = new ColorDrawable(this.mDimColor);
        colorDrawable.setBounds(0, 0, viewGroup.getWidth(), viewGroup.getHeight());
        colorDrawable.setAlpha((int) (this.mDimValue * 255.0f));
        viewGroup.getOverlay().add(colorDrawable);
    }

    private void clearBackgroundDim() {
        Activity activity;
        if (Build.VERSION.SDK_INT < 18 || !this.isBackgroundDim) {
            return;
        }
        ViewGroup viewGroup = this.mDimView;
        if (viewGroup != null) {
            clearDim(viewGroup);
        } else if (getContentView() == null || (activity = (Activity) getContentView().getContext()) == null) {
        } else {
            clearDim(activity);
        }
    }

    @RequiresApi(api = 18)
    private void clearDim(Activity activity) {
        ((ViewGroup) activity.getWindow().getDecorView().getRootView()).getOverlay().clear();
    }

    @RequiresApi(api = 18)
    private void clearDim(ViewGroup viewGroup) {
        viewGroup.getOverlay().clear();
    }

    public View getContentView() {
        PopupWindow popupWindow = this.mPopupWindow;
        if (popupWindow != null) {
            return popupWindow.getContentView();
        }
        return null;
    }

    public boolean isShowing() {
        PopupWindow popupWindow = this.mPopupWindow;
        return popupWindow != null && popupWindow.isShowing();
    }

    /* JADX WARN: Incorrect return type in method signature: <T:Landroid/view/View;>(I)TT; */
    public View findViewById(@IdRes int i) {
        if (getContentView() != null) {
            return getContentView().findViewById(i);
        }
        return null;
    }

    @Override // android.widget.PopupWindow.OnDismissListener
    public void onDismiss() {
        handleDismiss();
    }

    private void handleDismiss() {
        PopupWindow.OnDismissListener onDismissListener = this.mOnDismissListener;
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
        }
        clearBackgroundDim();
        PopupWindow popupWindow = this.mPopupWindow;
        if (popupWindow != null && popupWindow.isShowing()) {
            this.mPopupWindow.dismiss();
        }
        onPopupWindowDismiss();
    }
}
