package com.j256.ormlite.android;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.j256.ormlite.android.compat.ApiCompatibility;
import com.j256.ormlite.android.compat.ApiCompatibilityUtils;
import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.stmt.StatementBuilder;
import com.j256.ormlite.support.CompiledStatement;
import com.j256.ormlite.support.DatabaseResults;
import com.tomatolive.library.utils.ConstantUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class AndroidCompiledStatement implements CompiledStatement {
    public List<Object> args;
    public final boolean cancelQueriesEnabled;
    public ApiCompatibility.CancellationHook cancellationHook;
    public Cursor cursor;

    /* renamed from: db */
    public final SQLiteDatabase f1489db;
    public Integer max;
    public final String sql;
    public final StatementBuilder.StatementType type;
    public static Logger logger = LoggerFactory.getLogger(AndroidCompiledStatement.class);
    public static final String[] NO_STRING_ARGS = new String[0];
    public static final ApiCompatibility apiCompatibility = ApiCompatibilityUtils.getCompatibility();

    /* renamed from: com.j256.ormlite.android.AndroidCompiledStatement$1 */
    /* loaded from: classes3.dex */
    static /* synthetic */ class C21741 {
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
                $SwitchMap$com$j256$ormlite$field$SqlType[SqlType.DATE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$j256$ormlite$field$SqlType[SqlType.BOOLEAN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$j256$ormlite$field$SqlType[SqlType.CHAR.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$j256$ormlite$field$SqlType[SqlType.BYTE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$j256$ormlite$field$SqlType[SqlType.SHORT.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$j256$ormlite$field$SqlType[SqlType.INTEGER.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$j256$ormlite$field$SqlType[SqlType.LONG.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$j256$ormlite$field$SqlType[SqlType.FLOAT.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$j256$ormlite$field$SqlType[SqlType.DOUBLE.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$j256$ormlite$field$SqlType[SqlType.BYTE_ARRAY.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$j256$ormlite$field$SqlType[SqlType.SERIALIZABLE.ordinal()] = 13;
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

    public AndroidCompiledStatement(String str, SQLiteDatabase sQLiteDatabase, StatementBuilder.StatementType statementType, boolean z) {
        this.sql = str;
        this.f1489db = sQLiteDatabase;
        this.type = statementType;
        this.cancelQueriesEnabled = z;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0020, code lost:
        r5.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x001e, code lost:
        if (r5 == null) goto L8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0010, code lost:
        if (r5 != null) goto L11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0023, code lost:
        com.j256.ormlite.android.AndroidCompiledStatement.logger.trace("executing statement {} changed {} rows: {}", r3, java.lang.Integer.valueOf(r2), r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x002f, code lost:
        return r2;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int execSql(SQLiteDatabase sQLiteDatabase, String str, String str2, Object[] objArr) {
        int i;
        try {
            sQLiteDatabase.execSQL(str2, objArr);
            SQLiteStatement sQLiteStatement = null;
            try {
                sQLiteStatement = sQLiteDatabase.compileStatement("SELECT CHANGES()");
                i = (int) sQLiteStatement.simpleQueryForLong();
            } catch (SQLException unused) {
                i = 1;
            } catch (Throwable th) {
                if (sQLiteStatement != null) {
                    sQLiteStatement.close();
                }
                throw th;
            }
        } catch (SQLException e) {
            throw SqlExceptionUtil.create("Problems executing " + str + " Android statement: " + str2, e);
        }
    }

    private Object[] getArgArray() {
        List<Object> list = this.args;
        return list == null ? NO_STRING_ARGS : list.toArray(new Object[list.size()]);
    }

    private String[] getStringArray() {
        List<Object> list = this.args;
        return list == null ? NO_STRING_ARGS : (String[]) list.toArray(new String[list.size()]);
    }

    private void isInPrep() {
        if (this.cursor == null) {
            return;
        }
        throw new java.sql.SQLException("Query already run. Cannot add argument values.");
    }

    @Override // com.j256.ormlite.support.CompiledStatement
    public void cancel() {
        ApiCompatibility.CancellationHook cancellationHook = this.cancellationHook;
        if (cancellationHook != null) {
            cancellationHook.cancel();
        }
    }

    @Override // com.j256.ormlite.support.CompiledStatement
    public void close() {
        Cursor cursor = this.cursor;
        if (cursor != null) {
            try {
                cursor.close();
            } catch (SQLException e) {
                throw SqlExceptionUtil.create("Problems closing Android cursor", e);
            }
        }
        this.cancellationHook = null;
    }

    @Override // com.j256.ormlite.support.CompiledStatement
    public void closeQuietly() {
        try {
            close();
        } catch (java.sql.SQLException unused) {
        }
    }

    @Override // com.j256.ormlite.support.CompiledStatement
    public int getColumnCount() {
        return getCursor().getColumnCount();
    }

    @Override // com.j256.ormlite.support.CompiledStatement
    public String getColumnName(int i) {
        return getCursor().getColumnName(i);
    }

    public Cursor getCursor() {
        if (this.cursor == null) {
            String str = null;
            try {
                if (this.max == null) {
                    str = this.sql;
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append(this.sql);
                    sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
                    sb.append(this.max);
                    str = sb.toString();
                }
                if (this.cancelQueriesEnabled) {
                    this.cancellationHook = apiCompatibility.createCancellationHook();
                }
                this.cursor = apiCompatibility.rawQuery(this.f1489db, str, getStringArray(), this.cancellationHook);
                this.cursor.moveToFirst();
                logger.trace("{}: started rawQuery cursor for: {}", this, str);
            } catch (SQLException e) {
                throw SqlExceptionUtil.create("Problems executing Android query: " + str, e);
            }
        }
        return this.cursor;
    }

    @Override // com.j256.ormlite.support.CompiledStatement
    public int runExecute() {
        if (this.type.isOkForExecute()) {
            return execSql(this.f1489db, "runExecute", this.sql, getArgArray());
        }
        throw new IllegalArgumentException("Cannot call execute on a " + this.type + " statement");
    }

    @Override // com.j256.ormlite.support.CompiledStatement
    public DatabaseResults runQuery(ObjectCache objectCache) {
        if (this.type.isOkForQuery()) {
            return new AndroidDatabaseResults(getCursor(), objectCache);
        }
        throw new IllegalArgumentException("Cannot call query on a " + this.type + " statement");
    }

    @Override // com.j256.ormlite.support.CompiledStatement
    public int runUpdate() {
        String str;
        if (!this.type.isOkForUpdate()) {
            throw new IllegalArgumentException("Cannot call update on a " + this.type + " statement");
        }
        if (this.max == null) {
            str = this.sql;
        } else {
            str = this.sql + ConstantUtils.PLACEHOLDER_STR_ONE + this.max;
        }
        return execSql(this.f1489db, "runUpdate", str, getArgArray());
    }

    @Override // com.j256.ormlite.support.CompiledStatement
    public void setMaxRows(int i) {
        isInPrep();
        this.max = Integer.valueOf(i);
    }

    @Override // com.j256.ormlite.support.CompiledStatement
    public void setObject(int i, Object obj, SqlType sqlType) {
        List<Object> list;
        isInPrep();
        if (this.args == null) {
            this.args = new ArrayList();
        }
        if (obj == null) {
            this.args.add(i, null);
            return;
        }
        switch (C21741.$SwitchMap$com$j256$ormlite$field$SqlType[sqlType.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                list = this.args;
                obj = obj.toString();
                break;
            case 12:
            case 13:
                list = this.args;
                break;
            case 14:
            case 15:
                throw new java.sql.SQLException("Invalid Android type: " + sqlType);
            default:
                throw new java.sql.SQLException("Unknown sql argument type: " + sqlType);
        }
        list.add(i, obj);
    }

    @Override // com.j256.ormlite.support.CompiledStatement
    public void setQueryTimeout(long j) {
    }

    public String toString() {
        return AndroidCompiledStatement.class.getSimpleName() + "@" + Integer.toHexString(super.hashCode());
    }
}
