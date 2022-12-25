package com.zzhoujay.richtext.p142ig;

import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.cache.BitmapPool;
import com.zzhoujay.richtext.callback.BitmapStream;
import com.zzhoujay.richtext.exceptions.ImageDownloadTaskAddFailureException;
import com.zzhoujay.richtext.exceptions.ImageLoadCancelledException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* renamed from: com.zzhoujay.richtext.ig.ImageDownloaderManager */
/* loaded from: classes4.dex */
class ImageDownloaderManager {
    private final ImageDownloadFinishCallback IMAGE_READY_CALLBACK;
    private final HashMap<String, Task> tasks;

    /* renamed from: com.zzhoujay.richtext.ig.ImageDownloaderManager$ImageDownloadFinishCallback */
    /* loaded from: classes4.dex */
    public interface ImageDownloadFinishCallback {
        void imageDownloadFinish(String str);
    }

    private ImageDownloaderManager() {
        this.IMAGE_READY_CALLBACK = new ImageDownloadFinishCallback() { // from class: com.zzhoujay.richtext.ig.ImageDownloaderManager.1
            @Override // com.zzhoujay.richtext.p142ig.ImageDownloaderManager.ImageDownloadFinishCallback
            public void imageDownloadFinish(String str) {
                synchronized (ImageDownloaderManager.this.tasks) {
                    ImageDownloaderManager.this.tasks.remove(str);
                }
            }
        };
        this.tasks = new HashMap<>();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static ImageDownloaderManager getImageDownloaderManager() {
        return ImageDownloaderManagerHolder.IMAGE_DOWNLOADER_MANAGER;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Cancelable addTask(ImageHolder imageHolder, ImageDownloader imageDownloader, CallbackImageLoader callbackImageLoader) {
        Cancelable exec;
        String key = imageHolder.getKey();
        synchronized (this.tasks) {
            Task task = this.tasks.get(key);
            if (task == null) {
                task = new Task(imageHolder.getSource(), key, imageDownloader, this.IMAGE_READY_CALLBACK);
                this.tasks.put(key, task);
            }
            exec = task.exec(getExecutorService(), callbackImageLoader);
        }
        return exec;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.zzhoujay.richtext.ig.ImageDownloaderManager$TaskCancelable */
    /* loaded from: classes4.dex */
    public static class TaskCancelable implements Cancelable {
        private WeakReference<CallbackImageLoader> callbackImageLoaderWeakReference;
        private WeakReference<Task> taskWeakReference;

        TaskCancelable(Task task, CallbackImageLoader callbackImageLoader) {
            this.taskWeakReference = new WeakReference<>(task);
            this.callbackImageLoaderWeakReference = new WeakReference<>(callbackImageLoader);
        }

        @Override // com.zzhoujay.richtext.p142ig.Cancelable
        public void cancel() {
            CallbackImageLoader callbackImageLoader;
            Task task = this.taskWeakReference.get();
            if (task == null || (callbackImageLoader = this.callbackImageLoaderWeakReference.get()) == null) {
                return;
            }
            task.removeCallback(callbackImageLoader);
            callbackImageLoader.onFailure(new ImageLoadCancelledException());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.zzhoujay.richtext.ig.ImageDownloaderManager$Task */
    /* loaded from: classes4.dex */
    public static class Task implements Runnable {
        private final ImageDownloadFinishCallback imageDownloadFinishCallback;
        private final ImageDownloader imageDownloader;
        private final String imageUrl;
        private final String key;
        private final Object stateLock = new Object();
        private volatile int state = 0;
        private final ArrayList<CallbackImageLoader> callbackList = new ArrayList<>();

        Task(String str, String str2, ImageDownloader imageDownloader, ImageDownloadFinishCallback imageDownloadFinishCallback) {
            this.imageUrl = str;
            this.imageDownloader = imageDownloader;
            this.imageDownloadFinishCallback = imageDownloadFinishCallback;
            this.key = str2;
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (this.stateLock) {
                this.state = 1;
            }
            Exception e = null;
            try {
                BitmapStream download = this.imageDownloader.download(this.imageUrl);
                BitmapPool.getPool().writeBitmapToTemp(this.key, download.getInputStream());
                download.close();
            } catch (Exception e2) {
                e = e2;
            }
            synchronized (this.stateLock) {
                this.imageDownloadFinishCallback.imageDownloadFinish(this.key);
                if (this.state != 1) {
                    return;
                }
                this.state = 2;
                synchronized (this.callbackList) {
                    Iterator<CallbackImageLoader> it2 = this.callbackList.iterator();
                    while (it2.hasNext()) {
                        it2.next().onImageDownloadFinish(this.key, e);
                    }
                }
                this.state = 3;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeCallback(CallbackImageLoader callbackImageLoader) {
            synchronized (this.callbackList) {
                this.callbackList.remove(callbackImageLoader);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Cancelable exec(ExecutorService executorService, CallbackImageLoader callbackImageLoader) {
            TaskCancelable taskCancelable;
            synchronized (this.stateLock) {
                if (this.state == 1) {
                    synchronized (this.callbackList) {
                        this.callbackList.add(callbackImageLoader);
                        taskCancelable = new TaskCancelable(this, callbackImageLoader);
                    }
                } else {
                    taskCancelable = null;
                }
                if (this.state == 0) {
                    this.state = 1;
                    executorService.submit(this);
                    synchronized (this.callbackList) {
                        this.callbackList.add(callbackImageLoader);
                        taskCancelable = new TaskCancelable(this, callbackImageLoader);
                    }
                }
            }
            if (taskCancelable == null) {
                callbackImageLoader.onFailure(new ImageDownloadTaskAddFailureException());
            }
            return taskCancelable;
        }
    }

    private static ExecutorService getExecutorService() {
        return ExecutorServiceHolder.EXECUTOR_SERVICE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.zzhoujay.richtext.ig.ImageDownloaderManager$ExecutorServiceHolder */
    /* loaded from: classes4.dex */
    public static class ExecutorServiceHolder {
        private static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.zzhoujay.richtext.ig.ImageDownloaderManager$ImageDownloaderManagerHolder */
    /* loaded from: classes4.dex */
    public static class ImageDownloaderManagerHolder {
        private static final ImageDownloaderManager IMAGE_DOWNLOADER_MANAGER = new ImageDownloaderManager();
    }
}
