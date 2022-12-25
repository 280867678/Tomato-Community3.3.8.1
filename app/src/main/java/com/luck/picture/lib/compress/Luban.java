package com.luck.picture.lib.compress;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class Luban implements Handler.Callback {
    private int index;
    private OnCompressListener mCompressListener;
    private Handler mHandler;
    private int mLeastCompressSize;
    private List<String> mPaths;
    private String mTargetDir;
    private List<LocalMedia> medias;

    static /* synthetic */ int access$608(Luban luban) {
        int i = luban.index;
        luban.index = i + 1;
        return i;
    }

    private Luban(Builder builder) {
        this.index = -1;
        this.mPaths = builder.mPaths;
        this.medias = builder.medias;
        Context unused = builder.context;
        this.mTargetDir = builder.mTargetDir;
        this.mCompressListener = builder.mCompressListener;
        this.mLeastCompressSize = builder.mLeastCompressSize;
        this.mHandler = new Handler(Looper.getMainLooper(), this);
    }

    public static Builder with(Context context) {
        return new Builder(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public File getImageCacheFile(Context context, String str) {
        if (TextUtils.isEmpty(this.mTargetDir)) {
            this.mTargetDir = getImageCacheDir(context).getAbsolutePath();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.mTargetDir);
        sb.append("/");
        sb.append(System.currentTimeMillis());
        sb.append((int) (Math.random() * 1000.0d));
        if (TextUtils.isEmpty(str)) {
            str = ".jpg";
        }
        sb.append(str);
        return new File(sb.toString());
    }

    @Nullable
    private File getImageCacheDir(Context context) {
        return getImageCacheDir(context, "luban_disk_cache");
    }

    @Nullable
    private File getImageCacheDir(Context context, String str) {
        File file = new File(new File(PictureFileUtils.getDiskCacheDir(context)), str);
        if (!file.mkdirs()) {
            if (file.exists() && file.isDirectory()) {
                return file;
            }
            return null;
        }
        return file;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @UiThread
    public void launch(final Context context) {
        List<String> list = this.mPaths;
        if (list == null || (list.size() == 0 && this.mCompressListener != null)) {
            this.mCompressListener.onError(new NullPointerException("image file cannot be null"));
        }
        Iterator<String> it2 = this.mPaths.iterator();
        this.index = -1;
        while (it2.hasNext()) {
            final String next = it2.next();
            if (Checker.isImage(next)) {
                AsyncTask.SERIAL_EXECUTOR.execute(new Runnable() { // from class: com.luck.picture.lib.compress.Luban.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            Luban.access$608(Luban.this);
                            boolean z = true;
                            Luban.this.mHandler.sendMessage(Luban.this.mHandler.obtainMessage(1));
                            File compress = Checker.isNeedCompress(Luban.this.mLeastCompressSize, next) ? new Engine(next, Luban.this.getImageCacheFile(context, Checker.checkSuffix(next))).compress() : new File(next);
                            if (Luban.this.medias == null || Luban.this.medias.size() <= 0) {
                                Luban.this.mHandler.sendMessage(Luban.this.mHandler.obtainMessage(2, new IOException()));
                                return;
                            }
                            LocalMedia localMedia = (LocalMedia) Luban.this.medias.get(Luban.this.index);
                            boolean isHttp = PictureMimeType.isHttp(compress.getAbsolutePath());
                            localMedia.setCompressed(!isHttp);
                            localMedia.setCompressPath(isHttp ? "" : compress.getAbsolutePath());
                            if (Luban.this.index != Luban.this.medias.size() - 1) {
                                z = false;
                            }
                            if (!z) {
                                return;
                            }
                            Luban.this.mHandler.sendMessage(Luban.this.mHandler.obtainMessage(3, Luban.this.medias));
                        } catch (IOException e) {
                            Luban.this.mHandler.sendMessage(Luban.this.mHandler.obtainMessage(2, e));
                        }
                    }
                });
            } else {
                OnCompressListener onCompressListener = this.mCompressListener;
                onCompressListener.onError(new IllegalArgumentException("can not read the path : " + next));
            }
            it2.remove();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @WorkerThread
    public List<File> get(Context context) throws IOException {
        ArrayList arrayList = new ArrayList();
        Iterator<String> it2 = this.mPaths.iterator();
        while (it2.hasNext()) {
            String next = it2.next();
            if (Checker.isImage(next)) {
                arrayList.add(Checker.isNeedCompress(this.mLeastCompressSize, next) ? new Engine(next, getImageCacheFile(context, Checker.checkSuffix(next))).compress() : new File(next));
            }
            it2.remove();
        }
        return arrayList;
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        OnCompressListener onCompressListener = this.mCompressListener;
        if (onCompressListener == null) {
            return false;
        }
        int i = message.what;
        if (i == 1) {
            onCompressListener.onStart();
        } else if (i == 2) {
            onCompressListener.onError((Throwable) message.obj);
        } else if (i == 3) {
            onCompressListener.onSuccess((List) message.obj);
        }
        return false;
    }

    /* loaded from: classes3.dex */
    public static class Builder {
        private Context context;
        private OnCompressListener mCompressListener;
        private int mLeastCompressSize = 100;
        private List<String> mPaths = new ArrayList();
        private String mTargetDir;
        private List<LocalMedia> medias;

        Builder(Context context) {
            this.context = context;
        }

        private Luban build() {
            return new Luban(this);
        }

        public Builder loadLocalMedia(List<LocalMedia> list) {
            if (list == null) {
                list = new ArrayList<>();
            }
            this.medias = list;
            for (LocalMedia localMedia : list) {
                this.mPaths.add(localMedia.isCut() ? localMedia.getCutPath() : localMedia.getPath());
            }
            return this;
        }

        public Builder setCompressListener(OnCompressListener onCompressListener) {
            this.mCompressListener = onCompressListener;
            return this;
        }

        public Builder setTargetDir(String str) {
            this.mTargetDir = str;
            return this;
        }

        public Builder ignoreBy(int i) {
            this.mLeastCompressSize = i;
            return this;
        }

        public void launch() {
            build().launch(this.context);
        }

        public List<File> get() throws IOException {
            return build().get(this.context);
        }
    }
}
