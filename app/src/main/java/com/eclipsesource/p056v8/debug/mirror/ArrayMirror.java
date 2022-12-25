package com.eclipsesource.p056v8.debug.mirror;

import com.eclipsesource.p056v8.V8Object;

/* renamed from: com.eclipsesource.v8.debug.mirror.ArrayMirror */
/* loaded from: classes2.dex */
public class ArrayMirror extends ObjectMirror {
    private static final String LENGTH = "length";

    @Override // com.eclipsesource.p056v8.debug.mirror.Mirror
    public boolean isArray() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ArrayMirror(V8Object v8Object) {
        super(v8Object);
    }

    public int length() {
        return this.v8Object.executeIntegerFunction(LENGTH, null);
    }
}
