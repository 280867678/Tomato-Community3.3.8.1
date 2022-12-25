package com.one.tomato.mvp.p080ui.login.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.p002v4.app.NotificationCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.dialog.CustomAlertDialog;
import com.one.tomato.entity.ForgetPsSec;
import com.one.tomato.entity.p079db.CountryDB;
import com.one.tomato.entity.p079db.LoginInfo;
import com.one.tomato.entity.p079db.SystemParam;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.p080ui.login.impl.ILoginContact$ILoginView;
import com.one.tomato.mvp.p080ui.login.presenter.RegisterPresenter;
import com.one.tomato.thirdpart.captcha.CaptchaUtil;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.TimerTaskUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.widget.ClearEditText;
import java.util.HashMap;
import java.util.LinkedHashMap;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: ForgetPsActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.login.view.ForgetPsActivity */
/* loaded from: classes3.dex */
public final class ForgetPsActivity extends MvpBaseActivity<ILoginContact$ILoginView, RegisterPresenter> implements ILoginContact$ILoginView {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private CaptchaUtil captchaUtil;
    private boolean isCodeInput;
    private boolean isPSInput1;
    private boolean isPSInput2;
    private boolean isPhoneInput;
    private int type;
    private int step = 1;
    private String sec = "";

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
        return R.layout.activity_forget_ps;
    }

    @Override // com.one.tomato.mvp.p080ui.login.impl.ILoginContact$ILoginView
    public void handlerLogin(LoginInfo loginInfo) {
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

    /* compiled from: ForgetPsActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.login.view.ForgetPsActivity$Companion */
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
            intent.setClass(context, ForgetPsActivity.class);
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
        if (i == 0) {
            ((TextView) _$_findCachedViewById(R$id.tv_register_title)).setText(getString(R.string.login_forget_password));
            LinearLayout ll_select = (LinearLayout) _$_findCachedViewById(R$id.ll_select);
            Intrinsics.checkExpressionValueIsNotNull(ll_select, "ll_select");
            ll_select.setVisibility(0);
            LinearLayout ll_input_account = (LinearLayout) _$_findCachedViewById(R$id.ll_input_account);
            Intrinsics.checkExpressionValueIsNotNull(ll_input_account, "ll_input_account");
            ll_input_account.setVisibility(8);
            ConstraintLayout cl_input_password = (ConstraintLayout) _$_findCachedViewById(R$id.cl_input_password);
            Intrinsics.checkExpressionValueIsNotNull(cl_input_password, "cl_input_password");
            cl_input_password.setVisibility(8);
        } else if (i == 1) {
            int i2 = this.step;
            if (i2 != 1) {
                if (i2 != 2) {
                    return;
                }
                ((TextView) _$_findCachedViewById(R$id.tv_register_title)).setText(R.string.forget_ps_title2);
                LinearLayout ll_select2 = (LinearLayout) _$_findCachedViewById(R$id.ll_select);
                Intrinsics.checkExpressionValueIsNotNull(ll_select2, "ll_select");
                ll_select2.setVisibility(8);
                LinearLayout ll_input_account2 = (LinearLayout) _$_findCachedViewById(R$id.ll_input_account);
                Intrinsics.checkExpressionValueIsNotNull(ll_input_account2, "ll_input_account");
                ll_input_account2.setVisibility(8);
                ConstraintLayout cl_input_password2 = (ConstraintLayout) _$_findCachedViewById(R$id.cl_input_password);
                Intrinsics.checkExpressionValueIsNotNull(cl_input_password2, "cl_input_password");
                cl_input_password2.setVisibility(0);
                return;
            }
            ((TextView) _$_findCachedViewById(R$id.tv_register_title)).setText(R.string.forget_ps_title1);
            LinearLayout ll_select3 = (LinearLayout) _$_findCachedViewById(R$id.ll_select);
            Intrinsics.checkExpressionValueIsNotNull(ll_select3, "ll_select");
            ll_select3.setVisibility(8);
            LinearLayout ll_input_account3 = (LinearLayout) _$_findCachedViewById(R$id.ll_input_account);
            Intrinsics.checkExpressionValueIsNotNull(ll_input_account3, "ll_input_account");
            ll_input_account3.setVisibility(0);
            ConstraintLayout cl_forget_phone = (ConstraintLayout) _$_findCachedViewById(R$id.cl_forget_phone);
            Intrinsics.checkExpressionValueIsNotNull(cl_forget_phone, "cl_forget_phone");
            cl_forget_phone.setVisibility(0);
            ConstraintLayout cl_forget_email = (ConstraintLayout) _$_findCachedViewById(R$id.cl_forget_email);
            Intrinsics.checkExpressionValueIsNotNull(cl_forget_email, "cl_forget_email");
            cl_forget_email.setVisibility(8);
            ConstraintLayout cl_input_password3 = (ConstraintLayout) _$_findCachedViewById(R$id.cl_input_password);
            Intrinsics.checkExpressionValueIsNotNull(cl_input_password3, "cl_input_password");
            cl_input_password3.setVisibility(8);
        } else if (i != 2) {
        } else {
            int i3 = this.step;
            if (i3 != 1) {
                if (i3 != 2) {
                    return;
                }
                ((TextView) _$_findCachedViewById(R$id.tv_register_title)).setText(R.string.forget_ps_title2);
                LinearLayout ll_select4 = (LinearLayout) _$_findCachedViewById(R$id.ll_select);
                Intrinsics.checkExpressionValueIsNotNull(ll_select4, "ll_select");
                ll_select4.setVisibility(8);
                LinearLayout ll_input_account4 = (LinearLayout) _$_findCachedViewById(R$id.ll_input_account);
                Intrinsics.checkExpressionValueIsNotNull(ll_input_account4, "ll_input_account");
                ll_input_account4.setVisibility(8);
                ConstraintLayout cl_input_password4 = (ConstraintLayout) _$_findCachedViewById(R$id.cl_input_password);
                Intrinsics.checkExpressionValueIsNotNull(cl_input_password4, "cl_input_password");
                cl_input_password4.setVisibility(0);
                return;
            }
            ((TextView) _$_findCachedViewById(R$id.tv_register_title)).setText(getString(R.string.forget_email));
            LinearLayout ll_select5 = (LinearLayout) _$_findCachedViewById(R$id.ll_select);
            Intrinsics.checkExpressionValueIsNotNull(ll_select5, "ll_select");
            ll_select5.setVisibility(8);
            LinearLayout ll_input_account5 = (LinearLayout) _$_findCachedViewById(R$id.ll_input_account);
            Intrinsics.checkExpressionValueIsNotNull(ll_input_account5, "ll_input_account");
            ll_input_account5.setVisibility(0);
            ConstraintLayout cl_forget_phone2 = (ConstraintLayout) _$_findCachedViewById(R$id.cl_forget_phone);
            Intrinsics.checkExpressionValueIsNotNull(cl_forget_phone2, "cl_forget_phone");
            cl_forget_phone2.setVisibility(8);
            ConstraintLayout cl_forget_email2 = (ConstraintLayout) _$_findCachedViewById(R$id.cl_forget_email);
            Intrinsics.checkExpressionValueIsNotNull(cl_forget_email2, "cl_forget_email");
            cl_forget_email2.setVisibility(0);
            ConstraintLayout cl_input_password5 = (ConstraintLayout) _$_findCachedViewById(R$id.cl_input_password);
            Intrinsics.checkExpressionValueIsNotNull(cl_input_password5, "cl_input_password");
            cl_input_password5.setVisibility(8);
        }
    }

    private final void addListener() {
        ((ImageView) _$_findCachedViewById(R$id.iv_back)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.ForgetPsActivity$addListener$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ForgetPsActivity.this.onBackPressed();
            }
        });
        TextView textView = (TextView) _$_findCachedViewById(R$id.forget_phone);
        if (textView != null) {
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.ForgetPsActivity$addListener$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ForgetPsActivity.this.type = 1;
                    ForgetPsActivity.this.step = 1;
                    ForgetPsActivity.this.changeUI();
                }
            });
        }
        TextView textView2 = (TextView) _$_findCachedViewById(R$id.forget_email);
        if (textView2 != null) {
            textView2.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.ForgetPsActivity$addListener$3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ForgetPsActivity.this.type = 2;
                    ForgetPsActivity.this.step = 1;
                    ForgetPsActivity.this.changeUI();
                }
            });
        }
        TextView textView3 = (TextView) _$_findCachedViewById(R$id.forget_other);
        if (textView3 != null) {
            textView3.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.ForgetPsActivity$addListener$4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ForgetPsActivity.this.otherType();
                }
            });
        }
        ((TextView) _$_findCachedViewById(R$id.tv_country_name)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.ForgetPsActivity$addListener$5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CountryCodeActivity.Companion.startActivity(ForgetPsActivity.this, 111);
            }
        });
        ((ImageView) _$_findCachedViewById(R$id.iv_arrow_right)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.ForgetPsActivity$addListener$6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CountryCodeActivity.Companion.startActivity(ForgetPsActivity.this, 111);
            }
        });
        ((ImageView) _$_findCachedViewById(R$id.iv_login_phone)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.ForgetPsActivity$addListener$7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CountryCodeActivity.Companion.startActivity(ForgetPsActivity.this, 111);
            }
        });
        ((EditText) _$_findCachedViewById(R$id.et_country_code)).addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.mvp.ui.login.view.ForgetPsActivity$addListener$8
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
                ((TextView) ForgetPsActivity.this._$_findCachedViewById(R$id.tv_country_name)).setText(queryCountry.getCountryName());
            }
        });
        ((ClearEditText) _$_findCachedViewById(R$id.et_phone)).addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.mvp.ui.login.view.ForgetPsActivity$addListener$9
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
                ForgetPsActivity.this.isPhoneInput = s.length() > 0;
                TextView tv_get_code = (TextView) ForgetPsActivity.this._$_findCachedViewById(R$id.tv_get_code);
                Intrinsics.checkExpressionValueIsNotNull(tv_get_code, "tv_get_code");
                z = ForgetPsActivity.this.isPhoneInput;
                tv_get_code.setEnabled(z);
                TextView tv_input_account_next = (TextView) ForgetPsActivity.this._$_findCachedViewById(R$id.tv_input_account_next);
                Intrinsics.checkExpressionValueIsNotNull(tv_input_account_next, "tv_input_account_next");
                z2 = ForgetPsActivity.this.isPhoneInput;
                if (z2) {
                    z3 = ForgetPsActivity.this.isCodeInput;
                }
                z4 = false;
                tv_input_account_next.setEnabled(z4);
            }
        });
        ((ClearEditText) _$_findCachedViewById(R$id.et_input_email)).addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.mvp.ui.login.view.ForgetPsActivity$addListener$10
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
                ForgetPsActivity.this.isPhoneInput = s.length() > 0;
                TextView tv_get_code = (TextView) ForgetPsActivity.this._$_findCachedViewById(R$id.tv_get_code);
                Intrinsics.checkExpressionValueIsNotNull(tv_get_code, "tv_get_code");
                z = ForgetPsActivity.this.isPhoneInput;
                tv_get_code.setEnabled(z);
                TextView tv_input_account_next = (TextView) ForgetPsActivity.this._$_findCachedViewById(R$id.tv_input_account_next);
                Intrinsics.checkExpressionValueIsNotNull(tv_input_account_next, "tv_input_account_next");
                z2 = ForgetPsActivity.this.isPhoneInput;
                if (z2) {
                    z3 = ForgetPsActivity.this.isCodeInput;
                }
                z4 = false;
                tv_input_account_next.setEnabled(z4);
            }
        });
        ((ClearEditText) _$_findCachedViewById(R$id.et_verify_code)).addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.mvp.ui.login.view.ForgetPsActivity$addListener$11
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
                ForgetPsActivity.this.isCodeInput = s.length() > 0;
                TextView tv_input_account_next = (TextView) ForgetPsActivity.this._$_findCachedViewById(R$id.tv_input_account_next);
                Intrinsics.checkExpressionValueIsNotNull(tv_input_account_next, "tv_input_account_next");
                z = ForgetPsActivity.this.isPhoneInput;
                if (z) {
                    z2 = ForgetPsActivity.this.isCodeInput;
                }
                z3 = false;
                tv_input_account_next.setEnabled(z3);
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_get_code)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.ForgetPsActivity$addListener$12
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ForgetPsActivity.this.sendCode();
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_input_account_next)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.ForgetPsActivity$addListener$13
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ForgetPsActivity.this.nextAccount();
            }
        });
        ((ClearEditText) _$_findCachedViewById(R$id.et_input_ps1)).addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.mvp.ui.login.view.ForgetPsActivity$addListener$14
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
                ForgetPsActivity.this.isPSInput1 = s.length() > 0;
                TextView tv_input_ps_next = (TextView) ForgetPsActivity.this._$_findCachedViewById(R$id.tv_input_ps_next);
                Intrinsics.checkExpressionValueIsNotNull(tv_input_ps_next, "tv_input_ps_next");
                z = ForgetPsActivity.this.isPSInput1;
                if (z) {
                    z2 = ForgetPsActivity.this.isPSInput2;
                }
                z3 = false;
                tv_input_ps_next.setEnabled(z3);
            }
        });
        ((ClearEditText) _$_findCachedViewById(R$id.et_input_ps2)).addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.mvp.ui.login.view.ForgetPsActivity$addListener$15
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
                ForgetPsActivity.this.isPSInput2 = s.length() > 0;
                TextView tv_input_ps_next = (TextView) ForgetPsActivity.this._$_findCachedViewById(R$id.tv_input_ps_next);
                Intrinsics.checkExpressionValueIsNotNull(tv_input_ps_next, "tv_input_ps_next");
                z = ForgetPsActivity.this.isPSInput1;
                if (z) {
                    z2 = ForgetPsActivity.this.isPSInput2;
                }
                z3 = false;
                tv_input_ps_next.setEnabled(z3);
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_input_ps_next)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.ForgetPsActivity$addListener$16
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ForgetPsActivity.this.nextPs();
            }
        });
        CaptchaUtil captchaUtil = this.captchaUtil;
        if (captchaUtil != null) {
            captchaUtil.setCaptchaUtilListener(new CaptchaUtil.CaptchaUtilListener() { // from class: com.one.tomato.mvp.ui.login.view.ForgetPsActivity$addListener$17
                @Override // com.one.tomato.thirdpart.captcha.CaptchaUtil.CaptchaUtilListener
                public void validateFail() {
                }

                @Override // com.one.tomato.thirdpart.captcha.CaptchaUtil.CaptchaUtilListener
                public void validateSuccess(String str) {
                    ForgetPsActivity.this.login(str);
                }
            });
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("captchaUtil");
            throw null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void sendCode() {
        ((TextView) _$_findCachedViewById(R$id.tv_get_code)).postDelayed(new Runnable() { // from class: com.one.tomato.mvp.ui.login.view.ForgetPsActivity$sendCode$1
            @Override // java.lang.Runnable
            public final void run() {
                int i;
                RegisterPresenter mPresenter;
                RegisterPresenter mPresenter2;
                Editable text;
                String obj;
                CharSequence trim;
                String str = "";
                ((ClearEditText) ForgetPsActivity.this._$_findCachedViewById(R$id.et_verify_code)).setText(str);
                ForgetPsActivity.this.showWaitingDialog();
                i = ForgetPsActivity.this.type;
                if (i == 2) {
                    mPresenter2 = ForgetPsActivity.this.getMPresenter();
                    if (mPresenter2 != null) {
                        ClearEditText clearEditText = (ClearEditText) ForgetPsActivity.this._$_findCachedViewById(R$id.et_input_email);
                        if (clearEditText != null && (text = clearEditText.getText()) != null && (obj = text.toString()) != null) {
                            if (obj == null) {
                                throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                            }
                            trim = StringsKt__StringsKt.trim(obj);
                            String obj2 = trim.toString();
                            if (obj2 != null) {
                                str = obj2;
                            }
                        }
                        mPresenter2.forgetPsBySendEmail(str, 7);
                    }
                } else {
                    mPresenter = ForgetPsActivity.this.getMPresenter();
                    if (mPresenter != null) {
                        EditText et_country_code = (EditText) ForgetPsActivity.this._$_findCachedViewById(R$id.et_country_code);
                        Intrinsics.checkExpressionValueIsNotNull(et_country_code, "et_country_code");
                        String obj3 = et_country_code.getText().toString();
                        ClearEditText et_phone = (ClearEditText) ForgetPsActivity.this._$_findCachedViewById(R$id.et_phone);
                        Intrinsics.checkExpressionValueIsNotNull(et_phone, "et_phone");
                        mPresenter.forgetPsBySendPhone(obj3, et_phone.getText().toString(), 7);
                    }
                }
                TimerTaskUtil.getInstance().onStart(60, new TimerTaskUtil.TimeTask() { // from class: com.one.tomato.mvp.ui.login.view.ForgetPsActivity$sendCode$1.1
                    @Override // com.one.tomato.utils.TimerTaskUtil.TimeTask
                    public void position(int i2) {
                        TextView tv_get_code = (TextView) ForgetPsActivity.this._$_findCachedViewById(R$id.tv_get_code);
                        Intrinsics.checkExpressionValueIsNotNull(tv_get_code, "tv_get_code");
                        tv_get_code.setText(String.valueOf(i2) + "s");
                        TextView tv_get_code2 = (TextView) ForgetPsActivity.this._$_findCachedViewById(R$id.tv_get_code);
                        Intrinsics.checkExpressionValueIsNotNull(tv_get_code2, "tv_get_code");
                        tv_get_code2.setEnabled(false);
                    }

                    @Override // com.one.tomato.utils.TimerTaskUtil.TimeTask
                    public void stop() {
                        ((TextView) ForgetPsActivity.this._$_findCachedViewById(R$id.tv_get_code)).setText(R.string.register_get_code);
                        TextView tv_get_code = (TextView) ForgetPsActivity.this._$_findCachedViewById(R$id.tv_get_code);
                        Intrinsics.checkExpressionValueIsNotNull(tv_get_code, "tv_get_code");
                        tv_get_code.setEnabled(true);
                    }
                });
            }
        }, 500L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void nextAccount() {
        CharSequence trim;
        CharSequence trim2;
        CharSequence trim3;
        CharSequence trim4;
        hideKeyBoard(this);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        ClearEditText et_verify_code = (ClearEditText) _$_findCachedViewById(R$id.et_verify_code);
        Intrinsics.checkExpressionValueIsNotNull(et_verify_code, "et_verify_code");
        String obj = et_verify_code.getText().toString();
        if (obj == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
        }
        trim = StringsKt__StringsKt.trim(obj);
        linkedHashMap.put("verifyCode", trim.toString());
        int i = this.type;
        if (i != 1) {
            if (i != 2) {
                return;
            }
            ClearEditText et_input_email = (ClearEditText) _$_findCachedViewById(R$id.et_input_email);
            Intrinsics.checkExpressionValueIsNotNull(et_input_email, "et_input_email");
            String obj2 = et_input_email.getText().toString();
            if (obj2 == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
            }
            trim4 = StringsKt__StringsKt.trim(obj2);
            linkedHashMap.put(NotificationCompat.CATEGORY_EMAIL, trim4.toString());
            RegisterPresenter mPresenter = getMPresenter();
            if (mPresenter == null) {
                return;
            }
            mPresenter.varifyEmail(linkedHashMap);
            return;
        }
        EditText et_country_code = (EditText) _$_findCachedViewById(R$id.et_country_code);
        Intrinsics.checkExpressionValueIsNotNull(et_country_code, "et_country_code");
        String obj3 = et_country_code.getText().toString();
        if (obj3 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
        }
        trim2 = StringsKt__StringsKt.trim(obj3);
        linkedHashMap.put("countryCode", trim2.toString());
        ClearEditText et_phone = (ClearEditText) _$_findCachedViewById(R$id.et_phone);
        Intrinsics.checkExpressionValueIsNotNull(et_phone, "et_phone");
        String obj4 = et_phone.getText().toString();
        if (obj4 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
        }
        trim3 = StringsKt__StringsKt.trim(obj4);
        linkedHashMap.put("phone", trim3.toString());
        RegisterPresenter mPresenter2 = getMPresenter();
        if (mPresenter2 == null) {
            return;
        }
        mPresenter2.verifyPhoneCode(linkedHashMap);
    }

    @Override // com.one.tomato.mvp.p080ui.login.impl.ILoginContact$ILoginView
    public void handleVerifyPhoneCode(ForgetPsSec forgetPsSec) {
        hideWaitingDialog();
        this.sec = forgetPsSec != null ? forgetPsSec.getSec() : null;
        this.step = 2;
        changeUI();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void nextPs() {
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
        SystemParam systemParam = DBUtil.getSystemParam();
        Intrinsics.checkExpressionValueIsNotNull(systemParam, "DBUtil.getSystemParam()");
        if (systemParam.getLoginCaptcha() == 1) {
            CaptchaUtil captchaUtil = this.captchaUtil;
            if (captchaUtil != null) {
                captchaUtil.validate();
                return;
            } else {
                Intrinsics.throwUninitializedPropertyAccessException("captchaUtil");
                throw null;
            }
        }
        login("");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void login(final String str) {
        TextView textView = (TextView) _$_findCachedViewById(R$id.tv_get_code);
        if (textView != null) {
            textView.post(new Runnable() { // from class: com.one.tomato.mvp.ui.login.view.ForgetPsActivity$login$1
                @Override // java.lang.Runnable
                public final void run() {
                    int i;
                    CharSequence trim;
                    String sb;
                    CharSequence trim2;
                    CharSequence trim3;
                    String str2;
                    RegisterPresenter mPresenter;
                    CharSequence trim4;
                    ForgetPsActivity.this.showWaitingDialog();
                    LinkedHashMap linkedHashMap = new LinkedHashMap();
                    i = ForgetPsActivity.this.type;
                    if (i == 1) {
                        ClearEditText et_phone = (ClearEditText) ForgetPsActivity.this._$_findCachedViewById(R$id.et_phone);
                        Intrinsics.checkExpressionValueIsNotNull(et_phone, "et_phone");
                        String obj = et_phone.getText().toString();
                        if (obj == null) {
                            throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                        }
                        trim = StringsKt__StringsKt.trim(obj);
                        linkedHashMap.put("phone", trim.toString());
                        StringBuilder sb2 = new StringBuilder();
                        DomainServer domainServer = DomainServer.getInstance();
                        Intrinsics.checkExpressionValueIsNotNull(domainServer, "DomainServer.getInstance()");
                        sb2.append(domainServer.getServerUrl());
                        sb2.append("/app/memberInfo/resetPasswordByPhone");
                        sb = sb2.toString();
                    } else if (i != 2) {
                        sb = "";
                    } else {
                        ClearEditText et_input_email = (ClearEditText) ForgetPsActivity.this._$_findCachedViewById(R$id.et_input_email);
                        Intrinsics.checkExpressionValueIsNotNull(et_input_email, "et_input_email");
                        String obj2 = et_input_email.getText().toString();
                        if (obj2 == null) {
                            throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                        }
                        trim4 = StringsKt__StringsKt.trim(obj2);
                        linkedHashMap.put(NotificationCompat.CATEGORY_EMAIL, trim4.toString());
                        StringBuilder sb3 = new StringBuilder();
                        DomainServer domainServer2 = DomainServer.getInstance();
                        Intrinsics.checkExpressionValueIsNotNull(domainServer2, "DomainServer.getInstance()");
                        sb3.append(domainServer2.getServerUrl());
                        sb3.append("/app/email/resetPasswordByEmail");
                        sb = sb3.toString();
                    }
                    ClearEditText et_input_ps1 = (ClearEditText) ForgetPsActivity.this._$_findCachedViewById(R$id.et_input_ps1);
                    Intrinsics.checkExpressionValueIsNotNull(et_input_ps1, "et_input_ps1");
                    String obj3 = et_input_ps1.getText().toString();
                    if (obj3 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                    }
                    trim2 = StringsKt__StringsKt.trim(obj3);
                    linkedHashMap.put("firstNewPassword", trim2.toString());
                    ClearEditText et_input_ps2 = (ClearEditText) ForgetPsActivity.this._$_findCachedViewById(R$id.et_input_ps2);
                    Intrinsics.checkExpressionValueIsNotNull(et_input_ps2, "et_input_ps2");
                    String obj4 = et_input_ps2.getText().toString();
                    if (obj4 != null) {
                        trim3 = StringsKt__StringsKt.trim(obj4);
                        linkedHashMap.put("secondNewPassword", trim3.toString());
                        str2 = ForgetPsActivity.this.sec;
                        linkedHashMap.put("sec", str2);
                        String str3 = str;
                        if (str3 == null) {
                            str3 = "";
                        }
                        linkedHashMap.put("validate", str3);
                        mPresenter = ForgetPsActivity.this.getMPresenter();
                        if (mPresenter == null) {
                            return;
                        }
                        mPresenter.requestLogin(sb, linkedHashMap, true);
                        return;
                    }
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void otherType() {
        final CustomAlertDialog customAlertDialog = new CustomAlertDialog(this);
        customAlertDialog.setTitle(R.string.forget_by_tomato_title);
        customAlertDialog.setMessage(R.string.forget_by_tomato_message);
        customAlertDialog.setCancelButton(R.string.forget_by_tomato_nag);
        customAlertDialog.setConfirmButton(R.string.forget_by_tomato_pos, new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.ForgetPsActivity$otherType$1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CustomAlertDialog.this.dismiss();
                SystemParam systemParam = DBUtil.getSystemParam();
                Intrinsics.checkExpressionValueIsNotNull(systemParam, "DBUtil.getSystemParam()");
                AppUtil.startBrowseView(systemParam.getPotatoUrl());
            }
        });
        customAlertDialog.setCancelButtonTextColor(R.color.tip_color);
        customAlertDialog.setConfirmButtonTextColor(R.color.tip_color);
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity, com.one.tomato.net.LoginResponseObserver
    public void onLoginSuccess() {
        super.onLoginSuccess();
        hideWaitingDialog();
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
}
