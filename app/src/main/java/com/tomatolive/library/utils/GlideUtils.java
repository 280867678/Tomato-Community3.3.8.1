package com.tomatolive.library.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p002v4.app.Fragment;
import android.support.p002v4.content.ContextCompat;
import android.text.TextPaint;
import android.text.TextUtils;
import android.widget.ImageView;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.webp.decoder.WebpDrawable;
import com.bumptech.glide.integration.webp.decoder.WebpDrawableTransformation;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.opensource.svgaplayer.SVGADynamicEntity;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$string;
import com.tomatolive.library.p136ui.interfaces.OnGlideDownloadCallback;
import com.tomatolive.library.p136ui.view.widget.matisse.GlideApp;
import com.tomatolive.library.p136ui.view.widget.matisse.GlideRequest;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/* loaded from: classes4.dex */
public class GlideUtils {
    private static final int BLUR_RADIUS = 25;
    private static final int BLUR_SAMPLING = 6;
    private static Map<String, String> cacheMap = new HashMap();

    private GlideUtils() {
    }

    public static void loadAvatar(Context context, ImageView imageView, @DrawableRes int i) {
        if (!isValidContextForGlide(context)) {
            return;
        }
        GlideApp.with(context).mo6727load(Integer.valueOf(i)).mo6710transforms(new CenterCrop(), new CircleCrop()).into(imageView);
    }

    public static void loadAvatar(Fragment fragment, ImageView imageView, String str) {
        loadAvatar(fragment, imageView, str, R$drawable.fq_ic_placeholder_avatar);
    }

    public static void loadAvatar(Context context, ImageView imageView, String str) {
        loadAvatar(context, imageView, str, R$drawable.fq_ic_placeholder_avatar);
    }

    public static void loadAvatar(Fragment fragment, ImageView imageView, String str, @DrawableRes int i) {
        loadAvatar(fragment.getContext(), imageView, str, i);
    }

