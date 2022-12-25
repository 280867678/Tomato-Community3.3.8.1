package com.j256.ormlite.stmt;

import com.j256.ormlite.dao.AbstractC2183Dao;
import com.j256.ormlite.p075db.DatabaseType;
import com.j256.ormlite.stmt.StatementBuilder;
import com.j256.ormlite.table.TableInfo;
import java.util.List;

/* loaded from: classes3.dex */
public class DeleteBuilder<T, ID> extends StatementBuilder<T, ID> {
    public DeleteBuilder(DatabaseType databaseType, TableInfo<T, ID> tableInfo, AbstractC2183Dao<T, ID> abstractC2183Dao) {
        super(databaseType, tableInfo, abstractC2183Dao, StatementBuilder.StatementType.DELETE);
    }

    @Override // com.j256.ormlite.stmt.StatementBuilder
    public void appendStatementEnd(StringBuilder sb, List<ArgumentHolder> list) {
    }

    @Override // com.j256.ormlite.stmt.StatementBuilder
    public void appendStatementStart(StringBuilder sb, List<ArgumentHolder> list) {
        sb.append("DELETE FROM ");
        this.databaseType.appendEscapedEntityName(sb, this.tableInfo.getTableName());
        sb.append(' ');
    }

    @Override // com.j256.ormlite.stmt.StatementBuilder
    @Deprecated
    public void clear() {
        reset();
    }

    public int delete() {
        return this.dao.delete((PreparedDelete) prepare());
    }

    public PreparedDelete<T> prepare() {
        return super.prepareStatement(null);
    }

    @Override // com.j256.ormlite.stmt.StatementBuilder
    public void reset() {
        super.reset();
    }
}
