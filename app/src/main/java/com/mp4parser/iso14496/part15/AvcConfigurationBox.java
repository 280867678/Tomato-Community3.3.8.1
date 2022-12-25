package com.mp4parser.iso14496.part15;

import com.googlecode.mp4parser.AbstractBox;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
public final class AvcConfigurationBox extends AbstractBox {
    public static final String TYPE = "avcC";
    public AvcDecoderConfigurationRecord avcDecoderConfigurationRecord = new AvcDecoderConfigurationRecord();

    public AvcConfigurationBox() {
        super(TYPE);
    }

    public int getConfigurationVersion() {
        return this.avcDecoderConfigurationRecord.configurationVersion;
    }

    public int getAvcProfileIndication() {
        return this.avcDecoderConfigurationRecord.avcProfileIndication;
    }

    public int getProfileCompatibility() {
        return this.avcDecoderConfigurationRecord.profileCompatibility;
    }

    public int getAvcLevelIndication() {
        return this.avcDecoderConfigurationRecord.avcLevelIndication;
    }

    public int getLengthSizeMinusOne() {
        return this.avcDecoderConfigurationRecord.lengthSizeMinusOne;
    }

    public List<byte[]> getSequenceParameterSets() {
        return Collections.unmodifiableList(this.avcDecoderConfigurationRecord.sequenceParameterSets);
    }

    public List<byte[]> getPictureParameterSets() {
        return Collections.unmodifiableList(this.avcDecoderConfigurationRecord.pictureParameterSets);
    }

    public void setConfigurationVersion(int i) {
        this.avcDecoderConfigurationRecord.configurationVersion = i;
    }

    public void setAvcProfileIndication(int i) {
        this.avcDecoderConfigurationRecord.avcProfileIndication = i;
    }

    public void setProfileCompatibility(int i) {
        this.avcDecoderConfigurationRecord.profileCompatibility = i;
    }

    public void setAvcLevelIndication(int i) {
        this.avcDecoderConfigurationRecord.avcLevelIndication = i;
    }

    public void setLengthSizeMinusOne(int i) {
        this.avcDecoderConfigurationRecord.lengthSizeMinusOne = i;
    }

    public void setSequenceParameterSets(List<byte[]> list) {
        this.avcDecoderConfigurationRecord.sequenceParameterSets = list;
    }

    public void setPictureParameterSets(List<byte[]> list) {
        this.avcDecoderConfigurationRecord.pictureParameterSets = list;
    }

    public int getChromaFormat() {
        return this.avcDecoderConfigurationRecord.chromaFormat;
    }

    public void setChromaFormat(int i) {
        this.avcDecoderConfigurationRecord.chromaFormat = i;
    }

    public int getBitDepthLumaMinus8() {
        return this.avcDecoderConfigurationRecord.bitDepthLumaMinus8;
    }

    public void setBitDepthLumaMinus8(int i) {
        this.avcDecoderConfigurationRecord.bitDepthLumaMinus8 = i;
    }

    public int getBitDepthChromaMinus8() {
        return this.avcDecoderConfigurationRecord.bitDepthChromaMinus8;
    }

    public void setBitDepthChromaMinus8(int i) {
        this.avcDecoderConfigurationRecord.bitDepthChromaMinus8 = i;
    }

    public List<byte[]> getSequenceParameterSetExts() {
        return this.avcDecoderConfigurationRecord.sequenceParameterSetExts;
    }

    public void setSequenceParameterSetExts(List<byte[]> list) {
        this.avcDecoderConfigurationRecord.sequenceParameterSetExts = list;
    }

    public boolean hasExts() {
        return this.avcDecoderConfigurationRecord.hasExts;
    }

    public void setHasExts(boolean z) {
        this.avcDecoderConfigurationRecord.hasExts = z;
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    public void _parseDetails(ByteBuffer byteBuffer) {
        this.avcDecoderConfigurationRecord = new AvcDecoderConfigurationRecord(byteBuffer);
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    public long getContentSize() {
        return this.avcDecoderConfigurationRecord.getContentSize();
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    public void getContent(ByteBuffer byteBuffer) {
        this.avcDecoderConfigurationRecord.getContent(byteBuffer);
    }

    public String[] getSPS() {
        return this.avcDecoderConfigurationRecord.getSPS();
    }

    public String[] getPPS() {
        return this.avcDecoderConfigurationRecord.getPPS();
    }

    public AvcDecoderConfigurationRecord getavcDecoderConfigurationRecord() {
        return this.avcDecoderConfigurationRecord;
    }

    public String toString() {
        return "AvcConfigurationBox{avcDecoderConfigurationRecord=" + this.avcDecoderConfigurationRecord + '}';
    }
}
