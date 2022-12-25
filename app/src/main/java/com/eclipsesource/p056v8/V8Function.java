package com.eclipsesource.p056v8;

/* renamed from: com.eclipsesource.v8.V8Function */
/* loaded from: classes2.dex */
public class V8Function extends V8Object {
    public V8Function(C1257V8 c1257v8, JavaCallback javaCallback) {
        super(c1257v8, javaCallback);
    }

    protected V8Function(C1257V8 c1257v8) {
        this(c1257v8, null);
    }

    @Override // com.eclipsesource.p056v8.V8Object, com.eclipsesource.p056v8.V8Value
    protected V8Value createTwin() {
        return new V8Function(this.f1245v8);
    }

    @Override // com.eclipsesource.p056v8.V8Object
    public String toString() {
        return (this.released || this.f1245v8.isReleased()) ? "[Function released]" : super.toString();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.eclipsesource.p056v8.V8Value
    public void initialize(long j, Object obj) {
        if (obj == null) {
            super.initialize(j, null);
            return;
        }
        long[] initNewV8Function = this.f1245v8.initNewV8Function(j);
        this.f1245v8.createAndRegisterMethodDescriptor((JavaCallback) obj, initNewV8Function[1]);
        this.released = false;
        addObjectReference(initNewV8Function[0]);
    }

    @Override // com.eclipsesource.p056v8.V8Object, com.eclipsesource.p056v8.V8Value
    /* renamed from: twin */
    public V8Function mo5914twin() {
        return (V8Function) super.mo5914twin();
    }

    public Object call(V8Object v8Object, V8Array v8Array) {
        this.f1245v8.checkThread();
        checkReleased();
        this.f1245v8.checkRuntime(v8Object);
        this.f1245v8.checkRuntime(v8Array);
        if (v8Object == null) {
            v8Object = this.f1245v8;
        }
        long handle = v8Array == null ? 0L : v8Array.getHandle();
        if (v8Object.isUndefined()) {
            v8Object = this.f1245v8;
        }
        long handle2 = v8Object.getHandle();
        C1257V8 c1257v8 = this.f1245v8;
        return c1257v8.executeFunction(c1257v8.getV8RuntimePtr(), handle2, this.objectHandle, handle);
    }
}
