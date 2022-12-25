package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.download.thread_pool_support;

import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.p076mh.webappStart.android_plugin_impl.beans.DownloadFileParamsBean;
import com.p076mh.webappStart.android_plugin_impl.beans.DownloadTaskParamsBean;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.download.DownloadTask;
import com.p076mh.webappStart.util.ReflectManger;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.download.thread_pool_support.PoolDownloadManager */
/* loaded from: classes3.dex */
public class PoolDownloadManager {
    private static final PoolDownloadManager ourInstance = new PoolDownloadManager();
    private Map<String, DownloadTask> map = new HashMap();
    private final DownloadTaskPool downloadTaskPool = new DownloadTaskPool();

    public static PoolDownloadManager getInstance() {
        return ourInstance;
    }

    private PoolDownloadManager() {
    }

    public void release() {
        this.map.clear();
    }

    public void download(DownloadFileParamsBean downloadFileParamsBean, IWebFragmentController iWebFragmentController, Plugin.PluginCallback pluginCallback, Plugin.Executor executor) throws Exception {
        DownloadTask downloadTask = new DownloadTask(downloadFileParamsBean, iWebFragmentController, pluginCallback, executor);
        this.map.put(downloadFileParamsBean.getDownloadID(), downloadTask);
        this.downloadTaskPool.execute(downloadTask);
    }

    public void downloadTaskControl(DownloadTaskParamsBean downloadTaskParamsBean) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ReflectManger.invokeMethod(PoolDownloadManager.class, this, downloadTaskParamsBean.getCall(), new Class[]{DownloadTaskParamsBean.class}, new Object[]{downloadTaskParamsBean});
    }

    public void abort(DownloadTaskParamsBean downloadTaskParamsBean) {
        DownloadTask downloadTask = this.map.get(downloadTaskParamsBean.getDownloadID());
        if (downloadTask != null) {
            downloadTask.abort();
        }
    }

    public void onHeadersReceived(DownloadTaskParamsBean downloadTaskParamsBean) {
        DownloadTask downloadTask = this.map.get(downloadTaskParamsBean.getDownloadID());
        if (downloadTask != null) {
            downloadTask.onHeadersReceived();
        }
    }

    public void offHeadersReceived(DownloadTaskParamsBean downloadTaskParamsBean) {
        DownloadTask downloadTask = this.map.get(downloadTaskParamsBean.getDownloadID());
        if (downloadTask != null) {
            downloadTask.offHeadersReceived();
        }
    }

    public void onProgressUpdate(DownloadTaskParamsBean downloadTaskParamsBean) {
        DownloadTask downloadTask = this.map.get(downloadTaskParamsBean.getDownloadID());
        if (downloadTask != null) {
            downloadTask.onProgressUpdate();
        }
    }

    public void offProgressUpdate(DownloadTaskParamsBean downloadTaskParamsBean) {
        DownloadTask downloadTask = this.map.get(downloadTaskParamsBean.getDownloadID());
        if (downloadTask != null) {
            downloadTask.offProgressUpdate();
        }
    }
}
