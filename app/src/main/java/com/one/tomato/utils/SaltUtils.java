package com.one.tomato.utils;

import android.text.TextUtils;
import com.one.tomato.mvp.base.okhttp.interceptor.RequestHeaderInterceptor;
import com.one.tomato.utils.encrypt.AESUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import org.json.JSONObject;

/* compiled from: SaltUtils.kt */
/* loaded from: classes3.dex */
public final class SaltUtils {
    public static final SaltUtils INSTANCE = new SaltUtils();
    private static String salt = "";
    private static String salt1 = "";
    private static String salt2 = "";
    private static String salt3 = "";
    private static String salt4 = "";
    private static final String AES_KEY = AES_KEY;
    private static final String AES_KEY = AES_KEY;
    private static HashMap<String, String> keyVlues = new HashMap<>();
    private static LinkedHashSet<String> tempRequestKeySet = new LinkedHashSet<>();
    private static ArrayList<String> saltList = new ArrayList<>();

    private SaltUtils() {
    }

    public final synchronized void saltOne(String str, int i) {
        if (str == null) {
            return;
        }
        if (i == 1) {
            salt1 = str;
            if (keyVlues.size() > 0 && saltList.size() > 0) {
                HashMap<String, String> hashMap = keyVlues;
                String str2 = saltList.get(0);
                Intrinsics.checkExpressionValueIsNotNull(str2, "saltList[0]");
                hashMap.put(str2, salt1);
            }
            if (!TextUtils.isEmpty(salt2) && !TextUtils.isEmpty(salt3)) {
                String WTdecode = AESUtil.WTdecode(salt1 + salt2 + salt3, AES_KEY);
                Intrinsics.checkExpressionValueIsNotNull(WTdecode, "AESUtil.WTdecode(s, AES_KEY)");
                salt = WTdecode;
            }
        } else if (i == 2) {
            salt2 = str;
            if (keyVlues.size() > 0 && saltList.size() > 0) {
                HashMap<String, String> hashMap2 = keyVlues;
                String str3 = saltList.get(1);
                Intrinsics.checkExpressionValueIsNotNull(str3, "saltList[1]");
                hashMap2.put(str3, salt2);
            }
            if (!TextUtils.isEmpty(salt1) && !TextUtils.isEmpty(salt3)) {
                String WTdecode2 = AESUtil.WTdecode(salt1 + salt2 + salt3, AES_KEY);
                Intrinsics.checkExpressionValueIsNotNull(WTdecode2, "AESUtil.WTdecode(s, AES_KEY)");
                salt = WTdecode2;
            }
        } else if (i == 3) {
            salt3 = str;
            if (keyVlues.size() > 0 && saltList.size() > 0) {
                HashMap<String, String> hashMap3 = keyVlues;
                String str4 = saltList.get(2);
                Intrinsics.checkExpressionValueIsNotNull(str4, "saltList[2]");
                hashMap3.put(str4, salt3);
            }
            if (!TextUtils.isEmpty(salt1) && !TextUtils.isEmpty(salt2)) {
                String WTdecode3 = AESUtil.WTdecode(salt1 + salt2 + salt3, AES_KEY);
                Intrinsics.checkExpressionValueIsNotNull(WTdecode3, "AESUtil.WTdecode(s, AES_KEY)");
                salt = WTdecode3;
            }
        } else if (i == 4) {
            salt4 = str;
            if (!TextUtils.isEmpty(salt4)) {
                String WTdecode4 = AESUtil.WTdecode(salt4, AES_KEY);
                Intrinsics.checkExpressionValueIsNotNull(WTdecode4, "AESUtil.WTdecode(salt4, AES_KEY)");
                salt4 = WTdecode4;
                if (keyVlues.size() > 0 && saltList.size() > 0) {
                    HashMap<String, String> hashMap4 = keyVlues;
                    String str5 = saltList.get(3);
                    Intrinsics.checkExpressionValueIsNotNull(str5, "saltList[3]");
                    hashMap4.put(str5, salt4);
                }
            }
        }
    }

