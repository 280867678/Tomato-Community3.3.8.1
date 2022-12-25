package com.j256.ormlite.android;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.misc.VersionUtils;
import com.j256.ormlite.stmt.GenericRowMapper;
import com.j256.ormlite.stmt.StatementBuilder;
import com.j256.ormlite.support.CompiledStatement;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.support.GeneratedKeyHolder;
import java.sql.SQLException;
import java.sql.Savepoint;

/* loaded from: classes3.dex */
public class AndroidDatabaseConnection implements DatabaseConnection {
    public static final String ANDROID_VERSION = "VERSION__4.48__";
    public final boolean cancelQueriesEnabled;

    /* renamed from: db */
    public final SQLiteDatabase f1490db;
    public final boolean readWrite;
    public static Logger logger = LoggerFactory.getLogger(AndroidDatabaseConnection.class);
    public static final String[] NO_STRING_ARGS = new String[0];

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.j256.ormlite.android.AndroidDatabaseConnection$1 */
    /* loaded from: classes3.dex */
    public static /* synthetic */ class C21751 {
        public static final /* synthetic */ int[] $SwitchMap$com$j256$ormlite$field$SqlType = new int[SqlType.values().length];

        static {
            try {
                $SwitchMap$com$j256$ormlite$field$SqlType[SqlType.STRING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$j256$ormlite$field$SqlType[SqlType.LONG_STRING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$j256$ormlite$field$SqlType[SqlType.CHAR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$j256$ormlite$field$SqlType[SqlType.BOOLEAN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$j256$ormlite$field$SqlType[SqlType.BYTE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$j256$ormlite$field$SqlType[SqlType.SHORT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$j256$ormlite$field$SqlType[SqlType.INTEGER.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$j256$ormlite$field$SqlType[SqlType.LONG.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$j256$ormlite$field$SqlType[SqlType.FLOAT.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$j256$ormlite$field$SqlType[SqlType.DOUBLE.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$j256$ormlite$field$SqlType[SqlType.BYTE_ARRAY.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$j256$ormlite$field$SqlType[SqlType.SERIALIZABLE.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$j256$ormlite$field$SqlType[SqlType.DATE.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$j256$ormlite$field$SqlType[SqlType.BLOB.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$j256$ormlite$field$SqlType[SqlType.BIG_DECIMAL.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$j256$ormlite$field$SqlType[SqlType.UNKNOWN.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
        }
    }

    /* loaded from: classes3.dex */
    private static class OurSavePoint implements Savepoint {
        public String name;

        public OurSavePoint(String str) {
            this.name = str;
        }

        @Override // java.sql.Savepoint
        public int getSavepointId() {
            return 0;
        }

        @Override // java.sql.Savepoint
        public String getSavepointName() {
            return this.name;
        }
    }

    static {
        VersionUtils.checkCoreVersusAndroidVersions("VERSION__4.48__");
    }

    public AndroidDatabaseConnection(SQLiteDatabase sQLiteDatabase, boolean z) {
        this(sQLiteDatabase, z, false);
    }

    public AndroidDatabaseConnection(SQLiteDatabase sQLiteDatabase, boolean z, boolean z2) {
        this.f1490db = sQLiteDatabase;
        this.readWrite = z;
        this.cancelQueriesEnabled = z2;
        logger.trace("{}: db {} opened, read-write = {}", this, sQLiteDatabase, Boolean.valueOf(z));
    }

    private void bindArgs(SQLiteStatement sQLiteStatement, Object[] objArr, FieldType[] fieldTypeArr) {
        if (objArr == null) {
            return;
        }
        for (int i = 0; i < objArr.length; i++) {
            Object obj = objArr[i];
            if (obj == null) {
                sQLiteStatement.bindNull(i + 1);
            } else {
                SqlType sqlType = fieldTypeArr[i].getSqlType();
                switch (C21751.$SwitchMap$com$j256$ormlite$field$SqlType[sqlType.ordinal()]) {
                    case 1:
                    case 2:
                    case 3:
                        sQLiteStatement.bindString(i + 1, obj.toString());
                        continue;
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                        sQLiteStatement.bindLong(i + 1, ((Number) obj).longValue());
                        continue;
                    case 9:
                    case 10:
                        sQLiteStatement.bindDouble(i + 1, ((Number) obj).doubleValue());
                        continue;
                    case 11:
                    case 12:
                        sQLiteStatement.bindBlob(i + 1, (byte[]) obj);
                        continue;
                    case 13:
                    case 14:
                    case 15:
                        throw new SQLException("Invalid Android type: " + sqlType);
                    default:
                        throw new SQLException("Unknown sql argument type: " + sqlType);
                }
            }
        }
    }

    private String[] toStrings(Object[] objArr) {
        if (objArr == null || objArr.length == 0) {
            return null;
        }
        String[] strArr = new String[objArr.length];
        for (int i = 0; i < objArr.length; i++) {
            Object obj = objArr[i];
            if (obj == null) {
                strArr[i] = null;
            } else {
                strArr[i] = obj.toString();
            }
        }
        return strArr;
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0022, code lost:
        if (r0 != null) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0035, code lost:
        com.j256.ormlite.android.AndroidDatabaseConnection.logger.trace("{} statement is compiled and executed, changed {}: {}", r6, java.lang.Integer.valueOf(r5), r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0041, code lost:
        return r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0032, code lost:
        r0.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0030, code lost:
        if (r0 == null) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int update(String str, Object[] objArr, FieldType[] fieldTypeArr, String str2) {
        SQLiteStatement compileStatement;
        int i;
        SQLiteStatement sQLiteStatement = null;
        try {
            try {
                compileStatement = this.f1490db.compileStatement(str);
            } catch (android.database.SQLException e) {
                e = e;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            bindArgs(compileStatement, objArr, fieldTypeArr);
            compileStatement.execute();
            if (compileStatement != null) {
                compileStatement.close();
            } else {
                sQLiteStatement = compileStatement;
            }
            try {
                sQLiteStatement = this.f1490db.compileStatement("SELECT CHANGES()");
                i = (int) sQLiteStatement.simpleQueryForLong();
            } catch (android.database.SQLException unused) {
                i = 1;
            } catch (Throwable th2) {
                if (sQLiteStatement != null) {
                    sQLiteStatement.close();
                }
                throw th2;
            }
        } catch (android.database.SQLException e2) {
            e = e2;
            sQLiteStatement = compileStatement;
            StringBuilder sb = new StringBuilder();
            sb.append("updating database failed: ");
            sb.append(str);
            throw SqlExceptionUtil.create(sb.toString(), e);
        } catch (Throwable th3) {
            th = th3;
            sQLiteStatement = compileStatement;
            if (sQLiteStatement != null) {
                sQLiteStatement.close();
            }
            throw th;
        }
    }

    @Override // com.j256.ormlite.support.DatabaseConnection
    public void close() {
        try {
            this.f1490db.close();
            logger.trace("{}: db {} closed", this, this.f1490db);
        } catch (android.database.SQLException e) {
            throw SqlExceptionUtil.create("problems closing the database connection", e);
        }
    }

    @Override // com.j256.ormlite.support.DatabaseConnection
    public void closeQuietly() {
        try {
            close();
        } catch (SQLException unused) {
        }
    }

    @Override // com.j256.ormlite.support.DatabaseConnection
    public void commit(Savepoint savepoint) {
        try {
            this.f1490db.setTransactionSuccessful();
            this.f1490db.endTransaction();
            if (savepoint == null) {
                logger.trace("{}: transaction is successfuly ended", this);
                return;
            }
            logger.trace("{}: transaction {} is successfuly ended", this, savepoint.getSavepointName());
        } catch (android.database.SQLException e) {
            if (savepoint == null) {
                throw SqlExceptionUtil.create("problems commiting transaction", e);
            }
            throw SqlExceptionUtil.create("problems commiting transaction " + savepoint.getSavepointName(), e);
        }
    }

    @Override // com.j256.ormlite.support.DatabaseConnection
    public CompiledStatement compileStatement(String str, StatementBuilder.StatementType statementType, FieldType[] fieldTypeArr, int i) {
        AndroidCompiledStatement androidCompiledStatement = new AndroidCompiledStatement(str, this.f1490db, statementType, this.cancelQueriesEnabled);
        logger.trace("{}: compiled statement got {}: {}", this, androidCompiledStatement, str);
        return androidCompiledStatement;
    }

    @Override // com.j256.ormlite.support.DatabaseConnection
    public int delete(String str, Object[] objArr, FieldType[] fieldTypeArr) {
        return update(str, objArr, fieldTypeArr, "deleted");
    }

    @Override // com.j256.ormlite.support.DatabaseConnection
    public int executeStatement(String str, int i) {
        return AndroidCompiledStatement.execSql(this.f1490db, str, str, NO_STRING_ARGS);
    }

    @Override // com.j256.ormlite.support.DatabaseConnection
    public int insert(String str, Object[] objArr, FieldType[] fieldTypeArr, GeneratedKeyHolder generatedKeyHolder) {
        SQLiteStatement sQLiteStatement = null;
        try {
            try {
                sQLiteStatement = this.f1490db.compileStatement(str);
                bindArgs(sQLiteStatement, objArr, fieldTypeArr);
                long executeInsert = sQLiteStatement.executeInsert();
                if (generatedKeyHolder != null) {
                    generatedKeyHolder.addKey(Long.valueOf(executeInsert));
                }
                logger.trace("{}: insert statement is compiled and executed, changed {}: {}", (Object) this, (Object) 1, (Object) str);
                return 1;
            } catch (android.database.SQLException e) {
                StringBuilder sb = new StringBuilder();
                sb.append("inserting to database failed: ");
                sb.append(str);
                throw SqlExceptionUtil.create(sb.toString(), e);
            }
        } finally {
            if (sQLiteStatement != null) {
                sQLiteStatement.close();
            }
        }
    }

    @Override // com.j256.ormlite.support.DatabaseConnection
    public boolean isAutoCommit() {
        try {
            boolean inTransaction = this.f1490db.inTransaction();
            logger.trace("{}: in transaction is {}", this, Boolean.valueOf(inTransaction));
            return !inTransaction;
        } catch (android.database.SQLException e) {
            throw SqlExceptionUtil.create("problems getting auto-commit from database", e);
        }
    }

    @Override // com.j256.ormlite.support.DatabaseConnection
    public boolean isAutoCommitSupported() {
        return true;
    }

    @Override // com.j256.ormlite.support.DatabaseConnection
    public boolean isClosed() {
        try {
            boolean isOpen = this.f1490db.isOpen();
            logger.trace("{}: db {} isOpen returned {}", this, this.f1490db, Boolean.valueOf(isOpen));
            return !isOpen;
        } catch (android.database.SQLException e) {
            throw SqlExceptionUtil.create("problems detecting if the database is closed", e);
        }
    }

    public boolean isReadWrite() {
        return this.readWrite;
    }

    @Override // com.j256.ormlite.support.DatabaseConnection
    public boolean isTableExists(String str) {
        SQLiteDatabase sQLiteDatabase = this.f1490db;
        Cursor rawQuery = sQLiteDatabase.rawQuery("SELECT DISTINCT tbl_name FROM sqlite_master WHERE tbl_name = '" + str + "'", null);
        try {
            boolean z = rawQuery.getCount() > 0;
            logger.trace("{}: isTableExists '{}' returned {}", this, str, Boolean.valueOf(z));
            return z;
        } finally {
            rawQuery.close();
        }
    }

    @Override // com.j256.ormlite.support.DatabaseConnection
    public long queryForLong(String str) {
        SQLiteStatement sQLiteStatement = null;
        try {
            try {
                sQLiteStatement = this.f1490db.compileStatement(str);
                long simpleQueryForLong = sQLiteStatement.simpleQueryForLong();
                logger.trace("{}: query for long simple query returned {}: {}", this, Long.valueOf(simpleQueryForLong), str);
                return simpleQueryForLong;
            } catch (android.database.SQLException e) {
                StringBuilder sb = new StringBuilder();
                sb.append("queryForLong from database failed: ");
                sb.append(str);
                throw SqlExceptionUtil.create(sb.toString(), e);
            }
        } finally {
            if (sQLiteStatement != null) {
                sQLiteStatement.close();
            }
        }
    }

    @Override // com.j256.ormlite.support.DatabaseConnection
    public long queryForLong(String str, Object[] objArr, FieldType[] fieldTypeArr) {
        Cursor rawQuery;
        Cursor cursor = null;
        try {
            try {
                rawQuery = this.f1490db.rawQuery(str, toStrings(objArr));
            } catch (android.database.SQLException e) {
                e = e;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            AndroidDatabaseResults androidDatabaseResults = new AndroidDatabaseResults(rawQuery, null);
            long j = androidDatabaseResults.first() ? androidDatabaseResults.getLong(0) : 0L;
            logger.trace("{}: query for long raw query returned {}: {}", this, Long.valueOf(j), str);
            if (rawQuery != null) {
                rawQuery.close();
            }
            return j;
        } catch (android.database.SQLException e2) {
            cursor = rawQuery;
            e = e2;
            StringBuilder sb = new StringBuilder();
            sb.append("queryForLong from database failed: ");
            sb.append(str);
            throw SqlExceptionUtil.create(sb.toString(), e);
        } catch (Throwable th2) {
            th = th2;
            cursor = rawQuery;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    @Override // com.j256.ormlite.support.DatabaseConnection
    public <T> Object queryForOne(String str, Object[] objArr, FieldType[] fieldTypeArr, GenericRowMapper<T> genericRowMapper, ObjectCache objectCache) {
        Cursor rawQuery;
        Cursor cursor = null;
        try {
            try {
                rawQuery = this.f1490db.rawQuery(str, toStrings(objArr));
            } catch (android.database.SQLException e) {
                e = e;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            AndroidDatabaseResults androidDatabaseResults = new AndroidDatabaseResults(rawQuery, objectCache);
            logger.trace("{}: queried for one result: {}", this, str);
            if (!androidDatabaseResults.first()) {
                if (rawQuery != null) {
                    rawQuery.close();
                }
                return null;
            }
            T mapRow = genericRowMapper.mapRow(androidDatabaseResults);
            if (!androidDatabaseResults.next()) {
                if (rawQuery != null) {
                    rawQuery.close();
                }
                return mapRow;
            }
            Object obj = DatabaseConnection.MORE_THAN_ONE;
            if (rawQuery != null) {
                rawQuery.close();
            }
            return obj;
        } catch (android.database.SQLException e2) {
            cursor = rawQuery;
            e = e2;
            StringBuilder sb = new StringBuilder();
            sb.append("queryForOne from database failed: ");
            sb.append(str);
            throw SqlExceptionUtil.create(sb.toString(), e);
        } catch (Throwable th2) {
            th = th2;
            cursor = rawQuery;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    @Override // com.j256.ormlite.support.DatabaseConnection
    public void rollback(Savepoint savepoint) {
        try {
            this.f1490db.endTransaction();
            if (savepoint == null) {
                logger.trace("{}: transaction is ended, unsuccessfuly", this);
                return;
            }
            logger.trace("{}: transaction {} is ended, unsuccessfuly", this, savepoint.getSavepointName());
        } catch (android.database.SQLException e) {
            if (savepoint == null) {
                throw SqlExceptionUtil.create("problems rolling back transaction", e);
            }
            throw SqlExceptionUtil.create("problems rolling back transaction " + savepoint.getSavepointName(), e);
        }
    }

    @Override // com.j256.ormlite.support.DatabaseConnection
    public void setAutoCommit(boolean z) {
        if (!z) {
            if (this.f1490db.inTransaction()) {
                return;
            }
            this.f1490db.beginTransaction();
        } else if (!this.f1490db.inTransaction()) {
        } else {
            this.f1490db.setTransactionSuccessful();
            this.f1490db.endTransaction();
        }
    }

    @Override // com.j256.ormlite.support.DatabaseConnection
    public Savepoint setSavePoint(String str) {
        try {
            this.f1490db.beginTransaction();
            logger.trace("{}: save-point set with name {}", this, str);
            return new OurSavePoint(str);
        } catch (android.database.SQLException e) {
            throw SqlExceptionUtil.create("problems beginning transaction " + str, e);
        }
    }

    public String toString() {
        return AndroidDatabaseConnection.class.getSimpleName() + "@" + Integer.toHexString(super.hashCode());
    }

    @Override // com.j256.ormlite.support.DatabaseConnection
    public int update(String str, Object[] objArr, FieldType[] fieldTypeArr) {
        return update(str, objArr, fieldTypeArr, "updated");
    }
}
