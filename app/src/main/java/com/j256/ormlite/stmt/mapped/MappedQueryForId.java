package com.j256.ormlite.stmt.mapped;

import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.p075db.DatabaseType;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.TableInfo;
import java.sql.SQLException;

/* loaded from: classes3.dex */
public class MappedQueryForId<T, ID> extends BaseMappedQuery<T, ID> {
    public final String label;

    public MappedQueryForId(TableInfo<T, ID> tableInfo, String str, FieldType[] fieldTypeArr, FieldType[] fieldTypeArr2, String str2) {
        super(tableInfo, str, fieldTypeArr, fieldTypeArr2);
        this.label = str2;
    }

    public static <T, ID> MappedQueryForId<T, ID> build(DatabaseType databaseType, TableInfo<T, ID> tableInfo, FieldType fieldType) {
        if (fieldType == null && (fieldType = tableInfo.getIdField()) == null) {
            throw new SQLException("Cannot query-for-id with " + tableInfo.getDataClass() + " because it doesn't have an id field");
        }
        return new MappedQueryForId<>(tableInfo, buildStatement(databaseType, tableInfo, fieldType), new FieldType[]{fieldType}, tableInfo.getFieldTypes(), "query-for-id");
    }

    public static <T, ID> String buildStatement(DatabaseType databaseType, TableInfo<T, ID> tableInfo, FieldType fieldType) {
        StringBuilder sb = new StringBuilder(64);
        BaseMappedStatement.appendTableName(databaseType, sb, "SELECT * FROM ", tableInfo.getTableName());
        BaseMappedStatement.appendWhereFieldEq(databaseType, fieldType, sb, null);
        return sb.toString();
    }

    private void logArgs(Object[] objArr) {
        if (objArr.length > 0) {
            BaseMappedStatement.logger.trace("{} arguments: {}", this.label, objArr);
        }
    }

    public T execute(DatabaseConnection databaseConnection, ID id, ObjectCache objectCache) {
        Logger logger;
        String str;
        String str2;
        Integer valueOf;
        String str3;
        T t;
        if (objectCache == null || (t = (T) objectCache.get(this.clazz, id)) == null) {
            Object[] objArr = {convertIdToFieldObject(id)};
            T t2 = (T) databaseConnection.queryForOne(this.statement, objArr, this.argFieldTypes, this, objectCache);
            if (t2 == null) {
                logger = BaseMappedStatement.logger;
                str = this.label;
                str2 = this.statement;
                valueOf = Integer.valueOf(objArr.length);
                str3 = "{} using '{}' and {} args, got no results";
            } else if (t2 == DatabaseConnection.MORE_THAN_ONE) {
                BaseMappedStatement.logger.error("{} using '{}' and {} args, got >1 results", this.label, this.statement, Integer.valueOf(objArr.length));
                logArgs(objArr);
                throw new SQLException(this.label + " got more than 1 result: " + this.statement);
            } else {
                logger = BaseMappedStatement.logger;
                str = this.label;
                str2 = this.statement;
                valueOf = Integer.valueOf(objArr.length);
                str3 = "{} using '{}' and {} args, got 1 result";
            }
            logger.debug(str3, str, str2, valueOf);
            logArgs(objArr);
            return t2;
        }
        return t;
    }
}
