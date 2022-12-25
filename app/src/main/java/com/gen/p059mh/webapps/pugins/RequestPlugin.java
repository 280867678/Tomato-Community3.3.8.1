package com.gen.p059mh.webapps.pugins;

import android.support.p002v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Base64;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.utils.Logger;
import com.gen.p059mh.webapps.utils.Request;
import com.gen.p059mh.webapps.utils.ResourcesLoader;
import com.gen.p059mh.webapps.utils.Utils;
import com.google.gson.Gson;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import java.io.ByteArrayInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.gen.mh.webapps.pugins.RequestPlugin */
/* loaded from: classes2.dex */
public class RequestPlugin extends Plugin {
    public static int requestId = 100001;
    public Map<Integer, Request> requestMap = new HashMap();

    public RequestPlugin() {
        super("request");
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void process(String str, Plugin.PluginCallback pluginCallback) {
        byte[] decode;
        byte[] bytes;
        byte[] bytes2;
        byte[] bytes3;
        byte[] decode2;
        Map map = (Map) new Gson().fromJson(str, (Class<Object>) Map.class);
        String str2 = (String) map.get("url");
        Request.Body body = null;
        if (str2 == null) {
            pluginCallback.response(null);
            return;
        }
        String str3 = (String) map.get("method");
        String upperCase = str3 == null ? "GET" : str3.toUpperCase();
        String str4 = (String) map.get("handler_key");
        String str5 = (String) ((Map) map.get("header")).get("Content-Type");
        if (TextUtils.isEmpty(str5)) {
            str5 = "application/x-www-form-urlencoded";
        }
        int intValue = map.get(DatabaseFieldConfigLoader.FIELD_NAME_ID) != null ? ((Number) map.get(DatabaseFieldConfigLoader.FIELD_NAME_ID)).intValue() : 0;
        String str6 = (String) map.get("type");
        String str7 = (String) map.get("dataType");
        if (str7 == null) {
            str7 = "text";
        }
        String str8 = (String) map.get("body");
        if (!upperCase.equals("GET") && str8 != null && !str8.isEmpty()) {
            if (str7.equals("arraybuffer")) {
                body = new Request.Body();
                body.contentType = str5;
                body.inputStream = new ByteArrayInputStream(Base64.decode(str8, 0));
                body.contentLength = decode2.length;
            } else if (str7.equals("text")) {
                body = new Request.Body();
                body.contentType = str5;
                body.inputStream = new ByteArrayInputStream(str8.getBytes());
                body.contentLength = bytes3.length;
            } else if (str7.equals("json")) {
                body = new Request.Body();
                body.contentType = str5;
                body.inputStream = new ByteArrayInputStream(str8.getBytes());
                body.contentLength = bytes2.length;
            } else if (str7.equals("urlencoded")) {
                body = new Request.Body();
                body.contentType = str5;
                body.inputStream = new ByteArrayInputStream(str8.getBytes());
                body.contentLength = bytes.length;
            } else if (str7.equals("formdata")) {
                body = new Request.Body();
                body.contentType = str5;
                body.inputStream = new ByteArrayInputStream(Base64.decode(str8, 0));
                body.contentLength = decode.length;
            }
        }
        Map<String, Object> map2 = (Map) map.get("header");
        int intValue2 = map.get("timeout") != null ? ((Number) map.get("timeout")).intValue() : 0;
        if (str2.startsWith("/")) {
            str2 = "http://" + ResourcesLoader.WORK_HOST + "/" + str2;
        }
        if (str6 == null) {
            createRequest(str2, str4, upperCase, map2, body, pluginCallback, intValue2);
        } else if (!str6.equals("stop") || this.requestMap.get(Integer.valueOf(intValue)) == null) {
        } else {
            this.requestMap.get(Integer.valueOf(intValue)).cancel();
        }
    }

    private void createRequest(String str, final String str2, String str3, Map<String, Object> map, Request.Body body, Plugin.PluginCallback pluginCallback, int i) {
        try {
            final Request request = new Request();
            request.setUrl(new URL(str));
            if (i != 0) {
                request.setTimeout(i);
            }
            request.setMethod(str3);
            if (map != null) {
                for (String str4 : map.keySet()) {
                    if (map.get(str4) instanceof Number) {
                        request.setRequestHeaders(str4, JSONObject.numberToString((Number) map.get(str4)));
                    } else {
                        request.setRequestHeaders(str4, map.get(str4).toString());
                    }
                }
            }
            if (body != null) {
                request.setBody(body);
            }
            request.setRequestListener(new Request.RequestListener() { // from class: com.gen.mh.webapps.pugins.RequestPlugin.1
                Map<String, List<String>> responseHeader;
                String url;

                @Override // com.gen.p059mh.webapps.utils.Request.RequestListener
                public boolean onReceiveResponse(Request.Response response) {
                    this.responseHeader = response.headers;
                    this.url = response.responseUrl.toString();
                    return true;
                }

                @Override // com.gen.p059mh.webapps.utils.Request.RequestListener
                public void onFail(int i2, String str5) {
                    HashMap hashMap = new HashMap();
                    hashMap.put("state", Integer.valueOf(i2));
                    hashMap.put("error", str5);
                    HashMap hashMap2 = new HashMap();
                    hashMap2.put("type", "complete");
                    hashMap2.put(AopConstants.APP_PROPERTIES_KEY, hashMap);
                    RequestPlugin.this.getExecutor().executeEvent(str2, hashMap2, null);
                    RequestPlugin.this.requestMap.remove(request);
                    Logger.m4114e("onFail", i2 + "|" + str5);
                }

                @Override // com.gen.p059mh.webapps.utils.Request.RequestListener
                public void onComplete(int i2, byte[] bArr) {
                    HashMap hashMap = new HashMap();
                    hashMap.put("state", Integer.valueOf(i2));
                    hashMap.put(AopConstants.APP_PROPERTIES_KEY, Base64.encodeToString(bArr, 0));
                    HashMap hashMap2 = new HashMap();
                    Map<String, List<String>> map2 = this.responseHeader;
                    if (map2 != null) {
                        for (String str5 : map2.keySet()) {
                            hashMap2.put(str5, Utils.join(this.responseHeader.get(str5)));
                        }
                    }
                    hashMap.put("headers", hashMap2);
                    hashMap.put("responseURL", this.url);
                    HashMap hashMap3 = new HashMap();
                    hashMap3.put("type", "complete");
                    hashMap3.put(AopConstants.APP_PROPERTIES_KEY, hashMap);
                    RequestPlugin.this.getExecutor().executeEvent(str2, hashMap3, null);
                    RequestPlugin.this.requestMap.remove(request);
                }

                @Override // com.gen.p059mh.webapps.utils.Request.RequestListener
                public void onProgress(long j, long j2) {
                    HashMap hashMap = new HashMap();
                    hashMap.put("loaded", Long.valueOf(j));
                    hashMap.put("total", Long.valueOf(j2));
                    HashMap hashMap2 = new HashMap();
                    hashMap2.put("type", NotificationCompat.CATEGORY_PROGRESS);
                    hashMap2.put(AopConstants.APP_PROPERTIES_KEY, hashMap);
                    RequestPlugin.this.getExecutor().executeEvent(str2, hashMap2, null);
                }
            });
            request.start();
            requestId++;
            HashMap hashMap = new HashMap();
            hashMap.put(DatabaseFieldConfigLoader.FIELD_NAME_ID, Integer.valueOf(requestId));
            pluginCallback.response(hashMap);
            this.requestMap.put(Integer.valueOf(requestId), request);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            pluginCallback.response(null);
        } catch (JSONException e2) {
            e2.printStackTrace();
            pluginCallback.response(null);
        }
    }
}
