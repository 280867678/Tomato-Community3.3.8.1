package com.googlecode.mp4parser.authoring.tracks;

import android.support.p002v4.media.session.PlaybackStateCompat;
import com.coremedia.iso.boxes.CompositionTimeToSample;
import com.coremedia.iso.boxes.SampleDependencyTypeBox;
import com.coremedia.iso.boxes.SampleDescriptionBox;
import com.coremedia.iso.boxes.sampleentry.AudioSampleEntry;
import com.googlecode.mp4parser.DataSource;
import com.googlecode.mp4parser.authoring.AbstractTrack;
import com.googlecode.mp4parser.authoring.Sample;
import com.googlecode.mp4parser.authoring.TrackMetaData;
import com.googlecode.mp4parser.boxes.DTSSpecificBox;
import com.tencent.ugc.TXRecordCommon;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* loaded from: classes3.dex */
public class DTSTrackImpl extends AbstractTrack {
    private static final int BUFFER = 67108864;
    int bcCoreBitRate;
    int bcCoreChannelMask;
    int bcCoreMaxSampleRate;
    int bitrate;
    int channelCount;
    int channelMask;
    int codecDelayAtMaxFs;
    int coreBitRate;
    int coreChannelMask;
    int coreFramePayloadInBytes;
    int coreMaxSampleRate;
    boolean coreSubStreamPresent;
    private int dataOffset;
    private DataSource dataSource;
    DTSSpecificBox ddts;
    int extAvgBitrate;
    int extFramePayloadInBytes;
    int extPeakBitrate;
    int extSmoothBuffSize;
    boolean extensionSubStreamPresent;
    int frameSize;
    boolean isVBR;
    private String lang;
    int lbrCodingPresent;
    int lsbTrimPercent;
    int maxSampleRate;
    int numExtSubStreams;
    int numFramesTotal;
    int numSamplesOrigAudioAtMaxFs;
    SampleDescriptionBox sampleDescriptionBox;
    private long[] sampleDurations;
    int sampleSize;
    int samplerate;
    private List<Sample> samples;
    int samplesPerFrame;
    int samplesPerFrameAtMaxFs;
    TrackMetaData trackMetaData;
    String type;

    @Override // com.googlecode.mp4parser.authoring.AbstractTrack, com.googlecode.mp4parser.authoring.Track
    public List<CompositionTimeToSample.Entry> getCompositionTimeEntries() {
        return null;
    }

    @Override // com.googlecode.mp4parser.authoring.Track
    public String getHandler() {
        return "soun";
    }

    @Override // com.googlecode.mp4parser.authoring.AbstractTrack, com.googlecode.mp4parser.authoring.Track
    public List<SampleDependencyTypeBox.Entry> getSampleDependencies() {
        return null;
    }

    @Override // com.googlecode.mp4parser.authoring.AbstractTrack, com.googlecode.mp4parser.authoring.Track
    public long[] getSyncSamples() {
        return null;
    }

    public DTSTrackImpl(DataSource dataSource, String str) throws IOException {
        super(dataSource.toString());
        this.trackMetaData = new TrackMetaData();
        this.frameSize = 0;
        this.dataOffset = 0;
        this.ddts = new DTSSpecificBox();
        this.isVBR = false;
        this.coreSubStreamPresent = false;
        this.extensionSubStreamPresent = false;
        this.numExtSubStreams = 0;
        this.coreMaxSampleRate = 0;
        this.coreBitRate = 0;
        this.coreChannelMask = 0;
        this.coreFramePayloadInBytes = 0;
        this.extAvgBitrate = 0;
        this.extPeakBitrate = 0;
        this.extSmoothBuffSize = 0;
        this.extFramePayloadInBytes = 0;
        this.maxSampleRate = 0;
        this.lbrCodingPresent = 0;
        this.numFramesTotal = 0;
        this.samplesPerFrameAtMaxFs = 0;
        this.numSamplesOrigAudioAtMaxFs = 0;
        this.channelMask = 0;
        this.codecDelayAtMaxFs = 0;
        this.bcCoreMaxSampleRate = 0;
        this.bcCoreBitRate = 0;
        this.bcCoreChannelMask = 0;
        this.lsbTrimPercent = 0;
        this.type = "none";
        this.lang = "eng";
        this.lang = str;
        this.dataSource = dataSource;
        parse();
    }

    public DTSTrackImpl(DataSource dataSource) throws IOException {
        super(dataSource.toString());
        this.trackMetaData = new TrackMetaData();
        this.frameSize = 0;
        this.dataOffset = 0;
        this.ddts = new DTSSpecificBox();
        this.isVBR = false;
        this.coreSubStreamPresent = false;
        this.extensionSubStreamPresent = false;
        this.numExtSubStreams = 0;
        this.coreMaxSampleRate = 0;
        this.coreBitRate = 0;
        this.coreChannelMask = 0;
        this.coreFramePayloadInBytes = 0;
        this.extAvgBitrate = 0;
        this.extPeakBitrate = 0;
        this.extSmoothBuffSize = 0;
        this.extFramePayloadInBytes = 0;
        this.maxSampleRate = 0;
        this.lbrCodingPresent = 0;
        this.numFramesTotal = 0;
        this.samplesPerFrameAtMaxFs = 0;
        this.numSamplesOrigAudioAtMaxFs = 0;
        this.channelMask = 0;
        this.codecDelayAtMaxFs = 0;
        this.bcCoreMaxSampleRate = 0;
        this.bcCoreBitRate = 0;
        this.bcCoreChannelMask = 0;
        this.lsbTrimPercent = 0;
        this.type = "none";
        this.lang = "eng";
        this.dataSource = dataSource;
        parse();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.dataSource.close();
    }

