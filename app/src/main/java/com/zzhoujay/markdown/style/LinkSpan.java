package com.zzhoujay.markdown.style;

import android.text.TextPaint;
import android.text.style.URLSpan;

/* loaded from: classes4.dex */
public class LinkSpan extends URLSpan {
    private int color;

    public LinkSpan(String str, int i) {
        super(str);
        this.color = i;
    }

    @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
    public void updateDrawState(TextPaint textPaint) {
        super.updateDrawState(textPaint);
        textPaint.setColor(this.color);
        textPaint.setUnderlineText(false);
    }
}
