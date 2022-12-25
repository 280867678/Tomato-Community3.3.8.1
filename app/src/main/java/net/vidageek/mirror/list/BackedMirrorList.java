package net.vidageek.mirror.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import net.vidageek.mirror.list.dsl.Mapper;
import net.vidageek.mirror.list.dsl.Matcher;
import net.vidageek.mirror.list.dsl.MirrorList;

/* loaded from: classes4.dex */
public final class BackedMirrorList<T> implements MirrorList<T> {
    private final List<T> list;

    public BackedMirrorList(List<T> list) {
        this.list = Collections.unmodifiableList(list);
    }

    @Override // net.vidageek.mirror.list.dsl.MirrorList
    public MirrorList<T> matching(Matcher<T> matcher) {
        ArrayList arrayList = new ArrayList();
        for (T t : this.list) {
            if (matcher.accepts(t)) {
                arrayList.add(t);
            }
        }
        return new BackedMirrorList(arrayList);
    }

    @Override // net.vidageek.mirror.list.dsl.MirrorList
    public <E> MirrorList<E> mappingTo(Mapper<T, E> mapper) {
        ArrayList arrayList = new ArrayList();
        for (T t : this.list) {
            arrayList.add(mapper.map(t));
        }
        return new BackedMirrorList(arrayList);
    }

    @Override // java.util.List
    public void add(int i, T t) {
        this.list.add(i, t);
    }

    @Override // java.util.List, java.util.Collection
    public boolean add(T t) {
        return this.list.add(t);
    }

    @Override // java.util.List, java.util.Collection
    public boolean addAll(Collection<? extends T> collection) {
        return this.list.addAll(collection);
    }

    @Override // java.util.List
    public boolean addAll(int i, Collection<? extends T> collection) {
        return this.list.addAll(i, collection);
    }

    @Override // java.util.List, java.util.Collection
    public void clear() {
        this.list.clear();
    }

    @Override // java.util.List, java.util.Collection
    public boolean contains(Object obj) {
        return this.list.contains(obj);
    }

    @Override // java.util.List, java.util.Collection
    public boolean containsAll(Collection<?> collection) {
        return this.list.containsAll(collection);
    }

    @Override // java.util.List, java.util.Collection
    public boolean equals(Object obj) {
        return this.list.equals(obj);
    }

    @Override // java.util.List
    public T get(int i) {
        return this.list.get(i);
    }

    @Override // java.util.List, java.util.Collection
    public int hashCode() {
        return this.list.hashCode();
    }

    @Override // java.util.List
    public int indexOf(Object obj) {
        return this.list.indexOf(obj);
    }

    @Override // java.util.List, java.util.Collection
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override // java.util.List, java.util.Collection, java.lang.Iterable
    public Iterator<T> iterator() {
        return this.list.iterator();
    }

    @Override // java.util.List
    public int lastIndexOf(Object obj) {
        return this.list.lastIndexOf(obj);
    }

    @Override // java.util.List
    public ListIterator<T> listIterator() {
        return this.list.listIterator();
    }

    @Override // java.util.List
    public ListIterator<T> listIterator(int i) {
        return this.list.listIterator(i);
    }

    @Override // java.util.List
    public T remove(int i) {
        return this.list.remove(i);
    }

    @Override // java.util.List, java.util.Collection
    public boolean remove(Object obj) {
        return this.list.remove(obj);
    }

    @Override // java.util.List, java.util.Collection
    public boolean removeAll(Collection<?> collection) {
        return this.list.removeAll(collection);
    }

    @Override // java.util.List, java.util.Collection
    public boolean retainAll(Collection<?> collection) {
        return this.list.retainAll(collection);
    }

    @Override // java.util.List
    public T set(int i, T t) {
        return this.list.set(i, t);
    }

    @Override // java.util.List, java.util.Collection
    public int size() {
        return this.list.size();
    }

    @Override // java.util.List
    public List<T> subList(int i, int i2) {
        return this.list.subList(i, i2);
    }

    @Override // java.util.List, java.util.Collection
    public Object[] toArray() {
        return this.list.toArray();
    }

    @Override // java.util.List, java.util.Collection
    public <E> E[] toArray(E[] eArr) {
        return (E[]) this.list.toArray(eArr);
    }

    public String toString() {
        return this.list.toString();
    }
}
