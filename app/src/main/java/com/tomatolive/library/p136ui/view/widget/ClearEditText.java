package com.tomatolive.library.p136ui.view.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.p002v4.content.ContextCompat;
import android.support.p002v4.graphics.drawable.DrawableCompat;
import android.support.p005v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.tomatolive.library.R$drawable;

/* renamed from: com.tomatolive.library.ui.view.widget.ClearEditText */
/* loaded from: classes4.dex */
public class ClearEditText extends AppCompatEditText implements View.OnTouchListener, View.OnFocusChangeListener, TextWatcher {
    private Drawable mClearTextIcon;
    private final Context mContext;
    private View.OnFocusChangeListener mOnFocusChangeListener;
    private View.OnTouchListener mOnTouchListener;

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable editable) {
    }

    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public ClearEditText(Context context) {
        super(context);
        this.mContext = context;
        init(context);
    }

    public ClearEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        init(context);
    }

    public ClearEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mContext = context;
        init(context);
    }

    private void init(Context context) {
        Drawable drawable = ContextCompat.getDrawable(context, R$drawable.fq_ic_et_close);
        if (drawable != null) {
            Drawable wrap = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(wrap, getCurrentHintTextColor());
            this.mClearTextIcon = wrap;
            Drawable drawable2 = this.mClearTextIcon;
            drawable2.setBounds(0, 0, drawable2.getIntrinsicHeight(), this.mClearTextIcon.getIntrinsicHeight());
        }
        setClearIconVisible(false);
        super.setOnTouchListener(this);
        super.setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    @Override // android.view.View
    public void setOnFocusChangeListener(View.OnFocusChangeListener onFocusChangeListener) {
        this.mOnFocusChangeListener = onFocusChangeListener;
    }

    @Override // android.view.View
    public void setOnTouchListener(View.OnTouchListener onTouchListener) {
        this.mOnTouchListener = onTouchListener;
    }

    @Override // android.view.View.OnFocusChangeListener
    public void onFocusChange(View view, boolean z) {
        boolean z2 = false;
        if (z) {
            if (mo5763getText().length() > 0) {
                z2 = true;
            }
            setClearIconVisible(z2);
        } else {
            hideSoftInput(this.mContext, view);
            setClearIconVisible(false);
        }
        View.OnFocusChangeListener onFocusChangeListener = this.mOnFocusChangeListener;
        if (onFocusChangeListener != null) {
            onFocusChangeListener.onFocusChange(view, z);
        }
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        if (this.mClearTextIcon.isVisible() && x > (getWidth() - getPaddingRight()) - this.mClearTextIcon.getIntrinsicWidth()) {
            if (motionEvent.getAction() == 1) {
                setError(null);
                setText("");
            }
            return true;
        }
        View.OnTouchListener onTouchListener = this.mOnTouchListener;
        return onTouchListener != null && onTouchListener.onTouch(view, motionEvent);
    }

    @Override // android.widget.TextView, android.text.TextWatcher
    public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        if (isFocused()) {
            setClearIconVisible(charSequence.length() > 0);
        }
    }

    private void setClearIconVisible(boolean z) {
        this.mClearTextIcon.setVisible(z, false);
        Drawable[] compoundDrawables = getCompoundDrawables();
        setCompoundDrawables(compoundDrawables[0], compoundDrawables[1], z ? this.mClearTextIcon : null, compoundDrawables[3]);
    }

    private void hideSoftInput(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService("input_method");
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
