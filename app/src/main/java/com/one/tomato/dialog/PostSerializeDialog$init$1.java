package com.one.tomato.dialog;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PostSerializeDialog.kt */
/* loaded from: classes3.dex */
public final class PostSerializeDialog$init$1 implements OnRefreshLoadMoreListener {
    final /* synthetic */ PostSerializeDialog this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    public PostSerializeDialog$init$1(PostSerializeDialog postSerializeDialog) {
        this.this$0 = postSerializeDialog;
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
    public void onLoadMore(RefreshLayout refreshLayout) {
        Intrinsics.checkParameterIsNotNull(refreshLayout, "refreshLayout");
        refreshLayout.mo6485getLayout().postDelayed(PostSerializeDialog$init$1$onLoadMore$1.INSTANCE, 500L);
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
    public void onRefresh(RefreshLayout refreshLayout) {
        Intrinsics.checkParameterIsNotNull(refreshLayout, "refreshLayout");
        refreshLayout.mo6485getLayout().postDelayed(new Runnable() { // from class: com.one.tomato.dialog.PostSerializeDialog$init$1$onRefresh$1
            @Override // java.lang.Runnable
            public final void run() {
                PostSerializeDialog$init$1.this.this$0.refresh();
            }
        }, 100L);
    }
}
