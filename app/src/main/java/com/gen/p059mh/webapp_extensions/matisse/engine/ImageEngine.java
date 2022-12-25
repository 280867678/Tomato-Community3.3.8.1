package com.gen.p059mh.webapp_extensions.matisse.engine;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import com.p076mh.webappStart.android_plugin_impl.callback.CommonCallBack;
import java.io.File;

/* renamed from: com.gen.mh.webapp_extensions.matisse.engine.ImageEngine */
/* loaded from: classes2.dex */
public interface ImageEngine {
    void download(Context context, String str, CommonCallBack<File> commonCallBack);

    void download(Context context, String str, CommonCallBack<File> commonCallBack, boolean z);

    void init(Application application);

    void load(Context context, ImageView imageView, int i);

    void load(Context context, ImageView imageView, Uri uri);

    void load(Context context, ImageView imageView, File file);

    void load(Context context, ImageView imageView, String str);

    void load(Context context, ImageView imageView, String str, CommonCallBack<Drawable> commonCallBack);

    void load(Context context, ImageView imageView, String str, boolean z);

    void load(Context context, String str, CommonCallBack<byte[]> commonCallBack);

    void loadGifImage(Context context, int i, int i2, ImageView imageView, Uri uri);

    void loadGifThumbnail(Context context, int i, Drawable drawable, ImageView imageView, Uri uri);

    void loadImage(Context context, int i, int i2, ImageView imageView, Uri uri);

    void loadImage(Context context, String str, CommonCallBack<Drawable> commonCallBack);

    void loadImage(Context context, String str, CommonCallBack<Drawable> commonCallBack, boolean z);

    void loadThumbnail(Context context, int i, Drawable drawable, ImageView imageView, Uri uri);

    boolean supportAnimatedGif();
}
