package com.one.tomato.mvp.p080ui.game.view;

import com.one.tomato.mvp.p080ui.papa.adapter.BannerViewHolder;
import com.one.tomato.widget.mzbanner.holder.MZHolderCreator;
import com.one.tomato.widget.mzbanner.holder.MZViewHolder;

/* compiled from: GameTypeScrollTabFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.game.view.GameTypeScrollTabFragment$initBanner$2 */
/* loaded from: classes3.dex */
final class GameTypeScrollTabFragment$initBanner$2<VH extends MZViewHolder<Object>> implements MZHolderCreator<MZViewHolder<?>> {
    public static final GameTypeScrollTabFragment$initBanner$2 INSTANCE = new GameTypeScrollTabFragment$initBanner$2();

    GameTypeScrollTabFragment$initBanner$2() {
    }

    @Override // com.one.tomato.widget.mzbanner.holder.MZHolderCreator
    /* renamed from: createViewHolder */
    public final MZViewHolder<?> createViewHolder2() {
        return new BannerViewHolder();
    }
}
