package kotlin.random;

import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.PrimitiveCompanionObjects;

/* compiled from: Random.kt */
/* loaded from: classes4.dex */
public abstract class Random {
    public static final Default Default = new Default(null);
    private static final Random defaultRandom = PlatformImplementationsKt.IMPLEMENTATIONS.defaultPlatformRandom();

    public abstract int nextBits(int i);

    public int nextInt() {
        return nextBits(32);
    }

    public int nextInt(int i) {
        return nextInt(0, i);
    }

    public int nextInt(int i, int i2) {
        int nextInt;
        int i3;
        int i4;
        RandomKt.checkRangeBounds(i, i2);
        int i5 = i2 - i;
        if (i5 > 0 || i5 == Integer.MIN_VALUE) {
            if (((-i5) & i5) == i5) {
                i4 = nextBits(RandomKt.fastLog2(i5));
            } else {
                do {
                    nextInt = nextInt() >>> 1;
                    i3 = nextInt % i5;
                } while ((nextInt - i3) + (i5 - 1) < 0);
                i4 = i3;
            }
            return i + i4;
        }
        while (true) {
            int nextInt2 = nextInt();
            if (i <= nextInt2 && i2 > nextInt2) {
                return nextInt2;
            }
        }
    }

    public double nextDouble() {
        return PlatformRandomKt.doubleFromParts(nextBits(26), nextBits(27));
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:20:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public double nextDouble(double d, double d2) {
        double nextDouble;
        RandomKt.checkRangeBounds(d, d2);
        double d3 = d2 - d;
        if (Double.isInfinite(d3)) {
            boolean z = true;
            if (!Double.isInfinite(d) && !Double.isNaN(d)) {
                if (Double.isInfinite(d2) || Double.isNaN(d2)) {
                    z = false;
                }
                if (z) {
                    double d4 = 2;
                    double nextDouble2 = nextDouble() * ((d2 / d4) - (d / d4));
                    nextDouble = d + nextDouble2 + nextDouble2;
                    return nextDouble < d2 ? Math.nextAfter(d2, PrimitiveCompanionObjects.INSTANCE.getNEGATIVE_INFINITY()) : nextDouble;
                }
            }
        }
        nextDouble = d + (nextDouble() * d3);
        if (nextDouble < d2) {
        }
    }

    /* compiled from: Random.kt */
    /* loaded from: classes4.dex */
    public static final class Default extends Random {
        private Default() {
        }

        public /* synthetic */ Default(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @Override // kotlin.random.Random
        public int nextBits(int i) {
            return Random.defaultRandom.nextBits(i);
        }

        @Override // kotlin.random.Random
        public int nextInt() {
            return Random.defaultRandom.nextInt();
        }

        @Override // kotlin.random.Random
        public int nextInt(int i) {
            return Random.defaultRandom.nextInt(i);
        }

        @Override // kotlin.random.Random
        public int nextInt(int i, int i2) {
            return Random.defaultRandom.nextInt(i, i2);
        }

        @Override // kotlin.random.Random
        public double nextDouble() {
            return Random.defaultRandom.nextDouble();
        }

        @Override // kotlin.random.Random
        public double nextDouble(double d, double d2) {
            return Random.defaultRandom.nextDouble(d, d2);
        }
    }

    static {
        Companion companion = Companion.INSTANCE;
    }

    /* compiled from: Random.kt */
    /* loaded from: classes4.dex */
    public static final class Companion extends Random {
        public static final Companion INSTANCE = new Companion();

        private Companion() {
        }

        @Override // kotlin.random.Random
        public int nextBits(int i) {
            return Random.Default.nextBits(i);
        }
    }
}
