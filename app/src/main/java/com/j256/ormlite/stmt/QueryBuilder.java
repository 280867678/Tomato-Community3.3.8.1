package com.j256.ormlite.stmt;

import com.j256.ormlite.dao.AbstractC2183Dao;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.p075db.DatabaseType;
import com.j256.ormlite.stmt.StatementBuilder;
import com.j256.ormlite.stmt.query.OrderBy;
import com.j256.ormlite.table.TableInfo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
public class QueryBuilder<T, ID> extends StatementBuilder<T, ID> {
    public boolean distinct;
    public List<String> groupByList;
    public String groupByRaw;
    public String having;
    public final FieldType idField;
    public boolean isCountOfQuery;
    public boolean isInnerQuery;
    public List<QueryBuilder<T, ID>.JoinInfo> joinList;
    public Long limit;
    public Long offset;
    public ArgumentHolder[] orderByArgs;
    public List<OrderBy> orderByList;
    public String orderByRaw;
    public FieldType[] resultFieldTypes;
    public List<String> selectColumnList;
    public boolean selectIdColumn = true;
    public List<String> selectRawList;

    /* loaded from: classes3.dex */
    public static class InternalQueryBuilderWrapper {
        public final QueryBuilder<?, ?> queryBuilder;

        public InternalQueryBuilderWrapper(QueryBuilder<?, ?> queryBuilder) {
            this.queryBuilder = queryBuilder;
        }

        public void appendStatementString(StringBuilder sb, List<ArgumentHolder> list) {
            this.queryBuilder.appendStatementString(sb, list);
        }

