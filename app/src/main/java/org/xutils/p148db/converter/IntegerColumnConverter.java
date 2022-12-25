package org.xutils.p148db.converter;

import android.database.Cursor;
import org.xutils.p148db.sqlite.ColumnDbType;

/* renamed from: org.xutils.db.converter.IntegerColumnConverter */
/* loaded from: classes4.dex */
public class IntegerColumnConverter implements ColumnConverter<Integer> {
    @Override // org.xutils.p148db.converter.ColumnConverter
    public Object fieldValue2DbValue(Integer num) {
        return num;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.xutils.p148db.converter.ColumnConverter
    /* renamed from: getFieldValue */
    public Integer mo6858getFieldValue(Cursor cursor, int i) {
        if (cursor.isNull(i)) {
            return null;
        }
        return Integer.valueOf(cursor.getInt(i));
    }

    @Override // org.xutils.p148db.converter.ColumnConverter
    public ColumnDbType getColumnDbType() {
        return ColumnDbType.INTEGER;
    }
}
