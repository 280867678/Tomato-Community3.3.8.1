package com.zzhoujay.markdown.style;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.text.TextPaint;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;

@SuppressLint({"ParcelCreator"})
/* loaded from: classes4.dex */
public class EmailSpan extends URLSpan {
    private int color;

    public EmailSpan(String str, int i) {
        super(str);
        this.color = i;
    }

    @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
    public void updateDrawState(TextPaint textPaint) {
        super.updateDrawState(textPaint);
        textPaint.setColor(this.color);
        textPaint.setUnderlineText(false);
    }

    @Override // android.text.style.URLSpan, android.text.style.ClickableSpan
    public void onClick(View view) {
        Intent intent = new Intent("android.intent.action.SENDTO");
        intent.setData(Uri.parse("mailto:" + getURL()));
        intent.putExtra("android.intent.extra.SUBJECT", "");
        intent.putExtra("android.intent.extra.TEXT", "");
        try {
            view.getContext().startActivity(intent);
        } catch (ActivityNotFoundException unused) {
            Log.w("URLSpan", "Actvity was not found for intent, " + intent.toString());
        }
    }
}
