package com.gen.p059mh.webapps.database;

import com.gen.p059mh.webapps.database.Table;

/* renamed from: com.gen.mh.webapps.database.DBModule */
/* loaded from: classes2.dex */
public class DBModule {
    @DatabaseProperty(primary = true)

    /* renamed from: ID */
    int f1301ID = -1;

    public int getID() {
        return this.f1301ID;
    }

    public <T extends DBModule> Table<T> table() throws Table.SQLTaleNoSetupException {
        return Table.from(getClass());
    }

    public void save() {
        try {
            table().save(this);
        } catch (Table.SQLTaleNoSetupException e) {
            e.printStackTrace();
        }
    }

    public void remove() {
        try {
            table().remove(this);
        } catch (Table.SQLTaleNoSetupException e) {
            e.printStackTrace();
        }
    }

    protected void finalize() throws Throwable {
        table().removeCache(this.f1301ID);
        super.finalize();
    }
}
