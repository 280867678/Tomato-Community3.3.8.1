package com.alipay.sdk.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import com.alipay.sdk.app.statistic.C0954a;
import com.alipay.sdk.data.C0962a;
import com.alipay.sdk.packet.impl.C0981a;
import com.alipay.sdk.protocol.C0987b;
import com.alipay.sdk.protocol.EnumC0986a;
import com.alipay.sdk.sys.C0988a;
import com.alipay.sdk.sys.C0990b;
import com.alipay.sdk.util.C0996c;
import com.alipay.sdk.util.C0998e;
import com.alipay.sdk.util.C1006l;
import com.alipay.sdk.util.C1008n;
import com.alipay.sdk.widget.C1011a;
import com.coremedia.iso.boxes.AuthorBox;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class AuthTask {

    /* renamed from: a */
    static final Object f887a = C0998e.class;

    /* renamed from: b */
    private Activity f888b;

    /* renamed from: c */
    private C1011a f889c;

    public AuthTask(Activity activity) {
        this.f888b = activity;
        C0990b.m4478a().m4477a(this.f888b);
        this.f889c = new C1011a(activity, "去支付宝授权");
    }

    /* renamed from: a */
    private C0998e.AbstractC0999a m4691a() {
        return new C0944a(this);
    }

    /* renamed from: b */
    private void m4687b() {
        C1011a c1011a = this.f889c;
        if (c1011a != null) {
            c1011a.m4359b();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: c */
    public void m4685c() {
        C1011a c1011a = this.f889c;
        if (c1011a != null) {
            c1011a.m4357c();
        }
    }

    public synchronized Map<String, String> authV2(String str, boolean z) {
        C0988a c0988a;
        c0988a = new C0988a(this.f888b, str, "authV2");
        return C1006l.m4402a(c0988a, innerAuth(c0988a, str, z));
    }

    public synchronized String auth(String str, boolean z) {
        return innerAuth(new C0988a(this.f888b, str, AuthorBox.TYPE), str, z);
    }

    public synchronized String innerAuth(C0988a c0988a, String str, boolean z) {
        String m4643c;
        Activity activity;
        String str2;
        if (z) {
            m4687b();
        }
        C0990b.m4478a().m4477a(this.f888b);
        m4643c = C0952j.m4643c();
        C0951i.m4650a("");
        try {
            m4643c = m4690a(this.f888b, str, c0988a);
            C0954a.m4628b(c0988a, "biz", "PgReturn", "" + SystemClock.elapsedRealtime());
            C0962a.m4581j().m4593a(c0988a, this.f888b);
            m4685c();
            activity = this.f888b;
            str2 = c0988a.f1013p;
        } catch (Exception e) {
            C0996c.m4436a(e);
            C0954a.m4628b(c0988a, "biz", "PgReturn", "" + SystemClock.elapsedRealtime());
            C0962a.m4581j().m4593a(c0988a, this.f888b);
            m4685c();
            activity = this.f888b;
            str2 = c0988a.f1013p;
        }
        C0954a.m4629b(activity, c0988a, str, str2);
        return m4643c;
    }

    /* renamed from: a */
    private String m4690a(Activity activity, String str, C0988a c0988a) {
        String m4493a = c0988a.m4493a(str);
        List<C0962a.C0963a> m4582i = C0962a.m4581j().m4582i();
        if (!C0962a.m4581j().f968a || m4582i == null) {
            m4582i = C0951i.f930a;
        }
        if (C1008n.m4378b(c0988a, this.f888b, m4582i)) {
            String m4422a = new C0998e(activity, c0988a, m4691a()).m4422a(m4493a);
            if (!TextUtils.equals(m4422a, "failed") && !TextUtils.equals(m4422a, "scheme_failed")) {
                return TextUtils.isEmpty(m4422a) ? C0952j.m4643c() : m4422a;
            }
            C0954a.m4634a(c0988a, "biz", "LogBindCalledH5");
            return m4686b(activity, m4493a, c0988a);
        }
        C0954a.m4634a(c0988a, "biz", "LogCalledH5");
        return m4686b(activity, m4493a, c0988a);
    }

    /* renamed from: b */
    private String m4686b(Activity activity, String str, C0988a c0988a) {
        EnumC0953k enumC0953k;
        m4687b();
        try {
            try {
                List<C0987b> m4499a = C0987b.m4499a(new C0981a().mo4506a(c0988a, activity, str).m4527c().optJSONObject("form").optJSONObject("onload"));
                m4685c();
                for (int i = 0; i < m4499a.size(); i++) {
                    if (m4499a.get(i).m4498b() == EnumC0986a.WapPay) {
                        return m4688a(c0988a, m4499a.get(i));
                    }
                }
            } finally {
                m4685c();
            }
        } catch (IOException e) {
            EnumC0953k m4636b = EnumC0953k.m4636b(EnumC0953k.NETWORK_ERROR.m4640a());
            C0954a.m4630a(c0988a, "net", e);
            m4685c();
            enumC0953k = m4636b;
        } catch (Throwable th) {
            C0954a.m4632a(c0988a, "biz", "H5AuthDataAnalysisError", th);
        }
        m4685c();
        enumC0953k = null;
        if (enumC0953k == null) {
            enumC0953k = EnumC0953k.m4636b(EnumC0953k.FAILED.m4640a());
        }
        return C0952j.m4647a(enumC0953k.m4640a(), enumC0953k.m4637b(), "");
    }

    /* renamed from: a */
    private String m4688a(C0988a c0988a, C0987b c0987b) {
        String[] m4496c = c0987b.m4496c();
        Bundle bundle = new Bundle();
        bundle.putString("url", m4496c[0]);
        Intent intent = new Intent(this.f888b, H5AuthActivity.class);
        intent.putExtras(bundle);
        C0988a.C0989a.m4481a(c0988a, intent);
        this.f888b.startActivity(intent);
        synchronized (f887a) {
            try {
                f887a.wait();
            } catch (InterruptedException unused) {
                return C0952j.m4643c();
            }
        }
        String m4648a = C0952j.m4648a();
        return TextUtils.isEmpty(m4648a) ? C0952j.m4643c() : m4648a;
    }
}
