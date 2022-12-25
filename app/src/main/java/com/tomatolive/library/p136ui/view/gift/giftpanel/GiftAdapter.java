package com.tomatolive.library.p136ui.view.gift.giftpanel;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.p002v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.GiftDownloadItemEntity;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.GlideUtils;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.gift.giftpanel.GiftAdapter */
/* loaded from: classes3.dex */
public class GiftAdapter extends BaseQuickAdapter<GiftDownloadItemEntity, BaseViewHolder> {
    private int selectedPosition = -1;

    public GiftAdapter(@Nullable List<GiftDownloadItemEntity> list) {
        super(R$layout.fq_item_grid_gift, list);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, GiftDownloadItemEntity giftDownloadItemEntity) {
        baseViewHolder.setText(R$id.tv_item_title, giftDownloadItemEntity.name).setTextColor(R$id.tv_item_title, ContextCompat.getColor(this.mContext, giftDownloadItemEntity.isStayTuned ? R$color.fq_colorGray66 : R$color.fq_colorWhite)).setVisible(R$id.iv_label, isShowLabel(giftDownloadItemEntity)).setImageResource(R$id.iv_label, getLabelDrawableRes(giftDownloadItemEntity)).setVisible(R$id.iv_last_week_anchor_head, giftDownloadItemEntity.isLastWeekStarGift()).setVisible(R$id.tv_cur_week_star, giftDownloadItemEntity.isCurWeekStarGift()).getView(R$id.ll_item_layout).setSelected(this.selectedPosition == baseViewHolder.getAdapterPosition());
        ImageView imageView = (ImageView) baseViewHolder.getView(R$id.iv_item_logo);
        if (!TextUtils.isEmpty(giftDownloadItemEntity.imgurl)) {
            GlideUtils.loadImage(this.mContext, imageView, giftDownloadItemEntity.imgurl, R$drawable.fq_ic_gift_default);
        } else {
            GlideUtils.loadImage(this.mContext, imageView, R$drawable.fq_live_giftlist_comingsoon);
        }
        TextView textView = (TextView) baseViewHolder.getView(R$id.tv_item_desc);
        if (giftDownloadItemEntity.isStayTuned) {
            textView.setVisibility(4);
        } else {
            textView.setVisibility(0);
            textView.setText(giftDownloadItemEntity.isScoreGift() ? this.mContext.getString(R$string.fq_gift_score, giftDownloadItemEntity.price) : AppUtils.formatDisplayPrice(giftDownloadItemEntity.price, false));
            if (giftDownloadItemEntity.isScoreGift()) {
                textView.setCompoundDrawables(null, null, null, null);
            } else {
                AppUtils.setTextViewLeftDrawable(this.mContext, textView, R$drawable.fq_ic_placeholder_gold, 13, 13);
            }
        }
        if (giftDownloadItemEntity.isLastWeekStarGift()) {
            Context context = this.mContext;
            GlideUtils.loadAvatar(context, (ImageView) baseViewHolder.getView(R$id.iv_last_week_anchor_head), giftDownloadItemEntity.avatar, 1, ContextCompat.getColor(context, R$color.fq_colorWhite));
        }
    }

    public void setSelectedPosition(int i) {
        this.selectedPosition = i;
        notifyDataSetChanged();
    }

    public void clearSelectedPosition() {
        View viewByPosition;
        if (isPosition(this.selectedPosition) && (viewByPosition = getViewByPosition(this.selectedPosition, R$id.ll_item_layout)) != null) {
            viewByPosition.setSelected(false);
        }
    }

    public int getSelectedPosition() {
        return this.selectedPosition;
    }

    private boolean isPosition(int i) {
        return i >= 0 && i < getData().size();
    }

    private boolean isShowLabel(GiftDownloadItemEntity giftDownloadItemEntity) {
        if (giftDownloadItemEntity.isStayTuned || giftDownloadItemEntity.isCurWeekStarGift()) {
            return false;
        }
        if (!giftDownloadItemEntity.isLuckyGift()) {
            return giftDownloadItemEntity.isBroadcastFlag();
        }
        return true;
    }

    @DrawableRes
    private int getLabelDrawableRes(GiftDownloadItemEntity giftDownloadItemEntity) {
        if (giftDownloadItemEntity.isLuckyGift()) {
            return R$drawable.fq_ic_gift_lucky_turntable;
        }
        return R$drawable.fq_ic_gift_broadcast;
    }
}
