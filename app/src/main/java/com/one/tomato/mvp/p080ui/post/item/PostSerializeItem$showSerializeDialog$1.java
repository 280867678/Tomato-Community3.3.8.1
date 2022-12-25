package com.one.tomato.mvp.p080ui.post.item;

import com.one.tomato.entity.PostList;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: PostSerializeItem.kt */
/* renamed from: com.one.tomato.mvp.ui.post.item.PostSerializeItem$showSerializeDialog$1 */
/* loaded from: classes3.dex */
public final class PostSerializeItem$showSerializeDialog$1 extends Lambda implements Function1<PostList, Unit> {
    final /* synthetic */ PostSerializeItem this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PostSerializeItem$showSerializeDialog$1(PostSerializeItem postSerializeItem) {
        super(1);
        this.this$0 = postSerializeItem;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public /* bridge */ /* synthetic */ Unit mo6794invoke(PostList postList) {
        invoke2(postList);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    public final void invoke2(PostList it2) {
        Intrinsics.checkParameterIsNotNull(it2, "it");
        Function1 function1 = this.this$0.onClickCallBack;
        if (function1 != null) {
            Unit unit = (Unit) function1.mo6794invoke(it2);
        }
        this.this$0.scrollPostSelect(it2.getId());
    }
}
