package com.one.tomato.mvp.p080ui.post.item;

import com.one.tomato.entity.PostList;
import com.one.tomato.mvp.p080ui.post.adapter.NewPostImageItemAdapter;
import com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Lambda;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: PostImageItem.kt */
/* renamed from: com.one.tomato.mvp.ui.post.item.PostImageItem$isShowRewardDialog$1 */
/* loaded from: classes3.dex */
public final class PostImageItem$isShowRewardDialog$1 extends Lambda implements Functions<Unit> {
    final /* synthetic */ PostImageItem this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PostImageItem$isShowRewardDialog$1(PostImageItem postImageItem) {
        super(0);
        this.this$0 = postImageItem;
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
        NewPostItemOnClickCallBack newPostItemOnClickCallBack;
        PostList postList = this.this$0.postList;
        if (postList != null) {
            postList.setAlreadyPaid(true);
        }
        NewPostImageItemAdapter newPostImageItemAdapter = this.this$0.adapter;
        if (newPostImageItemAdapter != null) {
            newPostImageItemAdapter.isReward(false);
        }
        newPostItemOnClickCallBack = this.this$0.newPostItemOnClickCallBack;
        if (newPostItemOnClickCallBack != null) {
            newPostItemOnClickCallBack.itemPostPayComplete(this.this$0.postList);
        }
    }
}
