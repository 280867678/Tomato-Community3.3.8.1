package com.danikula.videocache;

import com.danikula.videocache.file.DiskUsage;
import com.danikula.videocache.file.FileNameGenerator;
import com.danikula.videocache.headers.HeaderInjector;
import com.danikula.videocache.sourcestorage.SourceInfoStorage;
import java.io.File;

/* loaded from: classes2.dex */
public class Config {
    public File cacheRoot;
    public String cdnUrl;
    public final DiskUsage diskUsage;
    public final HeaderInjector headerInjector;
    public final SourceInfoStorage sourceInfoStorage;

    /* JADX INFO: Access modifiers changed from: package-private */
    public Config(File file, FileNameGenerator fileNameGenerator, DiskUsage diskUsage, SourceInfoStorage sourceInfoStorage, HeaderInjector headerInjector, String str, String str2) {
        this.cacheRoot = file;
        this.diskUsage = diskUsage;
        this.sourceInfoStorage = sourceInfoStorage;
        this.headerInjector = headerInjector;
        this.cdnUrl = str;
    }
}
