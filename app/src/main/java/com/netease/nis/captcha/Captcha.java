package com.netease.nis.captcha;

import android.content.DialogInterface;
import com.netease.nis.captcha.CaptchaConfiguration;

/* loaded from: classes3.dex */
public class Captcha {
    public static final int NO_NETWORK = 2001;
    public static final int SDK_INTERNAL_ERROR = 2000;
    public static final String SDK_VERSION = "3.1.6";
    public static final String TAG = "Captcha";
    public static final int WEB_VIEW_HTTPS_ERROR = 2004;
    public static final int WEB_VIEW_HTTP_ERROR = 2003;
    public static final int WEB_VIEW_REQUEST_ERROR = 2002;

    /* renamed from: a */
    private static Captcha f1562a;

    /* renamed from: b */
    private CaptchaConfiguration f1563b;

    /* renamed from: c */
    private DialogC2407c f1564c;

    /* renamed from: d */
    private DialogC2405b f1565d;

    /* renamed from: e */
    private boolean f1566e = true;

    /* renamed from: f */
    private boolean f1567f;

    private Captcha() {
    }

    /* renamed from: e */
    private void m3836e() {
        this.f1564c = new DialogC2407c(this.f1563b.f1569a);
        this.f1564c.m3814a(this.f1563b.f1590v);
        this.f1564c.m3816a(this.f1563b.f1591w);
        this.f1564c.m3812b(this.f1563b.f1592x);
        this.f1564c.show();
    }

    /* renamed from: f */
    private void m3835f() {
        DialogC2405b dialogC2405b = this.f1565d;
        if (dialogC2405b != null) {
            dialogC2405b.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.netease.nis.captcha.Captcha.1
                @Override // android.content.DialogInterface.OnDismissListener
                public void onDismiss(DialogInterface dialogInterface) {
                    if (Captcha.this.f1566e) {
                        if (Captcha.this.f1565d == null) {
                            return;
                        }
                        Captcha.this.f1565d.m3825a(true);
                    } else if (Captcha.this.f1567f || Captcha.this.f1563b == null) {
                    } else {
                        Captcha.this.f1563b.f1581m.onClose();
                    }
                }
            });
        }
    }

    public static Captcha getInstance() {
        if (f1562a == null) {
            synchronized (Captcha.class) {
                if (f1562a == null) {
                    f1562a = new Captcha();
                }
            }
        }
        return f1562a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: a */
    public void m3845a() {
        if (this.f1565d == null) {
            this.f1565d = new DialogC2405b(this.f1563b);
            this.f1565d.m3828a();
        }
        this.f1565d.m3824b();
        m3835f();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: a */
    public void m3843a(boolean z) {
        this.f1567f = z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: b */
    public DialogC2407c m3842b() {
        return this.f1564c;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: c */
    public DialogC2405b m3840c() {
        return this.f1565d;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: d */
    public CaptchaConfiguration m3838d() {
        return this.f1563b;
    }

    public void destroy() {
        DialogC2407c dialogC2407c = this.f1564c;
        if (dialogC2407c != null) {
            if (dialogC2407c.isShowing()) {
                this.f1564c.dismiss();
            }
            this.f1564c = null;
        }
        DialogC2405b dialogC2405b = this.f1565d;
        if (dialogC2405b != null) {
            if (dialogC2405b.isShowing()) {
                this.f1565d.dismiss();
            }
            this.f1565d = null;
        }
        if (this.f1563b != null) {
            this.f1563b = null;
        }
    }

    public void destroy(boolean z) {
        DialogC2407c dialogC2407c = this.f1564c;
        if (dialogC2407c != null) {
            if (dialogC2407c.isShowing()) {
                this.f1564c.dismiss();
            }
            this.f1564c = null;
        }
        DialogC2405b dialogC2405b = this.f1565d;
        if (dialogC2405b != null) {
            if (dialogC2405b.isShowing()) {
                this.f1565d.dismiss();
            }
            if (z) {
                this.f1565d.m3822d().pauseTimers();
            }
            this.f1565d = null;
        }
        if (this.f1563b != null) {
            this.f1563b = null;
        }
    }

    public Captcha init(CaptchaConfiguration captchaConfiguration) {
        if (captchaConfiguration != null) {
            if (captchaConfiguration.f1581m == null) {
                throw new IllegalStateException("you must set a CaptchaListener before use it");
            }
            this.f1563b = captchaConfiguration;
            CaptchaConfiguration captchaConfiguration2 = this.f1563b;
            C2409d.m3803a(captchaConfiguration2.f1569a, captchaConfiguration2.f1573e);
            this.f1566e = captchaConfiguration.f1572d == CaptchaConfiguration.ModeType.MODE_INTELLIGENT_NO_SENSE;
            return this;
        }
        throw new IllegalArgumentException("CaptchaConfiguration  is not allowed to be null");
    }

    public void validate() {
        if (!C2409d.m3804a(this.f1563b.f1569a)) {
            m3836e();
            this.f1564c.m3808c(C2398R.string.tip_no_network);
            this.f1563b.f1581m.onError(2001, "no network,please check your network");
            return;
        }
        DialogC2405b dialogC2405b = this.f1565d;
        if (dialogC2405b != null && dialogC2405b.f1612a && !this.f1567f) {
            dialogC2405b.show();
            this.f1567f = false;
            return;
        }
        this.f1567f = false;
        this.f1565d = new DialogC2405b(this.f1563b);
        this.f1565d.m3828a();
        m3836e();
        m3845a();
    }
}
