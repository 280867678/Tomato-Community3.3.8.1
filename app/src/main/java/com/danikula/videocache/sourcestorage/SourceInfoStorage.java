package com.danikula.videocache.sourcestorage;

import android.content.Context;
import com.danikula.videocache.SourceInfo;

/* loaded from: classes2.dex */
public interface SourceInfoStorage {
    SourceInfo get(String str);

    Context getContext();

    void put(String str, SourceInfo sourceInfo);
}
