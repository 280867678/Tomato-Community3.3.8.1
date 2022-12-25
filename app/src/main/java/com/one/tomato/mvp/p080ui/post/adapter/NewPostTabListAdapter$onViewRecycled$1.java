package com.one.tomato.mvp.p080ui.post.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.broccoli.p150bh.R;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.Vector;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.anko.Async;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: NewPostTabListAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.post.adapter.NewPostTabListAdapter$onViewRecycled$1 */
/* loaded from: classes3.dex */
public final class NewPostTabListAdapter$onViewRecycled$1 extends Lambda implements Function1<Async<NewPostTabListAdapter>, Unit> {
    final /* synthetic */ BaseViewHolder $holder;
    final /* synthetic */ NewPostTabListAdapter this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NewPostTabListAdapter$onViewRecycled$1(NewPostTabListAdapter newPostTabListAdapter, BaseViewHolder baseViewHolder) {
        super(1);
        this.this$0 = newPostTabListAdapter;
        this.$holder = baseViewHolder;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public /* bridge */ /* synthetic */ Unit mo6794invoke(Async<NewPostTabListAdapter> async) {
        invoke2(async);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    public final void invoke2(Async<NewPostTabListAdapter> receiver) {
        View childAt;
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        LinearLayout linearLayout = (LinearLayout) this.$holder.itemView.findViewById(R.id.liner_content);
        if (linearLayout == null || linearLayout.getChildCount() <= 0 || (childAt = linearLayout.getChildAt(0)) == null) {
            return;
        }
        ImageView imageView = (ImageView) childAt.findViewById(R.id.image_video_cove);
        if (imageView != null) {
            Glide.with(((BaseQuickAdapter) this.this$0).mContext).clear(imageView);
        }
        linearLayout.removeView(childAt);
        Vector vector = this.this$0.vector;
        if (vector == null) {
            return;
        }
        vector.addElement(childAt);
    }
}
