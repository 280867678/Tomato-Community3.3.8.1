package com.coremedia.iso.boxes.sampleentry;

import android.support.p002v4.media.session.PlaybackStateCompat;
import com.coremedia.iso.BoxParser;
import com.coremedia.iso.IsoTypeReader;
import com.coremedia.iso.IsoTypeWriter;
import com.googlecode.mp4parser.DataSource;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.util.Arrays;

/* loaded from: classes2.dex */
public class TextSampleEntry extends AbstractSampleEntry {
    public static final String TYPE1 = "tx3g";
    public static final String TYPE_ENCRYPTED = "enct";
    private int[] backgroundColorRgba;
    private BoxRecord boxRecord;
    private long displayFlags;
    private int horizontalJustification;
    private StyleRecord styleRecord;
    private int verticalJustification;

    @Override // com.googlecode.mp4parser.BasicContainer
    public String toString() {
        return "TextSampleEntry";
    }

    public TextSampleEntry() {
        super(TYPE1);
        this.backgroundColorRgba = new int[4];
        this.boxRecord = new BoxRecord();
        this.styleRecord = new StyleRecord();
    }

    public TextSampleEntry(String str) {
        super(str);
        this.backgroundColorRgba = new int[4];
        this.boxRecord = new BoxRecord();
        this.styleRecord = new StyleRecord();
    }

    public void setType(String str) {
        this.type = str;
    }

    @Override // com.coremedia.iso.boxes.sampleentry.AbstractSampleEntry, com.googlecode.mp4parser.AbstractContainerBox, com.coremedia.iso.boxes.Box
    public void parse(DataSource dataSource, ByteBuffer byteBuffer, long j, BoxParser boxParser) throws IOException {
        ByteBuffer allocate = ByteBuffer.allocate(38);
        dataSource.read(allocate);
        allocate.position(6);
        this.dataReferenceIndex = IsoTypeReader.readUInt16(allocate);
        this.displayFlags = IsoTypeReader.readUInt32(allocate);
        this.horizontalJustification = IsoTypeReader.readUInt8(allocate);
        this.verticalJustification = IsoTypeReader.readUInt8(allocate);
        this.backgroundColorRgba = new int[4];
        this.backgroundColorRgba[0] = IsoTypeReader.readUInt8(allocate);
        this.backgroundColorRgba[1] = IsoTypeReader.readUInt8(allocate);
        this.backgroundColorRgba[2] = IsoTypeReader.readUInt8(allocate);
        this.backgroundColorRgba[3] = IsoTypeReader.readUInt8(allocate);
        this.boxRecord = new BoxRecord();
        this.boxRecord.parse(allocate);
        this.styleRecord = new StyleRecord();
        this.styleRecord.parse(allocate);
        initContainer(dataSource, j - 38, boxParser);
    }

    @Override // com.coremedia.iso.boxes.sampleentry.AbstractSampleEntry, com.googlecode.mp4parser.AbstractContainerBox, com.coremedia.iso.boxes.Box
    public void getBox(WritableByteChannel writableByteChannel) throws IOException {
        writableByteChannel.write(getHeader());
        ByteBuffer allocate = ByteBuffer.allocate(38);
        allocate.position(6);
        IsoTypeWriter.writeUInt16(allocate, this.dataReferenceIndex);
        IsoTypeWriter.writeUInt32(allocate, this.displayFlags);
        IsoTypeWriter.writeUInt8(allocate, this.horizontalJustification);
        IsoTypeWriter.writeUInt8(allocate, this.verticalJustification);
        IsoTypeWriter.writeUInt8(allocate, this.backgroundColorRgba[0]);
        IsoTypeWriter.writeUInt8(allocate, this.backgroundColorRgba[1]);
        IsoTypeWriter.writeUInt8(allocate, this.backgroundColorRgba[2]);
        IsoTypeWriter.writeUInt8(allocate, this.backgroundColorRgba[3]);
        this.boxRecord.getContent(allocate);
        this.styleRecord.getContent(allocate);
        writableByteChannel.write((ByteBuffer) allocate.rewind());
        writeContainer(writableByteChannel);
    }

