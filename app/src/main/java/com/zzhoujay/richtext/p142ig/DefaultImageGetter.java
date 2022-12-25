package com.zzhoujay.richtext.p142ig;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.TextView;
import com.zzhoujay.richtext.CacheType;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.R$id;
import com.zzhoujay.richtext.RichTextConfig;
import com.zzhoujay.richtext.cache.BitmapPool;
import com.zzhoujay.richtext.callback.ImageGetter;
import com.zzhoujay.richtext.callback.ImageLoadNotify;
import com.zzhoujay.richtext.drawable.DrawableSizeHolder;
import com.zzhoujay.richtext.drawable.DrawableWrapper;
import com.zzhoujay.richtext.ext.Base64;
import com.zzhoujay.richtext.ext.TextKit;
import java.util.HashSet;
import java.util.Iterator;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* renamed from: com.zzhoujay.richtext.ig.DefaultImageGetter */
/* loaded from: classes4.dex */
public class DefaultImageGetter implements ImageGetter, ImageLoadNotify {
    private static final int TASK_TAG = R$id.zhou_default_image_tag_id;
    private ImageLoadNotify notify;
    private int loadedCount = 0;
    private final HashSet<Cancelable> tasks = new HashSet<>();
    private final WeakHashMap<ImageLoader, Cancelable> taskMap = new WeakHashMap<>();

