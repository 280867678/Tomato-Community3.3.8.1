package com.gen.p059mh.webapps.server.worker;

import com.eclipsesource.p056v8.C1257V8;
import com.eclipsesource.p056v8.JavaCallback;
import com.eclipsesource.p056v8.V8Array;
import com.eclipsesource.p056v8.V8Object;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.gen.p059mh.webapps.utils.Logger;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/* renamed from: com.gen.mh.webapps.server.worker.GameWorker */
/* loaded from: classes2.dex */
public class GameWorker extends Worker {
    @Override // com.gen.p059mh.webapps.server.worker.Worker, com.gen.p059mh.webapps.server.runtime.V8Lifecycle
    public void afterExecute(File file) {
    }

    @Override // com.gen.p059mh.webapps.server.worker.Worker, com.gen.p059mh.webapps.server.runtime.V8Lifecycle
    public void onReady(C1257V8 c1257v8) {
    }

    public GameWorker(IWebFragmentController iWebFragmentController) {
        super(iWebFragmentController);
    }

    public void start(String str) {
        Logger.m4109w(str);
        try {
            URL url = new URL(str);
            final File file = new File((this.webViewFragment.getWorkPath() + url.getPath()).replace(this.webViewFragment.getWorkHost(), ""));
            if (!file.exists()) {
                return;
            }
            if (this.handler != null) {
                this.handler.post(new Runnable() { // from class: com.gen.mh.webapps.server.worker.GameWorker.1
                    @Override // java.lang.Runnable
                    public void run() {
                        GameWorker.this.start(file);
                    }
                });
            }
            Logger.m4112i("start worker", str);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override // com.gen.p059mh.webapps.server.worker.Worker, com.gen.p059mh.webapps.server.runtime.V8BaseRuntime, com.gen.p059mh.webapps.server.runtime.V8Lifecycle
    public void onInit() {
        super.onInit();
        this.runtime.registerJavaMethod(new JavaCallback() { // from class: com.gen.mh.webapps.server.worker.GameWorker.2
            @Override // com.eclipsesource.p056v8.JavaCallback
            public Object invoke(V8Object v8Object, V8Array v8Array) {
                return null;
            }
        }, "cancelAnimationFrame");
    }

    @Override // com.gen.p059mh.webapps.server.worker.Worker, com.gen.p059mh.webapps.server.runtime.V8Lifecycle
    public void beforeExecute(File file) {
        super.beforeExecute(file);
        this.runtime.executeScript("delete window");
        this.runtime.executeScript("var global = self;GameGlobal = self;");
        Logger.m4115e("before do something ready ");
    }
}
