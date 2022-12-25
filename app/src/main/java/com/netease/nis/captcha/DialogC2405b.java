package com.netease.nis.captcha;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import com.netease.nis.captcha.CaptchaConfiguration;
import com.tomatolive.library.http.utils.EncryptUtil;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/* renamed from: com.netease.nis.captcha.b */
/* loaded from: classes3.dex */
public class DialogC2405b extends Dialog {

    /* renamed from: A */
    private String f1611A;

    /* renamed from: b */
    boolean f1613b;

    /* renamed from: c */
    private final String f1614c;

    /* renamed from: d */
    private final Context f1615d;

    /* renamed from: e */
    private final String f1616e;

    /* renamed from: f */
    private final CaptchaConfiguration.ModeType f1617f;

    /* renamed from: g */
    private final CaptchaConfiguration.LangType f1618g;

    /* renamed from: h */
    private final float f1619h;

    /* renamed from: i */
    private final String f1620i;

    /* renamed from: j */
    private final String f1621j;

    /* renamed from: k */
    private final String f1622k;

    /* renamed from: l */
    private final int f1623l;

    /* renamed from: m */
    private final int f1624m;

    /* renamed from: n */
    private final int f1625n;

    /* renamed from: p */
    private final boolean f1627p;

    /* renamed from: q */
    private final boolean f1628q;

    /* renamed from: r */
    private final long f1629r;

    /* renamed from: s */
    private final int f1630s;

    /* renamed from: u */
    private final CaptchaListener f1632u;

    /* renamed from: v */
    private CaptchaWebView f1633v;

    /* renamed from: w */
    private View f1634w;

    /* renamed from: x */
    private String f1635x;

    /* renamed from: y */
    private String f1636y;

    /* renamed from: z */
    private boolean f1637z;

    /* renamed from: o */
    private final boolean f1626o = true;

    /* renamed from: t */
    private final int f1631t = 270;

    /* renamed from: a */
    boolean f1612a = false;

    public DialogC2405b(CaptchaConfiguration captchaConfiguration) {
        super(captchaConfiguration.f1569a, C2398R.C2403style.CaptchaDialogStyle);
        boolean z = true;
        this.f1615d = captchaConfiguration.f1569a;
        this.f1616e = captchaConfiguration.f1570b;
        this.f1614c = captchaConfiguration.f1571c;
        this.f1617f = captchaConfiguration.f1572d;
        this.f1618g = captchaConfiguration.f1573e;
        this.f1619h = captchaConfiguration.f1574f;
        this.f1620i = captchaConfiguration.f1575g;
        this.f1621j = captchaConfiguration.f1576h;
        this.f1622k = captchaConfiguration.f1577i;
        this.f1623l = captchaConfiguration.f1578j;
        this.f1624m = captchaConfiguration.f1579k;
        int i = captchaConfiguration.f1580l;
        this.f1625n = i == 0 ? m3827a(0) : i;
        this.f1627p = captchaConfiguration.f1584p;
        this.f1628q = captchaConfiguration.f1585q;
        this.f1629r = captchaConfiguration.f1582n;
        this.f1630s = captchaConfiguration.f1586r;
        this.f1632u = captchaConfiguration.f1581m;
        this.f1635x = captchaConfiguration.f1587s;
        this.f1636y = captchaConfiguration.f1588t;
        this.f1637z = captchaConfiguration.f1572d != CaptchaConfiguration.ModeType.MODE_INTELLIGENT_NO_SENSE ? false : z;
        this.f1613b = captchaConfiguration.f1583o;
        this.f1611A = captchaConfiguration.f1589u;
        m3819g();
    }

    /* renamed from: a */
    private int m3827a(int i) {
        DisplayMetrics displayMetrics = this.f1615d.getResources().getDisplayMetrics();
        int i2 = displayMetrics.widthPixels;
        int i3 = displayMetrics.heightPixels;
        float f = displayMetrics.density;
        if (i < 270) {
            if (i3 < i2) {
                i2 = (i3 * 3) / 4;
            }
            int i4 = (i2 * 4) / 5;
            return ((int) (((float) i4) / f)) < 270 ? (int) (f * 270.0f) : i4;
        }
        return i;
    }

