package com.tomatolive.library.p136ui.adapter;

import android.support.p002v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.WeekStarAnchorEntity;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.StringUtils;

/* renamed from: com.tomatolive.library.ui.adapter.WeekStarShineAdapter */
/* loaded from: classes3.dex */
public class WeekStarShineAdapter extends BaseQuickAdapter<WeekStarAnchorEntity, BaseViewHolder> {
    public WeekStarShineAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, WeekStarAnchorEntity weekStarAnchorEntity) {
        baseViewHolder.setText(R$id.tv_gift_name, StringUtils.formatStrLen(weekStarAnchorEntity.giftName, 4)).setText(R$id.tv_anchor_name, formatAnchorName(weekStarAnchorEntity.anchorName)).setText(R$id.tv_contribution, this.mContext.getString(R$string.fq_week_star_receive, weekStarAnchorEntity.starGiftNum)).setVisible(R$id.iv_live, AppUtils.isLiving(weekStarAnchorEntity.liveStatus));
        GlideUtils.loadAvatar(this.mContext, (ImageView) baseViewHolder.getView(R$id.iv_avatar), weekStarAnchorEntity.avatar, 6, ContextCompat.getColor(this.mContext, R$color.fq_colorWhite));
        GlideUtils.loadImage(this.mContext, (ImageView) baseViewHolder.getView(R$id.iv_gift), weekStarAnchorEntity.imgurl, R$drawable.fq_ic_gift_default);
        if (AppUtils.isLiving(weekStarAnchorEntity.liveStatus)) {
            GlideUtils.loadLivingGif(this.mContext, (ImageView) baseViewHolder.getView(R$id.iv_live));
        }
    }

    private String formatAnchorName(String str) {
        return TextUtils.isEmpty(str) ? this.mContext.getString(R$string.fq_text_list_empty_waiting) : AppUtils.formatUserNickName(str);
    }
}
