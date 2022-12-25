package com.facebook.imagepipeline.nativecode;

import com.facebook.soloader.SoLoader;
import java.util.ArrayList;
import java.util.Collections;

/* loaded from: classes2.dex */
public class ImagePipelineNativeLoader {
    static {
        Collections.unmodifiableList(new ArrayList());
    }

    public static void load() {
        SoLoader.loadLibrary("imagepipeline");
    }
}
