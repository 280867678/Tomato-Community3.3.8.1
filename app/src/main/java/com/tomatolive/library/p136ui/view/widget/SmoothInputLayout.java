package com.tomatolive.library.p136ui.view.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import com.tomatolive.library.R$styleable;

/* renamed from: com.tomatolive.library.ui.view.widget.SmoothInputLayout */
/* loaded from: classes4.dex */
public class SmoothInputLayout extends LinearLayout {
    public static final int DEFAULT_KEYBOARD_HEIGHT = 300;
    private static final String KEY_HEIGHT = "height";
    public static final int MIN_KEYBOARD_HEIGHT = 200;
    private static final String SP_KEYBOARD = "keyboard";
    private OnKeyboardChangeListener keyboardChangeListener;
    private boolean mAutoSaveKeyboardHeight;
    private int mDefaultKeyboardHeight;
    private View mInputPane;
    private int mInputPaneId;
    private View mInputView;
    private int mInputViewId;
    private int mKeyboardHeight;
    private boolean mKeyboardOpen;
    private KeyboardProcessor mKeyboardProcessor;
    private OnVisibilityChangeListener mListener;
    private int mMaxKeyboardHeight;
    private int mMinKeyboardHeight;
    private boolean noSaveHeight;
    private View outSideView;
    private boolean tShowInputPane;

    /* renamed from: com.tomatolive.library.ui.view.widget.SmoothInputLayout$KeyboardProcessor */
    /* loaded from: classes4.dex */
    public interface KeyboardProcessor {
        int getSavedKeyboardHeight(int i);

        void onSaveKeyboardHeight(int i);
    }

    /* renamed from: com.tomatolive.library.ui.view.widget.SmoothInputLayout$OnKeyboardChangeListener */
    /* loaded from: classes4.dex */
    public interface OnKeyboardChangeListener {
        void onKeyboardChanged(boolean z);
    }

    /* renamed from: com.tomatolive.library.ui.view.widget.SmoothInputLayout$OnVisibilityChangeListener */
    /* loaded from: classes4.dex */
    public interface OnVisibilityChangeListener {
        void onVisibilityChange(int i);
    }

    public SmoothInputLayout(Context context) {
        this(context, null);
    }

