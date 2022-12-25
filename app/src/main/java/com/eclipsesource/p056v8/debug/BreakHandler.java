package com.eclipsesource.p056v8.debug;

import com.eclipsesource.p056v8.V8Object;
import com.eclipsesource.p056v8.debug.DebugHandler;

/* renamed from: com.eclipsesource.v8.debug.BreakHandler */
/* loaded from: classes2.dex */
public interface BreakHandler {
    void onBreak(DebugHandler.DebugEvent debugEvent, ExecutionState executionState, EventData eventData, V8Object v8Object);
}
