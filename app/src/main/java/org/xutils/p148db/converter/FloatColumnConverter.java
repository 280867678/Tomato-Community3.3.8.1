package org.xutils.p148db.converter;

import android.database.Cursor;
import org.xutils.p148db.sqlite.ColumnDbType;

/* renamed from: org.xutils.db.converter.FloatColumnConverter */
/* loaded from: classes4.dex */
public class FloatColumnConverter implements ColumnConverter<Float> {
    @Override // org.xutils.p148db.converter.ColumnConverter
    public Object fieldValue2DbValue(Float f) {
        return f;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.xutils.p148db.converter.ColumnConverter
    /* renamed from: getFieldValue */
    public Float mo6858getFieldValue(Cursor cursor, int i) {
        if (cursor.isNull(i)) {
            return null;
        }
        return Float.valueOf(cursor.getFloat(i));
    }

    @Override // org.xutils.p148db.converter.ColumnConverter
    public ColumnDbType getColumnDbType() {
        return ColumnDbType.REAL;
    }
}
