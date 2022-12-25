package com.j256.ormlite.field.types;

import com.j256.ormlite.field.SqlType;

/* loaded from: classes3.dex */
public class FloatType extends FloatObjectType {
    public static final FloatType singleTon = new FloatType();

    public FloatType() {
        super(SqlType.FLOAT, new Class[]{Float.TYPE});
    }

    public FloatType(SqlType sqlType, Class<?>[] clsArr) {
        super(sqlType, clsArr);
    }

    public static FloatType getSingleton() {
        return singleTon;
    }

    @Override // com.j256.ormlite.field.types.BaseDataType, com.j256.ormlite.field.DataPersister
    public boolean isPrimitive() {
        return true;
    }
}
