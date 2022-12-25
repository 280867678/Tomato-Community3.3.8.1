package kotlin.collections;

import java.util.Iterator;
import kotlin.jvm.internal.markers.KMarkers;

/* compiled from: Iterators.kt */
/* loaded from: classes4.dex */
public abstract class IntIterator implements Iterator<Integer>, KMarkers {
    public abstract int nextInt();

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.Iterator
    /* renamed from: next */
    public final Integer mo6782next() {
        return Integer.valueOf(nextInt());
    }
}
