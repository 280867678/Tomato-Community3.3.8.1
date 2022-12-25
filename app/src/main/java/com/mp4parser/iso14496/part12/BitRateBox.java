package com.mp4parser.iso14496.part12;

import com.coremedia.iso.IsoTypeReader;
import com.coremedia.iso.IsoTypeWriter;
import com.googlecode.mp4parser.AbstractBox;
import java.nio.ByteBuffer;

/* loaded from: classes3.dex */
public final class BitRateBox extends AbstractBox {
    public static final String TYPE = "btrt";
    private long avgBitrate;
    private long bufferSizeDb;
    private long maxBitrate;

    @Override // com.googlecode.mp4parser.AbstractBox
    protected long getContentSize() {
        return 12L;
    }

    public BitRateBox() {
        super(TYPE);
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    public void _parseDetails(ByteBuffer byteBuffer) {
        this.bufferSizeDb = IsoTypeReader.readUInt32(byteBuffer);
        this.maxBitrate = IsoTypeReader.readUInt32(byteBuffer);
        this.avgBitrate = IsoTypeReader.readUInt32(byteBuffer);
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    protected void getContent(ByteBuffer byteBuffer) {
        IsoTypeWriter.writeUInt32(byteBuffer, this.bufferSizeDb);
        IsoTypeWriter.writeUInt32(byteBuffer, this.maxBitrate);
        IsoTypeWriter.writeUInt32(byteBuffer, this.avgBitrate);
    }

    public long getBufferSizeDb() {
        return this.bufferSizeDb;
    }

    public void setBufferSizeDb(long j) {
        this.bufferSizeDb = j;
    }

    public long getMaxBitrate() {
        return this.maxBitrate;
    }

    public void setMaxBitrate(long j) {
        this.maxBitrate = j;
    }

    public long getAvgBitrate() {
        return this.avgBitrate;
    }

    public void setAvgBitrate(long j) {
        this.avgBitrate = j;
    }
}
