package com.j256.ormlite.stmt;

import com.j256.ormlite.field.SqlType;

/* loaded from: classes3.dex */
public class ThreadLocalSelectArg extends BaseArgumentHolder implements ArgumentHolder {
    public ThreadLocal<ValueWrapper> threadValue = new ThreadLocal<>();

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class ValueWrapper {
        public Object value;

        public ValueWrapper(Object obj) {
            this.value = obj;
        }
    }

    public ThreadLocalSelectArg() {
    }

    public ThreadLocalSelectArg(SqlType sqlType, Object obj) {
        super(sqlType);
        setValue(obj);
    }

    public ThreadLocalSelectArg(Object obj) {
        setValue(obj);
    }

    public ThreadLocalSelectArg(String str, Object obj) {
        super(str);
        setValue(obj);
    }

    @Override // com.j256.ormlite.stmt.BaseArgumentHolder
    public Object getValue() {
        ValueWrapper valueWrapper = this.threadValue.get();
        if (valueWrapper == null) {
            return null;
        }
        return valueWrapper.value;
    }

    @Override // com.j256.ormlite.stmt.BaseArgumentHolder
    public boolean isValueSet() {
        return this.threadValue.get() != null;
    }

    @Override // com.j256.ormlite.stmt.BaseArgumentHolder, com.j256.ormlite.stmt.ArgumentHolder
    public void setValue(Object obj) {
        this.threadValue.set(new ValueWrapper(obj));
    }
}
