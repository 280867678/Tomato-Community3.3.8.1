package com.p097ta.utdid2.p098a.p099a;

/* renamed from: com.ta.utdid2.a.a.f */
/* loaded from: classes3.dex */
public class C3205f {

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.ta.utdid2.a.a.f$a */
    /* loaded from: classes3.dex */
    public static class C3207a {

        /* renamed from: d */
        public int[] f1902d;

        /* renamed from: x */
        public int f1903x;

        /* renamed from: y */
        public int f1904y;

        private C3207a() {
            this.f1902d = new int[256];
        }
    }

    /* renamed from: a */
    public static byte[] m3650a(byte[] bArr) {
        C3207a m3651a;
        if (bArr == null || (m3651a = m3651a("QrMgt8GGYI6T52ZY5AnhtxkLzb8egpFn3j5JELI8H6wtACbUnZ5cc3aYTsTRbmkAkRJeYbtx92LPBWm7nBO9UIl7y5i5MQNmUZNf5QENurR5tGyo7yJ2G0MBjWvy6iAtlAbacKP0SwOUeUWx5dsBdyhxa7Id1APtybSdDgicBDuNjI0mlZFUzZSS9dmN8lBD0WTVOMz0pRZbR3cysomRXOO1ghqjJdTcyDIxzpNAEszN8RMGjrzyU7Hjbmwi6YNK")) == null) {
            return null;
        }
        return m3649a(bArr, m3651a);
    }

    /* renamed from: a */
    private static C3207a m3651a(String str) {
        if (str != null) {
            C3207a c3207a = new C3207a();
            for (int i = 0; i < 256; i++) {
                c3207a.f1902d[i] = i;
            }
            c3207a.f1903x = 0;
            c3207a.f1904y = 0;
            int i2 = 0;
            int i3 = 0;
            for (int i4 = 0; i4 < 256; i4++) {
                try {
                    i3 = ((str.charAt(i2) + c3207a.f1902d[i4]) + i3) % 256;
                    int i5 = c3207a.f1902d[i4];
                    c3207a.f1902d[i4] = c3207a.f1902d[i3];
                    c3207a.f1902d[i3] = i5;
                    i2 = (i2 + 1) % str.length();
                } catch (Exception unused) {
                    return null;
                }
            }
            return c3207a;
        }
        return null;
    }

    /* renamed from: a */
    private static byte[] m3649a(byte[] bArr, C3207a c3207a) {
        if (bArr == null || c3207a == null) {
            return null;
        }
        int i = c3207a.f1903x;
        int i2 = c3207a.f1904y;
        for (int i3 = 0; i3 < bArr.length; i3++) {
            i = (i + 1) % 256;
            int[] iArr = c3207a.f1902d;
            i2 = (iArr[i] + i2) % 256;
            int i4 = iArr[i];
            iArr[i] = iArr[i2];
            iArr[i2] = i4;
            bArr[i3] = (byte) (iArr[(iArr[i] + iArr[i2]) % 256] ^ bArr[i3]);
        }
        c3207a.f1903x = i;
        c3207a.f1904y = i2;
        return bArr;
    }
}
