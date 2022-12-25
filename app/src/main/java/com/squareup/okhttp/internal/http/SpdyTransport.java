package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.spdy.ErrorCode;
import com.squareup.okhttp.internal.spdy.Header;
import com.squareup.okhttp.internal.spdy.SpdyConnection;
import com.squareup.okhttp.internal.spdy.SpdyStream;
import com.tomatolive.library.utils.ConstantUtils;
import java.io.IOException;
import java.io.OutputStream;
import java.net.CacheRequest;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import okio.Buffer;
import okio.ByteString;
import okio.Sink;
import okio.Source;
import okio.Timeout;

/* loaded from: classes3.dex */
public final class SpdyTransport implements Transport {
    private final HttpEngine httpEngine;
    private final SpdyConnection spdyConnection;
    private SpdyStream stream;
    private static final List<ByteString> SPDY_3_PROHIBITED_HEADERS = Util.immutableList(ByteString.encodeUtf8("connection"), ByteString.encodeUtf8("host"), ByteString.encodeUtf8("keep-alive"), ByteString.encodeUtf8("proxy-connection"), ByteString.encodeUtf8("transfer-encoding"));
    private static final List<ByteString> HTTP_2_PROHIBITED_HEADERS = Util.immutableList(ByteString.encodeUtf8("connection"), ByteString.encodeUtf8("host"), ByteString.encodeUtf8("keep-alive"), ByteString.encodeUtf8("proxy-connection"), ByteString.encodeUtf8("te"), ByteString.encodeUtf8("transfer-encoding"), ByteString.encodeUtf8("encoding"), ByteString.encodeUtf8("upgrade"));

    @Override // com.squareup.okhttp.internal.http.Transport
    public boolean canReuseConnection() {
        return true;
    }

    @Override // com.squareup.okhttp.internal.http.Transport
    public void emptyTransferStream() {
    }

    @Override // com.squareup.okhttp.internal.http.Transport
    public void releaseConnectionOnIdle() {
    }

    public SpdyTransport(HttpEngine httpEngine, SpdyConnection spdyConnection) {
        this.httpEngine = httpEngine;
        this.spdyConnection = spdyConnection;
    }

    @Override // com.squareup.okhttp.internal.http.Transport
    public Sink createRequestBody(Request request) throws IOException {
        writeRequestHeaders(request);
        return this.stream.getSink();
    }

    @Override // com.squareup.okhttp.internal.http.Transport
    public void writeRequestHeaders(Request request) throws IOException {
        if (this.stream != null) {
            return;
        }
        this.httpEngine.writingRequestHeaders();
        boolean hasRequestBody = this.httpEngine.hasRequestBody();
        String version = RequestLine.version(this.httpEngine.getConnection().getProtocol());
        SpdyConnection spdyConnection = this.spdyConnection;
        this.stream = spdyConnection.newStream(writeNameValueBlock(request, spdyConnection.getProtocol(), version), hasRequestBody, true);
        this.stream.readTimeout().timeout(this.httpEngine.client.getReadTimeout(), TimeUnit.MILLISECONDS);
    }

    @Override // com.squareup.okhttp.internal.http.Transport
    public void writeRequestBody(RetryableSink retryableSink) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override // com.squareup.okhttp.internal.http.Transport
    public void flushRequest() throws IOException {
        this.stream.getSink().close();
    }

    @Override // com.squareup.okhttp.internal.http.Transport
    public Response.Builder readResponseHeaders() throws IOException {
        return readNameValueBlock(this.stream.getResponseHeaders(), this.spdyConnection.getProtocol());
    }

