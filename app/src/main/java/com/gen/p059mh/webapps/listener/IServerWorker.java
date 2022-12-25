package com.gen.p059mh.webapps.listener;

import fi.iki.elonen.NanoHTTPD;
import java.io.File;
import java.util.Map;

/* renamed from: com.gen.mh.webapps.listener.IServerWorker */
/* loaded from: classes2.dex */
public interface IServerWorker {

    /* renamed from: com.gen.mh.webapps.listener.IServerWorker$-CC  reason: invalid class name */
    /* loaded from: classes2.dex */
    public final /* synthetic */ class CC {
        public static void $default$setServerType(IServerWorker iServerWorker, String str) {
        }

        public static void $default$start(IServerWorker iServerWorker, File file) {
        }
    }

    boolean checkHandle(String str, Map map);

    void destroyWorker();

    void setProxyPath(String str);

    void setServerType(String str);

    void start(File file);

    NanoHTTPD.Response startProxy(String str, NanoHTTPD.IHTTPSession iHTTPSession, Map map);
}
