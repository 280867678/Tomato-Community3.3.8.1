package p007b.p008a.p009a.p011b;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.p055cz.db_ormlite.CollectionUtil;
import java.sql.SQLException;
import java.util.List;

/* renamed from: b.a.a.b.c */
/* loaded from: classes2.dex */
public class C0573c<T> {

    /* renamed from: a */
    public Class<T> f111a;

    /* renamed from: b */
    public final String f112b;

    public C0573c(Class<T> cls) {
        this.f111a = cls;
        this.f112b = C0575e.m5552a(cls);
    }

    /* renamed from: a */
    public String m5567a() {
        return this.f112b;
    }

    /* renamed from: a */
    public final String m5561a(List<String> list, List<String> list2) {
        StringBuilder sb = new StringBuilder("");
        if (list == null || list2 == null) {
            return sb.toString();
        }
        int i = 0;
        for (String str : list) {
            if (!CollectionUtil.existValue(str, list2)) {
                if (i != 0) {
                    sb.append(", ");
                }
                sb.append("`");
                sb.append(str);
                sb.append("`");
                i++;
            }
        }
        return sb.toString();
    }

    /* renamed from: a */
    public void m5566a(SQLiteDatabase sQLiteDatabase, ConnectionSource connectionSource) {
        List<C0572b> m5555a = C0575e.m5555a(sQLiteDatabase, this.f112b);
        List<C0572b> m5553a = C0575e.m5553a(connectionSource, this.f111a);
        if (m5555a.isEmpty() && m5553a.isEmpty()) {
            Log.d("DatabaseHandler", "数据表结构都为空！不是合法的数据库bean！！！");
        } else if (m5555a.isEmpty()) {
            Log.d("DatabaseHandler", "新增表");
            m5563a(connectionSource);
        } else if (!m5553a.isEmpty()) {
            m5564a(sQLiteDatabase, connectionSource, m5555a, m5553a);
        } else {
            Log.e("DatabaseHandler", "删除表");
            m5560b(connectionSource);
        }
    }

    /* renamed from: a */
    public final void m5565a(SQLiteDatabase sQLiteDatabase, ConnectionSource connectionSource, String str) {
        sQLiteDatabase.beginTransaction();
        String str2 = this.f112b + "_temp";
        try {
            try {
                sQLiteDatabase.execSQL("ALTER TABLE " + this.f112b + " RENAME TO " + str2);
                try {
                    sQLiteDatabase.execSQL(TableUtils.getCreateTableStatements(connectionSource, this.f111a).get(0));
                } catch (Exception e) {
                    e.printStackTrace();
                    TableUtils.createTable(connectionSource, this.f111a);
                }
                StringBuilder sb = new StringBuilder();
                sb.append("INSERT INTO ");
                sb.append(this.f112b);
                sb.append(" (");
                sb.append(str);
                sb.append(")  SELECT ");
                sb.append(str);
                sb.append(" FROM ");
                sb.append(str2);
                sQLiteDatabase.execSQL(sb.toString());
                StringBuilder sb2 = new StringBuilder();
                sb2.append("DROP TABLE IF EXISTS ");
                sb2.append(str2);
                sQLiteDatabase.execSQL(sb2.toString());
                sQLiteDatabase.setTransactionSuccessful();
            } catch (Exception e2) {
                e2.printStackTrace();
                throw new SQLException("upgrade database table struct fail");
            }
        } finally {
            sQLiteDatabase.endTransaction();
        }
    }

    /* renamed from: a */
    public final void m5564a(SQLiteDatabase sQLiteDatabase, ConnectionSource connectionSource, List<C0572b> list, List<C0572b> list2) {
        if (C0575e.m5545c(list, list2)) {
            Log.d("DatabaseHandler", "数据表已有字段的描述改变");
            m5559c(connectionSource);
            return;
        }
        List<String> m5550a = C0575e.m5550a(list);
        List<String> m5550a2 = C0575e.m5550a(list2);
        if (m5550a.equals(m5550a2)) {
            Log.i("DatabaseHandler", "表没有发生变化,不需要更新数据表");
            return;
        }
        Log.d("DatabaseHandler", "表发生了变化");
        m5565a(sQLiteDatabase, connectionSource, m5561a(m5550a, C0575e.m5546b(m5550a, m5550a2)));
    }

    /* renamed from: a */
    public void m5563a(ConnectionSource connectionSource) {
        TableUtils.createTable(connectionSource, this.f111a);
    }

    /* renamed from: a */
    public void m5562a(ConnectionSource connectionSource, int i, int i2) {
        m5559c(connectionSource);
    }

    /* renamed from: b */
    public void m5560b(ConnectionSource connectionSource) {
        TableUtils.dropTable(connectionSource, (Class) this.f111a, true);
    }

    /* renamed from: c */
    public final void m5559c(ConnectionSource connectionSource) {
        m5560b(connectionSource);
        m5563a(connectionSource);
    }
}
