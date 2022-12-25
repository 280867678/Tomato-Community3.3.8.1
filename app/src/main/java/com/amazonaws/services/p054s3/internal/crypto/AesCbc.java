package com.amazonaws.services.p054s3.internal.crypto;

@Deprecated
/* renamed from: com.amazonaws.services.s3.internal.crypto.AesCbc */
/* loaded from: classes2.dex */
class AesCbc extends ContentCryptoScheme {
    @Override // com.amazonaws.services.p054s3.internal.crypto.ContentCryptoScheme
    int getBlockSizeInBytes() {
        return 16;
    }

    @Override // com.amazonaws.services.p054s3.internal.crypto.ContentCryptoScheme
    String getCipherAlgorithm() {
        return "AES/CBC/PKCS5Padding";
    }

    @Override // com.amazonaws.services.p054s3.internal.crypto.ContentCryptoScheme
    int getIVLengthInBytes() {
        return 16;
    }

    @Override // com.amazonaws.services.p054s3.internal.crypto.ContentCryptoScheme
    String getKeyGeneratorAlgorithm() {
        return "AES";
    }

    @Override // com.amazonaws.services.p054s3.internal.crypto.ContentCryptoScheme
    int getKeyLengthInBits() {
        return 256;
    }
}
