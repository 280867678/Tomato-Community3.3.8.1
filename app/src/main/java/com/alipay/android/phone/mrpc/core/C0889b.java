package com.alipay.android.phone.mrpc.core;

import android.net.SSLCertificateSocketFactory;
import android.util.Base64;
import android.util.Log;
import com.tomatolive.library.utils.DateUtils;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.security.Security;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.net.ssl.HttpsURLConnection;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.RequestWrapper;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HttpContext;

/* renamed from: com.alipay.android.phone.mrpc.core.b */
/* loaded from: classes2.dex */
public final class C0889b implements HttpClient {

    /* renamed from: a */
    public static long f764a = 160;

    /* renamed from: b */
    private static String[] f765b = {"text/", "application/xml", "application/json"};

    /* renamed from: c */
    private static final HttpRequestInterceptor f766c = new C0892c();

    /* renamed from: d */
    private final HttpClient f767d;

    /* renamed from: e */
    private RuntimeException f768e = new IllegalStateException("AndroidHttpClient created and never closed");

    /* renamed from: f */
    private volatile C0891b f769f;

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.alipay.android.phone.mrpc.core.b$a */
    /* loaded from: classes2.dex */
    public class C0890a implements HttpRequestInterceptor {
        private C0890a() {
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public /* synthetic */ C0890a(C0889b c0889b, byte b) {
            this();
        }

        public final void process(HttpRequest httpRequest, HttpContext httpContext) {
            C0891b c0891b = C0889b.this.f769f;
            if (c0891b == null || !C0891b.m4854a(c0891b) || !(httpRequest instanceof HttpUriRequest)) {
                return;
            }
            C0891b.m4853a(c0891b, C0889b.m4859a((HttpUriRequest) httpRequest));
        }
    }

    /* renamed from: com.alipay.android.phone.mrpc.core.b$b */
    /* loaded from: classes2.dex */
    private static class C0891b {

        /* renamed from: a */
        private final String f771a;

        /* renamed from: b */
        private final int f772b;

        /* renamed from: a */
        static /* synthetic */ void m4853a(C0891b c0891b, String str) {
            Log.println(c0891b.f772b, c0891b.f771a, str);
        }

        /* renamed from: a */
        static /* synthetic */ boolean m4854a(C0891b c0891b) {
            return Log.isLoggable(c0891b.f771a, c0891b.f772b);
        }
    }

    private C0889b(ClientConnectionManager clientConnectionManager, HttpParams httpParams) {
        this.f767d = new C0893d(this, clientConnectionManager, httpParams);
    }

    /* renamed from: a */
    public static C0889b m4863a(String str) {
        BasicHttpParams basicHttpParams = new BasicHttpParams();
        HttpProtocolParams.setVersion(basicHttpParams, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setUseExpectContinue(basicHttpParams, false);
        HttpConnectionParams.setStaleCheckingEnabled(basicHttpParams, true);
        HttpConnectionParams.setConnectionTimeout(basicHttpParams, 20000);
        HttpConnectionParams.setSoTimeout(basicHttpParams, 30000);
        HttpConnectionParams.setSocketBufferSize(basicHttpParams, 8192);
        HttpClientParams.setRedirecting(basicHttpParams, true);
        HttpClientParams.setAuthenticating(basicHttpParams, false);
        HttpProtocolParams.setUserAgent(basicHttpParams, str);
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https", SSLCertificateSocketFactory.getHttpSocketFactory(30000, null), 443));
        ThreadSafeClientConnManager threadSafeClientConnManager = new ThreadSafeClientConnManager(basicHttpParams, schemeRegistry);
        ConnManagerParams.setTimeout(basicHttpParams, (long) DateUtils.ONE_MINUTE_MILLIONS);
        ConnManagerParams.setMaxConnectionsPerRoute(basicHttpParams, new ConnPerRouteBean(10));
        ConnManagerParams.setMaxTotalConnections(basicHttpParams, 50);
        Security.setProperty("networkaddress.cache.ttl", "-1");
        HttpsURLConnection.setDefaultHostnameVerifier(SSLSocketFactory.STRICT_HOSTNAME_VERIFIER);
        return new C0889b(threadSafeClientConnManager, basicHttpParams);
    }

    /* renamed from: a */
    public static InputStream m4862a(HttpEntity httpEntity) {
        Header contentEncoding;
        String value;
        InputStream content = httpEntity.getContent();
        if (content != null && (contentEncoding = httpEntity.getContentEncoding()) != null && (value = contentEncoding.getValue()) != null && value.contains("gzip")) {
            return new GZIPInputStream(content);
        }
        return content;
    }

    /* renamed from: a */
    static /* synthetic */ String m4859a(HttpUriRequest httpUriRequest) {
        Header[] allHeaders;
        HttpEntity entity;
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append("curl ");
        for (Header header : httpUriRequest.getAllHeaders()) {
            if (!header.getName().equals("Authorization") && !header.getName().equals("Cookie")) {
                sb.append("--header \"");
                sb.append(header.toString().trim());
                sb.append("\" ");
            }
        }
        URI uri = httpUriRequest.getURI();
        if (httpUriRequest instanceof RequestWrapper) {
            HttpUriRequest original = ((RequestWrapper) httpUriRequest).getOriginal();
            if (original instanceof HttpUriRequest) {
                uri = original.getURI();
            }
        }
        sb.append("\"");
        sb.append(uri);
        sb.append("\"");
        if ((httpUriRequest instanceof HttpEntityEnclosingRequest) && (entity = ((HttpEntityEnclosingRequest) httpUriRequest).getEntity()) != null && entity.isRepeatable()) {
            if (entity.getContentLength() < 1024) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                entity.writeTo(byteArrayOutputStream);
                if (m4855b(httpUriRequest)) {
                    sb.insert(0, "echo '" + Base64.encodeToString(byteArrayOutputStream.toByteArray(), 2) + "' | base64 -d > /tmp/$$.bin; ");
                    str = " --data-binary @/tmp/$$.bin";
                } else {
                    String byteArrayOutputStream2 = byteArrayOutputStream.toString();
                    sb.append(" --data-ascii \"");
                    sb.append(byteArrayOutputStream2);
                    sb.append("\"");
                }
            } else {
                str = " [TOO MUCH DATA TO INCLUDE]";
            }
            sb.append(str);
        }
        return sb.toString();
    }

