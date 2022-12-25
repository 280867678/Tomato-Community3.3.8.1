package com.gen.p059mh.webapps.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.gen.p059mh.webapps.database.DBModule;
import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* renamed from: com.gen.mh.webapps.database.Table */
/* loaded from: classes2.dex */
public class Table<T extends DBModule> {
    private static File dbFile;
    private static Map<String, Table> tableMap = new HashMap();
    private Map<Integer, WeakReference<T>> caches = new HashMap();
    private ArrayList<Table<T>.Property> properties;
    private HashMap<String, Table<T>.Property> propertiesIndex;
    private String tableName;
    private Class<T> targetClass;
    private String version;

    /* renamed from: com.gen.mh.webapps.database.Table$SQLTaleNoSetupException */
    /* loaded from: classes2.dex */
    public static class SQLTaleNoSetupException extends Exception {
    }

    /* renamed from: com.gen.mh.webapps.database.Table$SqlActionHandler */
    /* loaded from: classes2.dex */
    public interface SqlActionHandler {
        void process(SQLiteDatabase sQLiteDatabase);
    }

    public static void setup(Context context) {
        File file = new File(context.getFilesDir().getAbsolutePath() + "/datas");
        if (!file.exists()) {
            file.mkdirs();
        }
        dbFile = new File(file.getAbsolutePath() + "/table.db");
    }

