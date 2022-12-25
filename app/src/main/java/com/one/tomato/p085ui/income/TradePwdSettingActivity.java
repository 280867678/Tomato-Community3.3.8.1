package com.one.tomato.p085ui.income;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.entity.BaseModel;
import com.one.tomato.entity.TradePwdBean;
import com.one.tomato.net.ResponseObserver;
import com.one.tomato.net.TomatoCallback;
import com.one.tomato.net.TomatoParams;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.encrypt.MD5Util;
import com.one.tomato.widget.ClearEditText;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_trade_pwd_setting)
/* renamed from: com.one.tomato.ui.income.TradePwdSettingActivity */
/* loaded from: classes3.dex */
public class TradePwdSettingActivity extends BaseActivity {
    @ViewInject(R.id.et_trade_pwd)
    private ClearEditText et_trade_pwd;
    @ViewInject(R.id.et_trade_pwd_confirm)
    private ClearEditText et_trade_pwd_confirm;
    private boolean isConfirmPwd;
    private boolean isTradePwd;
    private String sec_code;
    @ViewInject(R.id.tv_next)
    private TextView tv_next;
    @ViewInject(R.id.tv_tip)
    private TextView tv_tip;
    @ViewInject(R.id.tv_trade_pwd_first)
    private TextView tv_trade_pwd_first;
    private int type;

    public static void startActivity(Context context, int i, String str) {
        Intent intent = new Intent();
        intent.setClass(context, TradePwdSettingActivity.class);
        intent.putExtra("trade_pwd_type", i);
        intent.putExtra("sec_code", str);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initTitleBar();
        setListener();
        fetchIntentArg();
    }

    private void fetchIntentArg() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.getExtras().containsKey("trade_pwd_type")) {
                this.type = intent.getExtras().getInt("trade_pwd_type");
                updateView(this.type);
            }
            if (!intent.getExtras().containsKey("sec_code")) {
                return;
            }
            this.sec_code = intent.getExtras().getString("sec_code");
        }
    }

    private void setListener() {
        this.et_trade_pwd.addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.ui.income.TradePwdSettingActivity.1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().length() > 0) {
                    TradePwdSettingActivity.this.isTradePwd = true;
                } else {
                    TradePwdSettingActivity.this.isTradePwd = false;
                }
                if (!TradePwdSettingActivity.this.isTradePwd || !TradePwdSettingActivity.this.isConfirmPwd) {
                    TradePwdSettingActivity.this.tv_next.setEnabled(false);
                } else {
                    TradePwdSettingActivity.this.tv_next.setEnabled(true);
                }
            }
        });
        this.et_trade_pwd_confirm.addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.ui.income.TradePwdSettingActivity.2
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().length() > 0) {
                    TradePwdSettingActivity.this.isConfirmPwd = true;
                } else {
                    TradePwdSettingActivity.this.isConfirmPwd = false;
                }
                if (!TradePwdSettingActivity.this.isTradePwd || !TradePwdSettingActivity.this.isConfirmPwd) {
                    TradePwdSettingActivity.this.tv_next.setEnabled(false);
                } else {
                    TradePwdSettingActivity.this.tv_next.setEnabled(true);
                }
            }
        });
    }

    private void updateView(int i) {
        if (i == 1001) {
            this.titleTV.setText(R.string.trade_pwd_setting_title);
            this.tv_trade_pwd_first.setText(R.string.trade_pwd_setting_lable);
        } else if (i != 1002) {
        } else {
            this.titleTV.setText(R.string.trade_pwd_update_title);
            this.tv_trade_pwd_first.setText(R.string.trade_pwd_update_lable);
        }
    }

    private boolean veritifyPwd(String str, String str2) {
        return str.equals(str2);
    }

    private void setTipMsg(String str) {
        if (this.tv_tip.getVisibility() == 8) {
            this.tv_tip.setVisibility(0);
        }
        this.tv_tip.setTextColor(getResources().getColor(R.color.red_ff5252));
        this.tv_tip.setText(str);
    }

    private void submitTradePwd(String str, String str2) {
        showWaitingDialog();
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/withdraw/person/setTransactionPwd");
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.addParameter("pwd", MD5Util.md5(str));
        tomatoParams.addParameter("pwd2", MD5Util.md5(str2));
        tomatoParams.addParameter("sec", this.sec_code);
        tomatoParams.post(new TomatoCallback((ResponseObserver) this, 1, TradePwdBean.class));
    }

    @Event({R.id.tv_next})
    private void onClick(View view) {
        if (view.getId() != R.id.tv_next) {
            return;
        }
        String obj = this.et_trade_pwd.getText().toString();
        String obj2 = this.et_trade_pwd_confirm.getText().toString();
        if (veritifyPwd(obj, obj2)) {
            submitTradePwd(obj, obj2);
        } else {
            setTipMsg(getResources().getString(R.string.trade_pwd_error_msg));
        }
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public void handleResponse(Message message) {
        super.handleResponse(message);
        BaseModel baseModel = (BaseModel) message.obj;
        hideWaitingDialog();
        if (message.what != 1) {
            return;
        }
        int i = this.type;
        if (i == 1001) {
            ToastUtil.showCenterToast((int) R.string.trade_pws_setting_success);
        } else if (i == 1002) {
            ToastUtil.showCenterToast((int) R.string.trade_pws_setting_success);
        }
        finish();
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public boolean handleResponseError(Message message) {
        hideWaitingDialog();
        return message.what == 1;
    }
}
