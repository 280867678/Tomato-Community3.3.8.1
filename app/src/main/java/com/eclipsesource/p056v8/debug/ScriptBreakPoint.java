package com.eclipsesource.p056v8.debug;

import com.eclipsesource.p056v8.Releasable;
import com.eclipsesource.p056v8.V8Array;
import com.eclipsesource.p056v8.V8Object;
import com.eclipsesource.p056v8.V8ResultUndefined;

/* renamed from: com.eclipsesource.v8.debug.ScriptBreakPoint */
/* loaded from: classes2.dex */
public class ScriptBreakPoint implements Releasable {
    private static final String CONDITION = "condition";
    private static final String LINE = "line";
    private static final String NUMBER = "number";
    private static final String SET_CONDITION = "setCondition";
    private V8Object v8Object;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ScriptBreakPoint(V8Object v8Object) {
        this.v8Object = v8Object.mo5914twin();
    }

    public int getBreakPointNumber() {
        return this.v8Object.executeIntegerFunction(NUMBER, null);
    }

    public int getLine() {
        return this.v8Object.executeIntegerFunction(LINE, null);
    }

    public void setCondition(String str) {
        V8Array v8Array = new V8Array(this.v8Object.getRuntime());
        v8Array.push(str);
        try {
            this.v8Object.executeVoidFunction(SET_CONDITION, v8Array);
        } finally {
            v8Array.close();
        }
    }

    public String getCondition() {
        try {
            return this.v8Object.executeStringFunction(CONDITION, null);
        } catch (V8ResultUndefined unused) {
            return "undefined";
        }
    }

    @Override // com.eclipsesource.p056v8.Releasable, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        V8Object v8Object = this.v8Object;
        if (v8Object == null || v8Object.isReleased()) {
            return;
        }
        this.v8Object.close();
        this.v8Object = null;
    }

    @Override // com.eclipsesource.p056v8.Releasable
    public void release() {
        close();
    }
}
