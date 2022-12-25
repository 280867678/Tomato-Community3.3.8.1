package com.gen.p059mh.webapp_extensions.matisse.internal.entity;

/* renamed from: com.gen.mh.webapp_extensions.matisse.internal.entity.CaptureStrategy */
/* loaded from: classes2.dex */
public class CaptureStrategy {
    public final String authority;
    public final String directory;
    public final boolean isPublic;

    public CaptureStrategy(boolean z, String str) {
        this(z, str, null);
    }

    public CaptureStrategy(boolean z, String str, String str2) {
        this.isPublic = z;
        this.authority = str;
        this.directory = str2;
    }
}
