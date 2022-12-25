package com.coremedia.iso.boxes.fragment;

import com.coremedia.iso.IsoTypeReader;
import com.coremedia.iso.IsoTypeWriter;
import com.googlecode.mp4parser.AbstractFullBox;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public class TrackFragmentHeaderBox extends AbstractFullBox {
    public static final String TYPE = "tfhd";
    private boolean defaultBaseIsMoof;
    private SampleFlags defaultSampleFlags;
    private boolean durationIsEmpty;
    private long sampleDescriptionIndex;
    private long trackId;
    private long baseDataOffset = -1;
    private long defaultSampleDuration = -1;
    private long defaultSampleSize = -1;

    public TrackFragmentHeaderBox() {
        super(TYPE);
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    protected long getContentSize() {
        int flags = getFlags();
        long j = (flags & 1) == 1 ? 16L : 8L;
        if ((flags & 2) == 2) {
            j += 4;
        }
        if ((flags & 8) == 8) {
            j += 4;
        }
        if ((flags & 16) == 16) {
            j += 4;
        }
        return (flags & 32) == 32 ? j + 4 : j;
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    protected void getContent(ByteBuffer byteBuffer) {
        writeVersionAndFlags(byteBuffer);
        IsoTypeWriter.writeUInt32(byteBuffer, this.trackId);
        if ((getFlags() & 1) == 1) {
            IsoTypeWriter.writeUInt64(byteBuffer, getBaseDataOffset());
        }
        if ((getFlags() & 2) == 2) {
            IsoTypeWriter.writeUInt32(byteBuffer, getSampleDescriptionIndex());
        }
        if ((getFlags() & 8) == 8) {
            IsoTypeWriter.writeUInt32(byteBuffer, getDefaultSampleDuration());
        }
        if ((getFlags() & 16) == 16) {
            IsoTypeWriter.writeUInt32(byteBuffer, getDefaultSampleSize());
        }
        if ((getFlags() & 32) == 32) {
            this.defaultSampleFlags.getContent(byteBuffer);
        }
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    public void _parseDetails(ByteBuffer byteBuffer) {
        parseVersionAndFlags(byteBuffer);
        this.trackId = IsoTypeReader.readUInt32(byteBuffer);
        if ((getFlags() & 1) == 1) {
            this.baseDataOffset = IsoTypeReader.readUInt64(byteBuffer);
        }
        if ((getFlags() & 2) == 2) {
            this.sampleDescriptionIndex = IsoTypeReader.readUInt32(byteBuffer);
        }
        if ((getFlags() & 8) == 8) {
            this.defaultSampleDuration = IsoTypeReader.readUInt32(byteBuffer);
        }
        if ((getFlags() & 16) == 16) {
            this.defaultSampleSize = IsoTypeReader.readUInt32(byteBuffer);
        }
        if ((getFlags() & 32) == 32) {
            this.defaultSampleFlags = new SampleFlags(byteBuffer);
        }
        if ((getFlags() & 65536) == 65536) {
            this.durationIsEmpty = true;
        }
        if ((getFlags() & 131072) == 131072) {
            this.defaultBaseIsMoof = true;
        }
    }

    public boolean hasBaseDataOffset() {
        return (getFlags() & 1) != 0;
    }

    public boolean hasSampleDescriptionIndex() {
        return (getFlags() & 2) != 0;
    }

    public boolean hasDefaultSampleDuration() {
        return (getFlags() & 8) != 0;
    }

    public boolean hasDefaultSampleSize() {
        return (getFlags() & 16) != 0;
    }

    public boolean hasDefaultSampleFlags() {
        return (getFlags() & 32) != 0;
    }

    public long getTrackId() {
        return this.trackId;
    }

    public long getBaseDataOffset() {
        return this.baseDataOffset;
    }

    public long getSampleDescriptionIndex() {
        return this.sampleDescriptionIndex;
    }

    public long getDefaultSampleDuration() {
        return this.defaultSampleDuration;
    }

    public long getDefaultSampleSize() {
        return this.defaultSampleSize;
    }

    public SampleFlags getDefaultSampleFlags() {
        return this.defaultSampleFlags;
    }

    public boolean isDurationIsEmpty() {
        return this.durationIsEmpty;
    }

    public boolean isDefaultBaseIsMoof() {
        return this.defaultBaseIsMoof;
    }

    public void setTrackId(long j) {
        this.trackId = j;
    }

    public void setBaseDataOffset(long j) {
        if (j == -1) {
            setFlags(getFlags() & 2147483646);
        } else {
            setFlags(getFlags() | 1);
        }
        this.baseDataOffset = j;
    }

    public void setSampleDescriptionIndex(long j) {
        if (j == -1) {
            setFlags(getFlags() & 2147483645);
        } else {
            setFlags(getFlags() | 2);
        }
        this.sampleDescriptionIndex = j;
    }

    public void setDefaultSampleDuration(long j) {
        setFlags(getFlags() | 8);
        this.defaultSampleDuration = j;
    }

    public void setDefaultSampleSize(long j) {
        setFlags(getFlags() | 16);
        this.defaultSampleSize = j;
    }

    public void setDefaultSampleFlags(SampleFlags sampleFlags) {
        setFlags(getFlags() | 32);
        this.defaultSampleFlags = sampleFlags;
    }

    public void setDurationIsEmpty(boolean z) {
        setFlags(getFlags() | 65536);
        this.durationIsEmpty = z;
    }

    public void setDefaultBaseIsMoof(boolean z) {
        setFlags(getFlags() | 131072);
        this.defaultBaseIsMoof = z;
    }

    public String toString() {
        return "TrackFragmentHeaderBox{trackId=" + this.trackId + ", baseDataOffset=" + this.baseDataOffset + ", sampleDescriptionIndex=" + this.sampleDescriptionIndex + ", defaultSampleDuration=" + this.defaultSampleDuration + ", defaultSampleSize=" + this.defaultSampleSize + ", defaultSampleFlags=" + this.defaultSampleFlags + ", durationIsEmpty=" + this.durationIsEmpty + ", defaultBaseIsMoof=" + this.defaultBaseIsMoof + '}';
    }
}
