package razerdp.blur.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class ThreadPoolManager {
    private static ExecutorService threadPool;

    static {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        threadPool = new ThreadPoolExecutor(availableProcessors, (availableProcessors * 2) + 1, 20L, TimeUnit.SECONDS, new SynchronousQueue());
    }

    public static void execute(Runnable runnable) {
        try {
            threadPool.execute(runnable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
