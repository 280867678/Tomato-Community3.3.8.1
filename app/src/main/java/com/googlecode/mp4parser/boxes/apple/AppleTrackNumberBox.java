package com.googlecode.mp4parser.boxes.apple;

import java.nio.ByteBuffer;

/* loaded from: classes3.dex */
public class AppleTrackNumberBox extends AppleDataBox {

    /* renamed from: a */
    int f1350a;

    /* renamed from: b */
    int f1351b;

    @Override // com.googlecode.mp4parser.boxes.apple.AppleDataBox
    protected int getDataLength() {
        return 8;
    }

    public AppleTrackNumberBox() {
        super("trkn", 0);
    }

    public int getA() {
        return this.f1350a;
    }

    public void setA(int i) {
        this.f1350a = i;
    }

    public int getB() {
        return this.f1351b;
    }

    public void setB(int i) {
        this.f1351b = i;
    }

    @Override // com.googlecode.mp4parser.boxes.apple.AppleDataBox
    protected byte[] writeData() {
        ByteBuffer allocate = ByteBuffer.allocate(8);
        allocate.putInt(this.f1350a);
        allocate.putInt(this.f1351b);
        return allocate.array();
    }

    @Override // com.googlecode.mp4parser.boxes.apple.AppleDataBox
    protected void parseData(ByteBuffer byteBuffer) {
        this.f1350a = byteBuffer.getInt();
        this.f1351b = byteBuffer.getInt();
    }
}
