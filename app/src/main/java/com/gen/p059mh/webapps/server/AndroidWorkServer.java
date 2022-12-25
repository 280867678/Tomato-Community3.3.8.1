package com.gen.p059mh.webapps.server;

import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.gen.p059mh.webapps.pugins.ServerPlugin;
import com.gen.p059mh.webapps.utils.ResourcesLoader;
import com.gen.p059mh.webapps.utils.Utils;
import com.github.amr.mimetypes.MimeType;
import com.github.amr.mimetypes.MimeTypes;
import fi.iki.elonen.NanoHTTPD;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/* renamed from: com.gen.mh.webapps.server.AndroidWorkServer */
/* loaded from: classes2.dex */
public class AndroidWorkServer extends NanoHTTPD {
    IWebFragmentController webViewFragment;

    public AndroidWorkServer(int i, IWebFragmentController iWebFragmentController) {
        super(i);
        this.webViewFragment = iWebFragmentController;
    }

    public AndroidWorkServer(String str, int i, IWebFragmentController iWebFragmentController) {
        super(str, i);
        this.webViewFragment = iWebFragmentController;
    }

    @Override // fi.iki.elonen.NanoHTTPD
    public NanoHTTPD.Response serve(NanoHTTPD.IHTTPSession iHTTPSession) {
        byte[] loadData;
        MimeType byExtension = MimeTypes.getInstance().getByExtension(Utils.getExtension(iHTTPSession.getUri()));
        String mimeType = byExtension == null ? "application/stream" : byExtension.getMimeType();
        iHTTPSession.getHeaders();
        ServerPlugin serverPlugin = this.webViewFragment.getServerPlugin();
        StringBuilder sb = new StringBuilder();
        sb.append(getHostname() == null ? "127.0.0.1" : getHostname());
        sb.append(":");
        sb.append(getListeningPort());
        NanoHTTPD.Response handleServer = serverPlugin.handleServer(sb.toString(), iHTTPSession, mimeType);
        if (handleServer != null) {
            return handleServer;
        }
        if (iHTTPSession.getUri().contains("/_res_")) {
            loadData = Utils.loadData((this.webViewFragment.getAppFilesDir() + iHTTPSession.getUri()).replace("/_res_", "").replace(this.webViewFragment.getWorkHost(), ""), Utils.ENCODE_TYPE.NORMAL, null);
        } else if (iHTTPSession.getUri().contains("/_tmp_")) {
            loadData = Utils.loadData((this.webViewFragment.getTempDir() + iHTTPSession.getUri()).replace("/_tmp_", "").replace(this.webViewFragment.getWorkHost(), ""), Utils.ENCODE_TYPE.NORMAL, null);
        } else {
            loadData = Utils.loadData((this.webViewFragment.getWorkPath() + iHTTPSession.getUri()).replace(this.webViewFragment.getWorkHost(), ""), Utils.ENCODE_TYPE.WORK, this.webViewFragment.getWACrypto());
        }
        if (loadData == null) {
            return NanoHTTPD.newChunkedResponse(NanoHTTPD.Response.Status.OK, mimeType, new ByteArrayInputStream(new byte[0]));
        }
        if (mimeType.contains("text/html") && !this.webViewFragment.isOnline()) {
            loadData = html(loadData);
        }
        return NanoHTTPD.newChunkedResponse(NanoHTTPD.Response.Status.OK, mimeType, new ByteArrayInputStream(loadData));
    }

    public byte[] html(byte[] bArr) {
        String defaultsPath = this.webViewFragment.getDefaultsPath();
        ResourcesLoader.ResourceType resourceType = this.webViewFragment.getResourceType();
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        final ArrayList arrayList = new ArrayList();
        ResourcesLoader.ListHandler listHandler = new ResourcesLoader.ListHandler(this) { // from class: com.gen.mh.webapps.server.AndroidWorkServer.1
            @Override // com.gen.p059mh.webapps.utils.ResourcesLoader.ListHandler
            public void onList(File file, boolean z) {
                if (!z || arrayList.contains(file)) {
                    return;
                }
                arrayList.add(file);
            }
        };
        ResourcesLoader.listAllFile(new File(defaultsPath + "/android"), listHandler);
        ResourcesLoader.listAllFile(new File(defaultsPath + "/web"), listHandler);
        ResourcesLoader.listAllFile(new File(defaultsPath), listHandler);
        if (!this.webViewFragment.isAem()) {
            ResourcesLoader.listAllFile(new File(defaultsPath + "/api"), listHandler);
        }
        int size = resourceType.getDirs().size();
        for (int i = 0; i < size; i++) {
            ResourcesLoader.listAllFile(new File(defaultsPath + "/" + resourceType.getDirs().get(i)), listHandler);
        }
        Collections.sort(arrayList, new Comparator<File>(this) { // from class: com.gen.mh.webapps.server.AndroidWorkServer.2
            @Override // java.util.Comparator
            public int compare(File file, File file2) {
                return file.getName().compareTo(file2.getName());
            }
        });
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            File file = (File) it2.next();
            if (Utils.hasSuffix(file.getAbsolutePath(), ".pre.js")) {
                sb.append("<script type=\"application/javascript\" src=\"http://" + ResourcesLoader.DEFAULTS_HOST + file.getAbsolutePath().replace(defaultsPath, "") + "\"></script>\n");
            } else {
                sb2.append("<script type=\"application/javascript\" src=\"http://" + ResourcesLoader.DEFAULTS_HOST + file.getAbsolutePath().replace(defaultsPath, "") + "\"></script>\n");
            }
        }
        return Utils.inject(new String(bArr), sb.toString(), sb2.toString()).getBytes();
    }
}
