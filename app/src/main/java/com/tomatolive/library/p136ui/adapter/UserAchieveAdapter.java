package com.tomatolive.library.p136ui.adapter;

import android.text.TextUtils;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.UserAchieveEntity;
import com.tomatolive.library.utils.GlideUtils;

/* renamed from: com.tomatolive.library.ui.adapter.UserAchieveAdapter */
/* loaded from: classes3.dex */
public class UserAchieveAdapter extends BaseQuickAdapter<UserAchieveEntity, BaseViewHolder> {
    public UserAchieveAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, UserAchieveEntity userAchieveEntity) {
        baseViewHolder.setText(R$id.tv_name, userAchieveEntity.name).setText(R$id.iv_desc, getCountStr(userAchieveEntity.getTimes()));
        GlideUtils.loadImage(this.mContext, (ImageView) baseViewHolder.getView(R$id.iv_img), userAchieveEntity.url);
    }

    private String getCountStr(String str) {
        return TextUtils.isEmpty(str) ? "" : this.mContext.getString(R$string.fq_achieve_get_achieve_count, str);
    }
}
