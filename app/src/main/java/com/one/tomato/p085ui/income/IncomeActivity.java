package com.one.tomato.p085ui.income;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.dialog.PaySafeCenterDialog;
import com.one.tomato.entity.BalanceBean;
import com.one.tomato.entity.p079db.LoginInfo;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.login.view.LoginActivity;
import com.one.tomato.p085ui.feedback.FeedbackDetailActivity;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.ImmersionBarUtil;
import com.one.tomato.utils.RxUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.HashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: IncomeActivity.kt */
/* renamed from: com.one.tomato.ui.income.IncomeActivity */
/* loaded from: classes3.dex */
public final class IncomeActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    private HashMap _$_findViewCache;
    private int accountType = 1;
    private BalanceBean balanceBean;
    private PaySafeCenterDialog paySafeCenterDialog;
    public static final Companion Companion = new Companion(null);
    private static final String INTENT_ACCOUNT_TYPE = INTENT_ACCOUNT_TYPE;
    private static final String INTENT_ACCOUNT_TYPE = INTENT_ACCOUNT_TYPE;

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
        return R.layout.activity_my_income;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    /* compiled from: IncomeActivity.kt */
    /* renamed from: com.one.tomato.ui.income.IncomeActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final String getINTENT_ACCOUNT_TYPE() {
            return IncomeActivity.INTENT_ACCOUNT_TYPE;
        }

        public final void startActivity(Context context, int i) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            LoginInfo loginInfo = DBUtil.getLoginInfo();
            Intrinsics.checkExpressionValueIsNotNull(loginInfo, "DBUtil.getLoginInfo()");
            if (!loginInfo.isLogin()) {
                LoginActivity.Companion.startActivity(context);
                return;
            }
            Intent intent = new Intent();
            intent.setClass(context, IncomeActivity.class);
            intent.putExtra(getINTENT_ACCOUNT_TYPE(), i);
            context.startActivity(intent);
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        ImmersionBarUtil.init(this, (int) R.id.ll_title);
        Intent intent = getIntent();
        Integer valueOf = intent != null ? Integer.valueOf(intent.getIntExtra(INTENT_ACCOUNT_TYPE, 1)) : null;
        if (valueOf == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        this.accountType = valueOf.intValue();
        int i = this.accountType;
        if (i == 1) {
            ((TextView) _$_findCachedViewById(R$id.tv_title)).setText(R.string.income_title_live);
            ((TextView) _$_findCachedViewById(R$id.tv_account_avail_label)).setText(R.string.income_balance);
        } else if (i == 2) {
            ((TextView) _$_findCachedViewById(R$id.tv_title)).setText(R.string.income_title_up);
            ((TextView) _$_findCachedViewById(R$id.tv_account_avail_label)).setText(R.string.income_balance);
        } else if (i == 3) {
            ((TextView) _$_findCachedViewById(R$id.tv_title)).setText(R.string.income_title_game);
            ((TextView) _$_findCachedViewById(R$id.tv_account_avail_label)).setText(R.string.income_balance);
            TextView tv_game_tip = (TextView) _$_findCachedViewById(R$id.tv_game_tip);
            Intrinsics.checkExpressionValueIsNotNull(tv_game_tip, "tv_game_tip");
            tv_game_tip.setVisibility(0);
        } else if (i != 4) {
        } else {
            ((TextView) _$_findCachedViewById(R$id.tv_title)).setText(R.string.income_title_purse);
            ((TextView) _$_findCachedViewById(R$id.right_txt)).setText(R.string.inspect_record);
            View view_vertical_line = _$_findCachedViewById(R$id.view_vertical_line);
            Intrinsics.checkExpressionValueIsNotNull(view_vertical_line, "view_vertical_line");
            view_vertical_line.setVisibility(0);
            LinearLayout ll_account_purse = (LinearLayout) _$_findCachedViewById(R$id.ll_account_purse);
            Intrinsics.checkExpressionValueIsNotNull(ll_account_purse, "ll_account_purse");
            ll_account_purse.setVisibility(0);
            TextView tv_purse_tip = (TextView) _$_findCachedViewById(R$id.tv_purse_tip);
            Intrinsics.checkExpressionValueIsNotNull(tv_purse_tip, "tv_purse_tip");
            tv_purse_tip.setVisibility(0);
            ((TextView) _$_findCachedViewById(R$id.tv_account_avail_label)).setText(R.string.income_enable_amount_lable);
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        setListener();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        getIncomeAccountBalance();
    }

    private final void setListener() {
        ImageView imageView = (ImageView) _$_findCachedViewById(R$id.iv_back);
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.income.IncomeActivity$setListener$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    IncomeActivity.this.onBackPressed();
                }
            });
        }
        TextView textView = (TextView) _$_findCachedViewById(R$id.right_txt);
        if (textView != null) {
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.income.IncomeActivity$setListener$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    BalanceBean balanceBean;
                    int i;
                    int i2;
                    balanceBean = IncomeActivity.this.balanceBean;
                    if (balanceBean == null) {
                        return;
                    }
                    i = IncomeActivity.this.accountType;
                    if (i == 4) {
                        InspectRecordActivity.Companion.startActivity(IncomeActivity.this);
                        return;
                    }
                    IncomeActivity incomeActivity = IncomeActivity.this;
                    i2 = incomeActivity.accountType;
                    IncomeAndCashActivity.startActivity(incomeActivity, i2);
                }
            });
        }
        TextView textView2 = (TextView) _$_findCachedViewById(R$id.tv_income_submit);
        if (textView2 != null) {
            textView2.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.income.IncomeActivity$setListener$3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    BalanceBean balanceBean;
                    int i;
                    balanceBean = IncomeActivity.this.balanceBean;
                    if (balanceBean != null) {
                        if (!TextUtils.isEmpty(balanceBean.getAlipayAccount()) || !TextUtils.isEmpty(balanceBean.getBankAccount())) {
                            IncomeActivity incomeActivity = IncomeActivity.this;
                            i = incomeActivity.accountType;
                            CashActivity.startActivity(incomeActivity, balanceBean, i);
                            return;
                        }
                        VeritifyIdentityActivity.startActivity(IncomeActivity.this, 1002);
                    }
                }
            });
        }
        TextView textView3 = (TextView) _$_findCachedViewById(R$id.tv_income_safe_center);
        if (textView3 != null) {
            textView3.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.income.IncomeActivity$setListener$4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    IncomeActivity.this.showSafeCenterDialog();
                }
            });
        }
        LinearLayout linearLayout = (LinearLayout) _$_findCachedViewById(R$id.rl_income_feekback);
        if (linearLayout != null) {
            linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.income.IncomeActivity$setListener$5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    IncomeActivity incomeActivity = IncomeActivity.this;
                    FeedbackDetailActivity.startActivity(incomeActivity, "cash", incomeActivity.getResources().getString(R.string.feedback_cash_title));
                }
            });
        }
        TextView textView4 = (TextView) _$_findCachedViewById(R$id.tv_button);
        if (textView4 != null) {
            textView4.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.income.IncomeActivity$setListener$6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    IncomeActivity.this.getIncomeAccountBalance();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void getIncomeAccountBalance() {
        ApiImplService.Companion.getApiImplService().requestIncomeAccountBalance(DBUtil.getMemberId(), this.accountType).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler((RxAppCompatActivity) this)).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.ui.income.IncomeActivity$getIncomeAccountBalance$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                IncomeActivity.this.showWaitingDialog();
            }
        }).subscribe(new ApiDisposableObserver<BalanceBean>() { // from class: com.one.tomato.ui.income.IncomeActivity$getIncomeAccountBalance$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(BalanceBean balanceBean) {
                Intrinsics.checkParameterIsNotNull(balanceBean, "balanceBean");
                IncomeActivity.this.hideWaitingDialog();
                IncomeActivity.this.setEmptyViewState(false);
                IncomeActivity.this.updateViews(balanceBean);
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable e) {
                Intrinsics.checkParameterIsNotNull(e, "e");
                IncomeActivity.this.hideWaitingDialog();
                IncomeActivity.this.setEmptyViewState(true);
                TextView textView = (TextView) IncomeActivity.this._$_findCachedViewById(R$id.tv_income_submit);
                if (textView == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                textView.setEnabled(false);
                TextView textView2 = (TextView) IncomeActivity.this._$_findCachedViewById(R$id.tv_income_safe_center);
                if (textView2 != null) {
                    textView2.setEnabled(false);
                } else {
                    Intrinsics.throwNpe();
                    throw null;
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateViews(BalanceBean balanceBean) {
        this.balanceBean = balanceBean;
        if (this.accountType == 4) {
            ((TextView) _$_findCachedViewById(R$id.tv_account_avail)).setText(FormatUtil.formatTomato2RMB(balanceBean.getCanWithdrawBalance()));
        } else {
            ((TextView) _$_findCachedViewById(R$id.tv_account_avail)).setText(FormatUtil.formatTomato2RMB(balanceBean.getWalletBalance()));
        }
        ((TextView) _$_findCachedViewById(R$id.tv_account_purse)).setText(FormatUtil.formatTomato2RMB(balanceBean.getWalletBalance()));
        if (!TextUtils.isEmpty(balanceBean.getAlipayAccount()) || !TextUtils.isEmpty(balanceBean.getBankAccount())) {
            ((TextView) _$_findCachedViewById(R$id.tv_income_submit)).setText(R.string.income_cash_text);
        } else {
            ((TextView) _$_findCachedViewById(R$id.tv_income_submit)).setText(R.string.income_bind_alipay);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showSafeCenterDialog() {
        if (this.balanceBean == null) {
            return;
        }
        this.paySafeCenterDialog = new PaySafeCenterDialog(this);
        PaySafeCenterDialog paySafeCenterDialog = this.paySafeCenterDialog;
        if (paySafeCenterDialog == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        paySafeCenterDialog.setBalanceInfo(this.balanceBean);
        BalanceBean balanceBean = this.balanceBean;
        if (balanceBean == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        if (!balanceBean.isHasSetTransactionPwd()) {
            PaySafeCenterDialog paySafeCenterDialog2 = this.paySafeCenterDialog;
            if (paySafeCenterDialog2 == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            paySafeCenterDialog2.hasTradePwd(false);
        } else {
            PaySafeCenterDialog paySafeCenterDialog3 = this.paySafeCenterDialog;
            if (paySafeCenterDialog3 == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            paySafeCenterDialog3.hasTradePwd(true);
        }
        BalanceBean balanceBean2 = this.balanceBean;
        if (balanceBean2 == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        if (TextUtils.isEmpty(balanceBean2.getAlipayAccount())) {
            PaySafeCenterDialog paySafeCenterDialog4 = this.paySafeCenterDialog;
            if (paySafeCenterDialog4 == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            paySafeCenterDialog4.hasBindAlipay(false);
        } else {
            PaySafeCenterDialog paySafeCenterDialog5 = this.paySafeCenterDialog;
            if (paySafeCenterDialog5 == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            paySafeCenterDialog5.hasBindAlipay(true);
        }
        BalanceBean balanceBean3 = this.balanceBean;
        if (balanceBean3 == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        if (TextUtils.isEmpty(balanceBean3.getBankAccount())) {
            PaySafeCenterDialog paySafeCenterDialog6 = this.paySafeCenterDialog;
            if (paySafeCenterDialog6 == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            paySafeCenterDialog6.hasBindBank(false);
        } else {
            PaySafeCenterDialog paySafeCenterDialog7 = this.paySafeCenterDialog;
            if (paySafeCenterDialog7 == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            paySafeCenterDialog7.hasBindBank(true);
        }
        PaySafeCenterDialog paySafeCenterDialog8 = this.paySafeCenterDialog;
        if (paySafeCenterDialog8 != null) {
            paySafeCenterDialog8.show();
        } else {
            Intrinsics.throwNpe();
            throw null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final synchronized void setEmptyViewState(boolean z) {
        if (!z) {
            LinearLayout linearLayout = (LinearLayout) _$_findCachedViewById(R$id.emp_recharge);
            if (linearLayout == null) {
                Intrinsics.throwNpe();
                throw null;
            } else if (linearLayout.getVisibility() == 0) {
                LinearLayout emp_recharge = (LinearLayout) _$_findCachedViewById(R$id.emp_recharge);
                Intrinsics.checkExpressionValueIsNotNull(emp_recharge, "emp_recharge");
                emp_recharge.setVisibility(8);
                TextView textView = (TextView) _$_findCachedViewById(R$id.right_txt);
                if (textView == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                textView.setVisibility(0);
            }
        } else {
            LinearLayout linearLayout2 = (LinearLayout) _$_findCachedViewById(R$id.emp_recharge);
            if (linearLayout2 == null) {
                Intrinsics.throwNpe();
                throw null;
            } else if (linearLayout2.getVisibility() == 8) {
                LinearLayout emp_recharge2 = (LinearLayout) _$_findCachedViewById(R$id.emp_recharge);
                Intrinsics.checkExpressionValueIsNotNull(emp_recharge2, "emp_recharge");
                emp_recharge2.setVisibility(0);
                TextView textView2 = (TextView) _$_findCachedViewById(R$id.right_txt);
                if (textView2 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                textView2.setVisibility(8);
            }
        }
    }
}
