package com.eclipsesource.p056v8.inspector;

import com.eclipsesource.p056v8.C1257V8;
import com.eclipsesource.p056v8.V8Object;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.eclipsesource.v8.inspector.V8Inspector */
/* loaded from: classes2.dex */
public class V8Inspector {
    private long inspectorPtr;
    private C1257V8 runtime;
    private boolean waitingForConnection = true;
    private List<DebuggerConnectionListener> debuggerConnectionListeners = new ArrayList();

    protected V8Inspector(C1257V8 c1257v8, V8InspectorDelegate v8InspectorDelegate, String str) {
        this.inspectorPtr = 0L;
        this.runtime = c1257v8;
        this.inspectorPtr = c1257v8.createInspector(v8InspectorDelegate, str);
    }

    public static V8Inspector createV8Inspector(C1257V8 c1257v8, V8InspectorDelegate v8InspectorDelegate, String str) {
        return new V8Inspector(c1257v8, v8InspectorDelegate, str);
    }

    public static V8Inspector createV8Inspector(C1257V8 c1257v8, V8InspectorDelegate v8InspectorDelegate) {
        return new V8Inspector(c1257v8, v8InspectorDelegate, null);
    }

    public void dispatchProtocolMessage(String str) {
        try {
            this.runtime.dispatchProtocolMessage(this.inspectorPtr, str);
            if (!this.waitingForConnection) {
                return;
            }
            verifyDebuggerConnection(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addDebuggerConnectionListener(DebuggerConnectionListener debuggerConnectionListener) {
        this.debuggerConnectionListeners.add(debuggerConnectionListener);
    }

    public void removeDebuggerConnectionListener(DebuggerConnectionListener debuggerConnectionListener) {
        this.debuggerConnectionListeners.remove(debuggerConnectionListener);
    }

    private void verifyDebuggerConnection(String str) {
        V8Object v8Object = null;
        try {
            C1257V8 c1257v8 = this.runtime;
            v8Object = c1257v8.executeObjectScript("JSON.parse(JSON.stringify(" + str + "))");
            if (v8Object.getString("method").equals("Runtime.runIfWaitingForDebugger")) {
                this.waitingForConnection = false;
                this.runtime.schedulePauseOnNextStatement(this.inspectorPtr, "");
                for (DebuggerConnectionListener debuggerConnectionListener : this.debuggerConnectionListeners) {
                    debuggerConnectionListener.onDebuggerConnected();
                }
            }
        } finally {
            if (v8Object != null) {
                v8Object.close();
            }
        }
    }
}
