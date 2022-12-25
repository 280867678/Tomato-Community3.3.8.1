package com.j256.ormlite.dao;

import com.j256.ormlite.dao.AbstractC2183Dao;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.p075db.DatabaseType;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.GenericRowMapper;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.PreparedUpdate;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.stmt.StatementBuilder;
import com.j256.ormlite.stmt.StatementExecutor;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.support.DatabaseResults;
import com.j256.ormlite.table.DatabaseTableConfig;
import com.j256.ormlite.table.ObjectFactory;
import com.j256.ormlite.table.TableInfo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/* loaded from: classes3.dex */
public abstract class BaseDaoImpl<T, ID> implements AbstractC2183Dao<T, ID> {
    public static final ThreadLocal<List<BaseDaoImpl<?, ?>>> daoConfigLevelLocal = new ThreadLocal<List<BaseDaoImpl<?, ?>>>() { // from class: com.j256.ormlite.dao.BaseDaoImpl.1
        @Override // java.lang.ThreadLocal
        public List<BaseDaoImpl<?, ?>> initialValue() {
            return new ArrayList(10);
        }
    };
    public static ReferenceObjectCache defaultObjectCache;
    public ConnectionSource connectionSource;
    public final Class<T> dataClass;
    public DatabaseType databaseType;
    public boolean initialized;
    public CloseableIterator<T> lastIterator;
    public ObjectCache objectCache;
    public ObjectFactory<T> objectFactory;
    public StatementExecutor<T, ID> statementExecutor;
    public DatabaseTableConfig<T> tableConfig;
    public TableInfo<T, ID> tableInfo;

