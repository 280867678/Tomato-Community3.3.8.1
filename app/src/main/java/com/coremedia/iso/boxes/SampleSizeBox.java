package com.coremedia.iso.boxes;

import com.coremedia.iso.IsoTypeReader;
import com.coremedia.iso.IsoTypeWriter;
import com.googlecode.mp4parser.AbstractFullBox;
import com.googlecode.mp4parser.util.CastUtils;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public class SampleSizeBox extends AbstractFullBox {
    public static final String TYPE = "stsz";
    int sampleCount;
    private long sampleSize;
    private long[] sampleSizes = new long[0];

    public SampleSizeBox() {
        super(TYPE);
    }

    public long getSampleSize() {
        return this.sampleSize;
    }

    public void setSampleSize(long j) {
        this.sampleSize = j;
    }

    public long getSampleSizeAtIndex(int i) {
        long j = this.sampleSize;
        return j > 0 ? j : this.sampleSizes[i];
    }

    public long getSampleCount() {
        int length;
        if (this.sampleSize > 0) {
            length = this.sampleCount;
        } else {
            length = this.sampleSizes.length;
        }
        return length;
    }

    public long[] getSampleSizes() {
        return this.sampleSizes;
    }

    public void setSampleSizes(long[] jArr) {
        this.sampleSizes = jArr;
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    protected long getContentSize() {
        return (this.sampleSize == 0 ? this.sampleSizes.length * 4 : 0) + 12;
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    public void _parseDetails(ByteBuffer byteBuffer) {
        parseVersionAndFlags(byteBuffer);
        this.sampleSize = IsoTypeReader.readUInt32(byteBuffer);
        this.sampleCount = CastUtils.l2i(IsoTypeReader.readUInt32(byteBuffer));
        if (this.sampleSize == 0) {
            this.sampleSizes = new long[this.sampleCount];
            for (int i = 0; i < this.sampleCount; i++) {
                this.sampleSizes[i] = IsoTypeReader.readUInt32(byteBuffer);
            }
        }
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    protected void getContent(ByteBuffer byteBuffer) {
        writeVersionAndFlags(byteBuffer);
        IsoTypeWriter.writeUInt32(byteBuffer, this.sampleSize);
        if (this.sampleSize == 0) {
            IsoTypeWriter.writeUInt32(byteBuffer, this.sampleSizes.length);
            for (long j : this.sampleSizes) {
                IsoTypeWriter.writeUInt32(byteBuffer, j);
            }
            return;
        }
        IsoTypeWriter.writeUInt32(byteBuffer, this.sampleCount);
    }

    public String toString() {
        return "SampleSizeBox[sampleSize=" + getSampleSize() + ";sampleCount=" + getSampleCount() + "]";
    }
}
