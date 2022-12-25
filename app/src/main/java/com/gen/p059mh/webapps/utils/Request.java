package com.gen.p059mh.webapps.utils;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkUrlFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/* renamed from: com.gen.mh.webapps.utils.Request */
/* loaded from: classes2.dex */
public class Request {
    static ExecutorService fixedThreadPool = new ThreadPoolExecutor(50, 100, 60, TimeUnit.MILLISECONDS, new LinkedBlockingQueue());
    static OkHttpClient okHttpClient = new OkHttpClient();
    Body body;
    URL directURL;
    Map<String, String> requestHeaders;
    RequestListener requestListener;
    private URL url;
    int timeout = 30000;
    String method = "GET";
    AtomicBoolean loading = new AtomicBoolean(false);
    AtomicBoolean canceled = new AtomicBoolean(false);

    /* renamed from: com.gen.mh.webapps.utils.Request$Body */
    /* loaded from: classes2.dex */
    public static class Body {
        public long contentLength;
        public String contentType;
        public InputStream inputStream;
    }

    /* renamed from: com.gen.mh.webapps.utils.Request$RequestListener */
    /* loaded from: classes2.dex */
    public interface RequestListener {
        void onComplete(int i, byte[] bArr);

        void onFail(int i, String str);

        void onProgress(long j, long j2);

        boolean onReceiveResponse(Response response);
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public URL getUrl() {
        return this.url;
    }

    public void setTimeout(int i) {
        this.timeout = i;
    }

