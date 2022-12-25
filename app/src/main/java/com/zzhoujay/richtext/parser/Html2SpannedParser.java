package com.zzhoujay.richtext.parser;

import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import java.lang.reflect.Method;

/* loaded from: classes4.dex */
public class Html2SpannedParser implements SpannedParser {
    private static final Method Z_FROM_HTML_METHOD;
    private Html.TagHandler tagHandler;

    static {
        Method method;
        try {
            method = Class.forName("com.zzhoujay.html.Html").getMethod("fromHtml", String.class, Html.ImageGetter.class, Html.TagHandler.class);
        } catch (Exception unused) {
            method = null;
        }
        Z_FROM_HTML_METHOD = method;
    }

    public Html2SpannedParser(Html.TagHandler tagHandler) {
        this.tagHandler = tagHandler;
    }

    @Override // com.zzhoujay.richtext.parser.SpannedParser
    public Spanned parse(String str) {
        Method method = Z_FROM_HTML_METHOD;
        if (method != null) {
            try {
                return (Spanned) method.invoke(null, str, null, this.tagHandler);
            } catch (Exception e) {
                Log.d("Html2SpannedParser", "Z_FROM_HTML_METHOD invoke failure", e);
            }
        }
        return Html.fromHtml(str, null, this.tagHandler);
    }
}
