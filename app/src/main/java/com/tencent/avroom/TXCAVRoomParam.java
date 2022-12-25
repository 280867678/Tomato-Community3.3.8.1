package com.tencent.avroom;

/* loaded from: classes3.dex */
public class TXCAVRoomParam {
    public int authBits = -1;
    public byte[] authBuffer = null;
    public long roomID;

    public TXCAVRoomParam(long j) {
        this.roomID = j;
    }

    public TXCAVRoomParam authBits(int i) {
        this.authBits = i;
        return this;
    }

    public TXCAVRoomParam authBuffer(byte[] bArr) {
        this.authBuffer = bArr;
        return this;
    }

    public long getRoomID() {
        return this.roomID;
    }

    public int getAuthBits() {
        return this.authBits;
    }

    public byte[] getAuthBuffer() {
        return this.authBuffer;
    }
}
