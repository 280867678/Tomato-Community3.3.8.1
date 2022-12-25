package com.googlecode.mp4parser.util;

/* loaded from: classes3.dex */
public class Math {
    public static long gcd(long j, long j2) {
        while (true) {
            long j3 = j;
            j = j2;
            if (j > 0) {
                j2 = j3 % j;
            } else {
                return j3;
            }
        }
    }

    public static int gcd(int i, int i2) {
        while (true) {
            int i3 = i2;
            int i4 = i;
            i = i3;
            if (i > 0) {
                i2 = i4 % i;
            } else {
                return i4;
            }
        }
    }

    public static long lcm(long j, long j2) {
        return j * (j2 / gcd(j, j2));
    }

    public static int lcm(int i, int i2) {
        return i * (i2 / gcd(i, i2));
    }
}