    private void parse() throws IOException {
        if (!readVariables()) {
            throw new IOException();
        }
        this.sampleDescriptionBox = new SampleDescriptionBox();
        AudioSampleEntry audioSampleEntry = new AudioSampleEntry(this.type);
        audioSampleEntry.setChannelCount(this.channelCount);
        audioSampleEntry.setSampleRate(this.samplerate);
        audioSampleEntry.setDataReferenceIndex(1);
        audioSampleEntry.setSampleSize(16);
        audioSampleEntry.addBox(this.ddts);
        this.sampleDescriptionBox.addBox(audioSampleEntry);
        this.trackMetaData.setCreationTime(new Date());
        this.trackMetaData.setModificationTime(new Date());
        this.trackMetaData.setLanguage(this.lang);
        this.trackMetaData.setTimescale(this.samplerate);
    }

    @Override // com.googlecode.mp4parser.authoring.Track
    public List<Sample> getSamples() {
        return this.samples;
    }

    @Override // com.googlecode.mp4parser.authoring.Track
    public SampleDescriptionBox getSampleDescriptionBox() {
        return this.sampleDescriptionBox;
    }

    @Override // com.googlecode.mp4parser.authoring.Track
    public long[] getSampleDurations() {
        return this.sampleDurations;
    }

    @Override // com.googlecode.mp4parser.authoring.Track
    public TrackMetaData getTrackMetaData() {
        return this.trackMetaData;
    }

    private void parseDtshdhdr(int i, ByteBuffer byteBuffer) {
        byteBuffer.getInt();
        byteBuffer.get();
        byteBuffer.getInt();
        byteBuffer.get();
        short s = byteBuffer.getShort();
        byteBuffer.get();
        this.numExtSubStreams = byteBuffer.get();
        if ((s & 1) == 1) {
            this.isVBR = true;
        }
        if ((s & 8) == 8) {
            this.coreSubStreamPresent = true;
        }
        if ((s & 16) == 16) {
            this.extensionSubStreamPresent = true;
            this.numExtSubStreams++;
        } else {
            this.numExtSubStreams = 0;
        }
        for (int i2 = 14; i2 < i; i2++) {
            byteBuffer.get();
        }
    }

    private boolean parseCoressmd(int i, ByteBuffer byteBuffer) {
        this.coreMaxSampleRate = (byteBuffer.get() << 16) | (byteBuffer.getShort() & 65535);
        this.coreBitRate = byteBuffer.getShort();
        this.coreChannelMask = byteBuffer.getShort();
        this.coreFramePayloadInBytes = byteBuffer.getInt();
        for (int i2 = 11; i2 < i; i2++) {
            byteBuffer.get();
        }
        return true;
    }

    private boolean parseAuprhdr(int i, ByteBuffer byteBuffer) {
        int i2;
        byteBuffer.get();
        short s = byteBuffer.getShort();
        this.maxSampleRate = (byteBuffer.get() << 16) | (byteBuffer.getShort() & 65535);
        this.numFramesTotal = byteBuffer.getInt();
        this.samplesPerFrameAtMaxFs = byteBuffer.getShort();
        this.numSamplesOrigAudioAtMaxFs = (byteBuffer.get() << 32) | (byteBuffer.getInt() & 65535);
        this.channelMask = byteBuffer.getShort();
        this.codecDelayAtMaxFs = byteBuffer.getShort();
        if ((s & 3) == 3) {
            this.bcCoreMaxSampleRate = (byteBuffer.get() << 16) | (byteBuffer.getShort() & 65535);
            this.bcCoreBitRate = byteBuffer.getShort();
            this.bcCoreChannelMask = byteBuffer.getShort();
            i2 = 28;
        } else {
            i2 = 21;
        }
        if ((s & 4) > 0) {
            this.lsbTrimPercent = byteBuffer.get();
            i2++;
        }
        if ((s & 8) > 0) {
            this.lbrCodingPresent = 1;
        }
        while (i2 < i) {
            byteBuffer.get();
            i2++;
        }
        return true;
    }