    public BoxRecord getBoxRecord() {
        return this.boxRecord;
    }

    public void setBoxRecord(BoxRecord boxRecord) {
        this.boxRecord = boxRecord;
    }

    public StyleRecord getStyleRecord() {
        return this.styleRecord;
    }

    public void setStyleRecord(StyleRecord styleRecord) {
        this.styleRecord = styleRecord;
    }

    public boolean isScrollIn() {
        return (this.displayFlags & 32) == 32;
    }

    public void setScrollIn(boolean z) {
        if (z) {
            this.displayFlags |= 32;
        } else {
            this.displayFlags &= -33;
        }
    }

    public boolean isScrollOut() {
        return (this.displayFlags & 64) == 64;
    }

    public void setScrollOut(boolean z) {
        if (z) {
            this.displayFlags |= 64;
        } else {
            this.displayFlags &= -65;
        }
    }

    public boolean isScrollDirection() {
        return (this.displayFlags & 384) == 384;
    }

    public void setScrollDirection(boolean z) {
        if (z) {
            this.displayFlags |= 384;
        } else {
            this.displayFlags &= -385;
        }
    }

    public boolean isContinuousKaraoke() {
        return (this.displayFlags & 2048) == 2048;
    }

    public void setContinuousKaraoke(boolean z) {
        if (z) {
            this.displayFlags |= 2048;
        } else {
            this.displayFlags &= -2049;
        }
    }

    public boolean isWriteTextVertically() {
        return (this.displayFlags & 131072) == 131072;
    }

    public void setWriteTextVertically(boolean z) {
        if (z) {
            this.displayFlags |= 131072;
        } else {
            this.displayFlags &= -131073;
        }
    }

    public boolean isFillTextRegion() {
        return (this.displayFlags & PlaybackStateCompat.ACTION_SET_REPEAT_MODE) == PlaybackStateCompat.ACTION_SET_REPEAT_MODE;
    }

    public void setFillTextRegion(boolean z) {
        if (z) {
            this.displayFlags |= PlaybackStateCompat.ACTION_SET_REPEAT_MODE;
        } else {
            this.displayFlags &= -262145;
        }
    }

    public int getHorizontalJustification() {
        return this.horizontalJustification;
    }

    public void setHorizontalJustification(int i) {
        this.horizontalJustification = i;
    }

    public int getVerticalJustification() {
        return this.verticalJustification;
    }

    public void setVerticalJustification(int i) {
        this.verticalJustification = i;
    }

    public int[] getBackgroundColorRgba() {
        return this.backgroundColorRgba;
    }

    public void setBackgroundColorRgba(int[] iArr) {
        this.backgroundColorRgba = iArr;
    }

    /* loaded from: classes2.dex */
    public static class BoxRecord {
        int bottom;
        int left;
        int right;

        /* renamed from: top  reason: collision with root package name */
        int f6092top;

        public int getSize() {
            return 8;
        }

        public BoxRecord() {
        }

        public BoxRecord(int i, int i2, int i3, int i4) {
            this.f6092top = i;
            this.left = i2;
            this.bottom = i3;
            this.right = i4;
        }

        public void parse(ByteBuffer byteBuffer) {
            this.f6092top = IsoTypeReader.readUInt16(byteBuffer);
            this.left = IsoTypeReader.readUInt16(byteBuffer);
            this.bottom = IsoTypeReader.readUInt16(byteBuffer);
            this.right = IsoTypeReader.readUInt16(byteBuffer);
        }

