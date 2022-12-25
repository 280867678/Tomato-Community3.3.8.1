package com.eclipsesource.p056v8.debug;

import com.eclipsesource.p056v8.Releasable;
import com.eclipsesource.p056v8.V8Object;

/* renamed from: com.eclipsesource.v8.debug.EventData */
/* loaded from: classes2.dex */
public class EventData implements Releasable {
    protected V8Object v8Object;

    /* JADX INFO: Access modifiers changed from: package-private */
    public EventData(V8Object v8Object) {
        this.v8Object = v8Object.mo5914twin();
    }

    @Override // com.eclipsesource.p056v8.Releasable, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        if (!this.v8Object.isReleased()) {
            this.v8Object.close();
        }
    }

    @Override // com.eclipsesource.p056v8.Releasable
    @Deprecated
    public void release() {
        close();
    }
}
