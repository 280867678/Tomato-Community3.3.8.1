package com.alipay.sdk.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import com.alipay.sdk.util.C0996c;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;

/* renamed from: com.alipay.sdk.net.a */
/* loaded from: classes2.dex */
public final class C0973a {

    /* renamed from: c */
    private static final CookieManager f989c = new CookieManager();

    /* renamed from: com.alipay.sdk.net.a$a */
    /* loaded from: classes2.dex */
    public static final class C0974a {

        /* renamed from: a */
        public final String f990a;

        /* renamed from: b */
        public final byte[] f991b;

        /* renamed from: c */
        public final Map<String, String> f992c;

        public C0974a(String str, Map<String, String> map, byte[] bArr) {
            this.f990a = str;
            this.f991b = bArr;
            this.f992c = map;
        }

        public String toString() {
            return String.format("<UrlConnectionConfigure url=%s headers=%s>", this.f990a, this.f992c);
        }
    }

    /* renamed from: com.alipay.sdk.net.a$b */
    /* loaded from: classes2.dex */
    public static final class C0975b {

        /* renamed from: a */
        public final Map<String, List<String>> f993a;

        /* renamed from: c */
        public final byte[] f994c;

        public C0975b(Map<String, List<String>> map, String str, byte[] bArr) {
            this.f993a = map;
            this.f994c = bArr;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:101:0x01c6 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:105:0x01bf A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:97:0x01cd A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.io.OutputStream] */
    /* renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static C0975b m4539a(Context context, C0974a c0974a) {
        Throwable th;
        HttpURLConnection httpURLConnection;
        BufferedInputStream bufferedInputStream;
        BufferedOutputStream bufferedOutputStream;
        BufferedOutputStream bufferedOutputStream2;
        if (context == null) {
            return null;
        }
        try {
            C0996c.m4435b("mspl", "conn config: " + c0974a);
            URL url = new URL(c0974a.f990a);
            Proxy m4540a = m4540a(context);
            C0996c.m4435b("mspl", "conn proxy: " + m4540a);
            if (m4540a != null) {
                httpURLConnection = (HttpURLConnection) url.openConnection(m4540a);
            } else {
                httpURLConnection = (HttpURLConnection) url.openConnection();
            }
            try {
                System.setProperty("http.keepAlive", "false");
                if (httpURLConnection instanceof HttpsURLConnection) {
                    HttpsURLConnection httpsURLConnection = (HttpsURLConnection) httpURLConnection;
                }
                if (f989c.getCookieStore().getCookies().size() > 0) {
                    httpURLConnection.setRequestProperty("Cookie", TextUtils.join(";", f989c.getCookieStore().getCookies()));
                }
                httpURLConnection.setConnectTimeout(20000);
                httpURLConnection.setReadTimeout(30000);
                httpURLConnection.setInstanceFollowRedirects(true);
                httpURLConnection.setRequestProperty("User-Agent", "msp");
                if (c0974a.f991b != null && c0974a.f991b.length > 0) {
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setRequestProperty("Content-Type", "application/octet-stream;binary/octet-stream");
                    httpURLConnection.setRequestProperty("Accept-Charset", "UTF-8");
                    httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
                    httpURLConnection.setRequestProperty("Keep-Alive", "timeout=180, max=100");
                } else {
                    httpURLConnection.setRequestMethod("GET");
                }
                if (c0974a.f992c != null) {
                    for (Map.Entry<String, String> entry : c0974a.f992c.entrySet()) {
                        if (entry.getKey() != null) {
                            httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
                        }
                    }
                }
                httpURLConnection.setDoInput(true);
                if ("POST".equals(httpURLConnection.getRequestMethod())) {
                    httpURLConnection.setDoOutput(true);
                }
                if ("POST".equals(httpURLConnection.getRequestMethod())) {
                    bufferedOutputStream2 = new BufferedOutputStream(httpURLConnection.getOutputStream());
                    try {
                        bufferedOutputStream2.write(c0974a.f991b);
                        bufferedOutputStream2.flush();
                    } catch (Throwable th2) {
                        th = th2;
                        bufferedOutputStream = bufferedOutputStream2;
                        bufferedInputStream = null;
                        try {
                            C0996c.m4436a(th);
                            return null;
                        } finally {
                            if (httpURLConnection != null) {
                                try {
                                    httpURLConnection.disconnect();
                                } catch (Throwable unused) {
                                }
                            }
                            if (bufferedInputStream != null) {
                                try {
                                    bufferedInputStream.close();
                                } catch (Throwable unused2) {
                                }
                            }
                            if (bufferedOutputStream != 0) {
                                try {
                                    bufferedOutputStream.close();
                                } catch (Throwable unused3) {
                                }
                            }
                        }
                    }
                } else {
                    bufferedOutputStream2 = null;
                }
                BufferedInputStream bufferedInputStream2 = new BufferedInputStream(httpURLConnection.getInputStream());
                try {
                    byte[] m4538a = m4538a(bufferedInputStream2);
                    Map<String, List<String>> headerFields = httpURLConnection.getHeaderFields();
                    String join = (headerFields == null || headerFields.get(null) == null) ? null : TextUtils.join(",", headerFields.get(null));
                    List<String> list = headerFields.get("Set-Cookie");
                    if (list != null) {
                        for (String str : list) {
                            List<HttpCookie> parse = HttpCookie.parse(str);
                            if (parse != null && !parse.isEmpty()) {
                                f989c.getCookieStore().add(url.toURI(), parse.get(0));
                            }
                        }
                    }
                    C0975b c0975b = new C0975b(headerFields, join, m4538a);
                    if (httpURLConnection != null) {
                        try {
                            httpURLConnection.disconnect();
                        } catch (Throwable unused4) {
                        }
                    }
                    try {
                        bufferedInputStream2.close();
                    } catch (Throwable unused5) {
                    }
                    if (bufferedOutputStream2 != null) {
                        try {
                            bufferedOutputStream2.close();
                        } catch (Throwable unused6) {
                        }
                    }
                    return c0975b;
                } catch (Throwable th3) {
                    BufferedOutputStream bufferedOutputStream3 = bufferedOutputStream2;
                    bufferedInputStream = bufferedInputStream2;
                    th = th3;
                    bufferedOutputStream = bufferedOutputStream3;
                    C0996c.m4436a(th);
                    if (bufferedOutputStream != 0) {
                        try {
                            bufferedOutputStream.close();
                        } catch (Throwable unused7) {
                        }
                    }
                    return null;
                }
            } catch (Throwable th4) {
                th = th4;
                bufferedInputStream = null;
                bufferedOutputStream = bufferedInputStream;
                C0996c.m4436a(th);
                return null;
            }
        } catch (Throwable th5) {
            th = th5;
            httpURLConnection = null;
            bufferedInputStream = null;
        }
    }

    /* renamed from: a */
    private static Proxy m4540a(Context context) {
        String m4536c = m4536c(context);
        if (m4536c == null || m4536c.contains("wap")) {
            try {
                String property = System.getProperty("https.proxyHost");
                String property2 = System.getProperty("https.proxyPort");
                if (TextUtils.isEmpty(property)) {
                    return null;
                }
                return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(property, Integer.parseInt(property2)));
            } catch (Throwable unused) {
                return null;
            }
        }
        return null;
    }

    /* renamed from: b */
    private static NetworkInfo m4537b(Context context) {
        if (context == null) {
            return null;
        }
        try {
            return ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (Exception unused) {
            return null;
        }
    }

    /* renamed from: c */
    private static String m4536c(Context context) {
        try {
            NetworkInfo m4537b = m4537b(context);
            if (m4537b != null && m4537b.isAvailable()) {
                return m4537b.getType() == 1 ? AopConstants.WIFI : m4537b.getExtraInfo().toLowerCase();
            }
        } catch (Exception unused) {
        }
        return "none";
    }

    /* renamed from: a */
    private static byte[] m4538a(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[1024];
        while (true) {
            int read = inputStream.read(bArr, 0, bArr.length);
            if (read != -1) {
                byteArrayOutputStream.write(bArr, 0, read);
            } else {
                byteArrayOutputStream.flush();
                return byteArrayOutputStream.toByteArray();
            }
        }
    }
}
