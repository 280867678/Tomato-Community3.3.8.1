package com.j256.ormlite.stmt.query;

import com.j256.ormlite.p075db.DatabaseType;
import com.j256.ormlite.stmt.ArgumentHolder;
import java.util.List;

/* loaded from: classes3.dex */
public class ManyClause implements Clause, NeedsFutureClause {
    public static final String AND_OPERATION = "AND";
    public static final String OR_OPERATION = "OR";
    public final Clause first;
    public final String operation;
    public final Clause[] others;
    public Clause second;
    public final int startOthersAt;

    public ManyClause(Clause clause, Clause clause2, Clause[] clauseArr, String str) {
        this.first = clause;
        this.second = clause2;
        this.others = clauseArr;
        this.startOthersAt = 0;
        this.operation = str;
    }

    public ManyClause(Clause clause, String str) {
        this.first = clause;
        this.second = null;
        this.others = null;
        this.startOthersAt = 0;
        this.operation = str;
    }

    public ManyClause(Clause[] clauseArr, String str) {
        this.first = clauseArr[0];
        if (clauseArr.length < 2) {
            this.second = null;
            this.startOthersAt = clauseArr.length;
        } else {
            this.second = clauseArr[1];
            this.startOthersAt = 2;
        }
        this.others = clauseArr;
        this.operation = str;
    }

    @Override // com.j256.ormlite.stmt.query.Clause
    public void appendSql(DatabaseType databaseType, String str, StringBuilder sb, List<ArgumentHolder> list) {
        sb.append("(");
        this.first.appendSql(databaseType, str, sb, list);
        if (this.second != null) {
            sb.append(this.operation);
            sb.append(' ');
            this.second.appendSql(databaseType, str, sb, list);
        }
        if (this.others != null) {
            for (int i = this.startOthersAt; i < this.others.length; i++) {
                sb.append(this.operation);
                sb.append(' ');
                this.others[i].appendSql(databaseType, str, sb, list);
            }
        }
        sb.append(") ");
    }

    @Override // com.j256.ormlite.stmt.query.NeedsFutureClause
    public void setMissingClause(Clause clause) {
        this.second = clause;
    }
}
