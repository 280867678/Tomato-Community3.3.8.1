package com.coremedia.iso.boxes.dece;

import com.coremedia.iso.IsoTypeReader;
import com.coremedia.iso.IsoTypeWriter;
import com.googlecode.mp4parser.AbstractFullBox;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class TrickPlayBox extends AbstractFullBox {
    public static final String TYPE = "trik";
    private List<Entry> entries = new ArrayList();

    public TrickPlayBox() {
        super(TYPE);
    }

    public void setEntries(List<Entry> list) {
        this.entries = list;
    }

    public List<Entry> getEntries() {
        return this.entries;
    }

    /* loaded from: classes2.dex */
    public static class Entry {
        private int value;

        public Entry() {
        }

        public Entry(int i) {
            this.value = i;
        }

        public int getPicType() {
            return (this.value >> 6) & 3;
        }

        public void setPicType(int i) {
            this.value &= 31;
            this.value = ((i & 3) << 6) | this.value;
        }

        public int getDependencyLevel() {
            return this.value & 63;
        }

        public void setDependencyLevel(int i) {
            this.value = (i & 63) | this.value;
        }

        public String toString() {
            return "Entry{picType=" + getPicType() + ",dependencyLevel=" + getDependencyLevel() + '}';
        }
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    protected long getContentSize() {
        return this.entries.size() + 4;
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    public void _parseDetails(ByteBuffer byteBuffer) {
        parseVersionAndFlags(byteBuffer);
        while (byteBuffer.remaining() > 0) {
            this.entries.add(new Entry(IsoTypeReader.readUInt8(byteBuffer)));
        }
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    protected void getContent(ByteBuffer byteBuffer) {
        writeVersionAndFlags(byteBuffer);
        for (Entry entry : this.entries) {
            IsoTypeWriter.writeUInt8(byteBuffer, entry.value);
        }
    }

    public String toString() {
        return "TrickPlayBox{entries=" + this.entries + '}';
    }
}
