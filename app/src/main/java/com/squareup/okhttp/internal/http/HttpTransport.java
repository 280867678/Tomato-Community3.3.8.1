package com.squareup.okhttp.internal.http;

import com.gen.p059mh.webapp_extensions.fragments.MainFragment;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.net.CacheRequest;
import okio.Sink;
import okio.Source;

/* loaded from: classes3.dex */
public final class HttpTransport implements Transport {
    private final HttpConnection httpConnection;
    private final HttpEngine httpEngine;

    public HttpTransport(HttpEngine httpEngine, HttpConnection httpConnection) {
        this.httpEngine = httpEngine;
        this.httpConnection = httpConnection;
    }

    @Override // com.squareup.okhttp.internal.http.Transport
    public Sink createRequestBody(Request request) throws IOException {
        long contentLength = OkHeaders.contentLength(request);
        if (this.httpEngine.bufferRequestBody) {
            if (contentLength > 2147483647L) {
                throw new IllegalStateException("Use setFixedLengthStreamingMode() or setChunkedStreamingMode() for requests larger than 2 GiB.");
            }
            if (contentLength != -1) {
                writeRequestHeaders(request);
                return new RetryableSink((int) contentLength);
            }
            return new RetryableSink();
        } else if ("chunked".equalsIgnoreCase(request.header("Transfer-Encoding"))) {
            writeRequestHeaders(request);
            return this.httpConnection.newChunkedSink();
        } else if (contentLength != -1) {
            writeRequestHeaders(request);
            return this.httpConnection.newFixedLengthSink(contentLength);
        } else {
            throw new IllegalStateException("Cannot stream a request body without chunked encoding or a known content length!");
        }
    }

    @Override // com.squareup.okhttp.internal.http.Transport
    public void flushRequest() throws IOException {
        this.httpConnection.flush();
    }

    @Override // com.squareup.okhttp.internal.http.Transport
    public void writeRequestBody(RetryableSink retryableSink) throws IOException {
        this.httpConnection.writeRequestBody(retryableSink);
    }

    @Override // com.squareup.okhttp.internal.http.Transport
    public void writeRequestHeaders(Request request) throws IOException {
        this.httpEngine.writingRequestHeaders();
        this.httpConnection.writeRequest(request.headers(), RequestLine.get(request, this.httpEngine.getConnection().getRoute().getProxy().type(), this.httpEngine.getConnection().getProtocol()));
    }

    @Override // com.squareup.okhttp.internal.http.Transport
    public Response.Builder readResponseHeaders() throws IOException {
        return this.httpConnection.readResponse();
    }

    @Override // com.squareup.okhttp.internal.http.Transport
    public void releaseConnectionOnIdle() throws IOException {
        if (canReuseConnection()) {
            this.httpConnection.poolOnIdle();
        } else {
            this.httpConnection.closeOnIdle();
        }
    }

    @Override // com.squareup.okhttp.internal.http.Transport
    public boolean canReuseConnection() {
        return !MainFragment.CLOSE_EVENT.equalsIgnoreCase(this.httpEngine.getRequest().header("Connection")) && !MainFragment.CLOSE_EVENT.equalsIgnoreCase(this.httpEngine.getResponse().header("Connection")) && !this.httpConnection.isClosed();
    }

    @Override // com.squareup.okhttp.internal.http.Transport
    public void emptyTransferStream() throws IOException {
        this.httpConnection.emptyResponseBody();
    }

    @Override // com.squareup.okhttp.internal.http.Transport
    public Source getTransferStream(CacheRequest cacheRequest) throws IOException {
        if (!this.httpEngine.hasResponseBody()) {
            return this.httpConnection.newFixedLengthSource(cacheRequest, 0L);
        }
        if ("chunked".equalsIgnoreCase(this.httpEngine.getResponse().header("Transfer-Encoding"))) {
            return this.httpConnection.newChunkedSource(cacheRequest, this.httpEngine);
        }
        long contentLength = OkHeaders.contentLength(this.httpEngine.getResponse());
        if (contentLength != -1) {
            return this.httpConnection.newFixedLengthSource(cacheRequest, contentLength);
        }
        return this.httpConnection.newUnknownLengthSource(cacheRequest);
    }

    @Override // com.squareup.okhttp.internal.http.Transport
    public void disconnect(HttpEngine httpEngine) throws IOException {
        this.httpConnection.closeIfOwnedBy(httpEngine);
    }
}
