package com.tomatolive.library.p136ui.view.widget.matisse;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.CheckResult;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.TransitionOptions;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestListener;
import java.io.File;
import java.net.URL;

/* renamed from: com.tomatolive.library.ui.view.widget.matisse.GlideRequest */
/* loaded from: classes4.dex */
public class GlideRequest<TranscodeType> extends RequestBuilder<TranscodeType> implements Cloneable {
    @Override // com.bumptech.glide.RequestBuilder, com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: apply */
    public /* bridge */ /* synthetic */ RequestBuilder mo6653apply(@NonNull BaseRequestOptions baseRequestOptions) {
        return mo6653apply((BaseRequestOptions<?>) baseRequestOptions);
    }

    @Override // com.bumptech.glide.RequestBuilder, com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: apply  reason: collision with other method in class */
    public /* bridge */ /* synthetic */ BaseRequestOptions mo6653apply(@NonNull BaseRequestOptions baseRequestOptions) {
        return mo6653apply((BaseRequestOptions<?>) baseRequestOptions);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: decode */
    public /* bridge */ /* synthetic */ BaseRequestOptions mo6659decode(@NonNull Class cls) {
        return mo6659decode((Class<?>) cls);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: optionalTransform */
    public /* bridge */ /* synthetic */ BaseRequestOptions mo6691optionalTransform(@NonNull Transformation transformation) {
        return mo6691optionalTransform((Transformation<Bitmap>) transformation);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: set */
    public /* bridge */ /* synthetic */ BaseRequestOptions mo6698set(@NonNull Option option, @NonNull Object obj) {
        return mo6698set((Option<Option>) option, (Option) obj);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: transform */
    public /* bridge */ /* synthetic */ BaseRequestOptions mo6707transform(@NonNull Transformation transformation) {
        return mo6707transform((Transformation<Bitmap>) transformation);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: transform */
    public /* bridge */ /* synthetic */ BaseRequestOptions mo6709transform(@NonNull Transformation[] transformationArr) {
        return mo6709transform((Transformation<Bitmap>[]) transformationArr);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    @Deprecated
    /* renamed from: transforms */
    public /* bridge */ /* synthetic */ BaseRequestOptions mo6710transforms(@NonNull Transformation[] transformationArr) {
        return mo6710transforms((Transformation<Bitmap>[]) transformationArr);
    }

    GlideRequest(@NonNull Class<TranscodeType> cls, @NonNull RequestBuilder<?> requestBuilder) {
        super(cls, requestBuilder);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public GlideRequest(@NonNull Glide glide, @NonNull RequestManager requestManager, @NonNull Class<TranscodeType> cls, @NonNull Context context) {
        super(glide, requestManager, cls, context);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.bumptech.glide.RequestBuilder
    @CheckResult
    @NonNull
    /* renamed from: getDownloadOnlyRequest  reason: collision with other method in class */
    public GlideRequest<File> mo6675getDownloadOnlyRequest() {
        return new GlideRequest(File.class, this).mo6653apply((BaseRequestOptions<?>) RequestBuilder.DOWNLOAD_ONLY_OPTIONS);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: sizeMultiplier  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6700sizeMultiplier(@FloatRange(from = 0.0d, m5592to = 1.0d) float f) {
        return (GlideRequest) super.mo6700sizeMultiplier(f);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: useUnlimitedSourceGeneratorsPool  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6713useUnlimitedSourceGeneratorsPool(boolean z) {
        return (GlideRequest) super.mo6713useUnlimitedSourceGeneratorsPool(z);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: useAnimationPool  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6712useAnimationPool(boolean z) {
        return (GlideRequest) super.mo6712useAnimationPool(z);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: onlyRetrieveFromCache  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6686onlyRetrieveFromCache(boolean z) {
        return (GlideRequest) super.mo6686onlyRetrieveFromCache(z);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: diskCacheStrategy  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6661diskCacheStrategy(@NonNull DiskCacheStrategy diskCacheStrategy) {
        return (GlideRequest) super.mo6661diskCacheStrategy(diskCacheStrategy);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: priority  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6697priority(@NonNull Priority priority) {
        return (GlideRequest) super.mo6697priority(priority);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: placeholder  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6696placeholder(@Nullable Drawable drawable) {
        return (GlideRequest) super.mo6696placeholder(drawable);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: placeholder  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6695placeholder(@DrawableRes int i) {
        return (GlideRequest) super.mo6695placeholder(i);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: fallback  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6671fallback(@Nullable Drawable drawable) {
        return (GlideRequest) super.mo6671fallback(drawable);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: fallback  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6670fallback(@DrawableRes int i) {
        return (GlideRequest) super.mo6670fallback(i);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: error  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6668error(@Nullable Drawable drawable) {
        return (GlideRequest) super.mo6668error(drawable);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: error  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6667error(@DrawableRes int i) {
        return (GlideRequest) super.mo6667error(i);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: theme  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6702theme(@Nullable Resources.Theme theme) {
        return (GlideRequest) super.mo6702theme(theme);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: skipMemoryCache  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6701skipMemoryCache(boolean z) {
        return (GlideRequest) super.mo6701skipMemoryCache(z);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: override  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6694override(int i, int i2) {
        return (GlideRequest) super.mo6694override(i, i2);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: override  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6693override(int i) {
        return (GlideRequest) super.mo6693override(i);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: signature  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6699signature(@NonNull Key key) {
        return (GlideRequest) super.mo6699signature(key);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: set  reason: collision with other method in class */
    public <Y> GlideRequest<TranscodeType> mo6698set(@NonNull Option<Y> option, @NonNull Y y) {
        return (GlideRequest) super.mo6698set((Option<Option<Y>>) option, (Option<Y>) y);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: decode  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6659decode(@NonNull Class<?> cls) {
        return (GlideRequest) super.mo6659decode(cls);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: encodeFormat  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6665encodeFormat(@NonNull Bitmap.CompressFormat compressFormat) {
        return (GlideRequest) super.mo6665encodeFormat(compressFormat);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: encodeQuality  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6666encodeQuality(@IntRange(from = 0, m5591to = 100) int i) {
        return (GlideRequest) super.mo6666encodeQuality(i);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: frame  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6674frame(@IntRange(from = 0) long j) {
        return (GlideRequest) super.mo6674frame(j);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: format  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6673format(@NonNull DecodeFormat decodeFormat) {
        return (GlideRequest) super.mo6673format(decodeFormat);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: disallowHardwareConfig  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6660disallowHardwareConfig() {
        return (GlideRequest) super.mo6660disallowHardwareConfig();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: downsample  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6664downsample(@NonNull DownsampleStrategy downsampleStrategy) {
        return (GlideRequest) super.mo6664downsample(downsampleStrategy);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: timeout  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6706timeout(@IntRange(from = 0) int i) {
        return (GlideRequest) super.mo6706timeout(i);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: optionalCenterCrop  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6687optionalCenterCrop() {
        return (GlideRequest) super.mo6687optionalCenterCrop();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: centerCrop  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6654centerCrop() {
        return (GlideRequest) super.mo6654centerCrop();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: optionalFitCenter  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6690optionalFitCenter() {
        return (GlideRequest) super.mo6690optionalFitCenter();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: fitCenter  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6672fitCenter() {
        return (GlideRequest) super.mo6672fitCenter();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: optionalCenterInside  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6688optionalCenterInside() {
        return (GlideRequest) super.mo6688optionalCenterInside();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: centerInside  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6655centerInside() {
        return (GlideRequest) super.mo6655centerInside();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: optionalCircleCrop  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6689optionalCircleCrop() {
        return (GlideRequest) super.mo6689optionalCircleCrop();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: circleCrop  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6656circleCrop() {
        return (GlideRequest) super.mo6656circleCrop();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: transform  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6707transform(@NonNull Transformation<Bitmap> transformation) {
        return (GlideRequest) super.mo6707transform(transformation);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: transform  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6709transform(@NonNull Transformation<Bitmap>... transformationArr) {
        return (GlideRequest) super.mo6709transform(transformationArr);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    @Deprecated
    /* renamed from: transforms  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6710transforms(@NonNull Transformation<Bitmap>... transformationArr) {
        return (GlideRequest) super.mo6710transforms(transformationArr);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: optionalTransform  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6691optionalTransform(@NonNull Transformation<Bitmap> transformation) {
        return (GlideRequest) super.mo6691optionalTransform(transformation);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: optionalTransform  reason: collision with other method in class */
    public <Y> GlideRequest<TranscodeType> mo6692optionalTransform(@NonNull Class<Y> cls, @NonNull Transformation<Y> transformation) {
        return (GlideRequest) super.mo6692optionalTransform((Class) cls, (Transformation) transformation);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: transform  reason: collision with other method in class */
    public <Y> GlideRequest<TranscodeType> mo6708transform(@NonNull Class<Y> cls, @NonNull Transformation<Y> transformation) {
        return (GlideRequest) super.mo6708transform((Class) cls, (Transformation) transformation);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: dontTransform  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6663dontTransform() {
        return (GlideRequest) super.mo6663dontTransform();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: dontAnimate  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6662dontAnimate() {
        return (GlideRequest) super.mo6662dontAnimate();
    }

    @Override // com.bumptech.glide.RequestBuilder, com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: apply */
    public GlideRequest<TranscodeType> mo6653apply(@NonNull BaseRequestOptions<?> baseRequestOptions) {
        return (GlideRequest) super.mo6653apply(baseRequestOptions);
    }

    @Override // com.bumptech.glide.RequestBuilder
    @CheckResult
    @NonNull
    /* renamed from: transition  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6711transition(@NonNull TransitionOptions<?, ? super TranscodeType> transitionOptions) {
        return (GlideRequest) super.mo6711transition((TransitionOptions) transitionOptions);
    }

    @Override // com.bumptech.glide.RequestBuilder
    @CheckResult
    @NonNull
    /* renamed from: listener  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6676listener(@Nullable RequestListener<TranscodeType> requestListener) {
        return (GlideRequest) super.mo6676listener((RequestListener) requestListener);
    }

    @Override // com.bumptech.glide.RequestBuilder
    @CheckResult
    @NonNull
    /* renamed from: addListener  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6652addListener(@Nullable RequestListener<TranscodeType> requestListener) {
        return (GlideRequest) super.mo6652addListener((RequestListener) requestListener);
    }

    @Override // com.bumptech.glide.RequestBuilder
    @NonNull
    /* renamed from: error  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6669error(@Nullable RequestBuilder<TranscodeType> requestBuilder) {
        return (GlideRequest) super.mo6669error((RequestBuilder) requestBuilder);
    }

    @Override // com.bumptech.glide.RequestBuilder
    @CheckResult
    @NonNull
    /* renamed from: thumbnail  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6704thumbnail(@Nullable RequestBuilder<TranscodeType> requestBuilder) {
        return (GlideRequest) super.mo6704thumbnail((RequestBuilder) requestBuilder);
    }

    @Override // com.bumptech.glide.RequestBuilder
    @SafeVarargs
    @CheckResult
    @NonNull
    /* renamed from: thumbnail  reason: collision with other method in class */
    public final GlideRequest<TranscodeType> mo6705thumbnail(@Nullable RequestBuilder<TranscodeType>... requestBuilderArr) {
        return (GlideRequest) super.mo6705thumbnail((RequestBuilder[]) requestBuilderArr);
    }

    @Override // com.bumptech.glide.RequestBuilder
    @CheckResult
    @NonNull
    /* renamed from: thumbnail  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6703thumbnail(float f) {
        return (GlideRequest) super.mo6703thumbnail(f);
    }

    @Override // com.bumptech.glide.RequestBuilder, com.bumptech.glide.ModelTypes
    @CheckResult
    @NonNull
    /* renamed from: load  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6728load(@Nullable Object obj) {
        return (GlideRequest) super.mo6728load(obj);
    }

    @Override // com.bumptech.glide.RequestBuilder, com.bumptech.glide.ModelTypes
    @CheckResult
    @NonNull
    /* renamed from: load  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6723load(@Nullable Bitmap bitmap) {
        return (GlideRequest) super.mo6723load(bitmap);
    }

    @Override // com.bumptech.glide.RequestBuilder, com.bumptech.glide.ModelTypes
    @CheckResult
    @NonNull
    /* renamed from: load  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6724load(@Nullable Drawable drawable) {
        return (GlideRequest) super.mo6724load(drawable);
    }

    @Override // com.bumptech.glide.RequestBuilder, com.bumptech.glide.ModelTypes
    @CheckResult
    @NonNull
    /* renamed from: load  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6729load(@Nullable String str) {
        return (GlideRequest) super.mo6729load(str);
    }

    @Override // com.bumptech.glide.RequestBuilder, com.bumptech.glide.ModelTypes
    @CheckResult
    @NonNull
    /* renamed from: load  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6725load(@Nullable Uri uri) {
        return (GlideRequest) super.mo6725load(uri);
    }

    @Override // com.bumptech.glide.RequestBuilder, com.bumptech.glide.ModelTypes
    @CheckResult
    @NonNull
    /* renamed from: load  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6726load(@Nullable File file) {
        return (GlideRequest) super.mo6726load(file);
    }

    @Override // com.bumptech.glide.RequestBuilder, com.bumptech.glide.ModelTypes
    @CheckResult
    @NonNull
    /* renamed from: load  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6727load(@RawRes @DrawableRes @Nullable Integer num) {
        return (GlideRequest) super.mo6727load(num);
    }

    @Override // com.bumptech.glide.RequestBuilder, com.bumptech.glide.ModelTypes
    @CheckResult
    @Deprecated
    /* renamed from: load  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6730load(@Nullable URL url) {
        return (GlideRequest) super.mo6730load(url);
    }

    @Override // com.bumptech.glide.RequestBuilder, com.bumptech.glide.ModelTypes
    @CheckResult
    @NonNull
    /* renamed from: load  reason: collision with other method in class */
    public GlideRequest<TranscodeType> mo6731load(@Nullable byte[] bArr) {
        return (GlideRequest) super.mo6731load(bArr);
    }

    @Override // com.bumptech.glide.RequestBuilder, com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    /* renamed from: clone */
    public GlideRequest<TranscodeType> mo6658clone() {
        return (GlideRequest) super.mo6658clone();
    }
}
