package com.danikula.videocache;

/* loaded from: classes2.dex */
public class ProxyCacheException extends Exception {
    private static final String LIBRARY_VERSION = ". Version: 1.0";

    public ProxyCacheException(String str) {
        super(str + LIBRARY_VERSION);
    }

    public ProxyCacheException(String str, Throwable th) {
        super(str + LIBRARY_VERSION, th);
    }

    public ProxyCacheException(Throwable th) {
        super("No explanation error. Version: 1.0", th);
    }
}
