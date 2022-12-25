package com.one.tomato.mvp.p080ui.post.view;

import android.os.Bundle;
import android.support.p002v4.app.FragmentActivity;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.one.tomato.entity.PostList;
import com.one.tomato.mvp.p080ui.papa.view.NewPaPaListVideoActivity;
import com.one.tomato.mvp.p080ui.post.controller.PostEvenOneTabVideoListManger;
import com.one.tomato.mvp.p080ui.post.view.NewPostDetailViewPagerActivity;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.anko.Async;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: NewPostTabListFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.post.view.NewPostTabListFragment$jumpPostDetail$1$$special$$inlined$let$lambda$1 */
/* loaded from: classes3.dex */
public final class C2643x20c07022 extends Lambda implements Function1<NewPostTabListFragment, Unit> {
    final /* synthetic */ List $data$inlined;
    final /* synthetic */ ArrayList $list$inlined;
    final /* synthetic */ Async $this_doAsync$inlined;
    final /* synthetic */ NewPostTabListFragment$jumpPostDetail$1 this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C2643x20c07022(NewPostTabListFragment$jumpPostDetail$1 newPostTabListFragment$jumpPostDetail$1, Async async, List list, ArrayList arrayList) {
        super(1);
        this.this$0 = newPostTabListFragment$jumpPostDetail$1;
        this.$this_doAsync$inlined = async;
        this.$data$inlined = list;
        this.$list$inlined = arrayList;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public /* bridge */ /* synthetic */ Unit mo6794invoke(NewPostTabListFragment newPostTabListFragment) {
        invoke2(newPostTabListFragment);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    public final void invoke2(NewPostTabListFragment it2) {
        ArrayList<PostList> arrayListOf;
        int pageNo;
        PostEvenOneTabVideoListManger postEvenOneTabVideoListManger;
        int pageNo2;
        PostEvenOneTabVideoListManger postEvenOneTabVideoListManger2;
        IjkVideoView ijkPlayInstance;
        Intrinsics.checkParameterIsNotNull(it2, "it");
        PostList postList = this.this$0.$postList;
        Integer valueOf = postList != null ? Integer.valueOf(postList.getPostType()) : null;
        if (valueOf != null && valueOf.intValue() == 2) {
            this.this$0.this$0.isJumpPostDetail = true;
            if (this.this$0.this$0.getActivity() != null) {
                NewPostDetailViewPagerActivity.Companion companion = NewPostDetailViewPagerActivity.Companion;
                FragmentActivity activity = this.this$0.this$0.getActivity();
                ArrayList<PostList> arrayList = this.$list$inlined;
                int category = this.this$0.this$0.getCategory();
                int channelId = this.this$0.this$0.getChannelId();
                String businessType = this.this$0.this$0.getBusinessType();
                pageNo2 = this.this$0.this$0.getPageNo();
                NewPostTabListFragment$jumpPostDetail$1 newPostTabListFragment$jumpPostDetail$1 = this.this$0;
                boolean z = newPostTabListFragment$jumpPostDetail$1.$isScrollTop;
                int personMemberId = newPostTabListFragment$jumpPostDetail$1.this$0.getPersonMemberId();
                int groundId = this.this$0.this$0.getGroundId();
                int tagId = this.this$0.this$0.getTagId();
                String seachKey = this.this$0.this$0.getSeachKey();
                postEvenOneTabVideoListManger2 = this.this$0.this$0.videoPlayManger;
                companion.startActivity(activity, arrayList, category, channelId, businessType, pageNo2, z, (Bundle) null, personMemberId, groundId, tagId, seachKey, (postEvenOneTabVideoListManger2 == null || (ijkPlayInstance = postEvenOneTabVideoListManger2.getIjkPlayInstance()) == null) ? 0L : ijkPlayInstance.getCurrentPosition());
            }
            postEvenOneTabVideoListManger = this.this$0.this$0.videoPlayManger;
            if (postEvenOneTabVideoListManger == null) {
                return;
            }
            postEvenOneTabVideoListManger.release();
        } else if ((valueOf != null && valueOf.intValue() == 1) || (valueOf != null && valueOf.intValue() == 3)) {
            NewPostDetailViewPagerActivity.Companion companion2 = NewPostDetailViewPagerActivity.Companion;
            FragmentActivity activity2 = this.this$0.this$0.getActivity();
            ArrayList<PostList> arrayList2 = this.$list$inlined;
            int category2 = this.this$0.this$0.getCategory();
            int channelId2 = this.this$0.this$0.getChannelId();
            String businessType2 = this.this$0.this$0.getBusinessType();
            pageNo = this.this$0.this$0.getPageNo();
            NewPostTabListFragment$jumpPostDetail$1 newPostTabListFragment$jumpPostDetail$12 = this.this$0;
            companion2.startActivity(activity2, arrayList2, category2, channelId2, businessType2, pageNo, newPostTabListFragment$jumpPostDetail$12.$isScrollTop, null, newPostTabListFragment$jumpPostDetail$12.this$0.getPersonMemberId(), this.this$0.this$0.getGroundId(), this.this$0.this$0.getTagId(), this.this$0.this$0.getSeachKey());
        } else if (valueOf == null || valueOf.intValue() != 4) {
        } else {
            NewPaPaListVideoActivity.Companion companion3 = NewPaPaListVideoActivity.Companion;
            FragmentActivity activity3 = this.this$0.this$0.getActivity();
            if (activity3 == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            Intrinsics.checkExpressionValueIsNotNull(activity3, "activity!!");
            arrayListOf = CollectionsKt__CollectionsKt.arrayListOf(this.this$0.$postList);
            companion3.startAct(activity3, arrayListOf, null, null, 0, 0, 0, false);
        }
    }
}
