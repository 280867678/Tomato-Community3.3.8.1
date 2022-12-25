package com.j256.ormlite.dao;

import com.j256.ormlite.field.FieldType;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/* loaded from: classes3.dex */
public class LazyForeignCollection<T, ID> extends BaseForeignCollection<T, ID> implements ForeignCollection<T>, Serializable {
    public static final long serialVersionUID = -5460708106909626233L;
    public transient CloseableIterator<T> lastIterator;

    public LazyForeignCollection(AbstractC2183Dao<T, ID> abstractC2183Dao, Object obj, Object obj2, FieldType fieldType, String str, boolean z) {
        super(abstractC2183Dao, obj, obj2, fieldType, str, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CloseableIterator<T> seperateIteratorThrow(int i) {
        AbstractC2183Dao<T, ID> abstractC2183Dao = this.dao;
        if (abstractC2183Dao != null) {
            return abstractC2183Dao.iterator(getPreparedQuery(), i);
        }
        throw new IllegalStateException("Internal DAO object is null.  Lazy collections cannot be used if they have been deserialized.");
    }

    @Override // com.j256.ormlite.dao.ForeignCollection
    public void closeLastIterator() {
        CloseableIterator<T> closeableIterator = this.lastIterator;
        if (closeableIterator != null) {
            closeableIterator.close();
            this.lastIterator = null;
        }
    }

    @Override // com.j256.ormlite.dao.CloseableIterable
    public CloseableIterator<T> closeableIterator() {
        return closeableIterator(-1);
    }

    @Override // com.j256.ormlite.dao.ForeignCollection
    public CloseableIterator<T> closeableIterator(int i) {
        try {
            return iteratorThrow(i);
        } catch (SQLException e) {
            throw new IllegalStateException("Could not build lazy iterator for " + this.dao.getDataClass(), e);
        }
    }

    @Override // java.util.Collection
    public boolean contains(Object obj) {
        CloseableIterator<T> mo6328iterator = mo6328iterator();
        do {
            try {
                if (!mo6328iterator.hasNext()) {
                    try {
                        mo6328iterator.close();
                    } catch (SQLException unused) {
                    }
                    return false;
                }
            } finally {
                try {
                    mo6328iterator.close();
                } catch (SQLException unused2) {
                }
            }
        } while (!mo6328iterator.next().equals(obj));
        return true;
    }

    @Override // java.util.Collection
    public boolean containsAll(Collection<?> collection) {
        HashSet hashSet = new HashSet(collection);
        CloseableIterator<T> mo6328iterator = mo6328iterator();
        while (mo6328iterator.hasNext()) {
            try {
                hashSet.remove(mo6328iterator.next());
            } finally {
                try {
                    mo6328iterator.close();
                } catch (SQLException unused) {
                }
            }
        }
        return hashSet.isEmpty();
    }

    @Override // java.util.Collection
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override // com.j256.ormlite.dao.ForeignCollection
    public CloseableWrappedIterable<T> getWrappedIterable() {
        return getWrappedIterable(-1);
    }

    @Override // com.j256.ormlite.dao.ForeignCollection
    public CloseableWrappedIterable<T> getWrappedIterable(final int i) {
        return new CloseableWrappedIterableImpl(new CloseableIterable<T>() { // from class: com.j256.ormlite.dao.LazyForeignCollection.1
            @Override // com.j256.ormlite.dao.CloseableIterable
            public CloseableIterator<T> closeableIterator() {
                try {
                    return LazyForeignCollection.this.seperateIteratorThrow(i);
                } catch (Exception e) {
                    throw new IllegalStateException("Could not build lazy iterator for " + LazyForeignCollection.this.dao.getDataClass(), e);
                }
            }

            @Override // java.lang.Iterable
            /* renamed from: iterator */
            public CloseableIterator<T> mo6329iterator() {
                return closeableIterator();
            }
        });
    }

    @Override // java.util.Collection
    public int hashCode() {
        return super.hashCode();
    }

    @Override // com.j256.ormlite.dao.ForeignCollection
    public boolean isEager() {
        return false;
    }

    @Override // java.util.Collection
    public boolean isEmpty() {
        CloseableIterator<T> mo6328iterator = mo6328iterator();
        try {
            return !mo6328iterator.hasNext();
        } finally {
            try {
                mo6328iterator.close();
            } catch (SQLException unused) {
            }
        }
    }

    @Override // java.util.Collection, java.lang.Iterable
    /* renamed from: iterator */
    public CloseableIterator<T> mo6328iterator() {
        return closeableIterator(-1);
    }

    @Override // com.j256.ormlite.dao.ForeignCollection
    public CloseableIterator<T> iterator(int i) {
        return closeableIterator(i);
    }

    @Override // com.j256.ormlite.dao.ForeignCollection
    public CloseableIterator<T> iteratorThrow() {
        return iteratorThrow(-1);
    }

    @Override // com.j256.ormlite.dao.ForeignCollection
    public CloseableIterator<T> iteratorThrow(int i) {
        this.lastIterator = seperateIteratorThrow(i);
        return this.lastIterator;
    }

    @Override // com.j256.ormlite.dao.ForeignCollection
    public int refreshAll() {
        throw new UnsupportedOperationException("Cannot call updateAll() on a lazy collection.");
    }

    @Override // com.j256.ormlite.dao.ForeignCollection
    public int refreshCollection() {
        return 0;
    }

    @Override // com.j256.ormlite.dao.BaseForeignCollection, java.util.Collection
    public boolean remove(Object obj) {
        CloseableIterator<T> mo6328iterator = mo6328iterator();
        do {
            try {
                if (!mo6328iterator.hasNext()) {
                    try {
                        mo6328iterator.close();
                    } catch (SQLException unused) {
                    }
                    return false;
                }
            } finally {
                try {
                    mo6328iterator.close();
                } catch (SQLException unused2) {
                }
            }
        } while (!mo6328iterator.next().equals(obj));
        mo6328iterator.remove();
        return true;
    }

    @Override // com.j256.ormlite.dao.BaseForeignCollection, java.util.Collection
    public boolean removeAll(Collection<?> collection) {
        CloseableIterator<T> mo6328iterator = mo6328iterator();
        boolean z = false;
        while (mo6328iterator.hasNext()) {
            try {
                if (collection.contains(mo6328iterator.next())) {
                    mo6328iterator.remove();
                    z = true;
                }
            } finally {
                try {
                    mo6328iterator.close();
                } catch (SQLException unused) {
                }
            }
        }
        return z;
    }

    @Override // java.util.Collection
    public int size() {
        CloseableIterator<T> mo6328iterator = mo6328iterator();
        int i = 0;
        while (mo6328iterator.hasNext()) {
            try {
                mo6328iterator.moveToNext();
                i++;
            } finally {
                try {
                    mo6328iterator.close();
                } catch (SQLException unused) {
                }
            }
        }
        return i;
    }

    @Override // java.util.Collection
    public Object[] toArray() {
        ArrayList arrayList = new ArrayList();
        CloseableIterator<T> mo6328iterator = mo6328iterator();
        while (mo6328iterator.hasNext()) {
            try {
                arrayList.add(mo6328iterator.next());
            } finally {
                try {
                    mo6328iterator.close();
                } catch (SQLException unused) {
                }
            }
        }
        return arrayList.toArray();
    }

    @Override // java.util.Collection
    public <E> E[] toArray(E[] eArr) {
        CloseableIterator<T> mo6328iterator = mo6328iterator();
        ArrayList arrayList = null;
        int i = 0;
        while (mo6328iterator.hasNext()) {
            try {
                T next = mo6328iterator.next();
                if (i >= eArr.length) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                        for (E e : eArr) {
                            arrayList.add(e);
                        }
                    }
                    arrayList.add(next);
                } else {
                    eArr[i] = next;
                }
                i++;
            } finally {
                try {
                    mo6328iterator.close();
                } catch (SQLException unused) {
                }
            }
        }
        if (arrayList == null) {
            if (i < eArr.length - 1) {
                eArr[i] = 0;
            }
            return eArr;
        }
        return (E[]) arrayList.toArray(eArr);
    }

    @Override // com.j256.ormlite.dao.ForeignCollection
    public int updateAll() {
        throw new UnsupportedOperationException("Cannot call updateAll() on a lazy collection.");
    }
}
