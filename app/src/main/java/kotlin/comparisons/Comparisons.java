package kotlin.comparisons;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: kotlin.comparisons.ComparisonsKt__ComparisonsKt */
/* loaded from: classes4.dex */
public class Comparisons {
    public static <T extends Comparable<?>> int compareValues(T t, T t2) {
        if (t == t2) {
            return 0;
        }
        if (t == null) {
            return -1;
        }
        if (t2 != null) {
            return t.compareTo(t2);
        }
        return 1;
    }
}
