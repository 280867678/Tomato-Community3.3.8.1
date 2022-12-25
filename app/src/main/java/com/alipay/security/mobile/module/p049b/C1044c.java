package com.alipay.security.mobile.module.p049b;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

/* renamed from: com.alipay.security.mobile.module.b.c */
/* loaded from: classes2.dex */
final class C1044c implements FileFilter {
    /* JADX INFO: Access modifiers changed from: package-private */
    public C1044c(C1043b c1043b) {
    }

    @Override // java.io.FileFilter
    public final boolean accept(File file) {
        return Pattern.matches("cpu[0-9]+", file.getName());
    }
}
