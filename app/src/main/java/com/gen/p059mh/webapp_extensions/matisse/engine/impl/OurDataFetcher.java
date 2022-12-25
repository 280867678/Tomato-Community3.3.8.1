package com.gen.p059mh.webapp_extensions.matisse.engine.impl;

import android.support.annotation.NonNull;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.HttpException;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GlideUrl;
import com.gen.p059mh.webapps.listener.RequestResultListener;
import com.gen.p059mh.webapps.utils.Request;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Map;

/* renamed from: com.gen.mh.webapp_extensions.matisse.engine.impl.OurDataFetcher */
/* loaded from: classes2.dex */
class OurDataFetcher implements DataFetcher<InputStream> {
    private DataFetcher.DataCallback<? super InputStream> callback;
    RequestResultListener listener = new RequestResultListener() { // from class: com.gen.mh.webapp_extensions.matisse.engine.impl.OurDataFetcher.1
        @Override // com.gen.p059mh.webapps.listener.RequestResultListener
        protected void onComplete(Request.Response response, byte[] bArr) {
            if (response.statusCode == 200) {
                OurDataFetcher.this.stream = new ByteArrayInputStream(bArr);
                if (OurDataFetcher.this.callback == null) {
                    return;
                }
                OurDataFetcher.this.callback.onDataReady(OurDataFetcher.this.stream);
            }
        }

        @Override // com.gen.p059mh.webapps.utils.Request.RequestListener
        public void onFail(int i, String str) {
            if (OurDataFetcher.this.callback != null) {
                OurDataFetcher.this.callback.onLoadFailed(new HttpException(str, i));
            }
        }
    };
    Request request;
    private InputStream stream;
    private final GlideUrl url;

    public OurDataFetcher(GlideUrl glideUrl) {
        this.url = glideUrl;
    }

    @Override // com.bumptech.glide.load.data.DataFetcher
    public void loadData(@NonNull Priority priority, @NonNull DataFetcher.DataCallback<? super InputStream> dataCallback) {
        this.request = new Request();
        for (Map.Entry<String, String> entry : this.url.getHeaders().entrySet()) {
            this.request.setRequestHeaders(entry.getKey(), entry.getValue());
        }
        try {
            this.request.setUrl(this.url.toURL());
            this.request.setRequestListener(this.listener);
            this.request.start();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.callback = dataCallback;
    }

    @Override // com.bumptech.glide.load.data.DataFetcher
    public void cleanup() {
        try {
            if (this.stream != null) {
                this.stream.close();
            }
        } catch (IOException unused) {
        }
        this.callback = null;
    }

    @Override // com.bumptech.glide.load.data.DataFetcher
    public void cancel() {
        Request request = this.request;
        if (request != null) {
            request.cancel();
        }
    }

    @Override // com.bumptech.glide.load.data.DataFetcher
    @NonNull
    public Class<InputStream> getDataClass() {
        return InputStream.class;
    }

    @Override // com.bumptech.glide.load.data.DataFetcher
    @NonNull
    public DataSource getDataSource() {
        return DataSource.REMOTE;
    }
}
