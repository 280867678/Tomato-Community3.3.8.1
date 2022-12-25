package com.one.tomato.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.RechargeTypeAndMoney;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;

/* loaded from: classes3.dex */
public class RechargeTypeAdapter extends BaseRecyclerViewAdapter<RechargeTypeAndMoney> {
    public RechargeTypeListener listener;
    private int selectPosition = 0;

    /* loaded from: classes3.dex */
    public interface RechargeTypeListener {
        void onItemClick(RechargeTypeAndMoney rechargeTypeAndMoney);
    }

    public RechargeTypeAdapter(Context context, RecyclerView recyclerView) {
        super(context, R.layout.item_recharge_type, recyclerView);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, RechargeTypeAndMoney rechargeTypeAndMoney) {
        char c;
        int i;
        ConstraintLayout constraintLayout = (ConstraintLayout) baseViewHolder.getView(R.id.cl_bg);
        ImageView imageView = (ImageView) baseViewHolder.getView(R.id.iv_recharge);
        TextView textView = (TextView) baseViewHolder.getView(R.id.tv_recharge);
        int indexOf = this.mData.indexOf(rechargeTypeAndMoney);
        String str = rechargeTypeAndMoney.payMethod;
        int i2 = 0;
        switch (str.hashCode()) {
            case -1414960566:
                if (str.equals(RechargeTypeAndMoney.RECHARGE_ALIPAY)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -296504455:
                if (str.equals(RechargeTypeAndMoney.RECHARGE_UNION)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -67677203:
                if (str.equals(RechargeTypeAndMoney.RECHARGE_CLOUD_FLASG_PAY)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 3809:
                if (str.equals(RechargeTypeAndMoney.RECHARGE_WX)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 77448196:
                if (str.equals(RechargeTypeAndMoney.RECHARGE_PTPAY)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 316018012:
                if (str.equals(RechargeTypeAndMoney.RECHARGE_AGENT)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        if (c == 0) {
            i2 = R.drawable.recharge_select_agent;
            i = R.string.vip_recharge_type_agent;
        } else if (c == 1) {
            i2 = R.drawable.recharge_select_alipay;
            i = rechargeTypeAndMoney.isFix == 1 ? R.string.recharge_type_alipay_quota : R.string.recharge_type_alipay;
        } else if (c == 2) {
            i2 = R.drawable.recharge_select_wechat;
            i = rechargeTypeAndMoney.isFix == 1 ? R.string.recharge_type_wechat_quota : R.string.recharge_type_wechat;
        } else if (c == 3) {
            i2 = R.drawable.recharge_select_union;
            i = rechargeTypeAndMoney.isFix == 1 ? R.string.recharge_type_union_quota : R.string.recharge_type_union;
        } else if (c == 4) {
            i2 = R.drawable.recharge_select_cloud_pay;
            i = rechargeTypeAndMoney.isFix == 1 ? R.string.recharge_type_cloud_pay_quota : R.string.recharge_type_cloud_pay;
        } else if (c != 5) {
            i = 0;
        } else {
            i2 = R.drawable.recharge_select_pt;
            i = R.string.recharge_type_pt;
        }
        if (i2 != 0 && i != 0) {
            imageView.setImageResource(i2);
            textView.setText(i);
        }
        if (this.selectPosition == indexOf) {
            constraintLayout.setBackgroundColor(this.mContext.getResources().getColor(R.color.white));
        } else {
            constraintLayout.setBackgroundColor(this.mContext.getResources().getColor(R.color.app_bg_grey));
        }
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onRecyclerItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        super.onRecyclerItemClick(baseQuickAdapter, view, i);
        if (this.selectPosition == i) {
            return;
        }
        this.selectPosition = i;
        notifyDataSetChanged();
        this.listener.onItemClick((RechargeTypeAndMoney) this.mData.get(i));
    }

    public void setRechargeTypeListener(RechargeTypeListener rechargeTypeListener) {
        this.listener = rechargeTypeListener;
    }

    public RechargeTypeAndMoney getSelectItem() {
        return (RechargeTypeAndMoney) this.mData.get(this.selectPosition);
    }
}