    private void checkTarget(TextView textView) {
        synchronized (DefaultImageGetter.class) {
            HashSet<Cancelable> hashSet = (HashSet) textView.getTag(TASK_TAG);
            if (hashSet != null) {
                if (hashSet == this.tasks) {
                    return;
                }
                HashSet hashSet2 = new HashSet(hashSet);
                Iterator it2 = hashSet2.iterator();
                while (it2.hasNext()) {
                    ((Cancelable) it2.next()).cancel();
                }
                hashSet2.clear();
                hashSet.clear();
            }
            textView.setTag(TASK_TAG, this.tasks);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:17:0x011a  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0131  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x008d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v15, types: [com.zzhoujay.richtext.ig.LocalFileImageLoader, java.lang.Runnable] */
    /* JADX WARN: Type inference failed for: r0v17, types: [com.zzhoujay.richtext.ig.Base64ImageLoader, java.lang.Runnable] */
    /* JADX WARN: Type inference failed for: r1v11, types: [java.util.concurrent.ExecutorService] */
    /* JADX WARN: Type inference failed for: r1v5, types: [java.util.concurrent.ExecutorService] */
    @Override // com.zzhoujay.richtext.callback.DrawableGetter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Drawable getDrawable(ImageHolder imageHolder, RichTextConfig richTextConfig, TextView textView) {
        Exception exc;
        InputStreamImageLoader inputStreamImageLoader;
        Cancelable cancelable;
        CallbackImageLoader callbackImageLoader;
        Cancelable cancelable2;
        InputStreamImageLoader inputStreamImageLoader2;
        checkTarget(textView);
        BitmapPool pool = BitmapPool.getPool();
        String key = imageHolder.getKey();
        DrawableSizeHolder sizeHolder = pool.getSizeHolder(key);
        Bitmap bitmap = pool.getBitmap(key);
        DrawableWrapper drawableWrapper = (richTextConfig.cacheType.intValue() <= CacheType.none.intValue() || sizeHolder == null) ? new DrawableWrapper(imageHolder) : new DrawableWrapper(sizeHolder);
        boolean hasBitmapLocalCache = pool.hasBitmapLocalCache(key);
        InputStreamImageLoader inputStreamImageLoader3 = null;
        try {
        } catch (Exception e) {
            exc = e;
            inputStreamImageLoader = null;
            cancelable = null;
        }
        if (richTextConfig.cacheType.intValue() > CacheType.layout.intValue()) {
            if (bitmap != null) {
                BitmapDrawable bitmapDrawable = new BitmapDrawable(textView.getResources(), bitmap);
                bitmapDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                drawableWrapper.setDrawable(bitmapDrawable);
                drawableWrapper.calculate();
                return drawableWrapper;
            } else if (hasBitmapLocalCache) {
                InputStreamImageLoader inputStreamImageLoader4 = new InputStreamImageLoader(imageHolder, richTextConfig, textView, drawableWrapper, this, pool.readBitmapFromTemp(key));
                inputStreamImageLoader3 = inputStreamImageLoader4;
                cancelable = new FutureCancelableWrapper(getExecutorService().submit(inputStreamImageLoader4));
                if (inputStreamImageLoader3 != null) {
                    try {
                    } catch (Exception e2) {
                        exc = e2;
                        inputStreamImageLoader = inputStreamImageLoader3;
                        errorHandle(imageHolder, richTextConfig, textView, drawableWrapper, exc);
                        callbackImageLoader = inputStreamImageLoader;
                        cancelable2 = cancelable;
                        inputStreamImageLoader2 = callbackImageLoader;
                        checkTarget(textView);
                        if (cancelable2 != null) {
                        }
                        return drawableWrapper;
                    }
                    if (Base64.isBase64(imageHolder.getSource())) {
                        ?? base64ImageLoader = new Base64ImageLoader(imageHolder, richTextConfig, textView, drawableWrapper, this);
                        cancelable2 = new FutureCancelableWrapper(getExecutorService().submit(base64ImageLoader));
                        inputStreamImageLoader2 = base64ImageLoader;
                    } else if (TextKit.isAssetPath(imageHolder.getSource())) {
                        InputStreamImageLoader assetsImageLoader = new AssetsImageLoader(imageHolder, richTextConfig, textView, drawableWrapper, this);
                        cancelable2 = new FutureCancelableWrapper(getExecutorService().submit(assetsImageLoader));
                        inputStreamImageLoader2 = assetsImageLoader;
                    } else if (TextKit.isLocalPath(imageHolder.getSource())) {
                        ?? localFileImageLoader = new LocalFileImageLoader(imageHolder, richTextConfig, textView, drawableWrapper, this);
                        cancelable2 = new FutureCancelableWrapper(getExecutorService().submit(localFileImageLoader));
                        inputStreamImageLoader2 = localFileImageLoader;
                    } else {
                        CallbackImageLoader callbackImageLoader2 = new CallbackImageLoader(imageHolder, richTextConfig, textView, drawableWrapper, this);
                        cancelable = ImageDownloaderManager.getImageDownloaderManager().addTask(imageHolder, richTextConfig.imageDownloader, callbackImageLoader2);
                        callbackImageLoader = callbackImageLoader2;
                        cancelable2 = cancelable;
                        inputStreamImageLoader2 = callbackImageLoader;
                    }
                } else {
                    cancelable2 = cancelable;
                    inputStreamImageLoader2 = inputStreamImageLoader3;
                }
                checkTarget(textView);
                if (cancelable2 != null) {
                    addTask(cancelable2, inputStreamImageLoader2);
                }
                return drawableWrapper;
            }
        }
        cancelable = null;
        if (inputStreamImageLoader3 != null) {
        }
        checkTarget(textView);
        if (cancelable2 != null) {
        }
        return drawableWrapper;
    }

    private void errorHandle(ImageHolder imageHolder, RichTextConfig richTextConfig, TextView textView, DrawableWrapper drawableWrapper, Exception exc) {
        new AbstractImageLoader<Object>(this, imageHolder, richTextConfig, textView, drawableWrapper, this, null) { // from class: com.zzhoujay.richtext.ig.DefaultImageGetter.1
        }.onFailure(exc);
    }

    private void addTask(Cancelable cancelable, AbstractImageLoader abstractImageLoader) {
        synchronized (DefaultImageGetter.class) {
            this.tasks.add(cancelable);
            this.taskMap.put(abstractImageLoader, cancelable);
        }
    }

    @Override // com.zzhoujay.richtext.callback.ImageGetter
    public void registerImageLoadNotify(ImageLoadNotify imageLoadNotify) {
        this.notify = imageLoadNotify;
    }

    @Override // com.zzhoujay.richtext.callback.ImageLoadNotify
    public void done(Object obj) {
        if (obj instanceof AbstractImageLoader) {
            AbstractImageLoader abstractImageLoader = (AbstractImageLoader) obj;
            synchronized (DefaultImageGetter.class) {
                Cancelable cancelable = this.taskMap.get(abstractImageLoader);
                if (cancelable != null) {
                    this.tasks.remove(cancelable);
                }
                this.taskMap.remove(abstractImageLoader);
                this.loadedCount++;
                if (this.notify != null) {
                    this.notify.done(Integer.valueOf(this.loadedCount));
                }
            }
        }
    }

    private static ExecutorService getExecutorService() {
        return ExecutorServiceHolder.EXECUTOR_SERVICE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.zzhoujay.richtext.ig.DefaultImageGetter$ExecutorServiceHolder */
    /* loaded from: classes4.dex */
    public static class ExecutorServiceHolder {
        private static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();
    }
}
