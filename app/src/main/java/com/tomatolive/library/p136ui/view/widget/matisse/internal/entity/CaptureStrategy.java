package com.tomatolive.library.p136ui.view.widget.matisse.internal.entity;

/* renamed from: com.tomatolive.library.ui.view.widget.matisse.internal.entity.CaptureStrategy */
/* loaded from: classes4.dex */
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
