package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;

/* loaded from: classes2.dex */
public abstract class TypeSerializerBase extends TypeSerializer {
    protected final TypeIdResolver _idResolver;
    protected final BeanProperty _property;

    @Override // com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public String getPropertyName() {
        return null;
    }

    protected void handleMissingId(Object obj) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public TypeSerializerBase(TypeIdResolver typeIdResolver, BeanProperty beanProperty) {
        this._idResolver = typeIdResolver;
        this._property = beanProperty;
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public WritableTypeId writeTypePrefix(JsonGenerator jsonGenerator, WritableTypeId writableTypeId) throws IOException {
        _generateTypeId(writableTypeId);
        jsonGenerator.writeTypePrefix(writableTypeId);
        return writableTypeId;
    }

    @Override // com.fasterxml.jackson.databind.jsontype.TypeSerializer
    public WritableTypeId writeTypeSuffix(JsonGenerator jsonGenerator, WritableTypeId writableTypeId) throws IOException {
        jsonGenerator.writeTypeSuffix(writableTypeId);
        return writableTypeId;
    }

    protected void _generateTypeId(WritableTypeId writableTypeId) {
        String idFromValueAndType;
        if (writableTypeId.f1278id == null) {
            Object obj = writableTypeId.forValue;
            Class<?> cls = writableTypeId.forValueType;
            if (cls == null) {
                idFromValueAndType = idFromValue(obj);
            } else {
                idFromValueAndType = idFromValueAndType(obj, cls);
            }
            writableTypeId.f1278id = idFromValueAndType;
        }
    }

    protected String idFromValue(Object obj) {
        String idFromValue = this._idResolver.idFromValue(obj);
        if (idFromValue == null) {
            handleMissingId(obj);
        }
        return idFromValue;
    }

    protected String idFromValueAndType(Object obj, Class<?> cls) {
        String idFromValueAndType = this._idResolver.idFromValueAndType(obj, cls);
        if (idFromValueAndType == null) {
            handleMissingId(obj);
        }
        return idFromValueAndType;
    }
}
