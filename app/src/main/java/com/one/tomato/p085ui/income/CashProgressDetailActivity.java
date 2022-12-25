package com.one.tomato.p085ui.income;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.dialog.CustomAlertDialog;
import com.one.tomato.entity.BaseModel;
import com.one.tomato.entity.CashDetailBean;
import com.one.tomato.entity.CashRecodeListBean;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.net.ResponseObserver;
import com.one.tomato.net.TomatoCallback;
import com.one.tomato.net.TomatoParams;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.PreferencesUtil;
import com.one.tomato.utils.RxUtils;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.ViewUtil;
import com.one.tomato.utils.recharge.AlipayUtils;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_cash_detail)
/* renamed from: com.one.tomato.ui.income.CashProgressDetailActivity */
/* loaded from: classes3.dex */
public class CashProgressDetailActivity extends BaseActivity {
    private CashDetailBean cashDetailBean;
    private CashRecodeListBean cashRecodeBean;
    @ViewInject(R.id.divider_handle)
    private View divider_handle;
    @ViewInject(R.id.divider_receive)
    private View divider_receive;
    @ViewInject(R.id.divider_transfer)
    private View divider_transfer;
    @ViewInject(R.id.iv_check)
    private ImageView iv_check;
    @ViewInject(R.id.iv_handle)
    private ImageView iv_handle;
    @ViewInject(R.id.iv_receive)
    private ImageView iv_receive;
    @ViewInject(R.id.iv_transfer)
    private ImageView iv_transfer;
    @ViewInject(R.id.ll_payment)
    private LinearLayout ll_payment;
    @ViewInject(R.id.tv_cash_amount)
    private TextView tv_cash_amount;
    @ViewInject(R.id.tv_check)
    private TextView tv_check;
    @ViewInject(R.id.tv_handle)
    private TextView tv_handle;
    @ViewInject(R.id.tv_handle_time)
    private TextView tv_handle_time;
    @ViewInject(R.id.tv_order_account)
    private TextView tv_order_account;
    @ViewInject(R.id.tv_order_code)
    private TextView tv_order_code;
    @ViewInject(R.id.tv_order_fee)
    private TextView tv_order_fee;
    @ViewInject(R.id.tv_order_real_amount)
    private TextView tv_order_real_amount;
    @ViewInject(R.id.tv_order_time)
    private TextView tv_order_time;
    @ViewInject(R.id.tv_payment_again)
    private TextView tv_payment_again;
    @ViewInject(R.id.tv_receive)
    private TextView tv_receive;
    @ViewInject(R.id.tv_receive_error)
    private TextView tv_receive_error;
    @ViewInject(R.id.tv_return_account)
    private TextView tv_return_account;
    @ViewInject(R.id.tv_transfer)
    private TextView tv_transfer;
    @ViewInject(R.id.tv_transfer_error)
    private TextView tv_transfer_error;

