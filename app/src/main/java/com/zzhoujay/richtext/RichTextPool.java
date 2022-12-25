package com.zzhoujay.richtext;

import android.support.p002v4.util.LruCache;
import android.text.SpannableStringBuilder;
import com.zzhoujay.richtext.ext.MD5;
import com.zzhoujay.richtext.parser.CachedSpannedParser;
import com.zzhoujay.richtext.spans.ClickableImageSpan;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.WeakHashMap;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class RichTextPool {
    private final WeakHashMap<Object, HashSet<WeakReference<RichText>>> instances;
    private final LruCache<String, SoftReference<SpannableStringBuilder>> richCache;

    private RichTextPool() {
        this.richCache = new LruCache<>(50);
        this.instances = new WeakHashMap<>();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void cache(String str, SpannableStringBuilder spannableStringBuilder) {
        SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder(spannableStringBuilder);
        clearImageSpan(spannableStringBuilder2);
        spannableStringBuilder2.setSpan(new CachedSpannedParser.Cached(), 0, spannableStringBuilder2.length(), 33);
        this.richCache.put(MD5.generate(str), new SoftReference<>(spannableStringBuilder2));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public SpannableStringBuilder loadCache(String str) {
        SoftReference<SpannableStringBuilder> softReference = this.richCache.get(MD5.generate(str));
        SpannableStringBuilder spannableStringBuilder = softReference == null ? null : softReference.get();
        if (spannableStringBuilder != null) {
            return new SpannableStringBuilder(spannableStringBuilder);
        }
        return null;
    }

    SpannableStringBuilder clearImageSpan(SpannableStringBuilder spannableStringBuilder) {
        ClickableImageSpan[] clickableImageSpanArr = (ClickableImageSpan[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), ClickableImageSpan.class);
        if (clickableImageSpanArr != null && clickableImageSpanArr.length > 0) {
            for (ClickableImageSpan clickableImageSpan : clickableImageSpanArr) {
                int spanStart = spannableStringBuilder.getSpanStart(clickableImageSpan);
                int spanEnd = spannableStringBuilder.getSpanEnd(clickableImageSpan);
                int spanFlags = spannableStringBuilder.getSpanFlags(clickableImageSpan);
                Object copy = clickableImageSpan.copy();
                spannableStringBuilder.removeSpan(clickableImageSpan);
                spannableStringBuilder.setSpan(copy, spanStart, spanEnd, spanFlags);
            }
        }
        return spannableStringBuilder;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void bind(Object obj, RichText richText) {
        HashSet<WeakReference<RichText>> hashSet = this.instances.get(obj);
        if (hashSet == null) {
            hashSet = new HashSet<>();
            this.instances.put(obj, hashSet);
        }
        hashSet.add(new WeakReference<>(richText));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static class RichTextPoolHolder {
        private static final RichTextPool RICH_TEXT_POOL = new RichTextPool();
    }

    public static RichTextPool getPool() {
        return RichTextPoolHolder.RICH_TEXT_POOL;
    }

    public void recycle() {
        this.richCache.evictAll();
    }
}
