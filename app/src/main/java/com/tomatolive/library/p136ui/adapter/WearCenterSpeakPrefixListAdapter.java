package com.tomatolive.library.p136ui.adapter;

import android.text.TextUtils;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.MedalEntity;
import com.tomatolive.library.utils.GlideUtils;

/* renamed from: com.tomatolive.library.ui.adapter.WearCenterSpeakPrefixListAdapter */
/* loaded from: classes3.dex */
public class WearCenterSpeakPrefixListAdapter extends BaseQuickAdapter<MedalEntity, BaseViewHolder> {
    private int pos = -1;

    public WearCenterSpeakPrefixListAdapter(int i) {
        super(i);
    }

    public void setSelect(int i) {
        this.pos = i;
        notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, MedalEntity medalEntity) {
        baseViewHolder.getView(R$id.rl_root).setSelected(this.pos == baseViewHolder.getAdapterPosition());
        GlideUtils.loadImage(this.mContext, (ImageView) baseViewHolder.getView(R$id.iv_achieve_wear_speak_prefix), medalEntity.markUrl, R$drawable.fq_shape_default_cover_bg);
        baseViewHolder.setText(R$id.tv_prefix_desc, medalEntity.desc).setText(R$id.tv_prefix_endTime, getTimeStr(medalEntity));
    }

    private String getTimeStr(MedalEntity medalEntity) {
        return TextUtils.isEmpty(medalEntity.getEndTimeStr()) ? this.mContext.getString(R$string.fq_achieve_get_achieve_forever_valid) : this.mContext.getString(R$string.fq_achieve_wear_center_end_time, medalEntity.getEndTimeStr());
    }
}
