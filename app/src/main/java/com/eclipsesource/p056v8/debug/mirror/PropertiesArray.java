package com.eclipsesource.p056v8.debug.mirror;

import com.eclipsesource.p056v8.Releasable;
import com.eclipsesource.p056v8.V8Array;
import com.eclipsesource.p056v8.V8Object;

/* renamed from: com.eclipsesource.v8.debug.mirror.PropertiesArray */
/* loaded from: classes2.dex */
public class PropertiesArray implements Releasable {
    private V8Array v8Array;

    /* JADX INFO: Access modifiers changed from: package-private */
    public PropertiesArray(V8Array v8Array) {
        this.v8Array = v8Array.mo5914twin();
    }

    public PropertyMirror getProperty(int i) {
        V8Object object = this.v8Array.getObject(i);
        try {
            return new PropertyMirror(object);
        } finally {
            object.close();
        }
    }

    @Override // com.eclipsesource.p056v8.Releasable, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        if (!this.v8Array.isReleased()) {
            this.v8Array.close();
        }
    }

    @Override // com.eclipsesource.p056v8.Releasable
    @Deprecated
    public void release() {
        close();
    }

    public int length() {
        return this.v8Array.length();
    }
}
