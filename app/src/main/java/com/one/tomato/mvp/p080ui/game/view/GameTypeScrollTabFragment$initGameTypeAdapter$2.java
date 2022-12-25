package com.one.tomato.mvp.p080ui.game.view;

import com.one.tomato.entity.p079db.GameTypeData;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.GameUtils;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Lambda;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: GameTypeScrollTabFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.game.view.GameTypeScrollTabFragment$initGameTypeAdapter$2 */
/* loaded from: classes3.dex */
public final class GameTypeScrollTabFragment$initGameTypeAdapter$2 extends Lambda implements Functions<Unit> {
    final /* synthetic */ GameTypeScrollTabFragment this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GameTypeScrollTabFragment$initGameTypeAdapter$2(GameTypeScrollTabFragment gameTypeScrollTabFragment) {
        super(0);
        this.this$0 = gameTypeScrollTabFragment;
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
        BaseRecyclerViewAdapter baseRecyclerViewAdapter;
        BaseRecyclerViewAdapter baseRecyclerViewAdapter2;
        List<T> data;
        baseRecyclerViewAdapter = this.this$0.gameTypeAdapter;
        if (baseRecyclerViewAdapter != null && (data = baseRecyclerViewAdapter.getData()) != 0) {
            GameTypeData gameTypeData = (GameTypeData) data.remove(0);
        }
        baseRecyclerViewAdapter2 = this.this$0.gameTypeAdapter;
        if (baseRecyclerViewAdapter2 != null) {
            baseRecyclerViewAdapter2.notifyDataSetChanged();
        }
        GameUtils.INSTANCE.deleteGameBean();
    }
}
