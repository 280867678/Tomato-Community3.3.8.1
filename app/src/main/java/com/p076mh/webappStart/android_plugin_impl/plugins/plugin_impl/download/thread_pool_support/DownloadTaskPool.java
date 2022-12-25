package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.download.thread_pool_support;

import android.util.Log;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.download.DownloadTask;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.download.DownloadTaskObserver;
import com.p076mh.webappStart.util.queue.AbsQueueTaskHelper;
import com.p076mh.webappStart.util.thread.ThreadManager;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.download.thread_pool_support.DownloadTaskPool */
/* loaded from: classes3.dex */
public class DownloadTaskPool {
    private static final String TAG = "DownloadTaskPool";
    private int coreTaskSize;
    private final DownloadTaskQueueTaskHelper downloadTaskQueueTaskHelper;
    private int maximumTaskSize;

    public DownloadTaskPool() {
        this.coreTaskSize = 10;
        this.maximumTaskSize = 20;
        this.downloadTaskQueueTaskHelper = new DownloadTaskQueueTaskHelper();
        this.downloadTaskQueueTaskHelper.init();
        this.downloadTaskQueueTaskHelper.setOnTaskActivateListener(new AbsQueueTaskHelper.OnTaskActivateListener<DownloadTask>() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.download.thread_pool_support.DownloadTaskPool.1
            @Override // com.p076mh.webappStart.util.queue.AbsQueueTaskHelper.OnTaskActivateListener
            public void onTaskActivate(DownloadTask downloadTask) {
                ThreadManager.getInstance().executeTaskWithoutException(downloadTask);
            }
        });
        DownloadTaskObserver.getInstance().setOnDownloadTaskChangeListener(new DownloadTaskObserver.OnDownloadTaskChangeListener() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.download.thread_pool_support.DownloadTaskPool.2
            @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.download.DownloadTaskObserver.OnDownloadTaskChangeListener
            public void onDownloadTaskChanged(int i) {
                if (i < DownloadTaskPool.this.coreTaskSize) {
                    Log.e(DownloadTaskPool.TAG, "下载完毕:，尝试执行队列任务...");
                    DownloadTaskPool.this.downloadTaskQueueTaskHelper.next();
                }
            }
        });
    }

    public DownloadTaskPool(int i, int i2) {
        this();
        this.coreTaskSize = i;
        this.maximumTaskSize = i2;
    }

    public void execute(DownloadTask downloadTask) throws Exception {
        int runningTaskSize = DownloadTaskObserver.getInstance().getRunningTaskSize();
        int i = this.coreTaskSize;
        if (runningTaskSize < i) {
            ThreadManager.getInstance().executeTask(downloadTask);
        } else if (i + this.downloadTaskQueueTaskHelper.getQueueSize() < this.maximumTaskSize) {
            Log.e(TAG, "超过核心线程数，加入缓冲队列，等待执行...");
            this.downloadTaskQueueTaskHelper.join(downloadTask);
        } else {
            Log.e(TAG, "下载任务已经达到最大上限");
            throw new Exception("下载任务已经达到最大上限");
        }
    }
}
