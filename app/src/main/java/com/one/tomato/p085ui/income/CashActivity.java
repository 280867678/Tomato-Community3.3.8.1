package com.one.tomato.p085ui.income;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.google.gson.Gson;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.dialog.CustomAlertDialog;
import com.one.tomato.dialog.TradePwdEntryDialog;
import com.one.tomato.entity.BalanceBean;
import com.one.tomato.entity.BaseModel;
import com.one.tomato.entity.CashDetailBean;
import com.one.tomato.entity.TradePwdBean;
import com.one.tomato.mvp.p080ui.feedback.view.FeedbackRechargeIssuesActivity;
import com.one.tomato.net.ResponseObserver;
import com.one.tomato.net.TomatoCallback;
import com.one.tomato.net.TomatoParams;
import com.one.tomato.p085ui.feedback.FeedbackDetailActivity;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.ViewUtil;
import com.one.tomato.utils.encrypt.MD5Util;
import com.one.tomato.utils.recharge.AlipayUtils;
import java.text.DecimalFormat;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_cash)
/* renamed from: com.one.tomato.ui.income.CashActivity */
/* loaded from: classes3.dex */
public class CashActivity extends BaseActivity {
    private BalanceBean balanceBean;
    private CashDetailBean cashDetailBean;
    private TextView cententText;
    @ViewInject(R.id.et_cash_amount)
    private EditText et_cash_amount;
    @ViewInject(R.id.iv_pay_way)
    private ImageView iv_pay_way;
    @ViewInject(R.id.iv_switch)
    private ImageView iv_switch;
    @ViewInject(R.id.ll_cash_error_prompt)
    private View ll_cash_error_prompt;
    @ViewInject(R.id.ll_cash_prompt)
    private View ll_cash_prompt;
    private TradePwdBean tradePwdBean;
    private TradePwdEntryDialog tradePwdEntryDialog;
    @ViewInject(R.id.tv_cash_account)
    private TextView tv_cash_account;
    @ViewInject(R.id.tv_cash_account_lable)
    private TextView tv_cash_account_lable;
    @ViewInject(R.id.tv_cash_confirm)
    private TextView tv_cash_confirm;
    @ViewInject(R.id.tv_cash_error_prompt)
    private TextView tv_cash_error_prompt;
    @ViewInject(R.id.tv_cash_name)
    private TextView tv_cash_name;
    @ViewInject(R.id.tv_cash_prompt_amount)
    private TextView tv_cash_prompt_amount;
    @ViewInject(R.id.tv_cash_prompt_one)
    private TextView tv_cash_prompt_one;
    @ViewInject(R.id.tv_cash_prompt_two)
    private TextView tv_cash_prompt_two;
    @ViewInject(R.id.tv_cash_rule)
    private TextView tv_cash_rule;
    private int cashType = 1;
    private int accountType = 0;

    /* renamed from: df */
    private DecimalFormat f1752df = new DecimalFormat("0.00");

    public static void startActivity(Context context, BalanceBean balanceBean, int i) {
        Intent intent = new Intent();
        intent.setClass(context, CashActivity.class);
        intent.putExtra("account_type", i);
        intent.putExtra("account_balance", balanceBean);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initTitleBar();
        this.titleTV.setText(R.string.cash_title);
        this.rightTV.setVisibility(0);
        this.rightTV.setText(R.string.cash_help);
        fetchIntentArg();
        init();
        setListener();
    }

