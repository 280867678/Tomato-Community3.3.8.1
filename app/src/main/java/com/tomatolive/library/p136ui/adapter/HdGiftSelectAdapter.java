package com.tomatolive.library.p136ui.adapter;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.model.GiftDownloadItemEntity;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.StringUtils;

/* renamed from: com.tomatolive.library.ui.adapter.HdGiftSelectAdapter */
/* loaded from: classes3.dex */
public class HdGiftSelectAdapter extends BaseQuickAdapter<GiftDownloadItemEntity, BaseViewHolder> {
    private boolean isQMInteract;
    private int selectedPosition;

    public HdGiftSelectAdapter(int i) {
        super(i);
        this.selectedPosition = -1;
        this.isQMInteract = false;
    }

    public HdGiftSelectAdapter(int i, boolean z) {
        super(i);
        this.selectedPosition = -1;
        this.isQMInteract = false;
        this.isQMInteract = z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, GiftDownloadItemEntity giftDownloadItemEntity) {
        if (giftDownloadItemEntity == null) {
            return;
        }
        int i = 0;
        boolean z = this.selectedPosition == baseViewHolder.getAdapterPosition();
        baseViewHolder.setText(R$id.tv_item_title, giftDownloadItemEntity.name).setText(R$id.tv_item_desc, AppUtils.getLiveMoneyUnitStr(this.mContext, AppUtils.formatDisplayPrice(giftDownloadItemEntity.price, false)));
        GlideUtils.loadImage(this.mContext, (ImageView) baseViewHolder.getView(R$id.iv_item_logo), giftDownloadItemEntity.imgurl, R$drawable.fq_ic_gift_default);
        RelativeLayout relativeLayout = (RelativeLayout) baseViewHolder.getView(R$id.ll_item_layout);
        relativeLayout.setBackgroundResource(this.isQMInteract ? R$drawable.fq_qm_shape_gift_item_bg : R$drawable.fq_hd_shape_gift_item_bg);
        relativeLayout.setSelected(z);
        ImageView imageView = (ImageView) baseViewHolder.getView(R$id.iv_selected);
        imageView.setImageResource(this.isQMInteract ? R$drawable.fq_ic_qm_check_selected : R$drawable.fq_ic_hd_lottery_check_selected);
        if (!z) {
            i = 4;
        }
        imageView.setVisibility(i);
    }

    public void setCheckItem(int i) {
        this.selectedPosition = i;
        notifyDataSetChanged();
    }

    public String getGiftTips(GiftDownloadItemEntity giftDownloadItemEntity) {
        return StringUtils.formatStrLen(giftDownloadItemEntity.name, 5) + "   " + AppUtils.getLiveMoneyUnitStr(this.mContext, AppUtils.formatDisplayPrice(giftDownloadItemEntity.price, false));
    }
}
