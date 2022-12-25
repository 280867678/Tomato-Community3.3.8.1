package com.tomatolive.library.p136ui.view.gift;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$id;
import com.tomatolive.library.model.GiftBatchItemEntity;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.gift.GiftNumAdapter */
/* loaded from: classes3.dex */
public class GiftNumAdapter extends BaseQuickAdapter<GiftBatchItemEntity, BaseViewHolder> {
    private int pos = -1;

    public GiftNumAdapter(int i) {
        super(i);
    }

    public GiftNumAdapter(@LayoutRes int i, @Nullable List<GiftBatchItemEntity> list) {
        super(i, list);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, GiftBatchItemEntity giftBatchItemEntity) {
        baseViewHolder.setText(R$id.tv_gift_num, String.valueOf(giftBatchItemEntity.num));
        baseViewHolder.setText(R$id.tv_gift_desc, giftBatchItemEntity.desc);
        baseViewHolder.setBackgroundColor(R$id.item_root, baseViewHolder.getLayoutPosition() == this.pos ? Color.parseColor("#FFE2E2") : -1);
        baseViewHolder.setTextColor(R$id.tv_gift_desc, Color.parseColor(baseViewHolder.getLayoutPosition() == this.pos ? "#F24335" : "#292929"));
    }

    public void setSelectPos(int i) {
        this.pos = i;
        notifyDataSetChanged();
    }
}
