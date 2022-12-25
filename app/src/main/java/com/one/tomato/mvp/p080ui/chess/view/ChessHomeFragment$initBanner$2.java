package com.one.tomato.mvp.p080ui.chess.view;

import com.one.tomato.mvp.p080ui.papa.adapter.BannerViewHolder;
import com.one.tomato.widget.mzbanner.holder.MZHolderCreator;
import com.one.tomato.widget.mzbanner.holder.MZViewHolder;

/* compiled from: ChessHomeFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.chess.view.ChessHomeFragment$initBanner$2 */
/* loaded from: classes3.dex */
final class ChessHomeFragment$initBanner$2<VH extends MZViewHolder<Object>> implements MZHolderCreator<MZViewHolder<?>> {
    public static final ChessHomeFragment$initBanner$2 INSTANCE = new ChessHomeFragment$initBanner$2();

    ChessHomeFragment$initBanner$2() {
    }

    @Override // com.one.tomato.widget.mzbanner.holder.MZHolderCreator
    /* renamed from: createViewHolder */
    public final MZViewHolder<?> createViewHolder2() {
        return new BannerViewHolder(true);
    }
}
