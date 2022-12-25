package com.one.tomato.mvp.p080ui.papa.view;

import android.support.p005v7.widget.RecyclerView;
import com.one.tomato.entity.event.VideoTabStatusChange;
import com.one.tomato.thirdpart.recyclerview.BaseStaggerGridLayoutManager;
import io.reactivex.functions.Consumer;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PaPaHotListFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.papa.view.PaPaHotListFragment$registerRxBus$1 */
/* loaded from: classes3.dex */
public final class PaPaHotListFragment$registerRxBus$1 implements Consumer<VideoTabStatusChange> {
    final /* synthetic */ PaPaHotListFragment this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    public PaPaHotListFragment$registerRxBus$1(PaPaHotListFragment paPaHotListFragment) {
        this.this$0 = paPaHotListFragment;
    }

    @Override // io.reactivex.functions.Consumer
    public void accept(VideoTabStatusChange t) {
        RecyclerView recyclerView;
        Intrinsics.checkParameterIsNotNull(t, "t");
        if (!t.refresh || !this.this$0.isVisible()) {
            return;
        }
        recyclerView = this.this$0.getRecyclerView();
        if (recyclerView != null) {
            recyclerView.postDelayed(new Runnable() { // from class: com.one.tomato.mvp.ui.papa.view.PaPaHotListFragment$registerRxBus$1$accept$1
                @Override // java.lang.Runnable
                public final void run() {
                    RecyclerView recyclerView2;
                    recyclerView2 = PaPaHotListFragment$registerRxBus$1.this.this$0.getRecyclerView();
                    BaseStaggerGridLayoutManager baseStaggerGridLayoutManager = (BaseStaggerGridLayoutManager) (recyclerView2 != null ? recyclerView2.getLayoutManager() : null);
                    if (baseStaggerGridLayoutManager != null) {
                        baseStaggerGridLayoutManager.scrollToPositionWithOffset(0, 0);
                    } else {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                }
            }, 100L);
        }
        this.this$0.setLazyLoad(false);
        this.this$0.setUserVisibleHint(true);
    }
}
