package org.xutils.image;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.widget.ImageView;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicLong;
import org.xutils.C5540x;
import org.xutils.cache.LruCache;
import org.xutils.cache.LruDiskCache;
import org.xutils.common.Callback;
import org.xutils.common.task.Priority;
import org.xutils.common.task.PriorityExecutor;
import org.xutils.common.util.IOUtil;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.p149ex.FileLockedException;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public final class ImageLoader implements Callback.PrepareCallback<File, Drawable>, Callback.CacheCallback<Drawable>, Callback.ProgressCallback<Drawable>, Callback.TypedCallback<Drawable>, Callback.Cancelable {
    private static final HashMap<String, FakeImageView> FAKE_IMG_MAP;
    private static final Type loadType;
    private Callback.CacheCallback<Drawable> cacheCallback;
    private Callback.CommonCallback<Drawable> callback;
    private Callback.Cancelable cancelable;
    private MemCacheKey key;
    private ImageOptions options;
    private Callback.PrepareCallback<File, Drawable> prepareCallback;
    private Callback.ProgressCallback<Drawable> progressCallback;
    private WeakReference<ImageView> viewRef;
    private static final AtomicLong SEQ_SEEK = new AtomicLong(0);
    private static final Executor EXECUTOR = new PriorityExecutor(10, false);
    private static final LruCache<MemCacheKey, Drawable> MEM_CACHE = new LruCache<MemCacheKey, Drawable>(4194304) { // from class: org.xutils.image.ImageLoader.1
        private boolean deepClear = false;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.xutils.cache.LruCache
        public int sizeOf(MemCacheKey memCacheKey, Drawable drawable) {
            if (drawable instanceof BitmapDrawable) {
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                if (bitmap != null) {
                    return bitmap.getByteCount();
                }
                return 0;
            } else if (drawable instanceof GifDrawable) {
                return ((GifDrawable) drawable).getByteCount();
            } else {
                return super.sizeOf((C55331) memCacheKey, (MemCacheKey) drawable);
            }
        }

        @Override // org.xutils.cache.LruCache
        public void trimToSize(int i) {
            if (i < 0) {
                this.deepClear = true;
            }
            super.trimToSize(i);
            this.deepClear = false;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.xutils.cache.LruCache
        public void entryRemoved(boolean z, MemCacheKey memCacheKey, Drawable drawable, Drawable drawable2) {
            super.entryRemoved(z, (boolean) memCacheKey, drawable, drawable2);
            if (!z || !this.deepClear || !(drawable instanceof ReusableDrawable)) {
                return;
            }
            ((ReusableDrawable) drawable).setMemCacheKey(null);
        }
    };
    private final long seq = SEQ_SEEK.incrementAndGet();
    private volatile boolean stopped = false;
    private volatile boolean cancelled = false;
    private boolean hasCache = false;

    static {
        int memoryClass = (((ActivityManager) C5540x.app().getSystemService("activity")).getMemoryClass() * 1048576) / 8;
        if (memoryClass < 4194304) {
            memoryClass = 4194304;
        }
        MEM_CACHE.resize(memoryClass);
        FAKE_IMG_MAP = new HashMap<>();
        loadType = File.class;
    }

    private ImageLoader() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void clearMemCache() {
        MEM_CACHE.evictAll();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void clearCacheFiles() {
        LruDiskCache.getDiskCache("xUtils_img").clearCacheFiles();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Callback.Cancelable doLoadDrawable(String str, ImageOptions imageOptions, Callback.CommonCallback<Drawable> commonCallback) {
        FakeImageView fakeImageView;
        if (TextUtils.isEmpty(str)) {
            postArgsException(null, imageOptions, "url is null", commonCallback);
            return null;
        }
        synchronized (FAKE_IMG_MAP) {
            fakeImageView = FAKE_IMG_MAP.get(str);
            if (fakeImageView == null) {
                fakeImageView = new FakeImageView();
            }
        }
        return doBind(fakeImageView, str, imageOptions, commonCallback);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Callback.Cancelable doLoadFile(String str, ImageOptions imageOptions, Callback.CacheCallback<File> cacheCallback) {
        if (TextUtils.isEmpty(str)) {
            postArgsException(null, imageOptions, "url is null", cacheCallback);
            return null;
        }
        return C5540x.http().get(createRequestParams(str, imageOptions), cacheCallback);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0079, code lost:
        if (r2.isRecycled() != false) goto L91;
     */
    /* JADX WARN: Removed duplicated region for block: B:31:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x00ff  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Callback.Cancelable doBind(ImageView imageView, String str, ImageOptions imageOptions, Callback.CommonCallback<Drawable> commonCallback) {
        MemCacheKey memCacheKey;
        Drawable drawable;
        boolean z;
        boolean z2;
        if (imageView == null) {
            postArgsException(null, imageOptions, "view is null", commonCallback);
            return null;
        } else if (TextUtils.isEmpty(str)) {
            postArgsException(imageView, imageOptions, "url is null", commonCallback);
            return null;
        } else {
            if (imageOptions == null) {
                imageOptions = ImageOptions.DEFAULT;
            }
            imageOptions.optimizeMaxSize(imageView);
            MemCacheKey memCacheKey2 = new MemCacheKey(str, imageOptions);
            Drawable drawable2 = imageView.getDrawable();
            if (drawable2 instanceof AsyncDrawable) {
                ImageLoader imageLoader = ((AsyncDrawable) drawable2).getImageLoader();
                if (imageLoader != null && !imageLoader.stopped) {
                    if (memCacheKey2.equals(imageLoader.key)) {
                        return null;
                    }
                    imageLoader.cancel();
                }
            } else if ((drawable2 instanceof ReusableDrawable) && (memCacheKey = ((ReusableDrawable) drawable2).getMemCacheKey()) != null && memCacheKey.equals(memCacheKey2)) {
                MEM_CACHE.put(memCacheKey2, drawable2);
            }
            if (imageOptions.isUseMemCache()) {
                drawable = MEM_CACHE.get(memCacheKey2);
                if (drawable instanceof BitmapDrawable) {
                    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                    if (bitmap != null) {
                    }
                }
                if (drawable == null) {
                    boolean z3 = false;
                    try {
                        if (commonCallback instanceof Callback.ProgressCallback) {
                            ((Callback.ProgressCallback) commonCallback).onWaiting();
                        }
                        imageView.setScaleType(imageOptions.getImageScaleType());
                        imageView.setImageDrawable(drawable);
                        z = true;
                        try {
                            if (commonCallback instanceof Callback.CacheCallback) {
                                z2 = ((Callback.CacheCallback) commonCallback).onCache(drawable);
                                if (!z2) {
                                    try {
                                        Callback.Cancelable doLoad = new ImageLoader().doLoad(imageView, str, imageOptions, commonCallback);
                                        if (z2 && commonCallback != null) {
                                            try {
                                                commonCallback.onFinished();
                                            } catch (Throwable th) {
                                                LogUtil.m43e(th.getMessage(), th);
                                            }
                                        }
                                        return doLoad;
                                    } catch (Throwable th2) {
                                        th = th2;
                                        z = z2;
                                        try {
                                            LogUtil.m43e(th.getMessage(), th);
                                            try {
                                                return new ImageLoader().doLoad(imageView, str, imageOptions, commonCallback);
                                            } catch (Throwable th3) {
                                                th = th3;
                                                if (z3 && commonCallback != null) {
                                                    try {
                                                        commonCallback.onFinished();
                                                    } catch (Throwable th4) {
                                                        LogUtil.m43e(th4.getMessage(), th4);
                                                    }
                                                }
                                                throw th;
                                            }
                                        } catch (Throwable th5) {
                                            th = th5;
                                            z3 = z;
                                        }
                                    }
                                }
                            } else {
                                if (commonCallback != null) {
                                    commonCallback.onSuccess(drawable);
                                }
                                z2 = true;
                            }
                            if (z2 && commonCallback != null) {
                                try {
                                    commonCallback.onFinished();
                                } catch (Throwable th6) {
                                    LogUtil.m43e(th6.getMessage(), th6);
                                }
                            }
                            return null;
                        } catch (Throwable th7) {
                            th = th7;
                        }
                    } catch (Throwable th8) {
                        th = th8;
                        z = false;
                    }
                } else {
                    return new ImageLoader().doLoad(imageView, str, imageOptions, commonCallback);
                }
            }
            drawable = null;
            if (drawable == null) {
            }
        }
    }

    private Callback.Cancelable doLoad(ImageView imageView, String str, ImageOptions imageOptions, Callback.CommonCallback<Drawable> commonCallback) {
        this.viewRef = new WeakReference<>(imageView);
        this.options = imageOptions;
        this.key = new MemCacheKey(str, imageOptions);
        this.callback = commonCallback;
        if (commonCallback instanceof Callback.ProgressCallback) {
            this.progressCallback = (Callback.ProgressCallback) commonCallback;
        }
        if (commonCallback instanceof Callback.PrepareCallback) {
            this.prepareCallback = (Callback.PrepareCallback) commonCallback;
        }
        if (commonCallback instanceof Callback.CacheCallback) {
            this.cacheCallback = (Callback.CacheCallback) commonCallback;
        }
        if (imageOptions.isForceLoadingDrawable()) {
            Drawable loadingDrawable = imageOptions.getLoadingDrawable(imageView);
            imageView.setScaleType(imageOptions.getPlaceholderScaleType());
            imageView.setImageDrawable(new AsyncDrawable(this, loadingDrawable));
        } else {
            imageView.setImageDrawable(new AsyncDrawable(this, imageView.getDrawable()));
        }
        RequestParams createRequestParams = createRequestParams(str, imageOptions);
        if (imageView instanceof FakeImageView) {
            synchronized (FAKE_IMG_MAP) {
                FAKE_IMG_MAP.put(str, (FakeImageView) imageView);
            }
        }
        Callback.Cancelable cancelable = C5540x.http().get(createRequestParams, this);
        this.cancelable = cancelable;
        return cancelable;
    }

    @Override // org.xutils.common.Callback.Cancelable
    public void cancel() {
        this.stopped = true;
        this.cancelled = true;
        Callback.Cancelable cancelable = this.cancelable;
        if (cancelable != null) {
            cancelable.cancel();
        }
    }

    @Override // org.xutils.common.Callback.Cancelable
    public boolean isCancelled() {
        return this.cancelled || !validView4Callback(false);
    }

    @Override // org.xutils.common.Callback.ProgressCallback
    public void onWaiting() {
        Callback.ProgressCallback<Drawable> progressCallback = this.progressCallback;
        if (progressCallback != null) {
            progressCallback.onWaiting();
        }
    }

    @Override // org.xutils.common.Callback.ProgressCallback
    public void onStarted() {
        Callback.ProgressCallback<Drawable> progressCallback;
        if (!validView4Callback(true) || (progressCallback = this.progressCallback) == null) {
            return;
        }
        progressCallback.onStarted();
    }

    @Override // org.xutils.common.Callback.ProgressCallback
    public void onLoading(long j, long j2, boolean z) {
        Callback.ProgressCallback<Drawable> progressCallback;
        if (!validView4Callback(true) || (progressCallback = this.progressCallback) == null) {
            return;
        }
        progressCallback.onLoading(j, j2, z);
    }

    @Override // org.xutils.common.Callback.TypedCallback
    public Type getLoadType() {
        return loadType;
    }

    @Override // org.xutils.common.Callback.PrepareCallback
    public Drawable prepare(File file) {
        if (!validView4Callback(true)) {
            return null;
        }
        try {
            Drawable prepare = this.prepareCallback != null ? this.prepareCallback.prepare(file) : null;
            if (prepare == null) {
                prepare = ImageDecoder.decodeFileWithLock(file, this.options, this);
            }
            if (prepare != null && (prepare instanceof ReusableDrawable)) {
                ((ReusableDrawable) prepare).setMemCacheKey(this.key);
                MEM_CACHE.put(this.key, prepare);
            }
            return prepare;
        } catch (IOException e) {
            IOUtil.deleteFileOrDir(file);
            LogUtil.m37w(e.getMessage(), e);
            return null;
        }
    }

    @Override // org.xutils.common.Callback.CacheCallback
    public boolean onCache(Drawable drawable) {
        if (validView4Callback(true) && drawable != null) {
            this.hasCache = true;
            setSuccessDrawable4Callback(drawable);
            Callback.CacheCallback<Drawable> cacheCallback = this.cacheCallback;
            if (cacheCallback != null) {
                return cacheCallback.onCache(drawable);
            }
            Callback.CommonCallback<Drawable> commonCallback = this.callback;
            if (commonCallback != null) {
                commonCallback.onSuccess(drawable);
            }
            return true;
        }
        return false;
    }

    @Override // org.xutils.common.Callback.CommonCallback
    public void onSuccess(Drawable drawable) {
        if (validView4Callback(!this.hasCache) && drawable != null) {
            setSuccessDrawable4Callback(drawable);
            Callback.CommonCallback<Drawable> commonCallback = this.callback;
            if (commonCallback == null) {
                return;
            }
            commonCallback.onSuccess(drawable);
        }
    }

    @Override // org.xutils.common.Callback.CommonCallback
    public void onError(Throwable th, boolean z) {
        this.stopped = true;
        if (!validView4Callback(false)) {
            return;
        }
        if (th instanceof FileLockedException) {
            LogUtil.m46d("ImageFileLocked: " + this.key.url);
            C5540x.task().postDelayed(new Runnable() { // from class: org.xutils.image.ImageLoader.2
                @Override // java.lang.Runnable
                public void run() {
                    ImageLoader.doBind((ImageView) ImageLoader.this.viewRef.get(), ImageLoader.this.key.url, ImageLoader.this.options, ImageLoader.this.callback);
                }
            }, 10L);
            return;
        }
        LogUtil.m43e(this.key.url, th);
        setErrorDrawable4Callback();
        Callback.CommonCallback<Drawable> commonCallback = this.callback;
        if (commonCallback == null) {
            return;
        }
        commonCallback.onError(th, z);
    }

    @Override // org.xutils.common.Callback.CommonCallback
    public void onCancelled(Callback.CancelledException cancelledException) {
        Callback.CommonCallback<Drawable> commonCallback;
        this.stopped = true;
        if (validView4Callback(false) && (commonCallback = this.callback) != null) {
            commonCallback.onCancelled(cancelledException);
        }
    }

    @Override // org.xutils.common.Callback.CommonCallback
    public void onFinished() {
        Callback.CommonCallback<Drawable> commonCallback;
        this.stopped = true;
        if (this.viewRef.get() instanceof FakeImageView) {
            synchronized (FAKE_IMG_MAP) {
                FAKE_IMG_MAP.remove(this.key.url);
            }
        }
        if (validView4Callback(false) && (commonCallback = this.callback) != null) {
            commonCallback.onFinished();
        }
    }

    private static RequestParams createRequestParams(String str, ImageOptions imageOptions) {
        ImageOptions.ParamsBuilder paramsBuilder;
        RequestParams requestParams = new RequestParams(str);
        requestParams.setCacheDirName("xUtils_img");
        requestParams.setConnectTimeout(8000);
        requestParams.setPriority(Priority.BG_LOW);
        requestParams.setExecutor(EXECUTOR);
        requestParams.setCancelFast(true);
        requestParams.setUseCookie(false);
        return (imageOptions == null || (paramsBuilder = imageOptions.getParamsBuilder()) == null) ? requestParams : paramsBuilder.buildParams(requestParams, imageOptions);
    }

    private boolean validView4Callback(boolean z) {
        ImageView imageView = this.viewRef.get();
        if (imageView != null) {
            Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                ImageLoader imageLoader = ((AsyncDrawable) drawable).getImageLoader();
                if (imageLoader != null) {
                    if (imageLoader == this) {
                        if (imageView.getVisibility() == 0) {
                            return true;
                        }
                        imageLoader.cancel();
                        return false;
                    } else if (this.seq > imageLoader.seq) {
                        imageLoader.cancel();
                        return true;
                    } else {
                        cancel();
                        return false;
                    }
                }
            } else if (z) {
                cancel();
                return false;
            }
            return true;
        }
        return false;
    }

    private void setSuccessDrawable4Callback(Drawable drawable) {
        ImageView imageView = this.viewRef.get();
        if (imageView != null) {
            imageView.setScaleType(this.options.getImageScaleType());
            if (drawable instanceof GifDrawable) {
                if (imageView.getScaleType() == ImageView.ScaleType.CENTER) {
                    imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                }
                imageView.setLayerType(1, null);
            }
            if (this.options.getAnimation() != null) {
                ImageAnimationHelper.animationDisplay(imageView, drawable, this.options.getAnimation());
            } else if (this.options.isFadeIn()) {
                ImageAnimationHelper.fadeInDisplay(imageView, drawable);
            } else {
                imageView.setImageDrawable(drawable);
            }
        }
    }

    private void setErrorDrawable4Callback() {
        ImageView imageView = this.viewRef.get();
        if (imageView != null) {
            Drawable failureDrawable = this.options.getFailureDrawable(imageView);
            imageView.setScaleType(this.options.getPlaceholderScaleType());
            imageView.setImageDrawable(failureDrawable);
        }
    }

    private static void postArgsException(final ImageView imageView, final ImageOptions imageOptions, final String str, final Callback.CommonCallback<?> commonCallback) {
        C5540x.task().autoPost(new Runnable() { // from class: org.xutils.image.ImageLoader.3
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (Callback.CommonCallback.this instanceof Callback.ProgressCallback) {
                        ((Callback.ProgressCallback) Callback.CommonCallback.this).onWaiting();
                    }
                    if (imageView != null && imageOptions != null) {
                        imageView.setScaleType(imageOptions.getPlaceholderScaleType());
                        imageView.setImageDrawable(imageOptions.getFailureDrawable(imageView));
                    }
                    if (Callback.CommonCallback.this != null) {
                        Callback.CommonCallback.this.onError(new IllegalArgumentException(str), false);
                    }
                    Callback.CommonCallback commonCallback2 = Callback.CommonCallback.this;
                    if (commonCallback2 == null) {
                        return;
                    }
                    commonCallback2.onFinished();
                } catch (Throwable th) {
                    LogUtil.m43e(th.getMessage(), th);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    @SuppressLint({"ViewConstructor"})
    /* loaded from: classes4.dex */
    public static final class FakeImageView extends ImageView {
        private Drawable drawable;

        @Override // android.view.View
        public void setLayerType(int i, Paint paint) {
        }

        @Override // android.widget.ImageView
        public void setScaleType(ImageView.ScaleType scaleType) {
        }

        @Override // android.view.View
        public void startAnimation(Animation animation) {
        }

        public FakeImageView() {
            super(C5540x.app());
        }

        @Override // android.widget.ImageView
        public void setImageDrawable(Drawable drawable) {
            this.drawable = drawable;
        }

        @Override // android.widget.ImageView
        public Drawable getDrawable() {
            return this.drawable;
        }
    }
}
