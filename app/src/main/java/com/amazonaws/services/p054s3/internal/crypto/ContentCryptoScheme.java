package com.amazonaws.services.p054s3.internal.crypto;

/* JADX INFO: Access modifiers changed from: package-private */
@Deprecated
/* renamed from: com.amazonaws.services.s3.internal.crypto.ContentCryptoScheme */
/* loaded from: classes2.dex */
public abstract class ContentCryptoScheme {
    static final ContentCryptoScheme AES_GCM = new AesGcm();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract int getBlockSizeInBytes();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract String getCipherAlgorithm();

    abstract int getIVLengthInBytes();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract String getKeyGeneratorAlgorithm();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract int getKeyLengthInBits();

    String getSpecificCipherProvider() {
        return null;
    }

    int getTagLengthInBits() {
        return 0;
    }

    static {
        new AesCbc();
        new AesCtr();
    }

    public String toString() {
        return "cipherAlgo=" + getCipherAlgorithm() + ", blockSizeInBytes=" + getBlockSizeInBytes() + ", ivLengthInBytes=" + getIVLengthInBytes() + ", keyGenAlgo=" + getKeyGeneratorAlgorithm() + ", keyLengthInBits=" + getKeyLengthInBits() + ", specificProvider=" + getSpecificCipherProvider() + ", tagLengthInBits=" + getTagLengthInBits();
    }
}
