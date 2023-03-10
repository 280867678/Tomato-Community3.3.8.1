package com.facebook.imagepipeline.decoder;

import com.facebook.common.internal.Closeables;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Throwables;
import com.facebook.common.memory.ByteArrayPool;
import com.facebook.common.memory.PooledByteArrayBufferedInputStream;
import com.facebook.common.util.StreamUtil;
import com.facebook.imagepipeline.image.EncodedImage;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes2.dex */
public class ProgressiveJpegParser {
    private final ByteArrayPool mByteArrayPool;
    private boolean mEndMarkerRead;
    private int mBytesParsed = 0;
    private int mLastByteRead = 0;
    private int mNextFullScanNumber = 0;
    private int mBestScanEndOffset = 0;
    private int mBestScanNumber = 0;
    private int mParserState = 0;

    private static boolean doesMarkerStartSegment(int i) {
        if (i == 1) {
            return false;
        }
        return ((i >= 208 && i <= 215) || i == 217 || i == 216) ? false : true;
    }

    public ProgressiveJpegParser(ByteArrayPool byteArrayPool) {
        Preconditions.checkNotNull(byteArrayPool);
        this.mByteArrayPool = byteArrayPool;
    }

    public boolean parseMoreData(EncodedImage encodedImage) {
        if (this.mParserState != 6 && encodedImage.getSize() > this.mBytesParsed) {
            PooledByteArrayBufferedInputStream pooledByteArrayBufferedInputStream = new PooledByteArrayBufferedInputStream(encodedImage.getInputStream(), this.mByteArrayPool.mo5947get(16384), this.mByteArrayPool);
            try {
                try {
                    StreamUtil.skip(pooledByteArrayBufferedInputStream, this.mBytesParsed);
                    return doParseMoreData(pooledByteArrayBufferedInputStream);
                } catch (IOException e) {
                    Throwables.propagate(e);
                    throw null;
                }
            } finally {
                Closeables.closeQuietly(pooledByteArrayBufferedInputStream);
            }
        }
        return false;
    }

    private boolean doParseMoreData(InputStream inputStream) {
        int read;
        int i = this.mBestScanNumber;
        while (this.mParserState != 6 && (read = inputStream.read()) != -1) {
            try {
                this.mBytesParsed++;
                if (this.mEndMarkerRead) {
                    this.mParserState = 6;
                    this.mEndMarkerRead = false;
                    return false;
                }
                int i2 = this.mParserState;
                if (i2 != 0) {
                    if (i2 != 1) {
                        if (i2 != 2) {
                            if (i2 != 3) {
                                if (i2 == 4) {
                                    this.mParserState = 5;
                                } else if (i2 == 5) {
                                    int i3 = ((this.mLastByteRead << 8) + read) - 2;
                                    StreamUtil.skip(inputStream, i3);
                                    this.mBytesParsed += i3;
                                    this.mParserState = 2;
                                } else {
                                    Preconditions.checkState(false);
                                }
                            } else if (read == 255) {
                                this.mParserState = 3;
                            } else if (read == 0) {
                                this.mParserState = 2;
                            } else if (read == 217) {
                                this.mEndMarkerRead = true;
                                newScanOrImageEndFound(this.mBytesParsed - 2);
                                this.mParserState = 2;
                            } else {
                                if (read == 218) {
                                    newScanOrImageEndFound(this.mBytesParsed - 2);
                                }
                                if (doesMarkerStartSegment(read)) {
                                    this.mParserState = 4;
                                } else {
                                    this.mParserState = 2;
                                }
                            }
                        } else if (read == 255) {
                            this.mParserState = 3;
                        }
                    } else if (read == 216) {
                        this.mParserState = 2;
                    } else {
                        this.mParserState = 6;
                    }
                } else if (read == 255) {
                    this.mParserState = 1;
                } else {
                    this.mParserState = 6;
                }
                this.mLastByteRead = read;
            } catch (IOException e) {
                Throwables.propagate(e);
                throw null;
            }
        }
        return (this.mParserState == 6 || this.mBestScanNumber == i) ? false : true;
    }

    private void newScanOrImageEndFound(int i) {
        if (this.mNextFullScanNumber > 0) {
            this.mBestScanEndOffset = i;
        }
        int i2 = this.mNextFullScanNumber;
        this.mNextFullScanNumber = i2 + 1;
        this.mBestScanNumber = i2;
    }

    public int getBestScanEndOffset() {
        return this.mBestScanEndOffset;
    }

    public int getBestScanNumber() {
        return this.mBestScanNumber;
    }

    public boolean isEndMarkerRead() {
        return this.mEndMarkerRead;
    }
}
