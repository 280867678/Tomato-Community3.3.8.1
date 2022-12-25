package com.one.tomato.mvp.p080ui.post.view;

import com.one.tomato.entity.PostList;
import com.one.tomato.entity.event.PostDetailListDataEvent;
import com.one.tomato.mvp.p080ui.post.adapter.NewPostTabListAdapter;
import java.util.List;
import org.jetbrains.anko.AsyncKt;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: NewPostTabListFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.post.view.NewPostTabListFragment$setScrollPostion2$1 */
/* loaded from: classes3.dex */
public final class NewPostTabListFragment$setScrollPostion2$1 implements Runnable {
    final /* synthetic */ PostDetailListDataEvent $it;
    final /* synthetic */ NewPostTabListFragment this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    public NewPostTabListFragment$setScrollPostion2$1(NewPostTabListFragment newPostTabListFragment, PostDetailListDataEvent postDetailListDataEvent) {
        this.this$0 = newPostTabListFragment;
        this.$it = postDetailListDataEvent;
    }

    @Override // java.lang.Runnable
    public final void run() {
        NewPostTabListAdapter adapter;
        this.this$0.setPageNo(this.$it.getPageNo());
        PostList postList = this.$it.getPostList();
        if (postList instanceof PostList) {
            adapter = this.this$0.getAdapter();
            List<PostList> data = adapter != null ? adapter.getData() : null;
            if (data == null) {
                return;
            }
            AsyncKt.doAsync$default(this.this$0, null, new C2644xce9665aa(data, this, postList), 1, null);
        }
    }
}
