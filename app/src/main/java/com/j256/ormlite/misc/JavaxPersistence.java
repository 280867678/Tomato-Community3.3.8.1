package com.j256.ormlite.misc;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataPersisterManager;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseFieldConfig;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.j256.ormlite.p075db.DatabaseType;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;

/* loaded from: classes3.dex */
public class JavaxPersistence {
    public static DatabaseFieldConfig createFieldConfig(DatabaseType databaseType, Field field) {
        Annotation[] annotations;
        Annotation annotation;
        Annotation annotation2;
        Annotation annotation3 = null;
        Annotation annotation4 = null;
        Annotation annotation5 = null;
        Annotation annotation6 = null;
        Annotation annotation7 = null;
        Annotation annotation8 = null;
        Annotation annotation9 = null;
        Annotation annotation10 = null;
        Annotation annotation11 = null;
        for (Annotation annotation12 : field.getAnnotations()) {
            Class<? extends Annotation> annotationType = annotation12.annotationType();
            if (annotationType.getName().equals("javax.persistence.Column")) {
                annotation3 = annotation12;
            }
            if (annotationType.getName().equals("javax.persistence.Basic")) {
                annotation4 = annotation12;
            }
            if (annotationType.getName().equals("javax.persistence.Id")) {
                annotation5 = annotation12;
            }
            if (annotationType.getName().equals("javax.persistence.GeneratedValue")) {
                annotation10 = annotation12;
            }
            if (annotationType.getName().equals("javax.persistence.OneToOne")) {
                annotation6 = annotation12;
            }
            if (annotationType.getName().equals("javax.persistence.ManyToOne")) {
                annotation7 = annotation12;
            }
            if (annotationType.getName().equals("javax.persistence.JoinColumn")) {
                annotation11 = annotation12;
            }
            if (annotationType.getName().equals("javax.persistence.Enumerated")) {
                annotation8 = annotation12;
            }
            if (annotationType.getName().equals("javax.persistence.Version")) {
                annotation9 = annotation12;
            }
        }
        if (annotation3 == null && annotation4 == null && annotation5 == null && annotation6 == null && annotation7 == null && annotation8 == null && annotation9 == null) {
            return null;
        }
        DatabaseFieldConfig databaseFieldConfig = new DatabaseFieldConfig();
        String name = field.getName();
        if (databaseType.isEntityNamesMustBeUpCase()) {
            name = name.toUpperCase();
        }
        databaseFieldConfig.setFieldName(name);
        if (annotation3 != null) {
            try {
                annotation = annotation9;
                String str = (String) annotation3.getClass().getMethod("name", new Class[0]).invoke(annotation3, new Object[0]);
                if (str != null && str.length() > 0) {
                    databaseFieldConfig.setColumnName(str);
                }
                annotation2 = annotation8;
                String str2 = (String) annotation3.getClass().getMethod(DatabaseFieldConfigLoader.FIELD_NAME_COLUMN_DEFINITION, new Class[0]).invoke(annotation3, new Object[0]);
                if (str2 != null && str2.length() > 0) {
                    databaseFieldConfig.setColumnDefinition(str2);
                }
                databaseFieldConfig.setWidth(((Integer) annotation3.getClass().getMethod("length", new Class[0]).invoke(annotation3, new Object[0])).intValue());
                Boolean bool = (Boolean) annotation3.getClass().getMethod("nullable", new Class[0]).invoke(annotation3, new Object[0]);
                if (bool != null) {
                    databaseFieldConfig.setCanBeNull(bool.booleanValue());
                }
                Boolean bool2 = (Boolean) annotation3.getClass().getMethod(DatabaseFieldConfigLoader.FIELD_NAME_UNIQUE, new Class[0]).invoke(annotation3, new Object[0]);
                if (bool2 != null) {
                    databaseFieldConfig.setUnique(bool2.booleanValue());
                }
            } catch (Exception e) {
                throw SqlExceptionUtil.create("Problem accessing fields from the @Column annotation for field " + field, e);
            }
        } else {
            annotation2 = annotation8;
            annotation = annotation9;
        }
        if (annotation4 != null) {
            try {
                Boolean bool3 = (Boolean) annotation4.getClass().getMethod("optional", new Class[0]).invoke(annotation4, new Object[0]);
                databaseFieldConfig.setCanBeNull(bool3 == null ? true : bool3.booleanValue());
            } catch (Exception e2) {
                throw SqlExceptionUtil.create("Problem accessing fields from the @Basic annotation for field " + field, e2);
            }
        }
        if (annotation5 != null) {
            if (annotation10 == null) {
                databaseFieldConfig.setId(true);
            } else {
                databaseFieldConfig.setGeneratedId(true);
            }
        }
        if (annotation6 != null || annotation7 != null) {
            if (Collection.class.isAssignableFrom(field.getType()) || ForeignCollection.class.isAssignableFrom(field.getType())) {
                databaseFieldConfig.setForeignCollection(true);
                if (annotation11 != null) {
                    try {
                        String str3 = (String) annotation11.getClass().getMethod("name", new Class[0]).invoke(annotation11, new Object[0]);
                        if (str3 != null && str3.length() > 0) {
                            databaseFieldConfig.setForeignCollectionColumnName(str3);
                        }
                        Object invoke = annotation11.getClass().getMethod("fetch", new Class[0]).invoke(annotation11, new Object[0]);
                        if (invoke != null && invoke.toString().equals("EAGER")) {
                            databaseFieldConfig.setForeignCollectionEager(true);
                        }
                    } catch (Exception e3) {
                        throw SqlExceptionUtil.create("Problem accessing fields from the @JoinColumn annotation for field " + field, e3);
                    }
                }
            } else {
                databaseFieldConfig.setForeign(true);
                if (annotation11 != null) {
                    try {
                        String str4 = (String) annotation11.getClass().getMethod("name", new Class[0]).invoke(annotation11, new Object[0]);
                        if (str4 != null && str4.length() > 0) {
                            databaseFieldConfig.setColumnName(str4);
                        }
                        Boolean bool4 = (Boolean) annotation11.getClass().getMethod("nullable", new Class[0]).invoke(annotation11, new Object[0]);
                        if (bool4 != null) {
                            databaseFieldConfig.setCanBeNull(bool4.booleanValue());
                        }
                        Boolean bool5 = (Boolean) annotation11.getClass().getMethod(DatabaseFieldConfigLoader.FIELD_NAME_UNIQUE, new Class[0]).invoke(annotation11, new Object[0]);
                        if (bool5 != null) {
                            databaseFieldConfig.setUnique(bool5.booleanValue());
                        }
                    } catch (Exception e4) {
                        throw SqlExceptionUtil.create("Problem accessing fields from the @JoinColumn annotation for field " + field, e4);
                    }
                }
            }
        }
        if (annotation2 != null) {
            try {
                Object invoke2 = annotation2.getClass().getMethod("value", new Class[0]).invoke(annotation2, new Object[0]);
                databaseFieldConfig.setDataType((invoke2 == null || !invoke2.toString().equals("STRING")) ? DataType.ENUM_INTEGER : DataType.ENUM_STRING);
            } catch (Exception e5) {
                throw SqlExceptionUtil.create("Problem accessing fields from the @Enumerated annotation for field " + field, e5);
            }
        }
        if (annotation != null) {
            databaseFieldConfig.setVersion(true);
        }
        if (databaseFieldConfig.getDataPersister() == null) {
            databaseFieldConfig.setDataPersister(DataPersisterManager.lookupForField(field));
        }
        boolean z = false;
        if (DatabaseFieldConfig.findGetMethod(field, false) != null && DatabaseFieldConfig.findSetMethod(field, false) != null) {
            z = true;
        }
        databaseFieldConfig.setUseGetSet(z);
        return databaseFieldConfig;
    }

    public static String getEntityName(Class<?> cls) {
        Annotation[] annotations;
        Annotation annotation = null;
        for (Annotation annotation2 : cls.getAnnotations()) {
            if (annotation2.annotationType().getName().equals("javax.persistence.Entity")) {
                annotation = annotation2;
            }
        }
        if (annotation == null) {
            return null;
        }
        try {
            String str = (String) annotation.getClass().getMethod("name", new Class[0]).invoke(annotation, new Object[0]);
            if (str != null) {
                if (str.length() > 0) {
                    return str;
                }
            }
            return null;
        } catch (Exception e) {
            throw new IllegalStateException("Could not get entity name from class " + cls, e);
        }
    }
}
