package com.shizhefei.view.largeimage.factory;

import android.graphics.BitmapRegionDecoder;
import java.io.File;
import java.io.IOException;

/* loaded from: classes3.dex */
public class FileBitmapDecoderFactory implements BitmapDecoderFactory {
    private String path;

    public FileBitmapDecoderFactory(String str) {
        this.path = str;
    }

    public FileBitmapDecoderFactory(File file) {
        this.path = file.getAbsolutePath();
    }

    @Override // com.shizhefei.view.largeimage.factory.BitmapDecoderFactory
    public BitmapRegionDecoder made() throws IOException {
        return BitmapRegionDecoder.newInstance(this.path, false);
    }
}
