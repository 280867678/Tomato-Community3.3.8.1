package com.google.android.exoplayer2.decoder;

import java.lang.Exception;

/* loaded from: classes2.dex */
public interface Decoder<I, O, E extends Exception> {
    /* renamed from: dequeueInputBuffer */
    I mo6242dequeueInputBuffer() throws Exception;

    /* renamed from: dequeueOutputBuffer */
    O mo6243dequeueOutputBuffer() throws Exception;

    void flush();

    void queueInputBuffer(I i) throws Exception;

    void release();
}
