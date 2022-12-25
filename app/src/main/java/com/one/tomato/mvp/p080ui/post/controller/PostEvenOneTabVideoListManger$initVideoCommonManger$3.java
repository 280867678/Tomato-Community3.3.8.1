package com.one.tomato.mvp.p080ui.post.controller;

import com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: PostEvenOneTabVideoListManger.kt */
/* renamed from: com.one.tomato.mvp.ui.post.controller.PostEvenOneTabVideoListManger$initVideoCommonManger$3 */
/* loaded from: classes3.dex */
public final class PostEvenOneTabVideoListManger$initVideoCommonManger$3 extends Lambda implements Function1<Integer, Unit> {
    final /* synthetic */ PostEvenOneTabVideoListManger this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PostEvenOneTabVideoListManger$initVideoCommonManger$3(PostEvenOneTabVideoListManger postEvenOneTabVideoListManger) {
        super(1);
        this.this$0 = postEvenOneTabVideoListManger;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public /* bridge */ /* synthetic */ Unit mo6794invoke(Integer num) {
        invoke(num.intValue());
        return Unit.INSTANCE;
    }

    public final void invoke(int i) {
        NewPostItemOnClickCallBack newPostItemOnClickCallBack;
        newPostItemOnClickCallBack = this.this$0.newPostItemCallBack;
        if (newPostItemOnClickCallBack != null) {
            newPostItemOnClickCallBack.jumpPostDetailActivity(i);
        }
    }
}
