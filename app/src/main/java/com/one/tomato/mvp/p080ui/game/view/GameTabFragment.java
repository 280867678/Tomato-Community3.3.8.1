package com.one.tomato.mvp.p080ui.game.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.R$id;
import com.one.tomato.entity.C2516Ad;
import com.one.tomato.entity.GameBGLoginBean;
import com.one.tomato.entity.GameBingBingToken;
import com.one.tomato.entity.GameDaShengH5;
import com.one.tomato.entity.GameDaShengWebapp;
import com.one.tomato.entity.p079db.AdPage;
import com.one.tomato.entity.p079db.GameRegisterUserBean;
import com.one.tomato.entity.p079db.GameTypeData;
import com.one.tomato.entity.p079db.SubGamesBean;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.view.MvpBaseFragment;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.game.adapter.GameCenterHorAdapter;
import com.one.tomato.mvp.p080ui.game.impl.IGameContact$IGameView;
import com.one.tomato.mvp.p080ui.game.presenter.GamePresenter;
import com.one.tomato.mvp.p080ui.login.view.LoginActivity;
import com.one.tomato.p085ui.recharge.RechargeActivity;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.DisplayMetricsUtils;
import com.one.tomato.utils.GameUtils;
import com.one.tomato.utils.ImmersionBarUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.RxUtils;
import com.tomatolive.library.utils.LogConstants;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import kotlin.Standard;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: GameTabFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.game.view.GameTabFragment */
/* loaded from: classes3.dex */
public final class GameTabFragment extends MvpBaseFragment<IGameContact$IGameView, GamePresenter> implements IGameContact$IGameView {
    private HashMap _$_findViewCache;
    private SubGamesBean curGameBean;
    private GameCenterHorAdapter gameHorAdapter;
    private boolean startWebApp;
    private SubGamesBean startWebAppGameBean;
    private final String JsMsgAc = LogConstants.FOLLOW_OPERATION_TYPE;
    private final String JsMsgHandlerName = "messageHandlers";
    private final String JsMsgAcKey = "change_webview_frame";
    private final String JsMsgAcKeyUrl = "open_url";
    private final String JsMsgAcValueUrl = "url";
    private final String JsMsgAcKeyBalance = "live_money_changed";
    private final String JSMsgAcOpenRecharge = "live_open_recharge";
    private final String JSMsgAcOpenLogin = "live_open_login";
    private final ArrayList<String> gameRegisterUserBean = new ArrayList<>();

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view == null) {
            View view2 = getView();
            if (view2 == null) {
                return null;
            }
            View findViewById = view2.findViewById(i);
            this._$_findViewCache.put(Integer.valueOf(i), findViewById);
            return findViewById;
        }
        return view;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public int createLayoutID() {
        return R.layout.fragment_game_tab;
    }

    @Override // com.one.tomato.mvp.p080ui.game.impl.IGameContact$IGameView
    public void handleList(ArrayList<AdPage> list) {
        Intrinsics.checkParameterIsNotNull(list, "list");
    }

    @Override // com.one.tomato.mvp.p080ui.game.impl.IGameContact$IGameView
    public void handlerGameType(ArrayList<GameTypeData> list) {
        Intrinsics.checkParameterIsNotNull(list, "list");
    }

    @Override // com.one.tomato.mvp.base.view.IBaseView
    public void onError(String str) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final boolean webViewOverrideUrlLoading(WebView view, String url) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        Intrinsics.checkParameterIsNotNull(url, "url");
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void webViewPageFinished() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void webViewPageStarted() {
    }

    public GameTabFragment() {
        new WebChromeClient() { // from class: com.one.tomato.mvp.ui.game.view.GameTabFragment$webChromeClient$1
            @Override // android.webkit.WebChromeClient
            public void onProgressChanged(WebView view, int i) {
                Intrinsics.checkParameterIsNotNull(view, "view");
            }

            @Override // android.webkit.WebChromeClient
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> uploadMsg, WebChromeClient.FileChooserParams fileChooserParams) {
                Intrinsics.checkParameterIsNotNull(webView, "webView");
                Intrinsics.checkParameterIsNotNull(uploadMsg, "uploadMsg");
                Intrinsics.checkParameterIsNotNull(fileChooserParams, "fileChooserParams");
                return true;
            }

            @Override // android.webkit.WebChromeClient
            public void onReceivedTitle(WebView view, String title) {
                Intrinsics.checkParameterIsNotNull(view, "view");
                Intrinsics.checkParameterIsNotNull(title, "title");
                super.onReceivedTitle(view, title);
            }

            @Override // android.webkit.WebChromeClient
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                Intrinsics.checkParameterIsNotNull(origin, "origin");
                Intrinsics.checkParameterIsNotNull(callback, "callback");
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }
        };
        new WebViewClient() { // from class: com.one.tomato.mvp.ui.game.view.GameTabFragment$webViewClient$1
            @Override // android.webkit.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Intrinsics.checkParameterIsNotNull(view, "view");
                Intrinsics.checkParameterIsNotNull(url, "url");
                return GameTabFragment.this.webViewOverrideUrlLoading(view, url);
            }

            @Override // android.webkit.WebViewClient
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Intrinsics.checkParameterIsNotNull(view, "view");
                Intrinsics.checkParameterIsNotNull(url, "url");
                Intrinsics.checkParameterIsNotNull(favicon, "favicon");
                super.onPageStarted(view, url, favicon);
                GameTabFragment.this.webViewPageStarted();
            }

            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView view, String url) {
                Intrinsics.checkParameterIsNotNull(view, "view");
                Intrinsics.checkParameterIsNotNull(url, "url");
                super.onPageFinished(view, url);
                GameTabFragment.this.webViewPageFinished();
            }

            @Override // android.webkit.WebViewClient
            public void onReceivedError(WebView view, int i, String description, String failingUrl) {
                Intrinsics.checkParameterIsNotNull(view, "view");
                Intrinsics.checkParameterIsNotNull(description, "description");
                Intrinsics.checkParameterIsNotNull(failingUrl, "failingUrl");
                super.onReceivedError(view, i, description, failingUrl);
            }

            @Override // android.webkit.WebViewClient
            public void onFormResubmission(WebView view, Message dontResend, Message resend) {
                Intrinsics.checkParameterIsNotNull(view, "view");
                Intrinsics.checkParameterIsNotNull(dontResend, "dontResend");
                Intrinsics.checkParameterIsNotNull(resend, "resend");
                resend.sendToTarget();
            }

            @Override // android.webkit.WebViewClient
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                Intrinsics.checkParameterIsNotNull(view, "view");
                Intrinsics.checkParameterIsNotNull(handler, "handler");
                Intrinsics.checkParameterIsNotNull(error, "error");
                handler.proceed();
            }
        };
    }

    @Override // com.one.tomato.mvp.p080ui.game.impl.IGameContact$IGameView
    public void handlerBGLogin(GameBGLoginBean gameBGLoginBean, SubGamesBean subGamesBean) {
        Intrinsics.checkParameterIsNotNull(subGamesBean, "subGamesBean");
        throw new Standard("An operation is not implemented: not implemented");
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    /* renamed from: createPresenter  reason: collision with other method in class */
    public GamePresenter mo6441createPresenter() {
        return new GamePresenter();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void initView() {
        View view = getView();
        ImmersionBarUtil.setFragmentTitleBar(this, view != null ? view.findViewById(R.id.title_status_bar) : null);
        initWebView();
        initH5Game();
        initNotice();
        initBottomSheetDialog();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onResume() {
        SubGamesBean subGamesBean;
        super.onResume();
        if (!this.startWebApp || (subGamesBean = this.startWebAppGameBean) == null) {
            return;
        }
        GameUtils gameUtils = GameUtils.INSTANCE;
        if (subGamesBean == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        gameUtils.gameToPurse(subGamesBean);
        this.startWebApp = false;
        this.startWebAppGameBean = null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void inintData() {
        setUserVisibleHint(true);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void onLazyLoad() {
        super.onLazyLoad();
        requestList();
    }

    private final void requestList() {
        new HashMap().put("memberId", Integer.valueOf(DBUtil.getMemberId()));
    }

    private final void initH5Game() {
        this.gameHorAdapter = new GameCenterHorAdapter();
        RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(R$id.game_recycler);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), 0, false));
        }
        RecyclerView recyclerView2 = (RecyclerView) _$_findCachedViewById(R$id.game_recycler);
        if (recyclerView2 != null) {
            recyclerView2.setAdapter(this.gameHorAdapter);
        }
        ArrayList<SubGamesBean> postGameBean = DBUtil.getPostGameBean(C2516Ad.TYPE_ARTICLE_REC);
        if (!(postGameBean == null || postGameBean.isEmpty())) {
            SubGamesBean subGamesBean = postGameBean.get(0);
            Intrinsics.checkExpressionValueIsNotNull(subGamesBean, "adPage[0]");
            subGamesBean.setSelector(true);
            GameCenterHorAdapter gameCenterHorAdapter = this.gameHorAdapter;
            if (gameCenterHorAdapter != null) {
                gameCenterHorAdapter.setNewData(postGameBean);
            }
            GameCenterHorAdapter gameCenterHorAdapter2 = this.gameHorAdapter;
            if (gameCenterHorAdapter2 != null) {
                gameCenterHorAdapter2.setEnableLoadMore(false);
            }
            GameCenterHorAdapter gameCenterHorAdapter3 = this.gameHorAdapter;
            if (gameCenterHorAdapter3 == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            SubGamesBean subGamesBean2 = gameCenterHorAdapter3.getData().get(0);
            Intrinsics.checkExpressionValueIsNotNull(subGamesBean2, "gameHorAdapter!!.data[0]");
            loadGame(subGamesBean2);
        }
        GameCenterHorAdapter gameCenterHorAdapter4 = this.gameHorAdapter;
        if (gameCenterHorAdapter4 != null) {
            gameCenterHorAdapter4.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.game.view.GameTabFragment$initH5Game$1
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter<Object, BaseViewHolder> baseQuickAdapter, View view, int i) {
                    GameCenterHorAdapter gameCenterHorAdapter5;
                    GameCenterHorAdapter gameCenterHorAdapter6;
                    GameCenterHorAdapter gameCenterHorAdapter7;
                    gameCenterHorAdapter5 = GameTabFragment.this.gameHorAdapter;
                    if (gameCenterHorAdapter5 == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                    List<SubGamesBean> data = gameCenterHorAdapter5.getData();
                    Intrinsics.checkExpressionValueIsNotNull(data, "gameHorAdapter!!.data");
                    int size = data.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        if (i == i2) {
                            SubGamesBean subGamesBean3 = data.get(i);
                            Intrinsics.checkExpressionValueIsNotNull(subGamesBean3, "it[position]");
                            subGamesBean3.setSelector(true);
                        } else {
                            SubGamesBean subGamesBean4 = data.get(i2);
                            Intrinsics.checkExpressionValueIsNotNull(subGamesBean4, "it[its]");
                            subGamesBean4.setSelector(false);
                        }
                    }
                    gameCenterHorAdapter6 = GameTabFragment.this.gameHorAdapter;
                    if (gameCenterHorAdapter6 == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                    gameCenterHorAdapter6.notifyDataSetChanged();
                    RecyclerView recyclerView3 = (RecyclerView) GameTabFragment.this._$_findCachedViewById(R$id.game_recycler);
                    if (recyclerView3 != null) {
                        recyclerView3.smoothScrollToPosition(i);
                    }
                    GameTabFragment gameTabFragment = GameTabFragment.this;
                    gameCenterHorAdapter7 = gameTabFragment.gameHorAdapter;
                    if (gameCenterHorAdapter7 == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                    SubGamesBean subGamesBean5 = gameCenterHorAdapter7.getData().get(i);
                    Intrinsics.checkExpressionValueIsNotNull(subGamesBean5, "gameHorAdapter!!.data[position]");
                    gameTabFragment.loadGame(subGamesBean5);
                }
            });
        }
    }

    private final void initBottomSheetDialog() {
        ViewTreeObserver viewTreeObserver;
        FrameLayout frameLayout = (FrameLayout) _$_findCachedViewById(R$id.container);
        if (frameLayout == null || (viewTreeObserver = frameLayout.getViewTreeObserver()) == null) {
            return;
        }
        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.one.tomato.mvp.ui.game.view.GameTabFragment$initBottomSheetDialog$1
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                ViewTreeObserver viewTreeObserver2;
                FrameLayout frameLayout2 = (FrameLayout) GameTabFragment.this._$_findCachedViewById(R$id.container);
                if (frameLayout2 != null && (viewTreeObserver2 = frameLayout2.getViewTreeObserver()) != null) {
                    viewTreeObserver2.removeOnPreDrawListener(this);
                }
                FrameLayout frameLayout3 = (FrameLayout) GameTabFragment.this._$_findCachedViewById(R$id.container);
                if (frameLayout3 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                int height = frameLayout3.getHeight();
                float dp2px = height - DisplayMetricsUtils.dp2px(110.0f);
                GameBottomSheetFragment gameBottomSheetFragment = new GameBottomSheetFragment();
                gameBottomSheetFragment.setPeekHeight((int) dp2px, height);
                GameTabFragment.this.getChildFragmentManager().beginTransaction().add(R.id.container, gameBottomSheetFragment).commitAllowingStateLoss();
                return true;
            }
        });
    }

    private final void initNotice() {
        final GameRegisterUserBean gameRegisterBean = DBUtil.getGameRegisterBean();
        if (gameRegisterBean != null) {
            ArrayList<String> memberNames = gameRegisterBean.getMemberNames();
            if (!(memberNames == null || memberNames.isEmpty())) {
                this.gameRegisterUserBean.addAll(memberNames);
                startScrollNotice();
            }
        }
        ApiImplService.Companion.getApiImplService().queryGameUser().compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<GameRegisterUserBean>() { // from class: com.one.tomato.mvp.ui.game.view.GameTabFragment$initNotice$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(GameRegisterUserBean gameRegisterUserBean) {
                ArrayList arrayList;
                if (gameRegisterUserBean != null) {
                    ArrayList<String> memberNames2 = gameRegisterUserBean.getMemberNames();
                    boolean z = false;
                    if (memberNames2 == null || memberNames2.isEmpty()) {
                        return;
                    }
                    arrayList = GameTabFragment.this.gameRegisterUserBean;
                    arrayList.addAll(memberNames2);
                    GameRegisterUserBean gameRegisterUserBean2 = gameRegisterBean;
                    ArrayList<String> memberNames3 = gameRegisterUserBean2 != null ? gameRegisterUserBean2.getMemberNames() : null;
                    if (memberNames3 == null || memberNames3.isEmpty()) {
                        z = true;
                    }
                    if (z) {
                        GameTabFragment.this.startScrollNotice();
                    }
                    DBUtil.saveGameRegisterBean(gameRegisterUserBean);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                LogUtil.m3788d(":");
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void startScrollNotice() {
        ArrayList<String> arrayList = this.gameRegisterUserBean;
        boolean z = false;
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        GameCenterHorAdapter gameCenterHorAdapter = this.gameHorAdapter;
        List<SubGamesBean> data = gameCenterHorAdapter != null ? gameCenterHorAdapter.getData() : null;
        if (data == null || data.isEmpty()) {
            z = true;
        }
        if (z) {
            return;
        }
        Observable.interval(0L, 15L, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new GameTabFragment$startScrollNotice$subscribe$1(this, data));
    }

    @SuppressLint({"SetJavaScriptEnabled", "ResourceType"})
    private final void initWebView() {
        WebSettings webSettings = ((WebView) _$_findCachedViewById(R$id.web_view)).getSettings();
        webSettings.setAllowFileAccess(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        Intrinsics.checkExpressionValueIsNotNull(webSettings, "webSettings");
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(2);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setBlockNetworkImage(false);
        webSettings.setGeolocationEnabled(true);
        webSettings.setDomStorageEnabled(true);
        WebView webView = (WebView) _$_findCachedViewById(R$id.web_view);
        Context mContext = getMContext();
        if (mContext == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        webView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_737373));
        ((WebView) _$_findCachedViewById(R$id.web_view)).addJavascriptInterface(this, this.JsMsgHandlerName);
        if (Build.VERSION.SDK_INT < 21) {
            return;
        }
        ((WebView) _$_findCachedViewById(R$id.web_view)).getSettings().setMixedContentMode(0);
    }

    @JavascriptInterface
    public final double receiveMessageFromJS(String jsonStr) {
        Intrinsics.checkParameterIsNotNull(jsonStr, "jsonStr");
        try {
            LogUtil.m3784i("游戏Dialog，js调取java方法传递的json ：" + jsonStr);
            JSONObject jSONObject = new JSONObject(jsonStr);
            String string = jSONObject.getString(this.JsMsgAc);
            if (Intrinsics.areEqual(string, this.JsMsgAcKey)) {
                return 0.0d;
            }
            if (TextUtils.equals(string, this.JsMsgAcKeyUrl)) {
                final String string2 = jSONObject.getString(this.JsMsgAcValueUrl);
                WebView webView = (WebView) _$_findCachedViewById(R$id.web_view);
                if (webView == null) {
                    return 0.0d;
                }
                webView.post(new Runnable() { // from class: com.one.tomato.mvp.ui.game.view.GameTabFragment$receiveMessageFromJS$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        Context mContext;
                        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(string2));
                        mContext = GameTabFragment.this.getMContext();
                        if (mContext != null) {
                            mContext.startActivity(intent);
                        }
                    }
                });
                return 0.0d;
            } else if (TextUtils.equals(string, this.JsMsgAcKeyBalance)) {
                WebView webView2 = (WebView) _$_findCachedViewById(R$id.web_view);
                if (webView2 == null) {
                    return 0.0d;
                }
                webView2.post(GameTabFragment$receiveMessageFromJS$2.INSTANCE);
                return 0.0d;
            } else if (TextUtils.equals(string, this.JSMsgAcOpenRecharge)) {
                RechargeActivity.startActivity(getContext());
                return 0.0d;
            } else if (!TextUtils.equals(string, this.JSMsgAcOpenLogin)) {
                return 0.0d;
            } else {
                LoginActivity.Companion companion = LoginActivity.Companion;
                Context context = getContext();
                if (context != null) {
                    companion.startActivity(context);
                    return 0.0d;
                }
                Intrinsics.throwNpe();
                throw null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return 0.0d;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void loadGame(final SubGamesBean subGamesBean) {
        String game_dasheng_h5;
        try {
            String adGameHeight = subGamesBean.getAdGameHeight();
            Intrinsics.checkExpressionValueIsNotNull(adGameHeight, "gamesBean.adGameHeight");
            int parseInt = Integer.parseInt(adGameHeight);
            String adGameWidth = subGamesBean.getAdGameWidth();
            Intrinsics.checkExpressionValueIsNotNull(adGameWidth, "gamesBean.adGameWidth");
            int parseInt2 = Integer.parseInt(adGameWidth);
            if (parseInt != 0 && parseInt2 != 0) {
                float width = parseInt * (DisplayMetricsUtils.getWidth() / parseInt2);
                if (width > DisplayMetricsUtils.getHeight()) {
                    width = DisplayMetricsUtils.getHeight();
                }
                WebView webView = (WebView) _$_findCachedViewById(R$id.web_view);
                ViewGroup.LayoutParams layoutParams = webView != null ? webView.getLayoutParams() : null;
                if (layoutParams != null) {
                    layoutParams.height = (int) width;
                }
                if (layoutParams != null) {
                    layoutParams.width = -1;
                }
                WebView webView2 = (WebView) _$_findCachedViewById(R$id.web_view);
                if (webView2 != null) {
                    webView2.setLayoutParams(layoutParams);
                }
            }
        } catch (Exception unused) {
        }
        GameUtils.INSTANCE.getGAME_BINGBING_TOKEN();
        if ("1".equals(subGamesBean.getAdBrandId())) {
            SubGamesBean subGamesBean2 = this.curGameBean;
            if (subGamesBean2 != null && (!Intrinsics.areEqual(subGamesBean2, subGamesBean))) {
                GameUtils gameUtils = GameUtils.INSTANCE;
                SubGamesBean subGamesBean3 = this.curGameBean;
                if (subGamesBean3 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                gameUtils.gameToPurse(subGamesBean3);
            }
            game_dasheng_h5 = GameUtils.INSTANCE.getGAME_BINGBING_TOKEN();
        } else {
            game_dasheng_h5 = GameUtils.INSTANCE.getGAME_DASHENG_H5();
        }
        GameUtils gameUtils2 = GameUtils.INSTANCE;
        String adBrandId = subGamesBean.getAdBrandId();
        Intrinsics.checkExpressionValueIsNotNull(adBrandId, "gamesBean.adBrandId");
        String adGameId = subGamesBean.getAdGameId();
        Intrinsics.checkExpressionValueIsNotNull(adGameId, "gamesBean.adGameId");
        gameUtils2.requestGameResponse(game_dasheng_h5, adBrandId, adGameId, subGamesBean.getAdType(), new GameUtils.RequestGameResponseListener() { // from class: com.one.tomato.mvp.ui.game.view.GameTabFragment$loadGame$1
            @Override // com.one.tomato.utils.GameUtils.RequestGameResponseListener
            public void requestFail() {
            }

            @Override // com.one.tomato.utils.GameUtils.RequestGameResponseListener
            public void requestGameDaShengWebapp(GameDaShengWebapp gameDaShengWebapp) {
                Intrinsics.checkParameterIsNotNull(gameDaShengWebapp, "gameDaShengWebapp");
            }

            @Override // com.one.tomato.utils.GameUtils.RequestGameResponseListener
            public void requestGameBingBingToken(GameBingBingToken gameBingBingToken) {
                Intrinsics.checkParameterIsNotNull(gameBingBingToken, "gameBingBingToken");
                GameTabFragment.this.curGameBean = subGamesBean;
                GameUtils.INSTANCE.purseToGame(subGamesBean);
                String str = subGamesBean.getAdLink() + "/?game_id=" + subGamesBean.getAdGameId() + "&appid=" + gameBingBingToken.gameChannel + "&platform=" + gameBingBingToken.platformId + "&account=" + String.valueOf(DBUtil.getMemberId()) + "&token=" + gameBingBingToken.token + "&tourist=0";
                LogUtil.m3784i("bingbing游戏Dialog,打开游戏的url：" + str);
                WebView webView3 = (WebView) GameTabFragment.this._$_findCachedViewById(R$id.web_view);
                if (webView3 != null) {
                    webView3.loadUrl(str);
                }
            }

            @Override // com.one.tomato.utils.GameUtils.RequestGameResponseListener
            public void requestGameDaShengH5(GameDaShengH5 gameDaShengH5) {
                Intrinsics.checkParameterIsNotNull(gameDaShengH5, "gameDaShengH5");
                String str = gameDaShengH5.url;
                LogUtil.m3784i("dasheng游戏Dialog,打开游戏的url：" + str);
                WebView webView3 = (WebView) GameTabFragment.this._$_findCachedViewById(R$id.web_view);
                if (webView3 != null) {
                    webView3.loadUrl(str);
                }
            }
        });
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        try {
            WebView webView = (WebView) _$_findCachedViewById(R$id.web_view);
            if (webView != null) {
                webView.clearHistory();
            }
            WebView webView2 = (WebView) _$_findCachedViewById(R$id.web_view);
            if (webView2 != null) {
                webView2.destroy();
            }
        } catch (Exception unused) {
        }
        SubGamesBean subGamesBean = this.curGameBean;
        if (subGamesBean != null) {
            GameUtils gameUtils = GameUtils.INSTANCE;
            if (subGamesBean == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            gameUtils.gameToPurse(subGamesBean);
        }
        this.curGameBean = null;
        _$_clearFindViewByIdCache();
    }
}
