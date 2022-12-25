package com.coremedia.iso;

import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.Container;
import com.coremedia.iso.boxes.UserBox;
import com.googlecode.mp4parser.DataSource;
import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Logger;

/* loaded from: classes2.dex */
public abstract class AbstractBoxParser implements BoxParser {
    private static Logger LOG = Logger.getLogger(AbstractBoxParser.class.getName());
    ThreadLocal<ByteBuffer> header = new ThreadLocal<ByteBuffer>() { // from class: com.coremedia.iso.AbstractBoxParser.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // java.lang.ThreadLocal
        public ByteBuffer initialValue() {
            return ByteBuffer.allocate(32);
        }
    };

    public abstract Box createBox(String str, byte[] bArr, String str2);

    @Override // com.coremedia.iso.BoxParser
    public Box parseBox(DataSource dataSource, Container container) throws IOException {
        int read;
        long size;
        long position = dataSource.position();
        this.header.get().rewind().limit(8);
        do {
            read = dataSource.read(this.header.get());
            if (read == 8) {
                this.header.get().rewind();
                long readUInt32 = IsoTypeReader.readUInt32(this.header.get());
                byte[] bArr = null;
                if (readUInt32 < 8 && readUInt32 > 1) {
                    Logger logger = LOG;
                    logger.severe("Plausibility check failed: size < 8 (size = " + readUInt32 + "). Stop parsing!");
                    return null;
                }
                String read4cc = IsoTypeReader.read4cc(this.header.get());
                if (readUInt32 == 1) {
                    this.header.get().limit(16);
                    dataSource.read(this.header.get());
                    this.header.get().position(8);
                    size = IsoTypeReader.readUInt64(this.header.get()) - 16;
                } else {
                    size = readUInt32 == 0 ? dataSource.size() - dataSource.position() : readUInt32 - 8;
                }
                if (UserBox.TYPE.equals(read4cc)) {
                    this.header.get().limit(this.header.get().limit() + 16);
                    dataSource.read(this.header.get());
                    bArr = new byte[16];
                    for (int position2 = this.header.get().position() - 16; position2 < this.header.get().position(); position2++) {
                        bArr[position2 - (this.header.get().position() - 16)] = this.header.get().get(position2);
                    }
                    size -= 16;
                }
                long j = size;
                Box createBox = createBox(read4cc, bArr, container instanceof Box ? ((Box) container).getType() : "");
                createBox.setParent(container);
                this.header.get().rewind();
                createBox.parse(dataSource, this.header.get(), j, this);
                return createBox;
            }
        } while (read >= 0);
        dataSource.position(position);
        throw new EOFException();
    }
}
