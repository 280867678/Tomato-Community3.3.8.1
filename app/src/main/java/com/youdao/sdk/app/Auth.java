package com.youdao.sdk.app;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.j256.ormlite.stmt.query.SimpleComparison;
import com.tomatolive.library.utils.ConstantUtils;
import com.youdao.sdk.app.other.C5157b;
import com.youdao.sdk.app.other.C5170s;
import com.youdao.sdk.common.YDUrlGenerator;
import com.youdao.sdk.common.YouDaoLog;
import com.youdao.sdk.ydtranslate.TranslateSdk;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class Auth {
    public static void valid(Context context) {
        HashMap hashMap = new HashMap();
        String[] generateEncryptV1 = EncryptHelper.generateEncryptV1(paramString(context, ""));
        hashMap.put("s", generateEncryptV1[0]);
        hashMap.put("et", generateEncryptV1[1]);
        HttpHelper.postRequest("http://updateinfo.youdao.com/update?", hashMap, new C5157b(context), ConstantUtils.MAX_ITEM_NUM);
    }

    private static long setLocalDefault(Context context) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(6, 30);
        long time = calendar.getTime().getTime();
        C5170s.m178b(context, "offline_end_use_day", time + "");
        return time;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void initAuth(String str, Context context) {
        try {
            String generateDecryptV1 = EncryptHelper.generateDecryptV1(str);
            if (TextUtils.isEmpty(generateDecryptV1)) {
                return;
            }
            JSONObject jSONObject = new JSONObject(generateDecryptV1);
            String string = jSONObject.getString("errorCode");
            long j = jSONObject.getLong("timestamp");
            boolean z = Math.abs(System.currentTimeMillis() - j) < 600000;
            if ("0".equalsIgnoreCase(string) && z) {
                C5170s.m178b(context, "offline_auth", EncryptHelper.generateEncrypt("good" + j));
                setLocalDefault(context);
            } else {
                C5170s.m178b(context, "offline_auth", EncryptHelper.generateEncrypt("bad" + j));
            }
        } catch (Exception e) {
            YouDaoLog.m164w("check auth exception:" + e.getMessage(), e);
            C5170s.m178b(context, "offline_auth", EncryptHelper.generateEncrypt("good" + System.currentTimeMillis()));
        }
    }

    private static Map<String, String> params(Context context, String str) {
        String appKey = getAppKey();
        YDUrlGenerator yDUrlGenerator = new YDUrlGenerator(context);
        yDUrlGenerator.withAppKey(appKey);
        Map<String, String> generateUrlMap = yDUrlGenerator.generateUrlMap();
        int nextInt = new Random().nextInt(1000);
        String sign = new TranslateSdk().sign(context, appKey, str, String.valueOf(nextInt));
        generateUrlMap.put("q", str);
        generateUrlMap.put("salt", String.valueOf(nextInt));
        generateUrlMap.put("signType", "v1");
        generateUrlMap.put("docType", "json");
        generateUrlMap.put("sign", sign);
        generateUrlMap.put("offline", "0");
        generateUrlMap.put(DatabaseFieldConfigLoader.FIELD_NAME_VERSION, "v2");
        return generateUrlMap;
    }

    private static String paramString(Context context, String str) {
        Map<String, String> params = params(context, str);
        StringBuilder sb = new StringBuilder("");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (!TextUtils.isEmpty(value)) {
                sb.append(key);
                sb.append(SimpleComparison.EQUAL_TO_OPERATION);
                sb.append(Uri.encode(value));
                sb.append("&");
            }
        }
        return sb.toString();
    }

    private static final String getAppKey() {
        return YouDaoApplication.mAppKey;
    }
}
