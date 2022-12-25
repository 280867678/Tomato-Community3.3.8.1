package com.google.android.exoplayer2.extractor.ogg;

import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.io.EOFException;
import java.io.IOException;

/* loaded from: classes2.dex */
final class OggPageHeader {
    private static final int TYPE_OGGS = Util.getIntegerCodeForString("OggS");
    public int bodySize;
    public long granulePosition;
    public int headerSize;
    public int pageSegmentCount;
    public int revision;
    public int type;
    public final int[] laces = new int[255];
    private final ParsableByteArray scratch = new ParsableByteArray(255);

    public void reset() {
        this.revision = 0;
        this.type = 0;
        this.granulePosition = 0L;
        this.pageSegmentCount = 0;
        this.headerSize = 0;
        this.bodySize = 0;
    }

    public boolean populate(ExtractorInput extractorInput, boolean z) throws IOException, InterruptedException {
        this.scratch.reset();
        reset();
        if (!(extractorInput.getLength() == -1 || extractorInput.getLength() - extractorInput.getPeekPosition() >= 27) || !extractorInput.peekFully(this.scratch.data, 0, 27, true)) {
            if (!z) {
                throw new EOFException();
            }
            return false;
        } else if (this.scratch.readUnsignedInt() != TYPE_OGGS) {
            if (!z) {
                throw new ParserException("expected OggS capture pattern at begin of page");
            }
            return false;
        } else {
            this.revision = this.scratch.readUnsignedByte();
            if (this.revision != 0) {
                if (!z) {
                    throw new ParserException("unsupported bit stream revision");
                }
                return false;
            }
            this.type = this.scratch.readUnsignedByte();
            this.granulePosition = this.scratch.readLittleEndianLong();
            this.scratch.readLittleEndianUnsignedInt();
            this.scratch.readLittleEndianUnsignedInt();
            this.scratch.readLittleEndianUnsignedInt();
            this.pageSegmentCount = this.scratch.readUnsignedByte();
            this.headerSize = this.pageSegmentCount + 27;
            this.scratch.reset();
            extractorInput.peekFully(this.scratch.data, 0, this.pageSegmentCount);
            for (int i = 0; i < this.pageSegmentCount; i++) {
                this.laces[i] = this.scratch.readUnsignedByte();
                this.bodySize += this.laces[i];
            }
            return true;
        }
    }
}
