package okio;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.WritableByteChannel;

/* loaded from: classes4.dex */
public interface BufferedSink extends Sink, WritableByteChannel {
    Buffer buffer();

    BufferedSink emit() throws IOException;

    /* renamed from: emitCompleteSegments */
    BufferedSink mo6803emitCompleteSegments() throws IOException;

    @Override // okio.Sink, java.io.Flushable
    void flush() throws IOException;

    OutputStream outputStream();

    /* renamed from: write */
    BufferedSink mo6804write(ByteString byteString) throws IOException;

    /* renamed from: write */
    BufferedSink mo6805write(byte[] bArr) throws IOException;

    /* renamed from: write */
    BufferedSink mo6806write(byte[] bArr, int i, int i2) throws IOException;

    long writeAll(Source source) throws IOException;

    /* renamed from: writeByte */
    BufferedSink mo6807writeByte(int i) throws IOException;

    /* renamed from: writeDecimalLong */
    BufferedSink mo6808writeDecimalLong(long j) throws IOException;

    /* renamed from: writeHexadecimalUnsignedLong */
    BufferedSink mo6809writeHexadecimalUnsignedLong(long j) throws IOException;

    /* renamed from: writeInt */
    BufferedSink mo6810writeInt(int i) throws IOException;

    /* renamed from: writeIntLe */
    BufferedSink mo6811writeIntLe(int i) throws IOException;

    /* renamed from: writeLongLe */
    BufferedSink mo6812writeLongLe(long j) throws IOException;

    /* renamed from: writeShort */
    BufferedSink mo6813writeShort(int i) throws IOException;

    /* renamed from: writeUtf8 */
    BufferedSink mo6814writeUtf8(String str) throws IOException;
}
