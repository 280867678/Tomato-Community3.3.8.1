package io.requery.android.database.sqlite;

import androidx.core.os.CancellationSignal;
import androidx.sqlite.db.SupportSQLiteProgram;
import java.util.Arrays;

/* loaded from: classes4.dex */
public abstract class SQLiteProgram extends SQLiteClosable implements SupportSQLiteProgram {
    private static final String[] EMPTY_STRING_ARRAY = new String[0];
    private final Object[] mBindArgs;
    private final String[] mColumnNames;
    private final SQLiteDatabase mDatabase;
    private final int mNumParameters;
    private final boolean mReadOnly;
    private final String mSql;

    /* JADX INFO: Access modifiers changed from: package-private */
    public SQLiteProgram(SQLiteDatabase sQLiteDatabase, String str, Object[] objArr, CancellationSignal cancellationSignal) {
        this.mDatabase = sQLiteDatabase;
        this.mSql = str.trim();
        int sqlStatementType = SQLiteStatementType.getSqlStatementType(this.mSql);
        if (sqlStatementType == 4 || sqlStatementType == 5 || sqlStatementType == 6) {
            this.mReadOnly = false;
            this.mColumnNames = EMPTY_STRING_ARRAY;
            this.mNumParameters = 0;
        } else {
            boolean z = sqlStatementType != 1 ? false : true;
            SQLiteStatementInfo sQLiteStatementInfo = new SQLiteStatementInfo();
            sQLiteDatabase.getThreadSession().prepare(this.mSql, sQLiteDatabase.getThreadDefaultConnectionFlags(z), cancellationSignal, sQLiteStatementInfo);
            this.mReadOnly = sQLiteStatementInfo.readOnly;
            this.mColumnNames = sQLiteStatementInfo.columnNames;
            this.mNumParameters = sQLiteStatementInfo.numParameters;
        }
        if (objArr != null && objArr.length > this.mNumParameters) {
            throw new IllegalArgumentException("Too many bind arguments.  " + objArr.length + " arguments were provided but the statement needs " + this.mNumParameters + " arguments.");
        }
        int i = this.mNumParameters;
        if (i != 0) {
            this.mBindArgs = new Object[i];
            if (objArr == null) {
                return;
            }
            System.arraycopy(objArr, 0, this.mBindArgs, 0, objArr.length);
            return;
        }
        this.mBindArgs = null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final SQLiteDatabase getDatabase() {
        return this.mDatabase;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final String getSql() {
        return this.mSql;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final Object[] getBindArgs() {
        return this.mBindArgs;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final String[] getColumnNames() {
        return this.mColumnNames;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final SQLiteSession getSession() {
        return this.mDatabase.getThreadSession();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final int getConnectionFlags() {
        return this.mDatabase.getThreadDefaultConnectionFlags(this.mReadOnly);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void onCorruption() {
        this.mDatabase.onCorruption();
    }

    public void bindNull(int i) {
        bind(i, null);
    }

    public void bindLong(int i, long j) {
        bind(i, Long.valueOf(j));
    }

    public void bindDouble(int i, double d) {
        bind(i, Double.valueOf(d));
    }

    public void bindString(int i, String str) {
        if (str == null) {
            throw new IllegalArgumentException("the bind value at index " + i + " is null");
        }
        bind(i, str);
    }

    public void bindBlob(int i, byte[] bArr) {
        if (bArr == null) {
            throw new IllegalArgumentException("the bind value at index " + i + " is null");
        }
        bind(i, bArr);
    }

    public void bindObject(int i, Object obj) {
        if (obj == null) {
            bindNull(i);
        } else if ((obj instanceof Double) || (obj instanceof Float)) {
            bindDouble(i, ((Number) obj).doubleValue());
        } else if (obj instanceof Number) {
            bindLong(i, ((Number) obj).longValue());
        } else if (obj instanceof Boolean) {
            if (((Boolean) obj).booleanValue()) {
                bindLong(i, 1L);
            } else {
                bindLong(i, 0L);
            }
        } else if (obj instanceof byte[]) {
            bindBlob(i, (byte[]) obj);
        } else {
            bindString(i, obj.toString());
        }
    }

    public void clearBindings() {
        Object[] objArr = this.mBindArgs;
        if (objArr != null) {
            Arrays.fill(objArr, (Object) null);
        }
    }

    public void bindAllArgsAsStrings(String[] strArr) {
        if (strArr != null) {
            for (int length = strArr.length; length != 0; length--) {
                bindString(length, strArr[length - 1]);
            }
        }
    }

    @Override // io.requery.android.database.sqlite.SQLiteClosable
    protected void onAllReferencesReleased() {
        clearBindings();
    }

    private void bind(int i, Object obj) {
        if (i < 1 || i > this.mNumParameters) {
            throw new IllegalArgumentException("Cannot bind argument at index " + i + " because the index is out of range.  The statement has " + this.mNumParameters + " parameters.");
        }
        this.mBindArgs[i - 1] = obj;
    }
}
