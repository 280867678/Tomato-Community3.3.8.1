package com.gen.p059mh.webapp_extensions.matisse.engine.impl;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.gen.p059mh.webapp_extensions.WebApplication;
import com.gen.p059mh.webapp_extensions.matisse.engine.ImageEngine;
import com.gen.p059mh.webapps.utils.Logger;
import com.gen.p059mh.webapps.utils.Utils;
import com.p076mh.webappStart.android_plugin_impl.callback.CommonCallBack;
import com.p076mh.webappStart.util.MainHandler;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/* renamed from: com.gen.mh.webapp_extensions.matisse.engine.impl.GlideEngine */
/* loaded from: classes2.dex */
public class GlideEngine implements ImageEngine {
    boolean isUseProxy;

    private void checkCache(String str) {
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.engine.ImageEngine
    public boolean supportAnimatedGif() {
        return true;
    }

    public GlideEngine() {
        this.isUseProxy = false;
    }

    public GlideEngine(boolean z) {
        this.isUseProxy = false;
        this.isUseProxy = z;
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.engine.ImageEngine
    public void init(Application application) {
        if (application == null || !this.isUseProxy) {
            return;
        }
        Glide.get(application).getRegistry().replace(GlideUrl.class, InputStream.class, new OurLoadFactory());
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.engine.ImageEngine
    public void loadThumbnail(Context context, int i, Drawable drawable, ImageView imageView, Uri uri) {
        Logger.m4113i("glide load::" + uri.getPath());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.mo6696placeholder(drawable).mo6694override(i, i).mo6654centerCrop();
        Glide.with(context).mo6717asBitmap().mo6725load(uri).mo6653apply((BaseRequestOptions<?>) requestOptions).into(imageView);
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.engine.ImageEngine
    public void loadGifThumbnail(Context context, int i, Drawable drawable, ImageView imageView, Uri uri) {
        Logger.m4113i("glide load:" + uri.getPath());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.mo6696placeholder(drawable).mo6694override(i, i).mo6654centerCrop();
        Glide.with(context).mo6717asBitmap().mo6725load(uri).mo6653apply((BaseRequestOptions<?>) requestOptions).into(imageView);
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.engine.ImageEngine
    public void loadImage(Context context, int i, int i2, ImageView imageView, Uri uri) {
        Logger.m4113i("glide load:" + uri.getPath());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.mo6694override(i, i2).mo6654centerCrop().mo6697priority(Priority.HIGH).mo6672fitCenter();
        Glide.with(context).mo6725load(uri).mo6653apply((BaseRequestOptions<?>) requestOptions).into(imageView);
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.engine.ImageEngine
    public void loadGifImage(Context context, int i, int i2, ImageView imageView, Uri uri) {
        Logger.m4113i("glide load:" + uri.getPath());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.mo6694override(i, i2).mo6654centerCrop().mo6697priority(Priority.HIGH).mo6672fitCenter();
        Glide.with(context).mo6720asGif().mo6725load(uri).mo6653apply((BaseRequestOptions<?>) requestOptions).into(imageView);
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.engine.ImageEngine
    public void load(Context context, ImageView imageView, Uri uri) {
        Logger.m4113i("glide load:" + uri.getPath());
        Glide.with(context).mo6725load(uri).into(imageView);
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.engine.ImageEngine
    public void load(Context context, ImageView imageView, String str) {
        Logger.m4113i("glide load:" + str);
        Glide.with(context).mo6729load(str).into(imageView);
    }

    /* renamed from: com.gen.mh.webapp_extensions.matisse.engine.impl.GlideEngine$1 */
    /* loaded from: classes2.dex */
    class C14521 extends Thread {
        final /* synthetic */ Context val$context;
        final /* synthetic */ ImageView val$imageView;
        final /* synthetic */ String val$url;

        C14521(Context context, String str, ImageView imageView) {
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
                mainHandler.post(new Runnable() { // from class: com.gen.mh.webapp_extensions.matisse.engine.impl.-$$Lambda$GlideEngine$1$3b1Jy7hjK_TaCBW6BWSGkB_qZfE
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
            new C14521(context, str, imageView).start();
        } else {
            load(context, imageView, str);
        }
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.engine.ImageEngine
    public void load(Context context, ImageView imageView, File file) {
        Logger.m4113i("glide load:" + file.getPath());
        Glide.with(context).mo6726load(file).into(imageView);
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.engine.ImageEngine
    public void load(Context context, ImageView imageView, int i) {
        Glide.with(context).mo6727load(Integer.valueOf(i)).into(imageView);
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.engine.ImageEngine
    public void load(Context context, ImageView imageView, String str, final CommonCallBack<Drawable> commonCallBack) {
        Logger.m4113i("glide load:" + str);
        Glide.with(context).mo6729load(str).into((RequestBuilder<Drawable>) new SimpleTarget<Drawable>() { // from class: com.gen.mh.webapp_extensions.matisse.engine.impl.GlideEngine.2
            @Override // com.bumptech.glide.request.target.Target
            public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object obj, @Nullable Transition transition) {
                onResourceReady((Drawable) obj, (Transition<? super Drawable>) transition);
            }

            public void onResourceReady(@NonNull Drawable drawable, @Nullable Transition<? super Drawable> transition) {
                CommonCallBack commonCallBack2;
                if (drawable == null || (commonCallBack2 = commonCallBack) == null) {
                    return;
                }
                commonCallBack2.onResult(drawable);
            }
        });
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.engine.ImageEngine
    public void loadImage(Context context, String str, final CommonCallBack<Drawable> commonCallBack) {
        Glide.with(context).mo6729load(str).into((RequestBuilder<Drawable>) new SimpleTarget<Drawable>() { // from class: com.gen.mh.webapp_extensions.matisse.engine.impl.GlideEngine.3
            @Override // com.bumptech.glide.request.target.Target
            public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object obj, @Nullable Transition transition) {
                onResourceReady((Drawable) obj, (Transition<? super Drawable>) transition);
            }

            public void onResourceReady(@NonNull Drawable drawable, @Nullable Transition<? super Drawable> transition) {
                CommonCallBack commonCallBack2;
                if (drawable == null || (commonCallBack2 = commonCallBack) == null) {
                    return;
                }
                commonCallBack2.onResult(drawable);
            }
        });
    }

    /* renamed from: com.gen.mh.webapp_extensions.matisse.engine.impl.GlideEngine$4 */
    /* loaded from: classes2.dex */
    class C14554 extends Thread {
        final /* synthetic */ Context val$context;
        final /* synthetic */ CommonCallBack val$loadCallBack;
        final /* synthetic */ String val$url;

        C14554(Context context, String str, CommonCallBack commonCallBack) {
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
            mainHandler.post(new Runnable() { // from class: com.gen.mh.webapp_extensions.matisse.engine.impl.-$$Lambda$GlideEngine$4$zg3e1-_nrod9pdvW6zyG596OHIg
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
            new C14554(context, str, commonCallBack).start();
        } else {
            loadImage(context, str, commonCallBack);
        }
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.engine.ImageEngine
    public void load(Context context, String str, final CommonCallBack<byte[]> commonCallBack) {
        Glide.with(context).mo6729load(str).into((RequestBuilder<Drawable>) new SimpleTarget<Drawable>() { // from class: com.gen.mh.webapp_extensions.matisse.engine.impl.GlideEngine.5
            @Override // com.bumptech.glide.request.target.Target
            public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object obj, @Nullable Transition transition) {
                onResourceReady((Drawable) obj, (Transition<? super Drawable>) transition);
            }

            public void onResourceReady(@NonNull Drawable drawable, @Nullable Transition<? super Drawable> transition) {
                if (drawable != null) {
                    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    commonCallBack.onResult(byteArrayOutputStream.toByteArray());
                }
            }
        });
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.engine.ImageEngine
    @SuppressLint({"CheckResult"})
    public void download(Context context, String str, CommonCallBack<File> commonCallBack) {
        Logger.m4113i("glide load:" + str);
        Glide.with(context).mo6729load(str).downloadOnly(new C14576(commonCallBack));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.gen.mh.webapp_extensions.matisse.engine.impl.GlideEngine$6 */
    /* loaded from: classes2.dex */
    public class C14576 extends SimpleTarget<File> {
        final /* synthetic */ CommonCallBack val$downloadResult;

        C14576(CommonCallBack commonCallBack) {
            this.val$downloadResult = commonCallBack;
        }

        @Override // com.bumptech.glide.request.target.Target
        public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object obj, @Nullable Transition transition) {
            onResourceReady((File) obj, (Transition<? super File>) transition);
        }

        public void onResourceReady(@NonNull final File file, @Nullable Transition<? super File> transition) {
            if (this.val$downloadResult != null) {
                MainHandler mainHandler = MainHandler.getInstance();
                final CommonCallBack commonCallBack = this.val$downloadResult;
                mainHandler.post(new Runnable() { // from class: com.gen.mh.webapp_extensions.matisse.engine.impl.-$$Lambda$GlideEngine$6$3qmXuzWeV3yzuYKPu47uWwAL4UM
                    @Override // java.lang.Runnable
                    public final void run() {
                        CommonCallBack.this.onResult(file);
                    }
                });
            }
        }
    }

    /* renamed from: com.gen.mh.webapp_extensions.matisse.engine.impl.GlideEngine$7 */
    /* loaded from: classes2.dex */
    class C14587 extends Thread {
        final /* synthetic */ Context val$context;
        final /* synthetic */ CommonCallBack val$downloadResult;
        final /* synthetic */ String val$url;

        C14587(Context context, String str, CommonCallBack commonCallBack) {
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
            mainHandler.post(new Runnable() { // from class: com.gen.mh.webapp_extensions.matisse.engine.impl.-$$Lambda$GlideEngine$7$zPAPCJBIcaOHh-P7gxkGLQQ7ZH4
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
            new C14587(context, str, commonCallBack).start();
        } else {
            download(context, str, commonCallBack);
        }
    }
}
