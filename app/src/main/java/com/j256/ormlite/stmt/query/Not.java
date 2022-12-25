package com.j256.ormlite.stmt.query;

import com.j256.ormlite.p075db.DatabaseType;
import com.j256.ormlite.stmt.ArgumentHolder;
import java.util.List;

/* loaded from: classes3.dex */
public class Not implements Clause, NeedsFutureClause {
    public Comparison comparison = null;
    public Exists exists = null;

    public Not() {
    }

    public Not(Clause clause) {
        setMissingClause(clause);
    }

    @Override // com.j256.ormlite.stmt.query.Clause
    public void appendSql(DatabaseType databaseType, String str, StringBuilder sb, List<ArgumentHolder> list) {
        if (this.comparison == null && this.exists == null) {
            throw new IllegalStateException("Clause has not been set in NOT operation");
        }
        Comparison comparison = this.comparison;
        sb.append("(NOT ");
        if (comparison == null) {
            this.exists.appendSql(databaseType, str, sb, list);
        } else {
            if (str != null) {
                databaseType.appendEscapedEntityName(sb, str);
                sb.append('.');
            }
            databaseType.appendEscapedEntityName(sb, this.comparison.getColumnName());
            sb.append(' ');
            this.comparison.appendOperation(sb);
            this.comparison.appendValue(databaseType, sb, list);
        }
        sb.append(") ");
    }

    @Override // com.j256.ormlite.stmt.query.NeedsFutureClause
    public void setMissingClause(Clause clause) {
        if (this.comparison == null) {
            if (clause instanceof Comparison) {
                this.comparison = (Comparison) clause;
                return;
            } else if (clause instanceof Exists) {
                this.exists = (Exists) clause;
                return;
            } else {
                throw new IllegalArgumentException("NOT operation can only work with comparison SQL clauses, not " + clause);
            }
        }
        throw new IllegalArgumentException("NOT operation already has a comparison set");
    }

    public String toString() {
        if (this.comparison == null) {
            return "NOT without comparison";
        }
        return "NOT comparison " + this.comparison;
    }
}
