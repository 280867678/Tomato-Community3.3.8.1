package p007b.p008a.p009a.p011b;

import android.content.Context;
import android.util.Log;
import com.j256.ormlite.dao.AbstractC2183Dao;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.Where;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/* renamed from: b.a.a.b.h */
/* loaded from: classes2.dex */
public class C0578h<T> {

    /* renamed from: a */
    public final AbstractC2183Dao<T, Integer> f119a;

    /* renamed from: b */
    public final C0574d f120b;

    public C0578h(Context context, Class<T> cls) {
        this.f120b = C0574d.m5557a(context.getApplicationContext());
        this.f119a = this.f120b.getDao(cls);
    }

    /* renamed from: a */
    public List<T> m5538a() {
        try {
            return this.f119a.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* renamed from: a */
    public boolean m5537a(T t) {
        int i;
        try {
            i = this.f119a.create(t);
        } catch (SQLException e) {
            e.printStackTrace();
            i = 0;
        }
        return i > 0;
    }

    /* renamed from: a */
    public boolean m5536a(Map<String, Object> map) {
        DeleteBuilder<T, Integer> deleteBuilder = this.f119a.deleteBuilder();
        Where<T, Integer> where = deleteBuilder.where();
        try {
            where.isNotNull(DatabaseFieldConfigLoader.FIELD_NAME_ID);
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                where.and().m3918eq(entry.getKey(), entry.getValue());
            }
            return deleteBuilder.delete() > 0;
        } catch (SQLException e) {
            Log.e("OrmLiteDao", "delete error,delete line:0", e);
            return false;
        }
    }
}
