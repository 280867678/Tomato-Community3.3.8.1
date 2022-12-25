package com.zzhoujay.richtext.spans;

import android.annotation.SuppressLint;
import android.text.TextPaint;
import android.text.style.URLSpan;
import android.view.View;
import com.zzhoujay.richtext.LinkHolder;
import com.zzhoujay.richtext.callback.OnUrlClickListener;
import com.zzhoujay.richtext.callback.OnUrlLongClickListener;

@SuppressLint({"ParcelCreator"})
/* loaded from: classes4.dex */
public class LongClickableURLSpan extends URLSpan implements LongClickableSpan {
    private final LinkHolder linkHolder;
    private final OnUrlClickListener onUrlClickListener;
    private final OnUrlLongClickListener onUrlLongClickListener;

    public LongClickableURLSpan(LinkHolder linkHolder, OnUrlClickListener onUrlClickListener, OnUrlLongClickListener onUrlLongClickListener) {
        super(linkHolder.getUrl());
        this.onUrlClickListener = onUrlClickListener;
        this.onUrlLongClickListener = onUrlLongClickListener;
        this.linkHolder = linkHolder;
    }

    @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
    public void updateDrawState(TextPaint textPaint) {
        textPaint.setColor(this.linkHolder.getColor());
        textPaint.setUnderlineText(this.linkHolder.isUnderLine());
    }

    @Override // android.text.style.URLSpan, android.text.style.ClickableSpan, com.zzhoujay.richtext.spans.Clickable
    public void onClick(View view) {
        OnUrlClickListener onUrlClickListener = this.onUrlClickListener;
        if (onUrlClickListener == null || !onUrlClickListener.urlClicked(getURL())) {
            super.onClick(view);
        }
    }

    @Override // com.zzhoujay.richtext.spans.LongClickable
    public boolean onLongClick(View view) {
        OnUrlLongClickListener onUrlLongClickListener = this.onUrlLongClickListener;
        return onUrlLongClickListener != null && onUrlLongClickListener.urlLongClick(getURL());
    }
}
