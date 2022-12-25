package com.facebook.datasource;

import com.facebook.common.internal.Preconditions;

/* loaded from: classes2.dex */
public class SimpleDataSource<T> extends AbstractDataSource<T> {
    private SimpleDataSource() {
    }

    public static <T> SimpleDataSource<T> create() {
        return new SimpleDataSource<>();
    }

    @Override // com.facebook.datasource.AbstractDataSource
    public boolean setFailure(Throwable th) {
        Preconditions.checkNotNull(th);
        return super.setFailure(th);
    }
}
