package com.j256.ormlite.stmt.query;

/* loaded from: classes3.dex */
public class OrderBy {
    public final boolean ascending;
    public final String columnName;

    public OrderBy(String str, boolean z) {
        this.columnName = str;
        this.ascending = z;
    }

    public String getColumnName() {
        return this.columnName;
    }

    public boolean isAscending() {
        return this.ascending;
    }
}
