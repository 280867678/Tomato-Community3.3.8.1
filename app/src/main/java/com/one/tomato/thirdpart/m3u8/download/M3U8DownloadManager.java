package com.one.tomato.thirdpart.m3u8.download;

import android.text.TextUtils;
import com.one.tomato.entity.p079db.VideoDownload;
import com.one.tomato.thirdpart.m3u8.download.entity.M3U8;
import com.one.tomato.thirdpart.m3u8.download.entity.M3U8Task;
import com.one.tomato.thirdpart.m3u8.download.listener.IM3U8DownloadListener;
import com.one.tomato.thirdpart.m3u8.download.listener.ITaskDownloadListener;
import com.one.tomato.thirdpart.m3u8.download.utils.MUtils;
import com.one.tomato.utils.FileUtil;
import com.one.tomato.utils.LogUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class M3U8DownloadManager {
    private M3U8Task currentM3U8Task;
    private long currentTime;
    private DownloadQueue downloadQueue;
    private ITaskDownloadListener iTaskDownloadListener;
    private IM3U8DownloadListener im3U8DownloadListener;
    private M3U8DownloadTask m3U8DownloadTask;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class SingletonHolder {
        static M3U8DownloadManager instance = new M3U8DownloadManager();
    }

    private M3U8DownloadManager() {
        this.iTaskDownloadListener = new ITaskDownloadListener() { // from class: com.one.tomato.thirdpart.m3u8.download.M3U8DownloadManager.3
            private long lastLength;

            @Override // com.one.tomato.thirdpart.m3u8.download.listener.ITaskDownloadListener
            public void onStartDownload(M3U8 m3u8, long j, int i, int i2) {
                if (M3U8DownloadManager.this.currentM3U8Task.isPreDownload()) {
                    return;
                }
                float f = (i2 * 1.0f) / i;
                LogUtil.m3783i("Download", "onStartDownload：cachedSize = " + j + ",downloadProgress = " + f + ",totalTs = " + i + ",curTs = " + i2);
                M3U8DownloadManager.this.currentM3U8Task.setProgress(f);
                M3U8DownloadManager.this.currentM3U8Task.setM3U8(m3u8);
                M3U8DownloadManager.this.currentM3U8Task.setState(1);
                M3U8DownloadManager m3U8DownloadManager = M3U8DownloadManager.this;
                m3U8DownloadManager.saveToDB(m3U8DownloadManager.currentM3U8Task);
                if (M3U8DownloadManager.this.im3U8DownloadListener == null) {
                    return;
                }
                M3U8DownloadManager.this.im3U8DownloadListener.onDownloadPrepare(M3U8DownloadManager.this.currentM3U8Task);
            }

            @Override // com.one.tomato.thirdpart.m3u8.download.listener.ITaskDownloadListener
            public void onProgress(M3U8 m3u8, long j, int i, int i2) {
                if (!M3U8DownloadManager.this.currentM3U8Task.isPreDownload() && M3U8DownloadManager.this.m3U8DownloadTask.isRunning() && j - this.lastLength > 0) {
                    if (!M3U8DownloadManager.this.delayTime(300L)) {
                        float f = (i2 * 1.0f) / i;
                        LogUtil.m3783i("Download", "onProgress：cachedSize = " + j + ",lastLength = " + this.lastLength + ",downloadProgress = " + f + ",totalTs = " + i + ",curTs = " + i2);
                        M3U8DownloadManager.this.currentM3U8Task.setProgress(f);
                        M3U8DownloadManager.this.currentM3U8Task.setSpeed(j - this.lastLength);
                        M3U8DownloadManager.this.currentM3U8Task.setM3U8(m3u8);
                        M3U8DownloadManager.this.currentM3U8Task.setState(2);
                        M3U8DownloadManager m3U8DownloadManager = M3U8DownloadManager.this;
                        m3U8DownloadManager.saveToDB(m3U8DownloadManager.currentM3U8Task);
                        if (M3U8DownloadManager.this.im3U8DownloadListener != null) {
                            M3U8DownloadManager.this.im3U8DownloadListener.onDownloadProgress(M3U8DownloadManager.this.currentM3U8Task);
                        }
                    }
                    this.lastLength = j;
                }
            }

            @Override // com.one.tomato.thirdpart.m3u8.download.listener.ITaskDownloadListener
            public void onSuccess(M3U8 m3u8) {
                LogUtil.m3783i("Download", "onSuccess");
                if (!M3U8DownloadManager.this.currentM3U8Task.isPreDownload()) {
                    M3U8DownloadManager.this.m3U8DownloadTask.stop("下载成功，停止任务");
                    M3U8DownloadManager.this.currentM3U8Task.setM3U8(m3u8);
                    M3U8DownloadManager.this.currentM3U8Task.setState(3);
                    M3U8DownloadManager m3U8DownloadManager = M3U8DownloadManager.this;
                    m3U8DownloadManager.saveToDB(m3U8DownloadManager.currentM3U8Task);
                    if (M3U8DownloadManager.this.im3U8DownloadListener != null) {
                        M3U8DownloadManager.this.im3U8DownloadListener.onDownloadSuccess(M3U8DownloadManager.this.currentM3U8Task);
                    }
                }
                M3U8DownloadManager.this.downloadNextTask();
            }

            @Override // com.one.tomato.thirdpart.m3u8.download.listener.ITaskDownloadListener
            public void onError(M3U8 m3u8, Throwable th) {
                LogUtil.m3783i("Download", "onError");
                if (!M3U8DownloadManager.this.currentM3U8Task.isPreDownload()) {
                    M3U8DownloadManager.this.currentM3U8Task.setM3U8(m3u8);
                    M3U8DownloadManager.this.currentM3U8Task.setState(4);
                    M3U8DownloadManager m3U8DownloadManager = M3U8DownloadManager.this;
                    m3U8DownloadManager.saveToDB(m3U8DownloadManager.currentM3U8Task);
                    if (M3U8DownloadManager.this.im3U8DownloadListener != null) {
                        M3U8DownloadManager.this.im3U8DownloadListener.onDownloadError(M3U8DownloadManager.this.currentM3U8Task, th);
                    }
                }
                M3U8DownloadManager.this.downloadNextTask();
            }
        };
        this.downloadQueue = new DownloadQueue();
        this.m3U8DownloadTask = new M3U8DownloadTask();
    }

    public static M3U8DownloadManager getInstance() {
        return SingletonHolder.instance;
    }

    public void setIM3U8DownloadListener(IM3U8DownloadListener iM3U8DownloadListener) {
        this.im3U8DownloadListener = iM3U8DownloadListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean delayTime(long j) {
        boolean z = System.currentTimeMillis() - this.currentTime <= j;
        this.currentTime = System.currentTimeMillis();
        return z;
    }

    private void pendingTask(M3U8Task m3U8Task) {
        m3U8Task.setState(-1);
        IM3U8DownloadListener iM3U8DownloadListener = this.im3U8DownloadListener;
        if (iM3U8DownloadListener != null) {
            iM3U8DownloadListener.onDownloadPending(m3U8Task);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void downloadNextTask() {
        LogUtil.m3783i("Download", "下载下一个任务");
        startDownloadTask(this.downloadQueue.poll());
    }

    public void downloadQueue(ArrayList<VideoDownload> arrayList) {
        this.m3U8DownloadTask.stop("未完成的队列开始前，停止任务");
        this.downloadQueue.clear();
        Iterator<VideoDownload> it2 = arrayList.iterator();
        while (it2.hasNext()) {
            VideoDownload next = it2.next();
            if (next.getState() != 3) {
                M3U8Task m3U8Task = new M3U8Task(next.getUrl());
                m3U8Task.setState(0);
                m3U8Task.setTitle(next.getTitle());
                m3U8Task.setPostId(next.getPostId());
                m3U8Task.setVideoView(next.getVideoView());
                this.downloadQueue.offer(m3U8Task);
            }
        }
        if (!this.downloadQueue.isEmpty()) {
            LogUtil.m3783i("Download", "开始下载未完成的队列");
            M3U8Task peek = this.downloadQueue.peek();
            download(peek.getUrl(), peek.getTitle(), peek.getPostId(), peek.getVideoView());
            return;
        }
        LogUtil.m3783i("Download", "当前没有未完成的队列");
    }

    public void preDownload(String str, String str2, String str3, int i) {
        if (TextUtils.isEmpty(str)) {
            LogUtil.m3785e("Download", "url为空");
            downloadNextTask();
        } else if (MUtils.checkM3U8IsExist(str, false)) {
            LogUtil.m3785e("Download", "本地已经缓存过该视频，url为：" + str);
            downloadNextTask();
        } else {
            M3U8Task m3U8Task = new M3U8Task(str);
            m3U8Task.setTitle(str2);
            m3U8Task.setPostId(str3);
            m3U8Task.setPreDownload(true);
            m3U8Task.setVideoView(i);
            if (this.downloadQueue.contains(m3U8Task)) {
                M3U8Task task = this.downloadQueue.getTask(str);
                if (task.getState() == 3) {
                    return;
                }
                startDownloadTask(task);
                return;
            }
            this.downloadQueue.offer(m3U8Task);
            startDownloadTask(m3U8Task);
        }
    }

    public void download(String str, String str2, String str3, int i) {
        if (TextUtils.isEmpty(str)) {
            LogUtil.m3785e("Download", "url为空");
            downloadNextTask();
        } else if (MUtils.checkM3U8IsExist(str, true)) {
            LogUtil.m3785e("Download", "本地已经缓存过该视频，url为：" + str);
            VideoDownload singleVideoBeanFormFile = getSingleVideoBeanFormFile(str);
            if (singleVideoBeanFormFile != null) {
                singleVideoBeanFormFile.setState(3);
                long folderSize = FileUtil.getFolderSize(new File(MUtils.generateM3U8Folder(str, true)));
                singleVideoBeanFormFile.setTitle(str2);
                singleVideoBeanFormFile.setPostId(str3);
                singleVideoBeanFormFile.setVideoView(i);
                singleVideoBeanFormFile.setSize(FileUtil.formatFileSize(folderSize));
                saveVideoBeanToFile(singleVideoBeanFormFile);
            }
            downloadNextTask();
        } else {
            M3U8Task m3U8Task = new M3U8Task(str);
            m3U8Task.setTitle(str2);
            m3U8Task.setPostId(str3);
            m3U8Task.setVideoView(i);
            if (this.downloadQueue.contains(m3U8Task)) {
                M3U8Task task = this.downloadQueue.getTask(str);
                if (task.getState() == 3) {
                    return;
                }
                startDownloadTask(task);
                return;
            }
            this.downloadQueue.offer(m3U8Task);
            startDownloadTask(m3U8Task);
        }
    }

    private void startDownloadTask(M3U8Task m3U8Task) {
        LogUtil.m3783i("Download", "startDownloadTask");
        if (m3U8Task == null) {
            return;
        }
        pendingTask(m3U8Task);
        if (!this.downloadQueue.isHead(m3U8Task) || m3U8Task.getState() == 5) {
            return;
        }
        this.currentM3U8Task = m3U8Task;
        this.m3U8DownloadTask.download(m3U8Task.isPreDownload(), m3U8Task.getUrl(), m3U8Task.getPostId(), m3U8Task.getVideoView(), this.iTaskDownloadListener);
    }

    public void pause(String str, boolean z) {
        LogUtil.m3783i("Download", "pause");
        if (TextUtils.isEmpty(str)) {
            return;
        }
        M3U8Task task = this.downloadQueue.getTask(str);
        if (task != null && !z) {
            task.setState(5);
            saveToDB(task);
            IM3U8DownloadListener iM3U8DownloadListener = this.im3U8DownloadListener;
            if (iM3U8DownloadListener != null) {
                iM3U8DownloadListener.onDownloadPause(task);
            }
            if (task.equals(this.currentM3U8Task)) {
                this.m3U8DownloadTask.stop("暂停当前任务，准备开始下载下一个任务");
            }
            this.downloadQueue.remove(task);
        }
        downloadNextTask();
        if (!z) {
            return;
        }
        deleteSingleVideoDownload(str);
    }

    public void pause(List<String> list, boolean z) {
        M3U8Task task;
        LogUtil.m3783i("Download", "pause");
        if (list == null || list.size() == 0) {
            return;
        }
        for (String str : list) {
            if (this.downloadQueue.contains(new M3U8Task(str)) && (task = this.downloadQueue.getTask(str)) != null && !z) {
                task.setState(5);
                saveToDB(task);
                IM3U8DownloadListener iM3U8DownloadListener = this.im3U8DownloadListener;
                if (iM3U8DownloadListener != null) {
                    iM3U8DownloadListener.onDownloadPause(task);
                }
                if (task.equals(this.currentM3U8Task)) {
                    this.m3U8DownloadTask.stop("暂停当前任务，准备开始下载下一个任务");
                }
                this.downloadQueue.remove(task);
            }
            if (z) {
                deleteSingleVideoDownload(str);
            }
        }
        downloadNextTask();
    }

    public void cancelAndDelete(final String str, final boolean z) {
        LogUtil.m3783i("Download", "cancelAndDelete");
        pause(str, true);
        new Thread(new Runnable(this) { // from class: com.one.tomato.thirdpart.m3u8.download.M3U8DownloadManager.1
            @Override // java.lang.Runnable
            public void run() {
                FileUtil.deleteFolderFile(MUtils.generateM3U8Folder(str, z));
            }
        }).start();
    }

    public void cancelAndDelete(final List<String> list) {
        LogUtil.m3783i("Download", "cancelAndDelete");
        pause(list, true);
        new Thread(new Runnable(this) { // from class: com.one.tomato.thirdpart.m3u8.download.M3U8DownloadManager.2
            @Override // java.lang.Runnable
            public void run() {
                for (String str : list) {
                    FileUtil.deleteFolderFile(MUtils.generateM3U8Folder(str, true));
                }
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveToDB(M3U8Task m3U8Task) {
        VideoDownload singleVideoBeanFormFile = getSingleVideoBeanFormFile(m3U8Task.getUrl());
        if (singleVideoBeanFormFile == null) {
            return;
        }
        singleVideoBeanFormFile.setState(m3U8Task.getState());
        singleVideoBeanFormFile.setSpeed(m3U8Task.getSpeed());
        singleVideoBeanFormFile.setProgress(m3U8Task.getProgress());
        singleVideoBeanFormFile.setSize(m3U8Task.getFormatTotalSize());
        singleVideoBeanFormFile.setTitle(m3U8Task.getTitle());
        singleVideoBeanFormFile.setVideoView(m3U8Task.getVideoView());
        saveVideoBeanToFile(singleVideoBeanFormFile);
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x007a A[Catch: all -> 0x0064, IOException -> 0x0076, TRY_LEAVE, TryCatch #4 {IOException -> 0x0076, blocks: (B:45:0x0072, B:38:0x007a), top: B:44:0x0072, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0072 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public synchronized void saveVideoBeanToFile(VideoDownload videoDownload) {
        ObjectOutputStream objectOutputStream;
        FileOutputStream fileOutputStream = null;
        try {
            try {
                File file = new File(FileUtil.getVideoBeanDownDir(), videoDownload.getUrl().hashCode() + ".out");
                if (file.exists()) {
                    file.delete();
                }
                FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                try {
                    objectOutputStream = new ObjectOutputStream(fileOutputStream2);
                    try {
                        objectOutputStream.writeObject(videoDownload);
                        objectOutputStream.flush();
                        try {
                            fileOutputStream2.close();
                            objectOutputStream.close();
                        } catch (IOException e) {
                            e = e;
                            e.printStackTrace();
                        }
                    } catch (Exception e2) {
                        e = e2;
                        fileOutputStream = fileOutputStream2;
                        try {
                            e.printStackTrace();
                            if (fileOutputStream != null) {
                                try {
                                    fileOutputStream.close();
                                } catch (IOException e3) {
                                    e = e3;
                                    e.printStackTrace();
                                }
                            }
                            if (objectOutputStream != null) {
                                objectOutputStream.close();
                            }
                        } catch (Throwable th) {
                            th = th;
                            if (fileOutputStream != null) {
                                try {
                                    fileOutputStream.close();
                                } catch (IOException e4) {
                                    e4.printStackTrace();
                                    throw th;
                                }
                            }
                            if (objectOutputStream != null) {
                                objectOutputStream.close();
                            }
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        fileOutputStream = fileOutputStream2;
                        if (fileOutputStream != null) {
                        }
                        if (objectOutputStream != null) {
                        }
                        throw th;
                    }
                } catch (Exception e5) {
                    e = e5;
                    objectOutputStream = null;
                } catch (Throwable th3) {
                    th = th3;
                    objectOutputStream = null;
                }
            } catch (Throwable th4) {
                throw th4;
            }
        } catch (Exception e6) {
            e = e6;
            objectOutputStream = null;
        } catch (Throwable th5) {
            th = th5;
            objectOutputStream = null;
        }
    }

    public synchronized String[] pullVideoBeanFormFileList() {
        String[] list;
        File videoBeanDownDir = FileUtil.getVideoBeanDownDir();
        if (videoBeanDownDir.isDirectory() && (list = videoBeanDownDir.list()) != null) {
            if (list.length > 0) {
                return list;
            }
        }
        return null;
    }

    public synchronized ArrayList<VideoDownload> getVideoBeanFormFileList() {
        String[] pullVideoBeanFormFileList = pullVideoBeanFormFileList();
        if (pullVideoBeanFormFileList != null && pullVideoBeanFormFileList.length != 0) {
            ArrayList<VideoDownload> arrayList = new ArrayList<>();
            for (String str : pullVideoBeanFormFileList) {
                VideoDownload videoDownBean = getVideoDownBean(str);
                if (videoDownBean != null) {
                    arrayList.add(videoDownBean);
                }
            }
            return arrayList;
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x002e, code lost:
        r1 = getVideoDownBean(r4);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public synchronized VideoDownload getSingleVideoBeanFormFile(String str) {
        String[] pullVideoBeanFormFileList = pullVideoBeanFormFileList();
        VideoDownload videoDownload = null;
        if (pullVideoBeanFormFileList != null && pullVideoBeanFormFileList.length != 0) {
            String str2 = str.hashCode() + ".out";
            int length = pullVideoBeanFormFileList.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                String str3 = pullVideoBeanFormFileList[i];
                if (str3.equals(str2)) {
                    break;
                }
                i++;
            }
            return videoDownload;
        }
        return null;
    }

    public synchronized boolean isVideoDownloadExist(String str) {
        String[] pullVideoBeanFormFileList = pullVideoBeanFormFileList();
        if (pullVideoBeanFormFileList != null && pullVideoBeanFormFileList.length != 0) {
            String str2 = str.hashCode() + ".out";
            for (String str3 : pullVideoBeanFormFileList) {
                if (str3.equals(str2)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public synchronized boolean deleteSingleVideoDownload(String str) {
        String[] pullVideoBeanFormFileList = pullVideoBeanFormFileList();
        if (pullVideoBeanFormFileList != null && pullVideoBeanFormFileList.length != 0) {
            String str2 = str.hashCode() + ".out";
            for (String str3 : pullVideoBeanFormFileList) {
                if (str3.equals(str2)) {
                    File file = new File(FileUtil.getVideoBeanDownDir().getPath() + File.separator + str2);
                    if (file.exists()) {
                        return file.delete();
                    }
                }
            }
            return false;
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x0084 A[Catch: all -> 0x006b, IOException -> 0x0080, TRY_LEAVE, TryCatch #3 {IOException -> 0x0080, blocks: (B:50:0x007c, B:43:0x0084), top: B:49:0x007c, outer: #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x007c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private synchronized VideoDownload getVideoDownBean(String str) {
        ObjectInputStream objectInputStream;
        FileInputStream fileInputStream;
        VideoDownload videoDownload;
        FileInputStream fileInputStream2 = null;
        videoDownload = null;
        videoDownload = null;
        videoDownload = null;
        videoDownload = null;
        videoDownload = null;
        try {
            try {
                File file = new File(FileUtil.getVideoBeanDownDir().getPath() + File.separator + str);
                if (file.exists()) {
                    fileInputStream = new FileInputStream(file);
                    try {
                        objectInputStream = new ObjectInputStream(fileInputStream);
                        try {
                            try {
                                Object readObject = objectInputStream.readObject();
                                if (readObject != null) {
                                    videoDownload = (VideoDownload) readObject;
                                }
                            } catch (Exception e) {
                                e = e;
                                e.printStackTrace();
                                if (fileInputStream != null) {
                                    try {
                                        fileInputStream.close();
                                    } catch (IOException e2) {
                                        e = e2;
                                        e.printStackTrace();
                                        return videoDownload;
                                    }
                                }
                                if (objectInputStream != null) {
                                    objectInputStream.close();
                                }
                                return videoDownload;
                            }
                        } catch (Throwable th) {
                            fileInputStream2 = fileInputStream;
                            th = th;
                            if (fileInputStream2 != null) {
                                try {
                                    fileInputStream2.close();
                                } catch (IOException e3) {
                                    e3.printStackTrace();
                                    throw th;
                                }
                            }
                            if (objectInputStream != null) {
                                objectInputStream.close();
                            }
                            throw th;
                        }
                    } catch (Exception e4) {
                        e = e4;
                        objectInputStream = null;
                    } catch (Throwable th2) {
                        fileInputStream2 = fileInputStream;
                        th = th2;
                        objectInputStream = null;
                        if (fileInputStream2 != null) {
                        }
                        if (objectInputStream != null) {
                        }
                        throw th;
                    }
                } else {
                    fileInputStream = null;
                    objectInputStream = null;
                }
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e5) {
                        e = e5;
                        e.printStackTrace();
                        return videoDownload;
                    }
                }
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (Throwable th3) {
                throw th3;
            }
        } catch (Exception e6) {
            e = e6;
            fileInputStream = null;
            objectInputStream = null;
        } catch (Throwable th4) {
            th = th4;
            objectInputStream = null;
        }
        return videoDownload;
    }
}
