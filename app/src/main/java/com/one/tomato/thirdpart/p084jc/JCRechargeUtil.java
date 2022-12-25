package com.one.tomato.thirdpart.p084jc;

import android.content.Context;
import com.google.gson.JsonSyntaxException;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.one.tomato.entity.JCParameter;
import com.one.tomato.entity.p079db.SystemParam;
import com.one.tomato.entity.p079db.UserInfo;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.thirdpart.webapp.WebAppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.ToastUtil;
import com.tomatolive.library.utils.LogConstants;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.one.tomato.thirdpart.jc.JCRechargeUtil */
/* loaded from: classes3.dex */
public class JCRechargeUtil {
    public static JCParameter jcParameter;
    public static SystemParam systemParam;
    public static WebAppUtil webAppUtil;

    public static boolean initSuccess() {
        if (DBUtil.getUserInfo() == null) {
            ToastUtil.showCenterToast("用户信息获取失败");
            return false;
        }
        if (systemParam == null) {
            systemParam = DBUtil.getSystemParam();
        }
        if (systemParam == null) {
            return false;
        }
        LogUtil.m3784i("金蟾参数：" + systemParam.getJCParameter());
        try {
            jcParameter = (JCParameter) BaseApplication.getGson().fromJson(systemParam.getJCParameter(), (Class<Object>) JCParameter.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            jcParameter = null;
        }
        if (jcParameter == null) {
            return false;
        }
        if (webAppUtil != null) {
            return true;
        }
        webAppUtil = new WebAppUtil();
        return true;
    }

    private static void addInitParam(HashMap<String, String> hashMap, Map<String, String> map, boolean z) {
        hashMap.put("alias", jcParameter.appAlias);
        hashMap.put(DatabaseFieldConfigLoader.FIELD_NAME_ID, jcParameter.appId);
        hashMap.put("host", jcParameter.appUrl);
        hashMap.put("key", jcParameter.app_key);
        String jCUrl = DomainServer.getInstance().getJCUrl();
        String str = jCUrl + ":" + jcParameter.httpPort;
        String str2 = jCUrl + ":" + jcParameter.socketPort;
        if (str2.startsWith("http")) {
            str2 = str2.replace("http", "ws");
        } else if (str2.startsWith("https")) {
            str2 = str2.replace("https", "wss");
        }
        map.put("httpHost", str);
        map.put("imHost", str2);
        JCParameter jCParameter = jcParameter;
        map.put("appFlag", z ? jCParameter.spreadAppFlag : jCParameter.appFlag);
        map.put("apiKey", jcParameter.appKey);
        map.put("secretKey", jcParameter.appSecret);
        UserInfo userInfo = DBUtil.getUserInfo();
        String userId = userInfo.getUserId();
        String str3 = userInfo.getMemberId() + "";
        String name = userInfo.getName();
        map.put("thirdUserName", userId);
        map.put("thirdUserId", str3);
        map.put("nickname", name);
        map.put("bottomBlankHeight", "0");
    }

    public static void createOrder(Context context, String str, int i) {
        if (!initSuccess()) {
            LogUtil.m3786e("金蟾获取系统参数失败");
            return;
        }
        HashMap<String, String> hashMap = new HashMap<>();
        HashMap<String, String> hashMap2 = new HashMap<>();
        addInitParam(hashMap, hashMap2, false);
        hashMap2.put("forWhat", "mall");
        hashMap2.put(LogConstants.FOLLOW_OPERATION_TYPE, "addOrder");
        hashMap2.put("skuId", str);
        hashMap2.put("amount", i + "");
        webAppUtil.startWebAppActivity(context, hashMap, hashMap2);
    }

    public static void startOrderList(Context context) {
        if (!initSuccess()) {
            LogUtil.m3786e("金蟾获取系统参数失败");
            return;
        }
        HashMap<String, String> hashMap = new HashMap<>();
        HashMap<String, String> hashMap2 = new HashMap<>();
        addInitParam(hashMap, hashMap2, false);
        hashMap2.put("forWhat", "mall");
        hashMap2.put(LogConstants.FOLLOW_OPERATION_TYPE, "orderList");
        webAppUtil.startWebAppActivity(context, hashMap, hashMap2);
    }

    public static void startHelp(Context context) {
        if (!initSuccess()) {
            LogUtil.m3786e("金蟾获取系统参数失败");
            return;
        }
        HashMap<String, String> hashMap = new HashMap<>();
        HashMap<String, String> hashMap2 = new HashMap<>();
        addInitParam(hashMap, hashMap2, false);
        hashMap2.put("forWhat", "help");
        webAppUtil.startWebAppActivity(context, hashMap, hashMap2);
    }

    public static void startSpreadHelp(Context context) {
        if (!initSuccess()) {
            LogUtil.m3786e("金蟾获取系统参数失败");
            return;
        }
        HashMap<String, String> hashMap = new HashMap<>();
        HashMap<String, String> hashMap2 = new HashMap<>();
        addInitParam(hashMap, hashMap2, true);
        hashMap2.put("forWhat", "help");
        webAppUtil.startWebAppActivity(context, hashMap, hashMap2);
    }
}
