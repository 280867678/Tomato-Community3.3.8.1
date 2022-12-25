package kotlin.collections;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Tuples;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: kotlin.collections.MapsKt__MapsKt */
/* loaded from: classes4.dex */
public class Maps extends MapsJVM {
    public static <K, V> Map<K, V> mutableMapOf(Tuples<? extends K, ? extends V>... pairs) {
        Intrinsics.checkParameterIsNotNull(pairs, "pairs");
        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(pairs.length));
        putAll(linkedHashMap, pairs);
        return linkedHashMap;
    }

    public static int mapCapacity(int i) {
        if (i < 3) {
            return i + 1;
        }
        if (i >= 1073741824) {
            return Integer.MAX_VALUE;
        }
        return i + (i / 3);
    }

    public static final <K, V> void putAll(Map<? super K, ? super V> putAll, Tuples<? extends K, ? extends V>[] pairs) {
        Intrinsics.checkParameterIsNotNull(putAll, "$this$putAll");
        Intrinsics.checkParameterIsNotNull(pairs, "pairs");
        for (Tuples<? extends K, ? extends V> tuples : pairs) {
            putAll.put((K) tuples.component1(), (V) tuples.component2());
        }
    }
}
