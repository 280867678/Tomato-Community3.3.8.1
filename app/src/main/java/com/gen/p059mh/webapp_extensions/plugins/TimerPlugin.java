package com.gen.p059mh.webapp_extensions.plugins;

import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.utils.Logger;
import com.google.gson.Gson;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.tomatolive.library.utils.LogConstants;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

/* renamed from: com.gen.mh.webapp_extensions.plugins.TimerPlugin */
/* loaded from: classes2.dex */
public class TimerPlugin extends Plugin {
    public static int timerID = 100001;
    public static Map<Integer, Map> timerMap = new HashMap();

    public TimerPlugin() {
        super("timeout");
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void process(String str, Plugin.PluginCallback pluginCallback) {
        Logger.m4113i("input" + str);
        Map map = (Map) new Gson().fromJson(str, (Class<Object>) Map.class);
        if (map == null || map.get(LogConstants.FOLLOW_OPERATION_TYPE) == null) {
            return;
        }
        String str2 = (String) map.get(LogConstants.FOLLOW_OPERATION_TYPE);
        char c = 65535;
        int hashCode = str2.hashCode();
        if (hashCode != -1367724422) {
            if (hashCode == 3641717 && str2.equals("wait")) {
                c = 0;
            }
        } else if (str2.equals("cancel")) {
            c = 1;
        }
        if (c == 0) {
            createTimer(map, pluginCallback);
        } else if (c == 1) {
            cancelTimer(map, pluginCallback);
        } else {
            HashMap hashMap = new HashMap();
            hashMap.put("success", false);
            pluginCallback.response(hashMap);
        }
    }

    private void createTimer(Map map, Plugin.PluginCallback pluginCallback) {
        Timer timer = new Timer();
        long longValue = ((Number) (map.get(AopConstants.TIME_KEY) == null ? 0 : map.get(AopConstants.TIME_KEY))).longValue();
        String valueOf = String.valueOf(map.get("handler_key"));
        if (((Boolean) map.get("repeat")).booleanValue()) {
            timer.schedule(new TimerTask(valueOf, true), longValue, longValue);
        } else {
            timer.schedule(new TimerTask(valueOf, false), longValue);
        }
        int i = timerID;
        timerID = i + 1;
        HashMap hashMap = new HashMap();
        hashMap.put("handler_key", valueOf);
        hashMap.put("timer", timer);
        timerMap.put(Integer.valueOf(i), hashMap);
        HashMap hashMap2 = new HashMap();
        hashMap2.put(DatabaseFieldConfigLoader.FIELD_NAME_ID, Integer.valueOf(i));
        hashMap2.put("success", true);
        pluginCallback.response(hashMap2);
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void unload() {
        super.unload();
        for (Integer num : timerMap.keySet()) {
            ((Timer) timerMap.get(num).get("timer")).cancel();
        }
        timerMap.clear();
    }

    /* renamed from: com.gen.mh.webapp_extensions.plugins.TimerPlugin$TimerTask */
    /* loaded from: classes2.dex */
    public class TimerTask extends java.util.TimerTask {
        boolean isRepeat;
        String name;

        public TimerTask(String str, boolean z) {
            this.name = str;
            this.isRepeat = z;
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            Logger.m4113i(this.name + ":" + new Date().getTime());
            HashMap hashMap = new HashMap();
            hashMap.put("type", AopConstants.TIME_KEY);
            TimerPlugin.this.executor.executeEvent(this.name, hashMap, null);
            if (!this.isRepeat) {
                HashMap hashMap2 = new HashMap();
                hashMap.put("type", "invalidate");
                TimerPlugin.this.executor.executeEvent(this.name, hashMap2, null);
            }
        }
    }

    private void cancelTimer(Map map, Plugin.PluginCallback pluginCallback) {
        int intValue = ((Number) map.get(DatabaseFieldConfigLoader.FIELD_NAME_ID)).intValue();
        if (timerMap.containsKey(Integer.valueOf(intValue))) {
            ((Timer) timerMap.get(Integer.valueOf(intValue)).get("timer")).cancel();
            HashMap hashMap = new HashMap();
            map.put("type", "invalidate");
            this.executor.executeEvent((String) timerMap.get(Integer.valueOf(intValue)).get("handler_key"), hashMap, null);
        }
        HashMap hashMap2 = new HashMap();
        hashMap2.put("success", true);
        pluginCallback.response(hashMap2);
    }
}
