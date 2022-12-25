package com.eclipsesource.p056v8.utils;

import com.eclipsesource.p056v8.C1257V8;
import com.eclipsesource.p056v8.V8ArrayBuffer;
import java.nio.ByteBuffer;

/* renamed from: com.eclipsesource.v8.utils.ArrayBuffer */
/* loaded from: classes2.dex */
public class ArrayBuffer {
    private V8ArrayBuffer arrayBuffer;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ArrayBuffer(V8ArrayBuffer v8ArrayBuffer) {
        this.arrayBuffer = (V8ArrayBuffer) v8ArrayBuffer.mo5914twin().setWeak();
    }

    public ArrayBuffer(C1257V8 c1257v8, ByteBuffer byteBuffer) {
        V8ArrayBuffer v8ArrayBuffer = new V8ArrayBuffer(c1257v8, byteBuffer);
        try {
            this.arrayBuffer = (V8ArrayBuffer) v8ArrayBuffer.mo5914twin().setWeak();
        } finally {
            v8ArrayBuffer.close();
        }
    }

    public boolean isAvailable() {
        return !this.arrayBuffer.isReleased();
    }

    public V8ArrayBuffer getV8ArrayBuffer() {
        return this.arrayBuffer.mo5914twin();
    }
}
