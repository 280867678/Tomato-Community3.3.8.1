package com.tomatolive.library.p136ui.adapter;

import android.support.p002v4.app.Fragment;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.p136ui.view.custom.UserNickNameGradeView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.StringUtils;

/* renamed from: com.tomatolive.library.ui.adapter.SearchAnchorAdapter */
/* loaded from: classes3.dex */
public class SearchAnchorAdapter extends BaseQuickAdapter<AnchorEntity, BaseViewHolder> {
    private Fragment fragment;
    private String keyWord = "";

    public SearchAnchorAdapter(Fragment fragment, int i) {
        super(i);
        this.fragment = fragment;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, AnchorEntity anchorEntity) {
        baseViewHolder.setText(R$id.tv_attention_num, this.mContext.getString(R$string.fq_text_attention_num, AppUtils.formatTenThousandUnit(anchorEntity.followerCount))).setVisible(R$id.iv_live, AppUtils.isLiving(anchorEntity.isLiving)).addOnClickListener(R$id.tv_attention).getView(R$id.tv_attention).setSelected(anchorEntity.isAttention());
        ((UserNickNameGradeView) baseViewHolder.getView(R$id.user_name_grade_view)).initAnchorData(StringUtils.getHighLightText(this.mContext, anchorEntity.nickname, this.keyWord, R$color.fq_colorPrimary), anchorEntity.sex, anchorEntity.expGrade, anchorEntity.nobilityType);
        GlideUtils.loadAvatar(this.fragment, (ImageView) baseViewHolder.getView(R$id.iv_avatar), anchorEntity.avatar);
        GlideUtils.loadLivingGif(this.mContext, (ImageView) baseViewHolder.getView(R$id.iv_live));
    }

    public String getKeyWord() {
        return this.keyWord;
    }

    public void setKeyWord(String str) {
        this.keyWord = str;
    }
}
