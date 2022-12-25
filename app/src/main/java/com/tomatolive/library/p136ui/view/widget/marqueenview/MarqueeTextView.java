package com.tomatolive.library.p136ui.view.widget.marqueenview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.p005v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;
import com.tomatolive.library.R$styleable;

/* renamed from: com.tomatolive.library.ui.view.widget.marqueenview.MarqueeTextView */
/* loaded from: classes4.dex */
public class MarqueeTextView extends AppCompatTextView {
    private static final int FIRST_SCROLL_DELAY_DEFAULT = 1000;
    private static final int ONE = 1;
    private static final int ROLLING_INTERVAL_DEFAULT = 3000;
    public static final int SCROLL_FOREVER = 100;
    public static final int SCROLL_ONCE = 101;
    private static final int TWO = 2;
    private int distance;
    private int duration;
    private int mFirstScrollDelay;
    private boolean mPaused;
    private int mRollingInterval;
    private int mScrollMode;
    private Scroller mScroller;
    private int mXPaused;
    private int status;

    public MarqueeTextView(Context context) {
        this(context, null);
    }

    public MarqueeTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MarqueeTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mXPaused = 0;
        this.mPaused = true;
        this.status = 0;
        initView(context, attributeSet, i);
    }

    private void initView(Context context, AttributeSet attributeSet, int i) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.MarqueeTextView);
        this.mRollingInterval = obtainStyledAttributes.getInt(R$styleable.MarqueeTextView_scroll_interval, 3000);
        this.mScrollMode = obtainStyledAttributes.getInt(R$styleable.MarqueeTextView_scroll_mode, 100);
        this.mFirstScrollDelay = obtainStyledAttributes.getInt(R$styleable.MarqueeTextView_scroll_first_delay, 1000);
        obtainStyledAttributes.recycle();
        setSingleLine();
        setEllipsize(null);
    }

    public void startScroll() {
        this.mPaused = true;
        int calculateScrollingLen = calculateScrollingLen();
        if (calculateScrollingLen < (getWidth() - getPaddingLeft()) - getPaddingRight()) {
            Scroller scroller = this.mScroller;
            if (scroller != null) {
                scroller.startScroll(0, 0, (int) ((calculateScrollingLen / 2.0f) - (((getWidth() - getPaddingLeft()) - getPaddingRight()) / 2.0f)), 0, 0);
                return;
            } else {
                setGravity(17);
                return;
            }
        }
        setGravity(8388659);
        this.mXPaused = 0;
        this.status = 1;
        resumeScroll();
    }

    public void resumeScroll() {
        if (!this.mPaused) {
            return;
        }
        setHorizontallyScrolling(true);
        if (this.mScroller == null) {
            this.mScroller = new Scroller(getContext(), new LinearInterpolator());
            setScroller(this.mScroller);
        }
        int calculateScrollingLen = calculateScrollingLen();
        int i = this.status;
        if (i == 1) {
            this.distance = calculateScrollingLen - this.mXPaused;
            this.status = 2;
        } else if (i == 2) {
            this.distance = getWidth();
            this.mXPaused = getWidth() * (-1);
            this.status = 1;
        }
        this.duration = Double.valueOf(((this.mRollingInterval * this.distance) * 1.0d) / calculateScrollingLen).intValue();
        this.mScroller.startScroll(this.mXPaused, 0, this.distance, 0, this.duration);
        invalidate();
        this.mPaused = false;
    }

    public void pauseScroll() {
        Scroller scroller = this.mScroller;
        if (scroller != null && !this.mPaused) {
            this.mPaused = true;
            this.mXPaused = scroller.getCurrX();
            this.mScroller.abortAnimation();
        }
    }

    public void stopScroll() {
        Scroller scroller = this.mScroller;
        if (scroller == null) {
            return;
        }
        this.mPaused = true;
        scroller.startScroll(0, 0, 0, 0, 0);
    }

    private int calculateScrollingLen() {
        TextPaint paint = getPaint();
        Rect rect = new Rect();
        String charSequence = getText().toString();
        paint.getTextBounds(charSequence, 0, charSequence.length(), rect);
        return rect.width();
    }

    @Override // android.widget.TextView, android.view.View
    public void computeScroll() {
        super.computeScroll();
        Scroller scroller = this.mScroller;
        if (scroller != null && scroller.isFinished() && !this.mPaused) {
            if (this.mScrollMode == 101) {
                stopScroll();
                return;
            }
            this.mPaused = true;
            if (this.status == 1) {
                this.mXPaused = 0;
                postDelayed(new Runnable() { // from class: com.tomatolive.library.ui.view.widget.marqueenview.MarqueeTextView.1
                    @Override // java.lang.Runnable
                    public void run() {
                        MarqueeTextView.this.resumeScroll();
                    }
                }, 2000L);
                return;
            }
            resumeScroll();
        }
    }

    public int getRndDuration() {
        return this.mRollingInterval;
    }

    public void setRndDuration(int i) {
        this.mRollingInterval = i;
    }

    public void setScrollMode(int i) {
        this.mScrollMode = i;
    }

    public int getScrollMode() {
        return this.mScrollMode;
    }

    public void setScrollFirstDelay(int i) {
        this.mFirstScrollDelay = i;
    }

    public int getScrollFirstDelay() {
        return this.mFirstScrollDelay;
    }

    public boolean isPaused() {
        return this.mPaused;
    }

    public void startWithText(final String str) {
        post(new Runnable() { // from class: com.tomatolive.library.ui.view.widget.marqueenview.MarqueeTextView.2
            @Override // java.lang.Runnable
            public void run() {
                MarqueeTextView.this.setText(str);
                MarqueeTextView.this.startScroll();
            }
        });
    }
}
