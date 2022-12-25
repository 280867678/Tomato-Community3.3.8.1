package com.one.tomato.mvp.p080ui.p082up.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.adapter.CommonBaseAdapter;
import com.one.tomato.adapter.ViewHolder;
import com.one.tomato.entity.RechargeList;
import com.one.tomato.utils.FormatUtil;
import java.util.List;

/* renamed from: com.one.tomato.mvp.ui.up.adapter.RewardMoneyAdapter */
/* loaded from: classes3.dex */
public class RewardMoneyAdapter extends CommonBaseAdapter<RechargeList> {
    private int selectPosition = -1;

    public RewardMoneyAdapter(Context context, List<RechargeList> list) {
        super(context, list, R.layout.item_reward_money);
    }

    @Override // com.one.tomato.adapter.CommonBaseAdapter
    public void convert(ViewHolder viewHolder, RechargeList rechargeList, int i) {
        RelativeLayout relativeLayout = (RelativeLayout) viewHolder.getView(R.id.rl_bg);
        TextView textView = (TextView) viewHolder.getView(R.id.tv_recharge_money);
        ImageView imageView = (ImageView) viewHolder.getView(R.id.iv_recharge_selection);
        if (this.selectPosition == i) {
            relativeLayout.setBackgroundResource(R.drawable.common_shape_stroke_corner5_coloraccent);
            textView.setTextColor(this.mContext.getResources().getColor(R.color.red_ff5252));
            imageView.setVisibility(0);
        } else {
            relativeLayout.setBackgroundResource(R.drawable.shape_recharge_money_bg_n);
            textView.setTextColor(this.mContext.getResources().getColor(R.color.text_middle));
            imageView.setVisibility(8);
        }
        textView.setText("¥" + FormatUtil.formatTomato2RMB(rechargeList.getTomatoCurrency()));
    }

    public void setSelectPosition(int i) {
        int i2 = this.selectPosition;
        if (i2 != i) {
            this.selectPosition = i;
            notifyDataSetChanged();
        } else if (i == -1) {
            if (i2 == -1 && i == -1) {
                return;
            }
            notifyDataSetChanged();
        } else if (i != -2) {
        } else {
            this.selectPosition = -1;
            notifyDataSetChanged();
        }
    }

    public RechargeList getSelectItem() {
        int i;
        if (this.mDatas.size() <= 0 || (i = this.selectPosition) <= -1) {
            return null;
        }
        return (RechargeList) this.mDatas.get(i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.one.tomato.adapter.CommonBaseAdapter
    public void setList(List<RechargeList> list) {
        clear();
        int size = list.size();
        List list2 = list;
        if (size > 6) {
            list2 = list.subList(0, 6);
        }
        this.mDatas = list2;
        this.selectPosition = 0;
        notifyDataSetChanged();
    }
}
