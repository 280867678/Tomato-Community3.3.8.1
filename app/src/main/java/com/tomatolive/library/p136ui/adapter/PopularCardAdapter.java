package com.tomatolive.library.p136ui.adapter;

import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jakewharton.rxbinding2.view.RxView;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.PopularCardEntity;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import java.util.concurrent.TimeUnit;

/* renamed from: com.tomatolive.library.ui.adapter.PopularCardAdapter */
/* loaded from: classes3.dex */
public class PopularCardAdapter extends BaseQuickAdapter<PopularCardEntity, BaseViewHolder> {
    private OnPopularCardListener listener;

    /* renamed from: com.tomatolive.library.ui.adapter.PopularCardAdapter$OnPopularCardListener */
    /* loaded from: classes3.dex */
    public interface OnPopularCardListener {
        void onItemClick(PopularCardEntity popularCardEntity);
    }

    public PopularCardAdapter(int i) {
        super(i);
    }

    public void setOnPopularCardListener(OnPopularCardListener onPopularCardListener) {
        this.listener = onPopularCardListener;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, final PopularCardEntity popularCardEntity) {
        GlideUtils.loadImage(this.mContext, (ImageView) baseViewHolder.getView(R$id.iv_card_style), popularCardEntity.coverUrl, R$drawable.fq_ic_week_star_popularity_card_default);
        baseViewHolder.setText(R$id.tv_card_value, getCardValue(popularCardEntity));
        baseViewHolder.setText(R$id.tv_card_time, this.mContext.getString(R$string.fq_popular_card_time, popularCardEntity.duration));
        baseViewHolder.setText(R$id.tv_card_remaining, this.mContext.getString(R$string.fq_popular_card_remaining, popularCardEntity.validDays));
        RxView.clicks(baseViewHolder.itemView).throttleFirst(500L, TimeUnit.MILLISECONDS).subscribe(new SimpleRxObserver<Object>() { // from class: com.tomatolive.library.ui.adapter.PopularCardAdapter.1
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Object obj) {
                if (PopularCardAdapter.this.listener != null) {
                    PopularCardAdapter.this.listener.onItemClick(popularCardEntity);
                }
            }
        });
    }

    private String getCardValue(PopularCardEntity popularCardEntity) {
        return popularCardEntity.name + " + " + popularCardEntity.addition;
    }
}
