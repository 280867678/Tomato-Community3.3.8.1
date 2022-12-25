package com.eclipsesource.p056v8;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* renamed from: com.eclipsesource.v8.V8ArrayBuffer */
/* loaded from: classes2.dex */
public class V8ArrayBuffer extends V8Value {
    ByteBuffer byteBuffer;

    public V8ArrayBuffer(C1257V8 c1257v8, int i) {
        super(c1257v8);
        initialize(c1257v8.getV8RuntimePtr(), Integer.valueOf(i));
        this.byteBuffer = c1257v8.createV8ArrayBufferBackingStore(c1257v8.getV8RuntimePtr(), this.objectHandle, i);
        this.byteBuffer.order(ByteOrder.nativeOrder());
    }

    public V8ArrayBuffer(C1257V8 c1257v8, ByteBuffer byteBuffer) {
        super(c1257v8);
        byteBuffer = byteBuffer == null ? ByteBuffer.allocateDirect(0) : byteBuffer;
        if (!byteBuffer.isDirect()) {
            throw new IllegalArgumentException("ByteBuffer must be a allocated as a direct ByteBuffer");
        }
        initialize(c1257v8.getV8RuntimePtr(), byteBuffer);
        this.byteBuffer = byteBuffer;
        byteBuffer.order(ByteOrder.nativeOrder());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.eclipsesource.p056v8.V8Value
    public void initialize(long j, Object obj) {
        this.f1245v8.checkThread();
        if (obj instanceof ByteBuffer) {
            ByteBuffer byteBuffer = (ByteBuffer) obj;
            int limit = byteBuffer.limit();
            C1257V8 c1257v8 = this.f1245v8;
            this.objectHandle = c1257v8.initNewV8ArrayBuffer(c1257v8.getV8RuntimePtr(), byteBuffer, limit);
        } else {
            int intValue = ((Integer) obj).intValue();
            C1257V8 c1257v82 = this.f1245v8;
            this.objectHandle = c1257v82.initNewV8ArrayBuffer(c1257v82.getV8RuntimePtr(), intValue);
        }
        this.released = false;
        addObjectReference(this.objectHandle);
    }

    @Override // com.eclipsesource.p056v8.V8Value
    protected V8Value createTwin() {
        return new V8ArrayBuffer(this.f1245v8, this.byteBuffer);
    }

    @Override // com.eclipsesource.p056v8.V8Value
    /* renamed from: twin */
    public V8ArrayBuffer mo5914twin() {
        this.f1245v8.checkThread();
        checkReleased();
        return (V8ArrayBuffer) super.mo5914twin();
    }

    public int limit() {
        this.f1245v8.checkThread();
        checkReleased();
        return this.byteBuffer.limit();
    }

    public final int capacity() {
        this.f1245v8.checkThread();
        checkReleased();
        return this.byteBuffer.capacity();
    }

    public final int position() {
        this.f1245v8.checkThread();
        checkReleased();
        return this.byteBuffer.position();
    }

    public final V8ArrayBuffer position(int i) {
        this.f1245v8.checkThread();
        checkReleased();
        this.byteBuffer.position(i);
        return this;
    }

    public final V8ArrayBuffer limit(int i) {
        this.f1245v8.checkThread();
        checkReleased();
        this.byteBuffer.limit(i);
        return this;
    }

    public final V8ArrayBuffer mark() {
        this.f1245v8.checkThread();
        checkReleased();
        this.byteBuffer.mark();
        return this;
    }

    public final V8ArrayBuffer reset() {
        this.f1245v8.checkThread();
        checkReleased();
        this.byteBuffer.reset();
        return this;
    }

    public final V8ArrayBuffer clear() {
        this.f1245v8.checkThread();
        checkReleased();
        this.byteBuffer.clear();
        return this;
    }

    public final V8ArrayBuffer flip() {
        this.f1245v8.checkThread();
        checkReleased();
        this.byteBuffer.flip();
        return this;
    }

    public final V8ArrayBuffer rewind() {
        this.f1245v8.checkThread();
        checkReleased();
        this.byteBuffer.rewind();
        return this;
    }

    public final int remaining() {
        this.f1245v8.checkThread();
        checkReleased();
        return this.byteBuffer.remaining();
    }

    public final boolean hasRemaining() {
        this.f1245v8.checkThread();
        checkReleased();
        return this.byteBuffer.hasRemaining();
    }

    public boolean isReadOnly() {
        this.f1245v8.checkThread();
        checkReleased();
        return this.byteBuffer.isReadOnly();
    }

    public byte get() {
        this.f1245v8.checkThread();
        checkReleased();
        return this.byteBuffer.get();
    }

    public V8ArrayBuffer put(byte b) {
        this.f1245v8.checkThread();
        checkReleased();
        this.byteBuffer.put(b);
        return this;
    }

    public byte get(int i) {
        this.f1245v8.checkThread();
        checkReleased();
        return this.byteBuffer.get(i);
    }

    public V8ArrayBuffer put(int i, byte b) {
        this.f1245v8.checkThread();
        checkReleased();
        this.byteBuffer.put(i, b);
        return this;
    }

    public V8ArrayBuffer get(byte[] bArr, int i, int i2) {
        this.f1245v8.checkThread();
        checkReleased();
        this.byteBuffer.get(bArr, i, i2);
        return this;
    }

    public V8ArrayBuffer get(byte[] bArr) {
        this.f1245v8.checkThread();
        checkReleased();
        this.byteBuffer.get(bArr);
        return this;
    }

    public V8ArrayBuffer put(ByteBuffer byteBuffer) {
        this.f1245v8.checkThread();
        checkReleased();
        this.byteBuffer.put(byteBuffer);
        return this;
    }

    public V8ArrayBuffer put(byte[] bArr, int i, int i2) {
        this.f1245v8.checkThread();
        checkReleased();
        this.byteBuffer.put(bArr, i, i2);
        return this;
    }

    public final V8ArrayBuffer put(byte[] bArr) {
        this.f1245v8.checkThread();
        checkReleased();
        this.byteBuffer.put(bArr);
        return this;
    }

    public final boolean hasArray() {
        this.f1245v8.checkThread();
        checkReleased();
        return this.byteBuffer.hasArray();
    }

    public final byte[] array() {
        this.f1245v8.checkThread();
        checkReleased();
        return this.byteBuffer.array();
    }

    public final int arrayOffset() {
        this.f1245v8.checkThread();
        checkReleased();
        return this.byteBuffer.arrayOffset();
    }

    public V8ArrayBuffer compact() {
        this.f1245v8.checkThread();
        checkReleased();
        this.byteBuffer.compact();
        return this;
    }

    public boolean isDirect() {
        this.f1245v8.checkThread();
        checkReleased();
        return this.byteBuffer.isDirect();
    }

    public final ByteOrder order() {
        this.f1245v8.checkThread();
        checkReleased();
        return this.byteBuffer.order();
    }

    public final V8ArrayBuffer order(ByteOrder byteOrder) {
        this.f1245v8.checkThread();
        checkReleased();
        this.byteBuffer.order(byteOrder);
        return this;
    }

    public char getChar() {
        this.f1245v8.checkThread();
        checkReleased();
        return this.byteBuffer.getChar();
    }

    public V8ArrayBuffer putChar(char c) {
        this.f1245v8.checkThread();
        checkReleased();
        this.byteBuffer.putChar(c);
        return this;
    }

    public char getChar(int i) {
        this.f1245v8.checkThread();
        checkReleased();
        return this.byteBuffer.getChar(i);
    }

    public V8ArrayBuffer putChar(int i, char c) {
        this.f1245v8.checkThread();
        checkReleased();
        this.byteBuffer.putChar(i, c);
        return this;
    }

    public short getShort() {
        this.f1245v8.checkThread();
        checkReleased();
        return this.byteBuffer.getShort();
    }

    public V8ArrayBuffer putShort(short s) {
        this.f1245v8.checkThread();
        checkReleased();
        this.byteBuffer.putShort(s);
        return this;
    }

    public short getShort(int i) {
        this.f1245v8.checkThread();
        checkReleased();
        return this.byteBuffer.getShort(i);
    }

    public V8ArrayBuffer putShort(int i, short s) {
        this.f1245v8.checkThread();
        checkReleased();
        this.byteBuffer.putShort(i, s);
        return this;
    }

    public int getInt() {
        this.f1245v8.checkThread();
        checkReleased();
        return this.byteBuffer.getInt();
    }

    public V8ArrayBuffer putInt(int i) {
        this.f1245v8.checkThread();
        checkReleased();
        this.byteBuffer.putInt(i);
        return this;
    }

    public int getInt(int i) {
        this.f1245v8.checkThread();
        checkReleased();
        return this.byteBuffer.getInt(i);
    }

    public V8ArrayBuffer putInt(int i, int i2) {
        this.f1245v8.checkThread();
        checkReleased();
        this.byteBuffer.asIntBuffer().put(i, i2);
        return this;
    }

    public long getLong() {
        this.f1245v8.checkThread();
        checkReleased();
        return this.byteBuffer.getLong();
    }

    public V8ArrayBuffer putLong(long j) {
        this.f1245v8.checkThread();
        checkReleased();
        this.byteBuffer.putLong(j);
        return this;
    }

    public long getLong(int i) {
        this.f1245v8.checkThread();
        checkReleased();
        return this.byteBuffer.getLong(i);
    }

    public V8ArrayBuffer putLong(int i, long j) {
        this.f1245v8.checkThread();
        checkReleased();
        this.byteBuffer.putLong(i, j);
        return this;
    }

    public float getFloat() {
        this.f1245v8.checkThread();
        checkReleased();
        return this.byteBuffer.getFloat();
    }

    public V8ArrayBuffer putFloat(float f) {
        this.f1245v8.checkThread();
        checkReleased();
        this.byteBuffer.putFloat(f);
        return this;
    }

    public float getFloat(int i) {
        this.f1245v8.checkThread();
        checkReleased();
        return this.byteBuffer.getFloat(i);
    }

    public V8ArrayBuffer putFloat(int i, float f) {
        this.f1245v8.checkThread();
        checkReleased();
        this.byteBuffer.putFloat(i, f);
        return this;
    }

    public double getDouble() {
        this.f1245v8.checkThread();
        checkReleased();
        return this.byteBuffer.getDouble();
    }

    public V8ArrayBuffer putDouble(double d) {
        this.f1245v8.checkThread();
        checkReleased();
        this.byteBuffer.putDouble(d);
        return this;
    }

    public double getDouble(int i) {
        this.f1245v8.checkThread();
        checkReleased();
        return this.byteBuffer.getDouble(i);
    }

    public V8ArrayBuffer putDouble(int i, double d) {
        this.f1245v8.checkThread();
        checkReleased();
        this.byteBuffer.putDouble(i, d);
        return this;
    }

    public int floatLimit() {
        this.f1245v8.checkThread();
        checkReleased();
        return this.byteBuffer.asFloatBuffer().limit();
    }

    public int intLimit() {
        this.f1245v8.checkThread();
        checkReleased();
        return this.byteBuffer.asIntBuffer().limit();
    }

    public int shortLimit() {
        this.f1245v8.checkThread();
        checkReleased();
        return this.byteBuffer.asShortBuffer().limit();
    }

    public int doubleLimit() {
        this.f1245v8.checkThread();
        checkReleased();
        return this.byteBuffer.asDoubleBuffer().limit();
    }
}
