package com.one.tomato.mvp.p080ui.post.controller;

import com.one.tomato.entity.PostList;
import com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Lambda;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: PostEvenOneTabVideoListManger.kt */
/* renamed from: com.one.tomato.mvp.ui.post.controller.PostEvenOneTabVideoListManger$initVideoCommonManger$2 */
/* loaded from: classes3.dex */
public final class PostEvenOneTabVideoListManger$initVideoCommonManger$2 extends Lambda implements Functions<Unit> {
    final /* synthetic */ PostEvenOneTabVideoListManger this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PostEvenOneTabVideoListManger$initVideoCommonManger$2(PostEvenOneTabVideoListManger postEvenOneTabVideoListManger) {
        super(0);
        this.this$0 = postEvenOneTabVideoListManger;
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
        PostList postList;
        newPostItemOnClickCallBack = this.this$0.newPostItemCallBack;
        if (newPostItemOnClickCallBack != null) {
            postList = this.this$0.postList;
            newPostItemOnClickCallBack.callVideoDialog(postList);
        }
    }
}
