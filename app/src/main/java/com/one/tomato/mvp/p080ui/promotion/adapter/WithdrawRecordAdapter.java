package com.one.tomato.mvp.p080ui.promotion.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.PromotionOrderRecord;
import com.one.tomato.mvp.p080ui.promotion.p081ui.WithdrawStatusActivity;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: WithdrawRecordAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.promotion.adapter.WithdrawRecordAdapter */
/* loaded from: classes3.dex */
public final class WithdrawRecordAdapter extends BaseRecyclerViewAdapter<PromotionOrderRecord.C2524Record> {
    public WithdrawRecordAdapter(Context context, RecyclerView recyclerView) {
        super(context, R.layout.item_spread_withdraw_record, recyclerView);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder holder, PromotionOrderRecord.C2524Record c2524Record) {
        Intrinsics.checkParameterIsNotNull(holder, "holder");
        super.convert(holder, (BaseViewHolder) c2524Record);
        this.mData.indexOf(c2524Record);
        TextView textView = (TextView) holder.getView(R.id.tv_order);
        TextView textView2 = (TextView) holder.getView(R.id.tv_status);
        TextView textView3 = (TextView) holder.getView(R.id.tv_money);
        TextView textView4 = (TextView) holder.getView(R.id.tv_time);
        String str = null;
        textView.setText(c2524Record != null ? c2524Record.orderId : null);
        Integer valueOf = c2524Record != null ? Integer.valueOf(c2524Record.orderStatus) : null;
        if (valueOf != null && valueOf.intValue() == 1) {
            Context mContext = this.mContext;
            Intrinsics.checkExpressionValueIsNotNull(mContext, "mContext");
            textView2.setTextColor(mContext.getResources().getColor(R.color.text_dark));
            textView2.setText(R.string.spread_withdraw_record_success);
        } else if (valueOf != null && valueOf.intValue() == 2) {
            textView2.setTextColor(Color.parseColor("#FC4C7B"));
            textView2.setText(R.string.spread_withdraw_record_fail);
        } else if (valueOf != null && valueOf.intValue() == 3) {
            textView2.setTextColor(Color.parseColor("#149EFF"));
            textView2.setText(R.string.spread_withdraw_record_ing);
        }
        textView3.setText(c2524Record != null ? c2524Record.coinAmount : null);
        if (c2524Record != null) {
            str = c2524Record.orderTime;
        }
        textView4.setText(str);
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onRecyclerItemClick(BaseQuickAdapter<?, ?> baseQuickAdapter, View view, int i) {
        super.onRecyclerItemClick(baseQuickAdapter, view, i);
        WithdrawStatusActivity.Companion companion = WithdrawStatusActivity.Companion;
        Context mContext = this.mContext;
        Intrinsics.checkExpressionValueIsNotNull(mContext, "mContext");
        Object obj = this.mData.get(i);
        Intrinsics.checkExpressionValueIsNotNull(obj, "mData.get(position)");
        companion.startActivity(mContext, 2, (PromotionOrderRecord.C2524Record) obj);
    }
}
