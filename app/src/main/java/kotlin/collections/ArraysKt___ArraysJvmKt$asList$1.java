package kotlin.collections;

import java.util.RandomAccess;

/* compiled from: _ArraysJvm.kt */
/* loaded from: classes4.dex */
public final class ArraysKt___ArraysJvmKt$asList$1 extends AbstractList<Byte> implements RandomAccess {
    final /* synthetic */ byte[] $this_asList;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ArraysKt___ArraysJvmKt$asList$1(byte[] bArr) {
        this.$this_asList = bArr;
    }

    @Override // kotlin.collections.AbstractCollection, java.util.Collection
    public final /* bridge */ boolean contains(Object obj) {
        if (obj instanceof Byte) {
            return contains(((Number) obj).byteValue());
        }
        return false;
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public final /* bridge */ int indexOf(Object obj) {
        if (obj instanceof Byte) {
            return indexOf(((Number) obj).byteValue());
        }
        return -1;
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public final /* bridge */ int lastIndexOf(Object obj) {
        if (obj instanceof Byte) {
            return lastIndexOf(((Number) obj).byteValue());
        }
        return -1;
    }

    @Override // kotlin.collections.AbstractCollection
    public int getSize() {
        return this.$this_asList.length;
    }

    @Override // kotlin.collections.AbstractCollection, java.util.Collection
    public boolean isEmpty() {
        return this.$this_asList.length == 0;
    }

    public boolean contains(byte b) {
        return _Arrays.contains(this.$this_asList, b);
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    /* renamed from: get */
    public Byte mo6776get(int i) {
        return Byte.valueOf(this.$this_asList[i]);
    }

    public int indexOf(byte b) {
        return _Arrays.indexOf(this.$this_asList, b);
    }

    public int lastIndexOf(byte b) {
        return _Arrays.lastIndexOf(this.$this_asList, b);
    }
}
