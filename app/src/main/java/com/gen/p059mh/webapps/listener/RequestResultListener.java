package com.gen.p059mh.webapps.listener;

import com.gen.p059mh.webapps.utils.Request;

/* renamed from: com.gen.mh.webapps.listener.RequestResultListener */
/* loaded from: classes2.dex */
public abstract class RequestResultListener implements Request.RequestListener {
    Request.Response response;

    protected abstract void onComplete(Request.Response response, byte[] bArr);

    @Override // com.gen.p059mh.webapps.utils.Request.RequestListener
    public void onProgress(long j, long j2) {
    }

    @Override // com.gen.p059mh.webapps.utils.Request.RequestListener
    public boolean onReceiveResponse(Request.Response response) {
        this.response = response;
        return true;
    }

    @Override // com.gen.p059mh.webapps.utils.Request.RequestListener
    public void onComplete(int i, byte[] bArr) {
        onComplete(this.response, bArr);
    }
}
