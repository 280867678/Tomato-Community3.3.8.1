package com.one.tomato.mvp.p080ui.promotion.p081ui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.dialog.CustomAlertDialog;
import com.one.tomato.dialog.PaySafeCenterDialog;
import com.one.tomato.dialog.TradePwdEntryDialog;
import com.one.tomato.entity.BalanceBean;
import com.one.tomato.entity.BaseResponse;
import com.one.tomato.entity.PromotionAccount;
import com.one.tomato.entity.PromotionBlockUrl;
import com.one.tomato.entity.PromotionOrderRecord;
import com.one.tomato.entity.PromotionWalletBalance;
import com.one.tomato.entity.PromotionWithdrawConfig;
import com.one.tomato.entity.TradePwdBean;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.CallbackUtil;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.login.view.CountryCodeActivity;
import com.one.tomato.mvp.p080ui.promotion.dialog.WithdrawReceiveDialog;
import com.one.tomato.mvp.p080ui.promotion.p081ui.WithdrawStatusActivity;
import com.one.tomato.p085ui.income.VeritifyIdentityActivity;
import com.one.tomato.thirdpart.promotion.PromotionUtil;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.RxUtils;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.encrypt.MD5Util;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.json.JSONObject;

/* compiled from: WithdrawActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.promotion.ui.WithdrawActivity */
/* loaded from: classes3.dex */
public final class WithdrawActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    public static final Companion Companion = new Companion(null);
    private final int RESPONSE_CODE_PWD_ERROR = 50001;
    private final int RESPONSE_CODE_PWD_OUT_ERROR = 50002;
    private HashMap _$_findViewCache;
    private boolean isHasSetTransactionPwd;
    private PromotionBlockUrl promotionBlockUrl;
    private PromotionWalletBalance promotionWalletBalance;
    private PromotionWithdrawConfig promotionWithdrawConfig;
    private TradePwdEntryDialog tradePwdEntryDialog;

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
        return R.layout.activity_withdraw;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    public static final /* synthetic */ TradePwdEntryDialog access$getTradePwdEntryDialog$p(WithdrawActivity withdrawActivity) {
        TradePwdEntryDialog tradePwdEntryDialog = withdrawActivity.tradePwdEntryDialog;
        if (tradePwdEntryDialog != null) {
            return tradePwdEntryDialog;
        }
        Intrinsics.throwUninitializedPropertyAccessException("tradePwdEntryDialog");
        throw null;
    }

    /* compiled from: WithdrawActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.promotion.ui.WithdrawActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startActivity(Context context, PromotionWalletBalance promotionWalletBalance, PromotionWithdrawConfig promotionWithdrawConfig, int i) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            Intent intent = new Intent();
            intent.setClass(context, WithdrawActivity.class);
            intent.putExtra("promotionWalletBalance", promotionWalletBalance);
            intent.putExtra("promotionWithdrawConfig", promotionWithdrawConfig);
            ((Activity) context).startActivityForResult(intent, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        initTitleBar();
        TextView titleTV = getTitleTV();
        if (titleTV != null) {
            titleTV.setText(R.string.withdraw_title);
        }
        TextView rightTV = getRightTV();
        if (rightTV != null) {
            rightTV.setVisibility(0);
        }
        TextView rightTV2 = getRightTV();
        if (rightTV2 != null) {
            rightTV2.setTextColor(getResources().getColor(R.color.text_light));
        }
        TextView rightTV3 = getRightTV();
        if (rightTV3 != null) {
            rightTV3.setText(R.string.withdraw_sub_title);
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        if (getIntent().getSerializableExtra("promotionWalletBalance") != null) {
            Serializable serializableExtra = getIntent().getSerializableExtra("promotionWalletBalance");
            if (serializableExtra == null) {
                throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.entity.PromotionWalletBalance");
            }
            this.promotionWalletBalance = (PromotionWalletBalance) serializableExtra;
        }
        if (getIntent().getSerializableExtra("promotionWithdrawConfig") != null) {
            Serializable serializableExtra2 = getIntent().getSerializableExtra("promotionWithdrawConfig");
            if (serializableExtra2 == null) {
                throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.entity.PromotionWithdrawConfig");
            }
            this.promotionWithdrawConfig = (PromotionWithdrawConfig) serializableExtra2;
        }
        this.tradePwdEntryDialog = new TradePwdEntryDialog(this);
        addListener();
        initConfigUI();
        request();
    }

    private final void addListener() {
        TextView rightTV = getRightTV();
        if (rightTV != null) {
            rightTV.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.promotion.ui.WithdrawActivity$addListener$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    WithdrawActivity.this.showSafeCenterDialog();
                }
            });
        }
        ((TextView) _$_findCachedViewById(R$id.tv_withdraw_ecny_explain)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.promotion.ui.WithdrawActivity$addListener$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PromotionBlockUrl promotionBlockUrl;
                promotionBlockUrl = WithdrawActivity.this.promotionBlockUrl;
                AppUtil.startBrowseView(promotionBlockUrl != null ? promotionBlockUrl.blockChainWhiteUrl : null);
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_withdraw_xiaobai_explain)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.promotion.ui.WithdrawActivity$addListener$3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                new WithdrawReceiveDialog(WithdrawActivity.this);
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_country_code)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.promotion.ui.WithdrawActivity$addListener$4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CountryCodeActivity.Companion.startActivity(WithdrawActivity.this, 11);
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_withdraw_money_all)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.promotion.ui.WithdrawActivity$addListener$5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PromotionWalletBalance promotionWalletBalance;
                PromotionWalletBalance promotionWalletBalance2;
                promotionWalletBalance = WithdrawActivity.this.promotionWalletBalance;
                if (promotionWalletBalance == null || Double.compare(promotionWalletBalance.balance, 0) != 0) {
                    promotionWalletBalance2 = WithdrawActivity.this.promotionWalletBalance;
                    ((EditText) WithdrawActivity.this._$_findCachedViewById(R$id.et_withdraw_money_min)).setText(FormatUtil.formatTwo(promotionWalletBalance2 != null ? String.valueOf(promotionWalletBalance2.balance) : null));
                }
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_withdraw_tip_xiaobai)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.promotion.ui.WithdrawActivity$addListener$6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PromotionUtil.openXiaobai(WithdrawActivity.this);
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_withdraw_confirm)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.promotion.ui.WithdrawActivity$addListener$7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                WithdrawActivity.this.confirmWithdraw();
            }
        });
        TradePwdEntryDialog tradePwdEntryDialog = this.tradePwdEntryDialog;
        if (tradePwdEntryDialog == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tradePwdEntryDialog");
            throw null;
        }
        tradePwdEntryDialog.setOnTradePwdListener(new TradePwdEntryDialog.OnTradePwdListener() { // from class: com.one.tomato.mvp.ui.promotion.ui.WithdrawActivity$addListener$8
            @Override // com.one.tomato.dialog.TradePwdEntryDialog.OnTradePwdListener
            public void onTextChanged(String changeText) {
                Intrinsics.checkParameterIsNotNull(changeText, "changeText");
            }

            @Override // com.one.tomato.dialog.TradePwdEntryDialog.OnTradePwdListener
            public void onCompleted(String tradePwd) {
                Intrinsics.checkParameterIsNotNull(tradePwd, "tradePwd");
                WithdrawActivity.access$getTradePwdEntryDialog$p(WithdrawActivity.this).dismiss();
                WithdrawActivity.this.verifyTradePwd(tradePwd);
            }
        });
        ((EditText) _$_findCachedViewById(R$id.et_withdraw_money_min)).addTextChangedListener(new TextWatcher() { // from class: com.one.tomato.mvp.ui.promotion.ui.WithdrawActivity$addListener$9
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                PromotionWalletBalance promotionWalletBalance;
                Boolean bool;
                CharSequence trim;
                promotionWalletBalance = WithdrawActivity.this.promotionWalletBalance;
                boolean z = false;
                if (promotionWalletBalance == null || Double.compare(promotionWalletBalance.balance, 0) != 0) {
                    if (editable != null) {
                        if (editable.length() > 0) {
                            z = true;
                        }
                        bool = Boolean.valueOf(z);
                    } else {
                        bool = null;
                    }
                    if (bool == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    } else if (!bool.booleanValue()) {
                        WithdrawActivity.this.initConfigUI();
                    } else {
                        WithdrawActivity withdrawActivity = WithdrawActivity.this;
                        String obj = editable.toString();
                        if (obj == null) {
                            throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                        }
                        trim = StringsKt__StringsKt.trim(obj);
                        withdrawActivity.withdrawUI(trim.toString());
                    }
                }
            }
        });
    }

    private final void request() {
        ApiImplService.Companion.getApiImplService().requestIncomeAccountBalance(DBUtil.getMemberId(), 1).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler((RxAppCompatActivity) this)).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.mvp.ui.promotion.ui.WithdrawActivity$request$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                WithdrawActivity.this.showWaitingDialog();
            }
        }).subscribe(new ApiDisposableObserver<BalanceBean>() { // from class: com.one.tomato.mvp.ui.promotion.ui.WithdrawActivity$request$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(BalanceBean balanceBean) {
                Intrinsics.checkParameterIsNotNull(balanceBean, "balanceBean");
                WithdrawActivity.this.hideWaitingDialog();
                WithdrawActivity.this.isHasSetTransactionPwd = balanceBean.isHasSetTransactionPwd();
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable e) {
                Intrinsics.checkParameterIsNotNull(e, "e");
                WithdrawActivity.this.hideWaitingDialog();
            }
        });
        if (this.promotionWalletBalance == null) {
            PromotionUtil.requestToken(new WithdrawActivity$request$3(this));
        }
        if (this.promotionWithdrawConfig == null) {
            PromotionUtil.requestToken(new WithdrawActivity$request$4(this));
        }
        PromotionUtil.requestToken(new WithdrawActivity$request$5(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initConfigUI() {
        String str;
        String str2;
        TextView textView = (TextView) _$_findCachedViewById(R$id.tv_withdraw_money_avaible);
        Object[] objArr = new Object[1];
        PromotionWalletBalance promotionWalletBalance = this.promotionWalletBalance;
        if (promotionWalletBalance == null || (str = String.valueOf(promotionWalletBalance.balance)) == null) {
            str = "0";
        }
        objArr[0] = FormatUtil.formatTwo(str);
        textView.setText(AppUtil.getString(R.string.withdraw_renmingbi_num, objArr));
        EditText editText = (EditText) _$_findCachedViewById(R$id.et_withdraw_money_min);
        Object[] objArr2 = new Object[1];
        PromotionWithdrawConfig promotionWithdrawConfig = this.promotionWithdrawConfig;
        if (promotionWithdrawConfig == null || (str2 = String.valueOf(promotionWithdrawConfig.minExchangeMoney)) == null) {
            str2 = "0";
        }
        objArr2[0] = FormatUtil.formatTwo(str2);
        editText.setHint(AppUtil.getString(R.string.withdraw_money_min, objArr2));
        ((TextView) _$_findCachedViewById(R$id.tv_withdraw_procedure_money)).setText(AppUtil.getString(R.string.withdraw_procedure_money, "0"));
        ((TextView) _$_findCachedViewById(R$id.tv_withdraw_receive_ecny)).setText("--");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void withdrawUI(String str) {
        Double d;
        Double d2;
        String str2;
        String str3;
        Double d3 = null;
        if (str != null) {
            double parseDouble = Double.parseDouble(str);
            PromotionWithdrawConfig promotionWithdrawConfig = this.promotionWithdrawConfig;
            Double valueOf = promotionWithdrawConfig != null ? Double.valueOf(promotionWithdrawConfig.exchangeRate) : null;
            if (valueOf == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            d = Double.valueOf(parseDouble / valueOf.doubleValue());
        } else {
            d = null;
        }
        if (d != null) {
            double doubleValue = d.doubleValue();
            PromotionWithdrawConfig promotionWithdrawConfig2 = this.promotionWithdrawConfig;
            Double valueOf2 = promotionWithdrawConfig2 != null ? Double.valueOf(promotionWithdrawConfig2.handlingFeeRate) : null;
            if (valueOf2 == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            d2 = Double.valueOf(doubleValue * valueOf2.doubleValue());
        } else {
            d2 = null;
        }
        TextView textView = (TextView) _$_findCachedViewById(R$id.tv_withdraw_procedure_money);
        Object[] objArr = new Object[1];
        if (d2 == null || (str2 = String.valueOf(d2.doubleValue())) == null) {
            str2 = "0";
        }
        objArr[0] = FormatUtil.formatTwo(str2);
        textView.setText(AppUtil.getString(R.string.withdraw_procedure_money, objArr));
        if (d != null) {
            double doubleValue2 = d.doubleValue();
            if (d2 == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            d3 = Double.valueOf(doubleValue2 - d2.doubleValue());
        }
        TextView textView2 = (TextView) _$_findCachedViewById(R$id.tv_withdraw_receive_ecny);
        if (d3 == null || (str3 = String.valueOf(d3.doubleValue())) == null) {
            str3 = "0";
        }
        textView2.setText(FormatUtil.formatTwo(str3));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showSafeCenterDialog() {
        PaySafeCenterDialog paySafeCenterDialog = new PaySafeCenterDialog(this);
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) paySafeCenterDialog.findViewById(R.id.coordinatorLayout);
        if (coordinatorLayout == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        ViewParent parent = coordinatorLayout.getParent();
        if (parent == null) {
            throw new TypeCastException("null cannot be cast to non-null type android.view.View");
        }
        final BottomSheetBehavior from = BottomSheetBehavior.from((View) parent);
        paySafeCenterDialog.setOnlyPasswordVisible();
        paySafeCenterDialog.hasTradePwd(this.isHasSetTransactionPwd);
        paySafeCenterDialog.show();
        from.setState(3);
        paySafeCenterDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.one.tomato.mvp.ui.promotion.ui.WithdrawActivity$showSafeCenterDialog$1
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                BottomSheetBehavior.this.setState(4);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void confirmWithdraw() {
        CharSequence trim;
        CharSequence trim2;
        Integer valueOf;
        CharSequence trim3;
        PromotionWalletBalance promotionWalletBalance = this.promotionWalletBalance;
        if (promotionWalletBalance != null && Double.compare(promotionWalletBalance.balance, 0) == 0) {
            ToastUtil.showCenterToast((int) R.string.withdraw_confirm_no_money1);
            return;
        }
        String obj = ((EditText) _$_findCachedViewById(R$id.et_xiaobai_account)).getText().toString();
        if (obj == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
        }
        trim = StringsKt__StringsKt.trim(obj);
        String obj2 = trim.toString();
        if (TextUtils.isEmpty(obj2)) {
            ToastUtil.showCenterToast((int) R.string.withdraw_xiaobai_account_input);
            return;
        }
        String obj3 = ((EditText) _$_findCachedViewById(R$id.et_withdraw_money_min)).getText().toString();
        if (obj3 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
        }
        trim2 = StringsKt__StringsKt.trim(obj3);
        String obj4 = trim2.toString();
        if (TextUtils.isEmpty(obj4)) {
            ToastUtil.showCenterToast((int) R.string.withdraw_confirm_no_money2);
            return;
        }
        double parseDouble = Double.parseDouble(obj4);
        PromotionWithdrawConfig promotionWithdrawConfig = this.promotionWithdrawConfig;
        String str = null;
        if ((promotionWithdrawConfig != null ? Integer.valueOf(promotionWithdrawConfig.minExchangeMoney) : null) == null) {
            Intrinsics.throwNpe();
            throw null;
        } else if (Double.compare(parseDouble, valueOf.intValue()) == -1) {
            Object[] objArr = new Object[1];
            PromotionWithdrawConfig promotionWithdrawConfig2 = this.promotionWithdrawConfig;
            if (promotionWithdrawConfig2 != null) {
                str = String.valueOf(promotionWithdrawConfig2.minExchangeMoney);
            }
            objArr[0] = FormatUtil.formatTwo(str);
            ToastUtil.showCenterToast(AppUtil.getString(R.string.withdraw_money_min, objArr));
        } else {
            showWaitingDialog();
            TextView tv_country_code = (TextView) _$_findCachedViewById(R$id.tv_country_code);
            Intrinsics.checkExpressionValueIsNotNull(tv_country_code, "tv_country_code");
            String obj5 = tv_country_code.getText().toString();
            if (obj5 == null) {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
            }
            trim3 = StringsKt__StringsKt.trim(obj5);
            String obj6 = trim3.toString();
            if (obj6 == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            String substring = obj6.substring(1);
            Intrinsics.checkExpressionValueIsNotNull(substring, "(this as java.lang.String).substring(startIndex)");
            JSONObject jSONObject = new JSONObject();
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("countryCode", substring);
            jSONObject2.put("phone", obj2);
            jSONObject.put(AopConstants.APP_PROPERTIES_KEY, jSONObject2);
            jSONObject.put("merchantId", PromotionUtil.merchantId);
            jSONObject.put("uid", String.valueOf(DBUtil.getMemberId()));
            RequestBody body = RequestBody.create(MediaType.parse("application/json"), jSONObject.toString());
            Intrinsics.checkExpressionValueIsNotNull(body, "body");
            ApiImplService.Companion.getApiImplService().requestPromotionAccount(PromotionUtil.DOMAIN + "/api/app/promotion/app/getAccount", body).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler((RxAppCompatActivity) this)).subscribe(new ApiDisposableObserver<PromotionAccount>() { // from class: com.one.tomato.mvp.ui.promotion.ui.WithdrawActivity$confirmWithdraw$1
                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResult(PromotionAccount promotionAccount) {
                    boolean z;
                    Context mContext;
                    WithdrawActivity.this.hideWaitingDialog();
                    if (promotionAccount != null && Intrinsics.compare(promotionAccount.accountExist, 0) == 1) {
                        z = WithdrawActivity.this.isHasSetTransactionPwd;
                        if (!z) {
                            mContext = WithdrawActivity.this.getMContext();
                            VeritifyIdentityActivity.startActivity(mContext, 1003);
                            return;
                        }
                        WithdrawActivity.access$getTradePwdEntryDialog$p(WithdrawActivity.this).show();
                        return;
                    }
                    ToastUtil.showCenterToast((int) R.string.withdraw_xiaobai_account_not_exist);
                }

                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResultError(ResponseThrowable responseThrowable) {
                    WithdrawActivity.this.hideWaitingDialog();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void verifyTradePwd(String str) {
        ((EditText) _$_findCachedViewById(R$id.et_withdraw_money_min)).clearFocus();
        hideKeyBoard(this);
        showWaitingDialog();
        ApiImplService apiImplService = ApiImplService.Companion.getApiImplService();
        int memberId = DBUtil.getMemberId();
        String md5 = MD5Util.md5(str);
        Intrinsics.checkExpressionValueIsNotNull(md5, "MD5Util.md5(tradePwd)");
        apiImplService.verifyTradePassword(memberId, md5).compose(RxUtils.bindToLifecycler((RxAppCompatActivity) this)).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<TradePwdBean>() { // from class: com.one.tomato.mvp.ui.promotion.ui.WithdrawActivity$verifyTradePwd$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver, io.reactivex.Observer
            public void onNext(BaseResponse<TradePwdBean> baseResponse) {
                int i;
                int i2;
                Intrinsics.checkParameterIsNotNull(baseResponse, "baseResponse");
                WithdrawActivity.this.hideWaitingDialog();
                int code = baseResponse.getCode();
                if (code == 0) {
                    onResult(baseResponse.getData());
                } else if (code != -3) {
                    i = WithdrawActivity.this.RESPONSE_CODE_PWD_ERROR;
                    if (code != i) {
                        i2 = WithdrawActivity.this.RESPONSE_CODE_PWD_OUT_ERROR;
                        if (code == i2) {
                            WithdrawActivity.this.showLockTradePwdDialog();
                            return;
                        }
                        ResponseThrowable responseThrowable = new ResponseThrowable(baseResponse.getMessage(), baseResponse.getCode());
                        responseThrowable.setData(baseResponse.getData());
                        LogUtil.m3786e("code = " + baseResponse.getCode() + ",message = " + baseResponse.getMessage());
                        ToastUtil.showCenterToast(baseResponse.getMessage());
                        onResultError(responseThrowable);
                        return;
                    }
                    WithdrawActivity withdrawActivity = WithdrawActivity.this;
                    TradePwdBean data = baseResponse.getData();
                    withdrawActivity.showTradePwdErrorDialog(data != null ? Integer.valueOf(data.getLeaveTimes()) : null);
                } else {
                    LogUtil.m3786e("token已过期，请重新登录");
                    CallbackUtil.loginOut(false);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(TradePwdBean tradePwdBean) {
                WithdrawActivity withdrawActivity = WithdrawActivity.this;
                String sec = tradePwdBean != null ? tradePwdBean.getSec() : null;
                if (sec != null) {
                    withdrawActivity.requestPromotionWithdraw(sec);
                } else {
                    Intrinsics.throwNpe();
                    throw null;
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                WithdrawActivity.this.hideWaitingDialog();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void requestPromotionWithdraw(String str) {
        CharSequence trim;
        CharSequence trim2;
        CharSequence trim3;
        String obj = ((EditText) _$_findCachedViewById(R$id.et_withdraw_money_min)).getText().toString();
        if (obj == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
        }
        trim = StringsKt__StringsKt.trim(obj);
        String formatZero = FormatUtil.formatZero(trim.toString());
        Intrinsics.checkExpressionValueIsNotNull(formatZero, "FormatUtil.formatZero(et…Text().toString().trim())");
        int parseInt = Integer.parseInt(formatZero);
        String obj2 = ((EditText) _$_findCachedViewById(R$id.et_xiaobai_account)).getText().toString();
        if (obj2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
        }
        trim2 = StringsKt__StringsKt.trim(obj2);
        final String obj3 = trim2.toString();
        TextView tv_country_code = (TextView) _$_findCachedViewById(R$id.tv_country_code);
        Intrinsics.checkExpressionValueIsNotNull(tv_country_code, "tv_country_code");
        String obj4 = tv_country_code.getText().toString();
        if (obj4 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
        }
        trim3 = StringsKt__StringsKt.trim(obj4);
        ApiImplService.Companion.getApiImplService().requestPromotionWithdraw(DBUtil.getMemberId(), parseInt, obj3, trim3.toString(), str).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler((RxAppCompatActivity) this)).subscribe(new ApiDisposableObserver<Object>() { // from class: com.one.tomato.mvp.ui.promotion.ui.WithdrawActivity$requestPromotionWithdraw$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(Object obj5) {
                CharSequence trim4;
                Context mContext;
                String obj6 = ((EditText) WithdrawActivity.this._$_findCachedViewById(R$id.et_withdraw_money_min)).getText().toString();
                if (obj6 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                }
                trim4 = StringsKt__StringsKt.trim(obj6);
                String obj7 = trim4.toString();
                PromotionOrderRecord.C2524Record c2524Record = new PromotionOrderRecord.C2524Record();
                c2524Record.orderId = "";
                TextView tv_withdraw_receive_ecny = (TextView) WithdrawActivity.this._$_findCachedViewById(R$id.tv_withdraw_receive_ecny);
                Intrinsics.checkExpressionValueIsNotNull(tv_withdraw_receive_ecny, "tv_withdraw_receive_ecny");
                c2524Record.coinAmount = tv_withdraw_receive_ecny.getText().toString();
                c2524Record.orderAmount = obj7;
                c2524Record.orderStatus = 3;
                c2524Record.orderTime = FormatUtil.formatTime("yyyyMMdd", new Date());
                c2524Record.walletAccount = obj3;
                WithdrawStatusActivity.Companion companion = WithdrawStatusActivity.Companion;
                mContext = WithdrawActivity.this.getMContext();
                if (mContext == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                companion.startActivity(mContext, 1, c2524Record);
                WithdrawActivity.this.finish();
                WithdrawActivity.this.setResult(-1);
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                WithdrawActivity.this.hideWaitingDialog();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showTradePwdErrorDialog(Integer num) {
        final CustomAlertDialog customAlertDialog = new CustomAlertDialog(this);
        customAlertDialog.bottomButtonVisiblity(0);
        customAlertDialog.setTitle(R.string.common_notify);
        customAlertDialog.setMessage(AppUtil.getString(R.string.trade_pwd_wrong_times, num));
        customAlertDialog.setCancelButtonBackgroundRes(R.color.white);
        customAlertDialog.setCancelButtonTextColor(R.color.text_dark);
        customAlertDialog.setCancelButton(R.string.trade_pwd_input_again, new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.promotion.ui.WithdrawActivity$showTradePwdErrorDialog$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ((EditText) WithdrawActivity.this._$_findCachedViewById(R$id.et_withdraw_money_min)).setFocusable(true);
                customAlertDialog.dismiss();
                WithdrawActivity.access$getTradePwdEntryDialog$p(WithdrawActivity.this).show();
            }
        });
        customAlertDialog.setConfirmButtonBackgroundRes(R.drawable.common_selector_solid_corner30_coloraccent);
        customAlertDialog.setConfirmButtonTextColor(R.color.white);
        customAlertDialog.setConfirmButton(R.string.trade_pwd_forget, new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.promotion.ui.WithdrawActivity$showTradePwdErrorDialog$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VeritifyIdentityActivity.startActivity(WithdrawActivity.this, 1004);
                customAlertDialog.dismiss();
            }
        });
        customAlertDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showLockTradePwdDialog() {
        final CustomAlertDialog customAlertDialog = new CustomAlertDialog(this);
        customAlertDialog.bottomButtonVisiblity(2);
        customAlertDialog.setTitle(R.string.common_notify);
        customAlertDialog.setMessage(R.string.trade_pwd_wrong_too_times);
        customAlertDialog.setConfirmButtonBackgroundRes(R.drawable.common_selector_solid_corner30_coloraccent);
        customAlertDialog.setConfirmButtonTextColor(R.color.white);
        customAlertDialog.setConfirmButton(R.string.trade_pws_modify, new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.promotion.ui.WithdrawActivity$showLockTradePwdDialog$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VeritifyIdentityActivity.startActivity(WithdrawActivity.this, 1004);
                customAlertDialog.dismiss();
                WithdrawActivity.this.finish();
            }
        });
        customAlertDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == 11 && intent != null) {
            ((TextView) _$_findCachedViewById(R$id.tv_country_code)).setText(intent.getExtras().getString("country_code"));
        }
    }
}
