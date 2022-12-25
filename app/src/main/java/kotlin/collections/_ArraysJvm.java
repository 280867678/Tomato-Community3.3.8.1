package kotlin.collections;

import java.util.Arrays;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: kotlin.collections.ArraysKt___ArraysJvmKt */
/* loaded from: classes4.dex */
public class _ArraysJvm extends Arrays {
    public static <T> List<T> asList(T[] asList) {
        Intrinsics.checkParameterIsNotNull(asList, "$this$asList");
        List<T> asList2 = ArraysUtilJVM.asList(asList);
        Intrinsics.checkExpressionValueIsNotNull(asList2, "ArraysUtilJVM.asList(this)");
        return asList2;
    }

    public static final List<Byte> asList(byte[] asList) {
        Intrinsics.checkParameterIsNotNull(asList, "$this$asList");
        return new ArraysKt___ArraysJvmKt$asList$1(asList);
    }

    public static final byte[] copyOfRange(byte[] copyOfRangeImpl, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(copyOfRangeImpl, "$this$copyOfRangeImpl");
        ArraysJVM.copyOfRangeToIndexCheck(i2, copyOfRangeImpl.length);
        byte[] copyOfRange = Arrays.copyOfRange(copyOfRangeImpl, i, i2);
        Intrinsics.checkExpressionValueIsNotNull(copyOfRange, "java.util.Arrays.copyOfR…this, fromIndex, toIndex)");
        return copyOfRange;
    }
}
