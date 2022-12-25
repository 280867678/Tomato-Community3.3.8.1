package com.one.tomato.mvp.p080ui.papa.view;

import com.one.tomato.mvp.p080ui.papa.adapter.BannerViewHolder;
import com.one.tomato.widget.mzbanner.holder.MZHolderCreator;
import com.one.tomato.widget.mzbanner.holder.MZViewHolder;

/* compiled from: PaPaHotHeadView.kt */
/* renamed from: com.one.tomato.mvp.ui.papa.view.PaPaHotHeadView$initBanner$2 */
/* loaded from: classes3.dex */
final class PaPaHotHeadView$initBanner$2<VH extends MZViewHolder<Object>> implements MZHolderCreator<MZViewHolder<?>> {
    public static final PaPaHotHeadView$initBanner$2 INSTANCE = new PaPaHotHeadView$initBanner$2();

    PaPaHotHeadView$initBanner$2() {
    }

    @Override // com.one.tomato.widget.mzbanner.holder.MZHolderCreator
    /* renamed from: createViewHolder */
    public final MZViewHolder<?> createViewHolder2() {
        return new BannerViewHolder();
    }
}
