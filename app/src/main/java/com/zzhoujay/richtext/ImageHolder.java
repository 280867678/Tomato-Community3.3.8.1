package com.zzhoujay.richtext;

import android.graphics.drawable.Drawable;
import android.widget.TextView;
import com.zzhoujay.richtext.drawable.DrawableBorderHolder;
import com.zzhoujay.richtext.ext.MD5;
import com.zzhoujay.richtext.p142ig.ImageDownloader;

/* loaded from: classes4.dex */
public class ImageHolder {
    private boolean autoFix;
    private boolean autoPlay;
    private DrawableBorderHolder borderHolder;
    private Drawable errorImage;
    private int height;
    private int imageState;
    private boolean isGif = false;
    private String key;
    private Drawable placeHolder;
    private final int position;
    private String prefixCode;
    private ScaleType scaleType;
    private boolean show;
    private String source;
    private int width;

    /* loaded from: classes4.dex */
    public enum ScaleType {
        none(0),
        center(1),
        center_crop(2),
        center_inside(3),
        fit_center(4),
        fit_start(5),
        fit_end(6),
        fit_xy(7),
        fit_auto(8);
        
        int value;

        ScaleType(int i) {
            this.value = i;
        }

        public int intValue() {
            return this.value;
        }

        public static ScaleType valueOf(int i) {
            return values()[i];
        }
    }

    /* loaded from: classes4.dex */
    public static class SizeHolder {
        private int height;
        private float scale = 1.0f;
        private int width;

        public SizeHolder(int i, int i2) {
            this.width = i;
            this.height = i2;
        }

        public int getWidth() {
            return (int) (this.scale * this.width);
        }

        public int getHeight() {
            return (int) (this.scale * this.height);
        }

        public boolean isInvalidateSize() {
            return this.scale > 0.0f && this.width > 0 && this.height > 0;
        }
    }

    public ImageHolder(String str, int i, RichTextConfig richTextConfig, TextView textView) {
        this.source = str;
        this.position = i;
        ImageDownloader imageDownloader = richTextConfig.imageDownloader;
        this.prefixCode = imageDownloader == null ? "" : imageDownloader.getClass().getName();
        generateKey();
        this.autoPlay = richTextConfig.autoPlay;
        if (richTextConfig.autoFix) {
            this.width = Integer.MAX_VALUE;
            this.height = Integer.MIN_VALUE;
            this.scaleType = ScaleType.fit_auto;
        } else {
            this.scaleType = richTextConfig.scaleType;
            this.width = richTextConfig.width;
            this.height = richTextConfig.height;
        }
        this.show = !richTextConfig.noImage;
        this.borderHolder = new DrawableBorderHolder(richTextConfig.borderHolder);
        this.placeHolder = richTextConfig.placeHolderDrawableGetter.getDrawable(this, richTextConfig, textView);
        this.errorImage = richTextConfig.errorImageDrawableGetter.getDrawable(this, richTextConfig, textView);
    }

    private void generateKey() {
        this.key = MD5.generate(this.prefixCode + this.source);
    }

    public String getKey() {
        return this.key;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int i) {
        this.width = i;
    }

    public void setHeight(int i) {
        this.height = i;
    }

    public String getSource() {
        return this.source;
    }

    public ScaleType getScaleType() {
        return this.scaleType;
    }

    public boolean isGif() {
        return this.isGif;
    }

    public void setIsGif(boolean z) {
        this.isGif = z;
    }

    public boolean isAutoPlay() {
        return this.autoPlay;
    }

    public boolean isShow() {
        return this.show;
    }

    public void setImageState(int i) {
        this.imageState = i;
    }

    public DrawableBorderHolder getBorderHolder() {
        return this.borderHolder;
    }

    public Drawable getPlaceHolder() {
        return this.placeHolder;
    }

    public Drawable getErrorImage() {
        return this.errorImage;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ImageHolder)) {
            return false;
        }
        ImageHolder imageHolder = (ImageHolder) obj;
        if (this.position != imageHolder.position || this.width != imageHolder.width || this.height != imageHolder.height || this.scaleType != imageHolder.scaleType || this.imageState != imageHolder.imageState || this.autoFix != imageHolder.autoFix || this.autoPlay != imageHolder.autoPlay || this.show != imageHolder.show || this.isGif != imageHolder.isGif || !this.prefixCode.equals(imageHolder.prefixCode) || !this.source.equals(imageHolder.source) || !this.key.equals(imageHolder.key) || !this.borderHolder.equals(imageHolder.borderHolder)) {
            return false;
        }
        Drawable drawable = this.placeHolder;
        if (drawable == null ? imageHolder.placeHolder != null : !drawable.equals(imageHolder.placeHolder)) {
            return false;
        }
        Drawable drawable2 = this.errorImage;
        Drawable drawable3 = imageHolder.errorImage;
        return drawable2 != null ? drawable2.equals(drawable3) : drawable3 == null;
    }

    public int hashCode() {
        int hashCode = ((((((((((((((((((((this.source.hashCode() * 31) + this.key.hashCode()) * 31) + this.position) * 31) + this.width) * 31) + this.height) * 31) + this.scaleType.hashCode()) * 31) + this.imageState) * 31) + (this.autoFix ? 1 : 0)) * 31) + (this.autoPlay ? 1 : 0)) * 31) + (this.show ? 1 : 0)) * 31) + (this.isGif ? 1 : 0)) * 31;
        DrawableBorderHolder drawableBorderHolder = this.borderHolder;
        int i = 0;
        int hashCode2 = (hashCode + (drawableBorderHolder != null ? drawableBorderHolder.hashCode() : 0)) * 31;
        Drawable drawable = this.placeHolder;
        int hashCode3 = (hashCode2 + (drawable != null ? drawable.hashCode() : 0)) * 31;
        Drawable drawable2 = this.errorImage;
        if (drawable2 != null) {
            i = drawable2.hashCode();
        }
        return ((hashCode3 + i) * 31) + this.prefixCode.hashCode();
    }

    public String toString() {
        return "ImageHolder{source='" + this.source + "', key='" + this.key + "', position=" + this.position + ", width=" + this.width + ", height=" + this.height + ", scaleType=" + this.scaleType + ", imageState=" + this.imageState + ", autoFix=" + this.autoFix + ", autoPlay=" + this.autoPlay + ", show=" + this.show + ", isGif=" + this.isGif + ", borderHolder=" + this.borderHolder + ", placeHolder=" + this.placeHolder + ", errorImage=" + this.errorImage + ", prefixCode=" + this.prefixCode + '}';
    }
}
