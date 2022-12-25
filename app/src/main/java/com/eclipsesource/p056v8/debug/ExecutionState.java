package com.eclipsesource.p056v8.debug;

import com.eclipsesource.p056v8.Releasable;
import com.eclipsesource.p056v8.V8Array;
import com.eclipsesource.p056v8.V8Object;
import com.eclipsesource.p056v8.debug.mirror.Frame;

/* renamed from: com.eclipsesource.v8.debug.ExecutionState */
/* loaded from: classes2.dex */
public class ExecutionState implements Releasable {
    private static final String FRAME = "frame";
    private static final String FRAME_COUNT = "frameCount";
    private static final String PREPARE_STEP = "prepareStep";
    private V8Object v8Object;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ExecutionState(V8Object v8Object) {
        this.v8Object = v8Object.mo5914twin();
    }

    public int getFrameCount() {
        return this.v8Object.executeIntegerFunction(FRAME_COUNT, null);
    }

    public void prepareStep(StepAction stepAction) {
        V8Array v8Array = new V8Array(this.v8Object.getRuntime());
        v8Array.push(stepAction.index);
        try {
            this.v8Object.executeVoidFunction(PREPARE_STEP, v8Array);
        } finally {
            v8Array.close();
        }
    }

    public Frame getFrame(int i) {
        V8Array v8Array = new V8Array(this.v8Object.getRuntime());
        v8Array.push(i);
        V8Object v8Object = null;
        try {
            v8Object = this.v8Object.executeObjectFunction(FRAME, v8Array);
            return new Frame(v8Object);
        } finally {
            v8Array.close();
            if (v8Object != null) {
                v8Object.close();
            }
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
    @Deprecated
    public void release() {
        close();
    }
}
