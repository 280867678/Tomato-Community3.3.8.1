package com.bumptech.glide.load.resource.file;

import android.support.annotation.NonNull;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import java.io.File;

/* loaded from: classes2.dex */
public class FileDecoder implements ResourceDecoder<File, File> {
    @Override // com.bumptech.glide.load.ResourceDecoder
    public boolean handles(@NonNull File file, @NonNull Options options) {
        return true;
    }

    @Override // com.bumptech.glide.load.ResourceDecoder
    public Resource<File> decode(@NonNull File file, int i, int i2, @NonNull Options options) {
        return new FileResource(file);
    }
}