    public static <T extends DBModule> Table<T> from(Class<T> cls) throws SQLTaleNoSetupException {
        String simpleName;
        if (dbFile == null) {
            throw new SQLTaleNoSetupException();
        }
        DatabaseTable databaseTable = (DatabaseTable) cls.getAnnotation(DatabaseTable.class);
        if (databaseTable != null) {
            simpleName = databaseTable.value();
            if (simpleName.length() == 0) {
                simpleName = cls.getSimpleName();
            }
        } else {
            simpleName = cls.getSimpleName();
        }
        if (tableMap.containsKey(simpleName)) {
            return tableMap.get(simpleName);
        }
        Table<T> table = new Table<>();
        ((Table) table).targetClass = cls;
        table.process();
        tableMap.put(simpleName, table);
        return table;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.gen.mh.webapps.database.Table$Property */
    /* loaded from: classes2.dex */
    public class Property {
        Field field;
        DatabaseProperty property;

        public Property(Field field, DatabaseProperty databaseProperty) {
            this.field = field;
            field.setAccessible(true);
            this.property = databaseProperty;
        }

        public String getName() {
            if (this.property.value() == "") {
                return this.field.getName();
            }
            return this.property.value();
        }

        public void set(Object obj, Object obj2) {
            try {
                this.field.set(obj, obj2);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        public Object get(Object obj) {
            try {
                return this.field.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        }

        public int getType() {
            if (this.field.getType() == Integer.class) {
                return 1;
            }
            if (this.field.getType() == Float.class) {
                return 2;
            }
            if (this.field.getType() == String.class) {
                return 3;
            }
            return this.field.getType() == Blob.class ? 4 : 0;
        }

        public boolean primary() {
            return this.property.primary();
        }

        public boolean index() {
            return this.property.index();
        }

        public boolean nullable() {
            return this.property.nullable();
        }
    }

    public void drop() {
        action(new SqlActionHandler() { // from class: com.gen.mh.webapps.database.Table.1
            @Override // com.gen.p059mh.webapps.database.Table.SqlActionHandler
            public void process(SQLiteDatabase sQLiteDatabase) {
                sQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Table.this.tableName);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createTable(SQLiteDatabase sQLiteDatabase) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS ");
        sb.append(this.tableName);
        sb.append(" (");
        ArrayList arrayList = new ArrayList();
        int size = this.properties.size();
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                sb.append(',');
            }
            Table<T>.Property property = this.properties.get(i);
            sb.append(property.getName());
            if (property.primary()) {
                sb.append(" INTEGER PRIMARY KEY AUTOINCREMENT");
            } else {
                int type = property.getType();
                if (type == 1) {
                    sb.append(" INT");
                } else if (type == 2) {
                    sb.append(" REAL");
                } else if (type == 3) {
                    sb.append(" TEXT");
                } else if (type == 4) {
                    sb.append(" BLOB");
                }
            }
            if (!property.nullable()) {
                sb.append(" NOT NULL");
            }
            if (property.index()) {
                arrayList.add(property.getName());
            }
        }
        sb.append(")");
        sQLiteDatabase.execSQL(sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append("CREATE INDEX IF NOT EXISTS ");
        sb2.append(this.tableName);
        sb2.append("_INDEX ON ");
        sb2.append(this.tableName);
        sb2.append(" (");
        int size2 = arrayList.size();
        for (int i2 = 0; i2 < size2; i2++) {
            if (i2 != 0) {
                sb2.append(",");
            }
            sb2.append((String) arrayList.get(i2));
        }
        sb2.append(")");
        sQLiteDatabase.execSQL(sb2.toString());
    }

    private void refresh() {
        action(new SqlActionHandler() { // from class: com.gen.mh.webapps.database.Table.2
            @Override // com.gen.p059mh.webapps.database.Table.SqlActionHandler
            public void process(SQLiteDatabase sQLiteDatabase) {
                Cursor query = sQLiteDatabase.query(Table.this.tableName, null, null, null, null, null, null);
                ArrayList arrayList = new ArrayList();
                while (query.moveToNext()) {
                    try {
                        DBModule dBModule = (DBModule) Table.this.targetClass.newInstance();
                        int columnCount = query.getColumnCount();
                        for (int i = 0; i < columnCount; i++) {
                            String columnName = query.getColumnName(i);
                            if (columnName != "ID" && Table.this.propertiesIndex.containsKey(columnName)) {
                                Property property = (Property) Table.this.propertiesIndex.get(columnName);
                                int type = query.getType(i);
                                if (type == property.getType()) {
                                    if (type == 1) {
                                        property.set(dBModule, Integer.valueOf(query.getInt(i)));
                                    } else if (type == 2) {
                                        property.set(dBModule, Float.valueOf(query.getFloat(i)));
                                    } else if (type == 3) {
                                        property.set(dBModule, query.getString(i));
                                    } else if (type == 4) {
                                        property.set(dBModule, new Blob(query.getBlob(i)));
                                    }
                                }
                            }
                        }
                        arrayList.add(dBModule);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e2) {
                        e2.printStackTrace();
                    }
                }
                query.close();
                sQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Table.this.tableName);
                Table.this.createTable(sQLiteDatabase);
                Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    DBModule dBModule2 = (DBModule) it2.next();
                    ContentValues contentValues = new ContentValues();
                    Iterator it3 = Table.this.properties.iterator();
                    while (it3.hasNext()) {
                        Property property2 = (Property) it3.next();
                        Object obj = property2.get(dBModule2);
                        if (obj == null) {
                            contentValues.putNull(property2.getName());
                        } else if (Integer.class.isInstance(obj)) {
                            contentValues.put(property2.getName(), Integer.valueOf(((Integer) obj).intValue()));
                        } else if (Float.class.isInstance(obj)) {
                            contentValues.put(property2.getName(), Float.valueOf(((Float) obj).floatValue()));
                        } else if (String.class.isInstance(obj)) {
                            contentValues.put(property2.getName(), (String) obj);
                        } else if (Blob.class.isInstance(obj)) {
                            contentValues.put(property2.getName(), ((Blob) obj).buffer);
                        }
                    }
                    sQLiteDatabase.insert(Table.this.tableName, null, contentValues);
                }
            }
        });
    }

    private void process() {
        Field[] declaredFields;
        Class<T> cls = this.targetClass;
        if (cls == null) {
            Log.e("Table", "No target class found!");
            return;
        }
        this.properties = new ArrayList<>();
        this.propertiesIndex = new HashMap<>();
        while (cls != null) {
            for (Field field : cls.getDeclaredFields()) {
                DatabaseProperty databaseProperty = (DatabaseProperty) field.getAnnotation(DatabaseProperty.class);
                if (databaseProperty != null) {
                    Table<T>.Property property = new Property(field, databaseProperty);
                    this.properties.add(property);
                    this.propertiesIndex.put(property.getName(), property);
                }
            }
            if (cls == DBModule.class) {
                break;
            }
            cls = cls.getSuperclass();
        }
        DatabaseTable databaseTable = (DatabaseTable) this.targetClass.getAnnotation(DatabaseTable.class);
        if (databaseTable != null) {
            this.tableName = databaseTable.value();
            if (this.tableName.length() == 0) {
                this.tableName = this.targetClass.getSimpleName();
            }
            this.version = databaseTable.version();
        } else {
            this.tableName = this.targetClass.getSimpleName();
            this.version = "1.0";
        }
        if (!this.tableName.equals("TableVersion")) {
            try {
                TableVersion tableVersion = (TableVersion) from(TableVersion.class).findOne("tableName=\"" + this.tableName + "\"");
                if (tableVersion == null) {
                    TableVersion tableVersion2 = new TableVersion();
                    tableVersion2.setTableName(this.tableName);
                    tableVersion2.setVersion(this.version);
                    tableVersion2.save();
                } else if (!tableVersion.getVersion().equals(this.version)) {
                    refresh();
                    tableVersion.setVersion(this.version);
                    tableVersion.save();
                    return;
                }
            } catch (SQLTaleNoSetupException e) {
                e.printStackTrace();
            }
        }
        action(new SqlActionHandler() { // from class: com.gen.mh.webapps.database.Table.3
            @Override // com.gen.p059mh.webapps.database.Table.SqlActionHandler
            public void process(SQLiteDatabase sQLiteDatabase) {
                Table.this.createTable(sQLiteDatabase);
            }
        });
    }

    public static void action(SqlActionHandler sqlActionHandler) {
        SQLiteDatabase openOrCreateDatabase = SQLiteDatabase.openOrCreateDatabase(dbFile, (SQLiteDatabase.CursorFactory) null);
        openOrCreateDatabase.enableWriteAheadLogging();
        openOrCreateDatabase.setVersion(10);
        sqlActionHandler.process(openOrCreateDatabase);
        openOrCreateDatabase.close();
    }

    /* renamed from: com.gen.mh.webapps.database.Table$Blob */
    /* loaded from: classes2.dex */
    public static class Blob {
        byte[] buffer;

        public Blob(byte[] bArr) {
            this.buffer = bArr;
        }

        public byte[] getBuffer() {
            return this.buffer;
        }
    }

    public T[] find(final String str, final String[] strArr, final String str2, final String str3) {
        final ArrayList arrayList = new ArrayList();
        action(new SqlActionHandler() { // from class: com.gen.mh.webapps.database.Table.4
            @Override // com.gen.p059mh.webapps.database.Table.SqlActionHandler
            public void process(SQLiteDatabase sQLiteDatabase) {
                Cursor query = sQLiteDatabase.query(Table.this.tableName, null, str, strArr, null, null, str2, str3);
                if (query.moveToFirst()) {
                    do {
                        DBModule newOrFindCache = Table.this.newOrFindCache(query.getInt(query.getColumnIndex("ID")));
                        int columnCount = query.getColumnCount();
                        for (int i = 0; i < columnCount; i++) {
                            String columnName = query.getColumnName(i);
                            int type = query.getType(i);
                            if (Table.this.propertiesIndex.containsKey(columnName)) {
                                Property property = (Property) Table.this.propertiesIndex.get(columnName);
                                if (!property.primary() && property != null) {
                                    if (type == 0) {
                                        property.set(newOrFindCache, null);
                                    } else if (type == 1) {
                                        property.set(newOrFindCache, Integer.valueOf(query.getInt(i)));
                                    } else if (type == 2) {
                                        property.set(newOrFindCache, Float.valueOf(query.getFloat(i)));
                                    } else if (type == 3) {
                                        property.set(newOrFindCache, query.getString(i));
                                    } else if (type == 4) {
                                        property.set(newOrFindCache, new Blob(query.getBlob(i)));
                                    }
                                }
                            }
                        }
                        arrayList.add(newOrFindCache);
                    } while (query.moveToNext());
                    query.close();
                }
                query.close();
            }
        });
        T[] tArr = (T[]) ((DBModule[]) Array.newInstance((Class<?>) this.targetClass, arrayList.size()));
        arrayList.toArray(tArr);
        return tArr;
    }

    public T findOne(String str, String[] strArr) {
        T[] find = find(str, strArr, null, "1");
        if (find.length > 0) {
            return find[0];
        }
        return null;
    }

    public T[] findAll() {
        return find(null, null, null, null);
    }

    public T findOne(String str) {
        return findOne(str, null);
    }

    public void save(final T t) {
        action(new SqlActionHandler() { // from class: com.gen.mh.webapps.database.Table.5
            @Override // com.gen.p059mh.webapps.database.Table.SqlActionHandler
            public void process(SQLiteDatabase sQLiteDatabase) {
                if (t.getID() == -1) {
                    ContentValues contentValues = new ContentValues();
                    Iterator it2 = Table.this.properties.iterator();
                    while (it2.hasNext()) {
                        Property property = (Property) it2.next();
                        if (!property.primary()) {
                            Object obj = property.get(t);
                            if (obj == null) {
                                contentValues.putNull(property.getName());
                            } else if (Integer.class.isInstance(obj)) {
                                contentValues.put(property.getName(), Integer.valueOf(((Integer) obj).intValue()));
                            } else if (Float.class.isInstance(obj)) {
                                contentValues.put(property.getName(), Float.valueOf(((Float) obj).floatValue()));
                            } else if (String.class.isInstance(obj)) {
                                contentValues.put(property.getName(), (String) obj);
                            } else if (Blob.class.isInstance(obj)) {
                                contentValues.put(property.getName(), ((Blob) obj).buffer);
                            }
                        }
                    }
                    sQLiteDatabase.insert(Table.this.tableName, null, contentValues);
                    Cursor query = sQLiteDatabase.query(Table.this.tableName, new String[]{"ID"}, null, null, null, null, " ID DESC", "1");
                    if (query.moveToFirst()) {
                        t.f1301ID = query.getInt(0);
                        Table.this.cache(t);
                    }
                    query.close();
                    return;
                }
                ContentValues contentValues2 = new ContentValues();
                Iterator it3 = Table.this.properties.iterator();
                while (it3.hasNext()) {
                    Property property2 = (Property) it3.next();
                    if (!property2.primary()) {
                        Object obj2 = property2.get(t);
                        if (obj2 == null) {
                            contentValues2.putNull(property2.getName());
                        } else if (Integer.class.isInstance(obj2)) {
                            contentValues2.put(property2.getName(), Integer.valueOf(((Integer) obj2).intValue()));
                        } else if (Float.class.isInstance(obj2)) {
                            contentValues2.put(property2.getName(), Float.valueOf(((Float) obj2).floatValue()));
                        } else if (String.class.isInstance(obj2)) {
                            contentValues2.put(property2.getName(), (String) obj2);
                        } else if (Blob.class.isInstance(obj2)) {
                            contentValues2.put(property2.getName(), ((Blob) obj2).buffer);
                        }
                    }
                }
                String str = Table.this.tableName;
                sQLiteDatabase.update(str, contentValues2, "ID=" + t.getID(), null);
            }
        });
    }

    public int remove(final T t) {
        final int[] iArr = {0};
        action(new SqlActionHandler() { // from class: com.gen.mh.webapps.database.Table.6
            @Override // com.gen.p059mh.webapps.database.Table.SqlActionHandler
            public void process(SQLiteDatabase sQLiteDatabase) {
                if (t.getID() != -1) {
                    new ContentValues();
                    int[] iArr2 = iArr;
                    String str = Table.this.tableName;
                    iArr2[0] = sQLiteDatabase.delete(str, "ID=" + t.getID(), null);
                    t.f1301ID = -1;
                }
            }
        });
        return iArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public T newOrFindCache(int i) {
        T t;
        if (!this.caches.containsKey(Integer.valueOf(i)) || (t = this.caches.get(Integer.valueOf(i)).get()) == null) {
            try {
                T newInstance = this.targetClass.newInstance();
                newInstance.f1301ID = i;
                cache(newInstance);
                return newInstance;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            } catch (InstantiationException e2) {
                e2.printStackTrace();
                return null;
            }
        }
        return t;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cache(T t) {
        if (t.getID() >= 0) {
            this.caches.put(Integer.valueOf(t.getID()), new WeakReference<>(t));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void removeCache(int i) {
        if (i < 0 || !this.caches.containsKey(Integer.valueOf(i))) {
            return;
        }
        this.caches.remove(Integer.valueOf(i));
    }
}
