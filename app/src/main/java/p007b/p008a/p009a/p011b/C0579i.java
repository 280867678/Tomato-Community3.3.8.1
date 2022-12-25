package p007b.p008a.p009a.p011b;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.j256.ormlite.android.AndroidDatabaseConnection;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.AbstractC2183Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* renamed from: b.a.a.b.i */
/* loaded from: classes2.dex */
public class C0579i extends OrmLiteSqliteOpenHelper {

    /* renamed from: a */
    public List<C0573c> f121a;

    /* renamed from: b */
    public Map<String, AbstractC2183Dao> f122b = new HashMap();

    public C0579i(Context context, String str, SQLiteDatabase.CursorFactory cursorFactory, int i) {
        super(context, str, cursorFactory, i);
    }

    /* renamed from: a */
    public void m5534a(ConnectionSource connectionSource, int i, int i2) {
        Log.i("OrmLiteDatabaseHelper", "数据库降级了 oldVersion = " + i + " newVersion = " + i2);
        try {
            for (C0573c c0573c : this.f121a) {
                c0573c.m5562a(connectionSource, i, i2);
            }
        } catch (SQLException e) {
            Log.e("OrmLiteDatabaseHelper", "database downgrade fail", e);
        }
    }

    /* renamed from: a */
    public <T> void m5533a(Class<T> cls) {
        if (this.f121a == null) {
            this.f121a = new ArrayList();
        }
        C0573c c0573c = new C0573c(cls);
        if (m5535a(c0573c)) {
            this.f121a.add(c0573c);
        }
    }

    /* renamed from: a */
    public boolean m5535a(C0573c c0573c) {
        if (this.f121a == null || c0573c == null) {
            return false;
        }
        String m5567a = c0573c.m5567a();
        for (C0573c c0573c2 : this.f121a) {
            if (m5567a.equals(c0573c2.m5567a())) {
                return false;
            }
        }
        return true;
    }

    @Override // com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper, android.database.sqlite.SQLiteOpenHelper, java.lang.AutoCloseable
    public void close() {
        super.close();
        synchronized (this) {
            Iterator<Map.Entry<String, AbstractC2183Dao>> it2 = this.f122b.entrySet().iterator();
            while (it2.hasNext()) {
                it2.next().getValue();
                it2.remove();
            }
        }
    }

    @Override // com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
    public synchronized AbstractC2183Dao getDao(Class cls) {
        AbstractC2183Dao dao;
        String simpleName = cls.getSimpleName();
        if (this.f122b.containsKey(simpleName)) {
            dao = this.f122b.get(simpleName);
        } else {
            try {
                dao = super.getDao(cls);
                this.f122b.put(simpleName, dao);
            } catch (SQLException e) {
                Log.e("OrmLiteDatabaseHelper", "database operate fail", e);
                return null;
            }
        }
        return dao;
    }

    @Override // com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase, ConnectionSource connectionSource) {
        try {
            for (C0573c c0573c : this.f121a) {
                c0573c.m5563a(connectionSource);
            }
        } catch (SQLException e) {
            Log.e("OrmLiteDatabaseHelper", "database create fail", e);
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        ConnectionSource connectionSource = getConnectionSource();
        DatabaseConnection specialConnection = connectionSource.getSpecialConnection();
        boolean z = true;
        if (specialConnection == null) {
            specialConnection = new AndroidDatabaseConnection(sQLiteDatabase, true, this.cancelQueriesEnabled);
            try {
                connectionSource.saveSpecialConnection(specialConnection);
            } catch (SQLException e) {
                throw new IllegalStateException("Could not save special connection", e);
            }
        } else {
            z = false;
        }
        try {
            m5534a(connectionSource, i, i2);
        } finally {
            if (z) {
                connectionSource.clearSpecialConnection(specialConnection);
            }
        }
    }

    @Override // com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, ConnectionSource connectionSource, int i, int i2) {
        Log.i("OrmLiteDatabaseHelper", "数据库升级了 oldVersion = " + i + " newVersion = " + i2);
        try {
            for (C0573c c0573c : this.f121a) {
                c0573c.m5566a(sQLiteDatabase, connectionSource);
            }
        } catch (SQLException e) {
            Log.e("OrmLiteDatabaseHelper", "database upgrade fail", e);
        }
    }
}
