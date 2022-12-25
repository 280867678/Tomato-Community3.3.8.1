package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.databind.type.TypeBindings;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class JavaType extends ResolvedType implements Serializable, Type {
    private static final long serialVersionUID = 1;
    protected final boolean _asStatic;
    protected final Class<?> _class;
    protected final int _hash;
    protected final Object _typeHandler;
    protected final Object _valueHandler;

    @Deprecated
    protected abstract JavaType _narrow(Class<?> cls);

    @Override // com.fasterxml.jackson.core.type.ResolvedType
    /* renamed from: containedType  reason: collision with other method in class */
    public abstract JavaType mo6171containedType(int i);

    @Override // com.fasterxml.jackson.core.type.ResolvedType
    public abstract int containedTypeCount();

    @Override // com.fasterxml.jackson.core.type.ResolvedType
    @Deprecated
    public abstract String containedTypeName(int i);

    public abstract boolean equals(Object obj);

    public abstract JavaType findSuperType(Class<?> cls);

    public abstract JavaType[] findTypeParameters(Class<?> cls);

    public abstract TypeBindings getBindings();

    @Override // com.fasterxml.jackson.core.type.ResolvedType
    /* renamed from: getContentType  reason: collision with other method in class */
    public JavaType mo6160getContentType() {
        return null;
    }

    public Object getContentTypeHandler() {
        return null;
    }

    public Object getContentValueHandler() {
        return null;
    }

    public abstract StringBuilder getErasedSignature(StringBuilder sb);

    public abstract StringBuilder getGenericSignature(StringBuilder sb);

    public abstract List<JavaType> getInterfaces();

    @Override // com.fasterxml.jackson.core.type.ResolvedType
    /* renamed from: getKeyType  reason: collision with other method in class */
    public JavaType mo6146getKeyType() {
        return null;
    }

    @Override // com.fasterxml.jackson.core.type.ResolvedType
    @Deprecated
    public Class<?> getParameterSource() {
        return null;
    }

    @Override // com.fasterxml.jackson.core.type.ResolvedType
    /* renamed from: getReferencedType  reason: collision with other method in class */
    public JavaType mo6161getReferencedType() {
        return null;
    }

    public abstract JavaType getSuperClass();

    public boolean hasContentType() {
        return true;
    }

    @Override // com.fasterxml.jackson.core.type.ResolvedType
    public boolean isArrayType() {
        return false;
    }

    @Override // com.fasterxml.jackson.core.type.ResolvedType
    public boolean isCollectionLikeType() {
        return false;
    }

    @Override // com.fasterxml.jackson.core.type.ResolvedType
    public abstract boolean isContainerType();

    @Override // com.fasterxml.jackson.core.type.ResolvedType
    public boolean isMapLikeType() {
        return false;
    }

    public abstract JavaType refine(Class<?> cls, TypeBindings typeBindings, JavaType javaType, JavaType[] javaTypeArr);

    public abstract String toString();

    public abstract JavaType withContentType(JavaType javaType);

    /* renamed from: withContentTypeHandler */
    public abstract JavaType mo6162withContentTypeHandler(Object obj);

    /* renamed from: withContentValueHandler */
    public abstract JavaType mo6167withContentValueHandler(Object obj);

    /* renamed from: withStaticTyping */
    public abstract JavaType mo6168withStaticTyping();

    /* renamed from: withTypeHandler */
    public abstract JavaType mo6169withTypeHandler(Object obj);

    /* renamed from: withValueHandler */
    public abstract JavaType mo6170withValueHandler(Object obj);

    /* JADX INFO: Access modifiers changed from: protected */
    public JavaType(Class<?> cls, int i, Object obj, Object obj2, boolean z) {
        this._class = cls;
        this._hash = cls.getName().hashCode() + i;
        this._valueHandler = obj;
        this._typeHandler = obj2;
        this._asStatic = z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public JavaType(JavaType javaType) {
        this._class = javaType._class;
        this._hash = javaType._hash;
        this._valueHandler = javaType._valueHandler;
        this._typeHandler = javaType._typeHandler;
        this._asStatic = javaType._asStatic;
    }

    public JavaType withHandlersFrom(JavaType javaType) {
        Object typeHandler = javaType.getTypeHandler();
        JavaType mo6169withTypeHandler = typeHandler != this._typeHandler ? mo6169withTypeHandler(typeHandler) : this;
        Object valueHandler = javaType.getValueHandler();
        return valueHandler != this._valueHandler ? mo6169withTypeHandler.mo6170withValueHandler(valueHandler) : mo6169withTypeHandler;
    }

    @Deprecated
    public JavaType forcedNarrowBy(Class<?> cls) {
        return cls == this._class ? this : _narrow(cls);
    }

    @Override // com.fasterxml.jackson.core.type.ResolvedType
    public final Class<?> getRawClass() {
        return this._class;
    }

    @Override // com.fasterxml.jackson.core.type.ResolvedType
    public final boolean hasRawClass(Class<?> cls) {
        return this._class == cls;
    }

    public final boolean isTypeOrSubTypeOf(Class<?> cls) {
        Class<?> cls2 = this._class;
        return cls2 == cls || cls.isAssignableFrom(cls2);
    }

    public final boolean isTypeOrSuperTypeOf(Class<?> cls) {
        Class<?> cls2 = this._class;
        return cls2 == cls || cls2.isAssignableFrom(cls);
    }

    @Override // com.fasterxml.jackson.core.type.ResolvedType
    public boolean isAbstract() {
        return Modifier.isAbstract(this._class.getModifiers());
    }

    @Override // com.fasterxml.jackson.core.type.ResolvedType
    public boolean isConcrete() {
        if ((this._class.getModifiers() & 1536) == 0) {
            return true;
        }
        return this._class.isPrimitive();
    }

    @Override // com.fasterxml.jackson.core.type.ResolvedType
    public boolean isThrowable() {
        return Throwable.class.isAssignableFrom(this._class);
    }

    @Override // com.fasterxml.jackson.core.type.ResolvedType
    public final boolean isEnumType() {
        return this._class.isEnum();
    }

    @Override // com.fasterxml.jackson.core.type.ResolvedType
    public final boolean isInterface() {
        return this._class.isInterface();
    }

    @Override // com.fasterxml.jackson.core.type.ResolvedType
    public final boolean isPrimitive() {
        return this._class.isPrimitive();
    }

    @Override // com.fasterxml.jackson.core.type.ResolvedType
    public final boolean isFinal() {
        return Modifier.isFinal(this._class.getModifiers());
    }

    public final boolean isJavaLangObject() {
        return this._class == Object.class;
    }

    public final boolean useStaticType() {
        return this._asStatic;
    }

    @Override // com.fasterxml.jackson.core.type.ResolvedType
    public boolean hasGenericTypes() {
        return containedTypeCount() > 0;
    }

    public JavaType containedTypeOrUnknown(int i) {
        JavaType mo6171containedType = mo6171containedType(i);
        return mo6171containedType == null ? TypeFactory.unknownType() : mo6171containedType;
    }

    public <T> T getValueHandler() {
        return (T) this._valueHandler;
    }

    public <T> T getTypeHandler() {
        return (T) this._typeHandler;
    }

    public boolean hasValueHandler() {
        return this._valueHandler != null;
    }

    public boolean hasHandlers() {
        return (this._typeHandler == null && this._valueHandler == null) ? false : true;
    }

    public String getGenericSignature() {
        StringBuilder sb = new StringBuilder(40);
        getGenericSignature(sb);
        return sb.toString();
    }

    public String getErasedSignature() {
        StringBuilder sb = new StringBuilder(40);
        getErasedSignature(sb);
        return sb.toString();
    }

    public final int hashCode() {
        return this._hash;
    }
}