    private void fetchIntentArg() {
        Intent intent = getIntent();
        this.accountType = intent.getIntExtra("account_type", 1);
        this.balanceBean = (BalanceBean) intent.getExtras().getSerializable("account_balance");
        if (!TextUtils.isEmpty(this.balanceBean.getAlipayAccount()) && !TextUtils.isEmpty(this.balanceBean.getBankAccount())) {
            this.iv_switch.setVisibility(0);
        } else {
            this.iv_switch.setVisibility(4);
        }
        if (!TextUtils.isEmpty(this.balanceBean.getAlipayAccount())) {
            this.cashType = 1;
        } else if (!TextUtils.isEmpty(this.balanceBean.getBankAccount())) {
            this.cashType = 2;
        }
        if (this.balanceBean != null) {
            int i = this.cashType;
            if (i == 1) {
                this.iv_pay_way.setImageResource(R.drawable.recharge_select_alipay);
                this.tv_cash_account_lable.setText(R.string.cash_mode_alipay);
                this.tv_cash_account.setText(AlipayUtils.getInstance().hideAlipayAccount(this.balanceBean.getAlipayAccount()));
                TextView textView = this.tv_cash_name;
                textView.setText("(" + AlipayUtils.getInstance().hideAlipayName(this.balanceBean.getAlipayName()) + ")");
            } else if (i == 2) {
                this.iv_pay_way.setImageResource(R.drawable.recharge_select_union);
                this.tv_cash_account_lable.setText(this.balanceBean.getBankName());
                this.tv_cash_account.setText(AppUtil.hideBankAccount(this.balanceBean.getBankAccount()));
                TextView textView2 = this.tv_cash_name;
                textView2.setText("(" + AlipayUtils.getInstance().hideAlipayName(this.balanceBean.getCardName()) + ")");
            }
            this.ll_cash_prompt.setVisibility(0);
            this.tv_cash_prompt_one.setText(R.string.cash_prompt_cash_amount);
            this.tv_cash_prompt_amount.setText(FormatUtil.formatTomato2RMB(this.balanceBean.getCanWithdrawBalance()));
            this.tv_cash_prompt_two.setText(AppUtil.getString(R.string.cash_prompt_cash_max_amount, Integer.valueOf(this.balanceBean.getSingleMaxWithdraw())));
            if ("1".equals(this.balanceBean.getCheckPeriod())) {
                this.tv_cash_confirm.setText(R.string.cash_confirm);
            } else {
                this.tv_cash_confirm.setText(R.string.cash_confirm_1);
            }
        }
    }

