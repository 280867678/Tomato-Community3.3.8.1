package com.one.tomato.mvp.p080ui.post.view;

import android.graphics.Bitmap;
import android.widget.ImageView;
import com.one.tomato.R$id;
import com.one.tomato.entity.PostList;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: NewCommentHeadView.kt */
/* renamed from: com.one.tomato.mvp.ui.post.view.NewCommentHeadView$refreshData$$inlined$let$lambda$2 */
/* loaded from: classes3.dex */
final class NewCommentHeadView$refreshData$$inlined$let$lambda$2 extends Lambda implements Function1<Bitmap, Unit> {
    final /* synthetic */ PostList $postList$inlined;
    final /* synthetic */ NewCommentHeadView this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NewCommentHeadView$refreshData$$inlined$let$lambda$2(NewCommentHeadView newCommentHeadView, PostList postList) {
        super(1);
        this.this$0 = newCommentHeadView;
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
        if (bitmap != null) {
            ((ImageView) this.this$0._$_findCachedViewById(R$id.iv_single_image)).setImageBitmap(bitmap);
        }
    }
}
