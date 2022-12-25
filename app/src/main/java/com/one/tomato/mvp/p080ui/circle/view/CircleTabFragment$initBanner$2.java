package com.one.tomato.mvp.p080ui.circle.view;

import com.one.tomato.mvp.p080ui.papa.adapter.BannerViewHolder;
import com.one.tomato.widget.mzbanner.holder.MZHolderCreator;
import com.one.tomato.widget.mzbanner.holder.MZViewHolder;

/* compiled from: CircleTabFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.circle.view.CircleTabFragment$initBanner$2 */
/* loaded from: classes3.dex */
final class CircleTabFragment$initBanner$2<VH extends MZViewHolder<Object>> implements MZHolderCreator<MZViewHolder<?>> {
    public static final CircleTabFragment$initBanner$2 INSTANCE = new CircleTabFragment$initBanner$2();

    CircleTabFragment$initBanner$2() {
    }

    @Override // com.one.tomato.widget.mzbanner.holder.MZHolderCreator
    /* renamed from: createViewHolder */
    public final MZViewHolder<?> createViewHolder2() {
        return new BannerViewHolder();
    }
}
