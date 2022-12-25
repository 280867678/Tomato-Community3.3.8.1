package com.alipay.android.phone.mrpc.core;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

/* renamed from: com.alipay.android.phone.mrpc.core.q */
/* loaded from: classes2.dex */
public final class CallableC0907q implements Callable<C0911u> {

    /* renamed from: e */
    private static final HttpRequestRetryHandler f810e = new C0888ad();

    /* renamed from: a */
    protected C0902l f811a;

    /* renamed from: b */
    protected Context f812b;

    /* renamed from: c */
    protected C0905o f813c;

    /* renamed from: d */
    String f814d;

    /* renamed from: f */
    private HttpUriRequest f815f;

    /* renamed from: i */
    private CookieManager f818i;

    /* renamed from: j */
    private AbstractHttpEntity f819j;

    /* renamed from: k */
    private HttpHost f820k;

    /* renamed from: l */
    private URL f821l;

    /* renamed from: q */
    private String f826q;

    /* renamed from: g */
    private HttpContext f816g = new BasicHttpContext();

    /* renamed from: h */
    private CookieStore f817h = new BasicCookieStore();

    /* renamed from: m */
    private int f822m = 0;

    /* renamed from: n */
    private boolean f823n = false;

    /* renamed from: o */
    private boolean f824o = false;

    /* renamed from: p */
    private String f825p = null;

    public CallableC0907q(C0902l c0902l, C0905o c0905o) {
        this.f811a = c0902l;
        this.f812b = this.f811a.f788a;
        this.f813c = c0905o;
    }

    /* renamed from: a */
    private static long m4815a(String[] strArr) {
        for (int i = 0; i < strArr.length; i++) {
            if ("max-age".equalsIgnoreCase(strArr[i])) {
                int i2 = i + 1;
                if (strArr[i2] != null) {
                    try {
                        return Long.parseLong(strArr[i2]);
                    } catch (Exception unused) {
                        continue;
                    }
                } else {
                    continue;
                }
            }
        }
        return 0L;
    }

    /* renamed from: a */
    private static HttpUrlHeader m4817a(HttpResponse httpResponse) {
        Header[] allHeaders;
        HttpUrlHeader httpUrlHeader = new HttpUrlHeader();
        for (Header header : httpResponse.getAllHeaders()) {
            httpUrlHeader.setHead(header.getName(), header.getValue());
        }
        return httpUrlHeader;
    }

    /* renamed from: a */
    private C0911u m4816a(HttpResponse httpResponse, int i, String str) {
        ByteArrayOutputStream byteArrayOutputStream;
        String str2;
        new StringBuilder("开始handle，handleResponse-1,").append(Thread.currentThread().getId());
        HttpEntity entity = httpResponse.getEntity();
        String str3 = null;
        if (entity == null || httpResponse.getStatusLine().getStatusCode() != 200) {
            if (entity != null) {
                return null;
            }
            httpResponse.getStatusLine().getStatusCode();
            return null;
        }
        new StringBuilder("200，开始处理，handleResponse-2,threadid = ").append(Thread.currentThread().getId());
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                long currentTimeMillis = System.currentTimeMillis();
                m4818a(entity, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                this.f824o = false;
                this.f811a.m4836c(System.currentTimeMillis() - currentTimeMillis);
                this.f811a.m4841a(byteArray.length);
                new StringBuilder("res:").append(byteArray.length);
                C0906p c0906p = new C0906p(m4817a(httpResponse), i, str, byteArray);
                long m4813b = m4813b(httpResponse);
                Header contentType = httpResponse.getEntity().getContentType();
                if (contentType != null) {
                    HashMap<String, String> m4819a = m4819a(contentType.getValue());
                    str3 = m4819a.get("charset");
                    str2 = m4819a.get("Content-Type");
                } else {
                    str2 = null;
                }
                c0906p.m4799b(str2);
                c0906p.m4822a(str3);
                c0906p.m4823a(System.currentTimeMillis());
                c0906p.m4821b(m4813b);
                try {
                    byteArrayOutputStream.close();
                    return c0906p;
                } catch (IOException e) {
                    throw new RuntimeException("ArrayOutputStream close error!", e.getCause());
                }
            } catch (Throwable th) {
                th = th;
                if (byteArrayOutputStream != null) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (IOException e2) {
                        throw new RuntimeException("ArrayOutputStream close error!", e2.getCause());
                    }
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            byteArrayOutputStream = null;
        }
    }

