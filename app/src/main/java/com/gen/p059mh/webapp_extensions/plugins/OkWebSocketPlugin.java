package com.gen.p059mh.webapp_extensions.plugins;

import android.support.p002v4.app.NotificationCompat;
import android.util.Log;
import com.gen.p059mh.webapp_extensions.fragments.MainFragment;
import com.gen.p059mh.webapp_extensions.utils.EncryptUtils;
import com.gen.p059mh.webapp_extensions.websocket.WsManager;
import com.gen.p059mh.webapps.Plugin;
import com.google.gson.Gson;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.tomatolive.library.utils.LogConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/* renamed from: com.gen.mh.webapp_extensions.plugins.OkWebSocketPlugin */
/* loaded from: classes2.dex */
public class OkWebSocketPlugin extends Plugin {
    public static Map<Integer, WsManager> socketMap = new HashMap();
    public static int webSocketId = 100001;
    public List<Integer> currentSocketList = new ArrayList();

    public OkWebSocketPlugin() {
        super("websocket");
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void process(String str, Plugin.PluginCallback pluginCallback) {
        char c;
        Map map = (Map) new Gson().fromJson(str, (Class<Object>) Map.class);
        String valueOf = String.valueOf(map.get(LogConstants.FOLLOW_OPERATION_TYPE));
        int hashCode = valueOf.hashCode();
        if (hashCode == -1352294148) {
            if (valueOf.equals("create")) {
                c = 0;
            }
            c = 65535;
        } else if (hashCode != 3526536) {
            if (hashCode == 94756344 && valueOf.equals(MainFragment.CLOSE_EVENT)) {
                c = 2;
            }
            c = 65535;
        } else {
            if (valueOf.equals("send")) {
                c = 1;
            }
            c = 65535;
        }
        if (c != 0) {
            if (c == 1) {
                sendMessage(map, pluginCallback);
                return;
            } else if (c != 2) {
                return;
            } else {
                closeWebSocket(map, pluginCallback);
                return;
            }
        }
        try {
            createWebSocket(map, pluginCallback);
        } catch (Exception e) {
            e.printStackTrace();
            new HashMap().put("success", false);
            pluginCallback.response(null);
        }
    }

    private void closeWebSocket(Map map, Plugin.PluginCallback pluginCallback) {
        if (map.get(DatabaseFieldConfigLoader.FIELD_NAME_ID) != null && !"".equals(map.get(DatabaseFieldConfigLoader.FIELD_NAME_ID))) {
            int intValue = ((Number) map.get(DatabaseFieldConfigLoader.FIELD_NAME_ID)).intValue();
            if (socketMap.containsKey(Integer.valueOf(intValue))) {
                socketMap.get(Integer.valueOf(intValue)).stopConnect(((Number) map.get("code")).intValue(), map.get("reason") != null ? String.valueOf(map.get("reason")) : null);
                socketMap.remove(Integer.valueOf(intValue));
                if (this.currentSocketList.contains(Integer.valueOf(intValue))) {
                    this.currentSocketList.remove(intValue);
                }
            }
        }
        HashMap hashMap = new HashMap();
        hashMap.put("success", true);
        pluginCallback.response(hashMap);
    }

    private void sendMessage(Map map, Plugin.PluginCallback pluginCallback) {
        if (map.get(DatabaseFieldConfigLoader.FIELD_NAME_ID) != null && !"".equals(map.get(DatabaseFieldConfigLoader.FIELD_NAME_ID))) {
            int intValue = ((Number) map.get(DatabaseFieldConfigLoader.FIELD_NAME_ID)).intValue();
            if (socketMap.containsKey(Integer.valueOf(intValue))) {
                WsManager wsManager = socketMap.get(Integer.valueOf(intValue));
                if (map.get("dataType").equals("buffer")) {
                    wsManager.sendMessage(ByteString.m70of(EncryptUtils.base64Decode(String.valueOf(map.get(AopConstants.APP_PROPERTIES_KEY)).getBytes())));
                } else {
                    wsManager.sendMessage(String.valueOf(map.get(AopConstants.APP_PROPERTIES_KEY)));
                }
            }
        }
        HashMap hashMap = new HashMap();
        hashMap.put("success", true);
        pluginCallback.response(hashMap);
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void unload() {
        super.unload();
        List<Integer> list = this.currentSocketList;
        if (list != null && list.size() > 0) {
            for (Integer num : this.currentSocketList) {
                if (socketMap.containsKey(num)) {
                    socketMap.get(num).stopConnect(1000, null);
                    socketMap.remove(num);
                }
            }
        }
        this.currentSocketList.clear();
    }

    private void createWebSocket(final Map map, Plugin.PluginCallback pluginCallback) {
        int i = webSocketId;
        webSocketId = i + 1;
        this.currentSocketList.add(Integer.valueOf(i));
        WsManager.Builder builder = new WsManager.Builder(getWebViewFragment().getContext());
        OkHttpClient.Builder newBuilder = new OkHttpClient().newBuilder();
        newBuilder.pingInterval(15L, TimeUnit.SECONDS);
        newBuilder.retryOnConnectionFailure(true);
        builder.setmOkHttpClient(newBuilder.build());
        builder.setNeedReconnect(true);
        builder.setWsUrl(String.valueOf(map.get("url")));
        final WsManager build = builder.build();
        build.setmWebSocketListener(new WebSocketListener() { // from class: com.gen.mh.webapp_extensions.plugins.OkWebSocketPlugin.1
            @Override // okhttp3.WebSocketListener
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
                build.setmWebSocket(webSocket);
                build.setCurrentStatus(1);
                build.connected();
                HashMap hashMap = new HashMap();
                hashMap.put("type", "open");
                OkWebSocketPlugin.this.executor.executeEvent(String.valueOf(map.get("handler_key")), hashMap, null);
            }

            @Override // okhttp3.WebSocketListener
            public void onMessage(WebSocket webSocket, String str) {
                super.onMessage(webSocket, str);
                HashMap hashMap = new HashMap();
                hashMap.put("type", "message");
                hashMap.put("dataType", "string");
                hashMap.put(AopConstants.APP_PROPERTIES_KEY, str);
                OkWebSocketPlugin.this.executor.executeEvent(String.valueOf(map.get("handler_key")), hashMap, null);
            }

            @Override // okhttp3.WebSocketListener
            public void onMessage(WebSocket webSocket, ByteString byteString) {
                super.onMessage(webSocket, byteString);
                HashMap hashMap = new HashMap();
                hashMap.put("type", "message");
                hashMap.put("dataType", "buffer");
                hashMap.put(AopConstants.APP_PROPERTIES_KEY, new String(EncryptUtils.base64Encode(byteString.toByteArray())));
                OkWebSocketPlugin.this.executor.executeEvent(String.valueOf(map.get("handler_key")), hashMap, null);
            }

            @Override // okhttp3.WebSocketListener
            public void onClosing(WebSocket webSocket, int i2, String str) {
                super.onClosing(webSocket, i2, str);
                Log.i("socketClient", "onClosing: --" + i2 + " 数据json--" + str);
            }

            @Override // okhttp3.WebSocketListener
            public void onClosed(WebSocket webSocket, int i2, String str) {
                super.onClosed(webSocket, i2, str);
                HashMap hashMap = new HashMap();
                hashMap.put("type", MainFragment.CLOSE_EVENT);
                hashMap.put("code", Integer.valueOf(i2));
                OkWebSocketPlugin.this.executor.executeEvent(String.valueOf(map.get("handler_key")), hashMap, null);
            }

            @Override // okhttp3.WebSocketListener
            public void onFailure(WebSocket webSocket, Throwable th, Response response) {
                super.onFailure(webSocket, th, response);
                HashMap hashMap = new HashMap();
                hashMap.put("type", "error");
                hashMap.put(NotificationCompat.CATEGORY_MESSAGE, th.getLocalizedMessage());
                OkWebSocketPlugin.this.executor.executeEvent(String.valueOf(map.get("handler_key")), hashMap, null);
            }
        });
        build.startConnect();
        socketMap.put(Integer.valueOf(i), build);
        if (map.get("protocols") != null) {
            List list = (List) map.get("protocols");
        }
        if (map.get("headers") != null) {
            List list2 = (List) map.get("headers");
        }
        HashMap hashMap = new HashMap();
        hashMap.put("success", true);
        hashMap.put(DatabaseFieldConfigLoader.FIELD_NAME_ID, Integer.valueOf(i));
        pluginCallback.response(hashMap);
    }
}
