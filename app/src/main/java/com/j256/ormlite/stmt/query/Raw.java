package com.j256.ormlite.stmt.query;

import com.j256.ormlite.p075db.DatabaseType;
import com.j256.ormlite.stmt.ArgumentHolder;
import java.util.List;

/* loaded from: classes3.dex */
public class Raw implements Clause {
    public final ArgumentHolder[] args;
    public final String statement;

    public Raw(String str, ArgumentHolder[] argumentHolderArr) {
        this.statement = str;
        this.args = argumentHolderArr;
    }

    @Override // com.j256.ormlite.stmt.query.Clause
    public void appendSql(DatabaseType databaseType, String str, StringBuilder sb, List<ArgumentHolder> list) {
        sb.append(this.statement);
        sb.append(' ');
        for (ArgumentHolder argumentHolder : this.args) {
            list.add(argumentHolder);
        }
    }
}
