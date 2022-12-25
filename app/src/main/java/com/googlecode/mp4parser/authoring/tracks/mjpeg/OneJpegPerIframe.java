package com.googlecode.mp4parser.authoring.tracks.mjpeg;

import com.coremedia.iso.Hex;
import com.coremedia.iso.boxes.CompositionTimeToSample;
import com.coremedia.iso.boxes.SampleDescriptionBox;
import com.coremedia.iso.boxes.sampleentry.VisualSampleEntry;
import com.googlecode.mp4parser.authoring.AbstractTrack;
import com.googlecode.mp4parser.authoring.Edit;
import com.googlecode.mp4parser.authoring.Sample;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.TrackMetaData;
import com.googlecode.mp4parser.boxes.mp4.ESDescriptorBox;
import com.googlecode.mp4parser.boxes.mp4.objectdescriptors.ESDescriptor;
import com.googlecode.mp4parser.boxes.mp4.objectdescriptors.ObjectDescriptorFactory;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;

/* loaded from: classes3.dex */
public class OneJpegPerIframe extends AbstractTrack {
    File[] jpegs;
    long[] sampleDurations;
    SampleDescriptionBox stsd;
    long[] syncSamples;
    TrackMetaData trackMetaData = new TrackMetaData();

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
    }

    @Override // com.googlecode.mp4parser.authoring.Track
    public String getHandler() {
        return "vide";
    }

    public OneJpegPerIframe(String str, File[] fileArr, Track track) throws IOException {
        super(str);
        this.jpegs = fileArr;
        if (track.getSyncSamples().length != fileArr.length) {
            throw new RuntimeException("Number of sync samples doesn't match the number of stills (" + track.getSyncSamples().length + " vs. " + fileArr.length + ")");
        }
        BufferedImage read = ImageIO.read(fileArr[0]);
        this.trackMetaData.setWidth(read.getWidth());
        this.trackMetaData.setHeight(read.getHeight());
        this.trackMetaData.setTimescale(track.getTrackMetaData().getTimescale());
        long[] sampleDurations = track.getSampleDurations();
        long[] syncSamples = track.getSyncSamples();
        this.sampleDurations = new long[syncSamples.length];
        boolean z = true;
        long j = 0;
        int i = 1;
        for (int i2 = 1; i2 < sampleDurations.length; i2++) {
            if (i < syncSamples.length && i2 == syncSamples[i]) {
                this.sampleDurations[i - 1] = j;
                i++;
                j = 0;
            }
            j += sampleDurations[i2];
        }
        long[] jArr = this.sampleDurations;
        jArr[jArr.length - 1] = j;
        this.stsd = new SampleDescriptionBox();
        VisualSampleEntry visualSampleEntry = new VisualSampleEntry(VisualSampleEntry.TYPE1);
        this.stsd.addBox(visualSampleEntry);
        ESDescriptorBox eSDescriptorBox = new ESDescriptorBox();
        eSDescriptorBox.setData(ByteBuffer.wrap(Hex.decodeHex("038080801B000100048080800D6C11000000000A1CB4000A1CB4068080800102")));
        eSDescriptorBox.setEsDescriptor((ESDescriptor) ObjectDescriptorFactory.createFrom(-1, ByteBuffer.wrap(Hex.decodeHex("038080801B000100048080800D6C11000000000A1CB4000A1CB4068080800102"))));
        visualSampleEntry.addBox(eSDescriptorBox);
        this.syncSamples = new long[fileArr.length];
        int i3 = 0;
        while (true) {
            long[] jArr2 = this.syncSamples;
            if (i3 >= jArr2.length) {
                break;
            }
            int i4 = i3 + 1;
            jArr2[i3] = i4;
            i3 = i4;
        }
        double d = 0.0d;
        boolean z2 = true;
        for (Edit edit : track.getEdits()) {
            if (edit.getMediaTime() == -1 && !z) {
                throw new RuntimeException("Cannot accept edit list for processing (1)");
            }
            if (edit.getMediaTime() >= 0 && !z2) {
                throw new RuntimeException("Cannot accept edit list for processing (2)");
            }
            if (edit.getMediaTime() == -1) {
                d += edit.getSegmentDuration();
            } else {
                d -= edit.getMediaTime() / edit.getTimeScale();
                z = false;
                z2 = false;
            }
        }
        if (track.getCompositionTimeEntries() != null && track.getCompositionTimeEntries().size() > 0) {
            int[] copyOfRange = Arrays.copyOfRange(CompositionTimeToSample.blowupCompositionTimes(track.getCompositionTimeEntries()), 0, 50);
            long j2 = 0;
            for (int i5 = 0; i5 < copyOfRange.length; i5++) {
                copyOfRange[i5] = (int) (copyOfRange[i5] + j2);
                j2 += track.getSampleDurations()[i5];
            }
            Arrays.sort(copyOfRange);
            d += copyOfRange[0] / track.getTrackMetaData().getTimescale();
        }
        if (d < 0.0d) {
            getEdits().add(new Edit((long) ((-d) * getTrackMetaData().getTimescale()), getTrackMetaData().getTimescale(), 1.0d, getDuration() / getTrackMetaData().getTimescale()));
        } else if (d > 0.0d) {
            getEdits().add(new Edit(-1L, getTrackMetaData().getTimescale(), 1.0d, d));
            getEdits().add(new Edit(0L, getTrackMetaData().getTimescale(), 1.0d, getDuration() / getTrackMetaData().getTimescale()));
        }
    }

    @Override // com.googlecode.mp4parser.authoring.Track
    public SampleDescriptionBox getSampleDescriptionBox() {
        return this.stsd;
    }

    @Override // com.googlecode.mp4parser.authoring.Track
    public long[] getSampleDurations() {
        return this.sampleDurations;
    }

    @Override // com.googlecode.mp4parser.authoring.Track
    public TrackMetaData getTrackMetaData() {
        return this.trackMetaData;
    }

    @Override // com.googlecode.mp4parser.authoring.AbstractTrack, com.googlecode.mp4parser.authoring.Track
    public long[] getSyncSamples() {
        return this.syncSamples;
    }

    @Override // com.googlecode.mp4parser.authoring.Track
    public List<Sample> getSamples() {
        return new AbstractList<Sample>() { // from class: com.googlecode.mp4parser.authoring.tracks.mjpeg.OneJpegPerIframe.1
            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                return OneJpegPerIframe.this.jpegs.length;
            }

            @Override // java.util.AbstractList, java.util.List
            /* renamed from: get */
            public Sample mo6320get(final int i) {
                return new Sample() { // from class: com.googlecode.mp4parser.authoring.tracks.mjpeg.OneJpegPerIframe.1.1
                    ByteBuffer sample = null;

                    @Override // com.googlecode.mp4parser.authoring.Sample
                    public void writeTo(WritableByteChannel writableByteChannel) throws IOException {
                        RandomAccessFile randomAccessFile = new RandomAccessFile(OneJpegPerIframe.this.jpegs[i], "r");
                        randomAccessFile.getChannel().transferTo(0L, randomAccessFile.length(), writableByteChannel);
                        randomAccessFile.close();
                    }

                    @Override // com.googlecode.mp4parser.authoring.Sample
                    public long getSize() {
                        return OneJpegPerIframe.this.jpegs[i].length();
                    }

                    @Override // com.googlecode.mp4parser.authoring.Sample
                    public ByteBuffer asByteBuffer() {
                        if (this.sample == null) {
                            try {
                                RandomAccessFile randomAccessFile = new RandomAccessFile(OneJpegPerIframe.this.jpegs[i], "r");
                                this.sample = randomAccessFile.getChannel().map(FileChannel.MapMode.READ_ONLY, 0L, randomAccessFile.length());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        return this.sample;
                    }
                };
            }
        };
    }
}
