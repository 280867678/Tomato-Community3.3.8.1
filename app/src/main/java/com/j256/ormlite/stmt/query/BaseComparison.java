package com.j256.ormlite.stmt.query;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.p075db.DatabaseType;
import com.j256.ormlite.stmt.ArgumentHolder;
import com.j256.ormlite.stmt.ColumnArg;
import com.j256.ormlite.stmt.SelectArg;
import java.sql.SQLException;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class BaseComparison implements Comparison {
    public static final String NUMBER_CHARACTERS = "0123456789.-+";
    public final String columnName;
    public final FieldType fieldType;
    public final Object value;

    public BaseComparison(String str, FieldType fieldType, Object obj, boolean z) {
        if (!z || fieldType == null || fieldType.isComparable()) {
            this.columnName = str;
            this.fieldType = fieldType;
            this.value = obj;
            return;
        }
        throw new SQLException("Field '" + str + "' is of data type " + fieldType.getDataPersister() + " which can not be compared");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x00e0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void appendArgOrValue(DatabaseType databaseType, FieldType fieldType, StringBuilder sb, List<ArgumentHolder> list, Object obj) {
        if (obj == null) {
            throw new SQLException("argument for '" + fieldType.getFieldName() + "' is null");
        }
        boolean z = false;
        if (obj instanceof ArgumentHolder) {
            sb.append('?');
            ArgumentHolder argumentHolder = (ArgumentHolder) obj;
            argumentHolder.setMetaInfo(this.columnName, fieldType);
            list.add(argumentHolder);
        } else if (obj instanceof ColumnArg) {
            ColumnArg columnArg = (ColumnArg) obj;
            String tableName = columnArg.getTableName();
            if (tableName != null) {
                databaseType.appendEscapedEntityName(sb, tableName);
                sb.append('.');
            }
            databaseType.appendEscapedEntityName(sb, columnArg.getColumnName());
        } else if (fieldType.isArgumentHolderRequired()) {
            sb.append('?');
            SelectArg selectArg = new SelectArg();
            selectArg.setMetaInfo(this.columnName, fieldType);
            selectArg.setValue(obj);
            list.add(selectArg);
        } else if (fieldType.isForeign() && fieldType.getType().isAssignableFrom(obj.getClass())) {
            FieldType foreignIdField = fieldType.getForeignIdField();
            appendArgOrValue(databaseType, foreignIdField, sb, list, foreignIdField.extractJavaFieldValue(obj));
            if (z) {
                return;
            }
            sb.append(' ');
            return;
        } else if (fieldType.isEscapedValue()) {
            databaseType.appendEscapedWord(sb, fieldType.convertJavaFieldToSqlArgValue(obj).toString());
        } else if (fieldType.isForeign()) {
            String obj2 = fieldType.convertJavaFieldToSqlArgValue(obj).toString();
            if (obj2.length() > 0 && NUMBER_CHARACTERS.indexOf(obj2.charAt(0)) < 0) {
                throw new SQLException("Foreign field " + fieldType + " does not seem to be producing a numerical value '" + obj2 + "'. Maybe you are passing the wrong object to comparison: " + this);
            }
            sb.append(obj2);
        } else {
            sb.append(fieldType.convertJavaFieldToSqlArgValue(obj));
        }
        z = true;
        if (z) {
        }
    }

    @Override // com.j256.ormlite.stmt.query.Comparison
    public abstract void appendOperation(StringBuilder sb);

    @Override // com.j256.ormlite.stmt.query.Clause
    public void appendSql(DatabaseType databaseType, String str, StringBuilder sb, List<ArgumentHolder> list) {
        if (str != null) {
            databaseType.appendEscapedEntityName(sb, str);
            sb.append('.');
        }
        databaseType.appendEscapedEntityName(sb, this.columnName);
        sb.append(' ');
        appendOperation(sb);
        appendValue(databaseType, sb, list);
    }

    @Override // com.j256.ormlite.stmt.query.Comparison
    public void appendValue(DatabaseType databaseType, StringBuilder sb, List<ArgumentHolder> list) {
        appendArgOrValue(databaseType, this.fieldType, sb, list, this.value);
    }

    @Override // com.j256.ormlite.stmt.query.Comparison
    public String getColumnName() {
        return this.columnName;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.columnName);
        sb.append(' ');
        appendOperation(sb);
        sb.append(' ');
        sb.append(this.value);
        return sb.toString();
    }
}
