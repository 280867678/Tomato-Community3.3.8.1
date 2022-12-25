package com.alipay.sdk.widget;

import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import com.alipay.sdk.widget.C1028p;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.alipay.sdk.widget.s */
/* loaded from: classes2.dex */
public class C1034s extends WebChromeClient {

    /* renamed from: a */
    final /* synthetic */ C1028p f1116a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public C1034s(C1028p c1028p) {
        this.f1116a = c1028p;
    }

    @Override // android.webkit.WebChromeClient
    public void onProgressChanged(WebView webView, int i) {
        ProgressBar progressBar;
        ProgressBar progressBar2;
        ProgressBar progressBar3;
        ProgressBar progressBar4;
        if (i == 100) {
            progressBar4 = this.f1116a.f1106d;
            progressBar4.setVisibility(4);
            return;
        }
        progressBar = this.f1116a.f1106d;
        if (4 == progressBar.getVisibility()) {
            progressBar3 = this.f1116a.f1106d;
            progressBar3.setVisibility(0);
        }
        progressBar2 = this.f1116a.f1106d;
        progressBar2.setProgress(i);
    }

    @Override // android.webkit.WebChromeClient
    public boolean onJsPrompt(WebView webView, String str, String str2, String str3, JsPromptResult jsPromptResult) {
        C1028p.AbstractC1029a abstractC1029a;
        abstractC1029a = this.f1116a.f1108g;
        return abstractC1029a.mo4315a(this.f1116a, str, str2, str3, jsPromptResult);
    }

    @Override // android.webkit.WebChromeClient
    public void onReceivedTitle(WebView webView, String str) {
        C1028p.AbstractC1029a abstractC1029a;
        abstractC1029a = this.f1116a.f1108g;
        abstractC1029a.mo4316a(this.f1116a, str);
    }
}
