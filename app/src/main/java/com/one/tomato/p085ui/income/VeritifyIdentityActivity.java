package com.one.tomato.p085ui.income;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.entity.BaseModel;
import com.one.tomato.entity.VeritifyIdentityBean;
import com.one.tomato.entity.p079db.UserInfo;
import com.one.tomato.mvp.p080ui.login.view.CountryCodeActivity;
import com.one.tomato.net.ResponseObserver;
import com.one.tomato.net.TomatoCallback;
import com.one.tomato.net.TomatoParams;
import com.one.tomato.p085ui.feedback.FeedbackEnterActivity;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.TimerTaskUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.widget.ClearEditText;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_veritify_identity)
/* renamed from: com.one.tomato.ui.income.VeritifyIdentityActivity */
/* loaded from: classes3.dex */
public class VeritifyIdentityActivity extends BaseActivity {
    @ViewInject(R.id.et_code)
    private ClearEditText et_code;
    @ViewInject(R.id.et_content)
    private ClearEditText et_content;
    private boolean isCodeInput;
    private boolean isContentInput;
    @ViewInject(R.id.tv_country_code)
    private TextView tv_country_code;
    @ViewInject(R.id.tv_error)
    private TextView tv_error;
    @ViewInject(R.id.tv_get_code)
    private TextView tv_get_code;
    @ViewInject(R.id.tv_next)
    private TextView tv_next;
    private int type;
    private UserInfo userInfo;

    public static void startActivity(Context context, int i) {
        Intent intent = new Intent();
        intent.setClass(context, VeritifyIdentityActivity.class);
        intent.putExtra("veritify_type", i);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        fetchIntentArg();
        initTitleBar();
        init();
        setListener();
    }

    private void fetchIntentArg() {
        Intent intent = getIntent();
        if (intent == null || !intent.getExtras().containsKey("veritify_type")) {
            return;
        }
        this.type = intent.getExtras().getInt("veritify_type");
    }

    private void init() {
        this.userInfo = DBUtil.getUserInfo();
        if (TextUtils.isEmpty(this.userInfo.getPhone())) {
            this.et_content.setEnabled(true);
        } else {
            this.et_content.setEnabled(false);
            this.et_content.setText(this.userInfo.getPhone());
            this.tv_country_code.setText(this.userInfo.getCountryCodeStr());
            this.tv_get_code.setEnabled(true);
        }
        this.titleTV.setText(R.string.veritify_identity_title);
    }

