package com.tencent.ijk.media.player.misc;

import java.io.IOException;

/* loaded from: classes3.dex */
public interface IAndroidIO {
    int close() throws IOException;

    int open(String str) throws IOException;

    int read(byte[] bArr, int i) throws IOException;

    long seek(long j, int i) throws IOException;
}
