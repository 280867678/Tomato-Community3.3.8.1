package com.coremedia.iso.boxes.vodafone;

import com.coremedia.iso.IsoTypeReader;
import com.coremedia.iso.IsoTypeWriter;
import com.coremedia.iso.Utf8;
import com.googlecode.mp4parser.AbstractFullBox;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public class AlbumArtistBox extends AbstractFullBox {
    public static final String TYPE = "albr";
    private String albumArtist;
    private String language;

    public AlbumArtistBox() {
        super(TYPE);
    }

    public String getLanguage() {
        return this.language;
    }

    public String getAlbumArtist() {
        return this.albumArtist;
    }

    public void setLanguage(String str) {
        this.language = str;
    }

    public void setAlbumArtist(String str) {
        this.albumArtist = str;
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    protected long getContentSize() {
        return Utf8.utf8StringLengthInBytes(this.albumArtist) + 6 + 1;
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    public void _parseDetails(ByteBuffer byteBuffer) {
        parseVersionAndFlags(byteBuffer);
        this.language = IsoTypeReader.readIso639(byteBuffer);
        this.albumArtist = IsoTypeReader.readString(byteBuffer);
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    protected void getContent(ByteBuffer byteBuffer) {
        writeVersionAndFlags(byteBuffer);
        IsoTypeWriter.writeIso639(byteBuffer, this.language);
        byteBuffer.put(Utf8.convert(this.albumArtist));
        byteBuffer.put((byte) 0);
    }

    public String toString() {
        return "AlbumArtistBox[language=" + getLanguage() + ";albumArtist=" + getAlbumArtist() + "]";
    }
}
