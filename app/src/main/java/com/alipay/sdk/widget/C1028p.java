package com.alipay.sdk.widget;

import android.content.Context;
import android.net.http.SslError;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.alipay.sdk.sys.C0988a;
import com.alipay.sdk.util.C1005k;
import com.alipay.sdk.util.C1008n;

/* renamed from: com.alipay.sdk.widget.p */
/* loaded from: classes2.dex */
public class C1028p extends LinearLayout {

    /* renamed from: f */
    private static Handler f1102f = new Handler(Looper.getMainLooper());

    /* renamed from: a */
    private ImageView f1103a;

    /* renamed from: b */
    private TextView f1104b;

    /* renamed from: c */
    private ImageView f1105c;

    /* renamed from: d */
    private ProgressBar f1106d;

    /* renamed from: e */
    private WebView f1107e;

    /* renamed from: g */
    private AbstractC1029a f1108g;

    /* renamed from: h */
    private AbstractC1030b f1109h;

    /* renamed from: i */
    private AbstractC1031c f1110i;

    /* renamed from: j */
    private final C0988a f1111j;

    /* renamed from: k */
    private View.OnClickListener f1112k;

    /* renamed from: l */
    private final float f1113l;

    /* renamed from: com.alipay.sdk.widget.p$a */
    /* loaded from: classes2.dex */
    public interface AbstractC1029a {
        /* renamed from: a */
        void mo4316a(C1028p c1028p, String str);

        /* renamed from: a */
        boolean mo4315a(C1028p c1028p, String str, String str2, String str3, JsPromptResult jsPromptResult);
    }

    /* renamed from: com.alipay.sdk.widget.p$b */
    /* loaded from: classes2.dex */
    public interface AbstractC1030b {
        /* renamed from: a */
        boolean mo4314a(C1028p c1028p, int i, String str, String str2);

        /* renamed from: a */
        boolean mo4313a(C1028p c1028p, SslErrorHandler sslErrorHandler, SslError sslError);

        /* renamed from: b */
        boolean mo4312b(C1028p c1028p, String str);

        /* renamed from: c */
        boolean mo4311c(C1028p c1028p, String str);
    }

    /* renamed from: com.alipay.sdk.widget.p$c */
    /* loaded from: classes2.dex */
    public interface AbstractC1031c {
        /* renamed from: a */
        void mo4310a(C1028p c1028p);

        /* renamed from: b */
        void mo4309b(C1028p c1028p);
    }

    public C1028p(Context context, C0988a c0988a) {
        this(context, null, c0988a);
    }

    public C1028p(Context context, AttributeSet attributeSet, C0988a c0988a) {
        super(context, attributeSet);
        this.f1112k = new View$OnClickListenerC1032q(this);
        this.f1111j = c0988a;
        this.f1113l = context.getResources().getDisplayMetrics().density;
        setOrientation(1);
        m4329a(context);
        m4323b(context);
        m4321c(context);
    }

