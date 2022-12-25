package com.coremedia.iso.boxes.sampleentry;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.IsoTypeReader;
import com.coremedia.iso.IsoTypeWriter;
import com.googlecode.mp4parser.AbstractBox;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public class AmrSpecificBox extends AbstractBox {
    public static final String TYPE = "damr";
    private int decoderVersion;
    private int framesPerSample;
    private int modeChangePeriod;
    private int modeSet;
    private String vendor;

    @Override // com.googlecode.mp4parser.AbstractBox
    protected long getContentSize() {
        return 9L;
    }

    public AmrSpecificBox() {
        super(TYPE);
    }

    public String getVendor() {
        return this.vendor;
    }

    public int getDecoderVersion() {
        return this.decoderVersion;
    }

    public int getModeSet() {
        return this.modeSet;
    }

    public int getModeChangePeriod() {
        return this.modeChangePeriod;
    }

    public int getFramesPerSample() {
        return this.framesPerSample;
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    public void _parseDetails(ByteBuffer byteBuffer) {
        byte[] bArr = new byte[4];
        byteBuffer.get(bArr);
        this.vendor = IsoFile.bytesToFourCC(bArr);
        this.decoderVersion = IsoTypeReader.readUInt8(byteBuffer);
        this.modeSet = IsoTypeReader.readUInt16(byteBuffer);
        this.modeChangePeriod = IsoTypeReader.readUInt8(byteBuffer);
        this.framesPerSample = IsoTypeReader.readUInt8(byteBuffer);
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    public void getContent(ByteBuffer byteBuffer) {
        byteBuffer.put(IsoFile.fourCCtoBytes(this.vendor));
        IsoTypeWriter.writeUInt8(byteBuffer, this.decoderVersion);
        IsoTypeWriter.writeUInt16(byteBuffer, this.modeSet);
        IsoTypeWriter.writeUInt8(byteBuffer, this.modeChangePeriod);
        IsoTypeWriter.writeUInt8(byteBuffer, this.framesPerSample);
    }

    public String toString() {
        return "AmrSpecificBox[vendor=" + getVendor() + ";decoderVersion=" + getDecoderVersion() + ";modeSet=" + getModeSet() + ";modeChangePeriod=" + getModeChangePeriod() + ";framesPerSample=" + getFramesPerSample() + "]";
    }
}