    public SmoothInputLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    @TargetApi(11)
    public SmoothInputLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mMaxKeyboardHeight = Integer.MIN_VALUE;
        this.mKeyboardOpen = false;
        this.tShowInputPane = false;
        initView(attributeSet);
    }

    private void initView(AttributeSet attributeSet) {
        this.mInputViewId = -1;
        this.mInputPaneId = -1;
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.SmoothInputLayout);
        int dimensionPixelOffset = obtainStyledAttributes.getDimensionPixelOffset(R$styleable.SmoothInputLayout_silDefaultKeyboardHeight, (int) (getResources().getDisplayMetrics().density * 300.0f));
        int dimensionPixelOffset2 = obtainStyledAttributes.getDimensionPixelOffset(R$styleable.SmoothInputLayout_silMinKeyboardHeight, (int) (getResources().getDisplayMetrics().density * 200.0f));
        this.mInputViewId = obtainStyledAttributes.getResourceId(R$styleable.SmoothInputLayout_silInputView, this.mInputViewId);
        this.mInputPaneId = obtainStyledAttributes.getResourceId(R$styleable.SmoothInputLayout_silInputPane, this.mInputPaneId);
        boolean z = obtainStyledAttributes.getBoolean(R$styleable.SmoothInputLayout_silAutoSaveKeyboardHeight, true);
        obtainStyledAttributes.recycle();
        setDefaultKeyboardHeight(dimensionPixelOffset);
        setMinKeyboardHeight(dimensionPixelOffset2);
        setAutoSaveKeyboardHeight(z);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        int i = this.mInputViewId;
        if (i != -1) {
            setInputView(findViewById(i));
        }
        int i2 = this.mInputPaneId;
        if (i2 != -1) {
            setInputPane(findViewById(i2));
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        int size = View.MeasureSpec.getSize(i2);
        if (size > this.mMaxKeyboardHeight) {
            this.mMaxKeyboardHeight = size;
        }
        int i3 = this.mMaxKeyboardHeight - size;
        if (i3 > this.mMinKeyboardHeight) {
            updateHeight(i3);
        } else {
            initCloseKeyBoard();
        }
        super.onMeasure(i, i2);
    }

    public void setKeyboardBoolean(boolean z) {
        this.mKeyboardOpen = z;
    }

    public void updateHeight(int i) {
        if (this.mKeyboardHeight != i) {
            this.mKeyboardHeight = i;
            saveKeyboardHeight();
        }
        setKeyboardBoolean(true);
        View view = this.mInputPane;
        if (view == null || view.getVisibility() != 0) {
            return;
        }
        this.mInputPane.setVisibility(8);
        OnVisibilityChangeListener onVisibilityChangeListener = this.mListener;
        if (onVisibilityChangeListener == null) {
            return;
        }
        onVisibilityChangeListener.onVisibilityChange(8);
    }

    public void initCloseKeyBoard() {
        if (this.tShowInputPane) {
            this.tShowInputPane = false;
            View view = this.mInputPane;
            if (view == null || view.getVisibility() != 8) {
                return;
            }
            updateLayout();
            this.mInputPane.setVisibility(0);
            OnVisibilityChangeListener onVisibilityChangeListener = this.mListener;
            if (onVisibilityChangeListener != null) {
                onVisibilityChangeListener.onVisibilityChange(0);
            }
            forceLayout();
        }
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        OnKeyboardChangeListener onKeyboardChangeListener = this.keyboardChangeListener;
        if (onKeyboardChangeListener != null) {
            onKeyboardChangeListener.onKeyboardChanged(this.mKeyboardOpen);
        }
    }

    private SharedPreferences getKeyboardSharedPreferences() {
        return getContext().getSharedPreferences(SP_KEYBOARD, 0);
    }

    private void saveKeyboardHeight() {
        if (this.noSaveHeight) {
            return;
        }
        if (this.mAutoSaveKeyboardHeight) {
            getKeyboardSharedPreferences().edit().putInt("height", this.mKeyboardHeight).commit();
            return;
        }
        KeyboardProcessor keyboardProcessor = this.mKeyboardProcessor;
        if (keyboardProcessor == null) {
            return;
        }
        keyboardProcessor.onSaveKeyboardHeight(this.mKeyboardHeight);
    }

    private void updateLayout() {
        if (this.mInputPane == null) {
            return;
        }
        if (this.mKeyboardHeight == 0) {
            this.mKeyboardHeight = getKeyboardHeight(this.mDefaultKeyboardHeight);
        }
        ViewGroup.LayoutParams layoutParams = this.mInputPane.getLayoutParams();
        if (layoutParams == null) {
            return;
        }
        if (this.noSaveHeight) {
            layoutParams.height = getKeyboardHeight(this.mDefaultKeyboardHeight);
        } else {
            layoutParams.height = this.mKeyboardHeight;
        }
        this.mInputPane.setLayoutParams(layoutParams);
    }

    private int getKeyboardHeight(int i) {
        if (this.mAutoSaveKeyboardHeight) {
            return getKeyboardSharedPreferences().getInt("height", i);
        }
        KeyboardProcessor keyboardProcessor = this.mKeyboardProcessor;
        return keyboardProcessor != null ? keyboardProcessor.getSavedKeyboardHeight(i) : i;
    }

    public void setDefaultKeyboardHeight(int i) {
        if (this.mDefaultKeyboardHeight != i) {
            this.mDefaultKeyboardHeight = i;
        }
    }

    public void setMinKeyboardHeight(int i) {
        if (this.mMinKeyboardHeight != i) {
            this.mMinKeyboardHeight = i;
        }
    }

    public void setInputView(View view) {
        if (this.mInputView != view) {
            this.mInputView = view;
        }
    }

    public void setInputPane(View view) {
        if (this.mInputPane != view) {
            this.mInputPane = view;
        }
    }

    public void setOnVisibilityChangeListener(OnVisibilityChangeListener onVisibilityChangeListener) {
        this.mListener = onVisibilityChangeListener;
    }

    public void setOnKeyboardChangeListener(OnKeyboardChangeListener onKeyboardChangeListener) {
        this.keyboardChangeListener = onKeyboardChangeListener;
    }

    public void setAutoSaveKeyboardHeight(boolean z) {
        this.mAutoSaveKeyboardHeight = z;
    }

    public void setKeyboardProcessor(KeyboardProcessor keyboardProcessor) {
        this.mKeyboardProcessor = keyboardProcessor;
    }

    public boolean isKeyBoardOpen() {
        return this.mKeyboardOpen;
    }

    public boolean isInputPaneOpen() {
        View view = this.mInputPane;
        return view != null && view.getVisibility() == 0;
    }

    public void closeInputPane() {
        if (isInputPaneOpen()) {
            this.mInputPane.setVisibility(8);
            OnVisibilityChangeListener onVisibilityChangeListener = this.mListener;
            if (onVisibilityChangeListener == null) {
                return;
            }
            onVisibilityChangeListener.onVisibilityChange(8);
        }
    }

    public void showInputPane(boolean z) {
        if (isKeyBoardOpen()) {
            this.tShowInputPane = true;
            ((InputMethodManager) getContext().getSystemService("input_method")).hideSoftInputFromWindow(getWindowToken(), 2);
        } else {
            View view = this.mInputPane;
            if (view != null && view.getVisibility() == 8) {
                updateLayout();
                this.mInputPane.setVisibility(0);
                OnVisibilityChangeListener onVisibilityChangeListener = this.mListener;
                if (onVisibilityChangeListener != null) {
                    onVisibilityChangeListener.onVisibilityChange(0);
                }
            }
        }
        if (z) {
            View view2 = this.mInputView;
            if (view2 == null) {
                return;
            }
            view2.requestFocus();
            this.mInputView.requestFocusFromTouch();
        } else if (this.mInputView == null) {
        } else {
            setFocusable(true);
            setFocusableInTouchMode(true);
            this.mInputView.clearFocus();
        }
    }

    public void closeKeyboard(boolean z) {
        ((InputMethodManager) getContext().getSystemService("input_method")).hideSoftInputFromWindow(getWindowToken(), 2);
        if (!z || this.mInputView == null) {
            return;
        }
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.mInputView.clearFocus();
    }

    public void showKeyboard() {
        View view = this.mInputView;
        if (view == null) {
            return;
        }
        view.requestFocus();
        this.mInputView.requestFocusFromTouch();
        ((InputMethodManager) getContext().getSystemService("input_method")).showSoftInput(this.mInputView, 1);
    }

    public void setOutSideView(View view) {
        this.outSideView = view;
    }

    public void settShowInputPane(boolean z) {
        this.tShowInputPane = z;
    }

    public void setNoSaveHeight(boolean z) {
        this.noSaveHeight = z;
    }

    public static int getVirtualBarHeight(Context context) {
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        try {
            Class.forName("android.view.Display").getMethod("getRealMetrics", DisplayMetrics.class).invoke(defaultDisplay, displayMetrics);
            return displayMetrics.heightPixels - defaultDisplay.getHeight();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
