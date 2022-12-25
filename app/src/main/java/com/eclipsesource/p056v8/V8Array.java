package com.eclipsesource.p056v8;

/* renamed from: com.eclipsesource.v8.V8Array */
/* loaded from: classes2.dex */
public class V8Array extends V8Object {
    protected V8Array() {
    }

    public V8Array(C1257V8 c1257v8) {
        super(c1257v8);
        c1257v8.checkThread();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public V8Array(C1257V8 c1257v8, Object obj) {
        super(c1257v8, obj);
    }

    @Override // com.eclipsesource.p056v8.V8Object, com.eclipsesource.p056v8.V8Value
    protected V8Value createTwin() {
        return new V8Array(this.f1245v8);
    }

    @Override // com.eclipsesource.p056v8.V8Object, com.eclipsesource.p056v8.V8Value
    /* renamed from: twin */
    public V8Array mo5914twin() {
        return (V8Array) super.mo5914twin();
    }

    @Override // com.eclipsesource.p056v8.V8Object
    public String toString() {
        return (this.released || this.f1245v8.isReleased()) ? "[Array released]" : super.toString();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.eclipsesource.p056v8.V8Value
    public void initialize(long j, Object obj) {
        long initNewV8Array = this.f1245v8.initNewV8Array(j);
        this.released = false;
        addObjectReference(initNewV8Array);
    }

    public int length() {
        this.f1245v8.checkThread();
        checkReleased();
        C1257V8 c1257v8 = this.f1245v8;
        return c1257v8.arrayGetSize(c1257v8.getV8RuntimePtr(), getHandle());
    }

    public int getType(int i) {
        this.f1245v8.checkThread();
        checkReleased();
        C1257V8 c1257v8 = this.f1245v8;
        return c1257v8.getType(c1257v8.getV8RuntimePtr(), getHandle(), i);
    }

    public int getType() {
        this.f1245v8.checkThread();
        checkReleased();
        C1257V8 c1257v8 = this.f1245v8;
        return c1257v8.getArrayType(c1257v8.getV8RuntimePtr(), getHandle());
    }

    public int getType(int i, int i2) {
        this.f1245v8.checkThread();
        checkReleased();
        C1257V8 c1257v8 = this.f1245v8;
        return c1257v8.getType(c1257v8.getV8RuntimePtr(), getHandle(), i, i2);
    }

    public int getInteger(int i) {
        this.f1245v8.checkThread();
        checkReleased();
        C1257V8 c1257v8 = this.f1245v8;
        return c1257v8.arrayGetInteger(c1257v8.getV8RuntimePtr(), getHandle(), i);
    }

    public boolean getBoolean(int i) {
        this.f1245v8.checkThread();
        checkReleased();
        C1257V8 c1257v8 = this.f1245v8;
        return c1257v8.arrayGetBoolean(c1257v8.getV8RuntimePtr(), getHandle(), i);
    }

    public byte getByte(int i) {
        this.f1245v8.checkThread();
        checkReleased();
        C1257V8 c1257v8 = this.f1245v8;
        return c1257v8.arrayGetByte(c1257v8.getV8RuntimePtr(), getHandle(), i);
    }

    public double getDouble(int i) {
        this.f1245v8.checkThread();
        checkReleased();
        C1257V8 c1257v8 = this.f1245v8;
        return c1257v8.arrayGetDouble(c1257v8.getV8RuntimePtr(), getHandle(), i);
    }

    public String getString(int i) {
        this.f1245v8.checkThread();
        checkReleased();
        C1257V8 c1257v8 = this.f1245v8;
        return c1257v8.arrayGetString(c1257v8.getV8RuntimePtr(), getHandle(), i);
    }

    public int[] getIntegers(int i, int i2) {
        this.f1245v8.checkThread();
        checkReleased();
        C1257V8 c1257v8 = this.f1245v8;
        return c1257v8.arrayGetIntegers(c1257v8.getV8RuntimePtr(), getHandle(), i, i2);
    }

    public int getIntegers(int i, int i2, int[] iArr) {
        this.f1245v8.checkThread();
        checkReleased();
        if (i2 > iArr.length) {
            throw new IndexOutOfBoundsException();
        }
        C1257V8 c1257v8 = this.f1245v8;
        return c1257v8.arrayGetIntegers(c1257v8.getV8RuntimePtr(), getHandle(), i, i2, iArr);
    }

    public double[] getDoubles(int i, int i2) {
        this.f1245v8.checkThread();
        checkReleased();
        C1257V8 c1257v8 = this.f1245v8;
        return c1257v8.arrayGetDoubles(c1257v8.getV8RuntimePtr(), getHandle(), i, i2);
    }

    public int getDoubles(int i, int i2, double[] dArr) {
        this.f1245v8.checkThread();
        checkReleased();
        if (i2 > dArr.length) {
            throw new IndexOutOfBoundsException();
        }
        C1257V8 c1257v8 = this.f1245v8;
        return c1257v8.arrayGetDoubles(c1257v8.getV8RuntimePtr(), getHandle(), i, i2, dArr);
    }

    public boolean[] getBooleans(int i, int i2) {
        this.f1245v8.checkThread();
        checkReleased();
        C1257V8 c1257v8 = this.f1245v8;
        return c1257v8.arrayGetBooleans(c1257v8.getV8RuntimePtr(), getHandle(), i, i2);
    }

    public byte[] getBytes(int i, int i2) {
        this.f1245v8.checkThread();
        checkReleased();
        C1257V8 c1257v8 = this.f1245v8;
        return c1257v8.arrayGetBytes(c1257v8.getV8RuntimePtr(), getHandle(), i, i2);
    }

    public int getBooleans(int i, int i2, boolean[] zArr) {
        this.f1245v8.checkThread();
        checkReleased();
        if (i2 > zArr.length) {
            throw new IndexOutOfBoundsException();
        }
        C1257V8 c1257v8 = this.f1245v8;
        return c1257v8.arrayGetBooleans(c1257v8.getV8RuntimePtr(), getHandle(), i, i2, zArr);
    }

    public int getBytes(int i, int i2, byte[] bArr) {
        this.f1245v8.checkThread();
        checkReleased();
        if (i2 > bArr.length) {
            throw new IndexOutOfBoundsException();
        }
        C1257V8 c1257v8 = this.f1245v8;
        return c1257v8.arrayGetBytes(c1257v8.getV8RuntimePtr(), getHandle(), i, i2, bArr);
    }

    public String[] getStrings(int i, int i2) {
        this.f1245v8.checkThread();
        checkReleased();
        C1257V8 c1257v8 = this.f1245v8;
        return c1257v8.arrayGetStrings(c1257v8.getV8RuntimePtr(), getHandle(), i, i2);
    }

    public int getStrings(int i, int i2, String[] strArr) {
        this.f1245v8.checkThread();
        checkReleased();
        if (i2 > strArr.length) {
            throw new IndexOutOfBoundsException();
        }
        C1257V8 c1257v8 = this.f1245v8;
        return c1257v8.arrayGetStrings(c1257v8.getV8RuntimePtr(), getHandle(), i, i2, strArr);
    }

    public Object get(int i) {
        this.f1245v8.checkThread();
        checkReleased();
        C1257V8 c1257v8 = this.f1245v8;
        return c1257v8.arrayGet(c1257v8.getV8RuntimePtr(), 6, this.objectHandle, i);
    }

    public V8Array getArray(int i) {
        this.f1245v8.checkThread();
        checkReleased();
        C1257V8 c1257v8 = this.f1245v8;
        Object arrayGet = c1257v8.arrayGet(c1257v8.getV8RuntimePtr(), 5, this.objectHandle, i);
        if (arrayGet == null || (arrayGet instanceof V8Array)) {
            return (V8Array) arrayGet;
        }
        throw new V8ResultUndefined();
    }

    public V8Object getObject(int i) {
        this.f1245v8.checkThread();
        checkReleased();
        C1257V8 c1257v8 = this.f1245v8;
        Object arrayGet = c1257v8.arrayGet(c1257v8.getV8RuntimePtr(), 6, this.objectHandle, i);
        if (arrayGet == null || (arrayGet instanceof V8Object)) {
            return (V8Object) arrayGet;
        }
        throw new V8ResultUndefined();
    }

    public V8Array push(int i) {
        this.f1245v8.checkThread();
        checkReleased();
        C1257V8 c1257v8 = this.f1245v8;
        c1257v8.addArrayIntItem(c1257v8.getV8RuntimePtr(), getHandle(), i);
        return this;
    }

    public V8Array push(boolean z) {
        this.f1245v8.checkThread();
        checkReleased();
        C1257V8 c1257v8 = this.f1245v8;
        c1257v8.addArrayBooleanItem(c1257v8.getV8RuntimePtr(), getHandle(), z);
        return this;
    }

    public V8Array push(double d) {
        this.f1245v8.checkThread();
        checkReleased();
        C1257V8 c1257v8 = this.f1245v8;
        c1257v8.addArrayDoubleItem(c1257v8.getV8RuntimePtr(), getHandle(), d);
        return this;
    }

    public V8Array push(String str) {
        this.f1245v8.checkThread();
        checkReleased();
        if (str == null) {
            C1257V8 c1257v8 = this.f1245v8;
            c1257v8.addArrayNullItem(c1257v8.getV8RuntimePtr(), getHandle());
        } else if (str.equals(C1257V8.getUndefined())) {
            C1257V8 c1257v82 = this.f1245v8;
            c1257v82.addArrayUndefinedItem(c1257v82.getV8RuntimePtr(), getHandle());
        } else {
            C1257V8 c1257v83 = this.f1245v8;
            c1257v83.addArrayStringItem(c1257v83.getV8RuntimePtr(), getHandle(), str);
        }
        return this;
    }

    public V8Array push(V8Value v8Value) {
        this.f1245v8.checkThread();
        checkReleased();
        this.f1245v8.checkRuntime(v8Value);
        if (v8Value == null) {
            C1257V8 c1257v8 = this.f1245v8;
            c1257v8.addArrayNullItem(c1257v8.getV8RuntimePtr(), getHandle());
        } else if (v8Value.equals(C1257V8.getUndefined())) {
            C1257V8 c1257v82 = this.f1245v8;
            c1257v82.addArrayUndefinedItem(c1257v82.getV8RuntimePtr(), getHandle());
        } else {
            C1257V8 c1257v83 = this.f1245v8;
            c1257v83.addArrayObjectItem(c1257v83.getV8RuntimePtr(), getHandle(), v8Value.getHandle());
        }
        return this;
    }

    public V8Array push(Object obj) {
        this.f1245v8.checkThread();
        checkReleased();
        boolean z = obj instanceof V8Value;
        if (z) {
            this.f1245v8.checkRuntime((V8Value) obj);
        }
        if (obj == null) {
            C1257V8 c1257v8 = this.f1245v8;
            c1257v8.addArrayNullItem(c1257v8.getV8RuntimePtr(), getHandle());
        } else if (obj.equals(C1257V8.getUndefined())) {
            C1257V8 c1257v82 = this.f1245v8;
            c1257v82.addArrayUndefinedItem(c1257v82.getV8RuntimePtr(), getHandle());
        } else if (obj instanceof Double) {
            C1257V8 c1257v83 = this.f1245v8;
            c1257v83.addArrayDoubleItem(c1257v83.getV8RuntimePtr(), getHandle(), ((Double) obj).doubleValue());
        } else if (obj instanceof Integer) {
            C1257V8 c1257v84 = this.f1245v8;
            c1257v84.addArrayIntItem(c1257v84.getV8RuntimePtr(), getHandle(), ((Integer) obj).intValue());
        } else if (obj instanceof Float) {
            C1257V8 c1257v85 = this.f1245v8;
            c1257v85.addArrayDoubleItem(c1257v85.getV8RuntimePtr(), getHandle(), ((Float) obj).doubleValue());
        } else if (obj instanceof Number) {
            C1257V8 c1257v86 = this.f1245v8;
            c1257v86.addArrayDoubleItem(c1257v86.getV8RuntimePtr(), getHandle(), ((Number) obj).doubleValue());
        } else if (obj instanceof Boolean) {
            C1257V8 c1257v87 = this.f1245v8;
            c1257v87.addArrayBooleanItem(c1257v87.getV8RuntimePtr(), getHandle(), ((Boolean) obj).booleanValue());
        } else if (obj instanceof String) {
            C1257V8 c1257v88 = this.f1245v8;
            c1257v88.addArrayStringItem(c1257v88.getV8RuntimePtr(), getHandle(), (String) obj);
        } else if (z) {
            C1257V8 c1257v89 = this.f1245v8;
            c1257v89.addArrayObjectItem(c1257v89.getV8RuntimePtr(), getHandle(), ((V8Value) obj).getHandle());
        } else {
            throw new IllegalArgumentException();
        }
        return this;
    }

    public V8Array pushNull() {
        this.f1245v8.checkThread();
        checkReleased();
        C1257V8 c1257v8 = this.f1245v8;
        c1257v8.addArrayNullItem(c1257v8.getV8RuntimePtr(), getHandle());
        return this;
    }

    public V8Array pushUndefined() {
        this.f1245v8.checkThread();
        checkReleased();
        C1257V8 c1257v8 = this.f1245v8;
        c1257v8.addArrayUndefinedItem(c1257v8.getV8RuntimePtr(), getHandle());
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.eclipsesource.v8.V8Array$Undefined */
    /* loaded from: classes2.dex */
    public static class Undefined extends V8Array {
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

        @Override // com.eclipsesource.p056v8.V8Array, com.eclipsesource.p056v8.V8Object
        public String toString() {
            return "undefined";
        }

        @Override // com.eclipsesource.p056v8.V8Array, com.eclipsesource.p056v8.V8Object, com.eclipsesource.p056v8.V8Value
        /* renamed from: twin */
        public Undefined mo5914twin() {
            return (Undefined) super.mo5914twin();
        }

        @Override // com.eclipsesource.p056v8.V8Value
        public boolean equals(Object obj) {
            return (obj instanceof V8Object) && ((V8Object) obj).isUndefined();
        }

        @Override // com.eclipsesource.p056v8.V8Value
        public C1257V8 getRuntime() {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Object
        public V8Object add(String str, boolean z) {
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

        @Override // com.eclipsesource.p056v8.V8Array
        public Object get(int i) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Array
        public V8Array getArray(int i) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Array
        public boolean getBoolean(int i) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Array
        public boolean[] getBooleans(int i, int i2) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Array
        public byte[] getBytes(int i, int i2) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Array
        public int getBytes(int i, int i2, byte[] bArr) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Array
        public byte getByte(int i) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Array
        public int getBooleans(int i, int i2, boolean[] zArr) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Array
        public double getDouble(int i) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Array
        public double[] getDoubles(int i, int i2) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Array
        public int getDoubles(int i, int i2, double[] dArr) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Array
        public int getInteger(int i) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Array
        public int[] getIntegers(int i, int i2) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Array
        public int getIntegers(int i, int i2, int[] iArr) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Array
        public V8Object getObject(int i) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Array
        public String getString(int i) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Array
        public String[] getStrings(int i, int i2) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Array
        public int getStrings(int i, int i2, String[] strArr) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Array
        public int getType() {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Array
        public int getType(int i) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Array
        public int getType(int i, int i2) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Array
        public int length() {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Array
        public V8Array push(boolean z) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Array
        public V8Array push(double d) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Array
        public V8Array push(int i) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Array
        public V8Array push(String str) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Array
        public V8Array push(V8Value v8Value) {
            throw new UnsupportedOperationException();
        }

        @Override // com.eclipsesource.p056v8.V8Array
        public V8Array pushUndefined() {
            throw new UnsupportedOperationException();
        }
    }
}
