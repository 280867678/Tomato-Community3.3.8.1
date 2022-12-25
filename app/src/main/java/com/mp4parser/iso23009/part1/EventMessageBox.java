package com.mp4parser.iso23009.part1;

import com.coremedia.iso.IsoTypeReader;
import com.coremedia.iso.IsoTypeWriter;
import com.coremedia.iso.Utf8;
import com.googlecode.mp4parser.AbstractFullBox;
import java.nio.ByteBuffer;

/* loaded from: classes3.dex */
public class EventMessageBox extends AbstractFullBox {
    public static final String TYPE = "emsg";
    long eventDuration;

    /* renamed from: id */
    long f1561id;
    byte[] messageData;
    long presentationTimeDelta;
    String schemeIdUri;
    long timescale;
    String value;

    public EventMessageBox() {
        super(TYPE);
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    protected void _parseDetails(ByteBuffer byteBuffer) {
        parseVersionAndFlags(byteBuffer);
        this.schemeIdUri = IsoTypeReader.readString(byteBuffer);
        this.value = IsoTypeReader.readString(byteBuffer);
        this.timescale = IsoTypeReader.readUInt32(byteBuffer);
        this.presentationTimeDelta = IsoTypeReader.readUInt32(byteBuffer);
        this.eventDuration = IsoTypeReader.readUInt32(byteBuffer);
        this.f1561id = IsoTypeReader.readUInt32(byteBuffer);
        this.messageData = new byte[byteBuffer.remaining()];
        byteBuffer.get(this.messageData);
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    protected void getContent(ByteBuffer byteBuffer) {
        writeVersionAndFlags(byteBuffer);
        IsoTypeWriter.writeUtf8String(byteBuffer, this.schemeIdUri);
        IsoTypeWriter.writeUtf8String(byteBuffer, this.value);
        IsoTypeWriter.writeUInt32(byteBuffer, this.timescale);
        IsoTypeWriter.writeUInt32(byteBuffer, this.presentationTimeDelta);
        IsoTypeWriter.writeUInt32(byteBuffer, this.eventDuration);
        IsoTypeWriter.writeUInt32(byteBuffer, this.f1561id);
        byteBuffer.put(this.messageData);
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    protected long getContentSize() {
        return Utf8.utf8StringLengthInBytes(this.schemeIdUri) + 22 + Utf8.utf8StringLengthInBytes(this.value) + this.messageData.length;
    }

    public String getSchemeIdUri() {
        return this.schemeIdUri;
    }

    public void setSchemeIdUri(String str) {
        this.schemeIdUri = str;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String str) {
        this.value = str;
    }

    public long getTimescale() {
        return this.timescale;
    }

    public void setTimescale(long j) {
        this.timescale = j;
    }

    public long getPresentationTimeDelta() {
        return this.presentationTimeDelta;
    }

    public void setPresentationTimeDelta(long j) {
        this.presentationTimeDelta = j;
    }

    public long getEventDuration() {
        return this.eventDuration;
    }

    public void setEventDuration(long j) {
        this.eventDuration = j;
    }

    public long getId() {
        return this.f1561id;
    }

    public void setId(long j) {
        this.f1561id = j;
    }

    public byte[] getMessageData() {
        return this.messageData;
    }

    public void setMessageData(byte[] bArr) {
        this.messageData = bArr;
    }
}
