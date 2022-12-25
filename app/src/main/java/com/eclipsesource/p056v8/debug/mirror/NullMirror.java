package com.eclipsesource.p056v8.debug.mirror;

import com.eclipsesource.p056v8.V8Object;

/* renamed from: com.eclipsesource.v8.debug.mirror.NullMirror */
/* loaded from: classes2.dex */
public class NullMirror extends ValueMirror {
    @Override // com.eclipsesource.p056v8.debug.mirror.Mirror
    public boolean isNull() {
        return true;
    }

    @Override // com.eclipsesource.p056v8.debug.mirror.Mirror
    public String toString() {
        return "null";
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public NullMirror(V8Object v8Object) {
        super(v8Object);
    }
}
