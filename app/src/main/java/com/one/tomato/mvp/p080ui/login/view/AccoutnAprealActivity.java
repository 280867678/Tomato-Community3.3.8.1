package com.one.tomato.mvp.p080ui.login.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.p002v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.entity.ApprealBean;
import com.one.tomato.entity.HtmlConfig;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.login.view.AprealResultActivity;
import com.one.tomato.mvp.p080ui.login.view.CountryCodeActivity;
import com.one.tomato.p085ui.webview.HtmlShowActivity;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DeviceInfoUtil;
import com.one.tomato.utils.RxUtils;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.one.tomato.widget.ClearEditText;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.HashMap;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: AccoutnAprealActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.login.view.AccoutnAprealActivity */
/* loaded from: classes3.dex */
public final class AccoutnAprealActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    private HashMap _$_findViewCache;
    private int aprealType = 1;
    private int typeChekOrLogin = 4;
    private String currentApprealType = "";

    static {
        new Companion(null);
    }

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
        return R.layout.activity_accoutn_apreal;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    /* compiled from: AccoutnAprealActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.login.view.AccoutnAprealActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        ((TextView) _$_findCachedViewById(R$id.tv_get_code)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.AccoutnAprealActivity$initView$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                if (!AppUtil.isFastClick(((TextView) AccoutnAprealActivity.this._$_findCachedViewById(R$id.tv_get_code)).getId(), 3000)) {
                    AccoutnAprealActivity.this.getVerify();
                } else {
                    ToastUtil.showCenterToast((int) R.string.post_fast_click_tip);
                }
            }
        });
        ImageView imageView = (ImageView) _$_findCachedViewById(R$id.iv_back);
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.AccoutnAprealActivity$initView$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AccoutnAprealActivity.this.onBackPressed();
                }
            });
        }
        TextView textView = (TextView) _$_findCachedViewById(R$id.tv_apreal_type_phone);
        if (textView != null) {
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.AccoutnAprealActivity$initView$3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AccoutnAprealActivity.this.aprealType = 1;
                    AccoutnAprealActivity.this.changeUI();
                }
            });
        }
        TextView textView2 = (TextView) _$_findCachedViewById(R$id.tv_apreal_type_email);
        if (textView2 != null) {
            textView2.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.AccoutnAprealActivity$initView$4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AccoutnAprealActivity.this.aprealType = 2;
                    AccoutnAprealActivity.this.changeUI();
                }
            });
        }
        TextView textView3 = (TextView) _$_findCachedViewById(R$id.tv_apreal_type_account);
        if (textView3 != null) {
            textView3.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.AccoutnAprealActivity$initView$5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AccoutnAprealActivity.this.aprealType = 3;
                    AccoutnAprealActivity.this.changeUI();
                }
            });
        }
        TextView textView4 = (TextView) _$_findCachedViewById(R$id.tv_ok);
        if (textView4 != null) {
            textView4.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.AccoutnAprealActivity$initView$6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AccoutnAprealActivity.this.clickOk();
                }
            });
        }
        ((TextView) _$_findCachedViewById(R$id.tv_country_name)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.AccoutnAprealActivity$initView$7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                CountryCodeActivity.Companion companion = CountryCodeActivity.Companion;
                mContext = AccoutnAprealActivity.this.getMContext();
                if (mContext != null) {
                    companion.startActivity(mContext, 111);
                } else {
                    Intrinsics.throwNpe();
                    throw null;
                }
            }
        });
        ((ImageView) _$_findCachedViewById(R$id.iv_arrow_right)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.AccoutnAprealActivity$initView$8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                CountryCodeActivity.Companion companion = CountryCodeActivity.Companion;
                mContext = AccoutnAprealActivity.this.getMContext();
                if (mContext != null) {
                    companion.startActivity(mContext, 111);
                } else {
                    Intrinsics.throwNpe();
                    throw null;
                }
            }
        });
        ((ImageView) _$_findCachedViewById(R$id.iv_login_phone)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.AccoutnAprealActivity$initView$9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                CountryCodeActivity.Companion companion = CountryCodeActivity.Companion;
                mContext = AccoutnAprealActivity.this.getMContext();
                if (mContext != null) {
                    companion.startActivity(mContext, 111);
                } else {
                    Intrinsics.throwNpe();
                    throw null;
                }
            }
        });
        changeUI();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void changeUI() {
        int i = this.typeChekOrLogin;
        if (i == 4) {
            RelativeLayout cl_input_verify = (RelativeLayout) _$_findCachedViewById(R$id.cl_input_verify);
            Intrinsics.checkExpressionValueIsNotNull(cl_input_verify, "cl_input_verify");
            cl_input_verify.setVisibility(0);
            ConstraintLayout cl_apreal_password = (ConstraintLayout) _$_findCachedViewById(R$id.cl_apreal_password);
            Intrinsics.checkExpressionValueIsNotNull(cl_apreal_password, "cl_apreal_password");
            cl_apreal_password.setVisibility(8);
        } else if (i == 5) {
            RelativeLayout cl_input_verify2 = (RelativeLayout) _$_findCachedViewById(R$id.cl_input_verify);
            Intrinsics.checkExpressionValueIsNotNull(cl_input_verify2, "cl_input_verify");
            cl_input_verify2.setVisibility(8);
            ConstraintLayout cl_apreal_password2 = (ConstraintLayout) _$_findCachedViewById(R$id.cl_apreal_password);
            Intrinsics.checkExpressionValueIsNotNull(cl_apreal_password2, "cl_apreal_password");
            cl_apreal_password2.setVisibility(0);
        }
        int i2 = this.aprealType;
        if (i2 == 1) {
            TextView textView = (TextView) _$_findCachedViewById(R$id.tv_apreal_type_phone);
            Context mContext = getMContext();
            if (mContext == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            textView.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
            TextView textView2 = (TextView) _$_findCachedViewById(R$id.tv_apreal_type_email);
            Context mContext2 = getMContext();
            if (mContext2 == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            textView2.setTextColor(ContextCompat.getColor(mContext2, R.color.text_dark));
            TextView textView3 = (TextView) _$_findCachedViewById(R$id.tv_apreal_type_account);
            Context mContext3 = getMContext();
            if (mContext3 == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            textView3.setTextColor(ContextCompat.getColor(mContext3, R.color.text_dark));
            View cl_login_phone = _$_findCachedViewById(R$id.cl_login_phone);
            Intrinsics.checkExpressionValueIsNotNull(cl_login_phone, "cl_login_phone");
            cl_login_phone.setVisibility(0);
            RelativeLayout relate_register_email = (RelativeLayout) _$_findCachedViewById(R$id.relate_register_email);
            Intrinsics.checkExpressionValueIsNotNull(relate_register_email, "relate_register_email");
            relate_register_email.setVisibility(8);
            ConstraintLayout con_register_phone = (ConstraintLayout) _$_findCachedViewById(R$id.con_register_phone);
            Intrinsics.checkExpressionValueIsNotNull(con_register_phone, "con_register_phone");
            con_register_phone.setVisibility(0);
            ConstraintLayout cl_login_account = (ConstraintLayout) _$_findCachedViewById(R$id.cl_login_account);
            Intrinsics.checkExpressionValueIsNotNull(cl_login_account, "cl_login_account");
            cl_login_account.setVisibility(8);
        } else if (i2 != 2) {
            if (i2 != 3) {
                return;
            }
            TextView textView4 = (TextView) _$_findCachedViewById(R$id.tv_apreal_type_phone);
            Context mContext4 = getMContext();
            if (mContext4 == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            textView4.setTextColor(ContextCompat.getColor(mContext4, R.color.text_dark));
            TextView textView5 = (TextView) _$_findCachedViewById(R$id.tv_apreal_type_email);
            Context mContext5 = getMContext();
            if (mContext5 == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            textView5.setTextColor(ContextCompat.getColor(mContext5, R.color.text_dark));
            TextView textView6 = (TextView) _$_findCachedViewById(R$id.tv_apreal_type_account);
            Context mContext6 = getMContext();
            if (mContext6 == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            textView6.setTextColor(ContextCompat.getColor(mContext6, R.color.colorAccent));
            View cl_login_phone2 = _$_findCachedViewById(R$id.cl_login_phone);
            Intrinsics.checkExpressionValueIsNotNull(cl_login_phone2, "cl_login_phone");
            cl_login_phone2.setVisibility(8);
            ConstraintLayout cl_login_account2 = (ConstraintLayout) _$_findCachedViewById(R$id.cl_login_account);
            Intrinsics.checkExpressionValueIsNotNull(cl_login_account2, "cl_login_account");
            cl_login_account2.setVisibility(0);
        } else {
            TextView textView7 = (TextView) _$_findCachedViewById(R$id.tv_apreal_type_phone);
            Context mContext7 = getMContext();
            if (mContext7 == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            textView7.setTextColor(ContextCompat.getColor(mContext7, R.color.text_dark));
            TextView textView8 = (TextView) _$_findCachedViewById(R$id.tv_apreal_type_email);
            Context mContext8 = getMContext();
            if (mContext8 == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            textView8.setTextColor(ContextCompat.getColor(mContext8, R.color.colorAccent));
            TextView textView9 = (TextView) _$_findCachedViewById(R$id.tv_apreal_type_account);
            Context mContext9 = getMContext();
            if (mContext9 == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            textView9.setTextColor(ContextCompat.getColor(mContext9, R.color.text_dark));
            View cl_login_phone3 = _$_findCachedViewById(R$id.cl_login_phone);
            Intrinsics.checkExpressionValueIsNotNull(cl_login_phone3, "cl_login_phone");
            cl_login_phone3.setVisibility(0);
            RelativeLayout relate_register_email2 = (RelativeLayout) _$_findCachedViewById(R$id.relate_register_email);
            Intrinsics.checkExpressionValueIsNotNull(relate_register_email2, "relate_register_email");
            relate_register_email2.setVisibility(0);
            ConstraintLayout con_register_phone2 = (ConstraintLayout) _$_findCachedViewById(R$id.con_register_phone);
            Intrinsics.checkExpressionValueIsNotNull(con_register_phone2, "con_register_phone");
            con_register_phone2.setVisibility(8);
            ConstraintLayout cl_login_account3 = (ConstraintLayout) _$_findCachedViewById(R$id.cl_login_account);
            Intrinsics.checkExpressionValueIsNotNull(cl_login_account3, "cl_login_account");
            cl_login_account3.setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0038, code lost:
        if (r0 != null) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0068, code lost:
        if (r2 != null) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x009f, code lost:
        if (r1 != null) goto L50;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void clickOk() {
        String str;
        Editable text;
        String obj;
        CharSequence trim;
        String str2;
        String str3;
        Editable text2;
        String obj2;
        CharSequence trim2;
        EditText editText;
        Editable text3;
        String obj3;
        CharSequence trim3;
        int i = this.typeChekOrLogin;
        if (i == 4) {
            checkInputIsNull();
            String str4 = this.currentApprealType;
            if (str4 == null) {
                str4 = "";
            }
            ClearEditText clearEditText = (ClearEditText) _$_findCachedViewById(R$id.et_input_code);
            if (clearEditText != null && (text = clearEditText.getText()) != null && (obj = text.toString()) != null) {
                if (obj == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                }
                trim = StringsKt__StringsKt.trim(obj);
                str = trim.toString();
            }
            str = "";
            checkAccount(str4, str);
        } else if (i != 5) {
        } else {
            checkInputIsNull();
            if (this.aprealType == 1 && (editText = (EditText) _$_findCachedViewById(R$id.et_country_code)) != null && (text3 = editText.getText()) != null && (obj3 = text3.toString()) != null) {
                if (obj3 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                }
                trim3 = StringsKt__StringsKt.trim(obj3);
                str2 = trim3.toString();
            }
            str2 = "";
            String str5 = this.currentApprealType;
            if (str5 == null) {
                str5 = "";
            }
            ClearEditText clearEditText2 = (ClearEditText) _$_findCachedViewById(R$id.et_password);
            if (clearEditText2 != null && (text2 = clearEditText2.getText()) != null && (obj2 = text2.toString()) != null) {
                if (obj2 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                }
                trim2 = StringsKt__StringsKt.trim(obj2);
                str3 = trim2.toString();
            }
            str3 = "";
            checkPassWrod(str5, str3, str2);
        }
    }

    private final void checkInputIsNull() {
        String str;
        String str2;
        String str3;
        Editable text;
        String obj;
        CharSequence trim;
        Editable text2;
        String obj2;
        CharSequence trim2;
        Editable text3;
        String obj3;
        CharSequence trim3;
        Editable text4;
        String obj4;
        CharSequence trim4;
        Editable text5;
        String obj5;
        CharSequence trim5;
        String str4;
        String str5;
        Editable text6;
        String obj6;
        CharSequence trim6;
        Editable text7;
        String obj7;
        CharSequence trim7;
        String str6;
        String str7;
        Editable text8;
        String obj8;
        CharSequence trim8;
        Editable text9;
        String obj9;
        CharSequence trim9;
        int i = this.aprealType;
        String str8 = null;
        if (i == 1) {
            ClearEditText clearEditText = (ClearEditText) _$_findCachedViewById(R$id.et_phone);
            if (clearEditText == null || (text3 = clearEditText.getText()) == null || (obj3 = text3.toString()) == null) {
                str = null;
            } else if (obj3 == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
            } else {
                trim3 = StringsKt__StringsKt.trim(obj3);
                str = trim3.toString();
            }
            if (TextUtils.isEmpty(str)) {
                ToastUtil.showCenterToast(AppUtil.getString(R.string.account_bind_phone_hint));
                return;
            }
            EditText editText = (EditText) _$_findCachedViewById(R$id.et_country_code);
            if (editText == null || (text2 = editText.getText()) == null || (obj2 = text2.toString()) == null) {
                str2 = null;
            } else if (obj2 == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
            } else {
                trim2 = StringsKt__StringsKt.trim(obj2);
                str2 = trim2.toString();
            }
            if (TextUtils.isEmpty(str2)) {
                ToastUtil.showCenterToast(AppUtil.getString(R.string.country_code_title));
                return;
            }
            ClearEditText clearEditText2 = (ClearEditText) _$_findCachedViewById(R$id.et_phone);
            if (clearEditText2 == null || (text = clearEditText2.getText()) == null || (obj = text.toString()) == null) {
                str3 = null;
            } else if (obj == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
            } else {
                trim = StringsKt__StringsKt.trim(obj);
                str3 = trim.toString();
            }
            this.currentApprealType = str3;
        } else if (i == 2) {
            ClearEditText clearEditText3 = (ClearEditText) _$_findCachedViewById(R$id.et_input_email);
            if (clearEditText3 == null || (text7 = clearEditText3.getText()) == null || (obj7 = text7.toString()) == null) {
                str4 = null;
            } else if (obj7 == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
            } else {
                trim7 = StringsKt__StringsKt.trim(obj7);
                str4 = trim7.toString();
            }
            if (TextUtils.isEmpty(str4)) {
                ToastUtil.showCenterToast(AppUtil.getString(R.string.register_email));
                return;
            }
            ClearEditText clearEditText4 = (ClearEditText) _$_findCachedViewById(R$id.et_input_email);
            if (clearEditText4 == null || (text6 = clearEditText4.getText()) == null || (obj6 = text6.toString()) == null) {
                str5 = null;
            } else if (obj6 == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
            } else {
                trim6 = StringsKt__StringsKt.trim(obj6);
                str5 = trim6.toString();
            }
            this.currentApprealType = str5;
        } else if (i == 3) {
            ClearEditText clearEditText5 = (ClearEditText) _$_findCachedViewById(R$id.et_account);
            if (clearEditText5 == null || (text9 = clearEditText5.getText()) == null || (obj9 = text9.toString()) == null) {
                str6 = null;
            } else if (obj9 == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
            } else {
                trim9 = StringsKt__StringsKt.trim(obj9);
                str6 = trim9.toString();
            }
            if (TextUtils.isEmpty(str6)) {
                ToastUtil.showCenterToast(AppUtil.getString(R.string.login_input_account));
                return;
            }
            ClearEditText clearEditText6 = (ClearEditText) _$_findCachedViewById(R$id.et_account);
            if (clearEditText6 == null || (text8 = clearEditText6.getText()) == null || (obj8 = text8.toString()) == null) {
                str7 = null;
            } else if (obj8 == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
            } else {
                trim8 = StringsKt__StringsKt.trim(obj8);
                str7 = trim8.toString();
            }
            this.currentApprealType = str7;
        }
        if (this.typeChekOrLogin == 5) {
            ClearEditText clearEditText7 = (ClearEditText) _$_findCachedViewById(R$id.et_password);
            if (clearEditText7 != null && (text5 = clearEditText7.getText()) != null && (obj5 = text5.toString()) != null) {
                if (obj5 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                }
                trim5 = StringsKt__StringsKt.trim(obj5);
                str8 = trim5.toString();
            }
            if (!TextUtils.isEmpty(str8)) {
                return;
            }
            ToastUtil.showCenterToast(AppUtil.getString(R.string.login_input_password));
            return;
        }
        ClearEditText clearEditText8 = (ClearEditText) _$_findCachedViewById(R$id.et_input_code);
        if (clearEditText8 != null && (text4 = clearEditText8.getText()) != null && (obj4 = text4.toString()) != null) {
            if (obj4 == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
            }
            trim4 = StringsKt__StringsKt.trim(obj4);
            str8 = trim4.toString();
        }
        if (!TextUtils.isEmpty(str8)) {
            return;
        }
        ToastUtil.showCenterToast(AppUtil.getString(R.string.register_input_code));
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        getVerify();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void checkAccountSucess() {
        this.typeChekOrLogin = 5;
        changeUI();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void checkPassWordSucess(ApprealBean apprealBean) {
        if (apprealBean != null) {
            if (apprealBean.getStatus() == 0) {
                HtmlConfig htmlConfig = new HtmlConfig();
                htmlConfig.setTitle(AppUtil.getString(R.string.login_apreal_account));
                StringBuilder sb = new StringBuilder();
                DomainServer domainServer = DomainServer.getInstance();
                Intrinsics.checkExpressionValueIsNotNull(domainServer, "DomainServer.getInstance()");
                sb.append(domainServer.getH5Url());
                sb.append("/h5/AccountAppeal");
                sb.append("?isAndroid=1&account=");
                sb.append(this.currentApprealType);
                sb.append("&memberId=");
                sb.append(apprealBean.getMemberId());
                htmlConfig.setUrl(sb.toString());
                HtmlShowActivity.startActivity(getMContext(), htmlConfig);
                return;
            }
            AprealResultActivity.Companion companion = AprealResultActivity.Companion;
            Context mContext = getMContext();
            if (mContext != null) {
                companion.startAct(mContext, apprealBean, this.currentApprealType);
            } else {
                Intrinsics.throwNpe();
                throw null;
            }
        }
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

    /* JADX INFO: Access modifiers changed from: private */
    public final void getVerify() {
        ImageView imageView = (ImageView) _$_findCachedViewById(R$id.iv_image);
        Context mContext = getMContext();
        if (mContext == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.default_imag_white));
        DomainServer domainServer = DomainServer.getInstance();
        Intrinsics.checkExpressionValueIsNotNull(domainServer, "DomainServer.getInstance()");
        ImageLoaderUtil.loadVerifySecImage(getMContext(), (ImageView) _$_findCachedViewById(R$id.iv_image), domainServer.getServerUrl(), new ImageBean("/app/verify/_s3/loginCaptcha.jpg?deviceNo=" + DeviceInfoUtil.getUniqueDeviceID() + "&time=" + System.currentTimeMillis()), ImageLoaderUtil.getCenterInsideImageOptionWhite((ImageView) _$_findCachedViewById(R$id.iv_image)));
    }

    private final void checkAccount(String str, String str2) {
        ApiImplService.Companion.getApiImplService().apprealCheckAccount(str, str2).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler((RxAppCompatActivity) this)).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.login.view.AccoutnAprealActivity$checkAccount$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                AccoutnAprealActivity.this.showWaitingDialog();
            }
        }).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.mvp.ui.login.view.AccoutnAprealActivity$checkAccount$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj) {
                AccoutnAprealActivity.this.hideWaitingDialog();
                AccoutnAprealActivity.this.checkAccountSucess();
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                AccoutnAprealActivity.this.hideWaitingDialog();
            }
        });
    }

    private final void checkPassWrod(String str, String str2, String str3) {
        ApiImplService.Companion.getApiImplService().apprealCheckPassWord(str, str2, str3).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler((RxAppCompatActivity) this)).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.login.view.AccoutnAprealActivity$checkPassWrod$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                AccoutnAprealActivity.this.showWaitingDialog();
            }
        }).subscribe(new ApiDisposableObserver<ApprealBean>() { // from class: com.one.tomato.mvp.ui.login.view.AccoutnAprealActivity$checkPassWrod$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ApprealBean apprealBean) {
                AccoutnAprealActivity.this.hideWaitingDialog();
                AccoutnAprealActivity.this.checkPassWordSucess(apprealBean);
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                AccoutnAprealActivity.this.hideWaitingDialog();
            }
        });
    }
}
