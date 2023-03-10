package com.googlecode.mp4parser.authoring.tracks;

import com.coremedia.iso.boxes.CompositionTimeToSample;
import com.coremedia.iso.boxes.SampleDependencyTypeBox;
import com.coremedia.iso.boxes.SampleDescriptionBox;
import com.coremedia.iso.boxes.SubSampleInformationBox;
import com.coremedia.iso.boxes.TimeToSampleBox;
import com.googlecode.mp4parser.authoring.AbstractTrack;
import com.googlecode.mp4parser.authoring.Sample;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.TrackMetaData;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/* loaded from: classes3.dex */
public class CroppedTrack extends AbstractTrack {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private int fromSample;
    Track origTrack;
    private int toSample;

    public CroppedTrack(Track track, long j, long j2) {
        super("crop(" + track.getName() + ")");
        this.origTrack = track;
        this.fromSample = (int) j;
        this.toSample = (int) j2;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.origTrack.close();
    }

    @Override // com.googlecode.mp4parser.authoring.Track
    public List<Sample> getSamples() {
        return this.origTrack.getSamples().subList(this.fromSample, this.toSample);
    }

    @Override // com.googlecode.mp4parser.authoring.Track
    public SampleDescriptionBox getSampleDescriptionBox() {
        return this.origTrack.getSampleDescriptionBox();
    }

    @Override // com.googlecode.mp4parser.authoring.Track
    public synchronized long[] getSampleDurations() {
        long[] jArr;
        jArr = new long[this.toSample - this.fromSample];
        System.arraycopy(this.origTrack.getSampleDurations(), this.fromSample, jArr, 0, jArr.length);
        return jArr;
    }

    static List<TimeToSampleBox.Entry> getDecodingTimeEntries(List<TimeToSampleBox.Entry> list, long j, long j2) {
        TimeToSampleBox.Entry next;
        if (list == null || list.isEmpty()) {
            return null;
        }
        long j3 = 0;
        ListIterator<TimeToSampleBox.Entry> listIterator = list.listIterator();
        LinkedList linkedList = new LinkedList();
        while (true) {
            next = listIterator.next();
            if (next.getCount() + j3 > j) {
                break;
            }
            j3 += next.getCount();
        }
        if (next.getCount() + j3 >= j2) {
            linkedList.add(new TimeToSampleBox.Entry(j2 - j, next.getDelta()));
            return linkedList;
        }
        linkedList.add(new TimeToSampleBox.Entry((next.getCount() + j3) - j, next.getDelta()));
        long count = next.getCount();
        while (true) {
            j3 += count;
            if (!listIterator.hasNext()) {
                break;
            }
            next = listIterator.next();
            if (next.getCount() + j3 >= j2) {
                break;
            }
            linkedList.add(next);
            count = next.getCount();
        }
        linkedList.add(new TimeToSampleBox.Entry(j2 - j3, next.getDelta()));
        return linkedList;
    }

    @Override // com.googlecode.mp4parser.authoring.AbstractTrack, com.googlecode.mp4parser.authoring.Track
    public List<CompositionTimeToSample.Entry> getCompositionTimeEntries() {
        return getCompositionTimeEntries(this.origTrack.getCompositionTimeEntries(), this.fromSample, this.toSample);
    }

    static List<CompositionTimeToSample.Entry> getCompositionTimeEntries(List<CompositionTimeToSample.Entry> list, long j, long j2) {
        CompositionTimeToSample.Entry next;
        if (list == null || list.isEmpty()) {
            return null;
        }
        long j3 = 0;
        ListIterator<CompositionTimeToSample.Entry> listIterator = list.listIterator();
        ArrayList arrayList = new ArrayList();
        while (true) {
            next = listIterator.next();
            if (next.getCount() + j3 > j) {
                break;
            }
            j3 += next.getCount();
        }
        if (next.getCount() + j3 >= j2) {
            arrayList.add(new CompositionTimeToSample.Entry((int) (j2 - j), next.getOffset()));
            return arrayList;
        }
        arrayList.add(new CompositionTimeToSample.Entry((int) ((next.getCount() + j3) - j), next.getOffset()));
        int count = next.getCount();
        while (true) {
            j3 += count;
            if (!listIterator.hasNext()) {
                break;
            }
            next = listIterator.next();
            if (next.getCount() + j3 >= j2) {
                break;
            }
            arrayList.add(next);
            count = next.getCount();
        }
        arrayList.add(new CompositionTimeToSample.Entry((int) (j2 - j3), next.getOffset()));
        return arrayList;
    }

    @Override // com.googlecode.mp4parser.authoring.AbstractTrack, com.googlecode.mp4parser.authoring.Track
    public synchronized long[] getSyncSamples() {
        if (this.origTrack.getSyncSamples() != null) {
            long[] syncSamples = this.origTrack.getSyncSamples();
            int length = syncSamples.length;
            int i = 0;
            while (i < syncSamples.length && syncSamples[i] < this.fromSample) {
                i++;
            }
            while (length > 0 && this.toSample < syncSamples[length - 1]) {
                length--;
            }
            long[] copyOfRange = Arrays.copyOfRange(this.origTrack.getSyncSamples(), i, length);
            for (int i2 = 0; i2 < copyOfRange.length; i2++) {
                copyOfRange[i2] = copyOfRange[i2] - this.fromSample;
            }
            return copyOfRange;
        }
        return null;
    }

    @Override // com.googlecode.mp4parser.authoring.AbstractTrack, com.googlecode.mp4parser.authoring.Track
    public List<SampleDependencyTypeBox.Entry> getSampleDependencies() {
        if (this.origTrack.getSampleDependencies() == null || this.origTrack.getSampleDependencies().isEmpty()) {
            return null;
        }
        return this.origTrack.getSampleDependencies().subList(this.fromSample, this.toSample);
    }

    @Override // com.googlecode.mp4parser.authoring.Track
    public TrackMetaData getTrackMetaData() {
        return this.origTrack.getTrackMetaData();
    }

    @Override // com.googlecode.mp4parser.authoring.Track
    public String getHandler() {
        return this.origTrack.getHandler();
    }

    @Override // com.googlecode.mp4parser.authoring.AbstractTrack, com.googlecode.mp4parser.authoring.Track
    public SubSampleInformationBox getSubsampleInformationBox() {
        return this.origTrack.getSubsampleInformationBox();
    }
}
