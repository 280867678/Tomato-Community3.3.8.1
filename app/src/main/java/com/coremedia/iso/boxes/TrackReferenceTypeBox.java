package com.coremedia.iso.boxes;

import com.coremedia.iso.IsoTypeReader;
import com.coremedia.iso.IsoTypeWriter;
import com.googlecode.mp4parser.AbstractBox;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public class TrackReferenceTypeBox extends AbstractBox {
    public static final String TYPE1 = "hint";
    public static final String TYPE2 = "cdsc";
    private long[] trackIds;

    public TrackReferenceTypeBox(String str) {
        super(str);
    }

    public long[] getTrackIds() {
        return this.trackIds;
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    public void _parseDetails(ByteBuffer byteBuffer) {
        int remaining = byteBuffer.remaining() / 4;
        this.trackIds = new long[remaining];
        for (int i = 0; i < remaining; i++) {
            this.trackIds[i] = IsoTypeReader.readUInt32(byteBuffer);
        }
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    protected void getContent(ByteBuffer byteBuffer) {
        for (long j : this.trackIds) {
            IsoTypeWriter.writeUInt32(byteBuffer, j);
        }
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    protected long getContentSize() {
        return this.trackIds.length * 4;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TrackReferenceTypeBox[type=");
        sb.append(getType());
        for (int i = 0; i < this.trackIds.length; i++) {
            sb.append(";trackId");
            sb.append(i);
            sb.append(SimpleComparison.EQUAL_TO_OPERATION);
            sb.append(this.trackIds[i]);
        }
        sb.append("]");
        return sb.toString();
    }
}
