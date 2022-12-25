package com.j256.ormlite.stmt.mapped;

import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.logger.Log;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.p075db.DatabaseType;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.support.GeneratedKeyHolder;
import com.j256.ormlite.table.TableInfo;
import java.sql.SQLException;

/* loaded from: classes3.dex */
public class MappedCreate<T, ID> extends BaseMappedStatement<T, ID> {
    public String dataClassName;
    public final String queryNextSequenceStmt;
    public int versionFieldTypeIndex;

    /* loaded from: classes3.dex */
    private static class KeyHolder implements GeneratedKeyHolder {
        public Number key;

        public KeyHolder() {
        }

        @Override // com.j256.ormlite.support.GeneratedKeyHolder
        public void addKey(Number number) {
            if (this.key == null) {
                this.key = number;
                return;
            }
            throw new SQLException("generated key has already been set to " + this.key + ", now set to " + number);
        }

        public Number getKey() {
            return this.key;
        }
    }

    public MappedCreate(TableInfo<T, ID> tableInfo, String str, FieldType[] fieldTypeArr, String str2, int i) {
        super(tableInfo, str, fieldTypeArr);
        this.dataClassName = tableInfo.getDataClass().getSimpleName();
        this.queryNextSequenceStmt = str2;
        this.versionFieldTypeIndex = i;
    }

    private void assignIdValue(T t, Number number, String str, ObjectCache objectCache) {
        this.idField.assignIdValue(t, number, objectCache);
        if (BaseMappedStatement.logger.isLevelEnabled(Log.Level.DEBUG)) {
            BaseMappedStatement.logger.debug("assigned id '{}' from {} to '{}' in {} object", new Object[]{number, str, this.idField.getFieldName(), this.dataClassName});
        }
    }

    private void assignSequenceId(DatabaseConnection databaseConnection, T t, ObjectCache objectCache) {
        long queryForLong = databaseConnection.queryForLong(this.queryNextSequenceStmt);
        BaseMappedStatement.logger.debug("queried for sequence {} using stmt: {}", Long.valueOf(queryForLong), this.queryNextSequenceStmt);
        if (queryForLong != 0) {
            assignIdValue(t, Long.valueOf(queryForLong), "sequence", objectCache);
            return;
        }
        throw new SQLException("Should not have returned 0 for stmt: " + this.queryNextSequenceStmt);
    }

    public static <T, ID> MappedCreate<T, ID> build(DatabaseType databaseType, TableInfo<T, ID> tableInfo) {
        FieldType[] fieldTypes;
        FieldType[] fieldTypes2;
        StringBuilder sb = new StringBuilder(128);
        BaseMappedStatement.appendTableName(databaseType, sb, "INSERT INTO ", tableInfo.getTableName());
        int i = 0;
        int i2 = -1;
        for (FieldType fieldType : tableInfo.getFieldTypes()) {
            if (isFieldCreatable(databaseType, fieldType)) {
                if (fieldType.isVersion()) {
                    i2 = i;
                }
                i++;
            }
        }
        FieldType[] fieldTypeArr = new FieldType[i];
        if (i == 0) {
            databaseType.appendInsertNoColumns(sb);
        } else {
            sb.append('(');
            boolean z = true;
            int i3 = 0;
            for (FieldType fieldType2 : tableInfo.getFieldTypes()) {
                if (isFieldCreatable(databaseType, fieldType2)) {
                    if (z) {
                        z = false;
                    } else {
                        sb.append(",");
                    }
                    BaseMappedStatement.appendFieldColumnName(databaseType, sb, fieldType2, null);
                    fieldTypeArr[i3] = fieldType2;
                    i3++;
                }
            }
            sb.append(") VALUES (");
            boolean z2 = true;
            for (FieldType fieldType3 : tableInfo.getFieldTypes()) {
                if (isFieldCreatable(databaseType, fieldType3)) {
                    if (z2) {
                        z2 = false;
                    } else {
                        sb.append(",");
                    }
                    sb.append("?");
                }
            }
            sb.append(")");
        }
        return new MappedCreate<>(tableInfo, sb.toString(), fieldTypeArr, buildQueryNextSequence(databaseType, tableInfo.getIdField()), i2);
    }