    public static void startActivity(Context context, CashRecodeListBean cashRecodeListBean) {
        Intent intent = new Intent();
        intent.setClass(context, CashProgressDetailActivity.class);
        intent.putExtra("orderInfo", cashRecodeListBean);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initTitleBar();
        this.titleTV.setText(R.string.income_cash_detail_title);
        this.cashRecodeBean = (CashRecodeListBean) getIntent().getExtras().getSerializable("orderInfo");
        getCashProgress(this.cashRecodeBean.getOrderId());
        this.tv_payment_again.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.income.CashProgressDetailActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (!PreferencesUtil.getInstance().getBoolean("cash_progress_detail_show_dialog", true)) {
                    CashProgressDetailActivity.this.paymentAgain();
                    return;
                }
                PreferencesUtil.getInstance().putBoolean("cash_progress_detail_show_dialog", false);
                CashProgressDetailActivity.this.showPayDialog();
            }
        });
        this.tv_return_account.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.income.CashProgressDetailActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (!PreferencesUtil.getInstance().getBoolean("cash_progress_detail_show_dialog", true)) {
                    CashProgressDetailActivity.this.returnAccount();
                    return;
                }
                PreferencesUtil.getInstance().putBoolean("cash_progress_detail_show_dialog", false);
                CashProgressDetailActivity.this.showPayDialog();
            }
        });
    }

    private void updateViews() {
        TextView textView = this.tv_cash_amount;
        textView.setText(this.cashRecodeBean.getAmount() + "");
        this.tv_cash_amount.setTypeface(ViewUtil.getNumFontTypeface(this));
        this.tv_order_time.setText(this.cashDetailBean.getArrivalTime());
        this.tv_order_code.setText(this.cashDetailBean.getOrderId());
        String hideAlipayAccount = AlipayUtils.getInstance().hideAlipayAccount(this.cashRecodeBean.getAlipayAccount());
        String hideBankAccount = AppUtil.hideBankAccount(this.cashRecodeBean.getBankAccount());
        String bankName = this.cashRecodeBean.getBankName();
        int withdrawType = this.cashRecodeBean.getWithdrawType();
        if (withdrawType == 1) {
            TextView textView2 = this.tv_order_account;
            textView2.setText(AppUtil.getString(R.string.cash_mode_alipay) + hideAlipayAccount);
        } else if (withdrawType == 2) {
            TextView textView3 = this.tv_order_account;
            textView3.setText(bankName + hideBankAccount);
        }
        TextView textView4 = this.tv_order_fee;
        textView4.setText(this.cashRecodeBean.getTax() + "");
        TextView textView5 = this.tv_order_real_amount;
        textView5.setText(this.cashRecodeBean.getActualAmount() + "");
        updateProgress();
    }

    private void getCashProgress(String str) {
        showWaitingDialog();
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/withdraw/status/list");
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.addParameter("orderId", str);
        tomatoParams.post(new TomatoCallback((ResponseObserver) this, 3001, CashDetailBean.class));
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public void handleResponse(Message message) {
        Object obj;
        super.handleResponse(message);
        hideWaitingDialog();
        BaseModel baseModel = (BaseModel) message.obj;
        if (message.what == 3001 && (obj = baseModel.obj) != null) {
            this.cashDetailBean = (CashDetailBean) obj;
            updateViews();
        }
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public boolean handleResponseError(Message message) {
        hideWaitingDialog();
        return message.what == 3001;
    }

    private void updateProgress() {
        int status = this.cashDetailBean.getStatus();
        if (status == 0) {
            this.iv_check.setImageResource(R.drawable.progress_compeled);
            this.tv_check.setText(R.string.income_progress_check_ing);
            this.tv_check.setTextColor(getResources().getColor(R.color.blue_149eff));
        } else if (status == 1) {
            this.iv_check.setImageResource(R.drawable.progress_compeled);
            this.tv_check.setText(R.string.income_progress_check_success);
            this.tv_check.setTextColor(getResources().getColor(R.color.blue_149eff));
        } else if (status == 3) {
            this.iv_check.setImageResource(R.drawable.progress_compeled);
            this.tv_check.setText(R.string.income_progress_check_success);
            this.tv_check.setTextColor(getResources().getColor(R.color.blue_149eff));
            this.divider_handle.setBackgroundResource(R.color.blue_149eff);
            this.iv_handle.setImageResource(R.drawable.progress_compeled);
            this.tv_handle.setText(R.string.income_progress_handle_ing);
            this.tv_handle.setTextColor(getResources().getColor(R.color.blue_149eff));
            this.tv_handle_time.setText(AppUtil.getString(R.string.income_progress_handle_date, this.cashDetailBean.getArrivalTime()));
        } else if (status == 4) {
            this.iv_check.setImageResource(R.drawable.progress_compeled);
            this.tv_check.setText(R.string.income_progress_check_success);
            this.tv_check.setTextColor(getResources().getColor(R.color.blue_149eff));
            this.divider_handle.setBackgroundResource(R.color.blue_149eff);
            this.iv_handle.setImageResource(R.drawable.progress_compeled);
            this.tv_handle.setText(R.string.income_progress_handle_success);
            this.tv_handle.setTextColor(getResources().getColor(R.color.blue_149eff));
            this.tv_handle_time.setText(AppUtil.getString(R.string.income_progress_handle_date, this.cashDetailBean.getArrivalTime()));
            this.divider_transfer.setBackgroundResource(R.color.blue_149eff);
            this.iv_transfer.setImageResource(R.drawable.progress_compeled);
            this.tv_transfer.setText(R.string.income_progress_payment_success);
            this.tv_transfer.setTextColor(getResources().getColor(R.color.blue_149eff));
            this.divider_receive.setBackgroundResource(R.color.blue_149eff);
            this.iv_receive.setImageResource(R.drawable.progress_compeled);
            this.tv_receive.setText(R.string.income_progress_receive);
            this.tv_receive.setTextColor(getResources().getColor(R.color.blue_149eff));
        } else if (status != 5) {
            if (status != 6) {
                return;
            }
            this.iv_check.setImageResource(R.drawable.progress_compeled);
            this.tv_check.setText(R.string.income_progress_check_success);
            this.tv_check.setTextColor(getResources().getColor(R.color.blue_149eff));
            this.divider_handle.setBackgroundResource(R.color.blue_149eff);
            this.iv_handle.setImageResource(R.drawable.progress_compeled);
            this.tv_handle.setText(R.string.income_progress_handle_success);
            this.tv_handle.setTextColor(getResources().getColor(R.color.blue_149eff));
            this.divider_transfer.setBackgroundResource(R.color.blue_149eff);
            this.iv_transfer.setImageResource(R.drawable.progress_fail);
            this.tv_transfer.setText(R.string.income_order_status_6);
            this.tv_transfer.setTextColor(getResources().getColor(R.color.red_ff5252));
            this.tv_transfer_error.setText(this.cashDetailBean.sendFailureReason);
            this.tv_receive.setText(R.string.income_order_status_5);
            this.ll_payment.setVisibility(0);
        } else if (this.cashDetailBean.getProcessingState() == 3) {
            this.iv_check.setImageResource(R.drawable.progress_fail);
            this.tv_check.setText(R.string.income_progress_check_fail);
            this.tv_check.setTextColor(getResources().getColor(R.color.red_ff5252));
            this.divider_handle.setVisibility(8);
            this.iv_handle.setVisibility(8);
            this.tv_handle.setVisibility(8);
            this.tv_handle_time.setVisibility(8);
            this.divider_transfer.setVisibility(8);
            this.iv_transfer.setVisibility(8);
            this.tv_transfer.setVisibility(8);
            this.tv_transfer_error.setVisibility(8);
            this.divider_receive.setBackgroundResource(R.color.blue_149eff);
            this.iv_receive.setImageResource(R.drawable.progress_fail);
            this.tv_receive.setText(R.string.income_order_status_5);
            this.tv_receive.setTextColor(getResources().getColor(R.color.red_ff5252));
            this.tv_receive_error.setText(this.cashDetailBean.getReason());
        } else if (TextUtils.isEmpty(this.cashDetailBean.sendFailureReason)) {
        } else {
            this.iv_check.setImageResource(R.drawable.progress_compeled);
            this.tv_check.setText(R.string.income_progress_check_success);
            this.tv_check.setTextColor(getResources().getColor(R.color.blue_149eff));
            this.divider_handle.setBackgroundResource(R.color.blue_149eff);
            this.iv_handle.setImageResource(R.drawable.progress_compeled);
            this.tv_handle.setText(R.string.income_progress_handle_success);
            this.tv_handle.setTextColor(getResources().getColor(R.color.blue_149eff));
            this.divider_transfer.setBackgroundResource(R.color.blue_149eff);
            this.iv_transfer.setImageResource(R.drawable.progress_fail);
            this.tv_transfer.setText(R.string.income_order_status_6);
            this.tv_transfer.setTextColor(getResources().getColor(R.color.red_ff5252));
            this.tv_transfer_error.setText(this.cashDetailBean.sendFailureReason);
            this.divider_receive.setBackgroundResource(R.color.blue_149eff);
            this.iv_receive.setImageResource(R.drawable.progress_fail);
            this.tv_receive.setText(R.string.income_order_status_5);
            this.tv_receive.setTextColor(getResources().getColor(R.color.red_ff5252));
            this.tv_receive_error.setText(this.cashDetailBean.getReason());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showPayDialog() {
        final CustomAlertDialog customAlertDialog = new CustomAlertDialog(this);
        customAlertDialog.setCanceledOnTouchOutside(true);
        customAlertDialog.setTitle(R.string.video_save_tip_title);
        customAlertDialog.setMessage(R.string.order_payment_fail_info);
        customAlertDialog.setCancelButton(R.string.order_payment_again, new View.OnClickListener() { // from class: com.one.tomato.ui.income.CashProgressDetailActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                customAlertDialog.dismiss();
                CashProgressDetailActivity.this.paymentAgain();
            }
        });
        customAlertDialog.setConfirmButton(R.string.order_balance_return_to_account, new View.OnClickListener() { // from class: com.one.tomato.ui.income.CashProgressDetailActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                customAlertDialog.dismiss();
                CashProgressDetailActivity.this.returnAccount();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void paymentAgain() {
        showWaitingDialog();
        ApiImplService.Companion.getApiImplService().cashPaymentAgain(this.cashRecodeBean.getOrderId(), DBUtil.getMemberId()).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.ui.income.CashProgressDetailActivity.5
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                CashProgressDetailActivity.this.hideWaitingDialog();
                CashProgressDetailActivity.this.finish();
                CashProgressDetailActivity cashProgressDetailActivity = CashProgressDetailActivity.this;
                CashApplyCompeledActivity.startActivity(cashProgressDetailActivity, cashProgressDetailActivity.cashDetailBean);
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                CashProgressDetailActivity.this.hideWaitingDialog();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void returnAccount() {
        showWaitingDialog();
        ApiImplService.Companion.getApiImplService().cashReturnAccount(this.cashRecodeBean.getOrderId(), DBUtil.getMemberId()).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.ui.income.CashProgressDetailActivity.6
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                CashProgressDetailActivity.this.hideWaitingDialog();
                CashProgressDetailActivity.this.finish();
                ToastUtil.showCenterToast((int) R.string.up_apply_comit_suc);
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                CashProgressDetailActivity.this.hideWaitingDialog();
            }
        });
    }
}
