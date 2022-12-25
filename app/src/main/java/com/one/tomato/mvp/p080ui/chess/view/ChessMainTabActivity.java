package com.one.tomato.mvp.p080ui.chess.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.p002v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.entity.MainNotifyBean;
import com.one.tomato.entity.p079db.LoginInfo;
import com.one.tomato.mvp.base.AppManager;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.chess.dialog.ChessAdNoticeDialog;
import com.one.tomato.mvp.p080ui.chess.dialog.ChessNoticeDialog;
import com.one.tomato.utils.AppInitUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.DataUploadUtil;
import com.one.tomato.utils.PreferencesUtil;
import com.one.tomato.utils.RxUtils;
import com.one.tomato.utils.TimerTaskUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.UpdateManager;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONObject;

/* compiled from: ChessMainTabActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.chess.view.ChessMainTabActivity */
/* loaded from: classes3.dex */
public final class ChessMainTabActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    private static final int TAB_ITEM_HOME = 0;
    private HashMap _$_findViewCache;
    private ChessAppMarketFragment chessAppMarketFragment;
    private ChessAppShareFragment chessAppShareFragment;
    private ChessHomeFragment chessHomeFragment;
    private ChessMineFragment chessMineTabFragment;
    private ChessRechargeFragment chessRechargeFragment;
    private boolean exitApp;
    private int tabItem = TAB_ITEM_HOME;
    public static final Companion Companion = new Companion(null);
    private static final String INTENT_KEY_TABITEM = INTENT_KEY_TABITEM;
    private static final String INTENT_KEY_TABITEM = INTENT_KEY_TABITEM;
    private static final int TAB_ITEM_RECHARGE = 1;
    private static final int TAB_ITEM_APP_MARKET = 2;
    private static final int TAB_ITEM_APP_SHARE = 3;
    private static final int TAB_ITEM_MINE = 4;

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
        return R.layout.activity_chess_main_tab;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    /* compiled from: ChessMainTabActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.chess.view.ChessMainTabActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final String getINTENT_KEY_TABITEM() {
            return ChessMainTabActivity.INTENT_KEY_TABITEM;
        }

        public final int getTAB_ITEM_HOME() {
            return ChessMainTabActivity.TAB_ITEM_HOME;
        }

        public final int getTAB_ITEM_RECHARGE() {
            return ChessMainTabActivity.TAB_ITEM_RECHARGE;
        }

        public final int getTAB_ITEM_APP_MARKET() {
            return ChessMainTabActivity.TAB_ITEM_APP_MARKET;
        }

        public final int getTAB_ITEM_APP_SHARE() {
            return ChessMainTabActivity.TAB_ITEM_APP_SHARE;
        }

        public final int getTAB_ITEM_MINE() {
            return ChessMainTabActivity.TAB_ITEM_MINE;
        }

        public final void startActivity(Context context, int i) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            Intent intent = new Intent();
            intent.setClass(context, ChessMainTabActivity.class);
            intent.putExtra(getINTENT_KEY_TABITEM(), i);
            context.startActivity(intent);
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        UpdateManager.getUpdateManager().checkAppUpdate(this, false);
        if (getIntent() != null) {
            Intent intent = getIntent();
            Intrinsics.checkExpressionValueIsNotNull(intent, "intent");
            if (intent.getExtras() != null) {
                Intent intent2 = getIntent();
                Intrinsics.checkExpressionValueIsNotNull(intent2, "intent");
                Bundle extras = intent2.getExtras();
                if (extras == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                this.tabItem = extras.getInt(INTENT_KEY_TABITEM);
            }
        }
        selectTab(this.tabItem);
        addListener();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        queryMyMessage();
        ((LinearLayout) _$_findCachedViewById(R$id.ll_home)).postDelayed(new Runnable() { // from class: com.one.tomato.mvp.ui.chess.view.ChessMainTabActivity$initData$1
            @Override // java.lang.Runnable
            public final void run() {
                ChessMainTabActivity.this.requestChessMainNotify();
            }
        }, 1000L);
    }

    private final void addListener() {
        ((LinearLayout) _$_findCachedViewById(R$id.ll_home)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.chess.view.ChessMainTabActivity$addListener$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChessMainTabActivity.this.selectTab(ChessMainTabActivity.Companion.getTAB_ITEM_HOME());
            }
        });
        ((LinearLayout) _$_findCachedViewById(R$id.ll_recharge)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.chess.view.ChessMainTabActivity$addListener$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChessMainTabActivity.this.selectTab(ChessMainTabActivity.Companion.getTAB_ITEM_RECHARGE());
            }
        });
        ((LinearLayout) _$_findCachedViewById(R$id.ll_app_market)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.chess.view.ChessMainTabActivity$addListener$3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChessMainTabActivity.this.selectTab(ChessMainTabActivity.Companion.getTAB_ITEM_APP_MARKET());
            }
        });
        ((LinearLayout) _$_findCachedViewById(R$id.ll_app_share)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.chess.view.ChessMainTabActivity$addListener$4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChessMainTabActivity.this.selectTab(ChessMainTabActivity.Companion.getTAB_ITEM_APP_SHARE());
            }
        });
        ((FrameLayout) _$_findCachedViewById(R$id.ll_mine)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.chess.view.ChessMainTabActivity$addListener$5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChessMainTabActivity.this.selectTab(ChessMainTabActivity.Companion.getTAB_ITEM_MINE());
            }
        });
    }

    public final void selectTab(int i) {
        hideAllFragment();
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        Intrinsics.checkExpressionValueIsNotNull(beginTransaction, "supportFragmentManager.beginTransaction()");
        changeTabStatus(i);
        if (i == TAB_ITEM_HOME) {
            ChessHomeFragment chessHomeFragment = this.chessHomeFragment;
            if (chessHomeFragment == null) {
                this.chessHomeFragment = new ChessHomeFragment();
                ChessHomeFragment chessHomeFragment2 = this.chessHomeFragment;
                if (chessHomeFragment2 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                beginTransaction.add(R.id.fl_content, chessHomeFragment2);
            } else if (chessHomeFragment == null) {
                Intrinsics.throwNpe();
                throw null;
            } else {
                beginTransaction.show(chessHomeFragment);
            }
        } else if (i == TAB_ITEM_RECHARGE) {
            ChessRechargeFragment chessRechargeFragment = this.chessRechargeFragment;
            if (chessRechargeFragment == null) {
                this.chessRechargeFragment = new ChessRechargeFragment();
                ChessRechargeFragment chessRechargeFragment2 = this.chessRechargeFragment;
                if (chessRechargeFragment2 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                beginTransaction.add(R.id.fl_content, chessRechargeFragment2);
            } else if (chessRechargeFragment == null) {
                Intrinsics.throwNpe();
                throw null;
            } else {
                beginTransaction.show(chessRechargeFragment);
            }
        } else if (i == TAB_ITEM_APP_MARKET) {
            ChessAppMarketFragment chessAppMarketFragment = this.chessAppMarketFragment;
            if (chessAppMarketFragment == null) {
                this.chessAppMarketFragment = new ChessAppMarketFragment();
                ChessAppMarketFragment chessAppMarketFragment2 = this.chessAppMarketFragment;
                if (chessAppMarketFragment2 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                beginTransaction.add(R.id.fl_content, chessAppMarketFragment2);
            } else if (chessAppMarketFragment == null) {
                Intrinsics.throwNpe();
                throw null;
            } else {
                beginTransaction.show(chessAppMarketFragment);
            }
        } else if (i == TAB_ITEM_APP_SHARE) {
            ChessAppShareFragment chessAppShareFragment = this.chessAppShareFragment;
            if (chessAppShareFragment == null) {
                this.chessAppShareFragment = new ChessAppShareFragment();
                ChessAppShareFragment chessAppShareFragment2 = this.chessAppShareFragment;
                if (chessAppShareFragment2 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                beginTransaction.add(R.id.fl_content, chessAppShareFragment2);
            } else if (chessAppShareFragment == null) {
                Intrinsics.throwNpe();
                throw null;
            } else {
                beginTransaction.show(chessAppShareFragment);
            }
        } else if (i == TAB_ITEM_MINE) {
            ChessMineFragment chessMineFragment = this.chessMineTabFragment;
            if (chessMineFragment == null) {
                this.chessMineTabFragment = new ChessMineFragment();
                ChessMineFragment chessMineFragment2 = this.chessMineTabFragment;
                if (chessMineFragment2 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                beginTransaction.add(R.id.fl_content, chessMineFragment2);
            } else if (chessMineFragment == null) {
                Intrinsics.throwNpe();
                throw null;
            } else {
                beginTransaction.show(chessMineFragment);
            }
        }
        beginTransaction.commitAllowingStateLoss();
        if (!PreferencesUtil.getInstance().getBoolean("need_upgrade") || UpdateManager.cancelUpdate) {
            return;
        }
        UpdateManager.getUpdateManager().checkAppUpdate(this, false);
    }

    private final void changeTabStatus(int i) {
        clearTabStatus();
        if (i == TAB_ITEM_HOME) {
            ((ImageView) _$_findCachedViewById(R$id.iv_home)).setSelected(true);
            ((TextView) _$_findCachedViewById(R$id.tv_home)).setTextColor(getResources().getColor(R.color.white));
        } else if (i == TAB_ITEM_RECHARGE) {
            ((ImageView) _$_findCachedViewById(R$id.iv_recharge)).setSelected(true);
            ((TextView) _$_findCachedViewById(R$id.tv_recharge)).setTextColor(getResources().getColor(R.color.white));
        } else if (i == TAB_ITEM_APP_MARKET) {
            ((ImageView) _$_findCachedViewById(R$id.iv_app_market)).setSelected(true);
            ((TextView) _$_findCachedViewById(R$id.tv_app_market)).setTextColor(getResources().getColor(R.color.white));
        } else if (i == TAB_ITEM_APP_SHARE) {
            ((ImageView) _$_findCachedViewById(R$id.iv_app_share)).setSelected(true);
            ((TextView) _$_findCachedViewById(R$id.tv_app_share)).setTextColor(getResources().getColor(R.color.white));
        } else if (i != TAB_ITEM_MINE) {
        } else {
            ImageView iv_mine = (ImageView) _$_findCachedViewById(R$id.iv_mine);
            Intrinsics.checkExpressionValueIsNotNull(iv_mine, "iv_mine");
            iv_mine.setSelected(true);
            ((TextView) _$_findCachedViewById(R$id.tv_mine)).setTextColor(getResources().getColor(R.color.white));
        }
    }

    private final void clearTabStatus() {
        ((ImageView) _$_findCachedViewById(R$id.iv_home)).setSelected(false);
        ((TextView) _$_findCachedViewById(R$id.tv_home)).setTextColor(getResources().getColor(R.color.chess_main_tab_text));
        ((ImageView) _$_findCachedViewById(R$id.iv_recharge)).setSelected(false);
        ((TextView) _$_findCachedViewById(R$id.tv_recharge)).setTextColor(getResources().getColor(R.color.chess_main_tab_text));
        ((ImageView) _$_findCachedViewById(R$id.iv_app_market)).setSelected(false);
        ((TextView) _$_findCachedViewById(R$id.tv_app_market)).setTextColor(getResources().getColor(R.color.chess_main_tab_text));
        ((ImageView) _$_findCachedViewById(R$id.iv_app_share)).setSelected(false);
        ((TextView) _$_findCachedViewById(R$id.tv_app_share)).setTextColor(getResources().getColor(R.color.chess_main_tab_text));
        ((ImageView) _$_findCachedViewById(R$id.iv_mine)).setSelected(false);
        ((TextView) _$_findCachedViewById(R$id.tv_mine)).setTextColor(getResources().getColor(R.color.chess_main_tab_text));
    }

    private final void hideAllFragment() {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        Intrinsics.checkExpressionValueIsNotNull(beginTransaction, "supportFragmentManager.beginTransaction()");
        ChessHomeFragment chessHomeFragment = this.chessHomeFragment;
        if (chessHomeFragment != null) {
            if (chessHomeFragment != null) {
                if (chessHomeFragment.isVisible()) {
                    ChessHomeFragment chessHomeFragment2 = this.chessHomeFragment;
                    if (chessHomeFragment2 == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                    beginTransaction.hide(chessHomeFragment2);
                }
            } else {
                Intrinsics.throwNpe();
                throw null;
            }
        }
        ChessRechargeFragment chessRechargeFragment = this.chessRechargeFragment;
        if (chessRechargeFragment != null) {
            if (chessRechargeFragment != null) {
                if (chessRechargeFragment.isVisible()) {
                    ChessRechargeFragment chessRechargeFragment2 = this.chessRechargeFragment;
                    if (chessRechargeFragment2 == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                    beginTransaction.hide(chessRechargeFragment2);
                }
            } else {
                Intrinsics.throwNpe();
                throw null;
            }
        }
        ChessAppMarketFragment chessAppMarketFragment = this.chessAppMarketFragment;
        if (chessAppMarketFragment != null) {
            if (chessAppMarketFragment != null) {
                if (chessAppMarketFragment.isVisible()) {
                    ChessAppMarketFragment chessAppMarketFragment2 = this.chessAppMarketFragment;
                    if (chessAppMarketFragment2 == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                    beginTransaction.hide(chessAppMarketFragment2);
                }
            } else {
                Intrinsics.throwNpe();
                throw null;
            }
        }
        ChessAppShareFragment chessAppShareFragment = this.chessAppShareFragment;
        if (chessAppShareFragment != null) {
            if (chessAppShareFragment != null) {
                if (chessAppShareFragment.isVisible()) {
                    ChessAppShareFragment chessAppShareFragment2 = this.chessAppShareFragment;
                    if (chessAppShareFragment2 == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                    beginTransaction.hide(chessAppShareFragment2);
                }
            } else {
                Intrinsics.throwNpe();
                throw null;
            }
        }
        ChessMineFragment chessMineFragment = this.chessMineTabFragment;
        if (chessMineFragment != null) {
            if (chessMineFragment != null) {
                if (chessMineFragment.isVisible()) {
                    ChessMineFragment chessMineFragment2 = this.chessMineTabFragment;
                    if (chessMineFragment2 == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                    beginTransaction.hide(chessMineFragment2);
                }
            } else {
                Intrinsics.throwNpe();
                throw null;
            }
        }
        beginTransaction.commitAllowingStateLoss();
    }

    private final void queryMyMessage() {
        ApiImplService.Companion.getApiImplService().queryMyMessage(DBUtil.getMemberId()).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<String>() { // from class: com.one.tomato.mvp.ui.chess.view.ChessMainTabActivity$queryMyMessage$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(String str) {
                if (!TextUtils.isEmpty(str)) {
                    try {
                        JSONObject jSONObject = new JSONObject(str);
                        int i = jSONObject.getInt("1");
                        int i2 = jSONObject.getInt("2");
                        int i3 = jSONObject.getInt("4");
                        int i4 = jSONObject.getInt("6");
                        int i5 = jSONObject.getInt("8");
                        int i6 = jSONObject.getInt("9");
                        int i7 = i + i2 + i3 + i4 + i5 + i6 + jSONObject.getInt("10") + jSONObject.getInt("3");
                        if (i7 == 0) {
                            return;
                        }
                        BaseApplication.setIsMyMessageHave(i7);
                        ChessMainTabActivity.this._$_findCachedViewById(R$id.main_red_point).setVisibility(0);
                    } catch (Exception unused) {
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void requestChessMainNotify() {
        ApiImplService.Companion.getApiImplService().requestReviewNotice("7").compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<ArrayList<MainNotifyBean>>() { // from class: com.one.tomato.mvp.ui.chess.view.ChessMainTabActivity$requestChessMainNotify$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<MainNotifyBean> arrayList) {
                Context mContext;
                if (arrayList == null || !(!arrayList.isEmpty())) {
                    ChessMainTabActivity.this.showAdNoticeDialog();
                } else if (PreferencesUtil.getInstance().getBoolean("need_upgrade")) {
                } else {
                    mContext = ChessMainTabActivity.this.getMContext();
                    new ChessNoticeDialog(mContext, arrayList.get(0));
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                ChessMainTabActivity.this.showAdNoticeDialog();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showAdNoticeDialog() {
        if (!PreferencesUtil.getInstance().getBoolean("need_upgrade")) {
            LoginInfo loginInfo = DBUtil.getLoginInfo();
            Intrinsics.checkExpressionValueIsNotNull(loginInfo, "DBUtil.getLoginInfo()");
            if (loginInfo.isLogin()) {
                return;
            }
            new ChessAdNoticeDialog(getMContext());
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (DBUtil.getMemberId() == 0) {
            AppInitUtil.initAppInfoFromServer(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        TimerTaskUtil.getInstance().onStop();
    }

    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        if (this.exitApp) {
            AppManager.getAppManager().exitApp();
            return;
        }
        ToastUtil.showCenterToast((int) R.string.app_exit);
        this.exitApp = true;
        DataUploadUtil.uploadUseTime();
        TimerTaskUtil.getInstance().onStart(2, new TimerTaskUtil.TimeTask() { // from class: com.one.tomato.mvp.ui.chess.view.ChessMainTabActivity$onBackPressed$1
            @Override // com.one.tomato.utils.TimerTaskUtil.TimeTask
            public void position(int i) {
            }

            @Override // com.one.tomato.utils.TimerTaskUtil.TimeTask
            public void stop() {
                ChessMainTabActivity.this.exitApp = false;
            }
        });
    }
}
