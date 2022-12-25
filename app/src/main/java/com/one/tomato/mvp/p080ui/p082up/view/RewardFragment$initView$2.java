package com.one.tomato.mvp.p080ui.p082up.view;

import android.widget.TextView;
import com.one.tomato.R$id;
import com.one.tomato.utils.FormatUtil;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: RewardFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.up.view.RewardFragment$initView$2 */
/* loaded from: classes3.dex */
final class RewardFragment$initView$2 extends Lambda implements Function1<String, Unit> {
    final /* synthetic */ RewardFragment this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RewardFragment$initView$2(RewardFragment rewardFragment) {
        super(1);
        this.this$0 = rewardFragment;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public /* bridge */ /* synthetic */ Unit mo6794invoke(String str) {
        invoke2(str);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    public final void invoke2(String it2) {
        Intrinsics.checkParameterIsNotNull(it2, "it");
        this.this$0.currentTomato = it2;
        TextView textView = (TextView) this.this$0._$_findCachedViewById(R$id.text_balance);
        if (textView != null) {
            textView.setText(FormatUtil.formatTomato2RMB(it2));
        }
    }
}
