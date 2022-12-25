package com.one.tomato.mvp.p080ui.post.view;

import com.one.tomato.entity.PostList;
import com.one.tomato.mvp.p080ui.post.impl.IPostCommentContact;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Lambda;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: NewCommentHeadView.kt */
/* renamed from: com.one.tomato.mvp.ui.post.view.NewCommentHeadView$clickPostPay$1 */
/* loaded from: classes3.dex */
public final class NewCommentHeadView$clickPostPay$1 extends Lambda implements Functions<Unit> {
    final /* synthetic */ NewCommentHeadView this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NewCommentHeadView$clickPostPay$1(NewCommentHeadView newCommentHeadView) {
        super(0);
        this.this$0 = newCommentHeadView;
    }

    @Override // kotlin.jvm.functions.Functions
    /* renamed from: invoke */
    public /* bridge */ /* synthetic */ Unit mo6822invoke() {
        mo6822invoke();
        return Unit.INSTANCE;
    }

    @Override // kotlin.jvm.functions.Functions
    /* renamed from: invoke  reason: collision with other method in class */
    public final void mo6822invoke() {
        PostList postList;
        IPostCommentContact iPostCommentContact;
        PostList postList2;
        postList = this.this$0.intentPostList;
        if (postList != null) {
            postList.setAlreadyPaid(true);
        }
        iPostCommentContact = this.this$0.itemToViewCallBack;
        if (iPostCommentContact != null) {
            postList2 = this.this$0.intentPostList;
            iPostCommentContact.callRewardFinsh(postList2);
        }
    }
}
