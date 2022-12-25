package com.tomatolive.library.utils.litepal;

import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.j256.ormlite.field.FieldType;
import com.tomatolive.library.utils.litepal.annotation.Column;
import com.tomatolive.library.utils.litepal.crud.LitePalSupport;
import com.tomatolive.library.utils.litepal.crud.model.AssociationsInfo;
import com.tomatolive.library.utils.litepal.exceptions.DatabaseGenerateException;
import com.tomatolive.library.utils.litepal.parser.LitePalAttr;
import com.tomatolive.library.utils.litepal.tablemanager.model.AssociationsModel;
import com.tomatolive.library.utils.litepal.tablemanager.model.ColumnModel;
import com.tomatolive.library.utils.litepal.tablemanager.model.GenericModel;
import com.tomatolive.library.utils.litepal.tablemanager.model.TableModel;
import com.tomatolive.library.utils.litepal.tablemanager.typechange.BlobOrm;
import com.tomatolive.library.utils.litepal.tablemanager.typechange.BooleanOrm;
import com.tomatolive.library.utils.litepal.tablemanager.typechange.DateOrm;
import com.tomatolive.library.utils.litepal.tablemanager.typechange.DecimalOrm;
import com.tomatolive.library.utils.litepal.tablemanager.typechange.NumericOrm;
import com.tomatolive.library.utils.litepal.tablemanager.typechange.OrmChange;
import com.tomatolive.library.utils.litepal.tablemanager.typechange.TextOrm;
import com.tomatolive.library.utils.litepal.util.BaseUtility;
import com.tomatolive.library.utils.litepal.util.DBUtility;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes4.dex */
public abstract class LitePalBase {
    private static final int GET_ASSOCIATIONS_ACTION = 1;
    private static final int GET_ASSOCIATION_INFO_ACTION = 2;
    public static final String TAG = "LitePalBase";
    private Collection<AssociationsInfo> mAssociationInfos;
    private Collection<AssociationsModel> mAssociationModels;
    private Collection<GenericModel> mGenericModels;
    private OrmChange[] typeChangeRules = {new NumericOrm(), new TextOrm(), new BooleanOrm(), new DecimalOrm(), new DateOrm(), new BlobOrm()};
    private Map<String, List<Field>> classFieldsMap = new HashMap();
    private Map<String, List<Field>> classGenericFieldsMap = new HashMap();

