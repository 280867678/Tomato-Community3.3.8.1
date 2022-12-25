package com.zzhoujay.richtext;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.p002v4.util.Pair;
import android.widget.TextView;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.callback.Callback;
import com.zzhoujay.richtext.callback.DrawableGetter;
import com.zzhoujay.richtext.callback.ImageFixCallback;
import com.zzhoujay.richtext.callback.ImageGetter;
import com.zzhoujay.richtext.callback.LinkFixCallback;
import com.zzhoujay.richtext.callback.OnImageClickListener;
import com.zzhoujay.richtext.callback.OnImageLongClickListener;
import com.zzhoujay.richtext.callback.OnUrlClickListener;
import com.zzhoujay.richtext.callback.OnUrlLongClickListener;
import com.zzhoujay.richtext.drawable.DrawableBorderHolder;
import com.zzhoujay.richtext.p142ig.DefaultImageDownloader;
import com.zzhoujay.richtext.p142ig.DefaultImageGetter;
import com.zzhoujay.richtext.p142ig.ImageDownloader;
import java.lang.ref.WeakReference;
import java.util.HashMap;

/* loaded from: classes4.dex */
public final class RichTextConfig {
    public final boolean autoFix;
    public final boolean autoPlay;
    public final DrawableBorderHolder borderHolder;
    public final CacheType cacheType;
    public final Callback callback;
    public final int clickable;
    public final DrawableGetter errorImageDrawableGetter;
    public final int height;
    public final ImageDownloader imageDownloader;
    public final ImageFixCallback imageFixCallback;
    final ImageGetter imageGetter;
    public final LinkFixCallback linkFixCallback;
    public final boolean noImage;
    public final OnImageClickListener onImageClickListener;
    public final OnImageLongClickListener onImageLongClickListener;
    public final OnUrlClickListener onUrlClickListener;
    public final OnUrlLongClickListener onUrlLongClickListener;
    public final DrawableGetter placeHolderDrawableGetter;
    public final boolean resetSize;
    private WeakReference<RichText> richTextInstanceWeakReference;
    public final RichType richType;
    public final ImageHolder.ScaleType scaleType;
    public final boolean singleLoad;
    public final String source;
    public final int width;

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setRichTextInstance(RichText richText) {
        if (this.richTextInstanceWeakReference == null) {
            this.richTextInstanceWeakReference = new WeakReference<>(richText);
        }
    }

    private RichTextConfig(RichTextConfigBuild richTextConfigBuild) {
        this(richTextConfigBuild.source, richTextConfigBuild.richType, richTextConfigBuild.autoFix, richTextConfigBuild.resetSize, richTextConfigBuild.cacheType, richTextConfigBuild.imageFixCallback, richTextConfigBuild.linkFixCallback, richTextConfigBuild.noImage, richTextConfigBuild.clickable, richTextConfigBuild.onImageClickListener, richTextConfigBuild.onUrlClickListener, richTextConfigBuild.onImageLongClickListener, richTextConfigBuild.onUrlLongClickListener, richTextConfigBuild.imageGetter, richTextConfigBuild.callback, richTextConfigBuild.autoPlay, richTextConfigBuild.scaleType, richTextConfigBuild.width, richTextConfigBuild.height, richTextConfigBuild.borderHolder, richTextConfigBuild.singleLoad, richTextConfigBuild.imageDownloader, richTextConfigBuild.placeHolderDrawableGetter, richTextConfigBuild.errorImageDrawableGetter);
    }

    private RichTextConfig(String str, RichType richType, boolean z, boolean z2, CacheType cacheType, ImageFixCallback imageFixCallback, LinkFixCallback linkFixCallback, boolean z3, int i, OnImageClickListener onImageClickListener, OnUrlClickListener onUrlClickListener, OnImageLongClickListener onImageLongClickListener, OnUrlLongClickListener onUrlLongClickListener, ImageGetter imageGetter, Callback callback, boolean z4, ImageHolder.ScaleType scaleType, int i2, int i3, DrawableBorderHolder drawableBorderHolder, boolean z5, ImageDownloader imageDownloader, DrawableGetter drawableGetter, DrawableGetter drawableGetter2) {
        this.source = str;
        this.richType = richType;
        this.autoFix = z;
        this.resetSize = z2;
        this.imageFixCallback = imageFixCallback;
        this.linkFixCallback = linkFixCallback;
        this.noImage = z3;
        this.cacheType = cacheType;
        this.onImageClickListener = onImageClickListener;
        this.onUrlClickListener = onUrlClickListener;
        this.onImageLongClickListener = onImageLongClickListener;
        this.onUrlLongClickListener = onUrlLongClickListener;
        this.imageGetter = imageGetter;
        this.callback = callback;
        this.scaleType = scaleType;
        this.autoPlay = z4;
        this.width = i2;
        this.height = i3;
        this.borderHolder = drawableBorderHolder;
        this.singleLoad = z5;
        this.imageDownloader = imageDownloader;
        this.placeHolderDrawableGetter = drawableGetter;
        this.errorImageDrawableGetter = drawableGetter2;
        this.clickable = (i != 0 || (onImageLongClickListener == null && onUrlLongClickListener == null && onImageClickListener == null && onUrlClickListener == null)) ? i : 1;
        new HashMap();
    }

