package com.j256.ormlite.support;

import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.stmt.GenericRowMapper;
import com.j256.ormlite.stmt.StatementBuilder;
import java.sql.Savepoint;

/* loaded from: classes3.dex */
public class DatabaseConnectionProxy implements DatabaseConnection {
    public final DatabaseConnection proxy;

    public DatabaseConnectionProxy(DatabaseConnection databaseConnection) {
        this.proxy = databaseConnection;
    }

    @Override // com.j256.ormlite.support.DatabaseConnection
    public void close() {
        DatabaseConnection databaseConnection = this.proxy;
        if (databaseConnection != null) {
            databaseConnection.close();
        }
    }

    @Override // com.j256.ormlite.support.DatabaseConnection
    public void closeQuietly() {
        DatabaseConnection databaseConnection = this.proxy;
        if (databaseConnection != null) {
            databaseConnection.closeQuietly();
        }
    }

    @Override // com.j256.ormlite.support.DatabaseConnection
    public void commit(Savepoint savepoint) {
        DatabaseConnection databaseConnection = this.proxy;
        if (databaseConnection != null) {
            databaseConnection.commit(savepoint);
        }
    }

    @Override // com.j256.ormlite.support.DatabaseConnection
    public CompiledStatement compileStatement(String str, StatementBuilder.StatementType statementType, FieldType[] fieldTypeArr, int i) {
        DatabaseConnection databaseConnection = this.proxy;
        if (databaseConnection == null) {
            return null;
        }
        return databaseConnection.compileStatement(str, statementType, fieldTypeArr, i);
    }

    @Override // com.j256.ormlite.support.DatabaseConnection
    public int delete(String str, Object[] objArr, FieldType[] fieldTypeArr) {
        DatabaseConnection databaseConnection = this.proxy;
        if (databaseConnection == null) {
            return 0;
        }
        return databaseConnection.delete(str, objArr, fieldTypeArr);
    }

    @Override // com.j256.ormlite.support.DatabaseConnection
    public int executeStatement(String str, int i) {
        DatabaseConnection databaseConnection = this.proxy;
        if (databaseConnection == null) {
            return 0;
        }
        return databaseConnection.executeStatement(str, i);
    }

    @Override // com.j256.ormlite.support.DatabaseConnection
    public int insert(String str, Object[] objArr, FieldType[] fieldTypeArr, GeneratedKeyHolder generatedKeyHolder) {
        DatabaseConnection databaseConnection = this.proxy;
        if (databaseConnection == null) {
            return 0;
        }
        return databaseConnection.insert(str, objArr, fieldTypeArr, generatedKeyHolder);
    }

    @Override // com.j256.ormlite.support.DatabaseConnection
    public boolean isAutoCommit() {
        DatabaseConnection databaseConnection = this.proxy;
        if (databaseConnection == null) {
            return false;
        }
        return databaseConnection.isAutoCommit();
    }

    @Override // com.j256.ormlite.support.DatabaseConnection
    public boolean isAutoCommitSupported() {
        DatabaseConnection databaseConnection = this.proxy;
        if (databaseConnection == null) {
            return false;
        }
        return databaseConnection.isAutoCommitSupported();
    }

    @Override // com.j256.ormlite.support.DatabaseConnection
    public boolean isClosed() {
        DatabaseConnection databaseConnection = this.proxy;
        if (databaseConnection == null) {
            return true;
        }
        return databaseConnection.isClosed();
    }

    @Override // com.j256.ormlite.support.DatabaseConnection
    public boolean isTableExists(String str) {
        DatabaseConnection databaseConnection = this.proxy;
        if (databaseConnection == null) {
            return false;
        }
        return databaseConnection.isTableExists(str);
    }

    @Override // com.j256.ormlite.support.DatabaseConnection
    public long queryForLong(String str) {
        DatabaseConnection databaseConnection = this.proxy;
        if (databaseConnection == null) {
            return 0L;
        }
        return databaseConnection.queryForLong(str);
    }

    @Override // com.j256.ormlite.support.DatabaseConnection
    public long queryForLong(String str, Object[] objArr, FieldType[] fieldTypeArr) {
        DatabaseConnection databaseConnection = this.proxy;
        if (databaseConnection == null) {
            return 0L;
        }
        return databaseConnection.queryForLong(str, objArr, fieldTypeArr);
    }

    @Override // com.j256.ormlite.support.DatabaseConnection
    public <T> Object queryForOne(String str, Object[] objArr, FieldType[] fieldTypeArr, GenericRowMapper<T> genericRowMapper, ObjectCache objectCache) {
        DatabaseConnection databaseConnection = this.proxy;
        if (databaseConnection == null) {
            return null;
        }
        return databaseConnection.queryForOne(str, objArr, fieldTypeArr, genericRowMapper, objectCache);
    }

    @Override // com.j256.ormlite.support.DatabaseConnection
    public void rollback(Savepoint savepoint) {
        DatabaseConnection databaseConnection = this.proxy;
        if (databaseConnection != null) {
            databaseConnection.rollback(savepoint);
        }
    }

    @Override // com.j256.ormlite.support.DatabaseConnection
    public void setAutoCommit(boolean z) {
        DatabaseConnection databaseConnection = this.proxy;
        if (databaseConnection != null) {
            databaseConnection.setAutoCommit(z);
        }
    }

    @Override // com.j256.ormlite.support.DatabaseConnection
    public Savepoint setSavePoint(String str) {
        DatabaseConnection databaseConnection = this.proxy;
        if (databaseConnection == null) {
            return null;
        }
        return databaseConnection.setSavePoint(str);
    }

    @Override // com.j256.ormlite.support.DatabaseConnection
    public int update(String str, Object[] objArr, FieldType[] fieldTypeArr) {
        DatabaseConnection databaseConnection = this.proxy;
        if (databaseConnection == null) {
            return 0;
        }
        return databaseConnection.update(str, objArr, fieldTypeArr);
    }
}
