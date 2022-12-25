package com.zzhoujay.richtext.p142ig;

import android.widget.TextView;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichTextConfig;
import com.zzhoujay.richtext.callback.ImageLoadNotify;
import com.zzhoujay.richtext.drawable.DrawableWrapper;
import com.zzhoujay.richtext.exceptions.ImageDecodeException;
import com.zzhoujay.richtext.ext.Base64;

/* renamed from: com.zzhoujay.richtext.ig.Base64ImageLoader */
/* loaded from: classes4.dex */
class Base64ImageLoader extends AbstractImageLoader<byte[]> implements Runnable {
    /* JADX INFO: Access modifiers changed from: package-private */
    public Base64ImageLoader(ImageHolder imageHolder, RichTextConfig richTextConfig, TextView textView, DrawableWrapper drawableWrapper, ImageLoadNotify imageLoadNotify) {
        super(imageHolder, richTextConfig, textView, drawableWrapper, imageLoadNotify, SourceDecode.BASE64_SOURCE_DECODE);
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            doLoadImage(Base64.decode(this.holder.getSource()));
        } catch (Exception e) {
            onFailure(new ImageDecodeException(e));
        } catch (OutOfMemoryError e2) {
            onFailure(new ImageDecodeException(e2));
        }
    }
}
