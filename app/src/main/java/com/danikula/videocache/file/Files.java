package com.danikula.videocache.file;

import java.io.File;
import java.io.IOException;
import org.slf4j.LoggerFactory;

/* loaded from: classes2.dex */
class Files {
    static {
        LoggerFactory.getLogger("Files");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void makeDir(File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                return;
            }
            throw new IOException("File " + file + " is not directory!");
        } else if (!file.mkdirs()) {
            throw new IOException(String.format("Directory %s can't be created", file.getAbsolutePath()));
        }
    }
}
