package com.zzhoujay.richtext.p142ig;

import android.widget.TextView;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichTextConfig;
import com.zzhoujay.richtext.cache.BitmapPool;
import com.zzhoujay.richtext.callback.ImageLoadNotify;
import com.zzhoujay.richtext.drawable.DrawableWrapper;
import com.zzhoujay.richtext.exceptions.ImageDecodeException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.zzhoujay.richtext.ig.CallbackImageLoader */
/* loaded from: classes4.dex */
public class CallbackImageLoader extends AbstractImageLoader<InputStream> {
    /* JADX INFO: Access modifiers changed from: package-private */
    public CallbackImageLoader(ImageHolder imageHolder, RichTextConfig richTextConfig, TextView textView, DrawableWrapper drawableWrapper, ImageLoadNotify imageLoadNotify) {
        super(imageHolder, richTextConfig, textView, drawableWrapper, imageLoadNotify, SourceDecode.INPUT_STREAM_DECODE);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onImageDownloadFinish(String str, Exception exc) {
        if (exc != null) {
            onFailure(exc);
            return;
        }
        try {
            InputStream readBitmapFromTemp = BitmapPool.getPool().readBitmapFromTemp(str);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(readBitmapFromTemp);
            doLoadImage(bufferedInputStream);
            bufferedInputStream.close();
            readBitmapFromTemp.close();
        } catch (IOException e) {
            onFailure(e);
        } catch (OutOfMemoryError e2) {
            onFailure(new ImageDecodeException(e2));
        }
    }
}
