package com.google.android.exoplayer2.extractor.wav;

import android.util.Log;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import java.io.IOException;

/* loaded from: classes.dex */
final class WavHeaderReader {
    /* JADX WARN: Removed duplicated region for block: B:31:0x00cd  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00ec  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static WavHeader peek(ExtractorInput extractorInput) throws IOException, InterruptedException {
        int pcmEncoding;
        Assertions.checkNotNull(extractorInput);
        ParsableByteArray parsableByteArray = new ParsableByteArray(16);
        if (ChunkHeader.peek(extractorInput, parsableByteArray).f1317id != Util.getIntegerCodeForString("RIFF")) {
            return null;
        }
        int i = 4;
        extractorInput.peekFully(parsableByteArray.data, 0, 4);
        parsableByteArray.setPosition(0);
        int readInt = parsableByteArray.readInt();
        if (readInt != Util.getIntegerCodeForString("WAVE")) {
            Log.e("WavHeaderReader", "Unsupported RIFF format: " + readInt);
            return null;
        }
        ChunkHeader peek = ChunkHeader.peek(extractorInput, parsableByteArray);
        while (peek.f1317id != Util.getIntegerCodeForString("fmt ")) {
            extractorInput.advancePeekPosition((int) peek.size);
            peek = ChunkHeader.peek(extractorInput, parsableByteArray);
        }
        Assertions.checkState(peek.size >= 16);
        extractorInput.peekFully(parsableByteArray.data, 0, 16);
        parsableByteArray.setPosition(0);
        int readLittleEndianUnsignedShort = parsableByteArray.readLittleEndianUnsignedShort();
        int readLittleEndianUnsignedShort2 = parsableByteArray.readLittleEndianUnsignedShort();
        int readLittleEndianUnsignedIntToInt = parsableByteArray.readLittleEndianUnsignedIntToInt();
        int readLittleEndianUnsignedIntToInt2 = parsableByteArray.readLittleEndianUnsignedIntToInt();
        int readLittleEndianUnsignedShort3 = parsableByteArray.readLittleEndianUnsignedShort();
        int readLittleEndianUnsignedShort4 = parsableByteArray.readLittleEndianUnsignedShort();
        int i2 = (readLittleEndianUnsignedShort2 * readLittleEndianUnsignedShort4) / 8;
        if (readLittleEndianUnsignedShort3 != i2) {
            throw new ParserException("Expected block alignment: " + i2 + "; got: " + readLittleEndianUnsignedShort3);
        }
        if (readLittleEndianUnsignedShort != 1) {
            if (readLittleEndianUnsignedShort == 3) {
                if (readLittleEndianUnsignedShort4 != 32) {
                    i = 0;
                }
                pcmEncoding = i;
                if (pcmEncoding != 0) {
                    Log.e("WavHeaderReader", "Unsupported WAV bit depth " + readLittleEndianUnsignedShort4 + " for type " + readLittleEndianUnsignedShort);
                    return null;
                }
                extractorInput.advancePeekPosition(((int) peek.size) - 16);
                return new WavHeader(readLittleEndianUnsignedShort2, readLittleEndianUnsignedIntToInt, readLittleEndianUnsignedIntToInt2, readLittleEndianUnsignedShort3, readLittleEndianUnsignedShort4, pcmEncoding);
            } else if (readLittleEndianUnsignedShort != 65534) {
                Log.e("WavHeaderReader", "Unsupported WAV format type: " + readLittleEndianUnsignedShort);
                return null;
            }
        }
        pcmEncoding = Util.getPcmEncoding(readLittleEndianUnsignedShort4);
        if (pcmEncoding != 0) {
        }
    }

    public static void skipToData(ExtractorInput extractorInput, WavHeader wavHeader) throws IOException, InterruptedException {
        Assertions.checkNotNull(extractorInput);
        Assertions.checkNotNull(wavHeader);
        extractorInput.resetPeekPosition();
        ParsableByteArray parsableByteArray = new ParsableByteArray(8);
        ChunkHeader peek = ChunkHeader.peek(extractorInput, parsableByteArray);
        while (peek.f1317id != Util.getIntegerCodeForString(AopConstants.APP_PROPERTIES_KEY)) {
            Log.w("WavHeaderReader", "Ignoring unknown WAV chunk: " + peek.f1317id);
            long j = peek.size + 8;
            if (peek.f1317id == Util.getIntegerCodeForString("RIFF")) {
                j = 12;
            }
            if (j > 2147483647L) {
                throw new ParserException("Chunk is too large (~2GB+) to skip; id: " + peek.f1317id);
            }
            extractorInput.skipFully((int) j);
            peek = ChunkHeader.peek(extractorInput, parsableByteArray);
        }
        extractorInput.skipFully(8);
        wavHeader.setDataBounds(extractorInput.getPosition(), peek.size);
    }

    /* loaded from: classes.dex */
    private static final class ChunkHeader {

        /* renamed from: id */
        public final int f1317id;
        public final long size;

        private ChunkHeader(int i, long j) {
            this.f1317id = i;
            this.size = j;
        }

        public static ChunkHeader peek(ExtractorInput extractorInput, ParsableByteArray parsableByteArray) throws IOException, InterruptedException {
            extractorInput.peekFully(parsableByteArray.data, 0, 8);
            parsableByteArray.setPosition(0);
            return new ChunkHeader(parsableByteArray.readInt(), parsableByteArray.readLittleEndianUnsignedInt());
        }
    }
}
