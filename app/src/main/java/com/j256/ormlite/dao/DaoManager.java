package com.j256.ormlite.dao;

import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.DatabaseTableConfig;
import java.lang.reflect.Constructor;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class DaoManager {
    public static Map<ClassConnectionSource, AbstractC2183Dao<?, ?>> classMap;
    public static Map<Class<?>, DatabaseTableConfig<?>> configMap;
    public static Logger logger = LoggerFactory.getLogger(DaoManager.class);
    public static Map<TableConfigConnectionSource, AbstractC2183Dao<?, ?>> tableConfigMap;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class ClassConnectionSource {
        public Class<?> clazz;
        public ConnectionSource connectionSource;

        public ClassConnectionSource(ConnectionSource connectionSource, Class<?> cls) {
            this.connectionSource = connectionSource;
            this.clazz = cls;
        }

        public boolean equals(Object obj) {
            if (obj == null || ClassConnectionSource.class != obj.getClass()) {
                return false;
            }
            ClassConnectionSource classConnectionSource = (ClassConnectionSource) obj;
            return this.clazz.equals(classConnectionSource.clazz) && this.connectionSource.equals(classConnectionSource.connectionSource);
        }

        public int hashCode() {
            return ((this.clazz.hashCode() + 31) * 31) + this.connectionSource.hashCode();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class TableConfigConnectionSource {
        public ConnectionSource connectionSource;
        public DatabaseTableConfig<?> tableConfig;

        public TableConfigConnectionSource(ConnectionSource connectionSource, DatabaseTableConfig<?> databaseTableConfig) {
            this.connectionSource = connectionSource;
            this.tableConfig = databaseTableConfig;
        }

        public boolean equals(Object obj) {
            if (obj == null || TableConfigConnectionSource.class != obj.getClass()) {
                return false;
            }
            TableConfigConnectionSource tableConfigConnectionSource = (TableConfigConnectionSource) obj;
            return this.tableConfig.equals(tableConfigConnectionSource.tableConfig) && this.connectionSource.equals(tableConfigConnectionSource.connectionSource);
        }

        public int hashCode() {
            return ((this.tableConfig.hashCode() + 31) * 31) + this.connectionSource.hashCode();
        }
    }

    public static synchronized void addCachedDatabaseConfigs(Collection<DatabaseTableConfig<?>> collection) {
        synchronized (DaoManager.class) {
            Map<Class<?>, DatabaseTableConfig<?>> map = configMap;
            HashMap hashMap = map == null ? new HashMap() : new HashMap(map);
            for (DatabaseTableConfig<?> databaseTableConfig : collection) {
                hashMap.put(databaseTableConfig.getDataClass(), databaseTableConfig);
                logger.info("Loaded configuration for {}", databaseTableConfig.getDataClass());
            }
            configMap = hashMap;
        }
    }

    public static void addDaoToClassMap(ClassConnectionSource classConnectionSource, AbstractC2183Dao<?, ?> abstractC2183Dao) {
        if (classMap == null) {
            classMap = new HashMap();
        }
        classMap.put(classConnectionSource, abstractC2183Dao);
    }

    public static void addDaoToTableMap(TableConfigConnectionSource tableConfigConnectionSource, AbstractC2183Dao<?, ?> abstractC2183Dao) {
        if (tableConfigMap == null) {
            tableConfigMap = new HashMap();
        }
        tableConfigMap.put(tableConfigConnectionSource, abstractC2183Dao);
    }

    public static synchronized void clearCache() {
        synchronized (DaoManager.class) {
            Map<Class<?>, DatabaseTableConfig<?>> map = configMap;
            if (map != null) {
                map.clear();
                configMap = null;
            }
            clearDaoCache();
        }
    }

    public static synchronized void clearDaoCache() {
        synchronized (DaoManager.class) {
            Map<ClassConnectionSource, AbstractC2183Dao<?, ?>> map = classMap;
            if (map != null) {
                map.clear();
                classMap = null;
            }
            Map<TableConfigConnectionSource, AbstractC2183Dao<?, ?>> map2 = tableConfigMap;
            if (map2 != null) {
                map2.clear();
                tableConfigMap = null;
            }
        }
    }

    public static synchronized <D extends AbstractC2183Dao<T, ?>, T> D createDao(ConnectionSource connectionSource, DatabaseTableConfig<T> databaseTableConfig) {
        D d;
        synchronized (DaoManager.class) {
            if (connectionSource == null) {
                throw new IllegalArgumentException("connectionSource argument cannot be null");
            }
            d = (D) doCreateDao(connectionSource, databaseTableConfig);
        }
        return d;
    }

    public static synchronized <D extends AbstractC2183Dao<T, ?>, T> D createDao(ConnectionSource connectionSource, Class<T> cls) {
        D d;
        synchronized (DaoManager.class) {
            if (connectionSource != null) {
                D d2 = (D) lookupDao(new ClassConnectionSource(connectionSource, cls));
                if (d2 != null) {
                    return d2;
                }
                D d3 = (D) createDaoFromConfig(connectionSource, cls);
                if (d3 != null) {
                    return d3;
                }
                DatabaseTable databaseTable = (DatabaseTable) cls.getAnnotation(DatabaseTable.class);
                if (databaseTable != null && databaseTable.daoClass() != Void.class && databaseTable.daoClass() != BaseDaoImpl.class) {
                    Class<?> daoClass = databaseTable.daoClass();
                    Object[] objArr = {connectionSource, cls};
                    Constructor<?> findConstructor = findConstructor(daoClass, objArr);
                    if (findConstructor == null && (findConstructor = findConstructor(daoClass, (objArr = new Object[]{connectionSource}))) == null) {
                        throw new SQLException("Could not find public constructor with ConnectionSource and optional Class parameters " + daoClass + ".  Missing static on class?");
                    }
                    try {
                        d = (D) findConstructor.newInstance(objArr);
                        logger.debug("created dao for class {} from constructor", cls);
                        registerDao(connectionSource, d);
                        return d;
                    } catch (Exception e) {
                        throw SqlExceptionUtil.create("Could not call the constructor in class " + daoClass, e);
                    }
                }
                DatabaseTableConfig<T> extractDatabaseTableConfig = connectionSource.getDatabaseType().extractDatabaseTableConfig(connectionSource, cls);
                d = (D) (extractDatabaseTableConfig == null ? BaseDaoImpl.createDao(connectionSource, cls) : BaseDaoImpl.createDao(connectionSource, extractDatabaseTableConfig));
                logger.debug("created dao for class {} with reflection", cls);
                registerDao(connectionSource, d);
                return d;
            }
            throw new IllegalArgumentException("connectionSource argument cannot be null");
        }
    }

    public static <D, T> D createDaoFromConfig(ConnectionSource connectionSource, Class<T> cls) {
        DatabaseTableConfig<?> databaseTableConfig;
        Map<Class<?>, DatabaseTableConfig<?>> map = configMap;
        if (map == null || (databaseTableConfig = map.get(cls)) == null) {
            return null;
        }
        return (D) doCreateDao(connectionSource, databaseTableConfig);
    }

    public static <D extends AbstractC2183Dao<T, ?>, T> D doCreateDao(ConnectionSource connectionSource, DatabaseTableConfig<T> databaseTableConfig) {
        D d;
        TableConfigConnectionSource tableConfigConnectionSource = new TableConfigConnectionSource(connectionSource, databaseTableConfig);
        D d2 = (D) lookupDao(tableConfigConnectionSource);
        if (d2 != null) {
            return d2;
        }
        Class<T> dataClass = databaseTableConfig.getDataClass();
        ClassConnectionSource classConnectionSource = new ClassConnectionSource(connectionSource, dataClass);
        D d3 = (D) lookupDao(classConnectionSource);
        if (d3 != null) {
            addDaoToTableMap(tableConfigConnectionSource, d3);
            return d3;
        }
        DatabaseTable databaseTable = (DatabaseTable) databaseTableConfig.getDataClass().getAnnotation(DatabaseTable.class);
        if (databaseTable == null || databaseTable.daoClass() == Void.class || databaseTable.daoClass() == BaseDaoImpl.class) {
            d = (D) BaseDaoImpl.createDao(connectionSource, databaseTableConfig);
        } else {
            Class<?> daoClass = databaseTable.daoClass();
            Object[] objArr = {connectionSource, databaseTableConfig};
            Constructor<?> findConstructor = findConstructor(daoClass, objArr);
            if (findConstructor == null) {
                throw new SQLException("Could not find public constructor with ConnectionSource, DatabaseTableConfig parameters in class " + daoClass);
            }
            try {
                d = (D) findConstructor.newInstance(objArr);
            } catch (Exception e) {
                throw SqlExceptionUtil.create("Could not call the constructor in class " + daoClass, e);
            }
        }
        addDaoToTableMap(tableConfigConnectionSource, d);
        logger.debug("created dao for class {} from table config", dataClass);
        if (lookupDao(classConnectionSource) == null) {
            addDaoToClassMap(classConnectionSource, d);
        }
        return d;
    }

    public static Constructor<?> findConstructor(Class<?> cls, Object[] objArr) {
        Constructor<?>[] constructors;
        boolean z;
        for (Constructor<?> constructor : cls.getConstructors()) {
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            if (parameterTypes.length == objArr.length) {
                int i = 0;
                while (true) {
                    if (i >= parameterTypes.length) {
                        z = true;
                        break;
                    } else if (!parameterTypes[i].isAssignableFrom(objArr[i].getClass())) {
                        z = false;
                        break;
                    } else {
                        i++;
                    }
                }
                if (z) {
                    return constructor;
                }
            }
        }
        return null;
    }

    public static <T> AbstractC2183Dao<?, ?> lookupDao(ClassConnectionSource classConnectionSource) {
        if (classMap == null) {
            classMap = new HashMap();
        }
        AbstractC2183Dao<?, ?> abstractC2183Dao = classMap.get(classConnectionSource);
        if (abstractC2183Dao == null) {
            return null;
        }
        return abstractC2183Dao;
    }

    public static <T> AbstractC2183Dao<?, ?> lookupDao(TableConfigConnectionSource tableConfigConnectionSource) {
        if (tableConfigMap == null) {
            tableConfigMap = new HashMap();
        }
        AbstractC2183Dao<?, ?> abstractC2183Dao = tableConfigMap.get(tableConfigConnectionSource);
        if (abstractC2183Dao == null) {
            return null;
        }
        return abstractC2183Dao;
    }

    public static synchronized <D extends AbstractC2183Dao<T, ?>, T> D lookupDao(ConnectionSource connectionSource, DatabaseTableConfig<T> databaseTableConfig) {
        synchronized (DaoManager.class) {
            if (connectionSource != null) {
                D d = (D) lookupDao(new TableConfigConnectionSource(connectionSource, databaseTableConfig));
                if (d != null) {
                    return d;
                }
                return null;
            }
            throw new IllegalArgumentException("connectionSource argument cannot be null");
        }
    }

    public static synchronized <D extends AbstractC2183Dao<T, ?>, T> D lookupDao(ConnectionSource connectionSource, Class<T> cls) {
        D d;
        synchronized (DaoManager.class) {
            if (connectionSource == null) {
                throw new IllegalArgumentException("connectionSource argument cannot be null");
            }
            d = (D) lookupDao(new ClassConnectionSource(connectionSource, cls));
        }
        return d;
    }

    public static synchronized void registerDao(ConnectionSource connectionSource, AbstractC2183Dao<?, ?> abstractC2183Dao) {
        synchronized (DaoManager.class) {
            if (connectionSource == null) {
                throw new IllegalArgumentException("connectionSource argument cannot be null");
            }
            addDaoToClassMap(new ClassConnectionSource(connectionSource, abstractC2183Dao.getDataClass()), abstractC2183Dao);
        }
    }

    public static synchronized void registerDaoWithTableConfig(ConnectionSource connectionSource, AbstractC2183Dao<?, ?> abstractC2183Dao) {
        DatabaseTableConfig tableConfig;
        synchronized (DaoManager.class) {
            if (connectionSource != null) {
                if (!(abstractC2183Dao instanceof BaseDaoImpl) || (tableConfig = ((BaseDaoImpl) abstractC2183Dao).getTableConfig()) == null) {
                    addDaoToClassMap(new ClassConnectionSource(connectionSource, abstractC2183Dao.getDataClass()), abstractC2183Dao);
                    return;
                } else {
                    addDaoToTableMap(new TableConfigConnectionSource(connectionSource, tableConfig), abstractC2183Dao);
                    return;
                }
            }
            throw new IllegalArgumentException("connectionSource argument cannot be null");
        }
    }

    public static void removeDaoToClassMap(ClassConnectionSource classConnectionSource, AbstractC2183Dao<?, ?> abstractC2183Dao) {
        Map<ClassConnectionSource, AbstractC2183Dao<?, ?>> map = classMap;
        if (map != null) {
            map.remove(classConnectionSource);
        }
    }

    public static synchronized void unregisterDao(ConnectionSource connectionSource, AbstractC2183Dao<?, ?> abstractC2183Dao) {
        synchronized (DaoManager.class) {
            if (connectionSource == null) {
                throw new IllegalArgumentException("connectionSource argument cannot be null");
            }
            removeDaoToClassMap(new ClassConnectionSource(connectionSource, abstractC2183Dao.getDataClass()), abstractC2183Dao);
        }
    }
}
