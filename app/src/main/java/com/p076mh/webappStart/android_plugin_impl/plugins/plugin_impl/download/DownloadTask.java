package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.download;

import android.support.p002v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.gen.p059mh.webapps.utils.Logger;
import com.p076mh.webappStart.android_plugin_impl.beans.DownloadFileParamsBean;
import com.p076mh.webappStart.android_plugin_impl.callback.JsCallBackKeys;
import com.p076mh.webappStart.util.TempUtil;
import com.p076mh.webappStart.util.download.OkHttpDownloadUtil;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import okhttp3.Headers;
import okhttp3.Response;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.download.DownloadTask */
/* loaded from: classes3.dex */
public class DownloadTask implements Runnable {
    private static final String TAG = "DownloadTask";
    private DownloadFileParamsBean downloadFileParamsBean;
    private Plugin.Executor executor;
    private Plugin.PluginCallback pluginCallback;
    private IWebFragmentController webViewFragment;
    private boolean onHeadersReceived = false;
    private boolean onProgressUpdate = false;
    private Map onProgressUpdateMap = new HashMap();
    private Map downloadResultMap = new HashMap();
    private Map onHeadersReceivedMap = new HashMap();
    private final Map<String, String> headerMap = new HashMap();

    public DownloadTask(DownloadFileParamsBean downloadFileParamsBean, IWebFragmentController iWebFragmentController, Plugin.PluginCallback pluginCallback, Plugin.Executor executor) {
        this.downloadFileParamsBean = downloadFileParamsBean;
        this.webViewFragment = iWebFragmentController;
        this.pluginCallback = pluginCallback;
        this.executor = executor;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (DownloadTaskObserver.getInstance().findSameUrlTask(this.downloadFileParamsBean.getUrl()) != null) {
            callBackDownloadFailure("已有相同的URL下载任务");
            return;
        }
        DownloadTaskObserver.getInstance().joinTask(this);
        String tempFilePathFromSimpleFileName = TempUtil.getTempFilePathFromSimpleFileName(new File(this.downloadFileParamsBean.getUrl()).getName());
        if (!TextUtils.isEmpty(this.downloadFileParamsBean.getFilePath())) {
            tempFilePathFromSimpleFileName = this.webViewFragment.realPath(this.downloadFileParamsBean.getFilePath());
        }
        File file = new File(tempFilePathFromSimpleFileName);
        OkHttpDownloadUtil.getInstance().downloadFile(this.downloadFileParamsBean.getDownloadID(), this.downloadFileParamsBean.getUrl(), this.downloadFileParamsBean.getHeader(), file.getParent(), file.getName(), new OkHttpDownloadUtil.DownloadListener() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.download.DownloadTask.1
            @Override // com.p076mh.webappStart.util.download.OkHttpDownloadUtil.DownloadListener
            public void onResponse(Response response) {
                if (DownloadTask.this.onHeadersReceived) {
                    DownloadTask.this.onHeadersReceivedMap.clear();
                    Headers headers = response.headers();
                    int size = headers.size();
                    DownloadTask.this.headerMap.clear();
                    for (int i = 0; i < size; i++) {
                        String name = headers.name(i);
                        String str = headers.get(name);
                        Log.e(DownloadTask.TAG, "onResponse: headerName = " + name + ",headerValue = " + str);
                        DownloadTask.this.headerMap.put(name, str);
                    }
                    DownloadTask.this.onHeadersReceivedMap.put("header", DownloadTask.this.headerMap);
                    DownloadTask.this.onHeadersReceivedMap.put("downloadID", DownloadTask.this.downloadFileParamsBean.getDownloadID());
                    Plugin.Executor executor = DownloadTask.this.executor;
                    executor.executeEvent(DownloadTask.this.downloadFileParamsBean.getDownloadID() + JsCallBackKeys.ON_HEADERS_RECEIVED, DownloadTask.this.onHeadersReceivedMap, null);
                }
            }

            @Override // com.p076mh.webappStart.util.download.OkHttpDownloadUtil.DownloadListener
            public void onProgress(long j, long j2) {
                StringBuilder sb = new StringBuilder();
                sb.append("onProgress: ,progress = ");
                float f = (((float) j) * 100.0f) / ((float) j2);
                sb.append(f);
                sb.append(",alreadyDownloadedSize = ");
                sb.append(j);
                sb.append(",fileTotalSize = ");
                sb.append(j2);
                sb.append(",downloadUrl = ");
                sb.append(DownloadTask.this.downloadFileParamsBean.getUrl());
                sb.append(",downloadId = ");
                sb.append(DownloadTask.this.downloadFileParamsBean.getDownloadID());
                Logger.m4112i(DownloadTask.TAG, sb.toString());
                if (DownloadTask.this.onProgressUpdate) {
                    DownloadTask.this.onProgressUpdateMap.clear();
                    DownloadTask.this.onProgressUpdateMap.put(NotificationCompat.CATEGORY_PROGRESS, Integer.valueOf(Math.round(f)));
                    DownloadTask.this.onProgressUpdateMap.put("totalBytesWritten", Long.valueOf(j));
                    DownloadTask.this.onProgressUpdateMap.put("totalBytesExpectedToWrite", Long.valueOf(j2));
                    DownloadTask.this.onProgressUpdateMap.put("downloadID", DownloadTask.this.downloadFileParamsBean.getDownloadID());
                    Plugin.Executor executor = DownloadTask.this.executor;
                    executor.executeEvent(DownloadTask.this.downloadFileParamsBean.getDownloadID() + JsCallBackKeys.ON_PROGRESSUPDATE, DownloadTask.this.onProgressUpdateMap, null);
                }
            }

            @Override // com.p076mh.webappStart.util.download.OkHttpDownloadUtil.DownloadListener
            public void onSuccess(File file2) {
                DownloadTaskObserver.getInstance().removeTask(DownloadTask.this);
                Logger.m4112i(DownloadTask.TAG, "onSuccess: " + file2.getAbsolutePath());
                DownloadTask.this.downloadResultMap.clear();
                DownloadTask.this.downloadResultMap.put("tempFilePath", TempUtil.getWxTempPathFromLocalPath(DownloadTask.this.webViewFragment, file2.getAbsolutePath()));
                DownloadTask.this.downloadResultMap.put("filePath", DownloadTask.this.downloadFileParamsBean.getFilePath());
                DownloadTask.this.downloadResultMap.put("statusCode", 200);
                DownloadTask.this.downloadResultMap.put("success", true);
                DownloadTask.this.downloadResultMap.put("complete", true);
                DownloadTask.this.downloadResultMap.put("downloadID", DownloadTask.this.downloadFileParamsBean.getDownloadID());
                DownloadTask.this.pluginCallback.response(DownloadTask.this.downloadResultMap);
            }

            @Override // com.p076mh.webappStart.util.download.OkHttpDownloadUtil.DownloadListener
            public void onFailure(Exception exc) {
                Logger.m4112i(DownloadTask.TAG, "onFailure: " + exc.getMessage());
                DownloadTask.this.callBackDownloadFailure(exc.getMessage());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callBackDownloadFailure(String str) {
        Log.e(TAG, "callBackDownloadFailure: " + str);
        DownloadTaskObserver.getInstance().removeTask(this);
        this.downloadResultMap.clear();
        this.downloadResultMap.put("success", false);
        this.downloadResultMap.put("errMsg", str);
        this.downloadResultMap.put("complete", true);
        this.downloadResultMap.put("downloadID", this.downloadFileParamsBean.getDownloadID());
        this.pluginCallback.response(this.downloadResultMap);
    }

    public void abort() {
        DownloadTaskObserver.getInstance().removeTask(this);
        Logger.m4112i(TAG, "abort: " + this.downloadFileParamsBean.toString());
        try {
            try {
                OkHttpDownloadUtil.getInstance().abort(this.downloadFileParamsBean.getDownloadID());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            callBackDownloadFailure("downloadFile:fail abort");
        }
    }

    public void onHeadersReceived() {
        Logger.m4112i(TAG, "onHeadersReceived: " + this.downloadFileParamsBean.toString());
        this.onHeadersReceived = true;
    }

    public void offHeadersReceived() {
        Logger.m4112i(TAG, "offHeadersReceived: " + this.downloadFileParamsBean.toString());
        this.onHeadersReceived = false;
    }

    public void onProgressUpdate() {
        Logger.m4112i(TAG, "onProgressUpdate: " + this.downloadFileParamsBean.toString());
        this.onProgressUpdate = true;
    }

    public void offProgressUpdate() {
        Logger.m4112i(TAG, "onProgressUpdate: " + this.downloadFileParamsBean.toString());
        this.onProgressUpdate = false;
    }

    public DownloadFileParamsBean getDownloadFileParamsBean() {
        return this.downloadFileParamsBean;
    }
}
