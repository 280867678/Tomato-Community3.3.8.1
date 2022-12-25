package kotlin.random;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: PlatformRandom.kt */
/* loaded from: classes4.dex */
public final class FallbackThreadLocalRandom extends PlatformRandom {
    private final FallbackThreadLocalRandom$implStorage$1 implStorage = new ThreadLocal<java.util.Random>() { // from class: kotlin.random.FallbackThreadLocalRandom$implStorage$1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // java.lang.ThreadLocal
        public java.util.Random initialValue() {
            return new java.util.Random();
        }
    };

    @Override // kotlin.random.PlatformRandom
    public java.util.Random getImpl() {
        java.util.Random random = get();
        Intrinsics.checkExpressionValueIsNotNull(random, "implStorage.get()");
        return random;
    }
}
