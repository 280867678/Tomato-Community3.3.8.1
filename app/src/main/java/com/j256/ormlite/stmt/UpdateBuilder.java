package com.j256.ormlite.stmt;

import com.j256.ormlite.dao.AbstractC2183Dao;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.p075db.DatabaseType;
import com.j256.ormlite.stmt.StatementBuilder;
import com.j256.ormlite.stmt.query.Clause;
import com.j256.ormlite.stmt.query.SetExpression;
import com.j256.ormlite.stmt.query.SetValue;
import com.j256.ormlite.table.TableInfo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class UpdateBuilder<T, ID> extends StatementBuilder<T, ID> {
    public List<Clause> updateClauseList = null;

    public UpdateBuilder(DatabaseType databaseType, TableInfo<T, ID> tableInfo, AbstractC2183Dao<T, ID> abstractC2183Dao) {
        super(databaseType, tableInfo, abstractC2183Dao, StatementBuilder.StatementType.UPDATE);
    }

    private void addUpdateColumnToList(String str, Clause clause) {
        if (this.updateClauseList == null) {
            this.updateClauseList = new ArrayList();
        }
        this.updateClauseList.add(clause);
    }

    @Override // com.j256.ormlite.stmt.StatementBuilder
    public void appendStatementEnd(StringBuilder sb, List<ArgumentHolder> list) {
    }

    @Override // com.j256.ormlite.stmt.StatementBuilder
    public void appendStatementStart(StringBuilder sb, List<ArgumentHolder> list) {
        List<Clause> list2 = this.updateClauseList;
        if (list2 == null || list2.isEmpty()) {
            throw new IllegalArgumentException("UPDATE statements must have at least one SET column");
        }
        sb.append("UPDATE ");
        this.databaseType.appendEscapedEntityName(sb, this.tableInfo.getTableName());
        sb.append(" SET ");
        boolean z = true;
        for (Clause clause : this.updateClauseList) {
            if (z) {
                z = false;
            } else {
                sb.append(',');
            }
            clause.appendSql(this.databaseType, null, sb, list);
        }
    }

    @Override // com.j256.ormlite.stmt.StatementBuilder
    @Deprecated
    public void clear() {
        reset();
    }

    public String escapeColumnName(String str) {
        StringBuilder sb = new StringBuilder(str.length() + 4);
        this.databaseType.appendEscapedEntityName(sb, str);
        return sb.toString();
    }

    public void escapeColumnName(StringBuilder sb, String str) {
        this.databaseType.appendEscapedEntityName(sb, str);
    }

    public String escapeValue(String str) {
        StringBuilder sb = new StringBuilder(str.length() + 4);
        this.databaseType.appendEscapedWord(sb, str);
        return sb.toString();
    }

    public void escapeValue(StringBuilder sb, String str) {
        this.databaseType.appendEscapedWord(sb, str);
    }

    public PreparedUpdate<T> prepare() {
        return super.prepareStatement(null);
    }

    @Override // com.j256.ormlite.stmt.StatementBuilder
    public void reset() {
        super.reset();
        this.updateClauseList = null;
    }

    public int update() {
        return this.dao.update((PreparedUpdate) prepare());
    }

    public StatementBuilder<T, ID> updateColumnExpression(String str, String str2) {
        FieldType verifyColumnName = verifyColumnName(str);
        if (!verifyColumnName.isForeignCollection()) {
            addUpdateColumnToList(str, new SetExpression(str, verifyColumnName, str2));
            return this;
        }
        throw new SQLException("Can't update foreign colletion field: " + str);
    }

    public StatementBuilder<T, ID> updateColumnValue(String str, Object obj) {
        FieldType verifyColumnName = verifyColumnName(str);
        if (!verifyColumnName.isForeignCollection()) {
            addUpdateColumnToList(str, new SetValue(str, verifyColumnName, obj));
            return this;
        }
        throw new SQLException("Can't update foreign colletion field: " + str);
    }
}
