package kotlin.collections;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.Ranges;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: kotlin.collections.ArraysKt___ArraysKt */
/* loaded from: classes4.dex */
public class _Arrays extends _ArraysJvm {
    public static final <T> boolean contains(T[] contains, T t) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        return indexOf(contains, t) >= 0;
    }

    public static final boolean contains(byte[] contains, byte b) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        return indexOf(contains, b) >= 0;
    }

    public static final <T> int indexOf(T[] indexOf, T t) {
        Intrinsics.checkParameterIsNotNull(indexOf, "$this$indexOf");
        int i = 0;
        if (t == null) {
            int length = indexOf.length;
            while (i < length) {
                if (indexOf[i] == null) {
                    return i;
                }
                i++;
            }
            return -1;
        }
        int length2 = indexOf.length;
        while (i < length2) {
            if (Intrinsics.areEqual(t, indexOf[i])) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static final int indexOf(byte[] indexOf, byte b) {
        Intrinsics.checkParameterIsNotNull(indexOf, "$this$indexOf");
        int length = indexOf.length;
        for (int i = 0; i < length; i++) {
            if (b == indexOf[i]) {
                return i;
            }
        }
        return -1;
    }

    public static final int lastIndexOf(byte[] lastIndexOf, byte b) {
        Intrinsics.checkParameterIsNotNull(lastIndexOf, "$this$lastIndexOf");
        for (int length = lastIndexOf.length - 1; length >= 0; length--) {
            if (b == lastIndexOf[length]) {
                return length;
            }
        }
        return -1;
    }

    public static char single(char[] single) {
        Intrinsics.checkParameterIsNotNull(single, "$this$single");
        int length = single.length;
        if (length != 0) {
            if (length == 1) {
                return single[0];
            }
            throw new IllegalArgumentException("Array has more than one element.");
        }
        throw new NoSuchElementException("Array is empty.");
    }

    public static <T> T singleOrNull(T[] singleOrNull) {
        Intrinsics.checkParameterIsNotNull(singleOrNull, "$this$singleOrNull");
        if (singleOrNull.length == 1) {
            return singleOrNull[0];
        }
        return null;
    }

    public static List<Byte> slice(byte[] slice, Ranges indices) {
        Intrinsics.checkParameterIsNotNull(slice, "$this$slice");
        Intrinsics.checkParameterIsNotNull(indices, "indices");
        return indices.isEmpty() ? CollectionsKt.emptyList() : _ArraysJvm.asList(_ArraysJvm.copyOfRange(slice, indices.getStart().intValue(), indices.getEndInclusive().intValue() + 1));
    }

    public static final <T, C extends Collection<? super T>> C toCollection(T[] toCollection, C destination) {
        Intrinsics.checkParameterIsNotNull(toCollection, "$this$toCollection");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        for (T t : toCollection) {
            destination.add(t);
        }
        return destination;
    }

    public static final <T> Set<T> toSet(T[] toSet) {
        int mapCapacity;
        Intrinsics.checkParameterIsNotNull(toSet, "$this$toSet");
        int length = toSet.length;
        if (length != 0) {
            if (length == 1) {
                return SetsJVM.setOf(toSet[0]);
            }
            mapCapacity = Maps.mapCapacity(toSet.length);
            LinkedHashSet linkedHashSet = new LinkedHashSet(mapCapacity);
            toCollection(toSet, linkedHashSet);
            return linkedHashSet;
        }
        return SetsKt__SetsKt.emptySet();
    }
}
