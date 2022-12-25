package com.one.tomato.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.entity.BalanceBean;
import com.one.tomato.p085ui.income.VeritifyIdentityActivity;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.recharge.AlipayUtils;

/* loaded from: classes3.dex */
public class PaySafeCenterDialog extends BottomSheetDialog {
    private boolean isUpdatePwd = false;
    private boolean isUpdateAlipay = false;
    private boolean isUpdateBank = false;
    private TextView tv_trade_pwd = (TextView) findViewById(R.id.tv_trade_pwd);
    private View line2 = findViewById(R.id.line2);
    private View ll_bind_alipay = findViewById(R.id.ll_bind_alipay);
    private TextView tv_bind_alipay = (TextView) findViewById(R.id.tv_bind_alipay);
    private TextView tv_alipay_account = (TextView) findViewById(R.id.tv_alipay_account);
    private View line3 = findViewById(R.id.line3);
    private View rl_bind_bank = findViewById(R.id.rl_bind_bank);
    private TextView tv_bind_bank = (TextView) findViewById(R.id.tv_bind_bank);
    private TextView tv_bank_account = (TextView) findViewById(R.id.tv_bank_account);
    private ImageView iv_cancel = (ImageView) findViewById(R.id.iv_cancel);

    public PaySafeCenterDialog(@NonNull final Context context) {
        super(context);
        setContentView(LayoutInflater.from(context).inflate(R.layout.dialog_pay_safe_center, (ViewGroup) null));
        this.tv_trade_pwd.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.PaySafeCenterDialog.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PaySafeCenterDialog.this.dismiss();
                if (PaySafeCenterDialog.this.isUpdatePwd) {
                    VeritifyIdentityActivity.startActivity(context, 1004);
                } else {
                    VeritifyIdentityActivity.startActivity(context, 1003);
                }
            }
        });
        this.ll_bind_alipay.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.PaySafeCenterDialog.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PaySafeCenterDialog.this.dismiss();
                if (PaySafeCenterDialog.this.isUpdateAlipay) {
                    VeritifyIdentityActivity.startActivity(context, 1001);
                } else {
                    VeritifyIdentityActivity.startActivity(context, 1002);
                }
            }
        });
        this.rl_bind_bank.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.PaySafeCenterDialog.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PaySafeCenterDialog.this.dismiss();
                if (PaySafeCenterDialog.this.isUpdateBank) {
                    VeritifyIdentityActivity.startActivity(context, 1005);
                } else {
                    VeritifyIdentityActivity.startActivity(context, 1006);
                }
            }
        });
        this.iv_cancel.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.PaySafeCenterDialog.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PaySafeCenterDialog.this.dismiss();
            }
        });
    }

    public void hasTradePwd(boolean z) {
        this.isUpdatePwd = z;
        if (this.isUpdatePwd) {
            this.tv_trade_pwd.setText(R.string.trade_pwd_update);
        } else {
            this.tv_trade_pwd.setText(R.string.trade_pwd_setting);
        }
    }

    public void hasBindAlipay(boolean z) {
        this.isUpdateAlipay = z;
        if (this.isUpdateAlipay) {
            this.tv_bind_alipay.setText(R.string.alipay_update);
        } else {
            this.tv_bind_alipay.setText(R.string.alipay_bind);
        }
    }

    public void hasBindBank(boolean z) {
        this.isUpdateBank = z;
        if (this.isUpdateBank) {
            this.tv_bind_bank.setText(R.string.bank_update);
        } else {
            this.tv_bind_bank.setText(R.string.bank_bind);
        }
    }

    public void setBalanceInfo(BalanceBean balanceBean) {
        if (TextUtils.isEmpty(balanceBean.getAlipayAccount())) {
            this.tv_alipay_account.setText("");
        } else {
            this.tv_alipay_account.setText(AlipayUtils.getInstance().hideAlipayAccount(balanceBean.getAlipayAccount()));
        }
        if (TextUtils.isEmpty(balanceBean.getBankAccount())) {
            this.tv_bank_account.setText("");
        } else {
            this.tv_bank_account.setText(AppUtil.hideBankAccount(balanceBean.getBankAccount()));
        }
    }

    public void setOnlyPasswordVisible() {
        this.line2.setVisibility(8);
        this.ll_bind_alipay.setVisibility(8);
        this.line3.setVisibility(8);
        this.rl_bind_bank.setVisibility(8);
    }
}
