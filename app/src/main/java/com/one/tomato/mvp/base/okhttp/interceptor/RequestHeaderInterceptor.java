package com.one.tomato.mvp.base.okhttp.interceptor;

import android.text.TextUtils;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.j256.ormlite.stmt.query.SimpleComparison;
import com.one.tomato.entity.JavDBSysteamBean;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.thirdpart.promotion.PromotionUtil;
import com.one.tomato.utils.AppSecretUtil;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.DeviceInfoUtil;
import com.one.tomato.utils.DisplayMetricsUtils;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.NetWorkUtil;
import com.one.tomato.utils.PreferencesUtil;
import com.one.tomato.utils.SaltUtils;
import com.one.tomato.utils.encrypt.MD5Util;
import com.tomatolive.library.http.utils.EncryptUtil;
import com.tomatolive.library.utils.LogConstants;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/* loaded from: classes3.dex */
public class RequestHeaderInterceptor implements Interceptor {
    private Map<String, String> headers = new HashMap();

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder newBuilder = request.newBuilder();
        TreeMap treeMap = new TreeMap(new Comparator<String>(this) { // from class: com.one.tomato.mvp.base.okhttp.interceptor.RequestHeaderInterceptor.1
            @Override // java.util.Comparator
            public int compare(String str, String str2) {
                return str.compareTo(str2);
            }
        });
        String bodyString = getBodyString(request, treeMap);
        String pathString = getPathString(request, treeMap);
        initHeaders(newBuilder, bodyString, pathString, treeMap);
        return chain.proceed(getNewRequest(request, newBuilder, bodyString, pathString));
    }

    private synchronized String getBodyString(Request request, Map<String, String> map) {
        StringBuffer stringBuffer;
        FormBody formBody;
        int size;
        stringBuffer = new StringBuffer();
        RequestBody body = request.body();
        if (body != null && (body instanceof FormBody) && (size = (formBody = (FormBody) body).size()) > 0) {
            for (int i = 0; i < size; i++) {
                String encodedName = formBody.encodedName(i);
                String str = null;
                try {
                    str = URLDecoder.decode(formBody.encodedValue(i), EncryptUtil.CHARSET);
                    map.put(encodedName, str);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                stringBuffer.append(encodedName + SimpleComparison.EQUAL_TO_OPERATION + str);
                if (i != size - 1) {
                    stringBuffer.append("&");
                }
            }
        }
        return stringBuffer.toString();
    }

    private synchronized String getPathString(Request request, Map<String, String> map) {
        String sb;
        List<String> pathSegments = request.url().pathSegments();
        StringBuilder sb2 = new StringBuilder();
        int size = pathSegments.size();
        for (int i = 0; i < size; i++) {
            String str = pathSegments.get(i);
            if (!TextUtils.isEmpty(str)) {
                sb2.append('/');
                sb2.append(str);
            }
        }
        sb = sb2.toString();
        map.put("path", sb);
        return sb;
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0151, code lost:
        if (r0 == 1) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0153, code lost:
        if (r0 == 2) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0156, code lost:
        r8 = "en_US";
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x015a, code lost:
        r8 = "zh_CN";
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private synchronized void initHeaders(Request.Builder builder, String str, String str2, Map<String, String> map) {
        this.headers.put("memberId", String.valueOf(DBUtil.getMemberId()));
        this.headers.put("token", DBUtil.getToken());
        this.headers.put("versionNo", AppUtil.getVersionCodeStr());
        this.headers.put(LogConstants.APP_ID, "1");
        this.headers.put("channelId", TextUtils.isEmpty(BaseApplication.getApplication().getChannelId()) ? "" : BaseApplication.getApplication().getChannelId());
        this.headers.put("subChannelId", TextUtils.isEmpty(BaseApplication.getApplication().getChannelSubId()) ? "" : BaseApplication.getApplication().getChannelSubId());
        this.headers.put("accountNo", BaseApplication.getApplication().getApkSortId());
        this.headers.put("deviceNo", DeviceInfoUtil.getUniqueDeviceID());
        this.headers.put("brand", DeviceInfoUtil.getDeviceBrand());
        this.headers.put("model", DeviceInfoUtil.getDeviceTypeName());
        this.headers.put("osVersion", DeviceInfoUtil.getPhoneOSVersion());
        this.headers.put("netStatus", NetWorkUtil.getNetWorkType());
        this.headers.put("phoneResolution", DisplayMetricsUtils.getResolution());
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
        if (!TextUtils.isEmpty(string)) {
            String str3 = "";
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
            str3 = "zh_TW";
            this.headers.put("locale", str3);
        }
        String gcode = getGcode(map);
        if (!TextUtils.isEmpty(gcode)) {
            this.headers.put("gCode", gcode);
        }
        this.headers.put("NationalDay", "10.1");
        this.headers.put("v", "1");
        if (this.headers != null && this.headers.size() > 0) {
            for (String str4 : this.headers.keySet()) {
                builder.addHeader(str4, getValueEncoded(this.headers.get(str4)));
            }
        }
    }

    private synchronized String getGcode(Map<String, String> map) {
        StringBuilder sb;
        int i;
        sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey() + SimpleComparison.EQUAL_TO_OPERATION + entry.getValue());
            sb.append("&");
        }
        int length = sb.length();
        if (length > 0 && sb.lastIndexOf("&") == length - 1) {
            sb = sb.deleteCharAt(i);
        }
        return MD5Util.md5("USETVOQC5ZDUVYMW" + sb.toString());
    }

    public static synchronized String getValueEncoded(String str) {
        synchronized (RequestHeaderInterceptor.class) {
            if (str == null) {
                return "null";
            }
            String replace = str.replace("\n", "");
            int length = replace.length();
            for (int i = 0; i < length; i++) {
                char charAt = replace.charAt(i);
                if (charAt <= 31 || charAt >= 127) {
                    try {
                        return URLEncoder.encode(replace, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
            return replace;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private Request getNewRequest(Request request, Request.Builder builder, String str, String str2) {
        char c;
        String str3;
        String serverUrl = DomainServer.getInstance().getServerUrl();
        String header = request.header("serverType");
        long currentTimeMillis = System.currentTimeMillis();
        if (!TextUtils.isEmpty(header)) {
            switch (header.hashCode()) {
                case -934521548:
                    if (header.equals("report")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -799212381:
                    if (header.equals("promotion")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 3385:
                    if (header.equals("jc")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 104991:
                    if (header.equals("jav")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 92750597:
                    if (header.equals("agent")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            if (c == 0) {
                serverUrl = DomainServer.getInstance().getReportServerUrl();
            } else if (c == 1) {
                LogUtil.m3784i("requestUrl : " + request.url().toString() + str2 + "?" + str);
                return request;
            } else {
                String str4 = "";
                if (c == 2) {
                    try {
                        str3 = requestBodyToString(request.body());
                    } catch (IOException e) {
                        e.printStackTrace();
                        str3 = str4;
                    }
                    Request.Builder newBuilder = request.newBuilder();
                    String md5 = MD5Util.md5(DBUtil.getMemberId() + "&" + PromotionUtil.merchantId + "&" + currentTimeMillis + "&" + str3 + "&" + PromotionUtil.signKey);
                    newBuilder.addHeader("Content-Type", "application/json");
                    newBuilder.addHeader("User-Agent", "User-Agent");
                    newBuilder.addHeader("X-Client-Identity", "111");
                    StringBuilder sb = new StringBuilder();
                    sb.append(currentTimeMillis);
                    sb.append(str4);
                    newBuilder.addHeader("X-Client-TimeStamp", sb.toString());
                    newBuilder.addHeader("X-Client-Sign", md5);
                    newBuilder.addHeader("X-Auth-Token", PromotionUtil.promotionToken);
                    LogUtil.m3784i("requestUrl : " + request.url().toString() + "?" + str3);
                    return newBuilder.build();
                } else if (c == 3) {
                    try {
                        str4 = requestBodyToString(request.body());
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                    Request.Builder newBuilder2 = request.newBuilder();
                    newBuilder2.addHeader("Content-Type", "application/json");
                    newBuilder2.addHeader(DatabaseFieldConfigLoader.FIELD_NAME_VERSION, "1.0.0");
                    LogUtil.m3784i("requestUrl : " + request.url().toString() + "?" + str4);
                    return newBuilder2.build();
                } else if (c == 4) {
                    try {
                        str4 = requestBodyToString(request.body());
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                    Request.Builder newBuilder3 = request.newBuilder();
                    String javDbParameter = DBUtil.getSystemParam().getJavDbParameter();
                    if (!TextUtils.isEmpty(javDbParameter)) {
                        BaseApplication.getApplication();
                        JavDBSysteamBean javDBSysteamBean = (JavDBSysteamBean) BaseApplication.gson.fromJson(javDbParameter, (Class<Object>) JavDBSysteamBean.class);
                        if (javDBSysteamBean != null) {
                            String l = Long.toString(System.currentTimeMillis() / 1000);
                            String secretId = javDBSysteamBean.getSecretId();
                            String secretKey = javDBSysteamBean.getSecretKey();
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(l);
                            sb2.append(".");
                            sb2.append(secretId);
                            sb2.append(".");
                            sb2.append(MD5Util.md5(l + secretKey));
                            newBuilder3.addHeader("jdbsign", sb2.toString());
                            LogUtil.m3784i("requestUrl : " + request.url().toString() + "?" + str4);
                        }
                    }
                    return newBuilder3.build();
                }
            }
        }
        HttpUrl url = request.url();
        HttpUrl.Builder newBuilder4 = url.newBuilder();
        int size = url.pathSegments().size();
        for (int i = 0; i < size; i++) {
            newBuilder4.removePathSegment(0);
        }
        HttpUrl parse = HttpUrl.parse(serverUrl);
        String encodePath = AppSecretUtil.encodePath(str2);
        LogUtil.m3784i("requestUrl : " + serverUrl + str2 + "?" + str + ", encryptPath = " + encodePath);
        newBuilder4.scheme(parse.scheme());
        newBuilder4.host(parse.host());
        newBuilder4.port(parse.port());
        newBuilder4.setPathSegment(0, encodePath);
        builder.url(newBuilder4.build());
        return builder.build();
    }

    private String requestBodyToString(RequestBody requestBody) throws IOException {
        Buffer buffer = new Buffer();
        requestBody.writeTo(buffer);
        return buffer.readUtf8();
    }
}
