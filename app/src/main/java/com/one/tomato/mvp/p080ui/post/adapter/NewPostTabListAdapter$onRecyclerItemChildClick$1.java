package com.one.tomato.mvp.p080ui.post.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.one.tomato.entity.PostList;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: NewPostTabListAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.post.adapter.NewPostTabListAdapter$onRecyclerItemChildClick$1 */
/* loaded from: classes3.dex */
final class NewPostTabListAdapter$onRecyclerItemChildClick$1 extends Lambda implements Functions<Unit> {
    final /* synthetic */ BaseQuickAdapter $adapter;
    final /* synthetic */ int $position;
    final /* synthetic */ PostList $postList;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NewPostTabListAdapter$onRecyclerItemChildClick$1(PostList postList, BaseQuickAdapter baseQuickAdapter, int i) {
        super(0);
        this.$postList = postList;
        this.$adapter = baseQuickAdapter;
        this.$position = i;
    }

    @Override // kotlin.jvm.functions.Functions
    /* renamed from: invoke */
    public /* bridge */ /* synthetic */ Unit mo6822invoke() {
        mo6822invoke();
        return Unit.INSTANCE;
    }

    @Override // kotlin.jvm.functions.Functions
    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    public final void mo6822invoke() {
        PostList postList = this.$postList;
        Intrinsics.checkExpressionValueIsNotNull(postList, "postList");
        postList.setAlreadyPaid(true);
        BaseQuickAdapter baseQuickAdapter = this.$adapter;
        if (baseQuickAdapter != null) {
            baseQuickAdapter.notifyItemChanged(this.$position);
        }
    }
}
