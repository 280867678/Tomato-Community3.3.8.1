package com.one.tomato.mvp.p080ui.post.controller;

import com.one.tomato.entity.PostList;
import com.one.tomato.mvp.p080ui.post.item.PostVideoLookPayItem;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: PostEvenOneTabVideoListManger.kt */
/* renamed from: com.one.tomato.mvp.ui.post.controller.PostEvenOneTabVideoListManger$showNeedPayView$1 */
/* loaded from: classes3.dex */
public final class PostEvenOneTabVideoListManger$showNeedPayView$1 extends Lambda implements Function1<String, Unit> {
    final /* synthetic */ PostEvenOneTabVideoListManger this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PostEvenOneTabVideoListManger$showNeedPayView$1(PostEvenOneTabVideoListManger postEvenOneTabVideoListManger) {
        super(1);
        this.this$0 = postEvenOneTabVideoListManger;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public /* bridge */ /* synthetic */ Unit mo6794invoke(String str) {
        invoke2(str);
        return Unit.INSTANCE;
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0048, code lost:
        r6 = r5.this$0.controller;
     */
    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void invoke2(String it2) {
        PostVideoLookPayItem postVideoLookPayItem;
        boolean z;
        NewPostVideoController newPostVideoController;
        PostVideoLookPayItem postVideoLookPayItem2;
        PostList postList;
        PostList postList2;
        PostList postList3;
        Intrinsics.checkParameterIsNotNull(it2, "it");
        postVideoLookPayItem = this.this$0.showRewardVideoView;
        if (postVideoLookPayItem != null) {
            postList = this.this$0.postList;
            String valueOf = postList != null ? String.valueOf(postList.getPrice()) : null;
            postList2 = this.this$0.postList;
            int subscribeSwitch = postList2 != null ? postList2.getSubscribeSwitch() : 0;
            postList3 = this.this$0.postList;
            postVideoLookPayItem.setNeedPostPay(it2, valueOf, subscribeSwitch, postList3 != null ? postList3.getMemberId() : 0);
        }
        z = this.this$0.isVideoResume;
        if (z && newPostVideoController != null) {
            postVideoLookPayItem2 = this.this$0.showRewardVideoView;
            newPostVideoController.addView(postVideoLookPayItem2);
        }
        this.this$0.isVideoResume = false;
    }
}
