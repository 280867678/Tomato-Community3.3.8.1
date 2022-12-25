package com.scwang.smartrefresh.layout.api;

import android.support.annotation.FloatRange;
import android.view.ViewGroup;

/* loaded from: classes3.dex */
public interface RefreshLayout {
    /* renamed from: finishRefresh */
    RefreshLayout mo6481finishRefresh();

    /* renamed from: getLayout */
    ViewGroup mo6485getLayout();

    RefreshLayout resetNoMoreData();

    /* renamed from: setEnableAutoLoadMore */
    RefreshLayout mo6486setEnableAutoLoadMore(boolean z);

    /* renamed from: setEnableLoadMore */
    RefreshLayout mo6487setEnableLoadMore(boolean z);

    RefreshLayout setEnableNestedScroll(boolean z);

    /* renamed from: setHeaderMaxDragRate */
    RefreshLayout mo6488setHeaderMaxDragRate(@FloatRange(from = 1.0d, m5592to = 10.0d) float f);
}
