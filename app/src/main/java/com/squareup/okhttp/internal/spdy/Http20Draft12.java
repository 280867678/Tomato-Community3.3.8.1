package com.squareup.okhttp.internal.spdy;

import com.squareup.okhttp.internal.spdy.FrameReader;
import com.squareup.okhttp.internal.spdy.HpackDraft07;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.Source;
import okio.Timeout;

/* loaded from: classes3.dex */
public final class Http20Draft12 implements Variant {
    private static final Logger logger = Logger.getLogger(Http20Draft12.class.getName());
    private static final ByteString CONNECTION_PREFACE = ByteString.encodeUtf8("PRI * HTTP/2.0\r\n\r\nSM\r\n\r\n");

    @Override // com.squareup.okhttp.internal.spdy.Variant
    public int maxFrameSize() {
        return 16383;
    }

    static /* synthetic */ IOException access$200(String str, Object[] objArr) throws IOException {
        ioException(str, objArr);
        throw null;
    }

    static /* synthetic */ IllegalArgumentException access$500(String str, Object[] objArr) {
        illegalArgument(str, objArr);
        throw null;
    }

    @Override // com.squareup.okhttp.internal.spdy.Variant
    public FrameReader newReader(BufferedSource bufferedSource, boolean z) {
        return new Reader(bufferedSource, 4096, z);
    }

    @Override // com.squareup.okhttp.internal.spdy.Variant
    public FrameWriter newWriter(BufferedSink bufferedSink, boolean z) {
        return new Writer(bufferedSink, z);
    }

    /* loaded from: classes3.dex */
    static final class Reader implements FrameReader {
        private final boolean client;
        private final ContinuationSource continuation;
        final HpackDraft07.Reader hpackReader;
        private final BufferedSource source;

        Reader(BufferedSource bufferedSource, int i, boolean z) {
            this.source = bufferedSource;
            this.client = z;
            this.continuation = new ContinuationSource(this.source);
            this.hpackReader = new HpackDraft07.Reader(i, this.continuation);
        }

        @Override // com.squareup.okhttp.internal.spdy.FrameReader
        public void readConnectionPreface() throws IOException {
            if (this.client) {
                return;
            }
            ByteString readByteString = this.source.readByteString(Http20Draft12.CONNECTION_PREFACE.size());
            if (Http20Draft12.logger.isLoggable(Level.FINE)) {
                Http20Draft12.logger.fine(String.format("<< CONNECTION %s", readByteString.hex()));
            }
            if (Http20Draft12.CONNECTION_PREFACE.equals(readByteString)) {
                return;
            }
            Http20Draft12.access$200("Expected a connection header but was %s", new Object[]{readByteString.utf8()});
            throw null;
        }

        @Override // com.squareup.okhttp.internal.spdy.FrameReader
        public boolean nextFrame(FrameReader.Handler handler) throws IOException {
            try {
                int readInt = this.source.readInt();
                short s = (short) ((1073676288 & readInt) >> 16);
                byte b = (byte) ((65280 & readInt) >> 8);
                byte b2 = (byte) (readInt & 255);
                int readInt2 = this.source.readInt() & Integer.MAX_VALUE;
                if (Http20Draft12.logger.isLoggable(Level.FINE)) {
                    Http20Draft12.logger.fine(FrameLogger.formatHeader(true, readInt2, s, b, b2));
                }
                switch (b) {
                    case 0:
                        readData(handler, s, b2, readInt2);
                        break;
                    case 1:
                        readHeaders(handler, s, b2, readInt2);
                        break;
                    case 2:
                        readPriority(handler, s, b2, readInt2);
                        break;
                    case 3:
                        readRstStream(handler, s, b2, readInt2);
                        break;
                    case 4:
                        readSettings(handler, s, b2, readInt2);
                        break;
                    case 5:
                        readPushPromise(handler, s, b2, readInt2);
                        break;
                    case 6:
                        readPing(handler, s, b2, readInt2);
                        break;
                    case 7:
                        readGoAway(handler, s, b2, readInt2);
                        break;
                    case 8:
                        readWindowUpdate(handler, s, b2, readInt2);
                        break;
                    case 9:
                    default:
                        Http20Draft12.access$200("PROTOCOL_ERROR: unknown frame type %s", new Object[]{Byte.valueOf(b)});
                        throw null;
                    case 10:
                        readAlternateService(handler, s, b2, readInt2);
                        break;
                    case 11:
                        if (s != 0) {
                            Http20Draft12.access$200("TYPE_BLOCKED length != 0: %s", new Object[]{Short.valueOf(s)});
                            throw null;
                        }
                        break;
                }
                return true;
            } catch (IOException unused) {
                return false;
            }
        }

