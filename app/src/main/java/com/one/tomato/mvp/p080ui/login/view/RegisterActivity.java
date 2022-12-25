package com.one.tomato.mvp.p080ui.login.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.entity.ForgetPsSec;
import com.one.tomato.entity.p079db.CountryDB;
import com.one.tomato.entity.p079db.LoginInfo;
import com.one.tomato.entity.p079db.OpenInstallChannelBean;
import com.one.tomato.entity.p079db.SystemParam;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.p080ui.login.impl.ILoginContact$ILoginView;
import com.one.tomato.mvp.p080ui.login.presenter.RegisterPresenter;
import com.one.tomato.thirdpart.captcha.CaptchaUtil;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.DeviceInfoUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.PreferencesUtil;
import com.one.tomato.utils.TimerTaskUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.encrypt.MD5Util;
import com.one.tomato.widget.ClearEditText;
import java.util.HashMap;
import java.util.LinkedHashMap;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: RegisterActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.login.view.RegisterActivity */
/* loaded from: classes3.dex */
public final class RegisterActivity extends MvpBaseActivity<ILoginContact$ILoginView, RegisterPresenter> implements ILoginContact$ILoginView {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private CaptchaUtil captchaUtil;
    private boolean isEmailCodeInput;
    private boolean isEmailInput;
    private boolean isPSInput1;
    private boolean isPSInput2;
    private boolean isPhoneCodeInput;
    private boolean isPhoneInput;
    private int type = 1;
    private int step = 1;
    private String phoneCode = "";
    private String emailCode = "";

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
        return R.layout.activity_register;
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

    public static final /* synthetic */ CaptchaUtil access$getCaptchaUtil$p(RegisterActivity registerActivity) {
        CaptchaUtil captchaUtil = registerActivity.captchaUtil;
        if (captchaUtil != null) {
            return captchaUtil;
        }
        Intrinsics.throwUninitializedPropertyAccessException("captchaUtil");
        throw null;
    }