    public void start() {
        if (this.loading.get()) {
            return;
        }
        fixedThreadPool.execute(new NetWorkThread());
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String str) {
        this.method = str;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Body getBody() {
        return this.body;
    }

    /* renamed from: com.gen.mh.webapps.utils.Request$Response */
    /* loaded from: classes2.dex */
    public class Response {
        public int contentLength;
        public Map<String, List<String>> headers;
        public URL responseUrl;
        public int statusCode;

        public Response(HttpURLConnection httpURLConnection) throws IOException {
            this.statusCode = httpURLConnection.getResponseCode();
            this.contentLength = httpURLConnection.getContentLength();
            this.headers = httpURLConnection.getHeaderFields();
        }
    }

    public void cancel() {
        this.canceled.set(true);
    }

    public void setRequestHeaders(String str, String str2) {
        if (this.requestHeaders == null) {
            this.requestHeaders = new HashMap();
        }
        this.requestHeaders.put(str, str2);
    }

    public void setRequestHeaders(Map<String, String> map) {
        if (this.requestHeaders == null) {
            this.requestHeaders = new HashMap();
        }
        if (map != null) {
            this.requestHeaders.putAll(map);
        }
    }

    public String getRequestHeader(String str) {
        Map<String, String> map = this.requestHeaders;
        if (map != null) {
            return map.get(str);
        }
        return null;
    }

    public void setRequestListener(RequestListener requestListener) {
        this.requestListener = requestListener;
    }

    public RequestListener getRequestListener() {
        return this.requestListener;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.gen.mh.webapps.utils.Request$NetWorkThread */
    /* loaded from: classes2.dex */
    public class NetWorkThread extends Thread {
        OutputStream outputStream;

        NetWorkThread() {
        }

        void processBody(HttpURLConnection httpURLConnection) {
            Body body = Request.this.body;
            if (body != null) {
                httpURLConnection.setRequestProperty("Content-Type", body.contentType);
                httpURLConnection.setRequestProperty("Content-Length", "" + Request.this.body.contentLength);
                try {
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int read = Request.this.body.inputStream.read(bArr);
                        if (read == -1) {
                            return;
                        }
                        outputStream.write(bArr, 0, read);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:102:0x017a, code lost:
            r1 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:103:0x017b, code lost:
            r1.printStackTrace();
         */
        /* JADX WARN: Code restructure failed: missing block: B:104:0x0182, code lost:
            if (r14.this$0.requestListener != null) goto L105;
         */
        /* JADX WARN: Code restructure failed: missing block: B:107:0x018e, code lost:
            r14.this$0.requestListener.onFail(104, r1.toString());
         */
        /* JADX WARN: Code restructure failed: missing block: B:108:0x0135, code lost:
            r4 = new byte[4096];
         */
        /* JADX WARN: Code restructure failed: missing block: B:110:0x0123, code lost:
            r2 = new java.io.ByteArrayOutputStream();
         */
        /* JADX WARN: Code restructure failed: missing block: B:31:0x00b4, code lost:
            if (r3.statusCode != 304) goto L40;
         */
        /* JADX WARN: Code restructure failed: missing block: B:33:0x00ba, code lost:
            if (r14.this$0.requestListener == null) goto L39;
         */
        /* JADX WARN: Code restructure failed: missing block: B:35:0x00c4, code lost:
            if (r14.this$0.canceled.get() != false) goto L38;
         */
        /* JADX WARN: Code restructure failed: missing block: B:36:0x00c6, code lost:
            r14.this$0.requestListener.onComplete(r3.statusCode, null);
         */
        /* JADX WARN: Code restructure failed: missing block: B:37:?, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:38:?, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:39:?, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:41:0x00d7, code lost:
            if (r3.statusCode == 200) goto L58;
         */
        /* JADX WARN: Code restructure failed: missing block: B:43:0x00dd, code lost:
            if (r3.statusCode != 206) goto L45;
         */
        /* JADX WARN: Code restructure failed: missing block: B:45:0x00e0, code lost:
            r2 = r1.getErrorStream();
            r3 = new java.io.ByteArrayOutputStream();
            r4 = new byte[1024];
         */
        /* JADX WARN: Code restructure failed: missing block: B:46:0x00ed, code lost:
            r5 = r2.read(r4);
         */
        /* JADX WARN: Code restructure failed: missing block: B:47:0x00f1, code lost:
            if (r5 == (-1)) goto L49;
         */
        /* JADX WARN: Code restructure failed: missing block: B:48:0x00f3, code lost:
            r3.write(r4, 0, r5);
         */
        /* JADX WARN: Code restructure failed: missing block: B:50:0x00f7, code lost:
            r2 = r3.toString("UTF-8");
         */
        /* JADX WARN: Code restructure failed: missing block: B:51:0x0102, code lost:
            if (r14.this$0.requestListener == null) goto L57;
         */
        /* JADX WARN: Code restructure failed: missing block: B:53:0x010c, code lost:
            if (r14.this$0.canceled.get() != false) goto L56;
         */
        /* JADX WARN: Code restructure failed: missing block: B:54:0x010e, code lost:
            r14.this$0.requestListener.onFail(r1.getResponseCode(), r2);
         */
        /* JADX WARN: Code restructure failed: missing block: B:55:?, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:56:?, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:57:?, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:58:0x011b, code lost:
            r1 = r1.getInputStream();
            r2 = r14.outputStream;
         */
        /* JADX WARN: Code restructure failed: missing block: B:59:0x0121, code lost:
            if (r2 != null) goto L60;
         */
        /* JADX WARN: Code restructure failed: missing block: B:61:0x012d, code lost:
            if (r3.contentLength <= 4096) goto L108;
         */
        /* JADX WARN: Code restructure failed: missing block: B:62:0x012f, code lost:
            r4 = new byte[40960];
         */
        /* JADX WARN: Code restructure failed: missing block: B:63:0x0137, code lost:
            r8 = 0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:64:0x0138, code lost:
            r9 = r1.read(r4);
         */
        /* JADX WARN: Code restructure failed: missing block: B:65:0x013c, code lost:
            if (r9 == (-1)) goto L82;
         */
        /* JADX WARN: Code restructure failed: missing block: B:67:0x0146, code lost:
            if (r14.this$0.canceled.get() == false) goto L71;
         */
        /* JADX WARN: Code restructure failed: missing block: B:69:0x014c, code lost:
            if (r14.this$0.requestListener == null) goto L71;
         */
        /* JADX WARN: Code restructure failed: missing block: B:70:0x014e, code lost:
            r14.this$0.requestListener.onFail(102, "Request canceled");
         */
        /* JADX WARN: Code restructure failed: missing block: B:71:0x015a, code lost:
            r2.write(r4, 0, r9);
            r8 = r8 + r9;
         */
        /* JADX WARN: Code restructure failed: missing block: B:72:0x0162, code lost:
            if (r14.this$0.requestListener == null) goto L81;
         */
        /* JADX WARN: Code restructure failed: missing block: B:75:0x016c, code lost:
            if (r14.this$0.canceled.get() != false) goto L80;
         */
        /* JADX WARN: Code restructure failed: missing block: B:77:0x016e, code lost:
            r14.this$0.requestListener.onProgress(r8, r3.contentLength);
         */
        /* JADX WARN: Code restructure failed: missing block: B:84:0x019b, code lost:
            if (r14.outputStream == null) goto L85;
         */
        /* JADX WARN: Code restructure failed: missing block: B:86:0x01a1, code lost:
            if (r14.this$0.requestListener == null) goto L92;
         */
        /* JADX WARN: Code restructure failed: missing block: B:89:0x01ad, code lost:
            r14.this$0.requestListener.onComplete(r3.statusCode, ((java.io.ByteArrayOutputStream) r2).toByteArray());
         */
        /* JADX WARN: Code restructure failed: missing block: B:90:?, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:91:?, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:94:0x01c2, code lost:
            if (r14.this$0.requestListener == null) goto L100;
         */
        /* JADX WARN: Code restructure failed: missing block: B:97:0x01ce, code lost:
            r14.this$0.requestListener.onComplete(r3.statusCode, null);
         */
        /* JADX WARN: Code restructure failed: missing block: B:98:?, code lost:
            return;
         */
        /* JADX WARN: Code restructure failed: missing block: B:99:?, code lost:
            return;
         */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void run() {
            try {
                Request.this.directURL = Request.this.url;
                HttpURLConnection open = new OkUrlFactory(Request.okHttpClient).open(Request.this.directURL);
                open.setConnectTimeout(Request.this.timeout);
                open.setReadTimeout(Request.this.timeout);
                open.setRequestMethod(Request.this.method);
                open.setInstanceFollowRedirects(true);
                if (open instanceof HttpsURLConnection) {
                    Request.trustAllHttpsCertificates((HttpsURLConnection) open);
                }
                if (Request.this.requestHeaders != null) {
                    for (String str : Request.this.requestHeaders.keySet()) {
                        open.setRequestProperty(str, Request.this.requestHeaders.get(str));
                    }
                }
                processBody(open);
                Response response = new Response(open);
                response.responseUrl = Request.this.directURL;
                if (Request.this.requestListener != null && !Request.this.canceled.get() && !Request.this.requestListener.onReceiveResponse(response)) {
                    Request.this.requestListener.onFail(101, "User canceled");
                    return;
                }
                while (true) {
                    if (response.statusCode != 302 && response.statusCode != 301) {
                        break;
                    }
                    Request.this.directURL = new URL(open.getHeaderField("Location"));
                    open = (HttpURLConnection) Request.this.directURL.openConnection();
                    open.setConnectTimeout(Request.this.timeout);
                    open.setReadTimeout(Request.this.timeout);
                    open.setRequestMethod(Request.this.method);
                    open.setInstanceFollowRedirects(true);
                    if (Request.this.requestHeaders != null) {
                        for (String str2 : Request.this.requestHeaders.keySet()) {
                            open.setRequestProperty(str2, Request.this.requestHeaders.get(str2));
                        }
                    }
                    processBody(open);
                    response = new Response(open);
                    response.responseUrl = Request.this.directURL;
                    if (Request.this.requestListener != null && !Request.this.canceled.get() && !Request.this.requestListener.onReceiveResponse(response)) {
                        Request.this.requestListener.onFail(101, "User canceled");
                        return;
                    }
                }
            } catch (Exception e) {
                Logger.m4113i(Request.this.directURL);
                e.printStackTrace();
                Request request = Request.this;
                if (request.requestListener == null || request.canceled.get()) {
                    return;
                }
                Request.this.requestListener.onFail(104, "网络异常");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.gen.mh.webapps.utils.Request$miTM */
    /* loaded from: classes2.dex */
    public static class miTM implements TrustManager, X509TrustManager {
        @Override // javax.net.ssl.X509TrustManager
        public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
        }

        @Override // javax.net.ssl.X509TrustManager
        public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
        }

        @Override // javax.net.ssl.X509TrustManager
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public boolean isClientTrusted(X509Certificate[] x509CertificateArr) {
            return true;
        }

        public boolean isServerTrusted(X509Certificate[] x509CertificateArr) {
            return true;
        }

        miTM() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void trustAllHttpsCertificates(HttpsURLConnection httpsURLConnection) throws Exception {
        TrustManager[] trustManagerArr = {new miTM()};
        SSLContext sSLContext = SSLContext.getInstance("SSL");
        sSLContext.init(null, trustManagerArr, null);
        httpsURLConnection.setSSLSocketFactory(sSLContext.getSocketFactory());
        httpsURLConnection.setHostnameVerifier(new HostnameVerifier() { // from class: com.gen.mh.webapps.utils.Request.1
            @Override // javax.net.ssl.HostnameVerifier
            public boolean verify(String str, SSLSession sSLSession) {
                return true;
            }
        });
    }
}
