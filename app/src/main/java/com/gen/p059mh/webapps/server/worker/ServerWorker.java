package com.gen.p059mh.webapps.server.worker;

import android.support.p002v4.app.NotificationCompat;
import android.util.Base64;
import com.eclipsesource.p056v8.C1257V8;
import com.eclipsesource.p056v8.Releasable;
import com.eclipsesource.p056v8.V8Array;
import com.eclipsesource.p056v8.V8Object;
import com.eclipsesource.p056v8.V8Value;
import com.eclipsesource.p056v8.utils.V8ObjectUtils;
import com.gen.p059mh.webapps.listener.IServerWorker;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.gen.p059mh.webapps.server.runtime.V8BaseRuntime;
import com.gen.p059mh.webapps.utils.Logger;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import fi.iki.elonen.NanoHTTPD;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/* renamed from: com.gen.mh.webapps.server.worker.ServerWorker */
/* loaded from: classes2.dex */
public class ServerWorker extends Worker implements IServerWorker {
    V8Object obj;
    String proxyPath = "proxyPath";
    public String function = "(function process(res) {\n    function Server(server) {\n        for (var key in server) {\n            this[key] = server[key];\n        }\n        this._request = function(req, rRes) {\n            var res = {\n                setHeader: function (key, value) {\n                    rRes.setHeader(key, value)\n                },\n                send: function (options) {\n                    var data = options.data;\n                    if (data === null || data === undefined) {\n                        options.data = null;\n                    }else if (typeof data === 'object' && data instanceof ArrayBuffer) {\n                        options.data = {\n                            type: 'arraybuffer',\n                            content: wx.buffer.Buffer.from(data).toString('base64')\n                        };\n                    }else {\n                        options.data = {\n                            type: 'text',\n                            content: data.toString()\n                        }\n                    }\n                    rRes.send(options);\n                },\n                error: function(code, msg) {\n                    rRes.error(code, msg);\n                }\n            };\n            try {\n                this.request(req, res);\n            }catch (e) {\n                rRes.error(500, e.message);\n            }\n        }\n    }\n    return new Server(res);\n})";

    @Override // com.gen.p059mh.webapps.listener.IServerWorker
    public /* synthetic */ void setServerType(String str) {
        IServerWorker.CC.$default$setServerType(this, str);
    }

    public String getProxyPath() {
        return this.proxyPath;
    }

    public ServerWorker(IWebFragmentController iWebFragmentController) {
        super(iWebFragmentController);
    }

    @Override // com.gen.p059mh.webapps.server.runtime.V8BaseRuntime, com.gen.p059mh.webapps.server.runtime.V8Lifecycle
    public void start(final File file) {
        this.handler.post(new Runnable() { // from class: com.gen.mh.webapps.server.worker.ServerWorker.1
            @Override // java.lang.Runnable
            public void run() {
                ((V8BaseRuntime) ServerWorker.this).runtime.executeScript("var module = {exports: {}}");
                if (file != null) {
                    C1257V8 c1257v8 = ((V8BaseRuntime) ServerWorker.this).runtime;
                    c1257v8.executeScript("var scriptSrc ='" + file.getAbsolutePath() + "'");
                    ServerWorker.this.initDefault();
                    ServerWorker.this.executeFromPath(file.getAbsolutePath());
                }
                V8Object executeObjectScript = ((V8BaseRuntime) ServerWorker.this).runtime.executeObjectScript(ServerWorker.this.function);
                V8Object executeObjectScript2 = ((V8BaseRuntime) ServerWorker.this).runtime.executeObjectScript("module.exports");
                V8Array push = new V8Array(((V8BaseRuntime) ServerWorker.this).runtime).push((V8Value) ((V8BaseRuntime) ServerWorker.this).runtime).push((V8Value) executeObjectScript2);
                ServerWorker.this.obj = executeObjectScript.executeObjectFunction(NotificationCompat.CATEGORY_CALL, push);
                ((V8BaseRuntime) ServerWorker.this).runtime.executeScript("delete module");
                push.close();
                executeObjectScript.close();
                executeObjectScript2.close();
            }
        });
    }

