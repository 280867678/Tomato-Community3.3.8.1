package com.zzhoujay.richtext.ext;

import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.widget.TextView;
import com.zzhoujay.richtext.spans.ClickableImageSpan;
import com.zzhoujay.richtext.spans.LongClickableSpan;

/* loaded from: classes4.dex */
public class LongClickableLinkMovementMethod extends LinkMovementMethod {
    private long lastTime;

    @Override // android.text.method.LinkMovementMethod, android.text.method.ScrollingMovementMethod, android.text.method.BaseMovementMethod, android.text.method.MovementMethod
    public boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 1 || action == 0) {
            int x = ((int) motionEvent.getX()) - textView.getTotalPaddingLeft();
            int y = ((int) motionEvent.getY()) - textView.getTotalPaddingTop();
            int scrollX = x + textView.getScrollX();
            int scrollY = y + textView.getScrollY();
            Layout layout = textView.getLayout();
            int offsetForHorizontal = layout.getOffsetForHorizontal(layout.getLineForVertical(scrollY), scrollX);
            LongClickableSpan[] longClickableSpanArr = (LongClickableSpan[]) spannable.getSpans(offsetForHorizontal, offsetForHorizontal, LongClickableSpan.class);
            if (longClickableSpanArr.length != 0) {
                long currentTimeMillis = System.currentTimeMillis();
                LongClickableSpan longClickableSpan = longClickableSpanArr[0];
                int spanStart = spannable.getSpanStart(longClickableSpan);
                int spanEnd = spannable.getSpanEnd(longClickableSpan);
                ClickableImageSpan[] clickableImageSpanArr = (ClickableImageSpan[]) spannable.getSpans(spanStart, spanEnd, ClickableImageSpan.class);
                if (clickableImageSpanArr.length > 0) {
                    if (!clickableImageSpanArr[0].clicked(scrollX)) {
                        Selection.removeSelection(spannable);
                        return false;
                    }
                } else if (offsetForHorizontal < layout.getOffsetToLeftOf(spanStart) || offsetForHorizontal > layout.getOffsetToLeftOf(spanEnd + 1)) {
                    Selection.removeSelection(spannable);
                    return false;
                }
                if (action == 1) {
                    if (currentTimeMillis - this.lastTime > 500) {
                        if (!longClickableSpan.onLongClick(textView)) {
                            longClickableSpan.onClick(textView);
                        }
                    } else {
                        longClickableSpan.onClick(textView);
                    }
                } else {
                    Selection.setSelection(spannable, spanStart, spanEnd);
                }
                this.lastTime = currentTimeMillis;
                return true;
            }
            Selection.removeSelection(spannable);
            return false;
        }
        return super.onTouchEvent(textView, spannable, motionEvent);
    }
}
