package com.google.android.exoplayer2.extractor.p062ts;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.audio.Ac3Util;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.p062ts.TsPayloadReader;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;

/* renamed from: com.google.android.exoplayer2.extractor.ts.Ac3Reader */
/* loaded from: classes3.dex */
public final class Ac3Reader implements ElementaryStreamReader {
    private int bytesRead;
    private Format format;
    private final ParsableBitArray headerScratchBits;
    private final ParsableByteArray headerScratchBytes;
    private final String language;
    private boolean lastByteWas0B;
    private TrackOutput output;
    private long sampleDurationUs;
    private int sampleSize;
    private int state;
    private long timeUs;
    private String trackFormatId;

    @Override // com.google.android.exoplayer2.extractor.p062ts.ElementaryStreamReader
    public void packetFinished() {
    }

    public Ac3Reader() {
        this(null);
    }

    public Ac3Reader(String str) {
        this.headerScratchBits = new ParsableBitArray(new byte[128]);
        this.headerScratchBytes = new ParsableByteArray(this.headerScratchBits.data);
        this.state = 0;
        this.language = str;
    }

    @Override // com.google.android.exoplayer2.extractor.p062ts.ElementaryStreamReader
    public void seek() {
        this.state = 0;
        this.bytesRead = 0;
        this.lastByteWas0B = false;
    }

    @Override // com.google.android.exoplayer2.extractor.p062ts.ElementaryStreamReader
    public void createTracks(ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator trackIdGenerator) {
        trackIdGenerator.generateNewId();
        this.trackFormatId = trackIdGenerator.getFormatId();
        this.output = extractorOutput.track(trackIdGenerator.getTrackId(), 1);
    }

    @Override // com.google.android.exoplayer2.extractor.p062ts.ElementaryStreamReader
    public void packetStarted(long j, boolean z) {
        this.timeUs = j;
    }

    @Override // com.google.android.exoplayer2.extractor.p062ts.ElementaryStreamReader
    public void consume(ParsableByteArray parsableByteArray) {
        while (parsableByteArray.bytesLeft() > 0) {
            int i = this.state;
            if (i != 0) {
                if (i != 1) {
                    if (i == 2) {
                        int min = Math.min(parsableByteArray.bytesLeft(), this.sampleSize - this.bytesRead);
                        this.output.sampleData(parsableByteArray, min);
                        this.bytesRead += min;
                        int i2 = this.bytesRead;
                        int i3 = this.sampleSize;
                        if (i2 == i3) {
                            this.output.sampleMetadata(this.timeUs, 1, i3, 0, null);
                            this.timeUs += this.sampleDurationUs;
                            this.state = 0;
                        }
                    }
                } else if (continueRead(parsableByteArray, this.headerScratchBytes.data, 128)) {
                    parseHeader();
                    this.headerScratchBytes.setPosition(0);
                    this.output.sampleData(this.headerScratchBytes, 128);
                    this.state = 2;
                }
            } else if (skipToNextSync(parsableByteArray)) {
                this.state = 1;
                byte[] bArr = this.headerScratchBytes.data;
                bArr[0] = 11;
                bArr[1] = 119;
                this.bytesRead = 2;
            }
        }
    }

    private boolean continueRead(ParsableByteArray parsableByteArray, byte[] bArr, int i) {
        int min = Math.min(parsableByteArray.bytesLeft(), i - this.bytesRead);
        parsableByteArray.readBytes(bArr, this.bytesRead, min);
        this.bytesRead += min;
        return this.bytesRead == i;
    }

    private boolean skipToNextSync(ParsableByteArray parsableByteArray) {
        while (true) {
            boolean z = false;
            if (parsableByteArray.bytesLeft() > 0) {
                if (!this.lastByteWas0B) {
                    if (parsableByteArray.readUnsignedByte() == 11) {
                        z = true;
                    }
                    this.lastByteWas0B = z;
                } else {
                    int readUnsignedByte = parsableByteArray.readUnsignedByte();
                    if (readUnsignedByte == 119) {
                        this.lastByteWas0B = false;
                        return true;
                    }
                    if (readUnsignedByte == 11) {
                        z = true;
                    }
                    this.lastByteWas0B = z;
                }
            } else {
                return false;
            }
        }
    }

    private void parseHeader() {
        this.headerScratchBits.setPosition(0);
        Ac3Util.SyncFrameInfo parseAc3SyncframeInfo = Ac3Util.parseAc3SyncframeInfo(this.headerScratchBits);
        Format format = this.format;
        if (format == null || parseAc3SyncframeInfo.channelCount != format.channelCount || parseAc3SyncframeInfo.sampleRate != format.sampleRate || parseAc3SyncframeInfo.mimeType != format.sampleMimeType) {
            this.format = Format.createAudioSampleFormat(this.trackFormatId, parseAc3SyncframeInfo.mimeType, null, -1, -1, parseAc3SyncframeInfo.channelCount, parseAc3SyncframeInfo.sampleRate, null, null, 0, this.language);
            this.output.format(this.format);
        }
        this.sampleSize = parseAc3SyncframeInfo.frameSize;
        this.sampleDurationUs = (parseAc3SyncframeInfo.sampleCount * 1000000) / this.format.sampleRate;
    }
}
