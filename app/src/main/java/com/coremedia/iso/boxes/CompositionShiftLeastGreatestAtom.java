package com.coremedia.iso.boxes;

import com.googlecode.mp4parser.AbstractFullBox;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public class CompositionShiftLeastGreatestAtom extends AbstractFullBox {
    public static final String TYPE = "cslg";
    int compositionOffsetToDisplayOffsetShift;
    int displayEndTime;
    int displayStartTime;
    int greatestDisplayOffset;
    int leastDisplayOffset;

    @Override // com.googlecode.mp4parser.AbstractBox
    protected long getContentSize() {
        return 24L;
    }

    public CompositionShiftLeastGreatestAtom() {
        super(TYPE);
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    public void _parseDetails(ByteBuffer byteBuffer) {
        parseVersionAndFlags(byteBuffer);
        this.compositionOffsetToDisplayOffsetShift = byteBuffer.getInt();
        this.leastDisplayOffset = byteBuffer.getInt();
        this.greatestDisplayOffset = byteBuffer.getInt();
        this.displayStartTime = byteBuffer.getInt();
        this.displayEndTime = byteBuffer.getInt();
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    protected void getContent(ByteBuffer byteBuffer) {
        writeVersionAndFlags(byteBuffer);
        byteBuffer.putInt(this.compositionOffsetToDisplayOffsetShift);
        byteBuffer.putInt(this.leastDisplayOffset);
        byteBuffer.putInt(this.greatestDisplayOffset);
        byteBuffer.putInt(this.displayStartTime);
        byteBuffer.putInt(this.displayEndTime);
    }

    public int getCompositionOffsetToDisplayOffsetShift() {
        return this.compositionOffsetToDisplayOffsetShift;
    }

    public void setCompositionOffsetToDisplayOffsetShift(int i) {
        this.compositionOffsetToDisplayOffsetShift = i;
    }

    public int getLeastDisplayOffset() {
        return this.leastDisplayOffset;
    }

    public void setLeastDisplayOffset(int i) {
        this.leastDisplayOffset = i;
    }

    public int getGreatestDisplayOffset() {
        return this.greatestDisplayOffset;
    }

    public void setGreatestDisplayOffset(int i) {
        this.greatestDisplayOffset = i;
    }

    public int getDisplayStartTime() {
        return this.displayStartTime;
    }

    public void setDisplayStartTime(int i) {
        this.displayStartTime = i;
    }

    public int getDisplayEndTime() {
        return this.displayEndTime;
    }

    public void setDisplayEndTime(int i) {
        this.displayEndTime = i;
    }
}
