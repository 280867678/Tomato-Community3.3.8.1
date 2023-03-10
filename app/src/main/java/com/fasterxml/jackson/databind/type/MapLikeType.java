package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.databind.JavaType;
import java.lang.reflect.TypeVariable;
import java.util.Map;

/* loaded from: classes2.dex */
public class MapLikeType extends TypeBase {
    private static final long serialVersionUID = 1;
    protected final JavaType _keyType;
    protected final JavaType _valueType;

    @Override // com.fasterxml.jackson.databind.JavaType, com.fasterxml.jackson.core.type.ResolvedType
    public boolean isContainerType() {
        return true;
    }

    @Override // com.fasterxml.jackson.databind.JavaType, com.fasterxml.jackson.core.type.ResolvedType
    public boolean isMapLikeType() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public MapLikeType(Class<?> cls, TypeBindings typeBindings, JavaType javaType, JavaType[] javaTypeArr, JavaType javaType2, JavaType javaType3, Object obj, Object obj2, boolean z) {
        super(cls, typeBindings, javaType, javaTypeArr, javaType2.hashCode() ^ javaType3.hashCode(), obj, obj2, z);
        this._keyType = javaType2;
        this._valueType = javaType3;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public MapLikeType(TypeBase typeBase, JavaType javaType, JavaType javaType2) {
        super(typeBase);
        this._keyType = javaType;
        this._valueType = javaType2;
    }

    public static MapLikeType upgradeFrom(JavaType javaType, JavaType javaType2, JavaType javaType3) {
        if (javaType instanceof TypeBase) {
            return new MapLikeType((TypeBase) javaType, javaType2, javaType3);
        }
        throw new IllegalArgumentException("Cannot upgrade from an instance of " + javaType.getClass());
    }

    @Deprecated
    public static MapLikeType construct(Class<?> cls, JavaType javaType, JavaType javaType2) {
        TypeBindings emptyBindings;
        TypeVariable<Class<?>>[] typeParameters = cls.getTypeParameters();
        if (typeParameters == null || typeParameters.length != 2) {
            emptyBindings = TypeBindings.emptyBindings();
        } else {
            emptyBindings = TypeBindings.create(cls, javaType, javaType2);
        }
        return new MapLikeType(cls, emptyBindings, TypeBase._bogusSuperClass(cls), null, javaType, javaType2, null, null, false);
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    @Deprecated
    protected JavaType _narrow(Class<?> cls) {
        return new MapLikeType(cls, this._bindings, this._superClass, this._superInterfaces, this._keyType, this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
    }

    /* renamed from: withKeyType */
    public MapLikeType mo6154withKeyType(JavaType javaType) {
        return javaType == this._keyType ? this : new MapLikeType(this._class, this._bindings, this._superClass, this._superInterfaces, javaType, this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public JavaType withContentType(JavaType javaType) {
        return this._valueType == javaType ? this : new MapLikeType(this._class, this._bindings, this._superClass, this._superInterfaces, this._keyType, javaType, this._valueHandler, this._typeHandler, this._asStatic);
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    /* renamed from: withTypeHandler  reason: collision with other method in class */
    public MapLikeType mo6169withTypeHandler(Object obj) {
        return new MapLikeType(this._class, this._bindings, this._superClass, this._superInterfaces, this._keyType, this._valueType, this._valueHandler, obj, this._asStatic);
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    /* renamed from: withContentTypeHandler  reason: collision with other method in class */
    public MapLikeType mo6162withContentTypeHandler(Object obj) {
        return new MapLikeType(this._class, this._bindings, this._superClass, this._superInterfaces, this._keyType, this._valueType.mo6169withTypeHandler(obj), this._valueHandler, this._typeHandler, this._asStatic);
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    /* renamed from: withValueHandler  reason: collision with other method in class */
    public MapLikeType mo6170withValueHandler(Object obj) {
        return new MapLikeType(this._class, this._bindings, this._superClass, this._superInterfaces, this._keyType, this._valueType, obj, this._typeHandler, this._asStatic);
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    /* renamed from: withContentValueHandler  reason: collision with other method in class */
    public MapLikeType mo6167withContentValueHandler(Object obj) {
        return new MapLikeType(this._class, this._bindings, this._superClass, this._superInterfaces, this._keyType, this._valueType.mo6170withValueHandler(obj), this._valueHandler, this._typeHandler, this._asStatic);
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public JavaType withHandlersFrom(JavaType javaType) {
        JavaType withHandlersFrom;
        JavaType withHandlersFrom2;
        JavaType withHandlersFrom3 = super.withHandlersFrom(javaType);
        JavaType mo6146getKeyType = javaType.mo6146getKeyType();
        if ((withHandlersFrom3 instanceof MapLikeType) && mo6146getKeyType != null && (withHandlersFrom2 = this._keyType.withHandlersFrom(mo6146getKeyType)) != this._keyType) {
            withHandlersFrom3 = ((MapLikeType) withHandlersFrom3).mo6154withKeyType(withHandlersFrom2);
        }
        JavaType mo6160getContentType = javaType.mo6160getContentType();
        return (mo6160getContentType == null || (withHandlersFrom = this._valueType.withHandlersFrom(mo6160getContentType)) == this._valueType) ? withHandlersFrom3 : withHandlersFrom3.withContentType(withHandlersFrom);
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    /* renamed from: withStaticTyping  reason: collision with other method in class */
    public MapLikeType mo6168withStaticTyping() {
        return this._asStatic ? this : new MapLikeType(this._class, this._bindings, this._superClass, this._superInterfaces, this._keyType, this._valueType.mo6168withStaticTyping(), this._valueHandler, this._typeHandler, true);
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public JavaType refine(Class<?> cls, TypeBindings typeBindings, JavaType javaType, JavaType[] javaTypeArr) {
        return new MapLikeType(cls, typeBindings, javaType, javaTypeArr, this._keyType, this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
    }

    @Override // com.fasterxml.jackson.databind.type.TypeBase
    protected String buildCanonicalName() {
        StringBuilder sb = new StringBuilder();
        sb.append(this._class.getName());
        if (this._keyType != null) {
            sb.append('<');
            sb.append(this._keyType.toCanonical());
            sb.append(',');
            sb.append(this._valueType.toCanonical());
            sb.append('>');
        }
        return sb.toString();
    }

    @Override // com.fasterxml.jackson.databind.JavaType, com.fasterxml.jackson.core.type.ResolvedType
    /* renamed from: getKeyType  reason: collision with other method in class */
    public JavaType mo6146getKeyType() {
        return this._keyType;
    }

    @Override // com.fasterxml.jackson.databind.JavaType, com.fasterxml.jackson.core.type.ResolvedType
    /* renamed from: getContentType  reason: collision with other method in class */
    public JavaType mo6160getContentType() {
        return this._valueType;
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public Object getContentValueHandler() {
        return this._valueType.getValueHandler();
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public Object getContentTypeHandler() {
        return this._valueType.getTypeHandler();
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public boolean hasHandlers() {
        return super.hasHandlers() || this._valueType.hasHandlers() || this._keyType.hasHandlers();
    }

    @Override // com.fasterxml.jackson.databind.type.TypeBase, com.fasterxml.jackson.databind.JavaType
    public StringBuilder getErasedSignature(StringBuilder sb) {
        return TypeBase._classSignature(this._class, sb, true);
    }

    @Override // com.fasterxml.jackson.databind.type.TypeBase, com.fasterxml.jackson.databind.JavaType
    public StringBuilder getGenericSignature(StringBuilder sb) {
        TypeBase._classSignature(this._class, sb, false);
        sb.append('<');
        this._keyType.getGenericSignature(sb);
        this._valueType.getGenericSignature(sb);
        sb.append(">;");
        return sb;
    }

    /* renamed from: withKeyTypeHandler */
    public MapLikeType mo6155withKeyTypeHandler(Object obj) {
        return new MapLikeType(this._class, this._bindings, this._superClass, this._superInterfaces, this._keyType.mo6169withTypeHandler(obj), this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
    }

    /* renamed from: withKeyValueHandler */
    public MapLikeType mo6156withKeyValueHandler(Object obj) {
        return new MapLikeType(this._class, this._bindings, this._superClass, this._superInterfaces, this._keyType.mo6170withValueHandler(obj), this._valueType, this._valueHandler, this._typeHandler, this._asStatic);
    }

    public boolean isTrueMapType() {
        return Map.class.isAssignableFrom(this._class);
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public String toString() {
        return String.format("[map-like type; class %s, %s -> %s]", this._class.getName(), this._keyType, this._valueType);
    }

    @Override // com.fasterxml.jackson.databind.JavaType
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        MapLikeType mapLikeType = (MapLikeType) obj;
        return this._class == mapLikeType._class && this._keyType.equals(mapLikeType._keyType) && this._valueType.equals(mapLikeType._valueType);
    }
}
