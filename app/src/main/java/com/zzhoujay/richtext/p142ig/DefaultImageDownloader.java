package com.zzhoujay.richtext.p142ig;

import android.annotation.SuppressLint;
import com.tomatolive.library.utils.ConstantUtils;
import com.zzhoujay.richtext.callback.BitmapStream;
import com.zzhoujay.richtext.exceptions.HttpResponseCodeException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/* renamed from: com.zzhoujay.richtext.ig.DefaultImageDownloader */
/* loaded from: classes4.dex */
public class DefaultImageDownloader implements ImageDownloader {
    private static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() { // from class: com.zzhoujay.richtext.ig.DefaultImageDownloader.1
        @Override // javax.net.ssl.HostnameVerifier
        @SuppressLint({"BadHostnameVerifier"})
        public boolean verify(String str, SSLSession sSLSession) {
            return true;
        }
    };
    public static final String GLOBAL_ID = "com.zzhoujay.richtext.ig.DefaultImageDownloader";
    private static SSLContext sslContext;

    @Override // com.zzhoujay.richtext.p142ig.ImageDownloader
    public BitmapStream download(String str) throws IOException {
        return new BitmapStreamImpl(str);
    }

    /* renamed from: com.zzhoujay.richtext.ig.DefaultImageDownloader$BitmapStreamImpl */
    /* loaded from: classes4.dex */
    private static class BitmapStreamImpl implements BitmapStream {
        private HttpURLConnection connection;
        private InputStream inputStream;
        private final String url;

        private BitmapStreamImpl(String str) {
            this.url = str;
        }

        @Override // com.zzhoujay.richtext.callback.Closeable
        public void close() throws IOException {
            InputStream inputStream = this.inputStream;
            if (inputStream != null) {
                inputStream.close();
            }
            HttpURLConnection httpURLConnection = this.connection;
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }

        @Override // com.zzhoujay.richtext.callback.BitmapStream
        public InputStream getInputStream() throws IOException {
            this.connection = (HttpURLConnection) new URL(this.url).openConnection();
            this.connection.setConnectTimeout(ConstantUtils.MAX_ITEM_NUM);
            this.connection.setDoInput(true);
            this.connection.addRequestProperty("Connection", "Keep-Alive");
            HttpURLConnection httpURLConnection = this.connection;
            if (httpURLConnection instanceof HttpsURLConnection) {
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) httpURLConnection;
                httpsURLConnection.setHostnameVerifier(DefaultImageDownloader.DO_NOT_VERIFY);
                httpsURLConnection.setSSLSocketFactory(DefaultImageDownloader.sslContext.getSocketFactory());
            }
            this.connection.connect();
            int responseCode = this.connection.getResponseCode();
            if (responseCode == 200) {
                this.inputStream = this.connection.getInputStream();
                return this.inputStream;
            }
            throw new HttpResponseCodeException(responseCode);
        }
    }

    static {
        X509TrustManager x509TrustManager = new X509TrustManager() { // from class: com.zzhoujay.richtext.ig.DefaultImageDownloader.2
            @Override // javax.net.ssl.X509TrustManager
            @SuppressLint({"TrustAllX509TrustManager"})
            public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) {
            }

            @Override // javax.net.ssl.X509TrustManager
            @SuppressLint({"TrustAllX509TrustManager"})
            public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) {
            }

            @Override // javax.net.ssl.X509TrustManager
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{x509TrustManager}, new SecureRandom());
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
