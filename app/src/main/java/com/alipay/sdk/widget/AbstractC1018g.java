package com.alipay.sdk.widget;

import android.app.Activity;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.FrameLayout;

/* renamed from: com.alipay.sdk.widget.g */
/* loaded from: classes2.dex */
public abstract class AbstractC1018g extends FrameLayout {

    /* renamed from: a */
    protected Activity f1083a;

    /* renamed from: a */
    public abstract void mo4346a();

    /* renamed from: a */
    public abstract void mo4343a(String str);

    /* renamed from: b */
    public abstract boolean mo4339b();

    public AbstractC1018g(Activity activity) {
        super(activity);
        this.f1083a = activity;
    }

    /* renamed from: a */
    public void m4348a(String str, String str2) {
        if (!TextUtils.isEmpty(str2)) {
            CookieSyncManager.createInstance(this.f1083a.getApplicationContext()).sync();
            CookieManager.getInstance().setCookie(str, str2);
            CookieSyncManager.getInstance().sync();
        }
    }
}