    private void init() {
        this.tradePwdEntryDialog = new TradePwdEntryDialog(this);
        this.tipDialog = new CustomAlertDialog(this, false);
        this.tipDialog.setBackgroundResource(R.drawable.common_shape_solid_corner12_white);
        this.tipDialog.setCanceledOnTouchOutside(false);
        this.cententText = new TextView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.topMargin = getResources().getDimensionPixelSize(R.dimen.dimen_14);
        layoutParams.leftMargin = getResources().getDimensionPixelSize(R.dimen.dimen_16);
        layoutParams.rightMargin = getResources().getDimensionPixelSize(R.dimen.dimen_16);
        this.cententText.setLayoutParams(layoutParams);
        this.cententText.setTextSize(2, 14.0f);
        this.cententText.setTextColor(getResources().getColor(R.color.text_969696));
        this.cententText.setGravity(1);
        this.tipDialog.setContentView(this.cententText);
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.dimen_23);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.dimen_10);
        this.tipDialog.getCancelButton().setPadding(dimensionPixelSize, dimensionPixelSize2, dimensionPixelSize, dimensionPixelSize2);
        this.tipDialog.getConfirmButton().setPadding(dimensionPixelSize, dimensionPixelSize2, dimensionPixelSize, dimensionPixelSize2);
        this.et_cash_amount.setFocusable(true);
        this.et_cash_amount.requestFocus();
        this.et_cash_amount.requestFocusFromTouch();
        this.et_cash_amount.setTypeface(ViewUtil.getNumFontTypeface(this));
    }

    @Event({R.id.tv_cash_confirm, R.id.tv_cash_all, R.id.right_txt})
    private void onClick(View view) {
        int id = view.getId();
        if (id != R.id.right_txt) {
            if (id != R.id.tv_cash_all) {
                if (id != R.id.tv_cash_confirm) {
                    return;
                }
                veritifyCash();
                return;
            }
            String str = FormatUtil.formatZero(Double.valueOf(this.balanceBean.getCanWithdrawBalance() / 10.0d)) + "";
            this.et_cash_amount.setText(str);
            this.et_cash_amount.setSelection(str.length());
            return;
        }
        int i = 0;
        int i2 = this.accountType;
        if (i2 == 1) {
            i = 4;
        } else if (i2 == 2) {
            i = 6;
        } else if (i2 == 3) {
            i = 3;
        } else if (i2 == 4) {
            i = 5;
        }
        FeedbackRechargeIssuesActivity.Companion.startActivity(this, i);
    }

    private void setListener() {
        this.iv_switch.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.income.CashActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CashActivity cashActivity = CashActivity.this;
                int i = 1;
                if (cashActivity.cashType == 1) {
                    i = 2;
                }
                cashActivity.cashType = i;
                CashActivity.this.switchAccount();
            }
        });
        this.tradePwdEntryDialog.setOnTradePwdListener(new TradePwdEntryDialog.OnTradePwdListener() { // from class: com.one.tomato.ui.income.CashActivity.2
            @Override // com.one.tomato.dialog.TradePwdEntryDialog.OnTradePwdListener
            public void onTextChanged(String str) {
            }

            @Override // com.one.tomato.dialog.TradePwdEntryDialog.OnTradePwdListener
            public void onCompleted(String str) {
                CashActivity.this.veritifyTradePwd(str);
                CashActivity.this.tradePwdEntryDialog.dismiss();
            }
        });
        this.et_cash_amount.addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.ui.income.CashActivity.3
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            @SuppressLint({"StringFormatInvalid"})
            public void afterTextChanged(Editable editable) {
                double d;
                String trim = editable.toString().trim();
                CashActivity.this.tv_cash_confirm.setEnabled(false);
                try {
                    CashActivity.this.ll_cash_prompt.setVisibility(0);
                    if (TextUtils.isEmpty(trim)) {
                        CashActivity.this.ll_cash_error_prompt.setVisibility(8);
                        CashActivity.this.ll_cash_prompt.setVisibility(0);
                        CashActivity.this.tv_cash_prompt_one.setText(R.string.cash_prompt_cash_amount);
                        CashActivity.this.tv_cash_prompt_amount.setText(FormatUtil.formatTomato2RMB(CashActivity.this.balanceBean.getCanWithdrawBalance()));
                        CashActivity.this.tv_cash_prompt_two.setText(AppUtil.getString(R.string.cash_prompt_cash_max_amount, Integer.valueOf(CashActivity.this.balanceBean.getSingleMaxWithdraw())));
                        return;
                    }
                    double parseDouble = Double.parseDouble(trim);
                    double canWithdrawBalance = CashActivity.this.balanceBean.getCanWithdrawBalance() / 10.0d;
                    double singleMaxWithdraw = CashActivity.this.balanceBean.getSingleMaxWithdraw();
                    double singleMinimumWithdraw = CashActivity.this.balanceBean.getSingleMinimumWithdraw();
                    float taxRate = CashActivity.this.balanceBean.getTaxRate();
                    double minTax = CashActivity.this.balanceBean.getMinTax();
                    int intLimit = CashActivity.this.balanceBean.getIntLimit();
                    if (parseDouble <= 0.0d) {
                        CashActivity.this.ll_cash_error_prompt.setVisibility(0);
                        CashActivity.this.ll_cash_prompt.setVisibility(8);
                        CashActivity.this.tv_cash_error_prompt.setText(CashActivity.this.getResources().getString(R.string.cash_error_cash_amount));
                    } else if (parseDouble > canWithdrawBalance) {
                        CashActivity.this.ll_cash_error_prompt.setVisibility(0);
                        CashActivity.this.ll_cash_prompt.setVisibility(8);
                        CashActivity.this.tv_cash_error_prompt.setText(R.string.cash_error_avail_amount);
                    } else if (parseDouble < singleMinimumWithdraw) {
                        CashActivity.this.ll_cash_error_prompt.setVisibility(0);
                        CashActivity.this.ll_cash_prompt.setVisibility(8);
                        CashActivity.this.tv_cash_error_prompt.setText(CashActivity.this.getResources().getString(R.string.cash_error_min_cash_amount, String.valueOf(singleMinimumWithdraw)));
                    } else if (parseDouble > singleMaxWithdraw) {
                        CashActivity.this.ll_cash_error_prompt.setVisibility(0);
                        CashActivity.this.ll_cash_prompt.setVisibility(8);
                        CashActivity.this.tv_cash_error_prompt.setText(CashActivity.this.getResources().getString(R.string.cash_error_max_amount, String.valueOf(singleMaxWithdraw)));
                    } else if (parseDouble % intLimit != 0.0d) {
                        CashActivity.this.ll_cash_error_prompt.setVisibility(0);
                        CashActivity.this.ll_cash_prompt.setVisibility(8);
                        CashActivity.this.tv_cash_error_prompt.setText(CashActivity.this.getResources().getString(R.string.cash_rule_limit_hint, Integer.valueOf(intLimit)));
                    } else {
                        try {
                            CashActivity.this.ll_cash_error_prompt.setVisibility(8);
                            CashActivity.this.ll_cash_prompt.setVisibility(0);
                            CashActivity.this.tv_cash_prompt_one.setText(R.string.cash_prompt_real_amount);
                            double d2 = (parseDouble * taxRate) / 100.0d;
                            if (d2 > minTax) {
                                minTax = d2;
                            }
                            CashActivity.this.tv_cash_prompt_amount.setText(CashActivity.this.f1752df.format(parseDouble - minTax) + AppUtil.getString(R.string.common_renmingbi));
                            CashActivity.this.tv_cash_prompt_two.setText(CashActivity.this.getResources().getString(R.string.cash_prompt_rate, CashActivity.this.f1752df.format(d) + "%"));
                            CashActivity.this.tv_cash_confirm.setEnabled(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e2) {
                    CashActivity.this.ll_cash_error_prompt.setVisibility(0);
                    CashActivity.this.ll_cash_prompt.setVisibility(8);
                    CashActivity.this.tv_cash_error_prompt.setText(R.string.cash_amount_input_error);
                    e2.printStackTrace();
                }
            }
        });
        this.tv_cash_rule.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.income.CashActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CashActivity.this.balanceBean == null) {
                    return;
                }
                View inflate = LayoutInflater.from(((BaseActivity) CashActivity.this).mContext).inflate(R.layout.dailog_cash_rule, (ViewGroup) null);
                CustomAlertDialog customAlertDialog = new CustomAlertDialog(((BaseActivity) CashActivity.this).mContext);
                customAlertDialog.setContentView(inflate);
                customAlertDialog.bottomButtonVisiblity(2);
                customAlertDialog.setConfirmButtonListener();
                TextView textView = (TextView) inflate.findViewById(R.id.tv_cash_period);
                TextView textView2 = (TextView) inflate.findViewById(R.id.tv_remit_period);
                TextView textView3 = (TextView) inflate.findViewById(R.id.tv_rate);
                TextView textView4 = (TextView) inflate.findViewById(R.id.tv_times);
                TextView textView5 = (TextView) inflate.findViewById(R.id.tv_min);
                TextView textView6 = (TextView) inflate.findViewById(R.id.tv_max);
                TextView textView7 = (TextView) inflate.findViewById(R.id.tv_limit);
                if ("0".equals(CashActivity.this.balanceBean.getPresentBeginTime()) || "0".equals(CashActivity.this.balanceBean.getPresentEndTime())) {
                    textView.setText(AppUtil.getString(R.string.cash_rule_period_no_limit));
                } else {
                    textView.setText(AppUtil.getString(R.string.cash_rule_period_range, CashActivity.this.balanceBean.getPresentBeginTime(), CashActivity.this.balanceBean.getPresentEndTime()));
                }
                if ("1".equals(CashActivity.this.balanceBean.getCheckPeriod())) {
                    textView2.setText(R.string.cash_rule_remit_t1);
                } else {
                    textView2.setText(R.string.cash_rule_remit_no_limit);
                }
                float taxRate = CashActivity.this.balanceBean.getTaxRate();
                textView3.setText(CashActivity.this.f1752df.format(taxRate) + "%");
                textView4.setText(AppUtil.getString(R.string.cash_rule_times_hint, CashActivity.this.balanceBean.getLimitNumberWithdraw()));
                textView5.setText(CashActivity.this.balanceBean.getSingleMinimumWithdraw() + "");
                textView6.setText(CashActivity.this.balanceBean.getSingleMaxWithdraw() + "");
                textView7.setText(AppUtil.getString(R.string.cash_rule_limit_hint, Integer.valueOf(CashActivity.this.balanceBean.getIntLimit())));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void switchAccount() {
        if (this.balanceBean != null) {
            int i = this.cashType;
            if (i == 1) {
                this.iv_pay_way.setImageResource(R.drawable.recharge_select_alipay);
                this.tv_cash_account_lable.setText(R.string.cash_mode_alipay);
                this.tv_cash_account.setText(AlipayUtils.getInstance().hideAlipayAccount(this.balanceBean.getAlipayAccount()));
                this.tv_cash_name.setText("(" + AlipayUtils.getInstance().hideAlipayName(this.balanceBean.getAlipayName()) + ")");
            } else if (i == 2) {
                this.iv_pay_way.setImageResource(R.drawable.recharge_select_union);
                this.tv_cash_account_lable.setText(this.balanceBean.getBankName());
                this.tv_cash_account.setText(AppUtil.hideBankAccount(this.balanceBean.getBankAccount()));
                this.tv_cash_name.setText("(" + AlipayUtils.getInstance().hideAlipayName(this.balanceBean.getCardName()) + ")");
            }
            String obj = this.et_cash_amount.getText().toString();
            if (!TextUtils.isEmpty(obj)) {
                try {
                    this.tv_cash_prompt_one.setVisibility(0);
                    this.tv_cash_prompt_amount.setVisibility(0);
                    this.tv_cash_prompt_two.setVisibility(0);
                    double parseDouble = Double.parseDouble(obj);
                    float taxRate = this.balanceBean.getTaxRate();
                    this.tv_cash_prompt_one.setText(R.string.cash_prompt_real_amount);
                    double d = taxRate;
                    this.tv_cash_prompt_amount.setText(this.f1752df.format(parseDouble - ((parseDouble * d) / 100.0d)) + AppUtil.getString(R.string.common_renmingbi));
                    this.tv_cash_prompt_two.setText(getResources().getString(R.string.cash_prompt_rate, this.f1752df.format(d) + "%"));
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
            this.ll_cash_error_prompt.setVisibility(8);
            this.ll_cash_prompt.setVisibility(0);
            this.tv_cash_prompt_one.setText(R.string.cash_prompt_cash_amount);
            this.tv_cash_prompt_amount.setText(FormatUtil.formatTomato2RMB(this.balanceBean.getCanWithdrawBalance()));
            this.tv_cash_prompt_two.setText(AppUtil.getString(R.string.cash_prompt_cash_max_amount, Integer.valueOf(this.balanceBean.getSingleMaxWithdraw())));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showEntryTradePwdDialog() {
        TradePwdEntryDialog tradePwdEntryDialog = this.tradePwdEntryDialog;
        if (tradePwdEntryDialog == null) {
            return;
        }
        tradePwdEntryDialog.show();
    }

    private void showFreeDialog() {
        this.tipDialog.bottomButtonVisiblity(0);
        this.tipDialog.setTitle(R.string.common_notify);
        this.cententText.setText(R.string.cash_free_amount_error_message);
        this.tipDialog.setCancelButtonBackgroundRes(R.drawable.common_shape_solid_corner30_blue);
        this.tipDialog.setCancelButtonTextColor(R.color.white);
        this.tipDialog.setCancelButton(R.string.common_cancel, new View.OnClickListener() { // from class: com.one.tomato.ui.income.CashActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((BaseActivity) CashActivity.this).tipDialog.dismiss();
            }
        });
        this.tipDialog.setConfirmButtonBackgroundRes(R.drawable.common_shape_solid_corner30_blue);
        this.tipDialog.setConfirmButtonTextColor(R.color.white);
        this.tipDialog.setConfirmButton(R.string.my_feedback, new View.OnClickListener() { // from class: com.one.tomato.ui.income.CashActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CashActivity cashActivity = CashActivity.this;
                FeedbackDetailActivity.startActivity(cashActivity, "cash", cashActivity.getResources().getString(R.string.feedback_cash_title));
                ((BaseActivity) CashActivity.this).tipDialog.dismiss();
            }
        });
        this.tipDialog.show();
    }

    private void showTradePwdErrorDialog(int i) {
        this.tipDialog.bottomButtonVisiblity(0);
        this.tipDialog.setTitle(R.string.common_notify);
        this.cententText.setText(AppUtil.getString(R.string.trade_pwd_wrong_times, Integer.valueOf(i)));
        this.tipDialog.setCancelButtonBackgroundRes(R.color.white);
        this.tipDialog.setCancelButtonTextColor(R.color.text_dark);
        this.tipDialog.setCancelButton(R.string.trade_pwd_input_again, new View.OnClickListener() { // from class: com.one.tomato.ui.income.CashActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CashActivity.this.et_cash_amount.setFocusable(true);
                ((BaseActivity) CashActivity.this).tipDialog.dismiss();
                CashActivity.this.showEntryTradePwdDialog();
            }
        });
        this.tipDialog.setConfirmButtonBackgroundRes(R.drawable.common_selector_solid_corner30_coloraccent);
        this.tipDialog.setConfirmButtonTextColor(R.color.white);
        this.tipDialog.setConfirmButton(R.string.trade_pwd_forget, new View.OnClickListener() { // from class: com.one.tomato.ui.income.CashActivity.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                VeritifyIdentityActivity.startActivity(CashActivity.this, 1004);
                ((BaseActivity) CashActivity.this).tipDialog.dismiss();
            }
        });
        this.tipDialog.show();
    }

    private void showLockTradePwdDialog() {
        this.tipDialog.bottomButtonVisiblity(2);
        this.tipDialog.setTitle(R.string.common_notify);
        this.cententText.setText(R.string.trade_pwd_wrong_too_times);
        this.tipDialog.setConfirmButtonBackgroundRes(R.drawable.common_selector_solid_corner30_coloraccent);
        this.tipDialog.setConfirmButtonTextColor(R.color.white);
        this.tipDialog.setConfirmButton(R.string.trade_pws_modify, new View.OnClickListener() { // from class: com.one.tomato.ui.income.CashActivity.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                VeritifyIdentityActivity.startActivity(CashActivity.this, 1004);
                ((BaseActivity) CashActivity.this).tipDialog.dismiss();
                CashActivity.this.finish();
            }
        });
        this.tipDialog.show();
    }

    private void veritifyCash() {
        if (this.balanceBean.isHasSetTransactionPwd()) {
            showEntryTradePwdDialog();
            return;
        }
        VeritifyIdentityActivity.startActivity(this.mContext, 1003);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void veritifyTradePwd(String str) {
        showWaitingDialog();
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/withdraw/person/checkTransactionPwd", 20000);
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.addParameter("pwd", MD5Util.md5(str));
        tomatoParams.post(new TomatoCallback((ResponseObserver) this, 10001, TradePwdBean.class));
    }

    private void applyCash(String str, String str2) {
        showWaitingDialog();
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/withdraw/apply", 20000);
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.addParameter("money", str2);
        tomatoParams.addParameter("sec", str);
        tomatoParams.addParameter("type", Integer.valueOf(this.cashType));
        tomatoParams.addParameter("accountType", Integer.valueOf(this.accountType));
        tomatoParams.post(new TomatoCallback((ResponseObserver) this, 10005, CashDetailBean.class));
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public void handleResponse(Message message) {
        super.handleResponse(message);
        hideWaitingDialog();
        BaseModel baseModel = (BaseModel) message.obj;
        int i = message.what;
        if (i == 10001) {
            Object obj = baseModel.obj;
            if (obj == null) {
                return;
            }
            this.tradePwdBean = (TradePwdBean) obj;
            applyCash(this.tradePwdBean.getSec(), this.f1752df.format(Float.parseFloat(this.et_cash_amount.getText().toString())));
        } else if (i != 10005) {
        } else {
            Object obj2 = baseModel.obj;
            if (obj2 != null) {
                this.cashDetailBean = (CashDetailBean) obj2;
                this.cashDetailBean.setAmount(Float.parseFloat(this.et_cash_amount.getText().toString()));
                CashApplyCompeledActivity.startActivity(this, this.cashDetailBean);
                finish();
                return;
            }
            ToastUtil.showCenterToast((int) R.string.cash_amount_fail);
        }
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public boolean handleResponseError(Message message) {
        BaseModel baseModel = (BaseModel) message.obj;
        hideWaitingDialog();
        int i = message.what;
        if (i == 10001) {
            int i2 = baseModel.code;
            if (50001 == i2) {
                if (!TextUtils.isEmpty(baseModel.data)) {
                    this.tradePwdBean = (TradePwdBean) new Gson().fromJson(baseModel.data, (Class<Object>) TradePwdBean.class);
                    showTradePwdErrorDialog(this.tradePwdBean.getLeaveTimes());
                }
            } else if (50002 != i2) {
                return true;
            } else {
                showLockTradePwdDialog();
            }
        } else if (i == 10005) {
            this.tradePwdBean = (TradePwdBean) baseModel.obj;
            if (baseModel.code != -1001) {
                return true;
            }
            showFreeDialog();
        }
        return false;
    }
}
