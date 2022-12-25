package com.eclipsesource.p056v8.debug.mirror;

import com.eclipsesource.p056v8.V8Object;

/* renamed from: com.eclipsesource.v8.debug.mirror.PropertyMirror */
/* loaded from: classes2.dex */
public class PropertyMirror extends Mirror {
    @Override // com.eclipsesource.p056v8.debug.mirror.Mirror
    public boolean isProperty() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public PropertyMirror(V8Object v8Object) {
        super(v8Object);
    }

    public String getName() {
        return this.v8Object.executeStringFunction("name", null);
    }

    public Mirror getValue() {
        V8Object executeObjectFunction = this.v8Object.executeObjectFunction("value", null);
        try {
            return Mirror.createMirror(executeObjectFunction);
        } finally {
            executeObjectFunction.close();
        }
    }
}
