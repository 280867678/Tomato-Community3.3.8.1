package com.netease.nis.captcha;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import com.netease.nis.captcha.CaptchaConfiguration;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.netease.nis.captcha.e */
/* loaded from: classes3.dex */
public class C2411e {

    /* renamed from: b */
    private Context f1652b;

    /* renamed from: a */
    private Captcha f1651a = Captcha.getInstance();

    /* renamed from: c */
    private final DialogC2405b f1653c = this.f1651a.m3840c();

    /* renamed from: d */
    private final CaptchaConfiguration f1654d = this.f1651a.m3838d();

    /* renamed from: e */
    private final CaptchaListener f1655e = this.f1654d.f1581m;

    /* renamed from: f */
    private final CaptchaWebView f1656f = (CaptchaWebView) this.f1653c.m3822d();

    /* renamed from: g */
    private final DialogC2407c f1657g = this.f1651a.m3842b();

    public C2411e(Context context) {
        this.f1652b = context;
    }

    /* renamed from: a */
    private void m3797a() {
        ((Activity) this.f1652b).runOnUiThread(new Runnable() { // from class: com.netease.nis.captcha.e.3
            @Override // java.lang.Runnable
            public void run() {
                if (!C2411e.this.f1653c.isShowing()) {
                    C2411e.this.f1653c.show();
                }
            }
        });
    }

    /* renamed from: b */
    private void m3795b() {
        if (this.f1656f != null) {
            C2409d.m3800a("%s", "智能无感知调用captchaVerify");
            ((Activity) this.f1652b).runOnUiThread(new Runnable() { // from class: com.netease.nis.captcha.e.4
                @Override // java.lang.Runnable
                public void run() {
                    C2411e.this.f1656f.loadUrl("javascript:captchaVerify()");
                }
            });
        }
    }

    /* renamed from: c */
    private void m3793c() {
        ((Activity) this.f1652b).runOnUiThread(new Runnable() { // from class: com.netease.nis.captcha.e.5
            @Override // java.lang.Runnable
            public void run() {
                if (C2411e.this.f1654d.f1572d == CaptchaConfiguration.ModeType.MODE_INTELLIGENT_NO_SENSE) {
                    C2411e.this.f1653c.getWindow().setDimAmount(C2411e.this.f1654d.f1574f);
                }
                if (C2411e.this.f1653c.m3821e().getVisibility() == 4) {
                    C2409d.m3800a("%s", "显示验证码视图");
                    C2411e.this.f1653c.m3821e().setVisibility(0);
                }
            }
        });
    }

    /* renamed from: d */
    private void m3791d() {
        if (this.f1651a.m3842b() != null) {
            this.f1651a.m3842b().dismiss();
        }
    }

    @JavascriptInterface
    public void onError(String str) {
        C2409d.m3800a("%s", "onError is callback" + str);
        this.f1653c.dismiss();
        try {
            JSONObject jSONObject = new JSONObject(str);
            int i = jSONObject.getInt("code");
            String string = jSONObject.getString("message");
            if (this.f1655e != null) {
                this.f1655e.onError(i, string);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            C2409d.m3800a("%s", "onError is callback" + str);
            CaptchaListener captchaListener = this.f1655e;
            if (captchaListener != null) {
                captchaListener.onError(2000, e.toString());
            }
        }
        if (this.f1657g != null) {
            ((Activity) this.f1652b).runOnUiThread(new Runnable() { // from class: com.netease.nis.captcha.e.2
                @Override // java.lang.Runnable
                public void run() {
                    if (!C2411e.this.f1657g.isShowing()) {
                        C2411e.this.f1657g.show();
                    }
                    C2411e.this.f1657g.m3808c(C2398R.string.tip_load_failed);
                }
            });
        }
    }

    @JavascriptInterface
    public void onLoad() {
        C2409d.m3800a("%s", "onLoad is callback");
        if (this.f1654d.f1572d == CaptchaConfiguration.ModeType.MODE_CAPTCHA) {
            ((Activity) this.f1652b).runOnUiThread(new Runnable() { // from class: com.netease.nis.captcha.e.1
                @Override // java.lang.Runnable
                public void run() {
                    C2411e.this.f1656f.loadUrl("javascript:popupCaptcha()");
                }
            });
        }
    }

    @JavascriptInterface
    public void onReady(int i, int i2) {
        C2409d.m3800a("%s", "onReady is callback");
        m3797a();
        m3791d();
        CaptchaListener captchaListener = this.f1655e;
        if (captchaListener != null) {
            captchaListener.onReady();
        }
        if (this.f1654d.f1572d == CaptchaConfiguration.ModeType.MODE_INTELLIGENT_NO_SENSE) {
            m3795b();
        } else {
            m3793c();
        }
    }

    @JavascriptInterface
    public void onValidate(String str, String str2, String str3, String str4) {
        C2409d.m3800a("result=%s validate =%s message =%s next=%s", str, str2, str3, str4);
        if (!TextUtils.isEmpty(str4) && str4.equals("true")) {
            m3793c();
        } else if (!TextUtils.isEmpty(str2)) {
            this.f1651a.m3843a(true);
            this.f1651a.m3840c().dismiss();
        }
        if (this.f1655e != null && !str4.equals("true")) {
            DialogC2407c dialogC2407c = this.f1657g;
            if (dialogC2407c != null && !dialogC2407c.isShowing()) {
                this.f1657g.dismiss();
            }
            this.f1655e.onValidate(str, str2, str3);
        }
        DialogC2407c dialogC2407c2 = this.f1657g;
        if (dialogC2407c2 == null || !dialogC2407c2.isShowing()) {
            return;
        }
        this.f1657g.dismiss();
    }
}
