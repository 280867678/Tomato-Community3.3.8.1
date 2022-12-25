package com.tomatolive.library.p136ui.adapter;

import android.content.Context;
import android.text.Html;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.MyCarEntity;
import com.tomatolive.library.utils.GlideUtils;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/* renamed from: com.tomatolive.library.ui.adapter.MyCarAdapter */
/* loaded from: classes3.dex */
public class MyCarAdapter extends BaseQuickAdapter<MyCarEntity, BaseViewHolder> {
    public MyCarAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, MyCarEntity myCarEntity) {
        baseViewHolder.setText(R$id.tv_name, myCarEntity.name).setText(R$id.tv_tips, myCarEntity.brief).setText(R$id.tv_times, Html.fromHtml(this.mContext.getString(R$string.fq_remainder_day_tips, myCarEntity.remainDay))).setText(R$id.tv_money, formatEquipageStr(myCarEntity)).setVisible(R$id.iv_label, myCarEntity.isPrivatePermission()).addOnClickListener(R$id.tv_money).getView(R$id.tv_money).setSelected(myCarEntity.isEquipage());
        GlideUtils.loadRoundCornersImage(this.mContext, (ImageView) baseViewHolder.getView(R$id.iv_img), myCarEntity.imgUrl, 6, RoundedCornersTransformation.CornerType.TOP, R$drawable.fq_ic_placeholder_banner);
    }

    private String formatEquipageStr(MyCarEntity myCarEntity) {
        Context context;
        int i;
        if (myCarEntity.isEquipage()) {
            context = this.mContext;
            i = R$string.fq_equipage_yes;
        } else {
            context = this.mContext;
            i = R$string.fq_now_equipage;
        }
        return context.getString(i);
    }
}
