package kotlin.math;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: kotlin.math.MathKt__MathJVMKt */
/* loaded from: classes4.dex */
public class MathJVM extends MathH {
    public static int roundToInt(double d) {
        if (!Double.isNaN(d)) {
            if (d > Integer.MAX_VALUE) {
                return Integer.MAX_VALUE;
            }
            if (d >= Integer.MIN_VALUE) {
                return (int) Math.round(d);
            }
            return Integer.MIN_VALUE;
        }
        throw new IllegalArgumentException("Cannot round NaN value.");
    }
}
