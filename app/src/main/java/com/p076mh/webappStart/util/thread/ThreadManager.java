package com.p076mh.webappStart.util.thread;

import android.support.annotation.Nullable;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: com.mh.webappStart.util.thread.ThreadManager */
/* loaded from: classes3.dex */
public class ThreadManager {
    private static volatile ThreadManager instance;
    private LinkedBlockingQueue<Runnable> threadQueue = new LinkedBlockingQueue<>();
    private SmartThreadFactory smartThreadFactory = new SmartThreadFactory();
    private SmartRejectHandler smartRejectHandler = new SmartRejectHandler();
    private ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(50, Integer.MAX_VALUE, 0, TimeUnit.MILLISECONDS, this.threadQueue, this.smartThreadFactory, this.smartRejectHandler);

    public static ThreadManager getInstance() {
        if (instance == null) {
            synchronized (ThreadManager.class) {
                if (instance == null) {
                    instance = new ThreadManager();
                }
            }
        }
        return instance;
    }

    private ThreadManager() {
    }

    public boolean executeTaskWithoutException(@Nullable Runnable runnable) {
        try {
            execute(runnable);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Future submitTaskWithoutException(@Nullable Callable callable) {
        try {
            return submit(callable);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void executeTask(@Nullable Runnable runnable) throws Exception {
        execute(runnable);
    }

    private void execute(Runnable runnable) throws Exception {
        this.poolExecutor.execute(runnable);
    }

    private Future submit(Callable callable) throws Exception {
        return this.poolExecutor.submit(callable);
    }

    /* renamed from: com.mh.webappStart.util.thread.ThreadManager$SmartThreadFactory */
    /* loaded from: classes3.dex */
    static class SmartThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private String namePrefix;
        private final AtomicInteger threadNumber = new AtomicInteger(1);

        SmartThreadFactory() {
            ThreadGroup threadGroup;
            SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                threadGroup = securityManager.getThreadGroup();
            } else {
                threadGroup = Thread.currentThread().getThreadGroup();
            }
            this.group = threadGroup;
            this.namePrefix = "pool-" + poolNumber.getAndIncrement() + "-thread-";
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            ThreadGroup threadGroup = this.group;
            Thread thread = new Thread(threadGroup, runnable, this.namePrefix + this.threadNumber.getAndIncrement());
            if (thread.isDaemon()) {
                thread.setDaemon(false);
            }
            if (thread.getPriority() != 5) {
                thread.setPriority(5);
            }
            return thread;
        }
    }

    /* renamed from: com.mh.webappStart.util.thread.ThreadManager$SmartRejectHandler */
    /* loaded from: classes3.dex */
    static class SmartRejectHandler implements RejectedExecutionHandler {
        SmartRejectHandler() {
        }

        @Override // java.util.concurrent.RejectedExecutionHandler
        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
            if (!threadPoolExecutor.isShutdown()) {
                threadPoolExecutor.execute(runnable);
                return;
            }
            throw new RejectedExecutionException("Task " + runnable.toString() + " rejected from " + threadPoolExecutor.toString() + "\n pool is shutdown!");
        }
    }
}
