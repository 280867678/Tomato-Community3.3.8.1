package com.tomatolive.library.p136ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$id;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.NumberUtils;

/* renamed from: com.tomatolive.library.ui.adapter.LotteryTopAnchorAdapter */
/* loaded from: classes3.dex */
public class LotteryTopAnchorAdapter extends BaseQuickAdapter<AnchorEntity, BaseViewHolder> {
    public LotteryTopAnchorAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, AnchorEntity anchorEntity) {
        int adapterPosition = baseViewHolder.getAdapterPosition();
        StringBuilder sb = new StringBuilder();
        sb.append(adapterPosition + 3);
        String sb2 = sb.toString();
        baseViewHolder.setText(R$id.tv_anchor_name, isShowView(anchorEntity) ? AppUtils.formatUserNickName(anchorEntity.nickname) : "").setText(R$id.tv_anchor_price, isShowView(anchorEntity) ? NumberUtils.formatThreeNumStr(String.valueOf(anchorEntity.count)) : "").setVisible(R$id.v_divider, isShowView(anchorEntity));
        TextView textView = (TextView) baseViewHolder.getView(R$id.tv_top_number);
        Context context = this.mContext;
        if (!isShowView(anchorEntity)) {
            sb2 = "";
        }
        AppUtils.formatTvNumTypeface(context, textView, sb2);
    }

    private boolean isShowView(AnchorEntity anchorEntity) {
        return !TextUtils.isEmpty(anchorEntity.nickname);
    }
}
