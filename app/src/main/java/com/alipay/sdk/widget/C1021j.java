package com.alipay.sdk.widget;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.webkit.JsPromptResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.widget.ImageView;
import com.alipay.sdk.app.C0952j;
import com.alipay.sdk.app.statistic.C0954a;
import com.alipay.sdk.sys.C0988a;
import com.alipay.sdk.util.C1008n;
import com.alipay.sdk.widget.C1028p;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import java.util.Map;
import org.json.JSONObject;

/* renamed from: com.alipay.sdk.widget.j */
/* loaded from: classes2.dex */
public class C1021j extends AbstractC1018g implements C1028p.AbstractC1029a, C1028p.AbstractC1030b, C1028p.AbstractC1031c {

    /* renamed from: w */
    private final C0988a f1090w;

    /* renamed from: t */
    private boolean f1087t = true;

    /* renamed from: u */
    private String f1088u = "GET";

    /* renamed from: v */
    private boolean f1089v = false;

    /* renamed from: x */
    private C1028p f1091x = null;

    /* renamed from: y */
    private C1036u f1092y = new C1036u();

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (this.f1089v) {
            return true;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    public C1021j(Activity activity, C0988a c0988a) {
        super(activity);
        this.f1090w = c0988a;
        m4335c();
    }

    /* renamed from: c */
    private boolean m4335c() {
        try {
            this.f1091x = new C1028p(this.f1083a, this.f1090w);
            this.f1091x.setChromeProxy(this);
            this.f1091x.setWebClientProxy(this);
            this.f1091x.setWebEventProxy(this);
            addView(this.f1091x);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    /* renamed from: d */
    private void m4334d() {
        if (this.f1087t) {
            this.f1083a.finish();
        } else {
            this.f1091x.m4326a("javascript:window.AlipayJSBridge.callListener('h5BackAction');");
        }
    }

    /* renamed from: e */
    private void m4333e() {
        WebView webView = this.f1091x.getWebView();
        if (webView.canGoBack()) {
            webView.goBack();
            return;
        }
        C1036u c1036u = this.f1092y;
        if (c1036u != null && !c1036u.m4306b()) {
            m4332f();
        } else {
            m4340a(false);
        }
    }

    /* renamed from: a */
    public void m4341a(String str, String str2, boolean z) {
        this.f1088u = str2;
        this.f1091x.getTitle().setText(str);
        this.f1087t = z;
    }

    /* renamed from: a */
    private void m4340a(boolean z) {
        C0952j.m4645a(z);
        this.f1083a.finish();
    }

    @Override // com.alipay.sdk.widget.AbstractC1018g
    /* renamed from: a */
    public void mo4343a(String str) {
        if ("POST".equals(this.f1088u)) {
            this.f1091x.m4325a(str, (byte[]) null);
        } else {
            this.f1091x.m4326a(str);
        }
    }

    @Override // com.alipay.sdk.widget.AbstractC1018g
    /* renamed from: a */
    public void mo4346a() {
        this.f1091x.m4331a();
        this.f1092y.m4305c();
    }

    @Override // com.alipay.sdk.widget.AbstractC1018g
    /* renamed from: b */
    public boolean mo4339b() {
        if (!this.f1089v) {
            m4334d();
            return true;
        }
        return true;
    }

    @Override // com.alipay.sdk.widget.C1028p.AbstractC1029a
    /* renamed from: a */
    public boolean mo4315a(C1028p c1028p, String str, String str2, String str3, JsPromptResult jsPromptResult) {
        if (str2.startsWith("<head>") && str2.contains("sdk_result_code:")) {
            this.f1083a.runOnUiThread(new RunnableC1023k(this));
        }
        jsPromptResult.cancel();
        return true;
    }

    @Override // com.alipay.sdk.widget.C1028p.AbstractC1029a
    /* renamed from: a */
    public void mo4316a(C1028p c1028p, String str) {
        if (str.startsWith("http") || c1028p.getUrl().endsWith(str)) {
            return;
        }
        this.f1091x.getTitle().setText(str);
    }

    /* renamed from: f */
    private boolean m4332f() {
        if (this.f1092y.m4306b()) {
            this.f1083a.finish();
        } else {
            this.f1089v = true;
            C1028p c1028p = this.f1091x;
            this.f1091x = this.f1092y.m4308a();
            TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 1.0f, 1, 0.0f, 1, 0.0f);
            translateAnimation.setDuration(400L);
            translateAnimation.setFillAfter(false);
            translateAnimation.setAnimationListener(new C1024l(this, c1028p));
            c1028p.setAnimation(translateAnimation);
            removeView(c1028p);
            addView(this.f1091x);
        }
        return true;
    }

    /* renamed from: b */
    private boolean m4336b(String str, String str2) {
        C1028p c1028p = this.f1091x;
        try {
            this.f1091x = new C1028p(this.f1083a, this.f1090w);
            this.f1091x.setChromeProxy(this);
            this.f1091x.setWebClientProxy(this);
            this.f1091x.setWebEventProxy(this);
            if (!TextUtils.isEmpty(str2)) {
                this.f1091x.getTitle().setText(str2);
            }
            this.f1089v = true;
            this.f1092y.m4307a(c1028p);
            TranslateAnimation translateAnimation = new TranslateAnimation(1, 1.0f, 1, 0.0f, 1, 0.0f, 1, 0.0f);
            translateAnimation.setDuration(400L);
            translateAnimation.setFillAfter(false);
            translateAnimation.setAnimationListener(new C1025m(this, c1028p, str));
            this.f1091x.setAnimation(translateAnimation);
            addView(this.f1091x);
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    @Override // com.alipay.sdk.widget.C1028p.AbstractC1030b
    /* renamed from: b */
    public boolean mo4312b(C1028p c1028p, String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if (str.startsWith("alipayjsbridge://")) {
            m4337b(str.substring(17));
            return true;
        } else if (TextUtils.equals(str, "sdklite://h5quit")) {
            m4340a(false);
            return true;
        } else if (str.startsWith("http://") || str.startsWith("https://")) {
            this.f1091x.m4326a(str);
            return true;
        } else {
            try {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(Uri.parse(str));
                this.f1083a.startActivity(intent);
                return true;
            } catch (Throwable th) {
                C0954a.m4630a(this.f1090w, "biz", th);
                return true;
            }
        }
    }

    @Override // com.alipay.sdk.widget.C1028p.AbstractC1030b
    /* renamed from: c */
    public boolean mo4311c(C1028p c1028p, String str) {
        c1028p.m4326a("javascript:window.prompt('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');(function() {\n    if (window.AlipayJSBridge) {\n        return\n    }\n\n    function alipayjsbridgeFunc(url) {\n        var iframe = document.createElement(\"iframe\");\n        iframe.style.width = \"1px\";\n        iframe.style.height = \"1px\";\n        iframe.style.display = \"none\";\n        iframe.src = url;\n        document.body.appendChild(iframe);\n        setTimeout(function() {\n            document.body.removeChild(iframe)\n        }, 100)\n    }\n    window.alipayjsbridgeSetTitle = function(title) {\n        document.title = title;\n        alipayjsbridgeFunc(\"alipayjsbridge://setTitle?title=\" + encodeURIComponent(title))\n    };\n    window.alipayjsbridgeRefresh = function() {\n        alipayjsbridgeFunc(\"alipayjsbridge://onRefresh?\")\n    };\n    window.alipayjsbridgeBack = function() {\n        alipayjsbridgeFunc(\"alipayjsbridge://onBack?\")\n    };\n    window.alipayjsbridgeExit = function(bsucc) {\n        alipayjsbridgeFunc(\"alipayjsbridge://onExit?bsucc=\" + bsucc)\n    };\n    window.alipayjsbridgeShowBackButton = function(bshow) {\n        alipayjsbridgeFunc(\"alipayjsbridge://showBackButton?bshow=\" + bshow)\n    };\n    window.AlipayJSBridge = {\n        version: \"2.0\",\n        addListener: addListener,\n        hasListener: hasListener,\n        callListener: callListener,\n        callNativeFunc: callNativeFunc,\n        callBackFromNativeFunc: callBackFromNativeFunc\n    };\n    var uniqueId = 1;\n    var h5JsCallbackMap = {};\n\n    function iframeCall(paramStr) {\n        setTimeout(function() {\n        \tvar iframe = document.createElement(\"iframe\");\n        \tiframe.style.width = \"1px\";\n        \tiframe.style.height = \"1px\";\n        \tiframe.style.display = \"none\";\n        \tiframe.src = \"alipayjsbridge://callNativeFunc?\" + paramStr;\n        \tvar parent = document.body || document.documentElement;\n        \tparent.appendChild(iframe);\n        \tsetTimeout(function() {\n            \tparent.removeChild(iframe)\n        \t}, 0)\n        }, 0)\n    }\n\n    function callNativeFunc(nativeFuncName, data, h5JsCallback) {\n        var h5JsCallbackId = \"\";\n        if (h5JsCallback) {\n            h5JsCallbackId = \"cb_\" + (uniqueId++) + \"_\" + new Date().getTime();\n            h5JsCallbackMap[h5JsCallbackId] = h5JsCallback\n        }\n        var dataStr = \"\";\n        if (data) {\n            dataStr = encodeURIComponent(JSON.stringify(data))\n        }\n        var paramStr = \"func=\" + nativeFuncName + \"&cbId=\" + h5JsCallbackId + \"&data=\" + dataStr;\n        iframeCall(paramStr)\n    }\n\n    function callBackFromNativeFunc(h5JsCallbackId, data) {\n        var h5JsCallback = h5JsCallbackMap[h5JsCallbackId];\n        if (h5JsCallback) {\n            h5JsCallback(data);\n            delete h5JsCallbackMap[h5JsCallbackId]\n        }\n    }\n    var h5ListenerMap = {};\n\n    function addListener(jsFuncName, jsFunc) {\n        h5ListenerMap[jsFuncName] = jsFunc\n    }\n\n    function hasListener(jsFuncName) {\n        var jsFunc = h5ListenerMap[jsFuncName];\n        if (!jsFunc) {\n            return false\n        }\n        return true\n    }\n\n    function callListener(h5JsFuncName, data, nativeCallbackId) {\n        var responseCallback;\n        if (nativeCallbackId) {\n            responseCallback = function(responseData) {\n                var dataStr = \"\";\n                if (responseData) {\n                    dataStr = encodeURIComponent(JSON.stringify(responseData))\n                }\n                var paramStr = \"func=h5JsFuncCallback\" + \"&cbId=\" + nativeCallbackId + \"&data=\" + dataStr;\n                iframeCall(paramStr)\n            }\n        }\n        var h5JsFunc = h5ListenerMap[h5JsFuncName];\n        if (h5JsFunc) {\n            h5JsFunc(data, responseCallback)\n        } else if (h5JsFuncName == \"h5BackAction\") {\n            if (!window.alipayjsbridgeH5BackAction || !alipayjsbridgeH5BackAction()) {\n                var paramStr = \"func=back\";\n                iframeCall(paramStr)\n            }\n        } else {\n            console.log(\"AlipayJSBridge: no h5JsFunc \" + h5JsFuncName + data)\n        }\n    }\n    var event;\n    if (window.CustomEvent) {\n        event = new CustomEvent(\"alipayjsbridgeready\")\n    } else {\n        event = document.createEvent(\"Event\");\n        event.initEvent(\"alipayjsbridgeready\", true, true)\n    }\n    document.dispatchEvent(event);\n    setTimeout(excuteH5InitFuncs, 0);\n\n    function excuteH5InitFuncs() {\n        if (window.AlipayJSBridgeInitArray) {\n            var h5InitFuncs = window.AlipayJSBridgeInitArray;\n            delete window.AlipayJSBridgeInitArray;\n            for (var i = 0; i < h5InitFuncs.length; i++) {\n                try {\n                    h5InitFuncs[i](AlipayJSBridge)\n                } catch (e) {\n                    setTimeout(function() {\n                        throw e\n                    })\n                }\n            }\n        }\n    }\n})();\n;window.AlipayJSBridge.callListener('h5PageFinished');");
        c1028p.getRefreshButton().setVisibility(0);
        return true;
    }

    @Override // com.alipay.sdk.widget.C1028p.AbstractC1030b
    /* renamed from: a */
    public boolean mo4314a(C1028p c1028p, int i, String str, String str2) {
        C0988a c0988a = this.f1090w;
        C0954a.m4633a(c0988a, "net", "SSLError", "onReceivedError:" + str2);
        c1028p.getRefreshButton().setVisibility(0);
        return false;
    }

    @Override // com.alipay.sdk.widget.C1028p.AbstractC1030b
    /* renamed from: a */
    public boolean mo4313a(C1028p c1028p, SslErrorHandler sslErrorHandler, SslError sslError) {
        C0988a c0988a = this.f1090w;
        C0954a.m4633a(c0988a, "net", "SSLError", "2-" + sslError);
        this.f1083a.runOnUiThread(new RunnableC1026n(this, sslErrorHandler));
        return true;
    }

    /* renamed from: b */
    private void m4337b(String str) {
        Map<String, String> m4386a = C1008n.m4386a(this.f1090w, str);
        if (str.startsWith("callNativeFunc")) {
            m4342a(m4386a.get("func"), m4386a.get("cbId"), m4386a.get(AopConstants.APP_PROPERTIES_KEY));
        } else if (str.startsWith("onBack")) {
            m4333e();
        } else if (str.startsWith("setTitle") && m4386a.containsKey("title")) {
            this.f1091x.getTitle().setText(m4386a.get("title"));
        } else if (str.startsWith("onRefresh")) {
            this.f1091x.getWebView().reload();
        } else if (str.startsWith("showBackButton") && m4386a.containsKey("bshow")) {
            this.f1091x.getBackButton().setVisibility(TextUtils.equals("true", m4386a.get("bshow")) ? 0 : 4);
        } else if (str.startsWith("onExit")) {
            C0952j.m4646a(m4386a.get("result"));
            m4340a(TextUtils.equals("true", m4386a.get("bsucc")));
        } else if (!str.startsWith("onLoadJs")) {
        } else {
            this.f1091x.m4326a("javascript:(function() {\n    if (window.AlipayJSBridge) {\n        return\n    }\n\n    function alipayjsbridgeFunc(url) {\n        var iframe = document.createElement(\"iframe\");\n        iframe.style.width = \"1px\";\n        iframe.style.height = \"1px\";\n        iframe.style.display = \"none\";\n        iframe.src = url;\n        document.body.appendChild(iframe);\n        setTimeout(function() {\n            document.body.removeChild(iframe)\n        }, 100)\n    }\n    window.alipayjsbridgeSetTitle = function(title) {\n        document.title = title;\n        alipayjsbridgeFunc(\"alipayjsbridge://setTitle?title=\" + encodeURIComponent(title))\n    };\n    window.alipayjsbridgeRefresh = function() {\n        alipayjsbridgeFunc(\"alipayjsbridge://onRefresh?\")\n    };\n    window.alipayjsbridgeBack = function() {\n        alipayjsbridgeFunc(\"alipayjsbridge://onBack?\")\n    };\n    window.alipayjsbridgeExit = function(bsucc) {\n        alipayjsbridgeFunc(\"alipayjsbridge://onExit?bsucc=\" + bsucc)\n    };\n    window.alipayjsbridgeShowBackButton = function(bshow) {\n        alipayjsbridgeFunc(\"alipayjsbridge://showBackButton?bshow=\" + bshow)\n    };\n    window.AlipayJSBridge = {\n        version: \"2.0\",\n        addListener: addListener,\n        hasListener: hasListener,\n        callListener: callListener,\n        callNativeFunc: callNativeFunc,\n        callBackFromNativeFunc: callBackFromNativeFunc\n    };\n    var uniqueId = 1;\n    var h5JsCallbackMap = {};\n\n    function iframeCall(paramStr) {\n        setTimeout(function() {\n        \tvar iframe = document.createElement(\"iframe\");\n        \tiframe.style.width = \"1px\";\n        \tiframe.style.height = \"1px\";\n        \tiframe.style.display = \"none\";\n        \tiframe.src = \"alipayjsbridge://callNativeFunc?\" + paramStr;\n        \tvar parent = document.body || document.documentElement;\n        \tparent.appendChild(iframe);\n        \tsetTimeout(function() {\n            \tparent.removeChild(iframe)\n        \t}, 0)\n        }, 0)\n    }\n\n    function callNativeFunc(nativeFuncName, data, h5JsCallback) {\n        var h5JsCallbackId = \"\";\n        if (h5JsCallback) {\n            h5JsCallbackId = \"cb_\" + (uniqueId++) + \"_\" + new Date().getTime();\n            h5JsCallbackMap[h5JsCallbackId] = h5JsCallback\n        }\n        var dataStr = \"\";\n        if (data) {\n            dataStr = encodeURIComponent(JSON.stringify(data))\n        }\n        var paramStr = \"func=\" + nativeFuncName + \"&cbId=\" + h5JsCallbackId + \"&data=\" + dataStr;\n        iframeCall(paramStr)\n    }\n\n    function callBackFromNativeFunc(h5JsCallbackId, data) {\n        var h5JsCallback = h5JsCallbackMap[h5JsCallbackId];\n        if (h5JsCallback) {\n            h5JsCallback(data);\n            delete h5JsCallbackMap[h5JsCallbackId]\n        }\n    }\n    var h5ListenerMap = {};\n\n    function addListener(jsFuncName, jsFunc) {\n        h5ListenerMap[jsFuncName] = jsFunc\n    }\n\n    function hasListener(jsFuncName) {\n        var jsFunc = h5ListenerMap[jsFuncName];\n        if (!jsFunc) {\n            return false\n        }\n        return true\n    }\n\n    function callListener(h5JsFuncName, data, nativeCallbackId) {\n        var responseCallback;\n        if (nativeCallbackId) {\n            responseCallback = function(responseData) {\n                var dataStr = \"\";\n                if (responseData) {\n                    dataStr = encodeURIComponent(JSON.stringify(responseData))\n                }\n                var paramStr = \"func=h5JsFuncCallback\" + \"&cbId=\" + nativeCallbackId + \"&data=\" + dataStr;\n                iframeCall(paramStr)\n            }\n        }\n        var h5JsFunc = h5ListenerMap[h5JsFuncName];\n        if (h5JsFunc) {\n            h5JsFunc(data, responseCallback)\n        } else if (h5JsFuncName == \"h5BackAction\") {\n            if (!window.alipayjsbridgeH5BackAction || !alipayjsbridgeH5BackAction()) {\n                var paramStr = \"func=back\";\n                iframeCall(paramStr)\n            }\n        } else {\n            console.log(\"AlipayJSBridge: no h5JsFunc \" + h5JsFuncName + data)\n        }\n    }\n    var event;\n    if (window.CustomEvent) {\n        event = new CustomEvent(\"alipayjsbridgeready\")\n    } else {\n        event = document.createEvent(\"Event\");\n        event.initEvent(\"alipayjsbridgeready\", true, true)\n    }\n    document.dispatchEvent(event);\n    setTimeout(excuteH5InitFuncs, 0);\n\n    function excuteH5InitFuncs() {\n        if (window.AlipayJSBridgeInitArray) {\n            var h5InitFuncs = window.AlipayJSBridgeInitArray;\n            delete window.AlipayJSBridgeInitArray;\n            for (var i = 0; i < h5InitFuncs.length; i++) {\n                try {\n                    h5InitFuncs[i](AlipayJSBridge)\n                } catch (e) {\n                    setTimeout(function() {\n                        throw e\n                    })\n                }\n            }\n        }\n    }\n})();\n");
        }
    }

    /* renamed from: a */
    private void m4342a(String str, String str2, String str3) {
        JSONObject m4373c = C1008n.m4373c(str3);
        if ("title".equals(str) && m4373c.has("title")) {
            this.f1091x.getTitle().setText(m4373c.optString("title", ""));
        } else if ("refresh".equals(str)) {
            this.f1091x.getWebView().reload();
        } else if ("back".equals(str)) {
            m4333e();
        } else {
            int i = 0;
            if ("exit".equals(str)) {
                C0952j.m4646a(m4373c.optString("result", null));
                m4340a(m4373c.optBoolean("success", false));
            } else if ("backButton".equals(str)) {
                boolean optBoolean = m4373c.optBoolean("show", true);
                ImageView backButton = this.f1091x.getBackButton();
                if (!optBoolean) {
                    i = 4;
                }
                backButton.setVisibility(i);
            } else if ("refreshButton".equals(str)) {
                boolean optBoolean2 = m4373c.optBoolean("show", true);
                ImageView refreshButton = this.f1091x.getRefreshButton();
                if (!optBoolean2) {
                    i = 4;
                }
                refreshButton.setVisibility(i);
            } else if (!"pushWindow".equals(str) || m4373c.optString("url", null) == null) {
            } else {
                m4336b(m4373c.optString("url"), m4373c.optString("title", ""));
            }
        }
    }

    @Override // com.alipay.sdk.widget.C1028p.AbstractC1031c
    /* renamed from: a */
    public void mo4310a(C1028p c1028p) {
        m4334d();
    }

    @Override // com.alipay.sdk.widget.C1028p.AbstractC1031c
    /* renamed from: b */
    public void mo4309b(C1028p c1028p) {
        c1028p.getWebView().reload();
        c1028p.getRefreshButton().setVisibility(4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.alipay.sdk.widget.j$a  reason: invalid class name */
    /* loaded from: classes2.dex */
    public abstract class AbstractanimationAnimation$AnimationListenerC1022a implements Animation.AnimationListener {
        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationRepeat(Animation animation) {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationStart(Animation animation) {
        }

        private AbstractanimationAnimation$AnimationListenerC1022a(C1021j c1021j) {
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public /* synthetic */ AbstractanimationAnimation$AnimationListenerC1022a(C1021j c1021j, RunnableC1023k runnableC1023k) {
            this(c1021j);
        }
    }
}
