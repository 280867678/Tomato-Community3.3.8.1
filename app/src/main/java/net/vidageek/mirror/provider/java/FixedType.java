package net.vidageek.mirror.provider.java;

/* loaded from: classes4.dex */
enum FixedType {
    VOID(Void.TYPE),
    BOOLEAN(Boolean.TYPE),
    BYTE(Byte.TYPE),
    SHORT(Short.TYPE),
    CHAR(Character.TYPE),
    INT(Integer.TYPE),
    LONG(Long.TYPE),
    FLOAT(Float.TYPE),
    DOUBLE(Double.TYPE);
    
    private final Class<?> clazz;

    FixedType(Class cls) {
        this.clazz = cls;
    }

    public static Class<?> fromValue(String str) {
        FixedType[] values;
        for (FixedType fixedType : values()) {
            if (fixedType.clazz.toString().equals(str)) {
                return fixedType.clazz;
            }
        }
        return null;
    }
}
