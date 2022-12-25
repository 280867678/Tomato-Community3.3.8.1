package com.amazonaws.services.p054s3.internal.crypto;

@Deprecated
/* renamed from: com.amazonaws.services.s3.internal.crypto.AesCtr */
/* loaded from: classes2.dex */
class AesCtr extends ContentCryptoScheme {
    @Override // com.amazonaws.services.p054s3.internal.crypto.ContentCryptoScheme
    String getCipherAlgorithm() {
        return "AES/CTR/NoPadding";
    }

    @Override // com.amazonaws.services.p054s3.internal.crypto.ContentCryptoScheme
    int getIVLengthInBytes() {
        return 16;
    }

    @Override // com.amazonaws.services.p054s3.internal.crypto.ContentCryptoScheme
    String getKeyGeneratorAlgorithm() {
        return ContentCryptoScheme.AES_GCM.getKeyGeneratorAlgorithm();
    }

    @Override // com.amazonaws.services.p054s3.internal.crypto.ContentCryptoScheme
    int getKeyLengthInBits() {
        return ContentCryptoScheme.AES_GCM.getKeyLengthInBits();
    }

    @Override // com.amazonaws.services.p054s3.internal.crypto.ContentCryptoScheme
    int getBlockSizeInBytes() {
        return ContentCryptoScheme.AES_GCM.getBlockSizeInBytes();
    }
}
