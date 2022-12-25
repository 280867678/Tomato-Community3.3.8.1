package com.j256.ormlite.table;

import com.j256.ormlite.dao.AbstractC2183Dao;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.p075db.DatabaseType;
import com.j256.ormlite.stmt.StatementBuilder;
import com.j256.ormlite.support.CompiledStatement;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.support.DatabaseResults;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public class TableUtils {
    public static Logger logger = LoggerFactory.getLogger(TableUtils.class);
    public static final FieldType[] noFieldTypes = new FieldType[0];

    public static <T, ID> void addCreateIndexStatements(DatabaseType databaseType, TableInfo<T, ID> tableInfo, List<String> list, boolean z, boolean z2) {
        FieldType[] fieldTypes;
        HashMap hashMap = new HashMap();
        for (FieldType fieldType : tableInfo.getFieldTypes()) {
            String uniqueIndexName = z2 ? fieldType.getUniqueIndexName() : fieldType.getIndexName();
            if (uniqueIndexName != null) {
                List list2 = (List) hashMap.get(uniqueIndexName);
                if (list2 == null) {
                    list2 = new ArrayList();
                    hashMap.put(uniqueIndexName, list2);
                }
                list2.add(fieldType.getColumnName());
            }
        }
        StringBuilder sb = new StringBuilder(128);
        for (Map.Entry entry : hashMap.entrySet()) {
            logger.info("creating index '{}' for table '{}", entry.getKey(), tableInfo.getTableName());
            sb.append("CREATE ");
            if (z2) {
                sb.append("UNIQUE ");
            }
            sb.append("INDEX ");
            if (z && databaseType.isCreateIndexIfNotExistsSupported()) {
                sb.append("IF NOT EXISTS ");
            }
            databaseType.appendEscapedEntityName(sb, (String) entry.getKey());
            sb.append(" ON ");
            databaseType.appendEscapedEntityName(sb, tableInfo.getTableName());
            sb.append(" ( ");
            boolean z3 = true;
            for (String str : (List) entry.getValue()) {
                if (z3) {
                    z3 = false;
                } else {
                    sb.append(", ");
                }
                databaseType.appendEscapedEntityName(sb, str);
            }
            sb.append(" )");
            list.add(sb.toString());
            sb.setLength(0);
        }
    }

    public static <T, ID> List<String> addCreateTableStatements(ConnectionSource connectionSource, TableInfo<T, ID> tableInfo, boolean z) {
        ArrayList arrayList = new ArrayList();
        addCreateTableStatements(connectionSource.getDatabaseType(), tableInfo, arrayList, new ArrayList(), z);
        return arrayList;
    }

    public static <T, ID> void addCreateTableStatements(DatabaseType databaseType, TableInfo<T, ID> tableInfo, List<String> list, List<String> list2, boolean z) {
        boolean z2;
        int i;
        int i2;
        FieldType[] fieldTypeArr;
        StringBuilder sb = new StringBuilder(256);
        sb.append("CREATE TABLE ");
        if (z && databaseType.isCreateIfNotExistsSupported()) {
            sb.append("IF NOT EXISTS ");
        }
        databaseType.appendEscapedEntityName(sb, tableInfo.getTableName());
        sb.append(" (");
        ArrayList<String> arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        FieldType[] fieldTypes = tableInfo.getFieldTypes();
        int length = fieldTypes.length;
        boolean z3 = true;
        int i3 = 0;
        while (i3 < length) {
            FieldType fieldType = fieldTypes[i3];
            if (fieldType.isForeignCollection()) {
                i = i3;
                i2 = length;
                fieldTypeArr = fieldTypes;
            } else {
                if (z3) {
                    z2 = false;
                } else {
                    sb.append(", ");
                    z2 = z3;
                }
                String columnDefinition = fieldType.getColumnDefinition();
                if (columnDefinition == null) {
                    i = i3;
                    i2 = length;
                    fieldTypeArr = fieldTypes;
                    databaseType.appendColumnArg(tableInfo.getTableName(), sb, fieldType, arrayList, arrayList2, arrayList3, list2);
                } else {
                    i = i3;
                    i2 = length;
                    fieldTypeArr = fieldTypes;
                    databaseType.appendEscapedEntityName(sb, fieldType.getColumnName());
                    sb.append(' ');
                    sb.append(columnDefinition);
                    sb.append(' ');
                }
                z3 = z2;
            }
            i3 = i + 1;
            length = i2;
            fieldTypes = fieldTypeArr;
        }
        databaseType.addPrimaryKeySql(tableInfo.getFieldTypes(), arrayList, arrayList2, arrayList3, list2);
        databaseType.addUniqueComboSql(tableInfo.getFieldTypes(), arrayList, arrayList2, arrayList3, list2);
        for (String str : arrayList) {
            sb.append(", ");
            sb.append(str);
        }
        sb.append(") ");
        databaseType.appendCreateTableSuffix(sb);
        list.addAll(arrayList2);
        list.add(sb.toString());
        list.addAll(arrayList3);
        addCreateIndexStatements(databaseType, tableInfo, list, z, false);
        addCreateIndexStatements(databaseType, tableInfo, list, z, true);
    }

    public static <T, ID> void addDropIndexStatements(DatabaseType databaseType, TableInfo<T, ID> tableInfo, List<String> list) {
        FieldType[] fieldTypes;
        HashSet<String> hashSet = new HashSet();
        for (FieldType fieldType : tableInfo.getFieldTypes()) {
            String indexName = fieldType.getIndexName();
            if (indexName != null) {
                hashSet.add(indexName);
            }
            String uniqueIndexName = fieldType.getUniqueIndexName();
            if (uniqueIndexName != null) {
                hashSet.add(uniqueIndexName);
            }
        }
        StringBuilder sb = new StringBuilder(48);
        for (String str : hashSet) {
            logger.info("dropping index '{}' for table '{}", str, tableInfo.getTableName());
            sb.append("DROP INDEX ");
            databaseType.appendEscapedEntityName(sb, str);
            list.add(sb.toString());
            sb.setLength(0);
        }
    }

    public static <T, ID> void addDropTableStatements(DatabaseType databaseType, TableInfo<T, ID> tableInfo, List<String> list) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (FieldType fieldType : tableInfo.getFieldTypes()) {
            databaseType.dropColumnArg(fieldType, arrayList, arrayList2);
        }
        StringBuilder sb = new StringBuilder(64);
        sb.append("DROP TABLE ");
        databaseType.appendEscapedEntityName(sb, tableInfo.getTableName());
        sb.append(' ');
        list.addAll(arrayList);
        list.add(sb.toString());
        list.addAll(arrayList2);
    }

    public static <T> int clearTable(ConnectionSource connectionSource, DatabaseTableConfig<T> databaseTableConfig) {
        return clearTable(connectionSource, databaseTableConfig.getTableName());
    }

    public static <T> int clearTable(ConnectionSource connectionSource, Class<T> cls) {
        String extractTableName = DatabaseTableConfig.extractTableName(cls);
        if (connectionSource.getDatabaseType().isEntityNamesMustBeUpCase()) {
            extractTableName = extractTableName.toUpperCase();
        }
        return clearTable(connectionSource, extractTableName);
    }

    public static <T> int clearTable(ConnectionSource connectionSource, String str) {
        DatabaseType databaseType = connectionSource.getDatabaseType();
        StringBuilder sb = new StringBuilder(48);
        sb.append(databaseType.isTruncateSupported() ? "TRUNCATE TABLE " : "DELETE FROM ");
        databaseType.appendEscapedEntityName(sb, str);
        String sb2 = sb.toString();
        logger.info("clearing table '{}' with '{}", str, sb2);
        CompiledStatement compiledStatement = null;
        DatabaseConnection readWriteConnection = connectionSource.getReadWriteConnection();
        try {
            compiledStatement = readWriteConnection.compileStatement(sb2, StatementBuilder.StatementType.EXECUTE, noFieldTypes, -1);
            return compiledStatement.runExecute();
        } finally {
            if (compiledStatement != null) {
                compiledStatement.close();
            }
            connectionSource.releaseConnection(readWriteConnection);
        }
    }

    public static <T> int createTable(ConnectionSource connectionSource, DatabaseTableConfig<T> databaseTableConfig) {
        return createTable(connectionSource, (DatabaseTableConfig) databaseTableConfig, false);
    }

    public static <T, ID> int createTable(ConnectionSource connectionSource, DatabaseTableConfig<T> databaseTableConfig, boolean z) {
        AbstractC2183Dao createDao = DaoManager.createDao(connectionSource, databaseTableConfig);
        if (createDao instanceof BaseDaoImpl) {
            return doCreateTable(connectionSource, ((BaseDaoImpl) createDao).getTableInfo(), z);
        }
        databaseTableConfig.extractFieldTypes(connectionSource);
        return doCreateTable(connectionSource, new TableInfo(connectionSource.getDatabaseType(), (BaseDaoImpl) null, databaseTableConfig), z);
    }

    public static <T> int createTable(ConnectionSource connectionSource, Class<T> cls) {
        return createTable(connectionSource, (Class) cls, false);
    }

    public static <T, ID> int createTable(ConnectionSource connectionSource, Class<T> cls, boolean z) {
        AbstractC2183Dao createDao = DaoManager.createDao(connectionSource, cls);
        return createDao instanceof BaseDaoImpl ? doCreateTable(connectionSource, ((BaseDaoImpl) createDao).getTableInfo(), z) : doCreateTable(connectionSource, new TableInfo(connectionSource, (BaseDaoImpl) null, cls), z);
    }

    public static <T> int createTableIfNotExists(ConnectionSource connectionSource, DatabaseTableConfig<T> databaseTableConfig) {
        return createTable(connectionSource, (DatabaseTableConfig) databaseTableConfig, true);
    }

    public static <T> int createTableIfNotExists(ConnectionSource connectionSource, Class<T> cls) {
        return createTable(connectionSource, (Class) cls, true);
    }

    public static <T, ID> int doCreateTable(ConnectionSource connectionSource, TableInfo<T, ID> tableInfo, boolean z) {
        DatabaseType databaseType = connectionSource.getDatabaseType();
        logger.info("creating table '{}'", tableInfo.getTableName());
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        addCreateTableStatements(databaseType, tableInfo, arrayList, arrayList2, z);
        DatabaseConnection readWriteConnection = connectionSource.getReadWriteConnection();
        try {
            return doStatements(readWriteConnection, "create", arrayList, false, databaseType.isCreateTableReturnsNegative(), databaseType.isCreateTableReturnsZero()) + doCreateTestQueries(readWriteConnection, databaseType, arrayList2);
        } finally {
            connectionSource.releaseConnection(readWriteConnection);
        }
    }

    public static int doCreateTestQueries(DatabaseConnection databaseConnection, DatabaseType databaseType, List<String> list) {
        CompiledStatement compileStatement;
        int i = 0;
        for (String str : list) {
            CompiledStatement compiledStatement = null;
            try {
                try {
                    compileStatement = databaseConnection.compileStatement(str, StatementBuilder.StatementType.SELECT, noFieldTypes, -1);
                } catch (Throwable th) {
                    th = th;
                }
            } catch (SQLException e) {
                e = e;
            }
            try {
                DatabaseResults runQuery = compileStatement.runQuery(null);
                int i2 = 0;
                for (boolean first = runQuery.first(); first; first = runQuery.next()) {
                    i2++;
                }
                logger.info("executing create table after-query got {} results: {}", Integer.valueOf(i2), str);
                if (compileStatement != null) {
                    compileStatement.close();
                }
                i++;
            } catch (SQLException e2) {
                e = e2;
                compiledStatement = compileStatement;
                StringBuilder sb = new StringBuilder();
                sb.append("executing create table after-query failed: ");
                sb.append(str);
                throw SqlExceptionUtil.create(sb.toString(), e);
            } catch (Throwable th2) {
                th = th2;
                compiledStatement = compileStatement;
                if (compiledStatement != null) {
                    compiledStatement.close();
                }
                throw th;
            }
        }
        return i;
    }

    public static <T, ID> int doDropTable(DatabaseType databaseType, ConnectionSource connectionSource, TableInfo<T, ID> tableInfo, boolean z) {
        logger.info("dropping table '{}'", tableInfo.getTableName());
        ArrayList arrayList = new ArrayList();
        addDropIndexStatements(databaseType, tableInfo, arrayList);
        addDropTableStatements(databaseType, tableInfo, arrayList);
        DatabaseConnection readWriteConnection = connectionSource.getReadWriteConnection();
        try {
            return doStatements(readWriteConnection, "drop", arrayList, z, databaseType.isCreateTableReturnsNegative(), false);
        } finally {
            connectionSource.releaseConnection(readWriteConnection);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0049, code lost:
        if (r4 >= 0) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x004b, code lost:
        if (r12 == false) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0074, code lost:
        throw new java.sql.SQLException("SQL statement " + r2 + " updated " + r4 + " rows, we were expecting >= 0");
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x009b, code lost:
        r1 = r1 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0075, code lost:
        if (r4 <= 0) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0077, code lost:
        if (r13 != false) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x009a, code lost:
        throw new java.sql.SQLException("SQL statement updated " + r4 + " rows, we were expecting == 0: " + r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x009b, code lost:
        continue;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0046, code lost:
        if (r3 != null) goto L14;
     */
    /* JADX WARN: Removed duplicated region for block: B:33:0x003e A[Catch: all -> 0x0034, TRY_ENTER, TRY_LEAVE, TryCatch #0 {all -> 0x0034, blocks: (B:6:0x0013, B:8:0x001c, B:10:0x0020, B:12:0x0025, B:33:0x003e, B:35:0x0043, B:38:0x009f, B:40:0x00a7, B:41:0x00b5), top: B:5:0x0013 }] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x009f A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int doStatements(DatabaseConnection databaseConnection, String str, Collection<String> collection, boolean z, boolean z2, boolean z3) {
        SQLException e;
        int i;
        Iterator<String> it2 = collection.iterator();
        int i2 = 0;
        while (it2.hasNext()) {
            String next = it2.next();
            CompiledStatement compiledStatement = null;
            try {
                try {
                    compiledStatement = databaseConnection.compileStatement(next, StatementBuilder.StatementType.EXECUTE, noFieldTypes, -1);
                    try {
                        i = compiledStatement.runExecute();
                        try {
                            logger.info("executed {} table statement changed {} rows: {}", str, Integer.valueOf(i), next);
                        } catch (SQLException e2) {
                            e = e2;
                            if (z) {
                                StringBuilder sb = new StringBuilder();
                                sb.append("SQL statement failed: ");
                                sb.append(next);
                                throw SqlExceptionUtil.create(sb.toString(), e);
                            }
                            logger.info("ignoring {} error '{}' for statement: {}", str, e, next);
                        }
                    } catch (SQLException e3) {
                        e = e3;
                        i = 0;
                        if (z) {
                        }
                    }
                } finally {
                    if (compiledStatement != null) {
                        compiledStatement.close();
                    }
                }
            } catch (SQLException e4) {
                e = e4;
            }
        }
        return i2;
    }

    public static <T, ID> int dropTable(ConnectionSource connectionSource, DatabaseTableConfig<T> databaseTableConfig, boolean z) {
        DatabaseType databaseType = connectionSource.getDatabaseType();
        AbstractC2183Dao createDao = DaoManager.createDao(connectionSource, databaseTableConfig);
        if (createDao instanceof BaseDaoImpl) {
            return doDropTable(databaseType, connectionSource, ((BaseDaoImpl) createDao).getTableInfo(), z);
        }
        databaseTableConfig.extractFieldTypes(connectionSource);
        return doDropTable(databaseType, connectionSource, new TableInfo(databaseType, (BaseDaoImpl) null, databaseTableConfig), z);
    }

    public static <T, ID> int dropTable(ConnectionSource connectionSource, Class<T> cls, boolean z) {
        DatabaseType databaseType = connectionSource.getDatabaseType();
        AbstractC2183Dao createDao = DaoManager.createDao(connectionSource, cls);
        return createDao instanceof BaseDaoImpl ? doDropTable(databaseType, connectionSource, ((BaseDaoImpl) createDao).getTableInfo(), z) : doDropTable(databaseType, connectionSource, new TableInfo(connectionSource, (BaseDaoImpl) null, cls), z);
    }

    public static <T, ID> List<String> getCreateTableStatements(ConnectionSource connectionSource, DatabaseTableConfig<T> databaseTableConfig) {
        AbstractC2183Dao createDao = DaoManager.createDao(connectionSource, databaseTableConfig);
        if (createDao instanceof BaseDaoImpl) {
            return addCreateTableStatements(connectionSource, ((BaseDaoImpl) createDao).getTableInfo(), false);
        }
        databaseTableConfig.extractFieldTypes(connectionSource);
        return addCreateTableStatements(connectionSource, new TableInfo(connectionSource.getDatabaseType(), (BaseDaoImpl) null, databaseTableConfig), false);
    }

    public static <T, ID> List<String> getCreateTableStatements(ConnectionSource connectionSource, Class<T> cls) {
        AbstractC2183Dao createDao = DaoManager.createDao(connectionSource, cls);
        return createDao instanceof BaseDaoImpl ? addCreateTableStatements(connectionSource, ((BaseDaoImpl) createDao).getTableInfo(), false) : addCreateTableStatements(connectionSource, new TableInfo(connectionSource, (BaseDaoImpl) null, cls), false);
    }
}
