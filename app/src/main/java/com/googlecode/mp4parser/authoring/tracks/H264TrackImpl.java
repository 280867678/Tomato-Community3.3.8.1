package com.googlecode.mp4parser.authoring.tracks;

import com.coremedia.iso.boxes.CompositionTimeToSample;
import com.coremedia.iso.boxes.SampleDependencyTypeBox;
import com.coremedia.iso.boxes.SampleDescriptionBox;
import com.coremedia.iso.boxes.sampleentry.VisualSampleEntry;
import com.googlecode.mp4parser.DataSource;
import com.googlecode.mp4parser.authoring.Sample;
import com.googlecode.mp4parser.authoring.tracks.AbstractH26XTrack;
import com.googlecode.mp4parser.h264.model.HRDParameters;
import com.googlecode.mp4parser.h264.model.PictureParameterSet;
import com.googlecode.mp4parser.h264.model.SeqParameterSet;
import com.googlecode.mp4parser.h264.model.VUIParameters;
import com.googlecode.mp4parser.h264.read.CAVLCReader;
import com.googlecode.mp4parser.util.RangeStartMap;
import com.mp4parser.iso14496.part15.AvcConfigurationBox;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/* loaded from: classes3.dex */
public class H264TrackImpl extends AbstractH26XTrack {
    private static final Logger LOG = Logger.getLogger(H264TrackImpl.class.getName());
    PictureParameterSet currentPictureParameterSet;
    SeqParameterSet currentSeqParameterSet;
    private boolean determineFrameRate;
    PictureParameterSet firstPictureParameterSet;
    SeqParameterSet firstSeqParameterSet;
    int frameNrInGop;
    private int frametick;
    private int height;
    private String lang;
    RangeStartMap<Integer, byte[]> pictureParameterRangeMap;
    Map<Integer, PictureParameterSet> ppsIdToPps;
    Map<Integer, byte[]> ppsIdToPpsBytes;
    SampleDescriptionBox sampleDescriptionBox;
    private List<Sample> samples;
    private SEIMessage seiMessage;
    RangeStartMap<Integer, byte[]> seqParameterRangeMap;
    Map<Integer, SeqParameterSet> spsIdToSps;
    Map<Integer, byte[]> spsIdToSpsBytes;
    private long timescale;
    private int width;

    @Override // com.googlecode.mp4parser.authoring.Track
    public String getHandler() {
        return "vide";
    }

    public H264TrackImpl(DataSource dataSource, String str, long j, int i) throws IOException {
        super(dataSource);
        this.spsIdToSpsBytes = new HashMap();
        this.spsIdToSps = new HashMap();
        this.ppsIdToPpsBytes = new HashMap();
        this.ppsIdToPps = new HashMap();
        this.firstSeqParameterSet = null;
        this.firstPictureParameterSet = null;
        this.currentSeqParameterSet = null;
        this.currentPictureParameterSet = null;
        this.seqParameterRangeMap = new RangeStartMap<>();
        this.pictureParameterRangeMap = new RangeStartMap<>();
        this.frameNrInGop = 0;
        this.determineFrameRate = true;
        this.lang = "eng";
        this.lang = str;
        this.timescale = j;
        this.frametick = i;
        if (j > 0 && i > 0) {
            this.determineFrameRate = false;
        }
        parse(new AbstractH26XTrack.LookAhead(dataSource));
    }

    public H264TrackImpl(DataSource dataSource, String str) throws IOException {
        this(dataSource, str, -1L, -1);
    }

    public H264TrackImpl(DataSource dataSource) throws IOException {
        this(dataSource, "eng");
    }

