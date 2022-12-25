package com.googlecode.mp4parser.authoring.samples;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.Container;
import com.coremedia.iso.boxes.TrackBox;
import com.coremedia.iso.boxes.fragment.MovieFragmentBox;
import com.coremedia.iso.boxes.fragment.TrackExtendsBox;
import com.coremedia.iso.boxes.fragment.TrackFragmentBox;
import com.coremedia.iso.boxes.fragment.TrackFragmentHeaderBox;
import com.coremedia.iso.boxes.fragment.TrackRunBox;
import com.googlecode.mp4parser.authoring.Sample;
import com.googlecode.mp4parser.util.CastUtils;
import com.googlecode.mp4parser.util.Path;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public class FragmentedMp4SampleList extends AbstractList<Sample> {
    private List<TrackFragmentBox> allTrafs;
    private int[] firstSamples;
    IsoFile[] fragments;
    private SoftReference<Sample>[] sampleCache;
    Container topLevel;
    TrackBox trackBox;
    TrackExtendsBox trex;
    private Map<TrackRunBox, SoftReference<ByteBuffer>> trunDataCache = new HashMap();
    private int size_ = -1;

    public FragmentedMp4SampleList(long j, Container container, IsoFile... isoFileArr) {
        this.trackBox = null;
        this.trex = null;
        this.topLevel = container;
        this.fragments = isoFileArr;
        for (TrackBox trackBox : Path.getPaths(container, "moov[0]/trak")) {
            if (trackBox.getTrackHeaderBox().getTrackId() == j) {
                this.trackBox = trackBox;
            }
        }
        if (this.trackBox == null) {
            throw new RuntimeException("This MP4 does not contain track " + j);
        }
        for (TrackExtendsBox trackExtendsBox : Path.getPaths(container, "moov[0]/mvex[0]/trex")) {
            if (trackExtendsBox.getTrackId() == this.trackBox.getTrackHeaderBox().getTrackId()) {
                this.trex = trackExtendsBox;
            }
        }
        this.sampleCache = (SoftReference[]) Array.newInstance(SoftReference.class, size());
        initAllFragments();
    }

    private List<TrackFragmentBox> initAllFragments() {
        List<TrackFragmentBox> list = this.allTrafs;
        if (list != null) {
            return list;
        }
        ArrayList arrayList = new ArrayList();
        for (MovieFragmentBox movieFragmentBox : this.topLevel.getBoxes(MovieFragmentBox.class)) {
            for (TrackFragmentBox trackFragmentBox : movieFragmentBox.getBoxes(TrackFragmentBox.class)) {
                if (trackFragmentBox.getTrackFragmentHeaderBox().getTrackId() == this.trackBox.getTrackHeaderBox().getTrackId()) {
                    arrayList.add(trackFragmentBox);
                }
            }
        }
        IsoFile[] isoFileArr = this.fragments;
        if (isoFileArr != null) {
            for (IsoFile isoFile : isoFileArr) {
                for (MovieFragmentBox movieFragmentBox2 : isoFile.getBoxes(MovieFragmentBox.class)) {
                    for (TrackFragmentBox trackFragmentBox2 : movieFragmentBox2.getBoxes(TrackFragmentBox.class)) {
                        if (trackFragmentBox2.getTrackFragmentHeaderBox().getTrackId() == this.trackBox.getTrackHeaderBox().getTrackId()) {
                            arrayList.add(trackFragmentBox2);
                        }
                    }
                }
            }
        }
        this.allTrafs = arrayList;
        this.firstSamples = new int[this.allTrafs.size()];
        int i = 1;
        for (int i2 = 0; i2 < this.allTrafs.size(); i2++) {
            this.firstSamples[i2] = i;
            i += getTrafSize(this.allTrafs.get(i2));
        }
        return arrayList;
    }

    private int getTrafSize(TrackFragmentBox trackFragmentBox) {
        List<Box> boxes = trackFragmentBox.getBoxes();
        int i = 0;
        for (int i2 = 0; i2 < boxes.size(); i2++) {
            Box box = boxes.get(i2);
            if (box instanceof TrackRunBox) {
                i += CastUtils.l2i(((TrackRunBox) box).getSampleCount());
            }
        }
        return i;
    }

    @Override // java.util.AbstractList, java.util.List
    /* renamed from: get */
    public Sample mo6317get(int i) {
        long j;
        Sample sample;
        SoftReference<Sample>[] softReferenceArr = this.sampleCache;
        if (softReferenceArr[i] == null || (sample = softReferenceArr[i].get()) == null) {
            int i2 = i + 1;
            int length = this.firstSamples.length;
            while (true) {
                length--;
                if (i2 - this.firstSamples[length] >= 0) {
                    break;
                }
            }
            TrackFragmentBox trackFragmentBox = this.allTrafs.get(length);
            int i3 = i2 - this.firstSamples[length];
            MovieFragmentBox movieFragmentBox = (MovieFragmentBox) trackFragmentBox.getParent();
            int i4 = 0;
            for (Box box : trackFragmentBox.getBoxes()) {
                if (box instanceof TrackRunBox) {
                    TrackRunBox trackRunBox = (TrackRunBox) box;
                    int i5 = i3 - i4;
                    if (trackRunBox.getEntries().size() < i5) {
                        i4 += trackRunBox.getEntries().size();
                    } else {
                        List<TrackRunBox.Entry> entries = trackRunBox.getEntries();
                        TrackFragmentHeaderBox trackFragmentHeaderBox = trackFragmentBox.getTrackFragmentHeaderBox();
                        boolean isSampleSizePresent = trackRunBox.isSampleSizePresent();
                        boolean hasDefaultSampleSize = trackFragmentHeaderBox.hasDefaultSampleSize();
                        long j2 = 0;
                        if (isSampleSizePresent) {
                            j = 0;
                        } else if (hasDefaultSampleSize) {
                            j = trackFragmentHeaderBox.getDefaultSampleSize();
                        } else {
                            TrackExtendsBox trackExtendsBox = this.trex;
                            if (trackExtendsBox == null) {
                                throw new RuntimeException("File doesn't contain trex box but track fragments aren't fully self contained. Cannot determine sample size.");
                            }
                            j = trackExtendsBox.getDefaultSampleSize();
                        }
                        SoftReference<ByteBuffer> softReference = this.trunDataCache.get(trackRunBox);
                        final ByteBuffer byteBuffer = softReference != null ? softReference.get() : null;
                        if (byteBuffer == null) {
                            Container container = movieFragmentBox;
                            if (trackFragmentHeaderBox.hasBaseDataOffset()) {
                                j2 = 0 + trackFragmentHeaderBox.getBaseDataOffset();
                                container = movieFragmentBox.getParent();
                            }
                            if (trackRunBox.isDataOffsetPresent()) {
                                j2 += trackRunBox.getDataOffset();
                            }
                            int i6 = 0;
                            for (TrackRunBox.Entry entry : entries) {
                                i6 = (int) (isSampleSizePresent ? i6 + entry.getSampleSize() : i6 + j);
                            }
                            try {
                                ByteBuffer byteBuffer2 = container.getByteBuffer(j2, i6);
                                this.trunDataCache.put(trackRunBox, new SoftReference<>(byteBuffer2));
                                byteBuffer = byteBuffer2;
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        final int i7 = 0;
                        for (int i8 = 0; i8 < i5; i8++) {
                            i7 = (int) (isSampleSizePresent ? i7 + entries.get(i8).getSampleSize() : i7 + j);
                        }
                        final long sampleSize = isSampleSizePresent ? entries.get(i5).getSampleSize() : j;
                        Sample sample2 = new Sample() { // from class: com.googlecode.mp4parser.authoring.samples.FragmentedMp4SampleList.1
                            @Override // com.googlecode.mp4parser.authoring.Sample
                            public void writeTo(WritableByteChannel writableByteChannel) throws IOException {
                                writableByteChannel.write(asByteBuffer());
                            }

                            @Override // com.googlecode.mp4parser.authoring.Sample
                            public long getSize() {
                                return sampleSize;
                            }

                            @Override // com.googlecode.mp4parser.authoring.Sample
                            public ByteBuffer asByteBuffer() {
                                return (ByteBuffer) ((ByteBuffer) byteBuffer.position(i7)).slice().limit(CastUtils.l2i(sampleSize));
                            }
                        };
                        this.sampleCache[i] = new SoftReference<>(sample2);
                        return sample2;
                    }
                }
            }
            throw new RuntimeException("Couldn't find sample in the traf I was looking");
        }
        return sample;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        int i = this.size_;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        for (MovieFragmentBox movieFragmentBox : this.topLevel.getBoxes(MovieFragmentBox.class)) {
            for (TrackFragmentBox trackFragmentBox : movieFragmentBox.getBoxes(TrackFragmentBox.class)) {
                if (trackFragmentBox.getTrackFragmentHeaderBox().getTrackId() == this.trackBox.getTrackHeaderBox().getTrackId()) {
                    i2 = (int) (i2 + ((TrackRunBox) trackFragmentBox.getBoxes(TrackRunBox.class).get(0)).getSampleCount());
                }
            }
        }
        int i3 = i2;
        for (IsoFile isoFile : this.fragments) {
            for (MovieFragmentBox movieFragmentBox2 : isoFile.getBoxes(MovieFragmentBox.class)) {
                for (TrackFragmentBox trackFragmentBox2 : movieFragmentBox2.getBoxes(TrackFragmentBox.class)) {
                    if (trackFragmentBox2.getTrackFragmentHeaderBox().getTrackId() == this.trackBox.getTrackHeaderBox().getTrackId()) {
                        i3 = (int) (i3 + ((TrackRunBox) trackFragmentBox2.getBoxes(TrackRunBox.class).get(0)).getSampleCount());
                    }
                }
            }
        }
        this.size_ = i3;
        return i3;
    }
}
