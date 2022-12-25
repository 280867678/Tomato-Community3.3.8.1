package okhttp3.internal.http2;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import okhttp3.internal.Util;
import okhttp3.internal.http2.Hpack;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Source;
import okio.Timeout;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public final class Http2Reader implements Closeable {
    static final Logger logger = Logger.getLogger(Http2.class.getName());
    private final boolean client;
    private final ContinuationSource continuation;
    final Hpack.Reader hpackReader;
    private final BufferedSource source;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public interface Handler {
        void ackSettings();

        void data(boolean z, int i, BufferedSource bufferedSource, int i2) throws IOException;

        void goAway(int i, ErrorCode errorCode, ByteString byteString);

        void headers(boolean z, int i, int i2, List<Header> list);

        void ping(boolean z, int i, int i2);

        void priority(int i, int i2, int i3, boolean z);

        void pushPromise(int i, int i2, List<Header> list) throws IOException;

        void rstStream(int i, ErrorCode errorCode);

        void settings(boolean z, Settings settings);

        void windowUpdate(int i, long j);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Http2Reader(BufferedSource bufferedSource, boolean z) {
        this.source = bufferedSource;
        this.client = z;
        this.continuation = new ContinuationSource(this.source);
        this.hpackReader = new Hpack.Reader(4096, this.continuation);
    }

    public void readConnectionPreface(Handler handler) throws IOException {
        if (this.client) {
            if (nextFrame(true, handler)) {
                return;
            }
            Http2.ioException("Required SETTINGS preface not received", new Object[0]);
            throw null;
        }
        ByteString readByteString = this.source.readByteString(Http2.CONNECTION_PREFACE.size());
        if (logger.isLoggable(Level.FINE)) {
            logger.fine(Util.format("<< CONNECTION %s", readByteString.hex()));
        }
        if (Http2.CONNECTION_PREFACE.equals(readByteString)) {
            return;
        }
        Http2.ioException("Expected a connection header but was %s", readByteString.utf8());
        throw null;
    }

    public boolean nextFrame(boolean z, Handler handler) throws IOException {
        try {
            this.source.require(9L);
            int readMedium = readMedium(this.source);
            if (readMedium < 0 || readMedium > 16384) {
                Http2.ioException("FRAME_SIZE_ERROR: %s", Integer.valueOf(readMedium));
                throw null;
            }
            byte readByte = (byte) (this.source.readByte() & 255);
            if (z && readByte != 4) {
                Http2.ioException("Expected a SETTINGS frame but was %s", Byte.valueOf(readByte));
                throw null;
            }
            byte readByte2 = (byte) (this.source.readByte() & 255);
            int readInt = this.source.readInt() & Integer.MAX_VALUE;
            if (logger.isLoggable(Level.FINE)) {
                logger.fine(Http2.frameLog(true, readInt, readMedium, readByte, readByte2));
            }
            switch (readByte) {
                case 0:
                    readData(handler, readMedium, readByte2, readInt);
                    break;
                case 1:
                    readHeaders(handler, readMedium, readByte2, readInt);
                    break;
                case 2:
                    readPriority(handler, readMedium, readByte2, readInt);
                    break;
                case 3:
                    readRstStream(handler, readMedium, readByte2, readInt);
                    break;
                case 4:
                    readSettings(handler, readMedium, readByte2, readInt);
                    break;
                case 5:
                    readPushPromise(handler, readMedium, readByte2, readInt);
                    break;
                case 6:
                    readPing(handler, readMedium, readByte2, readInt);
                    break;
                case 7:
                    readGoAway(handler, readMedium, readByte2, readInt);
                    break;
                case 8:
                    readWindowUpdate(handler, readMedium, readByte2, readInt);
                    break;
                default:
                    this.source.skip(readMedium);
                    break;
            }
            return true;
        } catch (IOException unused) {
            return false;
        }
    }

    private void readHeaders(Handler handler, int i, byte b, int i2) throws IOException {
        short s = 0;
        if (i2 == 0) {
            Http2.ioException("PROTOCOL_ERROR: TYPE_HEADERS streamId == 0", new Object[0]);
            throw null;
        }
        boolean z = (b & 1) != 0;
        if ((b & 8) != 0) {
            s = (short) (this.source.readByte() & 255);
        }
        if ((b & 32) != 0) {
            readPriority(handler, i2);
            i -= 5;
        }
        handler.headers(z, i2, -1, readHeaderBlock(lengthWithoutPadding(i, b, s), s, b, i2));
    }

    private List<Header> readHeaderBlock(int i, short s, byte b, int i2) throws IOException {
        ContinuationSource continuationSource = this.continuation;
        continuationSource.left = i;
        continuationSource.length = i;
        continuationSource.padding = s;
        continuationSource.flags = b;
        continuationSource.streamId = i2;
        this.hpackReader.readHeaders();
        return this.hpackReader.getAndResetHeaderList();
    }

    private void readData(Handler handler, int i, byte b, int i2) throws IOException {
        short s = 0;
        if (i2 == 0) {
            Http2.ioException("PROTOCOL_ERROR: TYPE_DATA streamId == 0", new Object[0]);
            throw null;
        }
        boolean z = true;
        boolean z2 = (b & 1) != 0;
        if ((b & 32) == 0) {
            z = false;
        }
        if (z) {
            Http2.ioException("PROTOCOL_ERROR: FLAG_COMPRESSED without SETTINGS_COMPRESS_DATA", new Object[0]);
            throw null;
        }
        if ((b & 8) != 0) {
            s = (short) (this.source.readByte() & 255);
        }
        handler.data(z2, i2, this.source, lengthWithoutPadding(i, b, s));
        this.source.skip(s);
    }

    private void readPriority(Handler handler, int i, byte b, int i2) throws IOException {
        if (i != 5) {
            Http2.ioException("TYPE_PRIORITY length: %d != 5", Integer.valueOf(i));
            throw null;
        } else if (i2 == 0) {
            Http2.ioException("TYPE_PRIORITY streamId == 0", new Object[0]);
            throw null;
        } else {
            readPriority(handler, i2);
        }
    }

    private void readPriority(Handler handler, int i) throws IOException {
        int readInt = this.source.readInt();
        handler.priority(i, readInt & Integer.MAX_VALUE, (this.source.readByte() & 255) + 1, (Integer.MIN_VALUE & readInt) != 0);
    }

    private void readRstStream(Handler handler, int i, byte b, int i2) throws IOException {
        if (i != 4) {
            Http2.ioException("TYPE_RST_STREAM length: %d != 4", Integer.valueOf(i));
            throw null;
        } else if (i2 == 0) {
            Http2.ioException("TYPE_RST_STREAM streamId == 0", new Object[0]);
            throw null;
        } else {
            int readInt = this.source.readInt();
            ErrorCode fromHttp2 = ErrorCode.fromHttp2(readInt);
            if (fromHttp2 != null) {
                handler.rstStream(i2, fromHttp2);
            } else {
                Http2.ioException("TYPE_RST_STREAM unexpected error code: %d", Integer.valueOf(readInt));
                throw null;
            }
        }
    }

    private void readSettings(Handler handler, int i, byte b, int i2) throws IOException {
        if (i2 != 0) {
            Http2.ioException("TYPE_SETTINGS streamId != 0", new Object[0]);
            throw null;
        } else if ((b & 1) != 0) {
            if (i != 0) {
                Http2.ioException("FRAME_SIZE_ERROR ack frame should be empty!", new Object[0]);
                throw null;
            }
            handler.ackSettings();
        } else if (i % 6 != 0) {
            Http2.ioException("TYPE_SETTINGS length %% 6 != 0: %s", Integer.valueOf(i));
            throw null;
        } else {
            Settings settings = new Settings();
            for (int i3 = 0; i3 < i; i3 += 6) {
                int readShort = this.source.readShort() & 65535;
                int readInt = this.source.readInt();
                switch (readShort) {
                    case 2:
                        if (readInt != 0 && readInt != 1) {
                            Http2.ioException("PROTOCOL_ERROR SETTINGS_ENABLE_PUSH != 0 or 1", new Object[0]);
                            throw null;
                        }
                        break;
                    case 3:
                        readShort = 4;
                        break;
                    case 4:
                        readShort = 7;
                        if (readInt < 0) {
                            Http2.ioException("PROTOCOL_ERROR SETTINGS_INITIAL_WINDOW_SIZE > 2^31 - 1", new Object[0]);
                            throw null;
                        }
                        break;
                    case 5:
                        if (readInt < 16384 || readInt > 16777215) {
                            Http2.ioException("PROTOCOL_ERROR SETTINGS_MAX_FRAME_SIZE: %s", Integer.valueOf(readInt));
                            throw null;
                        }
                        break;
                        break;
                }
                settings.set(readShort, readInt);
            }
            handler.settings(false, settings);
        }
    }

    private void readPushPromise(Handler handler, int i, byte b, int i2) throws IOException {
        short s = 0;
        if (i2 == 0) {
            Http2.ioException("PROTOCOL_ERROR: TYPE_PUSH_PROMISE streamId == 0", new Object[0]);
            throw null;
        }
        if ((b & 8) != 0) {
            s = (short) (this.source.readByte() & 255);
        }
        handler.pushPromise(i2, this.source.readInt() & Integer.MAX_VALUE, readHeaderBlock(lengthWithoutPadding(i - 4, b, s), s, b, i2));
    }

    private void readPing(Handler handler, int i, byte b, int i2) throws IOException {
        boolean z = false;
        if (i != 8) {
            Http2.ioException("TYPE_PING length != 8: %s", Integer.valueOf(i));
            throw null;
        } else if (i2 != 0) {
            Http2.ioException("TYPE_PING streamId != 0", new Object[0]);
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

    private void readGoAway(Handler handler, int i, byte b, int i2) throws IOException {
        if (i < 8) {
            Http2.ioException("TYPE_GOAWAY length < 8: %s", Integer.valueOf(i));
            throw null;
        } else if (i2 != 0) {
            Http2.ioException("TYPE_GOAWAY streamId != 0", new Object[0]);
            throw null;
        } else {
            int readInt = this.source.readInt();
            int readInt2 = this.source.readInt();
            int i3 = i - 8;
            ErrorCode fromHttp2 = ErrorCode.fromHttp2(readInt2);
            if (fromHttp2 == null) {
                Http2.ioException("TYPE_GOAWAY unexpected error code: %d", Integer.valueOf(readInt2));
                throw null;
            }
            ByteString byteString = ByteString.EMPTY;
            if (i3 > 0) {
                byteString = this.source.readByteString(i3);
            }
            handler.goAway(readInt, fromHttp2, byteString);
        }
    }

    private void readWindowUpdate(Handler handler, int i, byte b, int i2) throws IOException {
        if (i != 4) {
            Http2.ioException("TYPE_WINDOW_UPDATE length !=4: %s", Integer.valueOf(i));
            throw null;
        }
        long readInt = this.source.readInt() & 2147483647L;
        if (readInt != 0) {
            handler.windowUpdate(i2, readInt);
        } else {
            Http2.ioException("windowSizeIncrement was 0", Long.valueOf(readInt));
            throw null;
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.source.close();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class ContinuationSource implements Source {
        byte flags;
        int left;
        int length;
        short padding;
        private final BufferedSource source;
        int streamId;

        @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
        }

        ContinuationSource(BufferedSource bufferedSource) {
            this.source = bufferedSource;
        }

        @Override // okio.Source
        public long read(Buffer buffer, long j) throws IOException {
            while (true) {
                int i = this.left;
                if (i == 0) {
                    this.source.skip(this.padding);
                    this.padding = (short) 0;
                    if ((this.flags & 4) != 0) {
                        return -1L;
                    }
                    readContinuationHeader();
                } else {
                    long read = this.source.read(buffer, Math.min(j, i));
                    if (read == -1) {
                        return -1L;
                    }
                    this.left = (int) (this.left - read);
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
            int readMedium = Http2Reader.readMedium(this.source);
            this.left = readMedium;
            this.length = readMedium;
            byte readByte = (byte) (this.source.readByte() & 255);
            this.flags = (byte) (this.source.readByte() & 255);
            if (Http2Reader.logger.isLoggable(Level.FINE)) {
                Http2Reader.logger.fine(Http2.frameLog(true, this.streamId, this.length, readByte, this.flags));
            }
            this.streamId = this.source.readInt() & Integer.MAX_VALUE;
            if (readByte != 9) {
                Http2.ioException("%s != TYPE_CONTINUATION", Byte.valueOf(readByte));
                throw null;
            } else if (this.streamId == i) {
            } else {
                Http2.ioException("TYPE_CONTINUATION streamId changed", new Object[0]);
                throw null;
            }
        }
    }

    static int readMedium(BufferedSource bufferedSource) throws IOException {
        return (bufferedSource.readByte() & 255) | ((bufferedSource.readByte() & 255) << 16) | ((bufferedSource.readByte() & 255) << 8);
    }

    static int lengthWithoutPadding(int i, byte b, short s) throws IOException {
        if ((b & 8) != 0) {
            i--;
        }
        if (s <= i) {
            return (short) (i - s);
        }
        Http2.ioException("PROTOCOL_ERROR padding %s > remaining length %s", Short.valueOf(s), Integer.valueOf(i));
        throw null;
    }
}
