package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.databind.JavaType;

/* loaded from: classes2.dex */
public class PlaceholderForType extends TypeBase {
    private static final long serialVersionUID = 1;
    protected JavaType _actualType;
    protected final int _ordinal;

    @Override // com.fasterxml.jackson.databind.JavaType
    public boolean equals(Object obj) {
        return obj == this;
    }

    @Override // com.fasterxml.jackson.databind.JavaType, com.fasterxml.jackson.core.type.ResolvedType
    public boolean isContainerType() {
        return false;
    }

    public PlaceholderForType(int i) {
        super(Object.class, TypeBindings.emptyBindings(), TypeFactory.unknownType(), null, 1, null, null, false);
        this._ordinal = i;
    }

    public JavaType actualType() {
        return this._actualType;
    }

    public void actualType(JavaType javaType) {
        this._actualType = javaType;
    }

    @Override // com.fasterxml.jackson.databind.type.TypeBase
    protected String buildCanonicalName() {
        return toString();
    }

    @Override // com.fasterxml.jackson.databind.type.TypeBase, com.fasterxml.jackson.databind.JavaType
    public StringBuilder getGenericSignature(StringBuilder sb) {
        return getErasedSignature(sb);
    }

    @Override // com.fasterxml.jackson.databind.type.TypeBase, com.fasterxml.jackson.databind.JavaType
    public StringBuilder getErasedSignature(StringBuilder sb) {
        sb.append('$');
        sb.append(this._ordinal + 1);
        return sb;
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    /* renamed from: withTypeHandler */
    public JavaType mo6169withTypeHandler(Object obj) {
        return (JavaType) _unsupported();
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    /* renamed from: withContentTypeHandler */
    public JavaType mo6162withContentTypeHandler(Object obj) {
        return (JavaType) _unsupported();
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    /* renamed from: withValueHandler */
    public JavaType mo6170withValueHandler(Object obj) {
        return (JavaType) _unsupported();
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    /* renamed from: withContentValueHandler */
    public JavaType mo6167withContentValueHandler(Object obj) {
        return (JavaType) _unsupported();
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public JavaType withContentType(JavaType javaType) {
        return (JavaType) _unsupported();
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    /* renamed from: withStaticTyping */
    public JavaType mo6168withStaticTyping() {
        return (JavaType) _unsupported();
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public JavaType refine(Class<?> cls, TypeBindings typeBindings, JavaType javaType, JavaType[] javaTypeArr) {
        return (JavaType) _unsupported();
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    protected JavaType _narrow(Class<?> cls) {
        return (JavaType) _unsupported();
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public String toString() {
        return getErasedSignature(new StringBuilder()).toString();
    }

    private <T> T _unsupported() {
        throw new UnsupportedOperationException("Operation should not be attempted on " + PlaceholderForType.class.getName());
    }
}
