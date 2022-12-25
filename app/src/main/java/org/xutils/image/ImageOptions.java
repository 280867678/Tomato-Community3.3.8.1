package org.xutils.image;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import java.lang.reflect.Field;
import org.xutils.common.util.DensityUtil;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;

/* loaded from: classes4.dex */
public class ImageOptions {
    public static final ImageOptions DEFAULT = new ImageOptions();
    private ParamsBuilder paramsBuilder;
    private int maxWidth = 0;
    private int maxHeight = 0;
    private int width = 0;
    private int height = 0;
    private boolean crop = false;
    private int radius = 0;
    private boolean square = false;
    private boolean circular = false;
    private boolean autoRotate = false;
    private boolean compress = true;
    private Bitmap.Config config = Bitmap.Config.RGB_565;
    private boolean ignoreGif = true;
    private int loadingDrawableId = 0;
    private int failureDrawableId = 0;
    private Drawable loadingDrawable = null;
    private Drawable failureDrawable = null;
    private boolean forceLoadingDrawable = true;
    private ImageView.ScaleType placeholderScaleType = ImageView.ScaleType.CENTER_INSIDE;
    private ImageView.ScaleType imageScaleType = ImageView.ScaleType.CENTER_CROP;
    private boolean fadeIn = false;
    private Animation animation = null;
    private boolean useMemCache = true;

    /* loaded from: classes4.dex */
    public interface ParamsBuilder {
        RequestParams buildParams(RequestParams requestParams, ImageOptions imageOptions);
    }

    protected ImageOptions() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void optimizeMaxSize(ImageView imageView) {
        int i;
        int i2 = this.width;
        if (i2 > 0 && (i = this.height) > 0) {
            this.maxWidth = i2;
            this.maxHeight = i;
            return;
        }
        int screenWidth = DensityUtil.getScreenWidth();
        int screenHeight = DensityUtil.getScreenHeight();
        if (this.width < 0) {
            this.maxWidth = (screenWidth * 3) / 2;
            this.compress = false;
        }
        if (this.height < 0) {
            this.maxHeight = (screenHeight * 3) / 2;
            this.compress = false;
        }
        if (imageView == null && this.maxWidth <= 0 && this.maxHeight <= 0) {
            this.maxWidth = screenWidth;
            this.maxHeight = screenHeight;
            return;
        }
        int i3 = this.maxWidth;
        int i4 = this.maxHeight;
        if (imageView != null) {
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            if (layoutParams != null) {
                if (i3 <= 0) {
                    int i5 = layoutParams.width;
                    if (i5 > 0) {
                        if (this.width <= 0) {
                            this.width = i5;
                        }
                        i3 = i5;
                    } else if (i5 != -2) {
                        i3 = imageView.getWidth();
                    }
                }
                if (i4 <= 0) {
                    int i6 = layoutParams.height;
                    if (i6 > 0) {
                        if (this.height <= 0) {
                            this.height = i6;
                        }
                        i4 = i6;
                    } else if (i6 != -2) {
                        i4 = imageView.getHeight();
                    }
                }
            }
            if (i3 <= 0) {
                i3 = getImageViewFieldValue(imageView, "mMaxWidth");
            }
            if (i4 <= 0) {
                i4 = getImageViewFieldValue(imageView, "mMaxHeight");
            }
        }
        if (i3 > 0) {
            screenWidth = i3;
        }
        if (i4 > 0) {
            screenHeight = i4;
        }
        this.maxWidth = screenWidth;
        this.maxHeight = screenHeight;
    }

    public int getMaxWidth() {
        return this.maxWidth;
    }

    public int getMaxHeight() {
        return this.maxHeight;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public boolean isCrop() {
        return this.crop;
    }

    public int getRadius() {
        return this.radius;
    }

    public boolean isSquare() {
        return this.square;
    }

    public boolean isCircular() {
        return this.circular;
    }

    public boolean isIgnoreGif() {
        return this.ignoreGif;
    }

    public boolean isAutoRotate() {
        return this.autoRotate;
    }

    public boolean isCompress() {
        return this.compress;
    }

    public Bitmap.Config getConfig() {
        return this.config;
    }

    public Drawable getLoadingDrawable(ImageView imageView) {
        if (this.loadingDrawable == null && this.loadingDrawableId > 0 && imageView != null) {
            try {
                this.loadingDrawable = imageView.getResources().getDrawable(this.loadingDrawableId);
            } catch (Throwable th) {
                LogUtil.m43e(th.getMessage(), th);
            }
        }
        return this.loadingDrawable;
    }

    public Drawable getFailureDrawable(ImageView imageView) {
        if (this.failureDrawable == null && this.failureDrawableId > 0 && imageView != null) {
            try {
                this.failureDrawable = imageView.getResources().getDrawable(this.failureDrawableId);
            } catch (Throwable th) {
                LogUtil.m43e(th.getMessage(), th);
            }
        }
        return this.failureDrawable;
    }

    public boolean isFadeIn() {
        return this.fadeIn;
    }

    public Animation getAnimation() {
        return this.animation;
    }

    public ImageView.ScaleType getPlaceholderScaleType() {
        return this.placeholderScaleType;
    }

    public ImageView.ScaleType getImageScaleType() {
        return this.imageScaleType;
    }

    public boolean isForceLoadingDrawable() {
        return this.forceLoadingDrawable;
    }

    public boolean isUseMemCache() {
        return this.useMemCache;
    }

    public ParamsBuilder getParamsBuilder() {
        return this.paramsBuilder;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || ImageOptions.class != obj.getClass()) {
            return false;
        }
        ImageOptions imageOptions = (ImageOptions) obj;
        return this.maxWidth == imageOptions.maxWidth && this.maxHeight == imageOptions.maxHeight && this.width == imageOptions.width && this.height == imageOptions.height && this.crop == imageOptions.crop && this.radius == imageOptions.radius && this.square == imageOptions.square && this.circular == imageOptions.circular && this.autoRotate == imageOptions.autoRotate && this.compress == imageOptions.compress && this.config == imageOptions.config;
    }

