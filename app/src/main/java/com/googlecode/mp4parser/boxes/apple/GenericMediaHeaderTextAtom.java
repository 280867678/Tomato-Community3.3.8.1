package com.googlecode.mp4parser.boxes.apple;

import com.googlecode.mp4parser.AbstractBox;
import java.nio.ByteBuffer;

/* loaded from: classes3.dex */
public class GenericMediaHeaderTextAtom extends AbstractBox {
    public static final String TYPE = "text";
    int unknown_2;
    int unknown_3;
    int unknown_4;
    int unknown_6;
    int unknown_7;
    int unknown_8;
    int unknown_1 = 65536;
    int unknown_5 = 65536;
    int unknown_9 = 1073741824;

    @Override // com.googlecode.mp4parser.AbstractBox
    protected long getContentSize() {
        return 36L;
    }

    public GenericMediaHeaderTextAtom() {
        super("text");
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    protected void getContent(ByteBuffer byteBuffer) {
        byteBuffer.putInt(this.unknown_1);
        byteBuffer.putInt(this.unknown_2);
        byteBuffer.putInt(this.unknown_3);
        byteBuffer.putInt(this.unknown_4);
        byteBuffer.putInt(this.unknown_5);
        byteBuffer.putInt(this.unknown_6);
        byteBuffer.putInt(this.unknown_7);
        byteBuffer.putInt(this.unknown_8);
        byteBuffer.putInt(this.unknown_9);
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    protected void _parseDetails(ByteBuffer byteBuffer) {
        this.unknown_1 = byteBuffer.getInt();
        this.unknown_2 = byteBuffer.getInt();
        this.unknown_3 = byteBuffer.getInt();
        this.unknown_4 = byteBuffer.getInt();
        this.unknown_5 = byteBuffer.getInt();
        this.unknown_6 = byteBuffer.getInt();
        this.unknown_7 = byteBuffer.getInt();
        this.unknown_8 = byteBuffer.getInt();
        this.unknown_9 = byteBuffer.getInt();
    }

    public int getUnknown_1() {
        return this.unknown_1;
    }

    public void setUnknown_1(int i) {
        this.unknown_1 = i;
    }

    public int getUnknown_2() {
        return this.unknown_2;
    }

    public void setUnknown_2(int i) {
        this.unknown_2 = i;
    }

    public int getUnknown_3() {
        return this.unknown_3;
    }

    public void setUnknown_3(int i) {
        this.unknown_3 = i;
    }

    public int getUnknown_4() {
        return this.unknown_4;
    }

    public void setUnknown_4(int i) {
        this.unknown_4 = i;
    }

    public int getUnknown_5() {
        return this.unknown_5;
    }

    public void setUnknown_5(int i) {
        this.unknown_5 = i;
    }

    public int getUnknown_6() {
        return this.unknown_6;
    }

    public void setUnknown_6(int i) {
        this.unknown_6 = i;
    }

    public int getUnknown_7() {
        return this.unknown_7;
    }

    public void setUnknown_7(int i) {
        this.unknown_7 = i;
    }

    public int getUnknown_8() {
        return this.unknown_8;
    }

    public void setUnknown_8(int i) {
        this.unknown_8 = i;
    }

    public int getUnknown_9() {
        return this.unknown_9;
    }

    public void setUnknown_9(int i) {
        this.unknown_9 = i;
    }
}
