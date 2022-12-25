package com.coremedia.iso.boxes.sampleentry;

import com.coremedia.iso.BoxParser;
import com.coremedia.iso.IsoTypeReader;
import com.coremedia.iso.IsoTypeWriter;
import com.coremedia.iso.Utf8;
import com.coremedia.iso.boxes.Container;
import com.googlecode.mp4parser.DataSource;
import com.googlecode.mp4parser.util.CastUtils;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

/* loaded from: classes2.dex */
public final class VisualSampleEntry extends AbstractSampleEntry implements Container {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final String TYPE1 = "mp4v";
    public static final String TYPE2 = "s263";
    public static final String TYPE3 = "avc1";
    public static final String TYPE4 = "avc3";
    public static final String TYPE5 = "drmi";
    public static final String TYPE6 = "hvc1";
    public static final String TYPE7 = "hev1";
    public static final String TYPE_ENCRYPTED = "encv";
    private String compressorname;
    private int depth;
    private int frameCount;
    private int height;
    private double horizresolution;
    private long[] predefined;
    private double vertresolution;
    private int width;

    public VisualSampleEntry() {
        super(TYPE3);
        this.horizresolution = 72.0d;
        this.vertresolution = 72.0d;
        this.frameCount = 1;
        this.compressorname = "";
        this.depth = 24;
        this.predefined = new long[3];
    }

    public VisualSampleEntry(String str) {
        super(str);
        this.horizresolution = 72.0d;
        this.vertresolution = 72.0d;
        this.frameCount = 1;
        this.compressorname = "";
        this.depth = 24;
        this.predefined = new long[3];
    }

    public void setType(String str) {
        this.type = str;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int i) {
        this.width = i;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int i) {
        this.height = i;
    }

    public double getHorizresolution() {
        return this.horizresolution;
    }

    public void setHorizresolution(double d) {
        this.horizresolution = d;
    }

    public double getVertresolution() {
        return this.vertresolution;
    }

    public void setVertresolution(double d) {
        this.vertresolution = d;
    }

    public int getFrameCount() {
        return this.frameCount;
    }

    public void setFrameCount(int i) {
        this.frameCount = i;
    }

    public String getCompressorname() {
        return this.compressorname;
    }

    public void setCompressorname(String str) {
        this.compressorname = str;
    }

    public int getDepth() {
        return this.depth;
    }

    public void setDepth(int i) {
        this.depth = i;
    }

