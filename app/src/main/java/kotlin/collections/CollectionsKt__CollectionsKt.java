package kotlin.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.Ranges;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Collections.kt */
/* loaded from: classes4.dex */
public class CollectionsKt__CollectionsKt extends CollectionsJVM {
    public static <T> List<T> emptyList() {
        return EmptyList.INSTANCE;
    }

    public static <T> List<T> mutableListOf(T... elements) {
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        return elements.length == 0 ? new ArrayList() : new ArrayList(new Collections(elements, true));
    }

    public static <T> ArrayList<T> arrayListOf(T... elements) {
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        return elements.length == 0 ? new ArrayList<>() : new ArrayList<>(new Collections(elements, true));
    }

    public static Ranges getIndices(Collection<?> indices) {
        Intrinsics.checkParameterIsNotNull(indices, "$this$indices");
        return new Ranges(0, indices.size() - 1);
    }

    public static final <T> int getLastIndex(List<? extends T> lastIndex) {
        Intrinsics.checkParameterIsNotNull(lastIndex, "$this$lastIndex");
        return lastIndex.size() - 1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final <T> List<T> optimizeReadOnlyList(List<? extends T> optimizeReadOnlyList) {
        List<T> emptyList;
        List<T> listOf;
        Intrinsics.checkParameterIsNotNull(optimizeReadOnlyList, "$this$optimizeReadOnlyList");
        int size = optimizeReadOnlyList.size();
        if (size == 0) {
            emptyList = emptyList();
            return emptyList;
        } else if (size != 1) {
            return optimizeReadOnlyList;
        } else {
            listOf = CollectionsJVM.listOf(optimizeReadOnlyList.get(0));
            return listOf;
        }
    }

    public static void throwIndexOverflow() {
        throw new ArithmeticException("Index overflow has happened.");
    }
}