    public static String buildQueryNextSequence(DatabaseType databaseType, FieldType fieldType) {
        String generatedIdSequence;
        if (fieldType == null || (generatedIdSequence = fieldType.getGeneratedIdSequence()) == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(64);
        databaseType.appendSelectNextValFromSequence(sb, generatedIdSequence);
        return sb.toString();
    }

    private boolean foreignCollectionsAreAssigned(FieldType[] fieldTypeArr, Object obj) {
        for (FieldType fieldType : fieldTypeArr) {
            if (fieldType.extractJavaFieldValue(obj) == null) {
                return false;
            }
        }
        return true;
    }

    public static boolean isFieldCreatable(DatabaseType databaseType, FieldType fieldType) {
        if (!fieldType.isForeignCollection() && !fieldType.isReadOnly()) {
            return (databaseType.isIdSequenceNeeded() && databaseType.isSelectSequenceBeforeInsert()) || !fieldType.isGeneratedId() || fieldType.isSelfGeneratedId() || fieldType.isAllowGeneratedIdInsert();
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0063 A[Catch: SQLException -> 0x013c, TryCatch #0 {SQLException -> 0x013c, blocks: (B:25:0x005b, B:27:0x0063, B:29:0x006d, B:33:0x0089, B:34:0x0076, B:36:0x007c, B:38:0x0086, B:43:0x008c, B:45:0x0094, B:47:0x009a, B:50:0x00b6, B:52:0x00bb, B:54:0x00cc, B:57:0x00d5, B:59:0x00e0, B:61:0x00e6, B:65:0x00f3, B:66:0x00f7, B:68:0x00fc, B:69:0x00ff, B:71:0x0100, B:73:0x0105, B:74:0x0108, B:76:0x010b, B:78:0x0117, B:82:0x0124, B:84:0x0129, B:86:0x0136, B:87:0x013b, B:49:0x00ae), top: B:24:0x005b, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00cc A[Catch: SQLException -> 0x013c, TryCatch #0 {SQLException -> 0x013c, blocks: (B:25:0x005b, B:27:0x0063, B:29:0x006d, B:33:0x0089, B:34:0x0076, B:36:0x007c, B:38:0x0086, B:43:0x008c, B:45:0x0094, B:47:0x009a, B:50:0x00b6, B:52:0x00bb, B:54:0x00cc, B:57:0x00d5, B:59:0x00e0, B:61:0x00e6, B:65:0x00f3, B:66:0x00f7, B:68:0x00fc, B:69:0x00ff, B:71:0x0100, B:73:0x0105, B:74:0x0108, B:76:0x010b, B:78:0x0117, B:82:0x0124, B:84:0x0129, B:86:0x0136, B:87:0x013b, B:49:0x00ae), top: B:24:0x005b, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00d3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int insert(DatabaseType databaseType, DatabaseConnection databaseConnection, T t, ObjectCache objectCache) {
        KeyHolder keyHolder;
        Object[] fieldObjects;
        Object obj;
        int insert;
        FieldType[] fieldTypes;
        Object extractRawJavaFieldValue;
        FieldType fieldType = this.idField;
        try {
            try {
                if (fieldType != null) {
                    boolean z = !fieldType.isAllowGeneratedIdInsert() || this.idField.isObjectsFieldValueDefault(t);
                    if (!this.idField.isSelfGeneratedId() || !this.idField.isGeneratedId()) {
                        if (!this.idField.isGeneratedIdSequence() || !databaseType.isSelectSequenceBeforeInsert()) {
                            if (this.idField.isGeneratedId() && z) {
                                keyHolder = new KeyHolder();
                                if (this.tableInfo.isForeignAutoCreate()) {
                                    for (FieldType fieldType2 : this.tableInfo.getFieldTypes()) {
                                        if (fieldType2.isForeignAutoCreate() && (extractRawJavaFieldValue = fieldType2.extractRawJavaFieldValue(t)) != null && fieldType2.getForeignIdField().isObjectsFieldValueDefault(extractRawJavaFieldValue)) {
                                            fieldType2.createWithForeignDao(extractRawJavaFieldValue);
                                        }
                                    }
                                }
                                fieldObjects = getFieldObjects(t);
                                if (this.versionFieldTypeIndex >= 0 || fieldObjects[this.versionFieldTypeIndex] != null) {
                                    obj = null;
                                } else {
                                    FieldType fieldType3 = this.argFieldTypes[this.versionFieldTypeIndex];
                                    obj = fieldType3.moveToNextValue(null);
                                    fieldObjects[this.versionFieldTypeIndex] = fieldType3.convertJavaFieldToSqlArgValue(obj);
                                }
                                insert = databaseConnection.insert(this.statement, fieldObjects, this.argFieldTypes, keyHolder);
                                BaseMappedStatement.logger.debug("insert data with statement '{}' and {} args, changed {} rows", this.statement, Integer.valueOf(fieldObjects.length), Integer.valueOf(insert));
                                if (fieldObjects.length > 0) {
                                    BaseMappedStatement.logger.trace("insert arguments: {}", (Object) fieldObjects);
                                }
                                if (insert > 0) {
                                    if (obj != null) {
                                        this.argFieldTypes[this.versionFieldTypeIndex].assignField(t, obj, false, null);
                                    }
                                    if (keyHolder != null) {
                                        Number key = keyHolder.getKey();
                                        if (key == null) {
                                            throw new SQLException("generated-id key was not set by the update call");
                                        }
                                        if (key.longValue() == 0) {
                                            throw new SQLException("generated-id key must not be 0 value");
                                        }
                                        assignIdValue(t, key, "keyholder", objectCache);
                                    }
                                    if (objectCache != 0 && foreignCollectionsAreAssigned(this.tableInfo.getForeignCollections(), t)) {
                                        objectCache.put(this.clazz, this.idField.extractJavaFieldValue(t), t);
                                    }
                                }
                                return insert;
                            }
                        } else if (z) {
                            assignSequenceId(databaseConnection, t, objectCache);
                        }
                    } else if (z) {
                        FieldType fieldType4 = this.idField;
                        fieldType4.assignField(t, fieldType4.generateId(), false, objectCache);
                    }
                }
                insert = databaseConnection.insert(this.statement, fieldObjects, this.argFieldTypes, keyHolder);
                BaseMappedStatement.logger.debug("insert data with statement '{}' and {} args, changed {} rows", this.statement, Integer.valueOf(fieldObjects.length), Integer.valueOf(insert));
                if (fieldObjects.length > 0) {
                }
                if (insert > 0) {
                }
                return insert;
            } catch (SQLException e) {
                BaseMappedStatement.logger.debug("insert data with statement '{}' and {} args, threw exception: {}", this.statement, Integer.valueOf(fieldObjects.length), e);
                if (fieldObjects.length > 0) {
                    BaseMappedStatement.logger.trace("insert arguments: {}", (Object) fieldObjects);
                }
                throw e;
            }
            if (this.tableInfo.isForeignAutoCreate()) {
            }
            fieldObjects = getFieldObjects(t);
            if (this.versionFieldTypeIndex >= 0) {
            }
            obj = null;
        } catch (SQLException e2) {
            throw SqlExceptionUtil.create("Unable to run insert stmt on object " + t + ": " + this.statement, e2);
        }
        keyHolder = null;
    }
}
