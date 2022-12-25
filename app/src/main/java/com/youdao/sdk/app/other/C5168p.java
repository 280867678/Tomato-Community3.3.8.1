package com.youdao.sdk.app.other;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;

/* renamed from: com.youdao.sdk.app.other.p */
/* loaded from: classes4.dex */
public class C5168p {

    /* renamed from: com.youdao.sdk.app.other.p$a */
    /* loaded from: classes4.dex */
    public static class C5169a {

        /* renamed from: a */
        private final Context f5930a;

        /* renamed from: b */
        private final ConnectivityManager f5931b;

        public C5169a(Context context) {
            this.f5930a = context;
            this.f5931b = (ConnectivityManager) context.getSystemService("connectivity");
        }

        /* renamed from: a */
        private HttpHost m181a() {
            String extraInfo;
            NetworkInfo activeNetworkInfo = this.f5931b.getActiveNetworkInfo();
            if (activeNetworkInfo == null || activeNetworkInfo.getType() != 0 || (extraInfo = activeNetworkInfo.getExtraInfo()) == null) {
                return null;
            }
            String lowerCase = extraInfo.toLowerCase();
            if (lowerCase.contains("cmnet")) {
                return null;
            }
            if (lowerCase.contains("cmwap")) {
                return new HttpHost("10.0.0.172");
            }
            if (lowerCase.contains("3gnet")) {
                return null;
            }
            if (lowerCase.contains("3gwap")) {
                return new HttpHost("10.0.0.172");
            }
            if (lowerCase.contains("uninet")) {
                return null;
            }
            if (lowerCase.contains("uniwap")) {
                return new HttpHost("10.0.0.172");
            }
            if (lowerCase.contains("ctnet")) {
                return null;
            }
            if (lowerCase.contains("ctwap")) {
                return new HttpHost("10.0.0.200");
            }
            if (lowerCase.contains("#777")) {
                try {
                    Cursor query = this.f5930a.getContentResolver().query(Uri.parse("content://telephony/carriers/preferapn"), new String[]{"proxy", "port"}, null, null, null);
                    if (query.moveToFirst()) {
                        int i = 0;
                        String string = query.getString(0);
                        if (string.length() > 3) {
                            try {
                                i = Integer.parseInt(query.getString(1));
                            } catch (NumberFormatException unused) {
                            }
                            if (i <= 0) {
                                i = 80;
                            }
                            return new HttpHost(string, i);
                        }
                    }
                } catch (Exception unused2) {
                }
            }
            return null;
        }

        /* renamed from: a */
        public void m180a(HttpClient httpClient) {
            C5168p.m182a(httpClient, m181a());
        }
    }

    /* renamed from: a */
    public static void m182a(HttpClient httpClient, HttpHost httpHost) {
        httpClient.getParams().setParameter("http.route.default-proxy", httpHost);
    }
}
