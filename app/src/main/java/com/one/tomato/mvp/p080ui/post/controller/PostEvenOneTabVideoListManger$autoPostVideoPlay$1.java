package com.one.tomato.mvp.p080ui.post.controller;

import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.one.tomato.entity.PostList;
import com.one.tomato.utils.LogUtil;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections._Collections;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref$ObjectRef;
import org.jetbrains.anko.Async;
import org.jetbrains.anko.AsyncKt;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: PostEvenOneTabVideoListManger.kt */
/* renamed from: com.one.tomato.mvp.ui.post.controller.PostEvenOneTabVideoListManger$autoPostVideoPlay$1 */
/* loaded from: classes3.dex */
public final class PostEvenOneTabVideoListManger$autoPostVideoPlay$1 extends Lambda implements Function1<Async<PostEvenOneTabVideoListManger>, Unit> {
    final /* synthetic */ RecyclerView $view;
    final /* synthetic */ PostEvenOneTabVideoListManger this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PostEvenOneTabVideoListManger$autoPostVideoPlay$1(PostEvenOneTabVideoListManger postEvenOneTabVideoListManger, RecyclerView recyclerView) {
        super(1);
        this.this$0 = postEvenOneTabVideoListManger;
        this.$view = recyclerView;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: PostEvenOneTabVideoListManger.kt */
    /* renamed from: com.one.tomato.mvp.ui.post.controller.PostEvenOneTabVideoListManger$autoPostVideoPlay$1$1 */
    /* loaded from: classes3.dex */
    public static final class C26011 extends Lambda implements Function1<PostEvenOneTabVideoListManger, Unit> {
        final /* synthetic */ Ref$ObjectRef $map;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C26011(Ref$ObjectRef ref$ObjectRef) {
            super(1);
            this.$map = ref$ObjectRef;
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public /* bridge */ /* synthetic */ Unit mo6794invoke(PostEvenOneTabVideoListManger postEvenOneTabVideoListManger) {
            invoke2(postEvenOneTabVideoListManger);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke  reason: avoid collision after fix types in other method */
        public final void invoke2(PostEvenOneTabVideoListManger it2) {
            Float max;
            FrameLayout frameLayout;
            PostList postList;
            Intrinsics.checkParameterIsNotNull(it2, "it");
            if (((Map) this.$map.element).isEmpty()) {
                PostEvenOneTabVideoListManger$autoPostVideoPlay$1.this.this$0.release();
                return;
            }
            LogUtil.m3785e("yan", ((Map) this.$map.element).toString());
            max = _Collections.max(((Map) this.$map.element).keySet());
            LogUtil.m3785e("yan", max);
            Integer num = (Integer) ((Map) this.$map.element).get(max);
            RecyclerView.ViewHolder findViewHolderForAdapterPosition = num != null ? PostEvenOneTabVideoListManger$autoPostVideoPlay$1.this.$view.findViewHolderForAdapterPosition(num.intValue()) : null;
            if (findViewHolderForAdapterPosition == null || (frameLayout = (FrameLayout) findViewHolderForAdapterPosition.itemView.findViewById(R.id.fram_ijkplay_view)) == null || frameLayout.getVisibility() == 0) {
                return;
            }
            PostEvenOneTabVideoListManger$autoPostVideoPlay$1.this.this$0.release();
            IjkVideoView ijkPlayInstance = PostEvenOneTabVideoListManger$autoPostVideoPlay$1.this.this$0.getIjkPlayInstance();
            if (ijkPlayInstance == null || (postList = (PostList) frameLayout.getTag(R.id.video_list_item_id)) == null) {
                return;
            }
            ImageView imgageView = (ImageView) findViewHolderForAdapterPosition.itemView.findViewById(R.id.image_video_cove);
            ImageView imagePlay = (ImageView) findViewHolderForAdapterPosition.itemView.findViewById(R.id.image_video_play);
            TextView tv_video_time = (TextView) findViewHolderForAdapterPosition.itemView.findViewById(R.id.tv_video_time);
            Intrinsics.checkExpressionValueIsNotNull(imgageView, "imgageView");
            if (imgageView.getDrawable() != null) {
                PostEvenOneTabVideoListManger$autoPostVideoPlay$1.this.this$0.setThumbImage(imgageView.getDrawable());
            }
            PostEvenOneTabVideoListManger$autoPostVideoPlay$1.this.this$0.startVideo(postList, "帖子列表");
            Intrinsics.checkExpressionValueIsNotNull(imagePlay, "imagePlay");
            imagePlay.setVisibility(8);
            imgageView.setVisibility(8);
            Intrinsics.checkExpressionValueIsNotNull(tv_video_time, "tv_video_time");
            tv_video_time.setVisibility(8);
            frameLayout.setVisibility(0);
            frameLayout.addView(ijkPlayInstance);
        }
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public /* bridge */ /* synthetic */ Unit mo6794invoke(Async<PostEvenOneTabVideoListManger> async) {
        invoke2(async);
        return Unit.INSTANCE;
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [java.util.LinkedHashMap, T] */
    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    public final void invoke2(Async<PostEvenOneTabVideoListManger> receiver) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        RecyclerView.LayoutManager layoutManager = this.$view.getLayoutManager();
        if (layoutManager == null) {
            throw new TypeCastException("null cannot be cast to non-null type android.support.v7.widget.LinearLayoutManager");
        }
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
        int findFirstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
        int findLastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
        Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        ref$ObjectRef.element = new LinkedHashMap();
        if (findFirstVisibleItemPosition <= findLastVisibleItemPosition) {
            while (true) {
                RecyclerView.ViewHolder findViewHolderForAdapterPosition = this.$view.findViewHolderForAdapterPosition(findFirstVisibleItemPosition);
                if (findViewHolderForAdapterPosition != null && ((FrameLayout) findViewHolderForAdapterPosition.itemView.findViewById(R.id.fram_ijkplay_view)) != null) {
                    View view = findViewHolderForAdapterPosition.itemView;
                    Intrinsics.checkExpressionValueIsNotNull(view, "viewHold.itemView");
                    int[] iArr = new int[2];
                    int[] iArr2 = new int[2];
                    view.getLocationOnScreen(iArr);
                    this.$view.getLocationOnScreen(iArr2);
                    int i = iArr[1] - iArr2[1];
                    if (i < 0) {
                        float height = ((view.getHeight() + i) * 100) / view.getHeight();
                        if (height > 50) {
                            ((Map) ref$ObjectRef.element).put(Float.valueOf(height), Integer.valueOf(findFirstVisibleItemPosition));
                        }
                    } else if (view.getHeight() + i < this.$view.getHeight()) {
                        ((Map) ref$ObjectRef.element).put(Float.valueOf(100.0f), Integer.valueOf(findFirstVisibleItemPosition));
                    } else {
                        float height2 = ((this.$view.getHeight() - i) * 100) / view.getHeight();
                        if (height2 > 40.0f) {
                            ((Map) ref$ObjectRef.element).put(Float.valueOf(height2), Integer.valueOf(findFirstVisibleItemPosition));
                        }
                    }
                }
                if (findFirstVisibleItemPosition == findLastVisibleItemPosition) {
                    break;
                }
                findFirstVisibleItemPosition++;
            }
        }
        AsyncKt.uiThread(receiver, new C26011(ref$ObjectRef));
    }
}
