package com.zzbwuhan.beard;

/* loaded from: classes4.dex */
public class BCrypto {
    public static native int auth(String str, String str2);

    public static native long decodeKey(String str, int i);

    public static native byte[] decodeVideoBuf(byte[] bArr, int i, long j, int i2);

    public static native int decodeVideoBuf2(byte[] bArr, int i, long j, int i2);

    public static native void releaseKey(long j);

    static {
        System.loadLibrary("bcrypto");
    }
}
