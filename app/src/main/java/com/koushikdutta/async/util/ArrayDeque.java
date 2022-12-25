package com.koushikdutta.async.util;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.AbstractCollection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import p007b.p014c.p015a.p023e.AbstractC0703d;

/* loaded from: classes3.dex */
public class ArrayDeque<E> extends AbstractCollection<E> implements AbstractC0703d<E>, Cloneable, Serializable {

    /* renamed from: a */
    public static final /* synthetic */ boolean f1546a = !ArrayDeque.class.desiredAssertionStatus();
    public static final long serialVersionUID = 2340985798034038923L;

    /* renamed from: b */
    public transient Object[] f1547b = new Object[16];

    /* renamed from: c */
    public transient int f1548c;

    /* renamed from: d */
    public transient int f1549d;

    /* renamed from: com.koushikdutta.async.util.ArrayDeque$a */
    /* loaded from: classes3.dex */
    private class C2209a implements Iterator<E> {

        /* renamed from: a */
        public int f1550a;

        /* renamed from: b */
        public int f1551b;

        /* renamed from: c */
        public int f1552c;

        public C2209a() {
            this.f1550a = ArrayDeque.this.f1548c;
            this.f1551b = ArrayDeque.this.f1549d;
            this.f1552c = -1;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.f1550a != this.f1551b;
        }

        @Override // java.util.Iterator
        public E next() {
            if (this.f1550a != this.f1551b) {
                E e = (E) ArrayDeque.this.f1547b[this.f1550a];
                if (ArrayDeque.this.f1549d != this.f1551b || e == null) {
                    throw new ConcurrentModificationException();
                }
                int i = this.f1550a;
                this.f1552c = i;
                this.f1550a = (i + 1) & (ArrayDeque.this.f1547b.length - 1);
                return e;
            }
            throw new NoSuchElementException();
        }

        @Override // java.util.Iterator
        public void remove() {
            int i = this.f1552c;
            if (i >= 0) {
                if (ArrayDeque.this.m3852b(i)) {
                    this.f1550a = (this.f1550a - 1) & (ArrayDeque.this.f1547b.length - 1);
                    this.f1551b = ArrayDeque.this.f1549d;
                }
                this.f1552c = -1;
                return;
            }
            throw new IllegalStateException();
        }
    }

