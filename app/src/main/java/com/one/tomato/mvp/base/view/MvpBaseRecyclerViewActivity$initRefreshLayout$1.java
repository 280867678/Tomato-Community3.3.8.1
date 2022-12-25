package com.one.tomato.mvp.base.view;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MvpBaseRecyclerViewActivity.kt */
/* loaded from: classes3.dex */
public final class MvpBaseRecyclerViewActivity$initRefreshLayout$1 implements OnRefreshLoadMoreListener {
    final /* synthetic */ MvpBaseRecyclerViewActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    public MvpBaseRecyclerViewActivity$initRefreshLayout$1(MvpBaseRecyclerViewActivity mvpBaseRecyclerViewActivity) {
        this.this$0 = mvpBaseRecyclerViewActivity;
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
    public void onLoadMore(RefreshLayout refreshLayout) {
        Intrinsics.checkParameterIsNotNull(refreshLayout, "refreshLayout");
        refreshLayout.mo6485getLayout().postDelayed(MvpBaseRecyclerViewActivity$initRefreshLayout$1$onLoadMore$1.INSTANCE, 500L);
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
    public void onRefresh(RefreshLayout refreshLayout) {
        Intrinsics.checkParameterIsNotNull(refreshLayout, "refreshLayout");
        refreshLayout.mo6485getLayout().postDelayed(new Runnable() { // from class: com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity$initRefreshLayout$1$onRefresh$1
            @Override // java.lang.Runnable
            public final void run() {
                MvpBaseRecyclerViewActivity mvpBaseRecyclerViewActivity = MvpBaseRecyclerViewActivity$initRefreshLayout$1.this.this$0;
                mvpBaseRecyclerViewActivity.setState(mvpBaseRecyclerViewActivity.getGET_LIST_REFRESH());
                MvpBaseRecyclerViewActivity$initRefreshLayout$1.this.this$0.refresh();
            }
        }, 500L);
    }
}
