package org.dom4j.tree;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* loaded from: classes4.dex */
public abstract class FilterIterator<T> implements Iterator<T> {
    private boolean first = true;
    private T next;
    protected Iterator<T> proxy;

    protected abstract boolean matches(T t);

    public FilterIterator(Iterator<T> it2) {
        this.proxy = it2;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        if (this.first) {
            this.next = findNext();
            this.first = false;
        }
        return this.next != null;
    }

    @Override // java.util.Iterator
    public T next() throws NoSuchElementException {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        T t = this.next;
        this.next = findNext();
        return t;
    }

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException();
    }

    protected T findNext() {
        if (this.proxy != null) {
            while (this.proxy.hasNext()) {
                T next = this.proxy.next();
                if (next != null && matches(next)) {
                    return next;
                }
            }
            this.proxy = null;
        }
        return null;
    }
}
