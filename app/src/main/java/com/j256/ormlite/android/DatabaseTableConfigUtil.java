package com.j256.ormlite.android;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.DatabaseFieldConfig;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.j256.ormlite.p075db.DatabaseType;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class DatabaseTableConfigUtil {
    public static final int ALLOW_GENERATED_ID_INSERT = 24;
    public static final int CAN_BE_NULL = 5;
    public static final int COLUMN_DEFINITON = 25;
    public static final int COLUMN_NAME = 1;
    public static final int DATA_TYPE = 2;
    public static final int DEFAULT_VALUE = 3;
    public static final int FOREIGN = 9;
    public static final int FOREIGN_AUTO_CREATE = 26;
    public static final int FOREIGN_AUTO_REFRESH = 21;
    public static final int FOREIGN_COLUMN_NAME = 28;
    public static final int FORMAT = 14;
    public static final int GENERATED_ID = 7;
    public static final int GENERATED_ID_SEQUENCE = 8;

    /* renamed from: ID */
    public static final int f1491ID = 6;
    public static final int INDEX = 17;
    public static final int INDEX_NAME = 19;
    public static final int MAX_FOREIGN_AUTO_REFRESH_LEVEL = 22;
    public static final int PERSISTED = 13;
    public static final int PERSISTER_CLASS = 23;
    public static final int READ_ONLY = 29;
    public static final int THROW_IF_NULL = 12;
    public static final int UNIQUE = 15;
    public static final int UNIQUE_COMBO = 16;
    public static final int UNIQUE_INDEX = 18;
    public static final int UNIQUE_INDEX_NAME = 20;
    public static final int UNKNOWN_ENUM_NAME = 11;
    public static final int USE_GET_SET = 10;
    public static final int VERSION = 27;
    public static final int WIDTH = 4;
    public static Class<?> annotationFactoryClazz;
    public static Class<?> annotationMemberClazz;
    public static final int[] configFieldNums = lookupClasses();
    public static Field elementsField;
    public static Field nameField;
    public static Field valueField;
    public static int workedC;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class DatabaseFieldSample {
        @DatabaseField
        public String field;
    }

    public static void assignConfigField(int i, DatabaseFieldConfig databaseFieldConfig, Field field, Object obj) {
        switch (i) {
            case 1:
                databaseFieldConfig.setColumnName(valueIfNotBlank((String) obj));
                return;
            case 2:
                databaseFieldConfig.setDataType((DataType) obj);
                return;
            case 3:
                String str = (String) obj;
                if (str == null || str.equals(DatabaseField.DEFAULT_STRING)) {
                    return;
                }
                databaseFieldConfig.setDefaultValue(str);
                return;
            case 4:
                databaseFieldConfig.setWidth(((Integer) obj).intValue());
                return;
            case 5:
                databaseFieldConfig.setCanBeNull(((Boolean) obj).booleanValue());
                return;
            case 6:
                databaseFieldConfig.setId(((Boolean) obj).booleanValue());
                return;
            case 7:
                databaseFieldConfig.setGeneratedId(((Boolean) obj).booleanValue());
                return;
            case 8:
                databaseFieldConfig.setGeneratedIdSequence(valueIfNotBlank((String) obj));
                return;
            case 9:
                databaseFieldConfig.setForeign(((Boolean) obj).booleanValue());
                return;
            case 10:
                databaseFieldConfig.setUseGetSet(((Boolean) obj).booleanValue());
                return;
            case 11:
                databaseFieldConfig.setUnknownEnumValue(DatabaseFieldConfig.findMatchingEnumVal(field, (String) obj));
                return;
            case 12:
                databaseFieldConfig.setThrowIfNull(((Boolean) obj).booleanValue());
                return;
            case 13:
                databaseFieldConfig.setPersisted(((Boolean) obj).booleanValue());
                return;
            case 14:
                databaseFieldConfig.setFormat(valueIfNotBlank((String) obj));
                return;
            case 15:
                databaseFieldConfig.setUnique(((Boolean) obj).booleanValue());
                return;
            case 16:
                databaseFieldConfig.setUniqueCombo(((Boolean) obj).booleanValue());
                return;
            case 17:
                databaseFieldConfig.setIndex(((Boolean) obj).booleanValue());
                return;
            case 18:
                databaseFieldConfig.setUniqueIndex(((Boolean) obj).booleanValue());
                return;
            case 19:
                databaseFieldConfig.setIndexName(valueIfNotBlank((String) obj));
                return;
            case 20:
                databaseFieldConfig.setUniqueIndexName(valueIfNotBlank((String) obj));
                return;
            case 21:
                databaseFieldConfig.setForeignAutoRefresh(((Boolean) obj).booleanValue());
                return;
            case 22:
                databaseFieldConfig.setMaxForeignAutoRefreshLevel(((Integer) obj).intValue());
                return;
            case 23:
                databaseFieldConfig.setPersisterClass((Class) obj);
                return;
            case 24:
                databaseFieldConfig.setAllowGeneratedIdInsert(((Boolean) obj).booleanValue());
                return;
            case 25:
                databaseFieldConfig.setColumnDefinition(valueIfNotBlank((String) obj));
                return;
            case 26:
                databaseFieldConfig.setForeignAutoCreate(((Boolean) obj).booleanValue());
                return;
            case 27:
                databaseFieldConfig.setVersion(((Boolean) obj).booleanValue());
                return;
            case 28:
                databaseFieldConfig.setForeignColumnName(valueIfNotBlank((String) obj));
                return;
            case 29:
                databaseFieldConfig.setReadOnly(((Boolean) obj).booleanValue());
                return;
            default:
                throw new IllegalStateException("Could not find support for DatabaseField number " + i);
        }
    }

    public static DatabaseFieldConfig buildConfig(DatabaseField databaseField, String str, Field field) {
        Object obj;
        InvocationHandler invocationHandler = Proxy.getInvocationHandler(databaseField);
        if (invocationHandler.getClass() == annotationFactoryClazz && (obj = elementsField.get(invocationHandler)) != null) {
            DatabaseFieldConfig databaseFieldConfig = new DatabaseFieldConfig(field.getName());
            Object[] objArr = (Object[]) obj;
            for (int i = 0; i < configFieldNums.length; i++) {
                Object obj2 = valueField.get(objArr[i]);
                if (obj2 != null) {
                    assignConfigField(configFieldNums[i], databaseFieldConfig, field, obj2);
                }
            }
            return databaseFieldConfig;
        }
        return null;
    }

    public static int configFieldNameToNum(String str) {
        if (str.equals(DatabaseFieldConfigLoader.FIELD_NAME_COLUMN_NAME)) {
            return 1;
        }
        if (str.equals("dataType")) {
            return 2;
        }
        if (str.equals(DatabaseFieldConfigLoader.FIELD_NAME_DEFAULT_VALUE)) {
            return 3;
        }
        if (str.equals("width")) {
            return 4;
        }
        if (str.equals(DatabaseFieldConfigLoader.FIELD_NAME_CAN_BE_NULL)) {
            return 5;
        }
        if (str.equals(DatabaseFieldConfigLoader.FIELD_NAME_ID)) {
            return 6;
        }
        if (str.equals(DatabaseFieldConfigLoader.FIELD_NAME_GENERATED_ID)) {
            return 7;
        }
        if (str.equals(DatabaseFieldConfigLoader.FIELD_NAME_GENERATED_ID_SEQUENCE)) {
            return 8;
        }
        if (str.equals(DatabaseFieldConfigLoader.FIELD_NAME_FOREIGN)) {
            return 9;
        }
        if (str.equals(DatabaseFieldConfigLoader.FIELD_NAME_USE_GET_SET)) {
            return 10;
        }
        if (str.equals("unknownEnumName")) {
            return 11;
        }
        if (str.equals(DatabaseFieldConfigLoader.FIELD_NAME_THROW_IF_NULL)) {
            return 12;
        }
        if (str.equals("persisted")) {
            return 13;
        }
        if (str.equals("format")) {
            return 14;
        }
        if (str.equals(DatabaseFieldConfigLoader.FIELD_NAME_UNIQUE)) {
            return 15;
        }
        if (str.equals(DatabaseFieldConfigLoader.FIELD_NAME_UNIQUE_COMBO)) {
            return 16;
        }
        if (str.equals(DatabaseFieldConfigLoader.FIELD_NAME_INDEX)) {
            return 17;
        }
        if (str.equals(DatabaseFieldConfigLoader.FIELD_NAME_UNIQUE_INDEX)) {
            return 18;
        }
        if (str.equals(DatabaseFieldConfigLoader.FIELD_NAME_INDEX_NAME)) {
            return 19;
        }
        if (str.equals(DatabaseFieldConfigLoader.FIELD_NAME_UNIQUE_INDEX_NAME)) {
            return 20;
        }
        if (str.equals(DatabaseFieldConfigLoader.FIELD_NAME_FOREIGN_AUTO_REFRESH)) {
            return 21;
        }
        if (str.equals(DatabaseFieldConfigLoader.FIELD_NAME_MAX_FOREIGN_AUTO_REFRESH_LEVEL)) {
            return 22;
        }
        if (str.equals(DatabaseFieldConfigLoader.FIELD_NAME_PERSISTER_CLASS)) {
            return 23;
        }
        if (str.equals(DatabaseFieldConfigLoader.FIELD_NAME_ALLOW_GENERATED_ID_INSERT)) {
            return 24;
        }
        if (str.equals(DatabaseFieldConfigLoader.FIELD_NAME_COLUMN_DEFINITION)) {
            return 25;
        }
        if (str.equals(DatabaseFieldConfigLoader.FIELD_NAME_FOREIGN_AUTO_CREATE)) {
            return 26;
        }
        if (str.equals(DatabaseFieldConfigLoader.FIELD_NAME_VERSION)) {
            return 27;
        }
        if (str.equals(DatabaseFieldConfigLoader.FIELD_NAME_FOREIGN_COLUMN_NAME)) {
            return 28;
        }
        if (str.equals(DatabaseFieldConfigLoader.FIELD_NAME_READ_ONLY)) {
            return 29;
        }
        throw new IllegalStateException("Could not find support for DatabaseField " + str);
    }

    public static DatabaseFieldConfig configFromField(DatabaseType databaseType, String str, Field field) {
        if (configFieldNums == null) {
            return DatabaseFieldConfig.fromField(databaseType, str, field);
        }
        DatabaseField databaseField = (DatabaseField) field.getAnnotation(DatabaseField.class);
        DatabaseFieldConfig databaseFieldConfig = null;
        if (databaseField != null) {
            try {
                databaseFieldConfig = buildConfig(databaseField, str, field);
            } catch (Exception unused) {
            }
        }
        if (databaseFieldConfig == null) {
            return DatabaseFieldConfig.fromField(databaseType, str, field);
        }
        workedC++;
        return databaseFieldConfig;
    }

    public static <T> DatabaseTableConfig<T> fromClass(ConnectionSource connectionSource, Class<T> cls) {
        DatabaseType databaseType = connectionSource.getDatabaseType();
        String extractTableName = DatabaseTableConfig.extractTableName(cls);
        ArrayList arrayList = new ArrayList();
        for (Class<T> cls2 = cls; cls2 != null; cls2 = cls2.getSuperclass()) {
            for (Field field : cls2.getDeclaredFields()) {
                DatabaseFieldConfig configFromField = configFromField(databaseType, extractTableName, field);
                if (configFromField != null && configFromField.isPersisted()) {
                    arrayList.add(configFromField);
                }
            }
        }
        if (arrayList.size() == 0) {
            return null;
        }
        return new DatabaseTableConfig<>(cls, extractTableName, arrayList);
    }

    public static int getWorkedC() {
        return workedC;
    }

    public static int[] lookupClasses() {
        Object obj;
        try {
            annotationFactoryClazz = Class.forName("org.apache.harmony.lang.annotation.AnnotationFactory");
            annotationMemberClazz = Class.forName("org.apache.harmony.lang.annotation.AnnotationMember");
            Class<?> cls = Class.forName("[Lorg.apache.harmony.lang.annotation.AnnotationMember;");
            elementsField = annotationFactoryClazz.getDeclaredField("elements");
            elementsField.setAccessible(true);
            nameField = annotationMemberClazz.getDeclaredField("name");
            nameField.setAccessible(true);
            valueField = annotationMemberClazz.getDeclaredField("value");
            valueField.setAccessible(true);
            InvocationHandler invocationHandler = Proxy.getInvocationHandler((DatabaseField) DatabaseFieldSample.class.getDeclaredField("field").getAnnotation(DatabaseField.class));
            if (invocationHandler.getClass() == annotationFactoryClazz && (obj = elementsField.get(invocationHandler)) != null && obj.getClass() == cls) {
                Object[] objArr = (Object[]) obj;
                int[] iArr = new int[objArr.length];
                for (int i = 0; i < objArr.length; i++) {
                    iArr[i] = configFieldNameToNum((String) nameField.get(objArr[i]));
                }
                return iArr;
            }
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException | SecurityException unused) {
        }
        return null;
    }

    public static String valueIfNotBlank(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        return str;
    }
}
