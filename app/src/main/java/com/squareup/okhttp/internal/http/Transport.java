package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.net.CacheRequest;
import okio.Sink;
import okio.Source;

/* loaded from: classes3.dex */
public interface Transport {
    boolean canReuseConnection();

    Sink createRequestBody(Request request) throws IOException;

    void disconnect(HttpEngine httpEngine) throws IOException;

    void emptyTransferStream() throws IOException;

    void flushRequest() throws IOException;

    Source getTransferStream(CacheRequest cacheRequest) throws IOException;

    Response.Builder readResponseHeaders() throws IOException;

    void releaseConnectionOnIdle() throws IOException;

    void writeRequestBody(RetryableSink retryableSink) throws IOException;

    void writeRequestHeaders(Request request) throws IOException;
}