    /* renamed from: f */
    private void m3820f() {
        Window window;
        float f;
        C2409d.m3800a("%s", "设置ContentView");
        View view = this.f1634w;
        if (view != null) {
            setContentView(view);
        } else {
            setContentView(C2398R.C2402layout.dailog_captcha);
        }
        if (this.f1633v == null) {
            this.f1633v = (CaptchaWebView) findViewById(C2398R.C2401id.web_view);
            this.f1633v.setCaptchaListener(this.f1632u);
        }
        findViewById(C2398R.C2401id.img_btn_close).setOnClickListener(new View.OnClickListener() { // from class: com.netease.nis.captcha.b.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (!DialogC2405b.this.f1637z) {
                    this.dismiss();
                    return;
                }
                this.hide();
                DialogC2405b.this.f1612a = true;
            }
        });
        this.f1634w.setVisibility(4);
        if (this.f1613b) {
            findViewById(C2398R.C2401id.img_btn_close).setVisibility(4);
        }
        if (this.f1617f == CaptchaConfiguration.ModeType.MODE_INTELLIGENT_NO_SENSE) {
            window = getWindow();
            f = 0.0f;
        } else {
            window = getWindow();
            f = this.f1619h;
        }
        window.setDimAmount(f);
        setCanceledOnTouchOutside(this.f1627p);
    }

    /* renamed from: g */
    private void m3819g() {
        C2409d.m3800a("set dialog position x:%d y:%d width:%d", Integer.valueOf(this.f1623l), Integer.valueOf(this.f1624m), Integer.valueOf(this.f1625n));
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        int i = this.f1623l;
        if (i != -1) {
            attributes.gravity = 3 | attributes.gravity;
            attributes.x = i;
        }
        int i2 = this.f1624m;
        if (i2 != -1) {
            attributes.gravity |= 48;
            attributes.y = i2;
        }
        int i3 = this.f1625n;
        if (i3 != 0) {
            attributes.width = i3;
        }
        attributes.gravity = 17;
        getWindow().setAttributes(attributes);
    }

    /* renamed from: h */
    private String m3818h() {
        float f = getContext().getResources().getDisplayMetrics().density;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("file:///android_asset/" + C2404a.m3829a(this.f1615d).m3830a());
        stringBuffer.append("?captchaId=" + this.f1616e);
        if (this.f1617f == CaptchaConfiguration.ModeType.MODE_INTELLIGENT_NO_SENSE) {
            stringBuffer.append("&mode=bind");
        }
        stringBuffer.append("&os=android");
        stringBuffer.append("&osVer=" + Build.VERSION.RELEASE);
        stringBuffer.append("&sdkVer=3.1.6");
        float f2 = ((float) this.f1625n) / f;
        try {
            stringBuffer.append("&popupStyles.width=" + URLEncoder.encode(String.valueOf(f2), EncryptUtil.CHARSET));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            stringBuffer.append("&popupStyles.width=" + f2);
        }
        String m3801a = C2409d.m3801a(this.f1618g);
        if (!TextUtils.isEmpty(m3801a)) {
            stringBuffer.append("&lang=" + m3801a);
        }
        if (!TextUtils.isEmpty(this.f1620i)) {
            stringBuffer.append("&slideIcon=" + this.f1620i);
        }
        if (!TextUtils.isEmpty(this.f1621j)) {
            stringBuffer.append("&slideIconMoving=" + this.f1621j);
        }
        if (!TextUtils.isEmpty(this.f1622k)) {
            stringBuffer.append("&slideIconError=" + this.f1622k);
        }
        stringBuffer.append("&defaultFallback=" + this.f1628q);
        stringBuffer.append("&errorFallbackCount=" + this.f1630s);
        stringBuffer.append("&mobileTimeout=" + this.f1629r);
        if (!TextUtils.isEmpty(this.f1635x)) {
            stringBuffer.append("&apiServer=" + this.f1635x);
        }
        if (!TextUtils.isEmpty(this.f1636y)) {
            stringBuffer.append("&staticServer=" + this.f1636y);
        }
        if (!TextUtils.isEmpty(this.f1611A)) {
            stringBuffer.append("&protocol=" + this.f1611A);
        }
        return stringBuffer.toString();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: a */
    public void m3828a() {
        this.f1634w = LayoutInflater.from(this.f1615d).inflate(C2398R.C2402layout.dailog_captcha, (ViewGroup) null);
        this.f1633v = (CaptchaWebView) this.f1634w.findViewById(C2398R.C2401id.web_view);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: a */
    public void m3825a(boolean z) {
        this.f1612a = z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: b */
    public void m3824b() {
        ViewGroup.LayoutParams layoutParams = this.f1633v.getLayoutParams();
        int i = this.f1625n;
        if (i != 0) {
            layoutParams.width = i;
        }
        layoutParams.height = -2;
        this.f1633v.setLayoutParams(layoutParams);
        C2409d.m3800a("%s", "request url is:" + m3818h());
        this.f1633v.addJavascriptInterface(new C2411e(this.f1615d), "JSInterface");
        this.f1633v.loadUrl(m3818h());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: c */
    public void m3823c() {
        String m3818h = m3818h();
        C2409d.m3800a("%s", "reload url is:" + m3818h);
        this.f1633v.loadUrl(m3818h);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: d */
    public WebView m3822d() {
        return this.f1633v;
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        try {
            if (this.f1615d == null || ((Activity) this.f1615d).isFinishing()) {
                return;
            }
            super.dismiss();
        } catch (Exception e) {
            C2409d.m3798b(Captcha.TAG, "Captcha  Dialog dismiss Error: %s", e.toString());
        }
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (!this.f1613b || keyEvent.getKeyCode() != 4) {
            return super.dispatchKeyEvent(keyEvent);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: e */
    public View m3821e() {
        return this.f1634w;
    }

    @Override // android.app.Dialog
    public void onBackPressed() {
        super.onBackPressed();
        if (this.f1637z) {
            hide();
            this.f1612a = true;
        }
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        m3820f();
    }

    @Override // android.app.Dialog
    public void show() {
        try {
            if (this.f1615d == null || ((Activity) this.f1615d).isFinishing()) {
                return;
            }
            super.show();
        } catch (Exception e) {
            C2409d.m3798b("Captcha  Dialog show Error:%s", e.toString());
        }
    }
}
