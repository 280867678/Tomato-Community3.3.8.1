package com.squareup.wire.internal;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.RandomAccess;

/* loaded from: classes3.dex */
final class MutableOnWriteList<T> extends AbstractList<T> implements RandomAccess, Serializable {
    private final List<T> immutableList;
    List<T> mutableList;

    /* JADX INFO: Access modifiers changed from: package-private */
    public MutableOnWriteList(List<T> list) {
        this.immutableList = list;
        this.mutableList = list;
    }

    @Override // java.util.AbstractList, java.util.List
    public T get(int i) {
        return this.mutableList.get(i);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this.mutableList.size();
    }

    @Override // java.util.AbstractList, java.util.List
    public T set(int i, T t) {
        List<T> list = this.mutableList;
        List<T> list2 = this.immutableList;
        if (list == list2) {
            this.mutableList = new ArrayList(list2);
        }
        return this.mutableList.set(i, t);
    }

    @Override // java.util.AbstractList, java.util.List
    public void add(int i, T t) {
        List<T> list = this.mutableList;
        List<T> list2 = this.immutableList;
        if (list == list2) {
            this.mutableList = new ArrayList(list2);
        }
        this.mutableList.add(i, t);
    }

    @Override // java.util.AbstractList, java.util.List
    public T remove(int i) {
        List<T> list = this.mutableList;
        List<T> list2 = this.immutableList;
        if (list == list2) {
            this.mutableList = new ArrayList(list2);
        }
        return this.mutableList.remove(i);
    }

    private Object writeReplace() throws ObjectStreamException {
        return new ArrayList(this.mutableList);
    }
}
