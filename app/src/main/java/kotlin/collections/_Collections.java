package kotlin.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.text.StringsKt;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: kotlin.collections.CollectionsKt___CollectionsKt */
/* loaded from: classes4.dex */
public class _Collections extends _CollectionsJvm {
    public static final <T> T first(Iterable<? extends T> first) {
        Intrinsics.checkParameterIsNotNull(first, "$this$first");
        if (first instanceof List) {
            return (T) CollectionsKt.first((List<? extends Object>) first);
        }
        Iterator<? extends T> it2 = first.iterator();
        if (!it2.hasNext()) {
            throw new NoSuchElementException("Collection is empty.");
        }
        return it2.next();
    }

    public static <T> T first(List<? extends T> first) {
        Intrinsics.checkParameterIsNotNull(first, "$this$first");
        if (first.isEmpty()) {
            throw new NoSuchElementException("List is empty.");
        }
        return first.get(0);
    }

    public static <T> T last(List<? extends T> last) {
        Intrinsics.checkParameterIsNotNull(last, "$this$last");
        if (last.isEmpty()) {
            throw new NoSuchElementException("List is empty.");
        }
        return last.get(CollectionsKt__CollectionsKt.getLastIndex(last));
    }

    public static <T> T single(Iterable<? extends T> single) {
        Intrinsics.checkParameterIsNotNull(single, "$this$single");
        if (single instanceof List) {
            return (T) single((List<? extends Object>) single);
        }
        Iterator<? extends T> it2 = single.iterator();
        if (!it2.hasNext()) {
            throw new NoSuchElementException("Collection is empty.");
        }
        T next = it2.next();
        if (it2.hasNext()) {
            throw new IllegalArgumentException("Collection has more than one element.");
        }
        return next;
    }

    public static final <T> T single(List<? extends T> single) {
        Intrinsics.checkParameterIsNotNull(single, "$this$single");
        int size = single.size();
        if (size != 0) {
            if (size == 1) {
                return single.get(0);
            }
            throw new IllegalArgumentException("List has more than one element.");
        }
        throw new NoSuchElementException("List is empty.");
    }

    public static <T> List<T> take(Iterable<? extends T> take, int i) {
        List<T> listOf;
        List<T> list;
        List<T> emptyList;
        Intrinsics.checkParameterIsNotNull(take, "$this$take");
        int i2 = 0;
        if (!(i >= 0)) {
            throw new IllegalArgumentException(("Requested element count " + i + " is less than zero.").toString());
        } else if (i == 0) {
            emptyList = CollectionsKt__CollectionsKt.emptyList();
            return emptyList;
        } else {
            if (take instanceof Collection) {
                if (i >= ((Collection) take).size()) {
                    list = toList(take);
                    return list;
                } else if (i == 1) {
                    listOf = CollectionsJVM.listOf(first(take));
                    return listOf;
                }
            }
            ArrayList arrayList = new ArrayList(i);
            for (T t : take) {
                int i3 = i2 + 1;
                if (i2 == i) {
                    break;
                }
                arrayList.add(t);
                i2 = i3;
            }
            return CollectionsKt__CollectionsKt.optimizeReadOnlyList(arrayList);
        }
    }

    public static final <T, C extends Collection<? super T>> C toCollection(Iterable<? extends T> toCollection, C destination) {
        Intrinsics.checkParameterIsNotNull(toCollection, "$this$toCollection");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        for (T t : toCollection) {
            destination.add(t);
        }
        return destination;
    }

    public static <T> List<T> toList(Iterable<? extends T> toList) {
        List<T> emptyList;
        List<T> listOf;
        Intrinsics.checkParameterIsNotNull(toList, "$this$toList");
        if (toList instanceof Collection) {
            Collection collection = (Collection) toList;
            int size = collection.size();
            if (size == 0) {
                emptyList = CollectionsKt__CollectionsKt.emptyList();
                return emptyList;
            } else if (size == 1) {
                listOf = CollectionsJVM.listOf(toList instanceof List ? ((List) toList).get(0) : toList.iterator().next());
                return listOf;
            } else {
                return toMutableList(collection);
            }
        }
        return CollectionsKt__CollectionsKt.optimizeReadOnlyList(toMutableList(toList));
    }

