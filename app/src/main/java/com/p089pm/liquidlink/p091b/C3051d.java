package com.p089pm.liquidlink.p091b;

import com.j256.ormlite.stmt.query.SimpleComparison;
import com.tomatolive.library.http.utils.EncryptUtil;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.Map;

/* renamed from: com.pm.liquidlink.b.d */
/* loaded from: classes3.dex */
public class C3051d {
    /* renamed from: a */
    public static String m3737a(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, EncryptUtil.CHARSET));
        StringBuilder sb = new StringBuilder();
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine == null) {
                bufferedReader.close();
                return sb.toString();
            }
            sb.append(readLine);
        }
    }

    /* renamed from: a */
    public static String m3736a(Map map) {
        if (map == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry entry : map.entrySet()) {
            sb.append(URLEncoder.encode((String) entry.getKey(), EncryptUtil.CHARSET));
            sb.append(SimpleComparison.EQUAL_TO_OPERATION);
            sb.append(URLEncoder.encode(entry.getValue() == null ? "" : (String) entry.getValue(), EncryptUtil.CHARSET));
            sb.append("&");
        }
        String sb2 = sb.toString();
        return sb2.length() > 0 ? sb2.substring(0, sb2.length() - 1) : sb2;
    }
}
