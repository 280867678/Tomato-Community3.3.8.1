package org.xutils.p148db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.xutils.C5540x;
import org.xutils.DbManager;
import org.xutils.common.util.IOUtil;
import org.xutils.common.util.KeyValue;
import org.xutils.common.util.LogUtil;
import org.xutils.p148db.sqlite.SqlInfo;
import org.xutils.p148db.sqlite.SqlInfoBuilder;
import org.xutils.p148db.sqlite.WhereBuilder;
import org.xutils.p148db.table.ColumnEntity;
import org.xutils.p148db.table.DbBase;
import org.xutils.p148db.table.DbModel;
import org.xutils.p148db.table.TableEntity;
import org.xutils.p149ex.DbException;

/* renamed from: org.xutils.db.DbManagerImpl */
/* loaded from: classes4.dex */
public final class DbManagerImpl extends DbBase {
    private static final HashMap<DbManager.DaoConfig, DbManagerImpl> DAO_MAP = new HashMap<>();
    private boolean allowTransaction;
    private DbManager.DaoConfig daoConfig;
    private SQLiteDatabase database;

    private DbManagerImpl(DbManager.DaoConfig daoConfig) {
        if (daoConfig == null) {
            throw new IllegalArgumentException("daoConfig may not be null");
        }
        this.daoConfig = daoConfig;
        this.allowTransaction = daoConfig.isAllowTransaction();
        this.database = openOrCreateDatabase(daoConfig);
        DbManager.DbOpenListener dbOpenListener = daoConfig.getDbOpenListener();
        if (dbOpenListener == null) {
            return;
        }
        dbOpenListener.onDbOpened(this);
    }

    public static synchronized DbManager getInstance(DbManager.DaoConfig daoConfig) {
        DbManagerImpl dbManagerImpl;
        synchronized (DbManagerImpl.class) {
            if (daoConfig == null) {
                daoConfig = new DbManager.DaoConfig();
            }
            dbManagerImpl = DAO_MAP.get(daoConfig);
            if (dbManagerImpl == null) {
                dbManagerImpl = new DbManagerImpl(daoConfig);
                DAO_MAP.put(daoConfig, dbManagerImpl);
            } else {
                dbManagerImpl.daoConfig = daoConfig;
            }
            SQLiteDatabase sQLiteDatabase = dbManagerImpl.database;
            int version = sQLiteDatabase.getVersion();
            int dbVersion = daoConfig.getDbVersion();
            if (version != dbVersion) {
                if (version != 0) {
                    DbManager.DbUpgradeListener dbUpgradeListener = daoConfig.getDbUpgradeListener();
                    if (dbUpgradeListener != null) {
                        dbUpgradeListener.onUpgrade(dbManagerImpl, version, dbVersion);
                    } else {
                        try {
                            dbManagerImpl.dropDb();
                        } catch (DbException e) {
                            LogUtil.m43e(e.getMessage(), e);
                        }
                    }
                }
                sQLiteDatabase.setVersion(dbVersion);
            }
        }
        return dbManagerImpl;
    }

    @Override // org.xutils.DbManager
    public SQLiteDatabase getDatabase() {
        return this.database;
    }

    @Override // org.xutils.DbManager
    public DbManager.DaoConfig getDaoConfig() {
        return this.daoConfig;
    }

    @Override // org.xutils.DbManager
    public void saveOrUpdate(Object obj) throws DbException {
        try {
            beginTransaction();
            if (obj instanceof List) {
                List<Object> list = (List) obj;
                if (list.isEmpty()) {
                    return;
                }
                TableEntity<?> table = getTable(list.get(0).getClass());
                createTableIfNotExist(table);
                for (Object obj2 : list) {
                    saveOrUpdateWithoutTransaction(table, obj2);
                }
            } else {
                TableEntity<?> table2 = getTable(obj.getClass());
                createTableIfNotExist(table2);
                saveOrUpdateWithoutTransaction(table2, obj);
            }
            setTransactionSuccessful();
        } finally {
            endTransaction();
        }
    }