    public static final <T> List<T> toMutableList(Iterable<? extends T> toMutableList) {
        Intrinsics.checkParameterIsNotNull(toMutableList, "$this$toMutableList");
        if (toMutableList instanceof Collection) {
            return toMutableList((Collection) toMutableList);
        }
        ArrayList arrayList = new ArrayList();
        toCollection(toMutableList, arrayList);
        return arrayList;
    }

    public static final <T> List<T> toMutableList(Collection<? extends T> toMutableList) {
        Intrinsics.checkParameterIsNotNull(toMutableList, "$this$toMutableList");
        return new ArrayList(toMutableList);
    }

    public static Float max(Iterable<Float> max) {
        Intrinsics.checkParameterIsNotNull(max, "$this$max");
        Iterator<Float> it2 = max.iterator();
        if (!it2.hasNext()) {
            return null;
        }
        float floatValue = it2.next().floatValue();
        if (Float.isNaN(floatValue)) {
            return Float.valueOf(floatValue);
        }
        while (it2.hasNext()) {
            float floatValue2 = it2.next().floatValue();
            if (Float.isNaN(floatValue2)) {
                return Float.valueOf(floatValue2);
            }
            if (floatValue < floatValue2) {
                floatValue = floatValue2;
            }
        }
        return Float.valueOf(floatValue);
    }

    public static final <T, A extends Appendable> A joinTo(Iterable<? extends T> joinTo, A buffer, CharSequence separator, CharSequence prefix, CharSequence postfix, int i, CharSequence truncated, Function1<? super T, ? extends CharSequence> function1) {
        Intrinsics.checkParameterIsNotNull(joinTo, "$this$joinTo");
        Intrinsics.checkParameterIsNotNull(buffer, "buffer");
        Intrinsics.checkParameterIsNotNull(separator, "separator");
        Intrinsics.checkParameterIsNotNull(prefix, "prefix");
        Intrinsics.checkParameterIsNotNull(postfix, "postfix");
        Intrinsics.checkParameterIsNotNull(truncated, "truncated");
        buffer.append(prefix);
        int i2 = 0;
        for (T t : joinTo) {
            i2++;
            if (i2 > 1) {
                buffer.append(separator);
            }
            if (i >= 0 && i2 > i) {
                break;
            }
            StringsKt.appendElement(buffer, t, function1);
        }
        if (i >= 0 && i2 > i) {
            buffer.append(truncated);
        }
        buffer.append(postfix);
        return buffer;
    }

    public static /* synthetic */ String joinToString$default(Iterable iterable, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, int i, CharSequence charSequence4, Function1 function1, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            charSequence = ", ";
        }
        CharSequence charSequence5 = "";
        CharSequence charSequence6 = (i2 & 2) != 0 ? charSequence5 : charSequence2;
        if ((i2 & 4) == 0) {
            charSequence5 = charSequence3;
        }
        int i3 = (i2 & 8) != 0 ? -1 : i;
        if ((i2 & 16) != 0) {
            charSequence4 = "...";
        }
        CharSequence charSequence7 = charSequence4;
        if ((i2 & 32) != 0) {
            function1 = null;
        }
        return joinToString(iterable, charSequence, charSequence6, charSequence5, i3, charSequence7, function1);
    }

    public static final <T> String joinToString(Iterable<? extends T> joinToString, CharSequence separator, CharSequence prefix, CharSequence postfix, int i, CharSequence truncated, Function1<? super T, ? extends CharSequence> function1) {
        Intrinsics.checkParameterIsNotNull(joinToString, "$this$joinToString");
        Intrinsics.checkParameterIsNotNull(separator, "separator");
        Intrinsics.checkParameterIsNotNull(prefix, "prefix");
        Intrinsics.checkParameterIsNotNull(postfix, "postfix");
        Intrinsics.checkParameterIsNotNull(truncated, "truncated");
        StringBuilder sb = new StringBuilder();
        joinTo(joinToString, sb, separator, prefix, postfix, i, truncated, function1);
        String sb2 = sb.toString();
        Intrinsics.checkExpressionValueIsNotNull(sb2, "joinTo(StringBuilder(), …ed, transform).toString()");
        return sb2;
    }

    public static <T> Sequence<T> asSequence(final Iterable<? extends T> asSequence) {
        Intrinsics.checkParameterIsNotNull(asSequence, "$this$asSequence");
        return new Sequence<T>() { // from class: kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1
            @Override // kotlin.sequences.Sequence
            public Iterator<T> iterator() {
                return asSequence.iterator();
            }
        };
    }
}