    @Override // com.coremedia.iso.boxes.sampleentry.AbstractSampleEntry, com.googlecode.mp4parser.AbstractContainerBox, com.coremedia.iso.boxes.Box
    public void parse(final DataSource dataSource, ByteBuffer byteBuffer, long j, BoxParser boxParser) throws IOException {
        final long position = dataSource.position() + j;
        ByteBuffer allocate = ByteBuffer.allocate(78);
        dataSource.read(allocate);
        allocate.position(6);
        this.dataReferenceIndex = IsoTypeReader.readUInt16(allocate);
        IsoTypeReader.readUInt16(allocate);
        IsoTypeReader.readUInt16(allocate);
        this.predefined[0] = IsoTypeReader.readUInt32(allocate);
        this.predefined[1] = IsoTypeReader.readUInt32(allocate);
        this.predefined[2] = IsoTypeReader.readUInt32(allocate);
        this.width = IsoTypeReader.readUInt16(allocate);
        this.height = IsoTypeReader.readUInt16(allocate);
        this.horizresolution = IsoTypeReader.readFixedPoint1616(allocate);
        this.vertresolution = IsoTypeReader.readFixedPoint1616(allocate);
        IsoTypeReader.readUInt32(allocate);
        this.frameCount = IsoTypeReader.readUInt16(allocate);
        int readUInt8 = IsoTypeReader.readUInt8(allocate);
        if (readUInt8 > 31) {
            readUInt8 = 31;
        }
        byte[] bArr = new byte[readUInt8];
        allocate.get(bArr);
        this.compressorname = Utf8.convert(bArr);
        if (readUInt8 < 31) {
            allocate.get(new byte[31 - readUInt8]);
        }
        this.depth = IsoTypeReader.readUInt16(allocate);
        IsoTypeReader.readUInt16(allocate);
        initContainer(new DataSource() { // from class: com.coremedia.iso.boxes.sampleentry.VisualSampleEntry.1
            @Override // com.googlecode.mp4parser.DataSource
            public int read(ByteBuffer byteBuffer2) throws IOException {
                if (position == dataSource.position()) {
                    return -1;
                }
                if (byteBuffer2.remaining() > position - dataSource.position()) {
                    ByteBuffer allocate2 = ByteBuffer.allocate(CastUtils.l2i(position - dataSource.position()));
                    dataSource.read(allocate2);
                    byteBuffer2.put((ByteBuffer) allocate2.rewind());
                    return allocate2.capacity();
                }
                return dataSource.read(byteBuffer2);
            }

            @Override // com.googlecode.mp4parser.DataSource
            public long size() throws IOException {
                return position;
            }

            @Override // com.googlecode.mp4parser.DataSource
            public long position() throws IOException {
                return dataSource.position();
            }

            @Override // com.googlecode.mp4parser.DataSource
            public void position(long j2) throws IOException {
                dataSource.position(j2);
            }

            @Override // com.googlecode.mp4parser.DataSource
            public long transferTo(long j2, long j3, WritableByteChannel writableByteChannel) throws IOException {
                return dataSource.transferTo(j2, j3, writableByteChannel);
            }

            @Override // com.googlecode.mp4parser.DataSource
            public ByteBuffer map(long j2, long j3) throws IOException {
                return dataSource.map(j2, j3);
            }

            @Override // com.googlecode.mp4parser.DataSource, java.io.Closeable, java.lang.AutoCloseable
            public void close() throws IOException {
                dataSource.close();
            }
        }, j - 78, boxParser);
    }

    @Override // com.coremedia.iso.boxes.sampleentry.AbstractSampleEntry, com.googlecode.mp4parser.AbstractContainerBox, com.coremedia.iso.boxes.Box
    public void getBox(WritableByteChannel writableByteChannel) throws IOException {
        writableByteChannel.write(getHeader());
        ByteBuffer allocate = ByteBuffer.allocate(78);
        allocate.position(6);
        IsoTypeWriter.writeUInt16(allocate, this.dataReferenceIndex);
        IsoTypeWriter.writeUInt16(allocate, 0);
        IsoTypeWriter.writeUInt16(allocate, 0);
        IsoTypeWriter.writeUInt32(allocate, this.predefined[0]);
        IsoTypeWriter.writeUInt32(allocate, this.predefined[1]);
        IsoTypeWriter.writeUInt32(allocate, this.predefined[2]);
        IsoTypeWriter.writeUInt16(allocate, getWidth());
        IsoTypeWriter.writeUInt16(allocate, getHeight());
        IsoTypeWriter.writeFixedPoint1616(allocate, getHorizresolution());
        IsoTypeWriter.writeFixedPoint1616(allocate, getVertresolution());
        IsoTypeWriter.writeUInt32(allocate, 0L);
        IsoTypeWriter.writeUInt16(allocate, getFrameCount());
        IsoTypeWriter.writeUInt8(allocate, Utf8.utf8StringLengthInBytes(getCompressorname()));
        allocate.put(Utf8.convert(getCompressorname()));
        int utf8StringLengthInBytes = Utf8.utf8StringLengthInBytes(getCompressorname());
        while (utf8StringLengthInBytes < 31) {
            utf8StringLengthInBytes++;
            allocate.put((byte) 0);
        }
        IsoTypeWriter.writeUInt16(allocate, getDepth());
        IsoTypeWriter.writeUInt16(allocate, 65535);
        writableByteChannel.write((ByteBuffer) allocate.rewind());
        writeContainer(writableByteChannel);
    }

    @Override // com.googlecode.mp4parser.AbstractContainerBox, com.coremedia.iso.boxes.Box
    public long getSize() {
        long containerSize = getContainerSize() + 78;
        return containerSize + ((this.largeBox || 8 + containerSize >= 4294967296L) ? 16 : 8);
    }
}
