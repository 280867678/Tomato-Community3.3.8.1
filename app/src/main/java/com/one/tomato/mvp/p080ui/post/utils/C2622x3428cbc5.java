package com.one.tomato.mvp.p080ui.post.utils;

import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.p080ui.post.utils.PostUtils$showImageNeedPayDialog$1;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: PostUtils.kt */
/* renamed from: com.one.tomato.mvp.ui.post.utils.PostUtils$showImageNeedPayDialog$1$3$$special$$inlined$let$lambda$2 */
/* loaded from: classes3.dex */
final class C2622x3428cbc5 extends Lambda implements Function1<ResponseThrowable, Unit> {
    final /* synthetic */ PostUtils$showImageNeedPayDialog$1.View$OnClickListenerC26253 this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C2622x3428cbc5(PostUtils$showImageNeedPayDialog$1.View$OnClickListenerC26253 view$OnClickListenerC26253) {
        super(1);
        this.this$0 = view$OnClickListenerC26253;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public /* bridge */ /* synthetic */ Unit mo6794invoke(ResponseThrowable responseThrowable) {
        invoke2(responseThrowable);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    public final void invoke2(ResponseThrowable responseThrowable) {
        this.this$0.$dialog.setLoadingVisiby(false);
    }
}
