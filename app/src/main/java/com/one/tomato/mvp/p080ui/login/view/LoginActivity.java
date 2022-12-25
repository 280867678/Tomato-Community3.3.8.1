package com.one.tomato.mvp.p080ui.login.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.p002v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.entity.ForgetPsSec;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.p079db.CountryDB;
import com.one.tomato.entity.p079db.LoginInfo;
import com.one.tomato.entity.p079db.SystemParam;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.p080ui.login.impl.ILoginContact$ILoginView;
import com.one.tomato.mvp.p080ui.login.presenter.LoginPresenter;
import com.one.tomato.mvp.p080ui.login.view.LoginVerifyAct;
import com.one.tomato.thirdpart.captcha.CaptchaUtil;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.thirdpart.roundimage.RoundedImageView;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.PreferencesUtil;
import com.one.tomato.utils.TimerTaskUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.one.tomato.widget.ClearEditText;
import com.tomatolive.library.utils.LogConstants;
import java.util.HashMap;
import java.util.LinkedHashMap;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import org.slf4j.Marker;

/* compiled from: LoginActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.login.view.LoginActivity */
/* loaded from: classes3.dex */
public final class LoginActivity extends MvpBaseActivity<ILoginContact$ILoginView, LoginPresenter> implements ILoginContact$ILoginView {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private CaptchaUtil captchaUtil;
    private boolean isInputAccount;
    private boolean isInputCode;
    private boolean isInputPassword;
    private boolean isInputPhone;
    private int loginType;
    private SystemParam systemParam;
    private int loginQuickType = 7;
    private String loginLastAccount = "";

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
        return R.layout.activity_login;
    }

    @Override // com.one.tomato.mvp.p080ui.login.impl.ILoginContact$ILoginView
    public void handleVerifyPhoneCode(ForgetPsSec forgetPsSec) {
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
    }

    @Override // com.one.tomato.mvp.base.view.IBaseView
    public void onError(String str) {
    }

    /* compiled from: LoginActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.login.view.LoginActivity$Companion */
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
            intent.setClass(context, LoginActivity.class);
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
        LoginPresenter mPresenter = getMPresenter();
        if (mPresenter != null) {
            mPresenter.requestCountryCodeFromDB();
        }
        SystemParam systemParam = DBUtil.getSystemParam();
        Intrinsics.checkExpressionValueIsNotNull(systemParam, "DBUtil.getSystemParam()");
        this.systemParam = systemParam;
        this.captchaUtil = new CaptchaUtil(getMContext());
        String string = PreferencesUtil.getInstance().getString("login_user_phone", "");
        Intrinsics.checkExpressionValueIsNotNull(string, "PreferencesUtil.getInsta…sConstant.USER_PHONE, \"\")");
        this.loginLastAccount = string;
        if (TextUtils.isEmpty(this.loginLastAccount)) {
            String string2 = PreferencesUtil.getInstance().getString("login_user_account", "");
            Intrinsics.checkExpressionValueIsNotNull(string2, "PreferencesUtil.getInsta…onstant.USER_ACCOUNT, \"\")");
            this.loginLastAccount = string2;
        }
        if (!TextUtils.isEmpty(this.loginLastAccount)) {
            this.loginType = 10;
            SystemParam systemParam2 = this.systemParam;
            if (systemParam2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("systemParam");
                throw null;
            } else if (systemParam2.getMultipleDevice() == 0) {
                this.loginType = 2;
            }
        } else {
            this.loginType = PreferencesUtil.getInstance().getInt("login_type", 2);
        }
        changeUI();
        addListener();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void changeUI() {
        String string;
        String string2;
        boolean contains$default;
        String string3;
        String string4;
        RoundedImageView roundedImageView;
        TextView textView = (TextView) _$_findCachedViewById(R$id.tv_login);
        if (textView != null) {
            textView.setText(getString(R.string.login_handle));
        }
        TextView textView2 = (TextView) _$_findCachedViewById(R$id.tv_register);
        if (textView2 != null) {
            textView2.setText(getString(R.string.login_no_account));
        }
        TextView textView3 = (TextView) _$_findCachedViewById(R$id.tv_register);
        if (textView3 != null) {
            Context mContext = getMContext();
            if (mContext == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            textView3.setTextColor(ContextCompat.getColor(mContext, R.color.text_middle));
        }
        int i = this.loginType;
        if (i == 2) {
            ConstraintLayout constraintLayout = (ConstraintLayout) _$_findCachedViewById(R$id.cl_login_phone_account);
            if (constraintLayout != null) {
                constraintLayout.setVisibility(0);
            }
            LinearLayout linearLayout = (LinearLayout) _$_findCachedViewById(R$id.liner_login_out);
            if (linearLayout != null) {
                linearLayout.setVisibility(8);
            }
            TextView textView4 = (TextView) _$_findCachedViewById(R$id.text_change_account);
            if (textView4 != null) {
                textView4.setVisibility(8);
            }
            TextView textView5 = (TextView) _$_findCachedViewById(R$id.tv_login_title);
            if (textView5 != null) {
                textView5.setVisibility(0);
            }
            ((TextView) _$_findCachedViewById(R$id.tv_login_title)).setText(R.string.login_type_account);
            RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.relate_login_out);
            if (relativeLayout != null) {
                relativeLayout.setVisibility(0);
            }
            ConstraintLayout con_register_phone = (ConstraintLayout) _$_findCachedViewById(R$id.con_register_phone);
            Intrinsics.checkExpressionValueIsNotNull(con_register_phone, "con_register_phone");
            con_register_phone.setVisibility(8);
            ConstraintLayout cl_login_password = (ConstraintLayout) _$_findCachedViewById(R$id.cl_login_password);
            Intrinsics.checkExpressionValueIsNotNull(cl_login_password, "cl_login_password");
            cl_login_password.setVisibility(0);
            ConstraintLayout cl_login_quick = (ConstraintLayout) _$_findCachedViewById(R$id.cl_login_quick);
            Intrinsics.checkExpressionValueIsNotNull(cl_login_quick, "cl_login_quick");
            cl_login_quick.setVisibility(8);
            TextView textView6 = (TextView) _$_findCachedViewById(R$id.text_find_account);
            if (textView6 != null) {
                textView6.setVisibility(8);
            }
            TextView textView7 = (TextView) _$_findCachedViewById(R$id.tv_register);
            if (textView7 != null) {
                textView7.setVisibility(0);
            }
            LinearLayout linearLayout2 = (LinearLayout) _$_findCachedViewById(R$id.constraintLayout);
            if (linearLayout2 != null) {
                linearLayout2.setVisibility(0);
            }
            LinearLayout linearLayout3 = (LinearLayout) _$_findCachedViewById(R$id.ll_protocal);
            if (linearLayout3 != null) {
                linearLayout3.setVisibility(8);
            }
            TextView textView8 = (TextView) _$_findCachedViewById(R$id.tv_forget_ps);
            if (textView8 != null) {
                textView8.setVisibility(0);
            }
            ConstraintLayout constraintLayout2 = (ConstraintLayout) _$_findCachedViewById(R$id.cl_login_account);
            if (constraintLayout2 != null) {
                constraintLayout2.setVisibility(0);
            }
            TextView text_change_login = (TextView) _$_findCachedViewById(R$id.text_change_login);
            Intrinsics.checkExpressionValueIsNotNull(text_change_login, "text_change_login");
            text_change_login.setVisibility(0);
            ((ClearEditText) _$_findCachedViewById(R$id.et_account)).setText(PreferencesUtil.getInstance().getString("login_user_account"));
            this.isInputAccount = !TextUtils.isEmpty(string);
            this.isInputPhone = false;
            ((ClearEditText) _$_findCachedViewById(R$id.et_password)).setText("");
            this.isInputPassword = false;
            ((ClearEditText) _$_findCachedViewById(R$id.et_input_code)).setText("");
            this.isInputCode = false;
            TextView tv_login = (TextView) _$_findCachedViewById(R$id.tv_login);
            Intrinsics.checkExpressionValueIsNotNull(tv_login, "tv_login");
            tv_login.setEnabled(false);
            TextView textView9 = (TextView) _$_findCachedViewById(R$id.tv_appeal);
            if (textView9 != null) {
                textView9.setVisibility(0);
            }
            TextView textView10 = (TextView) _$_findCachedViewById(R$id.tv_login_type_line);
            if (textView10 != null) {
                textView10.setVisibility(0);
            }
            TextView textView11 = (TextView) _$_findCachedViewById(R$id.tv_forget_ps);
            if (textView11 != null) {
                textView11.setVisibility(0);
            }
            TextView textView12 = (TextView) _$_findCachedViewById(R$id.tv_login_type_line2);
            if (textView12 != null) {
                textView12.setVisibility(0);
            }
            TextView textView13 = (TextView) _$_findCachedViewById(R$id.tv_register);
            if (textView13 != null) {
                textView13.setVisibility(0);
            }
            TextView textView14 = (TextView) _$_findCachedViewById(R$id.text_change_login);
            if (textView14 == null) {
                return;
            }
            textView14.setText(AppUtil.getString(R.string.login_type_quick));
        } else if (i == 3) {
            ((TextView) _$_findCachedViewById(R$id.tv_login_title)).setText(R.string.login_type_quick);
            TextView textView15 = (TextView) _$_findCachedViewById(R$id.tv_login_title);
            if (textView15 != null) {
                textView15.setVisibility(0);
            }
            TextView text_change_login2 = (TextView) _$_findCachedViewById(R$id.text_change_login);
            Intrinsics.checkExpressionValueIsNotNull(text_change_login2, "text_change_login");
            text_change_login2.setVisibility(0);
            RelativeLayout relativeLayout2 = (RelativeLayout) _$_findCachedViewById(R$id.relate_login_out);
            if (relativeLayout2 != null) {
                relativeLayout2.setVisibility(0);
            }
            ConstraintLayout constraintLayout3 = (ConstraintLayout) _$_findCachedViewById(R$id.cl_login_phone_account);
            if (constraintLayout3 != null) {
                constraintLayout3.setVisibility(0);
            }
            LinearLayout linearLayout4 = (LinearLayout) _$_findCachedViewById(R$id.liner_login_out);
            if (linearLayout4 != null) {
                linearLayout4.setVisibility(8);
            }
            ConstraintLayout cl_login_password2 = (ConstraintLayout) _$_findCachedViewById(R$id.cl_login_password);
            Intrinsics.checkExpressionValueIsNotNull(cl_login_password2, "cl_login_password");
            cl_login_password2.setVisibility(8);
            TextView textView16 = (TextView) _$_findCachedViewById(R$id.text_change_account);
            if (textView16 != null) {
                textView16.setVisibility(8);
            }
            ConstraintLayout cl_login_quick2 = (ConstraintLayout) _$_findCachedViewById(R$id.cl_login_quick);
            Intrinsics.checkExpressionValueIsNotNull(cl_login_quick2, "cl_login_quick");
            cl_login_quick2.setVisibility(0);
            TextView textView17 = (TextView) _$_findCachedViewById(R$id.tv_register);
            if (textView17 != null) {
                textView17.setVisibility(0);
            }
            LinearLayout linearLayout5 = (LinearLayout) _$_findCachedViewById(R$id.ll_protocal);
            if (linearLayout5 != null) {
                linearLayout5.setVisibility(8);
            }
            TextView textView18 = (TextView) _$_findCachedViewById(R$id.tv_forget_ps);
            if (textView18 != null) {
                textView18.setVisibility(0);
            }
            ConstraintLayout con_register_phone2 = (ConstraintLayout) _$_findCachedViewById(R$id.con_register_phone);
            Intrinsics.checkExpressionValueIsNotNull(con_register_phone2, "con_register_phone");
            con_register_phone2.setVisibility(0);
            TextView textView19 = (TextView) _$_findCachedViewById(R$id.text_find_account);
            if (textView19 != null) {
                textView19.setVisibility(8);
            }
            TextView tv_login_type_line = (TextView) _$_findCachedViewById(R$id.tv_login_type_line);
            Intrinsics.checkExpressionValueIsNotNull(tv_login_type_line, "tv_login_type_line");
            tv_login_type_line.setVisibility(8);
            TextView tv_login_type_line2 = (TextView) _$_findCachedViewById(R$id.tv_login_type_line2);
            Intrinsics.checkExpressionValueIsNotNull(tv_login_type_line2, "tv_login_type_line2");
            tv_login_type_line2.setVisibility(0);
            ConstraintLayout constraintLayout4 = (ConstraintLayout) _$_findCachedViewById(R$id.cl_login_account);
            if (constraintLayout4 != null) {
                constraintLayout4.setVisibility(8);
            }
            ((ClearEditText) _$_findCachedViewById(R$id.et_phone)).setText(PreferencesUtil.getInstance().getString("login_user_phone"));
            this.isInputPhone = !TextUtils.isEmpty(string2);
            this.isInputAccount = false;
            String countryCode = PreferencesUtil.getInstance().getString("country_code", AppUtil.getString(R.string.ps_city));
            Intrinsics.checkExpressionValueIsNotNull(countryCode, "countryCode");
            contains$default = StringsKt__StringsKt.contains$default(countryCode, Marker.ANY_NON_NULL_MARKER, false, 2, null);
            if (!contains$default) {
                countryCode = '+' + countryCode;
            }
            ((EditText) _$_findCachedViewById(R$id.et_country_code)).setText(countryCode);
            String string5 = PreferencesUtil.getInstance().getString("country_name", AppUtil.getString(R.string.location_country_china));
            ((TextView) _$_findCachedViewById(R$id.tv_country_name)).setText(string5);
            if (AppUtil.getString(R.string.ps_city).equals(countryCode) && TextUtils.isEmpty(string5)) {
                ((TextView) _$_findCachedViewById(R$id.tv_country_name)).setText(R.string.location_country_china);
            }
            ((ClearEditText) _$_findCachedViewById(R$id.et_password)).setText("");
            this.isInputPassword = false;
            ((ClearEditText) _$_findCachedViewById(R$id.et_input_code)).setText("");
            this.isInputCode = false;
            TextView tv_login2 = (TextView) _$_findCachedViewById(R$id.tv_login);
            Intrinsics.checkExpressionValueIsNotNull(tv_login2, "tv_login");
            tv_login2.setEnabled(false);
            TextView tv_get_code = (TextView) _$_findCachedViewById(R$id.tv_get_code);
            Intrinsics.checkExpressionValueIsNotNull(tv_get_code, "tv_get_code");
            tv_get_code.setEnabled(this.isInputPhone);
            TextView textView20 = (TextView) _$_findCachedViewById(R$id.tv_appeal);
            if (textView20 != null) {
                textView20.setVisibility(0);
            }
            TextView textView21 = (TextView) _$_findCachedViewById(R$id.tv_login_type_line);
            if (textView21 != null) {
                textView21.setVisibility(0);
            }
            TextView textView22 = (TextView) _$_findCachedViewById(R$id.tv_forget_ps);
            if (textView22 != null) {
                textView22.setVisibility(8);
            }
            TextView textView23 = (TextView) _$_findCachedViewById(R$id.tv_login_type_line2);
            if (textView23 != null) {
                textView23.setVisibility(8);
            }
            TextView textView24 = (TextView) _$_findCachedViewById(R$id.tv_register);
            if (textView24 != null) {
                textView24.setVisibility(0);
            }
            TextView textView25 = (TextView) _$_findCachedViewById(R$id.text_change_login);
            if (textView25 == null) {
                return;
            }
            textView25.setText(AppUtil.getString(R.string.login_type_account));
        } else if (i == 4) {
            ((TextView) _$_findCachedViewById(R$id.tv_login_title)).setText(getString(R.string.login_find_account_title));
            TextView textView26 = (TextView) _$_findCachedViewById(R$id.tv_login_title);
            if (textView26 != null) {
                textView26.setVisibility(0);
            }
            ConstraintLayout con_register_phone3 = (ConstraintLayout) _$_findCachedViewById(R$id.con_register_phone);
            Intrinsics.checkExpressionValueIsNotNull(con_register_phone3, "con_register_phone");
            con_register_phone3.setVisibility(8);
            ConstraintLayout cl_login_password3 = (ConstraintLayout) _$_findCachedViewById(R$id.cl_login_password);
            Intrinsics.checkExpressionValueIsNotNull(cl_login_password3, "cl_login_password");
            cl_login_password3.setVisibility(0);
            ConstraintLayout cl_login_quick3 = (ConstraintLayout) _$_findCachedViewById(R$id.cl_login_quick);
            Intrinsics.checkExpressionValueIsNotNull(cl_login_quick3, "cl_login_quick");
            cl_login_quick3.setVisibility(8);
            LinearLayout linearLayout6 = (LinearLayout) _$_findCachedViewById(R$id.constraintLayout);
            if (linearLayout6 != null) {
                linearLayout6.setVisibility(8);
            }
            TextView textView27 = (TextView) _$_findCachedViewById(R$id.tv_register);
            if (textView27 != null) {
                textView27.setVisibility(0);
            }
            LinearLayout linearLayout7 = (LinearLayout) _$_findCachedViewById(R$id.ll_protocal);
            if (linearLayout7 != null) {
                linearLayout7.setVisibility(8);
            }
            TextView textView28 = (TextView) _$_findCachedViewById(R$id.tv_forget_ps);
            if (textView28 != null) {
                textView28.setVisibility(8);
            }
            TextView textView29 = (TextView) _$_findCachedViewById(R$id.tv_register);
            if (textView29 != null) {
                textView29.setText(getString(R.string.login_aready_account));
            }
            TextView textView30 = (TextView) _$_findCachedViewById(R$id.tv_register);
            if (textView30 != null) {
                Context mContext2 = getMContext();
                if (mContext2 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                textView30.setTextColor(ContextCompat.getColor(mContext2, R.color.text_light));
            }
            TextView textView31 = (TextView) _$_findCachedViewById(R$id.text_find_account);
            if (textView31 != null) {
                textView31.setVisibility(8);
            }
            ((ClearEditText) _$_findCachedViewById(R$id.et_account)).setText(PreferencesUtil.getInstance().getString("login_user_account"));
            this.isInputAccount = !TextUtils.isEmpty(string3);
            this.isInputPhone = false;
            ((ClearEditText) _$_findCachedViewById(R$id.et_password)).setText("");
            this.isInputPassword = false;
            ((ClearEditText) _$_findCachedViewById(R$id.et_input_code)).setText("");
            this.isInputCode = false;
            TextView tv_login3 = (TextView) _$_findCachedViewById(R$id.tv_login);
            Intrinsics.checkExpressionValueIsNotNull(tv_login3, "tv_login");
            tv_login3.setEnabled(false);
            TextView textView32 = (TextView) _$_findCachedViewById(R$id.tv_login);
            if (textView32 == null) {
                return;
            }
            textView32.setText(AppUtil.getString(R.string.common_confirm));
        } else if (i != 5) {
            if (i != 10) {
                return;
            }
            ConstraintLayout constraintLayout5 = (ConstraintLayout) _$_findCachedViewById(R$id.cl_login_phone_account);
            if (constraintLayout5 != null) {
                constraintLayout5.setVisibility(8);
            }
            LinearLayout linearLayout8 = (LinearLayout) _$_findCachedViewById(R$id.liner_login_out);
            if (linearLayout8 != null) {
                linearLayout8.setVisibility(0);
            }
            TextView textView33 = (TextView) _$_findCachedViewById(R$id.text_change_account);
            if (textView33 != null) {
                textView33.setVisibility(0);
            }
            TextView textView34 = (TextView) _$_findCachedViewById(R$id.tv_login_title);
            if (textView34 != null) {
                textView34.setVisibility(8);
            }
            RelativeLayout relativeLayout3 = (RelativeLayout) _$_findCachedViewById(R$id.relate_login_out);
            if (relativeLayout3 != null) {
                relativeLayout3.setVisibility(0);
            }
            TextView text_change_login3 = (TextView) _$_findCachedViewById(R$id.text_change_login);
            Intrinsics.checkExpressionValueIsNotNull(text_change_login3, "text_change_login");
            text_change_login3.setVisibility(8);
            String string6 = PreferencesUtil.getInstance().getString("login_member_head", "");
            if (string6 != null) {
                ImageLoaderUtil.loadHeadImage(getMContext(), (RoundedImageView) _$_findCachedViewById(R$id.round_view), new ImageBean(string6));
            }
            if (TextUtils.isEmpty(string6) && (roundedImageView = (RoundedImageView) _$_findCachedViewById(R$id.round_view)) != null) {
                roundedImageView.setImageResource(R.drawable.default_img_head);
            }
            String str = this.loginLastAccount;
            TextView textView35 = (TextView) _$_findCachedViewById(R$id.text_login_out_account);
            if (textView35 != null) {
                textView35.setText(AppUtil.getMaskPhone(str));
            }
            TextView textView36 = (TextView) _$_findCachedViewById(R$id.tv_appeal);
            if (textView36 != null) {
                textView36.setVisibility(0);
            }
            TextView textView37 = (TextView) _$_findCachedViewById(R$id.tv_login_type_line);
            if (textView37 != null) {
                textView37.setVisibility(0);
            }
            TextView textView38 = (TextView) _$_findCachedViewById(R$id.tv_forget_ps);
            if (textView38 != null) {
                textView38.setVisibility(8);
            }
            TextView textView39 = (TextView) _$_findCachedViewById(R$id.tv_login_type_line2);
            if (textView39 != null) {
                textView39.setVisibility(8);
            }
            TextView textView40 = (TextView) _$_findCachedViewById(R$id.tv_register);
            if (textView40 == null) {
                return;
            }
            textView40.setVisibility(0);
        } else {
            ((TextView) _$_findCachedViewById(R$id.tv_login_title)).setText(getString(R.string.login_bind_phone));
            TextView textView41 = (TextView) _$_findCachedViewById(R$id.tv_login_title);
            if (textView41 != null) {
                textView41.setVisibility(0);
            }
            ConstraintLayout con_register_phone4 = (ConstraintLayout) _$_findCachedViewById(R$id.con_register_phone);
            Intrinsics.checkExpressionValueIsNotNull(con_register_phone4, "con_register_phone");
            con_register_phone4.setVisibility(0);
            ConstraintLayout cl_login_password4 = (ConstraintLayout) _$_findCachedViewById(R$id.cl_login_password);
            Intrinsics.checkExpressionValueIsNotNull(cl_login_password4, "cl_login_password");
            cl_login_password4.setVisibility(8);
            ConstraintLayout cl_login_quick4 = (ConstraintLayout) _$_findCachedViewById(R$id.cl_login_quick);
            Intrinsics.checkExpressionValueIsNotNull(cl_login_quick4, "cl_login_quick");
            cl_login_quick4.setVisibility(0);
            TextView textView42 = (TextView) _$_findCachedViewById(R$id.text_find_account);
            if (textView42 != null) {
                textView42.setVisibility(8);
            }
            TextView textView43 = (TextView) _$_findCachedViewById(R$id.tv_forget_ps);
            if (textView43 != null) {
                textView43.setVisibility(0);
            }
            LinearLayout linearLayout9 = (LinearLayout) _$_findCachedViewById(R$id.constraintLayout);
            if (linearLayout9 != null) {
                linearLayout9.setVisibility(8);
            }
            TextView textView44 = (TextView) _$_findCachedViewById(R$id.tv_register);
            if (textView44 != null) {
                textView44.setVisibility(0);
            }
            LinearLayout linearLayout10 = (LinearLayout) _$_findCachedViewById(R$id.ll_protocal);
            if (linearLayout10 != null) {
                linearLayout10.setVisibility(8);
            }
            TextView textView45 = (TextView) _$_findCachedViewById(R$id.tv_register);
            if (textView45 != null) {
                textView45.setText(getString(R.string.login_bind_phone_promt));
            }
            TextView textView46 = (TextView) _$_findCachedViewById(R$id.tv_register);
            if (textView46 != null) {
                Context mContext3 = getMContext();
                if (mContext3 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                textView46.setTextColor(ContextCompat.getColor(mContext3, R.color.text_light));
            }
            String string7 = PreferencesUtil.getInstance().getString("country_code", AppUtil.getString(R.string.ps_city));
            ((EditText) _$_findCachedViewById(R$id.et_country_code)).setText(string7);
            String string8 = PreferencesUtil.getInstance().getString("country_name", "");
            ((TextView) _$_findCachedViewById(R$id.tv_country_name)).setText(string8);
            if (AppUtil.getString(R.string.ps_city).equals(string7) && TextUtils.isEmpty(string8)) {
                ((TextView) _$_findCachedViewById(R$id.tv_country_name)).setText(R.string.location_country_china);
            }
            ((ClearEditText) _$_findCachedViewById(R$id.et_phone)).setText(PreferencesUtil.getInstance().getString("login_user_phone"));
            this.isInputPhone = !TextUtils.isEmpty(string4);
            this.isInputAccount = false;
            ((ClearEditText) _$_findCachedViewById(R$id.et_password)).setText("");
            this.isInputPassword = false;
            ((ClearEditText) _$_findCachedViewById(R$id.et_input_code)).setText("");
            this.isInputCode = false;
            TextView tv_login4 = (TextView) _$_findCachedViewById(R$id.tv_login);
            Intrinsics.checkExpressionValueIsNotNull(tv_login4, "tv_login");
            tv_login4.setEnabled(false);
            TextView tv_get_code2 = (TextView) _$_findCachedViewById(R$id.tv_get_code);
            Intrinsics.checkExpressionValueIsNotNull(tv_get_code2, "tv_get_code");
            tv_get_code2.setEnabled(this.isInputPhone);
            TextView textView47 = (TextView) _$_findCachedViewById(R$id.tv_login);
            if (textView47 == null) {
                return;
            }
            textView47.setText(AppUtil.getString(R.string.login_bind_btn));
        }
    }

    private final void addListener() {
        ((ImageView) _$_findCachedViewById(R$id.iv_back)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.LoginActivity$addListener$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LoginActivity.this.onBackPressed();
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_country_name)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.LoginActivity$addListener$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CountryCodeActivity.Companion.startActivity(LoginActivity.this, 111);
            }
        });
        ((ImageView) _$_findCachedViewById(R$id.iv_arrow_right)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.LoginActivity$addListener$3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CountryCodeActivity.Companion.startActivity(LoginActivity.this, 111);
            }
        });
        ((ImageView) _$_findCachedViewById(R$id.iv_login_phone)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.LoginActivity$addListener$4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CountryCodeActivity.Companion.startActivity(LoginActivity.this, 111);
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_forget_ps)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.LoginActivity$addListener$5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ForgetPsActivity.Companion.startActivity(LoginActivity.this);
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_login)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.LoginActivity$addListener$6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i;
                int i2;
                i = LoginActivity.this.loginType;
                if (i == 4) {
                    return;
                }
                i2 = LoginActivity.this.loginType;
                if (i2 == 5) {
                    return;
                }
                LoginActivity.this.showImageVer();
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_register)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.LoginActivity$addListener$7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RegisterActivity.Companion.startActivity(LoginActivity.this);
            }
        });
        TextView textView = (TextView) _$_findCachedViewById(R$id.text_change_login);
        if (textView != null) {
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.LoginActivity$addListener$8
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    int i;
                    i = LoginActivity.this.loginType;
                    if (i == 3) {
                        LoginActivity.this.loginType = 2;
                    } else {
                        LoginActivity.this.loginType = 3;
                    }
                    LoginActivity.this.changeUI();
                }
            });
        }
        TextView textView2 = (TextView) _$_findCachedViewById(R$id.text_change_account);
        if (textView2 != null) {
            textView2.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.LoginActivity$addListener$9
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    LoginActivity.this.loginType = 2;
                    LoginActivity.this.changeUI();
                }
            });
        }
        ((TextView) _$_findCachedViewById(R$id.tv_get_code)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.LoginActivity$addListener$10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LoginActivity.this.sendCode();
            }
        });
        TextView textView3 = (TextView) _$_findCachedViewById(R$id.text_find_account);
        if (textView3 != null) {
            textView3.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.LoginActivity$addListener$11
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    LoginActivity.this.loginType = 4;
                    LoginActivity.this.changeUI();
                }
            });
        }
        ((TextView) _$_findCachedViewById(R$id.tv_protocal)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.LoginActivity$addListener$12
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                UserProtocolActivity.Companion.startActivity(LoginActivity.this);
            }
        });
        ((EditText) _$_findCachedViewById(R$id.et_country_code)).addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.mvp.ui.login.view.LoginActivity$addListener$13
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                String valueOf = String.valueOf(editable);
                if (valueOf.length() > 0) {
                    CountryDB queryCountry = DBUtil.queryCountry(valueOf);
                    if (queryCountry != null) {
                        ((TextView) LoginActivity.this._$_findCachedViewById(R$id.tv_country_name)).setText(queryCountry.getCountryName());
                    } else {
                        ((TextView) LoginActivity.this._$_findCachedViewById(R$id.tv_country_name)).setText("");
                    }
                }
            }
        });
        ((ClearEditText) _$_findCachedViewById(R$id.et_phone)).addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.mvp.ui.login.view.LoginActivity$addListener$14
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int i, int i2, int i3) {
                Intrinsics.checkParameterIsNotNull(s, "s");
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int i, int i2, int i3) {
                Intrinsics.checkParameterIsNotNull(s, "s");
            }

            /* JADX WARN: Code restructure failed: missing block: B:9:0x003d, code lost:
                if (r0 != false) goto L10;
             */
            @Override // android.text.TextWatcher
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void afterTextChanged(Editable s) {
                int i;
                boolean z;
                boolean z2;
                boolean z3;
                Intrinsics.checkParameterIsNotNull(s, "s");
                boolean z4 = true;
                LoginActivity.this.isInputPhone = s.length() > 0;
                i = LoginActivity.this.loginType;
                if (3 == i) {
                    TextView tv_login = (TextView) LoginActivity.this._$_findCachedViewById(R$id.tv_login);
                    Intrinsics.checkExpressionValueIsNotNull(tv_login, "tv_login");
                    z = LoginActivity.this.isInputPhone;
                    if (z) {
                        z3 = LoginActivity.this.isInputCode;
                    }
                    z4 = false;
                    tv_login.setEnabled(z4);
                    TextView tv_get_code = (TextView) LoginActivity.this._$_findCachedViewById(R$id.tv_get_code);
                    Intrinsics.checkExpressionValueIsNotNull(tv_get_code, "tv_get_code");
                    z2 = LoginActivity.this.isInputPhone;
                    tv_get_code.setEnabled(z2);
                }
            }
        });
        ((ClearEditText) _$_findCachedViewById(R$id.et_account)).addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.mvp.ui.login.view.LoginActivity$addListener$15
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
                LoginActivity.this.isInputAccount = s.length() > 0;
                TextView tv_get_code = (TextView) LoginActivity.this._$_findCachedViewById(R$id.tv_get_code);
                Intrinsics.checkExpressionValueIsNotNull(tv_get_code, "tv_get_code");
                z = LoginActivity.this.isInputAccount;
                tv_get_code.setEnabled(z);
                TextView tv_login = (TextView) LoginActivity.this._$_findCachedViewById(R$id.tv_login);
                Intrinsics.checkExpressionValueIsNotNull(tv_login, "tv_login");
                z2 = LoginActivity.this.isInputAccount;
                if (z2) {
                    z3 = LoginActivity.this.isInputPassword;
                }
                z4 = false;
                tv_login.setEnabled(z4);
            }
        });
        ((ClearEditText) _$_findCachedViewById(R$id.et_password)).addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.mvp.ui.login.view.LoginActivity$addListener$16
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
                int i2;
                int i3;
                boolean z;
                boolean z2;
                boolean z3;
                boolean z4;
                Intrinsics.checkParameterIsNotNull(s, "s");
                boolean z5 = false;
                LoginActivity.this.isInputPassword = (s.length() > 0) && s.length() >= 6;
                i = LoginActivity.this.loginType;
                if (2 != i) {
                    i2 = LoginActivity.this.loginType;
                    if (i2 != 3) {
                        i3 = LoginActivity.this.loginType;
                        if (10 != i3) {
                            return;
                        }
                        TextView tv_login = (TextView) LoginActivity.this._$_findCachedViewById(R$id.tv_login);
                        Intrinsics.checkExpressionValueIsNotNull(tv_login, "tv_login");
                        tv_login.setEnabled(true);
                        return;
                    }
                    TextView tv_login2 = (TextView) LoginActivity.this._$_findCachedViewById(R$id.tv_login);
                    Intrinsics.checkExpressionValueIsNotNull(tv_login2, "tv_login");
                    z = LoginActivity.this.isInputPhone;
                    if (z) {
                        z2 = LoginActivity.this.isInputPassword;
                        if (z2) {
                            z5 = true;
                        }
                    }
                    tv_login2.setEnabled(z5);
                    return;
                }
                TextView tv_login3 = (TextView) LoginActivity.this._$_findCachedViewById(R$id.tv_login);
                Intrinsics.checkExpressionValueIsNotNull(tv_login3, "tv_login");
                z3 = LoginActivity.this.isInputAccount;
                if (z3) {
                    z4 = LoginActivity.this.isInputPassword;
                    if (z4) {
                        z5 = true;
                    }
                }
                tv_login3.setEnabled(z5);
            }
        });
        ((ClearEditText) _$_findCachedViewById(R$id.et_input_code)).addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.mvp.ui.login.view.LoginActivity$addListener$17
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int i, int i2, int i3) {
                Intrinsics.checkParameterIsNotNull(s, "s");
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int i, int i2, int i3) {
                Intrinsics.checkParameterIsNotNull(s, "s");
            }

            /* JADX WARN: Code restructure failed: missing block: B:25:0x0081, code lost:
                if (r0 != false) goto L26;
             */
            /* JADX WARN: Code restructure failed: missing block: B:36:0x00b2, code lost:
                if (r0 != false) goto L37;
             */
            /* JADX WARN: Code restructure failed: missing block: B:9:0x003d, code lost:
                if (r0 != false) goto L10;
             */
            @Override // android.text.TextWatcher
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void afterTextChanged(Editable s) {
                int i;
                int i2;
                boolean z;
                boolean z2;
                int i3;
                boolean z3;
                boolean z4;
                boolean z5;
                boolean z6;
                Intrinsics.checkParameterIsNotNull(s, "s");
                i = LoginActivity.this.loginType;
                boolean z7 = true;
                if (i != 2) {
                    i2 = LoginActivity.this.loginType;
                    if (i2 == 3) {
                        i3 = LoginActivity.this.loginQuickType;
                        if (i3 == 6) {
                            LoginActivity.this.isInputCode = s.length() > 0;
                            TextView tv_login = (TextView) LoginActivity.this._$_findCachedViewById(R$id.tv_login);
                            Intrinsics.checkExpressionValueIsNotNull(tv_login, "tv_login");
                            z3 = LoginActivity.this.isInputAccount;
                            if (z3) {
                                z4 = LoginActivity.this.isInputCode;
                            }
                            z7 = false;
                            tv_login.setEnabled(z7);
                            return;
                        }
                    }
                    LoginActivity.this.isInputCode = s.length() > 0;
                    TextView tv_login2 = (TextView) LoginActivity.this._$_findCachedViewById(R$id.tv_login);
                    Intrinsics.checkExpressionValueIsNotNull(tv_login2, "tv_login");
                    z = LoginActivity.this.isInputPhone;
                    if (z) {
                        z2 = LoginActivity.this.isInputCode;
                    }
                    z7 = false;
                    tv_login2.setEnabled(z7);
                    return;
                }
                LoginActivity.this.isInputCode = s.length() > 0;
                TextView tv_login3 = (TextView) LoginActivity.this._$_findCachedViewById(R$id.tv_login);
                Intrinsics.checkExpressionValueIsNotNull(tv_login3, "tv_login");
                z5 = LoginActivity.this.isInputAccount;
                if (z5) {
                    z6 = LoginActivity.this.isInputCode;
                }
                z7 = false;
                tv_login3.setEnabled(z7);
            }
        });
        TextView textView4 = (TextView) _$_findCachedViewById(R$id.tv_appeal);
        if (textView4 != null) {
            textView4.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.LoginActivity$addListener$18
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Context mContext;
                    Intent intent = new Intent(LoginActivity.this, AccoutnAprealActivity.class);
                    mContext = LoginActivity.this.getMContext();
                    if (mContext != null) {
                        mContext.startActivity(intent);
                    }
                }
            });
        }
        CaptchaUtil captchaUtil = this.captchaUtil;
        if (captchaUtil != null) {
            captchaUtil.setCaptchaUtilListener(new CaptchaUtil.CaptchaUtilListener() { // from class: com.one.tomato.mvp.ui.login.view.LoginActivity$addListener$19
                @Override // com.one.tomato.thirdpart.captcha.CaptchaUtil.CaptchaUtilListener
                public void validateFail() {
                }

                @Override // com.one.tomato.thirdpart.captcha.CaptchaUtil.CaptchaUtilListener
                public void validateSuccess(String str) {
                    LoginActivity.this.login(str);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void sendCode() {
        ClearEditText clearEditText = (ClearEditText) _$_findCachedViewById(R$id.et_input_code);
        if (clearEditText != null) {
            clearEditText.post(new Runnable() { // from class: com.one.tomato.mvp.ui.login.view.LoginActivity$sendCode$1
                /* JADX WARN: Code restructure failed: missing block: B:3:0x001e, code lost:
                    r0 = r5.this$0.getMPresenter();
                 */
                @Override // java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final void run() {
                    int i;
                    LoginPresenter mPresenter;
                    ((ClearEditText) LoginActivity.this._$_findCachedViewById(R$id.et_input_code)).setText("");
                    LoginActivity.this.showWaitingDialog();
                    i = LoginActivity.this.loginType;
                    if (i == 3 && mPresenter != null) {
                        StringBuilder sb = new StringBuilder();
                        DomainServer domainServer = DomainServer.getInstance();
                        Intrinsics.checkExpressionValueIsNotNull(domainServer, "DomainServer.getInstance()");
                        sb.append(domainServer.getServerUrl());
                        sb.append("/app/login/loginSmsCode");
                        String sb2 = sb.toString();
                        EditText et_country_code = (EditText) LoginActivity.this._$_findCachedViewById(R$id.et_country_code);
                        Intrinsics.checkExpressionValueIsNotNull(et_country_code, "et_country_code");
                        String obj = et_country_code.getText().toString();
                        ClearEditText et_phone = (ClearEditText) LoginActivity.this._$_findCachedViewById(R$id.et_phone);
                        Intrinsics.checkExpressionValueIsNotNull(et_phone, "et_phone");
                        mPresenter.requestPhoneCode(sb2, obj, et_phone.getText().toString(), 5);
                    }
                    TimerTaskUtil.getInstance().onStart(60, new TimerTaskUtil.TimeTask() { // from class: com.one.tomato.mvp.ui.login.view.LoginActivity$sendCode$1.1
                        @Override // com.one.tomato.utils.TimerTaskUtil.TimeTask
                        public void position(int i2) {
                            TextView tv_get_code = (TextView) LoginActivity.this._$_findCachedViewById(R$id.tv_get_code);
                            Intrinsics.checkExpressionValueIsNotNull(tv_get_code, "tv_get_code");
                            tv_get_code.setText(String.valueOf(i2) + "s");
                            TextView tv_get_code2 = (TextView) LoginActivity.this._$_findCachedViewById(R$id.tv_get_code);
                            Intrinsics.checkExpressionValueIsNotNull(tv_get_code2, "tv_get_code");
                            tv_get_code2.setEnabled(false);
                        }

                        @Override // com.one.tomato.utils.TimerTaskUtil.TimeTask
                        public void stop() {
                            ((TextView) LoginActivity.this._$_findCachedViewById(R$id.tv_get_code)).setText(R.string.register_get_code);
                            TextView tv_get_code = (TextView) LoginActivity.this._$_findCachedViewById(R$id.tv_get_code);
                            Intrinsics.checkExpressionValueIsNotNull(tv_get_code, "tv_get_code");
                            tv_get_code.setEnabled(true);
                        }
                    });
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void login(final String str) {
        TextView textView = (TextView) _$_findCachedViewById(R$id.tv_login);
        if (textView != null) {
            textView.post(new Runnable() { // from class: com.one.tomato.mvp.ui.login.view.LoginActivity$login$1
                @Override // java.lang.Runnable
                public final void run() {
                    int i;
                    int i2;
                    int i3;
                    String str2;
                    CharSequence trim;
                    CharSequence trim2;
                    LoginPresenter mPresenter;
                    CharSequence trim3;
                    LoginActivity loginActivity = LoginActivity.this;
                    loginActivity.hideKeyBoard(loginActivity);
                    LoginActivity.this.showWaitingDialog();
                    LinkedHashMap linkedHashMap = new LinkedHashMap();
                    i = LoginActivity.this.loginType;
                    String str3 = "/app/memberInfo/userAccountLogin";
                    if (2 != i) {
                        i2 = LoginActivity.this.loginType;
                        if (3 != i2) {
                            i3 = LoginActivity.this.loginType;
                            if (i3 == 10) {
                                str2 = LoginActivity.this.loginLastAccount;
                                linkedHashMap.put(LogConstants.ACCOUNT, str2);
                                ClearEditText et_password = (ClearEditText) LoginActivity.this._$_findCachedViewById(R$id.et_password);
                                Intrinsics.checkExpressionValueIsNotNull(et_password, "et_password");
                                String obj = et_password.getText().toString();
                                if (obj == null) {
                                    throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                                }
                                trim = StringsKt__StringsKt.trim(obj);
                                linkedHashMap.put("password", trim.toString());
                            } else {
                                str3 = "";
                            }
                        } else {
                            ClearEditText et_phone = (ClearEditText) LoginActivity.this._$_findCachedViewById(R$id.et_phone);
                            Intrinsics.checkExpressionValueIsNotNull(et_phone, "et_phone");
                            String obj2 = et_phone.getText().toString();
                            int length = obj2.length() - 1;
                            int i4 = 0;
                            boolean z = false;
                            while (i4 <= length) {
                                boolean z2 = obj2.charAt(!z ? i4 : length) <= ' ';
                                if (!z) {
                                    if (!z2) {
                                        z = true;
                                    } else {
                                        i4++;
                                    }
                                } else if (!z2) {
                                    break;
                                } else {
                                    length--;
                                }
                            }
                            linkedHashMap.put("phoneNum", obj2.subSequence(i4, length + 1).toString());
                            EditText et_country_code = (EditText) LoginActivity.this._$_findCachedViewById(R$id.et_country_code);
                            Intrinsics.checkExpressionValueIsNotNull(et_country_code, "et_country_code");
                            String obj3 = et_country_code.getText().toString();
                            if (obj3 == null) {
                                throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                            }
                            trim2 = StringsKt__StringsKt.trim(obj3);
                            linkedHashMap.put("countryCode", trim2.toString());
                            ClearEditText et_input_code = (ClearEditText) LoginActivity.this._$_findCachedViewById(R$id.et_input_code);
                            Intrinsics.checkExpressionValueIsNotNull(et_input_code, "et_input_code");
                            linkedHashMap.put("smsCode", et_input_code.getText().toString());
                            str3 = "/app/login/userSmsLogin";
                        }
                    } else {
                        ClearEditText et_account = (ClearEditText) LoginActivity.this._$_findCachedViewById(R$id.et_account);
                        Intrinsics.checkExpressionValueIsNotNull(et_account, "et_account");
                        String obj4 = et_account.getText().toString();
                        int length2 = obj4.length() - 1;
                        int i5 = 0;
                        boolean z3 = false;
                        while (i5 <= length2) {
                            boolean z4 = obj4.charAt(!z3 ? i5 : length2) <= ' ';
                            if (!z3) {
                                if (!z4) {
                                    z3 = true;
                                } else {
                                    i5++;
                                }
                            } else if (!z4) {
                                break;
                            } else {
                                length2--;
                            }
                        }
                        linkedHashMap.put(LogConstants.ACCOUNT, obj4.subSequence(i5, length2 + 1).toString());
                        ClearEditText et_password2 = (ClearEditText) LoginActivity.this._$_findCachedViewById(R$id.et_password);
                        Intrinsics.checkExpressionValueIsNotNull(et_password2, "et_password");
                        String obj5 = et_password2.getText().toString();
                        if (obj5 == null) {
                            throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                        }
                        trim3 = StringsKt__StringsKt.trim(obj5);
                        linkedHashMap.put("password", trim3.toString());
                    }
                    String str4 = str;
                    if (str4 == null) {
                        str4 = "";
                    }
                    linkedHashMap.put("validate", str4);
                    mPresenter = LoginActivity.this.getMPresenter();
                    if (mPresenter != null) {
                        StringBuilder sb = new StringBuilder();
                        DomainServer domainServer = DomainServer.getInstance();
                        Intrinsics.checkExpressionValueIsNotNull(domainServer, "DomainServer.getInstance()");
                        sb.append(domainServer.getServerUrl());
                        sb.append(str3);
                        mPresenter.requestLogin(sb.toString(), linkedHashMap, true);
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showImageVer() {
        if (this.loginType == 3) {
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
            return;
        }
        login("");
    }

    @Override // com.one.tomato.mvp.p080ui.login.impl.ILoginContact$ILoginView
    public void handlerLogin(LoginInfo loginInfo) {
        if (3 == this.loginType) {
            PreferencesUtil preferencesUtil = PreferencesUtil.getInstance();
            TextView tv_country_name = (TextView) _$_findCachedViewById(R$id.tv_country_name);
            Intrinsics.checkExpressionValueIsNotNull(tv_country_name, "tv_country_name");
            String obj = tv_country_name.getText().toString();
            int length = obj.length() - 1;
            int i = 0;
            boolean z = false;
            while (i <= length) {
                boolean z2 = obj.charAt(!z ? i : length) <= ' ';
                if (!z) {
                    if (!z2) {
                        z = true;
                    } else {
                        i++;
                    }
                } else if (!z2) {
                    break;
                } else {
                    length--;
                }
            }
            preferencesUtil.putString("country_name", obj.subSequence(i, length + 1).toString());
            PreferencesUtil preferencesUtil2 = PreferencesUtil.getInstance();
            EditText et_country_code = (EditText) _$_findCachedViewById(R$id.et_country_code);
            Intrinsics.checkExpressionValueIsNotNull(et_country_code, "et_country_code");
            String obj2 = et_country_code.getText().toString();
            int length2 = obj2.length() - 1;
            int i2 = 0;
            boolean z3 = false;
            while (i2 <= length2) {
                boolean z4 = obj2.charAt(!z3 ? i2 : length2) <= ' ';
                if (!z3) {
                    if (!z4) {
                        z3 = true;
                    } else {
                        i2++;
                    }
                } else if (!z4) {
                    break;
                } else {
                    length2--;
                }
            }
            preferencesUtil2.putString("country_code", obj2.subSequence(i2, length2 + 1).toString());
            PreferencesUtil preferencesUtil3 = PreferencesUtil.getInstance();
            ClearEditText et_phone = (ClearEditText) _$_findCachedViewById(R$id.et_phone);
            Intrinsics.checkExpressionValueIsNotNull(et_phone, "et_phone");
            String obj3 = et_phone.getText().toString();
            int length3 = obj3.length() - 1;
            int i3 = 0;
            boolean z5 = false;
            while (i3 <= length3) {
                boolean z6 = obj3.charAt(!z5 ? i3 : length3) <= ' ';
                if (!z5) {
                    if (!z6) {
                        z5 = true;
                    } else {
                        i3++;
                    }
                } else if (!z6) {
                    break;
                } else {
                    length3--;
                }
            }
            preferencesUtil3.putString("login_user_phone", obj3.subSequence(i3, length3 + 1).toString());
            PreferencesUtil.getInstance().putString("login_user_account", null);
        } else {
            ClearEditText et_account = (ClearEditText) _$_findCachedViewById(R$id.et_account);
            Intrinsics.checkExpressionValueIsNotNull(et_account, "et_account");
            String obj4 = et_account.getText().toString();
            int length4 = obj4.length() - 1;
            int i4 = 0;
            boolean z7 = false;
            while (i4 <= length4) {
                boolean z8 = obj4.charAt(!z7 ? i4 : length4) <= ' ';
                if (!z7) {
                    if (!z8) {
                        z7 = true;
                    } else {
                        i4++;
                    }
                } else if (!z8) {
                    break;
                } else {
                    length4--;
                }
            }
            String obj5 = obj4.subSequence(i4, length4 + 1).toString();
            if (TextUtils.isEmpty(obj5)) {
                obj5 = !TextUtils.isEmpty(this.loginLastAccount) ? this.loginLastAccount : "";
            }
            PreferencesUtil.getInstance().putString("login_user_account", obj5);
            PreferencesUtil.getInstance().putString("country_name", null);
            PreferencesUtil.getInstance().putString("country_code", null);
            PreferencesUtil.getInstance().putString("login_user_phone", null);
        }
        PreferencesUtil.getInstance().putInt("login_type", this.loginType);
    }

    @Override // com.one.tomato.mvp.p080ui.login.impl.ILoginContact$ILoginView
    public void handlerSafetyVerify(LoginInfo loginInfo) {
        dismissDialog();
        String str = null;
        Integer valueOf = loginInfo != null ? Integer.valueOf(loginInfo.getType()) : null;
        if (valueOf != null && valueOf.intValue() == 1) {
            ToastUtil.showCenterToast(AppUtil.getString(R.string.login_account_excption));
            Intent intent = new Intent(this, AccoutnAprealActivity.class);
            Context mContext = getMContext();
            if (mContext == null) {
                return;
            }
            mContext.startActivity(intent);
        } else if (valueOf != null && valueOf.intValue() == 2) {
            LoginVerifyAct.Companion companion = LoginVerifyAct.Companion;
            Context mContext2 = getMContext();
            if (mContext2 == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            String phone = loginInfo.getPhone();
            Intrinsics.checkExpressionValueIsNotNull(phone, "loginInfo.phone");
            if (loginInfo != null) {
                str = loginInfo.getCountryCode();
            }
            companion.startAct(7, mContext2, phone, str, "");
        } else if (valueOf == null || valueOf.intValue() != 3) {
        } else {
            LoginVerifyAct.Companion companion2 = LoginVerifyAct.Companion;
            Context mContext3 = getMContext();
            if (mContext3 == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            String email = loginInfo.getEmail();
            Intrinsics.checkExpressionValueIsNotNull(email, "loginInfo.email");
            companion2.startAct(6, mContext3, email, "", "");
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity, com.one.tomato.net.LoginResponseObserver
    public void onLoginSuccess() {
        super.onLoginSuccess();
        dismissDialog();
        finish();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1) {
            if (i == 1) {
                finish();
            } else if (i != 111) {
            } else {
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
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        TimerTaskUtil.getInstance().onStop();
        CaptchaUtil captchaUtil = this.captchaUtil;
        if (captchaUtil != null) {
            captchaUtil.onDestroy();
        }
    }
}
