package com.one.tomato.net;

import android.text.TextUtils;
import com.j256.ormlite.stmt.query.SimpleComparison;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.DeviceInfoUtil;
import com.one.tomato.utils.DisplayMetricsUtils;
import com.one.tomato.utils.NetWorkUtil;
import com.one.tomato.utils.PreferencesUtil;
import com.one.tomato.utils.SaltUtils;
import com.one.tomato.utils.encrypt.MD5Util;
import com.tomatolive.library.utils.LogConstants;
import com.tomatolive.library.utils.StringUtils;
import java.security.cert.X509Certificate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.xutils.common.util.KeyValue;
import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;
import org.xutils.http.app.ParamsBuilder;

/* loaded from: classes3.dex */
public class TomatoParamsBuilder implements ParamsBuilder {
    private static final String SECRET_MD5 = "USETVOQC5ZDUVYMW";
    private static SSLSocketFactory trustAllSSlSocketFactory;
    private HashMap<String, String> headers;

    @Override // org.xutils.http.app.ParamsBuilder
    public String buildCacheKey(RequestParams requestParams, String[] strArr) {
        return null;
    }

    @Override // org.xutils.http.app.ParamsBuilder
    public void buildSign(RequestParams requestParams, String[] strArr) throws Throwable {
    }

    @Override // org.xutils.http.app.ParamsBuilder
    public String buildUri(RequestParams requestParams, HttpRequest httpRequest) throws Throwable {
        return requestParams.getUri();
    }

    @Override // org.xutils.http.app.ParamsBuilder
    public SSLSocketFactory getSSLSocketFactory() throws Throwable {
        return getTrustAllSSLSocketFactory();
    }

    @Override // org.xutils.http.app.ParamsBuilder
    public void buildParams(RequestParams requestParams) throws Throwable {
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() { // from class: com.one.tomato.net.TomatoParamsBuilder.1
            @Override // javax.net.ssl.HostnameVerifier
            public boolean verify(String str, SSLSession sSLSession) {
                return true;
            }
        });
        commonHeader(requestParams);
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0132, code lost:
        if (r2 == 1) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0134, code lost:
        if (r2 == 2) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0137, code lost:
        r1 = "en_US";
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x013b, code lost:
        r1 = "zh_CN";
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public synchronized void commonHeader(RequestParams requestParams) {
        setHeaders("Response-Content-Type", "application/json");
        setHeaders("memberId", String.valueOf(DBUtil.getMemberId()));
        setHeaders("token", DBUtil.getToken());
        setHeaders("versionNo", AppUtil.getVersionCodeStr());
        setHeaders(LogConstants.APP_ID, "1");
        setHeaders("channelId", TextUtils.isEmpty(BaseApplication.getApplication().getChannelId()) ? "" : BaseApplication.getApplication().getChannelId());
        setHeaders("subChannelId", TextUtils.isEmpty(BaseApplication.getApplication().getChannelSubId()) ? "" : BaseApplication.getApplication().getChannelSubId());
        setHeaders("deviceNo", DeviceInfoUtil.getUniqueDeviceID());
        setHeaders("brand", DeviceInfoUtil.getDeviceBrand());
        setHeaders("model", DeviceInfoUtil.getDeviceTypeName());
        setHeaders("osVersion", DeviceInfoUtil.getPhoneOSVersion());
        setHeaders("netStatus", NetWorkUtil.getNetWorkType());
        setHeaders("phoneResolution", DisplayMetricsUtils.getResolution());
        if (TextUtils.isEmpty(SaltUtils.INSTANCE.saltVlue())) {
            String requestKey = SaltUtils.INSTANCE.getRequestKey();
            if (!TextUtils.isEmpty(requestKey)) {
                this.headers.put("reqSaltKey", requestKey);
            }
        } else {
            this.headers.remove("reqSaltKey");
            this.headers.put("salt", SaltUtils.INSTANCE.saltVlue());
        }
        String string = PreferencesUtil.getInstance().getString("language_country");
        if (!StringUtils.isEmpty(string)) {
            String str = "";
            char c = 65535;
            int hashCode = string.hashCode();
            if (hashCode != 2155) {
                if (hashCode != 2691) {
                    if (hashCode == 2718 && string.equals("US")) {
                        c = 2;
                    }
                } else if (string.equals("TW")) {
                    c = 0;
                }
            } else if (string.equals("CN")) {
                c = 1;
            }
            str = "zh_TW";
            setHeaders("locale", str);
        }
        String decodeStrings = decodeStrings(requestParams);
        if (!TextUtils.isEmpty(decodeStrings)) {
            setHeaders("gCode", decodeStrings);
        }
        setHeaders("NationalDay", "10.1");
        setHeaders("v", "1");
        requestParams.removeParameter("path");
        setHeader(requestParams);
    }

    private synchronized String decodeStrings(RequestParams requestParams) {
        StringBuilder sb;
        int i;
        TreeMap treeMap = new TreeMap(new Comparator<String>() { // from class: com.one.tomato.net.TomatoParamsBuilder.2
            @Override // java.util.Comparator
            public int compare(String str, String str2) {
                return str.compareTo(str2);
            }
        });
        sb = new StringBuilder();
        for (KeyValue keyValue : requestParams.getQueryStringParams()) {
            treeMap.put(keyValue.key, keyValue.getValueStr());
        }
        for (Map.Entry entry : treeMap.entrySet()) {
            sb.append(((String) entry.getKey()) + SimpleComparison.EQUAL_TO_OPERATION + ((String) entry.getValue()));
            sb.append("&");
        }
        int length = sb.length();
        if (length > 0 && sb.lastIndexOf("&") == length - 1) {
            sb = sb.deleteCharAt(i);
        }
        return MD5Util.md5(SECRET_MD5 + sb.toString());
    }

    private String headString(int i, String str) {
        return str.split("\\?")[i];
    }

    public synchronized void setHeader(RequestParams requestParams) {
        for (Map.Entry<String, String> entry : this.headers.entrySet()) {
            requestParams.setHeader(entry.getKey(), entry.getValue());
        }
    }

    public HashMap<String, String> gHeaders() {
        if (this.headers == null) {
            this.headers = new HashMap<>();
        }
        return this.headers;
    }

    public synchronized void setHeaders(String str, String str2) {
        if (this.headers == null) {
            this.headers = new HashMap<>();
        }
        this.headers.put(str, str2);
    }

    private String getHost(String str) {
        return TextUtils.isEmpty("http://192.168.xx.xxx") ? str : "http://192.168.xx.xxx";
    }

    public static SSLSocketFactory getTrustAllSSLSocketFactory() {
        if (trustAllSSlSocketFactory == null) {
            synchronized (TomatoParamsBuilder.class) {
                if (trustAllSSlSocketFactory == null) {
                    TrustManager[] trustManagerArr = {new X509TrustManager() { // from class: com.one.tomato.net.TomatoParamsBuilder.3
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