    @Override // org.xutils.DbManager
    public void replace(Object obj) throws DbException {
        try {
            beginTransaction();
            if (obj instanceof List) {
                List<Object> list = (List) obj;
                if (list.isEmpty()) {
                    return;
                }
                TableEntity<?> table = getTable(list.get(0).getClass());
                createTableIfNotExist(table);
                for (Object obj2 : list) {
                    execNonQuery(SqlInfoBuilder.buildReplaceSqlInfo(table, obj2));
                }
            } else {
                TableEntity<?> table2 = getTable(obj.getClass());
                createTableIfNotExist(table2);
                execNonQuery(SqlInfoBuilder.buildReplaceSqlInfo(table2, obj));
            }
            setTransactionSuccessful();
        } finally {
            endTransaction();
        }
    }

    @Override // org.xutils.DbManager
    public void save(Object obj) throws DbException {
        try {
            beginTransaction();
            if (obj instanceof List) {
                List<Object> list = (List) obj;
                if (list.isEmpty()) {
                    return;
                }
                TableEntity<?> table = getTable(list.get(0).getClass());
                createTableIfNotExist(table);
                for (Object obj2 : list) {
                    execNonQuery(SqlInfoBuilder.buildInsertSqlInfo(table, obj2));
                }
            } else {
                TableEntity<?> table2 = getTable(obj.getClass());
                createTableIfNotExist(table2);
                execNonQuery(SqlInfoBuilder.buildInsertSqlInfo(table2, obj));
            }
            setTransactionSuccessful();
        } finally {
            endTransaction();
        }
    }

    @Override // org.xutils.DbManager
    public boolean saveBindingId(Object obj) throws DbException {
        try {
            beginTransaction();
            boolean z = false;
            if (obj instanceof List) {
                List<Object> list = (List) obj;
                if (list.isEmpty()) {
                    return false;
                }
                TableEntity<?> table = getTable(list.get(0).getClass());
                createTableIfNotExist(table);
                for (Object obj2 : list) {
                    if (!saveBindingIdWithoutTransaction(table, obj2)) {
                        throw new DbException("saveBindingId error, transaction will not commit!");
                    }
                }
            } else {
                TableEntity<?> table2 = getTable(obj.getClass());
                createTableIfNotExist(table2);
                z = saveBindingIdWithoutTransaction(table2, obj);
            }
            setTransactionSuccessful();
            return z;
        } finally {
            endTransaction();
        }
    }

    @Override // org.xutils.DbManager
    public void deleteById(Class<?> cls, Object obj) throws DbException {
        TableEntity table = getTable(cls);
        if (!table.tableIsExist()) {
            return;
        }
        try {
            beginTransaction();
            execNonQuery(SqlInfoBuilder.buildDeleteSqlInfoById(table, obj));
            setTransactionSuccessful();
        } finally {
            endTransaction();
        }
    }

    @Override // org.xutils.DbManager
    public void delete(Object obj) throws DbException {
        try {
            beginTransaction();
            if (obj instanceof List) {
                List<Object> list = (List) obj;
                if (list.isEmpty()) {
                    return;
                }
                TableEntity table = getTable(list.get(0).getClass());
                if (!table.tableIsExist()) {
                    return;
                }
                for (Object obj2 : list) {
                    execNonQuery(SqlInfoBuilder.buildDeleteSqlInfo(table, obj2));
                }
            } else {
                TableEntity table2 = getTable(obj.getClass());
                if (!table2.tableIsExist()) {
                    return;
                }
                execNonQuery(SqlInfoBuilder.buildDeleteSqlInfo(table2, obj));
            }
            setTransactionSuccessful();
        } finally {
            endTransaction();
        }
    }

    @Override // org.xutils.DbManager
    public void delete(Class<?> cls) throws DbException {
        delete(cls, null);
    }

    @Override // org.xutils.DbManager
    public int delete(Class<?> cls, WhereBuilder whereBuilder) throws DbException {
        TableEntity table = getTable(cls);
        if (!table.tableIsExist()) {
            return 0;
        }
        try {
            beginTransaction();
            int executeUpdateDelete = executeUpdateDelete(SqlInfoBuilder.buildDeleteSqlInfo((TableEntity<?>) table, whereBuilder));
            setTransactionSuccessful();
            return executeUpdateDelete;
        } finally {
            endTransaction();
        }
    }

