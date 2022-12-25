package com.zzhoujay.richtext;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;
import com.zzhoujay.richtext.RichTextConfig;
import com.zzhoujay.richtext.cache.BitmapPool;
import com.zzhoujay.richtext.callback.ImageFixCallback;
import com.zzhoujay.richtext.callback.ImageLoadNotify;
import com.zzhoujay.richtext.ext.ContextKit;
import com.zzhoujay.richtext.ext.HtmlTagHandler;
import com.zzhoujay.richtext.ext.LongClickableLinkMovementMethod;
import com.zzhoujay.richtext.parser.CachedSpannedParser;
import com.zzhoujay.richtext.parser.Html2SpannedParser;
import com.zzhoujay.richtext.parser.ImageGetterWrapper;
import com.zzhoujay.richtext.parser.Markdown2SpannedParser;
import com.zzhoujay.richtext.parser.SpannedParser;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public class RichText implements ImageGetterWrapper, ImageLoadNotify {
    public static boolean debugMode = false;
    private final CachedSpannedParser cachedSpannedParser;
    private final RichTextConfig config;
    private int count;
    private HashMap<String, ImageHolder> imageHolderMap;
    private int loadingCount;
    private SoftReference<SpannableStringBuilder> richText;
    private final SpannedParser spannedParser;
    private final WeakReference<TextView> textViewWeakReference;
    private static Pattern IMAGE_TAG_PATTERN = Pattern.compile("<(img|IMG)(.*?)>");
    private static Pattern IMAGE_WIDTH_PATTERN = Pattern.compile("(width|WIDTH)=\"(.*?)\"");
    private static Pattern IMAGE_HEIGHT_PATTERN = Pattern.compile("(height|HEIGHT)=\"(.*?)\"");
    private static Pattern IMAGE_SRC_PATTERN = Pattern.compile("(src|SRC)=\"(.*?)\"");
    private static final HashMap<String, Object> GLOBAL_ARGS = new HashMap<>();

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void bind(Object obj, RichText richText) {
        RichTextPool.getPool().bind(obj, richText);
    }

    public static void recycle() {
        BitmapPool.getPool().clear();
        RichTextPool.getPool().recycle();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void putArgs(String str, Object obj) {
        synchronized (GLOBAL_ARGS) {
            GLOBAL_ARGS.put(str, obj);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Object getArgs(String str) {
        Object obj;
        synchronized (GLOBAL_ARGS) {
            obj = GLOBAL_ARGS.get(str);
        }
        return obj;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public RichText(RichTextConfig richTextConfig, TextView textView) {
        RichState richState = RichState.ready;
        this.config = richTextConfig;
        this.textViewWeakReference = new WeakReference<>(textView);
        if (richTextConfig.richType == RichType.markdown) {
            this.spannedParser = new Markdown2SpannedParser(textView);
        } else {
            this.spannedParser = new Html2SpannedParser(new HtmlTagHandler(textView));
        }
        int i = richTextConfig.clickable;
        if (i > 0) {
            textView.setMovementMethod(new LongClickableLinkMovementMethod());
        } else if (i == 0) {
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        }
        this.cachedSpannedParser = new CachedSpannedParser();
        richTextConfig.setRichTextInstance(this);
    }

    public static RichTextConfig.RichTextConfigBuild fromHtml(String str) {
        return from(str, RichType.html);
    }

    public static RichTextConfig.RichTextConfigBuild from(String str, RichType richType) {
        return new RichTextConfig.RichTextConfigBuild(str, richType);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void generateAndSet() {
        final TextView textView = this.textViewWeakReference.get();
        if (textView != null) {
            textView.post(new Runnable() { // from class: com.zzhoujay.richtext.RichText.1
                @Override // java.lang.Runnable
                public void run() {
                    RichText.this.asyncGenerate(textView);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static class ParseAsyncTask extends AsyncTask<Void, Void, CharSequence> {
        private RichText richText;
        private WeakReference<TextView> textViewWeakReference;

        ParseAsyncTask(RichText richText, TextView textView) {
            this.richText = richText;
            this.textViewWeakReference = new WeakReference<>(textView);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public CharSequence doInBackground(Void[] voidArr) {
            if (this.textViewWeakReference.get() == null) {
                return null;
            }
            return this.richText.generateRichText();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(CharSequence charSequence) {
            TextView textView;
            WeakReference<TextView> weakReference = this.textViewWeakReference;
            if (weakReference == null || (textView = weakReference.get()) == null || charSequence == null) {
                return;
            }
            textView.setText(charSequence);
            if (this.richText.config.callback == null) {
                return;
            }
            this.richText.config.callback.done(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void asyncGenerate(TextView textView) {
        ParseAsyncTask parseAsyncTask = new ParseAsyncTask(this, textView);
        new WeakReference(textView);
        if (this.config.singleLoad) {
            parseAsyncTask.execute(new Void[0]);
        } else {
            parseAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CharSequence generateRichText() {
        SpannableStringBuilder spannableStringBuilder = null;
        if (this.textViewWeakReference.get() == null) {
            return null;
        }
        RichTextConfig richTextConfig = this.config;
        if (richTextConfig.richType != RichType.markdown) {
            analyzeImages(richTextConfig.source);
        } else {
            this.imageHolderMap = new HashMap<>();
        }
        RichState richState = RichState.loading;
        if (this.config.cacheType.intValue() > CacheType.none.intValue() + 100) {
            spannableStringBuilder = RichTextPool.getPool().loadCache(this.config.source);
        }
        if (spannableStringBuilder == null) {
            spannableStringBuilder = parseRichText();
        }
        this.richText = new SoftReference<>(spannableStringBuilder);
        this.config.imageGetter.registerImageLoadNotify(this);
        this.count = this.cachedSpannedParser.parse(spannableStringBuilder, this, this.config);
        return spannableStringBuilder;
    }

    @NonNull
    private SpannableStringBuilder parseRichText() {
        Spanned parse = this.spannedParser.parse(this.config.source);
        if (parse instanceof SpannableStringBuilder) {
            return (SpannableStringBuilder) parse;
        }
        if (parse == null) {
            parse = new SpannableString("");
        }
        return new SpannableStringBuilder(parse);
    }

    private synchronized void analyzeImages(String str) {
        this.imageHolderMap = new HashMap<>();
        int i = 0;
        Matcher matcher = IMAGE_TAG_PATTERN.matcher(str);
        while (matcher.find()) {
            String trim = matcher.group(2).trim();
            Matcher matcher2 = IMAGE_SRC_PATTERN.matcher(trim);
            String str2 = null;
            if (matcher2.find()) {
                str2 = matcher2.group(2).trim();
            }
            if (!TextUtils.isEmpty(str2)) {
                ImageHolder imageHolder = new ImageHolder(str2, i, this.config, this.textViewWeakReference.get());
                imageHolder.setIsGif(isGif(str2));
                if (!this.config.autoFix && !this.config.resetSize) {
                    Matcher matcher3 = IMAGE_WIDTH_PATTERN.matcher(trim);
                    if (matcher3.find()) {
                        imageHolder.setWidth(parseStringToInteger(matcher3.group(2).trim()));
                    }
                    Matcher matcher4 = IMAGE_HEIGHT_PATTERN.matcher(trim);
                    if (matcher4.find()) {
                        imageHolder.setHeight(parseStringToInteger(matcher4.group(2).trim()));
                    }
                }
                this.imageHolderMap.put(imageHolder.getSource(), imageHolder);
                i++;
            }
        }
    }

    private static int parseStringToInteger(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                return Integer.parseInt(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    private static boolean isGif(String str) {
        int lastIndexOf = str.lastIndexOf(46);
        return lastIndexOf > 0 && "gif".toUpperCase().equals(str.substring(lastIndexOf + 1).toUpperCase());
    }

    @Override // com.zzhoujay.richtext.parser.ImageGetterWrapper
    public Drawable getDrawable(String str) {
        TextView textView;
        ImageHolder imageHolder;
        this.loadingCount++;
        RichTextConfig richTextConfig = this.config;
        if (richTextConfig.imageGetter != null && !richTextConfig.noImage && (textView = this.textViewWeakReference.get()) != null && ContextKit.activityIsAlive(textView.getContext())) {
            RichTextConfig richTextConfig2 = this.config;
            if (richTextConfig2.richType == RichType.markdown) {
                imageHolder = new ImageHolder(str, this.loadingCount - 1, richTextConfig2, textView);
                this.imageHolderMap.put(str, imageHolder);
            } else {
                imageHolder = this.imageHolderMap.get(str);
                if (imageHolder == null) {
                    imageHolder = new ImageHolder(str, this.loadingCount - 1, this.config, textView);
                    this.imageHolderMap.put(str, imageHolder);
                }
            }
            imageHolder.setImageState(0);
            ImageFixCallback imageFixCallback = this.config.imageFixCallback;
            if (imageFixCallback != null) {
                imageFixCallback.onInit(imageHolder);
                if (!imageHolder.isShow()) {
                    return null;
                }
            }
            RichTextConfig richTextConfig3 = this.config;
            return richTextConfig3.imageGetter.getDrawable(imageHolder, richTextConfig3, textView);
        }
        return null;
    }

    @Override // com.zzhoujay.richtext.callback.ImageLoadNotify
    public void done(Object obj) {
        SpannableStringBuilder spannableStringBuilder;
        if (!(obj instanceof Integer) || ((Integer) obj).intValue() < this.count) {
            return;
        }
        RichState richState = RichState.loaded;
        TextView textView = this.textViewWeakReference.get();
        if (this.config.cacheType.intValue() >= CacheType.layout.intValue() && (spannableStringBuilder = this.richText.get()) != null) {
            RichTextPool.getPool().cache(this.config.source, spannableStringBuilder);
        }
        if (this.config.callback == null || textView == null) {
            return;
        }
        textView.post(new Runnable() { // from class: com.zzhoujay.richtext.RichText.2
            @Override // java.lang.Runnable
            public void run() {
                RichText.this.config.callback.done(true);
            }
        });
    }
}
