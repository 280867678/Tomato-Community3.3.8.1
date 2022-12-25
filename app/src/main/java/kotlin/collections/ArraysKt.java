package kotlin.collections;

import java.util.List;

/* loaded from: classes4.dex */
public final class ArraysKt extends _Arrays {
    public static /* bridge */ /* synthetic */ <T> List<T> asList(T[] tArr) {
        return _ArraysJvm.asList(tArr);
    }

    public static /* bridge */ /* synthetic */ char single(char[] cArr) {
        return _Arrays.single(cArr);
    }

    public static /* bridge */ /* synthetic */ <T> T singleOrNull(T[] tArr) {
        return (T) _Arrays.singleOrNull(tArr);
    }
}
