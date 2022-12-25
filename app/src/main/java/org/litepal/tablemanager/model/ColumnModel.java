package org.litepal.tablemanager.model;

import android.text.TextUtils;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.j256.ormlite.field.FieldType;

/* loaded from: classes4.dex */
public class ColumnModel {
    private String columnName;
    private String columnType;
    private boolean isNullable = true;
    private boolean isUnique = false;
    private String defaultValue = "";

    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String str) {
        this.columnName = str;
    }

    public String getColumnType() {
        return this.columnType;
    }

    public void setColumnType(String str) {
        this.columnType = str;
    }

    public boolean isNullable() {
        return this.isNullable;
    }

    public void setNullable(boolean z) {
        this.isNullable = z;
    }

    public boolean isUnique() {
        return this.isUnique;
    }

    public void setUnique(boolean z) {
        this.isUnique = z;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String str) {
        if ("text".equalsIgnoreCase(this.columnType)) {
            if (TextUtils.isEmpty(str)) {
                return;
            }
            this.defaultValue = "'" + str + "'";
            return;
        }
        this.defaultValue = str;
    }

    public boolean isIdColumn() {
        return FieldType.FOREIGN_ID_FIELD_SUFFIX.equalsIgnoreCase(this.columnName) || DatabaseFieldConfigLoader.FIELD_NAME_ID.equalsIgnoreCase(this.columnName);
    }
}
