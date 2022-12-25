package com.one.tomato.mvp.p080ui.mine.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.entity.HtmlConfig;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.BaseHtmlActivity;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.utils.DBUtil;
import java.io.Serializable;
import java.util.HashMap;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CreatorCenterHtmlActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.mine.view.CreatorCenterHtmlActivity */
/* loaded from: classes3.dex */
public final class CreatorCenterHtmlActivity extends BaseHtmlActivity {
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
        return R.layout.activity_creator_center_html;
    }

    @Override // com.one.tomato.mvp.base.view.BaseHtmlActivity, com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    public CreatorCenterHtmlActivity() {
        DBUtil.getUserInfo();
    }

    /* compiled from: CreatorCenterHtmlActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.mine.view.CreatorCenterHtmlActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startActivity(Context context, String url, String title) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            Intrinsics.checkParameterIsNotNull(url, "url");
            Intrinsics.checkParameterIsNotNull(title, "title");
            Intent intent = new Intent();
            intent.setClass(context, CreatorCenterHtmlActivity.class);
            HtmlConfig htmlConfig = new HtmlConfig();
            htmlConfig.setUrl(url);
            htmlConfig.setTitle(title);
            intent.putExtra("html_config", htmlConfig);
            context.startActivity(intent);
        }
    }

    @Override // com.one.tomato.mvp.base.view.BaseHtmlActivity, com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        super.initView();
        initTitleBar();
        WebView webView = (WebView) _$_findCachedViewById(R$id.webView);
        Intrinsics.checkExpressionValueIsNotNull(webView, "webView");
        initWebView(webView);
        Serializable serializableExtra = getIntent().getSerializableExtra("html_config");
        if (serializableExtra == null) {
            throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.entity.HtmlConfig");
        }
        setHtmlConfig((HtmlConfig) serializableExtra);
        TextView titleTV = getTitleTV();
        if (titleTV != null) {
            titleTV.setText(getHtmlConfig().getTitle());
        }
        loadUrl();
    }

    private final void loadUrl() {
        ((WebView) _$_findCachedViewById(R$id.webView)).loadUrl(getHtmlConfig().getUrl());
    }

    @Override // com.one.tomato.mvp.base.view.BaseHtmlActivity, com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        super.initData();
    }

    @Override // com.one.tomato.mvp.base.view.BaseHtmlActivity
    public void webViewPageFinished() {
        super.webViewPageFinished();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity, com.one.tomato.net.LoginResponseObserver
    public void onLoginSuccess() {
        super.onLoginSuccess();
        loadUrl();
    }

    @Override // com.one.tomato.mvp.base.view.BaseHtmlActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
