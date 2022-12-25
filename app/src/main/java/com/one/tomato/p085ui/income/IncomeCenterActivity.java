package com.one.tomato.p085ui.income;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.entity.IncomeCenterAccount;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.p085ui.income.IncomeActivity;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.RxUtils;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.HashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: IncomeCenterActivity.kt */
/* renamed from: com.one.tomato.ui.income.IncomeCenterActivity */
/* loaded from: classes3.dex */
public final class IncomeCenterActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;

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
        return R.layout.activity_income_center;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    /* compiled from: IncomeCenterActivity.kt */
    /* renamed from: com.one.tomato.ui.income.IncomeCenterActivity$Companion */
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
            intent.setClass(context, IncomeCenterActivity.class);
            context.startActivity(intent);
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        initTitleBar();
        TextView titleTV = getTitleTV();
        if (titleTV != null) {
            titleTV.setText(R.string.income_center_title);
        }
        TextView rightTV = getRightTV();
        if (rightTV != null) {
            rightTV.setVisibility(0);
        }
        TextView rightTV2 = getRightTV();
        if (rightTV2 != null) {
            rightTV2.setText(R.string.income_cash_recode_title);
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
        requestAccountAll();
    }

    private final void setListener() {
        TextView rightTV = getRightTV();
        if (rightTV != null) {
            rightTV.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.income.IncomeCenterActivity$setListener$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Context mContext;
                    mContext = IncomeCenterActivity.this.getMContext();
                    if (mContext != null) {
                        CashListActivity.startActivity(mContext, 0);
                    } else {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                }
            });
        }
        ((ConstraintLayout) _$_findCachedViewById(R$id.cl_purse)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.income.IncomeCenterActivity$setListener$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                IncomeActivity.Companion companion = IncomeActivity.Companion;
                mContext = IncomeCenterActivity.this.getMContext();
                if (mContext != null) {
                    companion.startActivity(mContext, 4);
                } else {
                    Intrinsics.throwNpe();
                    throw null;
                }
            }
        });
        ((ConstraintLayout) _$_findCachedViewById(R$id.cl_game)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.income.IncomeCenterActivity$setListener$3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                IncomeActivity.Companion companion = IncomeActivity.Companion;
                mContext = IncomeCenterActivity.this.getMContext();
                if (mContext != null) {
                    companion.startActivity(mContext, 3);
                } else {
                    Intrinsics.throwNpe();
                    throw null;
                }
            }
        });
        ((ConstraintLayout) _$_findCachedViewById(R$id.cl_live)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.income.IncomeCenterActivity$setListener$4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                IncomeActivity.Companion companion = IncomeActivity.Companion;
                mContext = IncomeCenterActivity.this.getMContext();
                if (mContext != null) {
                    companion.startActivity(mContext, 1);
                } else {
                    Intrinsics.throwNpe();
                    throw null;
                }
            }
        });
        ((ConstraintLayout) _$_findCachedViewById(R$id.cl_up)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.income.IncomeCenterActivity$setListener$5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                IncomeActivity.Companion companion = IncomeActivity.Companion;
                mContext = IncomeCenterActivity.this.getMContext();
                if (mContext != null) {
                    companion.startActivity(mContext, 2);
                } else {
                    Intrinsics.throwNpe();
                    throw null;
                }
            }
        });
    }

    private final void requestAccountAll() {
        ApiImplService.Companion.getApiImplService().requestAccountAll(DBUtil.getMemberId()).compose(RxUtils.schedulersTransformer()).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.ui.income.IncomeCenterActivity$requestAccountAll$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                IncomeCenterActivity.this.showWaitingDialog();
            }
        }).subscribe(new ApiDisposableObserver<IncomeCenterAccount>() { // from class: com.one.tomato.ui.income.IncomeCenterActivity$requestAccountAll$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(IncomeCenterAccount bean) {
                Intrinsics.checkParameterIsNotNull(bean, "bean");
                IncomeCenterActivity.this.hideWaitingDialog();
                double d = 0.0d;
                if (bean.upHostType) {
                    ConstraintLayout cl_up = (ConstraintLayout) IncomeCenterActivity.this._$_findCachedViewById(R$id.cl_up);
                    Intrinsics.checkExpressionValueIsNotNull(cl_up, "cl_up");
                    cl_up.setVisibility(0);
                    d = 0.0d + bean.upBalance;
                    ((TextView) IncomeCenterActivity.this._$_findCachedViewById(R$id.tv_account_up)).setText(FormatUtil.formatTomato2RMB(bean.upBalance));
                }
                if (bean.anchorType) {
                    ConstraintLayout cl_live = (ConstraintLayout) IncomeCenterActivity.this._$_findCachedViewById(R$id.cl_live);
                    Intrinsics.checkExpressionValueIsNotNull(cl_live, "cl_live");
                    cl_live.setVisibility(0);
                    d += bean.anchorBalance;
                    ((TextView) IncomeCenterActivity.this._$_findCachedViewById(R$id.tv_account_live)).setText(FormatUtil.formatTomato2RMB(bean.anchorBalance));
                }
                double d2 = d + bean.walletBalance;
                ((TextView) IncomeCenterActivity.this._$_findCachedViewById(R$id.tv_account_purse)).setText(FormatUtil.formatTomato2RMB(bean.walletBalance));
                double d3 = d2 + bean.gameBalance;
                ((TextView) IncomeCenterActivity.this._$_findCachedViewById(R$id.tv_account_game)).setText(FormatUtil.formatTomato2RMB(bean.gameBalance));
                ((TextView) IncomeCenterActivity.this._$_findCachedViewById(R$id.tv_account_all)).setText(FormatUtil.formatTomato2RMB(d3));
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable e) {
                Intrinsics.checkParameterIsNotNull(e, "e");
                IncomeCenterActivity.this.hideWaitingDialog();
            }
        });
    }
}
