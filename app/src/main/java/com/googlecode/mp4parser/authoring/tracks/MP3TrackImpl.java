package com.googlecode.mp4parser.authoring.tracks;

import com.coremedia.iso.boxes.SampleDescriptionBox;
import com.coremedia.iso.boxes.sampleentry.AudioSampleEntry;
import com.googlecode.mp4parser.DataSource;
import com.googlecode.mp4parser.authoring.AbstractTrack;
import com.googlecode.mp4parser.authoring.Sample;
import com.googlecode.mp4parser.authoring.SampleImpl;
import com.googlecode.mp4parser.authoring.TrackMetaData;
import com.googlecode.mp4parser.boxes.mp4.ESDescriptorBox;
import com.googlecode.mp4parser.boxes.mp4.objectdescriptors.BitReaderBuffer;
import com.googlecode.mp4parser.boxes.mp4.objectdescriptors.DecoderConfigDescriptor;
import com.googlecode.mp4parser.boxes.mp4.objectdescriptors.ESDescriptor;
import com.googlecode.mp4parser.boxes.mp4.objectdescriptors.SLConfigDescriptor;
import com.tencent.ugc.TXRecordCommon;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes3.dex */
public class MP3TrackImpl extends AbstractTrack {
    private static final int ES_OBJECT_TYPE_INDICATION = 107;
    private static final int ES_STREAM_TYPE = 5;
    private static final int MPEG_L3 = 1;
    private static final int MPEG_V1 = 3;
    private static final int SAMPLES_PER_FRAME = 1152;
    long avgBitRate;
    private final DataSource dataSource;
    private long[] durations;
    MP3Header firstHeader;
    long maxBitRate;
    SampleDescriptionBox sampleDescriptionBox;
    private List<Sample> samples;
    TrackMetaData trackMetaData;
    private static final int[] SAMPLE_RATE = {TXRecordCommon.AUDIO_SAMPLERATE_44100, TXRecordCommon.AUDIO_SAMPLERATE_48000, TXRecordCommon.AUDIO_SAMPLERATE_32000, 0};
    private static final int[] BIT_RATE = {0, TXRecordCommon.AUDIO_SAMPLERATE_32000, 40000, TXRecordCommon.AUDIO_SAMPLERATE_48000, 56000, 64000, 80000, 96000, 112000, 128000, 160000, 192000, 224000, 256000, 320000, 0};

    @Override // com.googlecode.mp4parser.authoring.Track
    public String getHandler() {
        return "soun";
    }

    public String toString() {
        return "MP3TrackImpl";
    }

    public MP3TrackImpl(DataSource dataSource) throws IOException {
        this(dataSource, "eng");
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.dataSource.close();
    }

    public MP3TrackImpl(DataSource dataSource, String str) throws IOException {
        super(dataSource.toString());
        this.trackMetaData = new TrackMetaData();
        this.dataSource = dataSource;
        this.samples = new LinkedList();
        this.firstHeader = readSamples(dataSource);
        double d = this.firstHeader.sampleRate / 1152.0d;
        double size = this.samples.size() / d;
        LinkedList linkedList = new LinkedList();
        Iterator<Sample> it2 = this.samples.iterator();
        long j = 0;
        while (true) {
            int i = 0;
            if (it2.hasNext()) {
                int size2 = (int) it2.next().getSize();
                j += size2;
                linkedList.add(Integer.valueOf(size2));
                while (linkedList.size() > d) {
                    linkedList.pop();
                }
                if (linkedList.size() == ((int) d)) {
                    Iterator it3 = linkedList.iterator();
                    while (it3.hasNext()) {
                        i += ((Integer) it3.next()).intValue();
                    }
                    double size3 = ((i * 8.0d) / linkedList.size()) * d;
                    if (size3 > this.maxBitRate) {
                        this.maxBitRate = (int) size3;
                    }
                }
            } else {
                this.avgBitRate = (int) ((j * 8) / size);
                this.sampleDescriptionBox = new SampleDescriptionBox();
                AudioSampleEntry audioSampleEntry = new AudioSampleEntry(AudioSampleEntry.TYPE3);
                audioSampleEntry.setChannelCount(this.firstHeader.channelCount);
                audioSampleEntry.setSampleRate(this.firstHeader.sampleRate);
                audioSampleEntry.setDataReferenceIndex(1);
                audioSampleEntry.setSampleSize(16);
                ESDescriptorBox eSDescriptorBox = new ESDescriptorBox();
                ESDescriptor eSDescriptor = new ESDescriptor();
                eSDescriptor.setEsId(0);
                SLConfigDescriptor sLConfigDescriptor = new SLConfigDescriptor();
                sLConfigDescriptor.setPredefined(2);
                eSDescriptor.setSlConfigDescriptor(sLConfigDescriptor);
                DecoderConfigDescriptor decoderConfigDescriptor = new DecoderConfigDescriptor();
                decoderConfigDescriptor.setObjectTypeIndication(107);
                decoderConfigDescriptor.setStreamType(5);
                decoderConfigDescriptor.setMaxBitRate(this.maxBitRate);
                decoderConfigDescriptor.setAvgBitRate(this.avgBitRate);
                eSDescriptor.setDecoderConfigDescriptor(decoderConfigDescriptor);
                eSDescriptorBox.setData(eSDescriptor.serialize());
                audioSampleEntry.addBox(eSDescriptorBox);
                this.sampleDescriptionBox.addBox(audioSampleEntry);
                this.trackMetaData.setCreationTime(new Date());
                this.trackMetaData.setModificationTime(new Date());
                this.trackMetaData.setLanguage(str);
                this.trackMetaData.setVolume(1.0f);
                this.trackMetaData.setTimescale(this.firstHeader.sampleRate);
                this.durations = new long[this.samples.size()];
                Arrays.fill(this.durations, 1152L);
                return;
            }
        }
    }

