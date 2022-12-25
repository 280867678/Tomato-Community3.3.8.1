package com.gen.p059mh.webapps.server;

import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.gen.p059mh.webapps.utils.Utils;
import com.github.amr.mimetypes.MimeType;
import com.github.amr.mimetypes.MimeTypes;
import fi.iki.elonen.NanoHTTPD;
import java.io.ByteArrayInputStream;

/* renamed from: com.gen.mh.webapps.server.AndroidDefaultServer */
/* loaded from: classes2.dex */
public class AndroidDefaultServer extends NanoHTTPD {
    public static String THE_DEFAULTS_HOST = "/defaultshost";
    IWebFragmentController webViewFragment;

    public AndroidDefaultServer(String str, int i, IWebFragmentController iWebFragmentController) {
        super(str, i);
        this.webViewFragment = iWebFragmentController;
    }

    @Override // fi.iki.elonen.NanoHTTPD
    public NanoHTTPD.Response serve(NanoHTTPD.IHTTPSession iHTTPSession) {
        MimeType byExtension = MimeTypes.getInstance().getByExtension(Utils.getExtension(iHTTPSession.getUri()));
        String mimeType = byExtension == null ? "application/stream" : byExtension.getMimeType();
        byte[] loadData = Utils.loadData((this.webViewFragment.getDefaultsPath() + iHTTPSession.getUri()).replace(THE_DEFAULTS_HOST, ""), Utils.ENCODE_TYPE.DEFAULT, this.webViewFragment.getWACrypto());
        if (loadData == null) {
            loadData = new byte[0];
        }
        return NanoHTTPD.newChunkedResponse(NanoHTTPD.Response.Status.OK, mimeType, new ByteArrayInputStream(loadData));
    }
}
