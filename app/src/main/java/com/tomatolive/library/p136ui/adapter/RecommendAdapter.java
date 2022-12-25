package com.tomatolive.library.p136ui.adapter;

import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.StringUtils;

/* renamed from: com.tomatolive.library.ui.adapter.RecommendAdapter */
/* loaded from: classes3.dex */
public class RecommendAdapter extends BaseQuickAdapter<AnchorEntity, BaseViewHolder> {
    public RecommendAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, AnchorEntity anchorEntity) {
        baseViewHolder.setText(R$id.tv_nick_name, StringUtils.formatStrLen(anchorEntity.nickname, 6)).setText(R$id.tv_attention, anchorEntity.isAttention() ? R$string.fq_home_btn_attention_yes : R$string.fq_home_btn_attention).setVisible(R$id.iv_live, AppUtils.isLiving(anchorEntity.isLiving)).addOnClickListener(R$id.tv_attention).getView(R$id.tv_attention).setSelected(anchorEntity.isAttention());
        GlideUtils.loadAvatar(this.mContext, (ImageView) baseViewHolder.getView(R$id.iv_avatar), anchorEntity.avatar);
        GlideUtils.loadLivingGif(this.mContext, (ImageView) baseViewHolder.getView(R$id.iv_live));
        ImageView imageView = (ImageView) baseViewHolder.getView(R$id.iv_gender_sex);
        if (AppUtils.getGenderRes(anchorEntity.sex) != -1) {
            imageView.setImageResource(AppUtils.getGenderRes(anchorEntity.sex));
        }
    }
}
