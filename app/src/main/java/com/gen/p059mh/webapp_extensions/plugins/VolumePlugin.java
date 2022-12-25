package com.gen.p059mh.webapp_extensions.plugins;

import android.media.AudioManager;
import android.support.p002v4.app.NotificationCompat;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.utils.Logger;
import com.p076mh.webappStart.util.GsonUtil;
import com.tomatolive.library.utils.LogConstants;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.gen.mh.webapp_extensions.plugins.VolumePlugin */
/* loaded from: classes2.dex */
public class VolumePlugin extends Plugin {
    AudioManager mAudioManager;

    public VolumePlugin() {
        super("volume");
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void process(String str, Plugin.PluginCallback pluginCallback) {
        Logger.m4113i(str);
        Map map = (Map) GsonUtil.getInstance().fromJson(str, (Class<Object>) Map.class);
        if (map.get(LogConstants.FOLLOW_OPERATION_TYPE) != null) {
            String valueOf = String.valueOf(map.get(LogConstants.FOLLOW_OPERATION_TYPE));
            char c = 65535;
            int hashCode = valueOf.hashCode();
            if (hashCode != 102230) {
                if (hashCode == 113762 && valueOf.equals("set")) {
                    c = 1;
                }
            } else if (valueOf.equals("get")) {
                c = 0;
            }
            if (c == 0) {
                getVolume(pluginCallback);
                return;
            } else if (c == 1) {
                setVolume(map, pluginCallback);
                return;
            } else {
                HashMap hashMap = new HashMap();
                hashMap.put("success", false);
                pluginCallback.response(hashMap);
                return;
            }
        }
        HashMap hashMap2 = new HashMap();
        hashMap2.put("success", false);
        pluginCallback.response(hashMap2);
    }

    private void managerInit() {
        if (this.mAudioManager == null) {
            this.mAudioManager = (AudioManager) getWebViewFragment().getContext().getSystemService("audio");
        }
    }

    private void getVolume(Plugin.PluginCallback pluginCallback) {
        managerInit();
        float streamVolume = this.mAudioManager.getStreamVolume(3) / this.mAudioManager.getStreamMaxVolume(3);
        HashMap hashMap = new HashMap();
        hashMap.put("success", true);
        hashMap.put("volume", Float.valueOf(streamVolume));
        pluginCallback.response(hashMap);
    }

    private void setVolume(Map map, Plugin.PluginCallback pluginCallback) {
        managerInit();
        if (map.get("volume") != null) {
            float floatValue = ((Number) map.get("volume")).floatValue();
            Logger.m4113i(Float.valueOf(floatValue));
            int streamMaxVolume = (int) (this.mAudioManager.getStreamMaxVolume(3) * floatValue);
            Logger.m4113i(Integer.valueOf(streamMaxVolume));
            this.mAudioManager.setStreamVolume(3, streamMaxVolume, 1);
            HashMap hashMap = new HashMap();
            hashMap.put("success", true);
            pluginCallback.response(hashMap);
            return;
        }
        HashMap hashMap2 = new HashMap();
        hashMap2.put("success", false);
        hashMap2.put(NotificationCompat.CATEGORY_MESSAGE, "volume data error");
        pluginCallback.response(hashMap2);
    }
}
