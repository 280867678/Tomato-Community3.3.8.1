package com.one.tomato.mvp.p080ui.post.view;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.entity.PostList;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: NewDetailViewPagerRecyclerFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.post.view.NewDetailViewPagerRecyclerFragment$initDataToView$2 */
/* loaded from: classes3.dex */
public final class NewDetailViewPagerRecyclerFragment$initDataToView$2 extends Lambda implements Function1<Bitmap, Unit> {
    final /* synthetic */ NewDetailViewPagerRecyclerFragment this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NewDetailViewPagerRecyclerFragment$initDataToView$2(NewDetailViewPagerRecyclerFragment newDetailViewPagerRecyclerFragment) {
        super(1);
        this.this$0 = newDetailViewPagerRecyclerFragment;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public /* bridge */ /* synthetic */ Unit mo6794invoke(Bitmap bitmap) {
        invoke2(bitmap);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    public final void invoke2(Bitmap bitmap) {
        PostList postList;
        if (bitmap instanceof Bitmap) {
            ImageView imageView = (ImageView) this.this$0._$_findCachedViewById(R$id.image_bg_blur);
            String str = null;
            String str2 = (String) (imageView != null ? imageView.getTag(R.id.glide_load_image_id) : null);
            postList = this.this$0.itemData;
            if (postList != null) {
                str = postList.getSecVideoCover();
            }
            if (!Intrinsics.areEqual(str, str2)) {
                return;
            }
            View _$_findCachedViewById = this.this$0._$_findCachedViewById(R$id.background_view);
            if (_$_findCachedViewById != null) {
                _$_findCachedViewById.setVisibility(0);
            }
            ImageView imageView2 = (ImageView) this.this$0._$_findCachedViewById(R$id.image_bg_blur);
            if (imageView2 != null) {
                imageView2.setVisibility(0);
            }
            ((ImageView) this.this$0._$_findCachedViewById(R$id.image_bg_blur)).setImageBitmap(bitmap);
        }
    }
}
