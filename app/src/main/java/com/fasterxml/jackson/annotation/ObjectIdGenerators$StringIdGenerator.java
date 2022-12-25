package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import java.util.UUID;

/* loaded from: classes2.dex */
public final class ObjectIdGenerators$StringIdGenerator extends ObjectIdGenerators$Base<String> {
    private static final long serialVersionUID = 1;

    @Override // com.fasterxml.jackson.annotation.ObjectIdGenerator
    public ObjectIdGenerator<String> forScope(Class<?> cls) {
        return this;
    }

    @Override // com.fasterxml.jackson.annotation.ObjectIdGenerator
    public ObjectIdGenerator<String> newForSerialization(Object obj) {
        return this;
    }

    public ObjectIdGenerators$StringIdGenerator() {
        this(Object.class);
    }

    private ObjectIdGenerators$StringIdGenerator(Class<?> cls) {
        super(Object.class);
    }

    @Override // com.fasterxml.jackson.annotation.ObjectIdGenerators$Base, com.fasterxml.jackson.annotation.ObjectIdGenerator
    /* renamed from: generateId  reason: collision with other method in class */
    public String mo5966generateId(Object obj) {
        return UUID.randomUUID().toString();
    }

    @Override // com.fasterxml.jackson.annotation.ObjectIdGenerator
    public ObjectIdGenerator.IdKey key(Object obj) {
        if (obj == null) {
            return null;
        }
        return new ObjectIdGenerator.IdKey(ObjectIdGenerators$StringIdGenerator.class, null, obj);
    }

    @Override // com.fasterxml.jackson.annotation.ObjectIdGenerators$Base, com.fasterxml.jackson.annotation.ObjectIdGenerator
    public boolean canUseFor(ObjectIdGenerator<?> objectIdGenerator) {
        return objectIdGenerator instanceof ObjectIdGenerators$StringIdGenerator;
    }
}
