package org.xutils.image;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import java.io.File;
import org.xutils.C5540x;
import org.xutils.ImageManager;
import org.xutils.common.Callback;

/* loaded from: classes4.dex */
public final class ImageManagerImpl implements ImageManager {
    private static volatile ImageManagerImpl instance;
    private static final Object lock = new Object();

    private ImageManagerImpl() {
    }

    public static void registerInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new ImageManagerImpl();
                }
            }
        }
        C5540x.Ext.setImageManager(instance);
    }

    @Override // org.xutils.ImageManager
    public void bind(final ImageView imageView, final String str) {
        C5540x.task().autoPost(new Runnable(this) { // from class: org.xutils.image.ImageManagerImpl.1
            @Override // java.lang.Runnable
            public void run() {
                ImageLoader.doBind(imageView, str, null, null);
            }
        });
    }

    @Override // org.xutils.ImageManager
    public void bind(final ImageView imageView, final String str, final ImageOptions imageOptions) {
        C5540x.task().autoPost(new Runnable(this) { // from class: org.xutils.image.ImageManagerImpl.2
            @Override // java.lang.Runnable
            public void run() {
                ImageLoader.doBind(imageView, str, imageOptions, null);
            }
        });
    }

    @Override // org.xutils.ImageManager
    public void bind(final ImageView imageView, final String str, final Callback.CommonCallback<Drawable> commonCallback) {
        C5540x.task().autoPost(new Runnable(this) { // from class: org.xutils.image.ImageManagerImpl.3
            @Override // java.lang.Runnable
            public void run() {
                ImageLoader.doBind(imageView, str, null, commonCallback);
            }
        });
    }

    @Override // org.xutils.ImageManager
    public void bind(final ImageView imageView, final String str, final ImageOptions imageOptions, final Callback.CommonCallback<Drawable> commonCallback) {
        C5540x.task().autoPost(new Runnable(this) { // from class: org.xutils.image.ImageManagerImpl.4
            @Override // java.lang.Runnable
            public void run() {
                ImageLoader.doBind(imageView, str, imageOptions, commonCallback);
            }
        });
    }

    @Override // org.xutils.ImageManager
    public Callback.Cancelable loadDrawable(String str, ImageOptions imageOptions, Callback.CommonCallback<Drawable> commonCallback) {
        return ImageLoader.doLoadDrawable(str, imageOptions, commonCallback);
    }

    @Override // org.xutils.ImageManager
    public Callback.Cancelable loadFile(String str, ImageOptions imageOptions, Callback.CacheCallback<File> cacheCallback) {
        return ImageLoader.doLoadFile(str, imageOptions, cacheCallback);
    }

    @Override // org.xutils.ImageManager
    public void clearMemCache() {
        ImageLoader.clearMemCache();
    }

    @Override // org.xutils.ImageManager
    public void clearCacheFiles() {
        ImageLoader.clearCacheFiles();
        ImageDecoder.clearCacheFiles();
    }
}
