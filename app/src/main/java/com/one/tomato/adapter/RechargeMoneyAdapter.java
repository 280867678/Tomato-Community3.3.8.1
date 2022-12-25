package com.one.tomato.adapter;

import android.content.Context;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.RechargeTypeAndMoney;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.FormatUtil;

/* loaded from: classes3.dex */
public class RechargeMoneyAdapter extends BaseRecyclerViewAdapter<RechargeTypeAndMoney.RechargeMoney> {
    private int selectPosition = 0;

    public RechargeMoneyAdapter(Context context, RecyclerView recyclerView) {
        super(context, R.layout.item_recharge_money, recyclerView);
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, RechargeTypeAndMoney.RechargeMoney rechargeMoney) {
        RelativeLayout relativeLayout = (RelativeLayout) baseViewHolder.getView(R.id.rl_bg);
        TextView textView = (TextView) baseViewHolder.getView(R.id.tv_money_arrive);
        TextView textView2 = (TextView) baseViewHolder.getView(R.id.tv_money_recharge);
        ImageView imageView = (ImageView) baseViewHolder.getView(R.id.iv_recharge_selection);
        TextView textView3 = (TextView) baseViewHolder.getView(R.id.tv_vip_day);
        if (this.selectPosition == this.mData.indexOf(rechargeMoney)) {
            if (BaseApplication.getApplication().isChess()) {
                relativeLayout.setBackgroundResource(R.drawable.chess_bright_bg);
                textView.setTextColor(this.mContext.getResources().getColor(R.color.white));
                textView2.setTextColor(this.mContext.getResources().getColor(R.color.white));
            } else {
                relativeLayout.setBackgroundResource(R.drawable.shape_recharge_money_bg_s);
                textView.setTextColor(this.mContext.getResources().getColor(R.color.red_ff5252));
                textView2.setTextColor(this.mContext.getResources().getColor(R.color.red_ff5252));
            }
            imageView.setVisibility(0);
            if (rechargeMoney.vipDay > 0) {
                textView3.setVisibility(0);
                textView3.setText(AppUtil.getString(R.string.recharge_vip_day, Integer.valueOf(rechargeMoney.vipDay)));
            } else {
                textView3.setVisibility(8);
            }
        } else {
            if (BaseApplication.getApplication().isChess()) {
                relativeLayout.setBackgroundResource(R.drawable.chess_light_bg);
                textView.setTextColor(this.mContext.getResources().getColor(R.color.chess_text_light));
                textView2.setTextColor(this.mContext.getResources().getColor(R.color.chess_text_light));
            } else {
                relativeLayout.setBackgroundResource(R.drawable.shape_recharge_money_bg_n);
                textView.setTextColor(this.mContext.getResources().getColor(R.color.text_middle));
                textView2.setTextColor(this.mContext.getResources().getColor(R.color.text_dark));
            }
            imageView.setVisibility(8);
            textView3.setVisibility(8);
        }
        textView.setText(AppUtil.getString(R.string.recharge_exchange_balance, FormatUtil.formatTomato2RMB(rechargeMoney.tomatoCurrency)));
        textView2.setText(rechargeMoney.amount + "");
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onRecyclerItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        super.onRecyclerItemClick(baseQuickAdapter, view, i);
        if (this.selectPosition == i) {
            return;
        }
        this.selectPosition = i;
        notifyDataSetChanged();
    }

    public RechargeTypeAndMoney.RechargeMoney getSelectItem() {
        return (RechargeTypeAndMoney.RechargeMoney) this.mData.get(this.selectPosition);
    }

    public void setSelectPosition(int i) {
        this.selectPosition = i;
    }
}
