package com.tomatolive.library.p136ui.view.widget.matisse;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.CheckResult;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.manager.Lifecycle;
import com.bumptech.glide.manager.RequestManagerTreeNode;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import java.io.File;
import java.net.URL;

/* renamed from: com.tomatolive.library.ui.view.widget.matisse.GlideRequests */
/* loaded from: classes4.dex */
public class GlideRequests extends RequestManager {
    @Override // com.bumptech.glide.RequestManager
    @NonNull
    /* renamed from: addDefaultRequestListener */
    public /* bridge */ /* synthetic */ RequestManager mo6714addDefaultRequestListener(RequestListener requestListener) {
        return mo6714addDefaultRequestListener((RequestListener<Object>) requestListener);
    }

    public GlideRequests(@NonNull Glide glide, @NonNull Lifecycle lifecycle, @NonNull RequestManagerTreeNode requestManagerTreeNode, @NonNull Context context) {
        super(glide, lifecycle, requestManagerTreeNode, context);
    }

    @Override // com.bumptech.glide.RequestManager
    @CheckResult
    @NonNull
    /* renamed from: as  reason: collision with other method in class */
    public <ResourceType> GlideRequest<ResourceType> mo6716as(@NonNull Class<ResourceType> cls) {
        return new GlideRequest<>(this.glide, this, cls, this.context);
    }

    @Override // com.bumptech.glide.RequestManager
    @NonNull
    /* renamed from: applyDefaultRequestOptions  reason: collision with other method in class */
    public synchronized GlideRequests mo6715applyDefaultRequestOptions(@NonNull RequestOptions requestOptions) {
        return (GlideRequests) super.mo6715applyDefaultRequestOptions(requestOptions);
    }

    @Override // com.bumptech.glide.RequestManager
    @NonNull
    /* renamed from: setDefaultRequestOptions  reason: collision with other method in class */
    public synchronized GlideRequests mo6732setDefaultRequestOptions(@NonNull RequestOptions requestOptions) {
        return (GlideRequests) super.mo6732setDefaultRequestOptions(requestOptions);
    }

    @Override // com.bumptech.glide.RequestManager
    @NonNull
    /* renamed from: addDefaultRequestListener  reason: collision with other method in class */
    public GlideRequests mo6714addDefaultRequestListener(RequestListener<Object> requestListener) {
        return (GlideRequests) super.mo6714addDefaultRequestListener(requestListener);
    }

    @Override // com.bumptech.glide.RequestManager
    @CheckResult
    @NonNull
    /* renamed from: asBitmap  reason: collision with other method in class */
    public GlideRequest<Bitmap> mo6717asBitmap() {
        return (GlideRequest) super.mo6717asBitmap();
    }

    @Override // com.bumptech.glide.RequestManager
    @CheckResult
    @NonNull
    /* renamed from: asGif  reason: collision with other method in class */
    public GlideRequest<GifDrawable> mo6720asGif() {
        return (GlideRequest) super.mo6720asGif();
    }

    @Override // com.bumptech.glide.RequestManager
    @CheckResult
    @NonNull
    /* renamed from: asDrawable  reason: collision with other method in class */
    public GlideRequest<Drawable> mo6718asDrawable() {
        return (GlideRequest) super.mo6718asDrawable();
    }

    @Override // com.bumptech.glide.RequestManager, com.bumptech.glide.ModelTypes
    @CheckResult
    @NonNull
    /* renamed from: load  reason: collision with other method in class */
    public RequestBuilder<Drawable> mo6723load(@Nullable Bitmap bitmap) {
        return (GlideRequest) super.mo6723load(bitmap);
    }

    @Override // com.bumptech.glide.RequestManager, com.bumptech.glide.ModelTypes
    @CheckResult
    @NonNull
    /* renamed from: load  reason: collision with other method in class */
    public RequestBuilder<Drawable> mo6724load(@Nullable Drawable drawable) {
        return (GlideRequest) super.mo6724load(drawable);
    }

    @Override // com.bumptech.glide.RequestManager, com.bumptech.glide.ModelTypes
    @CheckResult
    @NonNull
    /* renamed from: load  reason: collision with other method in class */
    public RequestBuilder<Drawable> mo6729load(@Nullable String str) {
        return (GlideRequest) super.mo6729load(str);
    }

    @Override // com.bumptech.glide.RequestManager, com.bumptech.glide.ModelTypes
    @CheckResult
    @NonNull
    /* renamed from: load  reason: collision with other method in class */
    public RequestBuilder<Drawable> mo6725load(@Nullable Uri uri) {
        return (GlideRequest) super.mo6725load(uri);
    }

    @Override // com.bumptech.glide.RequestManager, com.bumptech.glide.ModelTypes
    @CheckResult
    @NonNull
    /* renamed from: load  reason: collision with other method in class */
    public RequestBuilder<Drawable> mo6726load(@Nullable File file) {
        return (GlideRequest) super.mo6726load(file);
    }

    @Override // com.bumptech.glide.RequestManager, com.bumptech.glide.ModelTypes
    @CheckResult
    @NonNull
    /* renamed from: load  reason: collision with other method in class */
    public RequestBuilder<Drawable> mo6727load(@RawRes @DrawableRes @Nullable Integer num) {
        return (GlideRequest) super.mo6727load(num);
    }

    @Override // com.bumptech.glide.RequestManager, com.bumptech.glide.ModelTypes
    @CheckResult
    @Deprecated
    /* renamed from: load  reason: collision with other method in class */
    public RequestBuilder<Drawable> mo6730load(@Nullable URL url) {
        return (GlideRequest) super.mo6730load(url);
    }

    @Override // com.bumptech.glide.RequestManager, com.bumptech.glide.ModelTypes
    @CheckResult
    @NonNull
    /* renamed from: load  reason: collision with other method in class */
    public RequestBuilder<Drawable> mo6731load(@Nullable byte[] bArr) {
        return (GlideRequest) super.mo6731load(bArr);
    }

    @Override // com.bumptech.glide.RequestManager, com.bumptech.glide.ModelTypes
    @CheckResult
    @NonNull
    /* renamed from: load  reason: collision with other method in class */
    public RequestBuilder<Drawable> mo6728load(@Nullable Object obj) {
        return (GlideRequest) super.mo6728load(obj);
    }

    @Override // com.bumptech.glide.RequestManager
    @CheckResult
    @NonNull
    /* renamed from: downloadOnly  reason: collision with other method in class */
    public GlideRequest<File> mo6722downloadOnly() {
        return (GlideRequest) super.mo6722downloadOnly();
    }

    @Override // com.bumptech.glide.RequestManager
    @CheckResult
    @NonNull
    /* renamed from: download  reason: collision with other method in class */
    public GlideRequest<File> mo6721download(@Nullable Object obj) {
        return (GlideRequest) super.mo6721download(obj);
    }

    @Override // com.bumptech.glide.RequestManager
    @CheckResult
    @NonNull
    /* renamed from: asFile  reason: collision with other method in class */
    public GlideRequest<File> mo6719asFile() {
        return (GlideRequest) super.mo6719asFile();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.bumptech.glide.RequestManager
    public void setRequestOptions(@NonNull RequestOptions requestOptions) {
        if (requestOptions instanceof GlideOptions) {
            super.setRequestOptions(requestOptions);
        } else {
            super.setRequestOptions(new GlideOptions().mo6653apply((BaseRequestOptions<?>) requestOptions));
        }
    }
}
