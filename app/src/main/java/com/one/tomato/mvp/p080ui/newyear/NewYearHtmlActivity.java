package com.one.tomato.mvp.p080ui.newyear;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.webkit.WebView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.entity.HtmlConfig;
import com.one.tomato.entity.p079db.LoginInfo;
import com.one.tomato.entity.p079db.UserInfo;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.BaseHtmlActivity;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.utils.DBUtil;
import java.io.Serializable;
import java.util.HashMap;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: NewYearHtmlActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.newyear.NewYearHtmlActivity */
/* loaded from: classes3.dex */
public final class NewYearHtmlActivity extends BaseHtmlActivity {
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
        return R.layout.activity_new_year_html;
    }

    @Override // com.one.tomato.mvp.base.view.BaseHtmlActivity, com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    public NewYearHtmlActivity() {
        DBUtil.getUserInfo();
    }

    /* compiled from: NewYearHtmlActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.newyear.NewYearHtmlActivity$Companion */
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
            intent.setClass(context, NewYearHtmlActivity.class);
            HtmlConfig htmlConfig = new HtmlConfig();
            StringBuilder sb = new StringBuilder();
            DomainServer domainServer = DomainServer.getInstance();
            Intrinsics.checkExpressionValueIsNotNull(domainServer, "DomainServer.getInstance()");
            sb.append(domainServer.getH5Url());
            sb.append("/activities/newYear");
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
        Serializable serializableExtra = getIntent().getSerializableExtra("html_config");
        if (serializableExtra == null) {
            throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.entity.HtmlConfig");
        }
        setHtmlConfig((HtmlConfig) serializableExtra);
        loadUrl();
    }

    private final void loadUrl() {
        String str;
        String url = getHtmlConfig().getUrl();
        int memberId = DBUtil.getMemberId();
        LoginInfo loginInfo = DBUtil.getLoginInfo();
        Intrinsics.checkExpressionValueIsNotNull(loginInfo, "DBUtil.getLoginInfo()");
        boolean isLogin = loginInfo.isLogin();
        String str2 = isLogin ? "1" : "0";
        if (isLogin) {
            UserInfo userInfo = DBUtil.getUserInfo();
            Intrinsics.checkExpressionValueIsNotNull(userInfo, "DBUtil.getUserInfo()");
            str = userInfo.getInviteCode();
        } else {
            str = "--";
        }
        ((WebView) _$_findCachedViewById(R$id.webView)).loadUrl(url + "?memberId=" + memberId + "&isLogin=" + str2 + "&inviteCode=" + str);
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
