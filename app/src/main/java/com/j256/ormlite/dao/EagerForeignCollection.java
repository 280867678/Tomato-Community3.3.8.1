package com.j256.ormlite.dao;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.support.DatabaseResults;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class EagerForeignCollection<T, ID> extends BaseForeignCollection<T, ID> implements ForeignCollection<T>, CloseableWrappedIterable<T>, Serializable {
    public static final long serialVersionUID = -2523335606983317721L;
    public List<T> results;

    public EagerForeignCollection(AbstractC2183Dao<T, ID> abstractC2183Dao, Object obj, Object obj2, FieldType fieldType, String str, boolean z) {
        super(abstractC2183Dao, obj, obj2, fieldType, str, z);
        this.results = obj2 == null ? new ArrayList<>() : abstractC2183Dao.query(getPreparedQuery());
    }

    @Override // com.j256.ormlite.dao.BaseForeignCollection, com.j256.ormlite.dao.ForeignCollection, java.util.Collection
    public boolean add(T t) {
        if (this.results.add(t)) {
            return super.add(t);
        }
        return false;
    }

    @Override // com.j256.ormlite.dao.BaseForeignCollection, java.util.Collection
    public boolean addAll(Collection<? extends T> collection) {
        if (this.results.addAll(collection)) {
            return super.addAll(collection);
        }
        return false;
    }

    @Override // com.j256.ormlite.dao.CloseableWrappedIterable
    public void close() {
    }

    @Override // com.j256.ormlite.dao.ForeignCollection
    public void closeLastIterator() {
    }

    @Override // com.j256.ormlite.dao.CloseableIterable
    public CloseableIterator<T> closeableIterator() {
        return iteratorThrow(-1);
    }

    @Override // com.j256.ormlite.dao.ForeignCollection
    public CloseableIterator<T> closeableIterator(int i) {
        return iteratorThrow(-1);
    }

    @Override // java.util.Collection
    public boolean contains(Object obj) {
        return this.results.contains(obj);
    }

    @Override // java.util.Collection
    public boolean containsAll(Collection<?> collection) {
        return this.results.containsAll(collection);
    }

    @Override // java.util.Collection
    public boolean equals(Object obj) {
        if (!(obj instanceof EagerForeignCollection)) {
            return false;
        }
        return this.results.equals(((EagerForeignCollection) obj).results);
    }

    @Override // com.j256.ormlite.dao.ForeignCollection
    public CloseableWrappedIterable<T> getWrappedIterable() {
        return this;
    }

    @Override // com.j256.ormlite.dao.ForeignCollection
    public CloseableWrappedIterable<T> getWrappedIterable(int i) {
        return this;
    }

    @Override // java.util.Collection
    public int hashCode() {
        return this.results.hashCode();
    }

    @Override // com.j256.ormlite.dao.ForeignCollection
    public boolean isEager() {
        return true;
    }

    @Override // java.util.Collection
    public boolean isEmpty() {
        return this.results.isEmpty();
    }

    @Override // java.util.Collection, java.lang.Iterable
    /* renamed from: iterator */
    public CloseableIterator<T> mo6327iterator() {
        return iteratorThrow(-1);
    }

    @Override // com.j256.ormlite.dao.ForeignCollection
    public CloseableIterator<T> iterator(int i) {
        return iteratorThrow(i);
    }

    @Override // com.j256.ormlite.dao.ForeignCollection
    public CloseableIterator<T> iteratorThrow() {
        return iteratorThrow(-1);
    }

    @Override // com.j256.ormlite.dao.ForeignCollection
    public CloseableIterator<T> iteratorThrow(int i) {
        return new CloseableIterator<T>() { // from class: com.j256.ormlite.dao.EagerForeignCollection.1
            public int offset = -1;

            @Override // com.j256.ormlite.dao.CloseableIterator
            public void close() {
            }

            @Override // com.j256.ormlite.dao.CloseableIterator
            public void closeQuietly() {
            }

            @Override // com.j256.ormlite.dao.CloseableIterator
            public T current() {
                if (this.offset < 0) {
                    this.offset = 0;
                }
                if (this.offset >= EagerForeignCollection.this.results.size()) {
                    return null;
                }
                return (T) EagerForeignCollection.this.results.get(this.offset);
            }

            @Override // com.j256.ormlite.dao.CloseableIterator
            public T first() {
                this.offset = 0;
                if (this.offset >= EagerForeignCollection.this.results.size()) {
                    return null;
                }
                return (T) EagerForeignCollection.this.results.get(0);
            }

            @Override // com.j256.ormlite.dao.CloseableIterator
            public DatabaseResults getRawResults() {
                return null;
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.offset + 1 < EagerForeignCollection.this.results.size();
            }

            @Override // com.j256.ormlite.dao.CloseableIterator
            public T moveRelative(int i2) {
                this.offset += i2;
                int i3 = this.offset;
                if (i3 < 0 || i3 >= EagerForeignCollection.this.results.size()) {
                    return null;
                }
                return (T) EagerForeignCollection.this.results.get(this.offset);
            }

            @Override // com.j256.ormlite.dao.CloseableIterator
            public void moveToNext() {
                this.offset++;
            }

            @Override // java.util.Iterator
            public T next() {
                this.offset++;
                return (T) EagerForeignCollection.this.results.get(this.offset);
            }

            @Override // com.j256.ormlite.dao.CloseableIterator
            public T nextThrow() {
                this.offset++;
                if (this.offset >= EagerForeignCollection.this.results.size()) {
                    return null;
                }
                return (T) EagerForeignCollection.this.results.get(this.offset);
            }

            @Override // com.j256.ormlite.dao.CloseableIterator
            public T previous() {
                this.offset--;
                int i2 = this.offset;
                if (i2 < 0 || i2 >= EagerForeignCollection.this.results.size()) {
                    return null;
                }
                return (T) EagerForeignCollection.this.results.get(this.offset);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Iterator
            public void remove() {
                int i2 = this.offset;
                if (i2 >= 0) {
                    if (i2 >= EagerForeignCollection.this.results.size()) {
                        throw new IllegalStateException("current results position (" + this.offset + ") is out of bounds");
                    }
                    Object remove = EagerForeignCollection.this.results.remove(this.offset);
                    this.offset--;
                    AbstractC2183Dao<T, ID> abstractC2183Dao = EagerForeignCollection.this.dao;
                    if (abstractC2183Dao == 0) {
                        return;
                    }
                    try {
                        abstractC2183Dao.delete((AbstractC2183Dao<T, ID>) remove);
                        return;
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                throw new IllegalStateException("next() must be called before remove()");
            }
        };
    }

    @Override // com.j256.ormlite.dao.ForeignCollection
    public int refreshAll() {
        int i = 0;
        for (T t : this.results) {
            i += this.dao.refresh(t);
        }
        return i;
    }

    @Override // com.j256.ormlite.dao.ForeignCollection
    public int refreshCollection() {
        this.results = this.dao.query(getPreparedQuery());
        return this.results.size();
    }

    @Override // com.j256.ormlite.dao.BaseForeignCollection, java.util.Collection
    public boolean remove(Object obj) {
        AbstractC2183Dao<T, ID> abstractC2183Dao;
        if (!this.results.remove(obj) || (abstractC2183Dao = this.dao) == null) {
            return false;
        }
        try {
            return abstractC2183Dao.delete((AbstractC2183Dao<T, ID>) obj) == 1;
        } catch (SQLException e) {
            throw new IllegalStateException("Could not delete data element from dao", e);
        }
    }

    @Override // com.j256.ormlite.dao.BaseForeignCollection, java.util.Collection
    public boolean removeAll(Collection<?> collection) {
        Iterator<?> it2 = collection.iterator();
        boolean z = false;
        while (it2.hasNext()) {
            if (remove(it2.next())) {
                z = true;
            }
        }
        return z;
    }

    @Override // com.j256.ormlite.dao.BaseForeignCollection, java.util.Collection
    public boolean retainAll(Collection<?> collection) {
        return super.retainAll(collection);
    }

    @Override // java.util.Collection
    public int size() {
        return this.results.size();
    }

    @Override // java.util.Collection
    public Object[] toArray() {
        return this.results.toArray();
    }

    @Override // java.util.Collection
    public <E> E[] toArray(E[] eArr) {
        return (E[]) this.results.toArray(eArr);
    }

    @Override // com.j256.ormlite.dao.ForeignCollection
    public int updateAll() {
        int i = 0;
        for (T t : this.results) {
            i += this.dao.update((AbstractC2183Dao<T, ID>) t);
        }
        return i;
    }
}