        private void readHeaders(FrameReader.Handler handler, short s, byte b, int i) throws IOException {
            if (i != 0) {
                boolean z = (b & 1) != 0;
                short readPadding = Http20Draft12.readPadding(this.source, b);
                if ((b & 32) != 0) {
                    readPriority(handler, i);
                    s = (short) (s - 5);
                }
                handler.headers(false, z, i, -1, readHeaderBlock(Http20Draft12.lengthWithoutPadding(s, b, readPadding), readPadding, b, i), HeadersMode.HTTP_20_HEADERS);
                return;
            }
            Http20Draft12.access$200("PROTOCOL_ERROR: TYPE_HEADERS streamId == 0", new Object[0]);
            throw null;
        }

        private List<Header> readHeaderBlock(short s, short s2, byte b, int i) throws IOException {
            ContinuationSource continuationSource = this.continuation;
            continuationSource.left = s;
            continuationSource.length = s;
            continuationSource.padding = s2;
            continuationSource.flags = b;
            continuationSource.streamId = i;
            this.hpackReader.readHeaders();
            this.hpackReader.emitReferenceSet();
            return this.hpackReader.getAndReset();
        }

        private void readData(FrameReader.Handler handler, short s, byte b, int i) throws IOException {
            boolean z = true;
            boolean z2 = (b & 1) != 0;
            if ((b & 32) == 0) {
                z = false;
            }
            if (!z) {
                short readPadding = Http20Draft12.readPadding(this.source, b);
                handler.data(z2, i, this.source, Http20Draft12.lengthWithoutPadding(s, b, readPadding));
                this.source.skip(readPadding);
                return;
            }
            Http20Draft12.access$200("PROTOCOL_ERROR: FLAG_COMPRESSED without SETTINGS_COMPRESS_DATA", new Object[0]);
            throw null;
        }

        private void readPriority(FrameReader.Handler handler, short s, byte b, int i) throws IOException {
            if (s != 5) {
                Http20Draft12.access$200("TYPE_PRIORITY length: %d != 5", new Object[]{Short.valueOf(s)});
                throw null;
            } else if (i == 0) {
                Http20Draft12.access$200("TYPE_PRIORITY streamId == 0", new Object[0]);
                throw null;
            } else {
                readPriority(handler, i);
            }
        }

        private void readPriority(FrameReader.Handler handler, int i) throws IOException {
            int readInt = this.source.readInt();
            handler.priority(i, readInt & Integer.MAX_VALUE, (this.source.readByte() & 255) + 1, (Integer.MIN_VALUE & readInt) != 0);
        }

        private void readRstStream(FrameReader.Handler handler, short s, byte b, int i) throws IOException {
            if (s != 4) {
                Http20Draft12.access$200("TYPE_RST_STREAM length: %d != 4", new Object[]{Short.valueOf(s)});
                throw null;
            } else if (i == 0) {
                Http20Draft12.access$200("TYPE_RST_STREAM streamId == 0", new Object[0]);
                throw null;
            } else {
                int readInt = this.source.readInt();
                ErrorCode fromHttp2 = ErrorCode.fromHttp2(readInt);
                if (fromHttp2 != null) {
                    handler.rstStream(i, fromHttp2);
                } else {
                    Http20Draft12.access$200("TYPE_RST_STREAM unexpected error code: %d", new Object[]{Integer.valueOf(readInt)});
                    throw null;
                }
            }
        }

