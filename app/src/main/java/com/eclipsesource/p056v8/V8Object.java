package com.eclipsesource.p056v8;

import java.lang.reflect.Method;

/* renamed from: com.eclipsesource.v8.V8Object */
/* loaded from: classes2.dex */
public class V8Object extends V8Value {
    public V8Object(C1257V8 c1257v8) {
        this(c1257v8, null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public V8Object(C1257V8 c1257v8, Object obj) {
        super(c1257v8);
        if (c1257v8 != null) {
            this.f1245v8.checkThread();
            initialize(this.f1245v8.getV8RuntimePtr(), obj);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public V8Object() {
    }

    @Override // com.eclipsesource.p056v8.V8Value
    protected V8Value createTwin() {
        return new V8Object(this.f1245v8);
    }

    @Override // com.eclipsesource.p056v8.V8Value
    /* renamed from: twin */
    public V8Object mo5914twin() {
        return (V8Object) super.mo5914twin();
    }

    public boolean contains(String str) {
        this.f1245v8.checkThread();
        checkReleased();
        checkKey(str);
        C1257V8 c1257v8 = this.f1245v8;
        return c1257v8.contains(c1257v8.getV8RuntimePtr(), this.objectHandle, str);
    }

    public String[] getKeys() {
        this.f1245v8.checkThread();
        checkReleased();
        C1257V8 c1257v8 = this.f1245v8;
        return c1257v8.getKeys(c1257v8.getV8RuntimePtr(), this.objectHandle);
    }

    public int getType(String str) {
        this.f1245v8.checkThread();
        checkReleased();
        checkKey(str);
        C1257V8 c1257v8 = this.f1245v8;
        return c1257v8.getType(c1257v8.getV8RuntimePtr(), this.objectHandle, str);
    }

    public Object get(String str) {
        this.f1245v8.checkThread();
        checkReleased();
        checkKey(str);
        C1257V8 c1257v8 = this.f1245v8;
        return c1257v8.get(c1257v8.getV8RuntimePtr(), 6, this.objectHandle, str);
    }

    public int getInteger(String str) {
        this.f1245v8.checkThread();
        checkReleased();
        checkKey(str);
        C1257V8 c1257v8 = this.f1245v8;
        return c1257v8.getInteger(c1257v8.getV8RuntimePtr(), this.objectHandle, str);
    }

    public boolean getBoolean(String str) {
        this.f1245v8.checkThread();
        checkReleased();
        checkKey(str);
        C1257V8 c1257v8 = this.f1245v8;
        return c1257v8.getBoolean(c1257v8.getV8RuntimePtr(), this.objectHandle, str);
    }

    public double getDouble(String str) {
        this.f1245v8.checkThread();
        checkReleased();
        checkKey(str);
        C1257V8 c1257v8 = this.f1245v8;
        return c1257v8.getDouble(c1257v8.getV8RuntimePtr(), this.objectHandle, str);
    }

    public String getString(String str) {
        this.f1245v8.checkThread();
        checkReleased();
        checkKey(str);
        C1257V8 c1257v8 = this.f1245v8;
        return c1257v8.getString(c1257v8.getV8RuntimePtr(), this.objectHandle, str);
    }

    public V8Array getArray(String str) {
        this.f1245v8.checkThread();
        checkReleased();
        checkKey(str);
        C1257V8 c1257v8 = this.f1245v8;
        Object obj = c1257v8.get(c1257v8.getV8RuntimePtr(), 5, this.objectHandle, str);
        if (obj == null || (obj instanceof V8Array)) {
            return (V8Array) obj;
        }
        throw new V8ResultUndefined();
    }

    public V8Object getObject(String str) {
        this.f1245v8.checkThread();
        checkReleased();
        checkKey(str);
        C1257V8 c1257v8 = this.f1245v8;
        Object obj = c1257v8.get(c1257v8.getV8RuntimePtr(), 6, this.objectHandle, str);
        if (obj == null || (obj instanceof V8Object)) {
            return (V8Object) obj;
        }
        throw new V8ResultUndefined();
    }

    public int executeIntegerFunction(String str, V8Array v8Array) {
        this.f1245v8.checkThread();
        checkReleased();
        this.f1245v8.checkRuntime(v8Array);
        long handle = v8Array == null ? 0L : v8Array.getHandle();
        C1257V8 c1257v8 = this.f1245v8;
        return c1257v8.executeIntegerFunction(c1257v8.getV8RuntimePtr(), getHandle(), str, handle);
    }

    public double executeDoubleFunction(String str, V8Array v8Array) {
        this.f1245v8.checkThread();
        checkReleased();
        this.f1245v8.checkRuntime(v8Array);
        long handle = v8Array == null ? 0L : v8Array.getHandle();
        C1257V8 c1257v8 = this.f1245v8;
        return c1257v8.executeDoubleFunction(c1257v8.getV8RuntimePtr(), getHandle(), str, handle);
    }

    public String executeStringFunction(String str, V8Array v8Array) {
        this.f1245v8.checkThread();
        checkReleased();
        this.f1245v8.checkRuntime(v8Array);
        long handle = v8Array == null ? 0L : v8Array.getHandle();
        C1257V8 c1257v8 = this.f1245v8;
        return c1257v8.executeStringFunction(c1257v8.getV8RuntimePtr(), getHandle(), str, handle);
    }

    public boolean executeBooleanFunction(String str, V8Array v8Array) {
        this.f1245v8.checkThread();
        checkReleased();
        this.f1245v8.checkRuntime(v8Array);
        long handle = v8Array == null ? 0L : v8Array.getHandle();
        C1257V8 c1257v8 = this.f1245v8;
        return c1257v8.executeBooleanFunction(c1257v8.getV8RuntimePtr(), getHandle(), str, handle);
    }

    public V8Array executeArrayFunction(String str, V8Array v8Array) {
        this.f1245v8.checkThread();
        checkReleased();
        this.f1245v8.checkRuntime(v8Array);
        long handle = v8Array == null ? 0L : v8Array.getHandle();
        C1257V8 c1257v8 = this.f1245v8;
        Object executeFunction = c1257v8.executeFunction(c1257v8.getV8RuntimePtr(), 5, this.objectHandle, str, handle);
        if (executeFunction instanceof V8Array) {
            return (V8Array) executeFunction;
        }
        throw new V8ResultUndefined();
    }

    public V8Object executeObjectFunction(String str, V8Array v8Array) {
        this.f1245v8.checkThread();
        checkReleased();
        this.f1245v8.checkRuntime(v8Array);
        long handle = v8Array == null ? 0L : v8Array.getHandle();
        C1257V8 c1257v8 = this.f1245v8;
        Object executeFunction = c1257v8.executeFunction(c1257v8.getV8RuntimePtr(), 6, this.objectHandle, str, handle);
        if (executeFunction instanceof V8Object) {
            return (V8Object) executeFunction;
        }
        throw new V8ResultUndefined();
    }

    public Object executeFunction(String str, V8Array v8Array) {
        this.f1245v8.checkThread();
        checkReleased();
        this.f1245v8.checkRuntime(v8Array);
        long handle = v8Array == null ? 0L : v8Array.getHandle();
        C1257V8 c1257v8 = this.f1245v8;
        return c1257v8.executeFunction(c1257v8.getV8RuntimePtr(), 0, this.objectHandle, str, handle);
    }

    public Object executeJSFunction(String str) {
        return executeFunction(str, null);
    }

    public Object executeJSFunction(String str, Object... objArr) {
        if (objArr == null) {
            return executeFunction(str, null);
        }
        V8Array v8Array = new V8Array(this.f1245v8.getRuntime());
        try {
            for (Object obj : objArr) {
                if (obj == null) {
                    v8Array.pushNull();
                } else if (obj instanceof V8Value) {
                    v8Array.push((V8Value) obj);
                } else if (obj instanceof Integer) {
                    v8Array.push(obj);
                } else if (obj instanceof Double) {
                    v8Array.push(obj);
                } else if (obj instanceof Long) {
                    v8Array.push(((Long) obj).doubleValue());
                } else if (obj instanceof Float) {
                    v8Array.push(((Float) obj).floatValue());
                } else if (obj instanceof Boolean) {
                    v8Array.push(obj);
                } else if (obj instanceof String) {
                    v8Array.push((String) obj);
                } else {
                    throw new IllegalArgumentException("Unsupported Object of type: " + obj.getClass());
                }
            }
            return executeFunction(str, v8Array);
        } finally {
            v8Array.close();
        }
    }

    public void executeVoidFunction(String str, V8Array v8Array) {
        this.f1245v8.checkThread();
        checkReleased();
        this.f1245v8.checkRuntime(v8Array);
        long handle = v8Array == null ? 0L : v8Array.getHandle();
        C1257V8 c1257v8 = this.f1245v8;
        c1257v8.executeVoidFunction(c1257v8.getV8RuntimePtr(), this.objectHandle, str, handle);
    }

    public V8Object add(String str, int i) {
        this.f1245v8.checkThread();
        checkReleased();
        C1257V8 c1257v8 = this.f1245v8;
        c1257v8.add(c1257v8.getV8RuntimePtr(), this.objectHandle, str, i);
        return this;
    }

    public V8Object add(String str, boolean z) {
        this.f1245v8.checkThread();
        checkReleased();
        C1257V8 c1257v8 = this.f1245v8;
        c1257v8.add(c1257v8.getV8RuntimePtr(), this.objectHandle, str, z);
        return this;
    }

    public V8Object add(String str, double d) {
        this.f1245v8.checkThread();
        checkReleased();
        C1257V8 c1257v8 = this.f1245v8;
        c1257v8.add(c1257v8.getV8RuntimePtr(), this.objectHandle, str, d);
        return this;
    }

    public V8Object add(String str, String str2) {
        this.f1245v8.checkThread();
        checkReleased();
        if (str2 == null) {
            C1257V8 c1257v8 = this.f1245v8;
            c1257v8.addNull(c1257v8.getV8RuntimePtr(), this.objectHandle, str);
        } else if (str2.equals(C1257V8.getUndefined())) {
            C1257V8 c1257v82 = this.f1245v8;
            c1257v82.addUndefined(c1257v82.getV8RuntimePtr(), this.objectHandle, str);
        } else {
            C1257V8 c1257v83 = this.f1245v8;
            c1257v83.add(c1257v83.getV8RuntimePtr(), this.objectHandle, str, str2);
        }
        return this;
    }

    public V8Object add(String str, V8Value v8Value) {
        this.f1245v8.checkThread();
        checkReleased();
        this.f1245v8.checkRuntime(v8Value);
        if (v8Value == null) {
            C1257V8 c1257v8 = this.f1245v8;
            c1257v8.addNull(c1257v8.getV8RuntimePtr(), this.objectHandle, str);
        } else if (v8Value.equals(C1257V8.getUndefined())) {
            C1257V8 c1257v82 = this.f1245v8;
            c1257v82.addUndefined(c1257v82.getV8RuntimePtr(), this.objectHandle, str);
        } else {
            C1257V8 c1257v83 = this.f1245v8;
            c1257v83.addObject(c1257v83.getV8RuntimePtr(), this.objectHandle, str, v8Value.getHandle());
        }
        return this;
    }

    public V8Object addUndefined(String str) {
        this.f1245v8.checkThread();
        checkReleased();
        C1257V8 c1257v8 = this.f1245v8;
        c1257v8.addUndefined(c1257v8.getV8RuntimePtr(), this.objectHandle, str);
        return this;
    }

    public V8Object addNull(String str) {
        this.f1245v8.checkThread();
        checkReleased();
        C1257V8 c1257v8 = this.f1245v8;
        c1257v8.addNull(c1257v8.getV8RuntimePtr(), this.objectHandle, str);
        return this;
    }

    public V8Object setPrototype(V8Object v8Object) {
        this.f1245v8.checkThread();
        checkReleased();
        C1257V8 c1257v8 = this.f1245v8;
        c1257v8.setPrototype(c1257v8.getV8RuntimePtr(), this.objectHandle, v8Object.getHandle());
        return this;
    }

    public V8Object registerJavaMethod(JavaCallback javaCallback, String str) {
        this.f1245v8.checkThread();
        checkReleased();
        this.f1245v8.registerCallback(javaCallback, getHandle(), str);
        return this;
    }

    public V8Object registerJavaMethod(JavaVoidCallback javaVoidCallback, String str) {
        this.f1245v8.checkThread();
        checkReleased();
        this.f1245v8.registerVoidCallback(javaVoidCallback, getHandle(), str);
        return this;
    }

    public V8Object registerJavaMethod(Object obj, String str, String str2, Class<?>[] clsArr) {
        return registerJavaMethod(obj, str, str2, clsArr, false);
    }

    public V8Object registerJavaMethod(Object obj, String str, String str2, Class<?>[] clsArr, boolean z) {
        this.f1245v8.checkThread();
        checkReleased();
        try {
            Method method = obj.getClass().getMethod(str, clsArr);
            method.setAccessible(true);
            this.f1245v8.registerCallback(obj, method, getHandle(), str2, z);
            return this;
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException(e);
        } catch (SecurityException e2) {
            throw new IllegalStateException(e2);
        }
    }

    public String toString() {
        if (isReleased() || this.f1245v8.isReleased()) {
            return "[Object released]";
        }
        this.f1245v8.checkThread();
        C1257V8 c1257v8 = this.f1245v8;
        return c1257v8.toString(c1257v8.getV8RuntimePtr(), getHandle());
    }

    private void checkKey(String str) {
        if (str != null) {
            return;
        }
        throw new IllegalArgumentException("Key cannot be null");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.eclipsesource.v8.V8Object$Undefined */
    /* loaded from: classes2.dex */
    public static class Undefined extends V8Object {
        @Override // com.eclipsesource.p056v8.V8Value, com.eclipsesource.p056v8.Releasable, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
        }

        @Override // com.eclipsesource.p056v8.V8Value
        public int hashCode() {
            return 919;
        }

        @Override // com.eclipsesource.p056v8.V8Value
        public boolean isReleased() {
            return false;
        }

        @Override // com.eclipsesource.p056v8.V8Value
        public boolean isUndefined() {
            return true;
        }

        @Override // com.eclipsesource.p056v8.V8Value, com.eclipsesource.p056v8.Releasable
        @Deprecated
        public void release() {
        }

        @Override // com.eclipsesource.p056v8.V8Object
        public String toString() {
            return "undefined";
        }

        @Override // com.eclipsesource.p056v8.V8Object, com.eclipsesource.p056v8.V8Value
        /* renamed from: twin */
        public Undefined mo5914twin() {
            return (Undefined) super.mo5914twin();
        }

        @Override // com.eclipsesource.p056v8.V8Value
        public boolean equals(Object obj) {
            return (obj instanceof V8Object) && ((V8Object) obj).isUndefined();
        }

        @Override // com.eclipsesource.p056v8.V8Object
        public V8Object add(String str, boolean z) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Value
        public C1257V8 getRuntime() {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Object
        public V8Object add(String str, double d) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Object
        public V8Object add(String str, int i) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Object
        public Object executeJSFunction(String str, Object... objArr) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Object
        public Object executeFunction(String str, V8Array v8Array) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Object
        public V8Object add(String str, String str2) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Object
        public V8Object add(String str, V8Value v8Value) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Object
        public V8Object addUndefined(String str) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Object
        public boolean contains(String str) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Object
        public V8Array executeArrayFunction(String str, V8Array v8Array) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Object
        public boolean executeBooleanFunction(String str, V8Array v8Array) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Object
        public double executeDoubleFunction(String str, V8Array v8Array) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Object
        public int executeIntegerFunction(String str, V8Array v8Array) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Object
        public V8Object executeObjectFunction(String str, V8Array v8Array) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Object
        public String executeStringFunction(String str, V8Array v8Array) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Object
        public void executeVoidFunction(String str, V8Array v8Array) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Object
        public V8Array getArray(String str) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Object
        public boolean getBoolean(String str) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Object
        public double getDouble(String str) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Object
        public int getInteger(String str) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Object
        public String[] getKeys() {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Object
        public V8Object getObject(String str) throws V8ResultUndefined {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Object
        public String getString(String str) throws V8ResultUndefined {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Object
        public int getType(String str) throws V8ResultUndefined {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Object
        public V8Object registerJavaMethod(JavaCallback javaCallback, String str) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Object
        public V8Object registerJavaMethod(JavaVoidCallback javaVoidCallback, String str) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Object
        public V8Object registerJavaMethod(Object obj, String str, String str2, Class<?>[] clsArr, boolean z) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Object
        public V8Object setPrototype(V8Object v8Object) {
            throw new UnsupportedOperationException();
        }
    }
}
