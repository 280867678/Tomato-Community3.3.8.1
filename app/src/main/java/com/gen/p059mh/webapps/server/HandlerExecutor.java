package com.gen.p059mh.webapps.server;

import android.os.Handler;
import android.os.HandlerThread;
import com.eclipsesource.p056v8.C1257V8;
import com.eclipsesource.p056v8.V8Object;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.gen.p059mh.webapps.utils.Logger;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/* renamed from: com.gen.mh.webapps.server.HandlerExecutor */
/* loaded from: classes2.dex */
public class HandlerExecutor {
    ExecutorService executorService;
    Handler handler;
    boolean isDebug;
    ScriptProvider scriptSourceProvider;

    /* renamed from: v8 */
    C1257V8 f1302v8;

    public void debugV8Init() {
    }

    public HandlerExecutor(boolean z, IWebFragmentController iWebFragmentController) {
        this.isDebug = z;
        Logger.m4112i("init runtime", Boolean.valueOf(z));
        init();
    }

    public void init() {
        if (this.isDebug) {
            debugV8Init();
        } else {
            normalV8Init();
        }
        Logger.m4112i("init runtime", Boolean.valueOf(this.isDebug));
    }

    public void normalV8Init() {
        HandlerThread handlerThread = new HandlerThread("handler");
        handlerThread.start();
        this.handler = new Handler(handlerThread.getLooper());
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        this.handler.post(new Runnable() { // from class: com.gen.mh.webapps.server.-$$Lambda$HandlerExecutor$Rp2qDK16Nui3wVMNwm5OYiW20CM
            @Override // java.lang.Runnable
            public final void run() {
                HandlerExecutor.this.lambda$normalV8Init$0$HandlerExecutor(countDownLatch);
            }
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public /* synthetic */ void lambda$normalV8Init$0$HandlerExecutor(CountDownLatch countDownLatch) {
        this.f1302v8 = C1257V8.createV8Runtime();
        countDownLatch.countDown();
    }

    public void post(Runnable runnable) {
        if (this.isDebug) {
            this.executorService.submit(runnable);
        } else {
            this.handler.post(runnable);
        }
    }

    public C1257V8 getV8Runtime() {
        return this.f1302v8;
    }

    public void add(String str) {
        if (!this.isDebug) {
            return;
        }
        this.scriptSourceProvider.add(str);
        throw null;
    }

    public void add(String str, String str2) {
        if (!this.isDebug) {
            return;
        }
        this.scriptSourceProvider.add(str, str2);
        throw null;
    }

    public void executeScript(String str, String str2, int i) {
        if (this.isDebug) {
            add(str2);
        }
        try {
            this.f1302v8.executeScript(str, str2, i);
        } catch (Exception unused) {
        }
    }

    public V8Object executeObjectScript(String str, String str2, int i) {
        if (this.isDebug) {
            add(str2, str);
        }
        return this.f1302v8.executeObjectScript(str, str2, i);
    }

    public void destroy() {
        if (this.isDebug) {
            this.scriptSourceProvider = null;
            this.executorService.shutdown();
        }
    }
}
