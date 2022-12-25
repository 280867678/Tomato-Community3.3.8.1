package com.one.tomato.p085ui.income;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.constants.Constants;
import com.one.tomato.entity.IncomeRecodeListBean;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.ViewUtil;
import org.slf4j.Marker;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_income_detail)
/* renamed from: com.one.tomato.ui.income.IncomeDetailActivity */
/* loaded from: classes3.dex */
public class IncomeDetailActivity extends BaseActivity {
    private int accountType = 1;
    private IncomeRecodeListBean incomeRecodeBean;
    @ViewInject(R.id.tv_amount_unit)
    private TextView tv_amount_unit;
    @ViewInject(R.id.tv_income_amount)
    private TextView tv_income_amount;
    @ViewInject(R.id.tv_income_order_code)
    private TextView tv_income_order_code;
    @ViewInject(R.id.tv_income_order_date)
    private TextView tv_income_order_date;
    @ViewInject(R.id.tv_income_order_type)
    private TextView tv_income_order_type;
    @ViewInject(R.id.tv_income_type)
    private TextView tv_income_type;

    public static void startActivity(Context context, IncomeRecodeListBean incomeRecodeListBean, int i) {
        Intent intent = new Intent();
        intent.setClass(context, IncomeDetailActivity.class);
        intent.putExtra("incomeInfo", incomeRecodeListBean);
        intent.putExtra("accountType", i);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initTitleBar();
        fetchIntentArg();
        updateViews(this.incomeRecodeBean);
    }

    private void fetchIntentArg() {
        Intent intent = getIntent();
        if (intent == null || !intent.getExtras().containsKey("incomeInfo")) {
            return;
        }
        this.incomeRecodeBean = (IncomeRecodeListBean) intent.getExtras().getSerializable("incomeInfo");
        this.accountType = intent.getIntExtra("accountType", 1);
    }

    private void updateViews(IncomeRecodeListBean incomeRecodeListBean) {
        if (incomeRecodeListBean != null) {
            if (incomeRecodeListBean.getType() == 1) {
                this.tv_income_type.setText(Constants.IncomeTypePlant.getValueByKey(3));
            } else {
                this.tv_income_type.setText(Constants.IncomeTypePlant.getValueByKey(4));
            }
            if (this.accountType == 3) {
                this.tv_income_amount.setText(String.valueOf(incomeRecodeListBean.getAmount()));
            } else {
                this.tv_income_amount.setText(FormatUtil.formatTomato2RMB(incomeRecodeListBean.getAmount()));
            }
            this.tv_income_amount.setTypeface(ViewUtil.getNumFontTypeface(this));
            if (2 == incomeRecodeListBean.getType()) {
                this.tv_amount_unit.setTextSize(2, 32.0f);
                this.tv_amount_unit.setText(Marker.ANY_NON_NULL_MARKER);
            } else if (1 == incomeRecodeListBean.getType()) {
                this.tv_amount_unit.setTextSize(2, 36.0f);
                this.tv_amount_unit.setText("-");
            }
            this.tv_income_order_type.setText(Constants.IncomeStatus.getValueByKey(incomeRecodeListBean.getType()));
            this.tv_income_order_date.setText(incomeRecodeListBean.getCreateTime());
            this.tv_income_order_code.setText(incomeRecodeListBean.getTomatoOrderId());
        }
    }
}