        public void getContent(ByteBuffer byteBuffer) {
            IsoTypeWriter.writeUInt16(byteBuffer, this.f6092top);
            IsoTypeWriter.writeUInt16(byteBuffer, this.left);
            IsoTypeWriter.writeUInt16(byteBuffer, this.bottom);
            IsoTypeWriter.writeUInt16(byteBuffer, this.right);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || BoxRecord.class != obj.getClass()) {
                return false;
            }
            BoxRecord boxRecord = (BoxRecord) obj;
            return this.bottom == boxRecord.bottom && this.left == boxRecord.left && this.right == boxRecord.right && this.f6092top == boxRecord.f6092top;
        }

        public int hashCode() {
            return (((((this.f6092top * 31) + this.left) * 31) + this.bottom) * 31) + this.right;
        }
    }

    /* loaded from: classes2.dex */
    public static class StyleRecord {
        int endChar;
        int faceStyleFlags;
        int fontId;
        int fontSize;
        int startChar;
        int[] textColor;

        public int getSize() {
            return 12;
        }

        public StyleRecord() {
            this.textColor = new int[]{255, 255, 255, 255};
        }

        public StyleRecord(int i, int i2, int i3, int i4, int i5, int[] iArr) {
            this.textColor = new int[]{255, 255, 255, 255};
            this.startChar = i;
            this.endChar = i2;
            this.fontId = i3;
            this.faceStyleFlags = i4;
            this.fontSize = i5;
            this.textColor = iArr;
        }

        public void parse(ByteBuffer byteBuffer) {
            this.startChar = IsoTypeReader.readUInt16(byteBuffer);
            this.endChar = IsoTypeReader.readUInt16(byteBuffer);
            this.fontId = IsoTypeReader.readUInt16(byteBuffer);
            this.faceStyleFlags = IsoTypeReader.readUInt8(byteBuffer);
            this.fontSize = IsoTypeReader.readUInt8(byteBuffer);
            this.textColor = new int[4];
            this.textColor[0] = IsoTypeReader.readUInt8(byteBuffer);
            this.textColor[1] = IsoTypeReader.readUInt8(byteBuffer);
            this.textColor[2] = IsoTypeReader.readUInt8(byteBuffer);
            this.textColor[3] = IsoTypeReader.readUInt8(byteBuffer);
        }

        public void getContent(ByteBuffer byteBuffer) {
            IsoTypeWriter.writeUInt16(byteBuffer, this.startChar);
            IsoTypeWriter.writeUInt16(byteBuffer, this.endChar);
            IsoTypeWriter.writeUInt16(byteBuffer, this.fontId);
            IsoTypeWriter.writeUInt8(byteBuffer, this.faceStyleFlags);
            IsoTypeWriter.writeUInt8(byteBuffer, this.fontSize);
            IsoTypeWriter.writeUInt8(byteBuffer, this.textColor[0]);
            IsoTypeWriter.writeUInt8(byteBuffer, this.textColor[1]);
            IsoTypeWriter.writeUInt8(byteBuffer, this.textColor[2]);
            IsoTypeWriter.writeUInt8(byteBuffer, this.textColor[3]);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || StyleRecord.class != obj.getClass()) {
                return false;
            }
            StyleRecord styleRecord = (StyleRecord) obj;
            return this.endChar == styleRecord.endChar && this.faceStyleFlags == styleRecord.faceStyleFlags && this.fontId == styleRecord.fontId && this.fontSize == styleRecord.fontSize && this.startChar == styleRecord.startChar && Arrays.equals(this.textColor, styleRecord.textColor);
        }

        public int hashCode() {
            int i = ((((((((this.startChar * 31) + this.endChar) * 31) + this.fontId) * 31) + this.faceStyleFlags) * 31) + this.fontSize) * 31;
            int[] iArr = this.textColor;
            return i + (iArr != null ? Arrays.hashCode(iArr) : 0);
        }
    }

    @Override // com.googlecode.mp4parser.AbstractContainerBox, com.coremedia.iso.boxes.Box
    public long getSize() {
        long containerSize = getContainerSize() + 38;
        return containerSize + ((this.largeBox || containerSize >= 4294967296L) ? 16 : 8);
    }
}
