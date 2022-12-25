package com.tomatolive.library.utils;

import android.text.TextUtils;
import com.j256.ormlite.stmt.query.SimpleComparison;
import com.tomatolive.library.R$string;
import com.tomatolive.library.TomatoLiveSDK;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.interceptor.AddHeaderInterceptor;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.TreeMap;
import okhttp3.Request;

/* loaded from: classes4.dex */
public class SignRequestUtils {
    private SignRequestUtils() {
    }

    private static byte[] getContentBytes(String str, String str2) {
        if (str2 == null || "".equals(str2)) {
            return str.getBytes();
        }
        try {
            return str.getBytes(str2);
        } catch (UnsupportedEncodingException unused) {
            throw new RuntimeException(SystemUtils.getResString(R$string.fq_md5_error) + str2);
        }
    }

    public static String signRequest(Request request, TreeMap<String, Object> treeMap, String str) {
        TreeMap treeMap2 = new TreeMap($$Lambda$TEfSBt3hRUlBSSARfPEHsJesTtE.INSTANCE);
        treeMap2.putAll(treeMap);
        treeMap2.put(AddHeaderInterceptor.TIME_STAMP_STR, request.header(AddHeaderInterceptor.TIME_STAMP_STR));
        treeMap2.put(AddHeaderInterceptor.RANDOM_STR, request.header(AddHeaderInterceptor.RANDOM_STR));
        treeMap2.put("deviceId", request.header("deviceId"));
        StringBuilder sb = new StringBuilder();
        for (Map.Entry entry : treeMap2.entrySet()) {
            String str2 = (String) entry.getKey();
            Object value = entry.getValue();
            if (!TextUtils.equals(RequestParams.PAGE_SIZE, str2) && !TextUtils.equals(RequestParams.PAGE_NUMBER, str2) && value != null) {
                sb.append(str2);
                sb.append(SimpleComparison.EQUAL_TO_OPERATION);
                sb.append(value);
                sb.append("&");
            }
        }
        if (sb.indexOf("&") != -1) {
            sb.deleteCharAt(sb.lastIndexOf("&"));
        }
        return MD5Utils.hash(TomatoLiveSDK.getSingleton().SIGN_API_KEY + "_" + sb.toString().toUpperCase());
    }

    public static String getRandomString() {
        return StringUtils.getRandomString(16);
    }

    public static String getTimestampString() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }
}
