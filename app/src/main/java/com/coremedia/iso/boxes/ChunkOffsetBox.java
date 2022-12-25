package com.coremedia.iso.boxes;

import com.googlecode.mp4parser.AbstractFullBox;

/* loaded from: classes2.dex */
public abstract class ChunkOffsetBox extends AbstractFullBox {
    public abstract long[] getChunkOffsets();

    public abstract void setChunkOffsets(long[] jArr);

    public ChunkOffsetBox(String str) {
        super(str);
    }

    public String toString() {
        return getClass().getSimpleName() + "[entryCount=" + getChunkOffsets().length + "]";
    }
}
