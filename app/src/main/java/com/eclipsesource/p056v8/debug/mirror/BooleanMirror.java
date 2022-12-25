package com.eclipsesource.p056v8.debug.mirror;

import com.eclipsesource.p056v8.V8Object;

/* renamed from: com.eclipsesource.v8.debug.mirror.BooleanMirror */
/* loaded from: classes2.dex */
public class BooleanMirror extends ValueMirror {
    @Override // com.eclipsesource.p056v8.debug.mirror.Mirror
    public boolean isBoolean() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BooleanMirror(V8Object v8Object) {
        super(v8Object);
    }

    @Override // com.eclipsesource.p056v8.debug.mirror.Mirror
    public String toString() {
        return this.v8Object.executeStringFunction("toText", null);
    }
}
