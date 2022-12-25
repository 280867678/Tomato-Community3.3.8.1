package com.one.tomato.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.DynamicLayout;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$styleable;
import com.tomatolive.library.utils.ConstantUtils;
import java.lang.reflect.Field;

/* loaded from: classes3.dex */
public class ExpandableTextView extends TextView {
    private String mEllipsisHint;
    private ExpandableClickListener mExpandableClickListener;
    private Layout mLayout;
    private OnExpandListener mOnExpandListener;
    private CharSequence mOrigText;
    private TextPaint mTextPaint;
    private String mToExpandHint;
    private String mToShrinkHint;
    private TouchableSpan mTouchableSpan;
    private String mGapToExpandHint = ConstantUtils.PLACEHOLDER_STR_ONE;
    private String mGapToShrinkHint = ConstantUtils.PLACEHOLDER_STR_ONE;
    private boolean mToggleEnable = true;
    private boolean mShowToExpandHint = true;
    private boolean mShowToShrinkHint = true;
    private int mMaxLinesOnShrink = 2;
    private int mToExpandHintColor = -13330213;
    private int mToShrinkHintColor = -1618884;
    private int mToExpandHintColorBgPressed = 1436129689;
    private int mToShrinkHintColorBgPressed = 1436129689;
    private int mCurrState = 0;
    private TextView.BufferType mBufferType = TextView.BufferType.NORMAL;
    private int mTextLineCount = -1;
    private int mLayoutWidth = 0;
    private int mFutureTextViewWidth = 0;

    /* loaded from: classes3.dex */
    public interface OnExpandListener {
        void onExpand(ExpandableTextView expandableTextView);

        void onShrink(ExpandableTextView expandableTextView);
    }

    private String getContentOfString(String str) {
        return str == null ? "" : str;
    }

    public ExpandableTextView(Context context) {
        super(context);
        init();
    }