    /* renamed from: a */
    private static HashMap<String, String> m4819a(String str) {
        String[] split;
        HashMap<String, String> hashMap = new HashMap<>();
        for (String str2 : str.split(";")) {
            String[] split2 = str2.indexOf(61) == -1 ? new String[]{"Content-Type", str2} : str2.split(SimpleComparison.EQUAL_TO_OPERATION);
            hashMap.put(split2[0], split2[1]);
        }
        return hashMap;
    }

    /* renamed from: a */
    private void m4818a(HttpEntity httpEntity, OutputStream outputStream) {
        InputStream m4862a = C0889b.m4862a(httpEntity);
        long contentLength = httpEntity.getContentLength();
        try {
            try {
                byte[] bArr = new byte[2048];
                while (true) {
                    int read = m4862a.read(bArr);
                    if (read == -1 || this.f813c.m4801h()) {
                        break;
                    }
                    outputStream.write(bArr, 0, read);
                    if (this.f813c.m4803f() != null) {
                        int i = (contentLength > 0L ? 1 : (contentLength == 0L ? 0 : -1));
                    }
                }
                outputStream.flush();
            } catch (Exception e) {
                e.getCause();
                throw new IOException("HttpWorker Request Error!" + e.getLocalizedMessage());
            }
        } finally {
            C0908r.m4805a(m4862a);
        }
    }

    /* renamed from: b */
    private static long m4813b(HttpResponse httpResponse) {
        Header firstHeader = httpResponse.getFirstHeader("Cache-Control");
        if (firstHeader != null) {
            String[] split = firstHeader.getValue().split(SimpleComparison.EQUAL_TO_OPERATION);
            if (split.length >= 2) {
                try {
                    return m4815a(split);
                } catch (NumberFormatException unused) {
                }
            }
        }
        Header firstHeader2 = httpResponse.getFirstHeader("Expires");
        if (firstHeader2 != null) {
            return C0889b.m4857b(firstHeader2.getValue()) - System.currentTimeMillis();
        }
        return 0L;
    }

    /* renamed from: b */
    private URI m4814b() {
        String m4835a = this.f813c.m4835a();
        String str = this.f814d;
        if (str != null) {
            m4835a = str;
        }
        if (m4835a != null) {
            return new URI(m4835a);
        }
        throw new RuntimeException("url should not be null");
    }