    public static List<Header> writeNameValueBlock(Request request, Protocol protocol, String str) {
        Headers headers = request.headers();
        ArrayList arrayList = new ArrayList(headers.size() + 10);
        arrayList.add(new Header(Header.TARGET_METHOD, request.method()));
        arrayList.add(new Header(Header.TARGET_PATH, RequestLine.requestPath(request.url())));
        String hostHeader = HttpEngine.hostHeader(request.url());
        if (Protocol.SPDY_3 == protocol) {
            arrayList.add(new Header(Header.VERSION, str));
            arrayList.add(new Header(Header.TARGET_HOST, hostHeader));
        } else if (Protocol.HTTP_2 == protocol) {
            arrayList.add(new Header(Header.TARGET_AUTHORITY, hostHeader));
        } else {
            throw new AssertionError();
        }
        arrayList.add(new Header(Header.TARGET_SCHEME, request.url().getProtocol()));
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (int i = 0; i < headers.size(); i++) {
            ByteString encodeUtf8 = ByteString.encodeUtf8(headers.name(i).toLowerCase(Locale.US));
            String value = headers.value(i);
            if (!isProhibitedHeader(protocol, encodeUtf8) && !encodeUtf8.equals(Header.TARGET_METHOD) && !encodeUtf8.equals(Header.TARGET_PATH) && !encodeUtf8.equals(Header.TARGET_SCHEME) && !encodeUtf8.equals(Header.TARGET_AUTHORITY) && !encodeUtf8.equals(Header.TARGET_HOST) && !encodeUtf8.equals(Header.VERSION)) {
                if (linkedHashSet.add(encodeUtf8)) {
                    arrayList.add(new Header(encodeUtf8, value));
                } else {
                    int i2 = 0;
                    while (true) {
                        if (i2 >= arrayList.size()) {
                            break;
                        } else if (((Header) arrayList.get(i2)).name.equals(encodeUtf8)) {
                            arrayList.set(i2, new Header(encodeUtf8, joinOnNull(((Header) arrayList.get(i2)).value.utf8(), value)));
                            break;
                        } else {
                            i2++;
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    private static String joinOnNull(String str, String str2) {
        return str + (char) 0 + str2;
    }

    public static Response.Builder readNameValueBlock(List<Header> list, Protocol protocol) throws IOException {
        Headers.Builder builder = new Headers.Builder();
        builder.set(OkHeaders.SELECTED_PROTOCOL, protocol.toString());
        String str = "HTTP/1.1";
        String str2 = null;
        int i = 0;
        while (i < list.size()) {
            ByteString byteString = list.get(i).name;
            String utf8 = list.get(i).value.utf8();
            String str3 = str;
            String str4 = str2;
            int i2 = 0;
            while (i2 < utf8.length()) {
                int indexOf = utf8.indexOf(0, i2);
                if (indexOf == -1) {
                    indexOf = utf8.length();
                }
                String substring = utf8.substring(i2, indexOf);
                if (byteString.equals(Header.RESPONSE_STATUS)) {
                    str4 = substring;
                } else if (byteString.equals(Header.VERSION)) {
                    str3 = substring;
                } else if (!isProhibitedHeader(protocol, byteString)) {
                    builder.add(byteString.utf8(), substring);
                }
                i2 = indexOf + 1;
            }
            i++;
            str2 = str4;
            str = str3;
        }
        if (str2 != null) {
            if (str == null) {
                throw new ProtocolException("Expected ':version' header not present");
            }
            StatusLine parse = StatusLine.parse(str + ConstantUtils.PLACEHOLDER_STR_ONE + str2);
            Response.Builder builder2 = new Response.Builder();
            builder2.protocol(parse.protocol);
            builder2.code(parse.code);
            builder2.message(parse.message);
            builder2.headers(builder.build());
            return builder2;
        }
        throw new ProtocolException("Expected ':status' header not present");
    }

    @Override // com.squareup.okhttp.internal.http.Transport
    public Source getTransferStream(CacheRequest cacheRequest) throws IOException {
        return new SpdySource(this.stream, cacheRequest);
    }

    @Override // com.squareup.okhttp.internal.http.Transport
    public void disconnect(HttpEngine httpEngine) throws IOException {
        this.stream.close(ErrorCode.CANCEL);
    }

    private static boolean isProhibitedHeader(Protocol protocol, ByteString byteString) {
        if (protocol == Protocol.SPDY_3) {
            return SPDY_3_PROHIBITED_HEADERS.contains(byteString);
        }
        if (protocol == Protocol.HTTP_2) {
            return HTTP_2_PROHIBITED_HEADERS.contains(byteString);
        }
        throw new AssertionError(protocol);
    }

    /* loaded from: classes3.dex */
    private static class SpdySource implements Source {
        private final OutputStream cacheBody;
        private final CacheRequest cacheRequest;
        private boolean closed;
        private boolean inputExhausted;
        private final Source source;
        private final SpdyStream stream;

        SpdySource(SpdyStream spdyStream, CacheRequest cacheRequest) throws IOException {
            this.stream = spdyStream;
            this.source = spdyStream.getSource();
            CacheRequest cacheRequest2 = null;
            OutputStream body = cacheRequest != null ? cacheRequest.getBody() : null;
            cacheRequest2 = body != null ? cacheRequest : cacheRequest2;
            this.cacheBody = body;
            this.cacheRequest = cacheRequest2;
        }

        @Override // okio.Source
        public long read(Buffer buffer, long j) throws IOException {
            if (j < 0) {
                throw new IllegalArgumentException("byteCount < 0: " + j);
            } else if (this.closed) {
                throw new IllegalStateException("closed");
            } else {
                if (this.inputExhausted) {
                    return -1L;
                }
                long read = this.source.read(buffer, j);
                if (read == -1) {
                    this.inputExhausted = true;
                    if (this.cacheRequest != null) {
                        this.cacheBody.close();
                    }
                    return -1L;
                }
                OutputStream outputStream = this.cacheBody;
                if (outputStream != null) {
                    buffer.copyTo(outputStream, buffer.size() - read, read);
                }
                return read;
            }
        }

        @Override // okio.Source
        public Timeout timeout() {
            return this.source.timeout();
        }

        @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            if (this.closed) {
                return;
            }
            if (!this.inputExhausted && this.cacheBody != null) {
                discardStream();
            }
            this.closed = true;
            if (this.inputExhausted) {
                return;
            }
            this.stream.closeLater(ErrorCode.CANCEL);
            CacheRequest cacheRequest = this.cacheRequest;
            if (cacheRequest == null) {
                return;
            }
            cacheRequest.abort();
        }

        private boolean discardStream() {
            boolean z;
            long timeoutNanos = this.stream.readTimeout().timeoutNanos();
            this.stream.readTimeout().timeout(100L, TimeUnit.MILLISECONDS);
            try {
                Util.skipAll(this, 100);
                z = true;
            } catch (IOException unused) {
                z = false;
            } catch (Throwable th) {
                this.stream.readTimeout().timeout(timeoutNanos, TimeUnit.NANOSECONDS);
                throw th;
            }
            this.stream.readTimeout().timeout(timeoutNanos, TimeUnit.NANOSECONDS);
            return z;
        }
    }
}