    /* compiled from: RegisterActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.login.view.RegisterActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startActivity(Context context) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            Intent intent = new Intent();
            intent.setClass(context, RegisterActivity.class);
            ((Activity) context).startActivityForResult(intent, 1);
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter  reason: collision with other method in class */
    public RegisterPresenter mo6439createPresenter() {
        return new RegisterPresenter();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        this.captchaUtil = new CaptchaUtil(this);
        changeUI();
        addListener();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void changeUI() {
        int i = this.type;
        boolean z = false;
        if (i == 1) {
            int i2 = this.step;
            if (i2 != 1) {
                if (i2 != 2) {
                    return;
                }
                ConstraintLayout cl_select = (ConstraintLayout) _$_findCachedViewById(R$id.cl_select);
                Intrinsics.checkExpressionValueIsNotNull(cl_select, "cl_select");
                cl_select.setVisibility(8);
                LinearLayout ll_input_account = (LinearLayout) _$_findCachedViewById(R$id.ll_input_account);
                Intrinsics.checkExpressionValueIsNotNull(ll_input_account, "ll_input_account");
                ll_input_account.setVisibility(8);
                ConstraintLayout cl_input_password = (ConstraintLayout) _$_findCachedViewById(R$id.cl_input_password);
                Intrinsics.checkExpressionValueIsNotNull(cl_input_password, "cl_input_password");
                cl_input_password.setVisibility(0);
                return;
            }
            ConstraintLayout cl_select2 = (ConstraintLayout) _$_findCachedViewById(R$id.cl_select);
            Intrinsics.checkExpressionValueIsNotNull(cl_select2, "cl_select");
            cl_select2.setVisibility(0);
            ((TextView) _$_findCachedViewById(R$id.tv_register_phone)).setTextColor(getResources().getColor(R.color.text_dark));
            ((TextView) _$_findCachedViewById(R$id.tv_register_phone)).setTextSize(2, 18.0f);
            View view_register_phone_line = _$_findCachedViewById(R$id.view_register_phone_line);
            Intrinsics.checkExpressionValueIsNotNull(view_register_phone_line, "view_register_phone_line");
            view_register_phone_line.setVisibility(0);
            ((TextView) _$_findCachedViewById(R$id.tv_register_email)).setTextColor(getResources().getColor(R.color.text_light));
            ((TextView) _$_findCachedViewById(R$id.tv_register_email)).setTextSize(2, 16.0f);
            View view_register_email_line = _$_findCachedViewById(R$id.view_register_email_line);
            Intrinsics.checkExpressionValueIsNotNull(view_register_email_line, "view_register_email_line");
            view_register_email_line.setVisibility(8);
            LinearLayout ll_input_account2 = (LinearLayout) _$_findCachedViewById(R$id.ll_input_account);
            Intrinsics.checkExpressionValueIsNotNull(ll_input_account2, "ll_input_account");
            ll_input_account2.setVisibility(0);
            ConstraintLayout cl_forget_phone = (ConstraintLayout) _$_findCachedViewById(R$id.cl_forget_phone);
            Intrinsics.checkExpressionValueIsNotNull(cl_forget_phone, "cl_forget_phone");
            cl_forget_phone.setVisibility(0);
            ConstraintLayout cl_forget_email = (ConstraintLayout) _$_findCachedViewById(R$id.cl_forget_email);
            Intrinsics.checkExpressionValueIsNotNull(cl_forget_email, "cl_forget_email");
            cl_forget_email.setVisibility(8);
            ImageView iv_select_protocol = (ImageView) _$_findCachedViewById(R$id.iv_select_protocol);
            Intrinsics.checkExpressionValueIsNotNull(iv_select_protocol, "iv_select_protocol");
            iv_select_protocol.setSelected(true);
            ConstraintLayout cl_input_password2 = (ConstraintLayout) _$_findCachedViewById(R$id.cl_input_password);
            Intrinsics.checkExpressionValueIsNotNull(cl_input_password2, "cl_input_password");
            cl_input_password2.setVisibility(8);
            ((ClearEditText) _$_findCachedViewById(R$id.et_verify_code)).setText(this.phoneCode);
            TextView tv_get_code = (TextView) _$_findCachedViewById(R$id.tv_get_code);
            Intrinsics.checkExpressionValueIsNotNull(tv_get_code, "tv_get_code");
            tv_get_code.setEnabled(this.isPhoneInput);
            TextView tv_input_account_next = (TextView) _$_findCachedViewById(R$id.tv_input_account_next);
            Intrinsics.checkExpressionValueIsNotNull(tv_input_account_next, "tv_input_account_next");
            if (this.isPhoneInput && this.isPhoneCodeInput) {
                z = true;
            }
            tv_input_account_next.setEnabled(z);
            TimerTaskUtil.getInstance().onStop();
            ((TextView) _$_findCachedViewById(R$id.tv_get_code)).setText(R.string.register_get_code);
        } else if (i != 2) {
        } else {
            int i3 = this.step;
            if (i3 != 1) {
                if (i3 != 2) {
                    return;
                }
                ConstraintLayout cl_select3 = (ConstraintLayout) _$_findCachedViewById(R$id.cl_select);
                Intrinsics.checkExpressionValueIsNotNull(cl_select3, "cl_select");
                cl_select3.setVisibility(8);
                LinearLayout ll_input_account3 = (LinearLayout) _$_findCachedViewById(R$id.ll_input_account);
                Intrinsics.checkExpressionValueIsNotNull(ll_input_account3, "ll_input_account");
                ll_input_account3.setVisibility(8);
                ConstraintLayout cl_input_password3 = (ConstraintLayout) _$_findCachedViewById(R$id.cl_input_password);
                Intrinsics.checkExpressionValueIsNotNull(cl_input_password3, "cl_input_password");
                cl_input_password3.setVisibility(0);
                return;
            }
            ConstraintLayout cl_select4 = (ConstraintLayout) _$_findCachedViewById(R$id.cl_select);
            Intrinsics.checkExpressionValueIsNotNull(cl_select4, "cl_select");
            cl_select4.setVisibility(0);
            ((TextView) _$_findCachedViewById(R$id.tv_register_phone)).setTextColor(getResources().getColor(R.color.text_light));
            ((TextView) _$_findCachedViewById(R$id.tv_register_phone)).setTextSize(2, 16.0f);
            View view_register_phone_line2 = _$_findCachedViewById(R$id.view_register_phone_line);
            Intrinsics.checkExpressionValueIsNotNull(view_register_phone_line2, "view_register_phone_line");
            view_register_phone_line2.setVisibility(8);
            ((TextView) _$_findCachedViewById(R$id.tv_register_email)).setTextColor(getResources().getColor(R.color.text_dark));
            ((TextView) _$_findCachedViewById(R$id.tv_register_email)).setTextSize(2, 18.0f);
            View view_register_email_line2 = _$_findCachedViewById(R$id.view_register_email_line);
            Intrinsics.checkExpressionValueIsNotNull(view_register_email_line2, "view_register_email_line");
            view_register_email_line2.setVisibility(0);
            LinearLayout ll_input_account4 = (LinearLayout) _$_findCachedViewById(R$id.ll_input_account);
            Intrinsics.checkExpressionValueIsNotNull(ll_input_account4, "ll_input_account");
            ll_input_account4.setVisibility(0);
            ConstraintLayout cl_forget_phone2 = (ConstraintLayout) _$_findCachedViewById(R$id.cl_forget_phone);
            Intrinsics.checkExpressionValueIsNotNull(cl_forget_phone2, "cl_forget_phone");
            cl_forget_phone2.setVisibility(8);
            ConstraintLayout cl_forget_email2 = (ConstraintLayout) _$_findCachedViewById(R$id.cl_forget_email);
            Intrinsics.checkExpressionValueIsNotNull(cl_forget_email2, "cl_forget_email");
            cl_forget_email2.setVisibility(0);
            ImageView iv_select_protocol2 = (ImageView) _$_findCachedViewById(R$id.iv_select_protocol);
            Intrinsics.checkExpressionValueIsNotNull(iv_select_protocol2, "iv_select_protocol");
            iv_select_protocol2.setSelected(true);
            ConstraintLayout cl_input_password4 = (ConstraintLayout) _$_findCachedViewById(R$id.cl_input_password);
            Intrinsics.checkExpressionValueIsNotNull(cl_input_password4, "cl_input_password");
            cl_input_password4.setVisibility(8);
            ((ClearEditText) _$_findCachedViewById(R$id.et_verify_code)).setText(this.emailCode);
            TextView tv_get_code2 = (TextView) _$_findCachedViewById(R$id.tv_get_code);
            Intrinsics.checkExpressionValueIsNotNull(tv_get_code2, "tv_get_code");
            tv_get_code2.setEnabled(this.isEmailInput);
            TextView tv_input_account_next2 = (TextView) _$_findCachedViewById(R$id.tv_input_account_next);
            Intrinsics.checkExpressionValueIsNotNull(tv_input_account_next2, "tv_input_account_next");
            if (this.isEmailInput && this.isEmailCodeInput) {
                z = true;
            }
            tv_input_account_next2.setEnabled(z);
            TimerTaskUtil.getInstance().onStop();
            ((TextView) _$_findCachedViewById(R$id.tv_get_code)).setText(R.string.register_get_code);
        }
    }

    private final void addListener() {
        ((ImageView) _$_findCachedViewById(R$id.iv_back)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.RegisterActivity$addListener$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RegisterActivity.this.onBackPressed();
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_register_phone)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.RegisterActivity$addListener$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i;
                i = RegisterActivity.this.type;
                if (1 == i) {
                    return;
                }
                RegisterActivity.this.type = 1;
                RegisterActivity.this.changeUI();
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_register_email)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.RegisterActivity$addListener$3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i;
                i = RegisterActivity.this.type;
                if (2 == i) {
                    return;
                }
                RegisterActivity.this.type = 2;
                RegisterActivity.this.changeUI();
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_country_name)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.RegisterActivity$addListener$4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CountryCodeActivity.Companion.startActivity(RegisterActivity.this, 111);
            }
        });
        ((ImageView) _$_findCachedViewById(R$id.iv_arrow_right)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.RegisterActivity$addListener$5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CountryCodeActivity.Companion.startActivity(RegisterActivity.this, 111);
            }
        });
        ((ImageView) _$_findCachedViewById(R$id.iv_login_phone)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.RegisterActivity$addListener$6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CountryCodeActivity.Companion.startActivity(RegisterActivity.this, 111);
            }
        });
        ((EditText) _$_findCachedViewById(R$id.et_country_code)).addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.mvp.ui.login.view.RegisterActivity$addListener$7
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                CountryDB queryCountry;
                String valueOf = String.valueOf(editable);
                if (!(valueOf.length() > 0) || (queryCountry = DBUtil.queryCountry(valueOf)) == null) {
                    return;
                }
                ((TextView) RegisterActivity.this._$_findCachedViewById(R$id.tv_country_name)).setText(queryCountry.getCountryName());
            }
        });
        ((ClearEditText) _$_findCachedViewById(R$id.et_phone)).addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.mvp.ui.login.view.RegisterActivity$addListener$8
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int i, int i2, int i3) {
                Intrinsics.checkParameterIsNotNull(s, "s");
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int i, int i2, int i3) {
                Intrinsics.checkParameterIsNotNull(s, "s");
            }

            /* JADX WARN: Code restructure failed: missing block: B:7:0x004d, code lost:
                if (r0 != false) goto L8;
             */
            @Override // android.text.TextWatcher
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void afterTextChanged(Editable s) {
                boolean z;
                boolean z2;
                boolean z3;
                Intrinsics.checkParameterIsNotNull(s, "s");
                boolean z4 = true;
                RegisterActivity.this.isPhoneInput = s.length() > 0;
                TextView tv_get_code = (TextView) RegisterActivity.this._$_findCachedViewById(R$id.tv_get_code);
                Intrinsics.checkExpressionValueIsNotNull(tv_get_code, "tv_get_code");
                z = RegisterActivity.this.isPhoneInput;
                tv_get_code.setEnabled(z);
                TextView tv_input_account_next = (TextView) RegisterActivity.this._$_findCachedViewById(R$id.tv_input_account_next);
                Intrinsics.checkExpressionValueIsNotNull(tv_input_account_next, "tv_input_account_next");
                z2 = RegisterActivity.this.isPhoneInput;
                if (z2) {
                    z3 = RegisterActivity.this.isPhoneCodeInput;
                }
                z4 = false;
                tv_input_account_next.setEnabled(z4);
            }
        });
        ((ClearEditText) _$_findCachedViewById(R$id.et_input_email)).addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.mvp.ui.login.view.RegisterActivity$addListener$9
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int i, int i2, int i3) {
                Intrinsics.checkParameterIsNotNull(s, "s");
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int i, int i2, int i3) {
                Intrinsics.checkParameterIsNotNull(s, "s");
            }

            /* JADX WARN: Code restructure failed: missing block: B:7:0x004d, code lost:
                if (r0 != false) goto L8;
             */
            @Override // android.text.TextWatcher
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void afterTextChanged(Editable s) {
                boolean z;
                boolean z2;
                boolean z3;
                Intrinsics.checkParameterIsNotNull(s, "s");
                boolean z4 = true;
                RegisterActivity.this.isEmailInput = s.length() > 0;
                TextView tv_get_code = (TextView) RegisterActivity.this._$_findCachedViewById(R$id.tv_get_code);
                Intrinsics.checkExpressionValueIsNotNull(tv_get_code, "tv_get_code");
                z = RegisterActivity.this.isEmailInput;
                tv_get_code.setEnabled(z);
                TextView tv_input_account_next = (TextView) RegisterActivity.this._$_findCachedViewById(R$id.tv_input_account_next);
                Intrinsics.checkExpressionValueIsNotNull(tv_input_account_next, "tv_input_account_next");
                z2 = RegisterActivity.this.isEmailInput;
                if (z2) {
                    z3 = RegisterActivity.this.isEmailCodeInput;
                }
                z4 = false;
                tv_input_account_next.setEnabled(z4);
            }
        });
        ((ClearEditText) _$_findCachedViewById(R$id.et_verify_code)).addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.mvp.ui.login.view.RegisterActivity$addListener$10
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int i, int i2, int i3) {
                Intrinsics.checkParameterIsNotNull(s, "s");
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int i, int i2, int i3) {
                Intrinsics.checkParameterIsNotNull(s, "s");
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                int i;
                boolean z;
                boolean z2;
                boolean z3;
                boolean z4;
                Intrinsics.checkParameterIsNotNull(s, "s");
                i = RegisterActivity.this.type;
                boolean z5 = false;
                if (i == 1) {
                    RegisterActivity.this.isPhoneCodeInput = s.length() > 0;
                    TextView tv_input_account_next = (TextView) RegisterActivity.this._$_findCachedViewById(R$id.tv_input_account_next);
                    Intrinsics.checkExpressionValueIsNotNull(tv_input_account_next, "tv_input_account_next");
                    z = RegisterActivity.this.isPhoneInput;
                    if (z) {
                        z2 = RegisterActivity.this.isPhoneCodeInput;
                        if (z2) {
                            z5 = true;
                        }
                    }
                    tv_input_account_next.setEnabled(z5);
                    RegisterActivity.this.phoneCode = s.toString();
                } else if (i != 2) {
                } else {
                    RegisterActivity.this.isEmailCodeInput = s.length() > 0;
                    TextView tv_input_account_next2 = (TextView) RegisterActivity.this._$_findCachedViewById(R$id.tv_input_account_next);
                    Intrinsics.checkExpressionValueIsNotNull(tv_input_account_next2, "tv_input_account_next");
                    z3 = RegisterActivity.this.isEmailInput;
                    if (z3) {
                        z4 = RegisterActivity.this.isEmailCodeInput;
                        if (z4) {
                            z5 = true;
                        }
                    }
                    tv_input_account_next2.setEnabled(z5);
                    RegisterActivity.this.emailCode = s.toString();
                }
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_get_code)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.RegisterActivity$addListener$11
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RegisterActivity.this.sendVerCode();
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_input_account_next)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.RegisterActivity$addListener$12
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SystemParam systemParam = DBUtil.getSystemParam();
                Intrinsics.checkExpressionValueIsNotNull(systemParam, "DBUtil.getSystemParam()");
                if (systemParam.getLoginCaptcha() == 0) {
                    RegisterActivity.this.registerVer("");
                } else {
                    RegisterActivity.access$getCaptchaUtil$p(RegisterActivity.this).validate();
                }
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_protocal)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.RegisterActivity$addListener$13
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                UserProtocolActivity.Companion.startActivity(RegisterActivity.this);
            }
        });
        ((ImageView) _$_findCachedViewById(R$id.iv_select_protocol)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.RegisterActivity$addListener$14
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ImageView iv_select_protocol = (ImageView) RegisterActivity.this._$_findCachedViewById(R$id.iv_select_protocol);
                Intrinsics.checkExpressionValueIsNotNull(iv_select_protocol, "iv_select_protocol");
                ImageView iv_select_protocol2 = (ImageView) RegisterActivity.this._$_findCachedViewById(R$id.iv_select_protocol);
                Intrinsics.checkExpressionValueIsNotNull(iv_select_protocol2, "iv_select_protocol");
                iv_select_protocol.setSelected(!iv_select_protocol2.isSelected());
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_login)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.RegisterActivity$addListener$15
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RegisterActivity.this.onBackPressed();
            }
        });
        ((ClearEditText) _$_findCachedViewById(R$id.et_input_ps1)).addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.mvp.ui.login.view.RegisterActivity$addListener$16
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int i, int i2, int i3) {
                Intrinsics.checkParameterIsNotNull(s, "s");
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int i, int i2, int i3) {
                Intrinsics.checkParameterIsNotNull(s, "s");
            }

            /* JADX WARN: Code restructure failed: missing block: B:7:0x0034, code lost:
                if (r0 != false) goto L8;
             */
            @Override // android.text.TextWatcher
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void afterTextChanged(Editable s) {
                boolean z;
                boolean z2;
                Intrinsics.checkParameterIsNotNull(s, "s");
                boolean z3 = true;
                RegisterActivity.this.isPSInput1 = s.length() > 0;
                TextView tv_input_ps_next = (TextView) RegisterActivity.this._$_findCachedViewById(R$id.tv_input_ps_next);
                Intrinsics.checkExpressionValueIsNotNull(tv_input_ps_next, "tv_input_ps_next");
                z = RegisterActivity.this.isPSInput1;
                if (z) {
                    z2 = RegisterActivity.this.isPSInput2;
                }
                z3 = false;
                tv_input_ps_next.setEnabled(z3);
            }
        });
        ((ClearEditText) _$_findCachedViewById(R$id.et_input_ps2)).addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.mvp.ui.login.view.RegisterActivity$addListener$17
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int i, int i2, int i3) {
                Intrinsics.checkParameterIsNotNull(s, "s");
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int i, int i2, int i3) {
                Intrinsics.checkParameterIsNotNull(s, "s");
            }

            /* JADX WARN: Code restructure failed: missing block: B:7:0x0034, code lost:
                if (r0 != false) goto L8;
             */
            @Override // android.text.TextWatcher
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void afterTextChanged(Editable s) {
                boolean z;
                boolean z2;
                Intrinsics.checkParameterIsNotNull(s, "s");
                boolean z3 = true;
                RegisterActivity.this.isPSInput2 = s.length() > 0;
                TextView tv_input_ps_next = (TextView) RegisterActivity.this._$_findCachedViewById(R$id.tv_input_ps_next);
                Intrinsics.checkExpressionValueIsNotNull(tv_input_ps_next, "tv_input_ps_next");
                z = RegisterActivity.this.isPSInput1;
                if (z) {
                    z2 = RegisterActivity.this.isPSInput2;
                }
                z3 = false;
                tv_input_ps_next.setEnabled(z3);
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_input_ps_next)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.RegisterActivity$addListener$18
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RegisterActivity.this.nextPs();
            }
        });
        CaptchaUtil captchaUtil = this.captchaUtil;
        if (captchaUtil != null) {
            captchaUtil.setCaptchaUtilListener(new CaptchaUtil.CaptchaUtilListener() { // from class: com.one.tomato.mvp.ui.login.view.RegisterActivity$addListener$19
                @Override // com.one.tomato.thirdpart.captcha.CaptchaUtil.CaptchaUtilListener
                public void validateFail() {
                }

                @Override // com.one.tomato.thirdpart.captcha.CaptchaUtil.CaptchaUtilListener
                public void validateSuccess(String str) {
                    RegisterActivity.this.registerVer(str);
                }
            });
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("captchaUtil");
            throw null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void sendVerCode() {
        TextView textView = (TextView) _$_findCachedViewById(R$id.tv_get_code);
        if (textView != null) {
            textView.post(new Runnable() { // from class: com.one.tomato.mvp.ui.login.view.RegisterActivity$sendVerCode$1
                /* JADX WARN: Code restructure failed: missing block: B:15:0x0049, code lost:
                    if (r2 != null) goto L16;
                 */
                @Override // java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final void run() {
                    int i;
                    RegisterPresenter mPresenter;
                    RegisterPresenter mPresenter2;
                    String str;
                    Editable text;
                    String obj;
                    CharSequence trim;
                    ((ClearEditText) RegisterActivity.this._$_findCachedViewById(R$id.et_verify_code)).setText("");
                    i = RegisterActivity.this.type;
                    if (i == 1) {
                        mPresenter = RegisterActivity.this.getMPresenter();
                        if (mPresenter != null) {
                            StringBuilder sb = new StringBuilder();
                            DomainServer domainServer = DomainServer.getInstance();
                            Intrinsics.checkExpressionValueIsNotNull(domainServer, "DomainServer.getInstance()");
                            sb.append(domainServer.getServerUrl());
                            sb.append("/app/memberInfo/sendVerifyCode");
                            String sb2 = sb.toString();
                            EditText et_country_code = (EditText) RegisterActivity.this._$_findCachedViewById(R$id.et_country_code);
                            Intrinsics.checkExpressionValueIsNotNull(et_country_code, "et_country_code");
                            String obj2 = et_country_code.getText().toString();
                            ClearEditText et_phone = (ClearEditText) RegisterActivity.this._$_findCachedViewById(R$id.et_phone);
                            Intrinsics.checkExpressionValueIsNotNull(et_phone, "et_phone");
                            mPresenter.requestPhoneCode(sb2, obj2, et_phone.getText().toString(), 4);
                        }
                        RegisterActivity.this.phoneCode = "";
                    } else if (i == 2) {
                        mPresenter2 = RegisterActivity.this.getMPresenter();
                        if (mPresenter2 != null) {
                            ClearEditText clearEditText = (ClearEditText) RegisterActivity.this._$_findCachedViewById(R$id.et_input_email);
                            if (clearEditText != null && (text = clearEditText.getText()) != null && (obj = text.toString()) != null) {
                                if (obj == null) {
                                    throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                                }
                                trim = StringsKt__StringsKt.trim(obj);
                                str = trim.toString();
                            }
                            str = "";
                            mPresenter2.senMail(str, 4);
                        }
                        RegisterActivity.this.emailCode = "";
                    }
                    TimerTaskUtil.getInstance().onStart(60, new TimerTaskUtil.TimeTask() { // from class: com.one.tomato.mvp.ui.login.view.RegisterActivity$sendVerCode$1.1
                        @Override // com.one.tomato.utils.TimerTaskUtil.TimeTask
                        public void position(int i2) {
                            TextView tv_get_code = (TextView) RegisterActivity.this._$_findCachedViewById(R$id.tv_get_code);
                            Intrinsics.checkExpressionValueIsNotNull(tv_get_code, "tv_get_code");
                            tv_get_code.setText(String.valueOf(i2) + "s");
                            TextView tv_get_code2 = (TextView) RegisterActivity.this._$_findCachedViewById(R$id.tv_get_code);
                            Intrinsics.checkExpressionValueIsNotNull(tv_get_code2, "tv_get_code");
                            tv_get_code2.setEnabled(false);
                        }

                        @Override // com.one.tomato.utils.TimerTaskUtil.TimeTask
                        public void stop() {
                            ((TextView) RegisterActivity.this._$_findCachedViewById(R$id.tv_get_code)).setText(R.string.register_get_code);
                            TextView tv_get_code = (TextView) RegisterActivity.this._$_findCachedViewById(R$id.tv_get_code);
                            Intrinsics.checkExpressionValueIsNotNull(tv_get_code, "tv_get_code");
                            tv_get_code.setEnabled(true);
                        }
                    });
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void registerVer(final String str) {
        TextView textView = (TextView) _$_findCachedViewById(R$id.tv_input_account_next);
        if (textView != null) {
            textView.post(new Runnable() { // from class: com.one.tomato.mvp.ui.login.view.RegisterActivity$registerVer$1
                @Override // java.lang.Runnable
                public final void run() {
                    CharSequence trim;
                    CharSequence trim2;
                    String regId;
                    int i;
                    CharSequence trim3;
                    CharSequence trim4;
                    RegisterPresenter mPresenter;
                    CharSequence trim5;
                    ImageView iv_select_protocol = (ImageView) RegisterActivity.this._$_findCachedViewById(R$id.iv_select_protocol);
                    Intrinsics.checkExpressionValueIsNotNull(iv_select_protocol, "iv_select_protocol");
                    if (!iv_select_protocol.isSelected()) {
                        ToastUtil.showCenterToast((int) R.string.register_protocol_select_tip);
                        return;
                    }
                    RegisterActivity.this.showWaitingDialog();
                    LinkedHashMap linkedHashMap = new LinkedHashMap();
                    ClearEditText et_verify_code = (ClearEditText) RegisterActivity.this._$_findCachedViewById(R$id.et_verify_code);
                    Intrinsics.checkExpressionValueIsNotNull(et_verify_code, "et_verify_code");
                    String obj = et_verify_code.getText().toString();
                    if (obj == null) {
                        throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                    }
                    trim = StringsKt__StringsKt.trim(obj);
                    linkedHashMap.put("verifyCode", trim.toString());
                    ClearEditText et_invite_code = (ClearEditText) RegisterActivity.this._$_findCachedViewById(R$id.et_invite_code);
                    Intrinsics.checkExpressionValueIsNotNull(et_invite_code, "et_invite_code");
                    String obj2 = et_invite_code.getText().toString();
                    if (obj2 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                    }
                    trim2 = StringsKt__StringsKt.trim(obj2);
                    String obj3 = trim2.toString();
                    String str2 = "";
                    if (TextUtils.isEmpty(obj3) || obj3.length() > 6) {
                        OpenInstallChannelBean openChannelBean = DBUtil.getOpenChannelBean();
                        Intrinsics.checkExpressionValueIsNotNull(openChannelBean, "DBUtil.getOpenChannelBean()");
                        if (!TextUtils.isEmpty(openChannelBean.getOpenInstallVcode())) {
                            OpenInstallChannelBean openChannelBean2 = DBUtil.getOpenChannelBean();
                            Intrinsics.checkExpressionValueIsNotNull(openChannelBean2, "DBUtil.getOpenChannelBean()");
                            if (openChannelBean2.getOpenInstallVcode().length() <= 6) {
                                OpenInstallChannelBean openChannelBean3 = DBUtil.getOpenChannelBean();
                                Intrinsics.checkExpressionValueIsNotNull(openChannelBean3, "DBUtil.getOpenChannelBean()");
                                obj3 = openChannelBean3.getOpenInstallVcode();
                            }
                        }
                        obj3 = (TextUtils.isEmpty(AppUtil.getClipData()) || AppUtil.getClipData().length() > 6) ? str2 : AppUtil.getClipData();
                    }
                    linkedHashMap.put("parentInviteCode", obj3);
                    linkedHashMap.put("versionNo", AppUtil.getVersionCodeStr());
                    String str3 = str;
                    if (str3 == null) {
                        str3 = str2;
                    }
                    linkedHashMap.put("validate", str3);
                    regId = RegisterActivity.this.getRegId();
                    linkedHashMap.put("regId", regId);
                    i = RegisterActivity.this.type;
                    if (i == 1) {
                        EditText et_country_code = (EditText) RegisterActivity.this._$_findCachedViewById(R$id.et_country_code);
                        Intrinsics.checkExpressionValueIsNotNull(et_country_code, "et_country_code");
                        String obj4 = et_country_code.getText().toString();
                        if (obj4 == null) {
                            throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                        }
                        trim3 = StringsKt__StringsKt.trim(obj4);
                        linkedHashMap.put("countryCode", trim3.toString());
                        ClearEditText et_phone = (ClearEditText) RegisterActivity.this._$_findCachedViewById(R$id.et_phone);
                        Intrinsics.checkExpressionValueIsNotNull(et_phone, "et_phone");
                        String obj5 = et_phone.getText().toString();
                        if (obj5 == null) {
                            throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                        }
                        trim4 = StringsKt__StringsKt.trim(obj5);
                        linkedHashMap.put("phone", trim4.toString());
                        StringBuilder sb = new StringBuilder();
                        DomainServer domainServer = DomainServer.getInstance();
                        Intrinsics.checkExpressionValueIsNotNull(domainServer, "DomainServer.getInstance()");
                        sb.append(domainServer.getServerUrl());
                        sb.append("/app/memberInfo/register");
                        str2 = sb.toString();
                    } else if (i == 2) {
                        ClearEditText et_input_email = (ClearEditText) RegisterActivity.this._$_findCachedViewById(R$id.et_input_email);
                        Intrinsics.checkExpressionValueIsNotNull(et_input_email, "et_input_email");
                        String obj6 = et_input_email.getText().toString();
                        if (obj6 == null) {
                            throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                        }
                        trim5 = StringsKt__StringsKt.trim(obj6);
                        linkedHashMap.put("mail", trim5.toString());
                        StringBuilder sb2 = new StringBuilder();
                        DomainServer domainServer2 = DomainServer.getInstance();
                        Intrinsics.checkExpressionValueIsNotNull(domainServer2, "DomainServer.getInstance()");
                        sb2.append(domainServer2.getServerUrl());
                        sb2.append("/app/email/mailRegister");
                        str2 = sb2.toString();
                    }
                    mPresenter = RegisterActivity.this.getMPresenter();
                    if (mPresenter == null) {
                        return;
                    }
                    mPresenter.requestLogin(str2, linkedHashMap, false);
                }
            });
        }
    }

    @Override // com.one.tomato.mvp.p080ui.login.impl.ILoginContact$ILoginView
    public void handlerLogin(LoginInfo loginInfo) {
        hideWaitingDialog();
        this.step = 2;
        changeUI();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void nextPs() {
        CharSequence trim;
        CharSequence trim2;
        ClearEditText et_input_ps1 = (ClearEditText) _$_findCachedViewById(R$id.et_input_ps1);
        Intrinsics.checkExpressionValueIsNotNull(et_input_ps1, "et_input_ps1");
        if (et_input_ps1.getText().toString().length() < 6) {
            ToastUtil.showCenterToast((int) R.string.ps_length_tip1);
            return;
        }
        ClearEditText et_input_ps2 = (ClearEditText) _$_findCachedViewById(R$id.et_input_ps2);
        Intrinsics.checkExpressionValueIsNotNull(et_input_ps2, "et_input_ps2");
        if (et_input_ps2.getText().toString().length() < 6) {
            ToastUtil.showCenterToast((int) R.string.ps_length_tip2);
            return;
        }
        ClearEditText et_input_ps12 = (ClearEditText) _$_findCachedViewById(R$id.et_input_ps1);
        Intrinsics.checkExpressionValueIsNotNull(et_input_ps12, "et_input_ps1");
        String obj = et_input_ps12.getText().toString();
        ClearEditText et_input_ps22 = (ClearEditText) _$_findCachedViewById(R$id.et_input_ps2);
        Intrinsics.checkExpressionValueIsNotNull(et_input_ps22, "et_input_ps2");
        if (!Intrinsics.areEqual(obj, et_input_ps22.getText().toString())) {
            ToastUtil.showCenterToast((int) R.string.ps_twice_tip);
            return;
        }
        hideKeyBoard(this);
        showWaitingDialog();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("memberId", Integer.valueOf(DBUtil.getMemberId()));
        ClearEditText et_input_ps13 = (ClearEditText) _$_findCachedViewById(R$id.et_input_ps1);
        Intrinsics.checkExpressionValueIsNotNull(et_input_ps13, "et_input_ps1");
        String obj2 = et_input_ps13.getText().toString();
        if (obj2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
        }
        trim = StringsKt__StringsKt.trim(obj2);
        linkedHashMap.put("firstPassword", trim.toString());
        ClearEditText et_input_ps23 = (ClearEditText) _$_findCachedViewById(R$id.et_input_ps2);
        Intrinsics.checkExpressionValueIsNotNull(et_input_ps23, "et_input_ps2");
        String obj3 = et_input_ps23.getText().toString();
        if (obj3 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
        }
        trim2 = StringsKt__StringsKt.trim(obj3);
        linkedHashMap.put("secondPassword", trim2.toString());
        linkedHashMap.put("token", DBUtil.getToken());
        RegisterPresenter mPresenter = getMPresenter();
        if (mPresenter == null) {
            return;
        }
        mPresenter.requestRegisterUpdate(linkedHashMap);
    }

    @Override // com.one.tomato.mvp.base.view.IBaseView
    public void onError(String str) {
        hideWaitingDialog();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity, com.one.tomato.net.LoginResponseObserver
    public void onLoginSuccess() {
        super.onLoginSuccess();
        hideWaitingDialog();
        RegisterUpdateActivity.Companion.startActivity(this);
        PreferencesUtil.getInstance().putBoolean("level_first_login", true);
        setResult(-1);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == 111) {
            if (intent == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            Bundle extras = intent.getExtras();
            if (extras == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            ((TextView) _$_findCachedViewById(R$id.tv_country_name)).setText(extras.getString("country_name", AppUtil.getString(R.string.location_country_china)));
            Bundle extras2 = intent.getExtras();
            if (extras2 == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            String string = extras2.getString("country_code", AppUtil.getString(R.string.country_code_china));
            ((EditText) _$_findCachedViewById(R$id.et_country_code)).setText(string);
            ((EditText) _$_findCachedViewById(R$id.et_country_code)).setSelection(string.length());
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        TimerTaskUtil.getInstance().onStop();
        CaptchaUtil captchaUtil = this.captchaUtil;
        if (captchaUtil != null) {
            captchaUtil.onDestroy();
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("captchaUtil");
            throw null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String getRegId() {
        int memberId = DBUtil.getMemberId();
        int i = memberId % 10;
        int i2 = (memberId % 100) / 10;
        if (i > i2) {
            i2 = i;
            i = i2;
        }
        String uniqueDeviceID = DeviceInfoUtil.getUniqueDeviceID();
        String md5 = MD5Util.md5(uniqueDeviceID + memberId);
        Intrinsics.checkExpressionValueIsNotNull(md5, "MD5Util.md5(devCode + memberId)");
        if (md5 != null) {
            String upperCase = md5.toUpperCase();
            Intrinsics.checkExpressionValueIsNotNull(upperCase, "(this as java.lang.String).toUpperCase()");
            if (upperCase != null) {
                String substring = upperCase.substring(i, i2);
                Intrinsics.checkExpressionValueIsNotNull(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                LogUtil.m3784i("regId = " + substring);
                return substring;
            }
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
    }
}