    /* renamed from: c */
    private HttpUriRequest m4812c() {
        HttpUriRequest httpUriRequest = this.f815f;
        if (httpUriRequest != null) {
            return httpUriRequest;
        }
        if (this.f819j == null) {
            byte[] m4829b = this.f813c.m4829b();
            String m4828b = this.f813c.m4828b("gzip");
            if (m4829b != null) {
                if (TextUtils.equals(m4828b, "true")) {
                    this.f819j = C0889b.m4858a(m4829b);
                } else {
                    this.f819j = new ByteArrayEntity(m4829b);
                }
                this.f819j.setContentType(this.f813c.m4827c());
            }
        }
        AbstractHttpEntity abstractHttpEntity = this.f819j;
        if (abstractHttpEntity != null) {
            HttpPost httpPost = new HttpPost(m4814b());
            httpPost.setEntity(abstractHttpEntity);
            this.f815f = httpPost;
        } else {
            this.f815f = new HttpGet(m4814b());
        }
        return this.f815f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0130 A[Catch: Exception -> 0x0270, NullPointerException -> 0x0292, IOException -> 0x02b6, UnknownHostException -> 0x02e0, HttpHostConnectException -> 0x030c, NoHttpResponseException -> 0x0330, SocketTimeoutException -> 0x035b, ConnectTimeoutException -> 0x0386, ConnectionPoolTimeoutException -> 0x03b0, SSLException -> 0x03da, SSLPeerUnverifiedException -> 0x0404, SSLHandshakeException -> 0x042e, URISyntaxException -> 0x0458, HttpException -> 0x0466, TryCatch #3 {SocketTimeoutException -> 0x035b, HttpHostConnectException -> 0x030c, HttpException -> 0x0466, URISyntaxException -> 0x0458, UnknownHostException -> 0x02e0, SSLHandshakeException -> 0x042e, SSLException -> 0x03da, IOException -> 0x02b6, Exception -> 0x0270, ConnectionPoolTimeoutException -> 0x03b0, NullPointerException -> 0x0292, SSLPeerUnverifiedException -> 0x0404, ConnectTimeoutException -> 0x0386, NoHttpResponseException -> 0x0330, blocks: (B:4:0x0007, B:8:0x0033, B:10:0x003b, B:12:0x0041, B:13:0x0045, B:15:0x004b, B:17:0x0059, B:19:0x00d6, B:21:0x00dc, B:23:0x00e6, B:25:0x00ef, B:27:0x00fc, B:30:0x0106, B:32:0x010d, B:33:0x0128, B:35:0x0130, B:36:0x013d, B:38:0x0163, B:39:0x016a, B:41:0x0170, B:42:0x0174, B:44:0x017a, B:47:0x0186, B:50:0x01b9, B:56:0x01d5, B:63:0x01f2, B:64:0x020b, B:67:0x020c, B:69:0x0214, B:71:0x021a, B:74:0x0226, B:76:0x022a, B:81:0x023b, B:83:0x0243, B:85:0x024d, B:88:0x0110, B:91:0x0263, B:92:0x026f, B:93:0x0018, B:95:0x001c, B:97:0x0020, B:99:0x0026, B:104:0x002e), top: B:3:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0163 A[Catch: Exception -> 0x0270, NullPointerException -> 0x0292, IOException -> 0x02b6, UnknownHostException -> 0x02e0, HttpHostConnectException -> 0x030c, NoHttpResponseException -> 0x0330, SocketTimeoutException -> 0x035b, ConnectTimeoutException -> 0x0386, ConnectionPoolTimeoutException -> 0x03b0, SSLException -> 0x03da, SSLPeerUnverifiedException -> 0x0404, SSLHandshakeException -> 0x042e, URISyntaxException -> 0x0458, HttpException -> 0x0466, TryCatch #3 {SocketTimeoutException -> 0x035b, HttpHostConnectException -> 0x030c, HttpException -> 0x0466, URISyntaxException -> 0x0458, UnknownHostException -> 0x02e0, SSLHandshakeException -> 0x042e, SSLException -> 0x03da, IOException -> 0x02b6, Exception -> 0x0270, ConnectionPoolTimeoutException -> 0x03b0, NullPointerException -> 0x0292, SSLPeerUnverifiedException -> 0x0404, ConnectTimeoutException -> 0x0386, NoHttpResponseException -> 0x0330, blocks: (B:4:0x0007, B:8:0x0033, B:10:0x003b, B:12:0x0041, B:13:0x0045, B:15:0x004b, B:17:0x0059, B:19:0x00d6, B:21:0x00dc, B:23:0x00e6, B:25:0x00ef, B:27:0x00fc, B:30:0x0106, B:32:0x010d, B:33:0x0128, B:35:0x0130, B:36:0x013d, B:38:0x0163, B:39:0x016a, B:41:0x0170, B:42:0x0174, B:44:0x017a, B:47:0x0186, B:50:0x01b9, B:56:0x01d5, B:63:0x01f2, B:64:0x020b, B:67:0x020c, B:69:0x0214, B:71:0x021a, B:74:0x0226, B:76:0x022a, B:81:0x023b, B:83:0x0243, B:85:0x024d, B:88:0x0110, B:91:0x0263, B:92:0x026f, B:93:0x0018, B:95:0x001c, B:97:0x0020, B:99:0x0026, B:104:0x002e), top: B:3:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0170 A[Catch: Exception -> 0x0270, NullPointerException -> 0x0292, IOException -> 0x02b6, UnknownHostException -> 0x02e0, HttpHostConnectException -> 0x030c, NoHttpResponseException -> 0x0330, SocketTimeoutException -> 0x035b, ConnectTimeoutException -> 0x0386, ConnectionPoolTimeoutException -> 0x03b0, SSLException -> 0x03da, SSLPeerUnverifiedException -> 0x0404, SSLHandshakeException -> 0x042e, URISyntaxException -> 0x0458, HttpException -> 0x0466, TryCatch #3 {SocketTimeoutException -> 0x035b, HttpHostConnectException -> 0x030c, HttpException -> 0x0466, URISyntaxException -> 0x0458, UnknownHostException -> 0x02e0, SSLHandshakeException -> 0x042e, SSLException -> 0x03da, IOException -> 0x02b6, Exception -> 0x0270, ConnectionPoolTimeoutException -> 0x03b0, NullPointerException -> 0x0292, SSLPeerUnverifiedException -> 0x0404, ConnectTimeoutException -> 0x0386, NoHttpResponseException -> 0x0330, blocks: (B:4:0x0007, B:8:0x0033, B:10:0x003b, B:12:0x0041, B:13:0x0045, B:15:0x004b, B:17:0x0059, B:19:0x00d6, B:21:0x00dc, B:23:0x00e6, B:25:0x00ef, B:27:0x00fc, B:30:0x0106, B:32:0x010d, B:33:0x0128, B:35:0x0130, B:36:0x013d, B:38:0x0163, B:39:0x016a, B:41:0x0170, B:42:0x0174, B:44:0x017a, B:47:0x0186, B:50:0x01b9, B:56:0x01d5, B:63:0x01f2, B:64:0x020b, B:67:0x020c, B:69:0x0214, B:71:0x021a, B:74:0x0226, B:76:0x022a, B:81:0x023b, B:83:0x0243, B:85:0x024d, B:88:0x0110, B:91:0x0263, B:92:0x026f, B:93:0x0018, B:95:0x001c, B:97:0x0020, B:99:0x0026, B:104:0x002e), top: B:3:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x01e9  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0110 A[Catch: Exception -> 0x0270, NullPointerException -> 0x0292, IOException -> 0x02b6, UnknownHostException -> 0x02e0, HttpHostConnectException -> 0x030c, NoHttpResponseException -> 0x0330, SocketTimeoutException -> 0x035b, ConnectTimeoutException -> 0x0386, ConnectionPoolTimeoutException -> 0x03b0, SSLException -> 0x03da, SSLPeerUnverifiedException -> 0x0404, SSLHandshakeException -> 0x042e, URISyntaxException -> 0x0458, HttpException -> 0x0466, TryCatch #3 {SocketTimeoutException -> 0x035b, HttpHostConnectException -> 0x030c, HttpException -> 0x0466, URISyntaxException -> 0x0458, UnknownHostException -> 0x02e0, SSLHandshakeException -> 0x042e, SSLException -> 0x03da, IOException -> 0x02b6, Exception -> 0x0270, ConnectionPoolTimeoutException -> 0x03b0, NullPointerException -> 0x0292, SSLPeerUnverifiedException -> 0x0404, ConnectTimeoutException -> 0x0386, NoHttpResponseException -> 0x0330, blocks: (B:4:0x0007, B:8:0x0033, B:10:0x003b, B:12:0x0041, B:13:0x0045, B:15:0x004b, B:17:0x0059, B:19:0x00d6, B:21:0x00dc, B:23:0x00e6, B:25:0x00ef, B:27:0x00fc, B:30:0x0106, B:32:0x010d, B:33:0x0128, B:35:0x0130, B:36:0x013d, B:38:0x0163, B:39:0x016a, B:41:0x0170, B:42:0x0174, B:44:0x017a, B:47:0x0186, B:50:0x01b9, B:56:0x01d5, B:63:0x01f2, B:64:0x020b, B:67:0x020c, B:69:0x0214, B:71:0x021a, B:74:0x0226, B:76:0x022a, B:81:0x023b, B:83:0x0243, B:85:0x024d, B:88:0x0110, B:91:0x0263, B:92:0x026f, B:93:0x0018, B:95:0x001c, B:97:0x0020, B:99:0x0026, B:104:0x002e), top: B:3:0x0007 }] */
    @Override // java.util.concurrent.Callable
    /* renamed from: d */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public C0911u call() {
        boolean z;
        HttpHost httpHost;
        List<Cookie> cookies;
        int statusCode;
        C0911u m4816a;
        String m4835a;
        while (true) {
            try {
                NetworkInfo[] allNetworkInfo = ((ConnectivityManager) this.f812b.getSystemService("connectivity")).getAllNetworkInfo();
                boolean z2 = true;
                if (allNetworkInfo != null) {
                    for (NetworkInfo networkInfo : allNetworkInfo) {
                        if (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnectedOrConnecting()) {
                            z = true;
                            break;
                        }
                    }
                }
                z = false;
                if (!z) {
                    throw new HttpException(1, "The network is not available");
                }
                ArrayList<Header> m4826d = this.f813c.m4826d();
                if (m4826d != null && !m4826d.isEmpty()) {
                    Iterator<Header> it2 = m4826d.iterator();
                    while (it2.hasNext()) {
                        m4812c().addHeader(it2.next());
                    }
                }
                C0889b.m4861a((HttpRequest) m4812c());
                C0889b.m4856b((HttpRequest) m4812c());
                m4812c().addHeader("cookie", m4806i().getCookie(this.f813c.m4835a()));
                this.f816g.setAttribute("http.cookie-store", this.f817h);
                this.f811a.m4842a().m4860a(f810e);
                long currentTimeMillis = System.currentTimeMillis();
                StringBuilder sb = new StringBuilder("By Http/Https to request. operationType=");
                sb.append(m4809f());
                sb.append(" url=");
                sb.append(this.f815f.getURI().toString());
                HttpParams params = this.f811a.m4842a().getParams();
                NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.f812b.getSystemService("connectivity")).getActiveNetworkInfo();
                HttpHost httpHost2 = null;
                if (activeNetworkInfo != null && activeNetworkInfo.isAvailable()) {
                    String defaultHost = Proxy.getDefaultHost();
                    int defaultPort = Proxy.getDefaultPort();
                    if (defaultHost != null) {
                        httpHost = new HttpHost(defaultHost, defaultPort);
                        if (httpHost != null || !TextUtils.equals(httpHost.getHostName(), "127.0.0.1") || httpHost.getPort() != 8087) {
                            httpHost2 = httpHost;
                        }
                        params.setParameter("http.route.default-proxy", httpHost2);
                        if (this.f820k == null) {
                            URL m4807h = m4807h();
                            this.f820k = new HttpHost(m4807h.getHost(), m4808g(), m4807h.getProtocol());
                        }
                        HttpHost httpHost3 = this.f820k;
                        if (m4808g() == 80) {
                            httpHost3 = new HttpHost(m4807h().getHost());
                        }
                        HttpResponse execute = this.f811a.m4842a().execute(httpHost3, (HttpRequest) this.f815f, this.f816g);
                        this.f811a.m4838b(System.currentTimeMillis() - currentTimeMillis);
                        cookies = this.f817h.getCookies();
                        if (this.f813c.m4825e()) {
                            m4806i().removeAllCookie();
                        }
                        if (!cookies.isEmpty()) {
                            for (Cookie cookie : cookies) {
                                if (cookie.getDomain() != null) {
                                    StringBuilder sb2 = new StringBuilder();
                                    sb2.append(cookie.getName());
                                    sb2.append(SimpleComparison.EQUAL_TO_OPERATION);
                                    sb2.append(cookie.getValue());
                                    sb2.append("; domain=");
                                    sb2.append(cookie.getDomain());
                                    sb2.append(cookie.isSecure() ? "; Secure" : "");
                                    m4806i().setCookie(this.f813c.m4835a(), sb2.toString());
                                    CookieSyncManager.getInstance().sync();
                                }
                            }
                        }
                        statusCode = execute.getStatusLine().getStatusCode();
                        String reasonPhrase = execute.getStatusLine().getReasonPhrase();
                        if (statusCode != 200) {
                            if (statusCode != 304) {
                                z2 = false;
                            }
                            if (!z2) {
                                throw new HttpException(Integer.valueOf(execute.getStatusLine().getStatusCode()), execute.getStatusLine().getReasonPhrase());
                            }
                        }
                        m4816a = m4816a(execute, statusCode, reasonPhrase);
                        if (((m4816a != null || m4816a.m4800b() == null) ? -1L : m4816a.m4800b().length) == -1 && (m4816a instanceof C0906p)) {
                            try {
                                Long.parseLong(((C0906p) m4816a).m4824a().getHead("Content-Length"));
                            } catch (Exception unused) {
                            }
                        }
                        m4835a = this.f813c.m4835a();
                        if (m4835a != null && !TextUtils.isEmpty(m4809f())) {
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append(m4835a);
                            sb3.append("#");
                            sb3.append(m4809f());
                        }
                        return m4816a;
                    }
                }
                httpHost = null;
                if (httpHost != null) {
                }
                httpHost2 = httpHost;
                params.setParameter("http.route.default-proxy", httpHost2);
                if (this.f820k == null) {
                }
                HttpHost httpHost32 = this.f820k;
                if (m4808g() == 80) {
                }
                HttpResponse execute2 = this.f811a.m4842a().execute(httpHost32, (HttpRequest) this.f815f, this.f816g);
                this.f811a.m4838b(System.currentTimeMillis() - currentTimeMillis);
                cookies = this.f817h.getCookies();
                if (this.f813c.m4825e()) {
                }
                if (!cookies.isEmpty()) {
                }
                statusCode = execute2.getStatusLine().getStatusCode();
                String reasonPhrase2 = execute2.getStatusLine().getReasonPhrase();
                if (statusCode != 200) {
                }
                m4816a = m4816a(execute2, statusCode, reasonPhrase2);
                if (((m4816a != null || m4816a.m4800b() == null) ? -1L : m4816a.m4800b().length) == -1) {
                    Long.parseLong(((C0906p) m4816a).m4824a().getHead("Content-Length"));
                }
                m4835a = this.f813c.m4835a();
                if (m4835a != null) {
                    StringBuilder sb32 = new StringBuilder();
                    sb32.append(m4835a);
                    sb32.append("#");
                    sb32.append(m4809f());
                }
                return m4816a;
            } catch (SocketTimeoutException e) {
                m4810e();
                if (this.f813c.m4803f() != null) {
                    new StringBuilder().append(e);
                }
                new StringBuilder().append(e);
                throw new HttpException(4, String.valueOf(e));
            } catch (HttpHostConnectException e2) {
                m4810e();
                if (this.f813c.m4803f() != null) {
                    new StringBuilder().append(e2);
                }
                throw new HttpException(8, String.valueOf(e2));
            } catch (HttpException e3) {
                m4810e();
                if (this.f813c.m4803f() != null) {
                    e3.getCode();
                    e3.getMsg();
                }
                new StringBuilder().append(e3);
                throw e3;
            } catch (URISyntaxException e4) {
                throw new RuntimeException("Url parser error!", e4.getCause());
            } catch (UnknownHostException e5) {
                m4810e();
                if (this.f813c.m4803f() != null) {
                    new StringBuilder().append(e5);
                }
                new StringBuilder().append(e5);
                throw new HttpException(9, String.valueOf(e5));
            } catch (SSLHandshakeException e6) {
                m4810e();
                if (this.f813c.m4803f() != null) {
                    new StringBuilder().append(e6);
                }
                new StringBuilder().append(e6);
                throw new HttpException(2, String.valueOf(e6));
            } catch (SSLException e7) {
                m4810e();
                if (this.f813c.m4803f() != null) {
                    new StringBuilder().append(e7);
                }
                new StringBuilder().append(e7);
                throw new HttpException(6, String.valueOf(e7));
            } catch (IOException e8) {
                m4810e();
                if (this.f813c.m4803f() != null) {
                    new StringBuilder().append(e8);
                }
                new StringBuilder().append(e8);
                throw new HttpException(6, String.valueOf(e8));
            } catch (Exception e9) {
                m4810e();
                if (this.f813c.m4803f() != null) {
                    new StringBuilder().append(e9);
                }
                throw new HttpException(0, String.valueOf(e9));
            } catch (ConnectionPoolTimeoutException e10) {
                m4810e();
                if (this.f813c.m4803f() != null) {
                    new StringBuilder().append(e10);
                }
                new StringBuilder().append(e10);
                throw new HttpException(3, String.valueOf(e10));
            } catch (NullPointerException e11) {
                m4810e();
                int i = this.f822m;
                if (i > 0) {
                    new StringBuilder().append(e11);
                    throw new HttpException(0, String.valueOf(e11));
                }
                this.f822m = i + 1;
            } catch (SSLPeerUnverifiedException e12) {
                m4810e();
                if (this.f813c.m4803f() != null) {
                    new StringBuilder().append(e12);
                }
                new StringBuilder().append(e12);
                throw new HttpException(2, String.valueOf(e12));
            } catch (ConnectTimeoutException e13) {
                m4810e();
                if (this.f813c.m4803f() != null) {
                    new StringBuilder().append(e13);
                }
                new StringBuilder().append(e13);
                throw new HttpException(3, String.valueOf(e13));
            } catch (NoHttpResponseException e14) {
                m4810e();
                if (this.f813c.m4803f() != null) {
                    new StringBuilder().append(e14);
                }
                new StringBuilder().append(e14);
                throw new HttpException(5, String.valueOf(e14));
            }
        }
    }

    /* renamed from: e */
    private void m4810e() {
        HttpUriRequest httpUriRequest = this.f815f;
        if (httpUriRequest != null) {
            httpUriRequest.abort();
        }
    }

    /* renamed from: f */
    private String m4809f() {
        if (!TextUtils.isEmpty(this.f826q)) {
            return this.f826q;
        }
        this.f826q = this.f813c.m4828b("operationType");
        return this.f826q;
    }

    /* renamed from: g */
    private int m4808g() {
        URL m4807h = m4807h();
        return m4807h.getPort() == -1 ? m4807h.getDefaultPort() : m4807h.getPort();
    }

    /* renamed from: h */
    private URL m4807h() {
        URL url = this.f821l;
        if (url != null) {
            return url;
        }
        this.f821l = new URL(this.f813c.m4835a());
        return this.f821l;
    }

    /* renamed from: i */
    private CookieManager m4806i() {
        CookieManager cookieManager = this.f818i;
        if (cookieManager != null) {
            return cookieManager;
        }
        this.f818i = CookieManager.getInstance();
        return this.f818i;
    }

    /* renamed from: a */
    public final C0905o m4820a() {
        return this.f813c;
    }
}
