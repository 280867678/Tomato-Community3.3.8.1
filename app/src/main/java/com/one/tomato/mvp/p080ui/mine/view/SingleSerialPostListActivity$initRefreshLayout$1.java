package com.one.tomato.mvp.p080ui.mine.view;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SingleSerialPostListActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.mine.view.SingleSerialPostListActivity$initRefreshLayout$1 */
/* loaded from: classes3.dex */
public final class SingleSerialPostListActivity$initRefreshLayout$1 implements OnRefreshLoadMoreListener {
    final /* synthetic */ SingleSerialPostListActivity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    public SingleSerialPostListActivity$initRefreshLayout$1(SingleSerialPostListActivity singleSerialPostListActivity) {
        this.this$0 = singleSerialPostListActivity;
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
    public void onLoadMore(RefreshLayout refreshLayout) {
        Intrinsics.checkParameterIsNotNull(refreshLayout, "refreshLayout");
        refreshLayout.mo6485getLayout().postDelayed(SingleSerialPostListActivity$initRefreshLayout$1$onLoadMore$1.INSTANCE, 500L);
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
    public void onRefresh(RefreshLayout refreshLayout) {
        Intrinsics.checkParameterIsNotNull(refreshLayout, "refreshLayout");
        refreshLayout.mo6485getLayout().postDelayed(new Runnable() { // from class: com.one.tomato.mvp.ui.mine.view.SingleSerialPostListActivity$initRefreshLayout$1$onRefresh$1
            @Override // java.lang.Runnable
            public final void run() {
                SingleSerialPostListActivity$initRefreshLayout$1.this.this$0.refresh();
            }
        }, 100L);
    }
}
