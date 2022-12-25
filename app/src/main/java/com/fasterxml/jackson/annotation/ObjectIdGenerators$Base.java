package com.fasterxml.jackson.annotation;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public abstract class ObjectIdGenerators$Base<T> extends ObjectIdGenerator<T> {
    protected final Class<?> _scope;

    @Override // com.fasterxml.jackson.annotation.ObjectIdGenerator
    /* renamed from: generateId */
    public abstract T mo5966generateId(Object obj);

    /* JADX INFO: Access modifiers changed from: protected */
    public ObjectIdGenerators$Base(Class<?> cls) {
        this._scope = cls;
    }

    @Override // com.fasterxml.jackson.annotation.ObjectIdGenerator
    public Class<?> getScope() {
        return this._scope;
    }

    @Override // com.fasterxml.jackson.annotation.ObjectIdGenerator
    public boolean canUseFor(ObjectIdGenerator<?> objectIdGenerator) {
        return objectIdGenerator.getClass() == getClass() && objectIdGenerator.getScope() == this._scope;
    }
}
