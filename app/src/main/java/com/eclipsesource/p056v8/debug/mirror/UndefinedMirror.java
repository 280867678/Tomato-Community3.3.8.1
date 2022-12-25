package com.eclipsesource.p056v8.debug.mirror;

import com.eclipsesource.p056v8.V8Object;

/* renamed from: com.eclipsesource.v8.debug.mirror.UndefinedMirror */
/* loaded from: classes2.dex */
public class UndefinedMirror extends ValueMirror {
    @Override // com.eclipsesource.p056v8.debug.mirror.Mirror
    public boolean isUndefined() {
        return true;
    }

    @Override // com.eclipsesource.p056v8.debug.mirror.Mirror
    public String toString() {
        return "undefined";
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public UndefinedMirror(V8Object v8Object) {
        super(v8Object);
    }
}
