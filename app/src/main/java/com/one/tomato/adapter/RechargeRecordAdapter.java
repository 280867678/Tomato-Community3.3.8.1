package com.one.tomato.adapter;

import android.support.p005v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.RechargeRecord;
import com.one.tomato.p085ui.recharge.RechargeRecordActivity;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.ViewUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import org.slf4j.Marker;

/* loaded from: classes3.dex */
public class RechargeRecordAdapter extends BaseRecyclerViewAdapter<RechargeRecord> {
    private RechargeRecordActivity activity;
    private RefreshLayout refreshLayout;

    public RechargeRecordAdapter(RechargeRecordActivity rechargeRecordActivity, RecyclerView recyclerView, RefreshLayout refreshLayout) {
        super(rechargeRecordActivity, R.layout.item_recharge_record, recyclerView);
        this.activity = rechargeRecordActivity;
        this.refreshLayout = refreshLayout;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, RechargeRecord rechargeRecord) {
        super.convert(baseViewHolder, (BaseViewHolder) rechargeRecord);
        ImageView imageView = (ImageView) baseViewHolder.getView(R.id.iv_item_recharge_icon);
        TextView textView = (TextView) baseViewHolder.getView(R.id.tv_item_recharge_count);
        TextView textView2 = (TextView) baseViewHolder.getView(R.id.tv_item_unit);
        ((TextView) baseViewHolder.getView(R.id.tv_item_title_name)).setText(rechargeRecord.getContent());
        ((TextView) baseViewHolder.getView(R.id.tv_item_gift_date)).setText(rechargeRecord.getCreateTime());
        textView.setText(FormatUtil.formatTomato2RMB(rechargeRecord.getAmount()));
        textView.setTypeface(ViewUtil.getNumFontTypeface(this.mContext));
        if (1 == rechargeRecord.getType()) {
            textView2.setText("-");
            imageView.setImageResource(R.drawable.recharge_cost);
        } else if (2 != rechargeRecord.getType()) {
        } else {
            textView2.setText(Marker.ANY_NON_NULL_MARKER);
            imageView.setImageResource(R.drawable.recharge_income);
        }
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onEmptyRefresh(int i) {
        setEmptyViewState(0, this.refreshLayout);
        this.activity.refresh();
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onLoadMore() {
        this.activity.loadMore();
    }
}
