package com.one.tomato.mvp.p080ui.post.controller;

import android.graphics.Rect;
import com.one.tomato.mvp.p080ui.post.item.PostVideoLookPayItem;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: PostEvenOneTabVideoListManger.kt */
/* renamed from: com.one.tomato.mvp.ui.post.controller.PostEvenOneTabVideoListManger$callBack$1 */
/* loaded from: classes3.dex */
public final class PostEvenOneTabVideoListManger$callBack$1 extends Lambda implements Functions<Unit> {
    final /* synthetic */ PostEvenOneTabVideoListManger this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PostEvenOneTabVideoListManger$callBack$1(PostEvenOneTabVideoListManger postEvenOneTabVideoListManger) {
        super(0);
        this.this$0 = postEvenOneTabVideoListManger;
    }

    @Override // kotlin.jvm.functions.Functions
    /* renamed from: invoke */
    public /* bridge */ /* synthetic */ Unit mo6822invoke() {
        mo6822invoke();
        return Unit.INSTANCE;
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x0008, code lost:
        r0 = r4.this$0.controller;
     */
    @Override // kotlin.jvm.functions.Functions
    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void mo6822invoke() {
        PostVideoLookPayItem postVideoLookPayItem;
        NewPostVideoController newPostVideoController;
        NewPostVideoController newPostVideoController2;
        PostVideoLookPayItem postVideoLookPayItem2;
        try {
            postVideoLookPayItem = this.this$0.postVideoLookPayItem;
            if (postVideoLookPayItem != null && newPostVideoController2 != null) {
                postVideoLookPayItem2 = this.this$0.postVideoLookPayItem;
                newPostVideoController2.removeView(postVideoLookPayItem2);
            }
            newPostVideoController = this.this$0.controller;
            if (newPostVideoController == null) {
                return;
            }
            newPostVideoController.postDelayed(new Runnable() { // from class: com.one.tomato.mvp.ui.post.controller.PostEvenOneTabVideoListManger$callBack$1.2
                @Override // java.lang.Runnable
                public final void run() {
                    NewPostVideoController newPostVideoController3;
                    NewPostVideoController newPostVideoController4;
                    newPostVideoController3 = PostEvenOneTabVideoListManger$callBack$1.this.this$0.controller;
                    Boolean bool = null;
                    Boolean valueOf = newPostVideoController3 != null ? Boolean.valueOf(newPostVideoController3.isShown()) : null;
                    Rect rect = new Rect();
                    newPostVideoController4 = PostEvenOneTabVideoListManger$callBack$1.this.this$0.controller;
                    if (newPostVideoController4 != null) {
                        bool = Boolean.valueOf(newPostVideoController4.getGlobalVisibleRect(rect));
                    }
                    if (!Intrinsics.areEqual(valueOf, true) || !Intrinsics.areEqual(bool, true)) {
                        return;
                    }
                    PostEvenOneTabVideoListManger$callBack$1.this.this$0.isVideoResume = true;
                    PostEvenOneTabVideoListManger$callBack$1.this.this$0.resumeVideo();
                }
            }, 500L);
        } catch (Exception unused) {
        }
    }
}
