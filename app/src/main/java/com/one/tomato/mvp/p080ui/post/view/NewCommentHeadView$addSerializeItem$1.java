package com.one.tomato.mvp.p080ui.post.view;

import com.one.tomato.entity.PostList;
import com.one.tomato.mvp.p080ui.post.impl.IPostCommentContact;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: NewCommentHeadView.kt */
/* renamed from: com.one.tomato.mvp.ui.post.view.NewCommentHeadView$addSerializeItem$1 */
/* loaded from: classes3.dex */
public final class NewCommentHeadView$addSerializeItem$1 extends Lambda implements Function1<PostList, Unit> {
    final /* synthetic */ NewCommentHeadView this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NewCommentHeadView$addSerializeItem$1(NewCommentHeadView newCommentHeadView) {
        super(1);
        this.this$0 = newCommentHeadView;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public /* bridge */ /* synthetic */ Unit mo6794invoke(PostList postList) {
        invoke2(postList);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    public final void invoke2(PostList it2) {
        PostList postList;
        IPostCommentContact iPostCommentContact;
        Intrinsics.checkParameterIsNotNull(it2, "it");
        postList = this.this$0.intentPostList;
        if (postList == null || postList.getId() != it2.getId()) {
            this.this$0.refreshData(it2);
            iPostCommentContact = this.this$0.itemToViewCallBack;
            if (iPostCommentContact == null) {
                return;
            }
            iPostCommentContact.itemClickRefreshPost(it2);
        }
    }
}
