package com.one.tomato.mvp.p080ui.mine.view;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.entity.HtmlConfig;
import com.one.tomato.entity.MainNotifyBean;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.BaseHtmlActivity;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.PreferencesUtil;
import com.one.tomato.utils.RxUtils;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;

/* compiled from: AppMarketHtmlActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.mine.view.AppMarketHtmlActivity */
/* loaded from: classes3.dex */
public final class AppMarketHtmlActivity extends BaseHtmlActivity {
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

    @Override // com.one.tomato.mvp.base.view.BaseHtmlActivity, com.one.tomato.mvp.base.view.MvpBaseActivity
    public int createLayoutView() {
        return R.layout.activity_app_market_html;
    }

    @Override // com.one.tomato.mvp.base.view.BaseHtmlActivity, com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    public AppMarketHtmlActivity() {
        DBUtil.getUserInfo();
    }

    /* compiled from: AppMarketHtmlActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.mine.view.AppMarketHtmlActivity$Companion */
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
            intent.setClass(context, AppMarketHtmlActivity.class);
            context.startActivity(intent);
        }
    }

    @Override // com.one.tomato.mvp.base.view.BaseHtmlActivity, com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        super.initView();
        initTitleBar();
        TextView titleTV = getTitleTV();
        if (titleTV != null) {
            titleTV.setText(R.string.my_game_title);
        }
        WebView webView = (WebView) _$_findCachedViewById(R$id.webView);
        Intrinsics.checkExpressionValueIsNotNull(webView, "webView");
        initWebView(webView);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [T, java.lang.String] */
    @Override // com.one.tomato.mvp.base.view.BaseHtmlActivity, com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        super.initData();
        setHtmlConfig(new HtmlConfig());
        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        ref$ObjectRef.element = PreferencesUtil.getInstance().getString("app_market");
        if (TextUtils.isEmpty((String) ref$ObjectRef.element)) {
            showWaitingDialog();
            ApiImplService.Companion.getApiImplService().postHomeMainNotify().compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler(getLifecycleProvider())).subscribe(new ApiDisposableObserver<ArrayList<MainNotifyBean>>() { // from class: com.one.tomato.mvp.ui.mine.view.AppMarketHtmlActivity$initData$1
                /* JADX WARN: Type inference failed for: r3v3, types: [T, java.lang.String] */
                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResult(ArrayList<MainNotifyBean> arrayList) {
                    AppMarketHtmlActivity.this.hideWaitingDialog();
                    if (arrayList == null || !(!arrayList.isEmpty())) {
                        return;
                    }
                    Ref$ObjectRef ref$ObjectRef2 = ref$ObjectRef;
                    MainNotifyBean mainNotifyBean = arrayList.get(0);
                    Intrinsics.checkExpressionValueIsNotNull(mainNotifyBean, "t[0]");
                    ref$ObjectRef2.element = mainNotifyBean.getLinkUrl();
                    if (TextUtils.isEmpty((String) ref$ObjectRef.element)) {
                        return;
                    }
                    ((WebView) AppMarketHtmlActivity.this._$_findCachedViewById(R$id.webView)).loadUrl((String) ref$ObjectRef.element);
                }

                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResultError(ResponseThrowable responseThrowable) {
                    AppMarketHtmlActivity.this.hideWaitingDialog();
                }
            });
            return;
        }
        ((WebView) _$_findCachedViewById(R$id.webView)).loadUrl((String) ref$ObjectRef.element);
    }

    @Override // com.one.tomato.mvp.base.view.BaseHtmlActivity
    public void webViewPageFinished() {
        super.webViewPageFinished();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity, com.one.tomato.net.LoginResponseObserver
    public void onLoginSuccess() {
        super.onLoginSuccess();
    }

    @Override // com.one.tomato.mvp.base.view.BaseHtmlActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
