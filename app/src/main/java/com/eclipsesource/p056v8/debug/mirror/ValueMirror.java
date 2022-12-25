package com.eclipsesource.p056v8.debug.mirror;

import com.eclipsesource.p056v8.V8Object;

/* renamed from: com.eclipsesource.v8.debug.mirror.ValueMirror */
/* loaded from: classes2.dex */
public class ValueMirror extends Mirror {
    private static final String VALUE = "value";

    @Override // com.eclipsesource.p056v8.debug.mirror.Mirror
    public boolean isValue() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ValueMirror(V8Object v8Object) {
        super(v8Object);
    }

    public Object getValue() {
        return this.v8Object.executeFunction(VALUE, null);
    }
}
