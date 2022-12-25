package com.tomatolive.library.p136ui.view.widget.matisse.engine.impl;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import com.bumptech.glide.Priority;
import com.tomatolive.library.p136ui.view.widget.matisse.GlideApp;
import com.tomatolive.library.p136ui.view.widget.matisse.engine.ImageEngine;

/* renamed from: com.tomatolive.library.ui.view.widget.matisse.engine.impl.GlideEngine */
/* loaded from: classes4.dex */
public class GlideEngine implements ImageEngine {
    @Override // com.tomatolive.library.p136ui.view.widget.matisse.engine.ImageEngine
    public boolean supportAnimatedGif() {
        return true;
    }

    @Override // com.tomatolive.library.p136ui.view.widget.matisse.engine.ImageEngine
    public void loadThumbnail(Context context, int i, Drawable drawable, ImageView imageView, Uri uri) {
        GlideApp.with(context).mo6717asBitmap().mo6725load(uri).mo6696placeholder(drawable).mo6694override(i, i).mo6654centerCrop().into(imageView);
    }

    @Override // com.tomatolive.library.p136ui.view.widget.matisse.engine.ImageEngine
    public void loadGifThumbnail(Context context, int i, Drawable drawable, ImageView imageView, Uri uri) {
        GlideApp.with(context).mo6717asBitmap().mo6725load(uri).mo6696placeholder(drawable).mo6694override(i, i).mo6654centerCrop().into(imageView);
    }

    @Override // com.tomatolive.library.p136ui.view.widget.matisse.engine.ImageEngine
    public void loadImage(Context context, int i, int i2, ImageView imageView, Uri uri) {
        GlideApp.with(context).mo6717asBitmap().mo6725load(uri).mo6694override(i, i2).mo6697priority(Priority.HIGH).mo6672fitCenter().into(imageView);
    }

    @Override // com.tomatolive.library.p136ui.view.widget.matisse.engine.ImageEngine
    public void loadGifImage(Context context, int i, int i2, ImageView imageView, Uri uri) {
        GlideApp.with(context).mo6720asGif().mo6725load(uri).mo6694override(i, i2).mo6697priority(Priority.HIGH).into(imageView);
    }
}
