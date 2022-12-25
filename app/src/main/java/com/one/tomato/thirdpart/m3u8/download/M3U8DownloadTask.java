package com.one.tomato.thirdpart.m3u8.download;

import com.one.tomato.mvp.base.okhttp.download.DownLoadManager;
import com.one.tomato.mvp.base.okhttp.download.ProgressCallBack;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.thirdpart.m3u8.download.entity.M3U8;
import com.one.tomato.thirdpart.m3u8.download.entity.M3U8Ts;
import com.one.tomato.thirdpart.m3u8.download.listener.ITaskDownloadListener;
import com.one.tomato.thirdpart.m3u8.download.utils.MUtils;
import com.one.tomato.utils.LogUtil;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class M3U8DownloadTask {
    private volatile long cachedSize;
    private ITaskDownloadListener taskDownloadListener;
    private volatile int curTs = 0;
    private volatile int totalTs = 0;
    private volatile boolean isStartDownload = true;
    private boolean isRunning = false;
    private List<String> downUrlList = new ArrayList();

    public void download(boolean z, String str, String str2, int i, ITaskDownloadListener iTaskDownloadListener) {
        this.taskDownloadListener = iTaskDownloadListener;
        if (!this.isRunning) {
            String ttViewVideoView2 = DomainServer.getInstance().getTtViewVideoView2();
            getM3U8Info(z, ttViewVideoView2 + str);
        }
    }

    public void getM3U8Info(final boolean z, final String str) {
        try {
            Observable.create(new ObservableOnSubscribe<M3U8>(this) { // from class: com.one.tomato.thirdpart.m3u8.download.M3U8DownloadTask.2
                @Override // io.reactivex.ObservableOnSubscribe
                public void subscribe(ObservableEmitter<M3U8> observableEmitter) throws Exception {
                    M3U8 m3u8 = new M3U8();
                    m3u8.setPreDownload(z);
                    m3u8.setUrl(str);
                    MUtils.parseIndex(m3u8);
                    observableEmitter.onNext(m3u8);
                    observableEmitter.onComplete();
                }
            }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<M3U8>() { // from class: com.one.tomato.thirdpart.m3u8.download.M3U8DownloadTask.1
                @Override // io.reactivex.Observer
                public void onComplete() {
                }

                @Override // io.reactivex.Observer
                public void onSubscribe(Disposable disposable) {
                }

                @Override // io.reactivex.Observer
                public void onNext(M3U8 m3u8) {
                    M3U8DownloadTask.this.startDownload(m3u8);
                }

                @Override // io.reactivex.Observer
                public void onError(Throwable th) {
                    LogUtil.m3788d("y");
                    M3U8DownloadTask.this.taskDownloadListener.onError(null, th);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.m3788d("y");
            this.taskDownloadListener.onError(null, e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startDownload(M3U8 m3u8) {
        if (!m3u8.isPreDownload()) {
            stop("新任务下载前，停止旧的任务");
        }
        this.totalTs = m3u8.getTsList().size();
        if (m3u8.isPreDownload()) {
            this.totalTs = 1;
        }
        this.curTs = 1;
        this.cachedSize = 0L;
        this.isRunning = true;
        this.isStartDownload = true;
        LogUtil.m3783i("Download", "单个任务正式开始下载，ts文件一共有" + this.totalTs + "个");
        if (checkDownloadTs(m3u8)) {
            return;
        }
        onDownloadTsIng(m3u8);
    }

    private boolean checkDownloadTs(M3U8 m3u8) {
        List<M3U8Ts> tsList = m3u8.getTsList();
        for (int i = 0; i < tsList.size(); i++) {
            M3U8Ts m3U8Ts = tsList.get(i);
            File file = new File(m3u8.getDirFilePath(), m3U8Ts.getUrl());
            if (m3u8.isPreDownload()) {
                File file2 = new File(m3u8.getDirFilePath(), m3U8Ts.getUrl() + ".download");
                if (!file.exists() && !file2.exists()) {
                    return false;
                }
                LogUtil.m3785e("Download", m3U8Ts.getUrl() + "预加载文件已存在");
                this.isRunning = false;
                ITaskDownloadListener iTaskDownloadListener = this.taskDownloadListener;
                if (iTaskDownloadListener != null) {
                    iTaskDownloadListener.onSuccess(m3u8);
                }
                return true;
            }
            if (file.exists()) {
                LogUtil.m3785e("Download", m3U8Ts.getUrl() + "文件已存在");
                long length = file.length();
                m3U8Ts.setFileSize(length);
                this.cachedSize = this.cachedSize + length;
                ITaskDownloadListener iTaskDownloadListener2 = this.taskDownloadListener;
                if (iTaskDownloadListener2 != null) {
                    iTaskDownloadListener2.onProgress(m3u8, this.cachedSize, this.totalTs, this.curTs);
                }
                this.curTs++;
            }
        }
        if (this.curTs == this.totalTs + 1) {
            this.isRunning = false;
            ITaskDownloadListener iTaskDownloadListener3 = this.taskDownloadListener;
            if (iTaskDownloadListener3 != null) {
                iTaskDownloadListener3.onSuccess(m3u8);
            }
            return true;
        }
        return false;
    }

    private void onDownloadTsIng(M3U8 m3u8) {
        List<M3U8Ts> tsList = m3u8.getTsList();
        if (this.curTs < this.totalTs + 1) {
            onDownloadTsIng(tsList.get(this.curTs - 1), m3u8);
        }
    }

    private void onDownloadTsIng(final M3U8Ts m3U8Ts, final M3U8 m3u8) {
        String url = m3U8Ts.getUrl();
        String str = url + ".download";
        final File file = new File(m3u8.getDirFilePath(), url);
        final File file2 = new File(m3u8.getDirFilePath(), str);
        LogUtil.m3783i("Download", "单个ts信息如下：tsName = " + url + ",文件夹地址：" + m3u8.getDirFilePath());
        this.downUrlList.clear();
        final String str2 = m3u8.getBaseUrl() + File.separator + m3U8Ts.getUrl();
        this.downUrlList.add(str2);
        DownLoadManager.getInstance().loadWithCancelable(str2, new ProgressCallBack(m3u8.getDirFilePath(), str) { // from class: com.one.tomato.thirdpart.m3u8.download.M3U8DownloadTask.3
            @Override // com.one.tomato.mvp.base.okhttp.download.ProgressCallBack
            public void onStart() {
                super.onStart();
                if (M3U8DownloadTask.this.isStartDownload) {
                    M3U8DownloadTask.this.isStartDownload = false;
                    if (M3U8DownloadTask.this.taskDownloadListener == null) {
                        return;
                    }
                    M3U8DownloadTask.this.taskDownloadListener.onStartDownload(m3u8, M3U8DownloadTask.this.cachedSize, M3U8DownloadTask.this.totalTs, M3U8DownloadTask.this.curTs);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.download.ProgressCallBack
            public void progress(long j, long j2) {
                M3U8DownloadTask.this.cachedSize += j;
                if (M3U8DownloadTask.this.taskDownloadListener != null) {
                    M3U8DownloadTask.this.taskDownloadListener.onProgress(m3u8, M3U8DownloadTask.this.cachedSize, M3U8DownloadTask.this.totalTs, M3U8DownloadTask.this.curTs);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.download.ProgressCallBack
            public void onSuccess(Object obj) {
                file2.renameTo(file);
                M3U8DownloadTask.this.onDownloadTsSuccess(file, m3U8Ts, m3u8);
                if (M3U8DownloadTask.this.downUrlList.contains(str2)) {
                    M3U8DownloadTask.this.downUrlList.remove(str2);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.download.ProgressCallBack
            public void onError(Throwable th) {
                M3U8DownloadTask.this.stop("文件下载异常");
                if (M3U8DownloadTask.this.taskDownloadListener != null) {
                    M3U8DownloadTask.this.taskDownloadListener.onError(m3u8, th);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onDownloadTsSuccess(File file, M3U8Ts m3U8Ts, M3U8 m3u8) {
        m3U8Ts.setFileSize(file.length());
        if (this.curTs == this.totalTs) {
            this.isRunning = false;
            ITaskDownloadListener iTaskDownloadListener = this.taskDownloadListener;
            if (iTaskDownloadListener == null) {
                return;
            }
            iTaskDownloadListener.onSuccess(m3u8);
            return;
        }
        ITaskDownloadListener iTaskDownloadListener2 = this.taskDownloadListener;
        if (iTaskDownloadListener2 != null) {
            iTaskDownloadListener2.onProgress(m3u8, this.cachedSize, this.totalTs, this.curTs);
        }
        this.curTs++;
        onDownloadTsIng(m3u8);
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public void stop(String str) {
        if (!this.downUrlList.isEmpty()) {
            for (String str2 : this.downUrlList) {
                DownLoadManager.getInstance().cancelLoad(str2);
            }
            this.downUrlList.clear();
        }
        this.totalTs = 0;
        this.isRunning = false;
        this.isStartDownload = false;
        LogUtil.m3785e("Download", "stop：" + str);
    }
}
