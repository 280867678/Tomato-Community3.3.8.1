package org.xutils.p148db.converter;

import android.database.Cursor;
import org.xutils.p148db.sqlite.ColumnDbType;

/* renamed from: org.xutils.db.converter.CharColumnConverter */
/* loaded from: classes4.dex */
public class CharColumnConverter implements ColumnConverter<Character> {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.xutils.p148db.converter.ColumnConverter
    /* renamed from: getFieldValue */
    public Character mo6858getFieldValue(Cursor cursor, int i) {
        if (cursor.isNull(i)) {
            return null;
        }
        return Character.valueOf((char) cursor.getInt(i));
    }

    @Override // org.xutils.p148db.converter.ColumnConverter
    public Object fieldValue2DbValue(Character ch) {
        if (ch == null) {
            return null;
        }
        return Integer.valueOf(ch.charValue());
    }

    @Override // org.xutils.p148db.converter.ColumnConverter
    public ColumnDbType getColumnDbType() {
        return ColumnDbType.INTEGER;
    }
}
