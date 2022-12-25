package com.amazonaws.mobileconnectors.p053s3.transfermanager.internal;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

/* renamed from: com.amazonaws.mobileconnectors.s3.transfermanager.internal.TransferManagerUtils */
/* loaded from: classes2.dex */
public class TransferManagerUtils {
    public static ThreadPoolExecutor createDefaultExecutorService() {
        return (ThreadPoolExecutor) Executors.newFixedThreadPool(10, new ThreadFactory() { // from class: com.amazonaws.mobileconnectors.s3.transfermanager.internal.TransferManagerUtils.1
            private int threadCount = 1;

            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable);
                StringBuilder sb = new StringBuilder();
                sb.append("s3-transfer-manager-worker-");
                int i = this.threadCount;
                this.threadCount = i + 1;
                sb.append(i);
                thread.setName(sb.toString());
                return thread;
            }
        });
    }
}
