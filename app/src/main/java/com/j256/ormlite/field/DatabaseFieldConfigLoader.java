package com.j256.ormlite.field;

import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.SQLException;

/* loaded from: classes3.dex */
public class DatabaseFieldConfigLoader {
    public static final String CONFIG_FILE_END_MARKER = "# --field-end--";
    public static final String CONFIG_FILE_START_MARKER = "# --field-start--";
    public static final DataPersister DEFAULT_DATA_PERSISTER = DatabaseFieldConfig.DEFAULT_DATA_TYPE.getDataPersister();
    public static final int DEFAULT_MAX_EAGER_FOREIGN_COLLECTION_LEVEL = 1;
    public static final String FIELD_NAME_ALLOW_GENERATED_ID_INSERT = "allowGeneratedIdInsert";
    public static final String FIELD_NAME_CAN_BE_NULL = "canBeNull";
    public static final String FIELD_NAME_COLUMN_DEFINITION = "columnDefinition";
    public static final String FIELD_NAME_COLUMN_NAME = "columnName";
    public static final String FIELD_NAME_DATA_PERSISTER = "dataPersister";
    public static final String FIELD_NAME_DEFAULT_VALUE = "defaultValue";
    public static final String FIELD_NAME_FIELD_NAME = "fieldName";
    public static final String FIELD_NAME_FOREIGN = "foreign";
    public static final String FIELD_NAME_FOREIGN_AUTO_CREATE = "foreignAutoCreate";
    public static final String FIELD_NAME_FOREIGN_AUTO_REFRESH = "foreignAutoRefresh";
    public static final String FIELD_NAME_FOREIGN_COLLECTION = "foreignCollection";
    public static final String FIELD_NAME_FOREIGN_COLLECTION_COLUMN_NAME = "foreignCollectionColumnName";
    public static final String FIELD_NAME_FOREIGN_COLLECTION_EAGER = "foreignCollectionEager";
    public static final String FIELD_NAME_FOREIGN_COLLECTION_FOREIGN_FIELD_NAME = "foreignCollectionForeignFieldName";
    public static final String FIELD_NAME_FOREIGN_COLLECTION_FOREIGN_FIELD_NAME_OLD = "foreignCollectionForeignColumnName";
    public static final String FIELD_NAME_FOREIGN_COLLECTION_ORDER_ASCENDING = "foreignCollectionOrderAscending";
    public static final String FIELD_NAME_FOREIGN_COLLECTION_ORDER_COLUMN_NAME = "foreignCollectionOrderColumnName";
    public static final String FIELD_NAME_FOREIGN_COLLECTION_ORDER_COLUMN_NAME_OLD = "foreignCollectionOrderColumn";
    public static final String FIELD_NAME_FOREIGN_COLUMN_NAME = "foreignColumnName";
    public static final String FIELD_NAME_FORMAT = "format";
    public static final String FIELD_NAME_GENERATED_ID = "generatedId";
    public static final String FIELD_NAME_GENERATED_ID_SEQUENCE = "generatedIdSequence";
    public static final String FIELD_NAME_ID = "id";
    public static final String FIELD_NAME_INDEX = "index";
    public static final String FIELD_NAME_INDEX_NAME = "indexName";
    public static final String FIELD_NAME_MAX_EAGER_FOREIGN_COLLECTION_LEVEL = "foreignCollectionMaxEagerLevel";
    public static final String FIELD_NAME_MAX_EAGER_FOREIGN_COLLECTION_LEVEL_OLD = "maxEagerForeignCollectionLevel";
    public static final String FIELD_NAME_MAX_FOREIGN_AUTO_REFRESH_LEVEL = "maxForeignAutoRefreshLevel";
    public static final String FIELD_NAME_PERSISTER_CLASS = "persisterClass";
    public static final String FIELD_NAME_READ_ONLY = "readOnly";
    public static final String FIELD_NAME_THROW_IF_NULL = "throwIfNull";
    public static final String FIELD_NAME_UNIQUE = "unique";
    public static final String FIELD_NAME_UNIQUE_COMBO = "uniqueCombo";
    public static final String FIELD_NAME_UNIQUE_INDEX = "uniqueIndex";
    public static final String FIELD_NAME_UNIQUE_INDEX_NAME = "uniqueIndexName";
    public static final String FIELD_NAME_UNKNOWN_ENUM_VALUE = "unknownEnumValue";
    public static final String FIELD_NAME_USE_GET_SET = "useGetSet";
    public static final String FIELD_NAME_VERSION = "version";
    public static final String FIELD_NAME_WIDTH = "width";

