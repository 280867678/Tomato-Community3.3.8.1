package com.one.tomato.p085ui.income;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.net.TomatoCallback;
import com.one.tomato.net.TomatoParams;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.ToastUtil;
import com.tomatolive.library.utils.LogConstants;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_alipay_bind)
/* renamed from: com.one.tomato.ui.income.AlipayBindActivity */
/* loaded from: classes3.dex */
public class AlipayBindActivity extends BaseActivity {
    private boolean accountInput;
    @ViewInject(R.id.et_alipay_account)
    private EditText et_alipay_account;
    @ViewInject(R.id.et_alipay_name)
    private EditText et_alipay_name;
    private boolean hasAlipayAccount;
    private boolean nameInput;
    @ViewInject(R.id.tv_confirm)
    private TextView tv_confirm;
    @ViewInject(R.id.tv_error)
    private TextView tv_error;

    public static void startActivity(Context context, boolean z) {
        Intent intent = new Intent();
        intent.setClass(context, AlipayBindActivity.class);
        intent.putExtra("alipay_account", z);
        ((Activity) context).startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initTitleBar();
        fetchIntentArgs();
        init();
        setListener();
    }

    private void init() {
        if (this.hasAlipayAccount) {
            this.titleTV.setText(R.string.alipay_bind_update);
        } else {
            this.titleTV.setText(R.string.alipay_bind);
        }
    }

    private void setListener() {
        this.et_alipay_account.addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.ui.income.AlipayBindActivity.1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    AlipayBindActivity.this.accountInput = true;
                } else {
                    AlipayBindActivity.this.accountInput = false;
                }
                if (!AlipayBindActivity.this.accountInput || !AlipayBindActivity.this.nameInput) {
                    AlipayBindActivity.this.tv_confirm.setEnabled(false);
                } else {
                    AlipayBindActivity.this.tv_confirm.setEnabled(true);
                }
            }
        });
        this.et_alipay_name.addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.ui.income.AlipayBindActivity.2
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    AlipayBindActivity.this.nameInput = true;
                } else {
                    AlipayBindActivity.this.nameInput = false;
                }
                if (!AlipayBindActivity.this.accountInput || !AlipayBindActivity.this.nameInput) {
                    AlipayBindActivity.this.tv_confirm.setEnabled(false);
                } else {
                    AlipayBindActivity.this.tv_confirm.setEnabled(true);
                }
            }
        });
        this.tv_confirm.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.income.AlipayBindActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (AlipayBindActivity.this.veritifyAccount(AlipayBindActivity.this.et_alipay_account.getText().toString())) {
                    AlipayBindActivity.this.requestUpdateAlipayAcount();
                }
            }
        });
    }

    private void fetchIntentArgs() {
        Intent intent = getIntent();
        if (intent == null || !intent.getExtras().containsKey("alipay_account")) {
            return;
        }
        this.hasAlipayAccount = intent.getExtras().getBoolean("alipay_account", false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean veritifyAccount(String str) {
        if (str.trim().length() < 6) {
            this.tv_error.setText(R.string.alipay_length_tip);
            return false;
        }
        this.tv_error.setText("");
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestUpdateAlipayAcount() {
        showWaitingDialog();
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/withdraw/account/bind");
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.addParameter(LogConstants.ACCOUNT, this.et_alipay_account.getText().toString().trim());
        tomatoParams.addParameter("name", this.et_alipay_name.getText().toString().trim());
        tomatoParams.get(new TomatoCallback(this, 1));
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public void handleResponse(Message message) {
        super.handleResponse(message);
        hideWaitingDialog();
        if (message.what != 1) {
            return;
        }
        if (this.hasAlipayAccount) {
            ToastUtil.showCenterToast((int) R.string.alipay_update_success_message);
        } else {
            AlipayBindSuccessActivity.startActivity(this, this.et_alipay_account.getText().toString().trim());
        }
        finish();
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public boolean handleResponseError(Message message) {
        hideWaitingDialog();
        return message.what == 1;
    }
}
