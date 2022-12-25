package com.one.tomato.mvp.p080ui.mine.view;

import com.one.tomato.entity.PostList;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: ChoosePostFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.mine.view.ChoosePostFragment$initView$1 */
/* loaded from: classes3.dex */
final class ChoosePostFragment$initView$1 extends Lambda implements Function1<PostList, Unit> {
    final /* synthetic */ ChoosePostFragment this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ChoosePostFragment$initView$1(ChoosePostFragment choosePostFragment) {
        super(1);
        this.this$0 = choosePostFragment;
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
        this.this$0.selectSamePostType(it2);
    }
}
