package com.one.tomato.mvp.p080ui.login.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.p002v4.app.NotificationCompat;
import android.support.p002v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.entity.ForgetPsSec;
import com.one.tomato.entity.p079db.LoginInfo;
import com.one.tomato.entity.p079db.SystemParam;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.p080ui.login.impl.ILoginContact$ILoginView;
import com.one.tomato.mvp.p080ui.login.presenter.LoginPresenter;
import com.one.tomato.thirdpart.captcha.CaptchaUtil;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.PreferencesUtil;
import com.one.tomato.utils.TimerTaskUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.widget.ClearEditText;
import java.util.HashMap;
import java.util.LinkedHashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: LoginVerifyAct.kt */
/* renamed from: com.one.tomato.mvp.ui.login.view.LoginVerifyAct */
/* loaded from: classes3.dex */
public final class LoginVerifyAct extends MvpBaseActivity<ILoginContact$ILoginView, LoginPresenter> implements ILoginContact$ILoginView {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private CaptchaUtil captchaUtil;
    private int loginQuickType = 7;
    private String account = "";
    private String country_code = "";
    private String country_name = "";

    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view == null) {
            View findViewById = findViewById(i);
            this._$_findViewCache.put(Integer.valueOf(i), findViewById);
            return findViewById;
        }
        return view;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public int createLayoutView() {
        return R.layout.login_verify_act;
    }

    @Override // com.one.tomato.mvp.p080ui.login.impl.ILoginContact$ILoginView
    public void handleVerifyPhoneCode(ForgetPsSec forgetPsSec) {
    }

    @Override // com.one.tomato.mvp.p080ui.login.impl.ILoginContact$ILoginView
    public void handlerSafetyVerify(LoginInfo loginInfo) {
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
    }

    @Override // com.one.tomato.mvp.base.view.IBaseView
    public void onError(String str) {
    }

    /* compiled from: LoginVerifyAct.kt */
    /* renamed from: com.one.tomato.mvp.ui.login.view.LoginVerifyAct$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startAct(int i, Context context, String account, String str, String str2) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            Intrinsics.checkParameterIsNotNull(account, "account");
            Intent intent = new Intent();
            intent.putExtra("verify_type", i);
            intent.putExtra("verify_account", account);
            intent.putExtra("country_code", str);
            intent.putExtra("country_name", str2);
            intent.setClass(context, LoginVerifyAct.class);
            context.startActivity(intent);
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter  reason: collision with other method in class */
    public LoginPresenter mo6439createPresenter() {
        return new LoginPresenter();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        String str;
        String str2;
        String str3;
        Intent intent = getIntent();
        this.loginQuickType = intent != null ? intent.getIntExtra("verify_type", 7) : 7;
        Intent intent2 = getIntent();
        if (intent2 == null || (str = intent2.getStringExtra("verify_account")) == null) {
            str = "";
        }
        this.account = str;
        Intent intent3 = getIntent();
        if (intent3 == null || (str2 = intent3.getStringExtra("country_code")) == null) {
            str2 = "";
        }
        this.country_code = str2;
        Intent intent4 = getIntent();
        if (intent4 == null || (str3 = intent4.getStringExtra("country_name")) == null) {
            str3 = "";
        }
        this.country_name = str3;
        this.captchaUtil = new CaptchaUtil(getMContext());
        addOnclick();
        int i = this.loginQuickType;
        if (i == 6) {
            ((TextView) _$_findCachedViewById(R$id.tv_login_title)).setText(AppUtil.getString(R.string.login_verify_email));
            Context mContext = getMContext();
            if (mContext == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.register_email);
            if (drawable != null) {
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            }
            TextView textView = (TextView) _$_findCachedViewById(R$id.account_text);
            if (textView != null) {
                textView.setCompoundDrawables(drawable, null, null, null);
            }
            TextView account_text = (TextView) _$_findCachedViewById(R$id.account_text);
            Intrinsics.checkExpressionValueIsNotNull(account_text, "account_text");
            account_text.setText(this.account);
        } else if (i != 7) {
        } else {
            ((TextView) _$_findCachedViewById(R$id.tv_login_title)).setText(AppUtil.getString(R.string.login_verify_phone));
            Context mContext2 = getMContext();
            if (mContext2 == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            Drawable drawable2 = ContextCompat.getDrawable(mContext2, R.drawable.login_phone);
            if (drawable2 != null) {
                drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
            }
            TextView textView2 = (TextView) _$_findCachedViewById(R$id.account_text);
            if (textView2 != null) {
                textView2.setCompoundDrawables(drawable2, null, null, null);
            }
            TextView account_text2 = (TextView) _$_findCachedViewById(R$id.account_text);
            Intrinsics.checkExpressionValueIsNotNull(account_text2, "account_text");
            account_text2.setText('+' + this.country_code + ' ' + this.account);
        }
    }

    @Override // com.one.tomato.mvp.p080ui.login.impl.ILoginContact$ILoginView
    public void handlerLogin(LoginInfo loginInfo) {
        if (this.loginQuickType == 7) {
            PreferencesUtil.getInstance().putString("country_name", this.country_name);
            PreferencesUtil.getInstance().putString("country_code", this.country_code);
            PreferencesUtil.getInstance().putString("login_user_phone", this.account);
            PreferencesUtil.getInstance().putString("login_user_account", null);
            return;
        }
        PreferencesUtil.getInstance().putString("login_user_account", this.account);
        PreferencesUtil.getInstance().putString("country_name", null);
        PreferencesUtil.getInstance().putString("country_code", null);
        PreferencesUtil.getInstance().putString("login_user_phone", null);
    }

    private final void addOnclick() {
        ((TextView) _$_findCachedViewById(R$id.tv_get_code)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.LoginVerifyAct$addOnclick$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LoginVerifyAct.this.sendCode();
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_login)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.LoginVerifyAct$addOnclick$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ClearEditText et_input_code = (ClearEditText) LoginVerifyAct.this._$_findCachedViewById(R$id.et_input_code);
                Intrinsics.checkExpressionValueIsNotNull(et_input_code, "et_input_code");
                if (!TextUtils.isEmpty(et_input_code.getText().toString())) {
                    LoginVerifyAct.this.showImageVer();
                } else {
                    ToastUtil.showCenterToast(AppUtil.getString(R.string.register_input_code));
                }
            }
        });
        ImageView imageView = (ImageView) _$_findCachedViewById(R$id.iv_back);
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.LoginVerifyAct$addOnclick$3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    LoginVerifyAct.this.onBackPressed();
                }
            });
        }
        CaptchaUtil captchaUtil = this.captchaUtil;
        if (captchaUtil != null) {
            captchaUtil.setCaptchaUtilListener(new CaptchaUtil.CaptchaUtilListener() { // from class: com.one.tomato.mvp.ui.login.view.LoginVerifyAct$addOnclick$4
                @Override // com.one.tomato.thirdpart.captcha.CaptchaUtil.CaptchaUtilListener
                public void validateFail() {
                }

                @Override // com.one.tomato.thirdpart.captcha.CaptchaUtil.CaptchaUtilListener
                public void validateSuccess(String str) {
                    LoginVerifyAct.this.login(str);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void sendCode() {
        ClearEditText clearEditText = (ClearEditText) _$_findCachedViewById(R$id.et_input_code);
        if (clearEditText != null) {
            clearEditText.post(new Runnable() { // from class: com.one.tomato.mvp.ui.login.view.LoginVerifyAct$sendCode$1
                @Override // java.lang.Runnable
                public final void run() {
                    int i;
                    LoginPresenter mPresenter;
                    String str;
                    String str2;
                    LoginPresenter mPresenter2;
                    String str3;
                    ((ClearEditText) LoginVerifyAct.this._$_findCachedViewById(R$id.et_input_code)).setText("");
                    LoginVerifyAct.this.showWaitingDialog();
                    i = LoginVerifyAct.this.loginQuickType;
                    if (i == 6) {
                        mPresenter2 = LoginVerifyAct.this.getMPresenter();
                        if (mPresenter2 != null) {
                            str3 = LoginVerifyAct.this.account;
                            mPresenter2.senMail(str3, 5);
                        }
                    } else {
                        mPresenter = LoginVerifyAct.this.getMPresenter();
                        if (mPresenter != null) {
                            StringBuilder sb = new StringBuilder();
                            DomainServer domainServer = DomainServer.getInstance();
                            Intrinsics.checkExpressionValueIsNotNull(domainServer, "DomainServer.getInstance()");
                            sb.append(domainServer.getServerUrl());
                            sb.append("/app/login/loginSmsCode");
                            String sb2 = sb.toString();
                            str = LoginVerifyAct.this.country_code;
                            str2 = LoginVerifyAct.this.account;
                            mPresenter.requestPhoneCode(sb2, str, str2, 5);
                        }
                    }
                    TimerTaskUtil.getInstance().onStart(60, new TimerTaskUtil.TimeTask() { // from class: com.one.tomato.mvp.ui.login.view.LoginVerifyAct$sendCode$1.1
                        @Override // com.one.tomato.utils.TimerTaskUtil.TimeTask
                        public void position(int i2) {
                            TextView tv_get_code = (TextView) LoginVerifyAct.this._$_findCachedViewById(R$id.tv_get_code);
                            Intrinsics.checkExpressionValueIsNotNull(tv_get_code, "tv_get_code");
                            tv_get_code.setText(String.valueOf(i2) + "s");
                            TextView tv_get_code2 = (TextView) LoginVerifyAct.this._$_findCachedViewById(R$id.tv_get_code);
                            Intrinsics.checkExpressionValueIsNotNull(tv_get_code2, "tv_get_code");
                            tv_get_code2.setEnabled(false);
                        }

                        @Override // com.one.tomato.utils.TimerTaskUtil.TimeTask
                        public void stop() {
                            ((TextView) LoginVerifyAct.this._$_findCachedViewById(R$id.tv_get_code)).setText(R.string.register_get_code);
                            TextView tv_get_code = (TextView) LoginVerifyAct.this._$_findCachedViewById(R$id.tv_get_code);
                            Intrinsics.checkExpressionValueIsNotNull(tv_get_code, "tv_get_code");
                            tv_get_code.setEnabled(true);
                        }
                    });
                }
            });
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity, com.one.tomato.net.LoginResponseObserver
    public void onLoginSuccess() {
        super.onLoginSuccess();
        dismissDialog();
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showImageVer() {
        SystemParam systemParam = DBUtil.getSystemParam();
        Intrinsics.checkExpressionValueIsNotNull(systemParam, "DBUtil.getSystemParam()");
        if (systemParam.getLoginCaptcha() == 1) {
            CaptchaUtil captchaUtil = this.captchaUtil;
            if (captchaUtil == null) {
                return;
            }
            captchaUtil.validate();
            return;
        }
        login("");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void login(final String str) {
        TextView textView = (TextView) _$_findCachedViewById(R$id.tv_login);
        if (textView != null) {
            textView.post(new Runnable() { // from class: com.one.tomato.mvp.ui.login.view.LoginVerifyAct$login$1
                @Override // java.lang.Runnable
                public final void run() {
                    int i;
                    String str2;
                    String str3;
                    String str4;
                    LoginPresenter mPresenter;
                    String str5;
                    LoginVerifyAct.this.showWaitingDialog();
                    LinkedHashMap linkedHashMap = new LinkedHashMap();
                    i = LoginVerifyAct.this.loginQuickType;
                    if (i != 6) {
                        str2 = LoginVerifyAct.this.account;
                        linkedHashMap.put("phoneNum", str2);
                        str3 = LoginVerifyAct.this.country_code;
                        linkedHashMap.put("countryCode", str3);
                        ClearEditText et_input_code = (ClearEditText) LoginVerifyAct.this._$_findCachedViewById(R$id.et_input_code);
                        Intrinsics.checkExpressionValueIsNotNull(et_input_code, "et_input_code");
                        linkedHashMap.put("smsCode", et_input_code.getText().toString());
                        str4 = "/app/login/userSmsLogin";
                    } else {
                        ClearEditText et_input_code2 = (ClearEditText) LoginVerifyAct.this._$_findCachedViewById(R$id.et_input_code);
                        Intrinsics.checkExpressionValueIsNotNull(et_input_code2, "et_input_code");
                        linkedHashMap.put("verifyCode", et_input_code2.getText().toString());
                        str5 = LoginVerifyAct.this.account;
                        linkedHashMap.put(NotificationCompat.CATEGORY_EMAIL, str5);
                        str4 = "/app/login/userEmailCodeLogin";
                    }
                    String str6 = str;
                    if (str6 == null) {
                        str6 = "";
                    }
                    linkedHashMap.put("validate", str6);
                    mPresenter = LoginVerifyAct.this.getMPresenter();
                    if (mPresenter != null) {
                        StringBuilder sb = new StringBuilder();
                        DomainServer domainServer = DomainServer.getInstance();
                        Intrinsics.checkExpressionValueIsNotNull(domainServer, "DomainServer.getInstance()");
                        sb.append(domainServer.getServerUrl());
                        sb.append(str4);
                        mPresenter.requestLogin(sb.toString(), linkedHashMap, true);
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        CaptchaUtil captchaUtil = this.captchaUtil;
        if (captchaUtil != null) {
            captchaUtil.onDestroy();
        }
    }
}