    public final String saltVlue() {
        if (!TextUtils.isEmpty(salt) && !TextUtils.isEmpty(salt4)) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("random", salt4);
                jSONObject.put("osVersion", RequestHeaderInterceptor.getValueEncoded(DeviceInfoUtil.getPhoneOSVersion()));
                jSONObject.put("model", RequestHeaderInterceptor.getValueEncoded(DeviceInfoUtil.getDeviceTypeName()));
                jSONObject.put("brand", RequestHeaderInterceptor.getValueEncoded(DeviceInfoUtil.getDeviceBrand()));
                jSONObject.put("deviceNo", RequestHeaderInterceptor.getValueEncoded(DeviceInfoUtil.getUniqueDeviceID()));
                String jSONObject2 = jSONObject.toString();
                Intrinsics.checkExpressionValueIsNotNull(jSONObject2, "jsonObject.toString()");
                String WTencryptAES = AESUtil.WTencryptAES(jSONObject2, salt);
                Intrinsics.checkExpressionValueIsNotNull(WTencryptAES, "AESUtil.WTencryptAES(s, salt)");
                return WTencryptAES;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public final void getKeyApp(String str) {
        List<String> split$default;
        keyVlues.clear();
        if (TextUtils.isEmpty(str)) {
            return;
        }
        if (str != null) {
            split$default = StringsKt__StringsKt.split$default(str, new String[]{","}, false, 0, 6, null);
            if (split$default.size() != 4) {
                return;
            }
            for (String str2 : split$default) {
                keyVlues.put(str2, "");
                saltList.add(str2);
            }
            return;
        }
        Intrinsics.throwNpe();
        throw null;
    }

    public final synchronized String getRequestKey() {
        if (keyVlues.size() > 0) {
            for (Map.Entry<String, String> entry : keyVlues.entrySet()) {
                if (TextUtils.isEmpty(entry.getValue())) {
                    tempRequestKeySet.add(entry.getKey());
                } else {
                    try {
                        tempRequestKeySet.remove(entry.getKey());
                    } catch (Exception unused) {
                    }
                }
            }
        }
        if (tempRequestKeySet.size() > 0) {
            String str = get(tempRequestKeySet, 0);
            tempRequestKeySet.remove(str);
            return str;
        }
        return "";
    }

    public final synchronized void parseResponseKey(String key) {
        List split$default;
        Intrinsics.checkParameterIsNotNull(key, "key");
        split$default = StringsKt__StringsKt.split$default(key, new String[]{","}, false, 0, 6, null);
        if (split$default.size() == 2) {
            String str = (String) split$default.get(1);
            int i = 0;
            String str2 = (String) split$default.get(0);
            if (saltList.size() > 0) {
                for (Object obj : saltList) {
                    int i2 = i + 1;
                    if (i >= 0) {
                        if (Intrinsics.areEqual(str, (String) obj)) {
                            INSTANCE.saltOne(str2, i2);
                            return;
                        }
                        i = i2;
                    } else {
                        CollectionsKt.throwIndexOverflow();
                        throw null;
                    }
                }
            }
        }
    }

    public final void clearData() {
        salt = "";
        salt1 = "";
        salt2 = "";
        salt3 = "";
        salt4 = "";
        tempRequestKeySet.clear();
        keyVlues.clear();
        saltList.clear();
    }

    private final String get(HashSet<String> hashSet, int i) {
        Iterator<String> it2 = hashSet.iterator();
        Intrinsics.checkExpressionValueIsNotNull(it2, "iterator()");
        int i2 = 0;
        while (it2.hasNext()) {
            String it3 = it2.next();
            if (i == i2) {
                Intrinsics.checkExpressionValueIsNotNull(it3, "it");
                return it3;
            }
            i2++;
        }
        return "";
    }
}
