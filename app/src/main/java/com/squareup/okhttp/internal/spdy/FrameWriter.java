package com.squareup.okhttp.internal.spdy;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import okio.Buffer;

/* loaded from: classes3.dex */
public interface FrameWriter extends Closeable {
    void ackSettings() throws IOException;

    void connectionPreface() throws IOException;

    void data(boolean z, int i, Buffer buffer, int i2) throws IOException;

    void flush() throws IOException;

    void goAway(int i, ErrorCode errorCode, byte[] bArr) throws IOException;

    void ping(boolean z, int i, int i2) throws IOException;

    void pushPromise(int i, int i2, List<Header> list) throws IOException;

    void rstStream(int i, ErrorCode errorCode) throws IOException;

    void settings(Settings settings) throws IOException;

    void synStream(boolean z, boolean z2, int i, int i2, List<Header> list) throws IOException;

    void windowUpdate(int i, long j) throws IOException;
}
