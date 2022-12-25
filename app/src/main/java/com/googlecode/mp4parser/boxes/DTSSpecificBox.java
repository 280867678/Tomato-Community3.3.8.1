package com.googlecode.mp4parser.boxes;

import com.coremedia.iso.IsoTypeReader;
import com.coremedia.iso.IsoTypeWriter;
import com.googlecode.mp4parser.AbstractBox;
import com.googlecode.mp4parser.annotations.DoNotParseDetail;
import com.googlecode.mp4parser.boxes.mp4.objectdescriptors.BitReaderBuffer;
import com.googlecode.mp4parser.boxes.mp4.objectdescriptors.BitWriterBuffer;
import java.nio.ByteBuffer;

/* loaded from: classes3.dex */
public class DTSSpecificBox extends AbstractBox {
    public static final String TYPE = "ddts";
    long DTSSamplingFrequency;
    int LBRDurationMod;
    long avgBitRate;
    int channelLayout;
    int coreLFEPresent;
    int coreLayout;
    int coreSize;
    int frameDuration;
    long maxBitRate;
    int multiAssetFlag;
    int pcmSampleDepth;
    int representationType;
    int reserved;
    int reservedBoxPresent;
    int stereoDownmix;
    int streamConstruction;

    @Override // com.googlecode.mp4parser.AbstractBox
    protected long getContentSize() {
        return 20L;
    }

