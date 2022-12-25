package com.j256.ormlite.android;

import android.database.Cursor;
import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.p075db.DatabaseType;
import com.j256.ormlite.p075db.SqliteAndroidDatabaseType;
import com.j256.ormlite.support.DatabaseResults;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class AndroidDatabaseResults implements DatabaseResults {
    public static final int MIN_NUM_COLUMN_NAMES_MAP = 8;
    public static final DatabaseType databaseType = new SqliteAndroidDatabaseType();
    public final Map<String, Integer> columnNameMap;
    public final String[] columnNames;
    public final Cursor cursor;
    public final ObjectCache objectCache;

    public AndroidDatabaseResults(Cursor cursor, ObjectCache objectCache) {
        this.cursor = cursor;
        this.columnNames = cursor.getColumnNames();
        if (this.columnNames.length >= 8) {
            this.columnNameMap = new HashMap();
            int i = 0;
            while (true) {
                String[] strArr = this.columnNames;
                if (i >= strArr.length) {
                    break;
                }
                this.columnNameMap.put(strArr[i], Integer.valueOf(i));
                i++;
            }
        } else {
            this.columnNameMap = null;
        }
        this.objectCache = objectCache;
    }

    @Deprecated
    public AndroidDatabaseResults(Cursor cursor, boolean z, ObjectCache objectCache) {
        this(cursor, objectCache);
    }

    private int lookupColumn(String str) {
        Map<String, Integer> map = this.columnNameMap;
        if (map != null) {
            Integer num = map.get(str);
            if (num != null) {
                return num.intValue();
            }
            return -1;
        }
        int i = 0;
        while (true) {
            String[] strArr = this.columnNames;
            if (i >= strArr.length) {
                return -1;
            }
            if (strArr[i].equals(str)) {
                return i;
            }
            i++;
        }
    }

    @Override // com.j256.ormlite.support.DatabaseResults
    public void close() {
        this.cursor.close();
    }

    @Override // com.j256.ormlite.support.DatabaseResults
    public void closeQuietly() {
        close();
    }

    @Override // com.j256.ormlite.support.DatabaseResults
    public int findColumn(String str) {
        int lookupColumn = lookupColumn(str);
        if (lookupColumn >= 0) {
            return lookupColumn;
        }
        StringBuilder sb = new StringBuilder(str.length() + 4);
        databaseType.appendEscapedEntityName(sb, str);
        int lookupColumn2 = lookupColumn(sb.toString());
        if (lookupColumn2 >= 0) {
            return lookupColumn2;
        }
        String[] columnNames = this.cursor.getColumnNames();
        throw new SQLException("Unknown field '" + str + "' from the Android sqlite cursor, not in:" + Arrays.toString(columnNames));
    }

    @Override // com.j256.ormlite.support.DatabaseResults
    public boolean first() {
        return this.cursor.moveToFirst();
    }

    @Override // com.j256.ormlite.support.DatabaseResults
    public BigDecimal getBigDecimal(int i) {
        throw new SQLException("Android does not support BigDecimal type.  Use BIG_DECIMAL or BIG_DECIMAL_STRING types");
    }

    @Override // com.j256.ormlite.support.DatabaseResults
    public InputStream getBlobStream(int i) {
        return new ByteArrayInputStream(this.cursor.getBlob(i));
    }

    @Override // com.j256.ormlite.support.DatabaseResults
    public boolean getBoolean(int i) {
        return !this.cursor.isNull(i) && this.cursor.getShort(i) != 0;
    }

    @Override // com.j256.ormlite.support.DatabaseResults
    public byte getByte(int i) {
        return (byte) getShort(i);
    }

    @Override // com.j256.ormlite.support.DatabaseResults
    public byte[] getBytes(int i) {
        return this.cursor.getBlob(i);
    }

    @Override // com.j256.ormlite.support.DatabaseResults
    public char getChar(int i) {
        String string = this.cursor.getString(i);
        if (string == null || string.length() == 0) {
            return (char) 0;
        }
        if (string.length() == 1) {
            return string.charAt(0);
        }
        throw new SQLException("More than 1 character stored in database column: " + i);
    }

    @Override // com.j256.ormlite.support.DatabaseResults
    public int getColumnCount() {
        return this.cursor.getColumnCount();
    }

    @Override // com.j256.ormlite.support.DatabaseResults
    public String[] getColumnNames() {
        int columnCount = getColumnCount();
        String[] strArr = new String[columnCount];
        for (int i = 0; i < columnCount; i++) {
            strArr[i] = this.cursor.getColumnName(i);
        }
        return strArr;
    }

    public int getCount() {
        return this.cursor.getCount();
    }

    @Override // com.j256.ormlite.support.DatabaseResults
    public double getDouble(int i) {
        return this.cursor.getDouble(i);
    }

    @Override // com.j256.ormlite.support.DatabaseResults
    public float getFloat(int i) {
        return this.cursor.getFloat(i);
    }

    @Override // com.j256.ormlite.support.DatabaseResults
    public int getInt(int i) {
        return this.cursor.getInt(i);
    }

    @Override // com.j256.ormlite.support.DatabaseResults
    public long getLong(int i) {
        return this.cursor.getLong(i);
    }

    @Override // com.j256.ormlite.support.DatabaseResults
    public ObjectCache getObjectCache() {
        return this.objectCache;
    }

    public int getPosition() {
        return this.cursor.getPosition();
    }

    public Cursor getRawCursor() {
        return this.cursor;
    }

    @Override // com.j256.ormlite.support.DatabaseResults
    public short getShort(int i) {
        return this.cursor.getShort(i);
    }

    @Override // com.j256.ormlite.support.DatabaseResults
    public String getString(int i) {
        return this.cursor.getString(i);
    }

    @Override // com.j256.ormlite.support.DatabaseResults
    public Timestamp getTimestamp(int i) {
        throw new SQLException("Android does not support timestamp.  Use JAVA_DATE_LONG or JAVA_DATE_STRING types");
    }

    @Override // com.j256.ormlite.support.DatabaseResults
    public boolean last() {
        return this.cursor.moveToLast();
    }

    @Override // com.j256.ormlite.support.DatabaseResults
    public boolean moveAbsolute(int i) {
        return this.cursor.moveToPosition(i);
    }

    @Override // com.j256.ormlite.support.DatabaseResults
    public boolean moveRelative(int i) {
        return this.cursor.move(i);
    }

    @Override // com.j256.ormlite.support.DatabaseResults
    public boolean next() {
        return this.cursor.moveToNext();
    }

    @Override // com.j256.ormlite.support.DatabaseResults
    public boolean previous() {
        return this.cursor.moveToPrevious();
    }

    public String toString() {
        return AndroidDatabaseResults.class.getSimpleName() + "@" + Integer.toHexString(super.hashCode());
    }

    @Override // com.j256.ormlite.support.DatabaseResults
    public boolean wasNull(int i) {
        return this.cursor.isNull(i);
    }
}
