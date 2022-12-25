package com.mp4parser.iso14496.part12;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.IsoTypeReader;
import com.coremedia.iso.IsoTypeWriter;
import com.googlecode.mp4parser.AbstractFullBox;
import com.googlecode.mp4parser.util.CastUtils;
import java.nio.ByteBuffer;

/* loaded from: classes3.dex */
public class SampleAuxiliaryInformationOffsetsBox extends AbstractFullBox {
    public static final String TYPE = "saio";
    private String auxInfoType;
    private String auxInfoTypeParameter;
    private long[] offsets = new long[0];

    public SampleAuxiliaryInformationOffsetsBox() {
        super(TYPE);
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    protected long getContentSize() {
        int i = 8;
        int length = (getVersion() == 0 ? this.offsets.length * 4 : this.offsets.length * 8) + 8;
        if ((getFlags() & 1) != 1) {
            i = 0;
        }
        return length + i;
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    protected void getContent(ByteBuffer byteBuffer) {
        writeVersionAndFlags(byteBuffer);
        if ((getFlags() & 1) == 1) {
            byteBuffer.put(IsoFile.fourCCtoBytes(this.auxInfoType));
            byteBuffer.put(IsoFile.fourCCtoBytes(this.auxInfoTypeParameter));
        }
        IsoTypeWriter.writeUInt32(byteBuffer, this.offsets.length);
        for (long j : this.offsets) {
            Long valueOf = Long.valueOf(j);
            if (getVersion() == 0) {
                IsoTypeWriter.writeUInt32(byteBuffer, valueOf.longValue());
            } else {
                IsoTypeWriter.writeUInt64(byteBuffer, valueOf.longValue());
            }
        }
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    public void _parseDetails(ByteBuffer byteBuffer) {
        parseVersionAndFlags(byteBuffer);
        if ((getFlags() & 1) == 1) {
            this.auxInfoType = IsoTypeReader.read4cc(byteBuffer);
            this.auxInfoTypeParameter = IsoTypeReader.read4cc(byteBuffer);
        }
        int l2i = CastUtils.l2i(IsoTypeReader.readUInt32(byteBuffer));
        this.offsets = new long[l2i];
        for (int i = 0; i < l2i; i++) {
            if (getVersion() == 0) {
                this.offsets[i] = IsoTypeReader.readUInt32(byteBuffer);
            } else {
                this.offsets[i] = IsoTypeReader.readUInt64(byteBuffer);
            }
        }
    }

    public String getAuxInfoType() {
        return this.auxInfoType;
    }

    public void setAuxInfoType(String str) {
        this.auxInfoType = str;
    }

    public String getAuxInfoTypeParameter() {
        return this.auxInfoTypeParameter;
    }

    public void setAuxInfoTypeParameter(String str) {
        this.auxInfoTypeParameter = str;
    }

    public long[] getOffsets() {
        return this.offsets;
    }

    public void setOffsets(long[] jArr) {
        this.offsets = jArr;
    }
}
