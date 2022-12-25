package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

/* loaded from: classes2.dex */
public final class WritableObjectId {
    public final ObjectIdGenerator<?> generator;

    /* renamed from: id */
    public Object f1281id;
    protected boolean idWritten = false;

    public WritableObjectId(ObjectIdGenerator<?> objectIdGenerator) {
        this.generator = objectIdGenerator;
    }

    public boolean writeAsId(JsonGenerator jsonGenerator, SerializerProvider serializerProvider, ObjectIdWriter objectIdWriter) throws IOException {
        if (this.f1281id != null) {
            if (!this.idWritten && !objectIdWriter.alwaysAsId) {
                return false;
            }
            if (jsonGenerator.canWriteObjectId()) {
                jsonGenerator.writeObjectRef(String.valueOf(this.f1281id));
                throw null;
            }
            objectIdWriter.serializer.serialize(this.f1281id, jsonGenerator, serializerProvider);
            return true;
        }
        return false;
    }

    public Object generateId(Object obj) {
        if (this.f1281id == null) {
            this.f1281id = this.generator.mo5966generateId(obj);
        }
        return this.f1281id;
    }

    public void writeAsField(JsonGenerator jsonGenerator, SerializerProvider serializerProvider, ObjectIdWriter objectIdWriter) throws IOException {
        this.idWritten = true;
        if (jsonGenerator.canWriteObjectId()) {
            jsonGenerator.writeObjectId(String.valueOf(this.f1281id));
            return;
        }
        SerializableString serializableString = objectIdWriter.propertyName;
        if (serializableString == null) {
            return;
        }
        jsonGenerator.writeFieldName(serializableString);
        objectIdWriter.serializer.serialize(this.f1281id, jsonGenerator, serializerProvider);
    }
}
