package com.j256.ormlite.misc;

import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.p075db.DatabaseType;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes3.dex */
public class TransactionManager {
    public static final String SAVE_POINT_PREFIX = "ORMLITE";
    public static final Logger logger = LoggerFactory.getLogger(TransactionManager.class);
    public static AtomicInteger savePointCounter = new AtomicInteger();
    public ConnectionSource connectionSource;

    public TransactionManager() {
    }

    public TransactionManager(ConnectionSource connectionSource) {
        this.connectionSource = connectionSource;
        initialize();
    }

    public static <T> T callInTransaction(ConnectionSource connectionSource, Callable<T> callable) {
        DatabaseConnection readWriteConnection = connectionSource.getReadWriteConnection();
        try {
            return (T) callInTransaction(readWriteConnection, connectionSource.saveSpecialConnection(readWriteConnection), connectionSource.getDatabaseType(), callable);
        } finally {
            connectionSource.clearSpecialConnection(readWriteConnection);
            connectionSource.releaseConnection(readWriteConnection);
        }
    }

    public static <T> T callInTransaction(DatabaseConnection databaseConnection, DatabaseType databaseType, Callable<T> callable) {
        return (T) callInTransaction(databaseConnection, false, databaseType, callable);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x006c A[Catch: all -> 0x007a, Exception -> 0x007e, SQLException -> 0x0092, TRY_LEAVE, TryCatch #8 {SQLException -> 0x0092, Exception -> 0x007e, blocks: (B:32:0x0066, B:34:0x006c), top: B:31:0x0066, outer: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0071  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static <T> T callInTransaction(DatabaseConnection databaseConnection, boolean z, DatabaseType databaseType, Callable<T> callable) {
        Savepoint savepoint;
        boolean z2;
        Throwable th;
        boolean z3 = false;
        try {
            try {
                try {
                    if (!z) {
                        try {
                            if (!databaseType.isNestedSavePointsSupported()) {
                                savepoint = null;
                                z2 = false;
                                T call = callable.call();
                                if (z3) {
                                    commit(databaseConnection, savepoint);
                                }
                                if (z2) {
                                    databaseConnection.setAutoCommit(true);
                                    logger.debug("restored auto-commit to true");
                                }
                                return call;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            th = th;
                            if (z3) {
                                databaseConnection.setAutoCommit(true);
                                logger.debug("restored auto-commit to true");
                            }
                            throw th;
                        }
                    }
                    T call2 = callable.call();
                    if (z3) {
                    }
                    if (z2) {
                    }
                    return call2;
                } catch (Throwable th3) {
                    th = th3;
                    z3 = z2;
                    th = th;
                    if (z3) {
                    }
                    throw th;
                }
            } catch (SQLException e) {
                if (z3) {
                    try {
                        rollBack(databaseConnection, savepoint);
                    } catch (SQLException unused) {
                        logger.error(e, "after commit exception, rolling back to save-point also threw exception");
                    }
                }
                throw e;
            } catch (Exception e2) {
                if (z3) {
                    try {
                        rollBack(databaseConnection, savepoint);
                    } catch (SQLException unused2) {
                        logger.error(e2, "after commit exception, rolling back to save-point also threw exception");
                    }
                }
                throw SqlExceptionUtil.create("Transaction callable threw non-SQL exception", e2);
            }
            StringBuilder sb = new StringBuilder();
            sb.append(SAVE_POINT_PREFIX);
            sb.append(savePointCounter.incrementAndGet());
            savepoint = databaseConnection.setSavePoint(sb.toString());
            if (savepoint == null) {
                logger.debug("started savePoint transaction");
            } else {
                logger.debug("started savePoint transaction {}", savepoint.getSavepointName());
            }
            z2 = z3;
            z3 = true;
        } catch (Throwable th4) {
            th = th4;
            if (z3) {
            }
            throw th;
        }
        if (databaseConnection.isAutoCommitSupported()) {
            boolean isAutoCommit = databaseConnection.isAutoCommit();
            if (isAutoCommit) {
                try {
                    databaseConnection.setAutoCommit(false);
                    logger.debug("had to set auto-commit to false");
                } catch (Throwable th5) {
                    th = th5;
                    z3 = isAutoCommit;
                    if (z3) {
                    }
                    throw th;
                }
            }
            z3 = isAutoCommit;
        }
    }

    public static void commit(DatabaseConnection databaseConnection, Savepoint savepoint) {
        String savepointName = savepoint == null ? null : savepoint.getSavepointName();
        databaseConnection.commit(savepoint);
        Logger logger2 = logger;
        if (savepointName == null) {
            logger2.debug("committed savePoint transaction");
        } else {
            logger2.debug("committed savePoint transaction {}", savepointName);
        }
    }

    public static void rollBack(DatabaseConnection databaseConnection, Savepoint savepoint) {
        String savepointName = savepoint == null ? null : savepoint.getSavepointName();
        databaseConnection.rollback(savepoint);
        Logger logger2 = logger;
        if (savepointName == null) {
            logger2.debug("rolled back savePoint transaction");
        } else {
            logger2.debug("rolled back savePoint transaction {}", savepointName);
        }
    }

    public <T> T callInTransaction(Callable<T> callable) {
        return (T) callInTransaction(this.connectionSource, callable);
    }

    public void initialize() {
        if (this.connectionSource != null) {
            return;
        }
        throw new IllegalStateException("dataSource was not set on " + TransactionManager.class.getSimpleName());
    }

    public void setConnectionSource(ConnectionSource connectionSource) {
        this.connectionSource = connectionSource;
    }
}
