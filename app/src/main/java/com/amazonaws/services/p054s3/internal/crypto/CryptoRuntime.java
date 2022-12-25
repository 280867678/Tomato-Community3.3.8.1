package com.amazonaws.services.p054s3.internal.crypto;

import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import java.security.Provider;
import java.security.Security;
import javax.crypto.Cipher;

@Deprecated
/* renamed from: com.amazonaws.services.s3.internal.crypto.CryptoRuntime */
/* loaded from: classes2.dex */
public class CryptoRuntime {
    private static final Log LOGGER = LogFactory.getLog(CryptoRuntime.class);

    public static synchronized boolean isBouncyCastleAvailable() {
        boolean z;
        synchronized (CryptoRuntime.class) {
            z = Security.getProvider("BC") != null;
        }
        return z;
    }

    public static synchronized void enableBouncyCastle() {
        synchronized (CryptoRuntime.class) {
            if (isBouncyCastleAvailable()) {
                return;
            }
            try {
                Security.addProvider((Provider) Class.forName("org.bouncycastle.jce.provider.BouncyCastleProvider").newInstance());
            } catch (Exception e) {
                LOGGER.debug("Bouncy Castle not available", e);
            }
        }
    }

    public static boolean isAesGcmAvailable(Provider provider) {
        if (provider == null) {
            provider = Security.getProvider("BC");
        }
        return AesGcm.check(provider);
    }

    /* renamed from: com.amazonaws.services.s3.internal.crypto.CryptoRuntime$AesGcm */
    /* loaded from: classes2.dex */
    private static final class AesGcm {
        /* JADX INFO: Access modifiers changed from: private */
        public static boolean check(Provider provider) {
            try {
                Cipher.getInstance(ContentCryptoScheme.AES_GCM.getCipherAlgorithm(), provider);
                return true;
            } catch (Exception unused) {
                return false;
            }
        }
    }
}
