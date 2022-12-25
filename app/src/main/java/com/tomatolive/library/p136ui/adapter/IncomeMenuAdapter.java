package com.tomatolive.library.p136ui.adapter;

import android.text.Html;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.TotalAccountEntity;
import com.tomatolive.library.utils.AppUtils;

/* renamed from: com.tomatolive.library.ui.adapter.IncomeMenuAdapter */
/* loaded from: classes3.dex */
public class IncomeMenuAdapter extends BaseQuickAdapter<TotalAccountEntity, BaseViewHolder> {
    public IncomeMenuAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, TotalAccountEntity totalAccountEntity) {
        baseViewHolder.setText(R$id.tv_type, totalAccountEntity.getTitleRes()).setText(R$id.tv_total, Html.fromHtml(this.mContext.getString(R$string.fq_income_total, formatPrice(totalAccountEntity)))).setImageResource(R$id.iv_logo, totalAccountEntity.getImgRes()).setVisible(R$id.tv_total, totalAccountEntity.isShowTotal());
    }

    private String formatPrice(TotalAccountEntity totalAccountEntity) {
        if (totalAccountEntity.getType() == 7) {
            return totalAccountEntity.getTotalAccount();
        }
        return AppUtils.formatDisplayPrice(totalAccountEntity.getTotalAccount(), true);
    }
}
