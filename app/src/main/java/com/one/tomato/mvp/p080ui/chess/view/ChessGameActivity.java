package com.one.tomato.mvp.p080ui.chess.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.GridLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.R$id;
import com.one.tomato.entity.GameBingBingToken;
import com.one.tomato.entity.GameDaShengH5;
import com.one.tomato.entity.GameDaShengWebapp;
import com.one.tomato.entity.p079db.GameRegisterUserBean;
import com.one.tomato.entity.p079db.SubGamesBean;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.chess.adapter.ChessGameAdapter;
import com.one.tomato.mvp.p080ui.login.view.LoginActivity;
import com.one.tomato.p085ui.recharge.RechargeActivity;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.DisplayMetricsUtils;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.GameUtils;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.RxUtils;
import com.one.tomato.widget.MarqueeTextView;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.LogConstants;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import org.json.JSONObject;

/* compiled from: ChessGameActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.chess.view.ChessGameActivity */
/* loaded from: classes3.dex */
public final class ChessGameActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private SubGamesBean curGameBean;
    private ChessGameAdapter gameHorAdapter;
    private final String JsMsgAc = LogConstants.FOLLOW_OPERATION_TYPE;
    private final String JsMsgHandlerName = "messageHandlers";
    private final String JsMsgAcKey = "change_webview_frame";
    private final String JsMsgAcKeyUrl = "open_url";
    private final String JsMsgAcValueUrl = "url";
    private final String JsMsgAcKeyBalance = "live_money_changed";
    private final String JSMsgAcOpenRecharge = "live_open_recharge";
    private final String JSMsgAcOpenLogin = "live_open_login";
    private final ArrayList<String> gameRegisterUserBean = new ArrayList<>();

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
        return R.layout.activity_chess_game;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
    }

    /* compiled from: ChessGameActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.chess.view.ChessGameActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startAct(Context context, ArrayList<SubGamesBean> gamesBean) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            Intrinsics.checkParameterIsNotNull(gamesBean, "gamesBean");
            Intent intent = new Intent(context, ChessGameActivity.class);
            intent.putExtra(AopConstants.APP_PROPERTIES_KEY, gamesBean);
            context.startActivity(intent);
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        initTitleBar();
        initWebView();
        initH5Game();
        initNotice();
    }

    private final void initH5Game() {
        this.gameHorAdapter = new ChessGameAdapter(getMContext(), (RecyclerView) _$_findCachedViewById(R$id.game_recycler));
        RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(R$id.game_recycler);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new GridLayoutManager(getMContext(), 4));
        }
        RecyclerView recyclerView2 = (RecyclerView) _$_findCachedViewById(R$id.game_recycler);
        if (recyclerView2 != null) {
            recyclerView2.setAdapter(this.gameHorAdapter);
        }
        ArrayList arrayList = new ArrayList();
        Intent intent = getIntent();
        Serializable serializableExtra = intent != null ? intent.getSerializableExtra(AopConstants.APP_PROPERTIES_KEY) : null;
        if (serializableExtra != null) {
            arrayList.addAll((ArrayList) serializableExtra);
        }
        if (!arrayList.isEmpty()) {
            int size = arrayList.size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    break;
                }
                Object obj = arrayList.get(i);
                Intrinsics.checkExpressionValueIsNotNull(obj, "adPage[its]");
                if (((SubGamesBean) obj).isSelector()) {
                    RecyclerView recyclerView3 = (RecyclerView) _$_findCachedViewById(R$id.game_recycler);
                    if (recyclerView3 != null) {
                        recyclerView3.smoothScrollToPosition(i);
                    }
                    Object obj2 = arrayList.get(i);
                    Intrinsics.checkExpressionValueIsNotNull(obj2, "adPage[its]");
                    loadGame((SubGamesBean) obj2);
                } else {
                    i++;
                }
            }
            ChessGameAdapter chessGameAdapter = this.gameHorAdapter;
            if (chessGameAdapter != null) {
                chessGameAdapter.setNewData(arrayList);
            }
            ChessGameAdapter chessGameAdapter2 = this.gameHorAdapter;
            if (chessGameAdapter2 != null) {
                chessGameAdapter2.setEnableLoadMore(false);
            }
        }
        ChessGameAdapter chessGameAdapter3 = this.gameHorAdapter;
        if (chessGameAdapter3 != null) {
            chessGameAdapter3.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.chess.view.ChessGameActivity$initH5Game$1
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter<Object, BaseViewHolder> baseQuickAdapter, View view, int i2) {
                    ChessGameAdapter chessGameAdapter4;
                    ChessGameAdapter chessGameAdapter5;
                    ChessGameAdapter chessGameAdapter6;
                    chessGameAdapter4 = ChessGameActivity.this.gameHorAdapter;
                    if (chessGameAdapter4 == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                    List<SubGamesBean> data = chessGameAdapter4.getData();
                    Intrinsics.checkExpressionValueIsNotNull(data, "gameHorAdapter!!.data");
                    int size2 = data.size();
                    for (int i3 = 0; i3 < size2; i3++) {
                        if (i2 == i3) {
                            SubGamesBean subGamesBean = data.get(i2);
                            Intrinsics.checkExpressionValueIsNotNull(subGamesBean, "it[position]");
                            subGamesBean.setSelector(true);
                        } else {
                            SubGamesBean subGamesBean2 = data.get(i3);
                            Intrinsics.checkExpressionValueIsNotNull(subGamesBean2, "it[its]");
                            subGamesBean2.setSelector(false);
                        }
                    }
                    chessGameAdapter5 = ChessGameActivity.this.gameHorAdapter;
                    if (chessGameAdapter5 == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                    chessGameAdapter5.notifyDataSetChanged();
                    RecyclerView recyclerView4 = (RecyclerView) ChessGameActivity.this._$_findCachedViewById(R$id.game_recycler);
                    if (recyclerView4 != null) {
                        recyclerView4.smoothScrollToPosition(i2);
                    }
                    ChessGameActivity chessGameActivity = ChessGameActivity.this;
                    chessGameAdapter6 = chessGameActivity.gameHorAdapter;
                    if (chessGameAdapter6 == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                    SubGamesBean subGamesBean3 = chessGameAdapter6.getData().get(i2);
                    Intrinsics.checkExpressionValueIsNotNull(subGamesBean3, "gameHorAdapter!!.data[position]");
                    chessGameActivity.loadGame(subGamesBean3);
                }
            });
        }
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
                webView.post(new Runnable() { // from class: com.one.tomato.mvp.ui.chess.view.ChessGameActivity$receiveMessageFromJS$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        Context mContext;
                        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(string2));
                        mContext = ChessGameActivity.this.getMContext();
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
                webView2.post(ChessGameActivity$receiveMessageFromJS$2.INSTANCE);
                return 0.0d;
            } else if (TextUtils.equals(string, this.JSMsgAcOpenRecharge)) {
                RechargeActivity.startActivity(getMContext());
                return 0.0d;
            } else if (!TextUtils.equals(string, this.JSMsgAcOpenLogin)) {
                return 0.0d;
            } else {
                LoginActivity.Companion companion = LoginActivity.Companion;
                Context mContext = getMContext();
                if (mContext != null) {
                    companion.startActivity(mContext);
                    return 0.0d;
                }
                Intrinsics.throwNpe();
                throw null;
            }
        } catch (Exception e) {
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
        gameUtils2.requestGameResponse(game_dasheng_h5, adBrandId, adGameId, subGamesBean.getAdType(), new GameUtils.RequestGameResponseListener() { // from class: com.one.tomato.mvp.ui.chess.view.ChessGameActivity$loadGame$1
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
                ChessGameActivity.this.curGameBean = subGamesBean;
                GameUtils.INSTANCE.purseToGame(subGamesBean);
                String str = subGamesBean.getAdLink() + "/?game_id=" + subGamesBean.getAdGameId() + "&appid=" + gameBingBingToken.gameChannel + "&platform=" + gameBingBingToken.platformId + "&account=" + String.valueOf(DBUtil.getMemberId()) + "&token=" + gameBingBingToken.token + "&tourist=0";
                LogUtil.m3784i("bingbing游戏Dialog,打开游戏的url：" + str);
                WebView webView3 = (WebView) ChessGameActivity.this._$_findCachedViewById(R$id.web_view);
                if (webView3 != null) {
                    webView3.loadUrl(str);
                }
            }

            @Override // com.one.tomato.utils.GameUtils.RequestGameResponseListener
            public void requestGameDaShengH5(GameDaShengH5 gameDaShengH5) {
                Intrinsics.checkParameterIsNotNull(gameDaShengH5, "gameDaShengH5");
                String str = gameDaShengH5.url;
                LogUtil.m3784i("dasheng游戏Dialog,打开游戏的url：" + str);
                WebView webView3 = (WebView) ChessGameActivity.this._$_findCachedViewById(R$id.web_view);
                if (webView3 != null) {
                    webView3.loadUrl(str);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
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
        ApiImplService.Companion.getApiImplService().queryGameUser().compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<GameRegisterUserBean>() { // from class: com.one.tomato.mvp.ui.chess.view.ChessGameActivity$initNotice$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(GameRegisterUserBean gameRegisterUserBean) {
                ArrayList arrayList;
                if (gameRegisterUserBean != null) {
                    ArrayList<String> memberNames2 = gameRegisterUserBean.getMemberNames();
                    boolean z = false;
                    if (memberNames2 == null || memberNames2.isEmpty()) {
                        return;
                    }
                    arrayList = ChessGameActivity.this.gameRegisterUserBean;
                    arrayList.addAll(memberNames2);
                    GameRegisterUserBean gameRegisterUserBean2 = gameRegisterBean;
                    ArrayList<String> memberNames3 = gameRegisterUserBean2 != null ? gameRegisterUserBean2.getMemberNames() : null;
                    if (memberNames3 == null || memberNames3.isEmpty()) {
                        z = true;
                    }
                    if (z) {
                        ChessGameActivity.this.startScrollNotice();
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
        ChessGameAdapter chessGameAdapter = this.gameHorAdapter;
        final List<SubGamesBean> data = chessGameAdapter != null ? chessGameAdapter.getData() : null;
        if (data == null || data.isEmpty()) {
            z = true;
        }
        if (z) {
            return;
        }
        Observable.interval(0L, 15L, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() { // from class: com.one.tomato.mvp.ui.chess.view.ChessGameActivity$startScrollNotice$subscribe$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Long l) {
                ArrayList arrayList2;
                ArrayList arrayList3;
                Random.Default r7 = Random.Default;
                arrayList2 = ChessGameActivity.this.gameRegisterUserBean;
                int i = 0;
                int nextInt = r7.nextInt(0, arrayList2.size());
                arrayList3 = ChessGameActivity.this.gameRegisterUserBean;
                Object obj = arrayList3.get(nextInt);
                Intrinsics.checkExpressionValueIsNotNull(obj, "gameRegisterUserBean[nextInt]");
                String string = AppUtil.getString(R.string.game_notice_text1);
                Intrinsics.checkExpressionValueIsNotNull(string, "AppUtil.getString(R.string.game_notice_text1)");
                Object[] objArr = {(String) obj};
                String format = String.format(string, Arrays.copyOf(objArr, objArr.length));
                Intrinsics.checkExpressionValueIsNotNull(format, "java.lang.String.format(this, *args)");
                MarqueeTextView marqueeTextView = (MarqueeTextView) ChessGameActivity.this._$_findCachedViewById(R$id.tv_notice);
                if (marqueeTextView != null) {
                    marqueeTextView.setVisibility(0);
                }
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
                spannableStringBuilder.append((CharSequence) format);
                final int nextInt2 = Random.Default.nextInt(0, data.size());
                SubGamesBean gameBrand = (SubGamesBean) data.get(nextInt2);
                MarqueeTextView marqueeTextView2 = (MarqueeTextView) ChessGameActivity.this._$_findCachedViewById(R$id.tv_notice);
                if (marqueeTextView2 != null) {
                    marqueeTextView2.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.chess.view.ChessGameActivity$startScrollNotice$subscribe$1.1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            ChessGameAdapter chessGameAdapter2;
                            ChessGameAdapter chessGameAdapter3;
                            chessGameAdapter2 = ChessGameActivity.this.gameHorAdapter;
                            List<SubGamesBean> data2 = chessGameAdapter2 != null ? chessGameAdapter2.getData() : null;
                            if (data2 != null) {
                                for (SubGamesBean its : data2) {
                                    Intrinsics.checkExpressionValueIsNotNull(its, "its");
                                    its.setSelector(false);
                                }
                                SubGamesBean subGamesBean = data2.get(nextInt2);
                                Intrinsics.checkExpressionValueIsNotNull(subGamesBean, "data[gameNameIndex]");
                                subGamesBean.setSelector(true);
                                chessGameAdapter3 = ChessGameActivity.this.gameHorAdapter;
                                if (chessGameAdapter3 == null) {
                                    return;
                                }
                                chessGameAdapter3.notifyDataSetChanged();
                                return;
                            }
                            Intrinsics.throwNpe();
                            throw null;
                        }
                    });
                }
                Intrinsics.checkExpressionValueIsNotNull(gameBrand, "gameBrand");
                spannableStringBuilder.append((CharSequence) gameBrand.getAdBrandName());
                SpannableString spannableString = new SpannableString(gameBrand.getAdName());
                spannableString.setSpan(new ClickableSpan() { // from class: com.one.tomato.mvp.ui.chess.view.ChessGameActivity$startScrollNotice$subscribe$1$titleClickSpan$1
                    @Override // android.text.style.ClickableSpan
                    public void onClick(View widget) {
                        Intrinsics.checkParameterIsNotNull(widget, "widget");
                    }

                    @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                    public void updateDrawState(TextPaint ds) {
                        Intrinsics.checkParameterIsNotNull(ds, "ds");
                        super.updateDrawState(ds);
                        ds.setUnderlineText(true);
                        ds.setColor(Color.parseColor("#E4BA9A"));
                    }
                }, 0, spannableString.length(), 33);
                spannableStringBuilder.append((CharSequence) spannableString);
                spannableStringBuilder.append((CharSequence) AppUtil.getString(R.string.game_notice_text2));
                spannableStringBuilder.append((CharSequence) String.valueOf(FormatUtil.formatTwo(Double.valueOf(Random.Default.nextDouble(950.0d, 10000.0d)))));
                spannableStringBuilder.append((CharSequence) AppUtil.getString(R.string.common_renmingbi));
                Paint paint = new Paint();
                int measureText = (int) ((paint.measureText(spannableStringBuilder.toString()) / 5) / paint.measureText(ConstantUtils.PLACEHOLDER_STR_ONE));
                if (measureText >= 0) {
                    while (true) {
                        spannableStringBuilder.append((CharSequence) ConstantUtils.PLACEHOLDER_STR_ONE);
                        if (i == measureText) {
                            break;
                        }
                        i++;
                    }
                }
                MarqueeTextView marqueeTextView3 = (MarqueeTextView) ChessGameActivity.this._$_findCachedViewById(R$id.tv_notice);
                if (marqueeTextView3 != null) {
                    marqueeTextView3.setText(spannableStringBuilder);
                }
            }
        });
    }
}
