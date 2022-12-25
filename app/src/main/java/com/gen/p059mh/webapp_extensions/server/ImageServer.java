package com.gen.p059mh.webapp_extensions.server;

import com.gen.p059mh.webapps.listener.IServerWorker;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.gen.p059mh.webapps.utils.Utils;
import fi.iki.elonen.NanoHTTPD;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Map;

/* renamed from: com.gen.mh.webapp_extensions.server.ImageServer */
/* loaded from: classes2.dex */
public class ImageServer implements IServerWorker {
    String proxyPath;
    String serverType = "";
    IWebFragmentController webViewFragment;

    @Override // com.gen.p059mh.webapps.listener.IServerWorker
    public void destroyWorker() {
    }

    @Override // com.gen.p059mh.webapps.listener.IServerWorker
    public /* synthetic */ void start(File file) {
        IServerWorker.CC.$default$start(this, file);
    }

    public ImageServer(IWebFragmentController iWebFragmentController) {
        this.webViewFragment = iWebFragmentController;
    }

    @Override // com.gen.p059mh.webapps.listener.IServerWorker
    public void setProxyPath(String str) {
        this.proxyPath = str;
    }

    @Override // com.gen.p059mh.webapps.listener.IServerWorker
    public void setServerType(String str) {
        this.serverType = str;
    }

    @Override // com.gen.p059mh.webapps.listener.IServerWorker
    public NanoHTTPD.Response startProxy(String str, NanoHTTPD.IHTTPSession iHTTPSession, Map map) {
        byte[] downloadImage = Utils.downloadImage(this.webViewFragment.getContext(), iHTTPSession.getParameters().get("url").get(0), this.serverType.equals("IMAGE_DECODER"));
        return NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.OK, map.get("mimeType") == null ? "image/png" : map.get("mimeType").toString(), new ByteArrayInputStream(downloadImage), downloadImage.length);
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
