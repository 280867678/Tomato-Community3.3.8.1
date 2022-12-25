package com.zzhoujay.richtext.parser;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.text.style.URLSpan;
import com.zzhoujay.richtext.LinkHolder;
import com.zzhoujay.richtext.RichTextConfig;
import com.zzhoujay.richtext.callback.LinkFixCallback;
import com.zzhoujay.richtext.callback.OnImageClickListener;
import com.zzhoujay.richtext.callback.OnImageLongClickListener;
import com.zzhoujay.richtext.spans.ClickableImageSpan;
import com.zzhoujay.richtext.spans.LongClickableURLSpan;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class CachedSpannedParser {

    /* loaded from: classes4.dex */
    public static class Cached {
    }

    public int parse(SpannableStringBuilder spannableStringBuilder, ImageGetterWrapper imageGetterWrapper, RichTextConfig richTextConfig) {
        boolean isCached = isCached(spannableStringBuilder);
        handleClick(spannableStringBuilder, richTextConfig, isCached);
        return handleImage(spannableStringBuilder, imageGetterWrapper, richTextConfig, isCached);
    }

    private void handleClick(SpannableStringBuilder spannableStringBuilder, RichTextConfig richTextConfig, boolean z) {
        int i = 0;
        if (z) {
            LongClickableURLSpan[] longClickableURLSpanArr = (LongClickableURLSpan[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), LongClickableURLSpan.class);
            if (longClickableURLSpanArr == null || longClickableURLSpanArr.length <= 0) {
                return;
            }
            int length = longClickableURLSpanArr.length;
            while (i < length) {
                resetLinkSpan(spannableStringBuilder, richTextConfig, longClickableURLSpanArr[i]);
                i++;
            }
        } else if (richTextConfig.clickable >= 0) {
            URLSpan[] uRLSpanArr = (URLSpan[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), URLSpan.class);
            int length2 = uRLSpanArr == null ? 0 : uRLSpanArr.length;
            while (i < length2) {
                resetLinkSpan(spannableStringBuilder, richTextConfig, uRLSpanArr[i]);
                i++;
            }
        } else {
            URLSpan[] uRLSpanArr2 = (URLSpan[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), URLSpan.class);
            int length3 = uRLSpanArr2 == null ? 0 : uRLSpanArr2.length;
            while (i < length3) {
                spannableStringBuilder.removeSpan(uRLSpanArr2[i]);
                i++;
            }
        }
    }

    private void resetLinkSpan(SpannableStringBuilder spannableStringBuilder, RichTextConfig richTextConfig, URLSpan uRLSpan) {
        int spanStart = spannableStringBuilder.getSpanStart(uRLSpan);
        int spanEnd = spannableStringBuilder.getSpanEnd(uRLSpan);
        spannableStringBuilder.removeSpan(uRLSpan);
        LinkHolder linkHolder = new LinkHolder(uRLSpan.getURL());
        LinkFixCallback linkFixCallback = richTextConfig.linkFixCallback;
        if (linkFixCallback != null) {
            linkFixCallback.fix(linkHolder);
        }
        spannableStringBuilder.setSpan(new LongClickableURLSpan(linkHolder, richTextConfig.onUrlClickListener, richTextConfig.onUrlLongClickListener), spanStart, spanEnd, 33);
    }

    private int handleImage(SpannableStringBuilder spannableStringBuilder, ImageGetterWrapper imageGetterWrapper, RichTextConfig richTextConfig, boolean z) {
        ImageSpan[] imageSpanArr;
        OnImageClickListener onImageClickListener;
        OnImageLongClickListener onImageLongClickListener;
        OnImageClickListener onImageClickListener2;
        OnImageLongClickListener onImageLongClickListener2;
        int i = 0;
        if (z) {
            ClickableImageSpan[] clickableImageSpanArr = (ClickableImageSpan[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), ClickableImageSpan.class);
            if (clickableImageSpanArr == null || clickableImageSpanArr.length <= 0) {
                return 0;
            }
            for (ClickableImageSpan clickableImageSpan : clickableImageSpanArr) {
                int spanStart = spannableStringBuilder.getSpanStart(clickableImageSpan);
                int spanEnd = spannableStringBuilder.getSpanEnd(clickableImageSpan);
                spannableStringBuilder.removeSpan(clickableImageSpan);
                if (richTextConfig.clickable > 0) {
                    onImageClickListener2 = richTextConfig.onImageClickListener;
                    onImageLongClickListener2 = richTextConfig.onImageLongClickListener;
                } else {
                    onImageClickListener2 = null;
                    onImageLongClickListener2 = null;
                }
                Drawable drawable = imageGetterWrapper.getDrawable(clickableImageSpan.getSource());
                if (drawable == null) {
                    drawable = new ColorDrawable(0);
                }
                spannableStringBuilder.setSpan(new ClickableImageSpan(drawable, clickableImageSpan, onImageClickListener2, onImageLongClickListener2), spanStart, spanEnd, 33);
            }
            return clickableImageSpanArr.length;
        } else if (richTextConfig.noImage || (imageSpanArr = (ImageSpan[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), ImageSpan.class)) == null || imageSpanArr.length <= 0) {
            return 0;
        } else {
            ArrayList arrayList = new ArrayList(imageSpanArr.length);
            int i2 = 0;
            while (i2 < imageSpanArr.length) {
                ImageSpan imageSpan = imageSpanArr[i2];
                String source = imageSpan.getSource();
                arrayList.add(source);
                int spanStart2 = spannableStringBuilder.getSpanStart(imageSpan);
                int spanEnd2 = spannableStringBuilder.getSpanEnd(imageSpan);
                Object[] objArr = (ClickableSpan[]) spannableStringBuilder.getSpans(spanStart2, spanEnd2, ClickableSpan.class);
                if (objArr != null && objArr.length != 0) {
                    for (Object obj : objArr) {
                        spannableStringBuilder.removeSpan(obj);
                    }
                }
                if (richTextConfig.clickable > 0) {
                    onImageClickListener = richTextConfig.onImageClickListener;
                    onImageLongClickListener = richTextConfig.onImageLongClickListener;
                } else {
                    onImageClickListener = null;
                    onImageLongClickListener = null;
                }
                Drawable drawable2 = imageGetterWrapper.getDrawable(source);
                if (drawable2 == null) {
                    drawable2 = new ColorDrawable(i);
                }
                Object clickableImageSpan2 = new ClickableImageSpan(drawable2, arrayList, i2, onImageClickListener, onImageLongClickListener);
                spannableStringBuilder.removeSpan(imageSpan);
                spannableStringBuilder.setSpan(clickableImageSpan2, spanStart2, spanEnd2, 33);
                i2++;
                i = 0;
            }
            return imageSpanArr.length;
        }
    }

    private boolean isCached(SpannableStringBuilder spannableStringBuilder) {
        Cached[] cachedArr = (Cached[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), Cached.class);
        return cachedArr != null && cachedArr.length > 0;
    }
}
