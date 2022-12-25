package org.xutils.p148db.table;

import android.database.Cursor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.xutils.common.util.LogUtil;
import org.xutils.p148db.annotation.Column;
import org.xutils.p148db.converter.ColumnConverter;
import org.xutils.p148db.converter.ColumnConverterFactory;
import org.xutils.p148db.sqlite.ColumnDbType;

/* renamed from: org.xutils.db.table.ColumnEntity */
/* loaded from: classes4.dex */
public final class ColumnEntity {
    protected final ColumnConverter columnConverter;
    protected final Field columnField;
    protected final Method getMethod;
    private final boolean isAutoId;
    private final boolean isId;
    protected final String name;
    private final String property;
    protected final Method setMethod;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ColumnEntity(Class<?> cls, Field field, Column column) {
        field.setAccessible(true);
        this.columnField = field;
        this.name = column.name();
        this.property = column.property();
        this.isId = column.isId();
        Class<?> type = field.getType();
        this.isAutoId = this.isId && column.autoGen() && ColumnUtils.isAutoIdType(type);
        this.columnConverter = ColumnConverterFactory.getColumnConverter(type);
        this.getMethod = ColumnUtils.findGetMethod(cls, field);
        Method method = this.getMethod;
        if (method != null && !method.isAccessible()) {
            this.getMethod.setAccessible(true);
        }
        this.setMethod = ColumnUtils.findSetMethod(cls, field);
        Method method2 = this.setMethod;
        if (method2 == null || method2.isAccessible()) {
            return;
        }
        this.setMethod.setAccessible(true);
    }

    public void setValueFromCursor(Object obj, Cursor cursor, int i) {
        Object mo6858getFieldValue = this.columnConverter.mo6858getFieldValue(cursor, i);
        if (mo6858getFieldValue == null) {
            return;
        }
        Method method = this.setMethod;
        if (method != null) {
            try {
                method.invoke(obj, mo6858getFieldValue);
                return;
            } catch (Throwable th) {
                LogUtil.m43e(th.getMessage(), th);
                return;
            }
        }
        try {
            this.columnField.set(obj, mo6858getFieldValue);
        } catch (Throwable th2) {
            LogUtil.m43e(th2.getMessage(), th2);
        }
    }

    public Object getColumnValue(Object obj) {
        Object fieldValue = getFieldValue(obj);
        if (!this.isAutoId || (!fieldValue.equals(0L) && !fieldValue.equals(0))) {
            return this.columnConverter.fieldValue2DbValue(fieldValue);
        }
        return null;
    }

    public void setAutoIdValue(Object obj, long j) {
        Object valueOf = Long.valueOf(j);
        if (ColumnUtils.isInteger(this.columnField.getType())) {
            valueOf = Integer.valueOf((int) j);
        }
        Method method = this.setMethod;
        if (method != null) {
            try {
                method.invoke(obj, valueOf);
                return;
            } catch (Throwable th) {
                LogUtil.m43e(th.getMessage(), th);
                return;
            }
        }
        try {
            this.columnField.set(obj, valueOf);
        } catch (Throwable th2) {
            LogUtil.m43e(th2.getMessage(), th2);
        }
    }

    public Object getFieldValue(Object obj) {
        if (obj != null) {
            Method method = this.getMethod;
            if (method != null) {
                try {
                    return method.invoke(obj, new Object[0]);
                } catch (Throwable th) {
                    LogUtil.m43e(th.getMessage(), th);
                }
            } else {
                try {
                    return this.columnField.get(obj);
                } catch (Throwable th2) {
                    LogUtil.m43e(th2.getMessage(), th2);
                }
            }
        }
        return null;
    }

    public String getName() {
        return this.name;
    }

    public String getProperty() {
        return this.property;
    }

    public boolean isId() {
        return this.isId;
    }

    public boolean isAutoId() {
        return this.isAutoId;
    }

    public Field getColumnField() {
        return this.columnField;
    }

    public ColumnConverter getColumnConverter() {
        return this.columnConverter;
    }

    public ColumnDbType getColumnDbType() {
        return this.columnConverter.getColumnDbType();
    }

    public String toString() {
        return this.name;
    }
}
