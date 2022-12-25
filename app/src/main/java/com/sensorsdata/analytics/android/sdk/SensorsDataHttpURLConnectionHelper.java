package com.sensorsdata.analytics.android.sdk;

import android.text.TextUtils;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/* loaded from: classes3.dex */
class SensorsDataHttpURLConnectionHelper {
    private static final int HTTP_307 = 307;

    static boolean needRedirects(int i) {
        return i == 301 || i == 302 || i == HTTP_307;
    }

    SensorsDataHttpURLConnectionHelper() {
    }

    static String getLocation(HttpURLConnection httpURLConnection, String str) throws MalformedURLException {
        if (httpURLConnection == null || TextUtils.isEmpty(str)) {
            return null;
        }
        String headerField = httpURLConnection.getHeaderField("Location");
        if (TextUtils.isEmpty(headerField)) {
            headerField = httpURLConnection.getHeaderField("location");
        }
        if (TextUtils.isEmpty(headerField)) {
            return null;
        }
        if (headerField.startsWith("http://") || headerField.startsWith("https://")) {
            return headerField;
        }
        URL url = new URL(str);
        return url.getProtocol() + "://" + url.getHost() + headerField;
    }
}
