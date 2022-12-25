package com.one.tomato.mvp.p080ui.post.item;

import android.graphics.Bitmap;
import android.widget.ImageView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.entity.PostList;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: PostVideoItem.kt */
/* renamed from: com.one.tomato.mvp.ui.post.item.PostVideoItem$setPostListData$$inlined$let$lambda$1 */
/* loaded from: classes3.dex */
final class PostVideoItem$setPostListData$$inlined$let$lambda$1 extends Lambda implements Function1<Bitmap, Unit> {
    final /* synthetic */ PostList $postList$inlined;
    final /* synthetic */ PostVideoItem this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PostVideoItem$setPostListData$$inlined$let$lambda$1(PostVideoItem postVideoItem, PostList postList) {
        super(1);
        this.this$0 = postVideoItem;
        this.$postList$inlined = postList;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public /* bridge */ /* synthetic */ Unit mo6794invoke(Bitmap bitmap) {
        invoke2(bitmap);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    public final void invoke2(Bitmap bitmap) {
        if (bitmap instanceof Bitmap) {
            ImageView imageView = (ImageView) this.this$0._$_findCachedViewById(R$id.image_bg_blur);
            if (!Intrinsics.areEqual(this.$postList$inlined.getSecVideoCover(), (String) (imageView != null ? imageView.getTag(R.id.glide_load_image_id) : null))) {
                return;
            }
            ImageView imageView2 = (ImageView) this.this$0._$_findCachedViewById(R$id.image_bg_blur);
            if (imageView2 != null) {
                imageView2.setVisibility(0);
            }
            ((ImageView) this.this$0._$_findCachedViewById(R$id.image_bg_blur)).setImageBitmap(bitmap);
        }
    }
}