    private void parse(AbstractH26XTrack.LookAhead lookAhead) throws IOException {
        this.samples = new LinkedList();
        if (!readSamples(lookAhead)) {
            throw new IOException();
        }
        if (!readVariables()) {
            throw new IOException();
        }
        this.sampleDescriptionBox = new SampleDescriptionBox();
        VisualSampleEntry visualSampleEntry = new VisualSampleEntry(VisualSampleEntry.TYPE3);
        visualSampleEntry.setDataReferenceIndex(1);
        visualSampleEntry.setDepth(24);
        visualSampleEntry.setFrameCount(1);
        visualSampleEntry.setHorizresolution(72.0d);
        visualSampleEntry.setVertresolution(72.0d);
        visualSampleEntry.setWidth(this.width);
        visualSampleEntry.setHeight(this.height);
        visualSampleEntry.setCompressorname("AVC Coding");
        AvcConfigurationBox avcConfigurationBox = new AvcConfigurationBox();
        avcConfigurationBox.setSequenceParameterSets(new ArrayList(this.spsIdToSpsBytes.values()));
        avcConfigurationBox.setPictureParameterSets(new ArrayList(this.ppsIdToPpsBytes.values()));
        avcConfigurationBox.setAvcLevelIndication(this.firstSeqParameterSet.level_idc);
        avcConfigurationBox.setAvcProfileIndication(this.firstSeqParameterSet.profile_idc);
        avcConfigurationBox.setBitDepthLumaMinus8(this.firstSeqParameterSet.bit_depth_luma_minus8);
        avcConfigurationBox.setBitDepthChromaMinus8(this.firstSeqParameterSet.bit_depth_chroma_minus8);
        avcConfigurationBox.setChromaFormat(this.firstSeqParameterSet.chroma_format_idc.getId());
        avcConfigurationBox.setConfigurationVersion(1);
        avcConfigurationBox.setLengthSizeMinusOne(3);
        int i = 0;
        int i2 = (this.firstSeqParameterSet.constraint_set_0_flag ? 128 : 0) + (this.firstSeqParameterSet.constraint_set_1_flag ? 64 : 0) + (this.firstSeqParameterSet.constraint_set_2_flag ? 32 : 0) + (this.firstSeqParameterSet.constraint_set_3_flag ? 16 : 0);
        if (this.firstSeqParameterSet.constraint_set_4_flag) {
            i = 8;
        }
        avcConfigurationBox.setProfileCompatibility(i2 + i + ((int) (this.firstSeqParameterSet.reserved_zero_2bits & 3)));
        visualSampleEntry.addBox(avcConfigurationBox);
        this.sampleDescriptionBox.addBox(visualSampleEntry);
        this.trackMetaData.setCreationTime(new Date());
        this.trackMetaData.setModificationTime(new Date());
        this.trackMetaData.setLanguage(this.lang);
        this.trackMetaData.setTimescale(this.timescale);
        this.trackMetaData.setWidth(this.width);
        this.trackMetaData.setHeight(this.height);
    }

    @Override // com.googlecode.mp4parser.authoring.Track
    public SampleDescriptionBox getSampleDescriptionBox() {
        return this.sampleDescriptionBox;
    }

    @Override // com.googlecode.mp4parser.authoring.Track
    public List<Sample> getSamples() {
        return this.samples;
    }

