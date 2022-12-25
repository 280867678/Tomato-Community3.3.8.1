package com.google.android.exoplayer2.extractor.mkv;

import android.support.annotation.Nullable;
import android.support.p006v8.renderscript.ScriptIntrinsicBLAS;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import com.google.android.exoplayer2.C1868C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.audio.Ac3Util;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.extractor.ChunkIndex;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.LongArray;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.AvcConfig;
import com.google.android.exoplayer2.video.ColorInfo;
import com.google.android.exoplayer2.video.HevcConfig;
import com.iceteck.silicompressorr.videocompression.MediaController;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/* loaded from: classes2.dex */
public final class MatroskaExtractor implements Extractor {
    private long blockDurationUs;
    private int blockFlags;
    private int blockLacingSampleCount;
    private int blockLacingSampleIndex;
    private int[] blockLacingSampleSizes;
    private int blockState;
    private long blockTimeUs;
    private int blockTrackNumber;
    private int blockTrackNumberLength;
    private long clusterTimecodeUs;
    private LongArray cueClusterPositions;
    private LongArray cueTimesUs;
    private long cuesContentPosition;
    private Track currentTrack;
    private long durationTimecode;
    private long durationUs;
    private final ParsableByteArray encryptionInitializationVector;
    private final ParsableByteArray encryptionSubsampleData;
    private ByteBuffer encryptionSubsampleDataBuffer;
    private ExtractorOutput extractorOutput;
    private final ParsableByteArray nalLength;
    private final ParsableByteArray nalStartCode;
    private final EbmlReader reader;
    private int sampleBytesRead;
    private int sampleBytesWritten;
    private int sampleCurrentNalBytesRemaining;
    private boolean sampleEncodingHandled;
    private boolean sampleInitializationVectorRead;
    private int samplePartitionCount;
    private boolean samplePartitionCountRead;
    private boolean sampleRead;
    private boolean sampleSeenReferenceBlock;
    private byte sampleSignalByte;
    private boolean sampleSignalByteRead;
    private final ParsableByteArray sampleStrippedBytes;
    private final ParsableByteArray scratch;
    private int seekEntryId;
    private final ParsableByteArray seekEntryIdBytes;
    private long seekEntryPosition;
    private boolean seekForCues;
    private final boolean seekForCuesEnabled;
    private long seekPositionAfterBuildingCues;
    private boolean seenClusterPositionForCurrentCuePoint;
    private long segmentContentPosition;
    private long segmentContentSize;
    private boolean sentSeekMap;
    private final ParsableByteArray subtitleSample;
    private long timecodeScale;
    private final SparseArray<Track> tracks;
    private final VarintReader varintReader;
    private final ParsableByteArray vorbisNumPageSamples;
    private static final byte[] SUBRIP_PREFIX = {49, 10, 48, 48, 58, 48, 48, 58, 48, 48, 44, 48, 48, 48, 32, 45, 45, 62, 32, 48, 48, 58, 48, 48, 58, 48, 48, 44, 48, 48, 48, 10};
    private static final byte[] SUBRIP_TIMECODE_EMPTY = {32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32};
    private static final byte[] SSA_DIALOGUE_FORMAT = Util.getUtf8Bytes("Format: Start, End, ReadOrder, Layer, Style, Name, MarginL, MarginR, MarginV, Effect, Text");
    private static final byte[] SSA_PREFIX = {68, 105, 97, 108, 111, 103, 117, 101, 58, 32, 48, 58, 48, 48, 58, 48, 48, 58, 48, 48, 44, 48, 58, 48, 48, 58, 48, 48, 58, 48, 48, 44};
    private static final byte[] SSA_TIMECODE_EMPTY = {32, 32, 32, 32, 32, 32, 32, 32, 32, 32};
    private static final UUID WAVE_SUBFORMAT_PCM = new UUID(72057594037932032L, -9223371306706625679L);

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void release() {
    }

    static {
        new ExtractorsFactory() { // from class: com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor.1
            @Override // com.google.android.exoplayer2.extractor.ExtractorsFactory
            public Extractor[] createExtractors() {
                return new Extractor[]{new MatroskaExtractor()};
            }
        };
    }

    public MatroskaExtractor() {
        this(0);
    }

    public MatroskaExtractor(int i) {
        this(new DefaultEbmlReader(), i);
    }

