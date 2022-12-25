package org.dom4j.tree;

import java.util.Iterator;

/* loaded from: classes4.dex */
public class SingleIterator<T> implements Iterator<T> {
    private boolean first = true;
    private T object;

    public SingleIterator(T t) {
        this.object = t;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.first;
    }

    @Override // java.util.Iterator
    public T next() {
        T t = this.object;
        this.object = null;
        this.first = false;
        return t;
    }

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("remove() is not supported by this iterator");
    }
}
