package com.amazonaws.mobileconnectors.p053s3.transfermanager;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.mobileconnectors.p053s3.transfermanager.internal.TransferManagerUtils;
import com.amazonaws.services.p054s3.AmazonS3;
import com.amazonaws.services.p054s3.AmazonS3Client;
import com.amazonaws.util.VersionInfoUtils;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

@Deprecated
/* renamed from: com.amazonaws.mobileconnectors.s3.transfermanager.TransferManager */
/* loaded from: classes2.dex */
public class TransferManager {
    private static final ThreadFactory daemonThreadFactory = new ThreadFactory() { // from class: com.amazonaws.mobileconnectors.s3.transfermanager.TransferManager.3
        final AtomicInteger threadCount = new AtomicInteger(0);

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            int incrementAndGet = this.threadCount.incrementAndGet();
            Thread thread = new Thread(runnable);
            thread.setDaemon(true);
            thread.setName("S3TransferManagerTimedThread-" + incrementAndGet);
            return thread;
        }
    };

    /* renamed from: s3 */
    private final AmazonS3 f1172s3;
    private final ExecutorService threadPool;
    private final ScheduledExecutorService timedThreadPool;

    static {
        LogFactory.getLog(TransferManager.class);
        String str = TransferManager.class.getName() + "/" + VersionInfoUtils.getVersion();
        String str2 = TransferManager.class.getName() + "_multipart/" + VersionInfoUtils.getVersion();
    }

    public TransferManager() {
        this(new AmazonS3Client(new DefaultAWSCredentialsProviderChain()));
    }

    public TransferManager(AmazonS3 amazonS3) {
        this(amazonS3, TransferManagerUtils.createDefaultExecutorService());
    }

    public TransferManager(AmazonS3 amazonS3, ExecutorService executorService) {
        this.timedThreadPool = new ScheduledThreadPoolExecutor(1, daemonThreadFactory);
        this.f1172s3 = amazonS3;
        this.threadPool = executorService;
        new TransferManagerConfiguration();
    }

    public void shutdownNow(boolean z) {
        this.threadPool.shutdownNow();
        this.timedThreadPool.shutdownNow();
        if (z) {
            AmazonS3 amazonS3 = this.f1172s3;
            if (!(amazonS3 instanceof AmazonS3Client)) {
                return;
            }
            ((AmazonS3Client) amazonS3).shutdown();
        }
    }

    private void shutdown() {
        this.threadPool.shutdown();
        this.timedThreadPool.shutdown();
    }

    protected void finalize() throws Throwable {
        shutdown();
    }
}
