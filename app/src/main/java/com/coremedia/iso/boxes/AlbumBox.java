package com.coremedia.iso.boxes;

import com.coremedia.iso.IsoTypeReader;
import com.coremedia.iso.IsoTypeWriter;
import com.coremedia.iso.Utf8;
import com.googlecode.mp4parser.AbstractFullBox;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public class AlbumBox extends AbstractFullBox {
    public static final String TYPE = "albm";
    private String albumTitle;
    private String language;
    private int trackNumber;

    public AlbumBox() {
        super(TYPE);
    }

    public String getLanguage() {
        return this.language;
    }

    public String getAlbumTitle() {
        return this.albumTitle;
    }

    public int getTrackNumber() {
        return this.trackNumber;
    }

    public void setLanguage(String str) {
        this.language = str;
    }

    public void setAlbumTitle(String str) {
        this.albumTitle = str;
    }

    public void setTrackNumber(int i) {
        this.trackNumber = i;
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    protected long getContentSize() {
        int i = 1;
        int utf8StringLengthInBytes = Utf8.utf8StringLengthInBytes(this.albumTitle) + 6 + 1;
        if (this.trackNumber == -1) {
            i = 0;
        }
        return utf8StringLengthInBytes + i;
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    public void _parseDetails(ByteBuffer byteBuffer) {
        parseVersionAndFlags(byteBuffer);
        this.language = IsoTypeReader.readIso639(byteBuffer);
        this.albumTitle = IsoTypeReader.readString(byteBuffer);
        if (byteBuffer.remaining() > 0) {
            this.trackNumber = IsoTypeReader.readUInt8(byteBuffer);
        } else {
            this.trackNumber = -1;
        }
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    protected void getContent(ByteBuffer byteBuffer) {
        writeVersionAndFlags(byteBuffer);
        IsoTypeWriter.writeIso639(byteBuffer, this.language);
        byteBuffer.put(Utf8.convert(this.albumTitle));
        byteBuffer.put((byte) 0);
        int i = this.trackNumber;
        if (i != -1) {
            IsoTypeWriter.writeUInt8(byteBuffer, i);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AlbumBox[language=");
        sb.append(getLanguage());
        sb.append(";");
        sb.append("albumTitle=");
        sb.append(getAlbumTitle());
        if (this.trackNumber >= 0) {
            sb.append(";trackNumber=");
            sb.append(getTrackNumber());
        }
        sb.append("]");
        return sb.toString();
    }
}
