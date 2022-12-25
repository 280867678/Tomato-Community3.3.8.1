package com.googlecode.mp4parser.authoring.samples;

import com.coremedia.iso.boxes.Container;
import com.coremedia.iso.boxes.MovieBox;
import com.coremedia.iso.boxes.SampleSizeBox;
import com.coremedia.iso.boxes.SampleToChunkBox;
import com.coremedia.iso.boxes.TrackBox;
import com.googlecode.mp4parser.authoring.Sample;
import com.googlecode.mp4parser.util.CastUtils;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class DefaultMp4SampleList extends AbstractList<Sample> {
    private static final long MAX_MAP_SIZE = 268435456;
    ByteBuffer[][] cache;
    int[] chunkNumsStartSampleNum;
    long[] chunkOffsets;
    long[] chunkSizes;
    int lastChunk = 0;
    long[][] sampleOffsetsWithinChunks;
    SampleSizeBox ssb;
    Container topLevel;
    TrackBox trackBox;

    public DefaultMp4SampleList(long j, Container container) {
        int i;
        this.trackBox = null;
        this.cache = null;
        int i2 = 0;
        this.topLevel = container;
        for (TrackBox trackBox : ((MovieBox) container.getBoxes(MovieBox.class).get(0)).getBoxes(TrackBox.class)) {
            if (trackBox.getTrackHeaderBox().getTrackId() == j) {
                this.trackBox = trackBox;
            }
        }
        TrackBox trackBox2 = this.trackBox;
        if (trackBox2 == null) {
            throw new RuntimeException("This MP4 does not contain track " + j);
        }
        this.chunkOffsets = trackBox2.getSampleTableBox().getChunkOffsetBox().getChunkOffsets();
        long[] jArr = this.chunkOffsets;
        this.chunkSizes = new long[jArr.length];
        this.cache = new ByteBuffer[jArr.length];
        this.sampleOffsetsWithinChunks = new long[jArr.length];
        this.ssb = this.trackBox.getSampleTableBox().getSampleSizeBox();
        List<SampleToChunkBox.Entry> entries = this.trackBox.getSampleTableBox().getSampleToChunkBox().getEntries();
        SampleToChunkBox.Entry[] entryArr = (SampleToChunkBox.Entry[]) entries.toArray(new SampleToChunkBox.Entry[entries.size()]);
        SampleToChunkBox.Entry entry = entryArr[0];
        long firstChunk = entry.getFirstChunk();
        int l2i = CastUtils.l2i(entry.getSamplesPerChunk());
        int size = size();
        int i3 = l2i;
        int i4 = 0;
        int i5 = 1;
        int i6 = 0;
        int i7 = 1;
        do {
            i4++;
            if (i4 == firstChunk) {
                if (entryArr.length > i5) {
                    SampleToChunkBox.Entry entry2 = entryArr[i5];
                    i6 = i3;
                    i3 = CastUtils.l2i(entry2.getSamplesPerChunk());
                    i5++;
                    firstChunk = entry2.getFirstChunk();
                } else {
                    i6 = i3;
                    i3 = -1;
                    firstChunk = Long.MAX_VALUE;
                }
            }
            this.sampleOffsetsWithinChunks[i4 - 1] = new long[i6];
            i7 += i6;
        } while (i7 <= size);
        this.chunkNumsStartSampleNum = new int[i4 + 1];
        SampleToChunkBox.Entry entry3 = entryArr[0];
        long firstChunk2 = entry3.getFirstChunk();
        int i8 = 1;
        int i9 = 1;
        int i10 = 0;
        int l2i2 = CastUtils.l2i(entry3.getSamplesPerChunk());
        int i11 = 0;
        while (true) {
            i = i11 + 1;
            this.chunkNumsStartSampleNum[i11] = i8;
            if (i == firstChunk2) {
                if (entryArr.length > i9) {
                    int i12 = i9 + 1;
                    SampleToChunkBox.Entry entry4 = entryArr[i9];
                    int l2i3 = CastUtils.l2i(entry4.getSamplesPerChunk());
                    firstChunk2 = entry4.getFirstChunk();
                    i9 = i12;
                    i10 = l2i2;
                    l2i2 = l2i3;
                } else {
                    i10 = l2i2;
                    l2i2 = -1;
                    firstChunk2 = Long.MAX_VALUE;
                }
            }
            i8 += i10;
            if (i8 > size) {
                break;
            }
            i11 = i;
        }
        this.chunkNumsStartSampleNum[i] = Integer.MAX_VALUE;
        long j2 = 0;
        for (int i13 = 1; i13 <= this.ssb.getSampleCount(); i13++) {
            while (i13 == this.chunkNumsStartSampleNum[i2]) {
                i2++;
                j2 = 0;
            }
            long[] jArr2 = this.chunkSizes;
            int i14 = i2 - 1;
            int i15 = i13 - 1;
            jArr2[i14] = jArr2[i14] + this.ssb.getSampleSizeAtIndex(i15);
            this.sampleOffsetsWithinChunks[i14][i13 - this.chunkNumsStartSampleNum[i14]] = j2;
            j2 += this.ssb.getSampleSizeAtIndex(i15);
        }
    }

    synchronized int getChunkForSample(int i) {
        int i2 = i + 1;
        if (i2 >= this.chunkNumsStartSampleNum[this.lastChunk] && i2 < this.chunkNumsStartSampleNum[this.lastChunk + 1]) {
            return this.lastChunk;
        } else if (i2 < this.chunkNumsStartSampleNum[this.lastChunk]) {
            this.lastChunk = 0;
            while (this.chunkNumsStartSampleNum[this.lastChunk + 1] <= i2) {
                this.lastChunk++;
            }
            return this.lastChunk;
        } else {
            this.lastChunk++;
            while (this.chunkNumsStartSampleNum[this.lastChunk + 1] <= i2) {
                this.lastChunk++;
            }
            return this.lastChunk;
        }
    }

    @Override // java.util.AbstractList, java.util.List
    /* renamed from: get */
    public Sample mo6316get(int i) {
        long j;
        final ByteBuffer byteBuffer;
        if (i >= this.ssb.getSampleCount()) {
            throw new IndexOutOfBoundsException();
        }
        int chunkForSample = getChunkForSample(i);
        int i2 = this.chunkNumsStartSampleNum[chunkForSample] - 1;
        long j2 = chunkForSample;
        long j3 = this.chunkOffsets[CastUtils.l2i(j2)];
        long[] jArr = this.sampleOffsetsWithinChunks[CastUtils.l2i(j2)];
        long j4 = jArr[i - i2];
        ByteBuffer[] byteBufferArr = this.cache[CastUtils.l2i(j2)];
        if (byteBufferArr == null) {
            ArrayList arrayList = new ArrayList();
            long j5 = 0;
            int i3 = 0;
            while (i3 < jArr.length) {
                try {
                    long j6 = j4;
                    if ((jArr[i3] + this.ssb.getSampleSizeAtIndex(i3 + i2)) - j5 > MAX_MAP_SIZE) {
                        arrayList.add(this.topLevel.getByteBuffer(j3 + j5, jArr[i3] - j5));
                        j5 = jArr[i3];
                    }
                    i3++;
                    j4 = j6;
                } catch (IOException e) {
                    throw new IndexOutOfBoundsException(e.getMessage());
                }
            }
            j = j4;
            arrayList.add(this.topLevel.getByteBuffer(j3 + j5, (-j5) + jArr[jArr.length - 1] + this.ssb.getSampleSizeAtIndex((i2 + jArr.length) - 1)));
            byteBufferArr = (ByteBuffer[]) arrayList.toArray(new ByteBuffer[arrayList.size()]);
            this.cache[CastUtils.l2i(j2)] = byteBufferArr;
        } else {
            j = j4;
        }
        int length = byteBufferArr.length;
        final long j7 = j;
        int i4 = 0;
        while (true) {
            if (i4 >= length) {
                byteBuffer = null;
                break;
            }
            byteBuffer = byteBufferArr[i4];
            if (j7 < byteBuffer.limit()) {
                break;
            }
            j7 -= byteBuffer.limit();
            i4++;
        }
        final long sampleSizeAtIndex = this.ssb.getSampleSizeAtIndex(i);
        return new Sample() { // from class: com.googlecode.mp4parser.authoring.samples.DefaultMp4SampleList.1
            @Override // com.googlecode.mp4parser.authoring.Sample
            public void writeTo(WritableByteChannel writableByteChannel) throws IOException {
                writableByteChannel.write(asByteBuffer());
            }

            @Override // com.googlecode.mp4parser.authoring.Sample
            public long getSize() {
                return sampleSizeAtIndex;
            }

            @Override // com.googlecode.mp4parser.authoring.Sample
            public ByteBuffer asByteBuffer() {
                return (ByteBuffer) ((ByteBuffer) byteBuffer.position(CastUtils.l2i(j7))).slice().limit(CastUtils.l2i(sampleSizeAtIndex));
            }

            public String toString() {
                return "DefaultMp4Sample(size:" + sampleSizeAtIndex + ")";
            }
        };
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return CastUtils.l2i(this.trackBox.getSampleTableBox().getSampleSizeBox().getSampleCount());
    }
}
