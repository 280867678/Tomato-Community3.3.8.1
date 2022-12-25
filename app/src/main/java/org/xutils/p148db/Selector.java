package org.xutils.p148db;

import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Marker;
import org.xutils.p148db.sqlite.WhereBuilder;
import org.xutils.p148db.table.DbModel;
import org.xutils.p148db.table.TableEntity;
import org.xutils.p149ex.DbException;

/* renamed from: org.xutils.db.Selector */
/* loaded from: classes4.dex */
public final class Selector<T> {
    private int limit = 0;
    private int offset = 0;
    private List<OrderBy> orderByList;
    private final TableEntity<T> table;
    private WhereBuilder whereBuilder;

    private Selector(TableEntity<T> tableEntity) {
        this.table = tableEntity;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> Selector<T> from(TableEntity<T> tableEntity) {
        return new Selector<>(tableEntity);
    }

    public Selector<T> where(WhereBuilder whereBuilder) {
        this.whereBuilder = whereBuilder;
        return this;
    }

    public Selector<T> where(String str, String str2, Object obj) {
        this.whereBuilder = WhereBuilder.m30b(str, str2, obj);
        return this;
    }

    public Selector<T> and(String str, String str2, Object obj) {
        this.whereBuilder.and(str, str2, obj);
        return this;
    }

    public Selector<T> and(WhereBuilder whereBuilder) {
        this.whereBuilder.and(whereBuilder);
        return this;
    }

    /* renamed from: or */
    public Selector<T> m33or(String str, String str2, Object obj) {
        this.whereBuilder.m29or(str, str2, obj);
        return this;
    }

    /* renamed from: or */
    public Selector m32or(WhereBuilder whereBuilder) {
        this.whereBuilder.m28or(whereBuilder);
        return this;
    }

    public Selector<T> expr(String str) {
        if (this.whereBuilder == null) {
            this.whereBuilder = WhereBuilder.m31b();
        }
        this.whereBuilder.expr(str);
        return this;
    }

    public DbModelSelector groupBy(String str) {
        return new DbModelSelector((Selector<?>) this, str);
    }

    public DbModelSelector select(String... strArr) {
        return new DbModelSelector((Selector<?>) this, strArr);
    }

    public Selector<T> orderBy(String str) {
        if (this.orderByList == null) {
            this.orderByList = new ArrayList(5);
        }
        this.orderByList.add(new OrderBy(str));
        return this;
    }

    public Selector<T> orderBy(String str, boolean z) {
        if (this.orderByList == null) {
            this.orderByList = new ArrayList(5);
        }
        this.orderByList.add(new OrderBy(str, z));
        return this;
    }

    public Selector<T> limit(int i) {
        this.limit = i;
        return this;
    }

    public Selector<T> offset(int i) {
        this.offset = i;
        return this;
    }

    public TableEntity<T> getTable() {
        return this.table;
    }

    public WhereBuilder getWhereBuilder() {
        return this.whereBuilder;
    }

    public List<OrderBy> getOrderByList() {
        return this.orderByList;
    }

    public int getLimit() {
        return this.limit;
    }

    public int getOffset() {
        return this.offset;
    }

    public T findFirst() throws DbException {
        if (!this.table.tableIsExist()) {
            return null;
        }
        limit(1);
        Cursor execQuery = this.table.getDb().execQuery(toString());
        if (execQuery != null) {
            try {
                if (execQuery.moveToNext()) {
                    return (T) CursorUtils.getEntity(this.table, execQuery);
                }
            } finally {
            }
        }
        return null;
    }

    public List<T> findAll() throws DbException {
        ArrayList arrayList = null;
        if (!this.table.tableIsExist()) {
            return null;
        }
        Cursor execQuery = this.table.getDb().execQuery(toString());
        if (execQuery != null) {
            try {
                arrayList = new ArrayList();
                while (execQuery.moveToNext()) {
                    arrayList.add(CursorUtils.getEntity(this.table, execQuery));
                }
            } finally {
            }
        }
        return arrayList;
    }

    public long count() throws DbException {
        if (!this.table.tableIsExist()) {
            return 0L;
        }
        DbModel findFirst = select("count(\"" + this.table.getId().getName() + "\") as count").findFirst();
        if (findFirst == null) {
            return 0L;
        }
        return findFirst.getLong("count");
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(Marker.ANY_MARKER);
        sb.append(" FROM ");
        sb.append("\"");
        sb.append(this.table.getName());
        sb.append("\"");
        WhereBuilder whereBuilder = this.whereBuilder;
        if (whereBuilder != null && whereBuilder.getWhereItemSize() > 0) {
            sb.append(" WHERE ");
            sb.append(this.whereBuilder.toString());
        }
        List<OrderBy> list = this.orderByList;
        if (list != null && list.size() > 0) {
            sb.append(" ORDER BY ");
            for (OrderBy orderBy : this.orderByList) {
                sb.append(orderBy.toString());
                sb.append(',');
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        if (this.limit > 0) {
            sb.append(" LIMIT ");
            sb.append(this.limit);
            sb.append(" OFFSET ");
            sb.append(this.offset);
        }
        return sb.toString();
    }

    /* renamed from: org.xutils.db.Selector$OrderBy */
    /* loaded from: classes4.dex */
    public static class OrderBy {
        private String columnName;
        private boolean desc;

        public OrderBy(String str) {
            this.columnName = str;
        }

        public OrderBy(String str, boolean z) {
            this.columnName = str;
            this.desc = z;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("\"");
            sb.append(this.columnName);
            sb.append("\"");
            sb.append(this.desc ? " DESC" : " ASC");
            return sb.toString();
        }
    }
}
