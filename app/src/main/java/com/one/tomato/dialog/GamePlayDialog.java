package com.one.tomato.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Message;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.R$id;
import com.one.tomato.adapter.GameDialogAdapter;
import com.one.tomato.entity.GameBingBingToken;
import com.one.tomato.entity.GameDaShengH5;
import com.one.tomato.entity.GameDaShengWebapp;
import com.one.tomato.entity.p079db.SubGamesBean;
import com.one.tomato.mvp.p080ui.login.view.LoginActivity;
import com.one.tomato.p085ui.recharge.RechargeActivity;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.DisplayMetricsUtils;
import com.one.tomato.utils.GameUtils;
import com.one.tomato.utils.LogUtil;
import com.tomatolive.library.utils.LogConstants;
import java.util.ArrayList;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: GamePlayDialog.kt */
/* loaded from: classes3.dex */
public final class GamePlayDialog extends Dialog {
    private GameDialogAdapter adapter;
    private SubGamesBean curGameBean;
    private final GamePlayDialog$webChromeClient$1 webChromeClient;
    private final GamePlayDialog$webViewClient$1 webViewClient;
    private final String JsMsgAc = LogConstants.FOLLOW_OPERATION_TYPE;
    private final String JsMsgHandlerName = "messageHandlers";
    private final String JsMsgAcKey = "change_webview_frame";
    private final String JsMsgAcKeyUrl = "open_url";
    private final String JsMsgAcValueUrl = "url";
    private final String JsMsgAcKeyBalance = "live_money_changed";
    private final String JSMsgAcOpenRecharge = "live_open_recharge";
    private final String JSMsgAcOpenLogin = "live_open_login";

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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r4v2, types: [com.one.tomato.dialog.GamePlayDialog$webChromeClient$1] */
    /* JADX WARN: Type inference failed for: r4v3, types: [com.one.tomato.dialog.GamePlayDialog$webViewClient$1] */
    public GamePlayDialog(Context context) {
        super(context, R.style.postGameDialogStyle);
        Intrinsics.checkParameterIsNotNull(context, "context");
        setContentView(LayoutInflater.from(context).inflate(R.layout.game_play_dialog, (ViewGroup) null));
        if (context instanceof Activity) {
            setOwnerActivity((Activity) context);
        }
        Window window = getWindow();
        Intrinsics.checkExpressionValueIsNotNull(window, "window");
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = 80;
        attributes.width = -1;
        attributes.alpha = 1.0f;
        window.setAttributes(attributes);
        initView();
        this.webChromeClient = new WebChromeClient() { // from class: com.one.tomato.dialog.GamePlayDialog$webChromeClient$1
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
        this.webViewClient = new WebViewClient() { // from class: com.one.tomato.dialog.GamePlayDialog$webViewClient$1
            @Override // android.webkit.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Intrinsics.checkParameterIsNotNull(view, "view");
                Intrinsics.checkParameterIsNotNull(url, "url");
                return GamePlayDialog.this.webViewOverrideUrlLoading(view, url);
            }

            @Override // android.webkit.WebViewClient
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Intrinsics.checkParameterIsNotNull(view, "view");
                Intrinsics.checkParameterIsNotNull(url, "url");
                Intrinsics.checkParameterIsNotNull(favicon, "favicon");
                super.onPageStarted(view, url, favicon);
                GamePlayDialog.this.webViewPageStarted();
            }

            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView view, String url) {
                Intrinsics.checkParameterIsNotNull(view, "view");
                Intrinsics.checkParameterIsNotNull(url, "url");
                super.onPageFinished(view, url);
                GamePlayDialog.this.webViewPageFinished();
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

    private final void initView() {
        initWebView();
        RecyclerView recyclerView = (RecyclerView) findViewById(R$id.game_recycler);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), 0, false));
        }
        this.adapter = new GameDialogAdapter();
        GameDialogAdapter gameDialogAdapter = this.adapter;
        if (gameDialogAdapter != null) {
            gameDialogAdapter.setGameType(2);
        }
        RecyclerView recyclerView2 = (RecyclerView) findViewById(R$id.game_recycler);
        if (recyclerView2 != null) {
            recyclerView2.setAdapter(this.adapter);
        }
        GameDialogAdapter gameDialogAdapter2 = this.adapter;
        if (gameDialogAdapter2 != null) {
            gameDialogAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.dialog.GamePlayDialog$initView$1
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter<Object, BaseViewHolder> subAdapter, View view, int i) {
                    GameDialogAdapter gameDialogAdapter3;
                    GameDialogAdapter gameDialogAdapter4;
                    gameDialogAdapter3 = GamePlayDialog.this.adapter;
                    List<SubGamesBean> data = gameDialogAdapter3 != null ? gameDialogAdapter3.getData() : null;
                    if (data != null) {
                        int size = data.size();
                        for (int i2 = 0; i2 < size; i2++) {
                            if (i == i2) {
                                SubGamesBean subGamesBean = data.get(i);
                                Intrinsics.checkExpressionValueIsNotNull(subGamesBean, "it[position]");
                                subGamesBean.setSelector(true);
                            } else {
                                SubGamesBean subGamesBean2 = data.get(i2);
                                Intrinsics.checkExpressionValueIsNotNull(subGamesBean2, "it[its]");
                                subGamesBean2.setSelector(false);
                            }
                        }
                        gameDialogAdapter4 = GamePlayDialog.this.adapter;
                        if (gameDialogAdapter4 != null) {
                            gameDialogAdapter4.notifyDataSetChanged();
                        }
                    }
                    RecyclerView recyclerView3 = (RecyclerView) GamePlayDialog.this.findViewById(R$id.game_recycler);
                    if (recyclerView3 != null) {
                        recyclerView3.smoothScrollToPosition(i);
                    }
                    GamePlayDialog gamePlayDialog = GamePlayDialog.this;
                    Intrinsics.checkExpressionValueIsNotNull(subAdapter, "subAdapter");
                    Object obj = subAdapter.getData().get(i);
                    if (obj != null) {
                        gamePlayDialog.loadGame((SubGamesBean) obj);
                        return;
                    }
                    throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.entity.db.SubGamesBean");
                }
            });
        }
        ImageView imageView = (ImageView) findViewById(R$id.image_close);
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.GamePlayDialog$initView$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    GamePlayDialog.this.dismiss();
                }
            });
        }
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private final void initWebView() {
        WebSettings webSettings = ((WebView) findViewById(R$id.web_view)).getSettings();
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
        ((WebView) findViewById(R$id.web_view)).addJavascriptInterface(this, this.JsMsgHandlerName);
        ((WebView) findViewById(R$id.web_view)).setWebChromeClient(this.webChromeClient);
        ((WebView) findViewById(R$id.web_view)).setWebViewClient(this.webViewClient);
        if (Build.VERSION.SDK_INT >= 21) {
            ((WebView) findViewById(R$id.web_view)).getSettings().setMixedContentMode(0);
        }
    }

    public final void showGame(ArrayList<SubGamesBean> listData, int i) {
        Intrinsics.checkParameterIsNotNull(listData, "listData");
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(listData);
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (i == i2) {
                Object obj = arrayList.get(i);
                Intrinsics.checkExpressionValueIsNotNull(obj, "newData[position]");
                ((SubGamesBean) obj).setSelector(true);
            } else {
                Object obj2 = arrayList.get(i2);
                Intrinsics.checkExpressionValueIsNotNull(obj2, "newData[it]");
                ((SubGamesBean) obj2).setSelector(false);
            }
        }
        GameDialogAdapter gameDialogAdapter = this.adapter;
        if (gameDialogAdapter != null) {
            gameDialogAdapter.setNewData(arrayList);
        }
        GameDialogAdapter gameDialogAdapter2 = this.adapter;
        if (gameDialogAdapter2 != null) {
            gameDialogAdapter2.setEnableLoadMore(false);
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R$id.game_recycler);
        if (recyclerView != null) {
            recyclerView.smoothScrollToPosition(i);
        }
        Object obj3 = arrayList.get(i);
        Intrinsics.checkExpressionValueIsNotNull(obj3, "newData[position]");
        loadGame((SubGamesBean) obj3);
        show();
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
            float width = parseInt * (DisplayMetricsUtils.getWidth() / Integer.parseInt(adGameWidth));
            if (width > DisplayMetricsUtils.getHeight()) {
                width = DisplayMetricsUtils.getHeight();
            }
            WebView webView = (WebView) findViewById(R$id.web_view);
            ViewGroup.LayoutParams layoutParams = webView != null ? webView.getLayoutParams() : null;
            if (layoutParams != null) {
                layoutParams.height = (int) width;
            }
            if (layoutParams != null) {
                layoutParams.width = -1;
            }
            WebView webView2 = (WebView) findViewById(R$id.web_view);
            if (webView2 != null) {
                webView2.setLayoutParams(layoutParams);
            }
            LinearLayout linearLayout = (LinearLayout) findViewById(R$id.dialog_bg);
            ViewGroup.LayoutParams layoutParams2 = linearLayout != null ? linearLayout.getLayoutParams() : null;
            if (layoutParams2 != null) {
                layoutParams2.width = -1;
            }
            int dp2px = ((int) width) + ((int) DisplayMetricsUtils.dp2px(56.0f));
            if (dp2px > DisplayMetricsUtils.getHeight()) {
                dp2px = (int) DisplayMetricsUtils.getHeight();
            }
            if (layoutParams2 != null) {
                layoutParams2.height = dp2px;
            }
            LinearLayout linearLayout2 = (LinearLayout) findViewById(R$id.dialog_bg);
            if (linearLayout2 != null) {
                linearLayout2.setLayoutParams(layoutParams2);
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
        gameUtils2.requestGameResponse(game_dasheng_h5, adBrandId, adGameId, subGamesBean.getAdType(), new GameUtils.RequestGameResponseListener() { // from class: com.one.tomato.dialog.GamePlayDialog$loadGame$1
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
                GamePlayDialog.this.curGameBean = subGamesBean;
                GameUtils.INSTANCE.purseToGame(subGamesBean);
                String str = subGamesBean.getAdLink() + "/?game_id=" + subGamesBean.getAdGameId() + "&appid=" + gameBingBingToken.gameChannel + "&platform=" + gameBingBingToken.platformId + "&account=" + String.valueOf(DBUtil.getMemberId()) + "&token=" + gameBingBingToken.token + "&tourist=0";
                LogUtil.m3784i("bingbing游戏Dialog,打开游戏的url：" + str);
                WebView webView3 = (WebView) GamePlayDialog.this.findViewById(R$id.web_view);
                if (webView3 != null) {
                    webView3.loadUrl(str);
                }
            }

            @Override // com.one.tomato.utils.GameUtils.RequestGameResponseListener
            public void requestGameDaShengH5(GameDaShengH5 gameDaShengH5) {
                Intrinsics.checkParameterIsNotNull(gameDaShengH5, "gameDaShengH5");
                String str = gameDaShengH5.url;
                LogUtil.m3784i("dasheng游戏Dialog,打开游戏的url：" + str);
                WebView webView3 = (WebView) GamePlayDialog.this.findViewById(R$id.web_view);
                if (webView3 != null) {
                    webView3.loadUrl(str);
                }
            }
        });
    }

    @JavascriptInterface
    public final double receiveMessageFromJS(String jsonStr) {
        Intrinsics.checkParameterIsNotNull(jsonStr, "jsonStr");
        try {
            LogUtil.m3784i("游戏Dialog，js调取java方法传递的json ：" + jsonStr);
            JSONObject jSONObject = new JSONObject(jsonStr);
            String string = jSONObject.getString(this.JsMsgAc);
            if (!Intrinsics.areEqual(string, this.JsMsgAcKey)) {
                if (TextUtils.equals(string, this.JsMsgAcKeyUrl)) {
                    final String string2 = jSONObject.getString(this.JsMsgAcValueUrl);
                    WebView webView = (WebView) findViewById(R$id.web_view);
                    if (webView != null) {
                        webView.post(new Runnable() { // from class: com.one.tomato.dialog.GamePlayDialog$receiveMessageFromJS$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(string2));
                                if (GamePlayDialog.this.getOwnerActivity() != null) {
                                    Activity ownerActivity = GamePlayDialog.this.getOwnerActivity();
                                    if (ownerActivity == null) {
                                        return;
                                    }
                                    ownerActivity.startActivity(intent);
                                    return;
                                }
                                GamePlayDialog.this.getContext().startActivity(intent);
                            }
                        });
                    }
                } else if (TextUtils.equals(string, this.JsMsgAcKeyBalance)) {
                    WebView webView2 = (WebView) findViewById(R$id.web_view);
                    if (webView2 != null) {
                        webView2.post(GamePlayDialog$receiveMessageFromJS$2.INSTANCE);
                    }
                } else if (TextUtils.equals(string, this.JSMsgAcOpenRecharge)) {
                    if (getOwnerActivity() != null) {
                        RechargeActivity.startActivity(getOwnerActivity());
                    } else {
                        RechargeActivity.startActivity(getContext());
                    }
                } else if (TextUtils.equals(string, this.JSMsgAcOpenLogin)) {
                    if (getOwnerActivity() != null) {
                        LoginActivity.Companion companion = LoginActivity.Companion;
                        Activity ownerActivity = getOwnerActivity();
                        Intrinsics.checkExpressionValueIsNotNull(ownerActivity, "ownerActivity");
                        companion.startActivity(ownerActivity);
                    } else {
                        LoginActivity.Companion companion2 = LoginActivity.Companion;
                        Context context = getContext();
                        Intrinsics.checkExpressionValueIsNotNull(context, "context");
                        companion2.startActivity(context);
                    }
                }
            }
            return 0.0d;
        } catch (JSONException e) {
            e.printStackTrace();
            return 0.0d;
        }
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        super.dismiss();
        try {
            WebView webView = (WebView) findViewById(R$id.web_view);
            if (webView != null) {
                webView.clearHistory();
            }
            WebView webView2 = (WebView) findViewById(R$id.web_view);
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
}