        private void readSettings(FrameReader.Handler handler, short s, byte b, int i) throws IOException {
            if (i != 0) {
                Http20Draft12.access$200("TYPE_SETTINGS streamId != 0", new Object[0]);
                throw null;
            } else if ((b & 1) != 0) {
                if (s != 0) {
                    Http20Draft12.access$200("FRAME_SIZE_ERROR ack frame should be empty!", new Object[0]);
                    throw null;
                }
                handler.ackSettings();
            } else if (s % 5 != 0) {
                Http20Draft12.access$200("TYPE_SETTINGS length %% 5 != 0: %s", new Object[]{Short.valueOf(s)});
                throw null;
            } else {
                Settings settings = new Settings();
                for (int i2 = 0; i2 < s; i2 += 5) {
                    byte readByte = this.source.readByte();
                    int readInt = this.source.readInt();
                    if (readByte != 1) {
                        if (readByte != 2) {
                            if (readByte == 3) {
                                readByte = 4;
                            } else if (readByte == 4) {
                                readByte = 7;
                                if (readInt < 0) {
                                    Http20Draft12.access$200("PROTOCOL_ERROR SETTINGS_INITIAL_WINDOW_SIZE > 2^31 - 1", new Object[0]);
                                    throw null;
                                }
                            } else if (readByte != 5) {
                                Http20Draft12.access$200("PROTOCOL_ERROR invalid settings id: %s", new Object[]{Integer.valueOf(readByte)});
                                throw null;
                            }
                        } else if (readInt != 0 && readInt != 1) {
                            Http20Draft12.access$200("PROTOCOL_ERROR SETTINGS_ENABLE_PUSH != 0 or 1", new Object[0]);
                            throw null;
                        }
                    }
                    settings.set(readByte, 0, readInt);
                }
                handler.settings(false, settings);
                if (settings.getHeaderTableSize() < 0) {
                    return;
                }
                this.hpackReader.maxHeaderTableByteCountSetting(settings.getHeaderTableSize());
            }
        }

        private void readPushPromise(FrameReader.Handler handler, short s, byte b, int i) throws IOException {
            if (i != 0) {
                handler.pushPromise(i, this.source.readInt() & Integer.MAX_VALUE, readHeaderBlock((short) (s - 4), Http20Draft12.readPadding(this.source, b), b, i));
                return;
            }
            Http20Draft12.access$200("PROTOCOL_ERROR: TYPE_PUSH_PROMISE streamId == 0", new Object[0]);
            throw null;
        }

        private void readPing(FrameReader.Handler handler, short s, byte b, int i) throws IOException {
            boolean z = false;
            if (s != 8) {
                Http20Draft12.access$200("TYPE_PING length != 8: %s", new Object[]{Short.valueOf(s)});
                throw null;
            } else if (i != 0) {
                Http20Draft12.access$200("TYPE_PING streamId != 0", new Object[0]);
                throw null;
            } else {
                int readInt = this.source.readInt();
                int readInt2 = this.source.readInt();
                if ((b & 1) != 0) {
                    z = true;
                }
                handler.ping(z, readInt, readInt2);
            }
        }

        private void readGoAway(FrameReader.Handler handler, short s, byte b, int i) throws IOException {
            if (s < 8) {
                Http20Draft12.access$200("TYPE_GOAWAY length < 8: %s", new Object[]{Short.valueOf(s)});
                throw null;
            } else if (i != 0) {
                Http20Draft12.access$200("TYPE_GOAWAY streamId != 0", new Object[0]);
                throw null;
            } else {
                int readInt = this.source.readInt();
                int readInt2 = this.source.readInt();
                int i2 = s - 8;
                ErrorCode fromHttp2 = ErrorCode.fromHttp2(readInt2);
                if (fromHttp2 == null) {
                    Http20Draft12.access$200("TYPE_GOAWAY unexpected error code: %d", new Object[]{Integer.valueOf(readInt2)});
                    throw null;
                }
                ByteString byteString = ByteString.EMPTY;
                if (i2 > 0) {
                    byteString = this.source.readByteString(i2);
                }
                handler.goAway(readInt, fromHttp2, byteString);
            }
        }

