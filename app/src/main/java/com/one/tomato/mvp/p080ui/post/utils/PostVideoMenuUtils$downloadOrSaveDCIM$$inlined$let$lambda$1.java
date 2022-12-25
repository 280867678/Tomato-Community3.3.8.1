package com.one.tomato.mvp.p080ui.post.utils;

import com.one.tomato.entity.PostList;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Lambda;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: PostVideoMenuUtils.kt */
/* renamed from: com.one.tomato.mvp.ui.post.utils.PostVideoMenuUtils$downloadOrSaveDCIM$$inlined$let$lambda$1 */
/* loaded from: classes3.dex */
public final class PostVideoMenuUtils$downloadOrSaveDCIM$$inlined$let$lambda$1 extends Lambda implements Functions<Unit> {
    final /* synthetic */ boolean $download$inlined;
    final /* synthetic */ PostList $postList$inlined;
    final /* synthetic */ PostVideoMenuUtils this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PostVideoMenuUtils$downloadOrSaveDCIM$$inlined$let$lambda$1(PostVideoMenuUtils postVideoMenuUtils, PostList postList, boolean z) {
        super(0);
        this.this$0 = postVideoMenuUtils;
        this.$postList$inlined = postList;
        this.$download$inlined = z;
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
        this.this$0.download(true);
    }
}
