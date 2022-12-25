package okhttp3.internal.http;

import com.gen.p059mh.webapp_extensions.fragments.MainFragment;
import java.io.IOException;
import java.net.ProtocolException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.connection.StreamAllocation;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/* loaded from: classes4.dex */
public final class CallServerInterceptor implements Interceptor {
    private final boolean forWebSocket;

    public CallServerInterceptor(boolean z) {
        this.forWebSocket = z;
    }

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Response build;
        RealInterceptorChain realInterceptorChain = (RealInterceptorChain) chain;
        HttpCodec httpStream = realInterceptorChain.httpStream();
        StreamAllocation streamAllocation = realInterceptorChain.streamAllocation();
        RealConnection realConnection = (RealConnection) realInterceptorChain.connection();
        Request request = realInterceptorChain.request();
        long currentTimeMillis = System.currentTimeMillis();
        realInterceptorChain.eventListener().requestHeadersStart(realInterceptorChain.call());
        httpStream.writeRequestHeaders(request);
        realInterceptorChain.eventListener().requestHeadersEnd(realInterceptorChain.call(), request);
        Response.Builder builder = null;
        if (HttpMethod.permitsRequestBody(request.method()) && request.body() != null) {
            if ("100-continue".equalsIgnoreCase(request.header("Expect"))) {
                httpStream.flushRequest();
                realInterceptorChain.eventListener().responseHeadersStart(realInterceptorChain.call());
                builder = httpStream.readResponseHeaders(true);
            }
            if (builder == null) {
                realInterceptorChain.eventListener().requestBodyStart(realInterceptorChain.call());
                CountingSink countingSink = new CountingSink(httpStream.createRequestBody(request, request.body().contentLength()));
                BufferedSink buffer = Okio.buffer(countingSink);
                request.body().writeTo(buffer);
                buffer.close();
                realInterceptorChain.eventListener().requestBodyEnd(realInterceptorChain.call(), countingSink.successfulCount);
            } else if (!realConnection.isMultiplexed()) {
                streamAllocation.noNewStreams();
            }
        }
        httpStream.finishRequest();
        if (builder == null) {
            realInterceptorChain.eventListener().responseHeadersStart(realInterceptorChain.call());
            builder = httpStream.readResponseHeaders(false);
        }
        builder.request(request);
        builder.handshake(streamAllocation.connection().handshake());
        builder.sentRequestAtMillis(currentTimeMillis);
        builder.receivedResponseAtMillis(System.currentTimeMillis());
        Response build2 = builder.build();
        int code = build2.code();
        if (code == 100) {
            Response.Builder readResponseHeaders = httpStream.readResponseHeaders(false);
            readResponseHeaders.request(request);
            readResponseHeaders.handshake(streamAllocation.connection().handshake());
            readResponseHeaders.sentRequestAtMillis(currentTimeMillis);
            readResponseHeaders.receivedResponseAtMillis(System.currentTimeMillis());
            build2 = readResponseHeaders.build();
            code = build2.code();
        }
        realInterceptorChain.eventListener().responseHeadersEnd(realInterceptorChain.call(), build2);
        if (this.forWebSocket && code == 101) {
            Response.Builder newBuilder = build2.newBuilder();
            newBuilder.body(Util.EMPTY_RESPONSE);
            build = newBuilder.build();
        } else {
            Response.Builder newBuilder2 = build2.newBuilder();
            newBuilder2.body(httpStream.openResponseBody(build2));
            build = newBuilder2.build();
        }
        if (MainFragment.CLOSE_EVENT.equalsIgnoreCase(build.request().header("Connection")) || MainFragment.CLOSE_EVENT.equalsIgnoreCase(build.header("Connection"))) {
            streamAllocation.noNewStreams();
        }
        if ((code == 204 || code == 205) && build.body().contentLength() > 0) {
            throw new ProtocolException("HTTP " + code + " had non-zero Content-Length: " + build.body().contentLength());
        }
        return build;
    }

    /* loaded from: classes4.dex */
    static final class CountingSink extends ForwardingSink {
        long successfulCount;

        CountingSink(Sink sink) {
            super(sink);
        }

        @Override // okio.ForwardingSink, okio.Sink
        public void write(Buffer buffer, long j) throws IOException {
            super.write(buffer, j);
            this.successfulCount += j;
        }
    }
}
