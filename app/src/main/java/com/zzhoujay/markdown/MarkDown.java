package com.zzhoujay.markdown;

import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;
import com.zzhoujay.markdown.parser.StyleBuilderImpl;
import java.io.IOException;

/* loaded from: classes4.dex */
public class MarkDown {
    public static Spanned fromMarkdown(String str, Html.ImageGetter imageGetter, TextView textView) {
        try {
            return new MarkDownParser(str, new StyleBuilderImpl(textView, imageGetter)).parse();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
