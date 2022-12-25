package com.tomatolive.library.p136ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$array;
import com.tomatolive.library.R$id;
import com.tomatolive.library.model.NobilityEntity;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.NumberUtils;

/* renamed from: com.tomatolive.library.ui.adapter.NobilityOpenOrderAdapter */
/* loaded from: classes3.dex */
public class NobilityOpenOrderAdapter extends BaseQuickAdapter<NobilityEntity, BaseViewHolder> {
    private int selectedPosition = -1;

    public NobilityOpenOrderAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, NobilityEntity nobilityEntity) {
        baseViewHolder.setText(R$id.tv_title, getTitle(nobilityEntity)).setText(R$id.tv_desc, formatGold(nobilityEntity.isRenew() ? nobilityEntity.getRenewPrice() : nobilityEntity.getOpenPrice())).setImageResource(R$id.iv_logo, AppUtils.getNobilityBadgeDrawableRes(NumberUtils.string2int(nobilityEntity.type))).getView(R$id.ll_item_layout).setSelected(this.selectedPosition == baseViewHolder.getAdapterPosition() - getHeaderLayoutCount());
    }

    public void setSelectedPosition(int i) {
        this.selectedPosition = i;
        notifyDataSetChanged();
    }

    private String formatGold(String str) {
        return AppUtils.formatMoneyUnitStr(this.mContext, str, true);
    }

    private String getTitle(NobilityEntity nobilityEntity) {
        String[] stringArray = this.mContext.getResources().getStringArray(R$array.fq_nobility_privilege_open_pay_tips);
        String[] stringArray2 = this.mContext.getResources().getStringArray(R$array.fq_nobility_privilege_renew_pay_tips);
        switch (NumberUtils.string2int(nobilityEntity.type)) {
            case 1:
                return nobilityEntity.isRenew() ? stringArray2[6] : stringArray[6];
            case 2:
                return nobilityEntity.isRenew() ? stringArray2[5] : stringArray[5];
            case 3:
                return nobilityEntity.isRenew() ? stringArray2[4] : stringArray[4];
            case 4:
                return nobilityEntity.isRenew() ? stringArray2[3] : stringArray[3];
            case 5:
                return nobilityEntity.isRenew() ? stringArray2[2] : stringArray[2];
            case 6:
                return nobilityEntity.isRenew() ? stringArray2[1] : stringArray[1];
            case 7:
                return nobilityEntity.isRenew() ? stringArray2[0] : stringArray[0];
            default:
                return "";
        }
    }
}
