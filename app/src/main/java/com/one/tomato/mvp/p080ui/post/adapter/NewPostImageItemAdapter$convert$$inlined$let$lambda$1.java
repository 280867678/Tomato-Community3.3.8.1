package com.one.tomato.mvp.p080ui.post.adapter;

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.one.tomato.entity.ImageBean;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: NewPostImageItemAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.post.adapter.NewPostImageItemAdapter$convert$$inlined$let$lambda$1 */
/* loaded from: classes3.dex */
final class NewPostImageItemAdapter$convert$$inlined$let$lambda$1 extends Lambda implements Function1<Bitmap, Unit> {
    final /* synthetic */ ImageView $image$inlined;
    final /* synthetic */ ImageBean $itemData$inlined;
    final /* synthetic */ ProgressBar $loading$inlined;
    final /* synthetic */ RelativeLayout $relate_image_layer$inlined;
    final /* synthetic */ NewPostImageItemAdapter this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NewPostImageItemAdapter$convert$$inlined$let$lambda$1(NewPostImageItemAdapter newPostImageItemAdapter, ImageView imageView, ImageBean imageBean, ProgressBar progressBar, RelativeLayout relativeLayout) {
        super(1);
        this.this$0 = newPostImageItemAdapter;
        this.$image$inlined = imageView;
        this.$itemData$inlined = imageBean;
        this.$loading$inlined = progressBar;
        this.$relate_image_layer$inlined = relativeLayout;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public /* bridge */ /* synthetic */ Unit mo6794invoke(Bitmap bitmap) {
        invoke2(bitmap);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    public final void invoke2(Bitmap bitmap) {
        ImageView imageView;
        if (bitmap == null || (imageView = this.$image$inlined) == null) {
            return;
        }
        imageView.setImageBitmap(bitmap);
    }
}