    public static void loadAvatar(final Context context, final ImageView imageView, String str, @DrawableRes final int i) {
        if (!isValidContextForGlide(context)) {
            return;
        }
        if (TextUtils.isEmpty(str)) {
            imageView.setImageResource(i);
            return;
        }
        imageView.setImageResource(i);
        final String formatDownUrl = formatDownUrl(str);
        if (!isEncryptionAvatarUrl(formatDownUrl)) {
            loadTargetToImageByCircle(context, imageView, formatDownUrl, i);
        } else if (isLocalCachePath(formatDownUrl)) {
            loadTargetToImageByCircle(context, imageView, cacheMapGet(formatDownUrl), i);
        } else {
            try {
                GlideApp.with(context).mo6719asFile().mo6729load(formatDownUrl).mo6701skipMemoryCache(false).mo6662dontAnimate().mo6695placeholder(i).into((GlideRequest<File>) new SimpleTarget<File>() { // from class: com.tomatolive.library.utils.GlideUtils.1
                    @Override // com.bumptech.glide.request.target.Target
                    public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object obj, @Nullable Transition transition) {
                        onResourceReady((File) obj, (Transition<? super File>) transition);
                    }

                    @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
                    public void onLoadStarted(@Nullable Drawable drawable) {
                        super.onLoadStarted(drawable);
                        imageView.setImageResource(i);
                    }

                    @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
                    public void onLoadFailed(@Nullable Drawable drawable) {
                        super.onLoadFailed(drawable);
                        imageView.setImageResource(i);
                    }

                    public void onResourceReady(@NonNull File file, @Nullable Transition<? super File> transition) {
                        GlideUtils.loadAvatarByObservable(context, imageView, file, i, formatDownUrl);
                    }
                });
            } catch (Exception unused) {
                imageView.setImageResource(i);
            } catch (OutOfMemoryError unused2) {
                imageView.setImageResource(i);
            }
        }
    }

    public static void loadAvatar(final Context context, final ImageView imageView, final String str, final int i, final int i2) {
        if (!isValidContextForGlide(context)) {
            return;
        }
        if (TextUtils.isEmpty(str)) {
            imageView.setImageResource(R$drawable.fq_ic_placeholder_avatar_white);
        } else if (!isEncryptionAvatarUrl(str)) {
            loadTargetToImageByCircle(context, imageView, str, i, i2, R$drawable.fq_ic_placeholder_avatar_white);
        } else if (isLocalCachePath(str)) {
            loadTargetToImageByCircle(context, imageView, cacheMapGet(str), i, i2, R$drawable.fq_ic_placeholder_avatar_white);
        } else {
            try {
                GlideApp.with(context).mo6719asFile().mo6729load(str).mo6701skipMemoryCache(false).mo6662dontAnimate().into((GlideRequest<File>) new SimpleTarget<File>() { // from class: com.tomatolive.library.utils.GlideUtils.2
                    @Override // com.bumptech.glide.request.target.Target
                    public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object obj, @Nullable Transition transition) {
                        onResourceReady((File) obj, (Transition<? super File>) transition);
                    }

                    @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
                    public void onLoadStarted(@Nullable Drawable drawable) {
                        super.onLoadStarted(drawable);
                        imageView.setImageResource(R$drawable.fq_ic_placeholder_avatar_white);
                    }

                    @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
                    public void onLoadFailed(@Nullable Drawable drawable) {
                        super.onLoadFailed(drawable);
                        imageView.setImageResource(R$drawable.fq_ic_placeholder_avatar_white);
                    }

                    public void onResourceReady(@NonNull File file, @Nullable Transition<? super File> transition) {
                        GlideUtils.loadAvatarByObservable(context, imageView, file, R$drawable.fq_ic_placeholder_avatar_white, i, i2, str);
                    }
                });
            } catch (Exception unused) {
                imageView.setImageResource(R$drawable.fq_ic_placeholder_avatar_white);
            } catch (OutOfMemoryError unused2) {
                imageView.setImageResource(R$drawable.fq_ic_placeholder_avatar_white);
            }
        }
    }

    public static void loadLivingGif(Context context, ImageView imageView) {
        if (!isValidContextForGlide(context)) {
            return;
        }
        GlideApp.with(context).mo6720asGif().mo6727load(Integer.valueOf(R$drawable.fq_ic_living_icon_circle_gif_2)).mo6695placeholder(R$drawable.fq_ic_living_icon_circle).into(imageView);
    }

    public static void loadImage(Context context, ImageView imageView, @DrawableRes int i) {
        if (!isValidContextForGlide(context)) {
            return;
        }
        GlideApp.with(context).mo6727load(Integer.valueOf(i)).mo6654centerCrop().into(imageView);
    }

    public static void loadImage(Context context, ImageView imageView, File file, @DrawableRes int i) {
        if (!isValidContextForGlide(context)) {
            return;
        }
        GlideApp.with(context).mo6717asBitmap().mo6726load(file).mo6654centerCrop().mo6695placeholder(i).into(imageView);
    }

    public static void loadImage(Fragment fragment, ImageView imageView, File file, @DrawableRes int i) {
        if (!isValidContextForGlide(fragment)) {
            return;
        }
        GlideApp.with(fragment).mo6717asBitmap().mo6726load(file).mo6654centerCrop().mo6695placeholder(i).into(imageView);
    }

    public static void loadImage(Context context, ImageView imageView, Object obj, @DrawableRes int i) {
        if (!isValidContextForGlide(context)) {
            return;
        }
        String formatDownUrl = obj instanceof String ? formatDownUrl((String) obj) : "";
        if (TextUtils.isEmpty(formatDownUrl)) {
            imageView.setImageResource(i);
        } else {
            GlideApp.with(context).mo6717asBitmap().mo6729load(formatDownUrl).mo6654centerCrop().mo6695placeholder(i).into(imageView);
        }
    }

    public static void loadImage(final Context context, final ImageView imageView, String str, @DrawableRes final int i) {
        if (!isValidContextForGlide(context)) {
            return;
        }
        if (TextUtils.isEmpty(str)) {
            imageView.setImageResource(i);
            return;
        }
        final String formatDownUrl = formatDownUrl(str);
        if (!isEncryptionAvatarUrl(formatDownUrl)) {
            loadTargetToImage(context, imageView, formatDownUrl, i);
        } else if (isLocalCachePath(formatDownUrl)) {
            loadTargetToImage(context, imageView, cacheMapGet(formatDownUrl), i);
        } else {
            try {
                GlideApp.with(context).mo6719asFile().mo6729load(formatDownUrl).into((GlideRequest<File>) new SimpleTarget<File>() { // from class: com.tomatolive.library.utils.GlideUtils.3
                    @Override // com.bumptech.glide.request.target.Target
                    public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object obj, @Nullable Transition transition) {
                        onResourceReady((File) obj, (Transition<? super File>) transition);
                    }

                    @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
                    public void onLoadStarted(@Nullable Drawable drawable) {
                        super.onLoadStarted(drawable);
                        imageView.setImageResource(i);
                    }

                    public void onResourceReady(@NonNull File file, @Nullable Transition<? super File> transition) {
                        GlideUtils.loadImgByObservable(context, imageView, file, i, formatDownUrl);
                    }

                    @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
                    public void onLoadFailed(@Nullable Drawable drawable) {
                        super.onLoadFailed(drawable);
                        imageView.setImageResource(i);
                    }
                });
            } catch (Exception unused) {
                imageView.setImageResource(i);
            } catch (OutOfMemoryError unused2) {
                imageView.setImageResource(i);
            }
        }
    }

    public static void loadImage(final Fragment fragment, final ImageView imageView, String str, @DrawableRes final int i) {
        if (!isValidContextForGlide(fragment)) {
            return;
        }
        if (TextUtils.isEmpty(str)) {
            imageView.setImageResource(i);
            return;
        }
        final String formatDownUrl = formatDownUrl(str);
        if (!isEncryptionAvatarUrl(formatDownUrl)) {
            loadTargetToImage(fragment, imageView, formatDownUrl, i);
        } else if (isLocalCachePath(formatDownUrl)) {
            loadTargetToImage(fragment, imageView, cacheMapGet(formatDownUrl), i);
        } else {
            try {
                GlideApp.with(fragment).mo6719asFile().mo6729load(formatDownUrl).into((GlideRequest<File>) new SimpleTarget<File>() { // from class: com.tomatolive.library.utils.GlideUtils.4
                    @Override // com.bumptech.glide.request.target.Target
                    public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object obj, @Nullable Transition transition) {
                        onResourceReady((File) obj, (Transition<? super File>) transition);
                    }

                    @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
                    public void onLoadStarted(@Nullable Drawable drawable) {
                        super.onLoadStarted(drawable);
                        imageView.setImageResource(i);
                    }

                    @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
                    public void onLoadFailed(@Nullable Drawable drawable) {
                        super.onLoadFailed(drawable);
                        imageView.setImageResource(i);
                    }

                    public void onResourceReady(@NonNull File file, @Nullable Transition<? super File> transition) {
                        GlideUtils.loadImageByObservable(fragment, imageView, file, i, formatDownUrl);
                    }
                });
            } catch (Exception unused) {
                imageView.setImageResource(i);
            } catch (OutOfMemoryError unused2) {
                imageView.setImageResource(i);
            }
        }
    }

    public static void loadImageByGray(final Context context, final ImageView imageView, String str, @DrawableRes final int i) {
        if (!isValidContextForGlide(context)) {
            return;
        }
        if (TextUtils.isEmpty(str)) {
            imageView.setImageResource(i);
            return;
        }
        final String formatDownUrl = formatDownUrl(str);
        if (!isEncryptionAvatarUrl(formatDownUrl)) {
            loadTargetToImageByGray(context, imageView, formatDownUrl, i);
        } else if (isLocalCachePath(formatDownUrl)) {
            loadTargetToImageByGray(context, imageView, cacheMapGet(formatDownUrl), i);
        } else {
            try {
                GlideApp.with(context).mo6719asFile().mo6729load(formatDownUrl).into((GlideRequest<File>) new SimpleTarget<File>() { // from class: com.tomatolive.library.utils.GlideUtils.5
                    @Override // com.bumptech.glide.request.target.Target
                    public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object obj, @Nullable Transition transition) {
                        onResourceReady((File) obj, (Transition<? super File>) transition);
                    }

                    @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
                    public void onLoadStarted(@Nullable Drawable drawable) {
                        super.onLoadStarted(drawable);
                        imageView.setImageResource(i);
                    }

                    public void onResourceReady(@NonNull File file, @Nullable Transition<? super File> transition) {
                        GlideUtils.loadGrayImgByObservable(context, imageView, file, i, formatDownUrl);
                    }

                    @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
                    public void onLoadFailed(@Nullable Drawable drawable) {
                        super.onLoadFailed(drawable);
                        imageView.setImageResource(i);
                    }
                });
            } catch (Exception unused) {
                imageView.setImageResource(i);
            } catch (OutOfMemoryError unused2) {
                imageView.setImageResource(i);
            }
        }
    }

    public static void loadImageNormal(Context context, ImageView imageView, String str) {
        if (!isValidContextForGlide(context)) {
            return;
        }
        GlideApp.with(context).mo6717asBitmap().mo6729load(formatDownUrl(str)).into(imageView);
    }

    public static void loadImage(Context context, ImageView imageView, String str) {
        if (!isValidContextForGlide(context)) {
            return;
        }
        GlideApp.with(context).mo6717asBitmap().mo6729load(formatDownUrl(str)).mo6654centerCrop().into(imageView);
    }

    public static void loadImage(Context context, ImageView imageView, String str, int i, int i2) {
        if (!isValidContextForGlide(context)) {
            return;
        }
        GlideApp.with(context).mo6717asBitmap().mo6729load(formatDownUrl(str)).mo6654centerCrop().mo6694override(i, i2).into(imageView);
    }

    public static void loadImageBlur(final Context context, final ImageView imageView, String str, @DrawableRes final int i) {
        if (!isValidContextForGlide(context)) {
            return;
        }
        if (TextUtils.isEmpty(str)) {
            imageView.setImageResource(i);
            return;
        }
        final String formatDownUrl = formatDownUrl(str);
        if (!isEncryptionAvatarUrl(formatDownUrl)) {
            GlideApp.with(context).mo6717asBitmap().mo6729load(formatDownUrl).mo6710transforms(new CenterCrop(), new BlurTransformation(25, 6)).mo6695placeholder(i).into(imageView);
            return;
        }
        try {
            GlideApp.with(context).mo6719asFile().mo6729load(formatDownUrl).into((GlideRequest<File>) new SimpleTarget<File>() { // from class: com.tomatolive.library.utils.GlideUtils.6
                @Override // com.bumptech.glide.request.target.Target
                public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object obj, @Nullable Transition transition) {
                    onResourceReady((File) obj, (Transition<? super File>) transition);
                }

                @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
                public void onLoadStarted(@Nullable Drawable drawable) {
                    super.onLoadStarted(drawable);
                    imageView.setImageResource(i);
                }

                @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
                public void onLoadFailed(@Nullable Drawable drawable) {
                    super.onLoadFailed(drawable);
                    imageView.setImageResource(i);
                }

                public void onResourceReady(@NonNull File file, @Nullable Transition<? super File> transition) {
                    GlideUtils.loadImageBlurByObservable(context, imageView, file, i, formatDownUrl);
                }
            });
        } catch (Exception unused) {
            imageView.setImageResource(i);
        } catch (OutOfMemoryError unused2) {
            imageView.setImageResource(i);
        }
    }

    public static void loadImageBlur(Context context, ImageView imageView, @DrawableRes int i) {
        if (!isValidContextForGlide(context)) {
            return;
        }
        GlideApp.with(context).mo6727load(Integer.valueOf(i)).mo6710transforms(new CenterCrop(), new BlurTransformation(25, 6)).into(imageView);
    }

    public static void loadRoundCornersImage(Context context, ImageView imageView, String str, int i) {
        if (!isValidContextForGlide(context)) {
            return;
        }
        GlideApp.with(context).mo6717asBitmap().mo6729load(formatDownUrl(str)).mo6701skipMemoryCache(false).mo6662dontAnimate().mo6710transforms(new CenterCrop(), new RoundedCornersTransformation(ConvertUtils.dp2px(i), 0)).into(imageView);
    }

    public static void loadRoundCornersImage(Context context, ImageView imageView, File file, int i) {
        if (!isValidContextForGlide(context)) {
            return;
        }
        GlideApp.with(context).mo6717asBitmap().mo6726load(file).mo6710transforms(new CenterCrop(), new RoundedCornersTransformation(ConvertUtils.dp2px(i), 0)).into(imageView);
    }

    public static void loadRoundCornersImage(final Fragment fragment, final ImageView imageView, String str, final int i, @DrawableRes final int i2) {
        if (!isValidContextForGlide(fragment.getContext())) {
            return;
        }
        if (TextUtils.isEmpty(str)) {
            imageView.setImageResource(i2);
            return;
        }
        imageView.setImageResource(i2);
        final String formatDownUrl = formatDownUrl(str);
        if (!isEncryptionAvatarUrl(formatDownUrl)) {
            loadTargetToImageByRoundCorners(fragment, imageView, formatDownUrl, i, i2);
        } else if (isLocalCachePath(formatDownUrl)) {
            loadTargetToImageByRoundCorners(fragment, imageView, cacheMapGet(formatDownUrl), i, i2);
        } else {
            try {
                GlideApp.with(fragment).mo6719asFile().mo6729load(formatDownUrl).into((GlideRequest<File>) new SimpleTarget<File>() { // from class: com.tomatolive.library.utils.GlideUtils.7
                    @Override // com.bumptech.glide.request.target.Target
                    public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object obj, @Nullable Transition transition) {
                        onResourceReady((File) obj, (Transition<? super File>) transition);
                    }

                    @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
                    public void onLoadStarted(@Nullable Drawable drawable) {
                        super.onLoadStarted(drawable);
                        imageView.setImageResource(i2);
                    }

                    public void onResourceReady(@NonNull File file, @Nullable Transition<? super File> transition) {
                        GlideUtils.loadRoundCornersImgByObservable(fragment, imageView, file, i, i2, formatDownUrl);
                    }

                    @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
                    public void onLoadFailed(@Nullable Drawable drawable) {
                        super.onLoadFailed(drawable);
                        imageView.setImageResource(i2);
                    }
                });
            } catch (Exception unused) {
                imageView.setImageResource(i2);
            } catch (OutOfMemoryError unused2) {
                imageView.setImageResource(i2);
            }
        }
    }

    public static void loadRoundCornersImage(final Context context, final ImageView imageView, String str, final int i, @DrawableRes final int i2) {
        if (!isValidContextForGlide(context)) {
            return;
        }
        if (TextUtils.isEmpty(str)) {
            imageView.setImageResource(i2);
            return;
        }
        imageView.setImageResource(i2);
        final String formatDownUrl = formatDownUrl(str);
        if (!isEncryptionAvatarUrl(formatDownUrl)) {
            loadTargetToImageByRoundCorners(context, imageView, formatDownUrl, i, i2);
        } else if (isLocalCachePath(formatDownUrl)) {
            loadTargetToImageByRoundCorners(context, imageView, cacheMapGet(formatDownUrl), i, i2);
        } else {
            try {
                GlideApp.with(context).mo6719asFile().mo6729load(formatDownUrl).into((GlideRequest<File>) new SimpleTarget<File>() { // from class: com.tomatolive.library.utils.GlideUtils.8
                    @Override // com.bumptech.glide.request.target.Target
                    public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object obj, @Nullable Transition transition) {
                        onResourceReady((File) obj, (Transition<? super File>) transition);
                    }

                    @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
                    public void onLoadStarted(@Nullable Drawable drawable) {
                        super.onLoadStarted(drawable);
                        imageView.setImageResource(i2);
                    }

                    @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
                    public void onLoadFailed(@Nullable Drawable drawable) {
                        super.onLoadFailed(drawable);
                        imageView.setImageResource(i2);
                    }

                    public void onResourceReady(@NonNull File file, @Nullable Transition<? super File> transition) {
                        GlideUtils.loadRoundCornersImgByObservable(context, imageView, file, i, i2, formatDownUrl);
                    }
                });
            } catch (Exception unused) {
                imageView.setImageResource(i2);
            } catch (OutOfMemoryError unused2) {
                imageView.setImageResource(i2);
            }
        }
    }

    public static void loadRoundCornersImage(Context context, ImageView imageView, @DrawableRes int i, int i2, RoundedCornersTransformation.CornerType cornerType) {
        GlideApp.with(context).mo6717asBitmap().mo6727load(Integer.valueOf(i)).mo6710transforms(new CenterCrop(), new RoundedCornersTransformation(ConvertUtils.dp2px(i2), 0, cornerType)).into(imageView);
    }

    public static void loadRoundCornersImage(Context context, ImageView imageView, String str, int i, RoundedCornersTransformation.CornerType cornerType, @DrawableRes int i2) {
        GlideApp.with(context).mo6717asBitmap().mo6729load(formatDownUrl(str)).mo6695placeholder(i2).mo6710transforms(new CenterCrop(), new RoundedCornersTransformation(ConvertUtils.dp2px(i), 0, cornerType)).into(imageView);
    }

    public static void loadGifImage(final Context context, final ImageView imageView, String str, @DrawableRes final int i) {
        if (!isValidContextForGlide(context)) {
            return;
        }
        if (TextUtils.isEmpty(str)) {
            imageView.setImageResource(i);
            return;
        }
        final String formatDownUrl = formatDownUrl(str);
        if (!isEncryptionAvatarUrl(formatDownUrl)) {
            loadTargetToGifImage(context, imageView, formatDownUrl, i);
        } else if (isLocalCachePath(formatDownUrl)) {
            loadTargetToGifImage(context, imageView, cacheMapGet(formatDownUrl), i);
        } else {
            try {
                GlideApp.with(context).mo6719asFile().mo6729load(formatDownUrl).into((GlideRequest<File>) new SimpleTarget<File>() { // from class: com.tomatolive.library.utils.GlideUtils.9
                    @Override // com.bumptech.glide.request.target.Target
                    public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object obj, @Nullable Transition transition) {
                        onResourceReady((File) obj, (Transition<? super File>) transition);
                    }

                    @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
                    public void onLoadStarted(@Nullable Drawable drawable) {
                        super.onLoadStarted(drawable);
                        imageView.setImageResource(i);
                    }

                    public void onResourceReady(@NonNull File file, @Nullable Transition<? super File> transition) {
                        GlideUtils.loadGifImgByObservable(context, imageView, file, i, formatDownUrl);
                    }

                    @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
                    public void onLoadFailed(@Nullable Drawable drawable) {
                        super.onLoadFailed(drawable);
                        imageView.setImageResource(i);
                    }
                });
            } catch (Exception unused) {
                imageView.setImageResource(i);
            } catch (OutOfMemoryError unused2) {
                imageView.setImageResource(i);
            }
        }
    }

    public static void loadGifImage(Fragment fragment, ImageView imageView, String str, @DrawableRes int i) {
        loadGifImage(fragment.getContext(), imageView, str, i);
    }

    public static void loadWebpImage(Context context, ImageView imageView, String str, @DrawableRes int i) {
        if (!isValidContextForGlide(context)) {
            return;
        }
        if (TextUtils.isEmpty(str)) {
            imageView.setImageResource(i);
            return;
        }
        String formatDownUrl = formatDownUrl(str);
        CenterCrop centerCrop = new CenterCrop();
        GlideApp.with(context).mo6729load(formatDownUrl).mo6691optionalTransform((Transformation<Bitmap>) centerCrop).mo6692optionalTransform(WebpDrawable.class, (Transformation) new WebpDrawableTransformation(centerCrop)).mo6695placeholder(i).into(imageView);
    }

    public static void loadWebpImage(Fragment fragment, ImageView imageView, String str, @DrawableRes int i) {
        if (!isValidContextForGlide(fragment)) {
            return;
        }
        if (TextUtils.isEmpty(str)) {
            imageView.setImageResource(i);
            return;
        }
        String formatDownUrl = formatDownUrl(str);
        CenterCrop centerCrop = new CenterCrop();
        GlideApp.with(fragment).mo6729load(formatDownUrl).mo6691optionalTransform((Transformation<Bitmap>) centerCrop).mo6692optionalTransform(WebpDrawable.class, (Transformation) new WebpDrawableTransformation(centerCrop)).mo6695placeholder(i).into(imageView);
    }

    public static File decodeImage(String str, File file, File file2) {
        if (str.contains("_s1")) {
            return decodeImageS1File(file, file2);
        }
        if (str.contains("_s3")) {
            return decodeImageS3File(file, file2);
        }
        return decodeImageFile(file, file2);
    }

    /* JADX WARN: Removed duplicated region for block: B:47:0x0062 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0058 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static File decodeImageFile(File file, File file2) {
        FileOutputStream fileOutputStream;
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(file);
            try {
                fileOutputStream = new FileOutputStream(file2);
                try {
                    try {
                        byte[] bArr = new byte[5000];
                        while (fileInputStream.read(bArr) != -1) {
                            fileOutputStream.write(bArr);
                        }
                        fileOutputStream.flush();
                        try {
                            fileInputStream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            fileOutputStream.close();
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                        return file2;
                    } catch (Exception e3) {
                        e = e3;
                        e.printStackTrace();
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (Exception e4) {
                                e4.printStackTrace();
                            }
                        }
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (Exception e5) {
                                e5.printStackTrace();
                            }
                        }
                        return null;
                    }
                } catch (Throwable th) {
                    th = th;
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (Exception e6) {
                            e6.printStackTrace();
                        }
                    }
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (Exception e7) {
                            e7.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Exception e8) {
                e = e8;
                fileOutputStream = null;
            } catch (Throwable th2) {
                th = th2;
                fileOutputStream = null;
                if (fileInputStream != null) {
                }
                if (fileOutputStream != null) {
                }
                throw th;
            }
        } catch (Exception e9) {
            e = e9;
            fileOutputStream = null;
            fileInputStream = null;
        } catch (Throwable th3) {
            th = th3;
            fileOutputStream = null;
            fileInputStream = null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:48:0x006c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0062 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static File decodeImageS1File(File file, File file2) {
        FileOutputStream fileOutputStream;
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (Exception e) {
            e = e;
            fileOutputStream = null;
            fileInputStream = null;
        } catch (Throwable th) {
            th = th;
            fileOutputStream = null;
            fileInputStream = null;
        }
        try {
            fileOutputStream = new FileOutputStream(file2);
            try {
                try {
                    byte[] bArr = new byte[5000];
                    while (fileInputStream.read(bArr) != -1) {
                        byte b = bArr[0];
                        bArr[0] = bArr[1];
                        bArr[1] = b;
                        fileOutputStream.write(bArr);
                    }
                    fileOutputStream.flush();
                    try {
                        fileInputStream.close();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    try {
                        fileOutputStream.close();
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                    return file2;
                } catch (Exception e4) {
                    e = e4;
                    e.printStackTrace();
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (Exception e5) {
                            e5.printStackTrace();
                        }
                    }
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (Exception e6) {
                            e6.printStackTrace();
                        }
                    }
                    return null;
                }
            } catch (Throwable th2) {
                th = th2;
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (Exception e7) {
                        e7.printStackTrace();
                    }
                }
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (Exception e8) {
                        e8.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (Exception e9) {
            e = e9;
            fileOutputStream = null;
        } catch (Throwable th3) {
            th = th3;
            fileOutputStream = null;
            if (fileInputStream != null) {
            }
            if (fileOutputStream != null) {
            }
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:59:0x0079 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x006f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static File decodeImageS3File(File file, File file2) {
        FileOutputStream fileOutputStream;
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(file);
            try {
                fileOutputStream = new FileOutputStream(file2);
                try {
                    try {
                        byte[] bArr = new byte[1];
                        byte b = -1;
                        while (true) {
                            int read = fileInputStream.read(bArr);
                            if (read == -1) {
                                break;
                            }
                            if (b == -1) {
                                b = bArr[0];
                                bArr = new byte[4096];
                            } else {
                                byte[] bArr2 = new byte[read];
                                for (int i = 0; i < read; i++) {
                                    bArr2[i] = (byte) (bArr[i] ^ b);
                                }
                                fileOutputStream.write(bArr2);
                            }
                        }
                        fileOutputStream.flush();
                        try {
                            fileInputStream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            fileOutputStream.close();
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                        return file2;
                    } catch (Exception e3) {
                        e = e3;
                        e.printStackTrace();
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (Exception e4) {
                                e4.printStackTrace();
                            }
                        }
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (Exception e5) {
                                e5.printStackTrace();
                            }
                        }
                        return null;
                    }
                } catch (Throwable th) {
                    th = th;
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (Exception e6) {
                            e6.printStackTrace();
                        }
                    }
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (Exception e7) {
                            e7.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Exception e8) {
                e = e8;
                fileOutputStream = null;
            } catch (Throwable th2) {
                th = th2;
                fileOutputStream = null;
                if (fileInputStream != null) {
                }
                if (fileOutputStream != null) {
                }
                throw th;
            }
        } catch (Exception e9) {
            e = e9;
            fileOutputStream = null;
            fileInputStream = null;
        } catch (Throwable th3) {
            th = th3;
            fileOutputStream = null;
            fileInputStream = null;
        }
    }

    public static boolean isEncryptionAvatarUrl(String str) {
        return !TextUtils.isEmpty(str) && (str.contains("_s1") || str.contains("_s3"));
    }

    public static boolean isValidContextForGlide(Context context) {
        if (context == null) {
            return false;
        }
        if (!(context instanceof Activity)) {
            return true;
        }
        return !((Activity) context).isFinishing();
    }

    public static boolean isValidContextForGlide(Fragment fragment) {
        return (fragment == null || fragment.getActivity() == null || fragment.getActivity().isFinishing()) ? false : true;
    }

    public static void loadImageByObservable(final Fragment fragment, final ImageView imageView, final File file, @DrawableRes final int i, final String str) {
        if (!isValidContextForGlide(fragment)) {
            return;
        }
        Observable.just(true).map(new Function<Boolean, File>() { // from class: com.tomatolive.library.utils.GlideUtils.11
            @Override // io.reactivex.functions.Function
            /* renamed from: apply  reason: avoid collision after fix types in other method */
            public File mo6755apply(Boolean bool) {
                return GlideUtils.formatDecodeImage2File(file, str);
            }
        }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<File>() { // from class: com.tomatolive.library.utils.GlideUtils.10
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
            }

            @Override // io.reactivex.Observer
            public void onNext(File file2) {
                if (file2 == null) {
                    imageView.setImageResource(i);
                    return;
                }
                GlideUtils.cacheMapPut(str, file2);
                GlideUtils.loadTargetToImage(fragment, imageView, file2.getAbsolutePath(), i);
            }
        });
    }

    public static void loadImgByObservable(final Context context, final ImageView imageView, final File file, @DrawableRes final int i, final String str) {
        if (!isValidContextForGlide(context)) {
            return;
        }
        Observable.just(true).map(new Function<Boolean, File>() { // from class: com.tomatolive.library.utils.GlideUtils.13
            @Override // io.reactivex.functions.Function
            /* renamed from: apply  reason: avoid collision after fix types in other method */
            public File mo6755apply(Boolean bool) {
                return GlideUtils.formatDecodeImage2File(file, str);
            }
        }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<File>() { // from class: com.tomatolive.library.utils.GlideUtils.12
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
            }

            @Override // io.reactivex.Observer
            public void onNext(File file2) {
                if (file2 == null) {
                    imageView.setImageResource(i);
                    return;
                }
                GlideUtils.cacheMapPut(str, file2);
                GlideUtils.loadTargetToImage(context, imageView, file2.getAbsolutePath(), i);
            }
        });
    }

    public static void loadGrayImgByObservable(final Context context, final ImageView imageView, final File file, @DrawableRes final int i, final String str) {
        if (!isValidContextForGlide(context)) {
            return;
        }
        Observable.just(true).map(new Function<Boolean, File>() { // from class: com.tomatolive.library.utils.GlideUtils.15
            @Override // io.reactivex.functions.Function
            /* renamed from: apply  reason: avoid collision after fix types in other method */
            public File mo6755apply(Boolean bool) {
                return GlideUtils.formatDecodeImage2File(file, str);
            }
        }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<File>() { // from class: com.tomatolive.library.utils.GlideUtils.14
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
            }

            @Override // io.reactivex.Observer
            public void onNext(File file2) {
                if (file2 == null) {
                    imageView.setImageResource(i);
                    return;
                }
                GlideUtils.cacheMapPut(str, file2);
                GlideUtils.loadTargetToImageByGray(context, imageView, file2.getAbsolutePath(), i);
            }
        });
    }

    public static void loadGifImgByObservable(final Context context, final ImageView imageView, final File file, @DrawableRes final int i, final String str) {
        if (!isValidContextForGlide(context)) {
            return;
        }
        Observable.just(true).map(new Function<Boolean, File>() { // from class: com.tomatolive.library.utils.GlideUtils.17
            @Override // io.reactivex.functions.Function
            /* renamed from: apply  reason: avoid collision after fix types in other method */
            public File mo6755apply(Boolean bool) {
                return GlideUtils.formatDecodeImage2File(file, str);
            }
        }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<File>() { // from class: com.tomatolive.library.utils.GlideUtils.16
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
            }

            @Override // io.reactivex.Observer
            public void onNext(File file2) {
                if (file2 == null) {
                    imageView.setImageResource(i);
                    return;
                }
                GlideUtils.cacheMapPut(str, file2);
                GlideUtils.loadTargetToGifImage(context, imageView, file2.getAbsolutePath(), i);
            }
        });
    }

    public static void loadAvatarByObservable(final Fragment fragment, final ImageView imageView, final File file, @DrawableRes final int i, final String str) {
        if (!isValidContextForGlide(fragment)) {
            return;
        }
        Observable.just(true).map(new Function<Boolean, File>() { // from class: com.tomatolive.library.utils.GlideUtils.19
            @Override // io.reactivex.functions.Function
            /* renamed from: apply  reason: avoid collision after fix types in other method */
            public File mo6755apply(Boolean bool) throws Exception {
                return GlideUtils.formatDecodeImage2File(file, str);
            }
        }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<File>() { // from class: com.tomatolive.library.utils.GlideUtils.18
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
            }

            @Override // io.reactivex.Observer
            public void onNext(File file2) {
                if (file2 == null) {
                    imageView.setImageResource(i);
                    return;
                }
                GlideUtils.cacheMapPut(str, file2);
                GlideUtils.loadTargetToImageByCircle(fragment, imageView, file2.getAbsolutePath(), i);
            }
        });
    }

    public static void loadAvatarByObservable(final Context context, final ImageView imageView, final File file, @DrawableRes final int i, final String str) {
        if (!isValidContextForGlide(context)) {
            return;
        }
        Observable.just(true).map(new Function<Boolean, File>() { // from class: com.tomatolive.library.utils.GlideUtils.21
            @Override // io.reactivex.functions.Function
            /* renamed from: apply  reason: avoid collision after fix types in other method */
            public File mo6755apply(Boolean bool) {
                return GlideUtils.formatDecodeImage2File(file, str);
            }
        }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<File>() { // from class: com.tomatolive.library.utils.GlideUtils.20
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
            }

            @Override // io.reactivex.Observer
            public void onNext(File file2) {
                if (file2 == null) {
                    imageView.setImageResource(i);
                    return;
                }
                GlideUtils.cacheMapPut(str, file2);
                GlideUtils.loadTargetToImageByCircle(context, imageView, file2.getAbsolutePath(), i);
            }
        });
    }

    public static void loadAvatarByObservable(final Context context, final ImageView imageView, final File file, @DrawableRes final int i, final int i2, final int i3, final String str) {
        if (!isValidContextForGlide(context)) {
            imageView.setImageResource(i);
        } else {
            Observable.just(true).map(new Function() { // from class: com.tomatolive.library.utils.-$$Lambda$GlideUtils$2ElSLVyMhBxrranmNpcfIWMPFGc
                @Override // io.reactivex.functions.Function
                /* renamed from: apply */
                public final Object mo6755apply(Object obj) {
                    File formatDecodeImage2File;
                    Boolean bool = (Boolean) obj;
                    formatDecodeImage2File = GlideUtils.formatDecodeImage2File(file, str);
                    return formatDecodeImage2File;
                }
            }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<File>() { // from class: com.tomatolive.library.utils.GlideUtils.22
                @Override // io.reactivex.Observer
                public void onComplete() {
                }

                @Override // io.reactivex.Observer
                public void onError(Throwable th) {
                }

                @Override // io.reactivex.Observer
                public void onSubscribe(Disposable disposable) {
                }

                @Override // io.reactivex.Observer
                public void onNext(File file2) {
                    if (file2 == null) {
                        imageView.setImageResource(i);
                        return;
                    }
                    GlideUtils.cacheMapPut(str, file2);
                    GlideUtils.loadTargetToImageByCircle(context, imageView, file2.getAbsolutePath(), i2, i3, R$drawable.fq_ic_placeholder_avatar_white);
                }
            });
        }
    }

    public static void loadRoundCornersImgByObservable(final Fragment fragment, final ImageView imageView, final File file, final int i, @DrawableRes final int i2, final String str) {
        if (!isValidContextForGlide(fragment)) {
            imageView.setImageResource(i2);
        } else {
            Observable.just(true).map(new Function<Boolean, File>() { // from class: com.tomatolive.library.utils.GlideUtils.24
                @Override // io.reactivex.functions.Function
                /* renamed from: apply  reason: avoid collision after fix types in other method */
                public File mo6755apply(Boolean bool) {
                    return GlideUtils.formatDecodeImage2File(file, str);
                }
            }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<File>() { // from class: com.tomatolive.library.utils.GlideUtils.23
                @Override // io.reactivex.Observer
                public void onComplete() {
                }

                @Override // io.reactivex.Observer
                public void onError(Throwable th) {
                }

                @Override // io.reactivex.Observer
                public void onSubscribe(Disposable disposable) {
                }

                @Override // io.reactivex.Observer
                public void onNext(File file2) {
                    if (file2 == null) {
                        imageView.setImageResource(i2);
                        return;
                    }
                    GlideUtils.cacheMapPut(str, file2);
                    GlideUtils.loadTargetToImageByRoundCorners(fragment, imageView, file2.getAbsolutePath(), i, i2);
                }
            });
        }
    }

    public static void loadRoundCornersImgByObservable(final Context context, final ImageView imageView, final File file, final int i, @DrawableRes final int i2, final String str) {
        if (!isValidContextForGlide(context)) {
            imageView.setImageResource(i2);
        } else {
            Observable.just(true).map(new Function<Boolean, File>() { // from class: com.tomatolive.library.utils.GlideUtils.26
                @Override // io.reactivex.functions.Function
                /* renamed from: apply  reason: avoid collision after fix types in other method */
                public File mo6755apply(Boolean bool) {
                    return GlideUtils.formatDecodeImage2File(file, str);
                }
            }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<File>() { // from class: com.tomatolive.library.utils.GlideUtils.25
                @Override // io.reactivex.Observer
                public void onComplete() {
                }

                @Override // io.reactivex.Observer
                public void onError(Throwable th) {
                }

                @Override // io.reactivex.Observer
                public void onSubscribe(Disposable disposable) {
                }

                @Override // io.reactivex.Observer
                public void onNext(File file2) {
                    if (file2 == null) {
                        imageView.setImageResource(i2);
                        return;
                    }
                    GlideUtils.cacheMapPut(str, file2);
                    GlideUtils.loadTargetToImageByRoundCorners(context, imageView, file2.getAbsolutePath(), i, i2);
                }
            });
        }
    }

    public static void loadImageBlurByObservable(final Context context, final ImageView imageView, final File file, @DrawableRes final int i, final String str) {
        if (!isValidContextForGlide(context)) {
            imageView.setImageResource(i);
        } else {
            Observable.just(true).map(new Function<Boolean, File>() { // from class: com.tomatolive.library.utils.GlideUtils.28
                @Override // io.reactivex.functions.Function
                /* renamed from: apply  reason: avoid collision after fix types in other method */
                public File mo6755apply(Boolean bool) {
                    return GlideUtils.formatDecodeImage2File(file, str);
                }
            }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<File>() { // from class: com.tomatolive.library.utils.GlideUtils.27
                @Override // io.reactivex.Observer
                public void onComplete() {
                }

                @Override // io.reactivex.Observer
                public void onError(Throwable th) {
                }

                @Override // io.reactivex.Observer
                public void onSubscribe(Disposable disposable) {
                }

                @Override // io.reactivex.Observer
                public void onNext(File file2) {
                    if (file2 == null) {
                        imageView.setImageResource(i);
                    } else {
                        GlideApp.with(context).mo6717asBitmap().mo6726load(file2).mo6701skipMemoryCache(false).mo6662dontAnimate().mo6710transforms(new CenterCrop(), new BlurTransformation(25, 6)).mo6695placeholder(i).into(imageView);
                    }
                }
            });
        }
    }

    public static File formatDecodeImage2File(File file, String str) {
        String str2 = PathUtils.getExternalAppCachePath() + File.separator + "imgCache";
        com.blankj.utilcode.util.FileUtils.createOrExistsDir(str2);
        File file2 = new File(str2, formatImageName(str));
        return !file2.exists() ? decodeImage(str, file, file2) : file2;
    }

    public static String formatImageName(String str) {
        String str2 = System.currentTimeMillis() + "";
        if (!TextUtils.isEmpty(str)) {
            str2 = MD5Utils.getMd5(str);
        }
        return str2 + ".webp";
    }

    public static String formatDownUrl(String str) {
        if (RegexUtils.isURL(str)) {
            return str;
        }
        return AppUtils.getImgDownloadURl() + str;
    }

    public static boolean isLocalCachePath(String str) {
        Map<String, String> map = cacheMap;
        if (map == null) {
            return false;
        }
        String str2 = map.get(formatImgUrlMD5(str));
        return !TextUtils.isEmpty(str2) && str2.contains(formatImageName(str));
    }

    public static void cacheMapPut(String str, File file) {
        cacheMap.put(formatImgUrlMD5(str), file.getAbsolutePath());
    }

    public static String cacheMapGet(String str) {
        return cacheMap.get(formatImgUrlMD5(str));
    }

    private static String formatImgUrlMD5(String str) {
        return MD5Utils.getMd5(str);
    }

    public static void loadTargetToGifImage(Context context, ImageView imageView, String str, @DrawableRes int i) {
        try {
            GlideApp.with(context).mo6720asGif().mo6729load(str).mo6695placeholder(i).mo6654centerCrop().into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
            imageView.setImageResource(i);
        }
    }

    public static void loadTargetToImage(Context context, ImageView imageView, @DrawableRes int i) {
        try {
            GlideApp.with(context).mo6717asBitmap().mo6727load(Integer.valueOf(i)).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
            imageView.setImageResource(i);
        }
    }

    public static void loadTargetToImage(Context context, ImageView imageView, String str, @DrawableRes int i) {
        try {
            GlideApp.with(context).mo6717asBitmap().mo6729load(str).mo6654centerCrop().mo6695placeholder(i).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
            imageView.setImageResource(i);
        }
    }

    public static void loadTargetToImage(Fragment fragment, ImageView imageView, String str, @DrawableRes int i) {
        try {
            GlideApp.with(fragment).mo6717asBitmap().mo6729load(str).mo6654centerCrop().mo6695placeholder(i).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
            imageView.setImageResource(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void loadTargetToImageByRoundCorners(Fragment fragment, final ImageView imageView, String str, int i, @DrawableRes final int i2) {
        try {
            GlideApp.with(fragment).mo6717asBitmap().mo6729load(str).mo6710transforms(new CenterCrop(), new RoundedCornersTransformation(ConvertUtils.dp2px(i), 0)).mo6701skipMemoryCache(false).mo6662dontAnimate().mo6695placeholder(i2).mo6676listener(new RequestListener<Bitmap>() { // from class: com.tomatolive.library.utils.GlideUtils.29
                @Override // com.bumptech.glide.request.RequestListener
                public boolean onResourceReady(Bitmap bitmap, Object obj, Target<Bitmap> target, DataSource dataSource, boolean z) {
                    return false;
                }

                @Override // com.bumptech.glide.request.RequestListener
                public boolean onLoadFailed(@Nullable GlideException glideException, Object obj, Target<Bitmap> target, boolean z) {
                    imageView.setImageResource(i2);
                    return true;
                }
            }).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
            imageView.setImageResource(i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void loadTargetToImageByRoundCorners(Context context, final ImageView imageView, String str, int i, @DrawableRes final int i2) {
        try {
            GlideApp.with(context).mo6717asBitmap().mo6729load(str).mo6710transforms(new CenterCrop(), new RoundedCornersTransformation(ConvertUtils.dp2px(i), 0)).mo6701skipMemoryCache(false).mo6662dontAnimate().mo6695placeholder(i2).mo6676listener(new RequestListener<Bitmap>() { // from class: com.tomatolive.library.utils.GlideUtils.30
                @Override // com.bumptech.glide.request.RequestListener
                public boolean onResourceReady(Bitmap bitmap, Object obj, Target<Bitmap> target, DataSource dataSource, boolean z) {
                    return false;
                }

                @Override // com.bumptech.glide.request.RequestListener
                public boolean onLoadFailed(@Nullable GlideException glideException, Object obj, Target<Bitmap> target, boolean z) {
                    imageView.setImageResource(i2);
                    return true;
                }
            }).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
            imageView.setImageResource(i2);
        }
    }

    public static void loadTargetToImageByCircle(Fragment fragment, ImageView imageView, String str, @DrawableRes int i) {
        if (!isValidContextForGlide(fragment)) {
            return;
        }
        if (TextUtils.isEmpty(str)) {
            imageView.setImageResource(i);
        } else {
            GlideApp.with(fragment).mo6717asBitmap().mo6729load(str).mo6701skipMemoryCache(false).mo6662dontAnimate().mo6710transforms(new CenterCrop(), new CircleCrop()).mo6695placeholder(i).into(imageView);
        }
    }

    public static void loadTargetToImageByCircle(Context context, ImageView imageView, String str, @DrawableRes int i) {
        if (!isValidContextForGlide(context)) {
            return;
        }
        if (TextUtils.isEmpty(str)) {
            imageView.setImageResource(i);
        } else {
            GlideApp.with(context).mo6717asBitmap().mo6729load(str).mo6701skipMemoryCache(false).mo6662dontAnimate().mo6710transforms(new CenterCrop(), new CircleCrop()).mo6695placeholder(i).into(imageView);
        }
    }

    public static void loadTargetToImageByGray(Context context, ImageView imageView, String str, @DrawableRes int i) {
        try {
            GlideApp.with(context).mo6717asBitmap().mo6729load(str).mo6707transform((Transformation<Bitmap>) new GrayscaleTransformation()).mo6695placeholder(i).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
            imageView.setImageResource(i);
        }
    }

    public static void loadTargetToImageByCircle(Context context, ImageView imageView, String str, int i, int i2, @DrawableRes int i3) {
        if (!isValidContextForGlide(context)) {
            return;
        }
        if (TextUtils.isEmpty(str)) {
            imageView.setImageResource(i3);
        } else {
            GlideApp.with(context).mo6717asBitmap().mo6729load(str).mo6701skipMemoryCache(false).mo6662dontAnimate().mo6710transforms(new CenterCrop(), new GlideCircleBorderTransform(i, i2)).mo6695placeholder(i3).into(imageView);
        }
    }

    public static void loadAdBannerImageForRoundView(Context context, ImageView imageView, String str, @DrawableRes int i) {
        if (!TextUtils.isEmpty(str)) {
            String fileExtension = FileUtils.getFileExtension(str);
            if (fileExtension.contains("gif")) {
                loadGifImage(context, imageView, str, i);
                return;
            } else if (fileExtension.contains("webp")) {
                loadWebpImage(context, imageView, str, i);
                return;
            } else {
                loadImage(context, imageView, str, i);
                return;
            }
        }
        imageView.setImageResource(i);
    }

    public static void loadAdBannerImageForRoundView(Fragment fragment, ImageView imageView, String str, @DrawableRes int i) {
        if (!TextUtils.isEmpty(str)) {
            String fileExtension = FileUtils.getFileExtension(str);
            if (fileExtension.contains("gif")) {
                loadGifImage(fragment, imageView, str, i);
                return;
            } else if (fileExtension.contains("webp")) {
                loadWebpImage(fragment, imageView, str, i);
                return;
            } else {
                loadImage(fragment, imageView, str, i);
                return;
            }
        }
        imageView.setImageResource(i);
    }

    public static SVGADynamicEntity getGuardSVGADynamicEntity(final Context context, final String str, String str2, String str3, String str4) {
        String str5 = TextUtils.equals(str4, "3") ? "#FFD171" : "#8AEEFF";
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(30.0f);
        textPaint.setFakeBoldText(true);
        textPaint.setColor(Color.parseColor(str5));
        TextPaint textPaint2 = new TextPaint();
        textPaint2.setTextSize(30.0f);
        textPaint2.setColor(-1);
        textPaint2.setFakeBoldText(true);
        final SVGADynamicEntity sVGADynamicEntity = new SVGADynamicEntity();
        Bitmap drawable2Bitmap = ImageUtils.drawable2Bitmap(ContextCompat.getDrawable(context, AppUtils.getUserGradeBgDrawableRes(str3)));
        Bitmap drawable2Bitmap2 = ImageUtils.drawable2Bitmap(ContextCompat.getDrawable(context, AppUtils.getUserGradeIconResource(true, NumberUtils.string2int(str3))));
        Bitmap drawable2Bitmap3 = ImageUtils.drawable2Bitmap(ContextCompat.getDrawable(context, TextUtils.equals(str4, "3") ? R$drawable.fq_ic_live_msg_year_guard : R$drawable.fq_ic_live_msg_mouth_guard));
        sVGADynamicEntity.setDynamicText(StringUtils.formatStrLen(str2, 5), textPaint, "img_1410");
        sVGADynamicEntity.setDynamicText(context.getString(R$string.fq_go_to_index), textPaint2, "img_1409");
        sVGADynamicEntity.setDynamicText(str3, textPaint2, "img_1088");
        sVGADynamicEntity.setDynamicImage(drawable2Bitmap, "img_1076");
        if (TextUtils.equals(str4, "3")) {
            sVGADynamicEntity.setDynamicImage(drawable2Bitmap2, "img_1607");
        } else {
            sVGADynamicEntity.setDynamicImage(drawable2Bitmap2, "img_1474");
        }
        sVGADynamicEntity.setDynamicImage(drawable2Bitmap3, "img_1077");
        if (!isValidContextForGlide(context)) {
            sVGADynamicEntity.setDynamicImage(ImageUtils.drawable2Bitmap(ContextCompat.getDrawable(context, R$drawable.fq_ic_placeholder_avatar)), "img_68");
            return sVGADynamicEntity;
        } else if (!isEncryptionAvatarUrl(str)) {
            GlideApp.with(context).mo6717asBitmap().mo6729load(str).into((GlideRequest<Bitmap>) new SimpleTarget<Bitmap>() { // from class: com.tomatolive.library.utils.GlideUtils.31
                @Override // com.bumptech.glide.request.target.Target
                public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object obj, @Nullable Transition transition) {
                    onResourceReady((Bitmap) obj, (Transition<? super Bitmap>) transition);
                }

                @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
                public void onLoadStarted(@Nullable Drawable drawable) {
                    super.onLoadStarted(drawable);
                    SVGADynamicEntity.this.setDynamicImage(ImageUtils.drawable2Bitmap(ContextCompat.getDrawable(context, R$drawable.fq_ic_placeholder_avatar)), r3);
                }

                public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                    Bitmap formatAnimAvatarImg = GlideUtils.formatAnimAvatarImg(bitmap);
                    if (formatAnimAvatarImg == null) {
                        SVGADynamicEntity.this.setDynamicImage(ImageUtils.drawable2Bitmap(ContextCompat.getDrawable(context, R$drawable.fq_ic_placeholder_avatar)), r3);
                    } else {
                        SVGADynamicEntity.this.setDynamicImage(formatAnimAvatarImg, r3);
                    }
                }

                @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
                public void onLoadFailed(@Nullable Drawable drawable) {
                    super.onLoadFailed(drawable);
                    SVGADynamicEntity.this.setDynamicImage(ImageUtils.drawable2Bitmap(ContextCompat.getDrawable(context, R$drawable.fq_ic_placeholder_avatar)), r3);
                }
            });
            return sVGADynamicEntity;
        } else {
            GlideApp.with(context).mo6719asFile().mo6729load(str).into((GlideRequest<File>) new SimpleTarget<File>() { // from class: com.tomatolive.library.utils.GlideUtils.32
                @Override // com.bumptech.glide.request.target.Target
                public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object obj, @Nullable Transition transition) {
                    onResourceReady((File) obj, (Transition<? super File>) transition);
                }

                @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
                public void onLoadStarted(@Nullable Drawable drawable) {
                    super.onLoadStarted(drawable);
                    SVGADynamicEntity.this.setDynamicImage(ImageUtils.drawable2Bitmap(ContextCompat.getDrawable(context, R$drawable.fq_ic_placeholder_avatar)), r3);
                }

                public void onResourceReady(@NonNull final File file, @Nullable Transition<? super File> transition) {
                    Observable.just(true).map(new Function<Boolean, Bitmap>() { // from class: com.tomatolive.library.utils.GlideUtils.32.2
                        @Override // io.reactivex.functions.Function
                        /* renamed from: apply  reason: avoid collision after fix types in other method */
                        public Bitmap mo6755apply(Boolean bool) {
                            File formatDecodeImage2File = GlideUtils.formatDecodeImage2File(file, str);
                            if (formatDecodeImage2File == null) {
                                return null;
                            }
                            return GlideUtils.formatAnimAvatarImg(ImageUtils.getBitmap(formatDecodeImage2File));
                        }
                    }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Bitmap>() { // from class: com.tomatolive.library.utils.GlideUtils.32.1
                        @Override // io.reactivex.Observer
                        public void onComplete() {
                        }

                        @Override // io.reactivex.Observer
                        public void onError(Throwable th) {
                        }

                        @Override // io.reactivex.Observer
                        public void onSubscribe(Disposable disposable) {
                        }

                        @Override // io.reactivex.Observer
                        public void onNext(Bitmap bitmap) {
                            if (bitmap == null) {
                                C497932 c497932 = C497932.this;
                                SVGADynamicEntity.this.setDynamicImage(ImageUtils.drawable2Bitmap(ContextCompat.getDrawable(context, R$drawable.fq_ic_placeholder_avatar)), r3);
                                return;
                            }
                            C497932 c4979322 = C497932.this;
                            SVGADynamicEntity.this.setDynamicImage(bitmap, r3);
                        }
                    });
                }

                @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
                public void onLoadFailed(@Nullable Drawable drawable) {
                    super.onLoadFailed(drawable);
                    SVGADynamicEntity.this.setDynamicImage(ImageUtils.drawable2Bitmap(ContextCompat.getDrawable(context, R$drawable.fq_ic_placeholder_avatar)), r3);
                }
            });
            return sVGADynamicEntity;
        }
    }

    public static SVGADynamicEntity getCarSVGADynamicEntity(final Context context, final String str, String str2, String str3, String str4) {
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(30.0f);
        textPaint.setFakeBoldText(true);
        textPaint.setColor(-1);
        TextPaint textPaint2 = new TextPaint();
        textPaint2.setTextSize(30.0f);
        textPaint2.setColor(-1);
        textPaint2.setFakeBoldText(true);
        final SVGADynamicEntity sVGADynamicEntity = new SVGADynamicEntity();
        Bitmap drawable2Bitmap = ImageUtils.drawable2Bitmap(ContextCompat.getDrawable(context, AppUtils.getUserGradeBgDrawableRes(str3)));
        Bitmap drawable2Bitmap2 = ImageUtils.drawable2Bitmap(ContextCompat.getDrawable(context, AppUtils.getUserGradeIconResource(true, NumberUtils.string2int(str3))));
        sVGADynamicEntity.setDynamicText(context.getString(R$string.fq_car_anim_enter_tips, AppUtils.formatUserNickName(str2), str4), textPaint, "img_19");
        sVGADynamicEntity.setDynamicText(str3, textPaint2, "img_146");
        sVGADynamicEntity.setDynamicImage(drawable2Bitmap, "img_144");
        sVGADynamicEntity.setDynamicImage(drawable2Bitmap2, "img_159");
        if (!isValidContextForGlide(context)) {
            sVGADynamicEntity.setDynamicImage(ImageUtils.drawable2Bitmap(ContextCompat.getDrawable(context, R$drawable.fq_ic_placeholder_avatar)), "img_21");
            return sVGADynamicEntity;
        } else if (!isEncryptionAvatarUrl(str)) {
            GlideApp.with(context).mo6717asBitmap().mo6729load(str).into((GlideRequest<Bitmap>) new SimpleTarget<Bitmap>() { // from class: com.tomatolive.library.utils.GlideUtils.33
                @Override // com.bumptech.glide.request.target.Target
                public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object obj, @Nullable Transition transition) {
                    onResourceReady((Bitmap) obj, (Transition<? super Bitmap>) transition);
                }

                @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
                public void onLoadStarted(@Nullable Drawable drawable) {
                    super.onLoadStarted(drawable);
                    SVGADynamicEntity.this.setDynamicImage(ImageUtils.drawable2Bitmap(ContextCompat.getDrawable(context, R$drawable.fq_ic_placeholder_avatar)), r3);
                }

                public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                    Bitmap formatAnimAvatarImg = GlideUtils.formatAnimAvatarImg(bitmap);
                    if (formatAnimAvatarImg == null) {
                        SVGADynamicEntity.this.setDynamicImage(ImageUtils.drawable2Bitmap(ContextCompat.getDrawable(context, R$drawable.fq_ic_placeholder_avatar)), r3);
                    } else {
                        SVGADynamicEntity.this.setDynamicImage(formatAnimAvatarImg, r3);
                    }
                }

                @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
                public void onLoadFailed(@Nullable Drawable drawable) {
                    super.onLoadFailed(drawable);
                    SVGADynamicEntity.this.setDynamicImage(ImageUtils.drawable2Bitmap(ContextCompat.getDrawable(context, R$drawable.fq_ic_placeholder_avatar)), r3);
                }
            });
            return sVGADynamicEntity;
        } else {
            GlideApp.with(context).mo6719asFile().mo6729load(str).into((GlideRequest<File>) new SimpleTarget<File>() { // from class: com.tomatolive.library.utils.GlideUtils.34
                @Override // com.bumptech.glide.request.target.Target
                public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object obj, @Nullable Transition transition) {
                    onResourceReady((File) obj, (Transition<? super File>) transition);
                }

                @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
                public void onLoadStarted(@Nullable Drawable drawable) {
                    super.onLoadStarted(drawable);
                    SVGADynamicEntity.this.setDynamicImage(ImageUtils.drawable2Bitmap(ContextCompat.getDrawable(context, R$drawable.fq_ic_placeholder_avatar)), r3);
                }

                public void onResourceReady(@NonNull final File file, @Nullable Transition<? super File> transition) {
                    Observable.just(true).map(new Function<Boolean, Bitmap>() { // from class: com.tomatolive.library.utils.GlideUtils.34.2
                        @Override // io.reactivex.functions.Function
                        /* renamed from: apply  reason: avoid collision after fix types in other method */
                        public Bitmap mo6755apply(Boolean bool) {
                            File formatDecodeImage2File = GlideUtils.formatDecodeImage2File(file, str);
                            if (formatDecodeImage2File == null) {
                                return null;
                            }
                            return GlideUtils.formatAnimAvatarImg(ImageUtils.getBitmap(formatDecodeImage2File));
                        }
                    }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Bitmap>() { // from class: com.tomatolive.library.utils.GlideUtils.34.1
                        @Override // io.reactivex.Observer
                        public void onComplete() {
                        }

                        @Override // io.reactivex.Observer
                        public void onSubscribe(Disposable disposable) {
                        }

                        @Override // io.reactivex.Observer
                        public void onNext(Bitmap bitmap) {
                            if (bitmap == null) {
                                C498334 c498334 = C498334.this;
                                SVGADynamicEntity.this.setDynamicImage(ImageUtils.drawable2Bitmap(ContextCompat.getDrawable(context, R$drawable.fq_ic_placeholder_avatar)), r3);
                                return;
                            }
                            C498334 c4983342 = C498334.this;
                            SVGADynamicEntity.this.setDynamicImage(bitmap, r3);
                        }

                        @Override // io.reactivex.Observer
                        public void onError(Throwable th) {
                            C498334 c498334 = C498334.this;
                            SVGADynamicEntity.this.setDynamicImage(ImageUtils.drawable2Bitmap(ContextCompat.getDrawable(context, R$drawable.fq_ic_placeholder_avatar)), r3);
                        }
                    });
                }

                @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
                public void onLoadFailed(@Nullable Drawable drawable) {
                    super.onLoadFailed(drawable);
                    SVGADynamicEntity.this.setDynamicImage(ImageUtils.drawable2Bitmap(ContextCompat.getDrawable(context, R$drawable.fq_ic_placeholder_avatar)), r3);
                }
            });
            return sVGADynamicEntity;
        }
    }

    public static SVGADynamicEntity getNobilitySVGADynamicEntity(final Context context, final String str, String str2, int i) {
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(23.0f);
        textPaint.setFakeBoldText(true);
        textPaint.setColor(-1);
        final SVGADynamicEntity sVGADynamicEntity = new SVGADynamicEntity();
        sVGADynamicEntity.setDynamicText(AppUtils.formatUserNickName(str2), textPaint, "name");
        if (!isValidContextForGlide(context)) {
            sVGADynamicEntity.setDynamicImage(ImageUtils.drawable2Bitmap(ContextCompat.getDrawable(context, R$drawable.fq_ic_placeholder_avatar)), "border2");
            return sVGADynamicEntity;
        } else if (!isEncryptionAvatarUrl(str)) {
            GlideApp.with(context).mo6717asBitmap().mo6729load(str).into((GlideRequest<Bitmap>) new SimpleTarget<Bitmap>() { // from class: com.tomatolive.library.utils.GlideUtils.35
                @Override // com.bumptech.glide.request.target.Target
                public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object obj, @Nullable Transition transition) {
                    onResourceReady((Bitmap) obj, (Transition<? super Bitmap>) transition);
                }

                @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
                public void onLoadStarted(@Nullable Drawable drawable) {
                    super.onLoadStarted(drawable);
                    SVGADynamicEntity.this.setDynamicImage(ImageUtils.drawable2Bitmap(ContextCompat.getDrawable(context, R$drawable.fq_ic_placeholder_avatar)), r3);
                }

                public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                    Bitmap formatAnimAvatarImg = GlideUtils.formatAnimAvatarImg(bitmap, 104, 104);
                    if (formatAnimAvatarImg == null) {
                        SVGADynamicEntity.this.setDynamicImage(ImageUtils.drawable2Bitmap(ContextCompat.getDrawable(context, R$drawable.fq_ic_placeholder_avatar)), r3);
                    } else {
                        SVGADynamicEntity.this.setDynamicImage(formatAnimAvatarImg, r3);
                    }
                }

                @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
                public void onLoadFailed(@Nullable Drawable drawable) {
                    super.onLoadFailed(drawable);
                    SVGADynamicEntity.this.setDynamicImage(ImageUtils.drawable2Bitmap(ContextCompat.getDrawable(context, R$drawable.fq_ic_placeholder_avatar)), r3);
                }
            });
            return sVGADynamicEntity;
        } else {
            GlideApp.with(context).mo6719asFile().mo6729load(str).into((GlideRequest<File>) new SimpleTarget<File>() { // from class: com.tomatolive.library.utils.GlideUtils.36
                @Override // com.bumptech.glide.request.target.Target
                public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object obj, @Nullable Transition transition) {
                    onResourceReady((File) obj, (Transition<? super File>) transition);
                }

                @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
                public void onLoadStarted(@Nullable Drawable drawable) {
                    super.onLoadStarted(drawable);
                    SVGADynamicEntity.this.setDynamicImage(ImageUtils.drawable2Bitmap(ContextCompat.getDrawable(context, R$drawable.fq_ic_placeholder_avatar)), r3);
                }

                public void onResourceReady(@NonNull final File file, @Nullable Transition<? super File> transition) {
                    Observable.just(true).map(new Function<Boolean, Bitmap>() { // from class: com.tomatolive.library.utils.GlideUtils.36.2
                        @Override // io.reactivex.functions.Function
                        /* renamed from: apply  reason: avoid collision after fix types in other method */
                        public Bitmap mo6755apply(Boolean bool) {
                            File formatDecodeImage2File = GlideUtils.formatDecodeImage2File(file, str);
                            if (formatDecodeImage2File == null) {
                                return null;
                            }
                            return GlideUtils.formatAnimAvatarImg(ImageUtils.getBitmap(formatDecodeImage2File), 104, 104);
                        }
                    }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Bitmap>() { // from class: com.tomatolive.library.utils.GlideUtils.36.1
                        @Override // io.reactivex.Observer
                        public void onComplete() {
                        }

                        @Override // io.reactivex.Observer
                        public void onError(Throwable th) {
                        }

                        @Override // io.reactivex.Observer
                        public void onSubscribe(Disposable disposable) {
                        }

                        @Override // io.reactivex.Observer
                        public void onNext(Bitmap bitmap) {
                            if (bitmap == null) {
                                C498736 c498736 = C498736.this;
                                SVGADynamicEntity.this.setDynamicImage(ImageUtils.drawable2Bitmap(ContextCompat.getDrawable(context, R$drawable.fq_ic_placeholder_avatar)), r3);
                                return;
                            }
                            C498736 c4987362 = C498736.this;
                            SVGADynamicEntity.this.setDynamicImage(bitmap, r3);
                        }
                    });
                }

                @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
                public void onLoadFailed(@Nullable Drawable drawable) {
                    super.onLoadFailed(drawable);
                    SVGADynamicEntity.this.setDynamicImage(ImageUtils.drawable2Bitmap(ContextCompat.getDrawable(context, R$drawable.fq_ic_placeholder_avatar)), r3);
                }
            });
            return sVGADynamicEntity;
        }
    }

    public static SVGADynamicEntity getFirstKillSVGADynamicEntity(final Context context, final String str, final boolean z) {
        final SVGADynamicEntity sVGADynamicEntity = new SVGADynamicEntity();
        if (!isValidContextForGlide(context)) {
            sVGADynamicEntity.setDynamicImage(ImageUtils.drawable2Bitmap(ContextCompat.getDrawable(context, R$drawable.fq_ic_placeholder_avatar)), "img_14");
            return sVGADynamicEntity;
        } else if (!isEncryptionAvatarUrl(str)) {
            GlideApp.with(context).mo6717asBitmap().mo6729load(str).into((GlideRequest<Bitmap>) new SimpleTarget<Bitmap>() { // from class: com.tomatolive.library.utils.GlideUtils.37
                @Override // com.bumptech.glide.request.target.Target
                public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object obj, @Nullable Transition transition) {
                    onResourceReady((Bitmap) obj, (Transition<? super Bitmap>) transition);
                }

                @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
                public void onLoadStarted(@Nullable Drawable drawable) {
                    super.onLoadStarted(drawable);
                    SVGADynamicEntity.this.setDynamicImage(ImageUtils.drawable2Bitmap(ContextCompat.getDrawable(context, R$drawable.fq_ic_placeholder_avatar)), r3);
                }

                public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                    Bitmap formatAnimAvatarImg = GlideUtils.formatAnimAvatarImg(bitmap, Color.parseColor(z ? "#FF5252" : "#527DFF"));
                    if (formatAnimAvatarImg == null) {
                        SVGADynamicEntity.this.setDynamicImage(ImageUtils.drawable2Bitmap(ContextCompat.getDrawable(context, R$drawable.fq_ic_placeholder_avatar)), r3);
                    } else {
                        SVGADynamicEntity.this.setDynamicImage(formatAnimAvatarImg, r3);
                    }
                }

                @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
                public void onLoadFailed(@Nullable Drawable drawable) {
                    super.onLoadFailed(drawable);
                    SVGADynamicEntity.this.setDynamicImage(ImageUtils.drawable2Bitmap(ContextCompat.getDrawable(context, R$drawable.fq_ic_placeholder_avatar)), r3);
                }
            });
            return sVGADynamicEntity;
        } else {
            GlideApp.with(context).mo6719asFile().mo6729load(str).into((GlideRequest<File>) new SimpleTarget<File>() { // from class: com.tomatolive.library.utils.GlideUtils.38
                @Override // com.bumptech.glide.request.target.Target
                public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object obj, @Nullable Transition transition) {
                    onResourceReady((File) obj, (Transition<? super File>) transition);
                }

                @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
                public void onLoadStarted(@Nullable Drawable drawable) {
                    super.onLoadStarted(drawable);
                    SVGADynamicEntity.this.setDynamicImage(ImageUtils.drawable2Bitmap(ContextCompat.getDrawable(context, R$drawable.fq_ic_placeholder_avatar)), r3);
                }

                public void onResourceReady(@NonNull final File file, @Nullable Transition<? super File> transition) {
                    Observable.just(true).map(new Function<Boolean, Bitmap>() { // from class: com.tomatolive.library.utils.GlideUtils.38.2
                        @Override // io.reactivex.functions.Function
                        /* renamed from: apply  reason: avoid collision after fix types in other method */
                        public Bitmap mo6755apply(Boolean bool) {
                            File formatDecodeImage2File = GlideUtils.formatDecodeImage2File(file, str);
                            if (formatDecodeImage2File == null) {
                                return null;
                            }
                            return GlideUtils.formatAnimAvatarImg(ImageUtils.getBitmap(formatDecodeImage2File), Color.parseColor(z ? "#FF5252" : "#527DFF"));
                        }
                    }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Bitmap>() { // from class: com.tomatolive.library.utils.GlideUtils.38.1
                        @Override // io.reactivex.Observer
                        public void onComplete() {
                        }

                        @Override // io.reactivex.Observer
                        public void onError(Throwable th) {
                        }

                        @Override // io.reactivex.Observer
                        public void onSubscribe(Disposable disposable) {
                        }

                        @Override // io.reactivex.Observer
                        public void onNext(Bitmap bitmap) {
                            if (bitmap == null) {
                                C499138 c499138 = C499138.this;
                                SVGADynamicEntity.this.setDynamicImage(ImageUtils.drawable2Bitmap(ContextCompat.getDrawable(context, R$drawable.fq_ic_placeholder_avatar)), r3);
                                return;
                            }
                            C499138 c4991382 = C499138.this;
                            SVGADynamicEntity.this.setDynamicImage(bitmap, r3);
                        }
                    });
                }

                @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
                public void onLoadFailed(@Nullable Drawable drawable) {
                    super.onLoadFailed(drawable);
                    SVGADynamicEntity.this.setDynamicImage(ImageUtils.drawable2Bitmap(ContextCompat.getDrawable(context, R$drawable.fq_ic_placeholder_avatar)), r3);
                }
            });
            return sVGADynamicEntity;
        }
    }

    public static Bitmap scaleImage(Bitmap bitmap, int i, int i2) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(i / width, i2 / height);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    public static Bitmap getBorderBitmap(Bitmap bitmap, int i, int i2) {
        if (bitmap == null) {
            return null;
        }
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        int min = Math.min(bitmap.getWidth(), bitmap.getHeight());
        float f = min;
        RectF rectF = new RectF(0.0f, 0.0f, f, f);
        float f2 = i;
        float f3 = min - i;
        RectF rectF2 = new RectF(f2, f2, f3, f3);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRoundRect(rectF2, rectF2.centerX(), rectF2.centerY(), paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, (Rect) null, rectF2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
        paint.setColor(i2);
        canvas.drawRoundRect(rectF, f, f, paint);
        return createBitmap;
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int i) {
        if (bitmap == null) {
            return null;
        }
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        int min = Math.min(bitmap.getWidth(), bitmap.getHeight());
        Rect rect = new Rect(0, 0, min, min);
        RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(-12434878);
        float f = i;
        canvas.drawRoundRect(rectF, f, f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return createBitmap;
    }

    public static void downloadFile2Drawable(Context context, final String str, final OnGlideDownloadCallback<Drawable> onGlideDownloadCallback) {
        if (context == null) {
            return;
        }
        GlideApp.with(context).mo6719asFile().mo6729load(str).into((GlideRequest<File>) new SimpleTarget<File>() { // from class: com.tomatolive.library.utils.GlideUtils.39
            @Override // com.bumptech.glide.request.target.Target
            public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object obj, @Nullable Transition transition) {
                onResourceReady((File) obj, (Transition<? super File>) transition);
            }

            @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
            public void onLoadStarted(@Nullable Drawable drawable) {
                super.onLoadStarted(drawable);
                OnGlideDownloadCallback onGlideDownloadCallback2 = OnGlideDownloadCallback.this;
                if (onGlideDownloadCallback2 != null) {
                    onGlideDownloadCallback2.onLoadStarted(drawable);
                }
            }

            public void onResourceReady(@NonNull File file, @Nullable Transition<? super File> transition) {
                GlideUtils.saveFileByLocalCache(str, file, OnGlideDownloadCallback.this);
            }

            @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
            public void onLoadFailed(@Nullable Drawable drawable) {
                super.onLoadFailed(drawable);
                OnGlideDownloadCallback onGlideDownloadCallback2 = OnGlideDownloadCallback.this;
                if (onGlideDownloadCallback2 != null) {
                    onGlideDownloadCallback2.onLoadFailed(drawable);
                }
            }
        });
    }

    public static Drawable getLocalCacheFile2Drawable(String str) {
        if (!TextUtils.isEmpty(str) && isLocalCachePath(str)) {
            String cacheMapGet = cacheMapGet(str);
            if (FileUtils.isExist(cacheMapGet)) {
                return new BitmapDrawable(Utils.getApp().getResources(), cacheMapGet);
            }
            return null;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void saveFileByLocalCache(final String str, File file, final OnGlideDownloadCallback<Drawable> onGlideDownloadCallback) {
        Observable.just(formatDecodeImage2File(file, str)).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<File>() { // from class: com.tomatolive.library.utils.GlideUtils.40
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(File file2) {
                if (file2 == null) {
                    OnGlideDownloadCallback onGlideDownloadCallback2 = OnGlideDownloadCallback.this;
                    if (onGlideDownloadCallback2 == null) {
                        return;
                    }
                    onGlideDownloadCallback2.onLoadFailed(null);
                    return;
                }
                GlideUtils.cacheMapPut(str, file2);
                if (OnGlideDownloadCallback.this == null) {
                    return;
                }
                OnGlideDownloadCallback.this.onLoadSuccess(new BitmapDrawable(Utils.getApp().getResources(), file2.getAbsolutePath()));
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
                OnGlideDownloadCallback onGlideDownloadCallback2 = OnGlideDownloadCallback.this;
                if (onGlideDownloadCallback2 != null) {
                    onGlideDownloadCallback2.onLoadFailed(null);
                }
            }
        });
    }

    public static Drawable getDrawableByGlide(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            return Glide.with(context).mo6718asDrawable().mo6729load(formatDownUrl(str)).submit(80, 80).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static Bitmap getBitmapByGlide(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            return Glide.with(context).mo6717asBitmap().mo6729load(formatDownUrl(str)).submit(80, 80).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Bitmap formatAnimAvatarImg(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        return getRoundedCornerBitmap(scaleImage(bitmap, 426, 426), 360);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Bitmap formatAnimAvatarImg(Bitmap bitmap, int i) {
        if (bitmap == null) {
            return null;
        }
        return getBorderBitmap(scaleImage(bitmap, 426, 426), ConvertUtils.dp2px(12.0f), i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Bitmap formatAnimAvatarImg(Bitmap bitmap, int i, int i2) {
        if (bitmap == null) {
            return null;
        }
        return getRoundedCornerBitmap(scaleImage(bitmap, i, i2), 360);
    }
}
