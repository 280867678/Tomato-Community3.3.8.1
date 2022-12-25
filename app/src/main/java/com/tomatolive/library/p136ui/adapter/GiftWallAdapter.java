package com.tomatolive.library.p136ui.adapter;

import android.support.p002v4.content.ContextCompat;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.model.GiftWallEntity;
import com.tomatolive.library.utils.GlideUtils;

/* renamed from: com.tomatolive.library.ui.adapter.GiftWallAdapter */
/* loaded from: classes3.dex */
public class GiftWallAdapter extends BaseQuickAdapter<GiftWallEntity.GiftWallGiftItemEntity, BaseViewHolder> {
    public GiftWallAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, GiftWallEntity.GiftWallGiftItemEntity giftWallGiftItemEntity) {
        baseViewHolder.setText(R$id.iv_gift_name, giftWallGiftItemEntity.name).setTextColor(R$id.iv_gift_name, ContextCompat.getColor(this.mContext, giftWallGiftItemEntity.isLight ? R$color.fq_colorBlack : R$color.fq_text_gray));
        if (giftWallGiftItemEntity.isLight) {
            GlideUtils.loadImage(this.mContext, (ImageView) baseViewHolder.getView(R$id.iv_gift_img), giftWallGiftItemEntity.imgurl, R$drawable.fq_live_giftlist_comingsoon);
        } else {
            GlideUtils.loadImageByGray(this.mContext, (ImageView) baseViewHolder.getView(R$id.iv_gift_img), giftWallGiftItemEntity.imgurl, R$drawable.fq_live_giftlist_comingsoon);
        }
    }
}
