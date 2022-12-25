package com.fasterxml.jackson.core.type;

/* loaded from: classes2.dex */
public abstract class ResolvedType {
    /* renamed from: containedType */
    public abstract ResolvedType mo6171containedType(int i);

    public abstract int containedTypeCount();

    public abstract String containedTypeName(int i);

    /* renamed from: getContentType */
    public abstract ResolvedType mo6160getContentType();

    /* renamed from: getKeyType */
    public abstract ResolvedType mo6146getKeyType();

    @Deprecated
    public abstract Class<?> getParameterSource();

    public abstract Class<?> getRawClass();

    /* renamed from: getReferencedType */
    public abstract ResolvedType mo6161getReferencedType();

    public abstract boolean hasGenericTypes();

    public abstract boolean hasRawClass(Class<?> cls);

    public abstract boolean isAbstract();

    public abstract boolean isArrayType();

    public abstract boolean isCollectionLikeType();

    public abstract boolean isConcrete();

    public abstract boolean isContainerType();

    public abstract boolean isEnumType();

    public abstract boolean isFinal();

    public abstract boolean isInterface();

    public abstract boolean isMapLikeType();

    public abstract boolean isPrimitive();

    public abstract boolean isThrowable();

    public abstract String toCanonical();

    public boolean isReferenceType() {
        return mo6161getReferencedType() != null;
    }
}
