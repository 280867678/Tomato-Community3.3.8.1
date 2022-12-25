package com.gen.p059mh.webapp_extensions.unity;

import com.gen.p059mh.webapp_extensions.listener.DownRemove;
import com.gen.p059mh.webapps.unity.Unity;
import com.gen.p059mh.webapps.utils.Logger;
import com.hlw.movie.download.M3U8DloaderTask;
import com.hlw.movie.download.M3U8ParallelDloader;
import com.hlw.movie.download.api.M3U8DloadListener;
import com.liulishuo.okdownload.SpeedCalculator;
import java.util.ArrayList;

/* renamed from: com.gen.mh.webapp_extensions.unity.DownloadItem */
/* loaded from: classes2.dex */
public class DownloadItem extends Unity {
    private DownloadData content;
    private DownRemove downRemove;
    private M3U8DloaderTask m3U8DloaderTask;
    private SpeedCalculator speedCalculator;
    private String url;
    private Unity.Method pause = new Unity.Method() { // from class: com.gen.mh.webapp_extensions.unity.DownloadItem.1
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            if (DownloadItem.this.m3U8DloaderTask != null) {
                DownloadItem.this.m3U8DloaderTask.pause();
            }
            methodCallback.run("success");
        }
    };
    private Unity.Method remove = new Unity.Method() { // from class: com.gen.mh.webapp_extensions.unity.DownloadItem.2
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            if (DownloadItem.this.m3U8DloaderTask != null) {
                M3U8ParallelDloader.with().deleteTaskById(DownloadItem.this.m3U8DloaderTask.getId());
            }
            if (DownloadItem.this.downRemove != null) {
                DownloadItem.this.downRemove.downItemRemove(DownloadItem.this);
            }
            methodCallback.run("success");
        }
    };
    private Unity.Method setUrl = new Unity.Method() { // from class: com.gen.mh.webapp_extensions.unity.DownloadItem.3
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            DownloadItem.this.setUrl((String) ((ArrayList) objArr[0]).get(0));
            methodCallback.run("success");
        }
    };
    private Unity.Method getProgress = new Unity.Method() { // from class: com.gen.mh.webapp_extensions.unity.DownloadItem.4
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            methodCallback.run(Float.valueOf(DownloadItem.this.m3U8DloaderTask.getmDloadTaskInfo().getDownPercent() / 100.0f));
        }
    };
    private Unity.Method getState = new Unity.Method() { // from class: com.gen.mh.webapp_extensions.unity.DownloadItem.5
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            DownloadItem downloadItem = DownloadItem.this;
            methodCallback.run(Integer.valueOf(downloadItem.checkState(downloadItem.m3U8DloaderTask.getmDloadTaskInfo().getDownState())));
        }
    };
    private Unity.Method resume = new Unity.Method() { // from class: com.gen.mh.webapp_extensions.unity.DownloadItem.6
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            DownloadItem.this.start();
            methodCallback.run("success");
        }
    };
    private Unity.Method getContent = new Unity.Method() { // from class: com.gen.mh.webapp_extensions.unity.DownloadItem.7
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4112i("DDDDDD", "getContent  " + objArr[0].toString());
            methodCallback.run(DownloadItem.this.content.object2Map());
        }
    };
    private Unity.Method getUrl = new Unity.Method() { // from class: com.gen.mh.webapp_extensions.unity.DownloadItem.8
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4112i("DDDDDD", "getUrl  " + objArr[0].toString());
            methodCallback.run(DownloadItem.this.url);
        }
    };
    M3U8DloadListener listener = new M3U8DloadListener(this) { // from class: com.gen.mh.webapp_extensions.unity.DownloadItem.9
    };

    public int checkState(int i) {
        switch (i) {
            case 1:
            case 8:
            case 12:
            default:
                return 2;
            case 2:
                return 1;
            case 3:
                return 3;
            case 4:
                return 0;
            case 5:
            case 6:
            case 9:
            case 10:
            case 11:
            case 14:
                return 5;
            case 7:
                return 4;
            case 13:
                return 6;
        }
    }

    public DownloadItem() throws NoClassDefFoundError {
        registerMethod("pause", this.pause);
        registerMethod("remove", this.remove);
        registerMethod("setUrl", this.setUrl);
        registerMethod("getProgress", this.getProgress);
        registerMethod("getState", this.getState);
        registerMethod("resume", this.resume);
        registerMethod("getContent", this.getContent);
        registerMethod("getUrl", this.getUrl);
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public void setContent(DownloadData downloadData) {
        this.content = downloadData;
    }

    public void setDownRemove(DownRemove downRemove) {
        this.downRemove = downRemove;
    }

    public String getUrl() {
        return this.url;
    }

    public M3U8DloaderTask getM3U8DloaderTask() {
        return this.m3U8DloaderTask;
    }

    public void start() {
        try {
            if (this.m3U8DloaderTask != null) {
                this.m3U8DloaderTask.restart();
                return;
            }
            this.m3U8DloaderTask = M3U8DloaderTask.toBuilder(this.url).setMovieInfo(this.content).setM3U8DloadListener(this.listener).builder();
            this.speedCalculator = new SpeedCalculator();
            int addTask = M3U8ParallelDloader.with().addTask(this.m3U8DloaderTask);
            if (addTask == 5) {
                this.m3U8DloaderTask = M3U8ParallelDloader.with().parallelQueueHelper().queryTaskById(this.m3U8DloaderTask.getId());
                Logger.m4113i("任务重复");
            }
            Logger.m4112i("Download", "addTaskState:" + addTask + " dloadTaskInfo:" + this.m3U8DloaderTask.getmDloadTaskInfo().toString());
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }
    }

    public void initWithInfo(M3U8DloaderTask m3U8DloaderTask) throws NoClassDefFoundError {
        this.m3U8DloaderTask = m3U8DloaderTask;
        this.m3U8DloaderTask.setM3U8DloadListener(this.listener);
        this.speedCalculator = new SpeedCalculator();
    }
}
