package com.one.tomato.utils.image;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.broccoli.p150bh.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.mvp.p080ui.post.utils.PostUtils;
import com.one.tomato.thirdpart.domain.DomainRequest;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.utils.AppSecretUtil;
import com.one.tomato.utils.CrashHandler;
import com.one.tomato.utils.FileUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.encrypt.MD5Util;
import com.shizhefei.view.largeimage.LargeImageView;
import com.shizhefei.view.largeimage.factory.FileBitmapDecoderFactory;
import java.io.File;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public class ImageLoaderUtil {
    public static void loadNormalAbsoluteImage(Context context, ImageView imageView, String str, RequestOptions requestOptions) {
        if (context == null || TextUtils.isEmpty(str)) {
            return;
        }
        if ((context instanceof Activity) && ((Activity) context).isDestroyed()) {
            return;
        }
        LogUtil.m3784i("loadNormalAbsoluteImage: " + str);
        Glide.with(context).mo6728load((Object) new ImageGlideUrl("", str)).mo6653apply((BaseRequestOptions<?>) requestOptions).mo6676listener(new RequestListener<Drawable>() { // from class: com.one.tomato.utils.image.ImageLoaderUtil.1
            @Override // com.bumptech.glide.request.RequestListener
            public boolean onResourceReady(Drawable drawable, Object obj, Target<Drawable> target, DataSource dataSource, boolean z) {
                return false;
            }

            @Override // com.bumptech.glide.request.RequestListener
            public boolean onLoadFailed(@Nullable GlideException glideException, Object obj, Target<Drawable> target, boolean z) {
                ImageLoaderUtil.commonLoadFail();
                return false;
            }
        }).into(imageView);
    }

    public static void loadSecAbsoluteImage(final Context context, final ImageView imageView, final String str, RequestOptions requestOptions) {
        if (context == null || TextUtils.isEmpty(str)) {
            return;
        }
        if ((context instanceof Activity) && ((Activity) context).isDestroyed()) {
            return;
        }
        LogUtil.m3784i("loadNormalAbsoluteImage: " + str);
        imageView.setTag(R.id.glide_load_image_id, str);
        Glide.with(context).mo6719asFile().mo6728load((Object) new ImageGlideUrl("", str)).mo6676listener(new MyRequestListener("loadHeadImage: " + str)).into((RequestBuilder<File>) new SimpleTarget<File>() { // from class: com.one.tomato.utils.image.ImageLoaderUtil.2
            @Override // com.bumptech.glide.request.target.Target
            public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object obj, @Nullable Transition transition) {
                onResourceReady((File) obj, (Transition<? super File>) transition);
            }

            @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
            public void onLoadStarted(@Nullable Drawable drawable) {
                super.onLoadStarted(drawable);
            }

            public void onResourceReady(@NonNull File file, @Nullable Transition<? super File> transition) {
                ImageLoaderUtil.commonLoadSuccess(context, imageView, new ImageBean(str), str, file, ImageLoaderUtil.getDefaultImageOptions(imageView));
            }

            @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
            public void onLoadFailed(@Nullable Drawable drawable) {
                super.onLoadFailed(drawable);
                ImageLoaderUtil.commonLoadFail();
            }
        });
    }

    public static void loadNormalLocalImage(Context context, ImageView imageView, String str, RequestOptions requestOptions) {
        if (context == null) {
            return;
        }
        if ((context instanceof Activity) && ((Activity) context).isDestroyed()) {
            return;
        }
        if (str.endsWith("GIF") || str.endsWith("gif")) {
            Glide.with(context).mo6720asGif().mo6729load(str).mo6653apply((BaseRequestOptions<?>) requestOptions).into(imageView);
        } else {
            Glide.with(context).mo6717asBitmap().mo6729load(str).mo6653apply((BaseRequestOptions<?>) requestOptions).into(imageView);
        }
    }

    public static void loadNormalDrawableImg(Context context, ImageView imageView, int i) {
        if (context == null) {
            return;
        }
        if ((context instanceof Activity) && ((Activity) context).isDestroyed()) {
            return;
        }
        Glide.with(context).mo6717asBitmap().mo6727load(Integer.valueOf(i)).into(imageView);
    }

    public static void loadNormalDrawableGif(Context context, ImageView imageView, int i) {
        if (context == null) {
            return;
        }
        if ((context instanceof Activity) && ((Activity) context).isDestroyed()) {
            return;
        }
        Glide.with(context).mo6720asGif().mo6727load(Integer.valueOf(i)).into(imageView);
    }

    public static void loadCircleLogo(final Context context, final ImageView imageView, final ImageBean imageBean) {
        if (context == null) {
            return;
        }
        if ((context instanceof Activity) && ((Activity) context).isDestroyed()) {
            return;
        }
        imageView.setTag(R.id.glide_load_image_id, imageBean.getImage());
        String ttViewPicture = DomainServer.getInstance().getTtViewPicture();
        final String image = imageBean.getImage();
        String str = "loadCircleLogo: " + ttViewPicture + image;
        LogUtil.m3784i(str);
        Glide.with(context).mo6719asFile().mo6728load((Object) new ImageGlideUrl(ttViewPicture, image)).mo6676listener(new MyRequestListener(str)).into((RequestBuilder<File>) new SimpleTarget<File>() { // from class: com.one.tomato.utils.image.ImageLoaderUtil.3
            @Override // com.bumptech.glide.request.target.Target
            public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object obj, @Nullable Transition transition) {
                onResourceReady((File) obj, (Transition<? super File>) transition);
            }

            @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
            public void onLoadStarted(@Nullable Drawable drawable) {
                super.onLoadStarted(drawable);
                imageView.setImageResource(R.drawable.default_img_circle);
            }

            public void onResourceReady(@NonNull File file, @Nullable Transition<? super File> transition) {
                Context context2 = context;
                ImageView imageView2 = imageView;
                ImageLoaderUtil.commonLoadSuccess(context2, imageView2, imageBean, image, file, ImageLoaderUtil.getRoundImageOption(imageView2));
            }

            @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
            public void onLoadFailed(@Nullable Drawable drawable) {
                super.onLoadFailed(drawable);
                ImageLoaderUtil.commonLoadFail();
            }
        });
    }

    public static void loadHeadImage(Context context, ImageView imageView, ImageBean imageBean) {
        if (!(context instanceof Activity) || !((Activity) context).isDestroyed()) {
            loadHeadImage(context, imageView, imageBean, DomainServer.getInstance().getTtViewPicture(), compressHeadImage(imageBean.getImage(), imageBean.isGif()));
        }
    }

    public static void loadHeadImage(Context context, ImageView imageView, ImageBean imageBean, int i, int i2) {
        if (!(context instanceof Activity) || !((Activity) context).isDestroyed()) {
            loadHeadImage(context, imageView, imageBean, DomainServer.getInstance().getTtViewPicture(), compressHeadImage(imageBean.getImage(), imageBean.isGif(), i, i2));
        }
    }

    private static void loadHeadImage(final Context context, final ImageView imageView, final ImageBean imageBean, String str, final String str2) {
        String str3 = "loadHeadImage: " + str + str2;
        LogUtil.m3784i(str3);
        imageView.setTag(R.id.glide_load_image_id, imageBean.getImage());
        Glide.with(context).mo6719asFile().mo6728load((Object) new ImageGlideUrl(str, str2)).mo6676listener(new MyRequestListener(str3)).into((RequestBuilder<File>) new SimpleTarget<File>() { // from class: com.one.tomato.utils.image.ImageLoaderUtil.4
            @Override // com.bumptech.glide.request.target.Target
            public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object obj, @Nullable Transition transition) {
                onResourceReady((File) obj, (Transition<? super File>) transition);
            }

            @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
            public void onLoadStarted(@Nullable Drawable drawable) {
                super.onLoadStarted(drawable);
                imageView.setImageResource(R.drawable.default_img_head);
            }

            public void onResourceReady(@NonNull File file, @Nullable Transition<? super File> transition) {
                imageBean.setHeader(true);
                Context context2 = context;
                ImageView imageView2 = imageView;
                ImageLoaderUtil.commonLoadSuccess(context2, imageView2, imageBean, str2, file, ImageLoaderUtil.getHeadImageOption(imageView2));
            }

            @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
            public void onLoadFailed(@Nullable Drawable drawable) {
                super.onLoadFailed(drawable);
                ImageLoaderUtil.commonLoadFail();
            }
        });
    }

    public static void loadRecyclerThumbImage(final Context context, final ImageView imageView, final ImageBean imageBean) {
        if (context == null) {
            return;
        }
        if ((context instanceof Activity) && ((Activity) context).isDestroyed()) {
            return;
        }
        imageView.setTag(R.id.glide_load_image_id, imageBean.getImage());
        boolean isGif = imageBean.isGif();
        boolean isLongImg = imageBean.isLongImg();
        String ttViewPicture = DomainServer.getInstance().getTtViewPicture();
        final String compressViewPagerImage = compressViewPagerImage(imageBean.getImage(), isGif, isLongImg);
        String str = "loadRecyclerThumbImage: " + ttViewPicture + compressViewPagerImage;
        LogUtil.m3784i(str);
        Glide.with(context).mo6719asFile().mo6728load((Object) new ImageGlideUrl(ttViewPicture, compressViewPagerImage)).mo6676listener(new MyRequestListener(str)).into((RequestBuilder<File>) new SimpleTarget<File>() { // from class: com.one.tomato.utils.image.ImageLoaderUtil.5
            @Override // com.bumptech.glide.request.target.Target
            public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object obj, @Nullable Transition transition) {
                onResourceReady((File) obj, (Transition<? super File>) transition);
            }

            @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
            public void onLoadStarted(@Nullable Drawable drawable) {
                super.onLoadStarted(drawable);
            }

            public void onResourceReady(@NonNull File file, @Nullable Transition<? super File> transition) {
                Context context2 = context;
                ImageView imageView2 = imageView;
                ImageLoaderUtil.commonLoadSuccess(context2, imageView2, imageBean, compressViewPagerImage, file, ImageLoaderUtil.getDefaultImageOptions(imageView2));
            }

            @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
            public void onLoadFailed(@Nullable Drawable drawable) {
                super.onLoadFailed(drawable);
                ImageLoaderUtil.commonLoadFail();
            }
        });
    }

    public static void loadRecyclerThumbSamllImage(final Context context, final ImageView imageView, final ImageBean imageBean) {
        if (context == null) {
            return;
        }
        if ((context instanceof Activity) && ((Activity) context).isDestroyed()) {
            return;
        }
        imageView.setTag(R.id.glide_load_image_id, imageBean.getImage());
        boolean isGif = imageBean.isGif();
        String ttViewPicture = DomainServer.getInstance().getTtViewPicture();
        final String compressViewPagerImageWidthAndHeight = compressViewPagerImageWidthAndHeight(imageBean.getImage(), isGif);
        String str = "loadRecyclerThumbSamllImage: " + ttViewPicture + compressViewPagerImageWidthAndHeight;
        LogUtil.m3784i(str);
        Glide.with(context).mo6719asFile().mo6728load((Object) new ImageGlideUrl(ttViewPicture, compressViewPagerImageWidthAndHeight)).mo6676listener(new MyRequestListener(str)).into((RequestBuilder<File>) new SimpleTarget<File>() { // from class: com.one.tomato.utils.image.ImageLoaderUtil.6
            @Override // com.bumptech.glide.request.target.Target
            public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object obj, @Nullable Transition transition) {
                onResourceReady((File) obj, (Transition<? super File>) transition);
            }

            @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
            public void onLoadStarted(@Nullable Drawable drawable) {
                super.onLoadStarted(drawable);
            }

            public void onResourceReady(@NonNull File file, @Nullable Transition<? super File> transition) {
                Context context2 = context;
                ImageView imageView2 = imageView;
                ImageLoaderUtil.commonLoadSuccess(context2, imageView2, imageBean, compressViewPagerImageWidthAndHeight, file, ImageLoaderUtil.getDefaultImageOptions(imageView2));
            }

            @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
            public void onLoadFailed(@Nullable Drawable drawable) {
                super.onLoadFailed(drawable);
                ImageLoaderUtil.commonLoadFail();
            }
        });
    }

    public static void loadRecyclerThumbImageNoCrop(final Context context, final ImageView imageView, final ImageBean imageBean) {
        if (context == null) {
            return;
        }
        if ((context instanceof Activity) && ((Activity) context).isDestroyed()) {
            return;
        }
        imageView.setTag(R.id.glide_load_image_id, imageBean.getImage());
        boolean isGif = imageBean.isGif();
        boolean isLongImg = imageBean.isLongImg();
        String ttViewPicture = DomainServer.getInstance().getTtViewPicture();
        final String compressViewPagerImage = compressViewPagerImage(imageBean.getImage(), isGif, isLongImg);
        String str = "loadRecyclerThumbImageNoCrop: " + ttViewPicture + compressViewPagerImage;
        LogUtil.m3784i(str);
        Glide.with(context).mo6719asFile().mo6728load((Object) new ImageGlideUrl(ttViewPicture, compressViewPagerImage)).mo6676listener(new MyRequestListener(str)).into((RequestBuilder<File>) new SimpleTarget<File>() { // from class: com.one.tomato.utils.image.ImageLoaderUtil.7
            @Override // com.bumptech.glide.request.target.Target
            public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object obj, @Nullable Transition transition) {
                onResourceReady((File) obj, (Transition<? super File>) transition);
            }

            @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
            public void onLoadStarted(@Nullable Drawable drawable) {
                super.onLoadStarted(drawable);
            }

            public void onResourceReady(@NonNull File file, @Nullable Transition<? super File> transition) {
                Context context2 = context;
                ImageView imageView2 = imageView;
                ImageLoaderUtil.commonLoadSuccess(context2, imageView2, imageBean, compressViewPagerImage, file, ImageLoaderUtil.getCenterInsideImageOption(imageView2));
            }

            @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
            public void onLoadFailed(@Nullable Drawable drawable) {
                super.onLoadFailed(drawable);
                ImageLoaderUtil.commonLoadFail();
            }
        });
    }

    public static void loadViewPagerOriginImage(final Context context, final ImageView imageView, final ProgressBar progressBar, final ImageBean imageBean, int i) {
        if (context == null) {
            return;
        }
        if ((context instanceof Activity) && ((Activity) context).isDestroyed()) {
            return;
        }
        imageView.setTag(R.id.glide_load_image_id, imageBean.getImage());
        boolean isGif = imageBean.isGif();
        final boolean isLongImg = imageBean.isLongImg();
        String ttViewPicture = DomainServer.getInstance().getTtViewPicture();
        final String compressViewPagerImage = compressViewPagerImage(imageBean.getImage(), isGif, isLongImg);
        String str = "loadViewPagerOriginImage: " + ttViewPicture + compressViewPagerImage;
        LogUtil.m3784i(str);
        Glide.with(context).mo6719asFile().mo6728load((Object) new ImageGlideUrl(ttViewPicture, compressViewPagerImage)).mo6676listener(new MyRequestListener(str)).into((RequestBuilder<File>) new SimpleTarget<File>() { // from class: com.one.tomato.utils.image.ImageLoaderUtil.8
            @Override // com.bumptech.glide.request.target.Target
            public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object obj, @Nullable Transition transition) {
                onResourceReady((File) obj, (Transition<? super File>) transition);
            }

            @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
            public void onLoadStarted(@Nullable Drawable drawable) {
                super.onLoadStarted(drawable);
                ProgressBar progressBar2 = progressBar;
                if (progressBar2 != null) {
                    progressBar2.setVisibility(0);
                }
            }

            public void onResourceReady(@NonNull File file, @Nullable Transition<? super File> transition) {
                Context context2 = context;
                ImageView imageView2 = imageView;
                ImageLoaderUtil.commonLoadSuccess(context2, imageView2, imageBean, compressViewPagerImage, file, isLongImg ? ImageLoaderUtil.getDefaultImageOptions(imageView2) : ImageLoaderUtil.getCenterInsideImageOption(imageView2));
                ProgressBar progressBar2 = progressBar;
                if (progressBar2 != null) {
                    progressBar2.setVisibility(8);
                }
            }

            @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
            public void onLoadFailed(@Nullable Drawable drawable) {
                super.onLoadFailed(drawable);
                ProgressBar progressBar2 = progressBar;
                if (progressBar2 != null) {
                    progressBar2.setVisibility(8);
                }
                ImageLoaderUtil.commonLoadFail();
            }
        });
    }

    public static void loadViewPagerOriginImageBlurs(final Context context, final ImageView imageView, final ProgressBar progressBar, final ImageBean imageBean, final Function1<Bitmap, Unit> function1) {
        if (context == null) {
            return;
        }
        if ((context instanceof Activity) && ((Activity) context).isDestroyed()) {
            return;
        }
        imageView.setTag(R.id.glide_load_image_id, imageBean.getImage());
        boolean isGif = imageBean.isGif();
        boolean isLongImg = imageBean.isLongImg();
        String ttViewPicture = DomainServer.getInstance().getTtViewPicture();
        final String compressViewPagerImage = compressViewPagerImage(imageBean.getImage(), isGif, isLongImg);
        String str = "loadViewPagerOriginImageBlurs: " + ttViewPicture + compressViewPagerImage;
        LogUtil.m3784i(str);
        Glide.with(context).mo6719asFile().mo6728load((Object) new ImageGlideUrl(ttViewPicture, compressViewPagerImage)).mo6676listener(new MyRequestListener(str)).into((RequestBuilder<File>) new SimpleTarget<File>() { // from class: com.one.tomato.utils.image.ImageLoaderUtil.9
            @Override // com.bumptech.glide.request.target.Target
            public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object obj, @Nullable Transition transition) {
                onResourceReady((File) obj, (Transition<? super File>) transition);
            }

            @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
            public void onLoadStarted(@Nullable Drawable drawable) {
                super.onLoadStarted(drawable);
                ProgressBar progressBar2 = progressBar;
                if (progressBar2 != null) {
                    progressBar2.setVisibility(0);
                }
            }

            public void onResourceReady(@NonNull File file, @Nullable Transition<? super File> transition) {
                Context context2 = context;
                ImageView imageView2 = imageView;
                ImageLoaderUtil.commonLoadSuccessBlurs(context2, imageView2, imageBean, compressViewPagerImage, file, ImageLoaderUtil.getCenterInsideImageOption(imageView2), function1);
                ProgressBar progressBar2 = progressBar;
                if (progressBar2 != null) {
                    progressBar2.setVisibility(8);
                }
            }

            @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
            public void onLoadFailed(@Nullable Drawable drawable) {
                super.onLoadFailed(drawable);
                ProgressBar progressBar2 = progressBar;
                if (progressBar2 != null) {
                    progressBar2.setVisibility(8);
                }
                ImageLoaderUtil.commonLoadFail();
            }
        });
    }

    public static void loadViewPagerLongImage(Context context, final LargeImageView largeImageView, final ProgressBar progressBar, final ImageBean imageBean) {
        if (context == null) {
            return;
        }
        if ((context instanceof Activity) && ((Activity) context).isDestroyed()) {
            return;
        }
        String ttViewPicture = DomainServer.getInstance().getTtViewPicture();
        String image = imageBean.getImage();
        String str = "loadViewPagerLongImage: " + ttViewPicture + image;
        LogUtil.m3784i(str);
        Glide.with(context).mo6719asFile().mo6728load((Object) new ImageGlideUrl(ttViewPicture, image)).mo6676listener(new MyRequestListener(str)).into((RequestBuilder<File>) new SimpleTarget<File>() { // from class: com.one.tomato.utils.image.ImageLoaderUtil.10
            @Override // com.bumptech.glide.request.target.Target
            public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object obj, @Nullable Transition transition) {
                onResourceReady((File) obj, (Transition<? super File>) transition);
            }

            @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
            public void onLoadStarted(@Nullable Drawable drawable) {
                super.onLoadStarted(drawable);
                ProgressBar progressBar2 = progressBar;
                if (progressBar2 != null) {
                    progressBar2.setVisibility(0);
                }
            }

            public void onResourceReady(@NonNull File file, @Nullable Transition<? super File> transition) {
                File imageCacheDir = FileUtil.getImageCacheDir();
                File file2 = new File(imageCacheDir, MD5Util.md5(imageBean.getImage()) + "");
                if (!file2.exists()) {
                    AppSecretUtil.decodeS3Image(file, file2);
                }
                largeImageView.setImage(new FileBitmapDecoderFactory(file2));
                ProgressBar progressBar2 = progressBar;
                if (progressBar2 != null) {
                    progressBar2.setVisibility(8);
                }
            }

            @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
            public void onLoadFailed(@Nullable Drawable drawable) {
                super.onLoadFailed(drawable);
                ProgressBar progressBar2 = progressBar;
                if (progressBar2 != null) {
                    progressBar2.setVisibility(8);
                }
                ImageLoaderUtil.commonLoadFail();
            }
        });
    }

    public static void loadVerifySecImage(final Context context, final ImageView imageView, String str, final ImageBean imageBean, RequestOptions requestOptions) {
        if (context == null) {
            return;
        }
        if ((context instanceof Activity) && ((Activity) context).isDestroyed()) {
            return;
        }
        String str2 = "loadVerifySecImage: " + str + imageBean.getImage();
        LogUtil.m3784i(str2);
        imageView.setTag(R.id.glide_load_image_id, imageBean.getImage());
        Glide.with(context).mo6719asFile().mo6728load((Object) new ImageGlideUrl(str, imageBean.getImage())).mo6676listener(new MyRequestListener(str2)).mo6653apply((BaseRequestOptions<?>) requestOptions).into((RequestBuilder<File>) new SimpleTarget<File>() { // from class: com.one.tomato.utils.image.ImageLoaderUtil.11
            @Override // com.bumptech.glide.request.target.Target
            public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object obj, @Nullable Transition transition) {
                onResourceReady((File) obj, (Transition<? super File>) transition);
            }

            @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
            public void onLoadStarted(@Nullable Drawable drawable) {
                super.onLoadStarted(drawable);
            }

            public void onResourceReady(@NonNull File file, @Nullable Transition<? super File> transition) {
                Context context2 = context;
                ImageView imageView2 = imageView;
                ImageBean imageBean2 = imageBean;
                ImageLoaderUtil.commonLoadSuccess(context2, imageView2, imageBean2, imageBean2.getImage(), file, ImageLoaderUtil.getDefaultImageOptionWhite(imageView));
            }

            @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
            public void onLoadFailed(@Nullable Drawable drawable) {
                super.onLoadFailed(drawable);
                ImageLoaderUtil.commonLoadFail();
            }
        });
    }

    public static void loadSecImage(final Context context, final ImageView imageView, final ImageBean imageBean, final RequestOptions requestOptions) {
        if (context == null) {
            return;
        }
        if ((context instanceof Activity) && ((Activity) context).isDestroyed()) {
            return;
        }
        String ttViewPicture = DomainServer.getInstance().getTtViewPicture();
        imageView.setTag(R.id.glide_load_image_id, imageBean.getImage());
        String str = "loadSecImage: " + ttViewPicture + imageBean.getImage();
        LogUtil.m3784i(str);
        Glide.with(context).mo6719asFile().mo6728load((Object) new ImageGlideUrl(ttViewPicture, imageBean.getImage())).mo6676listener(new MyRequestListener(str)).mo6653apply((BaseRequestOptions<?>) requestOptions).into((RequestBuilder<File>) new SimpleTarget<File>() { // from class: com.one.tomato.utils.image.ImageLoaderUtil.12
            @Override // com.bumptech.glide.request.target.Target
            public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object obj, @Nullable Transition transition) {
                onResourceReady((File) obj, (Transition<? super File>) transition);
            }

            @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
            public void onLoadStarted(@Nullable Drawable drawable) {
                super.onLoadStarted(drawable);
                imageView.setImageResource(R.drawable.default_img_grey);
            }

            public void onResourceReady(@NonNull File file, @Nullable Transition<? super File> transition) {
                Context context2 = context;
                ImageView imageView2 = imageView;
                ImageBean imageBean2 = imageBean;
                ImageLoaderUtil.commonLoadSuccess(context2, imageView2, imageBean2, imageBean2.getImage(), file, requestOptions);
            }

            @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
            public void onLoadFailed(@Nullable Drawable drawable) {
                super.onLoadFailed(drawable);
                ImageLoaderUtil.commonLoadFail();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void commonLoadSuccess(Context context, ImageView imageView, ImageBean imageBean, String str, File file, RequestOptions requestOptions) {
        if (context == null) {
            return;
        }
        if ((context instanceof Activity) && ((Activity) context).isDestroyed()) {
            return;
        }
        try {
            imageBean.isGif();
            File imageCacheDir = FileUtil.getImageCacheDir();
            File file2 = new File(imageCacheDir, MD5Util.md5(str) + "");
            if (!file2.exists()) {
                AppSecretUtil.decodeS3Image(file, file2);
            }
            if (imageBean.isGif()) {
                if (imageView == null) {
                    return;
                }
                String str2 = (String) imageView.getTag(R.id.glide_load_image_id);
                if (TextUtils.isEmpty(str2) || !str2.equals(imageBean.getImage())) {
                    return;
                }
                Glide.with(context).mo6720asGif().mo6726load(file2).mo6653apply((BaseRequestOptions<?>) requestOptions).into(imageView);
            } else if (imageView == null) {
            } else {
                String str3 = (String) imageView.getTag(R.id.glide_load_image_id);
                if (TextUtils.isEmpty(str3) || !str3.equals(imageBean.getImage())) {
                    return;
                }
                Glide.with(context).mo6717asBitmap().mo6726load(file2).mo6653apply((BaseRequestOptions<?>) requestOptions).into(imageView);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.m3786e("图片url：" + str + ",下载成功，加载异常");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void commonLoadSuccessBlurs(Context context, ImageView imageView, ImageBean imageBean, String str, File file, RequestOptions requestOptions, Function1<Bitmap, Unit> function1) {
        imageBean.isGif();
        File imageCacheDir = FileUtil.getImageCacheDir();
        File file2 = new File(imageCacheDir, MD5Util.md5(str) + "");
        if (!file2.exists()) {
            AppSecretUtil.decodeS3Image(file, file2);
        }
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.fromFile(file2));
            PostUtils.INSTANCE.blurBitmap(bitmap, function1);
            if (bitmap != null) {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (imageBean.isGif()) {
            if (imageView == null) {
                return;
            }
            String str2 = (String) imageView.getTag(R.id.glide_load_image_id);
            if (TextUtils.isEmpty(str2) || !str2.equals(imageBean.getImage())) {
                return;
            }
            Glide.with(context).mo6720asGif().mo6726load(file2).mo6653apply((BaseRequestOptions<?>) requestOptions).into(imageView);
        } else if (imageView == null) {
        } else {
            String str3 = (String) imageView.getTag(R.id.glide_load_image_id);
            if (TextUtils.isEmpty(str3) || !str3.equals(imageBean.getImage())) {
                return;
            }
            Glide.with(context).mo6717asBitmap().mo6726load(file2).mo6653apply((BaseRequestOptions<?>) requestOptions).into(imageView);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void commonLoadFail() {
        DomainRequest.getInstance().switchDomainUrlByType("ttViewPicture");
    }

    public static RequestOptions getDefaultImageOptions(ImageView imageView) {
        return getRequestOptions(imageView, ImageView.ScaleType.CENTER_CROP, R.drawable.default_img_grey, R.drawable.default_img_grey, false);
    }

    public static RequestOptions getDefaultImageOptionWhite(ImageView imageView) {
        return getRequestOptions(imageView, ImageView.ScaleType.CENTER_CROP, R.drawable.default_imag_white, R.drawable.default_imag_white, false);
    }

    public static RequestOptions getHeadImageOption(ImageView imageView) {
        return getRequestOptions(imageView, ImageView.ScaleType.CENTER_CROP, R.drawable.default_img_head, R.drawable.default_img_head, true);
    }

    public static RequestOptions getRoundImageOption(ImageView imageView) {
        return getRequestOptions(imageView, ImageView.ScaleType.CENTER_CROP, R.drawable.default_img_circle, R.drawable.default_img_circle, false).mo6707transform(new RoundedCorners(imageView.getResources().getDimensionPixelSize(R.dimen.dimen_5)));
    }

    public static RequestOptions getCenterInsideImageOption(ImageView imageView) {
        return getRequestOptions(imageView, ImageView.ScaleType.CENTER_INSIDE, R.drawable.default_img_grey, R.drawable.default_img_grey, false);
    }

    public static RequestOptions getCenterInsideImageOptionWhite(ImageView imageView) {
        return getRequestOptions(imageView, ImageView.ScaleType.CENTER_INSIDE, R.drawable.default_imag_white, R.drawable.default_imag_white, false);
    }

    public static RequestOptions getFitCenterImageOption(ImageView imageView) {
        return getRequestOptions(imageView, ImageView.ScaleType.FIT_CENTER, R.drawable.default_img_grey, R.drawable.default_img_grey, false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.one.tomato.utils.image.ImageLoaderUtil$13 */
    /* loaded from: classes3.dex */
    public static /* synthetic */ class C301313 {
        static final /* synthetic */ int[] $SwitchMap$android$widget$ImageView$ScaleType = new int[ImageView.ScaleType.values().length];

        static {
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.CENTER_INSIDE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.FIT_CENTER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ImageView.ScaleType.CENTER_CROP.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public static RequestOptions getRequestOptions(ImageView imageView, ImageView.ScaleType scaleType, int i, int i2, boolean z) {
        RequestOptions requestOptions = new RequestOptions();
        int i3 = C301313.$SwitchMap$android$widget$ImageView$ScaleType[scaleType.ordinal()];
        if (i3 == 1) {
            requestOptions.mo6655centerInside();
        } else if (i3 == 2) {
            requestOptions.mo6672fitCenter();
        } else if (i3 == 3) {
            requestOptions.mo6654centerCrop();
        } else {
            requestOptions.mo6654centerCrop();
        }
        if (i > 0) {
            requestOptions.mo6695placeholder(i);
        }
        if (i2 > 0) {
            requestOptions.mo6667error(i2);
        }
        if (z) {
            requestOptions.mo6656circleCrop();
        }
        requestOptions.mo6661diskCacheStrategy(DiskCacheStrategy.DATA);
        return requestOptions;
    }

    public static String compressImgOnline(String str, int i, int i2) {
        String[] split;
        String str2 = "";
        if (TextUtils.isEmpty(str)) {
            return str2;
        }
        if (i == 0 || i2 == 0 || (split = str.split("\\.")) == null || split.length == 0) {
            return str;
        }
        String str3 = ".png";
        if (split.length == 1) {
            str2 = split[0];
        } else if (split.length == 2) {
            str2 = split[0];
            str3 = split[1];
        }
        return str2 + "_" + i + "x" + i2 + "." + str3;
    }

    public static String compressHeadImage(String str, boolean z) {
        return z ? str : compressImgOnline(str, 64, 64);
    }

    public static String compressHeadImage(String str, boolean z, int i, int i2) {
        return z ? str : compressImgOnline(str, i, i2);
    }

    public static String compressViewPagerImage(String str, boolean z, boolean z2) {
        return (z || z2) ? str : compressImgOnline(str, 540, 960);
    }

    public static String compressViewPagerImageWidthAndHeight(String str, boolean z) {
        return z ? str : compressImgOnline(str, 200, 314);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class MyRequestListener implements RequestListener<File> {
        private String myUrl;

        @Override // com.bumptech.glide.request.RequestListener
        public boolean onResourceReady(File file, Object obj, Target<File> target, DataSource dataSource, boolean z) {
            return false;
        }

        public MyRequestListener(String str) {
            this.myUrl = str;
        }

        @Override // com.bumptech.glide.request.RequestListener
        public boolean onLoadFailed(@Nullable GlideException glideException, Object obj, Target<File> target, boolean z) {
            CrashHandler crashHandler = CrashHandler.getInstance();
            crashHandler.handleException(this.myUrl + "\n" + glideException.toString(), 15);
            return false;
        }
    }
}
