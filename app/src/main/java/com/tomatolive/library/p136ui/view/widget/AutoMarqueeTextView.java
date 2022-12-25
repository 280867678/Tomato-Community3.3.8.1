package com.tomatolive.library.p136ui.view.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.p005v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;

/* renamed from: com.tomatolive.library.ui.view.widget.AutoMarqueeTextView */
/* loaded from: classes4.dex */
public class AutoMarqueeTextView extends AppCompatTextView {
    @Override // android.view.View
    public boolean isFocused() {
        return true;
    }

    public AutoMarqueeTextView(Context context) {
        super(context);
        initData();
    }

    public AutoMarqueeTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initData();
    }

    public AutoMarqueeTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initData();
    }

    private void initData() {
        setFocusable(true);
        setFocusableInTouchMode(true);
        setSingleLine();
        setEllipsize(TextUtils.TruncateAt.MARQUEE);
        setMarqueeRepeatLimit(-1);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onFocusChanged(boolean z, int i, Rect rect) {
        if (z) {
            super.onFocusChanged(z, i, rect);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void onWindowFocusChanged(boolean z) {
        if (z) {
            super.onWindowFocusChanged(z);
        }
    }
}
