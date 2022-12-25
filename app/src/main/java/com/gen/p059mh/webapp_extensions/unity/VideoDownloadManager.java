package com.gen.p059mh.webapp_extensions.unity;

import com.gen.p059mh.webapp_extensions.listener.DownRemove;
import com.gen.p059mh.webapps.unity.Unity;
import com.gen.p059mh.webapps.utils.Logger;
import com.google.gson.reflect.TypeToken;
import com.hlw.movie.download.M3U8DloaderTask;
import com.hlw.movie.download.M3U8ParallelDloader;
import com.hlw.movie.download.data.DloadTaskInfo;
import com.one.tomato.entity.C2516Ad;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* renamed from: com.gen.mh.webapp_extensions.unity.VideoDownloadManager */
/* loaded from: classes2.dex */
public class VideoDownloadManager extends Unity {
    static VideoDownloadManager instance;
    List<DownloadItem> downloadItems;
    boolean isFirst = true;
    Unity.Method maxDownloadingCount = new Unity.Method(this) { // from class: com.gen.mh.webapp_extensions.unity.VideoDownloadManager.3
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4115e(objArr[0].toString());
            List list = (List) objArr[0];
            if (list.size() > 0) {
                M3U8ParallelDloader.with().setMaxParallelRunningCount(((Number) list.get(0)).intValue());
                methodCallback.run("success");
                return;
            }
            methodCallback.run(null);
        }
    };
    Unity.Method list = new Unity.Method() { // from class: com.gen.mh.webapp_extensions.unity.VideoDownloadManager.4
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            methodCallback.run(new ArrayList(VideoDownloadManager.this.downloadItems));
        }
    };
    Unity.Method startAll = new Unity.Method(this) { // from class: com.gen.mh.webapp_extensions.unity.VideoDownloadManager.5
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            try {
                M3U8ParallelDloader.with().parallelQueueHelper().startAllTask();
            } catch (NoClassDefFoundError unused) {
            }
            methodCallback.run("success");
        }
    };
    Unity.Method download = new Unity.Method() { // from class: com.gen.mh.webapp_extensions.unity.VideoDownloadManager.6
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            String str = (String) ((ArrayList) objArr[0]).get(0);
            DownloadData createData = DownloadData.createData((Map) ((ArrayList) objArr[0]).get(1));
            for (DownloadItem downloadItem : VideoDownloadManager.this.downloadItems) {
                if (downloadItem.getUrl().equals(str)) {
                    methodCallback.run(downloadItem);
                    return;
                }
            }
            DownloadItem downloadItem2 = new DownloadItem();
            downloadItem2.setWebViewFragment(VideoDownloadManager.this.getWebViewFragment());
            downloadItem2.setExecutor(VideoDownloadManager.this.getExecutor());
            downloadItem2.setUrl(str);
            downloadItem2.setContent(createData);
            downloadItem2.setDownRemove(new DownRemove() { // from class: com.gen.mh.webapp_extensions.unity.VideoDownloadManager.6.1
                @Override // com.gen.p059mh.webapp_extensions.listener.DownRemove
                public void downItemRemove(DownloadItem downloadItem3) {
                    VideoDownloadManager.this.downloadItems.remove(downloadItem3);
                }
            });
            VideoDownloadManager.this.downloadItems.add(downloadItem2);
            methodCallback.run(downloadItem2);
        }
    };
    Unity.Method getVersion = new Unity.Method(this) { // from class: com.gen.mh.webapp_extensions.unity.VideoDownloadManager.7
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4112i("DDDDDD", "getVersion  " + objArr[0].toString());
            methodCallback.run("1.1.0");
        }
    };
    Unity.Method clearCaches = new Unity.Method(this) { // from class: com.gen.mh.webapp_extensions.unity.VideoDownloadManager.8
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4112i("DDDDDD", "clearCaches  " + objArr[0].toString());
            methodCallback.run("success");
        }
    };
    Unity.Method getReachable = new Unity.Method(this) { // from class: com.gen.mh.webapp_extensions.unity.VideoDownloadManager.9
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4112i("DDDDDD", "getReachable  " + objArr[0].toString());
            methodCallback.run("success");
        }
    };
    Unity.Method getFreeSize = new Unity.Method() { // from class: com.gen.mh.webapp_extensions.unity.VideoDownloadManager.10
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Logger.m4112i("DDDDDD", "getFreeSize  " + objArr[0].toString());
            ArrayList arrayList = new ArrayList();
            for (DownloadItem downloadItem : VideoDownloadManager.this.downloadItems) {
                arrayList.add(Long.valueOf(downloadItem.getM3U8DloaderTask().getmDloadTaskInfo().getDloadSize()));
            }
            methodCallback.run(arrayList);
        }
    };

    private VideoDownloadManager() {
        registerMethod(C2516Ad.TYPE_LIST, this.list);
        registerMethod("download", this.download);
        registerMethod("startAll", this.startAll);
        registerMethod("maxDownloadingCount", this.maxDownloadingCount);
        registerMethod("getVersion", this.getVersion);
        registerMethod("clearCaches", this.clearCaches);
        registerMethod("getReachable", this.getReachable);
        registerMethod("getFreeSize", this.getFreeSize);
    }

    public static Unity getInstance() throws NoClassDefFoundError {
        if (instance == null) {
            instance = new VideoDownloadManager();
        }
        return instance;
    }

    @Override // com.gen.p059mh.webapps.unity.Unity
    public void onInitialize(Object obj) {
        super.onInitialize(obj);
        if (this.isFirst) {
            initM3U8Data();
        }
        initDownList();
    }

    private void initM3U8Data() {
        this.isFirst = false;
        try {
            M3U8ParallelDloader with = M3U8ParallelDloader.with();
            with.setRootPath(getWebViewFragment().getContext().getFilesDir() + File.separator + "videoDownload/");
            M3U8ParallelDloader.with().parallelQueueHelper().scanLocalMovieTask(new TypeToken<DloadTaskInfo<DownloadData>>(this) { // from class: com.gen.mh.webapp_extensions.unity.VideoDownloadManager.1
            }.getType());
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }
    }

    private void initDownList() {
        this.downloadItems = new ArrayList();
        try {
            for (M3U8DloaderTask m3U8DloaderTask : M3U8ParallelDloader.with().parallelQueueHelper().queryAllSortTask()) {
                DownloadItem downloadItem = new DownloadItem();
                downloadItem.setWebViewFragment(getWebViewFragment());
                downloadItem.setExecutor(getExecutor());
                downloadItem.initWithInfo(m3U8DloaderTask);
                downloadItem.setUrl(m3U8DloaderTask.getmDloadTaskInfo().getMovieUrl());
                downloadItem.setContent((DownloadData) m3U8DloaderTask.getmDloadTaskInfo().getMovieDloadInfo().getMovieInfo());
                downloadItem.setDownRemove(new DownRemove() { // from class: com.gen.mh.webapp_extensions.unity.VideoDownloadManager.2
                    @Override // com.gen.p059mh.webapp_extensions.listener.DownRemove
                    public void downItemRemove(DownloadItem downloadItem2) {
                        VideoDownloadManager.this.downloadItems.remove(downloadItem2);
                    }
                });
                this.downloadItems.add(downloadItem);
            }
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }
    }

    @Override // com.gen.p059mh.webapps.unity.Unity
    public void onHide() {
        super.onHide();
    }

    @Override // com.gen.p059mh.webapps.unity.Unity
    public void onShow() {
        super.onShow();
    }

    @Override // com.gen.p059mh.webapps.unity.Unity
    public void unload() {
        super.unload();
        try {
            M3U8ParallelDloader.with().parallelQueueHelper().pauseAllTask();
            if (this.downloadItems == null) {
                return;
            }
            this.downloadItems.clear();
        } catch (NoClassDefFoundError unused) {
        }
    }
}
