package com.danikula.videocache.sourcestorage;

import android.content.Context;
import com.danikula.videocache.SourceInfo;

/* loaded from: classes2.dex */
public class NoSourceInfoStorage implements SourceInfoStorage {
    public Context mContext;

    @Override // com.danikula.videocache.sourcestorage.SourceInfoStorage
    public SourceInfo get(String str) {
        return null;
    }

    @Override // com.danikula.videocache.sourcestorage.SourceInfoStorage
    public void put(String str, SourceInfo sourceInfo) {
    }

    public NoSourceInfoStorage() {
    }

    public NoSourceInfoStorage(Context context) {
        this.mContext = context;
    }

    @Override // com.danikula.videocache.sourcestorage.SourceInfoStorage
    public Context getContext() {
        return this.mContext;
    }
}
