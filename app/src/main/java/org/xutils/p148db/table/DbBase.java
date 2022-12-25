package org.xutils.p148db.table;

import android.database.Cursor;
import android.text.TextUtils;
import com.tomatolive.library.utils.ConstantUtils;
import java.util.HashMap;
import org.xutils.DbManager;
import org.xutils.common.util.IOUtil;
import org.xutils.p148db.sqlite.SqlInfoBuilder;
import org.xutils.p149ex.DbException;

/* renamed from: org.xutils.db.table.DbBase */
/* loaded from: classes4.dex */
public abstract class DbBase implements DbManager {
    private final HashMap<Class<?>, TableEntity<?>> tableMap = new HashMap<>();

    @Override // org.xutils.DbManager
    public <T> TableEntity<T> getTable(Class<T> cls) throws DbException {
        TableEntity<T> tableEntity;
        synchronized (this.tableMap) {
            tableEntity = (TableEntity<T>) this.tableMap.get(cls);
            if (tableEntity == null) {
                tableEntity = new TableEntity<>(this, cls);
                this.tableMap.put(cls, tableEntity);
            }
        }
        return tableEntity;
    }

    @Override // org.xutils.DbManager
    public void dropTable(Class<?> cls) throws DbException {
        TableEntity table = getTable(cls);
        if (!table.tableIsExist()) {
            return;
        }
        execNonQuery("DROP TABLE \"" + table.getName() + "\"");
        table.setCheckedDatabase(false);
        removeTable(cls);
    }

    @Override // org.xutils.DbManager
    public void dropDb() throws DbException {
        Cursor execQuery = execQuery("SELECT name FROM sqlite_master WHERE type='table' AND name<>'sqlite_sequence'");
        if (execQuery != null) {
            while (execQuery.moveToNext()) {
                try {
                    String string = execQuery.getString(0);
                    execNonQuery("DROP TABLE " + string);
                } catch (Throwable th) {
                    try {
                        throw new DbException(th);
                    } finally {
                        IOUtil.closeQuietly(execQuery);
                    }
                }
            }
            synchronized (this.tableMap) {
                for (TableEntity<?> tableEntity : this.tableMap.values()) {
                    tableEntity.setCheckedDatabase(false);
                }
                this.tableMap.clear();
            }
        }
    }

    @Override // org.xutils.DbManager
    public void addColumn(Class<?> cls, String str) throws DbException {
        TableEntity table = getTable(cls);
        ColumnEntity columnEntity = table.getColumnMap().get(str);
        if (columnEntity != null) {
            execNonQuery("ALTER TABLE \"" + table.getName() + "\" ADD COLUMN \"" + columnEntity.getName() + "\"" + ConstantUtils.PLACEHOLDER_STR_ONE + columnEntity.getColumnDbType() + ConstantUtils.PLACEHOLDER_STR_ONE + columnEntity.getProperty());
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void createTableIfNotExist(TableEntity<?> tableEntity) throws DbException {
        if (!tableEntity.tableIsExist()) {
            synchronized (tableEntity.getClass()) {
                if (!tableEntity.tableIsExist()) {
                    execNonQuery(SqlInfoBuilder.buildCreateTableSqlInfo(tableEntity));
                    String onCreated = tableEntity.getOnCreated();
                    if (!TextUtils.isEmpty(onCreated)) {
                        execNonQuery(onCreated);
                    }
                    tableEntity.setCheckedDatabase(true);
                    DbManager.TableCreateListener tableCreateListener = getDaoConfig().getTableCreateListener();
                    if (tableCreateListener != null) {
                        tableCreateListener.onTableCreated(this, tableEntity);
                    }
                }
            }
        }
    }

    protected void removeTable(Class<?> cls) {
        synchronized (this.tableMap) {
            this.tableMap.remove(cls);
        }
    }
}
