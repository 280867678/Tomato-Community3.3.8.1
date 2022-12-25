package org.xutils.p148db.converter;

import android.database.Cursor;
import java.sql.Date;
import org.xutils.p148db.sqlite.ColumnDbType;

/* renamed from: org.xutils.db.converter.SqlDateColumnConverter */
/* loaded from: classes4.dex */
public class SqlDateColumnConverter implements ColumnConverter<Date> {
    @Override // org.xutils.p148db.converter.ColumnConverter
    /* renamed from: getFieldValue  reason: collision with other method in class */
    public Date mo6858getFieldValue(Cursor cursor, int i) {
        if (cursor.isNull(i)) {
            return null;
        }
        return new Date(cursor.getLong(i));
    }

    @Override // org.xutils.p148db.converter.ColumnConverter
    public Object fieldValue2DbValue(Date date) {
        if (date == null) {
            return null;
        }
        return Long.valueOf(date.getTime());
    }

    @Override // org.xutils.p148db.converter.ColumnConverter
    public ColumnDbType getColumnDbType() {
        return ColumnDbType.INTEGER;
    }
}