        public FieldType[] getResultFieldTypes() {
            return this.queryBuilder.getResultFieldTypes();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class JoinInfo {
        public FieldType localField;
        public StatementBuilder.WhereOperation operation;
        public final QueryBuilder<?, ?> queryBuilder;
        public FieldType remoteField;
        public final String type;

        public JoinInfo(String str, QueryBuilder<?, ?> queryBuilder, StatementBuilder.WhereOperation whereOperation) {
            this.type = str;
            this.queryBuilder = queryBuilder;
            this.operation = whereOperation;
        }
    }

    public QueryBuilder(DatabaseType databaseType, TableInfo<T, ID> tableInfo, AbstractC2183Dao<T, ID> abstractC2183Dao) {
        super(databaseType, tableInfo, abstractC2183Dao, StatementBuilder.StatementType.SELECT);
        this.idField = tableInfo.getIdField();
    }

    private void addJoinInfo(String str, QueryBuilder<?, ?> queryBuilder, StatementBuilder.WhereOperation whereOperation) {
        QueryBuilder<T, ID>.JoinInfo joinInfo = new JoinInfo(str, queryBuilder, whereOperation);
        matchJoinedFields(joinInfo, queryBuilder);
        if (this.joinList == null) {
            this.joinList = new ArrayList();
        }
        this.joinList.add(joinInfo);
    }

    private void addSelectColumnToList(String str) {
        verifyColumnName(str);
        this.selectColumnList.add(str);
    }

    private void appendColumnName(StringBuilder sb, String str) {
        if (this.addTableName) {
            this.databaseType.appendEscapedEntityName(sb, this.tableName);
            sb.append('.');
        }
        this.databaseType.appendEscapedEntityName(sb, str);
    }

    private void appendColumns(StringBuilder sb) {
        List<String> list = this.selectColumnList;
        if (list == null) {
            if (this.addTableName) {
                this.databaseType.appendEscapedEntityName(sb, this.tableName);
                sb.append('.');
            }
            sb.append("* ");
            this.resultFieldTypes = this.tableInfo.getFieldTypes();
            return;
        }
        boolean z = this.isInnerQuery;
        List<FieldType> arrayList = new ArrayList<>(list.size() + 1);
        boolean z2 = true;
        for (String str : this.selectColumnList) {
            FieldType fieldTypeByColumnName = this.tableInfo.getFieldTypeByColumnName(str);
            if (fieldTypeByColumnName.isForeignCollection()) {
                arrayList.add(fieldTypeByColumnName);
            } else {
                if (z2) {
                    z2 = false;
                } else {
                    sb.append(',');
                }
                appendFieldColumnName(sb, fieldTypeByColumnName, arrayList);
                if (fieldTypeByColumnName == this.idField) {
                    z = true;
                }
            }
        }
        if (!z && this.selectIdColumn) {
            if (!z2) {
                sb.append(',');
            }
            appendFieldColumnName(sb, this.idField, arrayList);
        }
        sb.append(' ');
        this.resultFieldTypes = (FieldType[]) arrayList.toArray(new FieldType[arrayList.size()]);
    }

    private void appendFieldColumnName(StringBuilder sb, FieldType fieldType, List<FieldType> list) {
        appendColumnName(sb, fieldType.getColumnName());
        if (list != null) {
            list.add(fieldType);
        }
    }

    private void appendGroupBys(StringBuilder sb) {
        boolean z = true;
        if (hasGroupStuff()) {
            appendGroupBys(sb, true);
            z = false;
        }
        List<QueryBuilder<T, ID>.JoinInfo> list = this.joinList;
        if (list != null) {
            for (QueryBuilder<T, ID>.JoinInfo joinInfo : list) {
                QueryBuilder<?, ?> queryBuilder = joinInfo.queryBuilder;
                if (queryBuilder != null && queryBuilder.hasGroupStuff()) {
                    joinInfo.queryBuilder.appendGroupBys(sb, z);
                }
            }
        }
    }

    private void appendGroupBys(StringBuilder sb, boolean z) {
        if (z) {
            sb.append("GROUP BY ");
        }
        if (this.groupByRaw != null) {
            if (!z) {
                sb.append(',');
            }
            sb.append(this.groupByRaw);
        } else {
            for (String str : this.groupByList) {
                if (z) {
                    z = false;
                } else {
                    sb.append(',');
                }
                appendColumnName(sb, str);
            }
        }
        sb.append(' ');
    }

    private void appendHaving(StringBuilder sb) {
        if (this.having != null) {
            sb.append("HAVING ");
            sb.append(this.having);
            sb.append(' ');
        }
    }

    private void appendJoinSql(StringBuilder sb) {
        for (QueryBuilder<T, ID>.JoinInfo joinInfo : this.joinList) {
            sb.append(joinInfo.type);
            sb.append(" JOIN ");
            this.databaseType.appendEscapedEntityName(sb, joinInfo.queryBuilder.tableName);
            sb.append(" ON ");
            this.databaseType.appendEscapedEntityName(sb, this.tableName);
            sb.append('.');
            this.databaseType.appendEscapedEntityName(sb, joinInfo.localField.getColumnName());
            sb.append(" = ");
            this.databaseType.appendEscapedEntityName(sb, joinInfo.queryBuilder.tableName);
            sb.append('.');
            this.databaseType.appendEscapedEntityName(sb, joinInfo.remoteField.getColumnName());
            sb.append(' ');
            QueryBuilder<?, ?> queryBuilder = joinInfo.queryBuilder;
            if (queryBuilder.joinList != null) {
                queryBuilder.appendJoinSql(sb);
            }
        }
    }

    private void appendLimit(StringBuilder sb) {
        if (this.limit == null || !this.databaseType.isLimitSqlSupported()) {
            return;
        }
        this.databaseType.appendLimitValue(sb, this.limit.longValue(), this.offset);
    }

    private void appendOffset(StringBuilder sb) {
        if (this.offset == null) {
            return;
        }
        if (!this.databaseType.isOffsetLimitArgument()) {
            this.databaseType.appendOffsetValue(sb, this.offset.longValue());
        } else if (this.limit == null) {
            throw new SQLException("If the offset is specified, limit must also be specified with this database");
        }
    }

    private void appendOrderBys(StringBuilder sb, List<ArgumentHolder> list) {
        boolean z = true;
        if (hasOrderStuff()) {
            appendOrderBys(sb, true, list);
            z = false;
        }
        List<QueryBuilder<T, ID>.JoinInfo> list2 = this.joinList;
        if (list2 != null) {
            for (QueryBuilder<T, ID>.JoinInfo joinInfo : list2) {
                QueryBuilder<?, ?> queryBuilder = joinInfo.queryBuilder;
                if (queryBuilder != null && queryBuilder.hasOrderStuff()) {
                    joinInfo.queryBuilder.appendOrderBys(sb, z, list);
                }
            }
        }
    }

    private void appendOrderBys(StringBuilder sb, boolean z, List<ArgumentHolder> list) {
        if (z) {
            sb.append("ORDER BY ");
        }
        if (this.orderByRaw != null) {
            if (!z) {
                sb.append(',');
            }
            sb.append(this.orderByRaw);
            ArgumentHolder[] argumentHolderArr = this.orderByArgs;
            if (argumentHolderArr != null) {
                for (ArgumentHolder argumentHolder : argumentHolderArr) {
                    list.add(argumentHolder);
                }
            }
            z = false;
        }
        List<OrderBy> list2 = this.orderByList;
        if (list2 != null) {
            for (OrderBy orderBy : list2) {
                if (z) {
                    z = false;
                } else {
                    sb.append(',');
                }
                appendColumnName(sb, orderBy.getColumnName());
                if (!orderBy.isAscending()) {
                    sb.append(" DESC");
                }
            }
        }
        sb.append(' ');
    }

    private void appendSelectRaw(StringBuilder sb) {
        boolean z = true;
        for (String str : this.selectRawList) {
            if (z) {
                z = false;
            } else {
                sb.append(", ");
            }
            sb.append(str);
        }
        sb.append(' ');
    }

    private boolean hasGroupStuff() {
        List<String> list = this.groupByList;
        return (list != null && !list.isEmpty()) || this.groupByRaw != null;
    }

    private boolean hasOrderStuff() {
        List<OrderBy> list = this.orderByList;
        return (list != null && !list.isEmpty()) || this.orderByRaw != null;
    }

    private void matchJoinedFields(QueryBuilder<T, ID>.JoinInfo joinInfo, QueryBuilder<?, ?> queryBuilder) {
        FieldType[] fieldTypes;
        FieldType[] fieldTypes2;
        for (FieldType fieldType : this.tableInfo.getFieldTypes()) {
            FieldType foreignIdField = fieldType.getForeignIdField();
            if (fieldType.isForeign() && foreignIdField.equals(queryBuilder.tableInfo.getIdField())) {
                joinInfo.localField = fieldType;
                joinInfo.remoteField = foreignIdField;
                return;
            }
        }
        for (FieldType fieldType2 : queryBuilder.tableInfo.getFieldTypes()) {
            if (fieldType2.isForeign() && fieldType2.getForeignIdField().equals(this.idField)) {
                joinInfo.localField = this.idField;
                joinInfo.remoteField = fieldType2;
                return;
            }
        }
        throw new SQLException("Could not find a foreign " + this.tableInfo.getDataClass() + " field in " + queryBuilder.tableInfo.getDataClass() + " or vice versa");
    }

    private void setAddTableName(boolean z) {
        this.addTableName = z;
        List<QueryBuilder<T, ID>.JoinInfo> list = this.joinList;
        if (list != null) {
            for (QueryBuilder<T, ID>.JoinInfo joinInfo : list) {
                joinInfo.queryBuilder.setAddTableName(z);
            }
        }
    }

    @Override // com.j256.ormlite.stmt.StatementBuilder
    public void appendStatementEnd(StringBuilder sb, List<ArgumentHolder> list) {
        appendGroupBys(sb);
        appendHaving(sb);
        appendOrderBys(sb, list);
        if (!this.databaseType.isLimitAfterSelect()) {
            appendLimit(sb);
        }
        appendOffset(sb);
        setAddTableName(false);
    }

    @Override // com.j256.ormlite.stmt.StatementBuilder
    public void appendStatementStart(StringBuilder sb, List<ArgumentHolder> list) {
        setAddTableName(this.joinList != null);
        sb.append("SELECT ");
        if (this.databaseType.isLimitAfterSelect()) {
            appendLimit(sb);
        }
        if (this.distinct) {
            sb.append("DISTINCT ");
        }
        if (this.isCountOfQuery) {
            this.type = StatementBuilder.StatementType.SELECT_LONG;
            sb.append("COUNT(*) ");
        } else {
            List<String> list2 = this.selectRawList;
            if (list2 == null || list2.isEmpty()) {
                this.type = StatementBuilder.StatementType.SELECT;
                appendColumns(sb);
            } else {
                this.type = StatementBuilder.StatementType.SELECT_RAW;
                appendSelectRaw(sb);
            }
        }
        sb.append("FROM ");
        this.databaseType.appendEscapedEntityName(sb, this.tableName);
        sb.append(' ');
        if (this.joinList != null) {
            appendJoinSql(sb);
        }
    }

    @Override // com.j256.ormlite.stmt.StatementBuilder
    public boolean appendWhereStatement(StringBuilder sb, List<ArgumentHolder> list, StatementBuilder.WhereOperation whereOperation) {
        boolean z = whereOperation == StatementBuilder.WhereOperation.FIRST;
        if (this.where != null) {
            z = super.appendWhereStatement(sb, list, whereOperation);
        }
        List<QueryBuilder<T, ID>.JoinInfo> list2 = this.joinList;
        if (list2 != null) {
            for (QueryBuilder<T, ID>.JoinInfo joinInfo : list2) {
                z = joinInfo.queryBuilder.appendWhereStatement(sb, list, z ? StatementBuilder.WhereOperation.FIRST : joinInfo.operation);
            }
        }
        return z;
    }

    @Override // com.j256.ormlite.stmt.StatementBuilder
    @Deprecated
    public void clear() {
        reset();
    }

    public long countOf() {
        setCountOf(true);
        return this.dao.countOf(prepare());
    }

    public QueryBuilder<T, ID> distinct() {
        this.distinct = true;
        this.selectIdColumn = false;
        return this;
    }

    public void enableInnerQuery() {
        this.isInnerQuery = true;
    }

    @Override // com.j256.ormlite.stmt.StatementBuilder
    public FieldType[] getResultFieldTypes() {
        return this.resultFieldTypes;
    }

    public int getSelectColumnCount() {
        if (this.isCountOfQuery) {
            return 1;
        }
        List<String> list = this.selectRawList;
        if (list != null && !list.isEmpty()) {
            return this.selectRawList.size();
        }
        List<String> list2 = this.selectColumnList;
        if (list2 != null) {
            return list2.size();
        }
        return 0;
    }

    public List<String> getSelectColumns() {
        if (this.isCountOfQuery) {
            return Collections.singletonList("COUNT(*)");
        }
        List<String> list = this.selectRawList;
        if (list != null && !list.isEmpty()) {
            return this.selectRawList;
        }
        List<String> list2 = this.selectColumnList;
        return list2 == null ? Collections.emptyList() : list2;
    }

    public QueryBuilder<T, ID> groupBy(String str) {
        if (verifyColumnName(str).isForeignCollection()) {
            throw new IllegalArgumentException("Can't groupBy foreign colletion field: " + str);
        }
        if (this.groupByList == null) {
            this.groupByList = new ArrayList();
        }
        this.groupByList.add(str);
        this.selectIdColumn = false;
        return this;
    }

    public QueryBuilder<T, ID> groupByRaw(String str) {
        this.groupByRaw = str;
        return this;
    }

    public QueryBuilder<T, ID> having(String str) {
        this.having = str;
        return this;
    }

    public CloseableIterator<T> iterator() {
        return this.dao.iterator(prepare());
    }

    public QueryBuilder<T, ID> join(QueryBuilder<?, ?> queryBuilder) {
        addJoinInfo("INNER", queryBuilder, StatementBuilder.WhereOperation.AND);
        return this;
    }

    public QueryBuilder<T, ID> joinOr(QueryBuilder<?, ?> queryBuilder) {
        addJoinInfo("INNER", queryBuilder, StatementBuilder.WhereOperation.OR);
        return this;
    }

    public QueryBuilder<T, ID> leftJoin(QueryBuilder<?, ?> queryBuilder) {
        addJoinInfo("LEFT", queryBuilder, StatementBuilder.WhereOperation.AND);
        return this;
    }

    public QueryBuilder<T, ID> leftJoinOr(QueryBuilder<?, ?> queryBuilder) {
        addJoinInfo("LEFT", queryBuilder, StatementBuilder.WhereOperation.OR);
        return this;
    }

    @Deprecated
    public QueryBuilder<T, ID> limit(int i) {
        return limit(Long.valueOf(i));
    }

    public QueryBuilder<T, ID> limit(Long l) {
        this.limit = l;
        return this;
    }

    @Deprecated
    public QueryBuilder<T, ID> offset(int i) {
        return offset(Long.valueOf(i));
    }

    public QueryBuilder<T, ID> offset(Long l) {
        if (this.databaseType.isOffsetSqlSupported()) {
            this.offset = l;
            return this;
        }
        throw new SQLException("Offset is not supported by this database");
    }

    public QueryBuilder<T, ID> orderBy(String str, boolean z) {
        if (!verifyColumnName(str).isForeignCollection()) {
            if (this.orderByList == null) {
                this.orderByList = new ArrayList();
            }
            this.orderByList.add(new OrderBy(str, z));
            return this;
        }
        throw new IllegalArgumentException("Can't orderBy foreign colletion field: " + str);
    }

    public QueryBuilder<T, ID> orderByRaw(String str) {
        return orderByRaw(str, null);
    }

    public QueryBuilder<T, ID> orderByRaw(String str, ArgumentHolder... argumentHolderArr) {
        this.orderByRaw = str;
        this.orderByArgs = argumentHolderArr;
        return this;
    }

    public PreparedQuery<T> prepare() {
        return super.prepareStatement(this.limit);
    }

    public List<T> query() {
        return this.dao.query(prepare());
    }

    public T queryForFirst() {
        return this.dao.queryForFirst(prepare());
    }

    public GenericRawResults<String[]> queryRaw() {
        return this.dao.queryRaw(prepareStatementString(), new String[0]);
    }

    public String[] queryRawFirst() {
        return this.dao.queryRaw(prepareStatementString(), new String[0]).getFirstResult();
    }

    @Override // com.j256.ormlite.stmt.StatementBuilder
    public void reset() {
        super.reset();
        this.distinct = false;
        this.selectIdColumn = true;
        this.selectColumnList = null;
        this.selectRawList = null;
        this.orderByList = null;
        this.orderByRaw = null;
        this.groupByList = null;
        this.groupByRaw = null;
        this.isInnerQuery = false;
        this.isCountOfQuery = false;
        this.having = null;
        this.limit = null;
        this.offset = null;
        List<QueryBuilder<T, ID>.JoinInfo> list = this.joinList;
        if (list != null) {
            list.clear();
            this.joinList = null;
        }
        this.addTableName = false;
    }

    public QueryBuilder<T, ID> selectColumns(Iterable<String> iterable) {
        if (this.selectColumnList == null) {
            this.selectColumnList = new ArrayList();
        }
        for (String str : iterable) {
            addSelectColumnToList(str);
        }
        return this;
    }

    public QueryBuilder<T, ID> selectColumns(String... strArr) {
        if (this.selectColumnList == null) {
            this.selectColumnList = new ArrayList();
        }
        for (String str : strArr) {
            addSelectColumnToList(str);
        }
        return this;
    }

    public QueryBuilder<T, ID> selectRaw(String... strArr) {
        if (this.selectRawList == null) {
            this.selectRawList = new ArrayList();
        }
        for (String str : strArr) {
            this.selectRawList.add(str);
        }
        return this;
    }

    public QueryBuilder<T, ID> setCountOf(boolean z) {
        this.isCountOfQuery = z;
        return this;
    }

    @Override // com.j256.ormlite.stmt.StatementBuilder
    public boolean shouldPrependTableNameToColumns() {
        return this.joinList != null;
    }
}
