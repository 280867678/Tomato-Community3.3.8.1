package com.tomatolive.library.p136ui.view.widget.titlebar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.p005v7.widget.AppCompatCheckedTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$styleable;

/* renamed from: com.tomatolive.library.ui.view.widget.titlebar.BGATitleBar */
/* loaded from: classes4.dex */
public class BGATitleBar extends RelativeLayout {
    private Delegate mDelegate;
    private AppCompatCheckedTextView mLeftCtv;
    private AppCompatCheckedTextView mRightCtv;
    private AppCompatCheckedTextView mRightSecondaryCtv;
    private AppCompatCheckedTextView mTitleCtv;

    /* renamed from: com.tomatolive.library.ui.view.widget.titlebar.BGATitleBar$Delegate */
    /* loaded from: classes4.dex */
    public interface Delegate {
        void onClickLeftCtv();

        void onClickRightCtv();

        void onClickRightSecondaryCtv();

        void onClickTitleCtv();
    }

    /* renamed from: com.tomatolive.library.ui.view.widget.titlebar.BGATitleBar$SimpleDelegate */
    /* loaded from: classes4.dex */
    public static class SimpleDelegate implements Delegate {
        @Override // com.tomatolive.library.p136ui.view.widget.titlebar.BGATitleBar.Delegate
        public void onClickLeftCtv() {
        }

        @Override // com.tomatolive.library.p136ui.view.widget.titlebar.BGATitleBar.Delegate
        public void onClickRightCtv() {
        }

        @Override // com.tomatolive.library.p136ui.view.widget.titlebar.BGATitleBar.Delegate
        public void onClickRightSecondaryCtv() {
        }

        @Override // com.tomatolive.library.p136ui.view.widget.titlebar.BGATitleBar.Delegate
        public void onClickTitleCtv() {
        }
    }

    public BGATitleBar(Context context) {
        this(context, null);
    }

