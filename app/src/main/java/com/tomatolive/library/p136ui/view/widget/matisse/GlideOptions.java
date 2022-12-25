package com.tomatolive.library.p136ui.view.widget.matisse;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.CheckResult;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;

/* renamed from: com.tomatolive.library.ui.view.widget.matisse.GlideOptions */
/* loaded from: classes4.dex */
public final class GlideOptions extends RequestOptions implements Cloneable {
    private static GlideOptions centerCropTransform2;
    private static GlideOptions centerInsideTransform1;
    private static GlideOptions circleCropTransform3;
    private static GlideOptions fitCenterTransform0;
    private static GlideOptions noAnimation5;
    private static GlideOptions noTransformation4;

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: apply */
    public /* bridge */ /* synthetic */ RequestOptions mo6653apply(@NonNull BaseRequestOptions baseRequestOptions) {
        return mo6653apply((BaseRequestOptions<?>) baseRequestOptions);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: decode */
    public /* bridge */ /* synthetic */ RequestOptions mo6659decode(@NonNull Class cls) {
        return mo6659decode((Class<?>) cls);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: optionalTransform */
    public /* bridge */ /* synthetic */ RequestOptions mo6691optionalTransform(@NonNull Transformation transformation) {
        return mo6691optionalTransform((Transformation<Bitmap>) transformation);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: set */
    public /* bridge */ /* synthetic */ RequestOptions mo6698set(@NonNull Option option, @NonNull Object obj) {
        return mo6698set((Option<Option>) option, (Option) obj);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: transform */
    public /* bridge */ /* synthetic */ RequestOptions mo6707transform(@NonNull Transformation transformation) {
        return mo6707transform((Transformation<Bitmap>) transformation);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @SafeVarargs
    @CheckResult
    @NonNull
    /* renamed from: transform */
    public /* bridge */ /* synthetic */ RequestOptions mo6709transform(@NonNull Transformation[] transformationArr) {
        return mo6709transform((Transformation<Bitmap>[]) transformationArr);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @Deprecated
    @SafeVarargs
    @CheckResult
    /* renamed from: transforms */
    public /* bridge */ /* synthetic */ RequestOptions mo6710transforms(@NonNull Transformation[] transformationArr) {
        return mo6710transforms((Transformation<Bitmap>[]) transformationArr);
    }

    @CheckResult
    @NonNull
    public static GlideOptions sizeMultiplierOf(@FloatRange(from = 0.0d, m5592to = 1.0d) float f) {
        return new GlideOptions().mo6700sizeMultiplier(f);
    }

    @CheckResult
    @NonNull
    public static GlideOptions diskCacheStrategyOf(@NonNull DiskCacheStrategy diskCacheStrategy) {
        return new GlideOptions().mo6661diskCacheStrategy(diskCacheStrategy);
    }

    @CheckResult
    @NonNull
    public static GlideOptions priorityOf(@NonNull Priority priority) {
        return new GlideOptions().mo6697priority(priority);
    }

    @CheckResult
    @NonNull
    public static GlideOptions placeholderOf(@Nullable Drawable drawable) {
        return new GlideOptions().mo6696placeholder(drawable);
    }

    @CheckResult
    @NonNull
    public static GlideOptions placeholderOf(@DrawableRes int i) {
        return new GlideOptions().mo6695placeholder(i);
    }

    @CheckResult
    @NonNull
    public static GlideOptions errorOf(@Nullable Drawable drawable) {
        return new GlideOptions().mo6668error(drawable);
    }

    @CheckResult
    @NonNull
    public static GlideOptions errorOf(@DrawableRes int i) {
        return new GlideOptions().mo6667error(i);
    }

    @CheckResult
    @NonNull
    public static GlideOptions skipMemoryCacheOf(boolean z) {
        return new GlideOptions().mo6701skipMemoryCache(z);
    }

    @CheckResult
    @NonNull
    public static GlideOptions overrideOf(@IntRange(from = 0) int i, @IntRange(from = 0) int i2) {
        return new GlideOptions().mo6694override(i, i2);
    }

    @CheckResult
    @NonNull
    public static GlideOptions overrideOf(@IntRange(from = 0) int i) {
        return new GlideOptions().mo6693override(i);
    }

    @CheckResult
    @NonNull
    public static GlideOptions signatureOf(@NonNull Key key) {
        return new GlideOptions().mo6699signature(key);
    }

    @CheckResult
    @NonNull
    public static GlideOptions fitCenterTransform() {
        if (fitCenterTransform0 == null) {
            fitCenterTransform0 = new GlideOptions().mo6672fitCenter().autoClone();
        }
        return fitCenterTransform0;
    }

    @CheckResult
    @NonNull
    public static GlideOptions centerInsideTransform() {
        if (centerInsideTransform1 == null) {
            centerInsideTransform1 = new GlideOptions().mo6655centerInside().autoClone();
        }
        return centerInsideTransform1;
    }

    @CheckResult
    @NonNull
    public static GlideOptions centerCropTransform() {
        if (centerCropTransform2 == null) {
            centerCropTransform2 = new GlideOptions().mo6654centerCrop().autoClone();
        }
        return centerCropTransform2;
    }

    @CheckResult
    @NonNull
    public static GlideOptions circleCropTransform() {
        if (circleCropTransform3 == null) {
            circleCropTransform3 = new GlideOptions().mo6656circleCrop().autoClone();
        }
        return circleCropTransform3;
    }

    @CheckResult
    @NonNull
    public static GlideOptions bitmapTransform(@NonNull Transformation<Bitmap> transformation) {
        return new GlideOptions().mo6707transform(transformation);
    }

    @CheckResult
    @NonNull
    public static GlideOptions noTransformation() {
        if (noTransformation4 == null) {
            noTransformation4 = new GlideOptions().mo6663dontTransform().autoClone();
        }
        return noTransformation4;
    }

    @CheckResult
    @NonNull
    public static <T> GlideOptions option(@NonNull Option<T> option, @NonNull T t) {
        return new GlideOptions().mo6698set((Option<Option<T>>) option, (Option<T>) t);
    }

    @CheckResult
    @NonNull
    public static GlideOptions decodeTypeOf(@NonNull Class<?> cls) {
        return new GlideOptions().mo6659decode(cls);
    }

    @CheckResult
    @NonNull
    public static GlideOptions formatOf(@NonNull DecodeFormat decodeFormat) {
        return new GlideOptions().mo6673format(decodeFormat);
    }

    @CheckResult
    @NonNull
    public static GlideOptions frameOf(@IntRange(from = 0) long j) {
        return new GlideOptions().mo6674frame(j);
    }

    @CheckResult
    @NonNull
    public static GlideOptions downsampleOf(@NonNull DownsampleStrategy downsampleStrategy) {
        return new GlideOptions().mo6664downsample(downsampleStrategy);
    }

    @CheckResult
    @NonNull
    public static GlideOptions timeoutOf(@IntRange(from = 0) int i) {
        return new GlideOptions().mo6706timeout(i);
    }

    @CheckResult
    @NonNull
    public static GlideOptions encodeQualityOf(@IntRange(from = 0, m5591to = 100) int i) {
        return new GlideOptions().mo6666encodeQuality(i);
    }

    @CheckResult
    @NonNull
    public static GlideOptions encodeFormatOf(@NonNull Bitmap.CompressFormat compressFormat) {
        return new GlideOptions().mo6665encodeFormat(compressFormat);
    }

    @CheckResult
    @NonNull
    public static GlideOptions noAnimation() {
        if (noAnimation5 == null) {
            noAnimation5 = new GlideOptions().mo6662dontAnimate().autoClone();
        }
        return noAnimation5;
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: sizeMultiplier  reason: avoid collision after fix types in other method */
    public RequestOptions mo6700sizeMultiplier(@FloatRange(from = 0.0d, m5592to = 1.0d) float f) {
        return (GlideOptions) super.mo6700sizeMultiplier(f);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: useUnlimitedSourceGeneratorsPool  reason: avoid collision after fix types in other method */
    public RequestOptions mo6713useUnlimitedSourceGeneratorsPool(boolean z) {
        return (GlideOptions) super.mo6713useUnlimitedSourceGeneratorsPool(z);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: useAnimationPool  reason: avoid collision after fix types in other method */
    public RequestOptions mo6712useAnimationPool(boolean z) {
        return (GlideOptions) super.mo6712useAnimationPool(z);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: onlyRetrieveFromCache  reason: avoid collision after fix types in other method */
    public RequestOptions mo6686onlyRetrieveFromCache(boolean z) {
        return (GlideOptions) super.mo6686onlyRetrieveFromCache(z);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: diskCacheStrategy  reason: avoid collision after fix types in other method */
    public RequestOptions mo6661diskCacheStrategy(@NonNull DiskCacheStrategy diskCacheStrategy) {
        return (GlideOptions) super.mo6661diskCacheStrategy(diskCacheStrategy);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: priority  reason: avoid collision after fix types in other method */
    public RequestOptions mo6697priority(@NonNull Priority priority) {
        return (GlideOptions) super.mo6697priority(priority);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: placeholder  reason: avoid collision after fix types in other method */
    public RequestOptions mo6696placeholder(@Nullable Drawable drawable) {
        return (GlideOptions) super.mo6696placeholder(drawable);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: placeholder  reason: avoid collision after fix types in other method */
    public RequestOptions mo6695placeholder(@DrawableRes int i) {
        return (GlideOptions) super.mo6695placeholder(i);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: fallback  reason: avoid collision after fix types in other method */
    public RequestOptions mo6671fallback(@Nullable Drawable drawable) {
        return (GlideOptions) super.mo6671fallback(drawable);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: fallback  reason: avoid collision after fix types in other method */
    public RequestOptions mo6670fallback(@DrawableRes int i) {
        return (GlideOptions) super.mo6670fallback(i);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: error  reason: avoid collision after fix types in other method */
    public RequestOptions mo6668error(@Nullable Drawable drawable) {
        return (GlideOptions) super.mo6668error(drawable);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: error  reason: avoid collision after fix types in other method */
    public RequestOptions mo6667error(@DrawableRes int i) {
        return (GlideOptions) super.mo6667error(i);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: theme  reason: avoid collision after fix types in other method */
    public RequestOptions mo6702theme(@Nullable Resources.Theme theme) {
        return (GlideOptions) super.mo6702theme(theme);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: skipMemoryCache  reason: avoid collision after fix types in other method */
    public RequestOptions mo6701skipMemoryCache(boolean z) {
        return (GlideOptions) super.mo6701skipMemoryCache(z);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: override  reason: avoid collision after fix types in other method */
    public RequestOptions mo6694override(int i, int i2) {
        return (GlideOptions) super.mo6694override(i, i2);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: override  reason: avoid collision after fix types in other method */
    public RequestOptions mo6693override(int i) {
        return (GlideOptions) super.mo6693override(i);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: signature  reason: avoid collision after fix types in other method */
    public RequestOptions mo6699signature(@NonNull Key key) {
        return (GlideOptions) super.mo6699signature(key);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    /* renamed from: clone  reason: collision with other method in class */
    public RequestOptions mo6658clone() {
        return (GlideOptions) super.clone();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: set  reason: avoid collision after fix types in other method */
    public <Y> RequestOptions mo6698set(@NonNull Option<Y> option, @NonNull Y y) {
        return (GlideOptions) super.mo6698set((Option<Option<Y>>) option, (Option<Y>) y);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: decode  reason: avoid collision after fix types in other method */
    public RequestOptions mo6659decode(@NonNull Class<?> cls) {
        return (GlideOptions) super.mo6659decode(cls);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: encodeFormat  reason: avoid collision after fix types in other method */
    public RequestOptions mo6665encodeFormat(@NonNull Bitmap.CompressFormat compressFormat) {
        return (GlideOptions) super.mo6665encodeFormat(compressFormat);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: encodeQuality  reason: avoid collision after fix types in other method */
    public RequestOptions mo6666encodeQuality(@IntRange(from = 0, m5591to = 100) int i) {
        return (GlideOptions) super.mo6666encodeQuality(i);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: frame  reason: avoid collision after fix types in other method */
    public RequestOptions mo6674frame(@IntRange(from = 0) long j) {
        return (GlideOptions) super.mo6674frame(j);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: format  reason: avoid collision after fix types in other method */
    public RequestOptions mo6673format(@NonNull DecodeFormat decodeFormat) {
        return (GlideOptions) super.mo6673format(decodeFormat);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: disallowHardwareConfig  reason: avoid collision after fix types in other method */
    public RequestOptions mo6660disallowHardwareConfig() {
        return (GlideOptions) super.mo6660disallowHardwareConfig();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: downsample  reason: avoid collision after fix types in other method */
    public RequestOptions mo6664downsample(@NonNull DownsampleStrategy downsampleStrategy) {
        return (GlideOptions) super.mo6664downsample(downsampleStrategy);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: timeout  reason: avoid collision after fix types in other method */
    public RequestOptions mo6706timeout(@IntRange(from = 0) int i) {
        return (GlideOptions) super.mo6706timeout(i);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: optionalCenterCrop  reason: avoid collision after fix types in other method */
    public RequestOptions mo6687optionalCenterCrop() {
        return (GlideOptions) super.mo6687optionalCenterCrop();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: centerCrop  reason: avoid collision after fix types in other method */
    public RequestOptions mo6654centerCrop() {
        return (GlideOptions) super.mo6654centerCrop();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: optionalFitCenter  reason: avoid collision after fix types in other method */
    public RequestOptions mo6690optionalFitCenter() {
        return (GlideOptions) super.mo6690optionalFitCenter();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: fitCenter  reason: avoid collision after fix types in other method */
    public RequestOptions mo6672fitCenter() {
        return (GlideOptions) super.mo6672fitCenter();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: optionalCenterInside  reason: avoid collision after fix types in other method */
    public RequestOptions mo6688optionalCenterInside() {
        return (GlideOptions) super.mo6688optionalCenterInside();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: centerInside  reason: avoid collision after fix types in other method */
    public RequestOptions mo6655centerInside() {
        return (GlideOptions) super.mo6655centerInside();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: optionalCircleCrop  reason: avoid collision after fix types in other method */
    public RequestOptions mo6689optionalCircleCrop() {
        return (GlideOptions) super.mo6689optionalCircleCrop();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: circleCrop  reason: avoid collision after fix types in other method */
    public RequestOptions mo6656circleCrop() {
        return (GlideOptions) super.mo6656circleCrop();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: transform  reason: avoid collision after fix types in other method */
    public RequestOptions mo6707transform(@NonNull Transformation<Bitmap> transformation) {
        return (GlideOptions) super.mo6707transform(transformation);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @SafeVarargs
    @CheckResult
    @NonNull
    /* renamed from: transform  reason: avoid collision after fix types in other method */
    public final RequestOptions mo6709transform(@NonNull Transformation<Bitmap>... transformationArr) {
        return (GlideOptions) super.mo6709transform(transformationArr);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    @Deprecated
    @SafeVarargs
    @CheckResult
    /* renamed from: transforms  reason: avoid collision after fix types in other method */
    public final RequestOptions mo6710transforms(@NonNull Transformation<Bitmap>... transformationArr) {
        return (GlideOptions) super.mo6710transforms(transformationArr);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: optionalTransform  reason: avoid collision after fix types in other method */
    public RequestOptions mo6691optionalTransform(@NonNull Transformation<Bitmap> transformation) {
        return (GlideOptions) super.mo6691optionalTransform(transformation);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: optionalTransform  reason: avoid collision after fix types in other method */
    public <Y> RequestOptions mo6692optionalTransform(@NonNull Class<Y> cls, @NonNull Transformation<Y> transformation) {
        return (GlideOptions) super.mo6692optionalTransform((Class) cls, (Transformation) transformation);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: transform  reason: avoid collision after fix types in other method */
    public <Y> RequestOptions mo6708transform(@NonNull Class<Y> cls, @NonNull Transformation<Y> transformation) {
        return (GlideOptions) super.mo6708transform((Class) cls, (Transformation) transformation);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: dontTransform  reason: avoid collision after fix types in other method */
    public RequestOptions mo6663dontTransform() {
        return (GlideOptions) super.mo6663dontTransform();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: dontAnimate  reason: avoid collision after fix types in other method */
    public RequestOptions mo6662dontAnimate() {
        return (GlideOptions) super.mo6662dontAnimate();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @CheckResult
    @NonNull
    /* renamed from: apply  reason: collision with other method in class */
    public RequestOptions mo6653apply(@NonNull BaseRequestOptions<?> baseRequestOptions) {
        return (GlideOptions) super.mo6653apply(baseRequestOptions);
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    public RequestOptions lock() {
        return (GlideOptions) super.lock();
    }

    @Override // com.bumptech.glide.request.BaseRequestOptions
    @NonNull
    public RequestOptions autoClone() {
        return (GlideOptions) super.autoClone();
    }
}
