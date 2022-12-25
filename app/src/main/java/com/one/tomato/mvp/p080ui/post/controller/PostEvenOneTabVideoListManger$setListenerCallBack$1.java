package com.one.tomato.mvp.p080ui.post.controller;

import com.one.tomato.entity.PostList;
import com.one.tomato.mvp.p080ui.post.utils.PostUtils;
import com.one.tomato.utils.DataUploadUtil;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Lambda;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: PostEvenOneTabVideoListManger.kt */
/* renamed from: com.one.tomato.mvp.ui.post.controller.PostEvenOneTabVideoListManger$setListenerCallBack$1 */
/* loaded from: classes3.dex */
public final class PostEvenOneTabVideoListManger$setListenerCallBack$1 extends Lambda implements Function3<Long, Long, Integer, Unit> {
    final /* synthetic */ PostEvenOneTabVideoListManger this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PostEvenOneTabVideoListManger$setListenerCallBack$1(PostEvenOneTabVideoListManger postEvenOneTabVideoListManger) {
        super(3);
        this.this$0 = postEvenOneTabVideoListManger;
    }

    @Override // kotlin.jvm.functions.Function3
    public /* bridge */ /* synthetic */ Unit invoke(Long l, Long l2, Integer num) {
        invoke(l.longValue(), l2.longValue(), num.intValue());
        return Unit.INSTANCE;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0027, code lost:
        r2 = r9.this$0.postList;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0054, code lost:
        r0 = r9.this$0.postList;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void invoke(long j, long j2, int i) {
        boolean z;
        PostList postList;
        PostList postList2;
        boolean z2;
        boolean z3;
        PostList postList3;
        PostList postList4;
        boolean z4;
        boolean z5;
        PostList postList5;
        boolean z6;
        PostList postList6;
        boolean z7;
        PostList postList7;
        boolean z8;
        PostList postList8;
        PostList postList9;
        z = this.this$0.isReviewPost;
        if (z) {
            return;
        }
        long j3 = 1000;
        long j4 = j2 / j3;
        long j5 = j / j3;
        int i2 = (j5 > 0L ? 1 : (j5 == 0L ? 0 : -1));
        if (i2 > 0 && j5 > j4 / 2) {
            z8 = this.this$0.isAlreadyUpVideoHalf;
            if (!z8 && postList8 != null && !postList8.isAd()) {
                this.this$0.isAlreadyUpVideoHalf = true;
                postList9 = this.this$0.postList;
                DataUploadUtil.uploadVideoPlayHalf(postList9 != null ? postList9.getId() : 0);
            }
        }
        if (i2 > 0 && j5 > j4 - 2 && postList6 != null && !postList6.isAd()) {
            z7 = this.this$0.isAlreadyUpVideoCom;
            if (!z7) {
                this.this$0.isAlreadyUpVideoCom = true;
                postList7 = this.this$0.postList;
                DataUploadUtil.uploadVideoPlayWhole(postList7 != null ? postList7.getId() : 0);
            }
        }
        if (j <= 3) {
            return;
        }
        postList = this.this$0.postList;
        if (postList != null && !postList.isAlreadyPaid()) {
            postList4 = this.this$0.postList;
            if ((postList4 != null ? postList4.getPrice() : 0) > 0) {
                if (j4 <= 600) {
                    if (i >= 15) {
                        z6 = this.this$0.isPostPay;
                        if (z6) {
                            this.this$0.isPostPay = false;
                            this.this$0.showNeedPayView();
                        }
                    }
                } else if (i >= 60) {
                    z4 = this.this$0.isPostPay;
                    if (z4) {
                        this.this$0.isPostPay = false;
                        this.this$0.showNeedPayView();
                    }
                }
                z5 = this.this$0.isAlreadyUpData;
                if (!z5) {
                    System.out.println("播放的角标---------" + i);
                    PostUtils postUtils = PostUtils.INSTANCE;
                    postList5 = this.this$0.postList;
                    postUtils.updatePostBrowse(postList5);
                    this.this$0.isAlreadyUpData = true;
                }
                this.this$0.payPostDeductionLookTime();
                return;
            }
        }
        postList2 = this.this$0.postList;
        if (postList2 == null || !postList2.isAlreadyPaid()) {
            z2 = this.this$0.subLookTime;
            if (!z2) {
                return;
            }
            this.this$0.subLookTime = false;
            this.this$0.checkLookTimeIsEnd();
            return;
        }
        z3 = this.this$0.isAlreadyUpData;
        if (z3) {
            return;
        }
        System.out.println("播放的角标---------" + i);
        PostUtils postUtils2 = PostUtils.INSTANCE;
        postList3 = this.this$0.postList;
        postUtils2.updatePostBrowse(postList3);
        this.this$0.isAlreadyUpData = true;
    }
}