    private boolean parseExtssmd(int i, ByteBuffer byteBuffer) {
        int i2;
        this.extAvgBitrate = (byteBuffer.get() << 16) | (byteBuffer.getShort() & 65535);
        if (this.isVBR) {
            this.extPeakBitrate = (byteBuffer.get() << 16) | (byteBuffer.getShort() & 65535);
            this.extSmoothBuffSize = byteBuffer.getShort();
            i2 = 8;
        } else {
            this.extFramePayloadInBytes = byteBuffer.getInt();
            i2 = 7;
        }
        while (i2 < i) {
            byteBuffer.get();
            i2++;
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:0x0214, code lost:
        r2 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:101:0x0218, code lost:
        r18 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x0216, code lost:
        r2 = r21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x021e, code lost:
        if (r2 != 176167201) goto L109;
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x0220, code lost:
        if (r12 != true) goto L108;
     */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x0222, code lost:
        r2 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x0226, code lost:
        r12 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x0224, code lost:
        r2 = r21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:10:0x0088, code lost:
        r7 = r0.getLong();
        r22.dataOffset = r0.position();
        r2 = false;
        r3 = -1;
        r5 = -1;
        r9 = 65535;
        r10 = false;
        r11 = 0;
        r12 = false;
        r13 = false;
        r14 = false;
        r15 = 0;
        r16 = false;
        r17 = false;
        r18 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x022b, code lost:
        if (r2 != 1101174087) goto L115;
     */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x022d, code lost:
        if (r10 != true) goto L114;
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x022f, code lost:
        r2 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:113:0x0233, code lost:
        r10 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x0231, code lost:
        r2 = r21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x0238, code lost:
        if (r2 != 45126241) goto L121;
     */
    /* JADX WARN: Code restructure failed: missing block: B:117:0x023a, code lost:
        if (r13 != true) goto L120;
     */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x023c, code lost:
        r2 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x0240, code lost:
        r13 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:120:0x023e, code lost:
        r2 = r21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x0242, code lost:
        r2 = r21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x01bd, code lost:
        r2 = 12;
        r9 = 20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x0273, code lost:
        throw new java.io.IOException("No DTS_SYNCWORD_* found at " + r0.position());
     */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x0274, code lost:
        r4 = r16;
        r0 = r17;
        r1 = r18;
        r2 = r22.samplesPerFrame;
     */
    /* JADX WARN: Code restructure failed: missing block: B:128:0x027f, code lost:
        if (r2 == 512) goto L337;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x00a3, code lost:
        if (r2 != false) goto L126;
     */
    /* JADX WARN: Code restructure failed: missing block: B:130:0x0283, code lost:
        if (r2 == 1024) goto L336;
     */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x0287, code lost:
        if (r2 == 2048) goto L335;
     */
    /* JADX WARN: Code restructure failed: missing block: B:134:0x028b, code lost:
        if (r2 == 4096) goto L334;
     */
    /* JADX WARN: Code restructure failed: missing block: B:135:0x028d, code lost:
        r2 = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:137:0x0298, code lost:
        if (r2 != (-1)) goto L139;
     */
    /* JADX WARN: Code restructure failed: missing block: B:138:0x029a, code lost:
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x029c, code lost:
        if (r3 == 0) goto L144;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x00a5, code lost:
        r20 = r0.position();
        r1 = r0.getInt();
        r21 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:141:0x029f, code lost:
        if (r3 == 2) goto L144;
     */
    /* JADX WARN: Code restructure failed: missing block: B:142:0x02a1, code lost:
        switch(r3) {
            case 4: goto L144;
            case 5: goto L144;
            case 6: goto L144;
            case 7: goto L144;
            case 8: goto L144;
            case 9: goto L144;
            default: goto L143;
        };
     */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x02a4, code lost:
        r3 = 31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:145:0x02a9, code lost:
        if (r5 != 0) goto L213;
     */
    /* JADX WARN: Code restructure failed: missing block: B:146:0x02ab, code lost:
        r16 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x02ae, code lost:
        if (r10 != true) goto L199;
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x02b0, code lost:
        if (r13 != false) goto L198;
     */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x02b2, code lost:
        r4 = 17;
        r22.type = com.coremedia.iso.boxes.sampleentry.AudioSampleEntry.TYPE11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x00b2, code lost:
        if (r1 != 2147385345) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x02e3, code lost:
        r22.samplerate = r22.maxSampleRate;
        r22.sampleSize = 24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x03f4, code lost:
        r22.ddts.setDTSSamplingFrequency(r22.maxSampleRate);
     */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x03fe, code lost:
        if (r22.isVBR == false) goto L197;
     */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x0400, code lost:
        r22.ddts.setMaxBitRate((r22.coreBitRate + r22.extPeakBitrate) * 1000);
     */
    /* JADX WARN: Code restructure failed: missing block: B:154:0x041b, code lost:
        r22.ddts.setAvgBitRate((r22.coreBitRate + r22.extAvgBitrate) * 1000);
        r22.ddts.setPcmSampleDepth(r22.sampleSize);
        r22.ddts.setFrameDuration(r2);
        r22.ddts.setStreamConstruction(r4);
        r0 = r22.coreChannelMask;
     */
    /* JADX WARN: Code restructure failed: missing block: B:155:0x043d, code lost:
        if ((r0 & 8) > 0) goto L196;
     */
    /* JADX WARN: Code restructure failed: missing block: B:157:0x0441, code lost:
        if ((r0 & 4096) <= 0) goto L158;
     */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x0444, code lost:
        r22.ddts.setCoreLFEPresent(0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:159:0x0451, code lost:
        r22.ddts.setCoreLayout(r3);
        r22.ddts.setCoreSize(r22.coreFramePayloadInBytes);
        r22.ddts.setStereoDownmix(0);
        r22.ddts.setRepresentationType(4);
        r22.ddts.setChannelLayout(r22.channelMask);
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x00b4, code lost:
        if (r5 != 1) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:160:0x0472, code lost:
        if (r22.coreMaxSampleRate <= 0) goto L195;
     */
    /* JADX WARN: Code restructure failed: missing block: B:162:0x0476, code lost:
        if (r22.extAvgBitrate <= 0) goto L195;
     */
    /* JADX WARN: Code restructure failed: missing block: B:163:0x0478, code lost:
        r22.ddts.setMultiAssetFlag(1);
        r5 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:164:0x0486, code lost:
        r22.ddts.setLBRDurationMod(r22.lbrCodingPresent);
        r22.ddts.setReservedBoxPresent(r5);
        r22.channelCount = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:165:0x0496, code lost:
        if (r5 >= 16) goto L192;
     */
    /* JADX WARN: Code restructure failed: missing block: B:167:0x049d, code lost:
        if (((r22.channelMask >> r5) & 1) != 1) goto L191;
     */
    /* JADX WARN: Code restructure failed: missing block: B:169:0x04a3, code lost:
        if (r5 == 0) goto L190;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x00b6, code lost:
        r2 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:170:0x04a5, code lost:
        if (r5 == 12) goto L190;
     */
    /* JADX WARN: Code restructure failed: missing block: B:172:0x04a8, code lost:
        if (r5 == 14) goto L189;
     */
    /* JADX WARN: Code restructure failed: missing block: B:174:0x04ab, code lost:
        if (r5 == 3) goto L188;
     */
    /* JADX WARN: Code restructure failed: missing block: B:176:0x04ae, code lost:
        if (r5 == 4) goto L187;
     */
    /* JADX WARN: Code restructure failed: missing block: B:178:0x04b2, code lost:
        if (r5 == 7) goto L184;
     */
    /* JADX WARN: Code restructure failed: missing block: B:179:0x04b4, code lost:
        if (r5 == 8) goto L184;
     */
    /* JADX WARN: Code restructure failed: missing block: B:180:0x04b6, code lost:
        r22.channelCount += 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:183:0x04da, code lost:
        r5 = r5 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:185:0x04c8, code lost:
        r22.channelCount++;
     */
    /* JADX WARN: Code restructure failed: missing block: B:193:0x04dd, code lost:
        r22.samples = generateSamples(r22.dataSource, r22.dataOffset, r7, r16);
        r22.sampleDurations = new long[r22.samples.size()];
        java.util.Arrays.fill(r22.sampleDurations, r22.samplesPerFrame);
     */
    /* JADX WARN: Code restructure failed: missing block: B:194:0x0500, code lost:
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:195:0x0480, code lost:
        r5 = 0;
        r22.ddts.setMultiAssetFlag(0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:196:0x044b, code lost:
        r22.ddts.setCoreLFEPresent(1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:197:0x040e, code lost:
        r22.ddts.setMaxBitRate((r22.coreBitRate + r22.extAvgBitrate) * 1000);
     */
    /* JADX WARN: Code restructure failed: missing block: B:198:0x02ba, code lost:
        r4 = 21;
        r22.type = com.coremedia.iso.boxes.sampleentry.AudioSampleEntry.TYPE12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:199:0x02bf, code lost:
        if (r12 != true) goto L201;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x00b9, code lost:
        r1 = new com.googlecode.mp4parser.boxes.mp4.objectdescriptors.BitReaderBuffer(r0);
        r2 = r1.readBits(1);
        r5 = r1.readBits(5);
        r3 = r1.readBits(1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:200:0x02c1, code lost:
        r4 = 18;
        r22.type = com.coremedia.iso.boxes.sampleentry.AudioSampleEntry.TYPE13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:201:0x02c9, code lost:
        if (r13 != true) goto L212;
     */
    /* JADX WARN: Code restructure failed: missing block: B:202:0x02cb, code lost:
        r22.type = com.coremedia.iso.boxes.sampleentry.AudioSampleEntry.TYPE12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:203:0x02cd, code lost:
        if (r4 != false) goto L206;
     */
    /* JADX WARN: Code restructure failed: missing block: B:204:0x02cf, code lost:
        if (r10 != false) goto L206;
     */
    /* JADX WARN: Code restructure failed: missing block: B:205:0x02d1, code lost:
        r4 = 19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:206:0x02d4, code lost:
        if (r4 != true) goto L209;
     */
    /* JADX WARN: Code restructure failed: missing block: B:207:0x02d6, code lost:
        if (r10 != false) goto L209;
     */
    /* JADX WARN: Code restructure failed: missing block: B:208:0x02d8, code lost:
        r4 = 20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:209:0x02db, code lost:
        if (r4 != false) goto L212;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x00cb, code lost:
        if (r2 != 1) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:210:0x02dd, code lost:
        if (r10 != true) goto L212;
     */
    /* JADX WARN: Code restructure failed: missing block: B:211:0x02df, code lost:
        r4 = 21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:212:0x02e2, code lost:
        r4 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:213:0x02ed, code lost:
        r16 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:214:0x02f0, code lost:
        if (r9 >= 1) goto L227;
     */
    /* JADX WARN: Code restructure failed: missing block: B:215:0x02f2, code lost:
        if (r11 <= 0) goto L226;
     */
    /* JADX WARN: Code restructure failed: missing block: B:216:0x02f4, code lost:
        if (r15 == 0) goto L225;
     */
    /* JADX WARN: Code restructure failed: missing block: B:218:0x02f7, code lost:
        if (r15 == 2) goto L224;
     */
    /* JADX WARN: Code restructure failed: missing block: B:220:0x02fa, code lost:
        if (r15 == 6) goto L223;
     */
    /* JADX WARN: Code restructure failed: missing block: B:221:0x02fc, code lost:
        r22.type = com.coremedia.iso.boxes.sampleentry.AudioSampleEntry.TYPE12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:222:0x02fe, code lost:
        r4 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:223:0x0301, code lost:
        r22.type = com.coremedia.iso.boxes.sampleentry.AudioSampleEntry.TYPE12;
        r4 = 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:224:0x0306, code lost:
        r22.type = "dtsc";
        r4 = 4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:225:0x030e, code lost:
        r22.type = "dtsc";
        r4 = 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:226:0x0316, code lost:
        r22.type = "dtsc";
        r4 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:227:0x031e, code lost:
        r22.type = com.coremedia.iso.boxes.sampleentry.AudioSampleEntry.TYPE12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:228:0x0320, code lost:
        if (r11 != 0) goto L277;
     */
    /* JADX WARN: Code restructure failed: missing block: B:229:0x0322, code lost:
        if (r13 != false) goto L237;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x00cf, code lost:
        if (r5 != 31) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:231:0x0325, code lost:
        if (r4 != true) goto L237;
     */
    /* JADX WARN: Code restructure failed: missing block: B:232:0x0327, code lost:
        if (r0 != false) goto L237;
     */
    /* JADX WARN: Code restructure failed: missing block: B:233:0x0329, code lost:
        if (r1 != false) goto L237;
     */
    /* JADX WARN: Code restructure failed: missing block: B:234:0x032b, code lost:
        if (r10 != false) goto L237;
     */
    /* JADX WARN: Code restructure failed: missing block: B:235:0x032d, code lost:
        if (r12 != false) goto L237;
     */
    /* JADX WARN: Code restructure failed: missing block: B:236:0x032f, code lost:
        r4 = 5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:237:0x0332, code lost:
        if (r13 != false) goto L276;
     */
    /* JADX WARN: Code restructure failed: missing block: B:238:0x0334, code lost:
        if (r4 != false) goto L276;
     */
    /* JADX WARN: Code restructure failed: missing block: B:239:0x0336, code lost:
        if (r0 != false) goto L276;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x00d1, code lost:
        if (r3 == 0) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:240:0x0338, code lost:
        r5 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:241:0x0339, code lost:
        if (r1 != true) goto L245;
     */
    /* JADX WARN: Code restructure failed: missing block: B:242:0x033b, code lost:
        if (r10 != false) goto L245;
     */
    /* JADX WARN: Code restructure failed: missing block: B:243:0x033d, code lost:
        if (r12 != false) goto L245;
     */
    /* JADX WARN: Code restructure failed: missing block: B:244:0x033f, code lost:
        r4 = 6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:245:0x0343, code lost:
        if (r13 != false) goto L252;
     */
    /* JADX WARN: Code restructure failed: missing block: B:246:0x0345, code lost:
        if (r4 != r5) goto L252;
     */
    /* JADX WARN: Code restructure failed: missing block: B:247:0x0347, code lost:
        if (r0 != false) goto L252;
     */
    /* JADX WARN: Code restructure failed: missing block: B:248:0x0349, code lost:
        if (r1 != r5) goto L252;
     */
    /* JADX WARN: Code restructure failed: missing block: B:249:0x034b, code lost:
        if (r10 != false) goto L252;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x00d5, code lost:
        r22.samplesPerFrame = (r1.readBits(7) + 1) * 32;
        r2 = r1.readBits(14);
        r22.frameSize += r2 + 1;
        r11 = r1.readBits(6);
        r22.samplerate = getSampleRate(r1.readBits(4));
        r22.bitrate = getBitRate(r1.readBits(5));
     */
    /* JADX WARN: Code restructure failed: missing block: B:250:0x034d, code lost:
        if (r12 != false) goto L252;
     */
    /* JADX WARN: Code restructure failed: missing block: B:251:0x034f, code lost:
        r4 = 9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:252:0x0353, code lost:
        if (r13 != false) goto L260;
     */
    /* JADX WARN: Code restructure failed: missing block: B:253:0x0355, code lost:
        if (r4 != false) goto L260;
     */
    /* JADX WARN: Code restructure failed: missing block: B:255:0x0358, code lost:
        if (r0 != true) goto L260;
     */
    /* JADX WARN: Code restructure failed: missing block: B:256:0x035a, code lost:
        if (r1 != false) goto L260;
     */
    /* JADX WARN: Code restructure failed: missing block: B:257:0x035c, code lost:
        if (r10 != false) goto L260;
     */
    /* JADX WARN: Code restructure failed: missing block: B:258:0x035e, code lost:
        if (r12 != false) goto L260;
     */
    /* JADX WARN: Code restructure failed: missing block: B:259:0x0360, code lost:
        r4 = 10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x010b, code lost:
        if (r1.readBits(1) == 0) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:260:0x0364, code lost:
        if (r13 != false) goto L268;
     */
    /* JADX WARN: Code restructure failed: missing block: B:262:0x0367, code lost:
        if (r4 != true) goto L268;
     */
    /* JADX WARN: Code restructure failed: missing block: B:263:0x0369, code lost:
        if (r0 != true) goto L268;
     */
    /* JADX WARN: Code restructure failed: missing block: B:264:0x036b, code lost:
        if (r1 != false) goto L268;
     */
    /* JADX WARN: Code restructure failed: missing block: B:265:0x036d, code lost:
        if (r10 != false) goto L268;
     */
    /* JADX WARN: Code restructure failed: missing block: B:266:0x036f, code lost:
        if (r12 != false) goto L268;
     */
    /* JADX WARN: Code restructure failed: missing block: B:267:0x0371, code lost:
        r4 = 13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:268:0x0375, code lost:
        if (r13 != false) goto L222;
     */
    /* JADX WARN: Code restructure failed: missing block: B:269:0x0377, code lost:
        if (r4 != false) goto L222;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x010f, code lost:
        r1.readBits(1);
        r1.readBits(1);
        r1.readBits(1);
        r1.readBits(1);
        r15 = r1.readBits(3);
        r5 = r1.readBits(1);
        r1.readBits(1);
        r1.readBits(2);
        r1.readBits(1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:270:0x0379, code lost:
        if (r0 != false) goto L222;
     */
    /* JADX WARN: Code restructure failed: missing block: B:271:0x037b, code lost:
        if (r1 != false) goto L222;
     */
    /* JADX WARN: Code restructure failed: missing block: B:273:0x037e, code lost:
        if (r10 != true) goto L222;
     */
    /* JADX WARN: Code restructure failed: missing block: B:274:0x0380, code lost:
        if (r12 != false) goto L222;
     */
    /* JADX WARN: Code restructure failed: missing block: B:275:0x0382, code lost:
        r4 = 14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:276:0x0342, code lost:
        r5 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:277:0x0386, code lost:
        if (r15 != 0) goto L286;
     */
    /* JADX WARN: Code restructure failed: missing block: B:278:0x0388, code lost:
        if (r13 != false) goto L286;
     */
    /* JADX WARN: Code restructure failed: missing block: B:279:0x038a, code lost:
        if (r4 != false) goto L286;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0130, code lost:
        if (r3 != 1) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:280:0x038c, code lost:
        if (r0 != false) goto L286;
     */
    /* JADX WARN: Code restructure failed: missing block: B:282:0x038f, code lost:
        if (r1 != true) goto L286;
     */
    /* JADX WARN: Code restructure failed: missing block: B:283:0x0391, code lost:
        if (r10 != false) goto L286;
     */
    /* JADX WARN: Code restructure failed: missing block: B:284:0x0393, code lost:
        if (r12 != false) goto L286;
     */
    /* JADX WARN: Code restructure failed: missing block: B:285:0x0395, code lost:
        r4 = 7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:287:0x0399, code lost:
        if (r15 != 6) goto L296;
     */
    /* JADX WARN: Code restructure failed: missing block: B:288:0x039b, code lost:
        if (r13 != false) goto L296;
     */
    /* JADX WARN: Code restructure failed: missing block: B:289:0x039d, code lost:
        if (r4 != false) goto L296;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0132, code lost:
        r1.readBits(16);
     */
    /* JADX WARN: Code restructure failed: missing block: B:290:0x039f, code lost:
        if (r0 != false) goto L296;
     */
    /* JADX WARN: Code restructure failed: missing block: B:292:0x03a2, code lost:
        if (r1 != true) goto L296;
     */
    /* JADX WARN: Code restructure failed: missing block: B:293:0x03a4, code lost:
        if (r10 != false) goto L296;
     */
    /* JADX WARN: Code restructure failed: missing block: B:294:0x03a6, code lost:
        if (r12 != false) goto L296;
     */
    /* JADX WARN: Code restructure failed: missing block: B:295:0x03a8, code lost:
        r4 = 8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:296:0x03ab, code lost:
        if (r15 != 0) goto L305;
     */
    /* JADX WARN: Code restructure failed: missing block: B:297:0x03ad, code lost:
        if (r13 != false) goto L305;
     */
    /* JADX WARN: Code restructure failed: missing block: B:298:0x03af, code lost:
        if (r4 != false) goto L305;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0137, code lost:
        r1.readBits(1);
        r4 = r1.readBits(4);
        r1.readBits(2);
        r5 = r1.readBits(3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:300:0x03b2, code lost:
        if (r0 != true) goto L305;
     */
    /* JADX WARN: Code restructure failed: missing block: B:301:0x03b4, code lost:
        if (r1 != false) goto L305;
     */
    /* JADX WARN: Code restructure failed: missing block: B:302:0x03b6, code lost:
        if (r10 != false) goto L305;
     */
    /* JADX WARN: Code restructure failed: missing block: B:303:0x03b8, code lost:
        if (r12 != false) goto L305;
     */
    /* JADX WARN: Code restructure failed: missing block: B:304:0x03ba, code lost:
        r4 = 11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:306:0x03be, code lost:
        if (r15 != 6) goto L315;
     */
    /* JADX WARN: Code restructure failed: missing block: B:307:0x03c0, code lost:
        if (r13 != false) goto L315;
     */
    /* JADX WARN: Code restructure failed: missing block: B:308:0x03c2, code lost:
        if (r4 != false) goto L315;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0147, code lost:
        if (r5 == 0) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:310:0x03c5, code lost:
        if (r0 != true) goto L315;
     */
    /* JADX WARN: Code restructure failed: missing block: B:311:0x03c7, code lost:
        if (r1 != false) goto L315;
     */
    /* JADX WARN: Code restructure failed: missing block: B:312:0x03c9, code lost:
        if (r10 != false) goto L315;
     */
    /* JADX WARN: Code restructure failed: missing block: B:313:0x03cb, code lost:
        if (r12 != false) goto L315;
     */
    /* JADX WARN: Code restructure failed: missing block: B:314:0x03cd, code lost:
        r4 = 12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:315:0x03d0, code lost:
        if (r15 != 0) goto L324;
     */
    /* JADX WARN: Code restructure failed: missing block: B:316:0x03d2, code lost:
        if (r13 != false) goto L324;
     */
    /* JADX WARN: Code restructure failed: missing block: B:317:0x03d4, code lost:
        if (r4 != false) goto L324;
     */
    /* JADX WARN: Code restructure failed: missing block: B:318:0x03d6, code lost:
        if (r0 != false) goto L324;
     */
    /* JADX WARN: Code restructure failed: missing block: B:319:0x03d8, code lost:
        if (r1 != false) goto L324;
     */
    /* JADX WARN: Code restructure failed: missing block: B:321:0x03db, code lost:
        if (r10 != true) goto L324;
     */
    /* JADX WARN: Code restructure failed: missing block: B:322:0x03dd, code lost:
        if (r12 != false) goto L324;
     */
    /* JADX WARN: Code restructure failed: missing block: B:323:0x03df, code lost:
        r4 = 15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:325:0x03e3, code lost:
        if (r15 != 2) goto L222;
     */
    /* JADX WARN: Code restructure failed: missing block: B:326:0x03e5, code lost:
        if (r13 != false) goto L222;
     */
    /* JADX WARN: Code restructure failed: missing block: B:327:0x03e7, code lost:
        if (r4 != false) goto L222;
     */
    /* JADX WARN: Code restructure failed: missing block: B:328:0x03e9, code lost:
        if (r0 != false) goto L222;
     */
    /* JADX WARN: Code restructure failed: missing block: B:329:0x03eb, code lost:
        if (r1 != false) goto L222;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x014a, code lost:
        if (r5 == 1) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:331:0x03ee, code lost:
        if (r10 != true) goto L222;
     */
    /* JADX WARN: Code restructure failed: missing block: B:332:0x03f0, code lost:
        if (r12 != false) goto L222;
     */
    /* JADX WARN: Code restructure failed: missing block: B:333:0x03f2, code lost:
        r4 = 16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:334:0x0290, code lost:
        r2 = 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:335:0x0292, code lost:
        r2 = 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:336:0x0294, code lost:
        r2 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:337:0x0296, code lost:
        r2 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x014d, code lost:
        if (r5 == 2) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0150, code lost:
        if (r5 == 3) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0153, code lost:
        if (r5 == 5) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0156, code lost:
        if (r5 == 6) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0158, code lost:
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x015a, code lost:
        r22.sampleSize = 24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0168, code lost:
        r1.readBits(1);
        r1.readBits(1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0170, code lost:
        if (r4 == 6) goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0173, code lost:
        if (r4 == 7) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0175, code lost:
        r1.readBits(4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0183, code lost:
        r0.position((r20 + r2) + 1);
        r3 = r11;
        r11 = r5;
        r2 = r21;
        r5 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x017a, code lost:
        r1.readBits(4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x017f, code lost:
        r1.readBits(4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x015f, code lost:
        r22.sampleSize = 20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0164, code lost:
        r22.sampleSize = 16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x010d, code lost:
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:?, code lost:
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:?, code lost:
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0192, code lost:
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0199, code lost:
        if (r1 != 1683496997) goto L123;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x019c, code lost:
        if (r5 != (-1)) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x019e, code lost:
        r22.samplesPerFrame = r22.samplesPerFrameAtMaxFs;
        r5 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x01a3, code lost:
        r1 = new com.googlecode.mp4parser.boxes.mp4.objectdescriptors.BitReaderBuffer(r0);
        r1.readBits(8);
        r1.readBits(2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x01b6, code lost:
        if (r1.readBits(1) != 0) goto L122;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x01b8, code lost:
        r2 = 8;
        r9 = 16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x01c1, code lost:
        r1 = r1.readBits(r9) + 1;
        r0.position(r20 + (r1.readBits(r2) + 1));
        r2 = r0.getInt();
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x01d7, code lost:
        if (r2 != 1515870810) goto L82;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x01d9, code lost:
        if (r14 != true) goto L81;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x01db, code lost:
        r2 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x01df, code lost:
        r9 = r16;
        r14 = true;
        r16 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0244, code lost:
        if (r2 != false) goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x0246, code lost:
        r22.frameSize += r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x024b, code lost:
        r0.position(r20 + r1);
        r5 = r16;
        r16 = r9;
        r9 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x01dd, code lost:
        r2 = r21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x01e9, code lost:
        if (r2 != 1191201283) goto L89;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x01ed, code lost:
        if (r16 != true) goto L88;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x01ef, code lost:
        r2 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x01f3, code lost:
        r16 = r5;
        r9 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x01f1, code lost:
        r2 = r21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x01f7, code lost:
        r9 = r16;
        r16 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x01fe, code lost:
        if (r2 != 496366178) goto L96;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x0202, code lost:
        if (r17 != true) goto L95;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x0204, code lost:
        r2 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x0208, code lost:
        r17 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x0206, code lost:
        r2 = r21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x020e, code lost:
        if (r2 != 1700671838) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x0212, code lost:
        if (r18 != true) goto L102;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean readVariables() throws IOException {
        ByteBuffer map = this.dataSource.map(0L, 25000L);
        int i = map.getInt();
        int i2 = map.getInt();
        if (i == 1146377032 && i2 == 1145586770) {
            while (true) {
                if ((i != 1398035021 || i2 != 1145132097) && map.remaining() > 100) {
                    int i3 = (int) map.getLong();
                    if (i == 1146377032 && i2 == 1145586770) {
                        parseDtshdhdr(i3, map);
                    } else if (i == 1129271877 && i2 == 1397968196) {
                        if (!parseCoressmd(i3, map)) {
                            return false;
                        }
                    } else if (i == 1096110162 && i2 == 759710802) {
                        if (!parseAuprhdr(i3, map)) {
                            return false;
                        }
                    } else if (i != 1163416659 || i2 != 1398754628) {
                        for (int i4 = 0; i4 < i3; i4++) {
                            map.get();
                        }
                    } else if (!parseExtssmd(i3, map)) {
                        return false;
                    }
                    i = map.getInt();
                    i2 = map.getInt();
                }
            }
        } else {
            throw new IOException("data does not start with 'DTSHDHDR' as required for a DTS-HD file");
        }
    }

    private List<Sample> generateSamples(DataSource dataSource, int i, long j, int i2) throws IOException {
        LookAhead lookAhead = new LookAhead(dataSource, i, j, i2);
        ArrayList arrayList = new ArrayList();
        while (true) {
            final ByteBuffer findNextStart = lookAhead.findNextStart();
            if (findNextStart != null) {
                arrayList.add(new Sample() { // from class: com.googlecode.mp4parser.authoring.tracks.DTSTrackImpl.1
                    @Override // com.googlecode.mp4parser.authoring.Sample
                    public void writeTo(WritableByteChannel writableByteChannel) throws IOException {
                        writableByteChannel.write((ByteBuffer) findNextStart.rewind());
                    }

                    @Override // com.googlecode.mp4parser.authoring.Sample
                    public long getSize() {
                        return findNextStart.rewind().remaining();
                    }

                    @Override // com.googlecode.mp4parser.authoring.Sample
                    public ByteBuffer asByteBuffer() {
                        return findNextStart;
                    }
                });
            } else {
                System.err.println("all samples found");
                return arrayList;
            }
        }
    }

    private int getBitRate(int i) throws IOException {
        switch (i) {
            case 0:
                return 32;
            case 1:
                return 56;
            case 2:
                return 64;
            case 3:
                return 96;
            case 4:
                return 112;
            case 5:
                return 128;
            case 6:
                return 192;
            case 7:
                return 224;
            case 8:
                return 256;
            case 9:
                return 320;
            case 10:
                return 384;
            case 11:
                return 448;
            case 12:
                return 512;
            case 13:
                return 576;
            case 14:
                return 640;
            case 15:
                return 768;
            case 16:
                return 960;
            case 17:
                return 1024;
            case 18:
                return 1152;
            case 19:
                return 1280;
            case 20:
                return 1344;
            case 21:
                return 1408;
            case 22:
                return 1411;
            case 23:
                return 1472;
            case 24:
                return 1536;
            case 25:
                return -1;
            default:
                throw new IOException("Unknown bitrate value");
        }
    }

    private int getSampleRate(int i) throws IOException {
        switch (i) {
            case 1:
                return 8000;
            case 2:
                return TXRecordCommon.AUDIO_SAMPLERATE_16000;
            case 3:
                return TXRecordCommon.AUDIO_SAMPLERATE_32000;
            case 4:
            case 5:
            case 9:
            case 10:
            default:
                throw new IOException("Unknown Sample Rate");
            case 6:
                return 11025;
            case 7:
                return 22050;
            case 8:
                return TXRecordCommon.AUDIO_SAMPLERATE_44100;
            case 11:
                return 12000;
            case 12:
                return 24000;
            case 13:
                return TXRecordCommon.AUDIO_SAMPLERATE_48000;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public class LookAhead {
        ByteBuffer buffer;
        long bufferStartPos;
        private final int corePresent;
        long dataEnd;
        DataSource dataSource;
        int inBufferPos = 0;
        long start;

        LookAhead(DataSource dataSource, long j, long j2, int i) throws IOException {
            this.dataSource = dataSource;
            this.bufferStartPos = j;
            this.dataEnd = j2 + j;
            this.corePresent = i;
            fillBuffer();
        }

        public ByteBuffer findNextStart() throws IOException {
            while (true) {
                try {
                    if (this.corePresent != 1) {
                        if (nextFourEquals0x64582025()) {
                            break;
                        }
                        discardByte();
                    } else if (nextFourEquals0x7FFE8001()) {
                        break;
                    } else {
                        discardByte();
                    }
                } catch (EOFException unused) {
                    return null;
                }
                return null;
            }
            discardNext4AndMarkStart();
            while (true) {
                if (this.corePresent != 1) {
                    if (nextFourEquals0x64582025orEof()) {
                        break;
                    }
                    discardQWord();
                } else if (nextFourEquals0x7FFE8001orEof()) {
                    break;
                } else {
                    discardQWord();
                }
                return null;
            }
            return getSample();
        }

        private void fillBuffer() throws IOException {
            System.err.println("Fill Buffer");
            DataSource dataSource = this.dataSource;
            long j = this.bufferStartPos;
            this.buffer = dataSource.map(j, Math.min(this.dataEnd - j, 67108864L));
        }

        private boolean nextFourEquals0x64582025() throws IOException {
            return nextFourEquals((byte) 100, (byte) 88, (byte) 32, (byte) 37);
        }

        private boolean nextFourEquals0x7FFE8001() throws IOException {
            return nextFourEquals(Byte.MAX_VALUE, (byte) -2, Byte.MIN_VALUE, (byte) 1);
        }

        private boolean nextFourEquals(byte b, byte b2, byte b3, byte b4) throws IOException {
            int limit = this.buffer.limit();
            int i = this.inBufferPos;
            if (limit - i >= 4) {
                return this.buffer.get(i) == b && this.buffer.get(this.inBufferPos + 1) == b2 && this.buffer.get(this.inBufferPos + 2) == b3 && this.buffer.get(this.inBufferPos + 3) == b4;
            } else if (this.bufferStartPos + i + 4 >= this.dataSource.size()) {
                throw new EOFException();
            } else {
                return false;
            }
        }

        private boolean nextFourEquals0x64582025orEof() throws IOException {
            return nextFourEqualsOrEof((byte) 100, (byte) 88, (byte) 32, (byte) 37);
        }

        private boolean nextFourEquals0x7FFE8001orEof() throws IOException {
            return nextFourEqualsOrEof(Byte.MAX_VALUE, (byte) -2, Byte.MIN_VALUE, (byte) 1);
        }

        private boolean nextFourEqualsOrEof(byte b, byte b2, byte b3, byte b4) throws IOException {
            int limit = this.buffer.limit();
            int i = this.inBufferPos;
            if (limit - i >= 4) {
                if ((this.bufferStartPos + i) % PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED == 0) {
                    PrintStream printStream = System.err;
                    printStream.println("" + (((this.bufferStartPos + this.inBufferPos) / 1024) / 1024));
                }
                return this.buffer.get(this.inBufferPos) == b && this.buffer.get(this.inBufferPos + 1) == b2 && this.buffer.get(this.inBufferPos + 2) == b3 && this.buffer.get(this.inBufferPos + 3) == b4;
            }
            long j = this.bufferStartPos;
            long j2 = this.dataEnd;
            if (i + j + 4 > j2) {
                return j + ((long) i) == j2;
            }
            this.bufferStartPos = this.start;
            this.inBufferPos = 0;
            fillBuffer();
            return nextFourEquals0x7FFE8001();
        }

        private void discardByte() {
            this.inBufferPos++;
        }

        private void discardQWord() {
            this.inBufferPos += 4;
        }

        private void discardNext4AndMarkStart() {
            long j = this.bufferStartPos;
            int i = this.inBufferPos;
            this.start = j + i;
            this.inBufferPos = i + 4;
        }

        private ByteBuffer getSample() {
            long j = this.start;
            long j2 = this.bufferStartPos;
            if (j >= j2) {
                this.buffer.position((int) (j - j2));
                ByteBuffer slice = this.buffer.slice();
                slice.limit((int) (this.inBufferPos - (this.start - this.bufferStartPos)));
                return slice;
            }
            throw new RuntimeException("damn! NAL exceeds buffer");
        }
    }
}
