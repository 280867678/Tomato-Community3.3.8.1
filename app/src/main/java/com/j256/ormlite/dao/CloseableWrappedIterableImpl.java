package com.j256.ormlite.dao;

import java.sql.SQLException;

/* loaded from: classes3.dex */
public class CloseableWrappedIterableImpl<T> implements CloseableWrappedIterable<T> {
    public final CloseableIterable<T> iterable;
    public CloseableIterator<T> iterator;

    public CloseableWrappedIterableImpl(CloseableIterable<T> closeableIterable) {
        this.iterable = closeableIterable;
    }

    @Override // com.j256.ormlite.dao.CloseableWrappedIterable
    public void close() {
        CloseableIterator<T> closeableIterator = this.iterator;
        if (closeableIterator != null) {
            closeableIterator.close();
            this.iterator = null;
        }
    }

    @Override // com.j256.ormlite.dao.CloseableIterable
    public CloseableIterator<T> closeableIterator() {
        try {
            close();
        } catch (SQLException unused) {
        }
        this.iterator = this.iterable.closeableIterator();
        return this.iterator;
    }

    @Override // java.lang.Iterable
    /* renamed from: iterator */
    public CloseableIterator<T> mo6326iterator() {
        return closeableIterator();
    }
}
