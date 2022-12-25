package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import android.support.annotation.Nullable;
import java.io.IOException;

/* loaded from: classes.dex */
public interface DataSource {

    /* loaded from: classes.dex */
    public interface Factory {
        /* renamed from: createDataSource */
        DataSource mo6257createDataSource();
    }

    void close() throws IOException;

    @Nullable
    Uri getUri();

    long open(DataSpec dataSpec) throws IOException;

    int read(byte[] bArr, int i, int i2) throws IOException;
}
