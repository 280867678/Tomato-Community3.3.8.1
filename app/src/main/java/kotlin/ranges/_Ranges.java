package kotlin.ranges;

import java.util.NoSuchElementException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.random.RandomKt;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: kotlin.ranges.RangesKt___RangesKt */
/* loaded from: classes4.dex */
public class _Ranges extends RangesKt__RangesKt {
    public static int coerceAtLeast(int i, int i2) {
        return i < i2 ? i2 : i;
    }

    public static int coerceAtMost(int i, int i2) {
        return i > i2 ? i2 : i;
    }

    public static int random(Ranges random, Random random2) {
        Intrinsics.checkParameterIsNotNull(random, "$this$random");
        Intrinsics.checkParameterIsNotNull(random2, "random");
        try {
            return RandomKt.nextInt(random2, random);
        } catch (IllegalArgumentException e) {
            throw new NoSuchElementException(e.getMessage());
        }
    }

    public static Progressions downTo(int i, int i2) {
        return Progressions.Companion.fromClosedRange(i, i2, -1);
    }

    public static Ranges until(int i, int i2) {
        if (i2 <= Integer.MIN_VALUE) {
            return Ranges.Companion.getEMPTY();
        }
        return new Ranges(i, i2 - 1);
    }

    public static int coerceIn(int i, int i2, int i3) {
        if (i2 <= i3) {
            return i < i2 ? i2 : i > i3 ? i3 : i;
        }
        throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + i3 + " is less than minimum " + i2 + '.');
    }
}
