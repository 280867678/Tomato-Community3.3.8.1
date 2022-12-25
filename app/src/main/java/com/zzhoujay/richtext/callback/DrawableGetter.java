package com.zzhoujay.richtext.callback;

import android.graphics.drawable.Drawable;
import android.widget.TextView;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichTextConfig;

/* loaded from: classes4.dex */
public interface DrawableGetter {
    Drawable getDrawable(ImageHolder imageHolder, RichTextConfig richTextConfig, TextView textView);
}