    private boolean readVariables() {
        int i;
        SeqParameterSet seqParameterSet = this.firstSeqParameterSet;
        this.width = (seqParameterSet.pic_width_in_mbs_minus1 + 1) * 16;
        int i2 = seqParameterSet.frame_mbs_only_flag ? 1 : 2;
        SeqParameterSet seqParameterSet2 = this.firstSeqParameterSet;
        this.height = (seqParameterSet2.pic_height_in_map_units_minus1 + 1) * 16 * i2;
        if (seqParameterSet2.frame_cropping_flag) {
            int i3 = 0;
            if (!seqParameterSet2.residual_color_transform_flag) {
                i3 = seqParameterSet2.chroma_format_idc.getId();
            }
            if (i3 != 0) {
                i = this.firstSeqParameterSet.chroma_format_idc.getSubWidth();
                i2 *= this.firstSeqParameterSet.chroma_format_idc.getSubHeight();
            } else {
                i = 1;
            }
            int i4 = this.width;
            SeqParameterSet seqParameterSet3 = this.firstSeqParameterSet;
            this.width = i4 - (i * (seqParameterSet3.frame_crop_left_offset + seqParameterSet3.frame_crop_right_offset));
            this.height -= i2 * (seqParameterSet3.frame_crop_top_offset + seqParameterSet3.frame_crop_bottom_offset);
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1, types: [com.googlecode.mp4parser.authoring.tracks.H264TrackImpl$1FirstVclNalDetector] */
    /* JADX WARN: Type inference failed for: r2v10 */
    /* JADX WARN: Type inference failed for: r2v11 */
    /* JADX WARN: Type inference failed for: r2v12 */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r2v7 */
    /* JADX WARN: Type inference failed for: r2v8 */
    /* JADX WARN: Type inference failed for: r2v9 */
    /* JADX WARN: Type inference failed for: r6v1, types: [com.googlecode.mp4parser.authoring.tracks.H264TrackImpl$1FirstVclNalDetector] */
    private boolean readSamples(AbstractH26XTrack.LookAhead lookAhead) throws IOException {
        ArrayList arrayList = new ArrayList();
        ?? r2 = 0;
        while (true) {
            ByteBuffer findNextNal = findNextNal(lookAhead);
            if (findNextNal != null) {
                byte b = findNextNal.get(0);
                int i = (b >> 5) & 3;
                int i2 = b & 31;
                switch (i2) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                        ?? r6 = new Object(findNextNal, i, i2) { // from class: com.googlecode.mp4parser.authoring.tracks.H264TrackImpl.1FirstVclNalDetector
                            boolean bottom_field_flag;
                            int delta_pic_order_cnt_0;
                            int delta_pic_order_cnt_1;
                            int delta_pic_order_cnt_bottom;
                            boolean field_pic_flag;
                            int frame_num;
                            boolean idrPicFlag;
                            int idr_pic_id;
                            int nal_ref_idc;
                            int pic_order_cnt_lsb;
                            int pic_order_cnt_type;
                            int pic_parameter_set_id;

                            {
                                SliceHeader sliceHeader = new SliceHeader(AbstractH26XTrack.cleanBuffer(new ByteBufferBackedInputStream(findNextNal)), H264TrackImpl.this.spsIdToSps, H264TrackImpl.this.ppsIdToPps, i2 == 5);
                                this.frame_num = sliceHeader.frame_num;
                                int i3 = sliceHeader.pic_parameter_set_id;
                                this.pic_parameter_set_id = i3;
                                this.field_pic_flag = sliceHeader.field_pic_flag;
                                this.bottom_field_flag = sliceHeader.bottom_field_flag;
                                this.nal_ref_idc = i;
                                this.pic_order_cnt_type = H264TrackImpl.this.spsIdToSps.get(Integer.valueOf(H264TrackImpl.this.ppsIdToPps.get(Integer.valueOf(i3)).seq_parameter_set_id)).pic_order_cnt_type;
                                this.delta_pic_order_cnt_bottom = sliceHeader.delta_pic_order_cnt_bottom;
                                this.pic_order_cnt_lsb = sliceHeader.pic_order_cnt_lsb;
                                this.delta_pic_order_cnt_0 = sliceHeader.delta_pic_order_cnt_0;
                                this.delta_pic_order_cnt_1 = sliceHeader.delta_pic_order_cnt_1;
                                this.idr_pic_id = sliceHeader.idr_pic_id;
                            }

                            boolean isFirstInNew(C1FirstVclNalDetector c1FirstVclNalDetector) {
                                boolean z;
                                boolean z2;
                                boolean z3;
                                if (c1FirstVclNalDetector.frame_num == this.frame_num && c1FirstVclNalDetector.pic_parameter_set_id == this.pic_parameter_set_id && (z = c1FirstVclNalDetector.field_pic_flag) == this.field_pic_flag) {
                                    if ((z && c1FirstVclNalDetector.bottom_field_flag != this.bottom_field_flag) || c1FirstVclNalDetector.nal_ref_idc != this.nal_ref_idc) {
                                        return true;
                                    }
                                    if (c1FirstVclNalDetector.pic_order_cnt_type == 0 && this.pic_order_cnt_type == 0 && (c1FirstVclNalDetector.pic_order_cnt_lsb != this.pic_order_cnt_lsb || c1FirstVclNalDetector.delta_pic_order_cnt_bottom != this.delta_pic_order_cnt_bottom)) {
                                        return true;
                                    }
                                    if ((c1FirstVclNalDetector.pic_order_cnt_type == 1 && this.pic_order_cnt_type == 1 && (c1FirstVclNalDetector.delta_pic_order_cnt_0 != this.delta_pic_order_cnt_0 || c1FirstVclNalDetector.delta_pic_order_cnt_1 != this.delta_pic_order_cnt_1)) || (z2 = c1FirstVclNalDetector.idrPicFlag) != (z3 = this.idrPicFlag)) {
                                        return true;
                                    }
                                    return z2 && z3 && c1FirstVclNalDetector.idr_pic_id != this.idr_pic_id;
                                }
                                return true;
                            }
                        };
                        if (r2 != 0) {
                            boolean isFirstInNew = r2.isFirstInNew(r6);
                            r2 = r2;
                            if (isFirstInNew) {
                                createSample(arrayList);
                            }
                            arrayList.add((ByteBuffer) findNextNal.rewind());
                            break;
                        }
                        r2 = r6;
                        arrayList.add((ByteBuffer) findNextNal.rewind());
                    case 6:
                        if (r2 != 0) {
                            createSample(arrayList);
                            r2 = 0;
                        }
                        this.seiMessage = new SEIMessage(AbstractH26XTrack.cleanBuffer(new ByteBufferBackedInputStream(findNextNal)), this.currentSeqParameterSet);
                        arrayList.add(findNextNal);
                        break;
                    case 7:
                        if (r2 != 0) {
                            createSample(arrayList);
                            r2 = 0;
                        }
                        handleSPS((ByteBuffer) findNextNal.rewind());
                        break;
                    case 8:
                        if (r2 != 0) {
                            createSample(arrayList);
                            r2 = 0;
                        }
                        handlePPS((ByteBuffer) findNextNal.rewind());
                        break;
                    case 9:
                        if (r2 != 0) {
                            createSample(arrayList);
                            r2 = 0;
                        }
                        arrayList.add(findNextNal);
                        break;
                    case 10:
                    case 11:
                        break;
                    case 12:
                    default:
                        System.err.println("Unknown NAL unit type: " + i2);
                        break;
                    case 13:
                        throw new RuntimeException("Sequence parameter set extension is not yet handled. Needs TLC.");
                }
            }
        }
        createSample(arrayList);
        this.decodingTimes = new long[this.samples.size()];
        Arrays.fill(this.decodingTimes, this.frametick);
        return true;
    }

    private void createSample(List<ByteBuffer> list) throws IOException {
        int i = 0;
        boolean z = false;
        for (ByteBuffer byteBuffer : list) {
            if ((byteBuffer.get(0) & 31) == 5) {
                z = true;
            }
        }
        int i2 = z ? 38 : 22;
        if (new SliceHeader(AbstractH26XTrack.cleanBuffer(new ByteBufferBackedInputStream(list.get(list.size() - 1))), this.spsIdToSps, this.ppsIdToPps, z).slice_type == SliceHeader.SliceType.B) {
            i2 += 4;
        }
        Sample createSampleObject = createSampleObject(list);
        list.clear();
        SEIMessage sEIMessage = this.seiMessage;
        if (sEIMessage == null || sEIMessage.n_frames == 0) {
            this.frameNrInGop = 0;
        }
        SEIMessage sEIMessage2 = this.seiMessage;
        if (sEIMessage2 != null && sEIMessage2.clock_timestamp_flag) {
            i = sEIMessage2.n_frames - this.frameNrInGop;
        } else {
            SEIMessage sEIMessage3 = this.seiMessage;
            if (sEIMessage3 != null && sEIMessage3.removal_delay_flag) {
                i = sEIMessage3.dpb_removal_delay / 2;
            }
        }
        this.ctts.add(new CompositionTimeToSample.Entry(1, i * this.frametick));
        this.sdtp.add(new SampleDependencyTypeBox.Entry(i2));
        this.frameNrInGop++;
        this.samples.add(createSampleObject);
        if (z) {
            this.stss.add(Integer.valueOf(this.samples.size()));
        }
    }

    private void handlePPS(ByteBuffer byteBuffer) throws IOException {
        ByteBufferBackedInputStream byteBufferBackedInputStream = new ByteBufferBackedInputStream(byteBuffer);
        byteBufferBackedInputStream.read();
        PictureParameterSet read = PictureParameterSet.read(byteBufferBackedInputStream);
        if (this.firstPictureParameterSet == null) {
            this.firstPictureParameterSet = read;
        }
        this.currentPictureParameterSet = read;
        byte[] array = AbstractH26XTrack.toArray((ByteBuffer) byteBuffer.rewind());
        byte[] bArr = this.ppsIdToPpsBytes.get(Integer.valueOf(read.pic_parameter_set_id));
        if (bArr != null && !Arrays.equals(bArr, array)) {
            throw new RuntimeException("OMG - I got two SPS with same ID but different settings! (AVC3 is the solution)");
        }
        if (bArr == null) {
            this.pictureParameterRangeMap.put((RangeStartMap<Integer, byte[]>) Integer.valueOf(this.samples.size()), (Integer) array);
        }
        this.ppsIdToPpsBytes.put(Integer.valueOf(read.pic_parameter_set_id), array);
        this.ppsIdToPps.put(Integer.valueOf(read.pic_parameter_set_id), read);
    }

    private void handleSPS(ByteBuffer byteBuffer) throws IOException {
        InputStream cleanBuffer = AbstractH26XTrack.cleanBuffer(new ByteBufferBackedInputStream(byteBuffer));
        cleanBuffer.read();
        SeqParameterSet read = SeqParameterSet.read(cleanBuffer);
        if (this.firstSeqParameterSet == null) {
            this.firstSeqParameterSet = read;
            configureFramerate();
        }
        this.currentSeqParameterSet = read;
        byte[] array = AbstractH26XTrack.toArray((ByteBuffer) byteBuffer.rewind());
        byte[] bArr = this.spsIdToSpsBytes.get(Integer.valueOf(read.seq_parameter_set_id));
        if (bArr != null && !Arrays.equals(bArr, array)) {
            throw new RuntimeException("OMG - I got two SPS with same ID but different settings!");
        }
        if (bArr != null) {
            this.seqParameterRangeMap.put((RangeStartMap<Integer, byte[]>) Integer.valueOf(this.samples.size()), (Integer) array);
        }
        this.spsIdToSpsBytes.put(Integer.valueOf(read.seq_parameter_set_id), array);
        this.spsIdToSps.put(Integer.valueOf(read.seq_parameter_set_id), read);
    }

    private void configureFramerate() {
        if (this.determineFrameRate) {
            VUIParameters vUIParameters = this.firstSeqParameterSet.vuiParams;
            if (vUIParameters != null) {
                this.timescale = vUIParameters.time_scale >> 1;
                this.frametick = vUIParameters.num_units_in_tick;
                if (this.timescale != 0 && this.frametick != 0) {
                    return;
                }
                PrintStream printStream = System.err;
                printStream.println("Warning: vuiParams contain invalid values: time_scale: " + this.timescale + " and frame_tick: " + this.frametick + ". Setting frame rate to 25fps");
                this.timescale = 90000L;
                this.frametick = 3600;
                return;
            }
            System.err.println("Warning: Can't determine frame rate. Guessing 25 fps");
            this.timescale = 90000L;
            this.frametick = 3600;
        }
    }

    /* loaded from: classes3.dex */
    public static class SliceHeader {
        public boolean bottom_field_flag;
        public int colour_plane_id;
        public int delta_pic_order_cnt_0;
        public int delta_pic_order_cnt_1;
        public int delta_pic_order_cnt_bottom;
        public boolean field_pic_flag;
        public int first_mb_in_slice;
        public int frame_num;
        public int idr_pic_id;
        public int pic_order_cnt_lsb;
        public int pic_parameter_set_id;
        public SliceType slice_type;

        /* loaded from: classes3.dex */
        public enum SliceType {
            P,
            B,
            I,
            SP,
            SI
        }

        public SliceHeader(InputStream inputStream, Map<Integer, SeqParameterSet> map, Map<Integer, PictureParameterSet> map2, boolean z) {
            this.field_pic_flag = false;
            this.bottom_field_flag = false;
            try {
                inputStream.read();
                CAVLCReader cAVLCReader = new CAVLCReader(inputStream);
                this.first_mb_in_slice = cAVLCReader.readUE("SliceHeader: first_mb_in_slice");
                switch (cAVLCReader.readUE("SliceHeader: slice_type")) {
                    case 0:
                    case 5:
                        this.slice_type = SliceType.P;
                        break;
                    case 1:
                    case 6:
                        this.slice_type = SliceType.B;
                        break;
                    case 2:
                    case 7:
                        this.slice_type = SliceType.I;
                        break;
                    case 3:
                    case 8:
                        this.slice_type = SliceType.SP;
                        break;
                    case 4:
                    case 9:
                        this.slice_type = SliceType.SI;
                        break;
                }
                this.pic_parameter_set_id = cAVLCReader.readUE("SliceHeader: pic_parameter_set_id");
                PictureParameterSet pictureParameterSet = map2.get(Integer.valueOf(this.pic_parameter_set_id));
                SeqParameterSet seqParameterSet = map.get(Integer.valueOf(pictureParameterSet.seq_parameter_set_id));
                if (seqParameterSet.residual_color_transform_flag) {
                    this.colour_plane_id = cAVLCReader.readU(2, "SliceHeader: colour_plane_id");
                }
                this.frame_num = cAVLCReader.readU(seqParameterSet.log2_max_frame_num_minus4 + 4, "SliceHeader: frame_num");
                if (!seqParameterSet.frame_mbs_only_flag) {
                    this.field_pic_flag = cAVLCReader.readBool("SliceHeader: field_pic_flag");
                    if (this.field_pic_flag) {
                        this.bottom_field_flag = cAVLCReader.readBool("SliceHeader: bottom_field_flag");
                    }
                }
                if (z) {
                    this.idr_pic_id = cAVLCReader.readUE("SliceHeader: idr_pic_id");
                }
                if (seqParameterSet.pic_order_cnt_type == 0) {
                    this.pic_order_cnt_lsb = cAVLCReader.readU(seqParameterSet.log2_max_pic_order_cnt_lsb_minus4 + 4, "SliceHeader: pic_order_cnt_lsb");
                    if (pictureParameterSet.bottom_field_pic_order_in_frame_present_flag && !this.field_pic_flag) {
                        this.delta_pic_order_cnt_bottom = cAVLCReader.readSE("SliceHeader: delta_pic_order_cnt_bottom");
                    }
                }
                if (seqParameterSet.pic_order_cnt_type != 1 || seqParameterSet.delta_pic_order_always_zero_flag) {
                    return;
                }
                this.delta_pic_order_cnt_0 = cAVLCReader.readSE("delta_pic_order_cnt_0");
                if (!pictureParameterSet.bottom_field_pic_order_in_frame_present_flag || this.field_pic_flag) {
                    return;
                }
                this.delta_pic_order_cnt_1 = cAVLCReader.readSE("delta_pic_order_cnt_1");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public String toString() {
            return "SliceHeader{first_mb_in_slice=" + this.first_mb_in_slice + ", slice_type=" + this.slice_type + ", pic_parameter_set_id=" + this.pic_parameter_set_id + ", colour_plane_id=" + this.colour_plane_id + ", frame_num=" + this.frame_num + ", field_pic_flag=" + this.field_pic_flag + ", bottom_field_flag=" + this.bottom_field_flag + ", idr_pic_id=" + this.idr_pic_id + ", pic_order_cnt_lsb=" + this.pic_order_cnt_lsb + ", delta_pic_order_cnt_bottom=" + this.delta_pic_order_cnt_bottom + '}';
        }
    }

    /* loaded from: classes3.dex */
    public class ByteBufferBackedInputStream extends InputStream {
        private final ByteBuffer buf;

        public ByteBufferBackedInputStream(ByteBuffer byteBuffer) {
            this.buf = byteBuffer.duplicate();
        }

        @Override // java.io.InputStream
        public int read() throws IOException {
            if (!this.buf.hasRemaining()) {
                return -1;
            }
            return this.buf.get() & 255;
        }

        @Override // java.io.InputStream
        public int read(byte[] bArr, int i, int i2) throws IOException {
            if (!this.buf.hasRemaining()) {
                return -1;
            }
            int min = Math.min(i2, this.buf.remaining());
            this.buf.get(bArr, i, min);
            return min;
        }
    }

    /* loaded from: classes3.dex */
    public class SEIMessage {
        boolean clock_timestamp_flag;
        int cnt_dropped_flag;
        int counting_type;
        int cpb_removal_delay;
        int ct_type;
        int discontinuity_flag;
        int dpb_removal_delay;
        int full_timestamp_flag;
        int hours_value;
        int minutes_value;
        int n_frames;
        int nuit_field_based_flag;
        int payloadSize;
        int payloadType;
        int pic_struct;
        boolean removal_delay_flag;
        int seconds_value;
        SeqParameterSet sps;
        int time_offset;
        int time_offset_length;

        /* JADX WARN: Type inference failed for: r1v1 */
        /* JADX WARN: Type inference failed for: r1v2, types: [boolean, int] */
        /* JADX WARN: Type inference failed for: r1v4 */
        public SEIMessage(InputStream inputStream, SeqParameterSet seqParameterSet) throws IOException {
            int i;
            ?? r1 = 0;
            this.payloadType = 0;
            this.payloadSize = 0;
            this.sps = seqParameterSet;
            inputStream.read();
            int available = inputStream.available();
            int i2 = 0;
            while (i2 < available) {
                int i3 = r1 == true ? 1 : 0;
                int i4 = r1 == true ? 1 : 0;
                this.payloadType = i3;
                this.payloadSize = r1;
                int read = inputStream.read();
                while (true) {
                    i2++;
                    if (read == 255) {
                        this.payloadType += read;
                        read = inputStream.read();
                    } else {
                        this.payloadType += read;
                        int read2 = inputStream.read();
                        while (true) {
                            i2++;
                            if (read2 == 255) {
                                this.payloadSize += read2;
                                read2 = inputStream.read();
                            } else {
                                this.payloadSize += read2;
                                if (available - i2 < this.payloadSize) {
                                    i2 = available;
                                } else if (this.payloadType == 1) {
                                    VUIParameters vUIParameters = seqParameterSet.vuiParams;
                                    if (vUIParameters != null && (vUIParameters.nalHRDParams != null || vUIParameters.vclHRDParams != null || vUIParameters.pic_struct_present_flag)) {
                                        byte[] bArr = new byte[this.payloadSize];
                                        inputStream.read(bArr);
                                        i2 += this.payloadSize;
                                        CAVLCReader cAVLCReader = new CAVLCReader(new ByteArrayInputStream(bArr));
                                        VUIParameters vUIParameters2 = seqParameterSet.vuiParams;
                                        if (vUIParameters2.nalHRDParams != null || vUIParameters2.vclHRDParams != null) {
                                            this.removal_delay_flag = true;
                                            this.cpb_removal_delay = cAVLCReader.readU(seqParameterSet.vuiParams.nalHRDParams.cpb_removal_delay_length_minus1 + 1, "SEI: cpb_removal_delay");
                                            this.dpb_removal_delay = cAVLCReader.readU(seqParameterSet.vuiParams.nalHRDParams.dpb_output_delay_length_minus1 + 1, "SEI: dpb_removal_delay");
                                        } else {
                                            this.removal_delay_flag = r1;
                                        }
                                        if (seqParameterSet.vuiParams.pic_struct_present_flag) {
                                            this.pic_struct = cAVLCReader.readU(4, "SEI: pic_struct");
                                            switch (this.pic_struct) {
                                                case 3:
                                                case 4:
                                                case 7:
                                                    i = 2;
                                                    break;
                                                case 5:
                                                case 6:
                                                case 8:
                                                    i = 3;
                                                    break;
                                                default:
                                                    i = 1;
                                                    break;
                                            }
                                            for (int i5 = 0; i5 < i; i5++) {
                                                this.clock_timestamp_flag = cAVLCReader.readBool("pic_timing SEI: clock_timestamp_flag[" + i5 + "]");
                                                if (this.clock_timestamp_flag) {
                                                    this.ct_type = cAVLCReader.readU(2, "pic_timing SEI: ct_type");
                                                    this.nuit_field_based_flag = cAVLCReader.readU(1, "pic_timing SEI: nuit_field_based_flag");
                                                    this.counting_type = cAVLCReader.readU(5, "pic_timing SEI: counting_type");
                                                    this.full_timestamp_flag = cAVLCReader.readU(1, "pic_timing SEI: full_timestamp_flag");
                                                    this.discontinuity_flag = cAVLCReader.readU(1, "pic_timing SEI: discontinuity_flag");
                                                    this.cnt_dropped_flag = cAVLCReader.readU(1, "pic_timing SEI: cnt_dropped_flag");
                                                    this.n_frames = cAVLCReader.readU(8, "pic_timing SEI: n_frames");
                                                    if (this.full_timestamp_flag == 1) {
                                                        this.seconds_value = cAVLCReader.readU(6, "pic_timing SEI: seconds_value");
                                                        this.minutes_value = cAVLCReader.readU(6, "pic_timing SEI: minutes_value");
                                                        this.hours_value = cAVLCReader.readU(5, "pic_timing SEI: hours_value");
                                                    } else if (cAVLCReader.readBool("pic_timing SEI: seconds_flag")) {
                                                        this.seconds_value = cAVLCReader.readU(6, "pic_timing SEI: seconds_value");
                                                        if (cAVLCReader.readBool("pic_timing SEI: minutes_flag")) {
                                                            this.minutes_value = cAVLCReader.readU(6, "pic_timing SEI: minutes_value");
                                                            if (cAVLCReader.readBool("pic_timing SEI: hours_flag")) {
                                                                this.hours_value = cAVLCReader.readU(5, "pic_timing SEI: hours_value");
                                                            }
                                                        }
                                                    }
                                                    VUIParameters vUIParameters3 = seqParameterSet.vuiParams;
                                                    HRDParameters hRDParameters = vUIParameters3.nalHRDParams;
                                                    if (hRDParameters != null) {
                                                        this.time_offset_length = hRDParameters.time_offset_length;
                                                    } else {
                                                        HRDParameters hRDParameters2 = vUIParameters3.vclHRDParams;
                                                        if (hRDParameters2 != null) {
                                                            this.time_offset_length = hRDParameters2.time_offset_length;
                                                        } else {
                                                            this.time_offset_length = 24;
                                                        }
                                                    }
                                                    this.time_offset = cAVLCReader.readU(24, "pic_timing SEI: time_offset");
                                                }
                                            }
                                        }
                                    } else {
                                        for (int i6 = 0; i6 < this.payloadSize; i6++) {
                                            inputStream.read();
                                            i2++;
                                        }
                                    }
                                } else {
                                    for (int i7 = 0; i7 < this.payloadSize; i7++) {
                                        inputStream.read();
                                        i2++;
                                    }
                                }
                                H264TrackImpl.LOG.fine(toString());
                                r1 = 0;
                            }
                        }
                    }
                }
            }
        }

        public String toString() {
            String str = "SEIMessage{payloadType=" + this.payloadType + ", payloadSize=" + this.payloadSize;
            if (this.payloadType == 1) {
                VUIParameters vUIParameters = this.sps.vuiParams;
                if (vUIParameters.nalHRDParams != null || vUIParameters.vclHRDParams != null) {
                    str = str + ", cpb_removal_delay=" + this.cpb_removal_delay + ", dpb_removal_delay=" + this.dpb_removal_delay;
                }
                if (this.sps.vuiParams.pic_struct_present_flag) {
                    str = str + ", pic_struct=" + this.pic_struct;
                    if (this.clock_timestamp_flag) {
                        str = str + ", ct_type=" + this.ct_type + ", nuit_field_based_flag=" + this.nuit_field_based_flag + ", counting_type=" + this.counting_type + ", full_timestamp_flag=" + this.full_timestamp_flag + ", discontinuity_flag=" + this.discontinuity_flag + ", cnt_dropped_flag=" + this.cnt_dropped_flag + ", n_frames=" + this.n_frames + ", seconds_value=" + this.seconds_value + ", minutes_value=" + this.minutes_value + ", hours_value=" + this.hours_value + ", time_offset_length=" + this.time_offset_length + ", time_offset=" + this.time_offset;
                    }
                }
            }
            return str + '}';
        }
    }
}
