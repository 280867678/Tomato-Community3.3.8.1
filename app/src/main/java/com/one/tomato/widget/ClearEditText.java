package com.one.tomato.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import com.broccoli.p150bh.R;

/* loaded from: classes3.dex */
public class ClearEditText extends EditText implements View.OnFocusChangeListener, TextWatcher {
    private Drawable mClearDrawable;
    private OnTextClearListener onTextClearListener;

    /* loaded from: classes3.dex */
    public interface OnTextClearListener {
        void onTextClear();
    }

    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842862);
    }

    public ClearEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initialize();
    }

    private void initialize() {
        this.mClearDrawable = getCompoundDrawables()[2];
        if (this.mClearDrawable == null) {
            this.mClearDrawable = getResources().getDrawable(R.drawable.icon_clear_edittext);
        }
        Drawable drawable = this.mClearDrawable;
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), this.mClearDrawable.getMinimumHeight());
        setClearIconVisible(false);
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    @Override // android.widget.TextView, android.view.View
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        setClearIconVisible(z);
    }

    private void setClearIconVisible(boolean z) {
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], z ? this.mClearDrawable : null, getCompoundDrawables()[3]);
        setCompoundDrawablePadding(20);
    }

    @Override // android.widget.TextView, android.text.TextWatcher
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        int length = charSequence.length();
        if (isEnabled()) {
            setClearIconVisible(length > 0);
        }
    }

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable editable) {
        OnTextClearListener onTextClearListener;
        if (editable.toString().trim().length() != 0 || (onTextClearListener = this.onTextClearListener) == null) {
            return;
        }
        onTextClearListener.onTextClear();
    }

    @Override // android.view.View.OnFocusChangeListener
    public void onFocusChange(View view, boolean z) {
        boolean z2 = false;
        if (z) {
            int length = getText().toString().trim().length();
            if (!isEnabled()) {
                return;
            }
            if (length > 0) {
                z2 = true;
            }
            setClearIconVisible(z2);
            return;
        }
        setClearIconVisible(false);
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (getCompoundDrawables()[2] != null) {
            boolean z = true;
            if (motionEvent.getAction() == 1) {
                if (motionEvent.getX() <= (getWidth() - getPaddingRight()) - this.mClearDrawable.getMinimumWidth() || motionEvent.getX() >= getWidth() - getPaddingRight()) {
                    z = false;
                }
                if (z) {
                    setText("");
                }
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setOnTextClearListener(OnTextClearListener onTextClearListener) {
        this.onTextClearListener = onTextClearListener;
    }
}