    public DTSSpecificBox() {
        super(TYPE);
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    public void _parseDetails(ByteBuffer byteBuffer) {
        this.DTSSamplingFrequency = IsoTypeReader.readUInt32(byteBuffer);
        this.maxBitRate = IsoTypeReader.readUInt32(byteBuffer);
        this.avgBitRate = IsoTypeReader.readUInt32(byteBuffer);
        this.pcmSampleDepth = IsoTypeReader.readUInt8(byteBuffer);
        BitReaderBuffer bitReaderBuffer = new BitReaderBuffer(byteBuffer);
        this.frameDuration = bitReaderBuffer.readBits(2);
        this.streamConstruction = bitReaderBuffer.readBits(5);
        this.coreLFEPresent = bitReaderBuffer.readBits(1);
        this.coreLayout = bitReaderBuffer.readBits(6);
        this.coreSize = bitReaderBuffer.readBits(14);
        this.stereoDownmix = bitReaderBuffer.readBits(1);
        this.representationType = bitReaderBuffer.readBits(3);
        this.channelLayout = bitReaderBuffer.readBits(16);
        this.multiAssetFlag = bitReaderBuffer.readBits(1);
        this.LBRDurationMod = bitReaderBuffer.readBits(1);
        this.reservedBoxPresent = bitReaderBuffer.readBits(1);
        this.reserved = bitReaderBuffer.readBits(5);
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    protected void getContent(ByteBuffer byteBuffer) {
        IsoTypeWriter.writeUInt32(byteBuffer, this.DTSSamplingFrequency);
        IsoTypeWriter.writeUInt32(byteBuffer, this.maxBitRate);
        IsoTypeWriter.writeUInt32(byteBuffer, this.avgBitRate);
        IsoTypeWriter.writeUInt8(byteBuffer, this.pcmSampleDepth);
        BitWriterBuffer bitWriterBuffer = new BitWriterBuffer(byteBuffer);
        bitWriterBuffer.writeBits(this.frameDuration, 2);
        bitWriterBuffer.writeBits(this.streamConstruction, 5);
        bitWriterBuffer.writeBits(this.coreLFEPresent, 1);
        bitWriterBuffer.writeBits(this.coreLayout, 6);
        bitWriterBuffer.writeBits(this.coreSize, 14);
        bitWriterBuffer.writeBits(this.stereoDownmix, 1);
        bitWriterBuffer.writeBits(this.representationType, 3);
        bitWriterBuffer.writeBits(this.channelLayout, 16);
        bitWriterBuffer.writeBits(this.multiAssetFlag, 1);
        bitWriterBuffer.writeBits(this.LBRDurationMod, 1);
        bitWriterBuffer.writeBits(this.reservedBoxPresent, 1);
        bitWriterBuffer.writeBits(this.reserved, 5);
    }

    public long getAvgBitRate() {
        return this.avgBitRate;
    }

    public void setAvgBitRate(long j) {
        this.avgBitRate = j;
    }

    public long getDTSSamplingFrequency() {
        return this.DTSSamplingFrequency;
    }

    public void setDTSSamplingFrequency(long j) {
        this.DTSSamplingFrequency = j;
    }

    public long getMaxBitRate() {
        return this.maxBitRate;
    }

    public void setMaxBitRate(long j) {
        this.maxBitRate = j;
    }

    public int getPcmSampleDepth() {
        return this.pcmSampleDepth;
    }

    public void setPcmSampleDepth(int i) {
        this.pcmSampleDepth = i;
    }

    public int getFrameDuration() {
        return this.frameDuration;
    }

    public void setFrameDuration(int i) {
        this.frameDuration = i;
    }

    public int getStreamConstruction() {
        return this.streamConstruction;
    }

    public void setStreamConstruction(int i) {
        this.streamConstruction = i;
    }

    public int getCoreLFEPresent() {
        return this.coreLFEPresent;
    }

    public void setCoreLFEPresent(int i) {
        this.coreLFEPresent = i;
    }

    public int getCoreLayout() {
        return this.coreLayout;
    }

    public void setCoreLayout(int i) {
        this.coreLayout = i;
    }

    public int getCoreSize() {
        return this.coreSize;
    }

    public void setCoreSize(int i) {
        this.coreSize = i;
    }

    public int getStereoDownmix() {
        return this.stereoDownmix;
    }

    public void setStereoDownmix(int i) {
        this.stereoDownmix = i;
    }

    public int getRepresentationType() {
        return this.representationType;
    }

    public void setRepresentationType(int i) {
        this.representationType = i;
    }

    public int getChannelLayout() {
        return this.channelLayout;
    }

    public void setChannelLayout(int i) {
        this.channelLayout = i;
    }

    public int getMultiAssetFlag() {
        return this.multiAssetFlag;
    }

    public void setMultiAssetFlag(int i) {
        this.multiAssetFlag = i;
    }

    public int getLBRDurationMod() {
        return this.LBRDurationMod;
    }

    public void setLBRDurationMod(int i) {
        this.LBRDurationMod = i;
    }

    public int getReserved() {
        return this.reserved;
    }

    public void setReserved(int i) {
        this.reserved = i;
    }

    public int getReservedBoxPresent() {
        return this.reservedBoxPresent;
    }

    public void setReservedBoxPresent(int i) {
        this.reservedBoxPresent = i;
    }

    @DoNotParseDetail
    public int[] getDashAudioChannelConfiguration() {
        int i;
        int i2;
        int channelLayout = getChannelLayout();
        if ((channelLayout & 1) == 1) {
            i = 1;
            i2 = 4;
        } else {
            i = 0;
            i2 = 0;
        }
        if ((channelLayout & 2) == 2) {
            i += 2;
            i2 = i2 | 1 | 2;
        }
        if ((channelLayout & 4) == 4) {
            i += 2;
            i2 = i2 | 16 | 32;
        }
        if ((channelLayout & 8) == 8) {
            i++;
            i2 |= 8;
        }
        if ((channelLayout & 16) == 16) {
            i++;
            i2 |= 256;
        }
        if ((channelLayout & 32) == 32) {
            i += 2;
            i2 = i2 | 4096 | 16384;
        }
        if ((channelLayout & 64) == 64) {
            i += 2;
            i2 = i2 | 16 | 32;
        }
        if ((channelLayout & 128) == 128) {
            i++;
            i2 |= 8192;
        }
        if ((channelLayout & 256) == 256) {
            i++;
            i2 |= 2048;
        }
        if ((channelLayout & 512) == 512) {
            i += 2;
            i2 = i2 | 64 | 128;
        }
        if ((channelLayout & 1024) == 1024) {
            i += 2;
            i2 = i2 | 512 | 1024;
        }
        if ((channelLayout & 2048) == 2048) {
            i += 2;
            i2 = i2 | 16 | 32;
        }
        if ((channelLayout & 4096) == 4096) {
            i++;
            i2 |= 8;
        }
        if ((channelLayout & 8192) == 8192) {
            i += 2;
            i2 = i2 | 16 | 32;
        }
        if ((channelLayout & 16384) == 16384) {
            i++;
            i2 |= 65536;
        }
        if ((32768 & channelLayout) == 32768) {
            i += 2;
            i2 = 131072 | 32768 | i2;
        }
        if ((65536 & channelLayout) == 65536) {
            i++;
        }
        if ((channelLayout & 131072) == 131072) {
            i += 2;
        }
        return new int[]{i, i2};
    }
}
