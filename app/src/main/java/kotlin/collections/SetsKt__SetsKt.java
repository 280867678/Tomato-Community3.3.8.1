package kotlin.collections;

import java.util.Set;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Sets.kt */
/* loaded from: classes4.dex */
public class SetsKt__SetsKt extends SetsJVM {
    public static final <T> Set<T> emptySet() {
        return Sets.INSTANCE;
    }

    public static <T> Set<T> setOf(T... elements) {
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        return elements.length > 0 ? _Arrays.toSet(elements) : emptySet();
    }
}
