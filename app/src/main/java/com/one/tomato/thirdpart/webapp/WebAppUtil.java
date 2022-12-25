package com.one.tomato.thirdpart.webapp;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import com.gen.p059mh.webapp_extensions.WebApplication;
import com.gen.p059mh.webapp_extensions.activities.WebAppActivity;
import com.gen.p059mh.webapp_extensions.fragments.MainFragment;
import com.gen.p059mh.webapp_extensions.matisse.engine.impl.GlideEngine;
import com.gen.p059mh.webapps.webEngine.impl.DefaultWebEngine;
import com.one.tomato.utils.LogUtil;
import java.util.HashMap;

/* loaded from: classes3.dex */
public class WebAppUtil {
    public static void init(Application application) {
        WebApplication.getInstance().init(application).setDebug(false).setWebViewDebug(false).setImageEngine(new GlideEngine()).setWebEngine(new DefaultWebEngine());
    }

    public void startWebAppActivity(Context context, HashMap<String, String> hashMap, HashMap<String, String> hashMap2) {
        LogUtil.m3784i("点击跳转小程序固定传参：webAppMap = " + hashMap.toString());
        MainFragment.setup(hashMap);
        MainFragment.setApiHost(hashMap.get("host"));
        Intent intent = new Intent(context, WebAppActivity.class);
        intent.putExtra(WebAppActivity.FRAGMENT_CLASS_KEY, WebFragment.class.getName());
        intent.putExtra(WebAppActivity.APP_ID_KEY, hashMap.get("alias"));
        if (hashMap2 != null && !hashMap2.isEmpty()) {
            intent.putExtra(WebAppActivity.INIT_PARAMS, hashMap2);
            LogUtil.m3784i("点击跳转小程序自定义传参：paramsMap = " + hashMap2.toString());
        }
        context.startActivity(intent);
    }

    public void startWebGameActivity(Context context, HashMap<String, String> hashMap, boolean z, String str) {
        LogUtil.m3784i("点击跳转小程序固定传参：webAppMap = " + hashMap.toString());
        LogUtil.m3784i("点击跳转小程序H5游戏：是否是横屏 = " + z + "，url = " + str);
        MainFragment.setup(hashMap);
        MainFragment.setApiHost(hashMap.get("host"));
        Intent intent = new Intent(context, WebAppActivity.class);
        intent.putExtra(WebAppActivity.FRAGMENT_CLASS_KEY, WebFragment.class.getName());
        intent.putExtra(WebAppActivity.IS_ON_LINE, true);
        intent.putExtra(WebAppActivity.WORK_PATH_KEY, str);
        HashMap hashMap2 = new HashMap();
        HashMap hashMap3 = new HashMap();
        hashMap3.put("pageOrientation", z ? "landscape" : "portrait");
        hashMap3.put("keyboardAppear", "resize");
        hashMap3.put("statusBarHidden", "true");
        hashMap2.put("window", hashMap3);
        intent.putExtra(WebAppActivity.ON_LINE_CONFIG, hashMap2);
        context.startActivity(intent);
    }
}
