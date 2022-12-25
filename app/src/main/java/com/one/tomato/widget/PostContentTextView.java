package com.one.tomato.widget;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

/* loaded from: classes3.dex */
public class PostContentTextView extends TextView {
    private CircleNameSpan circleNameSpan;
    private DescriptionSpan descriptionSpan;
    private Layout mLayout;
    private MoreContentSpan mMoreContentSpan;
    private PostContentClickListener postContentClickListener;
    private String mEllipsisHint = "...全文";
    private TextView.BufferType mBufferType = TextView.BufferType.NORMAL;
    private int mMaxLinesOnShrink = 6;
    private int mTextLineCount = -1;
    private CharSequence mDescription = "";
    private CharSequence mCircleName = "";
    private CharSequence completeStr = "";

    /* loaded from: classes3.dex */
    public interface PostContentClickListener {
        void onClickCircle();

        void onClickContent();

        void onClickMore();
    }

    public PostContentTextView(Context context) {
        super(context);
        init();
    }

    public PostContentTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public PostContentTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        this.mMoreContentSpan = new MoreContentSpan();
        this.descriptionSpan = new DescriptionSpan();
        this.circleNameSpan = new CircleNameSpan();
        setMovementMethod(LinkMovementMethod.getInstance());
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.one.tomato.widget.PostContentTextView.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                PostContentTextView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                PostContentTextView postContentTextView = PostContentTextView.this;
                postContentTextView.setTextInternal(postContentTextView.getNewTextByConfig(), PostContentTextView.this.mBufferType);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CharSequence getNewTextByConfig() {
        if (TextUtils.isEmpty(this.mDescription)) {
            return "";
        }
        this.mLayout = getLayout();
        if (this.mLayout == null) {
            return "";
        }
        this.mTextLineCount = -1;
        this.mTextLineCount = getLineCount();
        int i = this.mTextLineCount;
        if (i == 0) {
            return "";
        }
        if (i <= this.mMaxLinesOnShrink) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            SpannableString spannableString = new SpannableString(this.mDescription);
            spannableString.setSpan(this.descriptionSpan, 0, spannableString.length(), 33);
            SpannableString spannableString2 = new SpannableString("\t" + ((Object) this.mCircleName));
            spannableString2.setSpan(this.circleNameSpan, 0, spannableString2.length(), 33);
            spannableStringBuilder.append((CharSequence) spannableString);
            spannableStringBuilder.append((CharSequence) spannableString2);
            return spannableStringBuilder;
        }
        StringBuilder sb = new StringBuilder();
        int i2 = 0;
        int i3 = 0;
        while (i2 < this.mMaxLinesOnShrink) {
            int lineEnd = this.mLayout.getLineEnd(i2);
            if (lineEnd == 0 || i3 > lineEnd) {
                return "";
            }
            String substring = this.completeStr.toString().substring(i3, lineEnd);
            if (i2 == 0) {
                sb.append(substring.substring(this.mCircleName.length(), substring.length()));
            } else if (i2 == this.mMaxLinesOnShrink - 1) {
                sb.append(substring.substring(0, substring.length() - this.mEllipsisHint.length()));
            } else {
                sb.append(substring);
            }
            i2++;
            i3 = lineEnd;
        }
        this.mDescription = sb.toString();
        SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder();
        SpannableString spannableString3 = new SpannableString(((Object) this.mCircleName) + "\t");
        spannableString3.setSpan(this.circleNameSpan, 0, spannableString3.length(), 33);
        spannableStringBuilder2.append((CharSequence) spannableString3);
        SpannableString spannableString4 = new SpannableString(this.mDescription);
        spannableString4.setSpan(this.descriptionSpan, 0, spannableString4.length(), 33);
        spannableStringBuilder2.append((CharSequence) spannableString4);
        SpannableString spannableString5 = new SpannableString(this.mEllipsisHint);
        spannableString5.setSpan(this.mMoreContentSpan, 0, spannableString5.length(), 33);
        spannableStringBuilder2.append((CharSequence) spannableString5);
        return spannableStringBuilder2;
    }

    private Layout getValidLayout() {
        Layout layout = this.mLayout;
        return layout != null ? layout : getLayout();
    }

    public void setCircleName(String str) {
        this.mCircleName = str;
    }

    @Override // android.widget.TextView
    public void setText(CharSequence charSequence, TextView.BufferType bufferType) {
        this.mDescription = charSequence;
        this.mBufferType = bufferType;
        if (TextUtils.isEmpty(this.mCircleName)) {
            this.mCircleName = "";
        }
        if (TextUtils.isEmpty(this.mDescription)) {
            this.mDescription = "";
        }
        this.completeStr = this.mCircleName.toString() + this.mDescription.toString();
        if (TextUtils.isEmpty(this.completeStr)) {
            this.completeStr = "";
        }
        setTextInternal(getNewTextByConfig(), bufferType);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTextInternal(CharSequence charSequence, TextView.BufferType bufferType) {
        super.setText(charSequence, bufferType);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class MoreContentSpan extends ClickableSpan {
        private MoreContentSpan() {
        }

        @Override // android.text.style.ClickableSpan
        public void onClick(View view) {
            if (PostContentTextView.this.postContentClickListener != null) {
                PostContentTextView.this.postContentClickListener.onClickMore();
            }
        }

        @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
        public void updateDrawState(TextPaint textPaint) {
            super.updateDrawState(textPaint);
            textPaint.setUnderlineText(false);
            textPaint.setColor(Color.parseColor("#738AFF"));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class DescriptionSpan extends ClickableSpan {
        private DescriptionSpan() {
        }

        @Override // android.text.style.ClickableSpan
        public void onClick(View view) {
            if (PostContentTextView.this.postContentClickListener != null) {
                PostContentTextView.this.postContentClickListener.onClickContent();
            }
        }

        @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
        public void updateDrawState(TextPaint textPaint) {
            super.updateDrawState(textPaint);
            textPaint.setUnderlineText(false);
            textPaint.setColor(Color.parseColor("#121213"));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class CircleNameSpan extends ClickableSpan {
        private CircleNameSpan() {
        }

        @Override // android.text.style.ClickableSpan
        public void onClick(View view) {
            if (PostContentTextView.this.postContentClickListener != null) {
                PostContentTextView.this.postContentClickListener.onClickCircle();
            }
        }

        @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
        public void updateDrawState(TextPaint textPaint) {
            super.updateDrawState(textPaint);
            textPaint.setUnderlineText(false);
            textPaint.setColor(Color.parseColor("#4A90E2"));
        }
    }

    public void setmMaxLinesOnShrink(int i) {
        this.mMaxLinesOnShrink = i;
    }

    public void setOnPostContentClickListener(PostContentClickListener postContentClickListener) {
        this.postContentClickListener = postContentClickListener;
    }
}
