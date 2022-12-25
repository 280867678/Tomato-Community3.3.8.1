package com.eclipsesource.p056v8;

/* renamed from: com.eclipsesource.v8.V8TypedArray */
/* loaded from: classes2.dex */
public class V8TypedArray extends V8Array {
    public V8TypedArray(C1257V8 c1257v8, V8ArrayBuffer v8ArrayBuffer, int i, int i2, int i3) {
        super(c1257v8, new V8ArrayData(v8ArrayBuffer, i2, i3, i));
    }

    private V8TypedArray(C1257V8 c1257v8) {
        super(c1257v8);
    }

    @Override // com.eclipsesource.p056v8.V8Array, com.eclipsesource.p056v8.V8Object, com.eclipsesource.p056v8.V8Value
    protected V8Value createTwin() {
        this.f1245v8.checkThread();
        checkReleased();
        return new V8TypedArray(this.f1245v8);
    }

    @Override // com.eclipsesource.p056v8.V8Array
    public Object get(int i) {
        this.f1245v8.checkThread();
        checkReleased();
        int type = getType();
        if (type != 1) {
            if (type == 2) {
                return super.get(i);
            }
            if (type == 9) {
                return Byte.valueOf(((Number) super.get(i)).byteValue());
            }
            switch (type) {
                case 11:
                    return Short.valueOf((short) (((Number) super.get(i)).shortValue() & 255));
                case 12:
                    return Short.valueOf((short) (((Number) super.get(i)).byteValue() & 255));
                case 13:
                    return Short.valueOf(((Number) super.get(i)).shortValue());
                case 14:
                    return Integer.valueOf(((Integer) super.get(i)).intValue() & 65535);
                case 15:
                    return Long.valueOf((-1) & ((Number) super.get(i)).longValue());
                case 16:
                    return Float.valueOf(((Number) super.get(i)).floatValue());
                default:
                    return null;
            }
        }
        return super.get(i);
    }

    public V8ArrayBuffer getBuffer() {
        return (V8ArrayBuffer) get("buffer");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.eclipsesource.p056v8.V8Array, com.eclipsesource.p056v8.V8Value
    public void initialize(long j, Object obj) {
        this.f1245v8.checkThread();
        if (obj == null) {
            super.initialize(j, obj);
            return;
        }
        V8ArrayData v8ArrayData = (V8ArrayData) obj;
        checkArrayProperties(v8ArrayData);
        long createTypedArray = createTypedArray(j, v8ArrayData);
        this.released = false;
        addObjectReference(createTypedArray);
    }

    private long createTypedArray(long j, V8ArrayData v8ArrayData) {
        int i = v8ArrayData.type;
        if (i != 1) {
            if (i == 2) {
                return this.f1245v8.initNewV8Float64Array(j, v8ArrayData.buffer.objectHandle, v8ArrayData.offset, v8ArrayData.size);
            }
            if (i != 9) {
                switch (i) {
                    case 11:
                        return this.f1245v8.initNewV8UInt8Array(j, v8ArrayData.buffer.objectHandle, v8ArrayData.offset, v8ArrayData.size);
                    case 12:
                        return this.f1245v8.initNewV8UInt8ClampedArray(j, v8ArrayData.buffer.objectHandle, v8ArrayData.offset, v8ArrayData.size);
                    case 13:
                        return this.f1245v8.initNewV8Int16Array(j, v8ArrayData.buffer.objectHandle, v8ArrayData.offset, v8ArrayData.size);
                    case 14:
                        return this.f1245v8.initNewV8UInt16Array(j, v8ArrayData.buffer.objectHandle, v8ArrayData.offset, v8ArrayData.size);
                    case 15:
                        return this.f1245v8.initNewV8UInt32Array(j, v8ArrayData.buffer.objectHandle, v8ArrayData.offset, v8ArrayData.size);
                    case 16:
                        return this.f1245v8.initNewV8Float32Array(j, v8ArrayData.buffer.objectHandle, v8ArrayData.offset, v8ArrayData.size);
                    default:
                        throw new IllegalArgumentException("Cannot create a typed array of type " + V8Value.getStringRepresentation(v8ArrayData.type));
                }
            }
            return this.f1245v8.initNewV8Int8Array(j, v8ArrayData.buffer.objectHandle, v8ArrayData.offset, v8ArrayData.size);
        }
        return this.f1245v8.initNewV8Int32Array(j, v8ArrayData.buffer.objectHandle, v8ArrayData.offset, v8ArrayData.size);
    }

    public static int getStructureSize(int i) {
        if (i != 1) {
            if (i == 2) {
                return 8;
            }
            if (i != 9) {
                switch (i) {
                    case 11:
                    case 12:
                        break;
                    case 13:
                    case 14:
                        return 2;
                    case 15:
                    case 16:
                        return 4;
                    default:
                        throw new IllegalArgumentException("Cannot create a typed array of type " + V8Value.getStringRepresentation(i));
                }
            }
            return 1;
        }
        return 4;
    }

    private void checkArrayProperties(V8ArrayData v8ArrayData) {
        checkOffset(v8ArrayData);
        checkSize(v8ArrayData);
    }

    private void checkSize(V8ArrayData v8ArrayData) {
        if (v8ArrayData.size < 0) {
            throw new IllegalStateException("RangeError: Invalid typed array length");
        }
        if ((v8ArrayData.size * getStructureSize(v8ArrayData.type)) + v8ArrayData.offset > v8ArrayData.buffer.limit()) {
            throw new IllegalStateException("RangeError: Invalid typed array length");
        }
    }

    private void checkOffset(V8ArrayData v8ArrayData) {
        if (v8ArrayData.offset % getStructureSize(v8ArrayData.type) == 0) {
            return;
        }
        throw new IllegalStateException("RangeError: Start offset of Int32Array must be a multiple of " + getStructureSize(v8ArrayData.type));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.eclipsesource.v8.V8TypedArray$V8ArrayData */
    /* loaded from: classes2.dex */
    public static class V8ArrayData {
        private V8ArrayBuffer buffer;
        private int offset;
        private int size;
        private int type;

        public V8ArrayData(V8ArrayBuffer v8ArrayBuffer, int i, int i2, int i3) {
            this.buffer = v8ArrayBuffer;
            this.offset = i;
            this.size = i2;
            this.type = i3;
        }
    }
}