    /* renamed from: a */
    public static AbstractHttpEntity m4858a(byte[] bArr) {
        if (bArr.length < f764a) {
            return new ByteArrayEntity(bArr);
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
        gZIPOutputStream.write(bArr);
        gZIPOutputStream.close();
        ByteArrayEntity byteArrayEntity = new ByteArrayEntity(byteArrayOutputStream.toByteArray());
        byteArrayEntity.setContentEncoding("gzip");
        StringBuilder sb = new StringBuilder("gzip size:");
        sb.append(bArr.length);
        sb.append("->");
        sb.append(byteArrayEntity.getContentLength());
        return byteArrayEntity;
    }

    /* renamed from: a */
    public static void m4861a(HttpRequest httpRequest) {
        httpRequest.addHeader("Accept-Encoding", "gzip");
    }

    /* renamed from: b */
    public static long m4857b(String str) {
        return C0900k.m4847a(str);
    }

    /* renamed from: b */
    public static void m4856b(HttpRequest httpRequest) {
        httpRequest.addHeader("Connection", "Keep-Alive");
    }

    /* renamed from: b */
    private static boolean m4855b(HttpUriRequest httpUriRequest) {
        Header[] headers = httpUriRequest.getHeaders("content-encoding");
        if (headers != null) {
            for (Header header : headers) {
                if ("gzip".equalsIgnoreCase(header.getValue())) {
                    return true;
                }
            }
        }
        Header[] headers2 = httpUriRequest.getHeaders("content-type");
        if (headers2 != null) {
            for (Header header2 : headers2) {
                for (String str : f765b) {
                    if (header2.getValue().startsWith(str)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /* renamed from: a */
    public final void m4860a(HttpRequestRetryHandler httpRequestRetryHandler) {
        this.f767d.setHttpRequestRetryHandler(httpRequestRetryHandler);
    }

    public final <T> T execute(HttpHost httpHost, HttpRequest httpRequest, ResponseHandler<? extends T> responseHandler) {
        return (T) this.f767d.execute(httpHost, httpRequest, responseHandler);
    }

    public final <T> T execute(HttpHost httpHost, HttpRequest httpRequest, ResponseHandler<? extends T> responseHandler, HttpContext httpContext) {
        return (T) this.f767d.execute(httpHost, httpRequest, responseHandler, httpContext);
    }

    public final <T> T execute(HttpUriRequest httpUriRequest, ResponseHandler<? extends T> responseHandler) {
        return (T) this.f767d.execute(httpUriRequest, responseHandler);
    }

    public final <T> T execute(HttpUriRequest httpUriRequest, ResponseHandler<? extends T> responseHandler, HttpContext httpContext) {
        return (T) this.f767d.execute(httpUriRequest, responseHandler, httpContext);
    }

    public final HttpResponse execute(HttpHost httpHost, HttpRequest httpRequest) {
        return this.f767d.execute(httpHost, httpRequest);
    }

    public final HttpResponse execute(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) {
        return this.f767d.execute(httpHost, httpRequest, httpContext);
    }

    public final HttpResponse execute(HttpUriRequest httpUriRequest) {
        return this.f767d.execute(httpUriRequest);
    }

    public final HttpResponse execute(HttpUriRequest httpUriRequest, HttpContext httpContext) {
        return this.f767d.execute(httpUriRequest, httpContext);
    }

    public final ClientConnectionManager getConnectionManager() {
        return this.f767d.getConnectionManager();
    }

    public final HttpParams getParams() {
        return this.f767d.getParams();
    }
}
