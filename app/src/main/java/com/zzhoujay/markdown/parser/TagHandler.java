package com.zzhoujay.markdown.parser;

/* loaded from: classes4.dex */
public interface TagHandler extends TagFinder, QueueConsumer, TagGetter {
    boolean codeBlock1(Line line);

    boolean codeBlock2(Line line);

    boolean gap(Line line);

    /* renamed from: h */
    boolean mo133h(Line line);

    boolean imageId(String str);

    boolean inline(Line line);

    boolean linkId(String str);

    /* renamed from: ol */
    boolean mo126ol(Line line);

    boolean quota(Line line);

    /* renamed from: ul */
    boolean mo124ul(Line line);
}
