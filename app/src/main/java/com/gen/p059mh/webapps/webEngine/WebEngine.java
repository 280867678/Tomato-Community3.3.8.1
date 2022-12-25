package com.gen.p059mh.webapps.webEngine;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import com.gen.p059mh.webapps.listener.IWebBizOperation;
import com.gen.p059mh.webapps.listener.PageInjectListener;
import com.gen.p059mh.webapps.listener.PageLoadFinishListener;

/* renamed from: com.gen.mh.webapps.webEngine.WebEngine */
/* loaded from: classes2.dex */
public interface WebEngine {
    void addJavascriptInterface(Object obj, String str);

    void destroy();

    void executeJs(String str);

    ViewGroup.LayoutParams getLayoutParams();

    String getOriginalUrl();

    String getUserAgentString();

    void goBack();

    boolean hasHistory();

    WebEngine init(Context context);

    boolean isDirty();

    void loadDataWithBaseURL(String str, String str2, String str3, String str4, String str5);

    void loadUrl(String str);

    void onActivityResult(int i, int i2, Intent intent);

    void onPause();

    void onResume();

    View provideView();

    void requestLayout();

    void setBackgroundColor(int i);

    void setLayoutParams(ViewGroup.LayoutParams layoutParams);

    void setNeedInject(boolean z);

    void setPadding(int i, int i2, int i3, int i4);

    void setPageInjectListener(PageInjectListener pageInjectListener);

    void setPageLoadFinishCallBack(PageLoadFinishListener pageLoadFinishListener);

    void setScroll(boolean z);

    void setUserAgent(String str);

    void setWebViewCallback(IWebBizOperation iWebBizOperation);
}
