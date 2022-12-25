package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.download.general;

import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.p076mh.webappStart.android_plugin_impl.beans.DownloadFileParamsBean;
import com.p076mh.webappStart.android_plugin_impl.beans.DownloadTaskParamsBean;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.download.DownloadTask;
import com.p076mh.webappStart.util.ReflectManger;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@Deprecated
/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.download.general.DownloadManager */
/* loaded from: classes3.dex */
public class DownloadManager {
    private static final DownloadManager ourInstance = new DownloadManager();
    private Map<String, DownloadTask> map = new HashMap();

    public static DownloadManager getInstance() {
        return ourInstance;
    }

    private DownloadManager() {
    }

    public void release() {
        this.map.clear();
    }

    public void download(DownloadFileParamsBean downloadFileParamsBean, IWebFragmentController iWebFragmentController, Plugin.PluginCallback pluginCallback, Plugin.Executor executor) {
        DownloadTask downloadTask = new DownloadTask(downloadFileParamsBean, iWebFragmentController, pluginCallback, executor);
        this.map.put(downloadFileParamsBean.getDownloadID(), downloadTask);
        downloadTask.run();
    }

    public void downloadTaskControl(DownloadTaskParamsBean downloadTaskParamsBean) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ReflectManger.invokeMethod(DownloadManager.class, this, downloadTaskParamsBean.getCall(), new Class[]{DownloadTaskParamsBean.class}, new Object[]{downloadTaskParamsBean});
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
