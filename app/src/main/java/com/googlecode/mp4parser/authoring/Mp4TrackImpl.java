package com.googlecode.mp4parser.authoring;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.CompositionTimeToSample;
import com.coremedia.iso.boxes.Container;
import com.coremedia.iso.boxes.EditListBox;
import com.coremedia.iso.boxes.MediaHeaderBox;
import com.coremedia.iso.boxes.MovieHeaderBox;
import com.coremedia.iso.boxes.SampleDependencyTypeBox;
import com.coremedia.iso.boxes.SampleDescriptionBox;
import com.coremedia.iso.boxes.SampleTableBox;
import com.coremedia.iso.boxes.SubSampleInformationBox;
import com.coremedia.iso.boxes.TimeToSampleBox;
import com.coremedia.iso.boxes.TrackBox;
import com.coremedia.iso.boxes.TrackHeaderBox;
import com.coremedia.iso.boxes.fragment.MovieExtendsBox;
import com.coremedia.iso.boxes.fragment.MovieFragmentBox;
import com.coremedia.iso.boxes.fragment.SampleFlags;
import com.coremedia.iso.boxes.fragment.TrackExtendsBox;
import com.coremedia.iso.boxes.fragment.TrackFragmentBox;
import com.coremedia.iso.boxes.fragment.TrackFragmentHeaderBox;
import com.coremedia.iso.boxes.fragment.TrackRunBox;
import com.coremedia.iso.boxes.mdat.SampleList;
import com.googlecode.mp4parser.AbstractContainerBox;
import com.googlecode.mp4parser.BasicContainer;
import com.googlecode.mp4parser.boxes.mp4.samplegrouping.GroupEntry;
import com.googlecode.mp4parser.boxes.mp4.samplegrouping.SampleGroupDescriptionBox;
import com.googlecode.mp4parser.boxes.mp4.samplegrouping.SampleToGroupBox;
import com.googlecode.mp4parser.util.CastUtils;
import com.googlecode.mp4parser.util.Path;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public class Mp4TrackImpl extends AbstractTrack {
    private long[] decodingTimes;
    IsoFile[] fragments;
    private String handler;
    private SampleDescriptionBox sampleDescriptionBox;
    private List<Sample> samples;
    private SubSampleInformationBox subSampleInformationBox;
    private long[] syncSamples;
    TrackBox trackBox;
    private TrackMetaData trackMetaData = new TrackMetaData();
    private List<CompositionTimeToSample.Entry> compositionTimeEntries = new ArrayList();
    private List<SampleDependencyTypeBox.Entry> sampleDependencies = new ArrayList();

    public Mp4TrackImpl(String str, TrackBox trackBox, IsoFile... isoFileArr) {
        super(str);
        Iterator it2;
        long j;
        Iterator it3;
        Iterator it4;
        long j2;
        Iterator it5;
        Iterator it6;
        Iterator it7;
        Iterator it8;
        long j3;
        boolean z;
        SampleFlags defaultSampleFlags;
        int i;
        int i2 = 0;
        this.syncSamples = new long[0];
        this.subSampleInformationBox = null;
        long trackId = trackBox.getTrackHeaderBox().getTrackId();
        this.samples = new SampleList(trackBox, isoFileArr);
        SampleTableBox sampleTableBox = trackBox.getMediaBox().getMediaInformationBox().getSampleTableBox();
        this.handler = trackBox.getMediaBox().getHandlerBox().getHandlerType();
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(sampleTableBox.getTimeToSampleBox().getEntries());
        if (sampleTableBox.getCompositionTimeToSample() != null) {
            this.compositionTimeEntries.addAll(sampleTableBox.getCompositionTimeToSample().getEntries());
        }
        if (sampleTableBox.getSampleDependencyTypeBox() != null) {
            this.sampleDependencies.addAll(sampleTableBox.getSampleDependencyTypeBox().getEntries());
        }
        if (sampleTableBox.getSyncSampleBox() != null) {
            this.syncSamples = sampleTableBox.getSyncSampleBox().getSampleNumber();
        }
        this.subSampleInformationBox = (SubSampleInformationBox) Path.getPath((AbstractContainerBox) sampleTableBox, SubSampleInformationBox.TYPE);
        ArrayList<MovieFragmentBox> arrayList2 = new ArrayList();
        arrayList2.addAll(((Box) trackBox.getParent()).getParent().getBoxes(MovieFragmentBox.class));
        for (IsoFile isoFile : isoFileArr) {
            arrayList2.addAll(isoFile.getBoxes(MovieFragmentBox.class));
        }
        this.sampleDescriptionBox = sampleTableBox.getSampleDescriptionBox();
        List boxes = trackBox.getParent().getBoxes(MovieExtendsBox.class);
        if (boxes.size() > 0) {
            Iterator it9 = boxes.iterator();
            while (it9.hasNext()) {
                Iterator it10 = ((MovieExtendsBox) it9.next()).getBoxes(TrackExtendsBox.class).iterator();
                while (it10.hasNext()) {
                    TrackExtendsBox trackExtendsBox = (TrackExtendsBox) it10.next();
                    if (trackExtendsBox.getTrackId() == trackId) {
                        if (Path.getPaths(((Box) trackBox.getParent()).getParent(), "/moof/traf/subs").size() > 0) {
                            this.subSampleInformationBox = new SubSampleInformationBox();
                        }
                        LinkedList<Long> linkedList = new LinkedList();
                        Iterator it11 = arrayList2.iterator();
                        long j4 = 1;
                        while (it11.hasNext()) {
                            Iterator it12 = ((MovieFragmentBox) it11.next()).getBoxes(TrackFragmentBox.class).iterator();
                            while (it12.hasNext()) {
                                TrackFragmentBox trackFragmentBox = (TrackFragmentBox) it12.next();
                                if (trackFragmentBox.getTrackFragmentHeaderBox().getTrackId() == trackId) {
                                    SubSampleInformationBox subSampleInformationBox = (SubSampleInformationBox) Path.getPath((AbstractContainerBox) trackFragmentBox, SubSampleInformationBox.TYPE);
                                    if (subSampleInformationBox != null) {
                                        it6 = it11;
                                        it7 = it12;
                                        long j5 = (j4 - i2) - 1;
                                        for (SubSampleInformationBox.SubSampleEntry subSampleEntry : subSampleInformationBox.getEntries()) {
                                            SubSampleInformationBox.SubSampleEntry subSampleEntry2 = new SubSampleInformationBox.SubSampleEntry();
                                            Iterator it13 = it9;
                                            Iterator it14 = it10;
                                            subSampleEntry2.getSubsampleEntries().addAll(subSampleEntry.getSubsampleEntries());
                                            long j6 = 0;
                                            if (j5 != 0) {
                                                subSampleEntry2.setSampleDelta(j5 + subSampleEntry.getSampleDelta());
                                            } else {
                                                j6 = j5;
                                                subSampleEntry2.setSampleDelta(subSampleEntry.getSampleDelta());
                                            }
                                            j5 = j6;
                                            this.subSampleInformationBox.getEntries().add(subSampleEntry2);
                                            it9 = it13;
                                            it10 = it14;
                                        }
                                        it4 = it9;
                                        it5 = it10;
                                    } else {
                                        it4 = it9;
                                        it5 = it10;
                                        it6 = it11;
                                        it7 = it12;
                                    }
                                    Iterator it15 = trackFragmentBox.getBoxes(TrackRunBox.class).iterator();
                                    while (it15.hasNext()) {
                                        TrackRunBox trackRunBox = (TrackRunBox) it15.next();
                                        TrackFragmentHeaderBox trackFragmentHeaderBox = ((TrackFragmentBox) trackRunBox.getParent()).getTrackFragmentHeaderBox();
                                        int i3 = 1;
                                        boolean z2 = true;
                                        for (TrackRunBox.Entry entry : trackRunBox.getEntries()) {
                                            if (trackRunBox.isSampleDurationPresent()) {
                                                if (arrayList.size() != 0) {
                                                    it8 = it15;
                                                    if (((TimeToSampleBox.Entry) arrayList.get(arrayList.size() - 1)).getDelta() == entry.getSampleDuration()) {
                                                        TimeToSampleBox.Entry entry2 = (TimeToSampleBox.Entry) arrayList.get(arrayList.size() - i3);
                                                        j3 = trackId;
                                                        z = z2;
                                                        entry2.setCount(entry2.getCount() + 1);
                                                    }
                                                } else {
                                                    it8 = it15;
                                                }
                                                j3 = trackId;
                                                z = z2;
                                                arrayList.add(new TimeToSampleBox.Entry(1L, entry.getSampleDuration()));
                                            } else {
                                                it8 = it15;
                                                j3 = trackId;
                                                z = z2;
                                                if (trackFragmentHeaderBox.hasDefaultSampleDuration()) {
                                                    arrayList.add(new TimeToSampleBox.Entry(1L, trackFragmentHeaderBox.getDefaultSampleDuration()));
                                                } else {
                                                    arrayList.add(new TimeToSampleBox.Entry(1L, trackExtendsBox.getDefaultSampleDuration()));
                                                }
                                            }
                                            if (trackRunBox.isSampleCompositionTimeOffsetPresent()) {
                                                if (this.compositionTimeEntries.size() != 0) {
                                                    List<CompositionTimeToSample.Entry> list = this.compositionTimeEntries;
                                                    i = 1;
                                                    if (list.get(list.size() - 1).getOffset() == entry.getSampleCompositionTimeOffset()) {
                                                        List<CompositionTimeToSample.Entry> list2 = this.compositionTimeEntries;
                                                        CompositionTimeToSample.Entry entry3 = list2.get(list2.size() - 1);
                                                        entry3.setCount(entry3.getCount() + 1);
                                                    }
                                                } else {
                                                    i = 1;
                                                }
                                                this.compositionTimeEntries.add(new CompositionTimeToSample.Entry(i, CastUtils.l2i(entry.getSampleCompositionTimeOffset())));
                                            }
                                            if (trackRunBox.isSampleFlagsPresent()) {
                                                defaultSampleFlags = entry.getSampleFlags();
                                            } else if (z && trackRunBox.isFirstSampleFlagsPresent()) {
                                                defaultSampleFlags = trackRunBox.getFirstSampleFlags();
                                            } else if (trackFragmentHeaderBox.hasDefaultSampleFlags()) {
                                                defaultSampleFlags = trackFragmentHeaderBox.getDefaultSampleFlags();
                                            } else {
                                                defaultSampleFlags = trackExtendsBox.getDefaultSampleFlags();
                                            }
                                            if (defaultSampleFlags != null && !defaultSampleFlags.isSampleIsDifferenceSample()) {
                                                linkedList.add(Long.valueOf(j4));
                                            }
                                            j4++;
                                            it15 = it8;
                                            trackId = j3;
                                            i3 = 1;
                                            z2 = false;
                                        }
                                    }
                                    j2 = trackId;
                                } else {
                                    it4 = it9;
                                    j2 = trackId;
                                    it5 = it10;
                                    it6 = it11;
                                    it7 = it12;
                                }
                                it11 = it6;
                                it12 = it7;
                                it9 = it4;
                                it10 = it5;
                                trackId = j2;
                                i2 = 0;
                            }
                        }
                        it2 = it9;
                        j = trackId;
                        it3 = it10;
                        long[] jArr = this.syncSamples;
                        this.syncSamples = new long[jArr.length + linkedList.size()];
                        System.arraycopy(jArr, 0, this.syncSamples, 0, jArr.length);
                        int length = jArr.length;
                        for (Long l : linkedList) {
                            this.syncSamples[length] = l.longValue();
                            length++;
                        }
                    } else {
                        it2 = it9;
                        j = trackId;
                        it3 = it10;
                    }
                    it9 = it2;
                    it10 = it3;
                    trackId = j;
                    i2 = 0;
                }
            }
            long j7 = trackId;
            new ArrayList();
            new ArrayList();
            for (MovieFragmentBox movieFragmentBox : arrayList2) {
                for (TrackFragmentBox trackFragmentBox2 : movieFragmentBox.getBoxes(TrackFragmentBox.class)) {
                    if (trackFragmentBox2.getTrackFragmentHeaderBox().getTrackId() == j7) {
                        this.sampleGroups = getSampleGroups(Path.getPaths((Container) trackFragmentBox2, SampleGroupDescriptionBox.TYPE), Path.getPaths((Container) trackFragmentBox2, SampleToGroupBox.TYPE), this.sampleGroups);
                    }
                }
            }
        } else {
            this.sampleGroups = getSampleGroups(sampleTableBox.getBoxes(SampleGroupDescriptionBox.class), sampleTableBox.getBoxes(SampleToGroupBox.class), this.sampleGroups);
        }
        this.decodingTimes = TimeToSampleBox.blowupTimeToSamples(arrayList);
        MediaHeaderBox mediaHeaderBox = trackBox.getMediaBox().getMediaHeaderBox();
        TrackHeaderBox trackHeaderBox = trackBox.getTrackHeaderBox();
        this.trackMetaData.setTrackId(trackHeaderBox.getTrackId());
        this.trackMetaData.setCreationTime(mediaHeaderBox.getCreationTime());
        this.trackMetaData.setLanguage(mediaHeaderBox.getLanguage());
        this.trackMetaData.setModificationTime(mediaHeaderBox.getModificationTime());
        this.trackMetaData.setTimescale(mediaHeaderBox.getTimescale());
        this.trackMetaData.setHeight(trackHeaderBox.getHeight());
        this.trackMetaData.setWidth(trackHeaderBox.getWidth());
        this.trackMetaData.setLayer(trackHeaderBox.getLayer());
        this.trackMetaData.setMatrix(trackHeaderBox.getMatrix());
        EditListBox editListBox = (EditListBox) Path.getPath((AbstractContainerBox) trackBox, "edts/elst");
        MovieHeaderBox movieHeaderBox = (MovieHeaderBox) Path.getPath((AbstractContainerBox) trackBox, "../mvhd");
        if (editListBox != null) {
            for (Iterator<EditListBox.Entry> it16 = editListBox.getEntries().iterator(); it16.hasNext(); it16 = it16) {
                EditListBox.Entry next = it16.next();
                this.edits.add(new Edit(next.getMediaTime(), mediaHeaderBox.getTimescale(), next.getMediaRate(), next.getSegmentDuration() / movieHeaderBox.getTimescale()));
                mediaHeaderBox = mediaHeaderBox;
            }
        }
    }

    private Map<GroupEntry, long[]> getSampleGroups(List<SampleGroupDescriptionBox> list, List<SampleToGroupBox> list2, Map<GroupEntry, long[]> map) {
        for (SampleGroupDescriptionBox sampleGroupDescriptionBox : list) {
            boolean z = false;
            for (SampleToGroupBox sampleToGroupBox : list2) {
                if (sampleToGroupBox.getGroupingType().equals(sampleGroupDescriptionBox.getGroupEntries().get(0).getType())) {
                    int i = 0;
                    for (SampleToGroupBox.Entry entry : sampleToGroupBox.getEntries()) {
                        if (entry.getGroupDescriptionIndex() > 0) {
                            GroupEntry groupEntry = sampleGroupDescriptionBox.getGroupEntries().get(entry.getGroupDescriptionIndex() - 1);
                            long[] jArr = map.get(groupEntry);
                            if (jArr == null) {
                                jArr = new long[0];
                            }
                            long[] jArr2 = new long[CastUtils.l2i(entry.getSampleCount()) + jArr.length];
                            System.arraycopy(jArr, 0, jArr2, 0, jArr.length);
                            for (int i2 = 0; i2 < entry.getSampleCount(); i2++) {
                                jArr2[jArr.length + i2] = i + i2;
                            }
                            map.put(groupEntry, jArr2);
                        }
                        i = (int) (i + entry.getSampleCount());
                    }
                    z = true;
                }
            }
            if (!z) {
                throw new RuntimeException("Could not find SampleToGroupBox for " + sampleGroupDescriptionBox.getGroupEntries().get(0).getType() + ".");
            }
        }
        return map;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        Container parent = this.trackBox.getParent();
        if (parent instanceof BasicContainer) {
            ((BasicContainer) parent).close();
        }
        for (IsoFile isoFile : this.fragments) {
            isoFile.close();
        }
    }

    @Override // com.googlecode.mp4parser.authoring.Track
    public List<Sample> getSamples() {
        return this.samples;
    }

    @Override // com.googlecode.mp4parser.authoring.Track
    public synchronized long[] getSampleDurations() {
        return this.decodingTimes;
    }

    @Override // com.googlecode.mp4parser.authoring.Track
    public SampleDescriptionBox getSampleDescriptionBox() {
        return this.sampleDescriptionBox;
    }

    @Override // com.googlecode.mp4parser.authoring.AbstractTrack, com.googlecode.mp4parser.authoring.Track
    public List<CompositionTimeToSample.Entry> getCompositionTimeEntries() {
        return this.compositionTimeEntries;
    }

    @Override // com.googlecode.mp4parser.authoring.AbstractTrack, com.googlecode.mp4parser.authoring.Track
    public long[] getSyncSamples() {
        if (this.syncSamples.length == this.samples.size()) {
            return null;
        }
        return this.syncSamples;
    }

    @Override // com.googlecode.mp4parser.authoring.AbstractTrack, com.googlecode.mp4parser.authoring.Track
    public List<SampleDependencyTypeBox.Entry> getSampleDependencies() {
        return this.sampleDependencies;
    }

    @Override // com.googlecode.mp4parser.authoring.Track
    public TrackMetaData getTrackMetaData() {
        return this.trackMetaData;
    }

    @Override // com.googlecode.mp4parser.authoring.Track
    public String getHandler() {
        return this.handler;
    }

    @Override // com.googlecode.mp4parser.authoring.AbstractTrack, com.googlecode.mp4parser.authoring.Track
    public SubSampleInformationBox getSubsampleInformationBox() {
        return this.subSampleInformationBox;
    }
}
