package kotlin.collections;

/* renamed from: kotlin.collections.ArraysKt__ArraysJVMKt */
/* loaded from: classes4.dex */
class ArraysJVM {
    public static final void copyOfRangeToIndexCheck(int i, int i2) {
        if (i <= i2) {
            return;
        }
        throw new IndexOutOfBoundsException("toIndex (" + i + ") is greater than size (" + i2 + ").");
    }
}