    public static DatabaseFieldConfig fromReader(BufferedReader bufferedReader) {
        DatabaseFieldConfig databaseFieldConfig = new DatabaseFieldConfig();
        boolean z = false;
        while (true) {
            try {
                String readLine = bufferedReader.readLine();
                if (readLine != null && !readLine.equals(CONFIG_FILE_END_MARKER)) {
                    if (readLine.length() != 0 && !readLine.startsWith("#") && !readLine.equals(CONFIG_FILE_START_MARKER)) {
                        String[] split = readLine.split(SimpleComparison.EQUAL_TO_OPERATION, -2);
                        if (split.length != 2) {
                            throw new SQLException("DatabaseFieldConfig reading from stream cannot parse line: " + readLine);
                        }
                        readField(databaseFieldConfig, split[0], split[1]);
                        z = true;
                    }
                }
            } catch (IOException e) {
                throw SqlExceptionUtil.create("Could not read DatabaseFieldConfig from stream", e);
            }
        }
        if (z) {
            return databaseFieldConfig;
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void readField(DatabaseFieldConfig databaseFieldConfig, String str, String str2) {
        Enum[] enumArr;
        if (str.equals(FIELD_NAME_FIELD_NAME)) {
            databaseFieldConfig.setFieldName(str2);
        } else if (str.equals(FIELD_NAME_COLUMN_NAME)) {
            databaseFieldConfig.setColumnName(str2);
        } else if (str.equals(FIELD_NAME_DATA_PERSISTER)) {
            databaseFieldConfig.setDataPersister(DataType.valueOf(str2).getDataPersister());
        } else if (str.equals(FIELD_NAME_DEFAULT_VALUE)) {
            databaseFieldConfig.setDefaultValue(str2);
        } else if (str.equals("width")) {
            databaseFieldConfig.setWidth(Integer.parseInt(str2));
        } else if (str.equals(FIELD_NAME_CAN_BE_NULL)) {
            databaseFieldConfig.setCanBeNull(Boolean.parseBoolean(str2));
        } else if (str.equals(FIELD_NAME_ID)) {
            databaseFieldConfig.setId(Boolean.parseBoolean(str2));
        } else if (str.equals(FIELD_NAME_GENERATED_ID)) {
            databaseFieldConfig.setGeneratedId(Boolean.parseBoolean(str2));
        } else if (str.equals(FIELD_NAME_GENERATED_ID_SEQUENCE)) {
            databaseFieldConfig.setGeneratedIdSequence(str2);
        } else if (str.equals(FIELD_NAME_FOREIGN)) {
            databaseFieldConfig.setForeign(Boolean.parseBoolean(str2));
        } else if (str.equals(FIELD_NAME_USE_GET_SET)) {
            databaseFieldConfig.setUseGetSet(Boolean.parseBoolean(str2));
        } else if (str.equals(FIELD_NAME_UNKNOWN_ENUM_VALUE)) {
            String[] split = str2.split("#", -2);
            if (split.length != 2) {
                throw new IllegalArgumentException("Invalid value for unknownEnumValue which should be in class#name format: " + str2);
            }
            try {
                Object[] enumConstants = Class.forName(split[0]).getEnumConstants();
                if (enumConstants == null) {
                    throw new IllegalArgumentException("Invalid class is not an Enum for unknownEnumValue: " + str2);
                }
                boolean z = false;
                for (Enum r5 : (Enum[]) enumConstants) {
                    if (r5.name().equals(split[1])) {
                        databaseFieldConfig.setUnknownEnumValue(r5);
                        z = true;
                    }
                }
                if (z) {
                    return;
                }
                throw new IllegalArgumentException("Invalid enum value name for unknownEnumvalue: " + str2);
            } catch (ClassNotFoundException unused) {
                throw new IllegalArgumentException("Unknown class specified for unknownEnumValue: " + str2);
            }
        } else if (str.equals(FIELD_NAME_THROW_IF_NULL)) {
            databaseFieldConfig.setThrowIfNull(Boolean.parseBoolean(str2));
        } else if (str.equals("format")) {
            databaseFieldConfig.setFormat(str2);
        } else if (str.equals(FIELD_NAME_UNIQUE)) {
            databaseFieldConfig.setUnique(Boolean.parseBoolean(str2));
        } else if (str.equals(FIELD_NAME_UNIQUE_COMBO)) {
            databaseFieldConfig.setUniqueCombo(Boolean.parseBoolean(str2));
        } else if (str.equals(FIELD_NAME_INDEX)) {
            databaseFieldConfig.setIndex(Boolean.parseBoolean(str2));
        } else if (str.equals(FIELD_NAME_INDEX_NAME)) {
            databaseFieldConfig.setIndex(true);
            databaseFieldConfig.setIndexName(str2);
        } else if (str.equals(FIELD_NAME_UNIQUE_INDEX)) {
            databaseFieldConfig.setUniqueIndex(Boolean.parseBoolean(str2));
        } else if (str.equals(FIELD_NAME_UNIQUE_INDEX_NAME)) {
            databaseFieldConfig.setUniqueIndex(true);
            databaseFieldConfig.setUniqueIndexName(str2);
        } else if (str.equals(FIELD_NAME_FOREIGN_AUTO_REFRESH)) {
            databaseFieldConfig.setForeignAutoRefresh(Boolean.parseBoolean(str2));
        } else if (str.equals(FIELD_NAME_MAX_FOREIGN_AUTO_REFRESH_LEVEL)) {
            databaseFieldConfig.setMaxForeignAutoRefreshLevel(Integer.parseInt(str2));
        } else if (str.equals(FIELD_NAME_PERSISTER_CLASS)) {
            try {
                databaseFieldConfig.setPersisterClass(Class.forName(str2));
            } catch (ClassNotFoundException unused2) {
                throw new IllegalArgumentException("Could not find persisterClass: " + str2);
            }
        } else if (str.equals(FIELD_NAME_ALLOW_GENERATED_ID_INSERT)) {
            databaseFieldConfig.setAllowGeneratedIdInsert(Boolean.parseBoolean(str2));
        } else if (str.equals(FIELD_NAME_COLUMN_DEFINITION)) {
            databaseFieldConfig.setColumnDefinition(str2);
        } else if (str.equals(FIELD_NAME_FOREIGN_AUTO_CREATE)) {
            databaseFieldConfig.setForeignAutoCreate(Boolean.parseBoolean(str2));
        } else if (str.equals(FIELD_NAME_VERSION)) {
            databaseFieldConfig.setVersion(Boolean.parseBoolean(str2));
        } else if (str.equals(FIELD_NAME_FOREIGN_COLUMN_NAME)) {
            databaseFieldConfig.setForeignColumnName(str2);
        } else if (str.equals(FIELD_NAME_READ_ONLY)) {
            databaseFieldConfig.setReadOnly(Boolean.parseBoolean(str2));
        } else if (str.equals(FIELD_NAME_FOREIGN_COLLECTION)) {
            databaseFieldConfig.setForeignCollection(Boolean.parseBoolean(str2));
        } else if (str.equals(FIELD_NAME_FOREIGN_COLLECTION_EAGER)) {
            databaseFieldConfig.setForeignCollectionEager(Boolean.parseBoolean(str2));
        } else if (str.equals(FIELD_NAME_MAX_EAGER_FOREIGN_COLLECTION_LEVEL_OLD) || str.equals(FIELD_NAME_MAX_EAGER_FOREIGN_COLLECTION_LEVEL)) {
            databaseFieldConfig.setForeignCollectionMaxEagerLevel(Integer.parseInt(str2));
        } else if (str.equals(FIELD_NAME_FOREIGN_COLLECTION_COLUMN_NAME)) {
            databaseFieldConfig.setForeignCollectionColumnName(str2);
        } else if (str.equals(FIELD_NAME_FOREIGN_COLLECTION_ORDER_COLUMN_NAME_OLD) || str.equals(FIELD_NAME_FOREIGN_COLLECTION_ORDER_COLUMN_NAME)) {
            databaseFieldConfig.setForeignCollectionOrderColumnName(str2);
        } else if (str.equals(FIELD_NAME_FOREIGN_COLLECTION_ORDER_ASCENDING)) {
            databaseFieldConfig.setForeignCollectionOrderAscending(Boolean.parseBoolean(str2));
        } else if (str.equals(FIELD_NAME_FOREIGN_COLLECTION_FOREIGN_FIELD_NAME_OLD) || str.equals(FIELD_NAME_FOREIGN_COLLECTION_FOREIGN_FIELD_NAME)) {
            databaseFieldConfig.setForeignCollectionForeignFieldName(str2);
        }
    }

    public static void write(BufferedWriter bufferedWriter, DatabaseFieldConfig databaseFieldConfig, String str) {
        try {
            writeConfig(bufferedWriter, databaseFieldConfig, str);
        } catch (IOException e) {
            throw SqlExceptionUtil.create("Could not write config to writer", e);
        }
    }

    public static void writeConfig(BufferedWriter bufferedWriter, DatabaseFieldConfig databaseFieldConfig, String str) {
        bufferedWriter.append(CONFIG_FILE_START_MARKER);
        bufferedWriter.newLine();
        if (databaseFieldConfig.getFieldName() != null) {
            bufferedWriter.append(FIELD_NAME_FIELD_NAME).append('=').append((CharSequence) databaseFieldConfig.getFieldName());
            bufferedWriter.newLine();
        }
        if (databaseFieldConfig.getColumnName() != null) {
            bufferedWriter.append(FIELD_NAME_COLUMN_NAME).append('=').append((CharSequence) databaseFieldConfig.getColumnName());
            bufferedWriter.newLine();
        }
        if (databaseFieldConfig.getDataPersister() != DEFAULT_DATA_PERSISTER) {
            DataType[] values = DataType.values();
            int length = values.length;
            boolean z = false;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                DataType dataType = values[i];
                if (dataType.getDataPersister() == databaseFieldConfig.getDataPersister()) {
                    bufferedWriter.append(FIELD_NAME_DATA_PERSISTER).append('=').append((CharSequence) dataType.name());
                    bufferedWriter.newLine();
                    z = true;
                    break;
                }
                i++;
            }
            if (!z) {
                throw new IllegalArgumentException("Unknown data persister field: " + databaseFieldConfig.getDataPersister());
            }
        }
        if (databaseFieldConfig.getDefaultValue() != null) {
            bufferedWriter.append(FIELD_NAME_DEFAULT_VALUE).append('=').append((CharSequence) databaseFieldConfig.getDefaultValue());
            bufferedWriter.newLine();
        }
        if (databaseFieldConfig.getWidth() != 0) {
            bufferedWriter.append("width").append('=').append((CharSequence) Integer.toString(databaseFieldConfig.getWidth()));
            bufferedWriter.newLine();
        }
        if (!databaseFieldConfig.isCanBeNull()) {
            bufferedWriter.append(FIELD_NAME_CAN_BE_NULL).append('=').append((CharSequence) Boolean.toString(databaseFieldConfig.isCanBeNull()));
            bufferedWriter.newLine();
        }
        if (databaseFieldConfig.isId()) {
            bufferedWriter.append(FIELD_NAME_ID).append('=').append("true");
            bufferedWriter.newLine();
        }
        if (databaseFieldConfig.isGeneratedId()) {
            bufferedWriter.append(FIELD_NAME_GENERATED_ID).append('=').append("true");
            bufferedWriter.newLine();
        }
        if (databaseFieldConfig.getGeneratedIdSequence() != null) {
            bufferedWriter.append(FIELD_NAME_GENERATED_ID_SEQUENCE).append('=').append((CharSequence) databaseFieldConfig.getGeneratedIdSequence());
            bufferedWriter.newLine();
        }
        if (databaseFieldConfig.isForeign()) {
            bufferedWriter.append(FIELD_NAME_FOREIGN).append('=').append("true");
            bufferedWriter.newLine();
        }
        if (databaseFieldConfig.isUseGetSet()) {
            bufferedWriter.append(FIELD_NAME_USE_GET_SET).append('=').append("true");
            bufferedWriter.newLine();
        }
        if (databaseFieldConfig.getUnknownEnumValue() != null) {
            bufferedWriter.append(FIELD_NAME_UNKNOWN_ENUM_VALUE).append('=').append((CharSequence) databaseFieldConfig.getUnknownEnumValue().getClass().getName()).append("#").append((CharSequence) databaseFieldConfig.getUnknownEnumValue().name());
            bufferedWriter.newLine();
        }
        if (databaseFieldConfig.isThrowIfNull()) {
            bufferedWriter.append(FIELD_NAME_THROW_IF_NULL).append('=').append("true");
            bufferedWriter.newLine();
        }
        if (databaseFieldConfig.getFormat() != null) {
            bufferedWriter.append("format").append('=').append((CharSequence) databaseFieldConfig.getFormat());
            bufferedWriter.newLine();
        }
        if (databaseFieldConfig.isUnique()) {
            bufferedWriter.append(FIELD_NAME_UNIQUE).append('=').append("true");
            bufferedWriter.newLine();
        }
        if (databaseFieldConfig.isUniqueCombo()) {
            bufferedWriter.append(FIELD_NAME_UNIQUE_COMBO).append('=').append("true");
            bufferedWriter.newLine();
        }
        String indexName = databaseFieldConfig.getIndexName(str);
        if (indexName != null) {
            bufferedWriter.append(FIELD_NAME_INDEX_NAME).append('=').append((CharSequence) indexName);
            bufferedWriter.newLine();
        }
        String uniqueIndexName = databaseFieldConfig.getUniqueIndexName(str);
        if (uniqueIndexName != null) {
            bufferedWriter.append(FIELD_NAME_UNIQUE_INDEX_NAME).append('=').append((CharSequence) uniqueIndexName);
            bufferedWriter.newLine();
        }
        if (databaseFieldConfig.isForeignAutoRefresh()) {
            bufferedWriter.append(FIELD_NAME_FOREIGN_AUTO_REFRESH).append('=').append("true");
            bufferedWriter.newLine();
        }
        if (databaseFieldConfig.getMaxForeignAutoRefreshLevel() != -1) {
            bufferedWriter.append(FIELD_NAME_MAX_FOREIGN_AUTO_REFRESH_LEVEL).append('=').append((CharSequence) Integer.toString(databaseFieldConfig.getMaxForeignAutoRefreshLevel()));
            bufferedWriter.newLine();
        }
        if (databaseFieldConfig.getPersisterClass() != DatabaseFieldConfig.DEFAULT_PERSISTER_CLASS) {
            bufferedWriter.append(FIELD_NAME_PERSISTER_CLASS).append('=').append((CharSequence) databaseFieldConfig.getPersisterClass().getName());
            bufferedWriter.newLine();
        }
        if (databaseFieldConfig.isAllowGeneratedIdInsert()) {
            bufferedWriter.append(FIELD_NAME_ALLOW_GENERATED_ID_INSERT).append('=').append("true");
            bufferedWriter.newLine();
        }
        if (databaseFieldConfig.getColumnDefinition() != null) {
            bufferedWriter.append(FIELD_NAME_COLUMN_DEFINITION).append('=').append((CharSequence) databaseFieldConfig.getColumnDefinition());
            bufferedWriter.newLine();
        }
        if (databaseFieldConfig.isForeignAutoCreate()) {
            bufferedWriter.append(FIELD_NAME_FOREIGN_AUTO_CREATE).append('=').append("true");
            bufferedWriter.newLine();
        }
        if (databaseFieldConfig.isVersion()) {
            bufferedWriter.append(FIELD_NAME_VERSION).append('=').append("true");
            bufferedWriter.newLine();
        }
        String foreignColumnName = databaseFieldConfig.getForeignColumnName();
        if (foreignColumnName != null) {
            bufferedWriter.append(FIELD_NAME_FOREIGN_COLUMN_NAME).append('=').append((CharSequence) foreignColumnName);
            bufferedWriter.newLine();
        }
        if (databaseFieldConfig.isReadOnly()) {
            bufferedWriter.append(FIELD_NAME_READ_ONLY).append('=').append("true");
            bufferedWriter.newLine();
        }
        if (databaseFieldConfig.isForeignCollection()) {
            bufferedWriter.append(FIELD_NAME_FOREIGN_COLLECTION).append('=').append("true");
            bufferedWriter.newLine();
        }
        if (databaseFieldConfig.isForeignCollectionEager()) {
            bufferedWriter.append(FIELD_NAME_FOREIGN_COLLECTION_EAGER).append('=').append("true");
            bufferedWriter.newLine();
        }
        if (databaseFieldConfig.getForeignCollectionMaxEagerLevel() != 1) {
            bufferedWriter.append(FIELD_NAME_MAX_EAGER_FOREIGN_COLLECTION_LEVEL).append('=').append((CharSequence) Integer.toString(databaseFieldConfig.getForeignCollectionMaxEagerLevel()));
            bufferedWriter.newLine();
        }
        if (databaseFieldConfig.getForeignCollectionColumnName() != null) {
            bufferedWriter.append(FIELD_NAME_FOREIGN_COLLECTION_COLUMN_NAME).append('=').append((CharSequence) databaseFieldConfig.getForeignCollectionColumnName());
            bufferedWriter.newLine();
        }
        if (databaseFieldConfig.getForeignCollectionOrderColumnName() != null) {
            bufferedWriter.append(FIELD_NAME_FOREIGN_COLLECTION_ORDER_COLUMN_NAME).append('=').append((CharSequence) databaseFieldConfig.getForeignCollectionOrderColumnName());
            bufferedWriter.newLine();
        }
        if (!databaseFieldConfig.isForeignCollectionOrderAscending()) {
            bufferedWriter.append(FIELD_NAME_FOREIGN_COLLECTION_ORDER_ASCENDING).append('=').append((CharSequence) Boolean.toString(databaseFieldConfig.isForeignCollectionOrderAscending()));
            bufferedWriter.newLine();
        }
        if (databaseFieldConfig.getForeignCollectionForeignFieldName() != null) {
            bufferedWriter.append(FIELD_NAME_FOREIGN_COLLECTION_FOREIGN_FIELD_NAME).append('=').append((CharSequence) databaseFieldConfig.getForeignCollectionForeignFieldName());
            bufferedWriter.newLine();
        }
        bufferedWriter.append(CONFIG_FILE_END_MARKER);
        bufferedWriter.newLine();
    }
}
