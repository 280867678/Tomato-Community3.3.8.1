package com.one.tomato.mvp.p080ui.promotion.p081ui;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.entity.HtmlConfig;
import com.one.tomato.entity.p079db.UserInfo;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.BaseHtmlActivity;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.p080ui.vip.p083ui.VipActivity;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import java.util.HashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SpreadHtmlActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.promotion.ui.SpreadHtmlActivity */
/* loaded from: classes3.dex */
public final class SpreadHtmlActivity extends BaseHtmlActivity {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private final UserInfo userInfo = DBUtil.getUserInfo();

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
        return R.layout.activity_spread_html;
    }

    @Override // com.one.tomato.mvp.base.view.BaseHtmlActivity, com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    /* compiled from: SpreadHtmlActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.promotion.ui.SpreadHtmlActivity$Companion */
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
            intent.setClass(context, SpreadHtmlActivity.class);
            HtmlConfig htmlConfig = new HtmlConfig();
            StringBuilder sb = new StringBuilder();
            DomainServer domainServer = DomainServer.getInstance();
            Intrinsics.checkExpressionValueIsNotNull(domainServer, "DomainServer.getInstance()");
            sb.append(domainServer.getH5Url());
            sb.append("/promote/fiveSell?inviteCode=");
            UserInfo userInfo = DBUtil.getUserInfo();
            Intrinsics.checkExpressionValueIsNotNull(userInfo, "DBUtil.getUserInfo()");
            sb.append(userInfo.getInviteCode());
            htmlConfig.setUrl(sb.toString());
            intent.putExtra("html_config", htmlConfig);
            context.startActivity(intent);
        }
    }

    public final UserInfo getUserInfo() {
        return this.userInfo;
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
        ((ImageView) _$_findCachedViewById(R$id.iv_back)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.promotion.ui.SpreadHtmlActivity$initData$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SpreadHtmlActivity.this.onBackPressed();
            }
        });
        ((ImageView) _$_findCachedViewById(R$id.iv_share)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.promotion.ui.SpreadHtmlActivity$initData$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HtmlConfig htmlConfig;
                htmlConfig = SpreadHtmlActivity.this.getHtmlConfig();
                AppUtil.copyShareText(htmlConfig.getUrl(), AppUtil.getString(R.string.vip_package_agenter_copy_toast));
            }
        });
        ((ConstraintLayout) _$_findCachedViewById(R$id.cl_spread)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.promotion.ui.SpreadHtmlActivity$initData$3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HtmlConfig htmlConfig;
                UserInfo userInfo = SpreadHtmlActivity.this.getUserInfo();
                Intrinsics.checkExpressionValueIsNotNull(userInfo, "userInfo");
                if (userInfo.getVipType() == 1) {
                    htmlConfig = SpreadHtmlActivity.this.getHtmlConfig();
                    AppUtil.copyShareText(htmlConfig.getUrl(), AppUtil.getString(R.string.vip_package_agenter_copy_toast));
                    SpreadHtmlActivity.this.onBackPressed();
                    return;
                }
                VipActivity.Companion.startActivity(SpreadHtmlActivity.this);
                SpreadHtmlActivity.this.finish();
            }
        });
    }

    @Override // com.one.tomato.mvp.base.view.BaseHtmlActivity
    public void webViewPageFinished() {
        super.webViewPageFinished();
        ImageView iv_share = (ImageView) _$_findCachedViewById(R$id.iv_share);
        Intrinsics.checkExpressionValueIsNotNull(iv_share, "iv_share");
        iv_share.setVisibility(0);
        ConstraintLayout cl_spread = (ConstraintLayout) _$_findCachedViewById(R$id.cl_spread);
        Intrinsics.checkExpressionValueIsNotNull(cl_spread, "cl_spread");
        cl_spread.setVisibility(0);
        UserInfo userInfo = this.userInfo;
        Intrinsics.checkExpressionValueIsNotNull(userInfo, "userInfo");
        if (userInfo.getVipType() == 1) {
            ((TextView) _$_findCachedViewById(R$id.tv_vip_agenter)).setText(R.string.vip_package_agenter1);
        } else {
            ((TextView) _$_findCachedViewById(R$id.tv_vip_agenter)).setText(R.string.vip_package_agenter);
        }
    }

    @Override // com.one.tomato.mvp.base.view.BaseHtmlActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        WebView webView = (WebView) _$_findCachedViewById(R$id.webView);
        Intrinsics.checkExpressionValueIsNotNull(webView, "webView");
        webOnBack(webView);
    }
}
