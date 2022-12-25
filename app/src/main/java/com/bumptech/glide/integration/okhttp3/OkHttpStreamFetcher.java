package com.bumptech.glide.integration.okhttp3;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.util.ContentLengthInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/* loaded from: classes2.dex */
public class OkHttpStreamFetcher implements DataFetcher<InputStream> {
    private volatile Call call;
    private final Call.Factory client;
    private ResponseBody responseBody;
    private InputStream stream;
    private final GlideUrl url;

    public OkHttpStreamFetcher(Call.Factory factory, GlideUrl glideUrl) {
        this.client = factory;
        this.url = glideUrl;
    }

    /* renamed from: loadData */
    public InputStream m5878loadData(Priority priority) throws Exception {
        Request.Builder builder = new Request.Builder();
        builder.url(this.url.toStringUrl());
        for (Map.Entry<String, String> entry : this.url.getHeaders().entrySet()) {
            builder.addHeader(entry.getKey(), entry.getValue());
        }
        this.call = this.client.newCall(builder.build());
        Response execute = this.call.execute();
        this.responseBody = execute.body();
        if (!execute.isSuccessful()) {
            throw new IOException("Request failed with code: " + execute.code());
        }
        this.stream = ContentLengthInputStream.obtain(this.responseBody.byteStream(), this.responseBody.contentLength());
        return this.stream;
    }

    @Override // com.bumptech.glide.load.data.DataFetcher
    public void cleanup() {
        try {
            if (this.stream != null) {
                this.stream.close();
            }
        } catch (IOException unused) {
        }
        ResponseBody responseBody = this.responseBody;
        if (responseBody != null) {
            responseBody.close();
        }
    }

    public String getId() {
        return this.url.getCacheKey();
    }

    @Override // com.bumptech.glide.load.data.DataFetcher
    public void cancel() {
        Call call = this.call;
        if (call != null) {
            call.cancel();
        }
    }
}
