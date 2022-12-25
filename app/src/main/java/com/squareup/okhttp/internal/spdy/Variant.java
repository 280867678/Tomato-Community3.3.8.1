package com.squareup.okhttp.internal.spdy;

import okio.BufferedSink;
import okio.BufferedSource;

/* loaded from: classes3.dex */
public interface Variant {
    int maxFrameSize();

    FrameReader newReader(BufferedSource bufferedSource, boolean z);

    FrameWriter newWriter(BufferedSink bufferedSink, boolean z);
}