    public BaseDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig<T> databaseTableConfig) {
        this(connectionSource, databaseTableConfig.getDataClass(), databaseTableConfig);
    }

    public BaseDaoImpl(ConnectionSource connectionSource, Class<T> cls) {
        this(connectionSource, cls, null);
    }

    public BaseDaoImpl(ConnectionSource connectionSource, Class<T> cls, DatabaseTableConfig<T> databaseTableConfig) {
        this.dataClass = cls;
        this.tableConfig = databaseTableConfig;
        if (connectionSource != null) {
            this.connectionSource = connectionSource;
            initialize();
        }
    }

    public BaseDaoImpl(Class<T> cls) {
        this(null, cls, null);
    }

    public static synchronized void clearAllInternalObjectCaches() {
        synchronized (BaseDaoImpl.class) {
            ReferenceObjectCache referenceObjectCache = defaultObjectCache;
            if (referenceObjectCache != null) {
                referenceObjectCache.clearAll();
                defaultObjectCache = null;
            }
        }
    }

    public static <T, ID> AbstractC2183Dao<T, ID> createDao(ConnectionSource connectionSource, DatabaseTableConfig<T> databaseTableConfig) {
        return new BaseDaoImpl<T, ID>(connectionSource, databaseTableConfig) { // from class: com.j256.ormlite.dao.BaseDaoImpl.5
            @Override // com.j256.ormlite.dao.BaseDaoImpl, com.j256.ormlite.dao.AbstractC2183Dao, java.lang.Iterable
            /* renamed from: iterator */
            public /* bridge */ /* synthetic */ Iterator mo6325iterator() {
                return super.mo6325iterator();
            }
        };
    }

    public static <T, ID> AbstractC2183Dao<T, ID> createDao(ConnectionSource connectionSource, Class<T> cls) {
        return new BaseDaoImpl<T, ID>(connectionSource, cls) { // from class: com.j256.ormlite.dao.BaseDaoImpl.4
            @Override // com.j256.ormlite.dao.BaseDaoImpl, com.j256.ormlite.dao.AbstractC2183Dao, java.lang.Iterable
            /* renamed from: iterator */
            public /* bridge */ /* synthetic */ Iterator mo6325iterator() {
                return super.mo6325iterator();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CloseableIterator<T> createIterator(int i) {
        try {
            return this.statementExecutor.buildIterator(this, this.connectionSource, i, this.objectCache);
        } catch (Exception e) {
            throw new IllegalStateException("Could not build iterator for " + this.dataClass, e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CloseableIterator<T> createIterator(PreparedQuery<T> preparedQuery, int i) {
        try {
            return this.statementExecutor.buildIterator(this, this.connectionSource, preparedQuery, this.objectCache, i);
        } catch (SQLException e) {
            throw SqlExceptionUtil.create("Could not build prepared-query iterator for " + this.dataClass, e);
        }
    }

    private <FT> ForeignCollection<FT> makeEmptyForeignCollection(T t, String str) {
        FieldType[] fieldTypes;
        checkForInitialized();
        ID extractId = t == null ? null : extractId(t);
        for (FieldType fieldType : this.tableInfo.getFieldTypes()) {
            if (fieldType.getColumnName().equals(str)) {
                BaseForeignCollection buildForeignCollection = fieldType.buildForeignCollection(t, extractId);
                if (t != null) {
                    fieldType.assignField(t, buildForeignCollection, true, null);
                }
                return buildForeignCollection;
            }
        }
        throw new IllegalArgumentException("Could not find a field named " + str);
    }

    private List<T> queryForFieldValues(Map<String, Object> map, boolean z) {
        checkForInitialized();
        QueryBuilder<T, ID> queryBuilder = queryBuilder();
        Where<T, ID> where = queryBuilder.where();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Object value = entry.getValue();
            if (z) {
                value = new SelectArg(value);
            }
            where.m3918eq(entry.getKey(), value);
        }
        if (map.size() == 0) {
            return Collections.emptyList();
        }
        where.and(map.size());
        return queryBuilder.query();
    }

    private List<T> queryForMatching(T t, boolean z) {
        FieldType[] fieldTypes;
        checkForInitialized();
        QueryBuilder<T, ID> queryBuilder = queryBuilder();
        Where<T, ID> where = queryBuilder.where();
        int i = 0;
        for (FieldType fieldType : this.tableInfo.getFieldTypes()) {
            Object fieldValueIfNotDefault = fieldType.getFieldValueIfNotDefault(t);
            if (fieldValueIfNotDefault != null) {
                if (z) {
                    fieldValueIfNotDefault = new SelectArg(fieldValueIfNotDefault);
                }
                where.m3918eq(fieldType.getColumnName(), fieldValueIfNotDefault);
                i++;
            }
        }
        if (i == 0) {
            return Collections.emptyList();
        }
        where.and(i);
        return queryBuilder.query();
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public void assignEmptyForeignCollection(T t, String str) {
        makeEmptyForeignCollection(t, str);
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public <CT> CT callBatchTasks(Callable<CT> callable) {
        checkForInitialized();
        DatabaseConnection readWriteConnection = this.connectionSource.getReadWriteConnection();
        try {
            return (CT) this.statementExecutor.callBatchTasks(readWriteConnection, this.connectionSource.saveSpecialConnection(readWriteConnection), callable);
        } finally {
            this.connectionSource.clearSpecialConnection(readWriteConnection);
            this.connectionSource.releaseConnection(readWriteConnection);
        }
    }

    public void checkForInitialized() {
        if (this.initialized) {
            return;
        }
        throw new IllegalStateException("you must call initialize() before you can use the dao");
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public void clearObjectCache() {
        ObjectCache objectCache = this.objectCache;
        if (objectCache != null) {
            objectCache.clear(this.dataClass);
        }
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public void closeLastIterator() {
        CloseableIterator<T> closeableIterator = this.lastIterator;
        if (closeableIterator != null) {
            closeableIterator.close();
            this.lastIterator = null;
        }
    }

    @Override // com.j256.ormlite.dao.CloseableIterable
    public CloseableIterator<T> closeableIterator() {
        return iterator(-1);
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public void commit(DatabaseConnection databaseConnection) {
        databaseConnection.commit(null);
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public long countOf() {
        checkForInitialized();
        DatabaseConnection readOnlyConnection = this.connectionSource.getReadOnlyConnection();
        try {
            return this.statementExecutor.queryForCountStar(readOnlyConnection);
        } finally {
            this.connectionSource.releaseConnection(readOnlyConnection);
        }
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public long countOf(PreparedQuery<T> preparedQuery) {
        checkForInitialized();
        if (preparedQuery.getType() == StatementBuilder.StatementType.SELECT_LONG) {
            DatabaseConnection readOnlyConnection = this.connectionSource.getReadOnlyConnection();
            try {
                return this.statementExecutor.queryForLong(readOnlyConnection, preparedQuery);
            } finally {
                this.connectionSource.releaseConnection(readOnlyConnection);
            }
        }
        throw new IllegalArgumentException("Prepared query is not of type " + StatementBuilder.StatementType.SELECT_LONG + ", did you call QueryBuilder.setCountOf(true)?");
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public int create(T t) {
        checkForInitialized();
        if (t == null) {
            return 0;
        }
        if (t instanceof BaseDaoEnabled) {
            ((BaseDaoEnabled) t).setDao(this);
        }
        DatabaseConnection readWriteConnection = this.connectionSource.getReadWriteConnection();
        try {
            return this.statementExecutor.create(readWriteConnection, t, this.objectCache);
        } finally {
            this.connectionSource.releaseConnection(readWriteConnection);
        }
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public T createIfNotExists(T t) {
        if (t == null) {
            return null;
        }
        T queryForSameId = queryForSameId(t);
        if (queryForSameId != null) {
            return queryForSameId;
        }
        create(t);
        return t;
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public AbstractC2183Dao.CreateOrUpdateStatus createOrUpdate(T t) {
        if (t == null) {
            return new AbstractC2183Dao.CreateOrUpdateStatus(false, false, 0);
        }
        ID extractId = extractId(t);
        return (extractId == null || !idExists(extractId)) ? new AbstractC2183Dao.CreateOrUpdateStatus(true, false, create(t)) : new AbstractC2183Dao.CreateOrUpdateStatus(false, true, update((BaseDaoImpl<T, ID>) t));
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public int delete(PreparedDelete<T> preparedDelete) {
        checkForInitialized();
        DatabaseConnection readWriteConnection = this.connectionSource.getReadWriteConnection();
        try {
            return this.statementExecutor.delete(readWriteConnection, preparedDelete);
        } finally {
            this.connectionSource.releaseConnection(readWriteConnection);
        }
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public int delete(T t) {
        checkForInitialized();
        if (t == null) {
            return 0;
        }
        DatabaseConnection readWriteConnection = this.connectionSource.getReadWriteConnection();
        try {
            return this.statementExecutor.delete(readWriteConnection, t, this.objectCache);
        } finally {
            this.connectionSource.releaseConnection(readWriteConnection);
        }
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public int delete(Collection<T> collection) {
        checkForInitialized();
        if (collection == null || collection.isEmpty()) {
            return 0;
        }
        DatabaseConnection readWriteConnection = this.connectionSource.getReadWriteConnection();
        try {
            return this.statementExecutor.deleteObjects(readWriteConnection, collection, this.objectCache);
        } finally {
            this.connectionSource.releaseConnection(readWriteConnection);
        }
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public DeleteBuilder<T, ID> deleteBuilder() {
        checkForInitialized();
        return new DeleteBuilder<>(this.databaseType, this.tableInfo, this);
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public int deleteById(ID id) {
        checkForInitialized();
        if (id == null) {
            return 0;
        }
        DatabaseConnection readWriteConnection = this.connectionSource.getReadWriteConnection();
        try {
            return this.statementExecutor.deleteById(readWriteConnection, id, this.objectCache);
        } finally {
            this.connectionSource.releaseConnection(readWriteConnection);
        }
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public int deleteIds(Collection<ID> collection) {
        checkForInitialized();
        if (collection == null || collection.isEmpty()) {
            return 0;
        }
        DatabaseConnection readWriteConnection = this.connectionSource.getReadWriteConnection();
        try {
            return this.statementExecutor.deleteIds(readWriteConnection, collection, this.objectCache);
        } finally {
            this.connectionSource.releaseConnection(readWriteConnection);
        }
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public void endThreadConnection(DatabaseConnection databaseConnection) {
        this.connectionSource.clearSpecialConnection(databaseConnection);
        this.connectionSource.releaseConnection(databaseConnection);
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public int executeRaw(String str, String... strArr) {
        checkForInitialized();
        DatabaseConnection readWriteConnection = this.connectionSource.getReadWriteConnection();
        try {
            try {
                return this.statementExecutor.executeRaw(readWriteConnection, str, strArr);
            } catch (SQLException e) {
                StringBuilder sb = new StringBuilder();
                sb.append("Could not run raw execute statement ");
                sb.append(str);
                throw SqlExceptionUtil.create(sb.toString(), e);
            }
        } finally {
            this.connectionSource.releaseConnection(readWriteConnection);
        }
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public int executeRawNoArgs(String str) {
        checkForInitialized();
        DatabaseConnection readWriteConnection = this.connectionSource.getReadWriteConnection();
        try {
            try {
                return this.statementExecutor.executeRawNoArgs(readWriteConnection, str);
            } catch (SQLException e) {
                StringBuilder sb = new StringBuilder();
                sb.append("Could not run raw execute statement ");
                sb.append(str);
                throw SqlExceptionUtil.create(sb.toString(), e);
            }
        } finally {
            this.connectionSource.releaseConnection(readWriteConnection);
        }
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public ID extractId(T t) {
        checkForInitialized();
        FieldType idField = this.tableInfo.getIdField();
        if (idField != null) {
            return (ID) idField.extractJavaFieldValue(t);
        }
        throw new SQLException("Class " + this.dataClass + " does not have an id field");
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public FieldType findForeignFieldType(Class<?> cls) {
        FieldType[] fieldTypes;
        checkForInitialized();
        for (FieldType fieldType : this.tableInfo.getFieldTypes()) {
            if (fieldType.getType() == cls) {
                return fieldType;
            }
        }
        return null;
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public ConnectionSource getConnectionSource() {
        return this.connectionSource;
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public Class<T> getDataClass() {
        return this.dataClass;
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public <FT> ForeignCollection<FT> getEmptyForeignCollection(String str) {
        return makeEmptyForeignCollection(null, str);
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public ObjectCache getObjectCache() {
        return this.objectCache;
    }

    public ObjectFactory<T> getObjectFactory() {
        return this.objectFactory;
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public RawRowMapper<T> getRawRowMapper() {
        return this.statementExecutor.getRawRowMapper();
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public GenericRowMapper<T> getSelectStarRowMapper() {
        return this.statementExecutor.getSelectStarRowMapper();
    }

    public DatabaseTableConfig<T> getTableConfig() {
        return this.tableConfig;
    }

    public TableInfo<T, ID> getTableInfo() {
        return this.tableInfo;
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public CloseableWrappedIterable<T> getWrappedIterable() {
        checkForInitialized();
        return new CloseableWrappedIterableImpl(new CloseableIterable<T>() { // from class: com.j256.ormlite.dao.BaseDaoImpl.2
            @Override // com.j256.ormlite.dao.CloseableIterable
            public CloseableIterator<T> closeableIterator() {
                try {
                    return BaseDaoImpl.this.createIterator(-1);
                } catch (Exception e) {
                    throw new IllegalStateException("Could not build iterator for " + BaseDaoImpl.this.dataClass, e);
                }
            }

            @Override // java.lang.Iterable
            public Iterator<T> iterator() {
                return closeableIterator();
            }
        });
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public CloseableWrappedIterable<T> getWrappedIterable(final PreparedQuery<T> preparedQuery) {
        checkForInitialized();
        return new CloseableWrappedIterableImpl(new CloseableIterable<T>() { // from class: com.j256.ormlite.dao.BaseDaoImpl.3
            @Override // com.j256.ormlite.dao.CloseableIterable
            public CloseableIterator<T> closeableIterator() {
                try {
                    return BaseDaoImpl.this.createIterator(preparedQuery, -1);
                } catch (Exception e) {
                    throw new IllegalStateException("Could not build prepared-query iterator for " + BaseDaoImpl.this.dataClass, e);
                }
            }

            @Override // java.lang.Iterable
            public Iterator<T> iterator() {
                return closeableIterator();
            }
        });
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public boolean idExists(ID id) {
        DatabaseConnection readOnlyConnection = this.connectionSource.getReadOnlyConnection();
        try {
            return this.statementExecutor.ifExists(readOnlyConnection, id);
        } finally {
            this.connectionSource.releaseConnection(readOnlyConnection);
        }
    }

    public void initialize() {
        TableInfo<T, ID> tableInfo;
        if (this.initialized) {
            return;
        }
        ConnectionSource connectionSource = this.connectionSource;
        if (connectionSource == null) {
            throw new IllegalStateException("connectionSource was never set on " + getClass().getSimpleName());
        }
        this.databaseType = connectionSource.getDatabaseType();
        if (this.databaseType == null) {
            throw new IllegalStateException("connectionSource is getting a null DatabaseType in " + getClass().getSimpleName());
        }
        DatabaseTableConfig<T> databaseTableConfig = this.tableConfig;
        if (databaseTableConfig == null) {
            tableInfo = new TableInfo<>(this.connectionSource, this, this.dataClass);
        } else {
            databaseTableConfig.extractFieldTypes(this.connectionSource);
            tableInfo = new TableInfo<>(this.databaseType, this, this.tableConfig);
        }
        this.tableInfo = tableInfo;
        this.statementExecutor = new StatementExecutor<>(this.databaseType, this.tableInfo, this);
        List<BaseDaoImpl<?, ?>> list = daoConfigLevelLocal.get();
        list.add(this);
        if (list.size() > 1) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            try {
                BaseDaoImpl<?, ?> baseDaoImpl = list.get(i);
                DaoManager.registerDao(this.connectionSource, baseDaoImpl);
                try {
                    for (FieldType fieldType : baseDaoImpl.getTableInfo().getFieldTypes()) {
                        fieldType.configDaoInformation(this.connectionSource, baseDaoImpl.getDataClass());
                    }
                    baseDaoImpl.initialized = true;
                } catch (SQLException e) {
                    DaoManager.unregisterDao(this.connectionSource, baseDaoImpl);
                    throw e;
                }
            } finally {
                list.clear();
                daoConfigLevelLocal.remove();
            }
        }
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public boolean isAutoCommit() {
        DatabaseConnection readWriteConnection = this.connectionSource.getReadWriteConnection();
        try {
            return isAutoCommit(readWriteConnection);
        } finally {
            this.connectionSource.releaseConnection(readWriteConnection);
        }
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public boolean isAutoCommit(DatabaseConnection databaseConnection) {
        return databaseConnection.isAutoCommit();
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public boolean isTableExists() {
        checkForInitialized();
        DatabaseConnection readOnlyConnection = this.connectionSource.getReadOnlyConnection();
        try {
            return readOnlyConnection.isTableExists(this.tableInfo.getTableName());
        } finally {
            this.connectionSource.releaseConnection(readOnlyConnection);
        }
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public boolean isUpdatable() {
        return this.tableInfo.isUpdatable();
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao, java.lang.Iterable
    /* renamed from: iterator */
    public CloseableIterator<T> mo6325iterator() {
        return iterator(-1);
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public CloseableIterator<T> iterator(int i) {
        checkForInitialized();
        this.lastIterator = createIterator(i);
        return this.lastIterator;
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public CloseableIterator<T> iterator(PreparedQuery<T> preparedQuery) {
        return iterator(preparedQuery, -1);
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public CloseableIterator<T> iterator(PreparedQuery<T> preparedQuery, int i) {
        checkForInitialized();
        this.lastIterator = createIterator(preparedQuery, i);
        return this.lastIterator;
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public T mapSelectStarRow(DatabaseResults databaseResults) {
        return this.statementExecutor.getSelectStarRowMapper().mapRow(databaseResults);
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public String objectToString(T t) {
        checkForInitialized();
        return this.tableInfo.objectToString(t);
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public boolean objectsEqual(T t, T t2) {
        FieldType[] fieldTypes;
        checkForInitialized();
        for (FieldType fieldType : this.tableInfo.getFieldTypes()) {
            if (!fieldType.getDataPersister().dataIsEqual(fieldType.extractJavaFieldValue(t), fieldType.extractJavaFieldValue(t2))) {
                return false;
            }
        }
        return true;
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public List<T> query(PreparedQuery<T> preparedQuery) {
        checkForInitialized();
        return this.statementExecutor.query(this.connectionSource, preparedQuery, this.objectCache);
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public QueryBuilder<T, ID> queryBuilder() {
        checkForInitialized();
        return new QueryBuilder<>(this.databaseType, this.tableInfo, this);
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public List<T> queryForAll() {
        checkForInitialized();
        return this.statementExecutor.queryForAll(this.connectionSource, this.objectCache);
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public List<T> queryForEq(String str, Object obj) {
        return queryBuilder().where().m3918eq(str, obj).query();
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public List<T> queryForFieldValues(Map<String, Object> map) {
        return queryForFieldValues(map, false);
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public List<T> queryForFieldValuesArgs(Map<String, Object> map) {
        return queryForFieldValues(map, true);
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public T queryForFirst(PreparedQuery<T> preparedQuery) {
        checkForInitialized();
        DatabaseConnection readOnlyConnection = this.connectionSource.getReadOnlyConnection();
        try {
            return this.statementExecutor.queryForFirst(readOnlyConnection, preparedQuery, this.objectCache);
        } finally {
            this.connectionSource.releaseConnection(readOnlyConnection);
        }
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public T queryForId(ID id) {
        checkForInitialized();
        DatabaseConnection readOnlyConnection = this.connectionSource.getReadOnlyConnection();
        try {
            return this.statementExecutor.queryForId(readOnlyConnection, id, this.objectCache);
        } finally {
            this.connectionSource.releaseConnection(readOnlyConnection);
        }
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public List<T> queryForMatching(T t) {
        return queryForMatching(t, false);
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public List<T> queryForMatchingArgs(T t) {
        return queryForMatching(t, true);
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public T queryForSameId(T t) {
        ID extractId;
        checkForInitialized();
        if (t == null || (extractId = extractId(t)) == null) {
            return null;
        }
        return queryForId(extractId);
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public <GR> GenericRawResults<GR> queryRaw(String str, RawRowMapper<GR> rawRowMapper, String... strArr) {
        checkForInitialized();
        try {
            return (GenericRawResults<GR>) this.statementExecutor.queryRaw(this.connectionSource, str, rawRowMapper, strArr, this.objectCache);
        } catch (SQLException e) {
            throw SqlExceptionUtil.create("Could not perform raw query for " + str, e);
        }
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public <UO> GenericRawResults<UO> queryRaw(String str, DataType[] dataTypeArr, RawRowObjectMapper<UO> rawRowObjectMapper, String... strArr) {
        checkForInitialized();
        try {
            return this.statementExecutor.queryRaw(this.connectionSource, str, dataTypeArr, rawRowObjectMapper, strArr, this.objectCache);
        } catch (SQLException e) {
            throw SqlExceptionUtil.create("Could not perform raw query for " + str, e);
        }
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public GenericRawResults<Object[]> queryRaw(String str, DataType[] dataTypeArr, String... strArr) {
        checkForInitialized();
        try {
            return this.statementExecutor.queryRaw(this.connectionSource, str, dataTypeArr, strArr, this.objectCache);
        } catch (SQLException e) {
            throw SqlExceptionUtil.create("Could not perform raw query for " + str, e);
        }
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public GenericRawResults<String[]> queryRaw(String str, String... strArr) {
        checkForInitialized();
        try {
            return this.statementExecutor.queryRaw(this.connectionSource, str, strArr, this.objectCache);
        } catch (SQLException e) {
            throw SqlExceptionUtil.create("Could not perform raw query for " + str, e);
        }
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public long queryRawValue(String str, String... strArr) {
        checkForInitialized();
        DatabaseConnection readOnlyConnection = this.connectionSource.getReadOnlyConnection();
        try {
            try {
                return this.statementExecutor.queryForLong(readOnlyConnection, str, strArr);
            } catch (SQLException e) {
                StringBuilder sb = new StringBuilder();
                sb.append("Could not perform raw value query for ");
                sb.append(str);
                throw SqlExceptionUtil.create(sb.toString(), e);
            }
        } finally {
            this.connectionSource.releaseConnection(readOnlyConnection);
        }
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public int refresh(T t) {
        checkForInitialized();
        if (t == null) {
            return 0;
        }
        if (t instanceof BaseDaoEnabled) {
            ((BaseDaoEnabled) t).setDao(this);
        }
        DatabaseConnection readOnlyConnection = this.connectionSource.getReadOnlyConnection();
        try {
            return this.statementExecutor.refresh(readOnlyConnection, t, this.objectCache);
        } finally {
            this.connectionSource.releaseConnection(readOnlyConnection);
        }
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public void rollBack(DatabaseConnection databaseConnection) {
        databaseConnection.rollback(null);
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public void setAutoCommit(DatabaseConnection databaseConnection, boolean z) {
        databaseConnection.setAutoCommit(z);
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public void setAutoCommit(boolean z) {
        DatabaseConnection readWriteConnection = this.connectionSource.getReadWriteConnection();
        try {
            setAutoCommit(readWriteConnection, z);
        } finally {
            this.connectionSource.releaseConnection(readWriteConnection);
        }
    }

    public void setConnectionSource(ConnectionSource connectionSource) {
        this.connectionSource = connectionSource;
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public void setObjectCache(ObjectCache objectCache) {
        if (objectCache == null) {
            ObjectCache objectCache2 = this.objectCache;
            if (objectCache2 == null) {
                return;
            }
            objectCache2.clear(this.dataClass);
            this.objectCache = null;
            return;
        }
        ObjectCache objectCache3 = this.objectCache;
        if (objectCache3 != null && objectCache3 != objectCache) {
            objectCache3.clear(this.dataClass);
        }
        if (this.tableInfo.getIdField() != null) {
            this.objectCache = objectCache;
            this.objectCache.registerClass(this.dataClass);
            return;
        }
        throw new SQLException("Class " + this.dataClass + " must have an id field to enable the object cache");
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public void setObjectCache(boolean z) {
        if (!z) {
            ObjectCache objectCache = this.objectCache;
            if (objectCache == null) {
                return;
            }
            objectCache.clear(this.dataClass);
            this.objectCache = null;
        } else if (this.objectCache != null) {
        } else {
            if (this.tableInfo.getIdField() == null) {
                throw new SQLException("Class " + this.dataClass + " must have an id field to enable the object cache");
            }
            synchronized (BaseDaoImpl.class) {
                if (defaultObjectCache == null) {
                    defaultObjectCache = ReferenceObjectCache.makeWeakCache();
                }
                this.objectCache = defaultObjectCache;
            }
            this.objectCache.registerClass(this.dataClass);
        }
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public void setObjectFactory(ObjectFactory<T> objectFactory) {
        checkForInitialized();
        this.objectFactory = objectFactory;
    }

    public void setTableConfig(DatabaseTableConfig<T> databaseTableConfig) {
        this.tableConfig = databaseTableConfig;
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public DatabaseConnection startThreadConnection() {
        DatabaseConnection readWriteConnection = this.connectionSource.getReadWriteConnection();
        this.connectionSource.saveSpecialConnection(readWriteConnection);
        return readWriteConnection;
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public int update(PreparedUpdate<T> preparedUpdate) {
        checkForInitialized();
        DatabaseConnection readWriteConnection = this.connectionSource.getReadWriteConnection();
        try {
            return this.statementExecutor.update(readWriteConnection, preparedUpdate);
        } finally {
            this.connectionSource.releaseConnection(readWriteConnection);
        }
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public int update(T t) {
        checkForInitialized();
        if (t == null) {
            return 0;
        }
        DatabaseConnection readWriteConnection = this.connectionSource.getReadWriteConnection();
        try {
            return this.statementExecutor.update(readWriteConnection, t, this.objectCache);
        } finally {
            this.connectionSource.releaseConnection(readWriteConnection);
        }
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public UpdateBuilder<T, ID> updateBuilder() {
        checkForInitialized();
        return new UpdateBuilder<>(this.databaseType, this.tableInfo, this);
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public int updateId(T t, ID id) {
        checkForInitialized();
        if (t == null) {
            return 0;
        }
        DatabaseConnection readWriteConnection = this.connectionSource.getReadWriteConnection();
        try {
            return this.statementExecutor.updateId(readWriteConnection, t, id, this.objectCache);
        } finally {
            this.connectionSource.releaseConnection(readWriteConnection);
        }
    }

    @Override // com.j256.ormlite.dao.AbstractC2183Dao
    public int updateRaw(String str, String... strArr) {
        checkForInitialized();
        DatabaseConnection readWriteConnection = this.connectionSource.getReadWriteConnection();
        try {
            try {
                return this.statementExecutor.updateRaw(readWriteConnection, str, strArr);
            } catch (SQLException e) {
                StringBuilder sb = new StringBuilder();
                sb.append("Could not run raw update statement ");
                sb.append(str);
                throw SqlExceptionUtil.create(sb.toString(), e);
            }
        } finally {
            this.connectionSource.releaseConnection(readWriteConnection);
        }
    }
}