    private void readObject(ObjectInputStream objectInputStream) {
        objectInputStream.defaultReadObject();
        int readInt = objectInputStream.readInt();
        m3857a(readInt);
        this.f1548c = 0;
        this.f1549d = readInt;
        for (int i = 0; i < readInt; i++) {
            this.f1547b[i] = objectInputStream.readObject();
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(size());
        int length = this.f1547b.length - 1;
        for (int i = this.f1548c; i != this.f1549d; i = (i + 1) & length) {
            objectOutputStream.writeObject(this.f1547b[i]);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0021, code lost:
        if (r3.f1547b[r0] == null) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0032, code lost:
        if (r2[(r2.length - 1) & (r1 - 1)] != null) goto L20;
     */
    /* renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m3858a() {
        if (f1546a || this.f1547b[this.f1549d] == null) {
            if (!f1546a) {
                int i = this.f1548c;
                int i2 = this.f1549d;
                if (i != i2) {
                    Object[] objArr = this.f1547b;
                    if (objArr[i] != null) {
                    }
                    throw new AssertionError();
                }
            }
            if (f1546a) {
                return;
            }
            Object[] objArr2 = this.f1547b;
            if (objArr2[(this.f1548c - 1) & (objArr2.length - 1)] != null) {
                throw new AssertionError();
            }
            return;
        }
        throw new AssertionError();
    }

    /* renamed from: a */
    public final void m3857a(int i) {
        int i2 = 8;
        if (i >= 8) {
            int i3 = i | (i >>> 1);
            int i4 = i3 | (i3 >>> 2);
            int i5 = i4 | (i4 >>> 4);
            int i6 = i5 | (i5 >>> 8);
            i2 = (i6 | (i6 >>> 16)) + 1;
            if (i2 < 0) {
                i2 >>>= 1;
            }
        }
        this.f1547b = new Object[i2];
    }

    /* renamed from: a */
    public final <T> T[] m3854a(T[] tArr) {
        int i = this.f1548c;
        int i2 = this.f1549d;
        if (i < i2) {
            System.arraycopy(this.f1547b, i, tArr, 0, size());
        } else if (i > i2) {
            Object[] objArr = this.f1547b;
            int length = objArr.length - i;
            System.arraycopy(objArr, i, tArr, 0, length);
            System.arraycopy(this.f1547b, 0, tArr, length, this.f1549d);
        }
        return tArr;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Queue
    public boolean add(E e) {
        addLast(e);
        return true;
    }

    public void addFirst(E e) {
        if (e != null) {
            Object[] objArr = this.f1547b;
            int length = (this.f1548c - 1) & (objArr.length - 1);
            this.f1548c = length;
            objArr[length] = e;
            if (this.f1548c != this.f1549d) {
                return;
            }
            m3853b();
            return;
        }
        throw new NullPointerException("e == null");
    }

    public void addLast(E e) {
        if (e != null) {
            Object[] objArr = this.f1547b;
            int i = this.f1549d;
            objArr[i] = e;
            int length = (objArr.length - 1) & (i + 1);
            this.f1549d = length;
            if (length != this.f1548c) {
                return;
            }
            m3853b();
            return;
        }
        throw new NullPointerException("e == null");
    }

    /* renamed from: b */
    public final void m3853b() {
        if (f1546a || this.f1548c == this.f1549d) {
            int i = this.f1548c;
            Object[] objArr = this.f1547b;
            int length = objArr.length;
            int i2 = length - i;
            int i3 = length << 1;
            if (i3 < 0) {
                throw new IllegalStateException("Sorry, deque too big");
            }
            Object[] objArr2 = new Object[i3];
            System.arraycopy(objArr, i, objArr2, 0, i2);
            System.arraycopy(this.f1547b, 0, objArr2, i2, i);
            this.f1547b = objArr2;
            this.f1548c = 0;
            this.f1549d = length;
            return;
        }
        throw new AssertionError();
    }

    /* renamed from: b */
    public final boolean m3852b(int i) {
        m3858a();
        Object[] objArr = this.f1547b;
        int length = objArr.length - 1;
        int i2 = this.f1548c;
        int i3 = this.f1549d;
        int i4 = (i - i2) & length;
        int i5 = (i3 - i) & length;
        if (i4 < ((i3 - i2) & length)) {
            if (i4 < i5) {
                if (i2 <= i) {
                    System.arraycopy(objArr, i2, objArr, i2 + 1, i4);
                } else {
                    System.arraycopy(objArr, 0, objArr, 1, i);
                    objArr[0] = objArr[length];
                    System.arraycopy(objArr, i2, objArr, i2 + 1, length - i2);
                }
                objArr[i2] = null;
                this.f1548c = (i2 + 1) & length;
                return false;
            }
            if (i < i3) {
                System.arraycopy(objArr, i + 1, objArr, i, i5);
                this.f1549d = i3 - 1;
            } else {
                System.arraycopy(objArr, i + 1, objArr, i, length - i);
                objArr[length] = objArr[0];
                System.arraycopy(objArr, 1, objArr, 0, i3);
                this.f1549d = (i3 - 1) & length;
            }
            return true;
        }
        throw new ConcurrentModificationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public void clear() {
        int i = this.f1548c;
        int i2 = this.f1549d;
        if (i != i2) {
            this.f1549d = 0;
            this.f1548c = 0;
            int length = this.f1547b.length - 1;
            do {
                this.f1547b[i] = null;
                i = (i + 1) & length;
            } while (i != i2);
        }
    }

    /* renamed from: clone */
    public ArrayDeque<E> m6335clone() {
        try {
            ArrayDeque<E> arrayDeque = (ArrayDeque) super.clone();
            System.arraycopy(this.f1547b, 0, arrayDeque.f1547b, 0, this.f1547b.length);
            return arrayDeque;
        } catch (CloneNotSupportedException unused) {
            throw new AssertionError();
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean contains(Object obj) {
        if (obj == null) {
            return false;
        }
        int length = this.f1547b.length - 1;
        int i = this.f1548c;
        while (true) {
            Object obj2 = this.f1547b[i];
            if (obj2 == null) {
                return false;
            }
            if (obj.equals(obj2)) {
                return true;
            }
            i = (i + 1) & length;
        }
    }

    @Override // java.util.Queue
    public E element() {
        return getFirst();
    }

    public E getFirst() {
        E e = (E) this.f1547b[this.f1548c];
        if (e != null) {
            return e;
        }
        throw new NoSuchElementException();
    }

    public E getLast() {
        Object[] objArr = this.f1547b;
        E e = (E) objArr[(this.f1549d - 1) & (objArr.length - 1)];
        if (e != null) {
            return e;
        }
        throw new NoSuchElementException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean isEmpty() {
        return this.f1548c == this.f1549d;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public Iterator<E> iterator() {
        return new C2209a();
    }

    @Override // java.util.Queue
    public boolean offer(E e) {
        return offerLast(e);
    }

    public boolean offerLast(E e) {
        addLast(e);
        return true;
    }

    @Override // java.util.Queue
    public E peek() {
        return peekFirst();
    }

    public E peekFirst() {
        return (E) this.f1547b[this.f1548c];
    }

    @Override // java.util.Queue
    public E poll() {
        return pollFirst();
    }

    public E pollFirst() {
        int i = this.f1548c;
        Object[] objArr = this.f1547b;
        E e = (E) objArr[i];
        if (e == null) {
            return null;
        }
        objArr[i] = null;
        this.f1548c = (i + 1) & (objArr.length - 1);
        return e;
    }

    @Override // java.util.Queue
    public E remove() {
        return removeFirst();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean remove(Object obj) {
        return removeFirstOccurrence(obj);
    }

    public E removeFirst() {
        E pollFirst = pollFirst();
        if (pollFirst != null) {
            return pollFirst;
        }
        throw new NoSuchElementException();
    }

    public boolean removeFirstOccurrence(Object obj) {
        if (obj == null) {
            return false;
        }
        int length = this.f1547b.length - 1;
        int i = this.f1548c;
        while (true) {
            Object obj2 = this.f1547b[i];
            if (obj2 == null) {
                return false;
            }
            if (obj.equals(obj2)) {
                m3852b(i);
                return true;
            }
            i = (i + 1) & length;
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public int size() {
        return (this.f1549d - this.f1548c) & (this.f1547b.length - 1);
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public Object[] toArray() {
        return m3854a(new Object[size()]);
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public <T> T[] toArray(T[] tArr) {
        int size = size();
        if (tArr.length < size) {
            tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), size));
        }
        m3854a(tArr);
        if (tArr.length > size) {
            tArr[size] = null;
        }
        return tArr;
    }
}
