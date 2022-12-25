package com.googlecode.mp4parser.boxes.apple;

import com.coremedia.iso.IsoTypeReader;
import com.coremedia.iso.IsoTypeWriter;
import com.googlecode.mp4parser.AbstractFullBox;
import java.nio.ByteBuffer;

/* loaded from: classes3.dex */
public class BaseMediaInfoAtom extends AbstractFullBox {
    public static final String TYPE = "gmin";
    short balance;
    short reserved;
    short graphicsMode = 64;
    int opColorR = 32768;
    int opColorG = 32768;
    int opColorB = 32768;

    @Override // com.googlecode.mp4parser.AbstractBox
    protected long getContentSize() {
        return 16L;
    }

    public BaseMediaInfoAtom() {
        super(TYPE);
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    protected void getContent(ByteBuffer byteBuffer) {
        writeVersionAndFlags(byteBuffer);
        byteBuffer.putShort(this.graphicsMode);
        IsoTypeWriter.writeUInt16(byteBuffer, this.opColorR);
        IsoTypeWriter.writeUInt16(byteBuffer, this.opColorG);
        IsoTypeWriter.writeUInt16(byteBuffer, this.opColorB);
        byteBuffer.putShort(this.balance);
        byteBuffer.putShort(this.reserved);
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    protected void _parseDetails(ByteBuffer byteBuffer) {
        parseVersionAndFlags(byteBuffer);
        this.graphicsMode = byteBuffer.getShort();
        this.opColorR = IsoTypeReader.readUInt16(byteBuffer);
        this.opColorG = IsoTypeReader.readUInt16(byteBuffer);
        this.opColorB = IsoTypeReader.readUInt16(byteBuffer);
        this.balance = byteBuffer.getShort();
        this.reserved = byteBuffer.getShort();
    }

    public short getGraphicsMode() {
        return this.graphicsMode;
    }

    public void setGraphicsMode(short s) {
        this.graphicsMode = s;
    }

    public int getOpColorR() {
        return this.opColorR;
    }

    public void setOpColorR(int i) {
        this.opColorR = i;
    }

    public int getOpColorG() {
        return this.opColorG;
    }

    public void setOpColorG(int i) {
        this.opColorG = i;
    }

    public int getOpColorB() {
        return this.opColorB;
    }

    public void setOpColorB(int i) {
        this.opColorB = i;
    }

    public short getBalance() {
        return this.balance;
    }

    public void setBalance(short s) {
        this.balance = s;
    }

    public short getReserved() {
        return this.reserved;
    }

    public void setReserved(short s) {
        this.reserved = s;
    }

    public String toString() {
        return "BaseMediaInfoAtom{graphicsMode=" + ((int) this.graphicsMode) + ", opColorR=" + this.opColorR + ", opColorG=" + this.opColorG + ", opColorB=" + this.opColorB + ", balance=" + ((int) this.balance) + ", reserved=" + ((int) this.reserved) + '}';
    }
}
