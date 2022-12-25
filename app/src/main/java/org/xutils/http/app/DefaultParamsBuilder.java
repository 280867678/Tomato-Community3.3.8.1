package org.xutils.http.app;

import com.j256.ormlite.stmt.query.SimpleComparison;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

/* loaded from: classes4.dex */
public class DefaultParamsBuilder implements ParamsBuilder {
    private static SSLSocketFactory trustAllSSlSocketFactory;

    @Override // org.xutils.http.app.ParamsBuilder
    public void buildParams(RequestParams requestParams) throws Throwable {
    }

    @Override // org.xutils.http.app.ParamsBuilder
    public void buildSign(RequestParams requestParams, String[] strArr) throws Throwable {
    }

    @Override // org.xutils.http.app.ParamsBuilder
    public String buildUri(RequestParams requestParams, HttpRequest httpRequest) throws Throwable {
        return httpRequest.host() + "/" + httpRequest.path();
    }

    @Override // org.xutils.http.app.ParamsBuilder
    public String buildCacheKey(RequestParams requestParams, String[] strArr) {
        if (strArr == null || strArr.length <= 0) {
            return null;
        }
        String str = requestParams.getUri() + "?";
        for (String str2 : strArr) {
            String stringParameter = requestParams.getStringParameter(str2);
            if (stringParameter != null) {
                str = str + str2 + SimpleComparison.EQUAL_TO_OPERATION + stringParameter + "&";
            }
        }
        return str;
    }

    @Override // org.xutils.http.app.ParamsBuilder
    public SSLSocketFactory getSSLSocketFactory() throws Throwable {
        return getTrustAllSSLSocketFactory();
    }

    public static SSLSocketFactory getTrustAllSSLSocketFactory() {
        if (trustAllSSlSocketFactory == null) {
            synchronized (DefaultParamsBuilder.class) {
                if (trustAllSSlSocketFactory == null) {
                    TrustManager[] trustManagerArr = {new X509TrustManager() { // from class: org.xutils.http.app.DefaultParamsBuilder.1
                        @Override // javax.net.ssl.X509TrustManager
                        public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) {
                        }

                        @Override // javax.net.ssl.X509TrustManager
                        public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) {
                        }

                        @Override // javax.net.ssl.X509TrustManager
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                    }};
                    SSLContext sSLContext = SSLContext.getInstance("TLS");
                    sSLContext.init(null, trustManagerArr, null);
                    trustAllSSlSocketFactory = sSLContext.getSocketFactory();
                }
            }
        }
        return trustAllSSlSocketFactory;
    }
}
