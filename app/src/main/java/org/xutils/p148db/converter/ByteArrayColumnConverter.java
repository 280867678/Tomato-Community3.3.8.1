package org.xutils.p148db.converter;

import android.database.Cursor;
import org.xutils.p148db.sqlite.ColumnDbType;

/* renamed from: org.xutils.db.converter.ByteArrayColumnConverter */
/* loaded from: classes4.dex */
public class ByteArrayColumnConverter implements ColumnConverter<byte[]> {
    @Override // org.xutils.p148db.converter.ColumnConverter
    public Object fieldValue2DbValue(byte[] bArr) {
        return bArr;
    }

    @Override // org.xutils.p148db.converter.ColumnConverter
    /* renamed from: getFieldValue  reason: collision with other method in class */
    public byte[] mo6858getFieldValue(Cursor cursor, int i) {
        if (cursor.isNull(i)) {
            return null;
        }
        return cursor.getBlob(i);
    }

    @Override // org.xutils.p148db.converter.ColumnConverter
    public ColumnDbType getColumnDbType() {
        return ColumnDbType.BLOB;
    }
}
