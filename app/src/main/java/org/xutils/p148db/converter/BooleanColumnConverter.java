package org.xutils.p148db.converter;

import android.database.Cursor;
import org.xutils.p148db.sqlite.ColumnDbType;

/* renamed from: org.xutils.db.converter.BooleanColumnConverter */
/* loaded from: classes4.dex */
public class BooleanColumnConverter implements ColumnConverter<Boolean> {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.xutils.p148db.converter.ColumnConverter
    /* renamed from: getFieldValue */
    public Boolean mo6858getFieldValue(Cursor cursor, int i) {
        if (cursor.isNull(i)) {
            return null;
        }
        int i2 = cursor.getInt(i);
        boolean z = true;
        if (i2 != 1) {
            z = false;
        }
        return Boolean.valueOf(z);
    }

    @Override // org.xutils.p148db.converter.ColumnConverter
    public Object fieldValue2DbValue(Boolean bool) {
        if (bool == null) {
            return null;
        }
        return Integer.valueOf(bool.booleanValue() ? 1 : 0);
    }

    @Override // org.xutils.p148db.converter.ColumnConverter
    public ColumnDbType getColumnDbType() {
        return ColumnDbType.INTEGER;
    }
}
