package com.gen.p059mh.webapp_extensions.listener;

import android.graphics.Rect;

/* renamed from: com.gen.mh.webapp_extensions.listener.WAEImageCache */
/* loaded from: classes2.dex */
public interface WAEImageCache {
    void addCache(String str, byte[] bArr);

    boolean haCache(String str);

    byte[] readeCache(String str, Rect rect);
}
