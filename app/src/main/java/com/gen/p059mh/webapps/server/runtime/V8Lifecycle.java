package com.gen.p059mh.webapps.server.runtime;

import com.eclipsesource.p056v8.C1257V8;
import java.io.File;

/* renamed from: com.gen.mh.webapps.server.runtime.V8Lifecycle */
/* loaded from: classes2.dex */
public interface V8Lifecycle {
    void afterExecute(File file);

    void beforeExecute(File file);

    void destroyWorker();

    void onInit();

    void onReady(C1257V8 c1257v8);

    void start(File file);
}
