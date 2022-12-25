package org.xutils.p148db.sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import java.util.ArrayList;
import java.util.List;
import org.xutils.common.util.KeyValue;
import org.xutils.p148db.converter.ColumnConverterFactory;
import org.xutils.p148db.table.ColumnUtils;

/* renamed from: org.xutils.db.sqlite.SqlInfo */
/* loaded from: classes4.dex */
public final class SqlInfo {
    private List<KeyValue> bindArgs;
    private String sql;

    public SqlInfo() {
    }

    public SqlInfo(String str) {
        this.sql = str;
    }

    public String getSql() {
        return this.sql;
    }

    public void setSql(String str) {
        this.sql = str;
    }

    public void addBindArg(KeyValue keyValue) {
        if (this.bindArgs == null) {
            this.bindArgs = new ArrayList();
        }
        this.bindArgs.add(keyValue);
    }

    public void addBindArgs(List<KeyValue> list) {
        List<KeyValue> list2 = this.bindArgs;
        if (list2 == null) {
            this.bindArgs = list;
        } else {
            list2.addAll(list);
        }
    }

    public SQLiteStatement buildStatement(SQLiteDatabase sQLiteDatabase) {
        SQLiteStatement compileStatement = sQLiteDatabase.compileStatement(this.sql);
        if (this.bindArgs != null) {
            for (int i = 1; i < this.bindArgs.size() + 1; i++) {
                Object convert2DbValueIfNeeded = ColumnUtils.convert2DbValueIfNeeded(this.bindArgs.get(i - 1).value);
                if (convert2DbValueIfNeeded == null) {
                    compileStatement.bindNull(i);
                } else {
                    int i2 = C55241.$SwitchMap$org$xutils$db$sqlite$ColumnDbType[ColumnConverterFactory.getColumnConverter(convert2DbValueIfNeeded.getClass()).getColumnDbType().ordinal()];
                    if (i2 == 1) {
                        compileStatement.bindLong(i, ((Number) convert2DbValueIfNeeded).longValue());
                    } else if (i2 == 2) {
                        compileStatement.bindDouble(i, ((Number) convert2DbValueIfNeeded).doubleValue());
                    } else if (i2 == 3) {
                        compileStatement.bindString(i, convert2DbValueIfNeeded.toString());
                    } else if (i2 == 4) {
                        compileStatement.bindBlob(i, (byte[]) convert2DbValueIfNeeded);
                    } else {
                        compileStatement.bindNull(i);
                    }
                }
            }
        }
        return compileStatement;
    }

    /* renamed from: org.xutils.db.sqlite.SqlInfo$1 */
    /* loaded from: classes4.dex */
    static /* synthetic */ class C55241 {
        static final /* synthetic */ int[] $SwitchMap$org$xutils$db$sqlite$ColumnDbType = new int[ColumnDbType.values().length];

        static {
            try {
                $SwitchMap$org$xutils$db$sqlite$ColumnDbType[ColumnDbType.INTEGER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$xutils$db$sqlite$ColumnDbType[ColumnDbType.REAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$xutils$db$sqlite$ColumnDbType[ColumnDbType.TEXT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$xutils$db$sqlite$ColumnDbType[ColumnDbType.BLOB.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public Object[] getBindArgs() {
        List<KeyValue> list = this.bindArgs;
        if (list != null) {
            Object[] objArr = new Object[list.size()];
            for (int i = 0; i < this.bindArgs.size(); i++) {
                objArr[i] = ColumnUtils.convert2DbValueIfNeeded(this.bindArgs.get(i).value);
            }
            return objArr;
        }
        return null;
    }

    public String[] getBindArgsAsStrArray() {
        List<KeyValue> list = this.bindArgs;
        if (list != null) {
            String[] strArr = new String[list.size()];
            for (int i = 0; i < this.bindArgs.size(); i++) {
                Object convert2DbValueIfNeeded = ColumnUtils.convert2DbValueIfNeeded(this.bindArgs.get(i).value);
                strArr[i] = convert2DbValueIfNeeded == null ? null : convert2DbValueIfNeeded.toString();
            }
            return strArr;
        }
        return null;
    }
}
