package com.gen.p059mh.webapp_extensions.matisse.engine.impl;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import com.gen.p059mh.webapp_extensions.WebApplication;
import com.gen.p059mh.webapp_extensions.matisse.engine.ImageEngine;
import com.gen.p059mh.webapps.utils.Logger;
import com.gen.p059mh.webapps.utils.Utils;
import com.p076mh.webappStart.android_plugin_impl.callback.CommonCallBack;
import com.p076mh.webappStart.util.MainHandler;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URLDecoder;

/* renamed from: com.gen.mh.webapp_extensions.matisse.engine.impl.PicassoEngine */
/* loaded from: classes2.dex */
public class PicassoEngine implements ImageEngine {
    boolean isUseProxy;

    @Override // com.gen.p059mh.webapp_extensions.matisse.engine.ImageEngine
    public void load(Context context, String str, CommonCallBack<byte[]> commonCallBack) {
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.engine.ImageEngine
    public boolean supportAnimatedGif() {
        return false;
    }

    public PicassoEngine() {
        this.isUseProxy = false;
    }

    public PicassoEngine(boolean z) {
        this.isUseProxy = false;
        this.isUseProxy = z;
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.engine.ImageEngine
    public void init(Application application) {
        if (application == null || !this.isUseProxy) {
            return;
        }
        Picasso.setSingletonInstance(new Picasso.Builder(application).downloader(new OurDownloader()).build());
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.engine.ImageEngine
    public void loadThumbnail(Context context, int i, Drawable drawable, ImageView imageView, Uri uri) {
        Logger.m4113i("picasso load:" + uri.getPath());
        Picasso.get().load(uri).placeholder(drawable).resize(i, i).centerCrop().into(imageView);
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.engine.ImageEngine
    public void loadGifThumbnail(Context context, int i, Drawable drawable, ImageView imageView, Uri uri) {
        loadThumbnail(context, i, drawable, imageView, uri);
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.engine.ImageEngine
    public void loadImage(Context context, int i, int i2, ImageView imageView, Uri uri) {
        Logger.m4113i("picasso load:" + uri.getPath());
        Picasso.get().load(uri).resize(i, i2).priority(Picasso.Priority.HIGH).centerInside().into(imageView);
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.engine.ImageEngine
    public void loadGifImage(Context context, int i, int i2, ImageView imageView, Uri uri) {
        Logger.m4113i("picasso load:" + uri.getPath());
        loadImage(context, i, i2, imageView, uri);
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.engine.ImageEngine
    public void load(Context context, ImageView imageView, Uri uri) {
        Logger.m4113i("picasso load:" + uri.getPath());
        Picasso.get().load(uri).into(imageView);
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.engine.ImageEngine
    public void load(Context context, ImageView imageView, String str) {
        Logger.m4113i("picasso load:" + str);
        Picasso.get().load(str).into(imageView);
    }

    /* renamed from: com.gen.mh.webapp_extensions.matisse.engine.impl.PicassoEngine$1 */
    /* loaded from: classes2.dex */
    class C14611 extends Thread {
        final /* synthetic */ Context val$context;
        final /* synthetic */ ImageView val$imageView;
        final /* synthetic */ String val$url;

        C14611(Context context, String str, ImageView imageView) {
            this.val$context = context;
            this.val$url = str;
            this.val$imageView = imageView;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            byte[] downloadImage = Utils.downloadImage(this.val$context, this.val$url, true, true);
            final Bitmap decodeByteArray = BitmapFactory.decodeByteArray(downloadImage, 0, downloadImage.length);
            if (this.val$imageView != null) {
                MainHandler mainHandler = MainHandler.getInstance();
                final ImageView imageView = this.val$imageView;
                mainHandler.post(new Runnable() { // from class: com.gen.mh.webapp_extensions.matisse.engine.impl.-$$Lambda$PicassoEngine$1$09Y8TX1F9y4voMvB-rP2tgvcc-A
                    @Override // java.lang.Runnable
                    public final void run() {
                        imageView.setImageBitmap(decodeByteArray);
                    }
                });
            }
        }
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.engine.ImageEngine
    public void load(Context context, ImageView imageView, String str, boolean z) {
        if (z) {
            new C14611(context, str, imageView).start();
        } else {
            load(context, imageView, str);
        }
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.engine.ImageEngine
    public void load(Context context, ImageView imageView, File file) {
        Logger.m4113i("picasso load:" + file.getPath());
        Picasso.get().load(file).into(imageView);
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.engine.ImageEngine
    public void load(Context context, ImageView imageView, int i) {
        Logger.m4113i("picasso load:" + i);
        Picasso.get().load(i).into(imageView);
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.engine.ImageEngine
    public void load(final Context context, ImageView imageView, String str, final CommonCallBack<Drawable> commonCallBack) {
        Logger.m4113i("picasso load:" + str);
        Picasso.get().load(str).into(new Target() { // from class: com.gen.mh.webapp_extensions.matisse.engine.impl.PicassoEngine.2
            public void onPrepareLoad(Drawable drawable) {
            }

            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
                if (bitmap != null) {
                    BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), bitmap);
                    CommonCallBack commonCallBack2 = commonCallBack;
                    if (commonCallBack2 == null) {
                        return;
                    }
                    commonCallBack2.onResult(bitmapDrawable);
                }
            }

            public void onBitmapFailed(Exception exc, Drawable drawable) {
                CommonCallBack commonCallBack2 = commonCallBack;
                if (commonCallBack2 != null) {
                    commonCallBack2.onFailure(exc);
                }
            }
        });
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.engine.ImageEngine
    public void loadImage(final Context context, String str, final CommonCallBack<Drawable> commonCallBack) {
        Picasso.get().load(str).into(new Target() { // from class: com.gen.mh.webapp_extensions.matisse.engine.impl.PicassoEngine.3
            public void onPrepareLoad(Drawable drawable) {
            }

            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
                if (bitmap != null) {
                    BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), bitmap);
                    CommonCallBack commonCallBack2 = commonCallBack;
                    if (commonCallBack2 == null) {
                        return;
                    }
                    commonCallBack2.onResult(bitmapDrawable);
                }
            }

            public void onBitmapFailed(Exception exc, Drawable drawable) {
                CommonCallBack commonCallBack2 = commonCallBack;
                if (commonCallBack2 != null) {
                    commonCallBack2.onFailure(exc);
                }
            }
        });
    }

    /* renamed from: com.gen.mh.webapp_extensions.matisse.engine.impl.PicassoEngine$4 */
    /* loaded from: classes2.dex */
    class C14644 extends Thread {
        final /* synthetic */ Context val$context;
        final /* synthetic */ CommonCallBack val$loadCallBack;
        final /* synthetic */ String val$url;

        C14644(Context context, String str, CommonCallBack commonCallBack) {
            this.val$context = context;
            this.val$url = str;
            this.val$loadCallBack = commonCallBack;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            byte[] downloadImage = Utils.downloadImage(this.val$context, this.val$url, true, true);
            final Bitmap decodeByteArray = BitmapFactory.decodeByteArray(downloadImage, 0, downloadImage.length);
            MainHandler mainHandler = MainHandler.getInstance();
            final CommonCallBack commonCallBack = this.val$loadCallBack;
            mainHandler.post(new Runnable() { // from class: com.gen.mh.webapp_extensions.matisse.engine.impl.-$$Lambda$PicassoEngine$4$ngv531HU57IY7rCkmVDZHc7GBHU
                @Override // java.lang.Runnable
                public final void run() {
                    CommonCallBack.this.onResult(new BitmapDrawable(decodeByteArray));
                }
            });
        }
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.engine.ImageEngine
    public void loadImage(Context context, String str, CommonCallBack<Drawable> commonCallBack, boolean z) {
        if (z) {
            new C14644(context, str, commonCallBack).start();
        } else {
            loadImage(context, str, commonCallBack);
        }
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.engine.ImageEngine
    public void download(Context context, String str, CommonCallBack<File> commonCallBack) {
        String decode = URLDecoder.decode(str);
        Logger.m4113i("picasso download " + decode);
        Picasso.get().load(Uri.parse(decode)).into(new C14655(commonCallBack));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.gen.mh.webapp_extensions.matisse.engine.impl.PicassoEngine$5 */
    /* loaded from: classes2.dex */
    public class C14655 implements Target {
        final /* synthetic */ CommonCallBack val$downloadResult;

        public void onPrepareLoad(Drawable drawable) {
        }

        C14655(CommonCallBack commonCallBack) {
            this.val$downloadResult = commonCallBack;
        }

        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
            final File file = new File(WebApplication.getInstance().getApplication().getFilesDir(), System.currentTimeMillis() + ".png");
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (this.val$downloadResult != null) {
                MainHandler mainHandler = MainHandler.getInstance();
                final CommonCallBack commonCallBack = this.val$downloadResult;
                mainHandler.post(new Runnable() { // from class: com.gen.mh.webapp_extensions.matisse.engine.impl.-$$Lambda$PicassoEngine$5$xFJH--RmtGY0yQUVOnzexRf_d9c
                    @Override // java.lang.Runnable
                    public final void run() {
                        CommonCallBack.this.onResult(file);
                    }
                });
            }
        }

        public void onBitmapFailed(final Exception exc, Drawable drawable) {
            exc.printStackTrace();
            if (this.val$downloadResult != null) {
                MainHandler mainHandler = MainHandler.getInstance();
                final CommonCallBack commonCallBack = this.val$downloadResult;
                mainHandler.post(new Runnable() { // from class: com.gen.mh.webapp_extensions.matisse.engine.impl.-$$Lambda$PicassoEngine$5$ARl495mB4PoiG2p2Sulm9OlHBb4
                    @Override // java.lang.Runnable
                    public final void run() {
                        CommonCallBack.this.onFailure(exc);
                    }
                });
            }
        }
    }

    /* renamed from: com.gen.mh.webapp_extensions.matisse.engine.impl.PicassoEngine$6 */
    /* loaded from: classes2.dex */
    class C14666 extends Thread {
        final /* synthetic */ Context val$context;
        final /* synthetic */ CommonCallBack val$downloadResult;
        final /* synthetic */ String val$url;

        C14666(Context context, String str, CommonCallBack commonCallBack) {
            this.val$context = context;
            this.val$url = str;
            this.val$downloadResult = commonCallBack;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            byte[] downloadImage = Utils.downloadImage(this.val$context, this.val$url, true, true);
            Bitmap decodeByteArray = BitmapFactory.decodeByteArray(downloadImage, 0, downloadImage.length);
            final File file = new File(WebApplication.getInstance().getApplication().getFilesDir(), System.currentTimeMillis() + ".png");
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                decodeByteArray.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            MainHandler mainHandler = MainHandler.getInstance();
            final CommonCallBack commonCallBack = this.val$downloadResult;
            mainHandler.post(new Runnable() { // from class: com.gen.mh.webapp_extensions.matisse.engine.impl.-$$Lambda$PicassoEngine$6$zt0qWLjgtULLM0Tzi3JsKJJ5bEs
                @Override // java.lang.Runnable
                public final void run() {
                    CommonCallBack.this.onResult(file);
                }
            });
        }
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.engine.ImageEngine
    public void download(Context context, String str, CommonCallBack<File> commonCallBack, boolean z) {
        if (z) {
            new C14666(context, str, commonCallBack).start();
        } else {
            download(context, str, commonCallBack);
        }
    }
}