    public BGATitleBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BGATitleBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        View.inflate(context, R$layout.view_bgatitlebar, this);
        initView();
        setListener();
        initAttrs(context, attributeSet);
    }

    private void initAttrs(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.BGATitleBar);
        int indexCount = obtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            initAttr(obtainStyledAttributes.getIndex(i), obtainStyledAttributes);
        }
        obtainStyledAttributes.recycle();
    }

    protected void initView() {
        this.mLeftCtv = (AppCompatCheckedTextView) getViewById(R$id.ctv_bgatitlebar_left);
        this.mRightCtv = (AppCompatCheckedTextView) getViewById(R$id.ctv_bgatitlebar_right);
        this.mRightSecondaryCtv = (AppCompatCheckedTextView) getViewById(R$id.ctv_bgatitlebar_right_secondary);
        this.mTitleCtv = (AppCompatCheckedTextView) getViewById(R$id.ctv_bgatitlebar_title);
    }

    protected void setListener() {
        this.mLeftCtv.setOnClickListener(new BGAOnNoDoubleClickListener() { // from class: com.tomatolive.library.ui.view.widget.titlebar.BGATitleBar.1
            @Override // com.tomatolive.library.p136ui.view.widget.titlebar.BGAOnNoDoubleClickListener
            public void onNoDoubleClick(View view) {
                if (BGATitleBar.this.mDelegate != null) {
                    BGATitleBar.this.mDelegate.onClickLeftCtv();
                }
            }
        });
        this.mTitleCtv.setOnClickListener(new BGAOnNoDoubleClickListener() { // from class: com.tomatolive.library.ui.view.widget.titlebar.BGATitleBar.2
            @Override // com.tomatolive.library.p136ui.view.widget.titlebar.BGAOnNoDoubleClickListener
            public void onNoDoubleClick(View view) {
                if (BGATitleBar.this.mDelegate != null) {
                    BGATitleBar.this.mDelegate.onClickTitleCtv();
                }
            }
        });
        this.mRightCtv.setOnClickListener(new BGAOnNoDoubleClickListener() { // from class: com.tomatolive.library.ui.view.widget.titlebar.BGATitleBar.3
            @Override // com.tomatolive.library.p136ui.view.widget.titlebar.BGAOnNoDoubleClickListener
            public void onNoDoubleClick(View view) {
                if (BGATitleBar.this.mDelegate != null) {
                    BGATitleBar.this.mDelegate.onClickRightCtv();
                }
            }
        });
        this.mRightSecondaryCtv.setOnClickListener(new BGAOnNoDoubleClickListener() { // from class: com.tomatolive.library.ui.view.widget.titlebar.BGATitleBar.4
            @Override // com.tomatolive.library.p136ui.view.widget.titlebar.BGAOnNoDoubleClickListener
            public void onNoDoubleClick(View view) {
                if (BGATitleBar.this.mDelegate != null) {
                    BGATitleBar.this.mDelegate.onClickRightSecondaryCtv();
                }
            }
        });
    }

    protected void initAttr(int i, TypedArray typedArray) {
        if (i == R$styleable.BGATitleBar_bgatitlebar_leftText) {
            setLeftText(typedArray.getText(i));
        } else if (i == R$styleable.BGATitleBar_bgatitlebar_titleText) {
            setTitleText(typedArray.getText(i));
        } else if (i == R$styleable.BGATitleBar_bgatitlebar_rightText) {
            setRightText(typedArray.getText(i));
        } else if (i == R$styleable.BGATitleBar_bgatitlebar_rightSecondaryText) {
            setRightSecondaryText(typedArray.getText(i));
        } else if (i == R$styleable.BGATitleBar_bgatitlebar_leftDrawable) {
            setLeftDrawable(typedArray.getDrawable(i));
        } else if (i == R$styleable.BGATitleBar_bgatitlebar_titleDrawable) {
            setTitleDrawable(typedArray.getDrawable(i));
        } else if (i == R$styleable.BGATitleBar_bgatitlebar_rightDrawable) {
            setRightDrawable(typedArray.getDrawable(i));
        } else if (i == R$styleable.BGATitleBar_bgatitlebar_rightSecondaryDrawable) {
            setRightSecondaryDrawable(typedArray.getDrawable(i));
        } else if (i == R$styleable.BGATitleBar_bgatitlebar_leftAndRightTextSize) {
            float dimensionPixelSize = typedArray.getDimensionPixelSize(i, sp2px(getContext(), 12.0f));
            this.mLeftCtv.setTextSize(0, dimensionPixelSize);
            this.mRightCtv.setTextSize(0, dimensionPixelSize);
            this.mRightSecondaryCtv.setTextSize(0, dimensionPixelSize);
        } else if (i == R$styleable.BGATitleBar_bgatitlebar_titleTextSize) {
            this.mTitleCtv.setTextSize(0, typedArray.getDimensionPixelSize(i, sp2px(getContext(), 16.0f)));
        } else if (i == R$styleable.BGATitleBar_bgatitlebar_leftAndRightTextColor) {
            this.mLeftCtv.setTextColor(typedArray.getColorStateList(i));
            this.mRightCtv.setTextColor(typedArray.getColorStateList(i));
            this.mRightSecondaryCtv.setTextColor(typedArray.getColorStateList(i));
        } else if (i == R$styleable.BGATitleBar_bgatitlebar_titleTextColor) {
            this.mTitleCtv.setTextColor(typedArray.getColorStateList(i));
        } else if (i == R$styleable.BGATitleBar_bgatitlebar_titleDrawablePadding) {
            this.mTitleCtv.setCompoundDrawablePadding(typedArray.getDimensionPixelSize(i, dp2px(getContext(), 3.0f)));
        } else if (i == R$styleable.BGATitleBar_bgatitlebar_leftDrawablePadding) {
            this.mLeftCtv.setCompoundDrawablePadding(typedArray.getDimensionPixelSize(i, dp2px(getContext(), 3.0f)));
        } else if (i == R$styleable.BGATitleBar_bgatitlebar_rightDrawablePadding) {
            this.mRightCtv.setCompoundDrawablePadding(typedArray.getDimensionPixelSize(i, dp2px(getContext(), 3.0f)));
            this.mRightSecondaryCtv.setCompoundDrawablePadding(typedArray.getDimensionPixelSize(i, dp2px(getContext(), 3.0f)));
        } else if (i == R$styleable.BGATitleBar_bgatitlebar_leftAndRightPadding) {
            int dimensionPixelSize2 = typedArray.getDimensionPixelSize(i, dp2px(getContext(), 10.0f));
            this.mLeftCtv.setPadding(dimensionPixelSize2, 0, dimensionPixelSize2, 0);
            int i2 = dimensionPixelSize2 / 2;
            this.mRightCtv.setPadding(i2, 0, dimensionPixelSize2, 0);
            this.mRightSecondaryCtv.setPadding(i2, 0, i2, 0);
        } else if (i == R$styleable.BGATitleBar_bgatitlebar_leftMaxWidth) {
            setLeftCtvMaxWidth(typedArray.getDimensionPixelSize(i, dp2px(getContext(), 85.0f)));
        } else if (i == R$styleable.BGATitleBar_bgatitlebar_rightMaxWidth) {
            setRightCtvMaxWidth(typedArray.getDimensionPixelSize(i, dp2px(getContext(), 85.0f)));
        } else if (i == R$styleable.BGATitleBar_bgatitlebar_titleMaxWidth) {
            setTitleCtvMaxWidth(typedArray.getDimensionPixelSize(i, dp2px(getContext(), 144.0f)));
        } else if (i == R$styleable.BGATitleBar_bgatitlebar_isTitleTextBold) {
            this.mTitleCtv.getPaint().setTypeface(getTypeface(typedArray.getBoolean(i, true)));
        } else if (i == R$styleable.BGATitleBar_bgatitlebar_isLeftTextBold) {
            this.mLeftCtv.getPaint().setTypeface(getTypeface(typedArray.getBoolean(i, false)));
        } else if (i != R$styleable.BGATitleBar_bgatitlebar_isRightTextBold) {
        } else {
            this.mRightCtv.getPaint().setTypeface(getTypeface(typedArray.getBoolean(i, false)));
            this.mRightSecondaryCtv.getPaint().setTypeface(getTypeface(typedArray.getBoolean(i, false)));
        }
    }

    private Typeface getTypeface(boolean z) {
        return z ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT;
    }

    public BGATitleBar setLeftCtvMaxWidth(int i) {
        this.mLeftCtv.setMaxWidth(i);
        return this;
    }

    public BGATitleBar setRightCtvMaxWidth(int i) {
        this.mRightCtv.setMaxWidth(i);
        this.mRightSecondaryCtv.setMaxWidth(i);
        return this;
    }

    public BGATitleBar setTitleCtvMaxWidth(int i) {
        this.mTitleCtv.setMaxWidth(i);
        return this;
    }

    public BGATitleBar hiddenLeftCtv() {
        this.mLeftCtv.setVisibility(8);
        return this;
    }

    public BGATitleBar showLeftCtv() {
        this.mLeftCtv.setVisibility(0);
        return this;
    }

    public BGATitleBar setLeftText(@StringRes int i) {
        setLeftText(getResources().getString(i));
        return this;
    }

    public BGATitleBar setLeftText(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            this.mLeftCtv.setText("");
            if (this.mLeftCtv.getCompoundDrawables()[0] == null) {
                hiddenLeftCtv();
            }
        } else {
            this.mLeftCtv.setText(charSequence);
            showLeftCtv();
        }
        return this;
    }

    public BGATitleBar setLeftDrawable(@DrawableRes int i) {
        setLeftDrawable(getResources().getDrawable(i));
        return this;
    }

    public BGATitleBar setLeftDrawable(Drawable drawable) {
        if (drawable == null) {
            this.mLeftCtv.setCompoundDrawables(null, null, null, null);
            if (TextUtils.isEmpty(this.mLeftCtv.getText())) {
                hiddenLeftCtv();
            }
        } else {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            this.mLeftCtv.setCompoundDrawables(drawable, null, null, null);
            showLeftCtv();
        }
        return this;
    }

    public BGATitleBar hiddenTitleCtv() {
        this.mTitleCtv.setVisibility(8);
        return this;
    }

    public BGATitleBar showTitleCtv() {
        this.mTitleCtv.setVisibility(0);
        return this;
    }

    public BGATitleBar setTitleText(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            this.mTitleCtv.setText("");
            if (this.mTitleCtv.getCompoundDrawables()[2] == null) {
                hiddenTitleCtv();
            }
        } else {
            this.mTitleCtv.setText(charSequence);
            showTitleCtv();
        }
        return this;
    }

    public BGATitleBar setTitleText(@StringRes int i) {
        setTitleText(getResources().getString(i));
        return this;
    }

    public BGATitleBar setTitleDrawable(@DrawableRes int i) {
        setTitleDrawable(getResources().getDrawable(i));
        return this;
    }

    public BGATitleBar setTitleDrawable(Drawable drawable) {
        if (drawable == null) {
            this.mTitleCtv.setCompoundDrawables(null, null, null, null);
            if (TextUtils.isEmpty(this.mTitleCtv.getText())) {
                hiddenTitleCtv();
            }
        } else {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            this.mTitleCtv.setCompoundDrawables(null, null, drawable, null);
            showTitleCtv();
        }
        return this;
    }

    public BGATitleBar hiddenRightSecondaryCtv() {
        this.mRightSecondaryCtv.setVisibility(8);
        return this;
    }

    public BGATitleBar showRightSecondaryCtv() {
        this.mRightSecondaryCtv.setVisibility(0);
        return this;
    }

    public BGATitleBar setRightSecondaryText(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            this.mRightSecondaryCtv.setText("");
            if (this.mRightSecondaryCtv.getCompoundDrawables()[2] == null) {
                hiddenRightSecondaryCtv();
            }
        } else {
            this.mRightSecondaryCtv.setText(charSequence);
            showRightSecondaryCtv();
        }
        return this;
    }

    public BGATitleBar setRightSecondaryText(@StringRes int i) {
        setRightSecondaryText(getResources().getString(i));
        return this;
    }

    public BGATitleBar setRightSecondaryDrawable(@DrawableRes int i) {
        setRightSecondaryDrawable(getResources().getDrawable(i));
        return this;
    }

    public BGATitleBar setRightSecondaryDrawable(Drawable drawable) {
        if (drawable == null) {
            this.mRightSecondaryCtv.setCompoundDrawables(null, null, null, null);
            if (TextUtils.isEmpty(this.mRightSecondaryCtv.getText())) {
                hiddenRightSecondaryCtv();
            }
        } else {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            this.mRightSecondaryCtv.setCompoundDrawables(null, null, drawable, null);
            showRightSecondaryCtv();
        }
        return this;
    }

    public BGATitleBar hiddenRightCtv() {
        this.mRightCtv.setVisibility(8);
        return this;
    }

    public BGATitleBar showRightCtv() {
        this.mRightCtv.setVisibility(0);
        return this;
    }

    public BGATitleBar setRightText(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            this.mRightCtv.setText("");
            if (this.mRightCtv.getCompoundDrawables()[2] == null) {
                hiddenRightCtv();
            }
        } else {
            this.mRightCtv.setText(charSequence);
            showRightCtv();
        }
        return this;
    }

    public BGATitleBar setRightText(@StringRes int i) {
        setRightText(getResources().getString(i));
        return this;
    }

    public BGATitleBar setRightDrawable(@DrawableRes int i) {
        setRightDrawable(getResources().getDrawable(i));
        return this;
    }

    public BGATitleBar setRightDrawable(Drawable drawable) {
        if (drawable == null) {
            this.mRightCtv.setCompoundDrawables(null, null, null, null);
            if (TextUtils.isEmpty(this.mRightCtv.getText())) {
                hiddenRightCtv();
            }
        } else {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            this.mRightCtv.setCompoundDrawables(null, null, drawable, null);
            showRightCtv();
        }
        return this;
    }

    public BGATitleBar setLeftCtvChecked(boolean z) {
        this.mLeftCtv.setChecked(z);
        return this;
    }

    public BGATitleBar setTitleCtvChecked(boolean z) {
        this.mTitleCtv.setChecked(z);
        return this;
    }

    public BGATitleBar setRightCtvChecked(boolean z) {
        this.mRightCtv.setChecked(z);
        return this;
    }

    public BGATitleBar setRightSecondaryCtvChecked(boolean z) {
        this.mRightSecondaryCtv.setChecked(z);
        return this;
    }

    public AppCompatCheckedTextView getLeftCtv() {
        return this.mLeftCtv;
    }

    public AppCompatCheckedTextView getRightCtv() {
        return this.mRightCtv;
    }

    public AppCompatCheckedTextView getRightSecondaryCtv() {
        return this.mRightSecondaryCtv;
    }

    public AppCompatCheckedTextView getTitleCtv() {
        return this.mTitleCtv;
    }

    public BGATitleBar setDelegate(Delegate delegate) {
        this.mDelegate = delegate;
        return this;
    }

    protected <VT extends View> VT getViewById(@IdRes int i) {
        return (VT) findViewById(i);
    }

    public static int dp2px(Context context, float f) {
        return (int) TypedValue.applyDimension(1, f, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, float f) {
        return (int) TypedValue.applyDimension(2, f, context.getResources().getDisplayMetrics());
    }
}
