package com.google.android.exoplayer2;

import com.google.android.exoplayer2.util.Util;
import java.util.UUID;

/* renamed from: com.google.android.exoplayer2.C */
/* loaded from: classes2.dex */
public final class C1868C {
    public static final int CHANNEL_OUT_7POINT1_SURROUND;
    public static final UUID CLEARKEY_UUID;
    public static final UUID COMMON_PSSH_UUID;
    public static final UUID UUID_NIL;
    public static final UUID WIDEVINE_UUID;

    public static long msToUs(long j) {
        return (j == -9223372036854775807L || j == Long.MIN_VALUE) ? j : j * 1000;
    }

    static {
        CHANNEL_OUT_7POINT1_SURROUND = Util.SDK_INT < 23 ? 1020 : 6396;
        UUID_NIL = new UUID(0L, 0L);
        COMMON_PSSH_UUID = new UUID(1186680826959645954L, -5988876978535335093L);
        CLEARKEY_UUID = new UUID(-2129748144642739255L, 8654423357094679310L);
        WIDEVINE_UUID = new UUID(-1301668207276963122L, -6645017420763422227L);
        new UUID(-7348484286925749626L, -6083546864340672619L);
    }

    public static long usToMs(long j) {
        return (j == -9223372036854775807L || j == Long.MIN_VALUE) ? j : j / 1000;
    }
}
