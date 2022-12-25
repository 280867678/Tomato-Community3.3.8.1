package com.danikula.videocache;

import java.io.File;

/* loaded from: classes2.dex */
public interface CacheListener {
    void onCacheAvailable(File file, String str, int i);

    void onFileLoadIsWrong();
}
