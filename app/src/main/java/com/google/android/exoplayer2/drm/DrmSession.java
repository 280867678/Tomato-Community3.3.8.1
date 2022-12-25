package com.google.android.exoplayer2.drm;

import android.annotation.TargetApi;
import com.google.android.exoplayer2.drm.ExoMediaCrypto;
import java.util.Map;

@TargetApi(16)
/* loaded from: classes2.dex */
public interface DrmSession<T extends ExoMediaCrypto> {
    DrmSessionException getError();

    T getMediaCrypto();

    int getState();

    Map<String, String> queryKeyStatus();

    /* loaded from: classes2.dex */
    public static class DrmSessionException extends Exception {
        public DrmSessionException(Throwable th) {
            super(th);
        }
    }
}
