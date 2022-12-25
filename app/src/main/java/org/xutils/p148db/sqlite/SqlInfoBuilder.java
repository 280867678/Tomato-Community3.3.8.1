package org.xutils.p148db.sqlite;

import com.j256.ormlite.stmt.query.SimpleComparison;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.xutils.common.util.KeyValue;
import org.xutils.p148db.table.ColumnEntity;
import org.xutils.p148db.table.TableEntity;
import org.xutils.p149ex.DbException;

/* renamed from: org.xutils.db.sqlite.SqlInfoBuilder */
/* loaded from: classes4.dex */
public final class SqlInfoBuilder {
    private static final ConcurrentHashMap<TableEntity<?>, String> INSERT_SQL_CACHE = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<TableEntity<?>, String> REPLACE_SQL_CACHE = new ConcurrentHashMap<>();

    private SqlInfoBuilder() {
    }

    public static SqlInfo buildInsertSqlInfo(TableEntity<?> tableEntity, Object obj) throws DbException {
        List<KeyValue> entity2KeyValueList = entity2KeyValueList(tableEntity, obj);
        if (entity2KeyValueList.size() == 0) {
            return null;
        }
        SqlInfo sqlInfo = new SqlInfo();
        String str = INSERT_SQL_CACHE.get(tableEntity);
        if (str == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO ");
            sb.append("\"");
            sb.append(tableEntity.getName());
            sb.append("\"");
            sb.append(" (");
            for (KeyValue keyValue : entity2KeyValueList) {
                sb.append("\"");
                sb.append(keyValue.key);
                sb.append("\"");
                sb.append(',');
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(") VALUES (");
            int size = entity2KeyValueList.size();
            for (int i = 0; i < size; i++) {
                sb.append("?,");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(")");
            String sb2 = sb.toString();
            sqlInfo.setSql(sb2);
            sqlInfo.addBindArgs(entity2KeyValueList);
            INSERT_SQL_CACHE.put(tableEntity, sb2);
        } else {
            sqlInfo.setSql(str);
            sqlInfo.addBindArgs(entity2KeyValueList);
        }
        return sqlInfo;
    }

    public static SqlInfo buildReplaceSqlInfo(TableEntity<?> tableEntity, Object obj) throws DbException {
        List<KeyValue> entity2KeyValueList = entity2KeyValueList(tableEntity, obj);
        if (entity2KeyValueList.size() == 0) {
            return null;
        }
        SqlInfo sqlInfo = new SqlInfo();
        String str = REPLACE_SQL_CACHE.get(tableEntity);
        if (str == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("REPLACE INTO ");
            sb.append("\"");
            sb.append(tableEntity.getName());
            sb.append("\"");
            sb.append(" (");
            for (KeyValue keyValue : entity2KeyValueList) {
                sb.append("\"");
                sb.append(keyValue.key);
                sb.append("\"");
                sb.append(',');
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(") VALUES (");
            int size = entity2KeyValueList.size();
            for (int i = 0; i < size; i++) {
                sb.append("?,");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(")");
            String sb2 = sb.toString();
            sqlInfo.setSql(sb2);
            sqlInfo.addBindArgs(entity2KeyValueList);
            REPLACE_SQL_CACHE.put(tableEntity, sb2);
        } else {
            sqlInfo.setSql(str);
            sqlInfo.addBindArgs(entity2KeyValueList);
        }
        return sqlInfo;
    }

    public static SqlInfo buildDeleteSqlInfo(TableEntity<?> tableEntity, Object obj) throws DbException {
        SqlInfo sqlInfo = new SqlInfo();
        ColumnEntity id = tableEntity.getId();
        Object columnValue = id.getColumnValue(obj);
        if (columnValue == null) {
            throw new DbException("this entity[" + tableEntity.getEntityType() + "]'s id value is null");
        }
        sqlInfo.setSql("DELETE FROM \"" + tableEntity.getName() + "\" WHERE " + WhereBuilder.m30b(id.getName(), SimpleComparison.EQUAL_TO_OPERATION, columnValue));
        return sqlInfo;
    }

    public static SqlInfo buildDeleteSqlInfoById(TableEntity<?> tableEntity, Object obj) throws DbException {
        SqlInfo sqlInfo = new SqlInfo();
        ColumnEntity id = tableEntity.getId();
        if (obj == null) {
            throw new DbException("this entity[" + tableEntity.getEntityType() + "]'s id value is null");
        }
        sqlInfo.setSql("DELETE FROM \"" + tableEntity.getName() + "\" WHERE " + WhereBuilder.m30b(id.getName(), SimpleComparison.EQUAL_TO_OPERATION, obj));
        return sqlInfo;
    }

    public static SqlInfo buildDeleteSqlInfo(TableEntity<?> tableEntity, WhereBuilder whereBuilder) throws DbException {
        StringBuilder sb = new StringBuilder("DELETE FROM ");
        sb.append("\"");
        sb.append(tableEntity.getName());
        sb.append("\"");
        if (whereBuilder != null && whereBuilder.getWhereItemSize() > 0) {
            sb.append(" WHERE ");
            sb.append(whereBuilder.toString());
        }
        return new SqlInfo(sb.toString());
    }

    public static SqlInfo buildUpdateSqlInfo(TableEntity<?> tableEntity, Object obj, String... strArr) throws DbException {
        List<KeyValue> entity2KeyValueList = entity2KeyValueList(tableEntity, obj);
        HashSet hashSet = null;
        if (entity2KeyValueList.size() == 0) {
            return null;
        }
        if (strArr != null && strArr.length > 0) {
            hashSet = new HashSet(strArr.length);
            Collections.addAll(hashSet, strArr);
        }
        ColumnEntity id = tableEntity.getId();
        Object columnValue = id.getColumnValue(obj);
        if (columnValue == null) {
            throw new DbException("this entity[" + tableEntity.getEntityType() + "]'s id value is null");
        }
        SqlInfo sqlInfo = new SqlInfo();
        StringBuilder sb = new StringBuilder("UPDATE ");
        sb.append("\"");
        sb.append(tableEntity.getName());
        sb.append("\"");
        sb.append(" SET ");
        for (KeyValue keyValue : entity2KeyValueList) {
            if (hashSet == null || hashSet.contains(keyValue.key)) {
                sb.append("\"");
                sb.append(keyValue.key);
                sb.append("\"");
                sb.append("=?,");
                sqlInfo.addBindArg(keyValue);
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(" WHERE ");
        sb.append(WhereBuilder.m30b(id.getName(), SimpleComparison.EQUAL_TO_OPERATION, columnValue));
        sqlInfo.setSql(sb.toString());
        return sqlInfo;
    }

    public static SqlInfo buildUpdateSqlInfo(TableEntity<?> tableEntity, WhereBuilder whereBuilder, KeyValue... keyValueArr) throws DbException {
        if (keyValueArr == null || keyValueArr.length == 0) {
            return null;
        }
        SqlInfo sqlInfo = new SqlInfo();
        StringBuilder sb = new StringBuilder("UPDATE ");
        sb.append("\"");
        sb.append(tableEntity.getName());
        sb.append("\"");
        sb.append(" SET ");
        for (KeyValue keyValue : keyValueArr) {
            sb.append("\"");
            sb.append(keyValue.key);
            sb.append("\"");
            sb.append("=?,");
            sqlInfo.addBindArg(keyValue);
        }
        sb.deleteCharAt(sb.length() - 1);
        if (whereBuilder != null && whereBuilder.getWhereItemSize() > 0) {
            sb.append(" WHERE ");
            sb.append(whereBuilder.toString());
        }
        sqlInfo.setSql(sb.toString());
        return sqlInfo;
    }

    public static SqlInfo buildCreateTableSqlInfo(TableEntity<?> tableEntity) throws DbException {
        ColumnEntity id = tableEntity.getId();
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS ");
        sb.append("\"");
        sb.append(tableEntity.getName());
        sb.append("\"");
        sb.append(" ( ");
        if (id.isAutoId()) {
            sb.append("\"");
            sb.append(id.getName());
            sb.append("\"");
            sb.append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
        } else {
            sb.append("\"");
            sb.append(id.getName());
            sb.append("\"");
            sb.append(id.getColumnDbType());
            sb.append(" PRIMARY KEY, ");
        }
        for (ColumnEntity columnEntity : tableEntity.getColumnMap().values()) {
            if (!columnEntity.isId()) {
                sb.append("\"");
                sb.append(columnEntity.getName());
                sb.append("\"");
                sb.append(' ');
                sb.append(columnEntity.getColumnDbType());
                sb.append(' ');
                sb.append(columnEntity.getProperty());
                sb.append(',');
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(" )");
        return new SqlInfo(sb.toString());
    }

    public static List<KeyValue> entity2KeyValueList(TableEntity<?> tableEntity, Object obj) {
        Collection<ColumnEntity> values = tableEntity.getColumnMap().values();
        ArrayList arrayList = new ArrayList(values.size());
        for (ColumnEntity columnEntity : values) {
            KeyValue column2KeyValue = column2KeyValue(obj, columnEntity);
            if (column2KeyValue != null) {
                arrayList.add(column2KeyValue);
            }
        }
        return arrayList;
    }

    private static KeyValue column2KeyValue(Object obj, ColumnEntity columnEntity) {
        if (columnEntity.isAutoId()) {
            return null;
        }
        return new KeyValue(columnEntity.getName(), columnEntity.getFieldValue(obj));
    }
}
