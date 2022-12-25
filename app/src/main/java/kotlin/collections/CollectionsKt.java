package kotlin.collections;

import java.util.List;

/* loaded from: classes4.dex */
public final class CollectionsKt extends _Collections {
    public static /* bridge */ /* synthetic */ <T> int collectionSizeOrDefault(Iterable<? extends T> iterable, int i) {
        return Iterables.collectionSizeOrDefault(iterable, i);
    }

    public static /* bridge */ /* synthetic */ <T> List<T> emptyList() {
        return CollectionsKt__CollectionsKt.emptyList();
    }

    public static /* bridge */ /* synthetic */ <T> T first(List<? extends T> list) {
        return (T) _Collections.first((List<? extends Object>) list);
    }

    public static /* bridge */ /* synthetic */ <T> T last(List<? extends T> list) {
        return (T) _Collections.last(list);
    }

    public static /* bridge */ /* synthetic */ <T> List<T> listOf(T t) {
        return CollectionsJVM.listOf(t);
    }

    public static /* bridge */ /* synthetic */ <T> T single(Iterable<? extends T> iterable) {
        return (T) _Collections.single(iterable);
    }

    public static /* bridge */ /* synthetic */ void throwIndexOverflow() {
        CollectionsKt__CollectionsKt.throwIndexOverflow();
        throw null;
    }
}
