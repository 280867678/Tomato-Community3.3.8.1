package com.one.tomato.mvp.base.view;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MvpBaseRecyclerViewFragment.kt */
/* loaded from: classes3.dex */
public final class MvpBaseRecyclerViewFragment$initRefreshLayout$1 implements OnRefreshLoadMoreListener {
    final /* synthetic */ MvpBaseRecyclerViewFragment this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    public MvpBaseRecyclerViewFragment$initRefreshLayout$1(MvpBaseRecyclerViewFragment mvpBaseRecyclerViewFragment) {
        this.this$0 = mvpBaseRecyclerViewFragment;
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
    public void onLoadMore(RefreshLayout refreshLayout) {
        Intrinsics.checkParameterIsNotNull(refreshLayout, "refreshLayout");
        refreshLayout.mo6485getLayout().postDelayed(MvpBaseRecyclerViewFragment$initRefreshLayout$1$onLoadMore$1.INSTANCE, 500L);
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
    public void onRefresh(RefreshLayout refreshLayout) {
        Intrinsics.checkParameterIsNotNull(refreshLayout, "refreshLayout");
        refreshLayout.mo6485getLayout().postDelayed(new Runnable() { // from class: com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment$initRefreshLayout$1$onRefresh$1
            @Override // java.lang.Runnable
            public final void run() {
                MvpBaseRecyclerViewFragment$initRefreshLayout$1.this.this$0.setPageNo(1);
                MvpBaseRecyclerViewFragment mvpBaseRecyclerViewFragment = MvpBaseRecyclerViewFragment$initRefreshLayout$1.this.this$0;
                mvpBaseRecyclerViewFragment.setState(mvpBaseRecyclerViewFragment.getGET_LIST_REFRESH());
                MvpBaseRecyclerViewFragment$initRefreshLayout$1.this.this$0.refresh();
            }
        }, 500L);
    }
}
