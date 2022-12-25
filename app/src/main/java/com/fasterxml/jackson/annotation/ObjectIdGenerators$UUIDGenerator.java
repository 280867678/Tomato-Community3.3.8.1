package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import java.util.UUID;

/* loaded from: classes2.dex */
public final class ObjectIdGenerators$UUIDGenerator extends ObjectIdGenerators$Base<UUID> {
    private static final long serialVersionUID = 1;

    @Override // com.fasterxml.jackson.annotation.ObjectIdGenerator
    public ObjectIdGenerator<UUID> forScope(Class<?> cls) {
        return this;
    }

    @Override // com.fasterxml.jackson.annotation.ObjectIdGenerator
    public ObjectIdGenerator<UUID> newForSerialization(Object obj) {
        return this;
    }

    public ObjectIdGenerators$UUIDGenerator() {
        this(Object.class);
    }

    private ObjectIdGenerators$UUIDGenerator(Class<?> cls) {
        super(Object.class);
    }

    @Override // com.fasterxml.jackson.annotation.ObjectIdGenerators$Base, com.fasterxml.jackson.annotation.ObjectIdGenerator
    /* renamed from: generateId  reason: collision with other method in class */
    public UUID mo5966generateId(Object obj) {
        return UUID.randomUUID();
    }

    @Override // com.fasterxml.jackson.annotation.ObjectIdGenerator
    public ObjectIdGenerator.IdKey key(Object obj) {
        if (obj == null) {
            return null;
        }
        return new ObjectIdGenerator.IdKey(ObjectIdGenerators$UUIDGenerator.class, null, obj);
    }

    @Override // com.fasterxml.jackson.annotation.ObjectIdGenerators$Base, com.fasterxml.jackson.annotation.ObjectIdGenerator
    public boolean canUseFor(ObjectIdGenerator<?> objectIdGenerator) {
        return objectIdGenerator.getClass() == ObjectIdGenerators$UUIDGenerator.class;
    }
}
