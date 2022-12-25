package org.xutils.p148db.table;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.LinkedHashMap;
import org.xutils.common.util.LogUtil;
import org.xutils.p148db.annotation.Column;
import org.xutils.p148db.converter.ColumnConverterFactory;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: org.xutils.db.table.TableUtils */
/* loaded from: classes4.dex */
public final class TableUtils {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static synchronized LinkedHashMap<String, ColumnEntity> findColumnMap(Class<?> cls) {
        LinkedHashMap<String, ColumnEntity> linkedHashMap;
        synchronized (TableUtils.class) {
            linkedHashMap = new LinkedHashMap<>();
            addColumns2Map(cls, linkedHashMap);
        }
        return linkedHashMap;
    }

    private static void addColumns2Map(Class<?> cls, HashMap<String, ColumnEntity> hashMap) {
        Field[] declaredFields;
        Column column;
        if (Object.class.equals(cls)) {
            return;
        }
        try {
            for (Field field : cls.getDeclaredFields()) {
                int modifiers = field.getModifiers();
                if (!Modifier.isStatic(modifiers) && !Modifier.isTransient(modifiers) && (column = (Column) field.getAnnotation(Column.class)) != null && ColumnConverterFactory.isSupportColumnConverter(field.getType())) {
                    ColumnEntity columnEntity = new ColumnEntity(cls, field, column);
                    if (!hashMap.containsKey(columnEntity.getName())) {
                        hashMap.put(columnEntity.getName(), columnEntity);
                    }
                }
            }
            addColumns2Map(cls.getSuperclass(), hashMap);
        } catch (Throwable th) {
            LogUtil.m43e(th.getMessage(), th);
        }
    }
}
