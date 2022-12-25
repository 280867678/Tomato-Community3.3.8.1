package com.one.tomato.mvp.p080ui.mine.view;

import com.one.tomato.dialog.UpNotifyDialog;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Lambda;

/* compiled from: MineTabFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.mine.view.MineTabFragment$showUpDialog$1 */
/* loaded from: classes3.dex */
final class MineTabFragment$showUpDialog$1 extends Lambda implements Functions<Unit> {
    final /* synthetic */ UpNotifyDialog $dialog;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MineTabFragment$showUpDialog$1(UpNotifyDialog upNotifyDialog) {
        super(0);
        this.$dialog = upNotifyDialog;
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
        this.$dialog.dismiss();
    }
}
