package com.gen.p059mh.webapps.database;

/* renamed from: com.gen.mh.webapps.database.TableVersion */
/* loaded from: classes2.dex */
public class TableVersion extends DBModule {
    @DatabaseProperty(index = true)
    private String tableName;
    @DatabaseProperty
    private String version;

    public String getTableName() {
        return this.tableName;
    }

    public String getVersion() {
        return this.version;
    }

    public void setTableName(String str) {
        this.tableName = str;
    }

    public void setVersion(String str) {
        this.version = str;
    }
}
