package org.xutils.p148db;

import android.database.Cursor;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Marker;
import org.xutils.p148db.Selector;
import org.xutils.p148db.sqlite.WhereBuilder;
import org.xutils.p148db.table.DbModel;
import org.xutils.p148db.table.TableEntity;
import org.xutils.p149ex.DbException;

/* renamed from: org.xutils.db.DbModelSelector */
/* loaded from: classes4.dex */
public final class DbModelSelector {
    private String[] columnExpressions;
    private String groupByColumnName;
    private WhereBuilder having;
    private Selector<?> selector;

    /* JADX INFO: Access modifiers changed from: protected */
    public DbModelSelector(Selector<?> selector, String str) {
        this.selector = selector;
        this.groupByColumnName = str;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public DbModelSelector(Selector<?> selector, String[] strArr) {
        this.selector = selector;
        this.columnExpressions = strArr;
    }

    public DbModelSelector where(WhereBuilder whereBuilder) {
        this.selector.where(whereBuilder);
        return this;
    }

    public DbModelSelector where(String str, String str2, Object obj) {
        this.selector.where(str, str2, obj);
        return this;
    }

    public DbModelSelector and(String str, String str2, Object obj) {
        this.selector.and(str, str2, obj);
        return this;
    }

    public DbModelSelector and(WhereBuilder whereBuilder) {
        this.selector.and(whereBuilder);
        return this;
    }

    /* renamed from: or */
    public DbModelSelector m35or(String str, String str2, Object obj) {
        this.selector.m33or(str, str2, obj);
        return this;
    }

    /* renamed from: or */
    public DbModelSelector m34or(WhereBuilder whereBuilder) {
        this.selector.m32or(whereBuilder);
        return this;
    }

    public DbModelSelector expr(String str) {
        this.selector.expr(str);
        return this;
    }

    public DbModelSelector groupBy(String str) {
        this.groupByColumnName = str;
        return this;
    }

    public DbModelSelector having(WhereBuilder whereBuilder) {
        this.having = whereBuilder;
        return this;
    }

    public DbModelSelector select(String... strArr) {
        this.columnExpressions = strArr;
        return this;
    }

    public DbModelSelector orderBy(String str) {
        this.selector.orderBy(str);
        return this;
    }

    public DbModelSelector orderBy(String str, boolean z) {
        this.selector.orderBy(str, z);
        return this;
    }

    public DbModelSelector limit(int i) {
        this.selector.limit(i);
        return this;
    }

    public DbModelSelector offset(int i) {
        this.selector.offset(i);
        return this;
    }

    public TableEntity<?> getTable() {
        return this.selector.getTable();
    }

    public DbModel findFirst() throws DbException {
        TableEntity<?> table = this.selector.getTable();
        if (!table.tableIsExist()) {
            return null;
        }
        limit(1);
        Cursor execQuery = table.getDb().execQuery(toString());
        if (execQuery != null) {
            try {
                if (execQuery.moveToNext()) {
                    return CursorUtils.getDbModel(execQuery);
                }
            } finally {
            }
        }
        return null;
    }

    public List<DbModel> findAll() throws DbException {
        TableEntity<?> table = this.selector.getTable();
        ArrayList arrayList = null;
        if (!table.tableIsExist()) {
            return null;
        }
        Cursor execQuery = table.getDb().execQuery(toString());
        if (execQuery != null) {
            try {
                arrayList = new ArrayList();
                while (execQuery.moveToNext()) {
                    arrayList.add(CursorUtils.getDbModel(execQuery));
                }
            } finally {
            }
        }
        return arrayList;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        String[] strArr = this.columnExpressions;
        if (strArr != null && strArr.length > 0) {
            for (String str : strArr) {
                sb.append(str);
                sb.append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
        } else if (!TextUtils.isEmpty(this.groupByColumnName)) {
            sb.append(this.groupByColumnName);
        } else {
            sb.append(Marker.ANY_MARKER);
        }
        sb.append(" FROM ");
        sb.append("\"");
        sb.append(this.selector.getTable().getName());
        sb.append("\"");
        WhereBuilder whereBuilder = this.selector.getWhereBuilder();
        if (whereBuilder != null && whereBuilder.getWhereItemSize() > 0) {
            sb.append(" WHERE ");
            sb.append(whereBuilder.toString());
        }
        if (!TextUtils.isEmpty(this.groupByColumnName)) {
            sb.append(" GROUP BY ");
            sb.append("\"");
            sb.append(this.groupByColumnName);
            sb.append("\"");
            WhereBuilder whereBuilder2 = this.having;
            if (whereBuilder2 != null && whereBuilder2.getWhereItemSize() > 0) {
                sb.append(" HAVING ");
                sb.append(this.having.toString());
            }
        }
        List<Selector.OrderBy> orderByList = this.selector.getOrderByList();
        if (orderByList != null && orderByList.size() > 0) {
            for (int i = 0; i < orderByList.size(); i++) {
                sb.append(" ORDER BY ");
                sb.append(orderByList.get(i).toString());
                sb.append(',');
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        if (this.selector.getLimit() > 0) {
            sb.append(" LIMIT ");
            sb.append(this.selector.getLimit());
            sb.append(" OFFSET ");
            sb.append(this.selector.getOffset());
        }
        return sb.toString();
    }
}