    /* loaded from: classes4.dex */
    public static final class RichTextConfigBuild {
        Callback callback;
        ImageDownloader imageDownloader;
        ImageFixCallback imageFixCallback;
        ImageGetter imageGetter;
        LinkFixCallback linkFixCallback;
        OnImageClickListener onImageClickListener;
        OnImageLongClickListener onImageLongClickListener;
        OnUrlClickListener onUrlClickListener;
        OnUrlLongClickListener onUrlLongClickListener;
        RichType richType;
        final String source;
        WeakReference<Object> tag;
        private static final Handler HANDLER = new Handler(Looper.getMainLooper()) { // from class: com.zzhoujay.richtext.RichTextConfig.RichTextConfigBuild.1
            @Override // android.os.Handler
            public void dispatchMessage(Message message) {
                if (message.what == 9) {
                    Pair pair = (Pair) message.obj;
                    TextView textView = (TextView) pair.second;
                    int width = (textView.getWidth() - textView.getPaddingLeft()) - textView.getPaddingRight();
                    ((Drawable) pair.first).setBounds(0, 0, width, width / 2);
                }
            }
        };
        private static final DrawableGetter PLACE_HOLDER_DRAWABLE_GETTER = new DrawableGetter() { // from class: com.zzhoujay.richtext.RichTextConfig.RichTextConfigBuild.2
            @Override // com.zzhoujay.richtext.callback.DrawableGetter
            public Drawable getDrawable(ImageHolder imageHolder, RichTextConfig richTextConfig, TextView textView) {
                ColorDrawable colorDrawable = new ColorDrawable(-3355444);
                int width = textView.getWidth();
                colorDrawable.setBounds(0, 0, width, width / 2);
                RichTextConfigBuild.HANDLER.obtainMessage(9, Pair.create(colorDrawable, textView)).sendToTarget();
                return colorDrawable;
            }
        };
        private static final DrawableGetter ERROR_IMAGE_DRAWABLE_GETTER = new DrawableGetter() { // from class: com.zzhoujay.richtext.RichTextConfig.RichTextConfigBuild.3
            @Override // com.zzhoujay.richtext.callback.DrawableGetter
            public Drawable getDrawable(ImageHolder imageHolder, RichTextConfig richTextConfig, TextView textView) {
                ColorDrawable colorDrawable = new ColorDrawable(-12303292);
                int width = textView.getWidth();
                colorDrawable.setBounds(0, 0, width, width / 2);
                RichTextConfigBuild.HANDLER.obtainMessage(9, Pair.create(colorDrawable, textView)).sendToTarget();
                return colorDrawable;
            }
        };
        boolean autoFix = true;
        boolean resetSize = false;
        boolean noImage = false;
        int clickable = 0;
        CacheType cacheType = CacheType.all;
        boolean autoPlay = false;
        ImageHolder.ScaleType scaleType = ImageHolder.ScaleType.none;
        int width = Integer.MIN_VALUE;
        int height = Integer.MIN_VALUE;
        DrawableBorderHolder borderHolder = new DrawableBorderHolder();
        boolean singleLoad = true;
        DrawableGetter placeHolderDrawableGetter = PLACE_HOLDER_DRAWABLE_GETTER;
        DrawableGetter errorImageDrawableGetter = ERROR_IMAGE_DRAWABLE_GETTER;

        /* JADX INFO: Access modifiers changed from: package-private */
        public RichTextConfigBuild(String str, RichType richType) {
            this.source = str;
            this.richType = richType;
        }

        public RichTextConfigBuild clickable(boolean z) {
            this.clickable = z ? 1 : -1;
            return this;
        }

        public RichText into(TextView textView) {
            if (this.imageGetter == null) {
                this.imageGetter = new DefaultImageGetter();
            }
            if ((this.imageGetter instanceof DefaultImageGetter) && this.imageDownloader == null) {
                try {
                    Class<?> cls = Class.forName("com.zzhoujay.okhttpimagedownloader.OkHttpImageDownloader");
                    ImageDownloader imageDownloader = (ImageDownloader) RichText.getArgs("com.zzhoujay.okhttpimagedownloader.OkHttpImageDownloader");
                    if (imageDownloader == null) {
                        imageDownloader = (ImageDownloader) cls.newInstance();
                        RichText.putArgs("com.zzhoujay.okhttpimagedownloader.OkHttpImageDownloader", imageDownloader);
                    }
                    this.imageDownloader = imageDownloader;
                } catch (Exception unused) {
                    DefaultImageDownloader defaultImageDownloader = (DefaultImageDownloader) RichText.getArgs(DefaultImageDownloader.GLOBAL_ID);
                    if (defaultImageDownloader == null) {
                        defaultImageDownloader = new DefaultImageDownloader();
                        RichText.putArgs(DefaultImageDownloader.GLOBAL_ID, defaultImageDownloader);
                    }
                    this.imageDownloader = defaultImageDownloader;
                }
            }
            RichText richText = new RichText(new RichTextConfig(this), textView);
            WeakReference<Object> weakReference = this.tag;
            if (weakReference != null) {
                RichText.bind(weakReference.get(), richText);
            }
            this.tag = null;
            richText.generateAndSet();
            return richText;
        }
    }
}
