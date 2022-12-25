package com.scwang.smartrefresh.layout.api;

import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import com.scwang.smartrefresh.layout.constant.RefreshState;

/* loaded from: classes3.dex */
public interface RefreshKernel {
    ValueAnimator animSpinner(int i);

    RefreshKernel finishTwoLevel();

    @NonNull
    RefreshLayout getRefreshLayout();

    RefreshKernel moveSpinner(int i, boolean z);

    RefreshKernel requestDrawBackgroundFor(@NonNull RefreshInternal refreshInternal, int i);

    RefreshKernel requestFloorDuration(int i);

    RefreshKernel requestNeedTouchEventFor(@NonNull RefreshInternal refreshInternal, boolean z);

    RefreshKernel setState(@NonNull RefreshState refreshState);

    RefreshKernel startTwoLevel(boolean z);
}
