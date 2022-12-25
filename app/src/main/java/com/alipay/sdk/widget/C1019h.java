package com.alipay.sdk.widget;

import android.app.Activity;
import android.content.Context;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.alipay.sdk.app.C0945b;
import com.alipay.sdk.app.C0952j;
import com.alipay.sdk.app.EnumC0953k;
import com.alipay.sdk.sys.C0988a;
import com.alipay.sdk.util.C1008n;
import java.lang.reflect.Method;

/* renamed from: com.alipay.sdk.widget.h */
/* loaded from: classes2.dex */
public class C1019h extends AbstractC1018g {

    /* renamed from: b */
    private C0945b f1084b;

    /* renamed from: c */
    private WebView f1085c;

    public C1019h(Activity activity, C0988a c0988a) {
        super(activity);
        this.f1085c = new WebView(activity);
        m4347a(activity);
        addView(this.f1085c);
        this.f1084b = new C0945b(activity, c0988a);
        this.f1085c.setWebViewClient(this.f1084b);
    }

    @Override // com.alipay.sdk.widget.AbstractC1018g
    /* renamed from: b */
    public boolean mo4339b() {
        if (this.f1085c.canGoBack()) {
            if (!this.f1084b.m4652b()) {
                return true;
            }
            EnumC0953k m4636b = EnumC0953k.m4636b(EnumC0953k.NETWORK_ERROR.m4640a());
            C0952j.m4646a(C0952j.m4647a(m4636b.m4640a(), m4636b.m4637b(), ""));
            this.f1083a.finish();
            return true;
        }
        C0952j.m4646a(C0952j.m4643c());
        this.f1083a.finish();
        return true;
    }

    @Override // com.alipay.sdk.widget.AbstractC1018g
    /* renamed from: a */
    public void mo4346a() {
        this.f1084b.m4654a();
        removeAllViews();
    }

    /* renamed from: a */
    private void m4347a(Context context) {
        WebSettings settings = this.f1085c.getSettings();
        settings.setUserAgentString(settings.getUserAgentString() + C1008n.m4374c(context));
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setSupportMultipleWindows(true);
        settings.setJavaScriptEnabled(true);
        settings.setSavePassword(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setMinimumFontSize(settings.getMinimumFontSize() + 8);
        settings.setAllowFileAccess(false);
        settings.setTextSize(WebSettings.TextSize.NORMAL);
        settings.setDomStorageEnabled(true);
        settings.setCacheMode(1);
        this.f1085c.resumeTimers();
        this.f1085c.setVerticalScrollbarOverlay(true);
        this.f1085c.setDownloadListener(new C1020i(this));
        try {
            try {
                this.f1085c.removeJavascriptInterface("searchBoxJavaBridge_");
                this.f1085c.removeJavascriptInterface("accessibility");
                this.f1085c.removeJavascriptInterface("accessibilityTraversal");
            } catch (Throwable unused) {
            }
        } catch (Throwable unused2) {
            Method method = this.f1085c.getClass().getMethod("removeJavascriptInterface", new Class[0]);
            if (method == null) {
                return;
            }
            method.invoke(this.f1085c, "searchBoxJavaBridge_");
            method.invoke(this.f1085c, "accessibility");
            method.invoke(this.f1085c, "accessibilityTraversal");
        }
    }

    @Override // com.alipay.sdk.widget.AbstractC1018g
    /* renamed from: a */
    public void mo4343a(String str) {
        this.f1085c.loadUrl(str);
    }
}