    MatroskaExtractor(EbmlReader ebmlReader, int i) {
        this.segmentContentPosition = -1L;
        this.timecodeScale = -9223372036854775807L;
        this.durationTimecode = -9223372036854775807L;
        this.durationUs = -9223372036854775807L;
        this.cuesContentPosition = -1L;
        this.seekPositionAfterBuildingCues = -1L;
        this.clusterTimecodeUs = -9223372036854775807L;
        this.reader = ebmlReader;
        this.reader.init(new InnerEbmlReaderOutput());
        this.seekForCuesEnabled = (i & 1) != 0 ? false : true;
        this.varintReader = new VarintReader();
        this.tracks = new SparseArray<>();
        this.scratch = new ParsableByteArray(4);
        this.vorbisNumPageSamples = new ParsableByteArray(ByteBuffer.allocate(4).putInt(-1).array());
        this.seekEntryIdBytes = new ParsableByteArray(4);
        this.nalStartCode = new ParsableByteArray(NalUnitUtil.NAL_START_CODE);
        this.nalLength = new ParsableByteArray(4);
        this.sampleStrippedBytes = new ParsableByteArray();
        this.subtitleSample = new ParsableByteArray();
        this.encryptionInitializationVector = new ParsableByteArray(8);
        this.encryptionSubsampleData = new ParsableByteArray();
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public boolean sniff(ExtractorInput extractorInput) throws IOException, InterruptedException {
        return new Sniffer().sniff(extractorInput);
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void init(ExtractorOutput extractorOutput) {
        this.extractorOutput = extractorOutput;
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public void seek(long j, long j2) {
        this.clusterTimecodeUs = -9223372036854775807L;
        this.blockState = 0;
        this.reader.reset();
        this.varintReader.reset();
        resetSample();
        for (int i = 0; i < this.tracks.size(); i++) {
            this.tracks.valueAt(i).reset();
        }
    }

    @Override // com.google.android.exoplayer2.extractor.Extractor
    public int read(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException, InterruptedException {
        this.sampleRead = false;
        boolean z = true;
        while (z && !this.sampleRead) {
            z = this.reader.read(extractorInput);
            if (z && maybeSeekForCues(positionHolder, extractorInput.getPosition())) {
                return 1;
            }
        }
        if (!z) {
            for (int i = 0; i < this.tracks.size(); i++) {
                this.tracks.valueAt(i).outputPendingSampleMetadata();
            }
            return -1;
        }
        return 0;
    }

    void startMasterElement(int i, long j, long j2) throws ParserException {
        if (i == 160) {
            this.sampleSeenReferenceBlock = false;
        } else if (i == 174) {
            this.currentTrack = new Track();
        } else if (i == 187) {
            this.seenClusterPositionForCurrentCuePoint = false;
        } else if (i == 19899) {
            this.seekEntryId = -1;
            this.seekEntryPosition = -1L;
        } else if (i == 20533) {
            this.currentTrack.hasContentEncryption = true;
        } else if (i == 21968) {
            this.currentTrack.hasColorInfo = true;
        } else if (i == 25152) {
        } else {
            if (i == 408125543) {
                long j3 = this.segmentContentPosition;
                if (j3 != -1 && j3 != j) {
                    throw new ParserException("Multiple Segment elements not supported");
                }
                this.segmentContentPosition = j;
                this.segmentContentSize = j2;
            } else if (i == 475249515) {
                this.cueTimesUs = new LongArray();
                this.cueClusterPositions = new LongArray();
            } else if (i != 524531317 || this.sentSeekMap) {
            } else {
                if (this.seekForCuesEnabled && this.cuesContentPosition != -1) {
                    this.seekForCues = true;
                    return;
                }
                this.extractorOutput.seekMap(new SeekMap.Unseekable(this.durationUs));
                this.sentSeekMap = true;
            }
        }
    }

    void endMasterElement(int i) throws ParserException {
        if (i == 160) {
            if (this.blockState != 2) {
                return;
            }
            if (!this.sampleSeenReferenceBlock) {
                this.blockFlags |= 1;
            }
            commitSampleToOutput(this.tracks.get(this.blockTrackNumber), this.blockTimeUs);
            this.blockState = 0;
        } else if (i == 174) {
            if (isCodecSupported(this.currentTrack.codecId)) {
                Track track = this.currentTrack;
                track.initializeOutput(this.extractorOutput, track.number);
                SparseArray<Track> sparseArray = this.tracks;
                Track track2 = this.currentTrack;
                sparseArray.put(track2.number, track2);
            }
            this.currentTrack = null;
        } else if (i == 19899) {
            int i2 = this.seekEntryId;
            if (i2 != -1) {
                long j = this.seekEntryPosition;
                if (j != -1) {
                    if (i2 != 475249515) {
                        return;
                    }
                    this.cuesContentPosition = j;
                    return;
                }
            }
            throw new ParserException("Mandatory element SeekID or SeekPosition not found");
        } else if (i == 25152) {
            Track track3 = this.currentTrack;
            if (!track3.hasContentEncryption) {
                return;
            }
            TrackOutput.CryptoData cryptoData = track3.cryptoData;
            if (cryptoData == null) {
                throw new ParserException("Encrypted Track found but ContentEncKeyID was not found");
            }
            track3.drmInitData = new DrmInitData(new DrmInitData.SchemeData(C1868C.UUID_NIL, "video/webm", cryptoData.encryptionKey));
        } else if (i == 28032) {
            Track track4 = this.currentTrack;
            if (track4.hasContentEncryption && track4.sampleStrippedBytes != null) {
                throw new ParserException("Combining encryption and compression is not supported");
            }
        } else if (i == 357149030) {
            if (this.timecodeScale == -9223372036854775807L) {
                this.timecodeScale = 1000000L;
            }
            long j2 = this.durationTimecode;
            if (j2 == -9223372036854775807L) {
                return;
            }
            this.durationUs = scaleTimecodeToUs(j2);
        } else if (i != 374648427) {
            if (i != 475249515 || this.sentSeekMap) {
                return;
            }
            this.extractorOutput.seekMap(buildSeekMap());
            this.sentSeekMap = true;
        } else if (this.tracks.size() == 0) {
            throw new ParserException("No valid tracks were found");
        } else {
            this.extractorOutput.endTracks();
        }
    }

    void integerElement(int i, long j) throws ParserException {
        if (i == 20529) {
            if (j == 0) {
                return;
            }
            throw new ParserException("ContentEncodingOrder " + j + " not supported");
        } else if (i == 20530) {
            if (j == 1) {
                return;
            }
            throw new ParserException("ContentEncodingScope " + j + " not supported");
        } else {
            boolean z = false;
            switch (i) {
                case ScriptIntrinsicBLAS.NON_UNIT /* 131 */:
                    this.currentTrack.type = (int) j;
                    return;
                case 136:
                    Track track = this.currentTrack;
                    if (j == 1) {
                        z = true;
                    }
                    track.flagDefault = z;
                    return;
                case 155:
                    this.blockDurationUs = scaleTimecodeToUs(j);
                    return;
                case 159:
                    this.currentTrack.channelCount = (int) j;
                    return;
                case 176:
                    this.currentTrack.width = (int) j;
                    return;
                case 179:
                    this.cueTimesUs.add(scaleTimecodeToUs(j));
                    return;
                case 186:
                    this.currentTrack.height = (int) j;
                    return;
                case 215:
                    this.currentTrack.number = (int) j;
                    return;
                case 231:
                    this.clusterTimecodeUs = scaleTimecodeToUs(j);
                    return;
                case 241:
                    if (this.seenClusterPositionForCurrentCuePoint) {
                        return;
                    }
                    this.cueClusterPositions.add(j);
                    this.seenClusterPositionForCurrentCuePoint = true;
                    return;
                case 251:
                    this.sampleSeenReferenceBlock = true;
                    return;
                case 16980:
                    if (j == 3) {
                        return;
                    }
                    throw new ParserException("ContentCompAlgo " + j + " not supported");
                case 17029:
                    if (j >= 1 && j <= 2) {
                        return;
                    }
                    throw new ParserException("DocTypeReadVersion " + j + " not supported");
                case 17143:
                    if (j == 1) {
                        return;
                    }
                    throw new ParserException("EBMLReadVersion " + j + " not supported");
                case 18401:
                    if (j == 5) {
                        return;
                    }
                    throw new ParserException("ContentEncAlgo " + j + " not supported");
                case 18408:
                    if (j == 1) {
                        return;
                    }
                    throw new ParserException("AESSettingsCipherMode " + j + " not supported");
                case 21420:
                    this.seekEntryPosition = j + this.segmentContentPosition;
                    return;
                case 21432:
                    int i2 = (int) j;
                    if (i2 == 0) {
                        this.currentTrack.stereoMode = 0;
                        return;
                    } else if (i2 == 1) {
                        this.currentTrack.stereoMode = 2;
                        return;
                    } else if (i2 == 3) {
                        this.currentTrack.stereoMode = 1;
                        return;
                    } else if (i2 != 15) {
                        return;
                    } else {
                        this.currentTrack.stereoMode = 3;
                        return;
                    }
                case 21680:
                    this.currentTrack.displayWidth = (int) j;
                    return;
                case 21682:
                    this.currentTrack.displayUnit = (int) j;
                    return;
                case 21690:
                    this.currentTrack.displayHeight = (int) j;
                    return;
                case 21930:
                    Track track2 = this.currentTrack;
                    if (j == 1) {
                        z = true;
                    }
                    track2.flagForced = z;
                    return;
                case 22186:
                    this.currentTrack.codecDelayNs = j;
                    return;
                case 22203:
                    this.currentTrack.seekPreRollNs = j;
                    return;
                case 25188:
                    this.currentTrack.audioBitDepth = (int) j;
                    return;
                case 2352003:
                    this.currentTrack.defaultSampleDurationNs = (int) j;
                    return;
                case 2807729:
                    this.timecodeScale = j;
                    return;
                default:
                    switch (i) {
                        case 21945:
                            int i3 = (int) j;
                            if (i3 == 1) {
                                this.currentTrack.colorRange = 2;
                                return;
                            } else if (i3 != 2) {
                                return;
                            } else {
                                this.currentTrack.colorRange = 1;
                                return;
                            }
                        case 21946:
                            int i4 = (int) j;
                            if (i4 != 1) {
                                if (i4 == 16) {
                                    this.currentTrack.colorTransfer = 6;
                                    return;
                                } else if (i4 == 18) {
                                    this.currentTrack.colorTransfer = 7;
                                    return;
                                } else if (i4 != 6 && i4 != 7) {
                                    return;
                                }
                            }
                            this.currentTrack.colorTransfer = 3;
                            return;
                        case 21947:
                            Track track3 = this.currentTrack;
                            track3.hasColorInfo = true;
                            int i5 = (int) j;
                            if (i5 == 1) {
                                track3.colorSpace = 1;
                                return;
                            } else if (i5 == 9) {
                                track3.colorSpace = 6;
                                return;
                            } else if (i5 != 4 && i5 != 5 && i5 != 6 && i5 != 7) {
                                return;
                            } else {
                                this.currentTrack.colorSpace = 2;
                                return;
                            }
                        case 21948:
                            this.currentTrack.maxContentLuminance = (int) j;
                            return;
                        case 21949:
                            this.currentTrack.maxFrameAverageLuminance = (int) j;
                            return;
                        default:
                            return;
                    }
            }
        }
    }

    void floatElement(int i, double d) {
        if (i == 181) {
            this.currentTrack.sampleRate = (int) d;
        } else if (i == 17545) {
            this.durationTimecode = (long) d;
        } else {
            switch (i) {
                case 21969:
                    this.currentTrack.primaryRChromaticityX = (float) d;
                    return;
                case 21970:
                    this.currentTrack.primaryRChromaticityY = (float) d;
                    return;
                case 21971:
                    this.currentTrack.primaryGChromaticityX = (float) d;
                    return;
                case 21972:
                    this.currentTrack.primaryGChromaticityY = (float) d;
                    return;
                case 21973:
                    this.currentTrack.primaryBChromaticityX = (float) d;
                    return;
                case 21974:
                    this.currentTrack.primaryBChromaticityY = (float) d;
                    return;
                case 21975:
                    this.currentTrack.whitePointChromaticityX = (float) d;
                    return;
                case 21976:
                    this.currentTrack.whitePointChromaticityY = (float) d;
                    return;
                case 21977:
                    this.currentTrack.maxMasteringLuminance = (float) d;
                    return;
                case 21978:
                    this.currentTrack.minMasteringLuminance = (float) d;
                    return;
                default:
                    return;
            }
        }
    }

    void stringElement(int i, String str) throws ParserException {
        if (i == 134) {
            this.currentTrack.codecId = str;
        } else if (i != 17026) {
            if (i != 2274716) {
                return;
            }
            this.currentTrack.language = str;
        } else if ("webm".equals(str) || "matroska".equals(str)) {
        } else {
            throw new ParserException("DocType " + str + " not supported");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:102:0x01ff, code lost:
        throw new com.google.android.exoplayer2.ParserException("EBML lacing sample size out of range.");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void binaryElement(int i, int i2, ExtractorInput extractorInput) throws IOException, InterruptedException {
        char c;
        int i3;
        int i4;
        int[] iArr;
        int i5 = 0;
        int i6 = 1;
        if (i != 161 && i != 163) {
            if (i == 16981) {
                Track track = this.currentTrack;
                track.sampleStrippedBytes = new byte[i2];
                extractorInput.readFully(track.sampleStrippedBytes, 0, i2);
                return;
            } else if (i == 18402) {
                byte[] bArr = new byte[i2];
                extractorInput.readFully(bArr, 0, i2);
                this.currentTrack.cryptoData = new TrackOutput.CryptoData(1, bArr, 0, 0);
                return;
            } else if (i == 21419) {
                Arrays.fill(this.seekEntryIdBytes.data, (byte) 0);
                extractorInput.readFully(this.seekEntryIdBytes.data, 4 - i2, i2);
                this.seekEntryIdBytes.setPosition(0);
                this.seekEntryId = (int) this.seekEntryIdBytes.readUnsignedInt();
                return;
            } else if (i == 25506) {
                Track track2 = this.currentTrack;
                track2.codecPrivate = new byte[i2];
                extractorInput.readFully(track2.codecPrivate, 0, i2);
                return;
            } else if (i == 30322) {
                Track track3 = this.currentTrack;
                track3.projectionData = new byte[i2];
                extractorInput.readFully(track3.projectionData, 0, i2);
                return;
            } else {
                throw new ParserException("Unexpected id: " + i);
            }
        }
        if (this.blockState == 0) {
            this.blockTrackNumber = (int) this.varintReader.readUnsignedVarint(extractorInput, false, true, 8);
            this.blockTrackNumberLength = this.varintReader.getLastLength();
            this.blockDurationUs = -9223372036854775807L;
            this.blockState = 1;
            this.scratch.reset();
        }
        Track track4 = this.tracks.get(this.blockTrackNumber);
        if (track4 == null) {
            extractorInput.skipFully(i2 - this.blockTrackNumberLength);
            this.blockState = 0;
            return;
        }
        if (this.blockState == 1) {
            readScratch(extractorInput, 3);
            int i7 = (this.scratch.data[2] & 6) >> 1;
            byte b = 255;
            if (i7 == 0) {
                this.blockLacingSampleCount = 1;
                this.blockLacingSampleSizes = ensureArrayCapacity(this.blockLacingSampleSizes, 1);
                this.blockLacingSampleSizes[0] = (i2 - this.blockTrackNumberLength) - 3;
            } else if (i != 163) {
                throw new ParserException("Lacing only supported in SimpleBlocks.");
            } else {
                readScratch(extractorInput, 4);
                this.blockLacingSampleCount = (this.scratch.data[3] & 255) + 1;
                this.blockLacingSampleSizes = ensureArrayCapacity(this.blockLacingSampleSizes, this.blockLacingSampleCount);
                if (i7 == 2) {
                    int i8 = this.blockLacingSampleCount;
                    Arrays.fill(this.blockLacingSampleSizes, 0, i8, ((i2 - this.blockTrackNumberLength) - 4) / i8);
                } else if (i7 == 1) {
                    int i9 = 0;
                    int i10 = 4;
                    int i11 = 0;
                    while (true) {
                        i3 = this.blockLacingSampleCount;
                        if (i9 >= i3 - 1) {
                            break;
                        }
                        this.blockLacingSampleSizes[i9] = 0;
                        do {
                            i10++;
                            readScratch(extractorInput, i10);
                            i4 = this.scratch.data[i10 - 1] & 255;
                            iArr = this.blockLacingSampleSizes;
                            iArr[i9] = iArr[i9] + i4;
                        } while (i4 == 255);
                        i11 += iArr[i9];
                        i9++;
                    }
                    this.blockLacingSampleSizes[i3 - 1] = ((i2 - this.blockTrackNumberLength) - i10) - i11;
                } else if (i7 != 3) {
                    throw new ParserException("Unexpected lacing value: " + i7);
                } else {
                    int i12 = 0;
                    int i13 = 4;
                    int i14 = 0;
                    while (true) {
                        int i15 = this.blockLacingSampleCount;
                        if (i12 < i15 - 1) {
                            this.blockLacingSampleSizes[i12] = i5;
                            i13++;
                            readScratch(extractorInput, i13);
                            int i16 = i13 - 1;
                            if (this.scratch.data[i16] == 0) {
                                throw new ParserException("No valid varint length mask found");
                            }
                            long j = 0;
                            int i17 = 0;
                            while (true) {
                                if (i17 >= 8) {
                                    break;
                                }
                                int i18 = i6 << (7 - i17);
                                if ((this.scratch.data[i16] & i18) != 0) {
                                    i13 += i17;
                                    readScratch(extractorInput, i13);
                                    long j2 = (~i18) & this.scratch.data[i16] & b;
                                    int i19 = i16 + 1;
                                    while (true) {
                                        j = j2;
                                        if (i19 >= i13) {
                                            break;
                                        }
                                        j2 = (j << 8) | (this.scratch.data[i19] & b);
                                        i19++;
                                        b = 255;
                                    }
                                    if (i12 > 0) {
                                        j -= (1 << ((i17 * 7) + 6)) - 1;
                                    }
                                } else {
                                    i17++;
                                    i6 = 1;
                                    b = 255;
                                }
                            }
                            long j3 = j;
                            if (j3 < -2147483648L || j3 > 2147483647L) {
                                break;
                            }
                            int i20 = (int) j3;
                            int[] iArr2 = this.blockLacingSampleSizes;
                            if (i12 != 0) {
                                i20 += iArr2[i12 - 1];
                            }
                            iArr2[i12] = i20;
                            i14 += this.blockLacingSampleSizes[i12];
                            i12++;
                            i5 = 0;
                            i6 = 1;
                            b = 255;
                        } else {
                            c = 1;
                            this.blockLacingSampleSizes[i15 - 1] = ((i2 - this.blockTrackNumberLength) - i13) - i14;
                            break;
                        }
                    }
                }
            }
            c = 1;
            byte[] bArr2 = this.scratch.data;
            this.blockTimeUs = this.clusterTimecodeUs + scaleTimecodeToUs((bArr2[c] & 255) | (bArr2[0] << 8));
            this.blockFlags = ((track4.type == 2 || (i == 163 && (this.scratch.data[2] & 128) == 128)) ? 1 : 0) | ((this.scratch.data[2] & 8) == 8 ? Integer.MIN_VALUE : 0);
            this.blockState = 2;
            this.blockLacingSampleIndex = 0;
        }
        if (i != 163) {
            writeSampleData(extractorInput, track4, this.blockLacingSampleSizes[0]);
            return;
        }
        while (true) {
            int i21 = this.blockLacingSampleIndex;
            if (i21 < this.blockLacingSampleCount) {
                writeSampleData(extractorInput, track4, this.blockLacingSampleSizes[i21]);
                commitSampleToOutput(track4, this.blockTimeUs + ((this.blockLacingSampleIndex * track4.defaultSampleDurationNs) / 1000));
                this.blockLacingSampleIndex++;
            } else {
                this.blockState = 0;
                return;
            }
        }
    }

    private void commitSampleToOutput(Track track, long j) {
        TrueHdSampleRechunker trueHdSampleRechunker = track.trueHdSampleRechunker;
        if (trueHdSampleRechunker != null) {
            trueHdSampleRechunker.sampleMetadata(track, j);
        } else {
            if ("S_TEXT/UTF8".equals(track.codecId)) {
                commitSubtitleSample(track, "%02d:%02d:%02d,%03d", 19, 1000L, SUBRIP_TIMECODE_EMPTY);
            } else if ("S_TEXT/ASS".equals(track.codecId)) {
                commitSubtitleSample(track, "%01d:%02d:%02d:%02d", 21, 10000L, SSA_TIMECODE_EMPTY);
            }
            track.output.sampleMetadata(j, this.blockFlags, this.sampleBytesWritten, 0, track.cryptoData);
        }
        this.sampleRead = true;
        resetSample();
    }

    private void resetSample() {
        this.sampleBytesRead = 0;
        this.sampleBytesWritten = 0;
        this.sampleCurrentNalBytesRemaining = 0;
        this.sampleEncodingHandled = false;
        this.sampleSignalByteRead = false;
        this.samplePartitionCountRead = false;
        this.samplePartitionCount = 0;
        this.sampleSignalByte = (byte) 0;
        this.sampleInitializationVectorRead = false;
        this.sampleStrippedBytes.reset();
    }

    private void readScratch(ExtractorInput extractorInput, int i) throws IOException, InterruptedException {
        if (this.scratch.limit() >= i) {
            return;
        }
        if (this.scratch.capacity() < i) {
            ParsableByteArray parsableByteArray = this.scratch;
            byte[] bArr = parsableByteArray.data;
            parsableByteArray.reset(Arrays.copyOf(bArr, Math.max(bArr.length * 2, i)), this.scratch.limit());
        }
        ParsableByteArray parsableByteArray2 = this.scratch;
        extractorInput.readFully(parsableByteArray2.data, parsableByteArray2.limit(), i - this.scratch.limit());
        this.scratch.setLimit(i);
    }

    private void writeSampleData(ExtractorInput extractorInput, Track track, int i) throws IOException, InterruptedException {
        int i2;
        if ("S_TEXT/UTF8".equals(track.codecId)) {
            writeSubtitleSampleData(extractorInput, SUBRIP_PREFIX, i);
        } else if ("S_TEXT/ASS".equals(track.codecId)) {
            writeSubtitleSampleData(extractorInput, SSA_PREFIX, i);
        } else {
            TrackOutput trackOutput = track.output;
            boolean z = true;
            if (!this.sampleEncodingHandled) {
                if (track.hasContentEncryption) {
                    this.blockFlags &= -1073741825;
                    int i3 = 128;
                    if (!this.sampleSignalByteRead) {
                        extractorInput.readFully(this.scratch.data, 0, 1);
                        this.sampleBytesRead++;
                        byte[] bArr = this.scratch.data;
                        if ((bArr[0] & 128) == 128) {
                            throw new ParserException("Extension bit is set in signal byte");
                        }
                        this.sampleSignalByte = bArr[0];
                        this.sampleSignalByteRead = true;
                    }
                    if ((this.sampleSignalByte & 1) == 1) {
                        boolean z2 = (this.sampleSignalByte & 2) == 2;
                        this.blockFlags |= 1073741824;
                        if (!this.sampleInitializationVectorRead) {
                            extractorInput.readFully(this.encryptionInitializationVector.data, 0, 8);
                            this.sampleBytesRead += 8;
                            this.sampleInitializationVectorRead = true;
                            byte[] bArr2 = this.scratch.data;
                            if (!z2) {
                                i3 = 0;
                            }
                            bArr2[0] = (byte) (i3 | 8);
                            this.scratch.setPosition(0);
                            trackOutput.sampleData(this.scratch, 1);
                            this.sampleBytesWritten++;
                            this.encryptionInitializationVector.setPosition(0);
                            trackOutput.sampleData(this.encryptionInitializationVector, 8);
                            this.sampleBytesWritten += 8;
                        }
                        if (z2) {
                            if (!this.samplePartitionCountRead) {
                                extractorInput.readFully(this.scratch.data, 0, 1);
                                this.sampleBytesRead++;
                                this.scratch.setPosition(0);
                                this.samplePartitionCount = this.scratch.readUnsignedByte();
                                this.samplePartitionCountRead = true;
                            }
                            int i4 = this.samplePartitionCount * 4;
                            this.scratch.reset(i4);
                            extractorInput.readFully(this.scratch.data, 0, i4);
                            this.sampleBytesRead += i4;
                            short s = (short) ((this.samplePartitionCount / 2) + 1);
                            int i5 = (s * 6) + 2;
                            ByteBuffer byteBuffer = this.encryptionSubsampleDataBuffer;
                            if (byteBuffer == null || byteBuffer.capacity() < i5) {
                                this.encryptionSubsampleDataBuffer = ByteBuffer.allocate(i5);
                            }
                            this.encryptionSubsampleDataBuffer.position(0);
                            this.encryptionSubsampleDataBuffer.putShort(s);
                            int i6 = 0;
                            int i7 = 0;
                            while (true) {
                                i2 = this.samplePartitionCount;
                                if (i6 >= i2) {
                                    break;
                                }
                                int readUnsignedIntToInt = this.scratch.readUnsignedIntToInt();
                                if (i6 % 2 == 0) {
                                    this.encryptionSubsampleDataBuffer.putShort((short) (readUnsignedIntToInt - i7));
                                } else {
                                    this.encryptionSubsampleDataBuffer.putInt(readUnsignedIntToInt - i7);
                                }
                                i6++;
                                i7 = readUnsignedIntToInt;
                            }
                            int i8 = (i - this.sampleBytesRead) - i7;
                            if (i2 % 2 == 1) {
                                this.encryptionSubsampleDataBuffer.putInt(i8);
                            } else {
                                this.encryptionSubsampleDataBuffer.putShort((short) i8);
                                this.encryptionSubsampleDataBuffer.putInt(0);
                            }
                            this.encryptionSubsampleData.reset(this.encryptionSubsampleDataBuffer.array(), i5);
                            trackOutput.sampleData(this.encryptionSubsampleData, i5);
                            this.sampleBytesWritten += i5;
                        }
                    }
                } else {
                    byte[] bArr3 = track.sampleStrippedBytes;
                    if (bArr3 != null) {
                        this.sampleStrippedBytes.reset(bArr3, bArr3.length);
                    }
                }
                this.sampleEncodingHandled = true;
            }
            int limit = i + this.sampleStrippedBytes.limit();
            if ("V_MPEG4/ISO/AVC".equals(track.codecId) || "V_MPEGH/ISO/HEVC".equals(track.codecId)) {
                byte[] bArr4 = this.nalLength.data;
                bArr4[0] = 0;
                bArr4[1] = 0;
                bArr4[2] = 0;
                int i9 = track.nalUnitLengthFieldLength;
                int i10 = 4 - i9;
                while (this.sampleBytesRead < limit) {
                    int i11 = this.sampleCurrentNalBytesRemaining;
                    if (i11 == 0) {
                        readToTarget(extractorInput, bArr4, i10, i9);
                        this.nalLength.setPosition(0);
                        this.sampleCurrentNalBytesRemaining = this.nalLength.readUnsignedIntToInt();
                        this.nalStartCode.setPosition(0);
                        trackOutput.sampleData(this.nalStartCode, 4);
                        this.sampleBytesWritten += 4;
                    } else {
                        this.sampleCurrentNalBytesRemaining = i11 - readToOutput(extractorInput, trackOutput, i11);
                    }
                }
            } else {
                if (track.trueHdSampleRechunker != null) {
                    if (this.sampleStrippedBytes.limit() != 0) {
                        z = false;
                    }
                    Assertions.checkState(z);
                    track.trueHdSampleRechunker.startSample(extractorInput, this.blockFlags, limit);
                }
                while (true) {
                    int i12 = this.sampleBytesRead;
                    if (i12 >= limit) {
                        break;
                    }
                    readToOutput(extractorInput, trackOutput, limit - i12);
                }
            }
            if (!"A_VORBIS".equals(track.codecId)) {
                return;
            }
            this.vorbisNumPageSamples.setPosition(0);
            trackOutput.sampleData(this.vorbisNumPageSamples, 4);
            this.sampleBytesWritten += 4;
        }
    }

    private void writeSubtitleSampleData(ExtractorInput extractorInput, byte[] bArr, int i) throws IOException, InterruptedException {
        int length = bArr.length + i;
        if (this.subtitleSample.capacity() < length) {
            this.subtitleSample.data = Arrays.copyOf(bArr, length + i);
        } else {
            System.arraycopy(bArr, 0, this.subtitleSample.data, 0, bArr.length);
        }
        extractorInput.readFully(this.subtitleSample.data, bArr.length, i);
        this.subtitleSample.reset(length);
    }

    private void commitSubtitleSample(Track track, String str, int i, long j, byte[] bArr) {
        setSampleDuration(this.subtitleSample.data, this.blockDurationUs, str, i, j, bArr);
        TrackOutput trackOutput = track.output;
        ParsableByteArray parsableByteArray = this.subtitleSample;
        trackOutput.sampleData(parsableByteArray, parsableByteArray.limit());
        this.sampleBytesWritten += this.subtitleSample.limit();
    }

    private static void setSampleDuration(byte[] bArr, long j, String str, int i, long j2, byte[] bArr2) {
        byte[] utf8Bytes;
        byte[] bArr3;
        if (j == -9223372036854775807L) {
            bArr3 = bArr2;
            utf8Bytes = bArr3;
        } else {
            int i2 = (int) (j / 3600000000L);
            long j3 = j - ((i2 * 3600) * 1000000);
            int i3 = (int) (j3 / 60000000);
            long j4 = j3 - ((i3 * 60) * 1000000);
            int i4 = (int) (j4 / 1000000);
            utf8Bytes = Util.getUtf8Bytes(String.format(Locale.US, str, Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf((int) ((j4 - (i4 * 1000000)) / j2))));
            bArr3 = bArr2;
        }
        System.arraycopy(utf8Bytes, 0, bArr, i, bArr3.length);
    }

    private void readToTarget(ExtractorInput extractorInput, byte[] bArr, int i, int i2) throws IOException, InterruptedException {
        int min = Math.min(i2, this.sampleStrippedBytes.bytesLeft());
        extractorInput.readFully(bArr, i + min, i2 - min);
        if (min > 0) {
            this.sampleStrippedBytes.readBytes(bArr, i, min);
        }
        this.sampleBytesRead += i2;
    }

    private int readToOutput(ExtractorInput extractorInput, TrackOutput trackOutput, int i) throws IOException, InterruptedException {
        int sampleData;
        int bytesLeft = this.sampleStrippedBytes.bytesLeft();
        if (bytesLeft > 0) {
            sampleData = Math.min(i, bytesLeft);
            trackOutput.sampleData(this.sampleStrippedBytes, sampleData);
        } else {
            sampleData = trackOutput.sampleData(extractorInput, i, false);
        }
        this.sampleBytesRead += sampleData;
        this.sampleBytesWritten += sampleData;
        return sampleData;
    }

    private SeekMap buildSeekMap() {
        LongArray longArray;
        LongArray longArray2;
        if (this.segmentContentPosition == -1 || this.durationUs == -9223372036854775807L || (longArray = this.cueTimesUs) == null || longArray.size() == 0 || (longArray2 = this.cueClusterPositions) == null || longArray2.size() != this.cueTimesUs.size()) {
            this.cueTimesUs = null;
            this.cueClusterPositions = null;
            return new SeekMap.Unseekable(this.durationUs);
        }
        int size = this.cueTimesUs.size();
        int[] iArr = new int[size];
        long[] jArr = new long[size];
        long[] jArr2 = new long[size];
        long[] jArr3 = new long[size];
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            jArr3[i2] = this.cueTimesUs.get(i2);
            jArr[i2] = this.segmentContentPosition + this.cueClusterPositions.get(i2);
        }
        while (true) {
            int i3 = size - 1;
            if (i < i3) {
                int i4 = i + 1;
                iArr[i] = (int) (jArr[i4] - jArr[i]);
                jArr2[i] = jArr3[i4] - jArr3[i];
                i = i4;
            } else {
                iArr[i3] = (int) ((this.segmentContentPosition + this.segmentContentSize) - jArr[i3]);
                jArr2[i3] = this.durationUs - jArr3[i3];
                this.cueTimesUs = null;
                this.cueClusterPositions = null;
                return new ChunkIndex(iArr, jArr, jArr2, jArr3);
            }
        }
    }

    private boolean maybeSeekForCues(PositionHolder positionHolder, long j) {
        if (this.seekForCues) {
            this.seekPositionAfterBuildingCues = j;
            positionHolder.position = this.cuesContentPosition;
            this.seekForCues = false;
            return true;
        }
        if (this.sentSeekMap) {
            long j2 = this.seekPositionAfterBuildingCues;
            if (j2 != -1) {
                positionHolder.position = j2;
                this.seekPositionAfterBuildingCues = -1L;
                return true;
            }
        }
        return false;
    }

    private long scaleTimecodeToUs(long j) throws ParserException {
        long j2 = this.timecodeScale;
        if (j2 == -9223372036854775807L) {
            throw new ParserException("Can't scale timecode prior to timecodeScale being set.");
        }
        return Util.scaleLargeTimestamp(j, j2, 1000L);
    }

    private static boolean isCodecSupported(String str) {
        return "V_VP8".equals(str) || "V_VP9".equals(str) || "V_MPEG2".equals(str) || "V_MPEG4/ISO/SP".equals(str) || "V_MPEG4/ISO/ASP".equals(str) || "V_MPEG4/ISO/AP".equals(str) || "V_MPEG4/ISO/AVC".equals(str) || "V_MPEGH/ISO/HEVC".equals(str) || "V_MS/VFW/FOURCC".equals(str) || "V_THEORA".equals(str) || "A_OPUS".equals(str) || "A_VORBIS".equals(str) || "A_AAC".equals(str) || "A_MPEG/L2".equals(str) || "A_MPEG/L3".equals(str) || "A_AC3".equals(str) || "A_EAC3".equals(str) || "A_TRUEHD".equals(str) || "A_DTS".equals(str) || "A_DTS/EXPRESS".equals(str) || "A_DTS/LOSSLESS".equals(str) || "A_FLAC".equals(str) || "A_MS/ACM".equals(str) || "A_PCM/INT/LIT".equals(str) || "S_TEXT/UTF8".equals(str) || "S_TEXT/ASS".equals(str) || "S_VOBSUB".equals(str) || "S_HDMV/PGS".equals(str) || "S_DVBSUB".equals(str);
    }

    private static int[] ensureArrayCapacity(int[] iArr, int i) {
        if (iArr == null) {
            return new int[i];
        }
        return iArr.length >= i ? iArr : new int[Math.max(iArr.length * 2, i)];
    }

    /* loaded from: classes2.dex */
    private final class InnerEbmlReaderOutput implements EbmlReaderOutput {
        @Override // com.google.android.exoplayer2.extractor.mkv.EbmlReaderOutput
        public int getElementType(int i) {
            switch (i) {
                case ScriptIntrinsicBLAS.NON_UNIT /* 131 */:
                case 136:
                case 155:
                case 159:
                case 176:
                case 179:
                case 186:
                case 215:
                case 231:
                case 241:
                case 251:
                case 16980:
                case 17029:
                case 17143:
                case 18401:
                case 18408:
                case 20529:
                case 20530:
                case 21420:
                case 21432:
                case 21680:
                case 21682:
                case 21690:
                case 21930:
                case 21945:
                case 21946:
                case 21947:
                case 21948:
                case 21949:
                case 22186:
                case 22203:
                case 25188:
                case 2352003:
                case 2807729:
                    return 2;
                case 134:
                case 17026:
                case 2274716:
                    return 3;
                case 160:
                case 174:
                case 183:
                case 187:
                case 224:
                case 225:
                case 18407:
                case 19899:
                case 20532:
                case 20533:
                case 21936:
                case 21968:
                case 25152:
                case 28032:
                case 30320:
                case 290298740:
                case 357149030:
                case 374648427:
                case 408125543:
                case 440786851:
                case 475249515:
                case 524531317:
                    return 1;
                case 161:
                case 163:
                case 16981:
                case 18402:
                case 21419:
                case 25506:
                case 30322:
                    return 4;
                case 181:
                case 17545:
                case 21969:
                case 21970:
                case 21971:
                case 21972:
                case 21973:
                case 21974:
                case 21975:
                case 21976:
                case 21977:
                case 21978:
                    return 5;
                default:
                    return 0;
            }
        }

        @Override // com.google.android.exoplayer2.extractor.mkv.EbmlReaderOutput
        public boolean isLevel1Element(int i) {
            return i == 357149030 || i == 524531317 || i == 475249515 || i == 374648427;
        }

        private InnerEbmlReaderOutput() {
        }

        @Override // com.google.android.exoplayer2.extractor.mkv.EbmlReaderOutput
        public void startMasterElement(int i, long j, long j2) throws ParserException {
            MatroskaExtractor.this.startMasterElement(i, j, j2);
        }

        @Override // com.google.android.exoplayer2.extractor.mkv.EbmlReaderOutput
        public void endMasterElement(int i) throws ParserException {
            MatroskaExtractor.this.endMasterElement(i);
        }

        @Override // com.google.android.exoplayer2.extractor.mkv.EbmlReaderOutput
        public void integerElement(int i, long j) throws ParserException {
            MatroskaExtractor.this.integerElement(i, j);
        }

        @Override // com.google.android.exoplayer2.extractor.mkv.EbmlReaderOutput
        public void floatElement(int i, double d) throws ParserException {
            MatroskaExtractor.this.floatElement(i, d);
        }

        @Override // com.google.android.exoplayer2.extractor.mkv.EbmlReaderOutput
        public void stringElement(int i, String str) throws ParserException {
            MatroskaExtractor.this.stringElement(i, str);
        }

        @Override // com.google.android.exoplayer2.extractor.mkv.EbmlReaderOutput
        public void binaryElement(int i, int i2, ExtractorInput extractorInput) throws IOException, InterruptedException {
            MatroskaExtractor.this.binaryElement(i, i2, extractorInput);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class TrueHdSampleRechunker {
        private int blockFlags;
        private int chunkSize;
        private boolean foundSyncframe;
        private int sampleCount;
        private final byte[] syncframePrefix = new byte[10];
        private long timeUs;

        public void reset() {
            this.foundSyncframe = false;
        }

        public void startSample(ExtractorInput extractorInput, int i, int i2) throws IOException, InterruptedException {
            if (!this.foundSyncframe) {
                extractorInput.peekFully(this.syncframePrefix, 0, 10);
                extractorInput.resetPeekPosition();
                if (Ac3Util.parseTrueHdSyncframeAudioSampleCount(this.syncframePrefix) == -1) {
                    return;
                }
                this.foundSyncframe = true;
                this.sampleCount = 0;
            }
            if (this.sampleCount == 0) {
                this.blockFlags = i;
                this.chunkSize = 0;
            }
            this.chunkSize += i2;
        }

        public void sampleMetadata(Track track, long j) {
            if (!this.foundSyncframe) {
                return;
            }
            int i = this.sampleCount;
            this.sampleCount = i + 1;
            if (i == 0) {
                this.timeUs = j;
            }
            if (this.sampleCount < 16) {
                return;
            }
            track.output.sampleMetadata(this.timeUs, this.blockFlags, this.chunkSize, 0, track.cryptoData);
            this.sampleCount = 0;
        }

        public void outputPendingSampleMetadata(Track track) {
            if (!this.foundSyncframe || this.sampleCount <= 0) {
                return;
            }
            track.output.sampleMetadata(this.timeUs, this.blockFlags, this.chunkSize, 0, track.cryptoData);
            this.sampleCount = 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class Track {
        public int audioBitDepth;
        public int channelCount;
        public long codecDelayNs;
        public String codecId;
        public byte[] codecPrivate;
        public int colorRange;
        public int colorSpace;
        public int colorTransfer;
        public TrackOutput.CryptoData cryptoData;
        public int defaultSampleDurationNs;
        public int displayHeight;
        public int displayUnit;
        public int displayWidth;
        public DrmInitData drmInitData;
        public boolean flagDefault;
        public boolean flagForced;
        public boolean hasColorInfo;
        public boolean hasContentEncryption;
        public int height;
        private String language;
        public int maxContentLuminance;
        public int maxFrameAverageLuminance;
        public float maxMasteringLuminance;
        public float minMasteringLuminance;
        public int nalUnitLengthFieldLength;
        public int number;
        public TrackOutput output;
        public float primaryBChromaticityX;
        public float primaryBChromaticityY;
        public float primaryGChromaticityX;
        public float primaryGChromaticityY;
        public float primaryRChromaticityX;
        public float primaryRChromaticityY;
        public byte[] projectionData;
        public int sampleRate;
        public byte[] sampleStrippedBytes;
        public long seekPreRollNs;
        public int stereoMode;
        @Nullable
        public TrueHdSampleRechunker trueHdSampleRechunker;
        public int type;
        public float whitePointChromaticityX;
        public float whitePointChromaticityY;
        public int width;

        private Track() {
            this.width = -1;
            this.height = -1;
            this.displayWidth = -1;
            this.displayHeight = -1;
            this.displayUnit = 0;
            this.projectionData = null;
            this.stereoMode = -1;
            this.hasColorInfo = false;
            this.colorSpace = -1;
            this.colorTransfer = -1;
            this.colorRange = -1;
            this.maxContentLuminance = 1000;
            this.maxFrameAverageLuminance = 200;
            this.primaryRChromaticityX = -1.0f;
            this.primaryRChromaticityY = -1.0f;
            this.primaryGChromaticityX = -1.0f;
            this.primaryGChromaticityY = -1.0f;
            this.primaryBChromaticityX = -1.0f;
            this.primaryBChromaticityY = -1.0f;
            this.whitePointChromaticityX = -1.0f;
            this.whitePointChromaticityY = -1.0f;
            this.maxMasteringLuminance = -1.0f;
            this.minMasteringLuminance = -1.0f;
            this.channelCount = 1;
            this.audioBitDepth = -1;
            this.sampleRate = 8000;
            this.codecDelayNs = 0L;
            this.seekPreRollNs = 0L;
            this.flagDefault = true;
            this.language = "eng";
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Removed duplicated region for block: B:15:0x035d  */
        /* JADX WARN: Removed duplicated region for block: B:18:0x0365  */
        /* JADX WARN: Removed duplicated region for block: B:21:0x038a  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void initializeOutput(ExtractorOutput extractorOutput, int i) throws ParserException {
            char c;
            String str;
            List<byte[]> singletonList;
            String str2;
            List<byte[]> list;
            int i2;
            int pcmEncoding;
            int i3;
            Format createImageSampleFormat;
            int i4;
            int i5;
            String str3 = this.codecId;
            int i6 = 0;
            int i7 = 3;
            switch (str3.hashCode()) {
                case -2095576542:
                    if (str3.equals("V_MPEG4/ISO/AP")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case -2095575984:
                    if (str3.equals("V_MPEG4/ISO/SP")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -1985379776:
                    if (str3.equals("A_MS/ACM")) {
                        c = 22;
                        break;
                    }
                    c = 65535;
                    break;
                case -1784763192:
                    if (str3.equals("A_TRUEHD")) {
                        c = 17;
                        break;
                    }
                    c = 65535;
                    break;
                case -1730367663:
                    if (str3.equals("A_VORBIS")) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                case -1482641358:
                    if (str3.equals("A_MPEG/L2")) {
                        c = '\r';
                        break;
                    }
                    c = 65535;
                    break;
                case -1482641357:
                    if (str3.equals("A_MPEG/L3")) {
                        c = 14;
                        break;
                    }
                    c = 65535;
                    break;
                case -1373388978:
                    if (str3.equals("V_MS/VFW/FOURCC")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case -933872740:
                    if (str3.equals("S_DVBSUB")) {
                        c = 28;
                        break;
                    }
                    c = 65535;
                    break;
                case -538363189:
                    if (str3.equals("V_MPEG4/ISO/ASP")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case -538363109:
                    if (str3.equals("V_MPEG4/ISO/AVC")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case -425012669:
                    if (str3.equals("S_VOBSUB")) {
                        c = 26;
                        break;
                    }
                    c = 65535;
                    break;
                case -356037306:
                    if (str3.equals("A_DTS/LOSSLESS")) {
                        c = 20;
                        break;
                    }
                    c = 65535;
                    break;
                case 62923557:
                    if (str3.equals("A_AAC")) {
                        c = '\f';
                        break;
                    }
                    c = 65535;
                    break;
                case 62923603:
                    if (str3.equals("A_AC3")) {
                        c = 15;
                        break;
                    }
                    c = 65535;
                    break;
                case 62927045:
                    if (str3.equals("A_DTS")) {
                        c = 18;
                        break;
                    }
                    c = 65535;
                    break;
                case 82338133:
                    if (str3.equals("V_VP8")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 82338134:
                    if (str3.equals("V_VP9")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 99146302:
                    if (str3.equals("S_HDMV/PGS")) {
                        c = 27;
                        break;
                    }
                    c = 65535;
                    break;
                case 444813526:
                    if (str3.equals("V_THEORA")) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case 542569478:
                    if (str3.equals("A_DTS/EXPRESS")) {
                        c = 19;
                        break;
                    }
                    c = 65535;
                    break;
                case 725957860:
                    if (str3.equals("A_PCM/INT/LIT")) {
                        c = 23;
                        break;
                    }
                    c = 65535;
                    break;
                case 738597099:
                    if (str3.equals("S_TEXT/ASS")) {
                        c = 25;
                        break;
                    }
                    c = 65535;
                    break;
                case 855502857:
                    if (str3.equals("V_MPEGH/ISO/HEVC")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 1422270023:
                    if (str3.equals("S_TEXT/UTF8")) {
                        c = 24;
                        break;
                    }
                    c = 65535;
                    break;
                case 1809237540:
                    if (str3.equals("V_MPEG2")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 1950749482:
                    if (str3.equals("A_EAC3")) {
                        c = 16;
                        break;
                    }
                    c = 65535;
                    break;
                case 1950789798:
                    if (str3.equals("A_FLAC")) {
                        c = 21;
                        break;
                    }
                    c = 65535;
                    break;
                case 1951062397:
                    if (str3.equals("A_OPUS")) {
                        c = 11;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    str = "video/x-vnd.on2.vp8";
                    list = null;
                    i2 = -1;
                    i3 = -1;
                    int i8 = (this.flagDefault ? 1 : 0) | 0;
                    if (this.flagForced) {
                        i6 = 2;
                    }
                    int i9 = i6 | i8;
                    if (!MimeTypes.isAudio(str)) {
                        createImageSampleFormat = Format.createAudioSampleFormat(Integer.toString(i), str, null, -1, i2, this.channelCount, this.sampleRate, i3, list, this.drmInitData, i9, this.language);
                        i7 = 1;
                    } else if (MimeTypes.isVideo(str)) {
                        if (this.displayUnit == 0) {
                            int i10 = this.displayWidth;
                            if (i10 == -1) {
                                i10 = this.width;
                            }
                            this.displayWidth = i10;
                            int i11 = this.displayHeight;
                            if (i11 == -1) {
                                i11 = this.height;
                            }
                            this.displayHeight = i11;
                        }
                        createImageSampleFormat = Format.createVideoSampleFormat(Integer.toString(i), str, null, -1, i2, this.width, this.height, -1.0f, list, -1, (this.displayWidth == -1 || (i5 = this.displayHeight) == -1) ? -1.0f : (this.height * i4) / (this.width * i5), this.projectionData, this.stereoMode, this.hasColorInfo ? new ColorInfo(this.colorSpace, this.colorRange, this.colorTransfer, getHdrStaticInfo()) : null, this.drmInitData);
                        i7 = 2;
                    } else if ("application/x-subrip".equals(str)) {
                        createImageSampleFormat = Format.createTextSampleFormat(Integer.toString(i), str, i9, this.language, this.drmInitData);
                    } else if ("text/x-ssa".equals(str)) {
                        ArrayList arrayList = new ArrayList(2);
                        arrayList.add(MatroskaExtractor.SSA_DIALOGUE_FORMAT);
                        arrayList.add(this.codecPrivate);
                        createImageSampleFormat = Format.createTextSampleFormat(Integer.toString(i), str, null, -1, i9, this.language, -1, this.drmInitData, Long.MAX_VALUE, arrayList);
                    } else if ("application/vobsub".equals(str) || "application/pgs".equals(str) || "application/dvbsubs".equals(str)) {
                        createImageSampleFormat = Format.createImageSampleFormat(Integer.toString(i), str, null, -1, i9, list, this.language, this.drmInitData);
                    } else {
                        throw new ParserException("Unexpected MIME type.");
                    }
                    this.output = extractorOutput.track(this.number, i7);
                    this.output.format(createImageSampleFormat);
                    return;
                case 1:
                    str = "video/x-vnd.on2.vp9";
                    list = null;
                    i2 = -1;
                    i3 = -1;
                    int i82 = (this.flagDefault ? 1 : 0) | 0;
                    if (this.flagForced) {
                    }
                    int i92 = i6 | i82;
                    if (!MimeTypes.isAudio(str)) {
                    }
                    this.output = extractorOutput.track(this.number, i7);
                    this.output.format(createImageSampleFormat);
                    return;
                case 2:
                    str = "video/mpeg2";
                    list = null;
                    i2 = -1;
                    i3 = -1;
                    int i822 = (this.flagDefault ? 1 : 0) | 0;
                    if (this.flagForced) {
                    }
                    int i922 = i6 | i822;
                    if (!MimeTypes.isAudio(str)) {
                    }
                    this.output = extractorOutput.track(this.number, i7);
                    this.output.format(createImageSampleFormat);
                    return;
                case 3:
                case 4:
                case 5:
                    byte[] bArr = this.codecPrivate;
                    singletonList = bArr == null ? null : Collections.singletonList(bArr);
                    str2 = "video/mp4v-es";
                    i2 = -1;
                    i3 = -1;
                    String str4 = str2;
                    list = singletonList;
                    str = str4;
                    int i8222 = (this.flagDefault ? 1 : 0) | 0;
                    if (this.flagForced) {
                    }
                    int i9222 = i6 | i8222;
                    if (!MimeTypes.isAudio(str)) {
                    }
                    this.output = extractorOutput.track(this.number, i7);
                    this.output.format(createImageSampleFormat);
                    return;
                case 6:
                    AvcConfig parse = AvcConfig.parse(new ParsableByteArray(this.codecPrivate));
                    list = parse.initializationData;
                    this.nalUnitLengthFieldLength = parse.nalUnitLengthFieldLength;
                    str = MediaController.MIME_TYPE;
                    i2 = -1;
                    i3 = -1;
                    int i82222 = (this.flagDefault ? 1 : 0) | 0;
                    if (this.flagForced) {
                    }
                    int i92222 = i6 | i82222;
                    if (!MimeTypes.isAudio(str)) {
                    }
                    this.output = extractorOutput.track(this.number, i7);
                    this.output.format(createImageSampleFormat);
                    return;
                case 7:
                    HevcConfig parse2 = HevcConfig.parse(new ParsableByteArray(this.codecPrivate));
                    list = parse2.initializationData;
                    this.nalUnitLengthFieldLength = parse2.nalUnitLengthFieldLength;
                    str = "video/hevc";
                    i2 = -1;
                    i3 = -1;
                    int i822222 = (this.flagDefault ? 1 : 0) | 0;
                    if (this.flagForced) {
                    }
                    int i922222 = i6 | i822222;
                    if (!MimeTypes.isAudio(str)) {
                    }
                    this.output = extractorOutput.track(this.number, i7);
                    this.output.format(createImageSampleFormat);
                    return;
                case '\b':
                    Pair<String, List<byte[]>> parseFourCcPrivate = parseFourCcPrivate(new ParsableByteArray(this.codecPrivate));
                    str2 = (String) parseFourCcPrivate.first;
                    singletonList = (List) parseFourCcPrivate.second;
                    i2 = -1;
                    i3 = -1;
                    String str42 = str2;
                    list = singletonList;
                    str = str42;
                    int i8222222 = (this.flagDefault ? 1 : 0) | 0;
                    if (this.flagForced) {
                    }
                    int i9222222 = i6 | i8222222;
                    if (!MimeTypes.isAudio(str)) {
                    }
                    this.output = extractorOutput.track(this.number, i7);
                    this.output.format(createImageSampleFormat);
                    return;
                case '\t':
                    str = "video/x-unknown";
                    list = null;
                    i2 = -1;
                    i3 = -1;
                    int i82222222 = (this.flagDefault ? 1 : 0) | 0;
                    if (this.flagForced) {
                    }
                    int i92222222 = i6 | i82222222;
                    if (!MimeTypes.isAudio(str)) {
                    }
                    this.output = extractorOutput.track(this.number, i7);
                    this.output.format(createImageSampleFormat);
                    return;
                case '\n':
                    list = parseVorbisCodecPrivate(this.codecPrivate);
                    str = "audio/vorbis";
                    i2 = 8192;
                    i3 = -1;
                    int i822222222 = (this.flagDefault ? 1 : 0) | 0;
                    if (this.flagForced) {
                    }
                    int i922222222 = i6 | i822222222;
                    if (!MimeTypes.isAudio(str)) {
                    }
                    this.output = extractorOutput.track(this.number, i7);
                    this.output.format(createImageSampleFormat);
                    return;
                case 11:
                    ArrayList arrayList2 = new ArrayList(3);
                    arrayList2.add(this.codecPrivate);
                    arrayList2.add(ByteBuffer.allocate(8).order(ByteOrder.nativeOrder()).putLong(this.codecDelayNs).array());
                    arrayList2.add(ByteBuffer.allocate(8).order(ByteOrder.nativeOrder()).putLong(this.seekPreRollNs).array());
                    str = "audio/opus";
                    list = arrayList2;
                    i2 = 5760;
                    i3 = -1;
                    int i8222222222 = (this.flagDefault ? 1 : 0) | 0;
                    if (this.flagForced) {
                    }
                    int i9222222222 = i6 | i8222222222;
                    if (!MimeTypes.isAudio(str)) {
                    }
                    this.output = extractorOutput.track(this.number, i7);
                    this.output.format(createImageSampleFormat);
                    return;
                case '\f':
                    singletonList = Collections.singletonList(this.codecPrivate);
                    str2 = "audio/mp4a-latm";
                    i2 = -1;
                    i3 = -1;
                    String str422 = str2;
                    list = singletonList;
                    str = str422;
                    int i82222222222 = (this.flagDefault ? 1 : 0) | 0;
                    if (this.flagForced) {
                    }
                    int i92222222222 = i6 | i82222222222;
                    if (!MimeTypes.isAudio(str)) {
                    }
                    this.output = extractorOutput.track(this.number, i7);
                    this.output.format(createImageSampleFormat);
                    return;
                case '\r':
                    str = "audio/mpeg-L2";
                    list = null;
                    i2 = 4096;
                    i3 = -1;
                    int i822222222222 = (this.flagDefault ? 1 : 0) | 0;
                    if (this.flagForced) {
                    }
                    int i922222222222 = i6 | i822222222222;
                    if (!MimeTypes.isAudio(str)) {
                    }
                    this.output = extractorOutput.track(this.number, i7);
                    this.output.format(createImageSampleFormat);
                    return;
                case 14:
                    str = "audio/mpeg";
                    list = null;
                    i2 = 4096;
                    i3 = -1;
                    int i8222222222222 = (this.flagDefault ? 1 : 0) | 0;
                    if (this.flagForced) {
                    }
                    int i9222222222222 = i6 | i8222222222222;
                    if (!MimeTypes.isAudio(str)) {
                    }
                    this.output = extractorOutput.track(this.number, i7);
                    this.output.format(createImageSampleFormat);
                    return;
                case 15:
                    str = "audio/ac3";
                    list = null;
                    i2 = -1;
                    i3 = -1;
                    int i82222222222222 = (this.flagDefault ? 1 : 0) | 0;
                    if (this.flagForced) {
                    }
                    int i92222222222222 = i6 | i82222222222222;
                    if (!MimeTypes.isAudio(str)) {
                    }
                    this.output = extractorOutput.track(this.number, i7);
                    this.output.format(createImageSampleFormat);
                    return;
                case 16:
                    str = "audio/eac3";
                    list = null;
                    i2 = -1;
                    i3 = -1;
                    int i822222222222222 = (this.flagDefault ? 1 : 0) | 0;
                    if (this.flagForced) {
                    }
                    int i922222222222222 = i6 | i822222222222222;
                    if (!MimeTypes.isAudio(str)) {
                    }
                    this.output = extractorOutput.track(this.number, i7);
                    this.output.format(createImageSampleFormat);
                    return;
                case 17:
                    this.trueHdSampleRechunker = new TrueHdSampleRechunker();
                    str = "audio/true-hd";
                    list = null;
                    i2 = -1;
                    i3 = -1;
                    int i8222222222222222 = (this.flagDefault ? 1 : 0) | 0;
                    if (this.flagForced) {
                    }
                    int i9222222222222222 = i6 | i8222222222222222;
                    if (!MimeTypes.isAudio(str)) {
                    }
                    this.output = extractorOutput.track(this.number, i7);
                    this.output.format(createImageSampleFormat);
                    return;
                case 18:
                case 19:
                    str = "audio/vnd.dts";
                    list = null;
                    i2 = -1;
                    i3 = -1;
                    int i82222222222222222 = (this.flagDefault ? 1 : 0) | 0;
                    if (this.flagForced) {
                    }
                    int i92222222222222222 = i6 | i82222222222222222;
                    if (!MimeTypes.isAudio(str)) {
                    }
                    this.output = extractorOutput.track(this.number, i7);
                    this.output.format(createImageSampleFormat);
                    return;
                case 20:
                    str = "audio/vnd.dts.hd";
                    list = null;
                    i2 = -1;
                    i3 = -1;
                    int i822222222222222222 = (this.flagDefault ? 1 : 0) | 0;
                    if (this.flagForced) {
                    }
                    int i922222222222222222 = i6 | i822222222222222222;
                    if (!MimeTypes.isAudio(str)) {
                    }
                    this.output = extractorOutput.track(this.number, i7);
                    this.output.format(createImageSampleFormat);
                    return;
                case 21:
                    singletonList = Collections.singletonList(this.codecPrivate);
                    str2 = "audio/flac";
                    i2 = -1;
                    i3 = -1;
                    String str4222 = str2;
                    list = singletonList;
                    str = str4222;
                    int i8222222222222222222 = (this.flagDefault ? 1 : 0) | 0;
                    if (this.flagForced) {
                    }
                    int i9222222222222222222 = i6 | i8222222222222222222;
                    if (!MimeTypes.isAudio(str)) {
                    }
                    this.output = extractorOutput.track(this.number, i7);
                    this.output.format(createImageSampleFormat);
                    return;
                case 22:
                    if (parseMsAcmCodecPrivate(new ParsableByteArray(this.codecPrivate))) {
                        pcmEncoding = Util.getPcmEncoding(this.audioBitDepth);
                        if (pcmEncoding == 0) {
                            Log.w("MatroskaExtractor", "Unsupported PCM bit depth: " + this.audioBitDepth + ". Setting mimeType to audio/x-unknown");
                        }
                        i3 = pcmEncoding;
                        str = "audio/raw";
                        list = null;
                        i2 = -1;
                        int i82222222222222222222 = (this.flagDefault ? 1 : 0) | 0;
                        if (this.flagForced) {
                        }
                        int i92222222222222222222 = i6 | i82222222222222222222;
                        if (!MimeTypes.isAudio(str)) {
                        }
                        this.output = extractorOutput.track(this.number, i7);
                        this.output.format(createImageSampleFormat);
                        return;
                    }
                    Log.w("MatroskaExtractor", "Non-PCM MS/ACM is unsupported. Setting mimeType to audio/x-unknown");
                    str = "audio/x-unknown";
                    list = null;
                    i2 = -1;
                    i3 = -1;
                    int i822222222222222222222 = (this.flagDefault ? 1 : 0) | 0;
                    if (this.flagForced) {
                    }
                    int i922222222222222222222 = i6 | i822222222222222222222;
                    if (!MimeTypes.isAudio(str)) {
                    }
                    this.output = extractorOutput.track(this.number, i7);
                    this.output.format(createImageSampleFormat);
                    return;
                case 23:
                    pcmEncoding = Util.getPcmEncoding(this.audioBitDepth);
                    if (pcmEncoding == 0) {
                        Log.w("MatroskaExtractor", "Unsupported PCM bit depth: " + this.audioBitDepth + ". Setting mimeType to audio/x-unknown");
                        str = "audio/x-unknown";
                        list = null;
                        i2 = -1;
                        i3 = -1;
                        int i8222222222222222222222 = (this.flagDefault ? 1 : 0) | 0;
                        if (this.flagForced) {
                        }
                        int i9222222222222222222222 = i6 | i8222222222222222222222;
                        if (!MimeTypes.isAudio(str)) {
                        }
                        this.output = extractorOutput.track(this.number, i7);
                        this.output.format(createImageSampleFormat);
                        return;
                    }
                    i3 = pcmEncoding;
                    str = "audio/raw";
                    list = null;
                    i2 = -1;
                    int i82222222222222222222222 = (this.flagDefault ? 1 : 0) | 0;
                    if (this.flagForced) {
                    }
                    int i92222222222222222222222 = i6 | i82222222222222222222222;
                    if (!MimeTypes.isAudio(str)) {
                    }
                    this.output = extractorOutput.track(this.number, i7);
                    this.output.format(createImageSampleFormat);
                    return;
                case 24:
                    str = "application/x-subrip";
                    list = null;
                    i2 = -1;
                    i3 = -1;
                    int i822222222222222222222222 = (this.flagDefault ? 1 : 0) | 0;
                    if (this.flagForced) {
                    }
                    int i922222222222222222222222 = i6 | i822222222222222222222222;
                    if (!MimeTypes.isAudio(str)) {
                    }
                    this.output = extractorOutput.track(this.number, i7);
                    this.output.format(createImageSampleFormat);
                    return;
                case 25:
                    str = "text/x-ssa";
                    list = null;
                    i2 = -1;
                    i3 = -1;
                    int i8222222222222222222222222 = (this.flagDefault ? 1 : 0) | 0;
                    if (this.flagForced) {
                    }
                    int i9222222222222222222222222 = i6 | i8222222222222222222222222;
                    if (!MimeTypes.isAudio(str)) {
                    }
                    this.output = extractorOutput.track(this.number, i7);
                    this.output.format(createImageSampleFormat);
                    return;
                case 26:
                    list = Collections.singletonList(this.codecPrivate);
                    str = "application/vobsub";
                    i2 = -1;
                    i3 = -1;
                    int i82222222222222222222222222 = (this.flagDefault ? 1 : 0) | 0;
                    if (this.flagForced) {
                    }
                    int i92222222222222222222222222 = i6 | i82222222222222222222222222;
                    if (!MimeTypes.isAudio(str)) {
                    }
                    this.output = extractorOutput.track(this.number, i7);
                    this.output.format(createImageSampleFormat);
                    return;
                case 27:
                    str = "application/pgs";
                    list = null;
                    i2 = -1;
                    i3 = -1;
                    int i822222222222222222222222222 = (this.flagDefault ? 1 : 0) | 0;
                    if (this.flagForced) {
                    }
                    int i922222222222222222222222222 = i6 | i822222222222222222222222222;
                    if (!MimeTypes.isAudio(str)) {
                    }
                    this.output = extractorOutput.track(this.number, i7);
                    this.output.format(createImageSampleFormat);
                    return;
                case 28:
                    byte[] bArr2 = this.codecPrivate;
                    singletonList = Collections.singletonList(new byte[]{bArr2[0], bArr2[1], bArr2[2], bArr2[3]});
                    str2 = "application/dvbsubs";
                    i2 = -1;
                    i3 = -1;
                    String str42222 = str2;
                    list = singletonList;
                    str = str42222;
                    int i8222222222222222222222222222 = (this.flagDefault ? 1 : 0) | 0;
                    if (this.flagForced) {
                    }
                    int i9222222222222222222222222222 = i6 | i8222222222222222222222222222;
                    if (!MimeTypes.isAudio(str)) {
                    }
                    this.output = extractorOutput.track(this.number, i7);
                    this.output.format(createImageSampleFormat);
                    return;
                default:
                    throw new ParserException("Unrecognized codec identifier.");
            }
        }

        public void outputPendingSampleMetadata() {
            TrueHdSampleRechunker trueHdSampleRechunker = this.trueHdSampleRechunker;
            if (trueHdSampleRechunker != null) {
                trueHdSampleRechunker.outputPendingSampleMetadata(this);
            }
        }

        public void reset() {
            TrueHdSampleRechunker trueHdSampleRechunker = this.trueHdSampleRechunker;
            if (trueHdSampleRechunker != null) {
                trueHdSampleRechunker.reset();
            }
        }

        private byte[] getHdrStaticInfo() {
            if (this.primaryRChromaticityX == -1.0f || this.primaryRChromaticityY == -1.0f || this.primaryGChromaticityX == -1.0f || this.primaryGChromaticityY == -1.0f || this.primaryBChromaticityX == -1.0f || this.primaryBChromaticityY == -1.0f || this.whitePointChromaticityX == -1.0f || this.whitePointChromaticityY == -1.0f || this.maxMasteringLuminance == -1.0f || this.minMasteringLuminance == -1.0f) {
                return null;
            }
            byte[] bArr = new byte[25];
            ByteBuffer wrap = ByteBuffer.wrap(bArr);
            wrap.put((byte) 0);
            wrap.putShort((short) ((this.primaryRChromaticityX * 50000.0f) + 0.5f));
            wrap.putShort((short) ((this.primaryRChromaticityY * 50000.0f) + 0.5f));
            wrap.putShort((short) ((this.primaryGChromaticityX * 50000.0f) + 0.5f));
            wrap.putShort((short) ((this.primaryGChromaticityY * 50000.0f) + 0.5f));
            wrap.putShort((short) ((this.primaryBChromaticityX * 50000.0f) + 0.5f));
            wrap.putShort((short) ((this.primaryBChromaticityY * 50000.0f) + 0.5f));
            wrap.putShort((short) ((this.whitePointChromaticityX * 50000.0f) + 0.5f));
            wrap.putShort((short) ((this.whitePointChromaticityY * 50000.0f) + 0.5f));
            wrap.putShort((short) (this.maxMasteringLuminance + 0.5f));
            wrap.putShort((short) (this.minMasteringLuminance + 0.5f));
            wrap.putShort((short) this.maxContentLuminance);
            wrap.putShort((short) this.maxFrameAverageLuminance);
            return bArr;
        }

        private static Pair<String, List<byte[]>> parseFourCcPrivate(ParsableByteArray parsableByteArray) throws ParserException {
            try {
                parsableByteArray.skipBytes(16);
                long readLittleEndianUnsignedInt = parsableByteArray.readLittleEndianUnsignedInt();
                if (readLittleEndianUnsignedInt == 1482049860) {
                    return new Pair<>("video/3gpp", null);
                }
                if (readLittleEndianUnsignedInt == 826496599) {
                    byte[] bArr = parsableByteArray.data;
                    for (int position = parsableByteArray.getPosition() + 20; position < bArr.length - 4; position++) {
                        if (bArr[position] == 0 && bArr[position + 1] == 0 && bArr[position + 2] == 1 && bArr[position + 3] == 15) {
                            return new Pair<>("video/wvc1", Collections.singletonList(Arrays.copyOfRange(bArr, position, bArr.length)));
                        }
                    }
                    throw new ParserException("Failed to find FourCC VC1 initialization data");
                }
                Log.w("MatroskaExtractor", "Unknown FourCC. Setting mimeType to video/x-unknown");
                return new Pair<>("video/x-unknown", null);
            } catch (ArrayIndexOutOfBoundsException unused) {
                throw new ParserException("Error parsing FourCC private data");
            }
        }

        private static List<byte[]> parseVorbisCodecPrivate(byte[] bArr) throws ParserException {
            try {
                if (bArr[0] != 2) {
                    throw new ParserException("Error parsing vorbis codec private");
                }
                int i = 1;
                int i2 = 0;
                while (bArr[i] == -1) {
                    i2 += 255;
                    i++;
                }
                int i3 = i + 1;
                int i4 = i2 + bArr[i];
                int i5 = 0;
                while (bArr[i3] == -1) {
                    i5 += 255;
                    i3++;
                }
                int i6 = i3 + 1;
                int i7 = i5 + bArr[i3];
                if (bArr[i6] != 1) {
                    throw new ParserException("Error parsing vorbis codec private");
                }
                byte[] bArr2 = new byte[i4];
                System.arraycopy(bArr, i6, bArr2, 0, i4);
                int i8 = i6 + i4;
                if (bArr[i8] != 3) {
                    throw new ParserException("Error parsing vorbis codec private");
                }
                int i9 = i8 + i7;
                if (bArr[i9] != 5) {
                    throw new ParserException("Error parsing vorbis codec private");
                }
                byte[] bArr3 = new byte[bArr.length - i9];
                System.arraycopy(bArr, i9, bArr3, 0, bArr.length - i9);
                ArrayList arrayList = new ArrayList(2);
                arrayList.add(bArr2);
                arrayList.add(bArr3);
                return arrayList;
            } catch (ArrayIndexOutOfBoundsException unused) {
                throw new ParserException("Error parsing vorbis codec private");
            }
        }

        private static boolean parseMsAcmCodecPrivate(ParsableByteArray parsableByteArray) throws ParserException {
            try {
                int readLittleEndianUnsignedShort = parsableByteArray.readLittleEndianUnsignedShort();
                if (readLittleEndianUnsignedShort == 1) {
                    return true;
                }
                if (readLittleEndianUnsignedShort != 65534) {
                    return false;
                }
                parsableByteArray.setPosition(24);
                if (parsableByteArray.readLong() == MatroskaExtractor.WAVE_SUBFORMAT_PCM.getMostSignificantBits()) {
                    if (parsableByteArray.readLong() == MatroskaExtractor.WAVE_SUBFORMAT_PCM.getLeastSignificantBits()) {
                        return true;
                    }
                }
                return false;
            } catch (ArrayIndexOutOfBoundsException unused) {
                throw new ParserException("Error parsing MS/ACM codec private");
            }
        }
    }
}
