package com.tomatolive.library.p136ui.view.widget.titlebar;

import android.view.View;

/* renamed from: com.tomatolive.library.ui.view.widget.titlebar.BGAOnNoDoubleClickListener */
/* loaded from: classes4.dex */
public abstract class BGAOnNoDoubleClickListener implements View.OnClickListener {
    private long mLastClickTime;
    private int mThrottleFirstTime;

    public abstract void onNoDoubleClick(View view);

    public BGAOnNoDoubleClickListener() {
        this.mThrottleFirstTime = 600;
        this.mLastClickTime = 0L;
    }

    public BGAOnNoDoubleClickListener(int i) {
        this.mThrottleFirstTime = 600;
        this.mLastClickTime = 0L;
        this.mThrottleFirstTime = i;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.mLastClickTime > this.mThrottleFirstTime) {
            this.mLastClickTime = currentTimeMillis;
            onNoDoubleClick(view);
        }
    }
}
