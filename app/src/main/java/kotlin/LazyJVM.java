package kotlin;

import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: kotlin.LazyKt__LazyJVMKt */
/* loaded from: classes4.dex */
public class LazyJVM {
    public static <T> Lazy<T> lazy(Functions<? extends T> initializer) {
        Intrinsics.checkParameterIsNotNull(initializer, "initializer");
        return new SynchronizedLazyImpl(initializer, null, 2, null);
    }
}
