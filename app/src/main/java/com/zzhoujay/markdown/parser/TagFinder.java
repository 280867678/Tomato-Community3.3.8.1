package com.zzhoujay.markdown.parser;

/* loaded from: classes4.dex */
public interface TagFinder {
    boolean find(int i, Line line);

    boolean find(int i, String str);

    int findCount(int i, Line line, int i2);
}