        private void readWindowUpdate(FrameReader.Handler handler, short s, byte b, int i) throws IOException {
            if (s != 4) {
                Http20Draft12.access$200("TYPE_WINDOW_UPDATE length !=4: %s", new Object[]{Short.valueOf(s)});
                throw null;
            }
            long readInt = this.source.readInt() & 2147483647L;
            if (readInt != 0) {
                handler.windowUpdate(i, readInt);
            } else {
                Http20Draft12.access$200("windowSizeIncrement was 0", new Object[]{Long.valueOf(readInt)});
                throw null;
            }
        }

        private void readAlternateService(FrameReader.Handler handler, short s, byte b, int i) throws IOException {
            long readInt = this.source.readInt() & 4294967295L;
            int readShort = this.source.readShort() & 65535;
            this.source.readByte();
            int readByte = this.source.readByte() & 255;
            ByteString readByteString = this.source.readByteString(readByte);
            int readByte2 = this.source.readByte() & 255;
            handler.alternateService(i, this.source.readUtf8(((s - 9) - readByte) - readByte2), readByteString, this.source.readUtf8(readByte2), readShort, readInt);
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            this.source.close();
        }
    }

    /* loaded from: classes3.dex */
    static final class Writer implements FrameWriter {
        private final boolean client;
        private boolean closed;
        private final Buffer hpackBuffer = new Buffer();
        private final HpackDraft07.Writer hpackWriter = new HpackDraft07.Writer(this.hpackBuffer);
        private final BufferedSink sink;

        Writer(BufferedSink bufferedSink, boolean z) {
            this.sink = bufferedSink;
            this.client = z;
        }

        @Override // com.squareup.okhttp.internal.spdy.FrameWriter
        public synchronized void flush() throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            this.sink.flush();
        }

