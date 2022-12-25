package com.one.tomato.mvp.p080ui.post.controller;

import android.graphics.Rect;
import com.one.tomato.entity.PostList;
import com.one.tomato.mvp.p080ui.p082up.PostRewardPayUtils;
import com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack;
import com.one.tomato.mvp.p080ui.post.item.PostVideoLookPayItem;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: PostEvenOneTabVideoListManger.kt */
/* renamed from: com.one.tomato.mvp.ui.post.controller.PostEvenOneTabVideoListManger$payCallBack$1 */
/* loaded from: classes3.dex */
public final class PostEvenOneTabVideoListManger$payCallBack$1 extends Lambda implements Functions<Unit> {
    final /* synthetic */ PostEvenOneTabVideoListManger this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PostEvenOneTabVideoListManger$payCallBack$1(PostEvenOneTabVideoListManger postEvenOneTabVideoListManger) {
        super(0);
        this.this$0 = postEvenOneTabVideoListManger;
    }

    @Override // kotlin.jvm.functions.Functions
    /* renamed from: invoke */
    public /* bridge */ /* synthetic */ Unit mo6822invoke() {
        mo6822invoke();
        return Unit.INSTANCE;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0036, code lost:
        r0 = r4.this$0.controller;
     */
    @Override // kotlin.jvm.functions.Functions
    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void mo6822invoke() {
        PostList postList;
        PostList postList2;
        NewPostItemOnClickCallBack newPostItemOnClickCallBack;
        PostVideoLookPayItem postVideoLookPayItem;
        NewPostVideoController newPostVideoController;
        NewPostVideoController newPostVideoController2;
        PostVideoLookPayItem postVideoLookPayItem2;
        PostList postList3;
        try {
            postList = this.this$0.postList;
            if (postList != null) {
                postList.setAlreadyPaid(true);
            }
            PostRewardPayUtils postRewardPayUtils = PostRewardPayUtils.INSTANCE;
            postList2 = this.this$0.postList;
            if (postList2 != null) {
                postRewardPayUtils.setPayPost(postList2.getId());
                newPostItemOnClickCallBack = this.this$0.newPostItemCallBack;
                if (newPostItemOnClickCallBack != null) {
                    postList3 = this.this$0.postList;
                    newPostItemOnClickCallBack.itemPostPayComplete(postList3);
                }
                postVideoLookPayItem = this.this$0.showRewardVideoView;
                if (postVideoLookPayItem != null && newPostVideoController2 != null) {
                    postVideoLookPayItem2 = this.this$0.showRewardVideoView;
                    newPostVideoController2.removeView(postVideoLookPayItem2);
                }
                newPostVideoController = this.this$0.controller;
                if (newPostVideoController == null) {
                    return;
                }
                newPostVideoController.postDelayed(new Runnable() { // from class: com.one.tomato.mvp.ui.post.controller.PostEvenOneTabVideoListManger$payCallBack$1.2
                    @Override // java.lang.Runnable
                    public final void run() {
                        NewPostVideoController newPostVideoController3;
                        NewPostVideoController newPostVideoController4;
                        newPostVideoController3 = PostEvenOneTabVideoListManger$payCallBack$1.this.this$0.controller;
                        Boolean bool = null;
                        Boolean valueOf = newPostVideoController3 != null ? Boolean.valueOf(newPostVideoController3.isShown()) : null;
                        Rect rect = new Rect();
                        newPostVideoController4 = PostEvenOneTabVideoListManger$payCallBack$1.this.this$0.controller;
                        if (newPostVideoController4 != null) {
                            bool = Boolean.valueOf(newPostVideoController4.getGlobalVisibleRect(rect));
                        }
                        if (!Intrinsics.areEqual(valueOf, true) || !Intrinsics.areEqual(bool, true)) {
                            return;
                        }
                        PostEvenOneTabVideoListManger$payCallBack$1.this.this$0.isVideoResume = true;
                        PostEvenOneTabVideoListManger$payCallBack$1.this.this$0.resumeVideo();
                    }
                }, 500L);
                return;
            }
            Intrinsics.throwNpe();
            throw null;
        } catch (Exception unused) {
        }
    }
}
