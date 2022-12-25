package com.tomatolive.library.utils.litepal.tablemanager;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.j256.ormlite.field.FieldType;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.litepal.exceptions.DatabaseGenerateException;
import com.tomatolive.library.utils.litepal.tablemanager.model.AssociationsModel;
import com.tomatolive.library.utils.litepal.tablemanager.model.ColumnModel;
import com.tomatolive.library.utils.litepal.tablemanager.model.GenericModel;
import com.tomatolive.library.utils.litepal.util.BaseUtility;
import com.tomatolive.library.utils.litepal.util.DBUtility;
import com.tomatolive.library.utils.litepal.util.LitePalLog;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

/* loaded from: classes4.dex */
public abstract class AssociationCreator extends Generator {
    @Override // com.tomatolive.library.utils.litepal.tablemanager.Generator
    protected abstract void createOrUpgradeTable(SQLiteDatabase sQLiteDatabase, boolean z);

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.utils.litepal.tablemanager.Generator
    public void addOrUpdateAssociation(SQLiteDatabase sQLiteDatabase, boolean z) {
        addAssociations(getAllAssociations(), sQLiteDatabase, z);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String generateCreateTableSQL(String str, List<ColumnModel> list, boolean z) {
        StringBuilder sb = new StringBuilder("create table ");
        sb.append(str);
        sb.append(" (");
        if (z) {
            sb.append("id integer primary key autoincrement,");
        }
        if (isContainsOnlyIdField(list)) {
            sb.deleteCharAt(sb.length() - 1);
        }
        boolean z2 = false;
        for (ColumnModel columnModel : list) {
            if (!columnModel.isIdColumn()) {
                if (z2) {
                    sb.append(", ");
                }
                sb.append(columnModel.getColumnName());
                sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
                sb.append(columnModel.getColumnType());
                if (!columnModel.isNullable()) {
                    sb.append(" not null");
                }
                if (columnModel.isUnique()) {
                    sb.append(" unique");
                }
                String defaultValue = columnModel.getDefaultValue();
                if (!TextUtils.isEmpty(defaultValue)) {
                    sb.append(" default ");
                    sb.append(defaultValue);
                }
                z2 = true;
            }
        }
        sb.append(")");
        LitePalLog.m234d("Generator", "create table sql is >> " + ((Object) sb));
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String generateDropTableSQL(String str) {
        return "drop table if exists " + str;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String generateAddColumnSQL(String str, ColumnModel columnModel) {
        StringBuilder sb = new StringBuilder();
        sb.append("alter table ");
        sb.append(str);
        sb.append(" add column ");
        sb.append(columnModel.getColumnName());
        sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
        sb.append(columnModel.getColumnType());
        if (!columnModel.isNullable()) {
            sb.append(" not null");
        }
        if (columnModel.isUnique()) {
            sb.append(" unique");
        }
        String defaultValue = columnModel.getDefaultValue();
        if (!TextUtils.isEmpty(defaultValue)) {
            sb.append(" default ");
            sb.append(defaultValue);
        } else if (!columnModel.isNullable()) {
            if ("integer".equalsIgnoreCase(columnModel.getColumnType())) {
                defaultValue = "0";
            } else if ("text".equalsIgnoreCase(columnModel.getColumnType())) {
                defaultValue = "''";
            } else if ("real".equalsIgnoreCase(columnModel.getColumnType())) {
                defaultValue = "0.0";
            }
            sb.append(" default ");
            sb.append(defaultValue);
        }
        LitePalLog.m234d("Generator", "add column sql is >> " + ((Object) sb));
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isForeignKeyColumnFormat(String str) {
        return !TextUtils.isEmpty(str) && str.toLowerCase(Locale.US).endsWith(FieldType.FOREIGN_ID_FIELD_SUFFIX) && !str.equalsIgnoreCase(FieldType.FOREIGN_ID_FIELD_SUFFIX);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void giveTableSchemaACopy(String str, int i, SQLiteDatabase sQLiteDatabase) {
        Cursor cursor;
        StringBuilder sb = new StringBuilder("select * from ");
        sb.append("table_schema");
        LitePalLog.m234d("Generator", "giveTableSchemaACopy SQL is >> " + ((Object) sb));
        Cursor cursor2 = null;
        try {
            try {
                cursor = sQLiteDatabase.rawQuery(sb.toString(), null);
            } catch (Throwable th) {
                th = th;
                cursor = cursor2;
            }
        } catch (Exception e) {
            e = e;
        }
        try {
            if (isNeedtoGiveACopy(cursor, str)) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("name", BaseUtility.changeCase(str));
                contentValues.put("type", Integer.valueOf(i));
                sQLiteDatabase.insert("table_schema", null, contentValues);
            }
            if (cursor == null) {
                return;
            }
            cursor.close();
        } catch (Exception e2) {
            e = e2;
            cursor2 = cursor;
            e.printStackTrace();
            if (cursor2 == null) {
                return;
            }
            cursor2.close();
        } catch (Throwable th2) {
            th = th2;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    private boolean isNeedtoGiveACopy(Cursor cursor, String str) {
        return !isValueExists(cursor, str) && !isSpecialTable(str);
    }

    private boolean isValueExists(Cursor cursor, String str) {
        if (cursor.moveToFirst()) {
            while (!cursor.getString(cursor.getColumnIndexOrThrow("name")).equalsIgnoreCase(str)) {
                if (!cursor.moveToNext()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private boolean isSpecialTable(String str) {
        return "table_schema".equalsIgnoreCase(str);
    }

    private void addAssociations(Collection<AssociationsModel> collection, SQLiteDatabase sQLiteDatabase, boolean z) {
        for (AssociationsModel associationsModel : collection) {
            if (2 == associationsModel.getAssociationType() || 1 == associationsModel.getAssociationType()) {
                addForeignKeyColumn(associationsModel.getTableName(), associationsModel.getAssociatedTableName(), associationsModel.getTableHoldsForeignKey(), sQLiteDatabase);
            } else if (3 == associationsModel.getAssociationType()) {
                createIntermediateTable(associationsModel.getTableName(), associationsModel.getAssociatedTableName(), sQLiteDatabase, z);
            }
        }
        for (GenericModel genericModel : getGenericModels()) {
            createGenericTable(genericModel, sQLiteDatabase, z);
        }
    }

    private void createIntermediateTable(String str, String str2, SQLiteDatabase sQLiteDatabase, boolean z) {
        ArrayList arrayList = new ArrayList();
        ColumnModel columnModel = new ColumnModel();
        columnModel.setColumnName(str + FieldType.FOREIGN_ID_FIELD_SUFFIX);
        columnModel.setColumnType("integer");
        ColumnModel columnModel2 = new ColumnModel();
        columnModel2.setColumnName(str2 + FieldType.FOREIGN_ID_FIELD_SUFFIX);
        columnModel2.setColumnType("integer");
        arrayList.add(columnModel);
        arrayList.add(columnModel2);
        String intermediateTableName = DBUtility.getIntermediateTableName(str, str2);
        ArrayList arrayList2 = new ArrayList();
        if (!DBUtility.isTableExists(intermediateTableName, sQLiteDatabase)) {
            arrayList2.add(generateCreateTableSQL(intermediateTableName, arrayList, false));
        } else if (z) {
            arrayList2.add(generateDropTableSQL(intermediateTableName));
            arrayList2.add(generateCreateTableSQL(intermediateTableName, arrayList, false));
        }
        execute(arrayList2, sQLiteDatabase);
        giveTableSchemaACopy(intermediateTableName, 1, sQLiteDatabase);
    }

    private void createGenericTable(GenericModel genericModel, SQLiteDatabase sQLiteDatabase, boolean z) {
        String tableName = genericModel.getTableName();
        String valueColumnName = genericModel.getValueColumnName();
        String valueColumnType = genericModel.getValueColumnType();
        String valueIdColumnName = genericModel.getValueIdColumnName();
        ArrayList arrayList = new ArrayList();
        ColumnModel columnModel = new ColumnModel();
        columnModel.setColumnName(valueColumnName);
        columnModel.setColumnType(valueColumnType);
        ColumnModel columnModel2 = new ColumnModel();
        columnModel2.setColumnName(valueIdColumnName);
        columnModel2.setColumnType("integer");
        arrayList.add(columnModel);
        arrayList.add(columnModel2);
        ArrayList arrayList2 = new ArrayList();
        if (!DBUtility.isTableExists(tableName, sQLiteDatabase)) {
            arrayList2.add(generateCreateTableSQL(tableName, arrayList, false));
        } else if (z) {
            arrayList2.add(generateDropTableSQL(tableName));
            arrayList2.add(generateCreateTableSQL(tableName, arrayList, false));
        }
        execute(arrayList2, sQLiteDatabase);
        giveTableSchemaACopy(tableName, 2, sQLiteDatabase);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void addForeignKeyColumn(String str, String str2, String str3, SQLiteDatabase sQLiteDatabase) {
        if (DBUtility.isTableExists(str, sQLiteDatabase)) {
            if (DBUtility.isTableExists(str2, sQLiteDatabase)) {
                String str4 = null;
                if (str.equals(str3)) {
                    str4 = getForeignKeyColumnName(str2);
                } else if (str2.equals(str3)) {
                    str4 = getForeignKeyColumnName(str);
                }
                if (!DBUtility.isColumnExists(str4, str3, sQLiteDatabase)) {
                    ColumnModel columnModel = new ColumnModel();
                    columnModel.setColumnName(str4);
                    columnModel.setColumnType("integer");
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(generateAddColumnSQL(str3, columnModel));
                    execute(arrayList, sQLiteDatabase);
                    return;
                }
                LitePalLog.m234d("Generator", "column " + str4 + " is already exist, no need to add one");
                return;
            }
            throw new DatabaseGenerateException("Table doesn't exist with the name of " + str2);
        }
        throw new DatabaseGenerateException("Table doesn't exist with the name of " + str);
    }

    private boolean isContainsOnlyIdField(List<ColumnModel> list) {
        return list.size() == 0 || (list.size() == 1 && isIdColumn(list.get(0).getColumnName())) || (list.size() == 2 && isIdColumn(list.get(0).getColumnName()) && isIdColumn(list.get(1).getColumnName()));
    }
}
