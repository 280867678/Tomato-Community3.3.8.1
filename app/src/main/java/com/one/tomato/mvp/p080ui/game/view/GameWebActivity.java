package com.one.tomato.mvp.p080ui.game.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.dialog.RechargeExGameDialog;
import com.one.tomato.entity.GameBalance;
import com.one.tomato.entity.HtmlConfig;
import com.one.tomato.entity.RechargeAccount;
import com.one.tomato.entity.RechargeExGame;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.BaseHtmlActivity;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.ImmersionBarUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.RxUtils;
import java.io.Serializable;
import java.util.HashMap;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GameWebActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.game.view.GameWebActivity */
/* loaded from: classes3.dex */
public final class GameWebActivity extends BaseHtmlActivity implements RechargeExGameDialog.RechargeExGameListener {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private boolean landscape;
    private boolean openBtn;
    private RechargeAccount rechargeAccount;
    private RechargeExGame rechargeExGame;
    private String url;

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

    @Override // com.one.tomato.mvp.base.view.BaseHtmlActivity, com.one.tomato.mvp.base.view.MvpBaseActivity
    public int createLayoutView() {
        return R.layout.activity_game_web;
    }

    @Override // com.one.tomato.mvp.base.view.BaseHtmlActivity, com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    public GameWebActivity() {
        DBUtil.getUserInfo();
    }

    /* compiled from: GameWebActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.game.view.GameWebActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startActivity(Context context, RechargeExGame rechargeExGame, String url, boolean z) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            Intrinsics.checkParameterIsNotNull(rechargeExGame, "rechargeExGame");
            Intrinsics.checkParameterIsNotNull(url, "url");
            Intent intent = new Intent();
            intent.setClass(context, GameWebActivity.class);
            intent.putExtra("rechargeExGame", rechargeExGame);
            intent.putExtra("url", url);
            intent.putExtra("landscape", z);
            context.startActivity(intent);
        }
    }

    @Override // com.one.tomato.mvp.base.view.BaseHtmlActivity, com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        ImmersionBarUtil.hideStatusBar(getWindow());
        WebView webView = (WebView) _$_findCachedViewById(R$id.webView);
        Intrinsics.checkExpressionValueIsNotNull(webView, "webView");
        initWebView(webView);
        addListener();
    }

    @Override // com.one.tomato.mvp.base.view.BaseHtmlActivity, com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        setHtmlConfig(new HtmlConfig());
        Serializable serializableExtra = getIntent().getSerializableExtra("rechargeExGame");
        if (serializableExtra == null) {
            throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.entity.RechargeExGame");
        }
        this.rechargeExGame = (RechargeExGame) serializableExtra;
        this.url = getIntent().getStringExtra("url");
        LogUtil.m3784i("传递的原始的url = " + this.url);
        this.landscape = getIntent().getBooleanExtra("landscape", false);
        if (this.landscape) {
            setRequestedOrientation(0);
        }
        ((WebView) _$_findCachedViewById(R$id.webView)).loadUrl(this.url);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        requestData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void requestData() {
        getRechargeAccountBalance(false);
        RechargeExGame rechargeExGame = this.rechargeExGame;
        if (rechargeExGame == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        String str = rechargeExGame.brandId;
        Intrinsics.checkExpressionValueIsNotNull(str, "rechargeExGame!!.brandId");
        getRechargeExGameBalance(str);
    }

    private final void addListener() {
        ((ImageView) _$_findCachedViewById(R$id.iv_btn_open)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.game.view.GameWebActivity$addListener$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                boolean z;
                z = GameWebActivity.this.openBtn;
                if (z) {
                    ((ConstraintLayout) GameWebActivity.this._$_findCachedViewById(R$id.cl_top_btn)).setBackgroundResource(0);
                    ConstraintLayout cl_top_btn = (ConstraintLayout) GameWebActivity.this._$_findCachedViewById(R$id.cl_top_btn);
                    Intrinsics.checkExpressionValueIsNotNull(cl_top_btn, "cl_top_btn");
                    cl_top_btn.setAlpha(1.0f);
                    ((ImageView) GameWebActivity.this._$_findCachedViewById(R$id.iv_btn_open)).setImageResource(R.drawable.game_web_arrow_open);
                    ImageView iv_close_activity = (ImageView) GameWebActivity.this._$_findCachedViewById(R$id.iv_close_activity);
                    Intrinsics.checkExpressionValueIsNotNull(iv_close_activity, "iv_close_activity");
                    iv_close_activity.setVisibility(8);
                    ImageView iv_refresh = (ImageView) GameWebActivity.this._$_findCachedViewById(R$id.iv_refresh);
                    Intrinsics.checkExpressionValueIsNotNull(iv_refresh, "iv_refresh");
                    iv_refresh.setVisibility(8);
                    ImageView iv_exchange = (ImageView) GameWebActivity.this._$_findCachedViewById(R$id.iv_exchange);
                    Intrinsics.checkExpressionValueIsNotNull(iv_exchange, "iv_exchange");
                    iv_exchange.setVisibility(8);
                    GameWebActivity.this.openBtn = false;
                    return;
                }
                ((ConstraintLayout) GameWebActivity.this._$_findCachedViewById(R$id.cl_top_btn)).setBackgroundResource(R.drawable.game_web_btn_bg);
                ConstraintLayout cl_top_btn2 = (ConstraintLayout) GameWebActivity.this._$_findCachedViewById(R$id.cl_top_btn);
                Intrinsics.checkExpressionValueIsNotNull(cl_top_btn2, "cl_top_btn");
                cl_top_btn2.setAlpha(0.5f);
                ((ImageView) GameWebActivity.this._$_findCachedViewById(R$id.iv_btn_open)).setImageResource(R.drawable.game_web_arrow_close);
                ImageView iv_close_activity2 = (ImageView) GameWebActivity.this._$_findCachedViewById(R$id.iv_close_activity);
                Intrinsics.checkExpressionValueIsNotNull(iv_close_activity2, "iv_close_activity");
                iv_close_activity2.setVisibility(0);
                ImageView iv_refresh2 = (ImageView) GameWebActivity.this._$_findCachedViewById(R$id.iv_refresh);
                Intrinsics.checkExpressionValueIsNotNull(iv_refresh2, "iv_refresh");
                iv_refresh2.setVisibility(0);
                ImageView iv_exchange2 = (ImageView) GameWebActivity.this._$_findCachedViewById(R$id.iv_exchange);
                Intrinsics.checkExpressionValueIsNotNull(iv_exchange2, "iv_exchange");
                iv_exchange2.setVisibility(0);
                GameWebActivity.this.openBtn = true;
            }
        });
        ((ImageView) _$_findCachedViewById(R$id.iv_close_activity)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.game.view.GameWebActivity$addListener$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                GameWebActivity.this.onBackPressed();
            }
        });
        ((ImageView) _$_findCachedViewById(R$id.iv_refresh)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.game.view.GameWebActivity$addListener$3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ((WebView) GameWebActivity.this._$_findCachedViewById(R$id.webView)).reload();
            }
        });
        ((ImageView) _$_findCachedViewById(R$id.iv_exchange)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.game.view.GameWebActivity$addListener$4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RechargeAccount rechargeAccount;
                rechargeAccount = GameWebActivity.this.rechargeAccount;
                if (rechargeAccount != null) {
                    GameWebActivity.this.showRechargeExGameDialog();
                }
            }
        });
    }

    private final void getRechargeAccountBalance(boolean z) {
        if (z) {
            showWaitingDialog();
        }
        ApiImplService.Companion.getApiImplService().requestRechargeAccount(DBUtil.getMemberId()).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<RechargeAccount>() { // from class: com.one.tomato.mvp.ui.game.view.GameWebActivity$getRechargeAccountBalance$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(RechargeAccount bean) {
                Intrinsics.checkParameterIsNotNull(bean, "bean");
                GameWebActivity.this.hideWaitingDialog();
                GameWebActivity.this.rechargeAccount = bean;
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable e) {
                Intrinsics.checkParameterIsNotNull(e, "e");
                GameWebActivity.this.hideWaitingDialog();
            }
        });
    }

    private final void getRechargeExGameBalance(String str) {
        ApiImplService.Companion.getApiImplService().requestGameBalance(DBUtil.getMemberId(), str).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<GameBalance>() { // from class: com.one.tomato.mvp.ui.game.view.GameWebActivity$getRechargeExGameBalance$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable e) {
                Intrinsics.checkParameterIsNotNull(e, "e");
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(GameBalance bean) {
                RechargeExGame rechargeExGame;
                Intrinsics.checkParameterIsNotNull(bean, "bean");
                rechargeExGame = GameWebActivity.this.rechargeExGame;
                if (rechargeExGame != null) {
                    rechargeExGame.balance = bean.balance;
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showRechargeExGameDialog() {
        new RechargeExGameDialog(this, 2, this.rechargeAccount, this.rechargeExGame, 2, this).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.one.tomato.mvp.ui.game.view.GameWebActivity$showRechargeExGameDialog$1
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                GameWebActivity.this.requestData();
            }
        });
    }

    @Override // com.one.tomato.dialog.RechargeExGameDialog.RechargeExGameListener
    public void rechargeExGameStart() {
        showWaitingDialog();
    }

    @Override // com.one.tomato.dialog.RechargeExGameDialog.RechargeExGameListener
    public void rechargeExGameSuccess(RechargeExGame rechargeExGame, int i, String inputStr) {
        Intrinsics.checkParameterIsNotNull(rechargeExGame, "rechargeExGame");
        Intrinsics.checkParameterIsNotNull(inputStr, "inputStr");
        hideWaitingDialog();
        requestData();
    }

    @Override // com.one.tomato.dialog.RechargeExGameDialog.RechargeExGameListener
    public void rechargeExGameFail() {
        hideWaitingDialog();
    }

    @Override // com.one.tomato.mvp.base.view.BaseHtmlActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        if (((WebView) _$_findCachedViewById(R$id.webView)) != null) {
            ((WebView) _$_findCachedViewById(R$id.webView)).destroy();
        }
        finish();
    }
}
