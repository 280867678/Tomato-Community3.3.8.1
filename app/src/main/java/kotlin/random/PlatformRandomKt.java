package kotlin.random;

/* compiled from: PlatformRandom.kt */
/* loaded from: classes4.dex */
public final class PlatformRandomKt {
    public static final double doubleFromParts(int i, int i2) {
        return ((i << 27) + i2) / 9007199254740992L;
    }
}
