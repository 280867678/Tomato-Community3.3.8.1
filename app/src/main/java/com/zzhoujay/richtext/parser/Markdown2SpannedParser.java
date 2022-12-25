package com.zzhoujay.richtext.parser;

import android.text.Spanned;
import android.widget.TextView;
import com.zzhoujay.markdown.MarkDown;

/* loaded from: classes4.dex */
public class Markdown2SpannedParser implements SpannedParser {
    private TextView textView;

    public Markdown2SpannedParser(TextView textView) {
        this.textView = textView;
    }

    @Override // com.zzhoujay.richtext.parser.SpannedParser
    public Spanned parse(String str) {
        return MarkDown.fromMarkdown(str, null, this.textView);
    }
}