    @Override // org.xutils.DbManager
    public void update(Object obj, String... strArr) throws DbException {
        try {
            beginTransaction();
            if (obj instanceof List) {
                List<Object> list = (List) obj;
                if (list.isEmpty()) {
                    return;
                }
                TableEntity table = getTable(list.get(0).getClass());
                if (!table.tableIsExist()) {
                    return;
                }
                for (Object obj2 : list) {
                    execNonQuery(SqlInfoBuilder.buildUpdateSqlInfo(table, obj2, strArr));
                }
            } else {
                TableEntity table2 = getTable(obj.getClass());
                if (!table2.tableIsExist()) {
                    return;
                }
                execNonQuery(SqlInfoBuilder.buildUpdateSqlInfo(table2, obj, strArr));
            }
            setTransactionSuccessful();
        } finally {
            endTransaction();
        }
    }

    @Override // org.xutils.DbManager
    public int update(Class<?> cls, WhereBuilder whereBuilder, KeyValue... keyValueArr) throws DbException {
        TableEntity table = getTable(cls);
        if (!table.tableIsExist()) {
            return 0;
        }
        try {
            beginTransaction();
            int executeUpdateDelete = executeUpdateDelete(SqlInfoBuilder.buildUpdateSqlInfo(table, whereBuilder, keyValueArr));
            setTransactionSuccessful();
            return executeUpdateDelete;
        } finally {
            endTransaction();
        }
    }

    @Override // org.xutils.DbManager
    public <T> T findById(Class<T> cls, Object obj) throws DbException {
        Cursor execQuery;
        TableEntity<T> table = getTable(cls);
        if (table.tableIsExist() && (execQuery = execQuery(Selector.from(table).where(table.getId().getName(), SimpleComparison.EQUAL_TO_OPERATION, obj).limit(1).toString())) != null) {
            try {
                if (execQuery.moveToNext()) {
                    return (T) CursorUtils.getEntity(table, execQuery);
                }
            } finally {
            }
        }
        return null;
    }

    @Override // org.xutils.DbManager
    public <T> T findFirst(Class<T> cls) throws DbException {
        return selector(cls).findFirst();
    }

    @Override // org.xutils.DbManager
    public <T> List<T> findAll(Class<T> cls) throws DbException {
        return selector(cls).findAll();
    }

    @Override // org.xutils.DbManager
    public <T> Selector<T> selector(Class<T> cls) throws DbException {
        return Selector.from(getTable(cls));
    }

    @Override // org.xutils.DbManager
    public DbModel findDbModelFirst(SqlInfo sqlInfo) throws DbException {
        Cursor execQuery = execQuery(sqlInfo);
        if (execQuery != null) {
            try {
                if (!execQuery.moveToNext()) {
                    return null;
                }
                return CursorUtils.getDbModel(execQuery);
            } catch (Throwable th) {
                try {
                    throw new DbException(th);
                } finally {
                    IOUtil.closeQuietly(execQuery);
                }
            }
        }
        return null;
    }

    @Override // org.xutils.DbManager
    public List<DbModel> findDbModelAll(SqlInfo sqlInfo) throws DbException {
        ArrayList arrayList = new ArrayList();
        Cursor execQuery = execQuery(sqlInfo);
        if (execQuery != null) {
            while (execQuery.moveToNext()) {
                try {
                    arrayList.add(CursorUtils.getDbModel(execQuery));
                } finally {
                }
            }
        }
        return arrayList;
    }

    private SQLiteDatabase openOrCreateDatabase(DbManager.DaoConfig daoConfig) {
        File dbDir = daoConfig.getDbDir();
        if (dbDir != null && (dbDir.exists() || dbDir.mkdirs())) {
            return SQLiteDatabase.openOrCreateDatabase(new File(dbDir, daoConfig.getDbName()), (SQLiteDatabase.CursorFactory) null);
        }
        return C5540x.app().openOrCreateDatabase(daoConfig.getDbName(), 0, null);
    }

    private void saveOrUpdateWithoutTransaction(TableEntity<?> tableEntity, Object obj) throws DbException {
        ColumnEntity id = tableEntity.getId();
        if (id.isAutoId()) {
            if (id.getColumnValue(obj) != null) {
                execNonQuery(SqlInfoBuilder.buildUpdateSqlInfo(tableEntity, obj, new String[0]));
                return;
            } else {
                saveBindingIdWithoutTransaction(tableEntity, obj);
                return;
            }
        }
        execNonQuery(SqlInfoBuilder.buildReplaceSqlInfo(tableEntity, obj));
    }

