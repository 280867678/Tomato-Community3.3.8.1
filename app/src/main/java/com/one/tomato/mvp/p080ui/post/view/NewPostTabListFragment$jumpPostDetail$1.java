package com.one.tomato.mvp.p080ui.post.view;

import com.one.tomato.entity.PostList;
import com.one.tomato.mvp.p080ui.post.adapter.NewPostTabListAdapter;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.anko.Async;
import org.jetbrains.anko.AsyncKt;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: NewPostTabListFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.post.view.NewPostTabListFragment$jumpPostDetail$1 */
/* loaded from: classes3.dex */
public final class NewPostTabListFragment$jumpPostDetail$1 extends Lambda implements Function1<Async<NewPostTabListFragment>, Unit> {
    final /* synthetic */ boolean $isScrollTop;
    final /* synthetic */ int $position;
    final /* synthetic */ PostList $postList;
    final /* synthetic */ NewPostTabListFragment this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NewPostTabListFragment$jumpPostDetail$1(NewPostTabListFragment newPostTabListFragment, PostList postList, int i, boolean z) {
        super(1);
        this.this$0 = newPostTabListFragment;
        this.$postList = postList;
        this.$position = i;
        this.$isScrollTop = z;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public /* bridge */ /* synthetic */ Unit mo6794invoke(Async<NewPostTabListFragment> async) {
        invoke2(async);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    public final void invoke2(Async<NewPostTabListFragment> receiver) {
        NewPostTabListAdapter adapter;
        int size;
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        adapter = this.this$0.getAdapter();
        List<PostList> data = adapter != null ? adapter.getData() : null;
        ArrayList arrayList = new ArrayList();
        if (data != null) {
            if (this.$postList != null) {
                ArrayList arrayList2 = new ArrayList();
                arrayList2.addAll(data);
                if (this.$position + 1 < data.size()) {
                    size = this.$position + 2;
                } else {
                    size = data.size();
                }
                List<PostList> subList = arrayList2.subList(this.$position, size);
                Intrinsics.checkExpressionValueIsNotNull(subList, "dataList.subList(position, endIndex)");
                for (PostList it2 : subList) {
                    Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                    if (!it2.isAd() && it2.getInsertFlag() != 1) {
                        arrayList.add(it2);
                    }
                }
            }
            AsyncKt.uiThread(receiver, new C2643x20c07022(this, receiver, data, arrayList));
        }
    }
}
