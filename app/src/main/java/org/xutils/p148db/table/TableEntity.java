package org.xutils.p148db.table;

import android.database.Cursor;
import java.lang.reflect.Constructor;
import java.util.LinkedHashMap;
import org.xutils.DbManager;
import org.xutils.p148db.annotation.Table;
import org.xutils.p149ex.DbException;

/* renamed from: org.xutils.db.table.TableEntity */
/* loaded from: classes4.dex */
public final class TableEntity<T> {
    private volatile boolean checkedDatabase;
    private final LinkedHashMap<String, ColumnEntity> columnMap;
    private Constructor<T> constructor;

    /* renamed from: db */
    private final DbManager f6066db;
    private Class<T> entityType;

    /* renamed from: id */
    private ColumnEntity f6067id;
    private final String name;
    private final String onCreated;

    /* JADX INFO: Access modifiers changed from: package-private */
    public TableEntity(DbManager dbManager, Class<T> cls) throws Throwable {
        this.f6066db = dbManager;
        this.entityType = cls;
        this.constructor = cls.getConstructor(new Class[0]);
        this.constructor.setAccessible(true);
        Table table = (Table) cls.getAnnotation(Table.class);
        this.name = table.name();
        this.onCreated = table.onCreated();
        this.columnMap = TableUtils.findColumnMap(cls);
        for (ColumnEntity columnEntity : this.columnMap.values()) {
            if (columnEntity.isId()) {
                this.f6067id = columnEntity;
                return;
            }
        }
    }

    public T createEntity() throws Throwable {
        return this.constructor.newInstance(new Object[0]);
    }

    public boolean tableIsExist() throws DbException {
        if (isCheckedDatabase()) {
            return true;
        }
        DbManager dbManager = this.f6066db;
        Cursor execQuery = dbManager.execQuery("SELECT COUNT(*) AS c FROM sqlite_master WHERE type='table' AND name='" + this.name + "'");
        if (execQuery != null) {
            try {
                if (execQuery.moveToNext() && execQuery.getInt(0) > 0) {
                    setCheckedDatabase(true);
                    return true;
                }
            } finally {
            }
        }
        return false;
    }

    public DbManager getDb() {
        return this.f6066db;
    }

    public String getName() {
        return this.name;
    }

    public Class<T> getEntityType() {
        return this.entityType;
    }

    public String getOnCreated() {
        return this.onCreated;
    }

    public ColumnEntity getId() {
        return this.f6067id;
    }

    public LinkedHashMap<String, ColumnEntity> getColumnMap() {
        return this.columnMap;
    }

    boolean isCheckedDatabase() {
        return this.checkedDatabase;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setCheckedDatabase(boolean z) {
        this.checkedDatabase = z;
    }

    public String toString() {
        return this.name;
    }
}
