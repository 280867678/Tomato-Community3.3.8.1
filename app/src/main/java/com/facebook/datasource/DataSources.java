package com.facebook.datasource;

import com.facebook.common.internal.Supplier;

/* loaded from: classes2.dex */
public class DataSources {
    public static <T> DataSource<T> immediateFailedDataSource(Throwable th) {
        SimpleDataSource create = SimpleDataSource.create();
        create.setFailure(th);
        return create;
    }

    public static <T> Supplier<DataSource<T>> getFailedDataSourceSupplier(final Throwable th) {
        return new Supplier<DataSource<T>>() { // from class: com.facebook.datasource.DataSources.1
            @Override // com.facebook.common.internal.Supplier
            /* renamed from: get */
            public DataSource<T> mo5939get() {
                return DataSources.immediateFailedDataSource(th);
            }
        };
    }
}
