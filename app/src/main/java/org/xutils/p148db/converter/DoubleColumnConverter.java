package org.xutils.p148db.converter;

import android.database.Cursor;
import org.xutils.p148db.sqlite.ColumnDbType;

/* renamed from: org.xutils.db.converter.DoubleColumnConverter */
/* loaded from: classes4.dex */
public class DoubleColumnConverter implements ColumnConverter<Double> {
    @Override // org.xutils.p148db.converter.ColumnConverter
    public Object fieldValue2DbValue(Double d) {
        return d;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.xutils.p148db.converter.ColumnConverter
    /* renamed from: getFieldValue */
    public Double mo6858getFieldValue(Cursor cursor, int i) {
        if (cursor.isNull(i)) {
            return null;
        }
        return Double.valueOf(cursor.getDouble(i));
    }

    @Override // org.xutils.p148db.converter.ColumnConverter
    public ColumnDbType getColumnDbType() {
        return ColumnDbType.REAL;
    }
}
