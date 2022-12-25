package com.eclipsesource.p056v8.utils;

import com.eclipsesource.p056v8.C1257V8;
import com.eclipsesource.p056v8.V8ArrayBuffer;
import com.eclipsesource.p056v8.V8TypedArray;

/* renamed from: com.eclipsesource.v8.utils.TypedArray */
/* loaded from: classes2.dex */
public class TypedArray {
    private V8TypedArray typedArray;

    /* JADX INFO: Access modifiers changed from: package-private */
    public TypedArray(V8TypedArray v8TypedArray) {
        this.typedArray = (V8TypedArray) v8TypedArray.mo5914twin().setWeak();
    }

    public TypedArray(C1257V8 c1257v8, ArrayBuffer arrayBuffer, int i, int i2, int i3) {
        V8ArrayBuffer v8ArrayBuffer = arrayBuffer.getV8ArrayBuffer();
        V8TypedArray v8TypedArray = new V8TypedArray(c1257v8, v8ArrayBuffer, i, i2, i3);
        try {
            this.typedArray = (V8TypedArray) v8TypedArray.mo5914twin().setWeak();
        } finally {
            v8ArrayBuffer.close();
            v8TypedArray.close();
        }
    }

    public boolean isAvailable() {
        return !this.typedArray.isReleased();
    }

    public V8TypedArray getV8TypedArray() {
        return (V8TypedArray) this.typedArray.mo5914twin();
    }
}