    /* renamed from: a */
    private void m4329a(Context context) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setBackgroundColor(-218103809);
        linearLayout.setOrientation(0);
        linearLayout.setGravity(16);
        this.f1103a = new ImageView(context);
        this.f1103a.setOnClickListener(this.f1112k);
        this.f1103a.setScaleType(ImageView.ScaleType.CENTER);
        this.f1103a.setImageDrawable(C1005k.m4405a("iVBORw0KGgoAAAANSUhEUgAAAEgAAABIBAMAAACnw650AAAAFVBMVEUAAAARjusRkOkQjuoRkeoRj+oQjunya570AAAABnRSTlMAinWeSkk7CjRNAAAAZElEQVRIx+3MOw6AIBQF0YsrMDGx1obaLeGH/S9BQgkJ82rypp4ceTN1ilvyKizmZIAyU7FML0JVYig55BBAfQ2EU4V4CpZJ+2AiSj11C6rUoTannBpRn4W6xNQjLBSI2+TN0w/+3HT2wPClrQAAAABJRU5ErkJggg==", context));
        this.f1103a.setPadding(m4330a(12), 0, m4330a(12), 0);
        linearLayout.addView(this.f1103a, new LinearLayout.LayoutParams(-2, -2));
        View view = new View(context);
        view.setBackgroundColor(-2500135);
        linearLayout.addView(view, new LinearLayout.LayoutParams(m4330a(1), m4330a(25)));
        this.f1104b = new TextView(context);
        this.f1104b.setTextColor(-15658735);
        this.f1104b.setTextSize(17.0f);
        this.f1104b.setMaxLines(1);
        this.f1104b.setEllipsize(TextUtils.TruncateAt.END);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.setMargins(m4330a(17), 0, 0, 0);
        layoutParams.weight = 1.0f;
        linearLayout.addView(this.f1104b, layoutParams);
        this.f1105c = new ImageView(context);
        this.f1105c.setOnClickListener(this.f1112k);
        this.f1105c.setScaleType(ImageView.ScaleType.CENTER);
        this.f1105c.setImageDrawable(C1005k.m4405a("iVBORw0KGgoAAAANSUhEUgAAAEgAAABICAMAAABiM0N1AAAAmVBMVEUAAAARj+oQjuoRkOsVk/AQj+oRjuoQj+oSkO3///8Rj+kRj+oQkOsTk+whm/8Qj+oRj+oQj+oSkus2p/8QjuoQj+oQj+oQj+oQj+oRj+oTkuwRj+oQj+oRj+oRj+oSkOsSkO0ZlfMbk+8XnPgQj+oRj+oQj+oQj+sSj+sRkOoSkescqv8Rj+oQj+oSj+sXku4Rj+kQjuoQjumXGBCVAAAAMnRSTlMAxPtPF8ry7CoB9npbGwe6lm0wBODazb1+aSejm5GEYjcTDwvls6uJc0g/CdWfRCF20AXrk5QAAAJqSURBVFjD7ZfXmpswEIUFphmDCxi3talurGvm/R8uYSDe5FNBwlzsxf6XmvFBmiaZ/PCdWDk9CWn61OhHCMAaXfoRAth7wx6EkMXnWyrho4yg4bDpquI8Jy78Q7eoj9cmUFijsaLM0JsD9CD0uQAa9aNdPuCFvbA7B9t/Becap8Pu6Q/2jcyH81VHc/WCHDQZXwbvtUhQ61iDlqadncU6Rp31yGkZIzOAu7AjtPpYGREzq/pY5DRFHS1siyO6HfkOKTrMjdb2qevV4zosK7MbkFY2LmYk55hL6juCIFWMOI2KGzblmho3b18EIbxL1hs6r5m2Q2WaEElwS3NW4xh6ZZJuzTtUsBKT4G0h35s4y1mNgkNoS6TZ8SKBXTZQGBNYdPTozXGYKoyLAmOasttjThT4xT6Ch+2qIjRhV9Ja3NC87Kyo5We1vCNEMW1T+j1VLZ9UhE54Q1DL52r5piJ0YxdegvWlHOwTu76uKkJX+MOTHno4YFSEbHYdhViojsLrCTg/MKnhKWaEYzvkZFM8aOkPH7iTSvoFZKD7jGEJbarkRaxQyOeWvGVIbsji152jK7TbDgRzcIuz7SGj89BFU8d30TqWeDtrILxyTkD1IXfvmHseuU3lVHDz607bw0f3xDqejm5ncd0j8VDwfoibRy8RcgTkWHBvocbDbMlJsQAkGnAOHwGy90kLmQY1Wkob07/GaCNRIzdoWK7/+6y/XkLDJCcynOGFuUrKIMuCMonNr9VpSOQoIxBgJ0SacGbzZNy4ICrkscvU2fpElYz+U3sd+aQThjfVmjNa5i15kLcojM3Gz8kP34jf4VaV3X55gNEAAAAASUVORK5CYII=", context));
        this.f1105c.setPadding(m4330a(12), 0, m4330a(12), 0);
        linearLayout.addView(this.f1105c, new LinearLayout.LayoutParams(-2, -2));
        addView(linearLayout, new LinearLayout.LayoutParams(-1, m4330a(48)));
    }

    /* renamed from: b */
    private void m4323b(Context context) {
        this.f1106d = new ProgressBar(context, null, 16973855);
        this.f1106d.setProgressDrawable(context.getResources().getDrawable(17301612));
        this.f1106d.setMax(100);
        this.f1106d.setBackgroundColor(-218103809);
        addView(this.f1106d, new LinearLayout.LayoutParams(-1, m4330a(2)));
    }

    /* renamed from: c */
    private void m4321c(Context context) {
        this.f1107e = new WebView(context);
        this.f1107e.setVerticalScrollbarOverlay(true);
        m4328a(this.f1107e, context);
        WebSettings settings = this.f1107e.getSettings();
        settings.setUseWideViewPort(true);
        settings.setAppCacheMaxSize(5242880L);
        settings.setAppCachePath(context.getCacheDir().getAbsolutePath());
        settings.setAllowFileAccess(true);
        settings.setAppCacheEnabled(true);
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(-1);
        settings.setSupportMultipleWindows(true);
        settings.setJavaScriptEnabled(true);
        settings.setSavePassword(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDomStorageEnabled(true);
        try {
            this.f1107e.removeJavascriptInterface("searchBoxJavaBridge_");
            this.f1107e.removeJavascriptInterface("accessibility");
            this.f1107e.removeJavascriptInterface("accessibilityTraversal");
        } catch (Exception unused) {
        }
        addView(this.f1107e, new LinearLayout.LayoutParams(-1, -1));
    }

    /* renamed from: a */
    protected void m4328a(WebView webView, Context context) {
        String userAgentString = webView.getSettings().getUserAgentString();
        String packageName = context.getPackageName();
        String m4392a = C1008n.m4392a(this.f1111j, context);
        webView.getSettings().setUserAgentString(userAgentString + " AlipaySDK(" + packageName + "/" + m4392a + "/15.7.4)");
    }

    public void setChromeProxy(AbstractC1029a abstractC1029a) {
        this.f1108g = abstractC1029a;
        if (abstractC1029a == null) {
            this.f1107e.setWebChromeClient(null);
        } else {
            this.f1107e.setWebChromeClient(new C1034s(this));
        }
    }

    public void setWebClientProxy(AbstractC1030b abstractC1030b) {
        this.f1109h = abstractC1030b;
        if (abstractC1030b == null) {
            this.f1107e.setWebViewClient(null);
        } else {
            this.f1107e.setWebViewClient(new C1035t(this));
        }
    }

    public void setWebEventProxy(AbstractC1031c abstractC1031c) {
        this.f1110i = abstractC1031c;
    }

    public String getUrl() {
        return this.f1107e.getUrl();
    }

    /* renamed from: a */
    public void m4326a(String str) {
        this.f1107e.loadUrl(str);
    }

    /* renamed from: a */
    public void m4325a(String str, byte[] bArr) {
        this.f1107e.postUrl(str, bArr);
    }

    public ImageView getBackButton() {
        return this.f1103a;
    }

    public TextView getTitle() {
        return this.f1104b;
    }

    public ImageView getRefreshButton() {
        return this.f1105c;
    }

    public ProgressBar getProgressbar() {
        return this.f1106d;
    }

    public WebView getWebView() {
        return this.f1107e;
    }

    /* renamed from: a */
    public void m4331a() {
        removeAllViews();
        this.f1107e.removeAllViews();
        this.f1107e.setWebViewClient(null);
        this.f1107e.setWebChromeClient(null);
        this.f1107e.destroy();
    }

    /* renamed from: a */
    private int m4330a(int i) {
        return (int) (i * this.f1113l);
    }
}
