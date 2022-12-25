package com.gen.p059mh.webapps.webEngine.impl;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import com.gen.p059mh.webapps.listener.IWebBizOperation;
import com.gen.p059mh.webapps.listener.PageInjectListener;
import com.gen.p059mh.webapps.listener.PageLoadFinishListener;
import com.gen.p059mh.webapps.utils.Logger;
import com.gen.p059mh.webapps.views.DefaultWebView;
import com.gen.p059mh.webapps.webEngine.WebEngine;

/* renamed from: com.gen.mh.webapps.webEngine.impl.DefaultWebEngineImpl */
/* loaded from: classes2.dex */
public class DefaultWebEngineImpl implements WebEngine {
    DefaultWebView defaultWebView;

    DefaultWebView getWebView() {
        return this.defaultWebView;
    }

    @Override // com.gen.p059mh.webapps.webEngine.WebEngine
    public void setPadding(int i, int i2, int i3, int i4) {
        if (getWebView() != null) {
            getWebView().setPadding(i, i2, i3, i4);
        }
    }

    @Override // com.gen.p059mh.webapps.webEngine.WebEngine
    public View provideView() {
        return getWebView();
    }

    @Override // com.gen.p059mh.webapps.webEngine.WebEngine
    public WebEngine init(Context context) {
        this.defaultWebView = new DefaultWebView(context);
        Logger.m4113i("当前使用默认引擎");
        return this;
    }

    @Override // com.gen.p059mh.webapps.webEngine.WebEngine
    public void destroy() {
        Logger.m4113i("destroy");
        if (getWebView() != null) {
            getWebView().loadUrl("about:blank");
            getWebView().destroy();
            this.defaultWebView = null;
        }
    }

    @Override // com.gen.p059mh.webapps.webEngine.WebEngine
    public void onPause() {
        if (getWebView() != null) {
            getWebView().onPause();
        }
    }

    @Override // com.gen.p059mh.webapps.webEngine.WebEngine
    public void onResume() {
        if (getWebView() != null) {
            getWebView().onResume();
        }
    }

    @Override // com.gen.p059mh.webapps.webEngine.WebEngine
    public ViewGroup.LayoutParams getLayoutParams() {
        if (getWebView() != null) {
            return getWebView().getLayoutParams();
        }
        return null;
    }

    @Override // com.gen.p059mh.webapps.webEngine.WebEngine
    public void setLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (getWebView() != null) {
            getWebView().setLayoutParams(layoutParams);
        }
    }

    @Override // com.gen.p059mh.webapps.webEngine.WebEngine
    public String getUserAgentString() {
        return getWebView() != null ? getWebView().getSettings().getUserAgentString() : "";
    }

    @Override // com.gen.p059mh.webapps.webEngine.WebEngine
    public void setUserAgent(String str) {
        if (getWebView() != null) {
            getWebView().getSettings().setUserAgentString(str);
        }
    }

    @Override // com.gen.p059mh.webapps.webEngine.WebEngine
    public void addJavascriptInterface(Object obj, String str) {
        if (getWebView() != null) {
            getWebView().addJavascriptInterface(obj, str);
        }
    }

    @Override // com.gen.p059mh.webapps.webEngine.WebEngine
    public void loadUrl(String str) {
        if (getWebView() != null) {
            getWebView().loadUrl(str);
        }
    }

    @Override // com.gen.p059mh.webapps.webEngine.WebEngine
    public void setBackgroundColor(int i) {
        if (getWebView() != null) {
            getWebView().setBackgroundColor(i);
        }
    }

    @Override // com.gen.p059mh.webapps.webEngine.WebEngine
    public void setWebViewCallback(IWebBizOperation iWebBizOperation) {
        if (getWebView() != null) {
            getWebView().setWebViewCallback(iWebBizOperation);
        }
    }

    @Override // com.gen.p059mh.webapps.webEngine.WebEngine
    public boolean isDirty() {
        if (getWebView() == null) {
            return false;
        }
        return getWebView().isDirty();
    }

    @Override // com.gen.p059mh.webapps.webEngine.WebEngine
    public void loadDataWithBaseURL(String str, String str2, String str3, String str4, String str5) {
        if (getWebView() != null) {
            getWebView().loadDataWithBaseURL(str, str2, str3, str4, str5);
        }
    }

    @Override // com.gen.p059mh.webapps.webEngine.WebEngine
    public void onActivityResult(int i, int i2, Intent intent) {
        if (getWebView() != null) {
            getWebView().getPaxWebChromeClient().onActivityResult(i, i2, intent);
        }
    }

    @Override // com.gen.p059mh.webapps.webEngine.WebEngine
    public String getOriginalUrl() {
        return getWebView() != null ? getWebView().getOriginalUrl() : "";
    }

    @Override // com.gen.p059mh.webapps.webEngine.WebEngine
    public void setScroll(boolean z) {
        if (getWebView() != null) {
            getWebView().setScroll(z);
        }
    }

    @Override // com.gen.p059mh.webapps.webEngine.WebEngine
    public void setPageLoadFinishCallBack(PageLoadFinishListener pageLoadFinishListener) {
        if (getWebView() != null) {
            getWebView().setPageLoadFinishListener(pageLoadFinishListener);
        }
    }

    @Override // com.gen.p059mh.webapps.webEngine.WebEngine
    public void setPageInjectListener(PageInjectListener pageInjectListener) {
        if (getWebView() != null) {
            getWebView().setPageInjectListener(pageInjectListener);
        }
    }

    @Override // com.gen.p059mh.webapps.webEngine.WebEngine
    public boolean hasHistory() {
        if (getWebView() != null) {
            return getWebView().canGoBack();
        }
        return false;
    }

    @Override // com.gen.p059mh.webapps.webEngine.WebEngine
    public void goBack() {
        if (getWebView() != null) {
            getWebView().goBack();
        }
    }

    @Override // com.gen.p059mh.webapps.webEngine.WebEngine
    public void executeJs(String str) {
        if (getWebView() != null) {
            getWebView().executeJs(str);
        }
    }

    @Override // com.gen.p059mh.webapps.webEngine.WebEngine
    public void setNeedInject(boolean z) {
        if (getWebView() != null) {
            getWebView().setNeedInject(z);
        }
    }

    @Override // com.gen.p059mh.webapps.webEngine.WebEngine
    public void requestLayout() {
        if (getWebView() != null) {
            getWebView().requestLayout();
        }
    }
}