    public int hashCode() {
        int i = ((((((((((((((((((this.maxWidth * 31) + this.maxHeight) * 31) + this.width) * 31) + this.height) * 31) + (this.crop ? 1 : 0)) * 31) + this.radius) * 31) + (this.square ? 1 : 0)) * 31) + (this.circular ? 1 : 0)) * 31) + (this.autoRotate ? 1 : 0)) * 31) + (this.compress ? 1 : 0)) * 31;
        Bitmap.Config config = this.config;
        return i + (config != null ? config.hashCode() : 0);
    }

    public String toString() {
        return "_" + this.maxWidth + "_" + this.maxHeight + "_" + this.width + "_" + this.height + "_" + this.radius + "_" + this.config + "_" + (this.crop ? 1 : 0) + (this.square ? 1 : 0) + (this.circular ? 1 : 0) + (this.autoRotate ? 1 : 0) + (this.compress ? 1 : 0);
    }

    private static int getImageViewFieldValue(ImageView imageView, String str) {
        try {
            Field declaredField = ImageView.class.getDeclaredField(str);
            declaredField.setAccessible(true);
            int intValue = ((Integer) declaredField.get(imageView)).intValue();
            if (intValue > 0 && intValue < Integer.MAX_VALUE) {
                return intValue;
            }
            return 0;
        } catch (Throwable unused) {
            return 0;
        }
    }

    /* loaded from: classes4.dex */
    public static class Builder {
        protected ImageOptions options;

        public Builder() {
            newImageOptions();
        }

        protected void newImageOptions() {
            this.options = new ImageOptions();
        }

        public ImageOptions build() {
            return this.options;
        }

        public Builder setSize(int i, int i2) {
            this.options.width = i;
            this.options.height = i2;
            return this;
        }

        public Builder setCrop(boolean z) {
            this.options.crop = z;
            return this;
        }

        public Builder setRadius(int i) {
            this.options.radius = i;
            return this;
        }

        public Builder setSquare(boolean z) {
            this.options.square = z;
            return this;
        }

        public Builder setCircular(boolean z) {
            this.options.circular = z;
            return this;
        }

        public Builder setAutoRotate(boolean z) {
            this.options.autoRotate = z;
            return this;
        }

        public Builder setConfig(Bitmap.Config config) {
            this.options.config = config;
            return this;
        }

        public Builder setIgnoreGif(boolean z) {
            this.options.ignoreGif = z;
            return this;
        }

        public Builder setLoadingDrawableId(int i) {
            this.options.loadingDrawableId = i;
            return this;
        }

        public Builder setLoadingDrawable(Drawable drawable) {
            this.options.loadingDrawable = drawable;
            return this;
        }

        public Builder setFailureDrawableId(int i) {
            this.options.failureDrawableId = i;
            return this;
        }

        public Builder setFailureDrawable(Drawable drawable) {
            this.options.failureDrawable = drawable;
            return this;
        }

        public Builder setFadeIn(boolean z) {
            this.options.fadeIn = z;
            return this;
        }

        public Builder setAnimation(Animation animation) {
            this.options.animation = animation;
            return this;
        }

        public Builder setPlaceholderScaleType(ImageView.ScaleType scaleType) {
            this.options.placeholderScaleType = scaleType;
            return this;
        }

        public Builder setImageScaleType(ImageView.ScaleType scaleType) {
            this.options.imageScaleType = scaleType;
            return this;
        }

        public Builder setForceLoadingDrawable(boolean z) {
            this.options.forceLoadingDrawable = z;
            return this;
        }

        public Builder setUseMemCache(boolean z) {
            this.options.useMemCache = z;
            return this;
        }

        public Builder setParamsBuilder(ParamsBuilder paramsBuilder) {
            this.options.paramsBuilder = paramsBuilder;
            return this;
        }
    }
}
