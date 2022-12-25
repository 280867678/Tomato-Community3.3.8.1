package com.p076mh.webappStart.util.download;

import com.gen.p059mh.webapps.utils.Logger;
import com.p076mh.webappStart.util.MainHandler;
import com.p076mh.webappStart.util.NetWorkUtil;
import com.p076mh.webappStart.util.bean.NetworkType;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/* renamed from: com.mh.webappStart.util.download.OkHttpDownloadUtil */
/* loaded from: classes3.dex */
public class OkHttpDownloadUtil {
    private static final OkHttpDownloadUtil ourInstance = new OkHttpDownloadUtil();
    private final OkHttpClient okHttpClient;
    private long maxDownloadFileSize = 52428800;
    private int progressUpdateStep = 20;
    private Map<String, DownloadItem> downloadItemContainer = new HashMap();

    /* renamed from: com.mh.webappStart.util.download.OkHttpDownloadUtil$DownloadListener */
    /* loaded from: classes3.dex */
    public interface DownloadListener {
        void onFailure(Exception exc);

        void onProgress(long j, long j2);

        void onResponse(Response response);

        void onSuccess(File file);
    }

    public static OkHttpDownloadUtil getInstance() {
        return ourInstance;
    }

    private OkHttpDownloadUtil() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(60L, TimeUnit.SECONDS);
        builder.readTimeout(600L, TimeUnit.SECONDS);
        builder.writeTimeout(60L, TimeUnit.SECONDS);
        this.okHttpClient = builder.build();
    }

    public void downloadFile(String str, String str2, Map<String, String> map, String str3, String str4, DownloadListener downloadListener) {
        if (NetworkType.NETWORK_NO == NetWorkUtil.getCurrentNetworkType()) {
            if (downloadListener == null) {
                return;
            }
            downloadListener.onFailure(new Exception("无网络"));
            return;
        }
        Request.Builder builder = new Request.Builder();
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        builder.url(str2);
        DownloadItem downloadItem = new DownloadItem(str, str3, str4, this.okHttpClient.newCall(builder.build()), downloadListener);
        this.downloadItemContainer.put(str, downloadItem);
        downloadItem.run();
    }

    public void abort(String str) {
        DownloadItem downloadItem = this.downloadItemContainer.get(str);
        if (downloadItem != null) {
            downloadItem.abort();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.mh.webappStart.util.download.OkHttpDownloadUtil$DownloadItem */
    /* loaded from: classes3.dex */
    public class DownloadItem {
        Call call;
        String downloadId;
        DownloadListener downloadListener;
        String fileName;
        boolean isAbort;
        String parentPath;

        public DownloadItem(String str, String str2, String str3, Call call, DownloadListener downloadListener) {
            this.isAbort = false;
            this.downloadId = str;
            this.parentPath = str2;
            this.fileName = str3;
            this.call = call;
            this.downloadListener = downloadListener;
            this.isAbort = false;
        }

        void run() {
            this.call.enqueue(new Callback() { // from class: com.mh.webappStart.util.download.OkHttpDownloadUtil.DownloadItem.1
                @Override // okhttp3.Callback
                public void onFailure(Call call, IOException iOException) {
                    DownloadListener downloadListener = DownloadItem.this.downloadListener;
                    if (downloadListener != null) {
                        downloadListener.onFailure(iOException);
                    }
                }

                @Override // okhttp3.Callback
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        DownloadListener downloadListener = DownloadItem.this.downloadListener;
                        if (downloadListener != null) {
                            downloadListener.onResponse(response);
                        }
                        DownloadItem.this.writeFile(response);
                        return;
                    }
                    DownloadListener downloadListener2 = DownloadItem.this.downloadListener;
                    if (downloadListener2 == null) {
                        return;
                    }
                    downloadListener2.onFailure(new Exception("请求失败,code = " + response.code() + ",errorMsg = " + response.message()));
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void writeFile(Response response) throws IOException {
            Throwable th;
            FileOutputStream fileOutputStream;
            final long contentLength = response.body().contentLength();
            response.header("Content-Type");
            long j = 0;
            if (OkHttpDownloadUtil.this.maxDownloadFileSize > 0 && OkHttpDownloadUtil.this.maxDownloadFileSize < contentLength) {
                DownloadListener downloadListener = this.downloadListener;
                if (downloadListener == null) {
                    return;
                }
                downloadListener.onFailure(new Exception("文件过大，超过最大上限" + OkHttpDownloadUtil.this.maxDownloadFileSize));
                return;
            }
            InputStream byteStream = response.body().byteStream();
            File file = new File(this.parentPath, this.fileName);
            try {
                fileOutputStream = new FileOutputStream(file);
                try {
                    byte[] bArr = new byte[1024];
                    long j2 = 0;
                    while (true) {
                        int read = byteStream.read(bArr);
                        if (read == -1) {
                            break;
                        } else if (this.isAbort) {
                            Logger.m4112i("OkHttpDownloadUtil", "abort");
                            file.delete();
                            break;
                        } else {
                            fileOutputStream.write(bArr, 0, read);
                            final long j3 = j2 + read;
                            if (j3 % OkHttpDownloadUtil.this.progressUpdateStep == j) {
                                MainHandler.getInstance().post(new Runnable() { // from class: com.mh.webappStart.util.download.OkHttpDownloadUtil.DownloadItem.2
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        DownloadListener downloadListener2 = DownloadItem.this.downloadListener;
                                        if (downloadListener2 != null) {
                                            downloadListener2.onProgress(j3, contentLength);
                                        }
                                    }
                                });
                            }
                            j2 = j3;
                            j = 0;
                        }
                    }
                    if (this.downloadListener != null) {
                        this.downloadListener.onSuccess(file);
                    }
                    OkHttpDownloadUtil.this.downloadItemContainer.remove(this.downloadId);
                    if (byteStream != null) {
                        try {
                            byteStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                    fileOutputStream.close();
                } catch (Throwable th2) {
                    th = th2;
                    if (byteStream != null) {
                        try {
                            byteStream.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                            throw th;
                        }
                    }
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                fileOutputStream = null;
            }
        }

        public void abort() {
            this.isAbort = true;
            this.call.cancel();
        }
    }
}
