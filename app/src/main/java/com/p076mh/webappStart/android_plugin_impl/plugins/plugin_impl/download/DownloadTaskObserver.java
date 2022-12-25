package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.download;

import java.util.ArrayList;
import java.util.List;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.download.DownloadTaskObserver */
/* loaded from: classes3.dex */
public class DownloadTaskObserver {
    private static final DownloadTaskObserver ourInstance = new DownloadTaskObserver();
    private List<DownloadTask> curRunningTaskList = new ArrayList();
    private OnDownloadTaskChangeListener onDownloadTaskChangeListener;

    /* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.download.DownloadTaskObserver$OnDownloadTaskChangeListener */
    /* loaded from: classes3.dex */
    public interface OnDownloadTaskChangeListener {
        void onDownloadTaskChanged(int i);
    }

    public static DownloadTaskObserver getInstance() {
        return ourInstance;
    }

    private DownloadTaskObserver() {
    }

    public void joinTask(DownloadTask downloadTask) {
        this.curRunningTaskList.add(downloadTask);
    }

    public void removeTask(DownloadTask downloadTask) {
        this.curRunningTaskList.remove(downloadTask);
        OnDownloadTaskChangeListener onDownloadTaskChangeListener = this.onDownloadTaskChangeListener;
        if (onDownloadTaskChangeListener != null) {
            onDownloadTaskChangeListener.onDownloadTaskChanged(this.curRunningTaskList.size());
        }
    }

    public DownloadTask findSameUrlTask(String str) {
        for (DownloadTask downloadTask : this.curRunningTaskList) {
            if (downloadTask.getDownloadFileParamsBean().getUrl().equals(str)) {
                return downloadTask;
            }
        }
        return null;
    }

    public void setOnDownloadTaskChangeListener(OnDownloadTaskChangeListener onDownloadTaskChangeListener) {
        this.onDownloadTaskChangeListener = onDownloadTaskChangeListener;
    }

    public int getRunningTaskSize() {
        return this.curRunningTaskList.size();
    }
}