    /* JADX INFO: Access modifiers changed from: protected */
    public TableModel getTableModel(String str) {
        String tableNameByClassName = DBUtility.getTableNameByClassName(str);
        TableModel tableModel = new TableModel();
        tableModel.setTableName(tableNameByClassName);
        tableModel.setClassName(str);
        for (Field field : getSupportedFields(str)) {
            tableModel.addColumnModel(convertFieldToColumnModel(field));
        }
        return tableModel;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Collection<AssociationsModel> getAssociations(List<String> list) {
        if (this.mAssociationModels == null) {
            this.mAssociationModels = new HashSet();
        }
        if (this.mGenericModels == null) {
            this.mGenericModels = new HashSet();
        }
        this.mAssociationModels.clear();
        this.mGenericModels.clear();
        for (String str : list) {
            analyzeClassFields(str, 1);
        }
        return this.mAssociationModels;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Collection<GenericModel> getGenericModels() {
        return this.mGenericModels;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Collection<AssociationsInfo> getAssociationInfo(String str) {
        if (this.mAssociationInfos == null) {
            this.mAssociationInfos = new HashSet();
        }
        this.mAssociationInfos.clear();
        analyzeClassFields(str, 2);
        return this.mAssociationInfos;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public List<Field> getSupportedFields(String str) {
        List<Field> list = this.classFieldsMap.get(str);
        if (list == null) {
            ArrayList arrayList = new ArrayList();
            try {
                recursiveSupportedFields(Class.forName(str), arrayList);
                this.classFieldsMap.put(str, arrayList);
                return arrayList;
            } catch (ClassNotFoundException unused) {
                throw new DatabaseGenerateException("can not find a class named " + str);
            }
        }
        return list;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public List<Field> getSupportedGenericFields(String str) {
        List<Field> list = this.classGenericFieldsMap.get(str);
        if (list == null) {
            ArrayList arrayList = new ArrayList();
            try {
                recursiveSupportedGenericFields(Class.forName(str), arrayList);
                this.classGenericFieldsMap.put(str, arrayList);
                return arrayList;
            } catch (ClassNotFoundException unused) {
                throw new DatabaseGenerateException("can not find a class named " + str);
            }
        }
        return list;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isCollection(Class<?> cls) {
        return isList(cls) || isSet(cls);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isList(Class<?> cls) {
        return List.class.isAssignableFrom(cls);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSet(Class<?> cls) {
        return Set.class.isAssignableFrom(cls);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isIdColumn(String str) {
        return FieldType.FOREIGN_ID_FIELD_SUFFIX.equalsIgnoreCase(str) || DatabaseFieldConfigLoader.FIELD_NAME_ID.equalsIgnoreCase(str);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getForeignKeyColumnName(String str) {
        return BaseUtility.changeCase(str + FieldType.FOREIGN_ID_FIELD_SUFFIX);
    }

    protected String getColumnType(String str) {
        for (OrmChange ormChange : this.typeChangeRules) {
            String object2Relation = ormChange.object2Relation(str);
            if (object2Relation != null) {
                return object2Relation;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Class<?> getGenericTypeClass(Field field) {
        Type genericType = field.getGenericType();
        if (genericType == null || !(genericType instanceof ParameterizedType)) {
            return null;
        }
        return (Class) ((ParameterizedType) genericType).getActualTypeArguments()[0];
    }

    private void recursiveSupportedFields(Class<?> cls, List<Field> list) {
        if (cls == LitePalSupport.class || cls == Object.class) {
            return;
        }
        Field[] declaredFields = cls.getDeclaredFields();
        if (declaredFields != null && declaredFields.length > 0) {
            for (Field field : declaredFields) {
                Column column = (Column) field.getAnnotation(Column.class);
                if ((column == null || !column.ignore()) && !Modifier.isStatic(field.getModifiers()) && BaseUtility.isFieldTypeSupported(field.getType().getName())) {
                    list.add(field);
                }
            }
        }
        recursiveSupportedFields(cls.getSuperclass(), list);
    }

    private void recursiveSupportedGenericFields(Class<?> cls, List<Field> list) {
        if (cls == LitePalSupport.class || cls == Object.class) {
            return;
        }
        Field[] declaredFields = cls.getDeclaredFields();
        if (declaredFields != null && declaredFields.length > 0) {
            for (Field field : declaredFields) {
                Column column = (Column) field.getAnnotation(Column.class);
                if ((column == null || !column.ignore()) && !Modifier.isStatic(field.getModifiers()) && isCollection(field.getType())) {
                    String genericTypeName = getGenericTypeName(field);
                    if (BaseUtility.isGenericTypeSupported(genericTypeName) || cls.getName().equalsIgnoreCase(genericTypeName)) {
                        list.add(field);
                    }
                }
            }
        }
        recursiveSupportedGenericFields(cls.getSuperclass(), list);
    }

    private void analyzeClassFields(String str, int i) {
        Field[] declaredFields;
        Column column;
        try {
            for (Field field : Class.forName(str).getDeclaredFields()) {
                if (isNonPrimitive(field) && ((column = (Column) field.getAnnotation(Column.class)) == null || !column.ignore())) {
                    oneToAnyConditions(str, field, i);
                    manyToAnyConditions(str, field, i);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new DatabaseGenerateException("can not find a class named " + str);
        }
    }

    private boolean isNonPrimitive(Field field) {
        return !field.getType().isPrimitive();
    }

    private boolean isPrivate(Field field) {
        return Modifier.isPrivate(field.getModifiers());
    }

    private void oneToAnyConditions(String str, Field field, int i) throws ClassNotFoundException {
        Field[] declaredFields;
        Class<?> type = field.getType();
        if (LitePalAttr.getInstance().getClassNames().contains(type.getName())) {
            boolean z = false;
            for (Field field2 : Class.forName(type.getName()).getDeclaredFields()) {
                if (!Modifier.isStatic(field2.getModifiers())) {
                    Class<?> type2 = field2.getType();
                    if (str.equals(type2.getName())) {
                        if (i == 1) {
                            addIntoAssociationModelCollection(str, type.getName(), type.getName(), 1);
                        } else if (i == 2) {
                            addIntoAssociationInfoCollection(str, type.getName(), type.getName(), field, field2, 1);
                        }
                    } else if (isCollection(type2) && str.equals(getGenericTypeName(field2))) {
                        if (i == 1) {
                            addIntoAssociationModelCollection(str, type.getName(), str, 2);
                        } else if (i == 2) {
                            addIntoAssociationInfoCollection(str, type.getName(), str, field, field2, 2);
                        }
                    }
                    z = true;
                }
            }
            if (z) {
                return;
            }
            if (i == 1) {
                addIntoAssociationModelCollection(str, type.getName(), type.getName(), 1);
            } else if (i == 2) {
                addIntoAssociationInfoCollection(str, type.getName(), type.getName(), field, null, 1);
            }
        }
    }

    private void manyToAnyConditions(String str, Field field, int i) throws ClassNotFoundException {
        Field[] declaredFields;
        if (isCollection(field.getType())) {
            String genericTypeName = getGenericTypeName(field);
            if (LitePalAttr.getInstance().getClassNames().contains(genericTypeName)) {
                boolean z = false;
                for (Field field2 : Class.forName(genericTypeName).getDeclaredFields()) {
                    if (!Modifier.isStatic(field2.getModifiers())) {
                        Class<?> type = field2.getType();
                        if (str.equals(type.getName())) {
                            if (i == 1) {
                                addIntoAssociationModelCollection(str, genericTypeName, genericTypeName, 2);
                            } else if (i == 2) {
                                addIntoAssociationInfoCollection(str, genericTypeName, genericTypeName, field, field2, 2);
                            }
                        } else if (isCollection(type) && str.equals(getGenericTypeName(field2))) {
                            if (i == 1) {
                                if (str.equalsIgnoreCase(genericTypeName)) {
                                    GenericModel genericModel = new GenericModel();
                                    genericModel.setTableName(DBUtility.getGenericTableName(str, field.getName()));
                                    genericModel.setValueColumnName(DBUtility.getM2MSelfRefColumnName(field));
                                    genericModel.setValueColumnType("integer");
                                    genericModel.setValueIdColumnName(DBUtility.getGenericValueIdColumnName(str));
                                    this.mGenericModels.add(genericModel);
                                } else {
                                    addIntoAssociationModelCollection(str, genericTypeName, null, 3);
                                }
                            } else if (i == 2 && !str.equalsIgnoreCase(genericTypeName)) {
                                addIntoAssociationInfoCollection(str, genericTypeName, null, field, field2, 3);
                            }
                        }
                        z = true;
                    }
                }
                if (z) {
                    return;
                }
                if (i == 1) {
                    addIntoAssociationModelCollection(str, genericTypeName, genericTypeName, 2);
                } else if (i == 2) {
                    addIntoAssociationInfoCollection(str, genericTypeName, genericTypeName, field, null, 2);
                }
            } else if (BaseUtility.isGenericTypeSupported(genericTypeName) && i == 1) {
                GenericModel genericModel2 = new GenericModel();
                genericModel2.setTableName(DBUtility.getGenericTableName(str, field.getName()));
                genericModel2.setValueColumnName(DBUtility.convertToValidColumnName(field.getName()));
                genericModel2.setValueColumnType(getColumnType(genericTypeName));
                genericModel2.setValueIdColumnName(DBUtility.getGenericValueIdColumnName(str));
                this.mGenericModels.add(genericModel2);
            }
        }
    }

    private void addIntoAssociationModelCollection(String str, String str2, String str3, int i) {
        AssociationsModel associationsModel = new AssociationsModel();
        associationsModel.setTableName(DBUtility.getTableNameByClassName(str));
        associationsModel.setAssociatedTableName(DBUtility.getTableNameByClassName(str2));
        associationsModel.setTableHoldsForeignKey(DBUtility.getTableNameByClassName(str3));
        associationsModel.setAssociationType(i);
        this.mAssociationModels.add(associationsModel);
    }

    private void addIntoAssociationInfoCollection(String str, String str2, String str3, Field field, Field field2, int i) {
        AssociationsInfo associationsInfo = new AssociationsInfo();
        associationsInfo.setSelfClassName(str);
        associationsInfo.setAssociatedClassName(str2);
        associationsInfo.setClassHoldsForeignKey(str3);
        associationsInfo.setAssociateOtherModelFromSelf(field);
        associationsInfo.setAssociateSelfFromOtherModel(field2);
        associationsInfo.setAssociationType(i);
        this.mAssociationInfos.add(associationsInfo);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getGenericTypeName(Field field) {
        Class<?> genericTypeClass = getGenericTypeClass(field);
        if (genericTypeClass != null) {
            return genericTypeClass.getName();
        }
        return null;
    }

    private ColumnModel convertFieldToColumnModel(Field field) {
        boolean z;
        boolean z2;
        String str;
        String columnType = getColumnType(field.getType().getName());
        Column column = (Column) field.getAnnotation(Column.class);
        if (column != null) {
            z = column.nullable();
            z2 = column.unique();
            str = column.defaultValue();
        } else {
            z = true;
            z2 = false;
            str = "";
        }
        ColumnModel columnModel = new ColumnModel();
        columnModel.setColumnName(DBUtility.convertToValidColumnName(field.getName()));
        columnModel.setColumnType(columnType);
        columnModel.setNullable(z);
        columnModel.setUnique(z2);
        columnModel.setDefaultValue(str);
        return columnModel;
    }
}
