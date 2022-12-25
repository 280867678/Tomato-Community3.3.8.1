package org.xutils.p148db;

import android.database.Cursor;
import java.util.LinkedHashMap;
import org.xutils.p148db.table.ColumnEntity;
import org.xutils.p148db.table.DbModel;
import org.xutils.p148db.table.TableEntity;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: org.xutils.db.CursorUtils */
/* loaded from: classes4.dex */
public final class CursorUtils {
    public static <T> T getEntity(TableEntity<T> tableEntity, Cursor cursor) throws Throwable {
        T createEntity = tableEntity.createEntity();
        LinkedHashMap<String, ColumnEntity> columnMap = tableEntity.getColumnMap();
        int columnCount = cursor.getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            ColumnEntity columnEntity = columnMap.get(cursor.getColumnName(i));
            if (columnEntity != null) {
                columnEntity.setValueFromCursor(createEntity, cursor, i);
            }
        }
        return createEntity;
    }

    public static DbModel getDbModel(Cursor cursor) {
        DbModel dbModel = new DbModel();
        int columnCount = cursor.getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            dbModel.add(cursor.getColumnName(i), cursor.getString(i));
        }
        return dbModel;
    }
}
