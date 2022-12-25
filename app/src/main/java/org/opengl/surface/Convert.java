package org.opengl.surface;

import java.math.BigInteger;

/* loaded from: classes4.dex */
public class Convert {
    private static BigInteger twoP64 = BigInteger.ZERO.flipBit(64);

    public static Object fromBuffer(short[] sArr) {
        return sArr;
    }

    public static Object fromBool(boolean z) {
        return Boolean.valueOf(z);
    }

    public static Object fromInt(int i) {
        return Integer.valueOf(i);
    }

    public static Object fromUInt(long j) {
        return Long.valueOf(j);
    }

    public static Object fromUInt64(long j) {
        BigInteger valueOf = BigInteger.valueOf(j);
        return j < 0 ? valueOf.add(twoP64) : valueOf;
    }

    public static Object fromFloat(float f) {
        return Float.valueOf(f);
    }
}
