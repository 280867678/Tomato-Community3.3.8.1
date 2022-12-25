package com.danikula.videocache.sourcestorage;

import android.content.Context;

/* loaded from: classes2.dex */
public class SourceInfoStorageFactory {
    public static SourceInfoStorage newEmptySourceInfoStorage() {
        return new NoSourceInfoStorage();
    }

    public static SourceInfoStorage newEmptySourceInfoStorage(Context context) {
        return new NoSourceInfoStorage(context);
    }
}