    @Override // com.gen.p059mh.webapps.listener.IServerWorker
    public NanoHTTPD.Response startProxy(final String str, final NanoHTTPD.IHTTPSession iHTTPSession, final Map map) {
        byte[] bArr;
        final HashMap hashMap = new HashMap();
        final HashMap hashMap2 = new HashMap();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        this.handler.post(new Runnable() { // from class: com.gen.mh.webapps.server.worker.ServerWorker.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    URL url = new URL("http://" + str + iHTTPSession.getUri() + "?" + iHTTPSession.getQueryParameterString());
                    V8Array v8Array = new V8Array(((V8BaseRuntime) ServerWorker.this).runtime);
                    V8Object v8Object = new V8Object(((V8BaseRuntime) ServerWorker.this).runtime);
                    V8Object v8Object2 = new V8Object(((V8BaseRuntime) ServerWorker.this).runtime);
                    v8Object2.add("path", url.getPath());
                    v8Object2.add("url", url.toString());
                    v8Object2.add("method", iHTTPSession.getMethod().toString());
                    V8Object v8Object3 = V8ObjectUtils.toV8Object(((V8BaseRuntime) ServerWorker.this).runtime, map);
                    v8Object2.add("params", v8Object3);
                    v8Object3.close();
                    V8Object v8Object4 = V8ObjectUtils.toV8Object(((V8BaseRuntime) ServerWorker.this).runtime, iHTTPSession.getParms());
                    v8Object2.add("query", v8Object4);
                    v8Object4.close();
                    V8Object v8Object5 = V8ObjectUtils.toV8Object(((V8BaseRuntime) ServerWorker.this).runtime, iHTTPSession.getHeaders());
                    v8Object2.add("headers", v8Object5);
                    v8Object5.close();
                    Object obj = new Object() { // from class: com.gen.mh.webapps.server.worker.ServerWorker.2.1
                        public void setHeader(String str2, String str3) {
                            hashMap2.put(str2, str3);
                        }

                        public void send(Object obj2) {
                            V8Object v8Object6 = (V8Object) obj2;
                            V8Object v8Object7 = (V8Object) v8Object6.get(AopConstants.APP_PROPERTIES_KEY);
                            Object obj3 = v8Object7.get("type");
                            Object obj4 = v8Object7.get("content");
                            Object obj5 = v8Object6.get("mimeType");
                            hashMap.put("type", obj3);
                            hashMap.put(AopConstants.APP_PROPERTIES_KEY, obj4);
                            hashMap.put("mimeType", obj5.toString());
                            if (obj5 instanceof Releasable) {
                                ((Releasable) obj5).close();
                            }
                            if (obj4 instanceof Releasable) {
                                ((Releasable) obj4).close();
                            }
                            if (obj3 instanceof Releasable) {
                                ((Releasable) obj3).close();
                            }
                            v8Object7.close();
                            v8Object6.close();
                            countDownLatch.countDown();
                        }

                        public void error(Integer num, String str2) {
                            Logger.m4112i("error", num + "_" + str2);
                        }
                    };
                    v8Object.registerJavaMethod(obj, "setHeader", "setHeader", new Class[]{String.class, String.class});
                    v8Object.registerJavaMethod(obj, "send", "send", new Class[]{Object.class});
                    v8Object.registerJavaMethod(obj, "error", "error", new Class[]{Integer.class, String.class});
                    v8Array.push((V8Value) v8Object2).push((V8Value) v8Object);
                    ServerWorker.this.obj.executeObjectFunction("_request", v8Array);
                    if (obj instanceof Releasable) {
                        ((Releasable) obj).close();
                    }
                    v8Object.release();
                    v8Object2.release();
                    v8Array.release();
                } catch (Error e) {
                    e.printStackTrace();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (hashMap.get(AopConstants.APP_PROPERTIES_KEY) != null) {
            String str2 = (String) hashMap.get("type");
            char c = 65535;
            int hashCode = str2.hashCode();
            if (hashCode != 3556653) {
                if (hashCode == 1154818009 && str2.equals("arraybuffer")) {
                    c = 0;
                }
            } else if (str2.equals("text")) {
                c = 1;
            }
            if (c == 0) {
                bArr = Base64.decode((String) hashMap.get(AopConstants.APP_PROPERTIES_KEY), 0);
            } else {
                bArr = c != 1 ? new byte[0] : ((String) hashMap.get(AopConstants.APP_PROPERTIES_KEY)).getBytes();
            }
        } else {
            bArr = new byte[0];
        }
        NanoHTTPD.Response newFixedLengthResponse = NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.OK, (String) hashMap.get("mimeType"), new ByteArrayInputStream(bArr), bArr.length);
        for (String str3 : hashMap2.keySet()) {
            newFixedLengthResponse.addHeader(str3, (String) hashMap2.get(str3));
        }
        hashMap.clear();
        hashMap2.clear();
        return newFixedLengthResponse;
    }

    @Override // com.gen.p059mh.webapps.server.runtime.V8BaseRuntime, com.gen.p059mh.webapps.server.runtime.V8Lifecycle
    public void destroyWorker() {
        this.handler.post(new Runnable() { // from class: com.gen.mh.webapps.server.worker.ServerWorker.3
            @Override // java.lang.Runnable
            public void run() {
                V8Object v8Object = ServerWorker.this.obj;
                if (v8Object != null) {
                    try {
                        if (v8Object.isReleased()) {
                            return;
                        }
                        ServerWorker.this.obj.close();
                    } catch (Error e) {
                        e.printStackTrace();
                    } catch (RuntimeException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        });
        super.destroyWorker();
    }

    @Override // com.gen.p059mh.webapps.listener.IServerWorker
    public void setProxyPath(String str) {
        this.proxyPath = str;
    }

    @Override // com.gen.p059mh.webapps.listener.IServerWorker
    public boolean checkHandle(String str, Map map) {
        String[] split = str.split("/");
        String[] split2 = this.proxyPath.split("/");
        if (split2.length == split.length) {
            for (int i = 0; i < split2.length; i++) {
                String str2 = split2[i];
                String str3 = split[i];
                if (str2.length() > 0 && str2.charAt(0) == ':') {
                    map.put(str2.substring(1), str3);
                } else if (!str2.equals(str3)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
