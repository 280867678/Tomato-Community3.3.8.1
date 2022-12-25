package com.tomatolive.library.p136ui.adapter;

import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.p136ui.view.custom.AnchorGradeView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.StringUtils;

/* renamed from: com.tomatolive.library.ui.adapter.SearchAllAnchorAdapter */
/* loaded from: classes3.dex */
public class SearchAllAnchorAdapter extends BaseQuickAdapter<AnchorEntity, BaseViewHolder> {
    private String keyword = "";

    public SearchAllAnchorAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, AnchorEntity anchorEntity) {
        baseViewHolder.setText(R$id.tv_nick_name, StringUtils.getHighLightText(this.mContext, StringUtils.formatStrLen(anchorEntity.nickname, 6), this.keyword, R$color.fq_colorPrimary)).setText(R$id.tv_attention, this.mContext.getString(R$string.fq_text_attention_num, AppUtils.formatTenThousandUnit(anchorEntity.followerCount))).setVisible(R$id.iv_live, AppUtils.isLiving(anchorEntity.isLiving));
        GlideUtils.loadLivingGif(this.mContext, (ImageView) baseViewHolder.getView(R$id.iv_live));
        GlideUtils.loadAvatar(this.mContext, (ImageView) baseViewHolder.getView(R$id.iv_avatar), anchorEntity.avatar);
        ((AnchorGradeView) baseViewHolder.getView(R$id.anchor_grade_view)).initUserGrade(anchorEntity.expGrade);
        ImageView imageView = (ImageView) baseViewHolder.getView(R$id.iv_gender_sex);
        if (AppUtils.getGenderRes(anchorEntity.sex) != -1) {
            imageView.setImageResource(AppUtils.getGenderRes(anchorEntity.sex));
        }
    }

    public String getKeyword() {
        return this.keyword;
    }

    public void setKeyword(String str) {
        this.keyword = str;
    }
}
