package com.zzhoujay.richtext.p142ig;

import android.widget.TextView;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichTextConfig;
import com.zzhoujay.richtext.callback.ImageLoadNotify;
import com.zzhoujay.richtext.drawable.DrawableWrapper;
import com.zzhoujay.richtext.ext.Debug;
import java.io.IOException;
import java.io.InputStream;

/* renamed from: com.zzhoujay.richtext.ig.AssetsImageLoader */
/* loaded from: classes4.dex */
class AssetsImageLoader extends InputStreamImageLoader implements Runnable {
    /* JADX INFO: Access modifiers changed from: package-private */
    public AssetsImageLoader(ImageHolder imageHolder, RichTextConfig richTextConfig, TextView textView, DrawableWrapper drawableWrapper, ImageLoadNotify imageLoadNotify) {
        super(imageHolder, richTextConfig, textView, drawableWrapper, imageLoadNotify, openAssetInputStream(imageHolder, textView));
    }

    private static InputStream openAssetInputStream(ImageHolder imageHolder, TextView textView) {
        try {
            return textView.getContext().getAssets().open(getAssetFileName(imageHolder.getSource()));
        } catch (IOException e) {
            Debug.m122e(e);
            return null;
        }
    }

    private static String getAssetFileName(String str) {
        if (str == null || !str.startsWith("file:///android_asset/")) {
            return null;
        }
        return str.replace("file:///android_asset/", "");
    }
}
