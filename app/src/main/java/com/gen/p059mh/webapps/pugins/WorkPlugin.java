package com.gen.p059mh.webapps.pugins;

import android.net.Uri;
import android.support.p002v4.app.NotificationCompat;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.server.worker.Worker;
import com.gen.p059mh.webapps.utils.Logger;
import com.google.gson.Gson;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.tomatolive.library.utils.LogConstants;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.gen.mh.webapps.pugins.WorkPlugin */
/* loaded from: classes2.dex */
public class WorkPlugin extends Plugin {
    public static int workId = 100001;
    public static Map<Integer, WorkerEntity> workerMap = new HashMap();
    Worker worker;

    /* renamed from: com.gen.mh.webapps.pugins.WorkPlugin$WorkerEntity */
    /* loaded from: classes2.dex */
    public static class WorkerEntity {
        public String handlerKey;
        public Worker worker;
    }

    public WorkPlugin() {
        super("worker");
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void process(String str, Plugin.PluginCallback pluginCallback) {
        char c;
        Logger.m4113i(str);
        Map map = (Map) new Gson().fromJson(str, (Class<Object>) Map.class);
        String valueOf = String.valueOf(map.get(LogConstants.FOLLOW_OPERATION_TYPE));
        int hashCode = valueOf.hashCode();
        if (hashCode == -1352294148) {
            if (valueOf.equals("create")) {
                c = 0;
            }
            c = 65535;
        } else if (hashCode != 1490029383) {
            if (hashCode == 2035990113 && valueOf.equals("terminate")) {
                c = 1;
            }
            c = 65535;
        } else {
            if (valueOf.equals("postMessage")) {
                c = 2;
            }
            c = 65535;
        }
        if (c == 0) {
            createWorker(map, pluginCallback);
        } else if (c == 1) {
            terminateWorker(map, pluginCallback);
        } else if (c != 2) {
        } else {
            postMessage(map, pluginCallback);
        }
    }

    private void postMessage(Map map, Plugin.PluginCallback pluginCallback) {
        if (map.get(DatabaseFieldConfigLoader.FIELD_NAME_ID) != null) {
            int intValue = ((Number) map.get(DatabaseFieldConfigLoader.FIELD_NAME_ID)).intValue();
            if (workerMap.containsKey(Integer.valueOf(intValue))) {
                workerMap.get(Integer.valueOf(intValue)).worker.postMessage(workerMap.get(Integer.valueOf(intValue)).handlerKey, (Map) map.get(AopConstants.APP_PROPERTIES_KEY));
                HashMap hashMap = new HashMap();
                hashMap.put("success", true);
                pluginCallback.response(hashMap);
                return;
            }
            HashMap hashMap2 = new HashMap();
            hashMap2.put("success", false);
            hashMap2.put(NotificationCompat.CATEGORY_MESSAGE, "can not find worker with this id");
            pluginCallback.response(hashMap2);
            return;
        }
        HashMap hashMap3 = new HashMap();
        hashMap3.put("success", false);
        hashMap3.put(NotificationCompat.CATEGORY_MESSAGE, "worker_id is empty");
        pluginCallback.response(hashMap3);
    }

    private void createWorker(Map map, Plugin.PluginCallback pluginCallback) {
        int i = workId;
        workId = i + 1;
        if (map.get("src") != null) {
            Uri parse = Uri.parse(String.valueOf(map.get("src")));
            File file = new File(getWebViewFragment().getWorkPath() + parse.getPath());
            if (!file.exists()) {
                return;
            }
            this.worker = new Worker(getWebViewFragment());
            this.worker.start(file);
            WorkerEntity workerEntity = new WorkerEntity();
            workerEntity.handlerKey = map.get("handler_key").toString();
            workerEntity.worker = this.worker;
            workerMap.put(Integer.valueOf(i), workerEntity);
            HashMap hashMap = new HashMap();
            hashMap.put("success", true);
            hashMap.put(DatabaseFieldConfigLoader.FIELD_NAME_ID, Integer.valueOf(i));
            pluginCallback.response(hashMap);
            return;
        }
        HashMap hashMap2 = new HashMap();
        hashMap2.put("success", false);
        pluginCallback.response(hashMap2);
    }

    private void terminateWorker(Map map, Plugin.PluginCallback pluginCallback) {
        if (map.get("worker_id") != null) {
            int intValue = ((Number) map.get("worker_id")).intValue();
            if (workerMap.containsKey(Integer.valueOf(intValue))) {
                workerMap.get(Integer.valueOf(intValue)).worker.destroyWorker();
                HashMap hashMap = new HashMap();
                hashMap.put("success", true);
                pluginCallback.response(hashMap);
                return;
            }
            HashMap hashMap2 = new HashMap();
            hashMap2.put("success", false);
            hashMap2.put(NotificationCompat.CATEGORY_MESSAGE, "can not find worker with this id");
            pluginCallback.response(hashMap2);
            return;
        }
        HashMap hashMap3 = new HashMap();
        hashMap3.put("success", false);
        hashMap3.put(NotificationCompat.CATEGORY_MESSAGE, "worker_id is empty");
        pluginCallback.response(hashMap3);
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void unload() {
        super.unload();
        Worker worker = this.worker;
        if (worker != null) {
            worker.destroyWorker();
        }
    }
}
