package com.one.tomato.mvp.p080ui.game.view;

import com.one.tomato.entity.p079db.SubGamesBean;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: GameTypeScrollTabFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.game.view.GameTypeScrollTabFragment$addScroll$4 */
/* loaded from: classes3.dex */
public final class GameTypeScrollTabFragment$addScroll$4 extends Lambda implements Function1<SubGamesBean, Unit> {
    final /* synthetic */ GameTypeScrollTabFragment this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GameTypeScrollTabFragment$addScroll$4(GameTypeScrollTabFragment gameTypeScrollTabFragment) {
        super(1);
        this.this$0 = gameTypeScrollTabFragment;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public /* bridge */ /* synthetic */ Unit mo6794invoke(SubGamesBean subGamesBean) {
        invoke2(subGamesBean);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    public final void invoke2(SubGamesBean it2) {
        Intrinsics.checkParameterIsNotNull(it2, "it");
        this.this$0.requestBGH5Login(it2);
    }
}