    private void setListener() {
        this.et_content.addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.ui.income.VeritifyIdentityActivity.1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().length() > 0) {
                    VeritifyIdentityActivity.this.isContentInput = true;
                    VeritifyIdentityActivity.this.tv_get_code.setEnabled(true);
                } else {
                    VeritifyIdentityActivity.this.isContentInput = false;
                    VeritifyIdentityActivity.this.tv_get_code.setEnabled(false);
                }
                if (!VeritifyIdentityActivity.this.isContentInput || !VeritifyIdentityActivity.this.isCodeInput) {
                    VeritifyIdentityActivity.this.tv_next.setEnabled(false);
                } else {
                    VeritifyIdentityActivity.this.tv_next.setEnabled(true);
                }
            }
        });
        this.et_code.addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.ui.income.VeritifyIdentityActivity.2
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().length() > 0) {
                    VeritifyIdentityActivity.this.isCodeInput = true;
                } else {
                    VeritifyIdentityActivity.this.isCodeInput = false;
                }
                if (VeritifyIdentityActivity.this.et_content.length() > 0) {
                    VeritifyIdentityActivity.this.isContentInput = true;
                } else {
                    VeritifyIdentityActivity.this.isContentInput = false;
                }
                if (!VeritifyIdentityActivity.this.isContentInput || !VeritifyIdentityActivity.this.isCodeInput) {
                    VeritifyIdentityActivity.this.tv_next.setEnabled(false);
                } else {
                    VeritifyIdentityActivity.this.tv_next.setEnabled(true);
                }
            }
        });
    }

    @Event({R.id.tv_country_code, R.id.tv_get_code, R.id.tv_next, R.id.tv_tip})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_country_code /* 2131298794 */:
                CountryCodeActivity.Companion.startActivity(this.mContext, 111);
                return;
            case R.id.tv_get_code /* 2131298925 */:
                getPhoneCode();
                return;
            case R.id.tv_next /* 2131299148 */:
                veritifyIdentity();
                return;
            case R.id.tv_tip /* 2131299446 */:
                FeedbackEnterActivity.startActivity(this);
                return;
            default:
                return;
        }
    }

    private void getPhoneCode() {
        this.et_code.setText("");
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/memberInfo/sendVerifyCode");
        tomatoParams.addParameter("phone", this.et_content.getText().toString());
        tomatoParams.addParameter("moduleType", 3);
        tomatoParams.addParameter("countryCode", this.tv_country_code.getText().toString());
        tomatoParams.post(new TomatoCallback(this, 101));
        TimerTaskUtil.getInstance().onStart(60, new TimerTaskUtil.TimeTask() { // from class: com.one.tomato.ui.income.VeritifyIdentityActivity.3
            @Override // com.one.tomato.utils.TimerTaskUtil.TimeTask
            public void position(int i) {
                TextView textView = VeritifyIdentityActivity.this.tv_get_code;
                textView.setText(i + "s");
                VeritifyIdentityActivity.this.tv_get_code.setEnabled(false);
                VeritifyIdentityActivity.this.tv_get_code.setTextColor(VeritifyIdentityActivity.this.getResources().getColor(R.color.tip_color));
            }

            @Override // com.one.tomato.utils.TimerTaskUtil.TimeTask
            public void stop() {
                VeritifyIdentityActivity.this.tv_get_code.setText(R.string.get_code);
                VeritifyIdentityActivity.this.tv_get_code.setEnabled(true);
                VeritifyIdentityActivity.this.tv_get_code.setTextColor(VeritifyIdentityActivity.this.getResources().getColor(R.color.text_middle));
            }
        });
    }

    private void veritifyIdentity() {
        showWaitingDialog();
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/withdraw/person/verify", 20000);
        tomatoParams.addParameter("memberId", Integer.valueOf(DBUtil.getMemberId()));
        tomatoParams.addParameter("phone", this.et_content.getText().toString());
        tomatoParams.addParameter("verifyCode", this.et_code.getText().toString());
        tomatoParams.addParameter("countryCode", this.tv_country_code.getText().toString());
        tomatoParams.post(new TomatoCallback((ResponseObserver) this, 102, VeritifyIdentityBean.class));
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public void handleResponse(Message message) {
        super.handleResponse(message);
        hideWaitingDialog();
        int i = message.what;
        if (i == 101) {
            ToastUtil.showCenterToast((int) R.string.get_code_success);
        } else if (i != 102) {
        } else {
            String sec = ((VeritifyIdentityBean) ((BaseModel) message.obj).obj).getSec();
            this.userInfo.setPhone(this.et_content.getText().toString());
            this.userInfo.setCountryCodeStr(this.tv_country_code.getText().toString());
            DBUtil.saveUserInfo(this.userInfo);
            int i2 = this.type;
            if (i2 == 1001) {
                AlipayBindActivity.startActivity((Context) this, true);
            } else if (i2 == 1002) {
                AlipayBindActivity.startActivity((Context) this, false);
            } else if (i2 == 1003) {
                TradePwdSettingActivity.startActivity(this, 1001, sec);
            } else if (i2 == 1004) {
                TradePwdSettingActivity.startActivity(this, 1002, sec);
            } else if (i2 == 1005) {
                BankBindActivity.startActivity((Context) this, true);
            } else if (i2 == 1006) {
                BankBindActivity.startActivity((Context) this, false);
            }
            finish();
        }
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public boolean handleResponseError(Message message) {
        hideWaitingDialog();
        BaseModel baseModel = (BaseModel) message.obj;
        int i = message.what;
        if (i == 101) {
            setErrorStr(baseModel.message);
            return false;
        } else if (i != 102) {
            return false;
        } else {
            setErrorStr(baseModel.message);
            return false;
        }
    }

    private void setErrorStr(String str) {
        this.tv_error.setVisibility(0);
        this.tv_error.setText(str);
        this.tv_error.postDelayed(new Runnable() { // from class: com.one.tomato.ui.income.VeritifyIdentityActivity.4
            @Override // java.lang.Runnable
            public void run() {
                VeritifyIdentityActivity.this.tv_error.setVisibility(4);
            }
        }, 3000L);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 111 && i2 == -1) {
            this.tv_country_code.setText(intent.getExtras().getString("country_name", "+86"));
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        TimerTaskUtil.getInstance().onStop();
    }
}
