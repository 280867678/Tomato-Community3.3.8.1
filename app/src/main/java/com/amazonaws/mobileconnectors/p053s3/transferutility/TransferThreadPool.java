package com.amazonaws.mobileconnectors.p053s3.transferutility;

import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.amazonaws.mobileconnectors.s3.transferutility.TransferThreadPool */
/* loaded from: classes2.dex */
public class TransferThreadPool {
    private static final Log LOGGER = LogFactory.getLog(TransferService.class);
    private static ExecutorService executorMainTask;
    private static ExecutorService executorPartTask;

    static synchronized void init(int i) {
        synchronized (TransferThreadPool.class) {
            Log log = LOGGER;
            log.debug("Initializing the thread pool of size: " + i);
            int max = Math.max((int) Math.ceil(((double) i) / 2.0d), 1);
            if (executorMainTask == null) {
                executorMainTask = buildExecutor(max);
            }
            if (executorPartTask == null) {
                executorPartTask = buildExecutor(max);
            }
        }
    }

    public static <T> Future<T> submitTask(Callable<T> callable) {
        init(TransferUtilityOptions.getDefaultThreadPoolSize());
        if (callable instanceof UploadPartTask) {
            return executorPartTask.submit(callable);
        }
        return executorMainTask.submit(callable);
    }

    private static ExecutorService buildExecutor(int i) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(i, i, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue());
        threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        return threadPoolExecutor;
    }
}
