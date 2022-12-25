package com.googlecode.mp4parser.boxes.apple;

import java.nio.ByteBuffer;

/* loaded from: classes3.dex */
public class AppleDiskNumberBox extends AppleDataBox {

    /* renamed from: a */
    int f1347a;

    /* renamed from: b */
    short f1348b;

    @Override // com.googlecode.mp4parser.boxes.apple.AppleDataBox
    protected int getDataLength() {
        return 6;
    }

    public AppleDiskNumberBox() {
        super("disk", 0);
    }

    public int getA() {
        return this.f1347a;
    }

    public void setA(int i) {
        this.f1347a = i;
    }

    public short getB() {
        return this.f1348b;
    }

    public void setB(short s) {
        this.f1348b = s;
    }

    @Override // com.googlecode.mp4parser.boxes.apple.AppleDataBox
    protected byte[] writeData() {
        ByteBuffer allocate = ByteBuffer.allocate(6);
        allocate.putInt(this.f1347a);
        allocate.putShort(this.f1348b);
        return allocate.array();
    }

    @Override // com.googlecode.mp4parser.boxes.apple.AppleDataBox
    protected void parseData(ByteBuffer byteBuffer) {
        this.f1347a = byteBuffer.getInt();
        this.f1348b = byteBuffer.getShort();
    }
}
