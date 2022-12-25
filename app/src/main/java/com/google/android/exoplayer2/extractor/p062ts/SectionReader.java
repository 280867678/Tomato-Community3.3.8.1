package com.google.android.exoplayer2.extractor.p062ts;

import android.support.p002v4.view.InputDeviceCompat;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.p062ts.TsPayloadReader;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.android.exoplayer2.util.Util;

/* renamed from: com.google.android.exoplayer2.extractor.ts.SectionReader */
/* loaded from: classes3.dex */
public final class SectionReader implements TsPayloadReader {
    private int bytesRead;
    private final SectionPayloadReader reader;
    private final ParsableByteArray sectionData = new ParsableByteArray(32);
    private boolean sectionSyntaxIndicator;
    private int totalSectionLength;
    private boolean waitingForPayloadStart;

    public SectionReader(SectionPayloadReader sectionPayloadReader) {
        this.reader = sectionPayloadReader;
    }

    @Override // com.google.android.exoplayer2.extractor.p062ts.TsPayloadReader
    public void init(TimestampAdjuster timestampAdjuster, ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator trackIdGenerator) {
        this.reader.init(timestampAdjuster, extractorOutput, trackIdGenerator);
        this.waitingForPayloadStart = true;
    }

    @Override // com.google.android.exoplayer2.extractor.p062ts.TsPayloadReader
    public void seek() {
        this.waitingForPayloadStart = true;
    }

    @Override // com.google.android.exoplayer2.extractor.p062ts.TsPayloadReader
    public void consume(ParsableByteArray parsableByteArray, boolean z) {
        int readUnsignedByte = z ? parsableByteArray.readUnsignedByte() + parsableByteArray.getPosition() : -1;
        if (this.waitingForPayloadStart) {
            if (!z) {
                return;
            }
            this.waitingForPayloadStart = false;
            parsableByteArray.setPosition(readUnsignedByte);
            this.bytesRead = 0;
        }
        while (parsableByteArray.bytesLeft() > 0) {
            int i = this.bytesRead;
            boolean z2 = true;
            if (i < 3) {
                if (i == 0) {
                    int readUnsignedByte2 = parsableByteArray.readUnsignedByte();
                    parsableByteArray.setPosition(parsableByteArray.getPosition() - 1);
                    if (readUnsignedByte2 == 255) {
                        this.waitingForPayloadStart = true;
                        return;
                    }
                }
                int min = Math.min(parsableByteArray.bytesLeft(), 3 - this.bytesRead);
                parsableByteArray.readBytes(this.sectionData.data, this.bytesRead, min);
                this.bytesRead += min;
                if (this.bytesRead == 3) {
                    this.sectionData.reset(3);
                    this.sectionData.skipBytes(1);
                    int readUnsignedByte3 = this.sectionData.readUnsignedByte();
                    int readUnsignedByte4 = this.sectionData.readUnsignedByte();
                    if ((readUnsignedByte3 & 128) == 0) {
                        z2 = false;
                    }
                    this.sectionSyntaxIndicator = z2;
                    this.totalSectionLength = (((readUnsignedByte3 & 15) << 8) | readUnsignedByte4) + 3;
                    int capacity = this.sectionData.capacity();
                    int i2 = this.totalSectionLength;
                    if (capacity < i2) {
                        ParsableByteArray parsableByteArray2 = this.sectionData;
                        byte[] bArr = parsableByteArray2.data;
                        parsableByteArray2.reset(Math.min((int) InputDeviceCompat.SOURCE_TOUCHSCREEN, Math.max(i2, bArr.length * 2)));
                        System.arraycopy(bArr, 0, this.sectionData.data, 0, 3);
                    }
                }
            } else {
                int min2 = Math.min(parsableByteArray.bytesLeft(), this.totalSectionLength - this.bytesRead);
                parsableByteArray.readBytes(this.sectionData.data, this.bytesRead, min2);
                this.bytesRead += min2;
                int i3 = this.bytesRead;
                int i4 = this.totalSectionLength;
                if (i3 != i4) {
                    continue;
                } else {
                    if (this.sectionSyntaxIndicator) {
                        if (Util.crc(this.sectionData.data, 0, i4, -1) != 0) {
                            this.waitingForPayloadStart = true;
                            return;
                        }
                        this.sectionData.reset(this.totalSectionLength - 4);
                    } else {
                        this.sectionData.reset(i4);
                    }
                    this.reader.consume(this.sectionData);
                    this.bytesRead = 0;
                }
            }
        }
    }
}
