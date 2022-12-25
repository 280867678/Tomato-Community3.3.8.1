package com.one.tomato.mvp.p080ui.post.controller;

import com.one.tomato.entity.PostList;
import com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: PostEvenOneTabVideoListManger.kt */
/* renamed from: com.one.tomato.mvp.ui.post.controller.PostEvenOneTabVideoListManger$initVideoCommonManger$4 */
/* loaded from: classes3.dex */
public final class PostEvenOneTabVideoListManger$initVideoCommonManger$4 extends Lambda implements Function1<PostList, Unit> {
    final /* synthetic */ PostEvenOneTabVideoListManger this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PostEvenOneTabVideoListManger$initVideoCommonManger$4(PostEvenOneTabVideoListManger postEvenOneTabVideoListManger) {
        super(1);
        this.this$0 = postEvenOneTabVideoListManger;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public /* bridge */ /* synthetic */ Unit mo6794invoke(PostList postList) {
        invoke2(postList);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    public final void invoke2(PostList it2) {
        NewPostItemOnClickCallBack newPostItemOnClickCallBack;
        Intrinsics.checkParameterIsNotNull(it2, "it");
        it2.setAlreadyPaid(true);
        newPostItemOnClickCallBack = this.this$0.newPostItemCallBack;
        if (newPostItemOnClickCallBack != null) {
            newPostItemOnClickCallBack.itemPostPayComplete(it2);
        }
        this.this$0.resumeVideo();
    }
}
