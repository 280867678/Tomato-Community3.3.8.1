package com.one.tomato.p085ui.income;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.entity.CashDetailBean;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.recharge.AlipayUtils;
import java.text.DecimalFormat;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_cash_apply_detail)
/* renamed from: com.one.tomato.ui.income.CashApplyCompeledActivity */
/* loaded from: classes3.dex */
public class CashApplyCompeledActivity extends BaseActivity {
    private CashDetailBean cashDetailBean;
    @ViewInject(R.id.tv_order_account)
    private TextView tv_order_account;
    @ViewInject(R.id.tv_order_cash_amount)
    private TextView tv_order_cash_amount;
    @ViewInject(R.id.tv_order_fee)
    private TextView tv_order_fee;
    @ViewInject(R.id.tv_order_real_amount)
    private TextView tv_order_real_amount;

    public CashApplyCompeledActivity() {
        new DecimalFormat("0.00");
    }

    public static void startActivity(Context context, CashDetailBean cashDetailBean) {
        Intent intent = new Intent();
        intent.setClass(context, CashApplyCompeledActivity.class);
        intent.putExtra("cash_info", cashDetailBean);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initTitleBar();
        this.titleTV.setText(R.string.income_cash_apply_detail_title);
        this.cashDetailBean = (CashDetailBean) getIntent().getExtras().getSerializable("cash_info");
        updateViews();
    }

    private void updateViews() {
        if (this.cashDetailBean != null) {
            String hideAlipayAccount = AlipayUtils.getInstance().hideAlipayAccount(this.cashDetailBean.getAlipayAccount());
            String hideBankAccount = AppUtil.hideBankAccount(this.cashDetailBean.getBankAccount());
            String bankName = this.cashDetailBean.getBankName();
            int withdrawType = this.cashDetailBean.getWithdrawType();
            if (withdrawType == 1) {
                TextView textView = this.tv_order_account;
                textView.setText(AppUtil.getString(R.string.cash_mode_alipay) + hideAlipayAccount);
            } else if (withdrawType == 2) {
                TextView textView2 = this.tv_order_account;
                textView2.setText(bankName + hideBankAccount);
            }
            TextView textView3 = this.tv_order_cash_amount;
            textView3.setText(this.cashDetailBean.getAmount() + "");
            TextView textView4 = this.tv_order_fee;
            textView4.setText(this.cashDetailBean.getTax() + "");
            TextView textView5 = this.tv_order_real_amount;
            textView5.setText(this.cashDetailBean.getActualAmount() + "");
        }
    }

    @Event({R.id.tv_complete})
    private void onClick(View view) {
        if (view.getId() != R.id.tv_complete) {
            return;
        }
        finish();
    }
}
