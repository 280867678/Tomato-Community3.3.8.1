package com.j256.ormlite.stmt.query;

import com.j256.ormlite.p075db.DatabaseType;
import com.j256.ormlite.stmt.ArgumentHolder;
import com.j256.ormlite.stmt.QueryBuilder;
import java.util.List;

/* loaded from: classes3.dex */
public class Exists implements Clause {
    public final QueryBuilder.InternalQueryBuilderWrapper subQueryBuilder;

    public Exists(QueryBuilder.InternalQueryBuilderWrapper internalQueryBuilderWrapper) {
        this.subQueryBuilder = internalQueryBuilderWrapper;
    }

    @Override // com.j256.ormlite.stmt.query.Clause
    public void appendSql(DatabaseType databaseType, String str, StringBuilder sb, List<ArgumentHolder> list) {
        sb.append("EXISTS (");
        this.subQueryBuilder.appendStatementString(sb, list);
        sb.append(") ");
    }
}
