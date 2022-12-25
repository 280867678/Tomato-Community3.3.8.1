package com.one.tomato.p085ui.income;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.entity.Bank;
import com.one.tomato.entity.BaseModel;
import com.one.tomato.net.TomatoCallback;
import com.one.tomato.net.TomatoParams;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.widget.ClearEditText;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_bank_bind)
/* renamed from: com.one.tomato.ui.income.BankBindActivity */
/* loaded from: classes3.dex */
public class BankBindActivity extends BaseActivity {
    private boolean accountInput;
    private Bank bank;
    @ViewInject(R.id.et_bank_account)
    private ClearEditText et_bank_account;
    @ViewInject(R.id.et_bank_name)
    private ClearEditText et_bank_name;
    private boolean hasAccount;
    private boolean nameInput;
    @ViewInject(R.id.rl_bank_select)
    private RelativeLayout rl_bank_select;
    private boolean selectBank;
    @ViewInject(R.id.tv_bank_title)
    private TextView tv_bank_title;
    @ViewInject(R.id.tv_confirm)
    private TextView tv_confirm;

    public static void startActivity(Context context, boolean z) {
        Intent intent = new Intent();
        intent.setClass(context, BankBindActivity.class);
        intent.putExtra("bank_account", z);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initTitleBar();
        this.hasAccount = getIntent().getExtras().getBoolean("bank_account");
        if (this.hasAccount) {
            this.titleTV.setText(R.string.bank_update_title);
        } else {
            this.titleTV.setText(R.string.bank_bind_title);
        }
        this.rl_bank_select.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.income.BankBindActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BankSelectListActivity.startActivity(((BaseActivity) BankBindActivity.this).mContext);
            }
        });
        this.et_bank_account.addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.ui.income.BankBindActivity.2
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().length() > 0) {
                    BankBindActivity.this.accountInput = true;
                } else {
                    BankBindActivity.this.accountInput = false;
                }
                if (!BankBindActivity.this.selectBank || !BankBindActivity.this.accountInput || !BankBindActivity.this.nameInput) {
                    BankBindActivity.this.tv_confirm.setEnabled(false);
                } else {
                    BankBindActivity.this.tv_confirm.setEnabled(true);
                }
            }
        });
        this.et_bank_name.addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.ui.income.BankBindActivity.3
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().length() > 0) {
                    BankBindActivity.this.nameInput = true;
                } else {
                    BankBindActivity.this.nameInput = false;
                }
                if (!BankBindActivity.this.selectBank || !BankBindActivity.this.accountInput || !BankBindActivity.this.nameInput) {
                    BankBindActivity.this.tv_confirm.setEnabled(false);
                } else {
                    BankBindActivity.this.tv_confirm.setEnabled(true);
                }
            }
        });
        this.tv_confirm.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.income.BankBindActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BankBindActivity.this.bandBank();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bandBank() {
        showWaitingDialog();
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/withdraw/account/bind/bank");
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.addParameter("bankAccount", this.et_bank_account.getText().toString().trim());
        tomatoParams.addParameter("bankName", this.et_bank_name.getText().toString().trim());
        tomatoParams.addParameter("bankId", this.bank.getBankId());
        tomatoParams.get(new TomatoCallback(this, 1));
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public void handleResponse(Message message) {
        super.handleResponse(message);
        BaseModel baseModel = (BaseModel) message.obj;
        hideWaitingDialog();
        if (message.what == 1) {
            ToastUtil.showCenterToast(baseModel.message);
            finish();
        }
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public boolean handleResponseError(Message message) {
        BaseModel baseModel = (BaseModel) message.obj;
        hideWaitingDialog();
        int i = message.what;
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 1 && i2 == -1) {
            this.bank = (Bank) intent.getExtras().getParcelable("intent_result");
            this.tv_bank_title.setText(this.bank.getName());
            this.selectBank = true;
        }
    }
}
