package com.mp4parser.iso14496.part15;

import com.coremedia.iso.IsoTypeReader;
import com.coremedia.iso.IsoTypeWriter;
import com.googlecode.mp4parser.AbstractBox;
import java.nio.ByteBuffer;

/* loaded from: classes3.dex */
public class TierInfoBox extends AbstractBox {
    public static final String TYPE = "tiri";
    int constantFrameRate;
    int discardable;
    int frameRate;
    int levelIndication;
    int profileIndication;
    int profile_compatibility;
    int reserved1 = 0;
    int reserved2 = 0;
    int tierID;
    int visualHeight;
    int visualWidth;

    @Override // com.googlecode.mp4parser.AbstractBox
    protected long getContentSize() {
        return 13L;
    }

    public TierInfoBox() {
        super(TYPE);
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    protected void getContent(ByteBuffer byteBuffer) {
        IsoTypeWriter.writeUInt16(byteBuffer, this.tierID);
        IsoTypeWriter.writeUInt8(byteBuffer, this.profileIndication);
        IsoTypeWriter.writeUInt8(byteBuffer, this.profile_compatibility);
        IsoTypeWriter.writeUInt8(byteBuffer, this.levelIndication);
        IsoTypeWriter.writeUInt8(byteBuffer, this.reserved1);
        IsoTypeWriter.writeUInt16(byteBuffer, this.visualWidth);
        IsoTypeWriter.writeUInt16(byteBuffer, this.visualHeight);
        IsoTypeWriter.writeUInt8(byteBuffer, (this.discardable << 6) + (this.constantFrameRate << 4) + this.reserved2);
        IsoTypeWriter.writeUInt16(byteBuffer, this.frameRate);
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    protected void _parseDetails(ByteBuffer byteBuffer) {
        this.tierID = IsoTypeReader.readUInt16(byteBuffer);
        this.profileIndication = IsoTypeReader.readUInt8(byteBuffer);
        this.profile_compatibility = IsoTypeReader.readUInt8(byteBuffer);
        this.levelIndication = IsoTypeReader.readUInt8(byteBuffer);
        this.reserved1 = IsoTypeReader.readUInt8(byteBuffer);
        this.visualWidth = IsoTypeReader.readUInt16(byteBuffer);
        this.visualHeight = IsoTypeReader.readUInt16(byteBuffer);
        int readUInt8 = IsoTypeReader.readUInt8(byteBuffer);
        this.discardable = (readUInt8 & 192) >> 6;
        this.constantFrameRate = (readUInt8 & 48) >> 4;
        this.reserved2 = readUInt8 & 15;
        this.frameRate = IsoTypeReader.readUInt16(byteBuffer);
    }

    public int getTierID() {
        return this.tierID;
    }

    public void setTierID(int i) {
        this.tierID = i;
    }

    public int getProfileIndication() {
        return this.profileIndication;
    }

    public void setProfileIndication(int i) {
        this.profileIndication = i;
    }

    public int getProfile_compatibility() {
        return this.profile_compatibility;
    }

    public void setProfile_compatibility(int i) {
        this.profile_compatibility = i;
    }

    public int getLevelIndication() {
        return this.levelIndication;
    }

    public void setLevelIndication(int i) {
        this.levelIndication = i;
    }

    public int getReserved1() {
        return this.reserved1;
    }

    public void setReserved1(int i) {
        this.reserved1 = i;
    }

    public int getVisualWidth() {
        return this.visualWidth;
    }

    public void setVisualWidth(int i) {
        this.visualWidth = i;
    }

    public int getVisualHeight() {
        return this.visualHeight;
    }

    public void setVisualHeight(int i) {
        this.visualHeight = i;
    }

    public int getDiscardable() {
        return this.discardable;
    }

    public void setDiscardable(int i) {
        this.discardable = i;
    }

    public int getConstantFrameRate() {
        return this.constantFrameRate;
    }

    public void setConstantFrameRate(int i) {
        this.constantFrameRate = i;
    }

    public int getReserved2() {
        return this.reserved2;
    }

    public void setReserved2(int i) {
        this.reserved2 = i;
    }

    public int getFrameRate() {
        return this.frameRate;
    }

    public void setFrameRate(int i) {
        this.frameRate = i;
    }
}
