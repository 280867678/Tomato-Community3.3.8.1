package com.one.tomato.mvp.p080ui.promotion.p081ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.entity.HtmlConfig;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.BaseHtmlActivity;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.thirdpart.promotion.PromotionUtil;
import java.util.HashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: XiaobaiHtmlActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.promotion.ui.XiaobaiHtmlActivity */
/* loaded from: classes3.dex */
public final class XiaobaiHtmlActivity extends BaseHtmlActivity {
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
        return R.layout.activity_xiaobai_html;
    }

    @Override // com.one.tomato.mvp.base.view.BaseHtmlActivity, com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    /* compiled from: XiaobaiHtmlActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.promotion.ui.XiaobaiHtmlActivity$Companion */
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
            intent.setClass(context, XiaobaiHtmlActivity.class);
            HtmlConfig htmlConfig = new HtmlConfig();
            StringBuilder sb = new StringBuilder();
            DomainServer domainServer = DomainServer.getInstance();
            Intrinsics.checkExpressionValueIsNotNull(domainServer, "DomainServer.getInstance()");
            sb.append(domainServer.getH5Url());
            sb.append("/promote/create");
            htmlConfig.setUrl(sb.toString());
            intent.putExtra("html_config", htmlConfig);
            context.startActivity(intent);
        }
    }

    @Override // com.one.tomato.mvp.base.view.BaseHtmlActivity, com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        super.initView();
        WebView webView = (WebView) _$_findCachedViewById(R$id.webView);
        Intrinsics.checkExpressionValueIsNotNull(webView, "webView");
        initWebView(webView);
        WebView webView2 = (WebView) _$_findCachedViewById(R$id.webView);
        Intrinsics.checkExpressionValueIsNotNull(webView2, "webView");
        loadUrl(webView2);
    }

    @Override // com.one.tomato.mvp.base.view.BaseHtmlActivity, com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        super.initData();
        ((ImageView) _$_findCachedViewById(R$id.iv_back)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.promotion.ui.XiaobaiHtmlActivity$initData$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                XiaobaiHtmlActivity.this.onBackPressed();
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_xiaobai)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.promotion.ui.XiaobaiHtmlActivity$initData$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PromotionUtil.openXiaobai(XiaobaiHtmlActivity.this);
            }
        });
    }

    @Override // com.one.tomato.mvp.base.view.BaseHtmlActivity
    public void webViewPageFinished() {
        super.webViewPageFinished();
        TextView tv_xiaobai = (TextView) _$_findCachedViewById(R$id.tv_xiaobai);
        Intrinsics.checkExpressionValueIsNotNull(tv_xiaobai, "tv_xiaobai");
        tv_xiaobai.setVisibility(0);
    }

    @Override // com.one.tomato.mvp.base.view.BaseHtmlActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        WebView webView = (WebView) _$_findCachedViewById(R$id.webView);
        Intrinsics.checkExpressionValueIsNotNull(webView, "webView");
        webOnBack(webView);
    }
}