    @Override // com.googlecode.mp4parser.authoring.Track
    public SampleDescriptionBox getSampleDescriptionBox() {
        return this.sampleDescriptionBox;
    }

    @Override // com.googlecode.mp4parser.authoring.Track
    public long[] getSampleDurations() {
        return this.durations;
    }

    @Override // com.googlecode.mp4parser.authoring.Track
    public TrackMetaData getTrackMetaData() {
        return this.trackMetaData;
    }

    @Override // com.googlecode.mp4parser.authoring.Track
    public List<Sample> getSamples() {
        return this.samples;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public class MP3Header {
        int bitRate;
        int bitRateIndex;
        int channelCount;
        int channelMode;
        int layer;
        int mpegVersion;
        int padding;
        int protectionAbsent;
        int sampleFrequencyIndex;
        int sampleRate;

        MP3Header() {
        }

        int getFrameLength() {
            return ((this.bitRate * 144) / this.sampleRate) + this.padding;
        }
    }

    private MP3Header readSamples(DataSource dataSource) throws IOException {
        MP3Header mP3Header = null;
        while (true) {
            long position = dataSource.position();
            MP3Header readMP3Header = readMP3Header(dataSource);
            if (readMP3Header == null) {
                return mP3Header;
            }
            if (mP3Header == null) {
                mP3Header = readMP3Header;
            }
            dataSource.position(position);
            ByteBuffer allocate = ByteBuffer.allocate(readMP3Header.getFrameLength());
            dataSource.read(allocate);
            allocate.rewind();
            this.samples.add(new SampleImpl(allocate));
        }
    }

    private MP3Header readMP3Header(DataSource dataSource) throws IOException {
        MP3Header mP3Header = new MP3Header();
        ByteBuffer allocate = ByteBuffer.allocate(4);
        while (allocate.position() < 4) {
            if (dataSource.read(allocate) == -1) {
                return null;
            }
        }
        BitReaderBuffer bitReaderBuffer = new BitReaderBuffer((ByteBuffer) allocate.rewind());
        if (bitReaderBuffer.readBits(11) != 2047) {
            throw new IOException("Expected Start Word 0x7ff");
        }
        int i = 2;
        mP3Header.mpegVersion = bitReaderBuffer.readBits(2);
        if (mP3Header.mpegVersion != 3) {
            throw new IOException("Expected MPEG Version 1 (ISO/IEC 11172-3)");
        }
        mP3Header.layer = bitReaderBuffer.readBits(2);
        if (mP3Header.layer != 1) {
            throw new IOException("Expected Layer III");
        }
        mP3Header.protectionAbsent = bitReaderBuffer.readBits(1);
        mP3Header.bitRateIndex = bitReaderBuffer.readBits(4);
        mP3Header.bitRate = BIT_RATE[mP3Header.bitRateIndex];
        if (mP3Header.bitRate == 0) {
            throw new IOException("Unexpected (free/bad) bit rate");
        }
        mP3Header.sampleFrequencyIndex = bitReaderBuffer.readBits(2);
        mP3Header.sampleRate = SAMPLE_RATE[mP3Header.sampleFrequencyIndex];
        if (mP3Header.sampleRate == 0) {
            throw new IOException("Unexpected (reserved) sample rate frequency");
        }
        mP3Header.padding = bitReaderBuffer.readBits(1);
        bitReaderBuffer.readBits(1);
        mP3Header.channelMode = bitReaderBuffer.readBits(2);
        if (mP3Header.channelMode == 3) {
            i = 1;
        }
        mP3Header.channelCount = i;
        return mP3Header;
    }
}
