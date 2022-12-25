package p007b.p008a.p009a.p011b;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;
import com.j256.ormlite.misc.JavaxPersistence;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.TableUtils;
import com.tomatolive.library.utils.ConstantUtils;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* renamed from: b.a.a.b.e */
/* loaded from: classes2.dex */
public class C0575e {
    /* renamed from: a */
    public static <T> String m5552a(Class<T> cls) {
        DatabaseTable databaseTable = (DatabaseTable) cls.getAnnotation(DatabaseTable.class);
        if (databaseTable == null || databaseTable.tableName() == null || databaseTable.tableName().length() <= 0) {
            String entityName = JavaxPersistence.getEntityName(cls);
            return entityName == null ? cls.getSimpleName().toLowerCase() : entityName;
        }
        return databaseTable.tableName();
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0058, code lost:
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0055, code lost:
        if (0 == 0) goto L18;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static List<C0572b> m5555a(SQLiteDatabase sQLiteDatabase, String str) {
        String str2;
        List arrayList = new ArrayList();
        Cursor cursor = null;
        try {
            try {
                String[] strArr = new String[2];
                strArr[0] = "table";
                strArr[1] = str;
                cursor = sQLiteDatabase.rawQuery("select * from sqlite_master where type = ? AND name = ?", strArr);
                if (cursor != null) {
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex("sql");
                    if (-1 == columnIndex || cursor.getCount() <= 0) {
                        str2 = "不存在旧表";
                    } else {
                        arrayList = m5551a(cursor.getString(columnIndex));
                    }
                } else {
                    str2 = "数据库操作失败";
                }
                Log.i("DatabaseUtil", str2);
            } catch (Exception e) {
                Log.e("DatabaseUtil", "create old table statements fail", e);
            }
        } finally {
            if (0 != 0) {
                cursor.close();
            }
        }
    }

    /* renamed from: a */
    public static <T> List<C0572b> m5553a(ConnectionSource connectionSource, Class<T> cls) {
        ArrayList arrayList = new ArrayList();
        try {
            return m5551a(TableUtils.getCreateTableStatements(connectionSource, cls).get(0));
        } catch (SQLException e) {
            Log.e("DatabaseUtil", "create new table statements fail", e);
            return arrayList;
        }
    }

    /* renamed from: a */
    public static List<C0572b> m5551a(String str) {
        String[] split;
        ArrayList arrayList = new ArrayList();
        for (String str2 : str.substring(str.indexOf("(") + 1, str.length() - 1).split(", ")) {
            if (str2.contains("(") || str2.contains(")")) {
                str2 = str2.replace("(", "").replace(")", "");
            }
            String trim = str2.trim();
            if (trim.startsWith("`")) {
                String[] split2 = trim.split("` ");
                arrayList.add(new C0572b(split2[0].replace("`", ""), split2[1]));
            } else {
                boolean contains = trim.contains(",");
                String[] split3 = trim.split(" `");
                if (contains) {
                    for (String str3 : split3[1].replace("`", "").split(",")) {
                        m5548a(arrayList, str3, "UniqueCombo");
                    }
                } else {
                    m5548a(arrayList, split3[1].replace("`", ""), split3[0]);
                }
            }
        }
        return arrayList;
    }

    /* renamed from: a */
    public static List<String> m5550a(List<C0572b> list) {
        ArrayList arrayList = new ArrayList();
        if (list == null) {
            return arrayList;
        }
        for (C0572b c0572b : list) {
            arrayList.add(c0572b.m5568b());
        }
        return arrayList;
    }

    /* renamed from: a */
    public static List<String> m5547a(List<String> list, List<String> list2) {
        ArrayList arrayList = new ArrayList();
        if (list != null && list2 != null) {
            for (String str : list) {
                boolean z = false;
                Iterator<String> it2 = list2.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    String next = it2.next();
                    if (next != null && next.equals(str)) {
                        z = true;
                        break;
                    }
                }
                if (!z) {
                    arrayList.add(str);
                }
            }
        }
        return arrayList;
    }

    /* renamed from: a */
    public static void m5548a(List<C0572b> list, String str, String str2) {
        if (list == null || list.isEmpty()) {
            Log.e("DatabaseUtil", "list is null.");
        } else if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            Log.e("DatabaseUtil", "columnName is null or limit is null.");
        } else {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                C0572b c0572b = list.get(i);
                if (str.equals(c0572b.m5568b())) {
                    c0572b.m5569a(c0572b.m5570a() + ConstantUtils.PLACEHOLDER_STR_ONE + str2);
                    return;
                }
            }
            list.add(new C0572b(str, str2));
        }
    }

    /* renamed from: a */
    public static boolean m5554a(C0572b c0572b, C0572b c0572b2) {
        if (c0572b == null || c0572b2 == null) {
            return false;
        }
        String m5568b = c0572b.m5568b();
        String m5570a = c0572b.m5570a();
        if (m5568b == null || !m5568b.equals(c0572b2.m5568b())) {
            return false;
        }
        String m5570a2 = c0572b2.m5570a();
        if (m5570a == null && m5570a2 == null) {
            return false;
        }
        return m5570a == null || m5570a2 == null || !m5570a.equals(m5570a2);
    }

    /* renamed from: a */
    public static boolean m5549a(List<C0572b> list, C0572b c0572b) {
        if (c0572b != null && !TextUtils.isEmpty(c0572b.m5568b())) {
            for (C0572b c0572b2 : list) {
                if (m5554a(c0572b, c0572b2)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    /* renamed from: b */
    public static List<String> m5546b(List<String> list, List<String> list2) {
        return m5547a(list, list2);
    }

    /* renamed from: c */
    public static boolean m5545c(List<C0572b> list, List<C0572b> list2) {
        if (list != null && list2 != null) {
            for (C0572b c0572b : list) {
                if (m5549a(list2, c0572b)) {
                    return true;
                }
            }
        }
        return false;
    }
}
