package com.googlecode.mp4parser.boxes.mp4.samplegrouping;

import com.coremedia.iso.Hex;
import java.nio.ByteBuffer;

/* loaded from: classes3.dex */
public class UnknownEntry extends GroupEntry {
    private ByteBuffer content;
    private String type;

    public UnknownEntry(String str) {
        this.type = str;
    }

    @Override // com.googlecode.mp4parser.boxes.mp4.samplegrouping.GroupEntry
    public String getType() {
        return this.type;
    }

    public ByteBuffer getContent() {
        return this.content;
    }

    public void setContent(ByteBuffer byteBuffer) {
        this.content = (ByteBuffer) byteBuffer.duplicate().rewind();
    }

    @Override // com.googlecode.mp4parser.boxes.mp4.samplegrouping.GroupEntry
    public void parse(ByteBuffer byteBuffer) {
        this.content = (ByteBuffer) byteBuffer.duplicate().rewind();
    }

    @Override // com.googlecode.mp4parser.boxes.mp4.samplegrouping.GroupEntry
    public ByteBuffer get() {
        return this.content.duplicate();
    }

    public String toString() {
        ByteBuffer duplicate = this.content.duplicate();
        duplicate.rewind();
        byte[] bArr = new byte[duplicate.limit()];
        duplicate.get(bArr);
        return "UnknownEntry{content=" + Hex.encodeHex(bArr) + '}';
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || UnknownEntry.class != obj.getClass()) {
            return false;
        }
        ByteBuffer byteBuffer = this.content;
        ByteBuffer byteBuffer2 = ((UnknownEntry) obj).content;
        return byteBuffer == null ? byteBuffer2 == null : byteBuffer.equals(byteBuffer2);
    }

    public int hashCode() {
        ByteBuffer byteBuffer = this.content;
        if (byteBuffer != null) {
            return byteBuffer.hashCode();
        }
        return 0;
    }
}
