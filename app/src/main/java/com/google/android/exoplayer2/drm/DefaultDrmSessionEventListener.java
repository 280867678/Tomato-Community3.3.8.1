package com.google.android.exoplayer2.drm;

import android.os.Handler;

/* loaded from: classes2.dex */
public interface DefaultDrmSessionEventListener {

    /* loaded from: classes2.dex */
    public static final class EventDispatcher {
        public void addListener(Handler handler, DefaultDrmSessionEventListener defaultDrmSessionEventListener) {
            throw null;
        }

        public void drmKeysLoaded() {
            throw null;
        }

        public void drmKeysRemoved() {
            throw null;
        }

        public void drmKeysRestored() {
            throw null;
        }

        public void drmSessionManagerError(Exception exc) {
            throw null;
        }
    }
}
