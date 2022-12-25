package org.xutils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.xutils.common.util.KeyValue;
import org.xutils.p148db.Selector;
import org.xutils.p148db.sqlite.SqlInfo;
import org.xutils.p148db.sqlite.WhereBuilder;
import org.xutils.p148db.table.DbModel;
import org.xutils.p148db.table.TableEntity;
import org.xutils.p149ex.DbException;

/* loaded from: classes4.dex */
public interface DbManager extends Closeable {

    /* loaded from: classes4.dex */
    public interface DbOpenListener {
        void onDbOpened(DbManager dbManager);
    }

    /* loaded from: classes4.dex */
    public interface DbUpgradeListener {
        void onUpgrade(DbManager dbManager, int i, int i2);
    }

    /* loaded from: classes4.dex */
    public interface TableCreateListener {
        void onTableCreated(DbManager dbManager, TableEntity<?> tableEntity);
    }

    void addColumn(Class<?> cls, String str) throws DbException;

    @Override // java.io.Closeable, java.lang.AutoCloseable
    void close() throws IOException;

    int delete(Class<?> cls, WhereBuilder whereBuilder) throws DbException;

    void delete(Class<?> cls) throws DbException;

    void delete(Object obj) throws DbException;

    void deleteById(Class<?> cls, Object obj) throws DbException;

    void dropDb() throws DbException;

    void dropTable(Class<?> cls) throws DbException;

    void execNonQuery(String str) throws DbException;

    void execNonQuery(SqlInfo sqlInfo) throws DbException;

    Cursor execQuery(String str) throws DbException;

    Cursor execQuery(SqlInfo sqlInfo) throws DbException;

    int executeUpdateDelete(String str) throws DbException;

    int executeUpdateDelete(SqlInfo sqlInfo) throws DbException;

    <T> List<T> findAll(Class<T> cls) throws DbException;

    <T> T findById(Class<T> cls, Object obj) throws DbException;

    List<DbModel> findDbModelAll(SqlInfo sqlInfo) throws DbException;

    DbModel findDbModelFirst(SqlInfo sqlInfo) throws DbException;

    <T> T findFirst(Class<T> cls) throws DbException;

    DaoConfig getDaoConfig();

    SQLiteDatabase getDatabase();

    <T> TableEntity<T> getTable(Class<T> cls) throws DbException;

    void replace(Object obj) throws DbException;

    void save(Object obj) throws DbException;

    boolean saveBindingId(Object obj) throws DbException;

    void saveOrUpdate(Object obj) throws DbException;

    <T> Selector<T> selector(Class<T> cls) throws DbException;

    int update(Class<?> cls, WhereBuilder whereBuilder, KeyValue... keyValueArr) throws DbException;

    void update(Object obj, String... strArr) throws DbException;

    /* loaded from: classes4.dex */
    public static class DaoConfig {
        private File dbDir;
        private DbOpenListener dbOpenListener;
        private DbUpgradeListener dbUpgradeListener;
        private TableCreateListener tableCreateListener;
        private String dbName = "xUtils.db";
        private int dbVersion = 1;
        private boolean allowTransaction = true;

        public DaoConfig setDbDir(File file) {
            this.dbDir = file;
            return this;
        }

        public DaoConfig setDbName(String str) {
            if (!TextUtils.isEmpty(str)) {
                this.dbName = str;
            }
            return this;
        }

        public DaoConfig setDbVersion(int i) {
            this.dbVersion = i;
            return this;
        }

        public DaoConfig setAllowTransaction(boolean z) {
            this.allowTransaction = z;
            return this;
        }

        public DaoConfig setDbOpenListener(DbOpenListener dbOpenListener) {
            this.dbOpenListener = dbOpenListener;
            return this;
        }

        public DaoConfig setDbUpgradeListener(DbUpgradeListener dbUpgradeListener) {
            this.dbUpgradeListener = dbUpgradeListener;
            return this;
        }

        public DaoConfig setTableCreateListener(TableCreateListener tableCreateListener) {
            this.tableCreateListener = tableCreateListener;
            return this;
        }

        public File getDbDir() {
            return this.dbDir;
        }

        public String getDbName() {
            return this.dbName;
        }

        public int getDbVersion() {
            return this.dbVersion;
        }

        public boolean isAllowTransaction() {
            return this.allowTransaction;
        }

        public DbOpenListener getDbOpenListener() {
            return this.dbOpenListener;
        }

        public DbUpgradeListener getDbUpgradeListener() {
            return this.dbUpgradeListener;
        }

        public TableCreateListener getTableCreateListener() {
            return this.tableCreateListener;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || DaoConfig.class != obj.getClass()) {
                return false;
            }
            DaoConfig daoConfig = (DaoConfig) obj;
            if (!this.dbName.equals(daoConfig.dbName)) {
                return false;
            }
            File file = this.dbDir;
            File file2 = daoConfig.dbDir;
            return file == null ? file2 == null : file.equals(file2);
        }

        public int hashCode() {
            int hashCode = this.dbName.hashCode() * 31;
            File file = this.dbDir;
            return hashCode + (file != null ? file.hashCode() : 0);
        }

        public String toString() {
            return String.valueOf(this.dbDir) + "/" + this.dbName;
        }
    }
}