    public ExpandableTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initAttr(context, attributeSet);
        init();
    }

    public ExpandableTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initAttr(context, attributeSet);
        init();
    }

    private void initAttr(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes;
        if (attributeSet == null || (obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.ExpandableTextView)) == null) {
            return;
        }
        int indexCount = obtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = obtainStyledAttributes.getIndex(i);
            if (index == 5) {
                this.mMaxLinesOnShrink = obtainStyledAttributes.getInteger(index, 2);
            } else if (index == 0) {
                this.mEllipsisHint = obtainStyledAttributes.getString(index);
            } else if (index == 6) {
                this.mToExpandHint = obtainStyledAttributes.getString(index);
            } else if (index == 10) {
                this.mToShrinkHint = obtainStyledAttributes.getString(index);
            } else if (index == 1) {
                this.mToggleEnable = obtainStyledAttributes.getBoolean(index, true);
            } else if (index == 9) {
                this.mShowToExpandHint = obtainStyledAttributes.getBoolean(index, true);
            } else if (index == 13) {
                this.mShowToShrinkHint = obtainStyledAttributes.getBoolean(index, true);
            } else if (index == 7) {
                this.mToExpandHintColor = obtainStyledAttributes.getInteger(index, -13330213);
            } else if (index == 11) {
                this.mToShrinkHintColor = obtainStyledAttributes.getInteger(index, -1618884);
            } else if (index == 8) {
                this.mToExpandHintColorBgPressed = obtainStyledAttributes.getInteger(index, 1436129689);
            } else if (index == 12) {
                this.mToShrinkHintColorBgPressed = obtainStyledAttributes.getInteger(index, 1436129689);
            } else if (index == 4) {
                this.mCurrState = obtainStyledAttributes.getInteger(index, 0);
            } else if (index == 2) {
                this.mGapToExpandHint = obtainStyledAttributes.getString(index);
            } else if (index == 3) {
                this.mGapToShrinkHint = obtainStyledAttributes.getString(index);
            }
        }
        obtainStyledAttributes.recycle();
    }

    private void init() {
        this.mTouchableSpan = new TouchableSpan();
        setMovementMethod(new LinkTouchMovementMethod(this));
        if (TextUtils.isEmpty(this.mEllipsisHint)) {
            this.mEllipsisHint = "..";
        }
        if (TextUtils.isEmpty(this.mToExpandHint)) {
            this.mToExpandHint = getResources().getString(R.string.to_expand_hint);
        }
        if (TextUtils.isEmpty(this.mToShrinkHint)) {
            this.mToShrinkHint = getResources().getString(R.string.to_shrink_hint);
        }
        if (this.mToggleEnable) {
            this.mExpandableClickListener = new ExpandableClickListener();
            setOnClickListener(this.mExpandableClickListener);
        }
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.one.tomato.widget.ExpandableTextView.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                ViewTreeObserver viewTreeObserver = ExpandableTextView.this.getViewTreeObserver();
                if (Build.VERSION.SDK_INT >= 16) {
                    viewTreeObserver.removeOnGlobalLayoutListener(this);
                } else {
                    viewTreeObserver.removeGlobalOnLayoutListener(this);
                }
                ExpandableTextView expandableTextView = ExpandableTextView.this;
                expandableTextView.setTextInternal(expandableTextView.getNewTextByConfig(), ExpandableTextView.this.mBufferType);
            }
        });
    }

    public int getExpandState() {
        return this.mCurrState;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CharSequence getNewTextByConfig() {
        String str;
        int i;
        int i2;
        int i3;
        if (TextUtils.isEmpty(this.mOrigText)) {
            return this.mOrigText;
        }
        this.mLayout = getLayout();
        Layout layout = this.mLayout;
        if (layout != null) {
            this.mLayoutWidth = layout.getWidth();
        }
        if (this.mLayoutWidth <= 0) {
            if (getWidth() == 0) {
                int i4 = this.mFutureTextViewWidth;
                if (i4 == 0) {
                    return this.mOrigText;
                }
                this.mLayoutWidth = (i4 - getPaddingLeft()) - getPaddingRight();
            } else {
                this.mLayoutWidth = (getWidth() - getPaddingLeft()) - getPaddingRight();
            }
        }
        this.mTextPaint = getPaint();
        this.mTextLineCount = -1;
        int i5 = this.mCurrState;
        if (i5 != 0) {
            if (i5 == 1) {
                if (!this.mShowToShrinkHint) {
                    return this.mOrigText;
                }
                this.mLayout = new DynamicLayout(this.mOrigText, this.mTextPaint, this.mLayoutWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
                this.mTextLineCount = this.mLayout.getLineCount();
                if (this.mTextLineCount <= this.mMaxLinesOnShrink) {
                    return this.mOrigText;
                }
                SpannableStringBuilder append = new SpannableStringBuilder(this.mOrigText).append((CharSequence) this.mGapToShrinkHint).append((CharSequence) this.mToShrinkHint);
                append.setSpan(this.mTouchableSpan, append.length() - getLengthOfString(this.mToShrinkHint), append.length(), 33);
                return append;
            }
            return this.mOrigText;
        }
        this.mLayout = new DynamicLayout(this.mOrigText, this.mTextPaint, this.mLayoutWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        this.mTextLineCount = this.mLayout.getLineCount();
        if (this.mTextLineCount <= this.mMaxLinesOnShrink) {
            return this.mOrigText;
        }
        int lineEnd = getValidLayout().getLineEnd(this.mMaxLinesOnShrink - 1);
        int lineStart = getValidLayout().getLineStart(this.mMaxLinesOnShrink - 1);
        int lengthOfString = (lineEnd - getLengthOfString(this.mEllipsisHint)) - (this.mShowToExpandHint ? getLengthOfString(this.mToExpandHint) + getLengthOfString(this.mGapToExpandHint) : 0);
        if (lengthOfString > lineStart) {
            lineEnd = lengthOfString;
        }
        int width = getValidLayout().getWidth() - ((int) (this.mTextPaint.measureText(this.mOrigText.subSequence(lineStart, lineEnd).toString()) + 0.5d));
        TextPaint textPaint = this.mTextPaint;
        StringBuilder sb = new StringBuilder();
        sb.append(getContentOfString(this.mEllipsisHint));
        if (this.mShowToExpandHint) {
            str = getContentOfString(this.mToExpandHint) + getContentOfString(this.mGapToExpandHint);
        } else {
            str = "";
        }
        sb.append(str);
        float measureText = textPaint.measureText(sb.toString());
        float f = width;
        if (f > measureText) {
            int i6 = 0;
            int i7 = 0;
            while (f > i6 + measureText && (i3 = lineEnd + (i7 = i7 + 1)) <= this.mOrigText.length()) {
                i6 = (int) (this.mTextPaint.measureText(this.mOrigText.subSequence(lineEnd, i3).toString()) + 0.5d);
            }
            i = lineEnd + (i7 - 1);
        } else {
            int i8 = 0;
            int i9 = 0;
            while (i8 + width < measureText && (i2 = lineEnd + (i9 - 1)) > lineStart) {
                i8 = (int) (this.mTextPaint.measureText(this.mOrigText.subSequence(i2, lineEnd).toString()) + 0.5d);
            }
            i = lineEnd + i9;
        }
        SpannableStringBuilder append2 = new SpannableStringBuilder(removeEndLineBreak(this.mOrigText.subSequence(0, i))).append((CharSequence) this.mEllipsisHint);
        if (this.mShowToExpandHint) {
            append2.append((CharSequence) (getContentOfString(this.mGapToExpandHint) + getContentOfString(this.mToExpandHint)));
            append2.setSpan(this.mTouchableSpan, append2.length() - getLengthOfString(this.mToExpandHint), append2.length(), 33);
        }
        return append2;
    }

    private String removeEndLineBreak(CharSequence charSequence) {
        String charSequence2 = charSequence.toString();
        while (charSequence2.endsWith("\n")) {
            charSequence2 = charSequence2.substring(0, charSequence2.length() - 1);
        }
        return charSequence2;
    }

    public void setExpandListener(OnExpandListener onExpandListener) {
        this.mOnExpandListener = onExpandListener;
    }

    private Layout getValidLayout() {
        Layout layout = this.mLayout;
        return layout != null ? layout : getLayout();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toggle() {
        int i = this.mCurrState;
        if (i == 0) {
            this.mCurrState = 1;
            OnExpandListener onExpandListener = this.mOnExpandListener;
            if (onExpandListener != null) {
                onExpandListener.onExpand(this);
            }
        } else if (i == 1) {
            this.mCurrState = 0;
            OnExpandListener onExpandListener2 = this.mOnExpandListener;
            if (onExpandListener2 != null) {
                onExpandListener2.onShrink(this);
            }
        }
        setTextInternal(getNewTextByConfig(), this.mBufferType);
    }

    @Override // android.widget.TextView
    public void setText(CharSequence charSequence, TextView.BufferType bufferType) {
        this.mOrigText = charSequence;
        this.mBufferType = bufferType;
        setTextInternal(getNewTextByConfig(), bufferType);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTextInternal(CharSequence charSequence, TextView.BufferType bufferType) {
        super.setText(charSequence, bufferType);
    }

    private int getLengthOfString(String str) {
        if (str == null) {
            return 0;
        }
        return str.length();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class ExpandableClickListener implements View.OnClickListener {
        private ExpandableClickListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            ExpandableTextView.this.toggle();
        }
    }

    public View.OnClickListener getOnClickListener(View view) {
        if (Build.VERSION.SDK_INT >= 14) {
            return getOnClickListenerV14(view);
        }
        return getOnClickListenerV(view);
    }

    private View.OnClickListener getOnClickListenerV(View view) {
        try {
            Field declaredField = Class.forName("android.view.View").getDeclaredField("mOnClickListener");
            declaredField.setAccessible(true);
            return (View.OnClickListener) declaredField.get(view);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private View.OnClickListener getOnClickListenerV14(View view) {
        Object obj;
        try {
            Field declaredField = Class.forName("android.view.View").getDeclaredField("mListenerInfo");
            if (declaredField != null) {
                declaredField.setAccessible(true);
                obj = declaredField.get(view);
            } else {
                obj = null;
            }
            Field declaredField2 = Class.forName("android.view.View$ListenerInfo").getDeclaredField("mOnClickListener");
            if (declaredField2 == null || obj == null) {
                return null;
            }
            declaredField2.setAccessible(true);
            return (View.OnClickListener) declaredField2.get(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class TouchableSpan extends ClickableSpan {
        private boolean mIsPressed;

        private TouchableSpan() {
        }

        public void setPressed(boolean z) {
            this.mIsPressed = z;
        }

        @Override // android.text.style.ClickableSpan
        public void onClick(View view) {
            if (ExpandableTextView.this.hasOnClickListeners()) {
                ExpandableTextView expandableTextView = ExpandableTextView.this;
                if (expandableTextView.getOnClickListener(expandableTextView) instanceof ExpandableClickListener) {
                    return;
                }
            }
            ExpandableTextView.this.toggle();
        }

        @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
        public void updateDrawState(TextPaint textPaint) {
            super.updateDrawState(textPaint);
            int i = ExpandableTextView.this.mCurrState;
            if (i == 0) {
                textPaint.setColor(ExpandableTextView.this.mToExpandHintColor);
                textPaint.bgColor = this.mIsPressed ? ExpandableTextView.this.mToExpandHintColorBgPressed : 0;
            } else if (i == 1) {
                textPaint.setColor(ExpandableTextView.this.mToShrinkHintColor);
                textPaint.bgColor = this.mIsPressed ? ExpandableTextView.this.mToShrinkHintColorBgPressed : 0;
            }
            textPaint.setUnderlineText(false);
        }
    }

    /* loaded from: classes3.dex */
    public class LinkTouchMovementMethod extends LinkMovementMethod {
        private TouchableSpan mPressedSpan;

        public LinkTouchMovementMethod(ExpandableTextView expandableTextView) {
        }

        @Override // android.text.method.LinkMovementMethod, android.text.method.ScrollingMovementMethod, android.text.method.BaseMovementMethod, android.text.method.MovementMethod
        public boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent motionEvent) {
            if (motionEvent.getAction() == 0) {
                this.mPressedSpan = getPressedSpan(textView, spannable, motionEvent);
                TouchableSpan touchableSpan = this.mPressedSpan;
                if (touchableSpan != null) {
                    touchableSpan.setPressed(true);
                    Selection.setSelection(spannable, spannable.getSpanStart(this.mPressedSpan), spannable.getSpanEnd(this.mPressedSpan));
                }
            } else if (motionEvent.getAction() == 2) {
                TouchableSpan pressedSpan = getPressedSpan(textView, spannable, motionEvent);
                TouchableSpan touchableSpan2 = this.mPressedSpan;
                if (touchableSpan2 != null && pressedSpan != touchableSpan2) {
                    touchableSpan2.setPressed(false);
                    this.mPressedSpan = null;
                    Selection.removeSelection(spannable);
                }
            } else {
                TouchableSpan touchableSpan3 = this.mPressedSpan;
                if (touchableSpan3 != null) {
                    touchableSpan3.setPressed(false);
                    super.onTouchEvent(textView, spannable, motionEvent);
                }
                this.mPressedSpan = null;
                Selection.removeSelection(spannable);
            }
            return true;
        }

        private TouchableSpan getPressedSpan(TextView textView, Spannable spannable, MotionEvent motionEvent) {
            int x = ((int) motionEvent.getX()) - textView.getTotalPaddingLeft();
            int y = ((int) motionEvent.getY()) - textView.getTotalPaddingTop();
            int scrollX = x + textView.getScrollX();
            Layout layout = textView.getLayout();
            int offsetForHorizontal = layout.getOffsetForHorizontal(layout.getLineForVertical(y + textView.getScrollY()), scrollX);
            TouchableSpan[] touchableSpanArr = (TouchableSpan[]) spannable.getSpans(offsetForHorizontal, offsetForHorizontal, TouchableSpan.class);
            if (touchableSpanArr.length > 0) {
                return touchableSpanArr[0];
            }
            return null;
        }
    }

    public int getmMaxLinesOnShrink() {
        return this.mMaxLinesOnShrink;
    }

    public int getmTextLineCount() {
        return this.mTextLineCount;
    }
}
