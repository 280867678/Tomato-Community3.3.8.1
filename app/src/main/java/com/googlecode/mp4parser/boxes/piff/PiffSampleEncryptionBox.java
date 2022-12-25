package com.googlecode.mp4parser.boxes.piff;

import com.coremedia.iso.boxes.UserBox;
import com.googlecode.mp4parser.annotations.DoNotParseDetail;
import com.googlecode.mp4parser.boxes.AbstractSampleEncryptionBox;

/* loaded from: classes3.dex */
public class PiffSampleEncryptionBox extends AbstractSampleEncryptionBox {
    public PiffSampleEncryptionBox() {
        super(UserBox.TYPE);
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    public byte[] getUserType() {
        return new byte[]{-94, 57, 79, 82, 90, -101, 79, 20, -94, 68, 108, 66, 124, 100, -115, -12};
    }

    public int getAlgorithmId() {
        return this.algorithmId;
    }

    public void setAlgorithmId(int i) {
        this.algorithmId = i;
    }

    public int getIvSize() {
        return this.ivSize;
    }

    public void setIvSize(int i) {
        this.ivSize = i;
    }

    public byte[] getKid() {
        return this.kid;
    }

    public void setKid(byte[] bArr) {
        this.kid = bArr;
    }

    @Override // com.googlecode.mp4parser.boxes.AbstractSampleEncryptionBox
    @DoNotParseDetail
    public boolean isOverrideTrackEncryptionBoxParameters() {
        return (getFlags() & 1) > 0;
    }

    @DoNotParseDetail
    public void setOverrideTrackEncryptionBoxParameters(boolean z) {
        if (z) {
            setFlags(getFlags() | 1);
        } else {
            setFlags(getFlags() & 16777214);
        }
    }
}
