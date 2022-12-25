package com.one.tomato.adapter;

import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.AgentChatMessage;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;

/* loaded from: classes3.dex */
public class PayMoabanGridAdapter extends BaseRecyclerViewAdapter<AgentChatMessage.PayMoBanBean> {
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onEmptyRefresh(int i) {
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onLoadMore() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, AgentChatMessage.PayMoBanBean payMoBanBean) {
        super.convert(baseViewHolder, (BaseViewHolder) payMoBanBean);
        ((TextView) baseViewHolder.getView(R.id.button_card_moban)).setText(payMoBanBean.getTplname());
    }
}
