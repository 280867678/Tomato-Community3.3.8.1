package com.j256.ormlite.field.types;

import com.j256.ormlite.field.SqlType;

/* loaded from: classes3.dex */
public class IntType extends IntegerObjectType {
    public static final IntType singleTon = new IntType();

    public IntType() {
        super(SqlType.INTEGER, new Class[]{Integer.TYPE});
    }

    public IntType(SqlType sqlType, Class<?>[] clsArr) {
        super(sqlType, clsArr);
    }

    public static IntType getSingleton() {
        return singleTon;
    }

    @Override // com.j256.ormlite.field.types.BaseDataType, com.j256.ormlite.field.DataPersister
    public boolean isPrimitive() {
        return true;
    }
}
