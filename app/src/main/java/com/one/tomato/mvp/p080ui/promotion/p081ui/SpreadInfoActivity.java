package com.one.tomato.mvp.p080ui.promotion.p081ui;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.p002v4.app.FragmentTransaction;
import android.support.p005v7.widget.CardView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.entity.PromotionFanyong;
import com.one.tomato.entity.PromotionMember;
import com.one.tomato.entity.PromotionWalletBalance;
import com.one.tomato.entity.PromotionWithdrawConfig;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.feedback.view.FeedbackRechargeIssuesActivity;
import com.one.tomato.mvp.p080ui.promotion.p081ui.WithdrawActivity;
import com.one.tomato.thirdpart.promotion.PromotionUtil;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.RxUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import java.io.Serializable;
import java.util.HashMap;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SpreadInfoActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.promotion.ui.SpreadInfoActivity */
/* loaded from: classes3.dex */
public final class SpreadInfoActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    private HashMap _$_findViewCache;
    private PromotionFanyong promotionFanyong;
    private PromotionWalletBalance promotionWalletBalance;
    private PromotionWithdrawConfig promotionWithdrawConfig;
    private SpreadInComeFragment spreadIncomeFragment;
    private FragmentTransaction transfraction;

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
        return R.layout.activity_spread_info;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    public static final /* synthetic */ FragmentTransaction access$getTransfraction$p(SpreadInfoActivity spreadInfoActivity) {
        FragmentTransaction fragmentTransaction = spreadInfoActivity.transfraction;
        if (fragmentTransaction != null) {
            return fragmentTransaction;
        }
        Intrinsics.throwUninitializedPropertyAccessException("transfraction");
        throw null;
    }

    /* compiled from: SpreadInfoActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.promotion.ui.SpreadInfoActivity$Companion */
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
        addListener();
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
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        Intrinsics.checkExpressionValueIsNotNull(beginTransaction, "fragmentManger.beginTransaction()");
        this.transfraction = beginTransaction;
        updateIncomeUI();
        request();
    }

    private final void addListener() {
        ((ImageView) _$_findCachedViewById(R$id.iv_back)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.promotion.ui.SpreadInfoActivity$addListener$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SpreadInfoActivity.this.onBackPressed();
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_sub_title)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.promotion.ui.SpreadInfoActivity$addListener$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SpreadHtmlActivity.Companion.startActivity(SpreadInfoActivity.this);
            }
        });
        ((TextView) _$_findCachedViewById(R$id.spread_withdraw_action)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.promotion.ui.SpreadInfoActivity$addListener$3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PromotionWalletBalance promotionWalletBalance;
                PromotionWithdrawConfig promotionWithdrawConfig;
                WithdrawActivity.Companion companion = WithdrawActivity.Companion;
                SpreadInfoActivity spreadInfoActivity = SpreadInfoActivity.this;
                promotionWalletBalance = spreadInfoActivity.promotionWalletBalance;
                promotionWithdrawConfig = SpreadInfoActivity.this.promotionWithdrawConfig;
                companion.startActivity(spreadInfoActivity, promotionWalletBalance, promotionWithdrawConfig, 1);
            }
        });
        ((TextView) _$_findCachedViewById(R$id.spread_withdraw_problem)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.promotion.ui.SpreadInfoActivity$addListener$4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FeedbackRechargeIssuesActivity.Companion.startActivity(SpreadInfoActivity.this, 2);
            }
        });
        ((CardView) _$_findCachedViewById(R$id.card_xiaobai)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.promotion.ui.SpreadInfoActivity$addListener$5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PromotionUtil.openXiaobai(SpreadInfoActivity.this);
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_spread_withdraw_explain_1_open_xiaobai)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.promotion.ui.SpreadInfoActivity$addListener$6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PromotionUtil.openXiaobai(SpreadInfoActivity.this);
            }
        });
        ((ConstraintLayout) _$_findCachedViewById(R$id.cl_spread_income)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.promotion.ui.SpreadInfoActivity$addListener$7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SpreadRecordActivity.Companion.startActivity(SpreadInfoActivity.this, 0);
            }
        });
        ((ConstraintLayout) _$_findCachedViewById(R$id.cl_spread_withdraw_record)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.promotion.ui.SpreadInfoActivity$addListener$8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SpreadRecordActivity.Companion.startActivity(SpreadInfoActivity.this, 1);
            }
        });
        ((CardView) _$_findCachedViewById(R$id.card_spread_explain)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.promotion.ui.SpreadInfoActivity$addListener$9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ((TextView) SpreadInfoActivity.this._$_findCachedViewById(R$id.tv_spread_explain)).setBackgroundResource(R.drawable.spread_btn_bg);
                ((TextView) SpreadInfoActivity.this._$_findCachedViewById(R$id.tv_spread_explain)).setTextColor(SpreadInfoActivity.this.getResources().getColor(R.color.white));
                ((TextView) SpreadInfoActivity.this._$_findCachedViewById(R$id.tv_spread_income)).setBackgroundResource(R.drawable.common_shape_solid_corner5_white);
                ((TextView) SpreadInfoActivity.this._$_findCachedViewById(R$id.tv_spread_income)).setTextColor(SpreadInfoActivity.this.getResources().getColor(R.color.text_light));
                ConstraintLayout cl_explain_info = (ConstraintLayout) SpreadInfoActivity.this._$_findCachedViewById(R$id.cl_explain_info);
                Intrinsics.checkExpressionValueIsNotNull(cl_explain_info, "cl_explain_info");
                cl_explain_info.setVisibility(0);
                FrameLayout framelayout = (FrameLayout) SpreadInfoActivity.this._$_findCachedViewById(R$id.framelayout);
                Intrinsics.checkExpressionValueIsNotNull(framelayout, "framelayout");
                framelayout.setVisibility(8);
            }
        });
        ((CardView) _$_findCachedViewById(R$id.card_spread_income)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.promotion.ui.SpreadInfoActivity$addListener$10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SpreadInComeFragment spreadInComeFragment;
                SpreadInComeFragment spreadInComeFragment2;
                ((TextView) SpreadInfoActivity.this._$_findCachedViewById(R$id.tv_spread_explain)).setBackgroundResource(R.drawable.common_shape_solid_corner5_white);
                ((TextView) SpreadInfoActivity.this._$_findCachedViewById(R$id.tv_spread_explain)).setTextColor(SpreadInfoActivity.this.getResources().getColor(R.color.text_light));
                ((TextView) SpreadInfoActivity.this._$_findCachedViewById(R$id.tv_spread_income)).setBackgroundResource(R.drawable.spread_btn_bg);
                ((TextView) SpreadInfoActivity.this._$_findCachedViewById(R$id.tv_spread_income)).setTextColor(SpreadInfoActivity.this.getResources().getColor(R.color.white));
                ConstraintLayout cl_explain_info = (ConstraintLayout) SpreadInfoActivity.this._$_findCachedViewById(R$id.cl_explain_info);
                Intrinsics.checkExpressionValueIsNotNull(cl_explain_info, "cl_explain_info");
                cl_explain_info.setVisibility(8);
                FrameLayout framelayout = (FrameLayout) SpreadInfoActivity.this._$_findCachedViewById(R$id.framelayout);
                Intrinsics.checkExpressionValueIsNotNull(framelayout, "framelayout");
                framelayout.setVisibility(0);
                spreadInComeFragment = SpreadInfoActivity.this.spreadIncomeFragment;
                if (spreadInComeFragment == null) {
                    SpreadInfoActivity.this.spreadIncomeFragment = SpreadInComeFragment.Companion.getInstance();
                    FragmentTransaction access$getTransfraction$p = SpreadInfoActivity.access$getTransfraction$p(SpreadInfoActivity.this);
                    spreadInComeFragment2 = SpreadInfoActivity.this.spreadIncomeFragment;
                    if (spreadInComeFragment2 == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                    access$getTransfraction$p.add(R.id.framelayout, spreadInComeFragment2);
                    SpreadInfoActivity.access$getTransfraction$p(SpreadInfoActivity.this).commitNowAllowingStateLoss();
                }
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_spread_action)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.promotion.ui.SpreadInfoActivity$addListener$11
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SpreadHtmlActivity.Companion.startActivity(SpreadInfoActivity.this);
            }
        });
    }

    private final void request() {
        ApiImplService.Companion.getApiImplService().requestSpreadMember(DBUtil.getMemberId()).compose(RxUtils.bindToLifecycler((RxAppCompatActivity) this)).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<PromotionMember>() { // from class: com.one.tomato.mvp.ui.promotion.ui.SpreadInfoActivity$request$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(PromotionMember t) {
                Intrinsics.checkParameterIsNotNull(t, "t");
                ((TextView) SpreadInfoActivity.this._$_findCachedViewById(R$id.tv_spread_people_level1_num)).setText(String.valueOf(t.promotionLevel1));
                ((TextView) SpreadInfoActivity.this._$_findCachedViewById(R$id.tv_spread_people_level2_num)).setText(String.valueOf(t.promotionLevel2));
                ((TextView) SpreadInfoActivity.this._$_findCachedViewById(R$id.tv_spread_people_level3_num)).setText(String.valueOf(t.promotionLevel3));
                ((TextView) SpreadInfoActivity.this._$_findCachedViewById(R$id.tv_spread_people_level4_num)).setText(String.valueOf(t.promotionLevel4));
                ((TextView) SpreadInfoActivity.this._$_findCachedViewById(R$id.tv_spread_people_level5_num)).setText(String.valueOf(t.promotionLevel5));
            }
        });
        ApiImplService.Companion.getApiImplService().requestSpreadFanyong().compose(RxUtils.bindToLifecycler((RxAppCompatActivity) this)).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<PromotionFanyong>() { // from class: com.one.tomato.mvp.ui.promotion.ui.SpreadInfoActivity$request$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(PromotionFanyong t) {
                Intrinsics.checkParameterIsNotNull(t, "t");
                SpreadInfoActivity.this.promotionFanyong = t;
                ((TextView) SpreadInfoActivity.this._$_findCachedViewById(R$id.tv_fannyong_money_all)).setText(AppUtil.getString(R.string.spread_agent_level_income, Integer.valueOf(t.levelRateTotal)));
                ((TextView) SpreadInfoActivity.this._$_findCachedViewById(R$id.tv_fannyong_money1)).setText(AppUtil.getString(R.string.spread_agent_level_income, Integer.valueOf(t.levelRateOne)));
                ((TextView) SpreadInfoActivity.this._$_findCachedViewById(R$id.tv_fannyong_money2)).setText(AppUtil.getString(R.string.spread_agent_level_income, Integer.valueOf(t.levelRateTwo)));
                ((TextView) SpreadInfoActivity.this._$_findCachedViewById(R$id.tv_fannyong_money3)).setText(AppUtil.getString(R.string.spread_agent_level_income, Integer.valueOf(t.levelRateThree)));
                ((TextView) SpreadInfoActivity.this._$_findCachedViewById(R$id.tv_fannyong_money4)).setText(AppUtil.getString(R.string.spread_agent_level_income, Integer.valueOf(t.levelRateFour)));
                ((TextView) SpreadInfoActivity.this._$_findCachedViewById(R$id.tv_fannyong_money5)).setText(AppUtil.getString(R.string.spread_agent_level_income, Integer.valueOf(t.levelRateFive)));
            }
        });
        PromotionUtil.requestToken(new SpreadInfoActivity$request$3(this));
        if (this.promotionWalletBalance == null) {
            PromotionUtil.requestToken(new SpreadInfoActivity$request$4(this));
        }
        PromotionUtil.requestToken(new SpreadInfoActivity$request$5(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateIncomeUI() {
        String str;
        String str2;
        TextView textView = (TextView) _$_findCachedViewById(R$id.spread_withdraw_renmingbi_num);
        Object[] objArr = new Object[1];
        PromotionWalletBalance promotionWalletBalance = this.promotionWalletBalance;
        if (promotionWalletBalance == null || (str = String.valueOf(promotionWalletBalance.balance)) == null) {
            str = "0";
        }
        objArr[0] = FormatUtil.formatTwo(str);
        textView.setText(AppUtil.getString(R.string.spread_withdraw_renmingbi_num, objArr));
        if (this.promotionWithdrawConfig != null) {
            TextView textView2 = (TextView) _$_findCachedViewById(R$id.spread_withdraw_ECNY_num);
            Object[] objArr2 = new Object[1];
            PromotionWalletBalance promotionWalletBalance2 = this.promotionWalletBalance;
            Double d = null;
            if (promotionWalletBalance2 != null) {
                double d2 = promotionWalletBalance2.balance;
                PromotionWithdrawConfig promotionWithdrawConfig = this.promotionWithdrawConfig;
                Double valueOf = promotionWithdrawConfig != null ? Double.valueOf(promotionWithdrawConfig.exchangeRate) : null;
                if (valueOf == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                d = Double.valueOf(d2 / valueOf.doubleValue());
            }
            objArr2[0] = FormatUtil.formatTwo(String.valueOf(d));
            textView2.setText(AppUtil.getString(R.string.spread_withdraw_ECNY_num, objArr2));
        } else {
            ((TextView) _$_findCachedViewById(R$id.spread_withdraw_ECNY_num)).setText(AppUtil.getString(R.string.spread_withdraw_ECNY_num, "0"));
        }
        TextView textView3 = (TextView) _$_findCachedViewById(R$id.tv_income_all_money);
        PromotionWalletBalance promotionWalletBalance3 = this.promotionWalletBalance;
        if (promotionWalletBalance3 == null || (str2 = String.valueOf(promotionWalletBalance3.allTotalSettlement)) == null) {
            str2 = "0";
        }
        textView3.setText(FormatUtil.formatTwo(str2));
    }

    public final PromotionFanyong getPromotionFanyong() {
        return this.promotionFanyong;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == 1) {
            finish();
        }
    }
}
