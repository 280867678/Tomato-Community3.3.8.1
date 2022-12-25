package com.one.tomato.mvp.p080ui.papa.view;

import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Lambda;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: NewPaPaVideoPlayFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.papa.view.NewPaPaVideoPlayFragment$setPostNeedPay$2 */
/* loaded from: classes3.dex */
public final class NewPaPaVideoPlayFragment$setPostNeedPay$2 extends Lambda implements Functions<Unit> {
    final /* synthetic */ NewPaPaVideoPlayFragment this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NewPaPaVideoPlayFragment$setPostNeedPay$2(NewPaPaVideoPlayFragment newPaPaVideoPlayFragment) {
        super(0);
        this.this$0 = newPaPaVideoPlayFragment;
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
        this.this$0.onVideoRelease();
    }
}
