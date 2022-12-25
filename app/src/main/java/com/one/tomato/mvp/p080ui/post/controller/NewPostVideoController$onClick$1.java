package com.one.tomato.mvp.p080ui.post.controller;

import android.widget.RelativeLayout;
import com.dueeeke.videoplayer.controller.BaseVideoController;
import com.dueeeke.videoplayer.controller.MediaPlayerControl;
import com.one.tomato.R$id;
import com.one.tomato.entity.PostList;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: NewPostVideoController.kt */
/* renamed from: com.one.tomato.mvp.ui.post.controller.NewPostVideoController$onClick$1 */
/* loaded from: classes3.dex */
final class NewPostVideoController$onClick$1 extends Lambda implements Functions<Unit> {
    final /* synthetic */ NewPostVideoController this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NewPostVideoController$onClick$1(NewPostVideoController newPostVideoController) {
        super(0);
        this.this$0 = newPostVideoController;
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
        MediaPlayerControl mediaPlayerControl;
        PostList postList;
        mediaPlayerControl = ((BaseVideoController) this.this$0).mMediaPlayer;
        if (mediaPlayerControl != null) {
            mediaPlayerControl.start();
        }
        RelativeLayout relativeLayout = (RelativeLayout) this.this$0._$_findCachedViewById(R$id.relate_need_pay);
        if (relativeLayout != null) {
            relativeLayout.setVisibility(8);
        }
        Function1<PostList, Unit> payComplete = this.this$0.getPayComplete();
        if (payComplete != null) {
            postList = this.this$0.postList;
            if (postList != null) {
                payComplete.mo6794invoke(postList);
            } else {
                Intrinsics.throwNpe();
                throw null;
            }
        }
    }
}
