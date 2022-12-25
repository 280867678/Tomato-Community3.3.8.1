package com.googlecode.mp4parser.boxes.dece;

import com.coremedia.iso.IsoTypeReader;
import com.coremedia.iso.IsoTypeWriter;
import com.coremedia.iso.Utf8;
import com.googlecode.mp4parser.AbstractFullBox;
import java.nio.ByteBuffer;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class ContentInformationBox extends AbstractFullBox {
    public static final String TYPE = "cinf";
    String codecs;
    String languages;
    String mimeSubtypeName;
    String profileLevelIdc;
    String protection;
    Map<String, String> brandEntries = new LinkedHashMap();
    Map<String, String> idEntries = new LinkedHashMap();

    public ContentInformationBox() {
        super(TYPE);
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    protected long getContentSize() {
        long utf8StringLengthInBytes = Utf8.utf8StringLengthInBytes(this.mimeSubtypeName) + 1 + 4 + Utf8.utf8StringLengthInBytes(this.profileLevelIdc) + 1 + Utf8.utf8StringLengthInBytes(this.codecs) + 1 + Utf8.utf8StringLengthInBytes(this.protection) + 1 + Utf8.utf8StringLengthInBytes(this.languages) + 1 + 1;
        for (Map.Entry<String, String> entry : this.brandEntries.entrySet()) {
            utf8StringLengthInBytes = utf8StringLengthInBytes + Utf8.utf8StringLengthInBytes(entry.getKey()) + 1 + Utf8.utf8StringLengthInBytes(entry.getValue()) + 1;
        }
        long j = utf8StringLengthInBytes + 1;
        for (Map.Entry<String, String> entry2 : this.idEntries.entrySet()) {
            j = j + Utf8.utf8StringLengthInBytes(entry2.getKey()) + 1 + Utf8.utf8StringLengthInBytes(entry2.getValue()) + 1;
        }
        return j;
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    protected void getContent(ByteBuffer byteBuffer) {
        writeVersionAndFlags(byteBuffer);
        IsoTypeWriter.writeZeroTermUtf8String(byteBuffer, this.mimeSubtypeName);
        IsoTypeWriter.writeZeroTermUtf8String(byteBuffer, this.profileLevelIdc);
        IsoTypeWriter.writeZeroTermUtf8String(byteBuffer, this.codecs);
        IsoTypeWriter.writeZeroTermUtf8String(byteBuffer, this.protection);
        IsoTypeWriter.writeZeroTermUtf8String(byteBuffer, this.languages);
        IsoTypeWriter.writeUInt8(byteBuffer, this.brandEntries.size());
        for (Map.Entry<String, String> entry : this.brandEntries.entrySet()) {
            IsoTypeWriter.writeZeroTermUtf8String(byteBuffer, entry.getKey());
            IsoTypeWriter.writeZeroTermUtf8String(byteBuffer, entry.getValue());
        }
        IsoTypeWriter.writeUInt8(byteBuffer, this.idEntries.size());
        for (Map.Entry<String, String> entry2 : this.idEntries.entrySet()) {
            IsoTypeWriter.writeZeroTermUtf8String(byteBuffer, entry2.getKey());
            IsoTypeWriter.writeZeroTermUtf8String(byteBuffer, entry2.getValue());
        }
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    protected void _parseDetails(ByteBuffer byteBuffer) {
        parseVersionAndFlags(byteBuffer);
        this.mimeSubtypeName = IsoTypeReader.readString(byteBuffer);
        this.profileLevelIdc = IsoTypeReader.readString(byteBuffer);
        this.codecs = IsoTypeReader.readString(byteBuffer);
        this.protection = IsoTypeReader.readString(byteBuffer);
        this.languages = IsoTypeReader.readString(byteBuffer);
        int readUInt8 = IsoTypeReader.readUInt8(byteBuffer);
        while (true) {
            int i = readUInt8 - 1;
            if (readUInt8 <= 0) {
                break;
            }
            this.brandEntries.put(IsoTypeReader.readString(byteBuffer), IsoTypeReader.readString(byteBuffer));
            readUInt8 = i;
        }
        int readUInt82 = IsoTypeReader.readUInt8(byteBuffer);
        while (true) {
            int i2 = readUInt82 - 1;
            if (readUInt82 > 0) {
                this.idEntries.put(IsoTypeReader.readString(byteBuffer), IsoTypeReader.readString(byteBuffer));
                readUInt82 = i2;
            } else {
                return;
            }
        }
    }

    /* loaded from: classes3.dex */
    public static class BrandEntry {
        String iso_brand;
        String version;

        public BrandEntry(String str, String str2) {
            this.iso_brand = str;
            this.version = str2;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || BrandEntry.class != obj.getClass()) {
                return false;
            }
            BrandEntry brandEntry = (BrandEntry) obj;
            String str = this.iso_brand;
            if (str == null ? brandEntry.iso_brand != null : !str.equals(brandEntry.iso_brand)) {
                return false;
            }
            String str2 = this.version;
            String str3 = brandEntry.version;
            return str2 == null ? str3 == null : str2.equals(str3);
        }

        public int hashCode() {
            String str = this.iso_brand;
            int i = 0;
            int hashCode = (str != null ? str.hashCode() : 0) * 31;
            String str2 = this.version;
            if (str2 != null) {
                i = str2.hashCode();
            }
            return hashCode + i;
        }
    }

    public String getMimeSubtypeName() {
        return this.mimeSubtypeName;
    }

    public void setMimeSubtypeName(String str) {
        this.mimeSubtypeName = str;
    }

    public String getProfileLevelIdc() {
        return this.profileLevelIdc;
    }

    public void setProfileLevelIdc(String str) {
        this.profileLevelIdc = str;
    }

    public String getCodecs() {
        return this.codecs;
    }

    public void setCodecs(String str) {
        this.codecs = str;
    }

    public String getProtection() {
        return this.protection;
    }

    public void setProtection(String str) {
        this.protection = str;
    }

    public String getLanguages() {
        return this.languages;
    }

    public void setLanguages(String str) {
        this.languages = str;
    }

    public Map<String, String> getBrandEntries() {
        return this.brandEntries;
    }

    public void setBrandEntries(Map<String, String> map) {
        this.brandEntries = map;
    }

    public Map<String, String> getIdEntries() {
        return this.idEntries;
    }

    public void setIdEntries(Map<String, String> map) {
        this.idEntries = map;
    }
}
