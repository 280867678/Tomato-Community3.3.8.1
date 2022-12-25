package com.one.tomato.dialog;

import android.content.Context;
import android.support.p005v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.RechargeTypeAndMoney;
import com.one.tomato.thirdpart.recyclerview.BaseLinearLayoutManager;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.AppUtil;
import java.util.List;

/* loaded from: classes3.dex */
public class VipRechargeDialog extends CustomAlertDialog {
    public VipRechargeDialogListener listener;

    /* loaded from: classes3.dex */
    public interface VipRechargeDialogListener {
        void onItemClick(String str);
    }

    public VipRechargeDialog(Context context, List<String> list, final int i) {
        super(context);
        setMiddleNeedPadding(false);
        bottomLayoutGone();
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_vip_recharge, (ViewGroup) null);
        setContentView(inflate);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.iv_cancel);
        TextView textView = (TextView) inflate.findViewById(R.id.tv_vip_day_tip);
        if (i > 0) {
            textView.setVisibility(0);
        }
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.-$$Lambda$VipRechargeDialog$FzW1CNPhjzUiZ451YiXMp47wwIQ
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VipRechargeDialog.this.lambda$new$0$VipRechargeDialog(view);
            }
        });
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new BaseLinearLayoutManager(context));
        list.add(0, RechargeTypeAndMoney.RECHARGE_AGENT);
        BaseRecyclerViewAdapter<String> baseRecyclerViewAdapter = new BaseRecyclerViewAdapter<String>(context, R.layout.item_dialog_vip_recharge, list, recyclerView) { // from class: com.one.tomato.dialog.VipRechargeDialog.1
            /* JADX INFO: Access modifiers changed from: protected */
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, String str) {
                char c;
                int i2;
                super.convert(baseViewHolder, (BaseViewHolder) str);
                int i3 = 0;
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
                    if (i > 0) {
                        baseViewHolder.setVisible(R.id.tv_vip_day, true);
                        baseViewHolder.setText(R.id.tv_vip_day, AppUtil.getString(R.string.recharge_vip_day, Integer.valueOf(i)));
                    } else {
                        baseViewHolder.setVisible(R.id.tv_vip_day, false);
                    }
                    i2 = R.string.vip_recharge_type_agent;
                    i3 = R.drawable.recharge_select_agent;
                } else if (c == 1) {
                    i3 = R.drawable.recharge_select_alipay;
                    i2 = R.string.recharge_type_alipay;
                } else if (c == 2) {
                    i3 = R.drawable.recharge_select_wechat;
                    i2 = R.string.recharge_type_wechat;
                } else if (c == 3) {
                    i3 = R.drawable.recharge_select_union;
                    i2 = R.string.recharge_type_union;
                } else if (c == 4) {
                    i3 = R.drawable.recharge_select_cloud_pay;
                    i2 = R.string.recharge_type_cloud_pay;
                } else if (c != 5) {
                    i2 = 0;
                } else {
                    i3 = R.drawable.recharge_select_pt;
                    i2 = R.string.recharge_type_pt;
                }
                if (i3 == 0 || i2 == 0) {
                    return;
                }
                baseViewHolder.setImageResource(R.id.iv_icon, i3);
                baseViewHolder.setText(R.id.tv_name, i2);
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onRecyclerItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                super.onRecyclerItemClick(baseQuickAdapter, view, i2);
                VipRechargeDialog.this.dismiss();
                VipRechargeDialogListener vipRechargeDialogListener = VipRechargeDialog.this.listener;
                if (vipRechargeDialogListener != null) {
                    vipRechargeDialogListener.onItemClick(getItem(i2));
                }
            }
        };
        recyclerView.setAdapter(baseRecyclerViewAdapter);
        baseRecyclerViewAdapter.setEnableLoadMore(false);
    }

    public /* synthetic */ void lambda$new$0$VipRechargeDialog(View view) {
        dismiss();
    }

    public void setVipRechargeDialogListener(VipRechargeDialogListener vipRechargeDialogListener) {
        this.listener = vipRechargeDialogListener;
    }
}
