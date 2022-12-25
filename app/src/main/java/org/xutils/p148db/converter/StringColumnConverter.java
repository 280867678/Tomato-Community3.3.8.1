package org.xutils.p148db.converter;

import android.database.Cursor;
import org.xutils.p148db.sqlite.ColumnDbType;

/* renamed from: org.xutils.db.converter.StringColumnConverter */
/* loaded from: classes4.dex */
public class StringColumnConverter implements ColumnConverter<String> {
    @Override // org.xutils.p148db.converter.ColumnConverter
    public Object fieldValue2DbValue(String str) {
        return str;
    }

    @Override // org.xutils.p148db.converter.ColumnConverter
    /* renamed from: getFieldValue  reason: collision with other method in class */
    public String mo6858getFieldValue(Cursor cursor, int i) {
        if (cursor.isNull(i)) {
            return null;
        }
        return cursor.getString(i);
    }

    @Override // org.xutils.p148db.converter.ColumnConverter
    public ColumnDbType getColumnDbType() {
        return ColumnDbType.TEXT;
    }
}