    private boolean saveBindingIdWithoutTransaction(TableEntity<?> tableEntity, Object obj) throws DbException {
        ColumnEntity id = tableEntity.getId();
        if (id.isAutoId()) {
            execNonQuery(SqlInfoBuilder.buildInsertSqlInfo(tableEntity, obj));
            long lastAutoIncrementId = getLastAutoIncrementId(tableEntity.getName());
            if (lastAutoIncrementId == -1) {
                return false;
            }
            id.setAutoIdValue(obj, lastAutoIncrementId);
            return true;
        }
        execNonQuery(SqlInfoBuilder.buildInsertSqlInfo(tableEntity, obj));
        return true;
    }

    private long getLastAutoIncrementId(String str) throws DbException {
        Cursor execQuery = execQuery("SELECT seq FROM sqlite_sequence WHERE name='" + str + "' LIMIT 1");
        long j = -1;
        if (execQuery != null) {
            try {
                if (execQuery.moveToNext()) {
                    j = execQuery.getLong(0);
                }
            } finally {
            }
        }
        return j;
    }

    @Override // org.xutils.DbManager, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (DAO_MAP.containsKey(this.daoConfig)) {
            DAO_MAP.remove(this.daoConfig);
            this.database.close();
        }
    }

    private void beginTransaction() {
        if (this.allowTransaction) {
            if (Build.VERSION.SDK_INT >= 16 && this.database.isWriteAheadLoggingEnabled()) {
                this.database.beginTransactionNonExclusive();
            } else {
                this.database.beginTransaction();
            }
        }
    }

    private void setTransactionSuccessful() {
        if (this.allowTransaction) {
            this.database.setTransactionSuccessful();
        }
    }

    private void endTransaction() {
        if (this.allowTransaction) {
            this.database.endTransaction();
        }
    }

    @Override // org.xutils.DbManager
    public int executeUpdateDelete(SqlInfo sqlInfo) throws DbException {
        SQLiteStatement sQLiteStatement = null;
        try {
            sQLiteStatement = sqlInfo.buildStatement(this.database);
            return sQLiteStatement.executeUpdateDelete();
        } catch (Throwable th) {
            try {
                throw new DbException(th);
            } finally {
                if (sQLiteStatement != null) {
                    try {
                        sQLiteStatement.releaseReference();
                    } catch (Throwable th2) {
                        LogUtil.m43e(th2.getMessage(), th2);
                    }
                }
            }
        }
    }

    @Override // org.xutils.DbManager
    public int executeUpdateDelete(String str) throws DbException {
        SQLiteStatement sQLiteStatement = null;
        try {
            sQLiteStatement = this.database.compileStatement(str);
            return sQLiteStatement.executeUpdateDelete();
        } catch (Throwable th) {
            try {
                throw new DbException(th);
            } finally {
                if (sQLiteStatement != null) {
                    try {
                        sQLiteStatement.releaseReference();
                    } catch (Throwable th2) {
                        LogUtil.m43e(th2.getMessage(), th2);
                    }
                }
            }
        }
    }

    @Override // org.xutils.DbManager
    public void execNonQuery(SqlInfo sqlInfo) throws DbException {
        SQLiteStatement sQLiteStatement = null;
        try {
            sQLiteStatement = sqlInfo.buildStatement(this.database);
            sQLiteStatement.execute();
            if (sQLiteStatement == null) {
                return;
            }
            try {
                sQLiteStatement.releaseReference();
            } catch (Throwable th) {
                LogUtil.m43e(th.getMessage(), th);
            }
        } catch (Throwable th2) {
            try {
                throw new DbException(th2);
            } catch (Throwable th3) {
                if (sQLiteStatement != null) {
                    try {
                        sQLiteStatement.releaseReference();
                    } catch (Throwable th4) {
                        LogUtil.m43e(th4.getMessage(), th4);
                    }
                }
                throw th3;
            }
        }
    }

    @Override // org.xutils.DbManager
    public void execNonQuery(String str) throws DbException {
        try {
            this.database.execSQL(str);
        } catch (Throwable th) {
            throw new DbException(th);
        }
    }

    @Override // org.xutils.DbManager
    public Cursor execQuery(SqlInfo sqlInfo) throws DbException {
        try {
            return this.database.rawQuery(sqlInfo.getSql(), sqlInfo.getBindArgsAsStrArray());
        } catch (Throwable th) {
            throw new DbException(th);
        }
    }

    @Override // org.xutils.DbManager
    public Cursor execQuery(String str) throws DbException {
        try {
            return this.database.rawQuery(str, null);
        } catch (Throwable th) {
            throw new DbException(th);
        }
    }
}
