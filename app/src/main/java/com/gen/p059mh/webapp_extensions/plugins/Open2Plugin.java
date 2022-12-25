package com.gen.p059mh.webapp_extensions.plugins;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.p002v4.app.NotificationCompat;
import android.widget.Toast;
import com.gen.p059mh.webapp_extensions.activities.OpenWebActivity;
import com.gen.p059mh.webapp_extensions.fragments.MainFragment;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.WebViewLaunchFragment;
import com.gen.p059mh.webapps.listener.JSResponseListener;
import com.gen.p059mh.webapps.utils.Logger;
import com.google.gson.Gson;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.tomatolive.library.utils.LogConstants;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.gen.mh.webapp_extensions.plugins.Open2Plugin */
/* loaded from: classes2.dex */
public class Open2Plugin extends Plugin {
    public static int Open2PluginId = 1;
    public static Map<Integer, Open2Plugin> openerMap = new HashMap();
    public int currentId;
    public int webOpenId;

    private void close(Map<String, Object> map, Plugin.PluginCallback pluginCallback) {
    }

    public Open2Plugin() {
        super("open2");
        int i = Open2PluginId + 1;
        Open2PluginId = i;
        this.currentId = i;
        Logger.m4112i("Open2Plugin", Integer.valueOf(this.currentId));
        openerMap.put(Integer.valueOf(this.currentId), this);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0098, code lost:
        if (r3.equals("open") != false) goto L8;
     */
    @Override // com.gen.p059mh.webapps.Plugin
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void process(String str, Plugin.PluginCallback pluginCallback) {
        Logger.m4114e("Open2Plugin id:" + this.webViewFragment.getAppID(), str);
        HashMap<String, Object> hashMap = (HashMap) new Gson().fromJson(str, (Class<Object>) HashMap.class);
        char c = 0;
        if (hashMap != null) {
            String str2 = (String) hashMap.get(LogConstants.FOLLOW_OPERATION_TYPE);
            switch (str2.hashCode()) {
                case 3417674:
                    break;
                case 3556498:
                    if (str2.equals("test")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 94756344:
                    if (str2.equals(MainFragment.CLOSE_EVENT)) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 1490029383:
                    if (str2.equals("postMessage")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 1609155497:
                    if (str2.equals("testOpener")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1671672458:
                    if (str2.equals("dismiss")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 1671764162:
                    if (str2.equals("display")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 1807816594:
                    if (str2.equals("postToOpener")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    openWebApp(hashMap, pluginCallback);
                    return;
                case 1:
                    hasOpener(pluginCallback);
                    return;
                case 2:
                    test(hashMap, pluginCallback);
                    return;
                case 3:
                    postMessage(hashMap, pluginCallback);
                    return;
                case 4:
                    postToOpener(hashMap, pluginCallback);
                    return;
                case 5:
                    close(hashMap, pluginCallback);
                    return;
                case 6:
                    display(hashMap, pluginCallback);
                    return;
                case 7:
                    dismiss(hashMap, pluginCallback);
                    return;
                default:
                    HashMap hashMap2 = new HashMap();
                    hashMap2.put("success", false);
                    hashMap2.put(NotificationCompat.CATEGORY_MESSAGE, "can not find the action");
                    pluginCallback.response(hashMap2);
                    return;
            }
        }
        HashMap hashMap3 = new HashMap();
        hashMap3.put("success", false);
        pluginCallback.response(hashMap3);
    }

    private void display(Map<String, Object> map, Plugin.PluginCallback pluginCallback) {
        if (map.containsKey(DatabaseFieldConfigLoader.FIELD_NAME_ID) && openerMap.containsKey(Integer.valueOf(((Number) map.get(DatabaseFieldConfigLoader.FIELD_NAME_ID)).intValue()))) {
            doDisplay(openerMap.get(Integer.valueOf(((Number) map.get(DatabaseFieldConfigLoader.FIELD_NAME_ID)).intValue())).getWebViewFragment().getFragment());
            HashMap hashMap = new HashMap();
            hashMap.put("success", false);
            pluginCallback.response(hashMap);
            return;
        }
        HashMap hashMap2 = new HashMap();
        hashMap2.put("success", false);
        pluginCallback.response(hashMap2);
    }

    private void dismiss(Map<String, Object> map, Plugin.PluginCallback pluginCallback) {
        if (map.containsKey(DatabaseFieldConfigLoader.FIELD_NAME_ID) && openerMap.containsKey(Integer.valueOf(((Number) map.get(DatabaseFieldConfigLoader.FIELD_NAME_ID)).intValue()))) {
            openerMap.get(Integer.valueOf(((Number) map.get(DatabaseFieldConfigLoader.FIELD_NAME_ID)).intValue())).doDismiss(getWebViewFragment().getFragment());
            HashMap hashMap = new HashMap();
            hashMap.put("success", false);
            pluginCallback.response(hashMap);
            return;
        }
        HashMap hashMap2 = new HashMap();
        hashMap2.put("success", false);
        pluginCallback.response(hashMap2);
    }

    private void doDismiss(WebViewLaunchFragment webViewLaunchFragment) {
        getWebViewFragment().dismiss(webViewLaunchFragment);
    }

    private void doDisplay(WebViewLaunchFragment webViewLaunchFragment) {
        getWebViewFragment().display(webViewLaunchFragment);
    }

    private void test(Map map, Plugin.PluginCallback pluginCallback) {
        String str = (String) map.get("url");
        boolean z = true;
        if (str.startsWith("wapp:")) {
            HashMap hashMap = new HashMap();
            hashMap.put("success", true);
            hashMap.put("result", true);
            pluginCallback.response(hashMap);
        } else if (str.startsWith("http")) {
            if (new Intent("android.intent.action.VIEW", Uri.parse(str)).resolveActivity(getWebViewFragment().getContext().getPackageManager()) == null) {
                z = false;
            }
            HashMap hashMap2 = new HashMap();
            hashMap2.put("success", true);
            hashMap2.put("result", Boolean.valueOf(z));
            pluginCallback.response(hashMap2);
        } else {
            HashMap hashMap3 = new HashMap();
            hashMap3.put("success", false);
            hashMap3.put("result", Boolean.valueOf(checkSchemeInstalled(getWebViewFragment().getContext(), str)));
            pluginCallback.response(hashMap3);
        }
    }

    public boolean checkSchemeInstalled(Context context, String str) {
        PackageManager packageManager = context.getPackageManager();
        boolean z = new Intent("android.intent.action.VIEW", Uri.parse(str)).resolveActivity(context.getPackageManager()) != null;
        if (!z) {
            Intent launchIntentForPackage = packageManager.getLaunchIntentForPackage(str);
            if (launchIntentForPackage == null) {
                return false;
            }
            try {
                return launchIntentForPackage.resolveActivity(packageManager) != null;
            } catch (Exception unused) {
                return false;
            }
        }
        return z;
    }

    private void hasOpener(Plugin.PluginCallback pluginCallback) {
        int i = getWebViewFragment().getFragment().getArguments().getInt("webapp_opener_id", -1);
        HashMap hashMap = new HashMap();
        boolean z = true;
        hashMap.put("success", true);
        if (i == -1 || !openerMap.containsKey(Integer.valueOf(i))) {
            z = false;
        }
        hashMap.put("hasOpener", Boolean.valueOf(z));
        pluginCallback.response(hashMap);
    }

    public void postToOpener(Map map, Plugin.PluginCallback pluginCallback) {
        int i = getWebViewFragment().getFragment().getArguments().getInt("webapp_opener_id", -1);
        if (i != -1 && openerMap.containsKey(Integer.valueOf(i))) {
            HashMap hashMap = new HashMap();
            hashMap.put("type", "message");
            hashMap.put("from", "subwindow");
            hashMap.put("content", map.get("content"));
            hashMap.put(DatabaseFieldConfigLoader.FIELD_NAME_ID, Integer.valueOf(this.currentId));
            openerMap.get(Integer.valueOf(i)).executor.executeEvent("open2.event", hashMap, new JSResponseListener(this) { // from class: com.gen.mh.webapp_extensions.plugins.Open2Plugin.1
                @Override // com.gen.p059mh.webapps.listener.JSResponseListener
                public void onResponse(Object obj) {
                    Logger.m4113i(obj);
                }
            });
            HashMap hashMap2 = new HashMap();
            hashMap2.put("success", true);
            pluginCallback.response(hashMap2);
            return;
        }
        HashMap hashMap3 = new HashMap();
        hashMap3.put("success", false);
        hashMap3.put(NotificationCompat.CATEGORY_MESSAGE, "can not find opener");
        pluginCallback.response(hashMap3);
    }

    public void postMessage(Map map, Plugin.PluginCallback pluginCallback) {
        int intValue = ((Number) map.get(DatabaseFieldConfigLoader.FIELD_NAME_ID)).intValue();
        if (intValue != -1 && openerMap.containsKey(Integer.valueOf(intValue))) {
            HashMap hashMap = new HashMap();
            hashMap.put("type", "message");
            hashMap.put("from", "opener");
            hashMap.put("content", map.get("content"));
            hashMap.put(DatabaseFieldConfigLoader.FIELD_NAME_ID, Integer.valueOf(intValue));
            openerMap.get(Integer.valueOf(intValue)).executor.executeEvent("open2.event", hashMap, null);
            HashMap hashMap2 = new HashMap();
            hashMap2.put("success", true);
            pluginCallback.response(hashMap2);
            return;
        }
        HashMap hashMap3 = new HashMap();
        hashMap3.put("success", false);
        hashMap3.put(NotificationCompat.CATEGORY_MESSAGE, "can not find opener");
        pluginCallback.response(hashMap3);
    }

    private void openWebApp(HashMap<String, Object> hashMap, Plugin.PluginCallback pluginCallback) {
        String str = (String) hashMap.get("url");
        boolean z = true;
        if (hashMap.containsKey("app") && ((Boolean) hashMap.get("app")).booleanValue()) {
            HashMap hashMap2 = new HashMap();
            hashMap2.put("success", true);
            hashMap2.put(DatabaseFieldConfigLoader.FIELD_NAME_ID, Integer.valueOf(Open2PluginId + 1));
            pluginCallback.response(hashMap2);
            getWebViewFragment().gotoNewWebApp(hashMap, this.currentId);
        } else if (hashMap.get("androidPackage") != null && ((Boolean) hashMap.get("androidPackage")).booleanValue()) {
            PackageManager packageManager = getWebViewFragment().getContext().getPackageManager();
            final Intent launchIntentForPackage = packageManager.getLaunchIntentForPackage(str);
            if (launchIntentForPackage.resolveActivity(packageManager) == null) {
                z = false;
            }
            if (z) {
                try {
                    checkData(launchIntentForPackage, (Map) hashMap.get("params"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                launchIntentForPackage.addFlags(268435456);
                getWebViewFragment().getActivity().runOnUiThread(new Runnable() { // from class: com.gen.mh.webapp_extensions.plugins.-$$Lambda$Open2Plugin$aKSDRxjN7C60ww0Tkp1VRX86o1g
                    @Override // java.lang.Runnable
                    public final void run() {
                        Open2Plugin.this.lambda$openWebApp$0$Open2Plugin(launchIntentForPackage);
                    }
                });
            } else {
                Toast.makeText(getWebViewFragment().getContext(), "未安装相关应用", 0).show();
            }
            HashMap hashMap3 = new HashMap();
            hashMap3.put("success", Boolean.valueOf(z));
            pluginCallback.response(hashMap3);
        } else if (str.startsWith("wapp:")) {
            Uri parse = Uri.parse(str);
            if (parse == null) {
                return;
            }
            HashMap hashMap4 = new HashMap();
            hashMap4.put("success", true);
            hashMap4.put(DatabaseFieldConfigLoader.FIELD_NAME_ID, Integer.valueOf(Open2PluginId + 1));
            pluginCallback.response(hashMap4);
            getWebViewFragment().gotoNewWebApp(parse, this.currentId);
        } else if (hashMap.get("system") != null && ((Boolean) hashMap.get("system")).booleanValue()) {
            HashMap hashMap5 = new HashMap();
            hashMap5.put("success", true);
            pluginCallback.response(hashMap5);
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(Uri.parse(str));
            getWebViewFragment().getFragment().startActivity(intent);
        } else if (str.startsWith("http")) {
            Intent intent2 = new Intent(getWebViewFragment().getContext(), OpenWebActivity.class);
            intent2.putExtra("open_url", str);
            intent2.putExtra("webapp_opener_id", this.webOpenId);
            intent2.putExtra("open_option", hashMap);
            getWebViewFragment().getFragment().startActivity(intent2);
            HashMap hashMap6 = new HashMap();
            hashMap6.put("success", true);
            hashMap6.put(DatabaseFieldConfigLoader.FIELD_NAME_ID, Integer.valueOf(this.webOpenId));
            pluginCallback.response(hashMap6);
        } else {
            Intent intent3 = new Intent("android.intent.action.VIEW", Uri.parse(str));
            if (intent3.resolveActivity(getWebViewFragment().getContext().getPackageManager()) == null) {
                z = false;
            }
            HashMap hashMap7 = new HashMap();
            hashMap7.put("success", true);
            pluginCallback.response(hashMap7);
            if (z) {
                intent3.addFlags(268435456);
                getWebViewFragment().getFragment().startActivity(intent3);
                return;
            }
            Toast.makeText(getWebViewFragment().getContext(), "未安装相关应用", 0).show();
        }
    }

    public /* synthetic */ void lambda$openWebApp$0$Open2Plugin(Intent intent) {
        getWebViewFragment().getContext().startActivity(intent);
    }

    private void checkData(Intent intent, Map<String, String> map) {
        if (map != null) {
            for (String str : map.keySet()) {
                if (map.get(str) != null) {
                    intent.putExtra(str, map.get(str));
                }
            }
        }
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void unload() {
        super.unload();
        int i = (getWebViewFragment() == null || getWebViewFragment().getFragment() == null || getWebViewFragment().getFragment().getArguments() == null) ? -1 : getWebViewFragment().getFragment().getArguments().getInt("webapp_opener_id", -1);
        openerMap.remove(Integer.valueOf(this.currentId));
        if (i == -1 || !openerMap.containsKey(Integer.valueOf(i))) {
            return;
        }
        HashMap hashMap = new HashMap();
        hashMap.put("type", "unload");
        hashMap.put("from", "subwindow");
        hashMap.put(DatabaseFieldConfigLoader.FIELD_NAME_ID, Integer.valueOf(this.currentId));
        openerMap.get(Integer.valueOf(i)).executor.executeEvent("open2.event", hashMap, null);
    }
}
