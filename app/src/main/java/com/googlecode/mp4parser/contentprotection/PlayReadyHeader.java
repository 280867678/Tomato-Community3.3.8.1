package com.googlecode.mp4parser.contentprotection;

import com.coremedia.iso.IsoTypeReader;
import com.coremedia.iso.IsoTypeWriter;
import com.googlecode.mp4parser.boxes.piff.ProtectionSpecificHeader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/* loaded from: classes3.dex */
public class PlayReadyHeader extends ProtectionSpecificHeader {
    public static UUID PROTECTION_SYSTEM_ID = UUID.fromString("9A04F079-9840-4286-AB92-E65BE0885F95");
    private long length;
    private List<PlayReadyRecord> records;

    static {
        ProtectionSpecificHeader.uuidRegistry.put(PROTECTION_SYSTEM_ID, PlayReadyHeader.class);
    }

    @Override // com.googlecode.mp4parser.boxes.piff.ProtectionSpecificHeader
    public UUID getSystemId() {
        return PROTECTION_SYSTEM_ID;
    }

    @Override // com.googlecode.mp4parser.boxes.piff.ProtectionSpecificHeader
    public void parse(ByteBuffer byteBuffer) {
        this.length = IsoTypeReader.readUInt32BE(byteBuffer);
        this.records = PlayReadyRecord.createFor(byteBuffer, IsoTypeReader.readUInt16BE(byteBuffer));
    }

    @Override // com.googlecode.mp4parser.boxes.piff.ProtectionSpecificHeader
    public ByteBuffer getData() {
        int i = 6;
        for (PlayReadyRecord playReadyRecord : this.records) {
            i = i + 4 + playReadyRecord.getValue().rewind().limit();
        }
        ByteBuffer allocate = ByteBuffer.allocate(i);
        IsoTypeWriter.writeUInt32BE(allocate, i);
        IsoTypeWriter.writeUInt16BE(allocate, this.records.size());
        for (PlayReadyRecord playReadyRecord2 : this.records) {
            IsoTypeWriter.writeUInt16BE(allocate, playReadyRecord2.type);
            IsoTypeWriter.writeUInt16BE(allocate, playReadyRecord2.getValue().limit());
            allocate.put(playReadyRecord2.getValue());
        }
        return allocate;
    }

    public void setRecords(List<PlayReadyRecord> list) {
        this.records = list;
    }

    public List<PlayReadyRecord> getRecords() {
        return Collections.unmodifiableList(this.records);
    }

    @Override // com.googlecode.mp4parser.boxes.piff.ProtectionSpecificHeader
    public String toString() {
        return "PlayReadyHeader{length=" + this.length + ", recordCount=" + this.records.size() + ", records=" + this.records + '}';
    }

    /* loaded from: classes3.dex */
    public static abstract class PlayReadyRecord {
        int type;

        public abstract ByteBuffer getValue();

        public abstract void parse(ByteBuffer byteBuffer);

        public PlayReadyRecord(int i) {
            this.type = i;
        }

        public static List<PlayReadyRecord> createFor(ByteBuffer byteBuffer, int i) {
            PlayReadyRecord rMHeader;
            ArrayList arrayList = new ArrayList(i);
            for (int i2 = 0; i2 < i; i2++) {
                int readUInt16BE = IsoTypeReader.readUInt16BE(byteBuffer);
                int readUInt16BE2 = IsoTypeReader.readUInt16BE(byteBuffer);
                if (readUInt16BE == 1) {
                    rMHeader = new RMHeader();
                } else if (readUInt16BE == 2) {
                    rMHeader = new DefaulPlayReadyRecord(2);
                } else if (readUInt16BE == 3) {
                    rMHeader = new EmeddedLicenseStore();
                } else {
                    rMHeader = new DefaulPlayReadyRecord(readUInt16BE);
                }
                rMHeader.parse((ByteBuffer) byteBuffer.slice().limit(readUInt16BE2));
                byteBuffer.position(byteBuffer.position() + readUInt16BE2);
                arrayList.add(rMHeader);
            }
            return arrayList;
        }

        public String toString() {
            return "PlayReadyRecord{type=" + this.type + ", length=" + getValue().limit() + '}';
        }

        /* loaded from: classes3.dex */
        public static class RMHeader extends PlayReadyRecord {
            String header;

            public RMHeader() {
                super(1);
            }

            @Override // com.googlecode.mp4parser.contentprotection.PlayReadyHeader.PlayReadyRecord
            public void parse(ByteBuffer byteBuffer) {
                try {
                    byte[] bArr = new byte[byteBuffer.slice().limit()];
                    byteBuffer.get(bArr);
                    this.header = new String(bArr, "UTF-16LE");
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override // com.googlecode.mp4parser.contentprotection.PlayReadyHeader.PlayReadyRecord
            public ByteBuffer getValue() {
                try {
                    return ByteBuffer.wrap(this.header.getBytes("UTF-16LE"));
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }

            public void setHeader(String str) {
                this.header = str;
            }

            public String getHeader() {
                return this.header;
            }

            @Override // com.googlecode.mp4parser.contentprotection.PlayReadyHeader.PlayReadyRecord
            public String toString() {
                return "RMHeader{length=" + getValue().limit() + ", header='" + this.header + "'}";
            }
        }

        /* loaded from: classes3.dex */
        public static class EmeddedLicenseStore extends PlayReadyRecord {
            ByteBuffer value;

            public EmeddedLicenseStore() {
                super(3);
            }

            @Override // com.googlecode.mp4parser.contentprotection.PlayReadyHeader.PlayReadyRecord
            public void parse(ByteBuffer byteBuffer) {
                this.value = byteBuffer.duplicate();
            }

            @Override // com.googlecode.mp4parser.contentprotection.PlayReadyHeader.PlayReadyRecord
            public ByteBuffer getValue() {
                return this.value;
            }

            @Override // com.googlecode.mp4parser.contentprotection.PlayReadyHeader.PlayReadyRecord
            public String toString() {
                return "EmeddedLicenseStore{length=" + getValue().limit() + '}';
            }
        }

        /* loaded from: classes3.dex */
        public static class DefaulPlayReadyRecord extends PlayReadyRecord {
            ByteBuffer value;

            public DefaulPlayReadyRecord(int i) {
                super(i);
            }

            @Override // com.googlecode.mp4parser.contentprotection.PlayReadyHeader.PlayReadyRecord
            public void parse(ByteBuffer byteBuffer) {
                this.value = byteBuffer.duplicate();
            }

            @Override // com.googlecode.mp4parser.contentprotection.PlayReadyHeader.PlayReadyRecord
            public ByteBuffer getValue() {
                return this.value;
            }
        }
    }
}
