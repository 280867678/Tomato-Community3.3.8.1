package com.tomatolive.library.utils.litepal.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.j256.ormlite.field.FieldType;
import com.tomatolive.library.utils.litepal.exceptions.DatabaseGenerateException;
import com.tomatolive.library.utils.litepal.tablemanager.model.ColumnModel;
import com.tomatolive.library.utils.litepal.tablemanager.model.TableModel;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public class DBUtility {
    private static final String KEYWORDS_COLUMN_SUFFIX = "_lpcolumn";
    private static final String REG_COLLECTION = "\\s+(not\\s+)?(in)\\s*\\(";
    private static final String REG_FUZZY = "\\s+(not\\s+)?(like|between)\\s+";
    private static final String REG_OPERATOR = "\\s*(=|!=|<>|<|>)";
    private static final String SQLITE_KEYWORDS = ",abort,add,after,all,alter,and,as,asc,autoincrement,before,begin,between,by,cascade,check,collate,column,commit,conflict,constraint,create,cross,database,deferrable,deferred,delete,desc,distinct,drop,each,end,escape,except,exclusive,exists,foreign,from,glob,group,having,in,index,inner,insert,intersect,into,is,isnull,join,like,limit,match,natural,not,notnull,null,of,offset,on,or,order,outer,plan,pragma,primary,query,raise,references,regexp,reindex,release,rename,replace,restrict,right,rollback,row,savepoint,select,set,table,temp,temporary,then,to,transaction,trigger,union,unique,update,using,vacuum,values,view,virtual,when,where,";
    private static final String TAG = "DBUtility";

    private DBUtility() {
    }

    public static String getTableNameByClassName(String str) {
        if (TextUtils.isEmpty(str) || '.' == str.charAt(str.length() - 1)) {
            return null;
        }
        return str.substring(str.lastIndexOf(".") + 1);
    }

    public static List<String> getTableNameListByClassNameList(List<String> list) {
        ArrayList arrayList = new ArrayList();
        if (list != null && !list.isEmpty()) {
            for (String str : list) {
                arrayList.add(getTableNameByClassName(str));
            }
        }
        return arrayList;
    }

    public static String getTableNameByForeignColumn(String str) {
        if (TextUtils.isEmpty(str) || !str.toLowerCase(Locale.US).endsWith(FieldType.FOREIGN_ID_FIELD_SUFFIX)) {
            return null;
        }
        return str.substring(0, str.length() - 3);
    }

    public static String getIntermediateTableName(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        if (str.toLowerCase(Locale.US).compareTo(str2.toLowerCase(Locale.US)) <= 0) {
            return str + "_" + str2;
        }
        return str2 + "_" + str;
    }

    public static String getGenericTableName(String str, String str2) {
        String tableNameByClassName = getTableNameByClassName(str);
        return BaseUtility.changeCase(tableNameByClassName + "_" + str2);
    }

    public static String getGenericValueIdColumnName(String str) {
        return BaseUtility.changeCase(getTableNameByClassName(str) + FieldType.FOREIGN_ID_FIELD_SUFFIX);
    }

    public static String getM2MSelfRefColumnName(Field field) {
        return BaseUtility.changeCase(field.getName() + FieldType.FOREIGN_ID_FIELD_SUFFIX);
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x003d, code lost:
        if (r0.getInt(r0.getColumnIndexOrThrow("type")) != 1) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x003f, code lost:
        if (r0 == null) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0041, code lost:
        r0.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0044, code lost:
        return true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean isIntermediateTable(String str, SQLiteDatabase sQLiteDatabase) {
        if (TextUtils.isEmpty(str) || !str.matches("[0-9a-zA-Z]+_[0-9a-zA-Z]+")) {
            return false;
        }
        Cursor cursor = null;
        try {
            try {
                cursor = sQLiteDatabase.query("table_schema", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    while (true) {
                        if (!str.equalsIgnoreCase(cursor.getString(cursor.getColumnIndexOrThrow("name")))) {
                            if (!cursor.moveToNext()) {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
                if (cursor == null) {
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (cursor == null) {
                    return false;
                }
            }
            cursor.close();
            return false;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x003d, code lost:
        if (r0.getInt(r0.getColumnIndexOrThrow("type")) != 2) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0040, code lost:
        if (r0 == null) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0042, code lost:
        r0.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0045, code lost:
        return true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean isGenericTable(String str, SQLiteDatabase sQLiteDatabase) {
        if (TextUtils.isEmpty(str) || !str.matches("[0-9a-zA-Z]+_[0-9a-zA-Z]+")) {
            return false;
        }
        Cursor cursor = null;
        try {
            try {
                cursor = sQLiteDatabase.query("table_schema", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    while (true) {
                        if (!str.equalsIgnoreCase(cursor.getString(cursor.getColumnIndexOrThrow("name")))) {
                            if (!cursor.moveToNext()) {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
                if (cursor == null) {
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (cursor == null) {
                    return false;
                }
            }
            cursor.close();
            return false;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    public static boolean isTableExists(String str, SQLiteDatabase sQLiteDatabase) {
        try {
            return BaseUtility.containsIgnoreCases(findAllTableNames(sQLiteDatabase), str);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0056, code lost:
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0053, code lost:
        if (r0 == null) goto L19;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean isColumnExists(String str, String str2, SQLiteDatabase sQLiteDatabase) {
        boolean z = false;
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return false;
        }
        Cursor cursor = null;
        try {
            try {
                cursor = sQLiteDatabase.rawQuery("pragma table_info(" + str2 + ")", null);
                if (cursor.moveToFirst()) {
                    while (true) {
                        if (!str.equalsIgnoreCase(cursor.getString(cursor.getColumnIndexOrThrow("name")))) {
                            if (!cursor.moveToNext()) {
                                break;
                            }
                        } else {
                            z = true;
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0033 A[DONT_GENERATE] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static List<String> findAllTableNames(SQLiteDatabase sQLiteDatabase) {
        ArrayList arrayList = new ArrayList();
        Cursor cursor = null;
        try {
            try {
                cursor = sQLiteDatabase.rawQuery("select * from sqlite_master where type = ?", new String[]{"table"});
                if (!cursor.moveToFirst()) {
                    return arrayList;
                }
                do {
                    String string = cursor.getString(cursor.getColumnIndexOrThrow("tbl_name"));
                    if (!arrayList.contains(string)) {
                        arrayList.add(string);
                    }
                } while (cursor.moveToNext());
                return arrayList;
            } catch (Exception e) {
                e.printStackTrace();
                throw new DatabaseGenerateException(e.getMessage());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x008d A[DONT_GENERATE] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static TableModel findPragmaTableInfo(String str, SQLiteDatabase sQLiteDatabase) {
        if (isTableExists(str, sQLiteDatabase)) {
            List<String> findUniqueColumns = findUniqueColumns(str, sQLiteDatabase);
            TableModel tableModel = new TableModel();
            tableModel.setTableName(str);
            Cursor cursor = null;
            try {
                try {
                    cursor = sQLiteDatabase.rawQuery("pragma table_info(" + str + ")", null);
                    if (!cursor.moveToFirst()) {
                        return tableModel;
                    }
                    do {
                        ColumnModel columnModel = new ColumnModel();
                        String string = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                        String string2 = cursor.getString(cursor.getColumnIndexOrThrow("type"));
                        boolean z = true;
                        if (cursor.getInt(cursor.getColumnIndexOrThrow("notnull")) == 1) {
                            z = false;
                        }
                        boolean contains = findUniqueColumns.contains(string);
                        String string3 = cursor.getString(cursor.getColumnIndexOrThrow("dflt_value"));
                        columnModel.setColumnName(string);
                        columnModel.setColumnType(string2);
                        columnModel.setNullable(z);
                        columnModel.setUnique(contains);
                        String str2 = "";
                        if (string3 != null) {
                            str2 = string3.replace("'", str2);
                        }
                        columnModel.setDefaultValue(str2);
                        tableModel.addColumnModel(columnModel);
                    } while (cursor.moveToNext());
                    return tableModel;
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new DatabaseGenerateException(e.getMessage());
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        throw new DatabaseGenerateException("Table doesn't exist when executing " + str);
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00a1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static List<String> findUniqueColumns(String str, SQLiteDatabase sQLiteDatabase) {
        Cursor cursor;
        Cursor cursor2;
        ArrayList arrayList = new ArrayList();
        Cursor cursor3 = null;
        try {
            cursor = sQLiteDatabase.rawQuery("pragma index_list(" + str + ")", null);
            try {
                if (cursor.moveToFirst()) {
                    cursor2 = null;
                    do {
                        try {
                            if (cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseFieldConfigLoader.FIELD_NAME_UNIQUE)) == 1) {
                                cursor2 = sQLiteDatabase.rawQuery("pragma index_info(" + cursor.getString(cursor.getColumnIndexOrThrow("name")) + ")", null);
                                if (cursor2.moveToFirst()) {
                                    arrayList.add(cursor2.getString(cursor2.getColumnIndexOrThrow("name")));
                                }
                            }
                        } catch (Exception e) {
                            e = e;
                            cursor3 = cursor;
                            try {
                                e.printStackTrace();
                                throw new DatabaseGenerateException(e.getMessage());
                            } catch (Throwable th) {
                                th = th;
                                cursor = cursor3;
                                if (cursor != null) {
                                    cursor.close();
                                }
                                if (cursor2 != null) {
                                    cursor2.close();
                                }
                                throw th;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            if (cursor != null) {
                            }
                            if (cursor2 != null) {
                            }
                            throw th;
                        }
                    } while (cursor.moveToNext());
                    cursor3 = cursor2;
                }
                if (cursor != null) {
                    cursor.close();
                }
                if (cursor3 != null) {
                    cursor3.close();
                }
                return arrayList;
            } catch (Exception e2) {
                e = e2;
                cursor2 = null;
            } catch (Throwable th3) {
                th = th3;
                cursor2 = null;
            }
        } catch (Exception e3) {
            e = e3;
            cursor2 = null;
        } catch (Throwable th4) {
            th = th4;
            cursor = null;
            cursor2 = null;
        }
    }

    public static boolean isFieldNameConflictWithSQLiteKeywords(String str) {
        if (!TextUtils.isEmpty(str)) {
            StringBuilder sb = new StringBuilder();
            sb.append(",");
            sb.append(str.toLowerCase(Locale.US));
            sb.append(",");
            return SQLITE_KEYWORDS.contains(sb.toString());
        }
        return false;
    }

    public static String convertToValidColumnName(String str) {
        if (isFieldNameConflictWithSQLiteKeywords(str)) {
            return str + KEYWORDS_COLUMN_SUFFIX;
        }
        return str;
    }

    public static String convertWhereClauseToColumnName(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                StringBuffer stringBuffer = new StringBuffer();
                Matcher matcher = Pattern.compile("(\\w+\\s*(=|!=|<>|<|>)|\\w+\\s+(not\\s+)?(like|between)\\s+|\\w+\\s+(not\\s+)?(in)\\s*\\()").matcher(str);
                while (matcher.find()) {
                    String group = matcher.group();
                    String replaceAll = group.replaceAll("(\\s*(=|!=|<>|<|>)|\\s+(not\\s+)?(like|between)\\s+|\\s+(not\\s+)?(in)\\s*\\()", "");
                    String replace = group.replace(replaceAll, "");
                    String convertToValidColumnName = convertToValidColumnName(replaceAll);
                    matcher.appendReplacement(stringBuffer, convertToValidColumnName + replace);
                }
                matcher.appendTail(stringBuffer);
                return stringBuffer.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return str;
    }

    public static String[] convertSelectClauseToValidNames(String[] strArr) {
        if (strArr == null || strArr.length <= 0) {
            return null;
        }
        String[] strArr2 = new String[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            strArr2[i] = convertToValidColumnName(strArr[i]);
        }
        return strArr2;
    }

    public static String convertOrderByClauseToValidName(String str) {
        if (!TextUtils.isEmpty(str)) {
            String lowerCase = str.trim().toLowerCase(Locale.US);
            if (lowerCase.contains(",")) {
                String[] split = lowerCase.split(",");
                StringBuilder sb = new StringBuilder();
                int length = split.length;
                int i = 0;
                boolean z = false;
                while (i < length) {
                    String str2 = split[i];
                    if (z) {
                        sb.append(",");
                    }
                    sb.append(convertOrderByItem(str2));
                    i++;
                    z = true;
                }
                return sb.toString();
            }
            return convertOrderByItem(lowerCase);
        }
        return null;
    }

    private static String convertOrderByItem(String str) {
        String str2 = "";
        if (str.endsWith("asc")) {
            str = str.replace("asc", str2).trim();
            str2 = " asc";
        } else if (str.endsWith("desc")) {
            str = str.replace("desc", str2).trim();
            str2 = " desc";
        }
        return convertToValidColumnName(str) + str2;
    }
}
