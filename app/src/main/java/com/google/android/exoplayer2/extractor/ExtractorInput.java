package com.google.android.exoplayer2.extractor;

import java.io.IOException;

/* loaded from: classes2.dex */
public interface ExtractorInput {
    void advancePeekPosition(int i) throws IOException, InterruptedException;

    long getLength();

    long getPeekPosition();

    long getPosition();

    void peekFully(byte[] bArr, int i, int i2) throws IOException, InterruptedException;

    boolean peekFully(byte[] bArr, int i, int i2, boolean z) throws IOException, InterruptedException;

    int read(byte[] bArr, int i, int i2) throws IOException, InterruptedException;

    void readFully(byte[] bArr, int i, int i2) throws IOException, InterruptedException;

    boolean readFully(byte[] bArr, int i, int i2, boolean z) throws IOException, InterruptedException;

    void resetPeekPosition();

    int skip(int i) throws IOException, InterruptedException;

    void skipFully(int i) throws IOException, InterruptedException;
}
