package org.xutils.p148db.converter;

import android.database.Cursor;
import org.xutils.p148db.sqlite.ColumnDbType;

/* renamed from: org.xutils.db.converter.ShortColumnConverter */
/* loaded from: classes4.dex */
public class ShortColumnConverter implements ColumnConverter<Short> {
    @Override // org.xutils.p148db.converter.ColumnConverter
    public Object fieldValue2DbValue(Short sh) {
        return sh;
    }

    @Override // org.xutils.p148db.converter.ColumnConverter
    /* renamed from: getFieldValue  reason: collision with other method in class */
    public Short mo6858getFieldValue(Cursor cursor, int i) {
        if (cursor.isNull(i)) {
            return null;
        }
        return Short.valueOf(cursor.getShort(i));
    }

    @Override // org.xutils.p148db.converter.ColumnConverter
    public ColumnDbType getColumnDbType() {
        return ColumnDbType.INTEGER;
    }
}
