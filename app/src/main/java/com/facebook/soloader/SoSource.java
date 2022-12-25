package com.facebook.soloader;

import android.os.StrictMode;
import java.io.IOException;

/* loaded from: classes2.dex */
public abstract class SoSource {
    public abstract int loadLibrary(String str, int i, StrictMode.ThreadPolicy threadPolicy) throws IOException;

    /* JADX INFO: Access modifiers changed from: protected */
    public void prepare(int i) throws IOException {
    }

    public String toString() {
        return getClass().getName();
    }
}
