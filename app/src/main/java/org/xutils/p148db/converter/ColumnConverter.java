package org.xutils.p148db.converter;

import android.database.Cursor;
import org.xutils.p148db.sqlite.ColumnDbType;

/* renamed from: org.xutils.db.converter.ColumnConverter */
/* loaded from: classes4.dex */
public interface ColumnConverter<T> {
    Object fieldValue2DbValue(T t);

    ColumnDbType getColumnDbType();

    /* renamed from: getFieldValue */
    T mo6858getFieldValue(Cursor cursor, int i);
}
