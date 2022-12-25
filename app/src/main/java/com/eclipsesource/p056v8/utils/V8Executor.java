package com.eclipsesource.p056v8.utils;

import com.eclipsesource.p056v8.C1257V8;
import com.eclipsesource.p056v8.JavaVoidCallback;
import com.eclipsesource.p056v8.Releasable;
import com.eclipsesource.p056v8.V8Array;
import com.eclipsesource.p056v8.V8Object;
import java.util.LinkedList;

/* renamed from: com.eclipsesource.v8.utils.V8Executor */
/* loaded from: classes2.dex */
public class V8Executor extends Thread {
    private Exception exception;
    private volatile boolean forceTerminating;
    private boolean longRunning;
    private String messageHandler;
    private LinkedList<String[]> messageQueue;
    private String result;
    private C1257V8 runtime;
    private final String script;
    private volatile boolean shuttingDown;
    private volatile boolean terminated;

    protected void setup(C1257V8 c1257v8) {
    }

    public V8Executor(String str, boolean z, String str2) {
        this.terminated = false;
        this.shuttingDown = false;
        this.forceTerminating = false;
        this.exception = null;
        this.messageQueue = new LinkedList<>();
        this.script = str;
        this.longRunning = z;
        this.messageHandler = str2;
    }

    public V8Executor(String str) {
        this(str, false, null);
    }

    public String getResult() {
        return this.result;
    }

    public void postMessage(String... strArr) {
        synchronized (this) {
            this.messageQueue.add(strArr);
            notify();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:51:0x009b, code lost:
        if (r8.messageQueue.isEmpty() != false) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x009d, code lost:
        r3 = 0;
        r2 = r8.messageQueue.remove(0);
        r4 = new com.eclipsesource.p056v8.V8Array(r8.runtime);
        r5 = new com.eclipsesource.p056v8.V8Array(r8.runtime);
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00b4, code lost:
        r6 = r2.length;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00b5, code lost:
        if (r3 >= r6) goto L58;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00b7, code lost:
        r5.push(r2[r3]);
        r3 = r3 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00bf, code lost:
        r4.push((com.eclipsesource.p056v8.V8Value) r5);
        r8.runtime.executeVoidFunction(r8.messageHandler, r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00c9, code lost:
        r5.close();
        r4.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00d0, code lost:
        r2 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00d1, code lost:
        r5.close();
        r4.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x00d7, code lost:
        throw r2;
     */
    @Override // java.lang.Thread, java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void run() {
        synchronized (this) {
            this.runtime = C1257V8.createV8Runtime();
            this.runtime.registerJavaMethod(new ExecutorTermination(), "__j2v8__checkThreadTerminate");
            setup(this.runtime);
        }
        try {
            try {
                if (!this.forceTerminating) {
                    C1257V8 c1257v8 = this.runtime;
                    Object executeScript = c1257v8.executeScript("__j2v8__checkThreadTerminate();\n" + this.script, getName(), -1);
                    if (executeScript != null) {
                        this.result = executeScript.toString();
                    }
                    if (executeScript instanceof Releasable) {
                        ((Releasable) executeScript).release();
                    }
                }
                while (!this.forceTerminating && this.longRunning) {
                    synchronized (this) {
                        if (this.messageQueue.isEmpty() && !this.shuttingDown) {
                            wait();
                        }
                        if ((!this.messageQueue.isEmpty() || !this.shuttingDown) && !this.forceTerminating) {
                        }
                    }
                    synchronized (this) {
                        if (this.runtime.getLocker().hasLock()) {
                            this.runtime.close();
                            this.runtime = null;
                        }
                        this.terminated = true;
                    }
                    return;
                }
                synchronized (this) {
                    if (this.runtime.getLocker().hasLock()) {
                        this.runtime.close();
                        this.runtime = null;
                    }
                    this.terminated = true;
                }
            } catch (Exception e) {
                this.exception = e;
                synchronized (this) {
                    if (this.runtime.getLocker().hasLock()) {
                        this.runtime.close();
                        this.runtime = null;
                    }
                    this.terminated = true;
                }
            }
        } catch (Throwable th) {
            synchronized (this) {
                if (this.runtime.getLocker().hasLock()) {
                    this.runtime.close();
                    this.runtime = null;
                }
                this.terminated = true;
                throw th;
            }
        }
    }

    public boolean hasException() {
        return this.exception != null;
    }

    public Exception getException() {
        return this.exception;
    }

    public boolean hasTerminated() {
        return this.terminated;
    }

    public void forceTermination() {
        synchronized (this) {
            this.forceTerminating = true;
            this.shuttingDown = true;
            if (this.runtime != null) {
                this.runtime.terminateExecution();
            }
            notify();
        }
    }

    public void shutdown() {
        synchronized (this) {
            this.shuttingDown = true;
            notify();
        }
    }

    public boolean isShuttingDown() {
        return this.shuttingDown;
    }

    public boolean isTerminating() {
        return this.forceTerminating;
    }

    /* renamed from: com.eclipsesource.v8.utils.V8Executor$ExecutorTermination */
    /* loaded from: classes2.dex */
    class ExecutorTermination implements JavaVoidCallback {
        ExecutorTermination() {
        }

        @Override // com.eclipsesource.p056v8.JavaVoidCallback
        public void invoke(V8Object v8Object, V8Array v8Array) {
            if (!V8Executor.this.forceTerminating) {
                return;
            }
            throw new RuntimeException("V8Thread Termination");
        }
    }
}
