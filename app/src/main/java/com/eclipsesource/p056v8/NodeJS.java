package com.eclipsesource.p056v8;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/* renamed from: com.eclipsesource.v8.NodeJS */
/* loaded from: classes2.dex */
public class NodeJS {
    private static final String GLOBAL = "global";
    private static final String NEXT_TICK = "nextTick";
    private static final String NODE = "node";
    private static final String PROCESS = "process";
    private static final String STARTUP_CALLBACK = "__run";
    private static final String STARTUP_SCRIPT = "global.__run(require, exports, module, __filename, __dirname);";
    private static final String STARTUP_SCRIPT_NAME = "startup";
    private static final String TMP_JS_EXT = ".js.tmp";
    private static final String VERSIONS = "versions";
    private String nodeVersion = null;
    private V8Function require;

    /* renamed from: v8 */
    private C1257V8 f1244v8;

    public static NodeJS createNodeJS() {
        return createNodeJS(null);
    }

    public String getNodeVersion() {
        V8Object v8Object;
        String str = this.nodeVersion;
        if (str != null) {
            return str;
        }
        V8Object v8Object2 = null;
        try {
            v8Object = this.f1244v8.getObject(PROCESS);
            try {
                v8Object2 = v8Object.getObject(VERSIONS);
                this.nodeVersion = v8Object2.getString(NODE);
                safeRelease(v8Object);
                safeRelease(v8Object2);
                return this.nodeVersion;
            } catch (Throwable th) {
                th = th;
                safeRelease(v8Object);
                safeRelease(v8Object2);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            v8Object = null;
        }
    }

    public static NodeJS createNodeJS(File file) {
        C1257V8 createV8Runtime = C1257V8.createV8Runtime(GLOBAL);
        NodeJS nodeJS = new NodeJS(createV8Runtime);
        createV8Runtime.registerJavaMethod(new JavaVoidCallback() { // from class: com.eclipsesource.v8.NodeJS.1
            @Override // com.eclipsesource.p056v8.JavaVoidCallback
            public void invoke(V8Object v8Object, V8Array v8Array) {
                V8Function v8Function = (V8Function) v8Array.get(0);
                try {
                    NodeJS.this.init(v8Function.mo5914twin());
                } finally {
                    v8Function.close();
                }
            }
        }, STARTUP_CALLBACK);
        try {
            File createTemporaryScriptFile = createTemporaryScriptFile(STARTUP_SCRIPT, STARTUP_SCRIPT_NAME);
            createV8Runtime.createNodeRuntime(createTemporaryScriptFile.getAbsolutePath());
            createTemporaryScriptFile.delete();
            if (file != null) {
                nodeJS.exec(file);
            }
            return nodeJS;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public C1257V8 getRuntime() {
        return this.f1244v8;
    }

    public boolean handleMessage() {
        this.f1244v8.checkThread();
        return this.f1244v8.pumpMessageLoop();
    }

    public void release() {
        this.f1244v8.checkThread();
        if (!this.require.isReleased()) {
            this.require.close();
        }
        if (!this.f1244v8.isReleased()) {
            this.f1244v8.close();
        }
    }

    public boolean isRunning() {
        this.f1244v8.checkThread();
        return this.f1244v8.isRunning();
    }

    public V8Object require(File file) {
        this.f1244v8.checkThread();
        V8Array v8Array = new V8Array(this.f1244v8);
        try {
            v8Array.push(file.getAbsolutePath());
            return (V8Object) this.require.call(null, v8Array);
        } finally {
            v8Array.close();
        }
    }

    public void exec(File file) {
        V8Array v8Array;
        Throwable th;
        V8Object v8Object;
        V8Function createScriptExecutionCallback = createScriptExecutionCallback(file);
        try {
            v8Object = this.f1244v8.getObject(PROCESS);
            try {
                v8Array = new V8Array(this.f1244v8);
                try {
                    v8Array.push((V8Value) createScriptExecutionCallback);
                    v8Object.executeObjectFunction(NEXT_TICK, v8Array);
                    safeRelease(v8Object);
                    safeRelease(v8Array);
                    safeRelease(createScriptExecutionCallback);
                } catch (Throwable th2) {
                    th = th2;
                    safeRelease(v8Object);
                    safeRelease(v8Array);
                    safeRelease(createScriptExecutionCallback);
                    throw th;
                }
            } catch (Throwable th3) {
                v8Array = null;
                th = th3;
            }
        } catch (Throwable th4) {
            v8Array = null;
            th = th4;
            v8Object = null;
        }
    }

    private V8Function createScriptExecutionCallback(final File file) {
        return new V8Function(this.f1244v8, new JavaCallback() { // from class: com.eclipsesource.v8.NodeJS.2
            @Override // com.eclipsesource.p056v8.JavaCallback
            public Object invoke(V8Object v8Object, V8Array v8Array) {
                V8Array v8Array2 = new V8Array(NodeJS.this.f1244v8);
                try {
                    v8Array2.push(file.getAbsolutePath());
                    return NodeJS.this.require.call(null, v8Array2);
                } finally {
                    v8Array2.close();
                }
            }
        });
    }

    private void safeRelease(Releasable releasable) {
        if (releasable != null) {
            releasable.release();
        }
    }

    private NodeJS(C1257V8 c1257v8) {
        this.f1244v8 = c1257v8;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void init(V8Function v8Function) {
        this.require = v8Function;
    }

    private static File createTemporaryScriptFile(String str, String str2) throws IOException {
        File createTempFile = File.createTempFile(str2, TMP_JS_EXT);
        PrintWriter printWriter = new PrintWriter(createTempFile, "UTF-8");
        try {
            printWriter.print(str);
            return createTempFile;
        } finally {
            printWriter.close();
        }
    }
}
