package com.amazonaws.services.p054s3.internal.crypto;

@Deprecated
/* renamed from: com.amazonaws.services.s3.internal.crypto.AesGcm */
/* loaded from: classes2.dex */
class AesGcm extends ContentCryptoScheme {
    @Override // com.amazonaws.services.p054s3.internal.crypto.ContentCryptoScheme
    int getBlockSizeInBytes() {
        return 16;
    }

    @Override // com.amazonaws.services.p054s3.internal.crypto.ContentCryptoScheme
    String getCipherAlgorithm() {
        return "AES/GCM/NoPadding";
    }

    @Override // com.amazonaws.services.p054s3.internal.crypto.ContentCryptoScheme
    int getIVLengthInBytes() {
        return 12;
    }

    @Override // com.amazonaws.services.p054s3.internal.crypto.ContentCryptoScheme
    String getKeyGeneratorAlgorithm() {
        return "AES";
    }

    @Override // com.amazonaws.services.p054s3.internal.crypto.ContentCryptoScheme
    int getKeyLengthInBits() {
        return 256;
    }

    @Override // com.amazonaws.services.p054s3.internal.crypto.ContentCryptoScheme
    String getSpecificCipherProvider() {
        return "BC";
    }

    @Override // com.amazonaws.services.p054s3.internal.crypto.ContentCryptoScheme
    int getTagLengthInBits() {
        return 128;
    }
}
