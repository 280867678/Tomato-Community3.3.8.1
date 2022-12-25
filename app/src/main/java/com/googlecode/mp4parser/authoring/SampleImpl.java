package com.googlecode.mp4parser.authoring;

import com.coremedia.iso.boxes.Container;
import com.googlecode.mp4parser.util.CastUtils;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

/* loaded from: classes3.dex */
public class SampleImpl implements Sample {
    private ByteBuffer[] data;
    private final long offset;
    private final Container parent;
    private final long size;

    public SampleImpl(ByteBuffer byteBuffer) {
        this.offset = -1L;
        this.size = byteBuffer.limit();
        this.data = new ByteBuffer[]{byteBuffer};
        this.parent = null;
    }

    public SampleImpl(ByteBuffer[] byteBufferArr) {
        this.offset = -1L;
        int i = 0;
        for (ByteBuffer byteBuffer : byteBufferArr) {
            i += byteBuffer.remaining();
        }
        this.size = i;
        this.data = byteBufferArr;
        this.parent = null;
    }

    public SampleImpl(long j, long j2, ByteBuffer byteBuffer) {
        this.offset = j;
        this.size = j2;
        this.data = new ByteBuffer[]{byteBuffer};
        this.parent = null;
    }

    public SampleImpl(long j, long j2, Container container) {
        this.offset = j;
        this.size = j2;
        this.data = null;
        this.parent = container;
    }

    protected void ensureData() {
        if (this.data != null) {
            return;
        }
        Container container = this.parent;
        if (container == null) {
            throw new RuntimeException("Missing parent container, can't read sample " + this);
        }
        try {
            this.data = new ByteBuffer[]{container.getByteBuffer(this.offset, this.size)};
        } catch (IOException e) {
            throw new RuntimeException("couldn't read sample " + this, e);
        }
    }

    @Override // com.googlecode.mp4parser.authoring.Sample
    public void writeTo(WritableByteChannel writableByteChannel) throws IOException {
        ensureData();
        for (ByteBuffer byteBuffer : this.data) {
            writableByteChannel.write(byteBuffer.duplicate());
        }
    }

    @Override // com.googlecode.mp4parser.authoring.Sample
    public long getSize() {
        return this.size;
    }

    @Override // com.googlecode.mp4parser.authoring.Sample
    public ByteBuffer asByteBuffer() {
        ensureData();
        ByteBuffer wrap = ByteBuffer.wrap(new byte[CastUtils.l2i(this.size)]);
        for (ByteBuffer byteBuffer : this.data) {
            wrap.put(byteBuffer.duplicate());
        }
        wrap.rewind();
        return wrap;
    }

    public String toString() {
        return "SampleImpl{offset=" + this.offset + "{size=" + this.size + '}';
    }
}
