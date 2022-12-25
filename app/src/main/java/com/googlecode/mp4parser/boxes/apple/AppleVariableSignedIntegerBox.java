package com.googlecode.mp4parser.boxes.apple;

import com.coremedia.iso.IsoTypeReaderVariable;
import com.coremedia.iso.IsoTypeWriterVariable;
import java.nio.ByteBuffer;

/* loaded from: classes3.dex */
public abstract class AppleVariableSignedIntegerBox extends AppleDataBox {
    int intLength = 1;
    long value;

    /* JADX INFO: Access modifiers changed from: protected */
    public AppleVariableSignedIntegerBox(String str) {
        super(str, 15);
    }

    public int getIntLength() {
        return this.intLength;
    }

    public void setIntLength(int i) {
        this.intLength = i;
    }

    public long getValue() {
        if (!isParsed()) {
            parseDetails();
        }
        return this.value;
    }

    public void setValue(long j) {
        if (j <= 127 && j > -128) {
            this.intLength = 1;
        } else if (j <= 32767 && j > -32768 && this.intLength < 2) {
            this.intLength = 2;
        } else if (j <= 8388607 && j > -8388608 && this.intLength < 3) {
            this.intLength = 3;
        } else {
            this.intLength = 4;
        }
        this.value = j;
    }

    @Override // com.googlecode.mp4parser.boxes.apple.AppleDataBox
    protected byte[] writeData() {
        int dataLength = getDataLength();
        ByteBuffer wrap = ByteBuffer.wrap(new byte[dataLength]);
        IsoTypeWriterVariable.write(this.value, wrap, dataLength);
        return wrap.array();
    }

    @Override // com.googlecode.mp4parser.boxes.apple.AppleDataBox
    protected void parseData(ByteBuffer byteBuffer) {
        int remaining = byteBuffer.remaining();
        this.value = IsoTypeReaderVariable.read(byteBuffer, remaining);
        this.intLength = remaining;
    }

    @Override // com.googlecode.mp4parser.boxes.apple.AppleDataBox
    protected int getDataLength() {
        return this.intLength;
    }
}
