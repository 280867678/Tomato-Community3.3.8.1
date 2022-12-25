package kotlin.collections;

import kotlin.comparisons.Comparisons;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: Collections.kt */
/* loaded from: classes4.dex */
public final class CollectionsKt__CollectionsKt$binarySearchBy$1 extends Lambda implements Function1<T, Integer> {
    final /* synthetic */ Comparable $key;
    final /* synthetic */ Function1 $selector;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CollectionsKt__CollectionsKt$binarySearchBy$1(Function1 function1, Comparable comparable) {
        super(1);
        this.$selector = function1;
        this.$key = comparable;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke  reason: collision with other method in class */
    public /* bridge */ /* synthetic */ Integer mo6794invoke(Object obj) {
        return Integer.valueOf(mo6794invoke((CollectionsKt__CollectionsKt$binarySearchBy$1) obj));
    }

    /* JADX WARN: Can't rename method to resolve collision */
    /* JADX WARN: Type inference failed for: r2v3, types: [int, java.lang.Integer] */
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Integer mo6794invoke(T t) {
        ?? compareValues;
        compareValues = Comparisons.compareValues((Comparable) this.$selector.mo6794invoke(t), this.$key);
        return compareValues;
    }
}