        @Override // com.squareup.okhttp.internal.spdy.FrameWriter
        public synchronized void ackSettings() throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            frameHeader(0, 0, (byte) 4, (byte) 1);
            this.sink.flush();
        }

        @Override // com.squareup.okhttp.internal.spdy.FrameWriter
        public synchronized void connectionPreface() throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            if (!this.client) {
                return;
            }
            if (Http20Draft12.logger.isLoggable(Level.FINE)) {
                Http20Draft12.logger.fine(String.format(">> CONNECTION %s", Http20Draft12.CONNECTION_PREFACE.hex()));
            }
            this.sink.mo6805write(Http20Draft12.CONNECTION_PREFACE.toByteArray());
            this.sink.flush();
        }

        @Override // com.squareup.okhttp.internal.spdy.FrameWriter
        public synchronized void synStream(boolean z, boolean z2, int i, int i2, List<Header> list) throws IOException {
            try {
                if (z2) {
                    throw new UnsupportedOperationException();
                }
                if (this.closed) {
                    throw new IOException("closed");
                }
                headers(z, i, list);
            } catch (Throwable th) {
                throw th;
            }
        }

        @Override // com.squareup.okhttp.internal.spdy.FrameWriter
        public synchronized void pushPromise(int i, int i2, List<Header> list) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            if (this.hpackBuffer.size() != 0) {
                throw new IllegalStateException();
            }
            this.hpackWriter.writeHeaders(list);
            long size = this.hpackBuffer.size();
            int min = (int) Math.min(16379L, size);
            long j = min;
            int i3 = (size > j ? 1 : (size == j ? 0 : -1));
            frameHeader(i, min + 4, (byte) 5, i3 == 0 ? (byte) 4 : (byte) 0);
            this.sink.mo6810writeInt(i2 & Integer.MAX_VALUE);
            this.sink.write(this.hpackBuffer, j);
            if (i3 > 0) {
                writeContinuationFrames(i, size - j);
            }
        }

        void headers(boolean z, int i, List<Header> list) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            if (this.hpackBuffer.size() != 0) {
                throw new IllegalStateException();
            }
            this.hpackWriter.writeHeaders(list);
            long size = this.hpackBuffer.size();
            int min = (int) Math.min(16383L, size);
            long j = min;
            int i2 = (size > j ? 1 : (size == j ? 0 : -1));
            byte b = i2 == 0 ? (byte) 4 : (byte) 0;
            if (z) {
                b = (byte) (b | 1);
            }
            frameHeader(i, min, (byte) 1, b);
            this.sink.write(this.hpackBuffer, j);
            if (i2 <= 0) {
                return;
            }
            writeContinuationFrames(i, size - j);
        }

        private void writeContinuationFrames(int i, long j) throws IOException {
            while (j > 0) {
                int min = (int) Math.min(16383L, j);
                long j2 = min;
                j -= j2;
                frameHeader(i, min, (byte) 9, j == 0 ? (byte) 4 : (byte) 0);
                this.sink.write(this.hpackBuffer, j2);
            }
        }

        @Override // com.squareup.okhttp.internal.spdy.FrameWriter
        public synchronized void rstStream(int i, ErrorCode errorCode) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            if (errorCode.spdyRstCode == -1) {
                throw new IllegalArgumentException();
            }
            frameHeader(i, 4, (byte) 3, (byte) 0);
            this.sink.mo6810writeInt(errorCode.httpCode);
            this.sink.flush();
        }

        @Override // com.squareup.okhttp.internal.spdy.FrameWriter
        public synchronized void data(boolean z, int i, Buffer buffer, int i2) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            byte b = 0;
            if (z) {
                b = (byte) 1;
            }
            dataFrame(i, b, buffer, i2);
        }

        void dataFrame(int i, byte b, Buffer buffer, int i2) throws IOException {
            frameHeader(i, i2, (byte) 0, b);
            if (i2 > 0) {
                this.sink.write(buffer, i2);
            }
        }

        @Override // com.squareup.okhttp.internal.spdy.FrameWriter
        public synchronized void settings(Settings settings) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            int i = 0;
            frameHeader(0, settings.size() * 5, (byte) 4, (byte) 0);
            while (i < 10) {
                if (settings.isSet(i)) {
                    this.sink.mo6807writeByte(i == 4 ? 3 : i == 7 ? 4 : i);
                    this.sink.mo6810writeInt(settings.get(i));
                }
                i++;
            }
            this.sink.flush();
        }

        @Override // com.squareup.okhttp.internal.spdy.FrameWriter
        public synchronized void ping(boolean z, int i, int i2) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            frameHeader(0, 8, (byte) 6, z ? (byte) 1 : (byte) 0);
            this.sink.mo6810writeInt(i);
            this.sink.mo6810writeInt(i2);
            this.sink.flush();
        }

        @Override // com.squareup.okhttp.internal.spdy.FrameWriter
        public synchronized void goAway(int i, ErrorCode errorCode, byte[] bArr) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            if (errorCode.httpCode == -1) {
                Http20Draft12.access$500("errorCode.httpCode == -1", new Object[0]);
                throw null;
            }
            frameHeader(0, bArr.length + 8, (byte) 7, (byte) 0);
            this.sink.mo6810writeInt(i);
            this.sink.mo6810writeInt(errorCode.httpCode);
            if (bArr.length > 0) {
                this.sink.mo6805write(bArr);
            }
            this.sink.flush();
        }

        @Override // com.squareup.okhttp.internal.spdy.FrameWriter
        public synchronized void windowUpdate(int i, long j) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            if (j == 0 || j > 2147483647L) {
                Http20Draft12.access$500("windowSizeIncrement == 0 || windowSizeIncrement > 0x7fffffffL: %s", new Object[]{Long.valueOf(j)});
                throw null;
            }
            frameHeader(i, 4, (byte) 8, (byte) 0);
            this.sink.mo6810writeInt((int) j);
            this.sink.flush();
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public synchronized void close() throws IOException {
            this.closed = true;
            this.sink.close();
        }

        void frameHeader(int i, int i2, byte b, byte b2) throws IOException {
            if (Http20Draft12.logger.isLoggable(Level.FINE)) {
                Http20Draft12.logger.fine(FrameLogger.formatHeader(false, i, i2, b, b2));
            }
            if (i2 > 16383) {
                Http20Draft12.access$500("FRAME_SIZE_ERROR length > %d: %d", new Object[]{16383, Integer.valueOf(i2)});
                throw null;
            } else if ((Integer.MIN_VALUE & i) != 0) {
                Http20Draft12.access$500("reserved bit set: %s", new Object[]{Integer.valueOf(i)});
                throw null;
            } else {
                this.sink.mo6810writeInt(((i2 & 16383) << 16) | ((b & 255) << 8) | (b2 & 255));
                this.sink.mo6810writeInt(i & Integer.MAX_VALUE);
            }
        }
    }

    private static IllegalArgumentException illegalArgument(String str, Object... objArr) {
        throw new IllegalArgumentException(String.format(str, objArr));
    }

    private static IOException ioException(String str, Object... objArr) throws IOException {
        throw new IOException(String.format(str, objArr));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static final class ContinuationSource implements Source {
        byte flags;
        short left;
        short length;
        short padding;
        private final BufferedSource source;
        int streamId;

        @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
        }

        public ContinuationSource(BufferedSource bufferedSource) {
            this.source = bufferedSource;
        }

        @Override // okio.Source
        public long read(Buffer buffer, long j) throws IOException {
            while (true) {
                short s = this.left;
                if (s == 0) {
                    this.source.skip(this.padding);
                    this.padding = (short) 0;
                    if ((this.flags & 4) != 0) {
                        return -1L;
                    }
                    readContinuationHeader();
                } else {
                    long read = this.source.read(buffer, Math.min(j, s));
                    if (read == -1) {
                        return -1L;
                    }
                    this.left = (short) (this.left - read);
                    return read;
                }
            }
        }

        @Override // okio.Source
        public Timeout timeout() {
            return this.source.timeout();
        }

        private void readContinuationHeader() throws IOException {
            int i = this.streamId;
            int readInt = this.source.readInt();
            int readInt2 = this.source.readInt();
            this.length = (short) ((1073676288 & readInt) >> 16);
            byte b = (byte) ((65280 & readInt) >> 8);
            this.flags = (byte) (readInt & 255);
            if (Http20Draft12.logger.isLoggable(Level.FINE)) {
                Http20Draft12.logger.fine(FrameLogger.formatHeader(true, this.streamId, this.length, b, this.flags));
            }
            this.padding = Http20Draft12.readPadding(this.source, this.flags);
            short lengthWithoutPadding = Http20Draft12.lengthWithoutPadding(this.length, this.flags, this.padding);
            this.left = lengthWithoutPadding;
            this.length = lengthWithoutPadding;
            this.streamId = Integer.MAX_VALUE & readInt2;
            if (b != 9) {
                Http20Draft12.access$200("%s != TYPE_CONTINUATION", new Object[]{Byte.valueOf(b)});
                throw null;
            } else if (this.streamId == i) {
            } else {
                Http20Draft12.access$200("TYPE_CONTINUATION streamId changed", new Object[0]);
                throw null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static short readPadding(BufferedSource bufferedSource, byte b) throws IOException {
        int readByte;
        int i = b & 16;
        if (i != 0 && (b & 8) == 0) {
            ioException("PROTOCOL_ERROR FLAG_PAD_HIGH set without FLAG_PAD_LOW", new Object[0]);
            throw null;
        }
        if (i != 0) {
            readByte = bufferedSource.readShort() & 65535;
        } else {
            readByte = (b & 8) != 0 ? bufferedSource.readByte() & 255 : 0;
        }
        if (readByte <= 16383) {
            return (short) readByte;
        }
        ioException("PROTOCOL_ERROR padding > %d: %d", 16383, Integer.valueOf(readByte));
        throw null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:6:0x0011  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static short lengthWithoutPadding(short s, byte b, short s2) throws IOException {
        int i;
        if ((b & 16) == 0) {
            if ((b & 8) != 0) {
                i = s - 1;
            }
            if (s2 > s) {
                return (short) (s - s2);
            }
            ioException("PROTOCOL_ERROR padding %s > remaining length %s", Short.valueOf(s2), Short.valueOf(s));
            throw null;
        }
        i = s - 2;
        s = (short) i;
        if (s2 > s) {
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static final class FrameLogger {
        private static final String[] TYPES = {"DATA", "HEADERS", "PRIORITY", "RST_STREAM", "SETTINGS", "PUSH_PROMISE", "PING", "GOAWAY", "WINDOW_UPDATE", "CONTINUATION", "ALTSVC", "BLOCKED"};
        private static final String[] FLAGS = new String[64];
        private static final String[] BINARY = new String[256];

        static String formatHeader(boolean z, int i, int i2, byte b, byte b2) {
            String[] strArr = TYPES;
            String format = b < strArr.length ? strArr[b] : String.format("0x%02x", Byte.valueOf(b));
            String formatFlags = formatFlags(b, b2);
            Object[] objArr = new Object[5];
            objArr[0] = z ? "<<" : ">>";
            objArr[1] = Integer.valueOf(i);
            objArr[2] = Integer.valueOf(i2);
            objArr[3] = format;
            objArr[4] = formatFlags;
            return String.format("%s 0x%08x %5d %-13s %s", objArr);
        }

        static String formatFlags(byte b, byte b2) {
            if (b2 == 0) {
                return "";
            }
            switch (b) {
                case 2:
                case 3:
                case 7:
                case 8:
                case 10:
                case 11:
                    return BINARY[b2];
                case 4:
                case 6:
                    return b2 == 1 ? "ACK" : BINARY[b2];
                case 5:
                case 9:
                default:
                    String[] strArr = FLAGS;
                    String str = b2 < strArr.length ? strArr[b2] : BINARY[b2];
                    if (b == 5 && (b2 & 4) != 0) {
                        return str.replace("HEADERS", "PUSH_PROMISE");
                    }
                    return (b != 0 || (b2 & 32) == 0) ? str : str.replace("PRIORITY", "COMPRESSED");
            }
        }

        static {
            int[] iArr;
            int i;
            int i2 = 0;
            while (true) {
                String[] strArr = BINARY;
                if (i2 >= strArr.length) {
                    break;
                }
                strArr[i2] = String.format("%8s", Integer.toBinaryString(i2)).replace(' ', '0');
                i2++;
            }
            String[] strArr2 = FLAGS;
            strArr2[0] = "";
            strArr2[1] = "END_STREAM";
            strArr2[2] = "END_SEGMENT";
            strArr2[3] = "END_STREAM|END_SEGMENT";
            int[] iArr2 = {1, 2, 3};
            strArr2[8] = "PAD_LOW";
            strArr2[24] = "PAD_LOW|PAD_HIGH";
            int[] iArr3 = {8, 24};
            for (int i3 : iArr2) {
                for (int i4 : iArr3) {
                    FLAGS[i3 | i4] = FLAGS[i3] + '|' + FLAGS[i4];
                }
            }
            String[] strArr3 = FLAGS;
            strArr3[4] = "END_HEADERS";
            strArr3[32] = "PRIORITY";
            strArr3[36] = "END_HEADERS|PRIORITY";
            for (int i5 : new int[]{4, 32, 36}) {
                for (int i6 : iArr2) {
                    int i7 = i6 | i5;
                    FLAGS[i7] = FLAGS[i6] + '|' + FLAGS[i5];
                    int length = iArr3.length;
                    int i8 = 0;
                    while (i8 < length) {
                        FLAGS[i7 | iArr3[i8]] = FLAGS[i6] + '|' + FLAGS[i5] + '|' + FLAGS[i];
                        i8++;
                        iArr3 = iArr3;
                    }
                }
            }
            int i9 = 0;
            while (true) {
                String[] strArr4 = FLAGS;
                if (i9 < strArr4.length) {
                    if (strArr4[i9] == null) {
                        strArr4[i9] = BINARY[i9];
                    }
                    i9++;
                } else {
                    return;
                }
            }
        }
    }
}
