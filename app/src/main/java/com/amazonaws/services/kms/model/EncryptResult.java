package com.amazonaws.services.kms.model;

import java.io.Serializable;
import java.nio.ByteBuffer;

/* loaded from: classes2.dex */
public class EncryptResult implements Serializable {
    private ByteBuffer ciphertextBlob;
    private String keyId;

    public ByteBuffer getCiphertextBlob() {
        return this.ciphertextBlob;
    }

    public void setCiphertextBlob(ByteBuffer byteBuffer) {
        this.ciphertextBlob = byteBuffer;
    }

    public EncryptResult withCiphertextBlob(ByteBuffer byteBuffer) {
        this.ciphertextBlob = byteBuffer;
        return this;
    }

    public String getKeyId() {
        return this.keyId;
    }

    public void setKeyId(String str) {
        this.keyId = str;
    }

    public EncryptResult withKeyId(String str) {
        this.keyId = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getCiphertextBlob() != null) {
            sb.append("CiphertextBlob: " + getCiphertextBlob() + ",");
        }
        if (getKeyId() != null) {
            sb.append("KeyId: " + getKeyId());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((getCiphertextBlob() == null ? 0 : getCiphertextBlob().hashCode()) + 31) * 31;
        if (getKeyId() != null) {
            i = getKeyId().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof EncryptResult)) {
            return false;
        }
        EncryptResult encryptResult = (EncryptResult) obj;
        if ((encryptResult.getCiphertextBlob() == null) ^ (getCiphertextBlob() == null)) {
            return false;
        }
        if (encryptResult.getCiphertextBlob() != null && !encryptResult.getCiphertextBlob().equals(getCiphertextBlob())) {
            return false;
        }
        if ((encryptResult.getKeyId() == null) ^ (getKeyId() == null)) {
            return false;
        }
        return encryptResult.getKeyId() == null || encryptResult.getKeyId().equals(getKeyId());
    }
}
