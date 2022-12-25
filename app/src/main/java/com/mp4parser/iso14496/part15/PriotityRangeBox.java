package com.mp4parser.iso14496.part15;

import com.coremedia.iso.IsoTypeReader;
import com.coremedia.iso.IsoTypeWriter;
import com.googlecode.mp4parser.AbstractBox;
import java.nio.ByteBuffer;

/* loaded from: classes3.dex */
public class PriotityRangeBox extends AbstractBox {
    public static final String TYPE = "svpr";
    int max_priorityId;
    int min_priorityId;
    int reserved1 = 0;
    int reserved2 = 0;

    @Override // com.googlecode.mp4parser.AbstractBox
    protected long getContentSize() {
        return 2L;
    }

    public PriotityRangeBox() {
        super(TYPE);
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    protected void getContent(ByteBuffer byteBuffer) {
        IsoTypeWriter.writeUInt8(byteBuffer, (this.reserved1 << 6) + this.min_priorityId);
        IsoTypeWriter.writeUInt8(byteBuffer, (this.reserved2 << 6) + this.max_priorityId);
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    protected void _parseDetails(ByteBuffer byteBuffer) {
        this.min_priorityId = IsoTypeReader.readUInt8(byteBuffer);
        int i = this.min_priorityId;
        this.reserved1 = (i & 192) >> 6;
        this.min_priorityId = i & 63;
        this.max_priorityId = IsoTypeReader.readUInt8(byteBuffer);
        int i2 = this.max_priorityId;
        this.reserved2 = (i2 & 192) >> 6;
        this.max_priorityId = i2 & 63;
    }

    public int getReserved1() {
        return this.reserved1;
    }

    public void setReserved1(int i) {
        this.reserved1 = i;
    }

    public int getMin_priorityId() {
        return this.min_priorityId;
    }

    public void setMin_priorityId(int i) {
        this.min_priorityId = i;
    }

    public int getReserved2() {
        return this.reserved2;
    }

    public void setReserved2(int i) {
        this.reserved2 = i;
    }

    public int getMax_priorityId() {
        return this.max_priorityId;
    }

    public void setMax_priorityId(int i) {
        this.max_priorityId = i;
    }
}
